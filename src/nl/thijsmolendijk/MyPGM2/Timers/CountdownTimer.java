package nl.thijsmolendijk.MyPGM2.Timers;

import nl.thijsmolendijk.MyPGM2.Main;

import org.bukkit.Bukkit;

public class CountdownTimer implements Runnable {

	public int timeLeft;
	
	public CountdownTimer(int time) {
		timeLeft = time;
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), this, 0L, 20L);
	}
	
	@Override
	public void run() {
		timeLeft--;
		if (timeLeft == 60){
			Bukkit.getServer().broadcastMessage(this.statusString(timeLeft));
		}
 
		if(timeLeft == 30 || timeLeft == 20 || timeLeft < 11){
			Bukkit.getServer().broadcastMessage(this.statusString(timeLeft));
		}
 
		if(timeLeft == 0){
			this.timerEnded();
		}
	}

	public String statusString(int count) {
		return "";
	}
	public void timerEnded() {
		//Do nothing
	}
}
