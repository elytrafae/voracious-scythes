package com.cmdgod.mc.voracious_scythes.inventories;

import java.util.ArrayList;
import java.util.Random;

import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public final class PersonalDiscPlayerInventory implements ImplementedInventory
{
    private final ItemStack stack;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(PersonalDiscPlayer.INVENTORY_SIZE, ItemStack.EMPTY);

    public PersonalDiscPlayerInventory(ItemStack stack)
    {
        this.stack = stack;
        NbtCompound tag = stack.getSubNbt(PersonalDiscPlayer.INVENTORY_NAME);

        if (tag != null)
        {
            Inventories.readNbt(tag, items);
        }
    }
    
    public boolean isValidMusicDisc(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (stack.getItem() instanceof MusicDiscItem) {
            return true;
        }
        return false;
    }

    public int getFirstValidSlot() {
        for (int i=0; i < items.size(); i++) {
            if (isValidMusicDisc(items.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int getNextMusicDiscSlotAfter(int slot) {
        int slotNumber = items.size();
        if (slot >= 0 && slot < items.size()) {
            slotNumber = slot;
        }
        int i = slotNumber + 1;
        while (i != slotNumber) {
            //System.out.println("INFINITE LOOP! " + i + " || " + slotNumber);
            if (i >= items.size()) {
                i = 0;
            }
            ItemStack itemStack = items.get(i);
            if (isValidMusicDisc(itemStack)) {
                return i;
            }
            i++;
        }
        return slotNumber;
    }

    public boolean hasDiscOnSlot(int slot) {
        if (slot < 0) {
            return false;
        }
        return isValidMusicDisc(items.get(slot));
    }

    public ItemStack getRandomMusicDisc() {
        ArrayList<ItemStack> validDiscs = new ArrayList<ItemStack>();
        items.forEach((itemStack) -> {
            if (isValidMusicDisc(itemStack)) {
                validDiscs.add(itemStack);
            } 
        });
        if (validDiscs.size() <= 0) {
            return null;
        }
        Random rand = new Random();
        return validDiscs.get(rand.nextInt(validDiscs.size()));
    }

    public boolean hasAtLeastOneMusicDisc() {
        for (int i=0; i < items.size(); i++) {
            ItemStack itemStack = items.get(i);
            if (isValidMusicDisc(itemStack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction side) {
        if (stack.isEmpty() || (stack.getItem() instanceof MusicDiscItem)) {
            return true;
        }
        return false;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void markDirty()
    {
        NbtCompound tag = stack.getOrCreateSubNbt(PersonalDiscPlayer.INVENTORY_NAME);
        Inventories.writeNbt(tag, items);
    }
}