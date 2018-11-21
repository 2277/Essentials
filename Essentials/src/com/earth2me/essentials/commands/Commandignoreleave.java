package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import com.earth2me.essentials.User;

import org.bukkit.Server;

public class Commandignoreleave extends EssentialsCommand {

	public Commandignoreleave() {
		super("ignoreleave");
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

		boolean ignoreLeave = !user.isIgnoreLeaveEnabled();

		if (commandLabel.toLowerCase().endsWith("on")) {
			ignoreLeave = true;
		} else if (commandLabel.toLowerCase().endsWith("off")) {
			ignoreLeave = false;
		}
		user.setIgnoredLeaveEnabled(ignoreLeave);
		if (ignoreLeave) {
			user.sendMessage(tl("ignoreLeaveToggleOn"));
		} else {
			user.sendMessage(tl("ignoreLeaveToggleOff"));
		}
	}
}
