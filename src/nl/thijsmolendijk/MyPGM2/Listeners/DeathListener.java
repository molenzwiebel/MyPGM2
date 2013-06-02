package nl.thijsmolendijk.MyPGM2.Listeners;

import nl.thijsmolendijk.MyPGM2.Main;
import nl.thijsmolendijk.MyPGM2.Maps.MapData;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {
	public DeathListener() {
		Bukkit.getPluginManager().registerEvents(this, Main.get());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if (MapManager.get().currentMap == null)
			return;
		MapData current = MapManager.get().currentMap;
		if (current.teamManager.teamForPlayer(e.getPlayer()) == null)
			return;
		if (current.teamManager.teamForPlayer(e.getPlayer()).spawnLocation() == null)
			return;
		e.setRespawnLocation(current.teamManager.teamForPlayer(e.getPlayer()).spawnLocation());
		Team t = current.teamManager.teamForPlayer(e.getPlayer());
		t.spawnKit.give(e.getPlayer());
	}
}
