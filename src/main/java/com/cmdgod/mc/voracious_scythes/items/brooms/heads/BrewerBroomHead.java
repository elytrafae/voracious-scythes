package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomPotionAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BrewerBroomHead extends BroomHead {
    
    public BrewerBroomHead() {
        super(new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_potion_ability"));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "brewer_broom_head"), this);
    }

}
