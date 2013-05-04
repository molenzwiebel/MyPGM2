package nl.thijsmolendijk.MyPGM2.Maps.Regions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class RegionSphere implements IRegion {
	private List<Location> range = new ArrayList<Location>();
	public Location base;
	public boolean inverted;
	public RegionSphere(Location center, int radius, boolean inv) {
		this.sphere(center, radius);
		this.inverted = inv;
		this.base = center;
	}

	@Override
	public boolean blockLiesInRegion(Block b) {
		return inverted ? !range.contains(b.getLocation()) : range.contains(b.getLocation());
	}

	private void sphere(Location loc, int r) {
		int bsize = r;
		int bx = loc.getBlockX();
		int by = loc.getBlockY();
		int bz = loc.getBlockZ();
		double zpow;
		double xpow;
		double bpow = Math.pow(r, 2);
		for (int z = -bsize; z <= bsize; z++) {
			zpow = Math.pow(z, 2);
			for (int x = -bsize; x <= bsize; x++) {
				xpow = Math.pow(x, 2);
				for (int y = -bsize; y <= bsize; y++) {
					if ((xpow + Math.pow(y, 2) + zpow) <= bpow) {
						range.remove(new Location(loc.getWorld(), bx + x, by + y, bz + z));
						range.add(new Location(loc.getWorld(), bx + x, by + y, bz + z));
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
