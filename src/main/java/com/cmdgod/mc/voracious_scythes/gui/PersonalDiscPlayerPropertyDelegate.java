package com.cmdgod.mc.voracious_scythes.gui;

import java.util.List;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.server.MinecraftServer;

public class PersonalDiscPlayerPropertyDelegate implements PropertyDelegate {

    ItemStack stack;
    PlayerEntity player;
    static List<String> propertyNames = List.of("musicVolume", "currentTrack", "playMode");
    static List<Integer> defaultValues = List.of(100, 0, 0);
    public static final int SIZE = propertyNames.size();

    public PersonalDiscPlayerPropertyDelegate(ItemStack stack) {
        this.stack = stack;
    }

    public PersonalDiscPlayerPropertyDelegate(ItemStack stack, PlayerEntity player) {
        this.stack = stack;
        this.player = player;
    }

    @Override
    public int get(int index) {
        if (index >= size() || index < 0) {
            return -1;
        }
        //System.out.println(Boolean.toString(this.stack.getItem() instanceof PersonalDiscPlayer));
        //System.out.println(this.stack.getName().asString());
        String key = propertyNames.get(index);
        NbtCompound nbt = stack.getOrCreateNbt();
        //System.out.println("getting #" + index + ": " + nbt);
        if (player != null) {
            //System.out.println("Get World: " + VoraciousScythes.returnServerOrClientString(player.getWorld()));
        }
        if (nbt.contains(key)) {
            return nbt.getInt(key);
        }
        return defaultValues.get(index);
    }

    public int getByName(String name) {
        if (!propertyNames.contains(name)) {
            return -1;
        }
        return get(propertyNames.indexOf(name));
    }

    @Override
    public void set(int index, int value) {
        if (index >= size() || index < 0) {
            return;
        }
        String key = propertyNames.get(index);
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(key, value);
        if (player != null) {
            System.out.println(VoraciousScythes.returnServerOrClientString(player.getWorld()));
        }
        if (player != null) {
            System.out.println("SetWorld: " + VoraciousScythes.returnServerOrClientString(player.getWorld()));
        }
        System.out.println(nbt);
        System.out.println(stack);
    }

    public void setByName(String name, int value) {
        if (!propertyNames.contains(name)) {
            return;
        }
        set(propertyNames.indexOf(name), value);
    }

    @Override
    public int size() {
        return propertyNames.size();
    }
    
}
