package nl.thijsmolendijk.MyPGM2.Commands;

import java.io.File;
import java.io.IOException;

import nl.thijsmolendijk.MyPGM2.EmptyWorldGenerator;
import nl.thijsmolendijk.MyPGM2.FileTools;
import nl.thijsmolendijk.MyPGM2.PlayerTools;
import nl.thijsmolendijk.MyPGM2.StringUtils;
import nl.thijsmolendijk.MyPGM2.Maps.MapData;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Teams.Team;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class MapCommands {
	@Command(
			aliases = { "map" },
			desc = "Reload MyPGM2",
			min = 1,
			max = -1
			)
	public static void mapCommand(final CommandContext args, final CommandSender sender) throws CommandException {
		if (!(sender instanceof Player)) { 
			sender.sendMessage(ChatColor.RED+"You might want to get in-game to do that!");
			//return;
		}
		if (MapManager.get().currentMap == null) {
			sender
			.sendMessage(ChatColor.RED+"There's no map running!");
			//return;
		}
		MapData found = MapManager.get().matchMap(args.getJoinedStrings(0));
		Validate.notNull(found, "No maps found!");
		for (Team t : found.teamManager.teams)
			System.out.println(t.name);
		sender.sendMessage(StringUtils.padMessage(found.name, "-", ChatColor.GOLD, ChatColor.LIGHT_PURPLE));
		sender.sendMessage(ChatColor.AQUA+""+ChatColor.BOLD+"Objective: "+ChatColor.RESET+ChatColor.GREEN+found.objective+"\n\n");
		sender.sendMessage(ChatColor.AQUA+""+ChatColor.BOLD+"Authors: ");
		for (String au : found.getAuthors()) {
			System.out.println(found.getAuthors().toString());
			if (!found.authors.get(au).equals(""))
				sender.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"* "+ChatColor.RESET+""+ChatColor.GREEN+au+ChatColor.LIGHT_PURPLE+" ("+found.authors.get(au)+")");
			else 
				sender.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"* "+ChatColor.RESET+""+ChatColor.GREEN+au);
		
		}
		sender.sendMessage(ChatColor.AQUA+""+ChatColor.BOLD+"Contributors: ");
		for (String co : found.contributors.keySet()) {
			System.out.println(found.getAuthors().toString());
			if (!found.contributors.get(co).equals(""))
				sender.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"* "+ChatColor.RESET+""+ChatColor.GREEN+co+ChatColor.LIGHT_PURPLE+" ("+found.contributors.get(co)+")");
			else 
				sender.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"* "+ChatColor.RESET+""+ChatColor.GREEN+co);
		
		}
	}
	
	
	@Command(
			aliases = { "cycleto" },
			desc = "Cycle to a map",
			min = 1,
			max = -1
			)
	public static void cycleTo(final CommandContext args, final CommandSender sender) throws CommandException {
		Player p = (Player) sender;
		MapData found = MapManager.get().matchMap(args.getJoinedStrings(0));
		Validate.notNull(found, "No maps found!");
		if (p.getWorld().getName().contains("_COPY")) {
			//Currently in a loaded world, send them back and delete this world
			String oldWorldName = p.getWorld().getName();
			World mainWorld = Bukkit.getServer().getWorld("world");
			for (Player pToTP : Bukkit.getServer().getOnlinePlayers()) {
				
				pToTP.teleport(mainWorld.getSpawnLocation());
			}
			p.sendMessage(ChatColor.GREEN+"Unloading world");
			Bukkit.getServer().unloadWorld(oldWorldName, false);
			p.sendMessage(ChatColor.GREEN+"Deleting _COPY world files");
			try {
				FileTools.deleteFolder(new File(oldWorldName));
			} catch (IOException e) {
				p.sendMessage(ChatColor.RED+"Something went wrong during deleting: "+e.getMessage());
			}
		}
		//Check if the map exists and is valid
		File path = new File("Maps/", found.fileLocation);
		if (!(new File(path,"level.dat").exists())) {
			p.sendMessage(ChatColor.RED+"No level.dat file found inside world folder");
		}
		//Correct world, try to copy the world to the main folder
		try {
			FileTools.copyFolder(path, new File(found.fileLocation+"_COPY"));
		} catch (IOException e) {
			p.sendMessage(ChatColor.RED+"Something went wrong during copying: "+e.getMessage());
		}
		//Copying succeeded, load the world and send all players to the new world
		World newWorld = Bukkit.getServer().createWorld(new WorldCreator(found.fileLocation+"_COPY").generator(new EmptyWorldGenerator()));
		MapManager.get().currentMap = found;
		PlayerTools.tpAll(newWorld.getSpawnLocation());
	}
}
