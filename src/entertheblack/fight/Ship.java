package entertheblack.fight;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entertheblack.storage.Variant;

// Object implementation of a ship.
// TODO: Add drones.

public class Ship {
	public Variant v;
	public double x, y;
	public double vx, vy;
	public double size;
	public double r;
	public double a;
	public double vmax;
	double omega;
	double gen; // energy generation.
	double m = 100; // TODO: Add to data file.
	double health;
	double energy;
	int cooldownturn;
	int shoot1cd;
	int shoot2cd;
	public double alpha;
	List<Projectile> projectiles;
	public Ship(Variant v, int x, int y) {
		this.v = v;
		this.x = x;
		this.y = y;
		vx = vy = 0;
		health = v.health;
		energy = v.energy;
		a = v.acceleration/100.0;
		vmax = v.vmax;
		omega = v.turnRate;
		gen = v.energyGeneration/60.0;
		alpha = 0;
		cooldownturn = 0;
		size = v.size;
		r = size/2;
		projectiles = new ArrayList<>();
	}
	
	// Update velocity.
	// Uses a simple Newtonian model, but also uses a defined maximum velocity to prevent the player from going super fast.
	public void calculateV() {
		double v = Math.sqrt(vx*vx+vy*vy);
		double ax = a*Math.sin(alpha);
		double ay = -a*Math.cos(alpha);
		
		if(v > vmax) {
			double alpha = Math.atan(vy/vx);
			if(vx < 0)
				alpha += Math.PI;
			vx = vmax*Math.cos(alpha);
			vy = vmax*Math.sin(alpha);
		}
		if(v > 0.1) // Prevent division by 0.
			vx += ax/v; // The acceleration at constant power scales with 1/(vt), where t is normed to 1.
		else
			vx += 10*ax;
		if(v > 0.1) // Prevent division by 0.
			vy += ay/v; // The acceleration at constant power scales with 1/(vt), where t is normed to 1.
		else
			vy += 10*ay;
	}
	
	// Flight out side of fighting areas doesn't need to account for enemy ships and energy, health, ...
	public void fly(boolean move, boolean turnRight, boolean turnLeft) {
		if (move) {
			calculateV();
		}
		x = x + vx;
		y = y + vy;
		
		if (turnLeft && !turnRight) {
			if (alpha <= 0) {
				alpha += 2*Math.PI;
			}
			alpha -= omega;
		} else if (turnRight && !turnLeft) {
			if (alpha >= 2*Math.PI) {
				alpha -= 2*Math.PI;
			}
			alpha += omega;
		}
	}

	private double getvx(double a, double v1, double v2, double theta1, double theta2, double m1, double m2) {
		return Math.cos(a)*(v1*Math.cos(theta1-a)*(m1-m2)+2*m2*v2*Math.cos(theta2-a))/(m1+m2)+v1*Math.sin(theta1-a)*Math.cos(a+Math.PI/2);
	}
	
