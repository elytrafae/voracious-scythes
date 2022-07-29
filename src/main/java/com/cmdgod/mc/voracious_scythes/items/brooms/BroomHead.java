package com.cmdgod.mc.voracious_scythes.items.brooms;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.CleaningBroomHead;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BroomHead extends Item {
    
    public BroomHead() {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
    }

    public static void registerMyHeads() {
        CleaningBroomHead CLEANING_BROOM_HEAD = new CleaningBroomHead();
    }

}
