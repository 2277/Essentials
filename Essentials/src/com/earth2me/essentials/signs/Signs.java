package com.earth2me.essentials.signs;

//This enum is used when checking to see what signs are enabled
public enum Signs {
    BALANCE(new SignBalance()),
    DISPOSAL(new SignDisposal()),
    ENCHANT(new SignEnchant()),
    FREE(new SignFree()),
    GAMEMODE(new SignGameMode()),
    HEAL(new SignHeal()),
    INFO(new SignInfo()),
    KIT(new SignKit()),
    PROTECTION(new SignProtection()),
    REPAIR(new SignRepair()),
    SPAWNMOB(new SignSpawnmob()),
    TIME(new SignTime()),
    WARP(new SignWarp()),
    WEATHER(new SignWeather());
    private final EssentialsSign sign;

    Signs(final EssentialsSign sign) {
        this.sign = sign;
    }

    public EssentialsSign getSign() {
        return sign;
    }
}
