package com.cmdgod.mc.voracious_scythes;

import com.cmdgod.mc.voracious_scythes.blocks.ScythingTable;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableGuiDescription;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class VoraciousScythesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //ScreenRegistry.<ScythingTableGuiDescription, ScythingTableScreen>register(VoraciousScythes.SCYTHING_TABLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new ScythingTableScreen(gui, inventory.player, title));
        ScreenRegistry.<ScythingTableGuiDescription, ScythingTableScreen>register(VoraciousScythes.SCYTHING_TABLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new ScythingTableScreen(gui, inventory.player, title));
    }
    
}
