package com.cmdgod.mc.voracious_scythes.settingsclasses;

import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class PocketFarmSubtype {
    
    public Identifier dropItemId;
    public float dropCountPerModule = 1;
    public int itemCount = 0;
    public int moduleCount = 0;

    public PocketFarmSubtype() { // I only recommend using this in the context of loading!

    }

    public PocketFarmSubtype(Identifier dropItemId, float dropCountPerModule) {
        this.dropCountPerModule = dropCountPerModule;
        this.dropItemId = dropItemId;
    }

    public void loadFromNbt(NbtCompound tag) {
        this.itemCount = tag.getInt("itemCount");
        this.moduleCount = tag.getInt("moduleCount");
        this.dropCountPerModule = tag.getFloat("dropCountPerModule");
        this.dropItemId = new Identifier(tag.getString("dropItemId"));
    }

    public NbtCompound writeToNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("itemCount", this.itemCount);
        tag.putInt("moduleCount", this.moduleCount);
        tag.putFloat("dropCountPerModule", this.dropCountPerModule);
        tag.putString("dropItemId", this.dropItemId.toString());
        return tag;
    }

}
