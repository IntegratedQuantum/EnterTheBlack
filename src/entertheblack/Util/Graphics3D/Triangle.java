package entertheblack.Util.Graphics3D;

import java.util.Random;

// Implements a triangle and handles the recursive terrain generation.

public class Triangle {
	
	// Functions needed to recursively generate the landscape:
	private static Vector getBetween(Vector v1, Vector v2, Random rand, Vector[] vectors, int[] index) {
		Vector ret;
		// Check if the midpoint is already calculated:
		if((ret = v1.hasMidPointBetween(v2)) != null) {
			return ret;
		}
		ret = new Vector(v1);
		double factor = v1.minus(v2).value()/32; // Scale with distance so the changed get smaller the smaller the triangle is.
		double factor2 = Math.pow(v1.minus(v2).value(), 0.5)/2000; // Scales with sqrt(distance) to reduce artifacts.
		ret.plusEquals(v2);
		ret.timesEquals((v1.value()+v2.value())/2/ret.value());
		// Offset the midpoint in a random direction:
		ret.x += factor*(rand.nextDouble()-0.5);
		ret.y += factor*(rand.nextDouble()-0.5);
		ret.z += factor*(rand.nextDouble()-0.5);
		// Scale the vector by a random value relative to the planets center:
		ret.timesEquals(1 + factor2*(rand.nextDouble()-0.5));
		vectors[index[1]++] = ret;
		v1.addConnection(v2, ret);
		v2.addConnection(v1, ret);
		return ret;
	}
	public static void iterate(Triangle[] oldArray, Triangle[] nextArray, Vector[] vectors, int[] index, Random rand) {
		for(Triangle t : oldArray) {
			generate(nextArray, vectors, index, t.points[0], t.points[1], t.points[2], rand);
		}
	}
	public static void generate(Triangle[] array, Vector[] vectors, int[] index, Vector v1, Vector v2, Vector v3, Random rand) {
		Vector int12 = getBetween(v1, v2, rand, vectors, index);
		Vector int13 = getBetween(v1, v3, rand, vectors, index);
		Vector int23 = getBetween(v2, v3, rand, vectors, index);
		array[index[0]++] = new Triangle(v1, int12, int13);
		array[index[0]++] = new Triangle(v2, int12, int23);
		array[index[0]++] = new Triangle(v3, int13, int23);
		array[index[0]++] = new Triangle(int12, int13, int23);
	}
	
	
	Vector[] points = new Vector[3];
	Vector orthogonal;
	public Triangle(Vector p1, Vector p2, Vector p3) {
		points[0] = p1;
		points[1] = p2;
		points[2] = p3;
	}
	
	static long rotate(long in, int amount) {
		return (in << amount) | (in >>> (64-amount));
	}
	
	public int getColor(double depth, double y) {
		depth /= 2;
		y /= 150;
		y *= y*y;
		if(depth <= 0.001) {
			int other = (int)((y-10)*255);
			if(other < 0) other = 0;
			if(other > 255) other = 255;
			return 0xff0000ff | (other << 16) | (other << 8);
		} else {
			depth += y;
			int g = (int)(1024/(depth+1) + depth*20);
			if(g > 255)
				g = 255;
			int r = (int)(depth*80);
			if(r > 255)
				r = 255;
			if(r < 0)
				r = 0;
			int b = (int)(depth*20);
			if(b > 255)
				b = 255;
			return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
		}
	}
	
	public void calculateOrthogonal() {
		orthogonal = (points[1].minus(points[0])).cross(points[2].minus(points[0]));
		if(orthogonal.dot(points[0]) < 0) {
			orthogonal.negate(); // The lazy way of making sure the orthgonal is pointed in the right direction.
		}
		orthogonal.timesEquals(1/orthogonal.value());
	}

	public void paint(Image3D image, Camera cam) {
		if(orthogonal.dot(cam.sight) > 0) return; // Only draw triangles facing towards the camera.
		double depth = (points[0].value()+points[1].value()+points[2].value())/3 - 400;
		int color = getColor(depth, Math.abs(points[0].y+points[1].y+points[2].y)/3);
		int [] x = new int[3];
		int [] y = new int[3];
		float [] z = new float[3];
		for(int i = 0; i < 3; i++) {
			x[i] = 200+(int)points[i].px;
			y[i] = 200+(int)points[i].py;
			z[i] = (float)points[i].pz;
		}
		double view = orthogonal.z;
		float lighting = (float)(view/2 + 0.5);
		// Make sure it is inside bounds:
		if(lighting < 0) lighting = 0;
		
		image.drawTriangle(x, y, z, color, lighting);
	}
}