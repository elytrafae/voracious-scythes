package com.cmdgod.mc.voracious_scythes.items.brooms;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.ArmoredBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.AxethusiastBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.EmptyBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.ExtraHealthyBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.GuerrillaBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.HastyBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.HealthyBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.HeavyArmorBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.RubberBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.SolidBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.SpeedBroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.gems.StrongBroomGem;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import blue.endless.jankson.annotation.Nullable;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class BroomGem extends Item {
    
    public BroomGem() {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getBroomModifiers(Multimap<EntityAttribute, EntityAttributeModifier> modifiers, ItemStack stack, @Nullable SlotReference slot, @Nullable LivingEntity entity, UUID uuid) {
        // INSERT STUFF HERE IN OTHER CLASSES!
        //modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":movement_speed", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        return modifiers;
    }

    public static void registerMyGems() {
        EmptyBroomGem EMPTY_BROOM_GEM = new EmptyBroomGem();
        SpeedBroomGem SPEED_BROOM_GEM = new SpeedBroomGem();
        ArmoredBroomGem ARMORED_BROOM_GEM = new ArmoredBroomGem();
        RubberBroomGem RUBBER_BROOM_GEM = new RubberBroomGem();
        HastyBroomGem HASTY_BROOM_GEM = new HastyBroomGem();
        HealthyBroomGem HEALTHY_BROOM_GEM = new HealthyBroomGem();
        SolidBroomGem SOLID_BROOM_GEM = new SolidBroomGem();
        StrongBroomGem STRONG_BROOM_GEM = new StrongBroomGem();
        ExtraHealthyBroomGem EXTRA_HEALTHY_BROOM_GEM = new ExtraHealthyBroomGem();
        HeavyArmorBroomGem HEAVILY_ARMORED_BROOM_GEM = new HeavyArmorBroomGem();
        GuerrillaBroomGem GUERRILLA_BROOM_GEM = new GuerrillaBroomGem();
        AxethusiastBroomGem AXETHUSIAST_BROOM_GEM = new AxethusiastBroomGem();
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Multimap<EntityAttribute, EntityAttributeModifier> map = getBroomModifiers(Multimaps.<EntityAttribute, EntityAttributeModifier>newMultimap(Maps.newLinkedHashMap(), ArrayList::new), stack, null, null, new UUID(22222, 69420));
        if (map.isEmpty()) {
            return;
        }
        tooltip.add(new TranslatableText("voracious_scythes.when_put_on_broom").setStyle(BroomBase.STAT_TITLE_STYLE));
        map.forEach((entityAttribute, entityAttributeModifier) -> {
            double value = entityAttributeModifier.getValue();
            double absValue = Math.abs(value);
            double displayValue = (absValue == (int)absValue) ? (int)absValue : absValue;
            String attributeNameString = new TranslatableText(entityAttribute.getTranslationKey()).getString();
            String mainTransKey = "attribute.modifier." + (value < 0 ? "take" : "plus") + "." + entityAttributeModifier.getOperation().getId();
            tooltip.add(new TranslatableText(mainTransKey, displayValue, attributeNameString).setStyle(value < 0 ? BroomBase.NEGATIVE_STAT_STYLE : BroomBase.STAT_STYLE));
        });
    }

}
