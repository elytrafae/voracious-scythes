package com.cmdgod.mc.voracious_scythes.settingsclasses;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SetItemPredicate {

    public enum SetItemLogic {
        AND, OR
    }
    
    private List<Identifier> identifiers;
    private List<SetItemPredicate> children;
    private SetItemLogic logic = SetItemLogic.AND;

    private boolean evaluate(PlayerEntity player) {
        boolean isOR = logic == SetItemLogic.OR;
        for (int i=0; i < identifiers.size(); i++) {
            if (doesPlayerHaveThisEquipped(player, identifiers.get(i)) == isOR) {
                return isOR;
            }
        }
        for (int i=0; i < children.size(); i++) {
            if (children.get(i).evaluate(player) == isOR) {
                return isOR;
            }
        }
        return !isOR;
    }

    private boolean doesPlayerHaveThisEquipped(PlayerEntity player, Identifier identifier) {
        Item item = Registry.ITEM.get(identifier);

        // For Trinkets
        if (item instanceof TrinketItem) {
            Optional<TrinketComponent> opt = TrinketsApi.getTrinketComponent(player);
            if (opt.isEmpty()) {return false;}
            return opt.get().isEquipped(item);
        }

        // For Vanilla Armor
        if (item instanceof Wearable) {
            Iterator<ItemStack> iterator = player.getArmorItems().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getItem() == item) {
                    return true;
                }
            }
            return false;
        }
        
        // For Weapons/Shields
        return player.getOffHandStack().getItem() == item || player.getMainHandStack().getItem() == item;
    }

}
