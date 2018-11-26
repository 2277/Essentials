package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import org.bukkit.Server;

import com.earth2me.essentials.User;

public class Commanddisposal extends EssentialsCommand {

    public Commanddisposal() {
        super("disposal");
    }

    @Override
    protected void run(Server server, User user, String commandLabel, String[] args) throws Exception {
        user.sendMessage(tl("openingDisposal"));
        user.getBase().openInventory(ess.getServer().createInventory(user.getBase(), 36, "Disposal"));
    }

}
