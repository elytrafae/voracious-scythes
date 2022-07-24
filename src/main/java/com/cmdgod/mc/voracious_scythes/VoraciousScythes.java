package com.cmdgod.mc.voracious_scythes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.screen.PropertyDelegate;

import com.cmdgod.mc.voracious_scythes.blockentities.PocketFarmEntity;
import com.cmdgod.mc.voracious_scythes.blockentities.ScythingTableEntity;
import com.cmdgod.mc.voracious_scythes.blocks.PocketFarm;
import com.cmdgod.mc.voracious_scythes.blocks.ScythingTable;
import com.cmdgod.mc.voracious_scythes.blocks.PocketFarms.SugarCanePocketFarm;
import com.cmdgod.mc.voracious_scythes.blocks.PocketFarms.WoolPocketFarm;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerDescription;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerNamedScreenHandlerFactory;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerPropertyDelegate;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerScreen;
import com.cmdgod.mc.voracious_scythes.gui.ScythingTableGuiDescription;
import com.cmdgod.mc.voracious_scythes.inventories.PersonalDiscPlayerInventory;
import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;
import com.cmdgod.mc.voracious_scythes.items.PosableMannequin;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.items.musicdiscs.DoomDiscFragment;
import com.cmdgod.mc.voracious_scythes.items.musicdiscs.ModdedMusicDisc;
import com.cmdgod.mc.voracious_scythes.items.musicdiscs.MusiclessDisc;
import com.cmdgod.mc.voracious_scythes.items.scythes.FlintScythe;
import com.cmdgod.mc.voracious_scythes.items.scythes.StoneScythe;
import com.cmdgod.mc.voracious_scythes.items.scythes.WoodenScythe;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.HeavyHit;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.TornadoFrenzy;
import com.cmdgod.mc.voracious_scythes.settingsclasses.PocketFarmSubtype;
import com.mojang.brigadier.arguments.FloatArgumentType;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoraciousScythes implements ModInitializer {

	public static final String MOD_NAMESPACE = "voracious_scythes";

	// TODO: Remove the test Scythe textures before publishing!

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAMESPACE);

	public static BlockEntityType<ScythingTableEntity> SCYTHING_TABLE_ENTITY;
	public static final ScythingTable SCYTHING_TABLE = new ScythingTable();
	public static ScreenHandlerType SCYTHING_TABLE_SCREEN_HANDLER_TYPE;

	public static final FlintScythe FLINT_SCYTHE = new FlintScythe();
	public static final WoodenScythe WOODEN_SCYTHE = new WoodenScythe();
	public static final StoneScythe STONE_SCYTHE = new StoneScythe();

	public static MusiclessDisc MUSICLESS_DISC;
	public static PersonalDiscPlayer PERSONAL_DISC_PLAYER;
	public static ScreenHandlerType PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE;

	public static final Identifier UPDATE_TRACK_NUMBER_ID = new Identifier(MOD_NAMESPACE, "network_update_track");

	public static final ItemGroup MUSIC_DISC_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_NAMESPACE, "music_discs"),() -> new ItemStack(MUSICLESS_DISC));

	//public static BlockEntityType<PocketFarmEntity> POCKET_FARM_ENTITY;

	public static BlockEntityType<PocketFarmEntity> POCKET_FARM_ENTITY;

	private static final PocketFarmSubtype SUGARCANE_FARM_TYPE = new PocketFarmSubtype(new Identifier("minecraft:sugar_cane"), 2);

	public static final PocketFarm SUGARCANE_FARM = new SugarCanePocketFarm();
	public static final PocketFarm WOOL_FARM = new WoolPocketFarm();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registry.BLOCK, new Identifier(MOD_NAMESPACE, "scything_table"), SCYTHING_TABLE);
        Registry.register(Registry.ITEM, new Identifier(MOD_NAMESPACE, "scything_table"), new BlockItem(SCYTHING_TABLE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
		SCYTHING_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_NAMESPACE, "scything_table_entity"), FabricBlockEntityTypeBuilder.create(ScythingTableEntity::new, SCYTHING_TABLE).build(null));
		SCYTHING_TABLE_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_NAMESPACE, "scything_table"), (syncId, inventory) -> new ScythingTableGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));

		Registry.register(Registry.ITEM, new Identifier(MOD_NAMESPACE, "flint_scythe"), FLINT_SCYTHE);
		Registry.register(Registry.ITEM, new Identifier(MOD_NAMESPACE, "wooden_scythe"), WOODEN_SCYTHE);
		Registry.register(Registry.ITEM, new Identifier(MOD_NAMESPACE, "stone_scythe"), STONE_SCYTHE);

		// Music Disc Ingredients
		MUSICLESS_DISC = new MusiclessDisc();
		PosableMannequin POSABLE_MANNEQUIN = new PosableMannequin();
		DoomDiscFragment DOOM_DISC_FRAGMENT = new DoomDiscFragment();

		// My music discs
		ModdedMusicDisc TWENTY_ICOSA_MUSIC_DISC = new ModdedMusicDisc("twenty_icosa");
		ModdedMusicDisc BACKROOM_LABYRINTH_MUSIC_DISC = new ModdedMusicDisc("backroom_labyrinth");
		ModdedMusicDisc GOD_RACE_MUSIC_DISC = new ModdedMusicDisc("god_race");
		// ModdedMusicDisc STAIRSHELF_MUSIC_DISC = new ModdedMusicDisc("stairshelf");
		ModdedMusicDisc HOURGLASS_MEADOW_MUSIC_DISC = new ModdedMusicDisc("hourglass_meadow");
		ModdedMusicDisc DEAD_GOD_GRAVEYARD_MUSIC_DISC = new ModdedMusicDisc("dead_god_graveyard");

		// Noreason's discs
		ModdedMusicDisc COLUMBINA_DAMSELETTE_LULLABY_MUSIC_DISC = new ModdedMusicDisc("columbina_damselette_lullaby");
		ModdedMusicDisc CLERIC_BEAST_MUSIC_DISC = new ModdedMusicDisc("cleric_beast");
		ModdedMusicDisc FATHER_GASCOIGNE_THE_HUNTER_MUSIC_DISC = new ModdedMusicDisc("father_gascoigne_the_hunter");
		ModdedMusicDisc AETHERES_MUSIC_DISC = new ModdedMusicDisc("aetheres");
		ModdedMusicDisc GEOXOR_DEAD_MUSIC_DISC = new ModdedMusicDisc("geoxor_dead");
		
		// Blep's discs
		ModdedMusicDisc HABIBI_MUSIC_DISC = new ModdedMusicDisc("habibi");
		ModdedMusicDisc KAWAII_MUSIC_DISC = new ModdedMusicDisc("kawaii");
		ModdedMusicDisc GIANTS_MUSIC_DISC = new ModdedMusicDisc("giants");
		ModdedMusicDisc PLAIN_JANE_MUSIC_DISC = new ModdedMusicDisc("plain_jane");
		ModdedMusicDisc TWO_PHUT_HON_MUSIC_DISC = new ModdedMusicDisc("two_phut_hon");

		// Troll discs
		ModdedMusicDisc MARIO_CBT_MUSIC_DISC = new ModdedMusicDisc("mario_cbt", Rarity.EPIC);

		// Special discs
		ModdedMusicDisc TERMINAL_EXISTENCE_MUSIC_DISC = new ModdedMusicDisc("terminal_existence", Rarity.EPIC);
		ModdedMusicDisc FIERCEST_WARRIOR_MUSIC_DISC = new ModdedMusicDisc("fiercest_warrior", Rarity.EPIC);

		// DOOM Discs
		ModdedMusicDisc BFG_DIVISION_MUSIC_DISC = new ModdedMusicDisc("bfg_division", Rarity.EPIC, true);
		ModdedMusicDisc THE_ONLY_THING_THEY_FEAR_IS_YOU_MUSIC_DISC = new ModdedMusicDisc("the_only_thing_they_fear_is_you", Rarity.EPIC, true);
		ModdedMusicDisc BFG_TEN_K_MUSIC_DISC = new ModdedMusicDisc("bfg_ten_k", Rarity.EPIC, true);
		ModdedMusicDisc MEATHOOK_MUSIC_DISC = new ModdedMusicDisc("meathook", Rarity.EPIC, true);
		ModdedMusicDisc RIP_AND_TEAR_MUSIC_DISC = new ModdedMusicDisc("rip_and_tear", Rarity.EPIC, true);
		ModdedMusicDisc GLADIATOR_MUSIC_DISC = new ModdedMusicDisc("gladiator", Rarity.EPIC, true);
		ModdedMusicDisc CULTIST_BASE_MUSIC_DISC = new ModdedMusicDisc("cultist_base", Rarity.EPIC, true);

		// Disc Player
		PERSONAL_DISC_PLAYER = new PersonalDiscPlayer();
		//PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_NAMESPACE, "personal_disc_player"), (syncId, inventory) -> new PersonalDiscPlayerDescription(syncId, inventory, new PersonalDiscPlayerInventory(new ItemStack(PERSONAL_DISC_PLAYER)), new PersonalDiscPlayerPropertyDelegate(new ItemStack(PERSONAL_DISC_PLAYER))));

		ScreenHandlerType<PersonalDiscPlayerDescription> type = new ScreenHandlerType<>((syncId, inventory) -> new PersonalDiscPlayerDescription(syncId, inventory, new PersonalDiscPlayerInventory(new ItemStack(PERSONAL_DISC_PLAYER)), new PersonalDiscPlayerPropertyDelegate(new ItemStack(PERSONAL_DISC_PLAYER))));
		PERSONAL_DISC_PLAYER_SCREEN_HANDLER_TYPE = Registry.register(Registry.SCREEN_HANDLER, new Identifier(MOD_NAMESPACE, "personal_disc_player"), type);

		SUGARCANE_FARM.selfRegister();
		WOOL_FARM.selfRegister();

		POCKET_FARM_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(VoraciousScythes.MOD_NAMESPACE, "pocket_farm_entity"), FabricBlockEntityTypeBuilder.create(PocketFarmEntity::new, SUGARCANE_FARM, WOOL_FARM).build(null));

		ScytheAbilityBase.registerAbility(new HeavyHit());
		ScytheAbilityBase.registerAbility(new TornadoFrenzy());

		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			ScytheBase scythe;
			if (player.getMainHandStack().getItem() instanceof ScytheBase) {
				scythe = (ScytheBase)player.getMainHandStack().getItem();
			} else if (player.getOffHandStack().getItem() instanceof ScytheBase) {
				scythe = (ScytheBase)player.getOffHandStack().getItem();
			} else {
				scythe = null;
			}
			/* Manual spectator check is necessary because AttackBlockCallbacks
               fire before the spectator check */
			if (player.isSpectator() ||  (scythe == null) ) {
				return ActionResult.PASS;
			}
			ItemStack stack = player.getStackInHand(hand);
			return scythe.attackSweep(player, world, stack);
        });

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ScytheBase scythe;
			if (player.getMainHandStack().getItem() instanceof ScytheBase) {
				scythe = (ScytheBase)player.getMainHandStack().getItem();
			} else if (player.getOffHandStack().getItem() instanceof ScytheBase) {
				scythe = (ScytheBase)player.getOffHandStack().getItem();
			} else {
				scythe = null;
			}
			/* Manual spectator check is necessary because AttackBlockCallbacks
               fire before the spectator check */
			if (player.isSpectator() || (scythe == null) ) {
				return ActionResult.PASS;
			}
			ItemStack stack = player.getStackInHand(hand);
			return scythe.attackSweep(player, world, stack);
        });

		/*
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			Item item = itemStack.getItem();
			// Manual spectator check is necessary because AttackBlockCallbacks
            // fire before the spectator check
			if (player.isSpectator()) {
				return ActionResult.PASS;
			}
			if (item instanceof PersonalDiscPlayer) {
				return ((PersonalDiscPlayer)item).onLeftClick(player, hand, world);
			}
			return ActionResult.PASS;
        });

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			Item item = itemStack.getItem();
			// Manual spectator check is necessary because AttackBlockCallbacks
        	// fire before the spectator check
			if (player.isSpectator()) {
				return ActionResult.PASS;
			}
			if (item instanceof PersonalDiscPlayer) {
				return ((PersonalDiscPlayer)item).onLeftClick(player, hand, world);
			}
			return ActionResult.PASS;
        });
		*/

		/*
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("blazetest").then(CommandManager.argument("radius", FloatArgumentType.floatArg()).executes(context -> { 
				ServerCommandSource source = context.getSource();
				if (source.getPlayer() == null) {
					source.sendError(Text.of("The command source is not a player!"));
					return 0;
				}
				PlayerEntity player = source.getPlayer();
				float range = FloatArgumentType.getFloat(context, "radius");
				World world = player.getWorld();
				for (int i=0; i < 360; i+=5) {
					double x = player.getX() + Math.sin(i)*range;
					double z = player.getZ() + Math.cos(i)*range;
					BlazeEntity blaze = new BlazeEntity(EntityType.BLAZE, world);
					blaze.setPos(x, player.getY() + 0.5, z);
					blaze.setAiDisabled(true);
					blaze.setSilent(true);
					blaze.setPersistent();
					world.spawnEntity(blaze);
				}
				player.sendMessage(Text.of("Blazes spawned!"), false);
				return 1;
			})));
        });
		*/

		ServerPlayNetworking.registerGlobalReceiver(VoraciousScythes.UPDATE_TRACK_NUMBER_ID, (server, serverPlayer, handler, buf, responseSender) -> {
			server.execute(() -> {
				int slot = buf.getInt(0);
				int trackNumber = buf.getInt(1);
				Optional<TrinketComponent> trinketsOptional = TrinketsApi.getTrinketComponent(serverPlayer);
				if (trinketsOptional.isEmpty()) {
					return;
				}
				TrinketComponent trinkets = trinketsOptional.get();
				ItemStack stack = trinkets.getAllEquipped().get(slot).getRight();
				if (!(stack.getItem() instanceof PersonalDiscPlayer)) {
					return;
				}
				PersonalDiscPlayerPropertyDelegate propDele = new PersonalDiscPlayerPropertyDelegate(stack);
				propDele.setByName("currentTrack", trackNumber);
			});
		});
		
	}

	public static SoundEvent registerSoundEvent(String name) {
		Identifier id = new Identifier(VoraciousScythes.MOD_NAMESPACE, name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

	public static String returnServerOrClientString(World world) {
		return world.isClient ? "CLIENT" : "SERVER";
	}

}
