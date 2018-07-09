package net.earthcomputer.rgpfinder;

import java.util.Random;

public class ChunkGeneratorEnd
{
    private final Random rand;
    private final NoiseGeneratorOctaves lperlinNoise1;
    private final NoiseGeneratorOctaves lperlinNoise2;
    private final NoiseGeneratorOctaves perlinNoise1;

    public NoiseGeneratorOctaves noiseGen5;

    public NoiseGeneratorOctaves noiseGen6;

    private final NoiseGeneratorSimplex islandNoise;
    private double[] buffer;

    double[] pnr;
    double[] ar;
    double[] br;
    private final WorldGenEndIsland endIslands = new WorldGenEndIsland();
    
    public boolean hasGeneratedGateway = false;
    public int relGatewayX;
    public int relGatewayY;
    public int relGatewayZ;

    public ChunkGeneratorEnd(long worldSeed)
    {
        this.rand = new Random(worldSeed);
        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.islandNoise = new NoiseGeneratorSimplex(this.rand);
    }

    /**
     * Generates a bare-bones chunk of nothing but stone or ocean blocks, formed, but featureless.
     */
    public void setBlocksInChunk(int x, int z, World primer)
    {
        int i = 2;
        int j = 3;
        int k = 33;
        int l = 3;
        this.buffer = this.getHeights(this.buffer, x * 2, 0, z * 2, 3, 33, 3);

        for (int i1 = 0; i1 < 2; ++i1)
        {
            for (int j1 = 0; j1 < 2; ++j1)
            {
                for (int k1 = 0; k1 < 32; ++k1)
                {
                    double d0 = 0.25D;
                    double d1 = this.buffer[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 0];
                    double d2 = this.buffer[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 0];
                    double d3 = this.buffer[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 0];
                    double d4 = this.buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 0];
                    double d5 = (this.buffer[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 1] - d1) * 0.25D;
                    double d6 = (this.buffer[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 1] - d2) * 0.25D;
                    double d7 = (this.buffer[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 1] - d3) * 0.25D;
                    double d8 = (this.buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 1] - d4) * 0.25D;

                    for (int l1 = 0; l1 < 4; ++l1)
                    {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.125D;
                        double d13 = (d4 - d2) * 0.125D;

                        for (int i2 = 0; i2 < 8; ++i2)
                        {
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.125D;

                            for (int j2 = 0; j2 < 8; ++j2)
                            {
                            	byte block = Blocks.AIR;

                                if (d15 > 0.0D)
                                {
                                	block = Blocks.END_STONE;
                                }

                                int k2 = i2 + i1 * 8;
                                int l2 = l1 + k1 * 4;
                                int i3 = j2 + j1 * 8;
                                primer.setBlock(x * 16 + k2, l2, z * 16 + i3, block);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void buildSurfaces(World primer, int x, int z)
    {
        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                int k = 1;
                int l = -1;
                byte iblockstate = Blocks.END_STONE;
                byte iblockstate1 = Blocks.END_STONE;

                for (int i1 = 127; i1 >= 0; --i1)
                {
                    byte iblockstate2 = primer.getBlock(x * 16 + i, i1, z * 16 + j);

                    if (iblockstate2 == Blocks.AIR)
                    {
                        l = -1;
                    }
                    /*
                    else if (iblockstate2.getBlock() == Blocks.STONE)
                    {
                        if (l == -1)
                        {
                            l = 1;

                            if (i1 >= 0)
                            {
                                primer.setBlockState(i, i1, j, iblockstate);
                            }
                            else
                            {
                                primer.setBlockState(i, i1, j, iblockstate1);
                            }
                        }
                        else if (l > 0)
                        {
                            --l;
                            primer.setBlockState(i, i1, j, iblockstate1);
                        }
                    }
                    */
                }
            }
        }
    }

    public void resetSeed(int x, int z) {
    	this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
    }
    
    public void generateChunk(World world, int x, int z)
    {
        resetSeed(x, z);
        this.setBlocksInChunk(x, z, world);
        this.buildSurfaces(world, x, z);
    }

    private float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_)
    {
        float f = (float)(p_185960_1_ * 2 + p_185960_3_);
        float f1 = (float)(p_185960_2_ * 2 + p_185960_4_);
        float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;

        if (f2 > 80.0F)
        {
            f2 = 80.0F;
        }

        if (f2 < -100.0F)
        {
            f2 = -100.0F;
        }

        for (int i = -12; i <= 12; ++i)
        {
            for (int j = -12; j <= 12; ++j)
            {
                long k = (long)(p_185960_1_ + i);
                long l = (long)(p_185960_2_ + j);

                if (k * k + l * l > 4096L && this.islandNoise.getValue((double)k, (double)l) < -0.8999999761581421D)
                {
                    float f3 = (MathHelper.abs((float)k) * 3439.0F + MathHelper.abs((float)l) * 147.0F) % 13.0F + 9.0F;
                    f = (float)(p_185960_3_ - i * 2);
                    f1 = (float)(p_185960_4_ - j * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * f3;

                    if (f4 > 80.0F)
                    {
                        f4 = 80.0F;
                    }

                    if (f4 < -100.0F)
                    {
                        f4 = -100.0F;
                    }

                    if (f4 > f2)
                    {
                        f2 = f4;
                    }
                }
            }
        }

        return f2;
    }

    public boolean isIslandChunk(int p_185961_1_, int p_185961_2_)
    {
        return (long)p_185961_1_ * (long)p_185961_1_ + (long)p_185961_2_ * (long)p_185961_2_ > 4096L && this.getIslandHeightValue(p_185961_1_, p_185961_2_, 1, 1) >= 0.0F;
    }

    private double[] getHeights(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_)
    {
        if (p_185963_1_ == null)
        {
            p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        d0 = d0 * 2.0D;
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
        int i = p_185963_2_ / 2;
        int j = p_185963_4_ / 2;
        int k = 0;

        for (int l = 0; l < p_185963_5_; ++l)
        {
            for (int i1 = 0; i1 < p_185963_7_; ++i1)
            {
                float f = this.getIslandHeightValue(i, j, l, i1);

                for (int j1 = 0; j1 < p_185963_6_; ++j1)
                {
                    double d2 = this.ar[k] / 512.0D;
                    double d3 = this.br[k] / 512.0D;
                    double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;
                    double d4;

                    if (d5 < 0.0D)
                    {
                        d4 = d2;
                    }
                    else if (d5 > 1.0D)
                    {
                        d4 = d3;
                    }
                    else
                    {
                        d4 = d2 + (d3 - d2) * d5;
                    }

                    d4 = d4 - 8.0D;
                    d4 = d4 + (double)f;
                    int k1 = 2;

                    if (j1 > p_185963_6_ / 2 - k1)
                    {
                        double d6 = (double)((float)(j1 - (p_185963_6_ / 2 - k1)) / 64.0F);
                        d6 = MathHelper.clamp(d6, 0.0D, 1.0D);
                        d4 = d4 * (1.0D - d6) + -3000.0D * d6;
                    }

                    k1 = 8;

                    if (j1 < k1)
                    {
                        double d7 = (double)((float)(k1 - j1) / ((float)k1 - 1.0F));
                        d4 = d4 * (1.0D - d7) + -30.0D * d7;
                    }

                    p_185963_1_[k] = d4;
                    ++k;
                }
            }
        }

        return p_185963_1_;
    }

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     */
    public void populate(World world, int x, int z)
    {
    	int blockX = x * 16;
    	int blockZ = z * 16;

        long distanceSqFromSpawn = (long)x * (long)x + (long)z * (long)z;

        if (distanceSqFromSpawn > 64 * 64)
        {
            float f = this.getIslandHeightValue(x, z, 1, 1);

            if (f < -20.0F && this.rand.nextInt(14) == 0)
            {
                this.endIslands.generate(world, this.rand, blockX + this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), blockZ + this.rand.nextInt(16) + 8);

                if (this.rand.nextInt(4) == 0)
                {
                    this.endIslands.generate(world, this.rand, blockX + this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), blockZ + this.rand.nextInt(16) + 8);
                }
            }

            if (this.getIslandHeightValue(x, z, 1, 1) > 40.0F)
            {
                int j = this.rand.nextInt(5);

                for (int k = 0; k < j; ++k)
                {
                    int l = this.rand.nextInt(16) + 8;
                    int i1 = this.rand.nextInt(16) + 8;
                    int j1 = world.getHeight(blockX + l, blockZ + i1);

                    if (j1 > 0)
                    {
                        int k1 = j1 - 1;

                        if (world.getBlock(blockX + l, k1 + 1, blockZ + i1) == Blocks.AIR && world.getBlock(blockX + l, k1, blockZ + i1) == Blocks.END_STONE)
                        {
                            BlockChorusFlower.generatePlant(world, blockX + l, k1 + 1, blockZ + i1, this.rand, 8);
                        }
                    }
                }

                if (this.rand.nextInt(700) == 0)
                {
                    int l1 = this.rand.nextInt(16) + 8;
                    int i2 = this.rand.nextInt(16) + 8;
                    int j2 = world.getHeight(blockX + l1, blockZ + i2);

                    if (j2 > 0)
                    {
                        int k2 = j2 + 3 + this.rand.nextInt(7);
                        hasGeneratedGateway = true;
                        relGatewayX = l1;
                        relGatewayY = k2;
                        relGatewayZ = i2;
                    }
                }
            }
        }
    }
}
