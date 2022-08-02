package com.cmdgod.mc.voracious_scythes.entities.projectiles;

import com.cmdgod.mc.voracious_scythes.EntitySpawnPacket;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StarBulletEntity extends ThrownItemEntity {

    private int lifespan = 40;

    public StarBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
 
	public StarBulletEntity(World world, LivingEntity owner) {
		super(VoraciousScythes.StarBulletEntityType, owner, world); // null will be changed later
	}
 
	public StarBulletEntity(World world, double x, double y, double z) {
		super(VoraciousScythes.StarBulletEntityType, x, y, z, world); // null will be changed later
	}

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public float getGravity() {
        return 0;
    }

    @Override
    public void tick() {
        super.tick();
        lifespan--;
        if (lifespan <= 0) {
            this.kill();
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Lifespan", lifespan);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        lifespan = nbt.getInt("Lifespan");
    }

    @Override
	public Packet createSpawnPacket() {
		return EntitySpawnPacket.create(this, VoraciousScythes.ENTITY_SPAWN_PACKET_ID);
	}
 
	@Override
	protected Item getDefaultItem() {
		return VoraciousScythes.YELLOW_STAR_BULLET_ITEM; // We will configure this later, once we have created the ProjectileItem.
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
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)5); // deals damage
 
		if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 20 * 5, 0)));
            livingEntity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.5F, 0.7F);
            /*
			livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
			livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
			livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 1))); // applies a status effect
			livingEntity.playSound(SoundEvents.AMBIENT_CAVE, 2F, 1F); // plays a sound for the entity hit only
            */
		}
	}
 
	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) { // checks if the world is client
            /* I CHANGED MY MIND ABOUT THE PIERCING. IT MIGHT MAKE THIS BROKEN . . .
            if (!(hitResult.getType() == HitResult.Type.ENTITY)) { // Make it piercing!
                this.world.sendEntityStatus(this, (byte)3); // particle?
                this.kill(); // kills the projectile
            }
            */
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
