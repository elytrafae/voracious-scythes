package com.cmdgod.mc.voracious_scythes.inventories;

import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
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