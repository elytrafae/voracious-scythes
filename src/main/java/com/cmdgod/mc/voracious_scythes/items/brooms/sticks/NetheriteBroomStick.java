package com.cmdgod.mc.voracious_scythes.items.brooms.sticks;


import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomStick;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NetheriteBroomStick extends BroomStick {

    public NetheriteBroomStick() {
        super(4);
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "netherite_broom_stick"), this);
    }
    
}
