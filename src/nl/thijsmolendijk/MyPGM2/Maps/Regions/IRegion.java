package nl.thijsmolendijk.MyPGM2.Maps.Regions;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public interface IRegion {
	public boolean blockLiesInRegion(Block b);
	public List<Location> blocks();
	public void addBlocksToRegion(List<Location> blocks);
	public void removeBlocksFromRegion(List<Location> blocks);
}
