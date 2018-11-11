package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Server;

import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.StringUtil;

public class Commandhomes extends EssentialsCommand {

	public Commandhomes() {
		super("homes");
	}

	@Override
	public void run(Server server, User user, String commandLabel, String[] args) throws Exception {
		User player = user;
		if (args.length > 0 && user.isAuthorized("essentials.home.others")) {
			player = getPlayer(server, user, args, 0);
		}
		Location bed = player.getBase().getBedSpawnLocation();
		List<String> homes = player.getHomes();
		if (homes.isEmpty()) {
			throw new Exception(tl("noHomeSetPlayer"));
		} else {
			int count = homes.size();
			if (user.isAuthorized("essentials.home.bed")) {
				if (bed != null) {
					homes.add(tl("bed"));
				} else {
					homes.add(tl("bedNull"));
				}
			}
			user.sendMessage(tl("homes", StringUtil.joinList(homes), count, getHomeLimit(player)));
		}
	}

	private String getHomeLimit(final User player) {
		if (!player.getBase().isOnline()) { return "?"; }
		if (player.isAuthorized("essentials.sethome.multiple.unlimited")) { return "*"; }
		return Integer.toString(ess.getSettings().getHomeLimit(player));
	}

	@Override
	public List<String> getTabCompleteOptions(Server server, User user, String commandLabel, String[] args) {
		return args.length == 1 && user.isAuthorized("essentials.home.others") ? getPlayers(server, user)
				: Collections.emptyList();
	}

}
