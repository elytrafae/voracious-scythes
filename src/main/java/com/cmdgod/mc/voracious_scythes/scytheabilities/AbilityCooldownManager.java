package com.cmdgod.mc.voracious_scythes.scytheabilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.cmdgod.mc.voracious_scythes.CustomEntityAttributes;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.huds.CooldownHUD;
import com.cmdgod.mc.voracious_scythes.mixinextensions.PlayerExtension;
import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AbilityCooldownManager {

    private final Map<ScytheAbilityBase, Entry> entries = Maps.newHashMap();
    private int tick = 0;

    public ArrayList<HUDEntry> getDataForHUD() {
        ArrayList<HUDEntry> hudEntries = new ArrayList<HUDEntry>();
        Iterator<Map.Entry<ScytheAbilityBase, Entry>> iterator = this.entries.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ScytheAbilityBase, Entry> listEntry = iterator.next();
            ScytheAbilityBase ability = listEntry.getKey();
            Entry entry = listEntry.getValue();
            if (entry.isCooldownOff()) {continue;}
            HUDEntry hudEntry = new HUDEntry();
            hudEntry.ability = ability;
            hudEntry.cooldownLeft = entry.endTick - tick;
            hudEntry.chargesLeft = entry.charges;
            hudEntries.add(hudEntry);
        }
        hudEntries.sort((a, b) -> {return b.cooldownLeft - a.cooldownLeft;});
        return hudEntries;
    }

    private Entry addEntry(ScytheAbilityBase ability) {
        Entry entry = new Entry();
        entry.charges = ability.charges;
        entries.put(ability, entry);
        return entry;
    }

    private Entry getEntryFor(ScytheAbilityBase ability) {
        if (entries.containsKey(ability)) {
            return entries.get(ability);
        }
        return addEntry(ability);
    }

    public void useChargeFor(ScytheAbilityBase ability, PlayerEntity player) {
        Entry entry = getEntryFor(ability);
        if (!canUse(entry)) {
            return;
        }
        entry.charges--;
        if (entry.isCooldownOff()) {
            startCooldownFor(ability, entry, player);
            //entry.setCooldown(ability.cooldown);
        }
    }

    private void startCooldownFor(ScytheAbilityBase ability, Entry entry, PlayerEntity player) {
        int realCooldown = (int)Math.floor((ability.cooldown*2) - (player.getAttributeInstance(CustomEntityAttributes.COOLDOWN_REDUCTION).getValue()*ability.cooldown));
        entry.setCooldown(realCooldown);
    }

    public void reduceCooldownFor(ScytheAbilityBase ability, int amount) {
        Entry entry = getEntryFor(ability);
        if (entry.isCooldownOff()) {
            return;
        }
        entry.endTick -= amount;
        updateCooldown(ability, entry, null);
    }

    public int getChargesFor(ScytheAbilityBase ability) {
        Entry entry = getEntryFor(ability);
        return entry.charges;
    }

    public int getCurrentCooldownFor(ScytheAbilityBase ability) {
        Entry entry = getEntryFor(ability);
        if (entry.isCooldownOff() || entry.endTick < tick) {
            return 0;
        }
        return entry.endTick - tick;
    }

    public void clearEverything() {
        entries.clear();
        tick = 0;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void restoreSaveData(String id, int startTick, int endTick, int charges) {
        Identifier identifier = new Identifier(id);
        ScytheAbilityBase ability = ScytheAbilityBase.ABILITY_REGISTRY.get(identifier);
        if (ability == null) {
            System.out.println("ABILITY WITH AN ID OF " + id + " NOT FOUND!");
            return;
        }
        Entry entry = this.getEntryFor(ability);
        entry.charges = charges;
        entry.startTick = startTick;
        entry.endTick = endTick;
    }

    public AbilitySaveData getSaveData() {
        AbilitySaveData save = new AbilitySaveData();
        int minStartTick = tick;
        if (!this.entries.isEmpty()) {
            Iterator<Map.Entry<ScytheAbilityBase, Entry>> iterator = this.entries.entrySet().iterator();
            minStartTick = getSmallestStartTick(this.entries.entrySet().iterator()); // Give this a new iterator!
            while (iterator.hasNext()) {
                Map.Entry<ScytheAbilityBase, Entry> entry = iterator.next();
                Entry e = entry.getValue();
                if (e.isCooldownOff()) {continue;} // We won't add entries that are completely off.
                save.dtos.add(new AbilitySaveDTO(e.startTick - minStartTick, e.endTick - minStartTick, e.charges, entry.getKey().id.toString()));
            }
        }
        save.tick = tick - minStartTick;
        return save;
    }

    private int getSmallestStartTick(Iterator<Map.Entry<ScytheAbilityBase, Entry>> iterator) {
        int min = tick;
        while (iterator.hasNext()) {
            Map.Entry<ScytheAbilityBase, Entry> entry = iterator.next();
            if (entry.getValue().isCooldownOff()) {continue;} // We won't add entries that are completely off.
            int s = entry.getValue().startTick;
            if (s < min) {
                min = s;
            }
        }
        return min;
    }

    public void tick(PlayerExtension playerExt) {
        this.tick++;
        if (!this.entries.isEmpty()) {
            Iterator<Map.Entry<ScytheAbilityBase, Entry>> iterator = this.entries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<ScytheAbilityBase, Entry> entry = iterator.next();
                this.updateCooldown(entry.getKey(), entry.getValue(), playerExt);
            }
        }
        PlayerEntity player = (PlayerEntity)((Object)playerExt);
        World world = player.getWorld();
        if (tick % 5 == 0 && world.isClient && player.isMainPlayer()) {
            CooldownHUD.tick(this);
        }
        if (tick % 20 == 0 && !world.isClient) {
            updateClientOnCooldowns(player);
        }
    }

    private void updateClientOnCooldowns(PlayerEntity player) {
        AbilitySaveData saveData = getSaveData();
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(saveData.tick); // The current tick
        buf.writeInt(saveData.dtos.size()); // How many DTOs are we sending?
        for (int i=0; i < saveData.dtos.size(); i++) {
            AbilitySaveDTO dto = saveData.dtos.get(i);
            buf.writeIdentifier(new Identifier(dto.id));
            buf.writeInt(dto.startTick);
            buf.writeInt(dto.endTick);
            buf.writeInt(dto.charges);
        }
        //buf.writeInt(slotIndex);
        //buf.writeInt(discSlot);
        ServerPlayNetworking.send((ServerPlayerEntity) player, VoraciousScythes.UPDATE_COOLDOWNS_ID, buf);
    }

    private void updateCooldown(ScytheAbilityBase ability, Entry entry, PlayerExtension playerExt) {
        if (entry.isCooldownOff()) {
            return;
        }
        // Prevents cooldown from actually progressing while at least one charge is still in use.
        // Whill not trigger in case of a manual cooldown reduction effect's update, which will give null for playerExt.
        if (!ability.chargeWhileInUse && (playerExt != null) && playerExt.getAbilityDurationManager().doesAbilityAlreadyHaveEntry(ability)) {
            int diff = tick - entry.startTick;
            entry.startTick += diff;
            entry.endTick += diff;
        }
        if (entry.endTick <= tick) {
            if (ability.cooldownType == CooldownType.PER_CHARGE) {
                entry.charges++;
            } else if (ability.cooldownType == CooldownType.ALL_AT_ONCE) {
                entry.charges = ability.charges;
            }
            if (entry.charges >= ability.charges) {
                entry.turnOffCooldown();
            } else {
                entry.setCooldown(ability.cooldown);
            }
        }
    }

    private boolean canUse(Entry entry) {
        return entry.charges > 0;
    }

    public boolean canUse(ScytheAbilityBase ability) {
        return canUse(getEntryFor(ability));
    }

    public static class AbilitySaveDTO {
        public int startTick;
        public int endTick;
        public int charges;
        public String id;

        public AbilitySaveDTO(int startTick, int endTick, int charges, String id) {
            this.startTick = startTick;
            this.endTick = endTick;
            this.charges = charges;
            this.id = id;
        }
    }

    public class AbilitySaveData {
        public int tick;
        public ArrayList<AbilitySaveDTO> dtos = new ArrayList<AbilitySaveDTO>();
    }

    private class Entry {
        public int startTick;
        public int endTick;
        public int charges;

        public Entry() {

        }

        public void turnOffCooldown() {
            startTick = -1;
            endTick = -1;
        }

        public boolean isCooldownOff() {
            return startTick == -1;
        }

        public void setCooldown(int duration) {
            startTick = tick;
            endTick = tick + duration;
        }

    }

    public class HUDEntry {
        public int cooldownLeft;
        public int chargesLeft;
        public ScytheAbilityBase ability;
    }

    public enum CooldownType {
        PER_CHARGE, // Each charge has its own cooldown, and are progressed one after another
        ALL_AT_ONCE // Once the cooldown expires, all charges are refunded!
    }

}
