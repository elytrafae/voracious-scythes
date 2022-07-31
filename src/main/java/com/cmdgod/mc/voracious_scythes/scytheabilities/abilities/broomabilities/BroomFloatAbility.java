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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class BroomFloatAbility extends ScytheAbilityBase {

    public BroomFloatAbility() {
        super("Apprentice's Float", 200, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_float_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 0; // Instant
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Passive: Slow Falling II unless Sneaking.");
        desc.add("Activate to launch yourself upwards.");
        this.changeDescription(desc);
    }

    PositionedSoundInstance liftoffSoundInstance;

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient && player.isMainPlayer()) {
            SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();

            Identifier id2 = new Identifier("entity.firework_rocket.launch");
            liftoffSoundInstance = new PositionedSoundInstance(id2, SoundCategory.PLAYERS, 1, 1, false, 0, AttenuationType.NONE, 0, 0, 0, true);
            soundManager.play(liftoffSoundInstance);
        }
    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        Vec3d velocity = player.getVelocity();
        Vec3d newVelocity = new Vec3d(velocity.x, 2, velocity.z);
        player.fallDistance = 0;
        player.setVelocity(newVelocity);
    }

    @Override
    public void passiveTick(PlayerEntity player) {
        if (player.isSneaking()) {
            player.removeStatusEffect(StatusEffects.SLOW_FALLING);
            return;
        }
        StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 1, true, false, true);
        player.addStatusEffect(instance);
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
