package nl.thijsmolendijk.MyPGM2.Maps.Regions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class RegionComplement implements IRegion {
	public List<Location> range = new ArrayList<Location>();
	public boolean inverted;
	@Override
	public boolean blockLiesInRegion(Block b) {
		return inverted ? !range.contains(b.getLocation()) : range.contains(b.getLocation());
	}
	
	public RegionComplement(IRegion start, boolean inv) {
		this.range = start.blocks();
		this.inverted = inv;
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
