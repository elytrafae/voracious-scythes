package com.cmdgod.mc.voracious_scythes.scytheabilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.mixinextensions.PlayerExtension;
import com.google.common.collect.Maps;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AbilityDurationManager {
    
    private final ArrayList<Entry> entries = new ArrayList<Entry>();
    private int tick = 0;

    private Entry addEntry() {
        Entry entry = new Entry();
        entries.add(entry);
        return entry;
    }

    public boolean doesAbilityAlreadyHaveEntry(ScytheAbilityBase ability) {
        for (int i=0; i < entries.size(); i++) {
            if (entries.get(i).ability.equals(ability)) {
                return true;
            }
        }
        return false;
    }

    public void startAbilityDuration(ScytheAbilityBase ability, PlayerEntity player, ItemStack itemStack) {
        if (!ability.simultaneousChargesAllowed && doesAbilityAlreadyHaveEntry(ability)) {
            return;
        }
        Entry entry = addEntry();
        entry.preStartTick = tick;
        entry.startTick = entry.preStartTick + ability.preFireDuration;
        entry.endTick = entry.startTick + ability.duration;
        entry.postEndTick = entry.endTick + ability.postFireDuration;
        entry.ability = ability;
        entry.item = itemStack.getItem();
        ability.modifyStackBeforeUse(player, itemStack);
        runTicksFor(entry, player);
    }

    public void tick(PlayerEntity player, PlayerExtension playerExt) {
        this.tick++;
        if (entries.isEmpty()) {return;}
        Iterator<Entry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            //player.sendMessage(Text.of("Ability Name: " + entry.ability.name.toString()), false);
            runTicksFor(entry, player);
            if (tick >= entry.postEndTick) {
                iterator.remove();
            }
        }
    }

    private void runTicksFor(Entry entry, PlayerEntity player) {
        entry.ability.globalActiveAbilityTick(new PublicAbilityDurationEntry(entry), tick, player);
        if (tick <= entry.startTick) {
            entry.ability.preFireTick(new PublicAbilityDurationEntry(entry), tick, player);
        }
        if (tick >= entry.startTick && tick <= entry.endTick) {
            entry.ability.activeTick(new PublicAbilityDurationEntry(entry), tick, player);
        }
        if (tick >= entry.endTick && tick <= entry.postEndTick) {
            entry.ability.activeTick(new PublicAbilityDurationEntry(entry), tick, player);
        }
    }

    public void restoreSaveData(String id, int preStartTick, int startTick, int endTick, int postEndTick, String itemId) {
        Identifier identifier = new Identifier(id);
        Identifier itemIdentifier = new Identifier(itemId);
        ScytheAbilityBase ability = ScytheAbilityBase.ABILITY_REGISTRY.get(identifier);
        Item item = Registry.ITEM.get(itemIdentifier);
        if (ability == null || item == null) {return;}
        Entry entry = this.addEntry();
        entry.preStartTick = preStartTick;
        entry.startTick = startTick;
        entry.endTick = endTick;
        entry.postEndTick = postEndTick;
        entry.item = item;
        entry.ability = ability;
    }

    public DurationSaveData getSaveData() {
        DurationSaveData save = new DurationSaveData();
        int minStartTick = tick;
        if (!this.entries.isEmpty()) {
            Iterator<Entry> iterator = this.entries.iterator();
            minStartTick = getSmallestStartTick(iterator);
            while (iterator.hasNext()) {
                Entry e = iterator.next();
                String itemId = Registry.ITEM.getId(e.item).toString();
                save.dtos.add(new DurationSaveDTO(e.preStartTick - minStartTick, e.startTick - minStartTick, e.endTick - minStartTick, e.postEndTick - minStartTick, e.ability.id.toString(), itemId));
            }
        }
        save.tick = tick - minStartTick;
        return save;
    }

    private int getSmallestStartTick(Iterator<Entry> iterator) {
        int min = tick;
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            int s = entry.preStartTick;
            if (s < min) {
                min = s;
            }
        }
        return min;
    }

    public void clearEverything() {
        entries.clear();
        tick = 0;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public class DurationSaveDTO {
        public int preStartTick;
        public int startTick;
        public int endTick;
        public int postEndTick;
        public String id; // This is the ability's id, btw.
        public String itemId;

        public DurationSaveDTO(int preStartTick, int startTick, int endTick, int postEndTick, String id, String itemId) {
            this.preStartTick = preStartTick;
            this.startTick = startTick;
            this.endTick = endTick;
            this.postEndTick = postEndTick;
            this.id = id;
            this.itemId = itemId;
        }
    }

    public class DurationSaveData {
        public int tick;
        public ArrayList<DurationSaveDTO> dtos = new ArrayList<DurationSaveDTO>();
    }

    private class Entry {
        public int startTick = -1;
        public int endTick = -1;
        public int preStartTick = -1;
        public int postEndTick = -1;
        public ScytheAbilityBase ability;
        public Item item;

        public Entry() {

        }

    }

    public class PublicAbilityDurationEntry {

        private Entry originEntry;

        public PublicAbilityDurationEntry(Entry entry) {
            this.originEntry = entry;
        }

        public int getPreStartTick() {
            return originEntry.preStartTick;
        }

        public int getStartTick() {
            return originEntry.startTick;
        }

        public int getEndTick() {
            return originEntry.endTick;
        }

        public int getPostEndTick() {
            return originEntry.postEndTick;
        }

        public Item getItem() {
            return originEntry.item;
        }

        public ScytheAbilityBase getAbility() {
            return originEntry.ability;
        }

        public void modifyDurationBy(int n) {
            int realN = Math.max(originEntry.endTick + n, tick-1) - originEntry.endTick;
            originEntry.endTick += realN;
            originEntry.postEndTick += realN;
        }

    }

}
