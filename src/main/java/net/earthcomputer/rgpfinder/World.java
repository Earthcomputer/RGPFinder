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
	
	public void setBlock(int x, int y, int z, byte block) {
		if (x < minX || y < minY || z < minZ || x >= minX + xSize || y >= minY + ySize || z >= minZ + zSize) {
			int newMinX = Math.min(minX, x);
			int newMinY = Math.min(minY, y);
			int newMinZ = Math.min(minZ, z);
			int newXSize = Math.max(xSize, x - minX + 1);
			int newYSize = Math.max(ySize, y - minY + 1);
			int newZSize = Math.max(zSize, z - minZ + 1);
			byte[][][] newBlocks = new byte[newXSize][newYSize][newZSize];
			for (int dx = minX; dx < minX + xSize; dx++) {
				for (int dy = minY; dy < minY + ySize; dy++) {
					for (int dz = minZ; dz < minZ + zSize; dz++) {
						newBlocks[dx - minX + newMinX][dy - minY + newMinY][dz - minZ + newMinZ] = blocks[x][y][z];
					}
				}
			}
			minX = newMinX;
			minY = newMinY;
			minZ = newMinZ;
			xSize = newXSize;
			ySize = newYSize;
			zSize = newZSize;
			blocks = newBlocks;
		}
		blocks[x - minX][y - minY][z - minZ] = block;
	}
	
	public byte getBlock(int x, int y, int z) {
		if (x < minX || y < minY || z < minZ || x >= minX + xSize || y >= minY + ySize || z >= minZ + zSize) {
			return Blocks.AIR;
		}
		return blocks[x - minX][y - minY][z - minZ];
	}
	
	public int getHeight(int x, int z) {
		for (int y = 256; y > 0; y--) {
			if (getBlock(x, y - 1, z) == Blocks.END_STONE)
				return y;
		}
		return 0;
	}
	
}
