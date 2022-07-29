package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import java.util.List;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BroomFlightAbility extends ScytheAbilityBase {

    private class entityPredicate implements Predicate<Entity> {

        @Override
        public boolean apply(Entity entity) {
            return true;
        }

    }

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

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        Vec3d rotation = player.getRotationVector().normalize();
        rotation.multiply(2);
        player.setVelocity(rotation);
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
