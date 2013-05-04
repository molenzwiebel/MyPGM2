package nl.thijsmolendijk.MyPGM2.Maps.Regions;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;


public class RegionRectangle implements IRegion {
	public Location min;
	public Location max;
	public boolean inverted;
	public List<Location> range = new ArrayList<Location>();
	public RegionRectangle(Location l1, Location l2, boolean inv) {
		this.min = l1;
		this.max = l2;
		this.inverted = inv;
		this.range = this.loopThrough(l1, l2, l1.getWorld());
	}

	@Override
	public boolean blockLiesInRegion(Block b) {
		return inverted ? !range.contains(b.getLocation()) : range.contains(b.getLocation());
	}

	@Override
	public List<Location> blocks() {
		return this.range;
	}
	
	
	//HELPER METHOD!!
	public List<Location> loopThrough(Location loc1, Location loc2, World w) {
		List<Location> toReturn = new ArrayList<Location>();
	    int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
	    minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
	    maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
	    maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
	    for(int x = minx; x<=maxx;x++){
	        for(int y = 0; y<=256;y++){
	            for(int z = minz; z<=maxz;z++){
	                Block b = w.getBlockAt(x, y, z);
	                toReturn.add(b.getLocation());
	            }
	        }
	    }
	    return toReturn;
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
