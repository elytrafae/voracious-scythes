package com.cmdgod.mc.voracious_scythes.items.brooms.gems;

import java.util.UUID;

import com.cmdgod.mc.voracious_scythes.CustomEntityAttributes;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomGem;
import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MagicalBroomGem extends BroomGem {
    
    public MagicalBroomGem() {
        super();
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "magical_broom_gem"), this);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getBroomModifiers(Multimap<EntityAttribute, EntityAttributeModifier> modifiers, ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        // INSERT STUFF HERE IN OTHER CLASSES!
        modifiers.put(CustomEntityAttributes.COOLDOWN_REDUCTION, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_gem_cooldown_reduction", 0.20, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        //modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_gem_armor", 8, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }

}
