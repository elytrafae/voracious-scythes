package com.cmdgod.mc.voracious_scythes.items;

import java.util.List;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class PosableMannequin extends Item {

    final static public Style BASIC_DESC_STYLE = Style.EMPTY.withColor(Formatting.GRAY);

    public PosableMannequin() {
        super(new Item.Settings().group(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON).maxCount(16));
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "posable_mannequin"), this);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("item.voracious_scythes.posable_mannequin.desc1").getWithStyle(BASIC_DESC_STYLE).get(0));
        tooltip.add(new TranslatableText("item.voracious_scythes.posable_mannequin.desc2").getWithStyle(BASIC_DESC_STYLE).get(0));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        SoundEvent sound = Registry.SOUND_EVENT.get(new Identifier(VoraciousScythes.MOD_NAMESPACE, "mario_cbt"));
        //player.playSound(Registry.SOUND_EVENT.get(new Identifier(VoraciousScythes.MOD_NAMESPACE, "mario_cbt")), SoundCategory.MUSIC, 10000, 1);
        //player.sound
        if (world.isClient) {
            PositionedSoundInstance soundInstance = new PositionedSoundInstance(new Identifier(VoraciousScythes.MOD_NAMESPACE, "mario_cbt"), SoundCategory.MUSIC, 1, 1, false, 0, SoundInstance.AttenuationType.NONE, 0, 0, 0, true);
            MinecraftClient.getInstance().getSoundManager().play(soundInstance);
        }
        
        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }
    
}
