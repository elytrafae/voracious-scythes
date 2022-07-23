package com.cmdgod.mc.voracious_scythes.scytheabilities;

import java.util.ArrayList;
import java.util.Collection;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;
import com.cmdgod.mc.voracious_scythes.items.ScytheBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager.CooldownType;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager.PublicAbilityDurationEntry;
import com.google.common.collect.ImmutableCollection;
import com.mojang.datafixers.types.templates.List;
import com.mojang.serialization.Lifecycle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ScytheAbilityBase {

    public final static DefaultedRegistry<ScytheAbilityBase> ABILITY_REGISTRY = new DefaultedRegistry<ScytheAbilityBase>(VoraciousScythes.MOD_NAMESPACE + ":noid", RegistryKey.ofRegistry(new Identifier("cmdgod_scythe_ability")), Lifecycle.experimental());

    public Text name = Text.of("NOABILITY");
    public int cooldown; // Measured in ticks!
    public int preFireDuration = 0; // Measured in ticks!
    public int duration = 0; // Measured in ticks!
    public int postFireDuration = 0; // Measured in ticks!
    public boolean simultaneousChargesAllowed = false; // Whether or not multiple charges being active on top of each-other is allowed.
    public boolean chargeWhileInUse = false; // Whether or not an ability should be able to charge while it's in use.
    public int charges;
    public ArrayList<Text> description;
    public Identifier id = new Identifier(VoraciousScythes.MOD_NAMESPACE, "empty_ability");
    public CooldownType cooldownType = CooldownType.PER_CHARGE;

    public static void registerAbility(ScytheAbilityBase ability) {
        if (!ScytheAbilityBase.ABILITY_REGISTRY.containsId(ability.id)) {
            DefaultedRegistry.register(ScytheAbilityBase.ABILITY_REGISTRY, ability.id, ability);
        }
    }

    public ScytheAbilityBase(String name, int cooldown, int charges, ArrayList<String> description, Identifier id) {
        construct(name, cooldown, charges, description, id);
    }

    public ScytheAbilityBase(String name, int cooldown, ArrayList<String> description, Identifier id) {
        construct(name, cooldown, 1, description, id);
    }

    private void construct(String name, int cooldown, int charges, ArrayList<String> description, Identifier id) {
        this.name = Text.of(name).getWithStyle(ScytheBase.ABILITY_NAME_STYLE).get(0);
        this.cooldown = cooldown;
        this.charges = charges;

        changeDescription(description);
        this.id = id;
    }

    public void modifyStackBeforeUse(PlayerEntity player, Hand hand, ItemStack stack) {

    }
    
    public void globalActiveAbilityTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {
    }

    public void preFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    public void activeTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    public void postFireTick(PublicAbilityDurationEntry entry, int tick, PlayerEntity player) {

    }

    public void changeDescription(ArrayList<String> desc) {
        description = new ArrayList<Text>();
        for (int i=0; i < desc.size(); i++) {
            this.description.add(Text.of(desc.get(i)).getWithStyle(ScytheBase.ABILITY_DESC_STYLE).get(0));
        }
    }

}
