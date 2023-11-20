package com.cmdgod.mc.voracious_scythes;

import java.util.ArrayList;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.cmdgod.mc.voracious_scythes.blocks.ScythingTable;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerDescription;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerScreen;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableGuiDescription;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableScreen;
import com.cmdgod.mc.voracious_scythes.gui.broomtable.BroomTableDescription;
import com.cmdgod.mc.voracious_scythes.gui.broomtable.BroomTableScreen;
import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;
import com.cmdgod.mc.voracious_scythes.mixinextensions.PlayerExtension;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager.AbilitySaveDTO;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class VoraciousScythesClient implements ClientModInitializer {

    public static KeyBinding BROOM_ABILITY_KEYBIND;

    @Override
    public void onInitializeClient() {
        //ScreenRegistry.<ScythingTableGuiDescription, ScythingTableScreen>register(VoraciousScythes.SCYTHING_TABLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new ScythingTableScreen(gui, inventory.player, title));
        ScreenRegistry.<ScythingTableGuiDescription, ScythingTableScreen>register(VoraciousScythes.SCYTHING_TABLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new ScythingTableScreen(gui, inventory.player, title));

        
        //HandledScreens.register(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, GenericContainerScreen::new);
        //ScreenRegistry.<PersonalDiscPlayerDescription, PersonalDiscPlayerScreen>register(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new PersonalDiscPlayerScreen(gui, inventory.player, title));
        HandledScreens.<PersonalDiscPlayerDescription, PersonalDiscPlayerScreen>register(VoraciousScythes.PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new PersonalDiscPlayerScreen(gui, inventory.player, title));

        HandledScreens.<BroomTableDescription, BroomTableScreen>register(VoraciousScythes.BROOM_TABLE_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new BroomTableScreen(gui, inventory.player, title));

        EntityRendererRegistry.register(VoraciousScythes.StarBulletEntityType, (context) -> new FlyingItemEntityRenderer<>(context));
        EntityRendererRegistry.register(VoraciousScythes.BigShotEntityType, (context) -> new FlyingItemEntityRenderer<>(context));
        EntityRendererRegistry.register(VoraciousScythes.WaterBombEntityType, (context) -> new FlyingItemEntityRenderer<>(context));

        BROOM_ABILITY_KEYBIND = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voracious_scythes.broom_ability", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_X, // The keycode of the key
            "category.voracious_scythes.keys" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (BROOM_ABILITY_KEYBIND.wasPressed()) {
                //client.player.sendMessage(Text.of("Key 1 was pressed!"), false);
                ClientPlayNetworking.send(VoraciousScythes.TRIGGER_BROOM_ABILITY_ID, PacketByteBufs.empty());
                ItemStack broomStack = VoraciousScythes.getEquippedBroomStack(client.player);
                if (broomStack != null) {
                    VoraciousScythes.BROOM_BASE.useAbility(client.player, broomStack);
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(VoraciousScythes.ENTITY_SPAWN_PACKET_ID, (client, handler, byteBuf, responseSender) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUuid();
			int entityId = byteBuf.readVarInt();
			Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			client.execute(() -> {
                MinecraftClient mcClient = MinecraftClient.getInstance();
				if (mcClient.world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(mcClient.world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos.x, pos.y, pos.z);
				e.setPos(pos.x, pos.y, pos.z);
				e.setPitch(pitch);
				e.setYaw(yaw);
				e.setId(entityId);
				e.setUuid(uuid);
				mcClient.world.addEntity(entityId, e);
			});
		});

        ClientPlayNetworking.registerGlobalReceiver(VoraciousScythes.UPDATE_COOLDOWNS_ID, (client, handler, buf, responseSender) -> {
            int tick = buf.readInt();
            int length = buf.readInt();
            ArrayList<AbilitySaveDTO> save = new ArrayList<AbilitySaveDTO>();
            for (var i=0; i < length; i++) {
                Identifier id = buf.readIdentifier();
                int startTick = buf.readInt();
                int endTick = buf.readInt();
                int charges = buf.readInt();
                save.add(new AbilitySaveDTO(startTick, endTick, charges, id.toString()));
            }
            client.execute(() -> {
                PlayerExtension playerExt = (PlayerExtension)client.player;
                AbilityCooldownManager cdManager = playerExt.getCdManager();
                cdManager.clearEverything();
                cdManager.setTick(tick);
                for (var i=0; i < length; i++) {
                    AbilitySaveDTO dto = save.get(i);
                    cdManager.restoreSaveData(dto.id, dto.startTick, dto.endTick, dto.charges);
                }
            });
        });
    }
    
}
