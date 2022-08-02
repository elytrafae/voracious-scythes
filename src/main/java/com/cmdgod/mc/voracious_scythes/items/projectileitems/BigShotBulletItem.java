package com.cmdgod.mc.voracious_scythes.items.projectileitems;

import com.cmdgod.mc.voracious_scythes.VoraciousScythes;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;

public class BigShotBulletItem extends Item {

    public BigShotBulletItem() {
        super(new FabricItemSettings());
        Registry.register(Registry.ITEM, new Identifier(VoraciousScythes.MOD_NAMESPACE, "big_shot_bullet"), this);
    }
 
    

}
