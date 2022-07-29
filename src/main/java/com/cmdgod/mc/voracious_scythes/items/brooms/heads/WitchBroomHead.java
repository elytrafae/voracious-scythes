package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomFlightAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WitchBroomHead extends BroomHead {
    
    public WitchBroomHead() {
        super(new BroomFlightAbility());
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "witch_broom_head"), this);
    }

}
