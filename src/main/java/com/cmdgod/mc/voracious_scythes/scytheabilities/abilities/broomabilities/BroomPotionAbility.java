package com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.broomabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BroomPotionAbility extends ScytheAbilityBase {

    public static final int EFFECT_COUNT = 2;

    public static List<StatusEffect> POSSIBLE_EFFECTS = List.of(
            StatusEffects.BLINDNESS, 
            StatusEffects.MINING_FATIGUE,
            StatusEffects.SLOWNESS, 
            StatusEffects.WEAKNESS, 
            StatusEffects.LEVITATION,
            StatusEffects.POISON, 
            StatusEffects.INSTANT_DAMAGE, 
            StatusEffects.WITHER
    );
    //private static ArrayList<ItemStack> POSSIBLE_POTIONS = new ArrayList<ItemStack>();

    private static ItemStack createPotionWithEffects(ArrayList<StatusEffect> effects) {
        ItemStack stack = new ItemStack(Items.SPLASH_POTION);
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtList effectsList = new NbtList();
        int color = 0;
        for (StatusEffect effect : effects) {
            NbtCompound nbtEffect = new NbtCompound();
            nbtEffect.putInt("Id", StatusEffect.getRawId(effect));
            nbtEffect.putInt("Amplifier", 1);
            nbtEffect.putInt("Duration", effect.isInstant() ? 1 : 600);
            effectsList.add(nbtEffect);
            color += effect.getColor();
        }
        color /= effects.size();
        nbt.put("CustomPotionEffects", effectsList);
        nbt.putInt("CustomPotionColor", color);
        return stack;
    }

    public BroomPotionAbility() {
        super("Potion Toss", 600, new ArrayList<String>(), new Identifier(VoraciousScythes.MOD_NAMESPACE, "broom_potion_ability"));

        ArrayList<String> desc = new ArrayList<String>();

        this.duration = 0; // Pretty much instant.
        this.preFireDuration = 0;
        this.postFireDuration = 0;

        desc.add("Toss a potion with " + EFFECT_COUNT);
        desc.add("different random effects");
        desc.add("forward at double speed!");
        desc.add("Possibilities: ");
        POSSIBLE_EFFECTS.forEach((effect) -> {
            desc.add("- " + (new TranslatableText(effect.getTranslationKey()).getString()));
        });
        this.changeDescription(desc);
    }

    @Override
    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient) {
            player.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, (float)0.75, (float)1.1);
            return;
        }
        Random rand = new Random();
        ArrayList<StatusEffect> effectsCopy = new ArrayList<StatusEffect>(POSSIBLE_EFFECTS);
        ArrayList<StatusEffect> chosenEffects = new ArrayList<StatusEffect>();
        for (int i=1; i <= EFFECT_COUNT; i++) {
            StatusEffect effect = effectsCopy.get(rand.nextInt(effectsCopy.size()));
            //player.sendMessage(Text.of("Chosen effect #" + i + " : " + effect.getName().getString()), false);
            chosenEffects.add(effect);
            effectsCopy.remove(effect);
        }
        ItemStack potion = createPotionWithEffects(chosenEffects);

        PotionEntity potionEntity = new PotionEntity(world, player);
        potionEntity.setItem(potion);
        potionEntity.setVelocity(player, player.getPitch(), player.getYaw(), -20.0f, 1f, 1.0f);
        world.spawnEntity(potionEntity);

        

        //player.sendMessage(Text.of("SPAWNED POTION! " + itemStack.toString() + " || " + nbt.toString()), false);
    }

    @Override
    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    @Override
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
        
    }
    
}
