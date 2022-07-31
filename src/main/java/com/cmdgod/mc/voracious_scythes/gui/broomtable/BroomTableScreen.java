package com.cmdgod.mc.voracious_scythes.gui.broomtable;

import com.cmdgod.mc.voracious_scythes.blocks.BroomTable;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class BroomTableScreen extends CottonInventoryScreen<BroomTableDescription> {
    public BroomTableScreen(BroomTableDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
