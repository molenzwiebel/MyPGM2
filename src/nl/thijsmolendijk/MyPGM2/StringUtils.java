package nl.thijsmolendijk.MyPGM2;

import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

public class StringUtils {
    /** Repeat character 'c' n times. */
    public static String repeat(String c, int n) {
        assert n >= 0;
        return new String(new char[n]).replace("\0", c);
    }

    public static String padMessage(String message, String c, ChatColor dashColor, ChatColor messageColor) {
        message = " " + message + " ";
        String dashes = StringUtils.repeat(c, (ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH - ChatColor.stripColor(message).length() - 2) / (c.length() * 2));
        return dashColor + dashes + ChatColor.RESET + messageColor + message + ChatColor.RESET + dashColor + dashes;
    }
}