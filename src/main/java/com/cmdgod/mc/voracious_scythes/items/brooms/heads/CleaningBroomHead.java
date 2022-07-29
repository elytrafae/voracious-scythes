package com.cmdgod.mc.voracious_scythes.items.brooms.heads;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CleaningBroomHead extends BroomHead {
    
    public CleaningBroomHead() {
        super();
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "cleaning_broom_head"), this);
    }

}
