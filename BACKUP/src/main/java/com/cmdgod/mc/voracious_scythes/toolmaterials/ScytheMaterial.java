package com.cmdgod.mc.voracious_scythes.toolmaterials;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ScytheMaterial implements ToolMaterial {

    @Override
    public float getAttackDamage() {
        return -1;
    }

    @Override
    public int getDurability() {
        return 100;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 3;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.COMMAND_BLOCK);
    }
    
}
