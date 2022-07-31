package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;

import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BroomTeleportAbility extends ScytheAbilityBase {

    public BroomTeleportAbility() {
        super("Blink", 200, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_blink_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 0; // Pretty much instant.
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Teleport a short distance forward.");
        this.changeDescription(desc);
    }

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        Vec3d rotation = player.getRotationVector().normalize();
        player.fallDistance = 0;
        player.move(MovementType.PLAYER, rotation.multiply(10));
        World world = player.getWorld();
        if (world.isClient) {
            player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, (float)0.75, (float)1.1);
        }
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
