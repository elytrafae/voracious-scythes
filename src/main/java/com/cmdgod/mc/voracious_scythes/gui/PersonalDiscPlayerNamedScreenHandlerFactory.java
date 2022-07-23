package com.cmdgod.mc.voracious_scythes.gui;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.inventories.PersonalDiscPlayerInventory;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PersonalDiscPlayerNamedScreenHandlerFactory implements NamedScreenHandlerFactory {

    private ItemStack stack;
    //private PropertyDelegate propertyDelegate;

    public PersonalDiscPlayerNamedScreenHandlerFactory(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public Text getDisplayName() {
        return stack.getName();
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PersonalDiscPlayerDescription(syncId, playerInventory, new PersonalDiscPlayerInventory(stack), new PersonalDiscPlayerPropertyDelegate(stack));
    }
    
}
