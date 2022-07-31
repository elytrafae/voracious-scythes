package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomFireballAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlazingBroomHead extends BroomHead {
    
    public BlazingBroomHead() {
        super(new BroomFireballAbility());
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "blazing_broom_head"), this);
    }

}
