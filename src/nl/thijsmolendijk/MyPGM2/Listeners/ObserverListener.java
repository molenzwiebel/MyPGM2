package nl.thijsmolendijk.MyPGM2.Listeners;

import nl.thijsmolendijk.MyPGM2.Main;
import nl.thijsmolendijk.MyPGM2.Maps.MapData;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ObserverListener implements Listener {
	public ObserverListener() {
		Bukkit.getPluginManager().registerEvents(this, Main.get());
	}

	public boolean check(Player p) {
		if (MapManager.get().currentMap == null)
			return true;
		MapData current = MapManager.get().currentMap;
		if (current.teamManager.teamForPlayer(p) == null)
			return true;
		if (current.teamManager.teamForPlayer(p).isSpectating)
			return true;
		if (current.inProgress == false)
			return true;
		return false;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		e.setCancelled(this.check(e.getPlayer()));
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		e.setCancelled(this.check(e.getPlayer()));
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		e.setCancelled(this.check(e.getPlayer()));
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		e.setCancelled(this.check(e.getPlayer()));
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		e.setCancelled(this.check((Player) e.getWhoClicked()));
	}

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		e.setCancelled(this.check(e.getPlayer()));
	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent ev){
		Entity e = ev.getEntity();
		if(e instanceof ExperienceOrb && ev.getTarget() instanceof Player && this.check((Player) ev.getTarget())){
			ev.setCancelled(true);
			ev.setTarget(null);
		}
	}
	
	
}
