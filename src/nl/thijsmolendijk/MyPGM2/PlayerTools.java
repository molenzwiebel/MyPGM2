package nl.thijsmolendijk.MyPGM2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerTools {

	public static void tpAll(Location loc) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(loc);
		}
	}

}
