package net.earthcomputer.rgpfinder;

import java.util.Random;

public class WorldGenEndIsland
{
    public boolean generate(World worldIn, Random rand, int x, int y, int z)
    {
        float f = (float)(rand.nextInt(3) + 4);

        for (int i = 0; f > 0.5F; --i)
        {
            for (int j = MathHelper.floor(-f); j <= MathHelper.ceil(f); ++j)
            {
                for (int k = MathHelper.floor(-f); k <= MathHelper.ceil(f); ++k)
                {
                    if ((float)(j * j + k * k) <= (f + 1.0F) * (f + 1.0F))
                    {
                    	worldIn.setBlock(x + j, y + i, z + k, Blocks.END_STONE);
                    }
                }
            }

            f = (float)((double)f - ((double)rand.nextInt(2) + 0.5D));
        }

        return true;
    }
}
