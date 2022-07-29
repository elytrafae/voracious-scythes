package com.cmdgod.mc.voracious_scythes.items.brooms;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.CleaningBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.WitchBroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BroomHead extends Item {
    
    ScytheAbilityBase ability = null;

    public BroomHead(ScytheAbilityBase ability) {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
        this.ability = ability;
    }

    public static void registerMyHeads() {
        CleaningBroomHead CLEANING_BROOM_HEAD = new CleaningBroomHead();
        WitchBroomHead WITCH_BROOM_HEAD = new WitchBroomHead();
    }

}
