package nl.thijsmolendijk.MyPGM2;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class EmptyWorldGenerator extends ChunkGenerator {
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
	{
		byte[][] result = new byte[world.getMaxHeight() / 16][]; //world height / chunk part height (=16, look above)
		return result;
	}
}

