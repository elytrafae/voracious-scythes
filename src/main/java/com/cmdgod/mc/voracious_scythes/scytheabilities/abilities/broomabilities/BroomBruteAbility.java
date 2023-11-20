package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundInstance.AttenuationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;


public class BroomBruteAbility extends ScytheAbilityBase {

    public static final int MIN_HUNGER = 8;
    public static final int STRENGTH_DURATION = 100;

    public BroomBruteAbility() {
        super("Brutish Strength", 200, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_brute_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 0; // Instant
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Passive: Cannot fall below " + MIN_HUNGER + " Hunger Points.");
        desc.add("Activate to gain Strength III for " + (((float)STRENGTH_DURATION)/20) + " seconds.");
        this.changeDescription(desc);
    }

    PositionedSoundInstance strengthSoundInstance;

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient && player.isMainPlayer()) {
            SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();

            Identifier id2 = new Identifier("entity.ravager.roar");
            strengthSoundInstance = new PositionedSoundInstance(id2, SoundCategory.PLAYERS, 1, 1, Random.create(), false, 0, AttenuationType.NONE, 0, 0, 0, true);
            soundManager.play(strengthSoundInstance);
        }
    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.STRENGTH, STRENGTH_DURATION, 2, true, true, true);
        player.addStatusEffect(instance);
    }

    @Override
    public void passiveTick(PlayerEntity player) {
        HungerManager hungerManager = player.getHungerManager();
        if (hungerManager.getFoodLevel() < MIN_HUNGER) {
            hungerManager.setFoodLevel(MIN_HUNGER);
        }
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
