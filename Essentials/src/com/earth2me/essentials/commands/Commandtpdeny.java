package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import org.bukkit.Server;

import com.earth2me.essentials.User;


public class Commandtpdeny extends EssentialsCommand {
    public Commandtpdeny() {
        super("tpdeny");
    }

    @Override
    public void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception {
        if (user.getTeleportRequest() == null) {
            throw new Exception(tl("noPendingRequest"));
        }
        final User player = ess.getUser(user.getTeleportRequest());
        if (player == null) {
            throw new Exception(tl("noPendingRequest"));
        }

        user.sendMessage(tl("requestDenied"));
        player.sendMessage(tl("requestDeniedFrom", user.getDisplayName()));
        user.requestTeleport(null, false);
    }
}
