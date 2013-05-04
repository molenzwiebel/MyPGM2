package nl.thijsmolendijk.MyPGM2;

import nl.thijsmolendijk.MyPGM2.Commands.AdminCommands;
import nl.thijsmolendijk.MyPGM2.Commands.MapCommands;
import nl.thijsmolendijk.MyPGM2.Commands.MatchCommands;
import nl.thijsmolendijk.MyPGM2.Maps.MapManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class Main extends JavaPlugin {
	private static Main instance;
	private CommandsManager<CommandSender> commands;
	public static Main get() {
		if (instance == null) {
			instance = new Main();
			return instance;
		}
		return instance;
	}


	@Override
	public void onEnable() {
		System.out.println("[MyPGM2] Loading maps");
		instance = this;
		this.setupCommands();
		try {
			MapManager.get().loadMaps();
		} catch (Exception e) {
			System.out.println("[MyPGM2] [SEVERE] Error whilst loading the maps: "+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		System.out.println("[MyPGM2] Saving logs and maps");
	}




	private void setupCommands() {
		this.commands = new CommandsManager<CommandSender>() {
			@Override public boolean hasPermission(CommandSender sender, String perm) {
				return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
			}
		};

		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
		cmdRegister.register(AdminCommands.class);
		cmdRegister.register(MapCommands.class);
		cmdRegister.register(MatchCommands.class);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + "NestedCommand:"+ e.getUsage());
			e.printStackTrace();
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + "Usage:"+ e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
			e.printStackTrace();
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else if (e.getCause() instanceof IllegalArgumentException) {
				sender.sendMessage(ChatColor.RED+e.getMessage().replace("java.lang.IllegalArgumentException: ", ""));
				e.printStackTrace();
			}
			else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + "Command:"+  e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
}
