package com.cmdgod.mc.voracious_scythes.blockentities;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.blocks.ScythingTable;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableGuiDescription;
import com.cmdgod.mc.voracious_scythes.inventories.ScythingTableInventory;

import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class ScythingTableEntity extends BlockEntity implements ScythingTableInventory, InventoryProvider, NamedScreenHandlerFactory {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    
    public ScythingTableEntity(BlockPos pos, BlockState state) {
        super(VoraciousScythes.SCYTHING_TABLE_ENTITY, pos, state);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
 
        return result;
    }

    
    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
    
    @Override
	public Text getDisplayName() {
		// Using the block name as the screen title
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new ScythingTableGuiDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
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

    @Override
    public SidedInventory getInventory(BlockState blockState, WorldAccess worldAccess, BlockPos pos) {
        return ScythingTableInventory.of(items);
    }
    
}
