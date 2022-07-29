package com.cmdgod.mc.voracious_scythes.items.brooms;

import java.util.Optional;
import java.util.UUID;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.google.common.collect.Multimap;
import com.terraformersmc.modmenu.util.TranslationUtil;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

// Stick: Damage when used as a Melee weapon
// Gem: Additional buffs, like bonus speed, cooldown reduction etc.
// Broom head (?): Specifies the ability

public class BroomBase extends TrinketItem {

    private final String STICK_NBT_ADDRESS = "BroomStick";
    private final String GEM_NBT_ADDRESS = "BroomGem";
    private final String HEAD_NBT_ADDRESS = "BroomHead";

    public BroomBase() {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom"), this);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        BroomStick broomStick = getStick(stack);
        modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":melee_attack_damage", broomStick.getMeleeDamage(), EntityAttributeModifier.Operation.ADDITION));

        modifiers = getGem(stack).getBroomModifiers(modifiers, stack, slot, entity, uuid);

        return modifiers;
    }

    public BroomStick getStick(ItemStack stack) {
        return (BroomStick)getComponent(stack, STICK_NBT_ADDRESS, VoraciousScythes.MOD_NAMESPACE + ":wooden_broom_stick");
    }

    public BroomGem getGem(ItemStack stack) {
        return (BroomGem)getComponent(stack, GEM_NBT_ADDRESS, VoraciousScythes.MOD_NAMESPACE + ":empty_broom_gem");
    }

    public BroomHead getHead(ItemStack stack) {
        return (BroomHead)getComponent(stack, HEAD_NBT_ADDRESS, VoraciousScythes.MOD_NAMESPACE + ":cleaning_broom_head");
    }

    private Item getComponent(ItemStack stack, String nbtAddress, String defaultValue) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(nbtAddress)) {
            nbt.putString(nbtAddress, defaultValue);
        }
        Item item = Registry.ITEM.get(new Identifier(nbt.getString(nbtAddress)));
        if (item == null) {
            item = Registry.ITEM.get(new Identifier(defaultValue));
        }
        return item;
    }

    public void setStick(ItemStack stack, BroomStick stick) {
        setComponent(stack, STICK_NBT_ADDRESS, stick);
    }

    public void setGem(ItemStack stack, BroomGem gem) {
        setComponent(stack, GEM_NBT_ADDRESS, gem);
    }

    public void setHead(ItemStack stack, BroomHead head) {
        setComponent(stack, HEAD_NBT_ADDRESS, head);
    }

    private void setComponent(ItemStack stack, String nbtAddress, Item item) {
        NbtCompound nbt = stack.getOrCreateNbt();
        Optional<RegistryKey<Item>> opt = Registry.ITEM.getKey(item);
        if (opt.isPresent()) {
            RegistryKey<Item> key = opt.get();
            nbt.putString(nbtAddress, key.getValue().toString());   
        }
    }

    @Override
    public Text getName() {
        return super.getName();
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasCustomName()) {
            return super.getName(stack);
        }
        BroomStick stick = getStick(stack);
        BroomGem gem = getGem(stack);
        BroomHead head = getHead(stack);
        String stickText = (new TranslatableText(stick.getTranslationKey() + ".prefix")).getString();
        String gemText = (new TranslatableText(gem.getTranslationKey() + ".prefix")).getString();
        String headText = (new TranslatableText(head.getTranslationKey() + ".prefix")).getString();

        String text = "";
        if (headText.length() > 0) {
            text += headText + " ";
        }
        if (gemText.length() > 0) {
            text += gemText + " ";
        }
        if (stickText.length() > 0) {
            text += stickText + " ";
        }
        text += (new TranslatableText(this.getTranslationKey())).getString();

        return Text.of(text);
    }
    
}
