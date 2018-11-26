package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Server;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.Console;
import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.FormatUtil;


public class Commandhelpop extends EssentialsCommand {
    public Commandhelpop() {
        super("helpop");
    }

    @Override
    public void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception {
        user.setDisplayNick();
        final String message = sendMessage(server, user.getSource(), user.getDisplayName(), args);
        if (!user.isAuthorized("essentials.helpop.receive")) {
            user.sendMessage(message);
        }
    }

    @Override
    public void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args) throws Exception {
        sendMessage(server, sender, Console.NAME, args);
    }

    private String sendMessage(final Server server, final CommandSource sender, final String from, final String[] args) throws Exception {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException();
        }
        final String message = tl("helpOp", from, FormatUtil.stripFormat(getFinalArg(args, 0)));
        server.getLogger().log(Level.INFO, message);
        ess.broadcastMessage("essentials.helpop.receive", message);
        return message;
    }

    @Override
    protected List<String> getTabCompleteOptions(Server server, CommandSource sender, String commandLabel, String[] args) {
        return null;  // Use vanilla handler for message
    }
}
