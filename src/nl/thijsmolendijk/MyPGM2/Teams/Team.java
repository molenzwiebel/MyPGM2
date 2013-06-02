package nl.thijsmolendijk.MyPGM2.Teams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Maps.Kits.Kit;
import nl.thijsmolendijk.MyPGM2.Maps.Regions.IRegion;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team {
	
	public ChatColor color;
	public String name;
	public int maxPlayers;
	public List<String> members = new ArrayList<String>();
	public IRegion spawnRegion;
	public int spawnYaw;
	public Kit spawnKit;
	public org.bukkit.scoreboard.Team sbTeam;
	
	public Team(String name, ChatColor color) {
		
	}
	
	public boolean isSpectating = false;
	
	public boolean isPlayerMember(Player p) {
		return members.contains(p.getName());
	}
	
	public Location spawnLocation() {
		Random r = new Random();
		if (spawnRegion == null || spawnRegion.blocks().size() == 0)
			return MapManager.get().currentMap.world.getSpawnLocation();
		Location loc = spawnRegion.blocks().get(r.nextInt(spawnRegion.blocks().size()));
		if (loc == null)
			return MapManager.get().currentMap.world.getSpawnLocation();
		loc.setWorld(MapManager.get().currentMap.world);
		loc.setYaw(spawnYaw);
		return loc;
	}
}
