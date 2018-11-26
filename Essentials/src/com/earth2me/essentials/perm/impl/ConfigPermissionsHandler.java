package com.earth2me.essentials.perm.impl;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.ess3.api.IEssentials;


public class ConfigPermissionsHandler extends SuperpermsHandler {
    private final transient IEssentials ess;

    public ConfigPermissionsHandler(final Plugin ess) {
        this.ess = (IEssentials) ess;
    }

    @Override
    public boolean canBuild(final Player base, final String group) {
        return true;
    }

    @Override
    public boolean hasPermission(final Player base, final String node) {
        final String[] cmds = node.split("\\.", 2);
        return ess.getSettings().isPlayerCommand(cmds[cmds.length - 1]) || super.hasPermission(base, node);
    }

    @Override
    public boolean tryProvider() {
        return true;
    }
}
