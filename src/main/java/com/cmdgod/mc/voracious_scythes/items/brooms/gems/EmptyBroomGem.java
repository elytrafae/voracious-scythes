package com.cmdgod.mc.voracious_scythes.items.brooms.gems;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomGem;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EmptyBroomGem extends BroomGem {
    
    public EmptyBroomGem() {
        super();
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "empty_broom_gem"), this);
    }

}
