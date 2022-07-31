package com.cmdgod.mc.voracious_scythes.blockentities;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.gui.broomtable.BroomTableDescription;
import com.cmdgod.mc.voracious_scythes.inventories.ImplementedInventory;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomBase;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomStick;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class BroomTableEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {

    public static final int INVENTORY_SIZE = 4;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (!checkRemovedBroom(slot)) {
            checkCompletedBroom();
        }
        return ImplementedInventory.super.removeStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        if (!checkRemovedBroom(slot)) {
            checkCompletedBroom();
        }
        return ImplementedInventory.super.removeStack(slot, count);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ImplementedInventory.super.setStack(slot, stack);
        checkCompletedBroom();
    }

    public boolean checkRemovedBroom(int slot) {
        if (slot == 3 && (items.get(3).getItem() instanceof BroomBase)) {
            items.set(0, ItemStack.EMPTY);
            items.set(1, ItemStack.EMPTY);
            items.set(2, ItemStack.EMPTY);
            return true;
        }
        return false;
    }

    public void checkCompletedBroom() {
        Item stick = items.get(0).getItem();
        Item gem = items.get(1).getItem();
        Item head = items.get(2).getItem();
        if ((stick instanceof BroomStick) && (gem instanceof BroomGem) && (head instanceof BroomHead)) {
            ItemStack stack = new ItemStack(VoraciousScythes.BROOM_BASE);
            BroomBase.setStick(stack, (BroomStick)stick);
            BroomBase.setGem(stack, (BroomGem)gem);
            BroomBase.setHead(stack, (BroomHead)head);
            items.set(3, stack);
        } else {
            items.set(3, ItemStack.EMPTY);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }
 
    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        super.writeNbt(nbt);
    }

    public BroomTableEntity(BlockPos pos, BlockState state) {
        super(VoraciousScythes.BROOM_TABLE_ENTITY, pos, state);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new BroomTableDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
    
}
