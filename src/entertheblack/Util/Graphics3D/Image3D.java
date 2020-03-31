package entertheblack.Util.Graphics3D;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

// A simple 3d renderer using the z-buffer method to display 3d triangles.
// Uses a BufferedImage to store the pixel data.

public class Image3D {
	int width, height;
	float[] z;
	BufferedImage drawable;
	byte[] pixels;
	public Image3D(int width, int height) {
		z = new float[width*height];
		this.width = width;
		this.height = height;
		drawable = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		pixels = ((DataBufferByte)drawable.getRaster().getDataBuffer()).getData();
		flush();
	}

	public BufferedImage drawToImage() {
		return drawable;
	}

	public void flush() {
		// Clear all arrays to prepare for the next rendering.
		int len = pixels.length;
		if (len > 0){
			pixels[0] = 0;
		}
		for (int i = 1; i < len; i += i) {
			System.arraycopy(pixels, 0, pixels, i, ((len - i) < i) ? (len - i) : i);
		}

		len = z.length;
		if (len > 0){
			z[0] = Integer.MAX_VALUE;
		}
		for (int i = 1; i < len; i += i) {
			System.arraycopy(z, 0, z, i, ((len - i) < i) ? (len - i) : i);
		}
	}

	public void drawTriangle(int[] x, int[] y, float[] z, int color, float light) {
		// Sort the points by their y coordinate:
		int smallest, second, third;
		if(y[0] <= y[1]) {
    		if(y[2] < y[1]) {
    			if(y[0] <= y[2]) {
					smallest = 0;
					second = 2;
				} else {
					smallest = 2;
					second = 0;
				}
				third = 1;
    		} else {
				smallest = 0;
				second = 1;
				third = 2;
    		}
    	} else {
    		if(y[1] <= y[2]) {
				smallest = 1;
    			if(y[0] <= y[2]) {
					second = 0;
					third = 2;
				} else {
					second = 2;
					third = 0;
				}
    		} else {
    			smallest = 2;
    			second = 1;
    			third = 0;
    		}
    	}
		// Determine the slopes of the three edges in x and z direction.(I know they are due to the projection not exactly linear, but for our purposes this is a good approximation)
		float m1 = (float)(x[second]-x[smallest])/(y[second]-y[smallest]);
		float m2 = (float)(x[third]-x[smallest])/(y[third]-y[smallest]);
		float m3 = (float)(x[third]-x[second])/(y[third]-y[second]);
		float m1z = (z[second]-z[smallest])/(y[second]-y[smallest]);
		float m2z = (z[third]-z[smallest])/(y[third]-y[smallest]);
		float m3z = (z[third]-z[second])/(y[third]-y[second]);
		// Determine color channels. Alpha value is ignored because this case doesn't need it.
		byte r = (byte)(((color >>> 16) & 255)*light);
		byte g = (byte)(((color >>> 8) & 255)*light);
		byte b = (byte)(((color >>> 0) & 255)*light);
		// Divide the triangle into two parts along the x coordinate of the second point which are easier to paint. 
		for(int py = y[smallest]; py < y[second]; py++) {
			int dy = py-y[smallest];
			int xMin = (int)(m1*dy+x[smallest]);
			int xMax = (int)(m2*dy+x[smallest]);
			float z0 = (m1z*dy+z[smallest]);
			float z1 = (m2z*dy+z[smallest]);
			if(xMin > xMax) { // Swap them:
				xMin ^= xMax;
				xMax ^= xMin;
				xMin ^= xMax;
				float local2 = z0;
				z0 = z1;
				z1 = local2;
			}
			float mz = (z1-z0)/(xMax-xMin);
			int index0 = py*width;
			for(int px = xMin; px < xMax; px++) {
				float pz = (mz*(px-xMin)+z0);
				if(pz > 0 && this.z[index0+px] > pz) {
					this.z[index0+px] = pz;
					int index1 = (index0 + px)*3;
					pixels[index1] = b;
					index1++;
					pixels[index1] = g;
					index1++;
					pixels[index1] = r;
				}
			}
		}
		for(int py = y[second]; py < y[third]; py++) {
			int dy0 = py-y[smallest];
			int dy = py-y[second];
			int xMin = (int)(m2*dy0+x[smallest]);
			int xMax = (int)(m3*dy+x[second]);
			float z0 = (m2z*dy0+z[smallest]);
			float z1 = (m3z*dy+z[second]);
			if(xMin > xMax) { // Swap them:
				xMin ^= xMax;
				xMax ^= xMin;
				xMin ^= xMax;
				float local2 = z0;
				z0 = z1;
				z1 = local2;
			}
			float mz = (float)(z1-z0)/(xMax-xMin);
			int index0 = py*width;
			for(int px = xMin; px < xMax; px++) {
				float pz = (mz*(px-xMin)+z0);
				if(pz > 0 && this.z[index0+px] > pz) {
					this.z[index0+px] = pz;
					int index1 = (index0 + px)*3;
					pixels[index1] = b;
					index1++;
					pixels[index1] = g;
					index1++;
					pixels[index1] = r;
				}
			}
		}
	}
}
