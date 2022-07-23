package com.cmdgod.mc.voracious_scythes.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

public class ScythingTableScreen extends CottonInventoryScreen<ScythingTableGuiDescription>  {

    public ScythingTableScreen(ScythingTableGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
    
}
