package com.cmdgod.mc.voracious_scythes.items.brooms;

import java.time.format.TextStyle;
import java.util.List;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.DiamondBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.GoldBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.IronBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.NetheriteBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.StoneBroomStick;
import com.cmdgod.mc.voracious_scythes.items.brooms.sticks.WoodenBroomStick;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.text.Style;

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

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("voracious_scythes.when_put_on_broom").setStyle(BroomBase.STAT_TITLE_STYLE));
        tooltip.add(new TranslatableText("attribute.modifier.plus.0", meleeDamage, new TranslatableText("attribute.name.generic.attack_damage").getString()).setStyle(BroomBase.STAT_STYLE));
    }
    
}
