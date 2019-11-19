package entertheblack.fight;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entertheblack.storage.ShipData;

public class Ship {
	public ShipData sd;
	public double x, y;
	double vx, vy;
	double size;
	public double r;
	public double a;
	double vmax;
	double ω;
	double gen; // energy generation.
	double m = 100; // TODO: Add to data file.
	double health;
	double energy;
	int cooldownturn;
	int shoot1cd;
	int shoot2cd;
	public double α;
	List<Projectile> projectiles;
	public Ship(ShipData sd, int x, int y) {
		this.sd = sd;
		this.x = x;
		this.y = y;
		vx = vy = 0;
		health = sd.health;
		energy = sd.energy;
		a = sd.acceleration;
		vmax = sd.vmax/100.0;
		ω = sd.turnRate/1000.0;
		gen = sd.energyGeneration/60.0;
		α = 0;
		cooldownturn = 0;
		size = sd.size;
		r = size/2;
		projectiles = new ArrayList<>();
	}
	
	// Flight out side of fighting areas doesn't need to account for enemy ships and energy, health, ...
	public void fly(boolean move, boolean turnRight, boolean turnLeft) {
		if (move) {
			vx = ((200-a)*vx + a*Math.cos(α - Math.PI/2))/200;
			vy = ((200-a)*vy + a*Math.sin(α - Math.PI/2))/200;
		}
		x = x + vx*vmax;
		y = y + vy*vmax;
		vx = 0.9999*vx;
		vy = 0.9999*vy;
		
		if (turnLeft && !turnRight) {
			if (α <= 0) {
				α += 2*Math.PI;
			}
			α -= ω;
		} else if (turnRight && !turnLeft) {
			if (α >= 2*Math.PI) {
				α -= 2*Math.PI;
			}
			α += ω;
		}
	}

	private double getvx(double a, double v1, double v2, double theta1, double theta2, double m1, double m2) {
		return Math.cos(a)*(v1*Math.cos(theta1-a)*(m1-m2)+2*m2*v2*Math.cos(theta2-a))/(m1+m2)+v1*Math.sin(theta1-a)*Math.cos(a+Math.PI/2);
	}
	
	public void fly(Ship en, boolean move, boolean turnRight, boolean turnLeft) {
		if (move) {
			vx = ((200-a)*vx + a*Math.cos(α - Math.PI/2))/200;
			vy = ((200-a)*vy + a*Math.sin(α - Math.PI/2))/200;
		}
		x = x + vx*vmax;
		y = y + vy*vmax;
		vx = 0.9999*vx;
		vy = 0.9999*vy;
		
		// Collision
		double Δx = en.x - x;
		double Δy = en.y - y;
		double radius = Math.sqrt(Δx*Δx + Δy*Δy);
		if(radius < r + en.r) {
			double α = Math.atan((Δy)/(Δx));
			if(en.x < x)
				α += Math.PI;
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
			vx = getvx(α, v1, v2, theta1, theta2, m, en.m);
			vy = Math.sin(α)*(v1*Math.cos(theta1-α)*(m - en.m)+2*en.m*v2*Math.cos(theta2-α))/(m+en.m)+v1*Math.sin(theta1-α)*Math.sin(α+Math.PI/2);
			en.vx = getvx(α, v2, v1, theta2, theta1, en.m, m);
			en.vy = Math.sin(α)*(v2*Math.cos(theta2-α)*(en.m-m)+2*m*v1*Math.cos(theta1-α))/(en.m+m)+v2*Math.sin(theta2-α)*Math.sin(α+Math.PI/2);
			// Move both ships to prevent post collision bugs.
			x += Δx*(radius - r - en.r)/(r + en.r);
			y += Δy*(radius - r - en.r)/(r + en.r);
			en.x -= Δx*(radius - r - en.r)/(r + en.r);
			en.y -= Δy*(radius - r - en.r)/(r + en.r);
			// Slightly reduce speed after hit, to simulate energy loss by structural damage. TODO: Conserve momentum!
			vx *= 0.9;
			vy *= 0.9;
			en.vx *= 0.9;
			en.vy *= 0.9;
			// TODO: Make damage to the ships based on dp/dt!
		}
		
		if (turnLeft && !turnRight) {
			if (α <= 0) {
				α += 2*Math.PI;
			}
			α -= ω;
		} else if (turnRight) {
			if (α >= 2*Math.PI) {
				α -= 2*Math.PI;
			}
			α += ω;
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if(p.update(en)) {
				projectiles.remove(i);
			}
		}
		
		energy += gen;
		
		if(energy > sd.energy)
			energy = sd.energy;
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
		if (sd.wd1!= null/*has primary weapon/system*/ && energy >= sd.wd1.cost * sd.prim.size() && shoot1cd <= 0) {
			for (int i = 0; i < sd.prim.size(); i++) {
				projectiles.add(new Projectile(this, sd.wd1, sd.prim.get(i)));
				shoot1cd = sd.wd1.reload;
				energy -= sd.wd1.cost;
			}
		}
	}

	private void shoot2() {
		if (sd.wd2 != null/*has secondary weapon/system*/ && energy >= sd.wd2.cost * sd.secn.size() && shoot2cd <= 0) {
			for (int i = 0; i < sd.secn.size(); i++) {
				projectiles.add(new Projectile(this, sd.wd2, sd.secn.get(i)));
				shoot2cd = sd.wd2.reload;
				energy -= sd.wd2.cost;
			}
		}
	}
	
	public void paint(Graphics2D g2d) {
		g2d.translate((int)((x + r)), (int)((y + r)));
		g2d.rotate(α);
		g2d.translate(-((int)((x + r))), -((int)((y + r))));
		g2d.drawImage(sd.img, (int)(x), (int)(y), (int)size, (int)size, null);
		g2d.translate((int)((x + r)), (int)((y + r)));
		g2d.rotate(-α);
		g2d.translate(-((int)((x + r))), -((int)((y + r))));
		for(Projectile p : projectiles) {
			p.paint(g2d);
		}
	}
}
