package com.cmdgod.mc.voracious_scythes.items.musicdiscs;

import java.util.List;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DoomDiscFragment extends Item {

    final static public Style BASIC_DESC_STYLE = Style.EMPTY.withColor(Formatting.DARK_RED);

    public DoomDiscFragment() {
        super(new Item.Settings().group(VoraciousScythes.MUSIC_DISC_ITEM_GROUP).rarity(Rarity.EPIC).maxCount(8));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "doom_disc_fragment"), this);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.voracious_scythes.doom_disc_fragment.desc").getWithStyle(BASIC_DESC_STYLE).get(0));
    }
    
}
