package nl.thijsmolendijk.MyPGM2.Maps.Regions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RegionCilinder implements IRegion {
	private List<Location> range = new ArrayList<Location>();
	public Location base;
	public boolean inverted;
	public RegionCilinder(Location center, int radius, int height, boolean inv) {
		this.cylinder(center, radius, height);
		this.base = center;
		this.inverted = inv;
	}

	@Override
	public boolean blockLiesInRegion(Block b) {
		return inverted ? range.contains(b.getLocation()) : !range.contains(b.getLocation());
	}

	private void cylinder(Location loc, int r, int h) {
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		World w = loc.getWorld();
		int rSquared = r * r;

		for (int x = cx - r; x <= cx +r; x++) {
			for (int y = cy; y <= cy +h; y++) {
				for (int z = cz - r; z <= cz +r; z++) {
					if ((cx - x) * (cx -x) + (cz - z) * (cz - z) <= rSquared) {
						range.add(new Location(w, x, y, z));
					}
				}
			}
		}
	}

	@Override
	public List<Location> blocks() {
		return range;
	}
	
	@Override
	public void addBlocksToRegion(List<Location> blocks) {
		this.range.addAll(blocks);
	}

	@Override
	public void removeBlocksFromRegion(List<Location> blocks) {
		this.range.removeAll(blocks);
	}
}
