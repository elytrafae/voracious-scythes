package com.cmdgod.mc.voracious_scythes.items.musicdiscs;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModdedMusicDisc extends MusicDiscItem {

    public ModdedMusicDisc(String sound_name) {
        super(15, VoraciousScythes.registerSoundEvent(sound_name), new Item.Settings().group(VoraciousScythes.MUSIC_DISC_ITEM_GROUP).rarity(Rarity.RARE).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, sound_name + "_music_disc"), this);
    }

    public ModdedMusicDisc(String sound_name, Rarity rarity) {
        super(15, VoraciousScythes.registerSoundEvent(sound_name), new Item.Settings().group(VoraciousScythes.MUSIC_DISC_ITEM_GROUP).rarity(rarity).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, sound_name + "_music_disc"), this);
    }

}
