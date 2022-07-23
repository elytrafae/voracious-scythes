package com.cmdgod.mc.voracious_scythes.items;

import java.util.Collection;
import java.util.List;

import com.cmdgod.mc.voracious_scythes.mixinextensions.PlayerExtension;
import com.cmdgod.mc.voracious_scythes.mixins.PlayerMixin;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.ScytheAbilityBase;
import com.cmdgod.mc.voracious_scythes.scytheabilities.abilities.HeavyHit;
import com.cmdgod.mc.voracious_scythes.toolmaterials.ScytheMaterial;
import com.google.common.base.Predicate;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext.Result;
import net.minecraft.block.Material;
import net.minecraft.block.entity.StructureBlockBlockEntity.Action;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ScytheBase extends SwordItem {

    public float reachDistance = 5;
    public double arc = Math.PI/2;
    //public ScytheAbilityBase ability = new HeavyHit();

    private class entityPredicate implements Predicate<Entity> {

        @Override
        public boolean apply(Entity entity) {
            return true;
        }

    }

    public static boolean isTwoHandedViolated(ItemStack mainStack, ItemStack offStack) {
        if ((mainStack.getItem() instanceof ScytheBase) && !offStack.isEmpty()) {
            return true;
        }
        if ((mainStack.getItem() instanceof ScytheBase) && !offStack.isEmpty()) {
            return true;
        }
        return false;
    }

    public ScytheBase(int damage, int durability, @Nullable Float attack_speed) {
        super(new ScytheMaterial(), damage, attack_speed != null ? attack_speed : -2.0F, new Item.Settings().group(ItemGroup.COMBAT).maxDamage(durability));
    }

    final static public Style BASIC_DESC_STYLE = Style.EMPTY.withColor(Formatting.GRAY);
    final static public Style NO_ABILITY_STYLE = Style.EMPTY.withColor(Formatting.RED);
    final static public Style ABILITY_NAME_STYLE = Style.EMPTY.withColor(Formatting.DARK_BLUE).withBold(true);
    final static public Style ABILITY_CD_STYLE = Style.EMPTY.withColor(Formatting.DARK_PURPLE);
    final static public Style ABILITY_DESC_STYLE = Style.EMPTY.withColor(Formatting.BLUE);

    public ScytheAbilityBase getAbilityForStack(ItemStack itemStack) {
        String id = itemStack.getNbt().getString("CMDGod_ScytheAbility");
        if (id == null || id.equals("")) {
            return null;
        }
        Identifier identifier = new Identifier(id);
        return ScytheAbilityBase.ABILITY_REGISTRY.get(identifier);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        ScytheAbilityBase ability = getAbilityForStack(itemStack);
        tooltip.add(Text.of("A two-handed weapon that always").getWithStyle(BASIC_DESC_STYLE).get(0));
        tooltip.add(Text.of("does sweep attacks in a large").getWithStyle(BASIC_DESC_STYLE).get(0));
        tooltip.add(Text.of("area in front of the user.").getWithStyle(BASIC_DESC_STYLE).get(0));
        tooltip.add(Text.of(" "));
        if (ability == null || ability.name.toString() == "NOABILITY") {
            tooltip.add(Text.of("Use a Scything Table to give this item").getWithStyle(NO_ABILITY_STYLE).get(0));
            tooltip.add(Text.of("a unique ability or to upgrade it.").getWithStyle(NO_ABILITY_STYLE).get(0));
        } else {
            tooltip.add(ability.name);
            tooltip.add(Text.of("Cooldown: " + (((float)ability.cooldown) / 20) + " seconds").getWithStyle(ABILITY_CD_STYLE).get(0));
            tooltip.addAll(ability.description);
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        /*
        player.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        HitResult hitResult = player.raycast(10, 0.0F, false);
        if (hitResult.getType().equals(HitResult.Type.ENTITY)) {
            EntityHitResult entitryHitResult = (EntityHitResult)hitResult;
            Entity target = entitryHitResult.getEntity();
            player.tryAttack( target );
            player.sendMessage(Text.of("Hit a " + target.getName().toString() + "!"), false);
        } else {
            player.sendMessage(Text.of("Missed! " + (hitResult.getType().equals(HitResult.Type.BLOCK) ? "HIT BLOCK" : "COMPLETELY MISSED")), false);
        }
        */
        ItemStack itemStack = player.getStackInHand(hand);
        ScytheAbilityBase ability = getAbilityForStack(itemStack);
        PlayerExtension playerExt = (PlayerExtension)player;
        AbilityCooldownManager cdManager = playerExt.getCdManager();
        AbilityDurationManager durationManager = playerExt.getAbilityDurationManager();
        if (ScytheBase.isTwoHandedViolated(player.getMainHandStack(), player.getOffHandStack())) {
            player.sendMessage(Text.of("One hand is not enough to use Scythes!").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0), true);
            return new TypedActionResult<ItemStack>(ActionResult.FAIL, player.getStackInHand(hand));
        }
        if (ability == null) {
            player.sendMessage(Text.of("This scythe does not have an ability!").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0), true);
            return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
        }
        /*
        if (ability != null) {
            player.sendMessage(Text.of(cdManager.getChargesFor(ability) + " || " + cdManager.getCurrentCooldownFor(ability) + " || " + ability.cooldown), false);
        }
        */
        if (!cdManager.canUse(ability)) {
            player.sendMessage(Text.of("This ability is on cooldown!").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0), true);
            return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
        }
        if (!ability.simultaneousChargesAllowed && durationManager.doesAbilityAlreadyHaveEntry(ability)) {
            player.sendMessage(Text.of("This ability is already in use!").getWithStyle(Style.EMPTY.withColor(Formatting.RED)).get(0), true);
            return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
        }
        cdManager.useChargeFor(ability);
        durationManager.startAbilityDuration(ability, player, hand, itemStack);
            // ability.activeAbility(world, player, hand, this, itemStack);
        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    public void sweep(PlayerEntity player, World world, int damage) {
        Vec3d rotation = player.getRotationVector();
		Vec3d eyePos = player.getEyePos();

        Vec2f eyePlane = new Vec2f( (float) eyePos.x, (float) eyePos.z);
        Vec2f eyeRot2d = new Vec2f( (float) rotation.x, (float) rotation.z);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, Box.of(eyePos, reachDistance*2, reachDistance*2, reachDistance*2), new entityPredicate());
        for (var i=0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            Vec3d entityPos = entity.getPos();
            Vec2f entityVector2d = new Vec2f( (float) (entityPos.x - eyePlane.x), (float) (entityPos.z - eyePlane.y)).normalize(); // The entity's position, relative to the player's. Will be compared to the player's rotation as two vectors to find the angle between the two, and this the angular offset from the crosshair to the entity.
            double angle = Math.acos(eyeRot2d.dot(entityVector2d) / ( eyeRot2d.length() * entityVector2d.length()) );
            if (angle >= -arc && angle <= arc) {
                entity.damage(DamageSource.player(player), damage);
            }
        }
        player.spawnSweepAttackParticles();
    }

    public ActionResult attackSweep(PlayerEntity player, World world, ItemStack stack) {

        if (player.getAttackCooldownProgress(1) >= 0.9) { // I know that 1 is not really the best there, but I only need this to check whether or not the cooldown has been completed, soooo . . .
            // TODO: Make this dynamic, relative to the weapon's damage stat!
            this.sweep(player, world, 5);
            player.resetLastAttackedTicks();
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
    
}
