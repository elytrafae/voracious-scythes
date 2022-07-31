package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomBlockAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GuardianBroomHead extends BroomHead {
    
    public GuardianBroomHead() {
        super(new BroomBlockAbility());
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "guardian_broom_head"), this);
    }

}
