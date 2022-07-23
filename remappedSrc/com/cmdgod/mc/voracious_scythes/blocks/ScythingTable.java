package com.cmdgod.mc.voracious_scythes.blocks;

import com.cmdgod.mc.voracious_scythes.blockentities.ScythingTableEntity;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ScythingTable extends Block implements BlockEntityProvider {

    public ScythingTable() {
        super(FabricBlockSettings.of(Material.WOOD, MapColor.DARK_CRIMSON));
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        player.openHandledScreen(blockState.createScreenHandlerFactory(world, blockPos));
        player.sendMessage(Text.of("USING!"), false);
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ScythingTableEntity(pos, state);
    }
    
}
