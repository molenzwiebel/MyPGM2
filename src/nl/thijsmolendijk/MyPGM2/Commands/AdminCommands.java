package nl.thijsmolendijk.MyPGM2.Commands;

import java.util.List;

import nl.thijsmolendijk.MyPGM2.Main;
import nl.thijsmolendijk.MyPGM2.Maps.MapData;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Maps.Regions.IRegion;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class AdminCommands {
	@Command(
			aliases = { "reloadpgm" },
			desc = "Reload MyPGM2",
			min = 0,
			max = 0
			)
	public static void reload(final CommandContext args, final CommandSender sender) throws CommandException {
		System.out.println("[MyPGM2] Reloaded");
		sender.sendMessage(ChatColor.GREEN+"[MyPGM2] Reloaded");
	}
	@Command(
			aliases = { "restart" },
			desc = "Restart the server",
			min = 0,
			max = 0
			)
	public static void restart(final CommandContext args, final CommandSender sender) throws CommandException {
		System.out.println("[MyPGM2] Restarting...");
		sender.sendMessage(ChatColor.GREEN+"[MyPGM2] Restarting...");
		for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer(ChatColor.RED+"Server restarting. "+ChatColor.AQUA.toString()+ChatColor.BOLD+"Rejoin!");
        }
		System.exit(999);
	}
	
	@Command(
			aliases = { "showregions" },
			desc = "Show all regions",
			min = 0,
			max = 0
			)
	public static void showregions(final CommandContext args, final CommandSender sender) throws CommandException {
		MapData found = MapManager.get().currentMap;
		Validate.notNull(found, "No map running!");
		System.out.println("Showing regions..");
		for (String key : found.regions.keySet()) {
			sender.sendMessage("Showing region "+key);
			IRegion r = found.regions.get(key);
			changeBlocksLater(r.blocks(), (Player) sender);
		}
	}
	
	public static void changeBlocksLater(final List<Location> blocks, final Player p) {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Location l : blocks) {
					p.sendBlockChange(l, Material.GLASS, (byte)0);
				}
			}

		}.runTaskLater(Main.get(), 20);
	}
}