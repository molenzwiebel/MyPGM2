package nl.thijsmolendijk.MyPGM2.Commands;

import nl.thijsmolendijk.MyPGM2.Maps.MapData;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;
import nl.thijsmolendijk.MyPGM2.Teams.Team;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class MatchCommands {

	@Command(
			aliases = { "join" },
			desc = "Joins a team",
			min = 1,
			max = -1
			)
	public static void reload(final CommandContext args, final CommandSender sender) throws CommandException {
		MapData found = MapManager.get().currentMap;
		Validate.notNull(found, "No map running!");
		Team t = found.teamManager.matchTeam(args.getJoinedStrings(0));
		found.teamManager.addPlayerToTeam((Player) sender, t);
	}

}
