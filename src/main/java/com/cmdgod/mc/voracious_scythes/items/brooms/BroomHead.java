package com.cmdgod.mc.voracious_scythes.items.brooms;

import java.util.List;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BlinkingBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BrewerBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BruteBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.CleaningBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.GuardianBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.StarBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.WaterWitchBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.ApprenticeBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BigShotBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BlazingBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.WitchBroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BroomHead extends Item {
    
    ScytheAbilityBase ability = null;

    public BroomHead(Identifier abilityId) {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
        if (abilityId != null) {
            this.ability = ScytheAbilityBase.ABILITY_REGISTRY.get(abilityId);
        }
    }

    public static void registerMyHeads() {
        CleaningBroomHead CLEANING_BROOM_HEAD = new CleaningBroomHead();
        WitchBroomHead WITCH_BROOM_HEAD = new WitchBroomHead();
        BlinkingBroomHead BLINKING_BROOM_HEAD = new BlinkingBroomHead();
        BrewerBroomHead BREWER_BROOM_HEAD = new BrewerBroomHead();
        BlazingBroomHead BLAZING_BROOM_HEAD = new BlazingBroomHead();
        ApprenticeBroomHead APPRENTICE_BROOM_HEAD = new ApprenticeBroomHead();
        GuardianBroomHead GUARDIAN_BROOM_HEAD = new GuardianBroomHead();
        BruteBroomHead BRUTE_BROOM_HEAD = new BruteBroomHead();
        StarBroomHead STAR_SPREADER_BROOM_HEAD = new StarBroomHead();
        BigShotBroomHead SPAMTON_SILLY_STRINGS = new BigShotBroomHead();
        WaterWitchBroomHead WATER_WITCH_BROOM_HEAD = new WaterWitchBroomHead();
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (ability != null) {
            tooltip.add(new TranslatableText("voracious_scythes.when_put_on_broom").setStyle(BroomBase.STAT_TITLE_STYLE));
            ScytheBase.addAbilityDescriptionToTooltip(tooltip, ability);
        }
        String translationKey = getTranslationKey() + ".desc";
        TranslatableText flairText = new TranslatableText(translationKey);
        if (!flairText.getString().equals(translationKey)) {
            tooltip.add(flairText.setStyle(BroomBase.FLAIR_STYLE));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

}
