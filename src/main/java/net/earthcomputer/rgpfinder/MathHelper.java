package net.earthcomputer.rgpfinder;

public class MathHelper {

	private MathHelper() {}
	
    public static long lfloor(double value)
    {
        long i = (long)value;
        return value < (double)i ? i - 1L : i;
    }
    
    public static int floor(float value)
    {
        int i = (int)value;
        return value < (float)i ? i - 1 : i;
    }
    
    public static int ceil(float value)
    {
        int i = (int)value;
        return value > (float)i ? i + 1 : i;
    }
    
    public static float sqrt(float value)
    {
        return (float)Math.sqrt((double)value);
    }
    
    public static float abs(float value)
    {
        return value >= 0.0F ? value : -value;
    }
    
    public static double clamp(double num, double min, double max)
    {
        if (num < min)
        {
            return min;
        }
        else
        {
            return num > max ? max : num;
        }
    }
	
}
