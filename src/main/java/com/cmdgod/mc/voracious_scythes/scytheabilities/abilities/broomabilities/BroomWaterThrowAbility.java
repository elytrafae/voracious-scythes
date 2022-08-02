package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.entities.projectiles.WaterBombEntity;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BroomWaterThrowAbility extends ScytheAbilityBase {

    //private static ArrayList<ItemStack> POSSIBLE_POTIONS = new ArrayList<ItemStack>();

    public BroomWaterThrowAbility() {
        super("Water Bomb", 30 * 20, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_water_throw_ability"));

        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 0; // Pretty much instant.
        this.preFireDuration = 0;
        this.postFireDuration = 0;
        this.charges = 2;

        desc.add("Toss a Water Bomb forward,");
        desc.add("dealing 5 Damage to all enemies");
        desc.add("in its radius and giving them");
        desc.add("Slowness III for 15 seconds.");
        this.changeDescription(desc);
    }

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient) {
            return;
        }
        player.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, (float)0.75, (float)1.1);
        Vec3d pos = player.getEyePos();
        WaterBombEntity waterBomb = new WaterBombEntity(world, pos.x, pos.y, pos.z);
        waterBomb.setOwner(player);
        waterBomb.setVelocity(player, player.getPitch(), player.getYaw(), -20.0f, 1f, 1.0f);
        world.spawnEntity(waterBomb);
        
        //player.sendMessage(Text.of("SPAWNED POTION! " + itemStack.toString() + " || " + nbt.toString()), false);
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
