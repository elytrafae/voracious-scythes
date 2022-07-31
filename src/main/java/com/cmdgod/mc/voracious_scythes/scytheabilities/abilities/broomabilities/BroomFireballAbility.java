package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance.AttenuationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BroomFireballAbility extends ScytheAbilityBase {

    public static final int FIREBALL_SHOOT_COUNT = 3;
    public static final int FIREBALL_SHOOT_INTERVAL = 8;

    public BroomFireballAbility() {
        super("Fireball!", 1500, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_fireball_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = (FIREBALL_SHOOT_COUNT-1)*FIREBALL_SHOOT_INTERVAL; // Because this will procc on tick 0 as well
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Shoot " + FIREBALL_SHOOT_COUNT + " Fireballs in rapid succession.");
        this.changeDescription(desc);
    }

    PositionedSoundInstance flyingSoundInstance;
    PositionedSoundInstance liftoffSoundInstance;

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        int currentTick = tick - entry.getStartTick();
        if (currentTick % FIREBALL_SHOOT_INTERVAL == 0) {
            if (world.isClient) {
                Identifier id = new Identifier("minecraft:entity.blaze.shoot");
                flyingSoundInstance = new PositionedSoundInstance(id, SoundCategory.PLAYERS, (float) 1, (float) 0.5, false, 0, AttenuationType.NONE, 0, 0, 0, true);
                MinecraftClient.getInstance().getSoundManager().play(flyingSoundInstance);
            } else {
                Vec3d rotation = player.getRotationVector().normalize().multiply(6);
                Vec3d eyePos = player.getEyePos();
                SmallFireballEntity fireballEntity = new SmallFireballEntity(world, eyePos.x, eyePos.y, eyePos.z, rotation.x, rotation.y, rotation.z);
                fireballEntity.setOwner(player);
                //SmallFireballEntity fireballEntity = new SmallFireballEntity(world, player, rotation.x, rotation.y, rotation.z);
                world.spawnEntity(fireballEntity);
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
