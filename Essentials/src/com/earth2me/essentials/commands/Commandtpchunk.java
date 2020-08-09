package com.earth2me.essentials.commands;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.FloatUtil;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.Collections;
import java.util.List;

import static com.earth2me.essentials.I18n.tl;


public class Commandtpchunk extends EssentialsCommand {
    public Commandtpchunk() {
        super("tpchunk");
    }

    @Override
    public void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception {
        if (args.length < 2) {
            throw new NotEnoughArgumentsException();
        }

        final int x = args[0].startsWith("~") ? (user.getLocation().getBlockX() >> 4) + (args[0].length() > 1 ? Integer.parseInt(args[0].substring(1)) : 0) : Integer.parseInt(args[0]);
        final int z = args[1].startsWith("~") ? (user.getLocation().getBlockZ() >> 4) + (args[2].length() > 1 ? Integer.parseInt(args[2].substring(1)) : 0) : Integer.parseInt(args[2]);
        final Location loc = new Location(user.getWorld(), x << 4, user.getLocation().getY(), z << 4, user.getLocation().getYaw(), user.getLocation().getPitch());
        if (args.length == 3) {
            World w = ess.getWorld(args[2]);
            if (user.getWorld() != w && ess.getSettings().isWorldTeleportPermissions() && !user.isAuthorized("essentials.worlds." + w.getName())) {
                throw new Exception(tl("noPerm", "essentials.worlds." + w.getName()));
            }
            loc.setWorld(w);
        }
        if (args.length > 3) {
            loc.setYaw((FloatUtil.parseFloat(args[2]) + 360) % 360);
            loc.setPitch(FloatUtil.parseFloat(args[3]));
        }
        if (args.length > 4) {
            World w = ess.getWorld(args[4]);
            if (user.getWorld() != w && ess.getSettings().isWorldTeleportPermissions() && !user.isAuthorized("essentials.worlds." + w.getName())) {
                throw new Exception(tl("noPerm", "essentials.worlds." + w.getName()));
            }
            loc.setWorld(w);
        }
        if (x > 30000000 || z > 30000000 || x < -30000000 || z < -30000000) {
            throw new NotEnoughArgumentsException(tl("teleportInvalidLocation"));
        }
        final Trade charge = new Trade(this.getName(), ess);
        charge.isAffordableFor(user);
        user.sendMessage(tl("teleporting", loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        user.getAsyncTeleport().teleport(loc, charge, TeleportCause.COMMAND, getNewExceptionFuture(user.getSource(), commandLabel));
    }

    @Override
    public void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args) throws Exception {
        if (args.length < 4) {
            throw new NotEnoughArgumentsException();
        }

        User user = getPlayer(server, args, 0, true, false);
        final int x = args[1].startsWith("~") ? (user.getLocation().getBlockX() >> 4) + (args[0].length() > 1 ? Integer.parseInt(args[0].substring(1)) : 0) : Integer.parseInt(args[0]);
        final int z = args[2].startsWith("~") ? (user.getLocation().getBlockZ() >> 4) + (args[2].length() > 1 ? Integer.parseInt(args[2].substring(1)) : 0) : Integer.parseInt(args[2]);
        final Location loc = new Location(user.getWorld(), x << 4, user.getLocation().getY(), z << 4, user.getLocation().getYaw(), user.getLocation().getPitch());
        if (args.length == 4) {
            loc.setWorld(ess.getWorld(args[3]));
        }
        if (args.length > 4) {
            loc.setYaw((FloatUtil.parseFloat(args[3]) + 360) % 360);
            loc.setPitch(FloatUtil.parseFloat(args[4]));
        }
        if (args.length > 5) {
            loc.setWorld(ess.getWorld(args[5]));
        }
        if (x > 30000000 || z > 30000000 || x < -30000000 || z < -30000000) {
            throw new NotEnoughArgumentsException(tl("teleportInvalidLocation"));
        }
        sender.sendMessage(tl("teleporting", loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        user.sendMessage(tl("teleporting", loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        user.getAsyncTeleport().teleport(loc, null, TeleportCause.COMMAND, getNewExceptionFuture(user.getSource(), commandLabel));
    }

    @Override
    protected List<String> getTabCompleteOptions(Server server, User user, String commandLabel, String[] args) {
        if (args.length == 1 || args.length == 2) {
            return Lists.newArrayList("~0");
        } else if (args.length == 3 || args.length == 4) {
            return Lists.newArrayList("0");
        } else if (args.length == 5) {
            List<String> worlds = Lists.newArrayList();
            for (World world : server.getWorlds()) {
                worlds.add(world.getName());
            }
            return worlds;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    protected List<String> getTabCompleteOptions(Server server, CommandSource sender, String commandLabel, String[] args) {
        if (args.length == 1) {
            return getPlayers(server, sender);
        } else if (args.length == 2 || args.length == 3) {
            return Lists.newArrayList("~0");
        } else if (args.length == 4 || args.length == 5) {
            return Lists.newArrayList("0");
        } else if (args.length == 6) {
            List<String> worlds = Lists.newArrayList();
            for (World world : server.getWorlds()) {
                worlds.add(world.getName());
            }
            return worlds;
        } else {
            return Collections.emptyList();
        }
    }
}