package com.cmdgod.mc.voracious_scythes.gui.elements;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WSlider;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class WSimpleTooltipSlider extends WSlider {

    private String translateKey;

    public WSimpleTooltipSlider(int min, int max, Axis axis, String translateKey) {
        super(min, max, axis);
        this.translateKey = translateKey;
    }

    @Override
    public void addTooltip(TooltipBuilder tooltip) {
        tooltip.add(new TranslatableText(translateKey, this.getValue()));
    }
    
}
