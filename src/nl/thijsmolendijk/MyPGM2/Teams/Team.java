package nl.thijsmolendijk.MyPGM2.Teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team {
	public ChatColor color;
	public String name;
	public int maxPlayers;
	public List<String> members = new ArrayList<String>();
	public Location spawn;
	public boolean isSpectating = false;
	
	public boolean isPlayerMember(Player p) {
		return members.contains(p.getName());
	}
}
