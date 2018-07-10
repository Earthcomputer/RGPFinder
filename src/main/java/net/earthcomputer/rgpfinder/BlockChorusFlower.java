package net.earthcomputer.rgpfinder;

import java.util.Random;

public class BlockChorusFlower {

	private static final int NORTH = 0,
			EAST = 1,
			SOUTH = 2,
			WEST = 3,
			DOWN = 4,
			UP = 5;
	private static final int[] HOR_FACING = { NORTH, EAST, SOUTH, WEST };
	private static final int[] OPPOSITES = { SOUTH, WEST, NORTH, EAST, UP, DOWN };
	private static final int[] X_OFF = { 0, 1, 0, -1, 0, 0 };
	private static final int[] Y_OFF = { 0, 0, 0, 0, -1, 1 };
	private static final int[] Z_OFF = { -1, 0, 1, 0, 0, 0 };

	private static boolean areAllNeighborsEmpty(World worldIn, int x, int y, int z, int excludingSide) {
		for (int side : HOR_FACING) {
			if (side != excludingSide && worldIn.getBlock(x + X_OFF[side], y + Y_OFF[side],
					z + Z_OFF[side]) != Blocks.AIR) {
				return false;
			}
		}

		return true;
	}

	public static void generatePlant(World world, int x, int y, int z, Random rand, int maxRadius) {
		world.setBlock(x, y, z, Blocks.CHORUS_PLANT);
		growTreeRecursive(world, x, y, z, rand, x, y, z, maxRadius, 0);
	}

	private static void growTreeRecursive(World world, int branchX, int branchY, int branchZ, Random rand, int baseX,
			int baseY, int baseZ, int maxRadius, int depth) {
		int branchHeight = rand.nextInt(4) + 1;

		if (depth == 0) {
			branchHeight++;
		}

		for (int dy = 0; dy < branchHeight; dy++) {
			if (!areAllNeighborsEmpty(world, branchX, branchY + dy + 1, branchZ, -1)) {
				return;
			}

			world.setBlock(branchX, branchY + dy + 1, branchZ, Blocks.CHORUS_PLANT);
		}

		boolean branchedOff = false;

		if (depth < 4) {
			int numBranches = rand.nextInt(4);

			if (depth == 0) {
				numBranches++;
			}

			for (int i = 0; i < numBranches; i++) {
				int branchSide = HOR_FACING[rand.nextInt(4)];
				int nextBranchX = branchX + X_OFF[branchSide];
				int nextBranchY = branchY + branchHeight;
				int nextBranchZ = branchZ + Z_OFF[branchSide];

				if (Math.abs(nextBranchX - baseX) < maxRadius && Math.abs(nextBranchZ - baseZ) < maxRadius
						&& world.getBlock(nextBranchX, nextBranchY, nextBranchZ) == Blocks.AIR
						&& world.getBlock(nextBranchX, nextBranchY - 1, nextBranchZ) == Blocks.AIR
						&& areAllNeighborsEmpty(world, nextBranchX, nextBranchY, nextBranchZ, OPPOSITES[branchSide])) {
					branchedOff = true;
					world.setBlock(nextBranchX, nextBranchY, nextBranchZ, Blocks.CHORUS_PLANT);
					growTreeRecursive(world, nextBranchX, nextBranchY, nextBranchZ, rand, baseX, baseY, baseZ,
							maxRadius, depth + 1);
				}
			}
		}

		if (!branchedOff) {
			world.setBlock(branchX, branchY + branchHeight, branchZ, Blocks.CHORUS_FLOWER);
		}
	}
}
