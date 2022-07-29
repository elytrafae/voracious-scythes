package com.cmdgod.mc.voracious_scythes.items.brooms;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.DiamondBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.GoldBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.IronBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.NetheriteBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.StoneBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.WoodenBroomStick;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BroomStick extends Item {

    private int meleeDamage = 0;

    public BroomStick(int meleeDamage) {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
        this.meleeDamage = meleeDamage;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public static void registerMySticks() {
        WoodenBroomStick WOODEN_BROOM_STICK = new WoodenBroomStick();
        GoldBroomStick GOLD_BROOM_STICK = new GoldBroomStick();
        StoneBroomStick STONE_BROOM_STICK = new StoneBroomStick();
        IronBroomStick IRON_BROOM_STICK = new IronBroomStick();
        DiamondBroomStick DIAMOND_BROOM_STICK = new DiamondBroomStick();
        NetheriteBroomStick NETHERITE_BROOM_STICK = new NetheriteBroomStick();
    }
    
}
