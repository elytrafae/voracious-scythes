package com.cmdgod.mc.voracious_scythes.blocks;

import java.util.ArrayList;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.blockentities.PocketFarmEntity;
import com.cmdgod.mc.voracious_scythes.settingsclasses.PocketFarmSubtype;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class PocketFarm extends BlockWithEntity implements BlockEntityProvider  {

    public Identifier id;

    public void selfRegister() {
        Registry.register(Registry.BLOCK, id, this);
        Registry.register(Registry.ITEM, id, new BlockItem(this, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PocketFarmEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, VoraciousScythes.POCKET_FARM_ENTITY, (world1, pos, state1, be) -> PocketFarmEntity.tick(world1, pos, state1, be));
    }

    public PocketFarm() {
        super(FabricBlockSettings.of(Material.WOOD, MapColor.PALE_YELLOW));
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        //player.openHandledScreen(blockState.createScreenHandlerFactory(world, blockPos));
        BlockEntity entity = world.getBlockEntity(blockPos);
        if (!(entity instanceof PocketFarmEntity)) {
            return ActionResult.FAIL;
        }
        PocketFarmEntity farmEntity = (PocketFarmEntity)entity;
        farmEntity.getFarmSubtypes().forEach( (farmType) -> {
            if (Registry.ITEM.containsId(farmType.dropItemId)) {
                Item item = Registry.ITEM.get(farmType.dropItemId);
                ItemStack stack = new ItemStack(item, 64);
                player.sendMessage(item.getName(stack), false);
                player.giveItemStack(stack);
            } else {
                player.sendMessage(Text.of("The item with the id " + farmType.dropItemId.toString() + " was not found!"), false);
            }
            
        });
        return ActionResult.SUCCESS;
    }
    
}
