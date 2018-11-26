package com.earth2me.essentials;

import java.util.Locale;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.utils.EnumUtil;
import com.earth2me.essentials.utils.LocationUtil;

import net.ess3.api.IEssentials;


public class EssentialsBlockListener implements Listener {
    private final transient IEssentials ess;

    public EssentialsBlockListener(final IEssentials ess) {
        this.ess = ess;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockPlace(final BlockPlaceEvent event) {
        // Do not rely on getItemInHand();
        // http://leaky.bukkit.org/issues/663
        final ItemStack is = LocationUtil.convertBlockToItem(event.getBlockPlaced());
        if (is == null) {
            return;
        }
        Material MOB_SPAWNER = EnumUtil.getMaterial("SPAWNER", "MOB_SPAWNER");

        if (is.getType() == MOB_SPAWNER && event.getItemInHand() != null && event.getPlayer() != null && event.getItemInHand().getType() == MOB_SPAWNER) {
            final BlockState blockState = event.getBlockPlaced().getState();
            if (blockState instanceof CreatureSpawner) {
                final CreatureSpawner spawner = (CreatureSpawner) blockState;
                final EntityType type = ess.getSpawnerProvider().getEntityType(event.getItemInHand());
                if (type != null && Mob.fromBukkitType(type) != null) {
                    if (ess.getUser(event.getPlayer()).isAuthorized("essentials.spawnerconvert." + Mob.fromBukkitType(type).name().toLowerCase(Locale.ENGLISH))) {
                        spawner.setSpawnedType(type);
                        spawner.update();
                    }
                }
            }
        }

        final User user = ess.getUser(event.getPlayer());
        if (user.hasUnlimited(is) && user.getBase().getGameMode() == GameMode.SURVIVAL) {
            class UnlimitedItemSpawnTask implements Runnable {
                @Override
                public void run() {
                    user.getBase().getInventory().addItem(is);
                    user.getBase().updateInventory();
                }
            }
            ess.scheduleSyncDelayedTask(new UnlimitedItemSpawnTask());
        }
    }
}
