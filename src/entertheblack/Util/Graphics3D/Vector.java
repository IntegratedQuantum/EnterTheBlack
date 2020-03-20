package entertheblack.Util.Graphics3D;

// Represents a 3D vector of doubles and its coordinates when projected onto the screen.

public class Vector {
	double x, y, z, px, py, pz;
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	Vector() {}
	Vector(Vector other) {
		x = other.x;
		y = other.y;
		z = other.z;
		px = other.px;
		py = other.py;
		pz = other.pz;
	}
	Vector(Vector other, double factor) {
		x = other.x*factor;
		y = other.y*factor;
		z = other.z*factor;
		px = other.px;
		py = other.py;
		pz = other.pz;
	}
	void negate() {
		x = -x;
		y = -y;
		z = -z;
	}
	void plusEquals(Vector v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	void minusEquals(Vector v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}
	Vector minus(Vector v) {
		return new Vector(x-v.x, y-v.y, z-v.z);
	}
	public void timesEquals(double fact) {
		x *= fact;
		y *= fact;
		z *= fact;
	}
	Vector cross(Vector v) {
		return new Vector(y*v.z-z*v.y, z*v.x-x*v.z, x*v.y-y*v.x);
	}
	double dot(Vector v) {
		return x*v.x+y*v.y+z*v.z;
	}
	double distance(Vector v) {
		double x = this.x-v.x;
		double y = this.y-v.y;
		double z = this.z-v.z;
		return x*x+y*y+z*z;
	}
	double valueSquared() {
		return x*x+y*y+z*z;
	}
	public double value() {
		return Math.sqrt(x*x+y*y+z*z);
	}

	// Determines the coordinates of the vector projected onto the screen.
	public void project(Camera c) {
		double bz = 1500;
		double x = c.x-this.x;
		double y = c.y-this.y;
		double z = c.z-this.z;
		double z3 = (z*c.cosX-x*c.sinX);
		pz = (z3*c.cosY-y*c.sinY)/bz+1;
		px = (x*c.cosX+z*c.sinX)/pz;
		py = (y*c.cosY+z3*c.sinY)/pz;
	}
	
	public String toString() {
		return "x: "+x+" y: "+y+" z: "+z;
	}
	
	// Some special functionality that allows to easily prevent duplicates in the vector array:
	// This is done by storing the midpoints between the vectors and its neighbors for each iteration.
	Vector[] connected = new Vector[6], midPoints = new Vector[6];
	int size = 0;
	public Vector hasMidPointBetween(Vector other) {
		for(int i = 0; i < size; i++) {
			if(connected[i] == other)
				return midPoints[i];
		}
		return null;
	}
	public void addConnection(Vector con, Vector mid) {
		connected[size] = con;
		midPoints[size] = mid;
		size++;
	}
	public void clear() {
		size = 0;
	}
}