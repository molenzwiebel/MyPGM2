package nl.thijsmolendijk.MyPGM2.Timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RestartTimer extends CountdownTimer {

	public RestartTimer(int time) {
		
		super(time);
	}

	@Override
	public String statusString(int count) {
		return ChatColor.GREEN+"Server restarting in "+ChatColor.AQUA+count+ChatColor.GREEN.toString()+" seconds";
	}
	
	@Override
	public void timerEnded() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer(ChatColor.RED+"Server restarting."+ChatColor.AQUA+ChatColor.BOLD.toString()+" Rejoin!");
		}
		System.exit(999);
	}
}
