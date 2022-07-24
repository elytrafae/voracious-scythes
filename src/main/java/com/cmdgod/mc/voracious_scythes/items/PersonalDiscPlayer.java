package com.cmdgod.mc.voracious_scythes.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerNamedScreenHandlerFactory;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerPropertyDelegate;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerScreen;
import com.cmdgod.mc.voracious_scythes.inventories.PersonalDiscPlayerInventory;
import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.SlotType;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.StructureBlockBlockEntity.Action;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BundleItem;
import net.minecraft.item.DebugStickItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.GiveCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class PersonalDiscPlayer extends TrinketItem  {

    public final static int INVENTORY_ROWS = 6;
    public final static int INVENTORY_COLUMNS = 8;
    public final static int INVENTORY_SIZE = INVENTORY_COLUMNS * INVENTORY_ROWS;
    public static final String INVENTORY_NAME = "Discs";

    public PersonalDiscPlayer() {
        super(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "personal_disc_player"), this);
    }
    
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        // +10% movement speed
        //modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, VoraciousScythes.MOD_NAMESPACE + ":movement_speed", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        // Give an extra hat slot
        SlotAttributes.addSlotModifier(modifiers, "head/hat", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
        return modifiers;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);
        entity.sendSystemMessage(Text.of("TICK!"), entity.getUuid());
        if (!(entity instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity)entity;
        SlotType type = slot.inventory().getSlotType();
        player.sendMessage(Text.of(type.getGroup() + " " + type.getName()), true);
        player.sendMessage(Text.of("MUSIC SHOULD BE PLAYING!"), true);
    }

    public ArrayList<ItemStack> getCountainedItems(ItemStack itemStack) {
        NbtCompound nbt = itemStack.getOrCreateNbt();
        if (!nbt.contains(INVENTORY_NAME)) {
            nbt.put(INVENTORY_NAME, new NbtList());
            return new ArrayList<ItemStack>();
        }
        NbtList itemList = nbt.getList(INVENTORY_NAME, 10);
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for (int i=0; i < itemList.size(); i++) {
            NbtCompound nbtItem = itemList.getCompound(i);
            items.add(ItemStack.fromNbt(nbtItem));
        }
        return items;
    }

    final static public Style BASIC_DESC_STYLE = Style.EMPTY.withColor(Formatting.GRAY);
    final static public Style PLAYLIST_TITLE_STYLE = Style.EMPTY.withColor(Formatting.LIGHT_PURPLE);
    final static public Style SONG_NAME_STYLE = Style.EMPTY.withColor(Formatting.WHITE);
    final static public Style CURRENT_SONG_NAME_STYLE = Style.EMPTY.withColor(Formatting.GOLD);

    /*
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("item.voracious_scythes.personal_disc_player.desc1").getWithStyle(BASIC_DESC_STYLE).get(0));
        tooltip.add(new TranslatableText("item.voracious_scythes.personal_disc_player.desc2").getWithStyle(BASIC_DESC_STYLE).get(0));
        ArrayList<ItemStack> items = getCountainedItems(itemStack);
        if (items.size() <= 0) {
            tooltip.add(new TranslatableText("item.voracious_scythes.personal_disc_player.noplaylist").getWithStyle(PLAYLIST_TITLE_STYLE).get(0));
            return;
        }
        tooltip.add(new TranslatableText("item.voracious_scythes.personal_disc_player.playlist").getWithStyle(PLAYLIST_TITLE_STYLE).get(0));
        items.forEach((item) -> {
            Identifier id = Registry.ITEM.getId(item.getItem());
            String translationKey = "item." + id.getNamespace() + "." + id.getPath() + ".desc";
            tooltip.add(new TranslatableText(translationKey).getWithStyle(SONG_NAME_STYLE).get(0));
        });
    }
    */

    public ActionResult onLeftClick(PlayerEntity player, Hand hand, World world) {
        ItemStack stack = player.getStackInHand(hand);
        player.openHandledScreen(new PersonalDiscPlayerNamedScreenHandlerFactory(stack, player)); 
        return ActionResult.SUCCESS;
    }


}
