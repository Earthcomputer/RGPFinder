package net.earthcomputer.rgpfinder;

public class World {

	private int minX;
	private int minY;
	private int minZ;
	private int xSize;
	private int ySize;
	private int zSize;
	private byte[][][] blocks;
	
	private World() {}
	
	public static World alloc(int minX, int minY, int minZ, int xSize, int ySize, int zSize) {
		World world = new World();
		world.minX = minX;
		world.minY = minY;
		world.minZ = minZ;
		world.xSize = xSize;
		world.ySize = ySize;
		world.zSize = zSize;
		world.blocks = new byte[xSize][ySize][zSize];
		return world;
	}
	
	public World copy() {
		World world = alloc(minX, minY, minZ, xSize, ySize, zSize);
		for (int x = 0; x < xSize; x++)
			for (int y = 0; y < ySize; y++)
				for (int z = 0; z < zSize; z++)
					world.blocks[x][y][z] = blocks[x][y][z];
		return world;
	}
	
	public void setBlock(int x, int y, int z, byte block) {
		checkBounds(x, y, z);
		blocks[x - minX][y - minY][z - minZ] = block;
	}
	
	public byte getBlock(int x, int y, int z) {
		checkBounds(x, y, z);
		return blocks[x - minX][y - minY][z - minZ];
	}
	
	private void checkBounds(int x, int y, int z) {
		if (x < minX) {
			throw new IndexOutOfBoundsException("x = " + x + ", minX = " + minX);
		}
		if (y < minY) {
			throw new IndexOutOfBoundsException("y = " + y + ", minY = " + minY);
		}
		if (z < minZ) {
			throw new IndexOutOfBoundsException("z = " + z + ", minZ = " + minZ);
		}
		if (x >= minX + xSize) {
			throw new IndexOutOfBoundsException("x = " + x + ", minX = " + minX + ", xSize = " + xSize);
		}
		if (y >= minY + ySize) {
			throw new IndexOutOfBoundsException("y = " + y + ", minY = " + minY + ", ySize = " + ySize);
		}
		if (z >= minZ + zSize) {
			throw new IndexOutOfBoundsException("z = " + z + ", minZ = " + minZ + ", zSize = " + zSize);
		}
	}
	
	public int getHeight(int x, int z) {
		for (int y = 256; y > 0; y--) {
			if (getBlock(x, y - 1, z) == Blocks.END_STONE)
				return y;
		}
		return 0;
	}
	
}
