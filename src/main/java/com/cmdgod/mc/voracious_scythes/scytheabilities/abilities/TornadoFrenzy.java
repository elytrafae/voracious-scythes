package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities;

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
import net.minecraft.world.World;

public class TornadoFrenzy extends ScytheAbilityBase {
    
    private final int FREQUENCY = 4; // 0.2 seconds.
    private final int DAMAGE_PER_TICK = 1;
    private final int DURATION_INCREASE_ON_KILL = 20;
    private final int RADIUS = 6;

    private class entityPredicate implements Predicate<Entity> {

        @Override
        public boolean apply(Entity entity) {
            return true;
        }

    }

    public TornadoFrenzy() {
        super("Tornado Frenzy", 400, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "tornado_frenzy_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 60; // 3 seconds.
        this.preFireDuration = 5;
        this.postFireDuration = 5;

        desc.add("Damage every enemy around you ");
        desc.add("in a " + RADIUS + "-block radius for " + DAMAGE_PER_TICK + " Damage");
        desc.add("every " + (((float)FREQUENCY)/20) + " seconds for " + (((float)this.duration)/20) + " seconds.");
        desc.add("Every time this ability kills,");
        desc.add("increase the active duration");
        desc.add("by " + (((float)DURATION_INCREASE_ON_KILL)/20) + " second.");
        this.changeDescription(desc);
    }

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        /*
        int damage;
        stack.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach( (attribute, modifier) -> {
            attribute.
        });
        */
        int currentTick = tick - entry.getStartTick();
        if (currentTick % FREQUENCY == 0) {
            World world = player.getWorld();
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, Box.of(player.getPos(), RADIUS*2, player.getHeight(), RADIUS*2), new entityPredicate());
            for (var i=0; i < entities.size(); i++) {
                LivingEntity entity = entities.get(i);
                entity.damage(DamageSource.player(player), DAMAGE_PER_TICK);
                if (entity.isDead()) {
                    entry.modifyDurationBy(DURATION_INCREASE_ON_KILL);
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
