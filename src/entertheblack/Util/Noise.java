package entertheblack.Util;

import java.util.Random;

// Noise generation.
// TODO: Make more efficient.

public class Noise {
	private static Random r = new Random();
	private static final int FACTORX = 3743927, FACTORY = 524903093;
	private static int width = 32, height = 16;
	private static double[] getGridPoint(int x, int y, long seed) {
		double[] ret = new double[2];
		r.setSeed(seed+(x % width)*FACTORX+(y % height)*FACTORY);
		ret[0] = r.nextDouble()-0.5;
		ret[1] = r.nextDouble()-0.5;
		double size = Math.sqrt(ret[0]*ret[0] + ret[1]*ret[1]);
		ret[0] /= size;
		ret[1] /= size;
		return ret;
	}
	private static double dotProduct(double[] a, double[] b) {
		return a[0]*b[0] + a[1]*b[1];
	}
	private static double interpolate(double a, double b, double w) {
		return w*a + (1-w)*b;
	}
	public static double sCurve(double x) {
		return 3*x*x-2*x*x*x;
	}
	private static double noise(double x, double y, long seed) {
		double[] grid0 = getGridPoint((int)x, (int)y, seed);
		double[] grid1 = getGridPoint((int)x+1, (int)y, seed);
		double[] grid2 = getGridPoint((int)x, (int)y+1, seed);
		double[] grid3 = getGridPoint((int)x+1, (int)y+1, seed);
		double[] d0 = {x-(int)x, y-(int)y};
		double[] d1 = {x-(int)x-1, y-(int)y};
		double[] d2 = {x-(int)x, y-(int)y-1};
		double[] d3 = {x-(int)x-1, y-(int)y-1};
		double wx = sCurve(-d3[0]);
		double wy = sCurve(-d3[1]);
		double r0 = dotProduct(grid0, d0);
		double r1 = dotProduct(grid1, d1);
		double r2 = dotProduct(grid2, d2);
		double r3 = dotProduct(grid3, d3);
		double r01 = interpolate(r0, r1, wx);
		double r23 = interpolate(r2, r3, wx);
		double r0123 = interpolate(r01, r23, wy);
		return r0123;
	}
	public static double[][] generateNoiseMap(long seed, int width,int height) {
		double[][] ret = new double[width][height];
		for(int k = 4; k <= 11; k++) {
			Noise.height = 1 << k;
			Noise.width = 2 << k;
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					ret[i][j] += noise((double)i*Noise.width/width, (double)j*Noise.height/height, seed)/7;
				}
			}
		}
		return ret;
	}
}
