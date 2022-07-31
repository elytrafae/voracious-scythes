package com.cmdgod.mc.voracious_scythes.items.brooms;

import java.util.List;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BlinkingBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BrewerBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.CleaningBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.GuardianBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.ApprenticeBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.BlazingBroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.heads.WitchBroomHead;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class BroomHead extends Item {
    
    ScytheAbilityBase ability = null;

    public BroomHead(ScytheAbilityBase ability) {
        super(new Item.Settings().group(VoraciousScythes.BROOM_ITEM_GROUP).maxCount(1));
        this.ability = ability;
    }

    public static void registerMyHeads() {
        CleaningBroomHead CLEANING_BROOM_HEAD = new CleaningBroomHead();
        WitchBroomHead WITCH_BROOM_HEAD = new WitchBroomHead();
        BlinkingBroomHead BLINKING_BROOM_HEAD = new BlinkingBroomHead();
        BrewerBroomHead BREWER_BROOM_HEAD = new BrewerBroomHead();
        BlazingBroomHead BLAZING_BROOM_HEAD = new BlazingBroomHead();
        ApprenticeBroomHead APPRENTICE_BROOM_HEAD = new ApprenticeBroomHead();
        GuardianBroomHead GUARDIAN_BROOM_HEAD = new GuardianBroomHead();
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (ability != null) {
            tooltip.add(new TranslatableText("voracious_scythes.when_put_on_broom").setStyle(BroomBase.STAT_TITLE_STYLE));
            ScytheBase.addAbilityDescriptionToTooltip(tooltip, ability);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

}
