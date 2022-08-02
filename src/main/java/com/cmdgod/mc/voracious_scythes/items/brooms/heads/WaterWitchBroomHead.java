package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomFloatAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WaterWitchBroomHead extends BroomHead {
    
    public WaterWitchBroomHead() {
        super(new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_water_throw_ability"));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "water_witch_broom_head"), this);
    }

}
