package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundInstance.AttenuationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class BroomFlightAbility extends ScytheAbilityBase {

    public BroomFlightAbility() {
        super("Witch's Flight", 1200, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_flight_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 120; // 6 seconds.
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Fly in the direction you ");
        desc.add("want in for " + (((float)this.duration)/20) + " seconds.");
        this.changeDescription(desc);
    }

    PositionedSoundInstance flyingSoundInstance;
    PositionedSoundInstance liftoffSoundInstance;
    //ParticleEffect particleEffect = Registry.PARTICLE_TYPE.get(new Identifier("minecraft", "end_rod"));

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient && player.isMainPlayer()) {
            SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();
            Identifier id = new Identifier("minecraft:item.elytra.flying");
            flyingSoundInstance = new PositionedSoundInstance(id, SoundCategory.PLAYERS, (float) 0.75, 1, true, 0, AttenuationType.NONE, 0, 0, 0, true);
            soundManager.play(flyingSoundInstance);

            Identifier id2 = new Identifier("entity.firework_rocket.launch");
            liftoffSoundInstance = new PositionedSoundInstance(id2, SoundCategory.PLAYERS, 1, 1, false, 0, AttenuationType.NONE, 0, 0, 0, true);
            soundManager.play(liftoffSoundInstance);
        }
    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        Vec3d rotation = player.getRotationVector().normalize();
        player.fallDistance = 0;
        player.setVelocity(rotation.multiply(1.2));
        //player.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient && player.isMainPlayer()) {
            SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();
            soundManager.stop(flyingSoundInstance);
            soundManager.stop(liftoffSoundInstance);
        }
    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
