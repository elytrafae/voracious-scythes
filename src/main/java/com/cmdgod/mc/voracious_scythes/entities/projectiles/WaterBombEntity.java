package com.cmdgod.mc.voracious_scythes.entities.projectiles;

import java.util.List;

import com.cmdgod.mc.voracious_scythes.EntitySpawnPacket;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder.Living;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WaterBombEntity extends ThrownItemEntity {

    private int lifespan = 40;

    public WaterBombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
 
	public WaterBombEntity(World world, LivingEntity owner) {
		super(VoraciousScythes.WaterBombEntityType, owner, world); // null will be changed later
	}
 
	public WaterBombEntity(World world, double x, double y, double z) {
		super(VoraciousScythes.WaterBombEntityType, x, y, z, world); // null will be changed later
	}

    @Override
	public Packet createSpawnPacket() {
		return EntitySpawnPacket.create(this, VoraciousScythes.ENTITY_SPAWN_PACKET_ID);
	}
 
	@Override
	protected Item getDefaultItem() {
		return Items.POTION; // We will configure this later, once we have created the ProjectileItem.
	}

    @Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	}
 
	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();
 
			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
 
	}
 
	protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        applySplash();
		super.onEntityHit(entityHitResult);
	}

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        applySplash();
        super.onBlockHit(blockHitResult);
    }

    private void applySplash() {
        if (this.world.isClient) {
            return;
        }
        Entity owner = this.getOwner();
        if (owner instanceof PlayerEntity) {
            playImpactSoundsTo((PlayerEntity)owner);
        }
        Box box = this.getBoundingBox().expand(8.0, 4.0, 8.0); // Double the range of a Splash Potion
        List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            DamageSource source = DamageSource.DROWN;
            if (owner != null && owner instanceof LivingEntity) {
                source = DamageSource.mob((LivingEntity)owner);
                if (owner instanceof PlayerEntity) {
                    source = DamageSource.player((PlayerEntity)owner);
                }
            }
            for (LivingEntity livingEntity : list) {
                if (livingEntity == owner) { // Do not hit the owner!
                    continue;
                }
                livingEntity.damage(source, 5);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 15 * 20, 2, false, true), owner);
                if (livingEntity instanceof PlayerEntity) {
                    playImpactSoundsTo((PlayerEntity)livingEntity);
                }
            }
        }
    }

    private void playImpactSoundsTo(PlayerEntity player) {
        player.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 0.5F, 1);
        player.playSound(SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.PLAYERS, 1F, 0.8F);
    }
 
	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) { // checks if the world is client
            this.world.sendEntityStatus(this, (byte)3);
            this.kill();
		}
 
	}


    public int getLifespan() {
        return this.lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    
}
