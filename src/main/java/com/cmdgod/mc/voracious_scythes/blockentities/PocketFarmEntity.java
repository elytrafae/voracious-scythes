package com.cmdgod.mc.voracious_scythes.blockentities;

import java.util.ArrayList;
import java.util.Set;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.blocks.PocketFarm;
import com.cmdgod.mc.voracious_scythes.settingsclasses.PocketFarmSubtype;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PocketFarmEntity extends BlockEntity {

    private int tick = 0;
    private int harvestFrequency = 0;
    private int fuel = 0;
    private ArrayList<PocketFarmSubtype> farmSubtypes = new ArrayList<PocketFarmSubtype>();
    private int emptyModules = 0;
    private boolean worksWithEmptyModules = false;
    private ArrayList<ItemStack> newModuleMaterials;

    public PocketFarmEntity(BlockPos pos, BlockState state) {
        super(VoraciousScythes.POCKET_FARM_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PocketFarmEntity be) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (!(entity instanceof PocketFarmEntity)) {
            return;
        }
        PocketFarmEntity farmEntity = (PocketFarmEntity)entity;
        if (farmEntity.fuel > 0) {
            farmEntity.tick++;
        }
        if (farmEntity.tick >= farmEntity.harvestFrequency) {
            farmEntity.triggerHarvest();
            farmEntity.tick = 0;
        }
    }

    public void triggerHarvest() {
        this.farmSubtypes.forEach( (farmType) -> {
            int maxRemovable = Math.min(farmType.moduleCount, this.fuel);
            farmType.itemCount += Math.floor(maxRemovable * farmType.dropCountPerModule);
            this.fuel -= maxRemovable;
        });
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        // Save the current value of the number to the tag
        tag.putInt("tick", tick);
        tag.putInt("fuel", fuel);
        tag.putInt("emptyModules", emptyModules);

        NbtList subtypes = new NbtList();
        for (int i=0; i < farmSubtypes.size(); i++) {
            PocketFarmSubtype type = farmSubtypes.get(i);
            subtypes.add(type.writeToNbt());
        }
        tag.put("subtypes", subtypes);
 
        super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
    
        tick = tag.getInt("tick");
        fuel = tag.getInt("fuel");
        emptyModules = tag.getInt("emptyModules");

        this.farmSubtypes = new ArrayList<PocketFarmSubtype>();
        NbtList subtypes = tag.getList("subtypes", 10);
        for (int i=0; i < subtypes.size(); i++) {
            NbtCompound subtype = subtypes.getCompound(i);
            PocketFarmSubtype type = new PocketFarmSubtype();
            type.loadFromNbt(subtype);
        }
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

    public void setWorksWithEmptyModules(boolean worksWithEmptyModules) {
        this.worksWithEmptyModules = worksWithEmptyModules;
    }

    public void setFarmSubtypes(ArrayList<PocketFarmSubtype> subtypes) {
        this.farmSubtypes = subtypes;
    }

    public ArrayList<PocketFarmSubtype> getFarmSubtypes() {
        return this.farmSubtypes;
    }

    public int getTick() {
        return this.tick;
    }

    public int getHarvestFrequency() {
        return this.harvestFrequency;
    }

    public void setHarvestFrequency(int harvestFrequency) {
        this.harvestFrequency = harvestFrequency;
    }

    public int getFuel() {
        return this.fuel;
    }

    public int getEmptyModules() {
        return this.emptyModules;
    }

    public boolean doesWorkWithEmptyModules() {
        return this.worksWithEmptyModules;
    }

    public ArrayList<ItemStack> getNewModuleMaterials() {
        return this.newModuleMaterials;
    }

    public void setNewModuleMaterials(ArrayList<ItemStack> newModuleMaterials) {
        this.newModuleMaterials = newModuleMaterials;
    }


}

