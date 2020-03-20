package entertheblack.Util.Graphics3D;

// Represents the camera position and orientation.

public class Camera extends Vector {
	double ax;
	private double ay;
	double sinX;
	double cosX;
	double sinY;
	double cosY;
	private double distance; // How much behind the actual coordinates the camera is positioned. Useful when the camera should be outside the object, like when observing a planet.
	Vector sight;
	public Camera(double dist) {
		distance = dist;
		ax = Math.PI;
		ay = 0;
		sight = new Vector();
		updateAngle(0, 0);
		update(0, 0, 0);
	}
	public void update(double x, double y, double z) {
		this.y = -sight.y*distance + y;
		this.x = -sight.x*distance + x;
		this.z = -sight.z*distance + z;
	}
	public void updateAngle(double x, double y) {
		ax += x;
		ay += y;
		if(ay > Math.PI/2)
			ay = Math.PI/2;
		else if(ay < -Math.PI/2)
			ay = -Math.PI/2;
		// Store a few useful variables.
		sinX = Math.sin(ax);
		cosX = Math.cos(ax);
		sinY = Math.sin(ay);
		cosY = Math.cos(ay);
		sight.x = sinX*cosY;
		sight.y = sinY;
		sight.z = -cosX*cosY;
	}
}
