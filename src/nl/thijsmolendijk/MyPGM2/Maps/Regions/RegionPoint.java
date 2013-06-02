package nl.thijsmolendijk.MyPGM2.Maps.Regions;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class RegionPoint implements IRegion {
	private Location block;
	private boolean inv;
	
	public RegionPoint(Location block, boolean inv) {
		this.block = block;
		this.inv = inv;
	}
	
	@Override
	public boolean blockLiesInRegion(Block b) {
		return inv ? !(b.getLocation() == block) : (b.getLocation() == block);
	}

	@Override
	public List<Location> blocks() {
		return Arrays.asList(block);
	}

	@Override
	public void addBlocksToRegion(List<Location> blocks) {
		//Do nothing
	}

	@Override
	public void removeBlocksFromRegion(List<Location> blocks) {
		//Do nothing
	}

}
