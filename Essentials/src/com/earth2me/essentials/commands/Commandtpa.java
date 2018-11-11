package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import java.util.Collections;
import java.util.List;

import org.bukkit.Server;

import com.earth2me.essentials.User;

import mkremins.fanciful.FancyMessage;

public class Commandtpa extends EssentialsCommand {
	public Commandtpa() {
		super("tpa");
	}

	@Override
	public void run(Server server, User user, String commandLabel, String[] args) throws Exception {
		if (args.length < 1) { throw new NotEnoughArgumentsException(); }

		User player = getPlayer(server, user, args, 0);
		if (user.getName().equalsIgnoreCase(player.getName())) { throw new NotEnoughArgumentsException(); }
		if (!player.isTeleportEnabled()) { throw new Exception(tl("teleportDisabled", player.getDisplayName())); }
		if (user.getWorld() != player.getWorld() && ess.getSettings().isWorldTeleportPermissions()
				&& !user.isAuthorized("essentials.worlds." + player.getWorld().getName())) { throw new Exception(
						tl("noPerm", "essentials.worlds." + player.getWorld().getName())); }
		// Don't let sender request teleport twice to the same player.
		if (user.getConfigUUID().equals(player.getTeleportRequest()) && player.hasOutstandingTeleportRequest() // Check
																												// timeout
				&& player.isTpRequestHere() == false) { // Make sure the last teleport request was actually tpa and not
														// tpahere
			throw new Exception(tl("requestSentAlready", player.getDisplayName()));
		}

		if (!player.isIgnoredPlayer(user)) {
			player.requestTeleport(user, false);

			FancyMessage message = new FancyMessage(tl("teleportRequest", user.getDisplayName()));

			if (ess.getSettings().getTpaAcceptCancellation() != 0) {
				message.tooltip(tl("teleportRequestTimeoutInfo", ess.getSettings().getTpaAcceptCancellation()));
			}

			message.then(tl("clickTpaccept"))
					.tooltip(tl("tooltipTpaccept"))
					.command("/tpaccept")
					.then(tl("clickTpdeny"))
					.tooltip(tl("tooltipTpdeny"))
					.command("/tpdeny");
			message.send(player.getBase());

//            player.sendMessage(tl("teleportRequest", user.getDisplayName()));
//            player.sendMessage(tl("typeTpaccept"));
//            player.sendMessage(tl("typeTpdeny"));
//            if (ess.getSettings().getTpaAcceptCancellation() != 0) {
//                player.sendMessage(tl("teleportRequestTimeoutInfo", ess.getSettings().getTpaAcceptCancellation()));
//            }
		}
		user.sendMessage(tl("requestSent", player.getDisplayName()));
		user.sendMessage(tl("typeTpacancel"));
	}

	@Override
	protected List<String> getTabCompleteOptions(Server server, User user, String commandLabel, String[] args) {
		if (args.length == 1) {
			return getPlayers(server, user);
		} else {
			return Collections.emptyList();
		}
	}
}
