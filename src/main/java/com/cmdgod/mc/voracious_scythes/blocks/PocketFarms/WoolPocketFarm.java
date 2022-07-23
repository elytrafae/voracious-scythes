package com.cmdgod.mc.voracious_scythes.blocks.PocketFarms;

import java.util.ArrayList;
import java.util.Arrays;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.blockentities.PocketFarmEntity;
import com.cmdgod.mc.voracious_scythes.blocks.PocketFarm;
import com.cmdgod.mc.voracious_scythes.settingsclasses.PocketFarmSubtype;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class WoolPocketFarm extends PocketFarm {
    
    public WoolPocketFarm() {
        this.id = new Identifier(VoraciousScythes.MOD_NAMESPACE, "wool_pocket_farm");
        //TODO Auto-generated constructor stub
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        PocketFarmEntity entity = new PocketFarmEntity(pos, state);
        entity.setWorksWithEmptyModules(true);
        entity.setHarvestFrequency(300);

        ArrayList<PocketFarmSubtype> wool_farm_types = new ArrayList<PocketFarmSubtype>();
		for (DyeColor color : DyeColor.values()) { 
			Identifier id = new Identifier("minecraft:" + color.getName() + "_wool");
			wool_farm_types.add(new PocketFarmSubtype(id, 2));
		}

        entity.setFarmSubtypes(wool_farm_types);

        return entity;
    }

}
