package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import com.earth2me.essentials.User;

import org.bukkit.Server;

public class Commandignoreafk extends EssentialsCommand {

	public Commandignoreafk() {
		super("ignoreafk");
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

		boolean ignoreAfk = !user.isIgnoreAfkEnabled();

		if (commandLabel.toLowerCase().endsWith("on")) {
			ignoreAfk = true;
		} else if (commandLabel.toLowerCase().endsWith("off")) {
			ignoreAfk = false;
		}
		user.setIgnoredAfkEnabled(ignoreAfk);
		if (ignoreAfk) {
			user.sendMessage(tl("ignoreAfkToggleOn"));
		} else {
			user.sendMessage(tl("ignoreAfkToggleOff"));
		}
	}
}
