package net.ess3.api;

import java.util.Collection;

import net.ess3.nms.PotionMetaProvider;
import net.ess3.nms.SpawnEggProvider;

public interface IEssentials extends com.earth2me.essentials.IEssentials {

    Collection<String> getVanishedPlayersNew();

    SpawnEggProvider getSpawnEggProvider();

    PotionMetaProvider getPotionMetaProvider();
}
