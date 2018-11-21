package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n.tl;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.MetaItemStack;
import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.NumberUtil;
import com.google.common.collect.Lists;

public class Commandsetslot extends EssentialsCommand {
	public Commandsetslot() {
		super("setslot");
	}

	@Override
	public void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args)
			throws Exception {
		if (args.length < 3) { throw new NotEnoughArgumentsException(); }

		ItemStack stack = ess.getItemDb().get(args[1]);

		final String itemname = stack.getType().toString().toLowerCase(Locale.ENGLISH).replace("_", "");
		if (sender.isPlayer() && (ess.getSettings().permissionBasedItemSpawn()
				? (!ess.getUser(sender.getPlayer()).isAuthorized("essentials.itemspawn.item-all")
						&& !ess.getUser(sender.getPlayer()).isAuthorized("essentials.itemspawn.item-" + itemname))
				: (!ess.getUser(sender.getPlayer()).isAuthorized("essentials.itemspawn.exempt")
						&& !ess.getUser(sender.getPlayer()).canSpawnItem(stack.getType())))) { throw new Exception(
								tl("cantSpawnItem", itemname)); }

		final User giveTo = getPlayer(server, sender, args, 0);

		int slot;
		switch (args[2].toLowerCase()) {
			case "helmet":
			case "head":
				slot = 39;
				break;
			case "chestplate":
			case "chest":
				slot = 38;
				break;
			case "legs":
			case "leggings":
				slot = 37;
				break;
			case "feet":
			case "boots":
				slot = 36;
				break;

			default:
				try {
					slot = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					throw new NotEnoughArgumentsException();
				}
		}

		try {
			if (args.length > 4 && NumberUtil.isInt(args[3]) && NumberUtil.isInt(args[4])) {
				stack.setAmount(Integer.parseInt(args[3]));
				stack.setDurability(Short.parseShort(args[4]));
			} else if (args.length > 3 && Integer.parseInt(args[3]) > 0) {
				stack.setAmount(Integer.parseInt(args[3]));
			} else if (ess.getSettings().getDefaultStackSize() > 0) {
				stack.setAmount(ess.getSettings().getDefaultStackSize());
			} else if (ess.getSettings().getOversizedStackSize() > 0
					&& giveTo.isAuthorized("essentials.oversizedstacks")) {
				stack.setAmount(ess.getSettings().getOversizedStackSize());
			}
		} catch (NumberFormatException e) {
			throw new NotEnoughArgumentsException();
		}

		MetaItemStack metaStack = new MetaItemStack(stack);
		if (!metaStack.canSpawn(ess)) { throw new Exception(tl("unableToSpawnItem", itemname)); }

		if (args.length > 4) {

			boolean allowUnsafe = ess.getSettings().allowUnsafeEnchantments();
			if (allowUnsafe && sender.isPlayer()
					&& !ess.getUser(sender.getPlayer()).isAuthorized("essentials.enchantments.allowunsafe")) {
				allowUnsafe = false;
			}

			int metaStart = NumberUtil.isInt(args[4]) ? 5 : 4;

			if (args.length > metaStart) {
				metaStack.parseStringMeta(sender, allowUnsafe, args, metaStart, ess);
			}

			stack = metaStack.getItemStack();
		}

		if (stack.getType() == Material.AIR) { throw new Exception(tl("cantSpawnItem", "Air")); }

		giveTo.getBase().getInventory().setItem(slot, stack);
	}

	@Override
	protected List<String> getTabCompleteOptions(final Server server, final CommandSource sender,
			final String commandLabel, final String[] args) {
		if (args.length == 1) {
			return getPlayers(server, sender);
		} else if (args.length == 2) {
			return getItems();
		} else if (args.length == 3) {
			return Lists.newArrayList("helmet", "chestplate", "leggings", "boots");
		} else if (args.length == 4) {
			return Lists.newArrayList("1", "64"); // TODO: get actual max size
		} else if (args.length == 5) {
			return Lists.newArrayList("0");
		} else {
			return Collections.emptyList();
		}
	}
}
