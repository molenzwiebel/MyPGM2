package nl.thijsmolendijk.MyPGM2;

import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerTools {

	public static void tpAll(Location loc) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(loc);
		}
	}
	public static void removeAllEffects(Player p) {
		for (PotionEffect e : p.getActivePotionEffects()) 
			p.removePotionEffect(e.getType());
	}
	
	public static void joinAll(Team t) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			MapManager.get().currentMap.teamManager.addPlayerToTeam(p, t);
		}
	}
}
