package com.cmdgod.mc.voracious_scythes.huds;

import java.util.ArrayList;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager.HUDEntry;

import io.github.cottonmc.cotton.gui.client.CottonHud;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CooldownHUD {
    
    private static final int DISPLAY_X = 5;
    private static final int DISPLAY_Y = -30;
    private static WPlainPanel mainPanel; 

    private static final Style COOLDOWN_STYLE = Style.EMPTY.withColor(Formatting.WHITE);

    private static final Identifier COOLDOWN_FRAME_ID = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/cooldown_hud/cooldown_icon_frame.png");
    private static final Identifier COOLDOWN_FILL_PX_ID = new Identifier(VoraciousScythes.MOD_NAMESPACE, "textures/ui/cooldown_hud/menu_gray_px_transparent.png");

    public static void initialize() {
        addMainPanel();

        //CottonHud.add(new WLabel(Text.of("Test label")), 10, -30, 10, 10);
    }

    public static void tick(AbilityCooldownManager cdManager) {
        CottonHud.remove(mainPanel);
        addMainPanel();
        
        ArrayList<HUDEntry> hudEntries = cdManager.getDataForHUD();
        for (int i=0; i < Math.min(hudEntries.size(), 3); i++) {
            HUDEntry entry = hudEntries.get(i);
            // This is a test version!
            WSprite background = new WSprite(COOLDOWN_FRAME_ID);
            background.setSize(68, 68);
            mainPanel.add(background, i*20, 0);

            Identifier id = new Identifier(entry.ability.id.getNamespace(), "textures/ability/" + entry.ability.id.getPath() + ".png");
            WSprite abilitySprite = new WSprite(id);
            abilitySprite.setSize(64, 64);
            mainPanel.add(abilitySprite, background.getX(), background.getY());

            WSprite fillSprite = new WSprite(COOLDOWN_FILL_PX_ID);
            fillSprite.setSize(64, 64*(entry.cooldownLeft/entry.ability.cooldown));
            mainPanel.add(fillSprite, background.getX(), background.getY());

            int cooldownLeftSeconds = entry.cooldownLeft/20;
            String timeString = cooldownLeftSeconds + "s";
            if (cooldownLeftSeconds > 60) {
                timeString = ((int)cooldownLeftSeconds/60) + "m";
            }
            WLabel cooldownLabel = new WLabel(Text.of(timeString).getWithStyle(COOLDOWN_STYLE).get(0));
            cooldownLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cooldownLabel.setVerticalAlignment(VerticalAlignment.CENTER);
            int cooldownX = background.getX() + background.getWidth()/2;// - cooldownLabel.getWidth()/2;
            int cooldownY = background.getY() + background.getHeight()/2;// - cooldownLabel.getHeight()/2;
            mainPanel.add(cooldownLabel, cooldownX, cooldownY);
            
            if (entry.ability.charges > 1) {
                WLabel chargeLabel = new WLabel(Text.of(entry.chargesLeft + " ").getWithStyle(COOLDOWN_STYLE).get(0));
                chargeLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
                chargeLabel.setVerticalAlignment(VerticalAlignment.CENTER);
                int chargeX = background.getX() + background.getWidth();// - cooldownLabel.getWidth()/2;
                int chargeY = background.getY() + background.getHeight();// - cooldownLabel.getHeight()/2;
                mainPanel.add(chargeLabel, chargeX, chargeY);
            }
        }
    }

    private static void addMainPanel() {
        mainPanel = new WPlainPanel();
        CottonHud.add(mainPanel, DISPLAY_X, DISPLAY_Y);
    }

}
