package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import com.earth2me.essentials.User;

import org.bukkit.Server;

public class Commandignorejoin extends EssentialsCommand {

	public Commandignorejoin() {
		super("ignorejoin");
	}

	@Override
	public void run(Server server, User user, String commandLabel, String[] args) throws Exception {
//        boolean confirmingClear = !user.isPromptingClearConfirm();
//        if (commandLabel.toLowerCase().endsWith("on")) {
//            confirmingClear = true;
//        } else if (commandLabel.toLowerCase().endsWith("off")) {
//            confirmingClear = false;
//        }
//        user.setPromptingClearConfirm(confirmingClear);
//        if (confirmingClear) {
//            user.sendMessage(tl("clearInventoryConfirmToggleOn"));
//        } else {
//            user.sendMessage(tl("clearInventoryConfirmToggleOff"));
//        }
//        user.setConfirmingClearCommand(null);

		boolean ignoreJoin = !user.isIgnoreJoinEnabled();

		if (commandLabel.toLowerCase().endsWith("on")) {
			ignoreJoin = true;
		} else if (commandLabel.toLowerCase().endsWith("off")) {
			ignoreJoin = false;
		}
		user.setIgnoredJoinEnabled(ignoreJoin);
		if (ignoreJoin) {
			user.sendMessage(tl("ignoreJoinToggleOn"));
		} else {
			user.sendMessage(tl("ignoreJoinToggleOff"));
		}
	}
}
