package com.cmdgod.mc.voracious_scythes.gui.elements;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.inventory.Inventory;

public class WPersonalDiscPlayerItemSlot extends WItemSlot {

    @FunctionalInterface
    public interface EventHandler {

        public InputResult run(int x, int y, int button);
    }

    private EventHandler handler;

    public WPersonalDiscPlayerItemSlot(Inventory inventory, int startIndex) {
        this(inventory, startIndex, 1, 1, false);
    }

    public WPersonalDiscPlayerItemSlot(Inventory inventory, int startIndex, int slotsWide, int slotsHigh, boolean big) {
        super(inventory, startIndex, slotsWide, slotsHigh, big);
        //TODO Auto-generated constructor stub
    }

    public void setOnClickEventHandler(EventHandler handler) {
        this.handler = handler;
    }

    @Override 
    public InputResult onClick(int x, int y, int button) {
        return handler.run(x, y, button);
    }
    
}
