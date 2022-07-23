package com.cmdgod.mc.voracious_scythes.items.musicdiscs;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class MusiclessDisc extends Item {

    public MusiclessDisc() {
        super(new Item.Settings().group(VoraciousScythes.MUSIC_DISC_ITEM_GROUP).rarity(Rarity.UNCOMMON).maxCount(8));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "musicless_disc"), this);
    }
    
}
