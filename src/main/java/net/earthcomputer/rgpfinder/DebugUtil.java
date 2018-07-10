package net.earthcomputer.rgpfinder;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DebugUtil {

	private static final Field RANDOM_SEED;
	
	static {
		try {
			RANDOM_SEED = Random.class.getDeclaredField("seed");
		} catch (Exception e) {
			throw new AssertionError(e);
		}
		RANDOM_SEED.setAccessible(true);
	}
	
	public static long getSeed(Random rand) {
		try {
			return ((AtomicLong) RANDOM_SEED.get(rand)).get();
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}
	
	public static void main(String[] args) {
		ChunkGeneratorEnd chunkGen = new ChunkGeneratorEnd(1563854987194673247L);
		World world = World.alloc(72 * 16, 0, 17 * 16, 32, 256, 32);
		chunkGen.generateChunk(world, 72, 17);
		chunkGen.generateChunk(world, 73, 17);
		chunkGen.generateChunk(world, 72, 18);
		chunkGen.generateChunk(world, 73, 18);
		
		chunkGen.resetSeed(101, 12);
		chunkGen.populate(world, 72, 17);
	}
	
}
