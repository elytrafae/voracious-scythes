package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.entities.projectiles.StarBulletEntity;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance.AttenuationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BroomStarAbility extends ScytheAbilityBase {

    public static final int STAR_SHOOT_COUNT = 30;
    public static final int STAR_SHOOT_INTERVAL = 3;

    public BroomStarAbility() {
        super("Omnidirectional Stars", 1500, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_star_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = (STAR_SHOOT_COUNT-1)*STAR_SHOOT_INTERVAL; // Because this will procc on tick 0 as well
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Shoot Stars like crazy for " + ((double)this.duration/20) + " seconds.");
        desc.add("If you're in the dark, shoot");
        desc.add("more stars in 4 more directions.");
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
        if (currentTick % STAR_SHOOT_INTERVAL == 0) {
            if (world.isClient) {
                Identifier id = new Identifier("minecraft:entity.blaze.shoot");
                flyingSoundInstance = new PositionedSoundInstance(id, SoundCategory.PLAYERS, (float) 1, (float) 0.5, Random.create(), false, 0, AttenuationType.NONE, 0, 0, 0, true);
                MinecraftClient.getInstance().getSoundManager().play(flyingSoundInstance);
            } else {
                Vec3d rotation = player.getRotationVector().normalize();
                Vec3d mainProjectileVec = rotation.multiply(2);
                Vec3d eyePos = player.getEyePos();
                StarBulletEntity starEntity = new StarBulletEntity(world, eyePos.x, eyePos.y, eyePos.z);
                starEntity.setOwner(player);
                starEntity.setVelocity(mainProjectileVec);
                starEntity.setLifespan(360);
                world.spawnEntity(starEntity);
                if (VoraciousScythes.isInDarkCondition(player)) {
                    Vec2f rotation2d = new Vec2f((float)rotation.x, (float)rotation.z);
                    double sideRot = Math.toDegrees(Math.asin(rotation2d.x));
                    //player.sendMessage(Text.of("ROTATION: " + sideRot), false);
                    double angleDistance = 76;
                    for (int i=1; i <= 4; i++) {
                        double newRot = sideRot + (i * angleDistance);
                        Vec3d sideProjectileVec = new Vec3d(Math.sin(Math.toRadians(newRot)), 0, Math.cos(Math.toRadians(newRot)));
                        StarBulletEntity sideStarEntity = new StarBulletEntity(world, eyePos.x, eyePos.y, eyePos.z);
                        sideStarEntity.setOwner(player);
                        sideStarEntity.setVelocity(sideProjectileVec);
                        world.spawnEntity(sideStarEntity);
                    }
                }
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

