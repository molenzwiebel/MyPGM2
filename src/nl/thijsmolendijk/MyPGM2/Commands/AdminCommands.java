package nl.thijsmolendijk.MyPGM2.Commands;

import java.util.List;

import nl.thijsmolendijk.MyPGM2.StringUtils;
import nl.thijsmolendijk.MyPGM2.Maps.MapData;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Maps.Kits.Kit;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.bukkit.pagination.PaginatedResult;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class AdminCommands {
	
	
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
			aliases = { "maplist", "ml" },
			desc = "List maps loaded",
			min = 0,
			max = 1
			)
	public static void maplist(final CommandContext args, final CommandSender sender) throws CommandException {
		List<String> whitelisted = MapManager.get().loadedMapNames();

		new PaginatedResult<String>(8) {
			@Override public String format(String map, int index) {
				return (index + 1) + ". " + map;
			}
			@Override public String formatHeader(int page, int maxPages) {
				ChatColor dashColor = ChatColor.RED;
				ChatColor textColor = ChatColor.DARK_AQUA;
				ChatColor highlight = ChatColor.AQUA;

				String message = textColor + "Loaded maps" + textColor + " (" + highlight + page + textColor + " of " + highlight + maxPages + textColor + ")";
				return StringUtils.padMessage(message, "-", dashColor, textColor);
			}
		}.display(sender, whitelisted, args.getInteger(0, 1));
	}
	
	@Command(
			aliases = { "givekit", "gk" },
			desc = "Give a defined kit",
			min = 0,
			max = 1
			)
	public static void givekit(final CommandContext args, final CommandSender sender) throws CommandException {
		Validate.isTrue(sender instanceof Player, "You need to be a player do to that!");
		MapData found = MapManager.get().currentMap;
		Validate.notNull(found, "No map running");
		Kit k = found.kits.get(args.getJoinedStrings(0));
		Validate.notNull(k, "No kit found");
		k.give(((Player) sender));
	}
}