package com.cmdgod.mc.voracious_scythes.gui;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.inventories.PersonalDiscPlayerInventory;
import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandlerContext;

public class PersonalDiscPlayerDescription extends SyncedGuiDescription {

    PersonalDiscPlayerInventory itemInventory;

    public PersonalDiscPlayerDescription(int syncId, PlayerInventory playerInventory, PersonalDiscPlayerInventory itemInventory, ScreenHandlerContext context) {
        super(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, syncId, playerInventory, getBlockInventory(context, PersonalDiscPlayer.INVENTORY_SIZE), getBlockPropertyDelegate(context));
        this.itemInventory = itemInventory;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot, 4, 1);

        root.add(this.createPlayerInventoryPanel(), 0, 3);

        root.validate(this);
    }
}