package com.cmdgod.mc.voracious_scythes.items.brooms.sticks;


import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomStick;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GoldBroomStick extends BroomStick {

    public GoldBroomStick() {
        super(2);
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "gold_broom_stick"), this);
    }
    
}
