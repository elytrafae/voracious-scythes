package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities;

import java.util.ArrayList;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HeavyHit extends ScytheAbilityBase {
    
    public HeavyHit() {
        super("Heavy Hit", 100, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "heavy_hit_ability"));
        ArrayList<String> desc = new ArrayList<String>();
        desc.add("Instantly do a heavy attack,");
        desc.add("dealing increased damage!");
        this.changeDescription(desc);

        this.duration = 0;
        this.preFireDuration = 0;
        this.postFireDuration = 0;
    }

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        //player.sendMessage(Text.of("preFireTick! " + startTick + " || " + endTick + " || " + tick), false);
    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        /*
        int damage;
        stack.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach( (attribute, modifier) -> {
            attribute.
        });
        */
        World world = player.getWorld();
        // TODO: Make this dynamic, relative to the weapon's damage stat!
        //player.sendMessage(Text.of("activeTick! " + startTick + " || " + endTick + " || " + tick), false);
        Item item = entry.getItem();
        if (!(item instanceof ScytheBase)) {
            return;
        }
        ScytheBase scythe = (ScytheBase)item;
        scythe.sweep(player, world, 10);
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        //player.sendMessage(Text.of("postFireTick! " + startTick + " || " + endTick + " || " + tick), false);
    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        //player.sendMessage(Text.of("globalActiveAbilityTick! " + startTick + " || " + endTick + " || " + tick), false);
    }
    
}
