package com.cmdgod.mc.voracious_scythes.blocks.PocketFarms;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.blockentities.PocketFarmEntity;
import com.cmdgod.mc.voracious_scythes.blocks.PocketFarm;
import com.cmdgod.mc.voracious_scythes.settingsclasses.PocketFarmSubtype;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;



public class SugarCanePocketFarm extends PocketFarm {

    public SugarCanePocketFarm() {
        this.id = new Identifier(VoraciousScythes.MOD_NAMESPACE, "sugarcane_pocket_farm");
        //TODO Auto-generated constructor stub
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        PocketFarmEntity entity = new PocketFarmEntity(pos, state);
        entity.setWorksWithEmptyModules(false);
        entity.setHarvestFrequency(200);

        PocketFarmSubtype type = new PocketFarmSubtype(new Identifier("minecraft:sugar_cane"), 2);
        entity.setFarmSubtypes(new ArrayList<PocketFarmSubtype>(Arrays.asList(type)));

        return entity;
    }
    
}
