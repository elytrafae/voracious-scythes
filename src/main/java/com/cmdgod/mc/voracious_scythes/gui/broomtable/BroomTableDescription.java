package com.cmdgod.mc.voracious_scythes.gui.broomtable;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomGem;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomHead;
import com.cmdgod.mc.voracious_scythes.items.brooms.BroomStick;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.Icon;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.Identifier;

public class BroomTableDescription extends SyncedGuiDescription {

    private static final int SCREEN_HEIGHT = 180;
    private static final int INVENTORY_SIZE = 4;
    public static final int TITLE_HEIGHT = 10;
    public static final int ITEM_SLOT_SIZE = 18;
    public static final int INPUT_SLOT_Y = 68;

    
    private static final Identifier BROOM_TEXTURE = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/slots/broom.png");
    private static final Identifier STICK_TEXTURE = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/slots/broom_stick.png");
    private static final Identifier GEM_TEXTURE = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/slots/broom_gem.png");
    private static final Identifier HEAD_TEXTURE = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/slots/broom_head.png");
    private static final Identifier GRAY_PX_TEXTURE = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/menu_gray_px.png");
    private static final Identifier YELLOW_PX_TEXTURE = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/yellow_px.png");
    

    public BroomTableDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(VoraciousScythes.BROOM_TABLE_SCREEN_HANDLER_TYPE, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);

        WPlayerInvPanel playerInv = this.createPlayerInventoryPanel();

        root.setSize(playerInv.getWidth(), SCREEN_HEIGHT);
        root.setInsets(Insets.ROOT_PANEL);

        root.add(playerInv, 0, SCREEN_HEIGHT - playerInv.getHeight());

        WItemSlot outputSlot = WItemSlot.of(blockInventory, 3);
        outputSlot.setIcon(new TextureIcon(BROOM_TEXTURE));
        outputSlot.setInsertingAllowed(false);
        root.add(outputSlot, root.getWidth()/2 - ITEM_SLOT_SIZE/2, TITLE_HEIGHT*2);

        int inputPadding = root.getWidth()/4;

        WItemSlot broomStickSlot = WItemSlot.of(blockInventory, 0);
        broomStickSlot.setIcon(new TextureIcon(STICK_TEXTURE));
        broomStickSlot.setFilter((stack) -> {return stack.isEmpty() || (stack.getItem() instanceof BroomStick);});
        root.add(broomStickSlot, inputPadding - ITEM_SLOT_SIZE/2, INPUT_SLOT_Y);

        WItemSlot broomGemSlot = WItemSlot.of(blockInventory, 1);
        broomGemSlot.setIcon(new TextureIcon(GEM_TEXTURE));
        broomGemSlot.setFilter((stack) -> {return stack.isEmpty() || (stack.getItem() instanceof BroomGem);});
        root.add(broomGemSlot, inputPadding*2 - ITEM_SLOT_SIZE/2, INPUT_SLOT_Y);

        WItemSlot broomHeadSlot = WItemSlot.of(blockInventory, 2);
        broomHeadSlot.setIcon(new TextureIcon(HEAD_TEXTURE));
        broomHeadSlot.setFilter((stack) -> {return stack.isEmpty() || (stack.getItem() instanceof BroomHead);});
        root.add(broomHeadSlot, inputPadding*3 - ITEM_SLOT_SIZE/2, INPUT_SLOT_Y);

        root.validate(this);
    }
}
