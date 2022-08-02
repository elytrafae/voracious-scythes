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

public class StrongBroomGem extends BroomGem {
    
    public StrongBroomGem() {
        super();
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "strong_broom_gem"), this);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getBroomModifiers(Multimap<EntityAttribute, EntityAttributeModifier> modifiers, ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        // INSERT STUFF HERE IN OTHER CLASSES!
        modifiers.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_gem_knockback_resistence", 0.3, EntityAttributeModifier.Operation.ADDITION));
        modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_gem_attack_damage", 3, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }

}
