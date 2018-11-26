package com.earth2me.essentials.api;

import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.User;


public interface IItemDb {
    ItemStack get(final String name, final int quantity) throws Exception;

    ItemStack get(final String name) throws Exception;

    String names(ItemStack item);

    List<String> nameList(ItemStack item);

    String name(ItemStack item);

    List<ItemStack> getMatching(User user, String[] args) throws Exception;

    String serialize(ItemStack is);

    Collection<String> listNames();

    @Deprecated
    Material getFromLegacyId(int id);

    @Deprecated
    int getLegacyId(Material material) throws Exception;
}
