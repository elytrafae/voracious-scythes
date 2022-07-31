package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BroomBlockAbility extends ScytheAbilityBase {

    public static final int RESISTANCE_DURATION = 40;
    public static final int REGENERATION_INTERVAL = 40;

    public BroomBlockAbility() {
        super("On Guard", 400, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_block_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 0; // Instant
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Passive: Heal 1 HP every " + (REGENERATION_INTERVAL/20) + " seconds.");
        desc.add("Activate to gain Resistance V for " + (RESISTANCE_DURATION/20) + " seconds.");
        this.changeDescription(desc);
    }

    //PositionedSoundInstance blockSoundInstance;
    //PositionedSoundInstance healSoundInstance;

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient && player.isMainPlayer()) {
            /*
            SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();

            Identifier id2 = new Identifier("entity.firework_rocket.launch");
            liftoffSoundInstance = new PositionedSoundInstance(id2, SoundCategory.PLAYERS, 1, 1, false, 0, AttenuationType.NONE, 0, 0, 0, true);
            soundManager.play(liftoffSoundInstance);
            */
            player.playSound(SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, (float)1, (float)1.1);
        }
    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.RESISTANCE, RESISTANCE_DURATION, 4, true, true, true);
        player.addStatusEffect(instance);
    }

    @Override
    public void passiveTick(PlayerEntity player) {
        //StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 1, true, false, true);
        //player.addStatusEffect(instance);
        if (AbilityDurationManager.getTickFor(player) % REGENERATION_INTERVAL == 0) {
            if (player.getHealth() < player.getMaxHealth()) {
                World world = player.getWorld();
                if (world.isClient) {
                    player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, (float)0.5, (float)1.5);
                }
                player.heal(1);
            } 
        }
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
