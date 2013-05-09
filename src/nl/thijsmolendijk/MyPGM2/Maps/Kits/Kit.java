package nl.thijsmolendijk.MyPGM2.Maps.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Kit {
	public ItemStack[] inventoryContents;
	public ItemStack[] armorContents;
	public List<PotionEffect> potionEffects = new ArrayList<PotionEffect>();
	
	public Kit(Kit parent) {
		this.inventoryContents = parent.inventoryContents;
		this.armorContents = parent.armorContents;
		this.potionEffects = parent.potionEffects;
	}
	
	public Kit(ItemStack[] inv, ItemStack[] armor) {
		this.inventoryContents = inv;
		this.armorContents = armor;
	}

	public Kit() {
		this.armorContents = new ItemStack[4];
		this.inventoryContents = new ItemStack[36];
	}	
	
	public void give(Player p) {
		for (PotionEffect e : p.getActivePotionEffects()) 
			p.removePotionEffect(e.getType());
		for (PotionEffect eff : potionEffects) 
			p.addPotionEffect(eff);
		p.getInventory().setArmorContents(armorContents);
		for (int i = 0; i < 36; i++) {
			p.getInventory().setItem(i, this.inventoryContents[i]);
		}
	}
}
