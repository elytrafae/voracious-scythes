package com.cmdgod.mc.voracious_scythes.gui.elements;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.inventory.Inventory;

public class WPersonalDiscPlayerItemSlot extends WItemSlot {

    public WPersonalDiscPlayerItemSlot(Inventory inventory, int startIndex, int slotsWide, int slotsHigh, boolean big) {
        super(inventory, startIndex, slotsWide, slotsHigh, big);
        //TODO Auto-generated constructor stub
    }

    @Override 
    public InputResult onClick(int x, int y, int button) {
        return InputResult.PROCESSED;
    }
    
}
