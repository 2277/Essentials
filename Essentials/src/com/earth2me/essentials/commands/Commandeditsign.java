package com.earth2me.essentials.commands;

import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.FormatUtil;
import com.earth2me.essentials.utils.NumberUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.earth2me.essentials.I18n.tl;

public class Commandeditsign extends EssentialsCommand {
    public Commandeditsign() {
        super("editsign");
    }

    @Override
    protected void run(Server server, User user, String commandLabel, String[] args) throws Exception {
        if (args.length == 0 || (args.length > 1 && !NumberUtil.isInt(args[1]))) {
            throw new NotEnoughArgumentsException();
        }

        Block target = user.getBase().getTargetBlockExact(5); //5 is a good number
        if (target == null || !(target.getState() instanceof Sign)) {
            throw new Exception(tl("editsignCommandTarget"));
        }
        Sign sign = (Sign) target.getState();
        String[] lines = sign.getLines();
        try {
            if (args[0].equalsIgnoreCase("set") && args.length > 2) {
                int line = Integer.parseInt(args[1]) - 1;
                String text = FormatUtil.formatString(user, "essentials.editsign", getFinalArg(args, 2)).trim();
                if (ChatColor.stripColor(text).length() > 15 && !user.isAuthorized("essentials.editsign.unlimited")) {
                    throw new Exception(tl("editsignCommandLimit"));
                }
                lines[line] = text;
                SignChangeEvent event = new SignChangeEvent(target, user.getBase(), lines);
                server.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    user.sendMessage(tl("editsignCommandCancelled"));
                    return;
                }
                text = Strings.nullToEmpty(event.getLine(line));
                sign.setLine(line, text);
                sign.update();
                user.sendMessage(tl("editsignCommandSetSuccess", line + 1, text));
            } else if (args[0].equalsIgnoreCase("clear")) {
                if (args.length == 1) {
                    Arrays.fill(lines, "");
                    SignChangeEvent event = new SignChangeEvent(target, user.getBase(), lines);
                    server.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        user.sendMessage(tl("editsignCommandCancelled"));
                        return;
                    }
                    for (int i = 0; i < 4; i++) { // A whole one line of line savings!
                        sign.setLine(i, Strings.nullToEmpty(event.getLine(i)));
                    }
                    sign.update();
                    user.sendMessage(tl("editsignCommandClear"));
                } else {
                    int line = Integer.parseInt(args[1]) - 1;
                    lines[line] = "";
                    SignChangeEvent event = new SignChangeEvent(target, user.getBase(), lines);
                    server.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        user.sendMessage(tl("editsignCommandCancelled"));
                        return;
                    }
                    sign.setLine(line, Strings.nullToEmpty(event.getLine(line)));
                    sign.update();
                    user.sendMessage(tl("editsignCommandClearLine", line + 1));
                }
            } else {
                throw new NotEnoughArgumentsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new Exception(tl("editsignCommandNoLine"));
        }
    }

    @Override
    protected List<String> getTabCompleteOptions(Server server, User user, String commandLabel, String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList("set", "clear");
        } else if (args.length == 2) {
            return Lists.newArrayList("1", "2", "3", "4");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("set") && NumberUtil.isPositiveInt(args[1])) {
            int line = Integer.parseInt(args[1]);
            Block target = user.getBase().getTargetBlockExact(5);
            if (target != null && target.getState() instanceof Sign && line <= 4) {
                Sign sign = (Sign) target.getState();
                return Lists.newArrayList(FormatUtil.unformatString(user, "essentials.editsign", sign.getLine(line - 1)));
            }
            return Collections.emptyList();
        } else {
            return Collections.emptyList();
        }
    }
}
