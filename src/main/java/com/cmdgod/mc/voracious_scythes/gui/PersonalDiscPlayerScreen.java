package com.cmdgod.mc.voracious_scythes.gui;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.widget.WSlider;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class PersonalDiscPlayerScreen extends CottonInventoryScreen<PersonalDiscPlayerDescription> {
    public PersonalDiscPlayerScreen(PersonalDiscPlayerDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}