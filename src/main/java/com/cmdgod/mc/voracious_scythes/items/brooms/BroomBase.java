package com.cmdgod.mc.voracious_scythes.items.brooms;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.mixinextensions.PlayerExtension;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.google.common.collect.Multimap;
import com.terraformersmc.modmenu.util.TranslationUtil;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

// Stick: Damage when used as a Melee weapon
// Gem: Additional buffs, like bonus speed, cooldown reduction etc.
// Broom head (?): Specifies the ability

public class BroomBase extends TrinketItem {

    public static final Style FLAIR_STYLE = Style.EMPTY.withColor(Formatting.GRAY).withItalic(true);
    public static final Style STAT_TITLE_STYLE = Style.EMPTY.withColor(Formatting.GRAY);
    public static final Style STAT_STYLE = Style.EMPTY.withColor(Formatting.BLUE);
    public static final Style NEGATIVE_STAT_STYLE = Style.EMPTY.withColor(Formatting.RED);

    private static final String STICK_NBT_ADDRESS = "BroomStick";
    private static final String GEM_NBT_ADDRESS = "BroomGem";
    private static final String HEAD_NBT_ADDRESS = "BroomHead";

    public BroomBase() {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom"), this);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);

        modifiers = getGem(stack).getBroomModifiers(modifiers, stack, slot, entity, uuid);

        BroomStick broomStick = getStick(stack);
        double valueSoFar = 0;
        // Stacking causes issues, so I guess I gotta fix this.
        if (modifiers.containsKey(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
            Collection<EntityAttributeModifier> attributeCollection = modifiers.get(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            for (EntityAttributeModifier entityAttributeModifier : attributeCollection) {
                valueSoFar += entityAttributeModifier.getValue();
            }
            attributeCollection.clear();
        }
        modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, 
            new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":broom_melee_attack_damage", 
            broomStick.getMeleeDamage() + valueSoFar, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    public static BroomStick getStick(ItemStack stack) {
        String defaultValue = VoraciousScythes.MOD_NAMESPACE + ":wooden_broom_stick";
        Item item = getComponent(stack, STICK_NBT_ADDRESS, defaultValue);
        if (!(item instanceof BroomStick)) {
            return (BroomStick)Registry.ITEM.get(new Identifier(defaultValue));
        }
        return (BroomStick)item;
    }

    public static BroomGem getGem(ItemStack stack) {
        String defaultValue = VoraciousScythes.MOD_NAMESPACE + ":empty_broom_gem";
        Item item = getComponent(stack, GEM_NBT_ADDRESS, defaultValue);
        if (!(item instanceof BroomGem)) {
            return (BroomGem)Registry.ITEM.get(new Identifier(defaultValue));
        }
        return (BroomGem)item;
    }

    public static BroomHead getHead(ItemStack stack) {
        String defaultValue = VoraciousScythes.MOD_NAMESPACE + ":cleaning_broom_head";
        Item item = getComponent(stack, HEAD_NBT_ADDRESS, defaultValue);
        if (!(item instanceof BroomHead)) {
            return (BroomHead)Registry.ITEM.get(new Identifier(defaultValue));
        }
        return (BroomHead)item;
    }

    private static Item getComponent(ItemStack stack, String nbtAddress, String defaultValue) {
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

    public static void setStick(ItemStack stack, BroomStick stick) {
        setComponent(stack, STICK_NBT_ADDRESS, stick);
    }

    public static void setGem(ItemStack stack, BroomGem gem) {
        setComponent(stack, GEM_NBT_ADDRESS, gem);
    }

    public static void setHead(ItemStack stack, BroomHead head) {
        setComponent(stack, HEAD_NBT_ADDRESS, head);
    }

    private static void setComponent(ItemStack stack, String nbtAddress, Item item) {
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
        String stickText = (Text.translatable(stick.getTranslationKey() + ".prefix")).getString();
        String gemText = (Text.translatable(gem.getTranslationKey() + ".prefix")).getString();
        String headText = (Text.translatable(head.getTranslationKey() + ".prefix")).getString();

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
        text += (Text.translatable(this.getTranslationKey())).getString();

        return Text.of(text);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        BroomHead head = getHead(stack);
        ScytheAbilityBase ability = head.ability;
        if (ability != null) {
            ScytheBase.addAbilityDescriptionToTooltip(tooltip, ability);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public void useAbility(PlayerEntity player, ItemStack stack) {
        BroomHead head = getHead(stack);
        if (head.ability == null) {
            player.sendMessage(Text.of("Your broom has no active abilities!"), true);
            return;
        }
        //player.sendMessage(Text.of("Triggering ability for " + head.getName(stack).getString()), false);
        PlayerExtension playerExt = (PlayerExtension)player;
        AbilityCooldownManager cdManager = playerExt.getCdManager();
        AbilityDurationManager durationManager = playerExt.getAbilityDurationManager();
        if (!cdManager.canUse(head.ability)) {
            player.sendMessage(Text.of("This ability is on cooldown!").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0), true);
            return;
        }
        if (!head.ability.simultaneousChargesAllowed && durationManager.doesAbilityAlreadyHaveEntry(head.ability)) {
            player.sendMessage(Text.of("This ability is already in use!").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0), true);
            return;
        }
        cdManager.useChargeFor(head.ability, player);
        durationManager.startAbilityDuration(head.ability, player, stack);
        World world = player.getWorld();
        if (!world.isClient) {
            cdManager.updateClientOnCooldowns(player);
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        //super.tick(stack, slot, entity);
        BroomHead head = getHead(stack);
        if (head == null) {
            return;
        }
        head.ability.passiveTick((PlayerEntity)entity);
    }
    
}
