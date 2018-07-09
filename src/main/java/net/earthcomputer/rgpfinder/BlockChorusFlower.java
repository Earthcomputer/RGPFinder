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
		for (int enumfacing : HOR_FACING) {
			if (enumfacing != excludingSide && worldIn.getBlock(x + X_OFF[enumfacing], y + Y_OFF[enumfacing],
					z + Z_OFF[enumfacing]) != Blocks.AIR) {
				return false;
			}
		}

		return true;
	}

	public static void generatePlant(World worldIn, int x, int y, int z, Random rand, int p_185603_3_) {
		worldIn.setBlock(x, y, z, Blocks.CHORUS_PLANT);
		growTreeRecursive(worldIn, x, y, z, rand, x, y, z, p_185603_3_, 0);
	}

	private static void growTreeRecursive(World worldIn, int p_185601_1_x, int p_185601_1_y, int p_185601_1_z,
			Random rand, int p_185601_3_x, int p_185601_3_y, int p_185601_3_z, int p_185601_4_, int p_185601_5_) {
		int i = rand.nextInt(4) + 1;

		if (p_185601_5_ == 0) {
			++i;
		}

		for (int j = 0; j < i; ++j) {
			if (!areAllNeighborsEmpty(worldIn, p_185601_1_x, p_185601_1_y + j + 1, p_185601_1_z, -1)) {
				return;
			}

			worldIn.setBlock(p_185601_1_x, p_185601_1_y + j + 1, p_185601_1_z, Blocks.CHORUS_PLANT);
		}

		boolean flag = false;

		if (p_185601_5_ < 4) {
			int l = rand.nextInt(4);

			if (p_185601_5_ == 0) {
				++l;
			}

			for (int k = 0; k < l; ++k) {
				int enumfacing = HOR_FACING[rand.nextInt(4)];
				int x1 = p_185601_1_x + X_OFF[enumfacing];
				int y1 = p_185601_1_y + i;
				int z1 = p_185601_1_z + Z_OFF[enumfacing];

				if (Math.abs(x1 - p_185601_3_x) < p_185601_4_ && Math.abs(x1 - p_185601_3_z) < p_185601_4_
						&& worldIn.getBlock(x1, y1, z1) == Blocks.AIR && worldIn.getBlock(x1, y1 - 1, z1) == Blocks.AIR
						&& areAllNeighborsEmpty(worldIn, x1, y1, z1, OPPOSITES[enumfacing])) {
					flag = true;
					worldIn.setBlock(x1, y1, x1, Blocks.CHORUS_PLANT);
					growTreeRecursive(worldIn, x1, y1, z1, rand, p_185601_3_x, p_185601_3_y, p_185601_3_z, p_185601_4_,
							p_185601_5_ + 1);
				}
			}
		}

		if (!flag) {
			worldIn.setBlock(p_185601_1_x, p_185601_1_y + i, p_185601_1_z, Blocks.CHORUS_FLOWER);
		}
	}
}
