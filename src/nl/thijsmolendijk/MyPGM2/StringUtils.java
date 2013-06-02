package nl.thijsmolendijk.MyPGM2;

import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

import com.google.common.base.Joiner;

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
    
    public static String authorList(Iterable<String> l) {
    	String comma = Joiner.on(ChatColor.LIGHT_PURPLE+", "+ChatColor.AQUA).join(l);
    	System.out.println(comma);
    	System.out.println(replaceLast(ChatColor.LIGHT_PURPLE+" and "+ChatColor.AQUA, ", ", comma));
    	return replaceLast(comma, ", ", " and ");
    }
    
    public static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                 + replacement
                 + string.substring(pos + toReplace.length(), string.length());
        } else {
            return string;
        }
    }
}