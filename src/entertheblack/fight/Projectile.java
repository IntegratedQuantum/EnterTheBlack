package entertheblack.fight;

import entertheblack.storage.Slot;
import entertheblack.storage.WeaponData;

import java.awt.Graphics2D;

// Object representing a projectile/missile or similar.
// TODO: Allow missiles to destroy others.
// TODO: Make missiles follow the same doughnut-logic like the ships.

public class Projectile {
	WeaponData wd;
	double x, y;
	double vx, vy;
	double alpha;
	int r;
	double mass;
	double range; // Range in pixels
	double guiding;
	public Projectile(Ship source, WeaponData wd, Slot slot) {
		this.wd = wd;
		x = source.x + source.r - 2;
		y = source.y + source.r - 2;
		double angle = Math.atan((double)slot.x / (double)slot.y);
		if(slot.y < 0)
			angle += Math.PI;
		x += Math.sqrt((slot.x*slot.x + slot.y*slot.y)) * Math.sin(source.alpha + angle);
		y += -Math.sqrt((slot.x*slot.x + slot.y*slot.y)) * Math.cos(source.alpha + angle);
		alpha = source.alpha+slot.alpha;
		vy = -(wd.velocity / 10) * Math.cos(alpha);// + source.vy;
		vx = (wd.velocity / 10) * Math.sin(alpha);// + source.vx;
		r = 10;
		guiding = -wd.tracking/1000.0;
	}
	// Returns true if lifetime limit reached or target is it.
	public boolean update(Ship en) {
		x += vx;
		y += vy;
		// Make sure projectile stays inside the region around the players.
		if(x < -960) {
			x += 3840;
		} else if(x > 2880) {
			x -= 3840;
		} else if(y < -540) {
			y += 2160;
		} else if(y > 1620) {
			y -= 2160;
		}
		range += Math.sqrt(vx*vx+vy*vy);
		if (range >= wd.range) {
			return true;
		}
		if (x - r < en.x + en.size && y - r < en.y + en.size && x + r > en.x && y + r > en.y) {
			en.health = en.health - wd.dmg;
			return true;
		}
		if(guiding != 0) {
			double beta;
			if (y < en.y) {
				beta = Math.PI - Math.atan((x - en.x - en.r) / (y - en.y - en.r));
			} else {
				beta = 2*Math.PI - Math.atan((x - en.x - en.r) / (y - en.y - en.r));
			}
			if (alpha != beta) {
				if (alpha < beta - guiding) {
					if (alpha > beta - Math.PI) {
						alpha = alpha - guiding;
					} else {
						alpha = alpha + guiding;
					}
				} else if (alpha > beta + guiding) {
					if (alpha < beta + Math.PI) {
						alpha = alpha + guiding;
					} else {
						alpha = alpha - guiding;
					}
				} else {
					alpha = beta;
				}
				if (alpha < Math.PI/2) {
					alpha = alpha + 2*Math.PI;
				}
				else if (alpha > 2.5*Math.PI) {
					alpha = alpha - 2*Math.PI;
				}
				double v = Math.sqrt(vx*vx + vy*vy);
				vy = -v*Math.cos(alpha);
				vx = v*Math.sin(alpha);
			}
		}
		return false;
	}
	
	public void paint(Graphics2D g2d) {
		g2d.translate((int)x, (int)y);
		g2d.rotate(alpha);
		g2d.drawImage(wd.img, (int)-r, (int)-r, r*2, r*2, null);
		g2d.rotate(-alpha);
		g2d.translate(-((int)(x)), -((int)(y)));
	}
}
