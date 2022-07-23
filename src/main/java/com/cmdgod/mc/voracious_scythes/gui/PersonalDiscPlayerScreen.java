package com.cmdgod.mc.voracious_scythes.gui;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.widget.WSlider;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class PersonalDiscPlayerScreen extends CottonInventoryScreen<PersonalDiscPlayerDescription> {
    public PersonalDiscPlayerScreen(PersonalDiscPlayerDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}

/*
public class PersonalDiscPlayerScreen extends ScreenHandler
{
    private static final int field_30780 = 9;
    private final Inventory inventory;
    private final int rows;
    PropertyDelegate propertyDelegate;

    private PersonalDiscPlayerScreen(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, int rows, PropertyDelegate propertyDelegate) {
        this(type, syncId, playerInventory, new SimpleInventory(9 * rows), rows, new ArrayPropertyDelegate(2));
    }


    public static PersonalDiscPlayerScreen createGeneric9x6(int syncId, PlayerInventory playerInventory) {
        return new PersonalDiscPlayerScreen(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, syncId, playerInventory, 6, new ArrayPropertyDelegate(2));
    }

    public static PersonalDiscPlayerScreen createGeneric9x6(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new PersonalDiscPlayerScreen(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, syncId, playerInventory, inventory, 6, new ArrayPropertyDelegate(2));
    }

    public PersonalDiscPlayerScreen(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows, PropertyDelegate propertyDelegate) {
        super(type, syncId);
        this.propertyDelegate = propertyDelegate;
        checkSize(inventory, rows * 9);
        this.inventory = inventory;
        this.rows = rows;
        inventory.onOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;

        int j;
        int k;
        for(j = 0; j < this.rows; ++j) {
            for(k = 0; k < 9; ++k) {
                this.addSlot(new BackpackSlot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(j = 0; j < 3; ++j) {
            for(k = 0; k < 9; ++k) {
                this.addSlot(new BackpackSlot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for(j = 0; j < 9; ++j) {
            
            this.addSlot(new BackpackSlot(playerInventory, j, 8 + j * 18, 161 + i));
        }

    }

    public int getVolume() {
        return propertyDelegate.get(0);
    }

    public int getCurrentTrack() {
        return propertyDelegate.get(1);
    }

    public static class BackpackSlot extends Slot
    {
        public BackpackSlot(Inventory inventory, int index, int x, int y)
        {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity)
        {
            return canMoveStack(getStack());
        }

        @Override
        public boolean canInsert(ItemStack stack)
        {
            return canMoveStack(stack);
        }

        // Prevents items that override canBeNested() from being inserted into backpack
        public boolean canMoveStack(ItemStack stack)
        {
            return stack.getItem().canBeNested();
        }
    }

    @Override
    public void onSlotClick(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity)
    {
        // Prevents single or shift-click while pack is open
        if (slotId >= 0)  // slotId < 0 are used for networking internals
        {
            ItemStack stack = getSlot(slotId).getStack();
            playerEntity.sendMessage(Text.of("SLOT CLICKED " + (playerEntity.getWorld().isClient ? "CLIENT" : "SERVER") + " SLOT ID: " + slotId), false);

            if (!isStackValid(stack)) {
                return;
            }

            playerEntity.sendMessage(Text.of("SLOT CLICKED 2 " + (playerEntity.getWorld().isClient ? "CLIENT" : "SERVER") + " SLOT ID: " + slotId), false);

        }

        playerEntity.sendMessage(Text.of("SLOT CLICKED SUPER " + (playerEntity.getWorld().isClient ? "CLIENT" : "SERVER") + " SLOT ID: " + slotId), false);
        super.onSlotClick(slotId, clickData, actionType, playerEntity);
    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);

        ItemStack originalStack = slot.getStack();
        Item testItem = originalStack.getItem();

        if (!isStackValid(originalStack)) {
            return ItemStack.EMPTY;
        }

        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.rows * 9) {
                if (!this.insertItem(itemStack2, this.rows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.rows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    // Helper function I made to not copy-paste the same condition to two different locations.
    private boolean isStackValid(ItemStack stack) {
        return stack.isEmpty() || (stack.getItem() instanceof MusicDiscItem);
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getRows() {
        return this.rows;
    }
    
}
*/