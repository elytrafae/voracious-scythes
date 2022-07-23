package com.cmdgod.mc.voracious_scythes.mixinextensions;

import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityCooldownManager;
import com.cmdgod.mc.voracious_scythes.scytheabilities.AbilityDurationManager;

public interface PlayerExtension {

    public AbilityCooldownManager getCdManager();
    public AbilityDurationManager getAbilityDurationManager();
    
}
