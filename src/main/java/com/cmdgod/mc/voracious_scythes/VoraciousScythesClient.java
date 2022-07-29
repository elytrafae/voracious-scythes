package com.cmdgod.mc.voracious_scythes;

import com.cmdgod.mc.voracious_scythes.blocks.ScythingTable;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerDescription;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerScreen;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableGuiDescription;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableScreen;
import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;

public class VoraciousScythesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //ScreenRegistry.<ScythingTableGuiDescription, ScythingTableScreen>register(VoraciousScythes.SCYTHING_TABLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new ScythingTableScreen(gui, inventory.player, title));
        ScreenRegistry.<ScythingTableGuiDescription, ScythingTableScreen>register(VoraciousScythes.SCYTHING_TABLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new ScythingTableScreen(gui, inventory.player, title));

        
        //HandledScreens.register(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, GenericContainerScreen::new);
        //ScreenRegistry.<PersonalDiscPlayerDescription, PersonalDiscPlayerScreen>register(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new PersonalDiscPlayerScreen(gui, inventory.player, title));
        HandledScreens.<PersonalDiscPlayerDescription, PersonalDiscPlayerScreen>register(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new PersonalDiscPlayerScreen(gui, inventory.player, title));
    }
    
}
