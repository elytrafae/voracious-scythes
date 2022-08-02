package com.cmdgod.mc.voracious_scythes.items.brooms.gems;

import java.util.UUID;

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

public class AxethusiastBroomGem extends BroomGem {
    
    public AxethusiastBroomGem() {
        super();
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "axethusiast_broom_gem"), this);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getBroomModifiers(Multimap<EntityAttribute, EntityAttributeModifier> modifiers, ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        // INSERT STUFF HERE IN OTHER CLASSES!
        //modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_gem_armor", -0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_gem_attack_damage", 8, EntityAttributeModifier.Operation.ADDITION));
        modifiers.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_gem_attack_speed", -0.30, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        return modifiers;
    }

}
