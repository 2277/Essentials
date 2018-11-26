package com.earth2me.essentials.craftbukkit;

import java.util.Set;

import org.bukkit.BanEntry;
import org.bukkit.BanList;

import com.earth2me.essentials.User;

import net.ess3.api.IEssentials;


public class BanLookup {
    public static Boolean isBanned(IEssentials ess, User user) {
        return isBanned(ess, user.getName());
    }

    public static Boolean isBanned(IEssentials ess, String name) {
        return getBanEntry(ess, name) != null;
    }

    public static BanEntry getBanEntry(IEssentials ess, String name) {
        Set<BanEntry> benteries = ess.getServer().getBanList(BanList.Type.NAME).getBanEntries();
        for (BanEntry banEnt : benteries) {
            if (banEnt.getTarget().equals(name)) {
                return banEnt;
            }
        }
        return null;
    }

}
