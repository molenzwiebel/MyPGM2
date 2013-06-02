package nl.thijsmolendijk.MyPGM2.Timers;

import org.bukkit.ChatColor;

public class GameStartTimer extends CountdownTimer {

	public GameStartTimer(int time) {
		super(time);
	}

	@Override
	public String statusString(int count) {
		return ChatColor.GREEN+"Game starting in "+ChatColor.AQUA+count+ChatColor.GREEN.toString()+" seconds";
	}
	
	@Override
	public void timerEnded() {
		//Start game
	}
}
