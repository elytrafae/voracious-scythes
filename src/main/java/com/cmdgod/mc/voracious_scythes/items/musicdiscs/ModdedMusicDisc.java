package com.cmdgod.mc.voracious_scythes.items.musicdiscs;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModdedMusicDisc extends MusicDiscItem {

    public boolean isShiny = false;

    public ModdedMusicDisc(String sound_name) {
        this(sound_name, Rarity.RARE, false);
    }

    public ModdedMusicDisc(String sound_name, Rarity rarity) {
        this(sound_name, rarity, false);
    }

    public ModdedMusicDisc(String sound_name, Rarity rarity, boolean isShiny) {
        super(15, VoraciousScythes.registerSoundEvent(sound_name), new Item.Settings().group(VoraciousScythes.MUSIC_DISC_ITEM_GROUP).rarity(rarity).maxCount(1), 1);
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, sound_name + "_music_disc"), this);
        this.isShiny = isShiny;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        if (this.isShiny) {
            return true;
        }
        return super.hasGlint(stack);
    }

}
