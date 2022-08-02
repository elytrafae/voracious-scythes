package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.entities.projectiles.BigShotBulletEntity;
import com.cmdgod.mc.voracious_scythes.mixinextensions.PlayerExtension;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager.CooldownType;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundInstance.AttenuationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class BroomBigShotAbility extends ScytheAbilityBase {

    public static final int BIG_SHOT_DAMAGE = 30;

    public BroomBigShotAbility() {
        super("BIG SHOT!!!", 4 * 60 * 20, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_big_shot_ability"));
        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 0; // Instant
        this.preFireDuration = 0;
        this.postFireDuration = 0;
        this.charges = 2;
        this.cooldownType = CooldownType.ALL_AT_ONCE;

        desc.add("If you're in the dark, shoot");
        desc.add("a piercing BIG SHOT bullet,");
        desc.add("dealing " + BIG_SHOT_DAMAGE + " damage!");
        this.changeDescription(desc);
    }

    PositionedSoundInstance soundInstance;

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();
        //player.sendMessage(Text.of(VoraciousScythes.returnServerOrClientString(world)), false);
        if (world.isClient) {
            return; // The darkness check sadly fails on the client :(
        }
        if (!VoraciousScythes.isInDarkCondition(player)) {
            PlayerExtension playeExt = (PlayerExtension)player;
            playeExt.getCdManager().reduceCooldownFor(this, this.cooldown);
            player.sendMessage(new TranslatableText("item.voracious_scythes.big_shot_broom_head.light_fail").setStyle(Style.EMPTY.withColor(Formatting.RED)), true);
            player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.PLAYERS, 1F, 0.5F);
            /*
            if (world.isClient && player.isMainPlayer()) {
                Identifier id = new Identifier("block.respawn_anchor.deplete");
                soundInstance = new PositionedSoundInstance(id, SoundCategory.PLAYERS, 1, 0.5F, false, 0, AttenuationType.NONE, 0, 0, 0, true);
                soundManager.play(soundInstance);
            }
            */
            return;
        }
        //player.sendMessage(Text.of("Now's your chance to BE SHOT!"), false);
        player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.PLAYERS, 1F, 0.5F);
        Vec3d eyePos = player.getEyePos();
        BigShotBulletEntity shot = new BigShotBulletEntity(world, eyePos.x, eyePos.y, eyePos.z);
        shot.setOwner(player);
        shot.setVelocity(player.getRotationVector().normalize().multiply(0.8));
        world.spawnEntity(shot);
        /*
        if (world.isClient && player.isMainPlayer()) {
            Identifier id = new Identifier("block.respawn_anchor.charge");
            soundInstance = new PositionedSoundInstance(id, SoundCategory.PLAYERS, 1, 1.5F, false, 0, AttenuationType.NONE, 0, 0, 0, true);
            soundManager.play(soundInstance);
        }
        */
    }

    @Override
    public void passiveTick(PlayerEntity player) {

    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
