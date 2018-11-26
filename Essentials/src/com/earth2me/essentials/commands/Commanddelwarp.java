package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Server;

import com.earth2me.essentials.CommandSource;


public class Commanddelwarp extends EssentialsCommand {
    public Commanddelwarp() {
        super("delwarp");
    }

    @Override
    public void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args) throws Exception {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException();
        }

        ess.getWarps().removeWarp(args[0]);
        sender.sendMessage(tl("deleteWarp", args[0]));
    }

    @Override
    protected List<String> getTabCompleteOptions(final Server server, final CommandSource sender, final String commandLabel, final String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(ess.getWarps().getList());
        } else {
            return Collections.emptyList();
        }
    }
}
