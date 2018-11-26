package com.earth2me.essentials.commands;

import org.bukkit.Server;

import com.earth2me.essentials.User;


public class Commandworkbench extends EssentialsCommand {
    public Commandworkbench() {
        super("workbench");
    }


    @Override
    public void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception {
        user.getBase().openWorkbench(null, true);
    }
}