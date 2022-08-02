package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomTeleportAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlinkingBroomHead extends BroomHead {
    
    public BlinkingBroomHead() {
        super(new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_blink_ability"));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "blinking_broom_head"), this);
    }

}