	public void fly(Ship en, boolean move, boolean turnRight, boolean turnLeft) {
		if(move) {
			calculateV();
		}
		x = x + vx;
		y = y + vy;
		
		// Collision
		double deltax = en.x - x;
		double deltay = en.y - y;
		double radius = Math.sqrt(deltax*deltax + deltay*deltay);
		if(radius < r + en.r) {
			double alpha = Math.atan((deltay)/(deltax));
			if(en.x < x)
				alpha += Math.PI;
			double v1 = Math.sqrt(vx*vx+vy*vy);
			double theta1 = Math.atan(vy/vx);
			if(vx < 0)
				theta1 += Math.PI;
			if(theta1 != theta1)
				theta1 = 0; // prevent NaN when standing still!
			double v2 = Math.sqrt(en.vx*en.vx+en.vy*en.vy);
			double theta2 = Math.atan(en.vy/en.vx);
			if(en.vx < 0)
				theta2 += Math.PI;
			if(theta2 != theta2)
				theta2 = 0; // prevent NaN when standing still!
			vx = getvx(alpha, v1, v2, theta1, theta2, m, en.m);
			vy = Math.sin(alpha)*(v1*Math.cos(theta1-alpha)*(m - en.m)+2*en.m*v2*Math.cos(theta2-alpha))/(m+en.m)+v1*Math.sin(theta1-alpha)*Math.sin(alpha+Math.PI/2);
			en.vx = getvx(alpha, v2, v1, theta2, theta1, en.m, m);
			en.vy = Math.sin(alpha)*(v2*Math.cos(theta2-alpha)*(en.m-m)+2*m*v1*Math.cos(theta1-alpha))/(en.m+m)+v2*Math.sin(theta2-alpha)*Math.sin(alpha+Math.PI/2);
			// Move both ships to prevent post collision bugs.
			x += deltax*(radius - r - en.r)/(r + en.r);
			y += deltay*(radius - r - en.r)/(r + en.r);
			en.x -= deltax*(radius - r - en.r)/(r + en.r);
			en.y -= deltay*(radius - r - en.r)/(r + en.r);
			// Slightly reduce speed after hit, to simulate energy loss by structural damage. TODO: Conserve momentum!
			vx *= 0.9;
			vy *= 0.9;
			en.vx *= 0.9;
			en.vy *= 0.9;
			// TODO: Make damage to the ships based on dp/dt!
		}
		
		if (turnLeft && !turnRight) {
			if (alpha <= 0) {
				alpha += 2*Math.PI;
			}
			alpha -= omega;
		} else if (turnRight) {
			if (alpha >= 2*Math.PI) {
				alpha -= 2*Math.PI;
			}
			alpha += omega;
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if(p.update(en)) {
				projectiles.remove(i);
			}
		}
		
		energy += gen;
		
		if(energy > v.energy)
			energy = v.energy;
	}
	
	public void change(boolean x, boolean y) {
		// Simulate what happens when the ships change position:
		for(Projectile p : projectiles) {
			if(x)
				p.x -= 1920;
			if(y)
				p.y -= 1080;
		}
	}

	public void shoot(boolean type1, boolean type2) {
		if(type1 && shoot1cd <= 0) shoot1();
		if(type2 && shoot2cd <= 0) shoot2();
		shoot1cd--;
		shoot2cd--;
	}
	
	public void shift(int px, int nx, int py, int ny) { // backgroundShift
		if(px > 0) {
			x += px;
			for(Projectile p : projectiles) {
				p.x += px;
			}
		} else if(nx > 0) {
			x -= nx;
			for(Projectile p : projectiles) {
				p.x -= nx;
			}
		}
		if(py > 0) {
			y += py;
			for(Projectile p : projectiles) {
				p.y += py;
			}
		} else if(ny > 0) {
			y -= ny;
			for(Projectile p : projectiles) {
				p.y -= ny;
			}
		}
	}

	private void shoot1() {
		if (v.wd1!= null/*has primary weapon/system*/ && energy >= v.wd1.cost * v.prim.size() && shoot1cd <= 0) {
			for (int i = 0; i < v.prim.size(); i++) {
				projectiles.add(new Projectile(this, v.wd1, v.prim.get(i)));
				shoot1cd = v.wd1.reload;
				energy -= v.wd1.cost;
			}
		}
	}

	private void shoot2() {
		if (v.wd2 != null/*has secondary weapon/system*/ && energy >= v.wd2.cost * v.secn.size() && shoot2cd <= 0) {
			for (int i = 0; i < v.secn.size(); i++) {
				projectiles.add(new Projectile(this, v.wd2, v.secn.get(i)));
				shoot2cd = v.wd2.reload;
				energy -= v.wd2.cost;
			}
		}
	}
	
	public void paint(Graphics2D g2d) {
		g2d.translate((int)((x + r)), (int)((y + r)));
		g2d.rotate(alpha);
		g2d.translate(-((int)((x + r))), -((int)((y + r))));
		g2d.drawImage(v.img, (int)(x), (int)(y), (int)size, (int)size, null);
		g2d.translate((int)((x + r)), (int)((y + r)));
		g2d.rotate(-alpha);
		g2d.translate(-((int)((x + r))), -((int)((y + r))));
		for(Projectile p : projectiles) {
			p.paint(g2d);
		}
	}
}
