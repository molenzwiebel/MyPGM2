package nl.thijsmolendijk.MyPGM2;

import java.util.List;

import org.bukkit.ChatColor;

public class Tools {

	static String[] colors = {"aqua", "black", "blue", "dark aqua", "dark blue", "dark gray", "dark green", "dark purple",
		"dark red", "gold", "gray", "green", "light purple", "red", "white", "yellow"};
	static ChatColor[] colorsC = {ChatColor.AQUA, ChatColor.BLACK, ChatColor.BLUE, ChatColor.DARK_AQUA,
		ChatColor.DARK_BLUE, ChatColor.DARK_GRAY, ChatColor.DARK_GREEN, ChatColor.DARK_PURPLE,
		ChatColor.DARK_RED, ChatColor.GOLD, ChatColor.GRAY, ChatColor.GREEN,
		ChatColor.LIGHT_PURPLE, ChatColor.RED, ChatColor.WHITE, ChatColor.YELLOW};

	public static ChatColor matchColor(String toMatch) {
		ChatColor color = ChatColor.RED;
		for (int i1 = 0; i1 < colors.length; i1++) {
			
			if (colors[i1].equals(toMatch)) {
				
				color = colorsC[i1];
			}
		}
		return color;
	}
	
	public static String match(List<String> list, String str) {
		String found = null;
        String lowerName = str.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (String item : list) {
            if (item.toLowerCase().startsWith(lowerName)) {
                int curDelta = item.length() - lowerName.length();
                if (curDelta < delta) {
                    found = item;
                    delta = curDelta;
                }
                if (curDelta == 0) break;
            }
        }
        return found;
	}	
}
