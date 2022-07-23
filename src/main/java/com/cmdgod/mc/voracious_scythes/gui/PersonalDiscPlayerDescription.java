package com.cmdgod.mc.voracious_scythes.gui;

import java.util.List;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.gui.elements.WSimpleTooltipSlider;
import com.cmdgod.mc.voracious_scythes.inventories.PersonalDiscPlayerInventory;
import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WSlider;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.Icon;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PersonalDiscPlayerDescription extends SyncedGuiDescription {

    PersonalDiscPlayerInventory itemInventory;
    public static final int TITLE_HEIGHT = 10;
    public static final int ITEM_SLOT_SIZE = 18;
    public static final int MUSIC_PANEL_WIDTH = 15;
    public static final int MISC_PADDING = 4;

    private PersonalDiscPlayerPropertyDelegate customPropertyDelegate;

    private static Identifier repeatId = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/playmode_repeat.png");
    private static Identifier repeatOneId = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/playmode_repeat_one.png");
    private static Identifier randomId = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/playmode_random.png");

    public static final List<Icon> PLAY_MODE_ICONS = List.of(new TextureIcon(repeatId), new TextureIcon(repeatOneId), new TextureIcon(randomId));

    private static WButton modeSwitchButton;

    public PersonalDiscPlayerDescription(int syncId, PlayerInventory playerInventory, PersonalDiscPlayerInventory itemInventory, PersonalDiscPlayerPropertyDelegate propertyDelegate) {
        super(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, syncId, playerInventory, itemInventory, propertyDelegate);
        this.itemInventory = itemInventory;
        this.customPropertyDelegate = propertyDelegate;

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize( ITEM_SLOT_SIZE * PersonalDiscPlayer.INVENTORY_COLUMNS + MUSIC_PANEL_WIDTH + MISC_PADDING, 400);
        root.setInsets(Insets.ROOT_PANEL);

        WGridPanel itemPanel = new WGridPanel();
        for (int i=0; i < PersonalDiscPlayer.INVENTORY_ROWS; i++) {
            for (int j=0; j < PersonalDiscPlayer.INVENTORY_COLUMNS; j++) {
                WItemSlot itemSlot = WItemSlot.of(itemInventory, i*PersonalDiscPlayer.INVENTORY_COLUMNS + j);
                itemPanel.add(itemSlot, j, i);
            }
        }
        root.add(itemPanel, 0, TITLE_HEIGHT);

        WPlainPanel musicPanel = new WPlainPanel();

        WSimpleTooltipSlider volumeSlider = new WSimpleTooltipSlider(0, 100, Axis.VERTICAL, "item.voracious_scythes.personal_disc_player.volume");
        volumeSlider.setValue(propertyDelegate.getByName("musicVolume"));
        volumeSlider.setDraggingFinishedListener((intConsumer) -> {
            customPropertyDelegate.setByName("musicVolume", volumeSlider.getValue());
        });
        musicPanel.add(volumeSlider, 3, -1, MUSIC_PANEL_WIDTH, 83);

        modeSwitchButton = new WButton(Text.of(""));
        musicPanel.add(modeSwitchButton, 2, 88);
        setModeSwitchButtonIcon(getAndValidatePlayMode());
        modeSwitchButton.setSize(18, 18);
        modeSwitchButton.setOnClick(() -> {
            toggleSwitchButton();
        });

        root.add(musicPanel, ITEM_SLOT_SIZE * PersonalDiscPlayer.INVENTORY_COLUMNS, TITLE_HEIGHT);

        WPlayerInvPanel playerInvPanel = this.createPlayerInventoryPanel();
        root.add(playerInvPanel, 0, PersonalDiscPlayer.INVENTORY_ROWS * ITEM_SLOT_SIZE + TITLE_HEIGHT + MISC_PADDING);

        int rootWidth = ITEM_SLOT_SIZE * 9 + Insets.ROOT_PANEL.left() + Insets.ROOT_PANEL.right();
        int rootHeight = ITEM_SLOT_SIZE * PersonalDiscPlayer.INVENTORY_ROWS + TITLE_HEIGHT + MISC_PADDING + playerInvPanel.getHeight() + Insets.ROOT_PANEL.bottom() + Insets.ROOT_PANEL.top();

        root.setSize(rootWidth, rootHeight);
        root.validate(this);
    }

    private int getAndValidatePlayMode() {
        int playMode = customPropertyDelegate.getByName("playMode");
        if (playMode < 0 || playMode >= PLAY_MODE_ICONS.size()) {
            ItemStack warnStack = new ItemStack(Items.PAPER);
            warnStack.setCount(1);
            warnStack.setCustomName(Text.of("INVALID PLAY MODE DETECTED!" + playMode));
            playerInventory.insertStack(warnStack);
            playMode = 0;
            customPropertyDelegate.setByName("playMode", 0);
        }
        return playMode;
    }

    private void setModeSwitchButtonIcon(int id) {
        modeSwitchButton.setIcon(PLAY_MODE_ICONS.get(id));
    }

    private void toggleSwitchButton() {
        int playMode = getAndValidatePlayMode();
        playMode++;
        if (playMode >= PLAY_MODE_ICONS.size()) {
            playMode = 0;
        }
        setModeSwitchButtonIcon(playMode);
        customPropertyDelegate.setByName("playMode", playMode);
    }
}