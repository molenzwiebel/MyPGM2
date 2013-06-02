package nl.thijsmolendijk.MyPGM2.Maps.Kits;

import java.awt.Color;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KitUtils {

	public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	public static Kit parseKit(Element tag, Kit parent) {
		Kit kit;
		if (parent == null)
			kit = new Kit();
		else
			kit = new Kit(parent);

		//Items
		Inventory playerInv = Bukkit.createInventory(null, 36);
		playerInv.setContents(kit.inventoryContents);
		NodeList items = tag.getElementsByTagName("item");
		for (int i = 0; i < items.getLength(); i++) {
			Element item = (Element) items.item(i);
			playerInv = parseItem(item, playerInv);
		}
		kit.inventoryContents = playerInv.getContents();
		
		//Armor
		ItemStack[] armor = new ItemStack[4];
		if (tag.getElementsByTagName("helmet").getLength() > 0) {
			armor[3] = parseItemNI(tag.getElementsByTagName("helmet").item(0));
		} else
			armor[3] = kit.armorContents[3];
		if (tag.getElementsByTagName("chestplate").getLength() > 0) {
			armor[2] = parseItemNI(tag.getElementsByTagName("chestplate").item(0));
		} else
			armor[2] = kit.armorContents[2];
		if (tag.getElementsByTagName("leggings").getLength() > 0) {
			armor[1] = parseItemNI(tag.getElementsByTagName("leggings").item(0));
		} else
			armor[1] = kit.armorContents[1];
		if (tag.getElementsByTagName("boots").getLength() > 0) {
			armor[0] = parseItemNI(tag.getElementsByTagName("boots").item(0));
		} else
			armor[0] = kit.armorContents[0];
		kit.armorContents = armor;
		
		//Potions
		NodeList pots = tag.getElementsByTagName("potion");
		for (int i = 0; i < pots.getLength(); i++) {
			Element pot = (Element) pots.item(i);
			kit.potionEffects.add(parsePotion(pot));
		}	
		
		return kit;
	}
	
	private static Inventory parseItem(Element tag, Inventory inv) {
		ItemStack toReturn = new ItemStack(Material.AIR);
		String materialName = tag.getTextContent().replace(" ", "_").toUpperCase();
		Material mat = Material.matchMaterial(materialName);
		if (mat == null)
			mat = Material.getMaterial(Integer.parseInt(materialName));
		toReturn.setType(mat);
		
		if (tag.hasAttribute("amount"))
			toReturn.setAmount(Integer.parseInt(tag.getAttribute("amount")));
		else
			toReturn.setAmount(1);
		
		if (tag.hasAttribute("damage"))
			toReturn.setDurability((short)Integer.parseInt(tag.getAttribute("damage")));
		else
			toReturn.setDurability((short)0);
		ItemMeta m = toReturn.getItemMeta();
		if (tag.hasAttribute("name"))
			m.setDisplayName(tag.getAttribute("name"));
		if (tag.hasAttribute("lore"))
			m.setLore(Arrays.asList(tag.getAttribute("name")));
		toReturn.setItemMeta(m);
		if (tag.hasAttribute("enchantment")) {
			for (int i = 0; i < tag.getAttribute("enchantment").split(";").length; i++) {
				String rawEnch = tag.getAttribute("enchantment").split(";")[i];
				String enchantment = rawEnch.split(":")[0].replace(" ", "_").toUpperCase();
				String level;
				if (rawEnch.split(":").length == 2)
					level = rawEnch.split(":")[1];
				else
					level = "1";
				Enchantment e = Enchantment.getByName(enchantment);
				toReturn.addUnsafeEnchantment(e, Integer.parseInt(level));
			}
		}
		
		if (tag.hasAttribute("color"))
			toReturn = applyColor(toReturn, tag.getAttribute("color"));
			
		inv.setItem(Integer.parseInt(tag.getAttribute("slot")), toReturn);
		return inv;
	}
	private static ItemStack applyColor(ItemStack item, String color) {
		ItemMeta m = item.getItemMeta();
		if (m instanceof LeatherArmorMeta) {
			LeatherArmorMeta lm = (LeatherArmorMeta) m;
			Color javaColor = hex2Rgb("#" + color);
			lm.setColor(org.bukkit.Color.fromRGB(javaColor.getRed(), javaColor.getGreen(), javaColor.getBlue()));
			item.setItemMeta(lm);
		}
		return item;
	}
	
	private static ItemStack parseItemNI(Node tag3) {
		Element tag = (Element) tag3;
		ItemStack toReturn = new ItemStack(Material.AIR);
		String materialName = tag.getTextContent().replace(" ", "_").toUpperCase();
		
		Material mat = Material.matchMaterial(materialName);
		if (mat == null)
			mat = Material.getMaterial(Integer.parseInt(materialName));
		toReturn.setType(mat);
		
		if (tag.hasAttribute("amount"))
			toReturn.setAmount(Integer.parseInt(tag.getAttribute("amount")));
		else
			toReturn.setAmount(1);
		
		if (tag.hasAttribute("damage"))
			toReturn.setDurability((short)Integer.parseInt(tag.getAttribute("damage")));
		else
			toReturn.setDurability((short)0);
		
		ItemMeta m = toReturn.getItemMeta();
		if (tag.hasAttribute("name"))
			m.setDisplayName(tag.getAttribute("name"));
		if (tag.hasAttribute("lore"))
			m.setLore(Arrays.asList(tag.getAttribute("name")));
		toReturn.setItemMeta(m);
		if (tag.hasAttribute("enchantment")) {
			for (int i = 0; i < tag.getAttribute("enchantment").split(";").length; i++) {
				String rawEnch = tag.getAttribute("enchantment").split(";")[i];
				String enchantment = rawEnch.split(":")[0].replace(" ", "_").toUpperCase();
				String level = rawEnch.split(":")[1];
				Enchantment e = Enchantment.getByName(enchantment);
				toReturn.addUnsafeEnchantment(e, Integer.parseInt(level));
			}
		}
		
		if (tag.hasAttribute("color"))
			toReturn = applyColor(toReturn, tag.getAttribute("color"));
		return toReturn;
	}
	
	private static PotionEffect parsePotion(Element e) {
		String potion = e.getTextContent().replace(" ", "_").toUpperCase();
		PotionEffectType eff = PotionEffectType.getByName(potion);
		int amp, dur;
		if (e.hasAttribute("amplifier"))
			amp = Integer.parseInt(e.getAttribute("amplifier"));
		else
			amp = 1;
		if (e.hasAttribute("duration"))
			dur = Integer.parseInt(e.getAttribute("duration"));
		else 
			dur = 1;
		if (dur == -1)
			dur = Integer.MAX_VALUE;
		System.out.println(""+eff.toString()+", "+dur+", "+amp);
		return new PotionEffect(eff, dur, amp, true);
	}
}