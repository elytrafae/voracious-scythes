package com.cmdgod.mc.voracious_scythes.blocks;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.blockentities.BroomTableEntity;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BroomTable extends Block implements BlockEntityProvider {

    public BroomTable() {
        super(FabricBlockSettings.of(Material.WOOD).strength(4.0f));
        Registry.register(Registry.BLOCK, new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_table"), this);
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_table"), new BlockItem(this, new FabricItemSettings().group(VoraciousScythes.BROOM_ITEM_GROUP)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BroomTableEntity(pos, state);
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)((Object)blockEntity) : null;
    }

    @Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		// You need a Block.createScreenHandlerFactory implementation that delegates to the block entity,
		// such as the one from BlockWithEntity
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.SUCCESS;
	}

    
    
}
