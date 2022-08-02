package com.cmdgod.mc.voracious_scythes;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.registry.Registry;

public class CustomEntityAttributes {
    
    public static final String TRANS_KEY_PREFIX = "attribute.name." + VoraciousScythes.MOD_NAMESPACE + ".";

    public static final EntityAttribute COOLDOWN_REDUCTION = register(VoraciousScythes.MOD_NAMESPACE + ".cooldown_reduction", new ClampedEntityAttribute(TRANS_KEY_PREFIX + "cooldown_reduction", 1, 0.2, 1.8).setTracked(true));
    public static final EntityAttribute GENERIC_DAMAGE_REDUCTION = register(VoraciousScythes.MOD_NAMESPACE + ".generic_damage_reduction", new ClampedEntityAttribute(TRANS_KEY_PREFIX + "generic_damage_reduction", 1, -1, 1.8).setTracked(true));

    public static void initialize() {
        
    }

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, id, attribute);
    }

}
