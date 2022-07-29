package com.cmdgod.mc.voracious_scythes.mixins;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.gui.PersonalDiscPlayerPropertyDelegate;
import com.cmdgod.mc.voracious_scythes.inventories.PersonalDiscPlayerInventory;
import com.cmdgod.mc.voracious_scythes.items.PersonalDiscPlayer;
import com.cmdgod.mc.voracious_scythes.items.PosableMannequin;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.mixinextensions.PlayerExtension;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager.AbilitySaveDTO;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager.AbilitySaveData;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.DurationSaveDTO;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.DurationSaveData;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerExtension {

    private final AbilityCooldownManager cdManager = new AbilityCooldownManager();
    private final AbilityDurationManager abilityDurationManager = new AbilityDurationManager();

    public AbilityCooldownManager getCdManager() {
        return cdManager;
    }

    public AbilityDurationManager getAbilityDurationManager() {
        return abilityDurationManager;
        
    }

    private void writeCooldownDataToNbt(NbtCompound nbt) {
        AbilitySaveData save = cdManager.getSaveData();
        NbtCompound nbtSave = new NbtCompound();
        NbtList cdList = new NbtList();
        nbtSave.putInt("tick", save.tick);
        for (int i=0; i < save.dtos.size(); i++) {
            AbilitySaveDTO dto = save.dtos.get(i);
            NbtCompound nbtDTO = new NbtCompound();
            nbtDTO.putString("id", dto.id);
            nbtDTO.putInt("startTick", dto.startTick);
            nbtDTO.putInt("endTick", dto.endTick);
            nbtDTO.putInt("charges", dto.charges);
            cdList.add(nbtDTO);
        }
        nbtSave.put("entries", cdList);
        nbt.put("CMDGod_AbilityCooldown", nbtSave);
    }

    private void writeDurationDataToNbt(NbtCompound nbt) {
        DurationSaveData save = abilityDurationManager.getSaveData();
        NbtCompound nbtSave = new NbtCompound();
        NbtList cdList = new NbtList();
        nbtSave.putInt("tick", save.tick);
        for (int i=0; i < save.dtos.size(); i++) {
            DurationSaveDTO dto = save.dtos.get(i);
            NbtCompound nbtDTO = new NbtCompound();
            nbtDTO.putString("id", dto.id);
            nbtDTO.putString("scytheId", dto.itemId);
            nbtDTO.putInt("preStartTick", dto.preStartTick);
            nbtDTO.putInt("startTick", dto.startTick);
            nbtDTO.putInt("endTick", dto.endTick);
            nbtDTO.putInt("postEndTick", dto.postEndTick);
            cdList.add(nbtDTO);
        }
        nbtSave.put("entries", cdList);
        nbt.put("CMDGod_AbilityDuration", nbtSave);
    }
	
	@Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
	public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        writeCooldownDataToNbt(nbt);
        writeDurationDataToNbt(nbt);
	}

    private void readCooldownDataFromNbt(NbtCompound nbt) {
        cdManager.clearEverything();
        NbtCompound nbtSave = nbt.getCompound("CMDGod_AbilityCooldown");
		cdManager.setTick(nbtSave.getInt("tick"));
        NbtList list = nbtSave.getList("entries", 0);
        for (int i=0; i < list.size(); i++) {
            NbtCompound nbtDTO = list.getCompound(i);
            cdManager.restoreSaveData(nbtDTO.getString("id"), nbtDTO.getInt("startTick"), nbtDTO.getInt("endTick"), nbtDTO.getInt("charges"));
        }
    }

    private void readDurationDataFromNbt(NbtCompound nbt) {
        abilityDurationManager.clearEverything();
        NbtCompound nbtSave = nbt.getCompound("CMDGod_AbilityDuration");
		abilityDurationManager.setTick(nbtSave.getInt("tick"));
        NbtList list = nbtSave.getList("entries", 0);
        for (int i=0; i < list.size(); i++) {
            NbtCompound nbtDTO = list.getCompound(i);
            abilityDurationManager.restoreSaveData(nbtDTO.getString("id"), nbtDTO.getInt("preStartTick"), nbtDTO.getInt("startTick"), nbtDTO.getInt("endTick"), nbtDTO.getInt("postEndTick"),nbtDTO.getString("scytheId"));
        }
    }
	
	@Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
	public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        readCooldownDataFromNbt(nbt);
        readDurationDataFromNbt(nbt);
	}

    @Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo info) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        cdManager.tick(this);
        abilityDurationManager.tick(player, this);

        // Two-handed violation checker!
        ItemStack mainStack = player.getMainHandStack();
        ItemStack offStack = player.getOffHandStack();
        if (ScytheBase.isTwoHandedViolated(mainStack, offStack)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 9));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 2));
        }
        if ( (offStack.getItem() instanceof ScytheBase) && mainStack.isEmpty()) {
            player.setStackInHand(Hand.MAIN_HAND, offStack);
            player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
        }

        if (player.getWorld().isClient) {
            this.clientTick(this, player);
        }

    }

    private void clientTick(PlayerMixin playerExt, PlayerEntity player) {
        personalJukeboxTick(playerExt, player);
    }

    PositionedSoundInstance personalSoundInstance;
    private void personalJukeboxTick(PlayerMixin playerExt, PlayerEntity player) {
        Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
        if (trinkets.isEmpty()) {
            return;
        }
        List<Pair<SlotReference, ItemStack>> items = trinkets.get().getAllEquipped();
        boolean hadAMatch = false;
        for (int i=0; i < items.size(); i++) {
            ItemStack stack = items.get(i).getRight();
            if ((!stack.isEmpty()) && stack.getItem() instanceof PersonalDiscPlayer) {
                PersonalDiscPlayerInventory inventory = new PersonalDiscPlayerInventory(stack);
                if (inventory.hasAtLeastOneMusicDisc()) {
                    personalJukeboxTick2(playerExt, player, stack, inventory, i);
                    hadAMatch = true;
                }
            }
        }
        if (!hadAMatch) {
            if (personalSoundInstance != null) {
                MinecraftClient.getInstance().getSoundManager().stop(personalSoundInstance);
            }
        }
    }

    private void personalJukeboxTick2(PlayerMixin playerExt, PlayerEntity player, ItemStack stack, PersonalDiscPlayerInventory inventory, int slotIndex) {
        if (canPlayPersonalJukebox(player)) {
            PersonalDiscPlayerPropertyDelegate propDelegate = new PersonalDiscPlayerPropertyDelegate(stack);
            int playMode = propDelegate.getByName("playMode");
            int volume = propDelegate.getByName("musicVolume");

            if (playMode == 2) {
                MusicDiscItem disc = (MusicDiscItem)inventory.getRandomMusicDisc().getItem();
                personalJukeboxPlaySong(disc, volume, player);
                return;
            }

            int discSlot = propDelegate.getByName("currentTrack");
            if (!inventory.hasDiscOnSlot(discSlot)) {
                discSlot = inventory.getFirstValidSlot();
            }
            MusicDiscItem disc = (MusicDiscItem)inventory.getStack(discSlot).getItem();
            personalJukeboxPlaySong(disc, volume, player);

            if (playMode == 0) {
                discSlot = inventory.getNextMusicDiscSlotAfter(discSlot);
            }

            propDelegate.setByName("currentTrack", discSlot);
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(slotIndex);
            buf.writeInt(discSlot);
            ClientPlayNetworking.send(VoraciousScythes.UPDATE_TRACK_NUMBER_ID, buf);
        }
    }

    private boolean canPlayPersonalJukebox(PlayerEntity player) {
        if (personalSoundInstance == null) {
            return true;
        }
        return !MinecraftClient.getInstance().getSoundManager().isPlaying(personalSoundInstance);
    }

    private void personalJukeboxPlaySong(MusicDiscItem disc, int volume, PlayerEntity player) {
        boolean shouldMute = !player.getWorld().isClient || !player.isMainPlayer();
        float trueVolume = shouldMute ? 0 : ((float)volume)/100;
        Identifier id = disc.getSound().getId();
        SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();
        soundManager.stopSounds(null, SoundCategory.MUSIC);
        personalSoundInstance = new PositionedSoundInstance(id, SoundCategory.PLAYERS, trueVolume, 1, false, 0, SoundInstance.AttenuationType.NONE, 0, 0, 0, true);
        MinecraftClient.getInstance().getSoundManager().play(personalSoundInstance);
    }

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}
	
}

