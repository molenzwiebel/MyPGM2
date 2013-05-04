package nl.thijsmolendijk.MyPGM2.Teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.minecraft.util.commands.ChatColor;


public class TeamManager {
	public List<Team> teams = new ArrayList<Team>();
	public TeamManager() {
	}

	public Team teamForPlayer(Player p) {
		Team found = null;
		for (Team t : this.teams) {
			if (t.isPlayerMember(p))
				found = t;
		}
		return found;
	}
	
	public void addPlayerToTeam(Player p, Team t) {
		/* Team adding handling... ------------------------------------- */
		for (Team te : this.teams)
			te.members.remove(p.getName());
		t.members.add(p.getName());
		
		/* ------------------------------------------------------------- */
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[4]);
		p.sendMessage(ChatColor.GREEN + "You joined "+t.color+t.name);
		
		//Kill if not spectating...
		if (!this.teamForPlayer(p).isSpectating)
			//p.setHealth(0);
		
		if (t.isSpectating) {
			p.setGameMode(GameMode.CREATIVE);
			p.setItemInHand(new ItemStack(Material.COMPASS));
		} else {
			p.setGameMode(GameMode.SURVIVAL);
		}
		
		//Make sure to do a valid spawn!
		if (t.spawn == null)
			p.teleport(p.getWorld().getSpawnLocation());
		else
			p.teleport(t.spawn);
	}
	
	public Team teamForPlayer(String p) {
		return this.teamForPlayer(Bukkit.getPlayer(p));
	}
	
	public void addTeam(Team t) {
		if (this.teams.contains(t))
			return;
		this.teams.add(t);
	}
	
	public Team matchTeam(String map) {
		Team found = null;
        String lowerName = map.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (Team player : this.teams) {
            if (player.name.toLowerCase().startsWith(lowerName)) {
                int curDelta = player.name.length() - lowerName.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) break;
            }
        }
        return found;
	}
	
	public Team teamForName(String name) {
		Team found = null;
		for (Team t : this.teams) 
			if (t.name.equals(name))
				found = t;
		return found;	
	}
}
