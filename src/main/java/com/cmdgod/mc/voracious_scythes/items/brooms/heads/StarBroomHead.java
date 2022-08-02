package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities.BroomFloatAbility;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StarBroomHead extends BroomHead {
    
    public StarBroomHead() {
        super(new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_star_ability"));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "star_spreader_broom_head"), this);
    }

}
