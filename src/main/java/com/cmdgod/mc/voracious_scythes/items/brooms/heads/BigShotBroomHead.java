package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomFloatAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BigShotBroomHead extends BroomHead {
    
    public BigShotBroomHead() {
        super(new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_big_shot_ability"));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "big_shot_broom_head"), this);
    }

}
