package nl.thijsmolendijk.MyPGM2.Commands;

import java.io.File;

import net.minecraft.server.v1_5_R2.MinecraftServer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.ChatColor;

public class RestartUtils {

	public static void restart() {
        try {
            String startupScript = "ServerStart.sh";
            final File file = new File(startupScript);
            if (file.isFile()) {
                System.out.println("Attempting to restart with " + startupScript);

                // Kick all players
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.kickPlayer(ChatColor.RED+"Server restarting. "+ChatColor.AQUA.toString()+ChatColor.BOLD+"Rejoin!");
                }
                // Give the socket a chance to send the packets
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
                // Close the socket so we can rebind with the new process
                MinecraftServer.getServer().ae().a();

                // Give time for it to kick in
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

                // Actually shutdown
                try {
                    MinecraftServer.getServer().stop();
                } catch (Throwable t) {
                }

                // This will be done AFTER the server has completely halted
                Thread shutdownHook = new Thread() {
                    @Override
                    public void run() {
                        try {
                            String os = System.getProperty("os.name").toLowerCase();
                            if (os.contains("win")) {
                                Runtime.getRuntime().exec("cmd /c start " + file.getPath());
                            } else {
                                Runtime.getRuntime().exec(new String[]{"sh", file.getPath()});
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                shutdownHook.setDaemon(true);
                Runtime.getRuntime().addShutdownHook(shutdownHook);
            } else {
                System.out.println("Startup script '" + startupScript + "' does not exist! Stopping server.");
            }
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
