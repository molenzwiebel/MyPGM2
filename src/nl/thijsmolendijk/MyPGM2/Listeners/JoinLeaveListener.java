package nl.thijsmolendijk.MyPGM2.Listeners;

import nl.thijsmolendijk.MyPGM2.Main;
import nl.thijsmolendijk.MyPGM2.Maps.MapData;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {
	public JoinLeaveListener() {
		Bukkit.getPluginManager().registerEvents(this, Main.get());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		MapData found = MapManager.get().currentMap;
		if (found == null)
			return;
		Team t = found.teamManager.matchTeam("Observers");
		found.teamManager.addPlayerToTeam(e.getPlayer(), t);
		e.getPlayer().setScoreboard(Main.mainSB);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		
	}
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent ev) {
		if (ev.getSpawnReason() == SpawnReason.NATURAL)
			ev.setCancelled(true);
	}
}
