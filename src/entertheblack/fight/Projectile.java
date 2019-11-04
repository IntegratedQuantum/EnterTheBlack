package entertheblack.fight;

import entertheblack.storage.Slot;
import entertheblack.storage.WeaponData;

import java.awt.Graphics2D;

public class Projectile {
	WeaponData wd;
	double x, y;
	double vx, vy;
	double α;
	int r;
	double mass;
	double range; // Range in pixels
	double guiding;
	public Projectile(Ship source, WeaponData wd, Slot slot) {
		this.wd = wd;
		x = source.x + source.r - 2;
		y = source.y + source.r - 2;
		x += Math.sqrt((slot.x*slot.x + slot.y*slot.y)) * Math.sin(source.α + Math.atan(((double)slot.x / (double)slot.y)) + slot.α);
		y += -Math.sqrt((slot.x*slot.x + slot.y*slot.y)) * Math.cos(source.α + Math.atan(((double)slot.x / (double)slot.y)) + slot.α);
		vy = -(wd.velocity / 10) * Math.cos(source.α) + source.vy;
		vx = (wd.velocity / 10) * Math.sin(source.α) + source.vx;
		α = source.α;
		r = 10;
		guiding = -wd.tracking/1000.0;
	}
	// Returns true if lifetime limit reached or target is it.
	public boolean update(Ship en) {
		x += vx;
		y += vy;
		range += Math.sqrt(vx*vx+vy*vy);
		if (range >= wd.range) {
			return true;
		}
		if (x - r < en.x + en.size && y - r < en.y + en.size && x + r > en.x && y + r > en.y) {
			en.health = en.health - wd.dmg;
			return true;
		}
		if(guiding != 0) {
			double β;
			if (y < en.y) {
				β = Math.PI - Math.atan((x - en.x - en.r) / (y - en.y - en.r));
			} else {
				β = 2*Math.PI - Math.atan((x - en.x - en.r) / (y - en.y - en.r));
			}
			if (α != β) {
				if (α < β - guiding) {
					if (α > β - Math.PI) {
						α = α - guiding;
					} else {
						α = α + guiding;
					}
				} else if (α > β + guiding) {
					if (α < β + Math.PI) {
						α = α + guiding;
					} else {
						α = α - guiding;
					}
				} else {
					α = β;
				}
				if (α < Math.PI/2) {
					α = α + 2*Math.PI;
				}
				else if (α > 2.5*Math.PI) {
					α = α - 2*Math.PI;
				}
				double v = Math.sqrt(vx*vx + vy*vy);
				vy = -v*Math.cos(α);
				vx = v*Math.sin(α);
			}
		}
		return false;
	}
	
	public void paint(Graphics2D g2d) {
		g2d.translate((int)x, (int)y);
		g2d.rotate(α);
		g2d.translate(-(int)x, -(int)y);
		g2d.drawImage(wd.img, (int)x - r, (int)y - r, r*2, r*2, null);
		g2d.translate((int)(x), (int)(y));
		g2d.rotate(-α);
		g2d.translate(-((int)(x)), -((int)(y)));
	}
}
