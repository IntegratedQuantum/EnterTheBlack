package entertheblack.fight;

import java.awt.image.BufferedImage;

import entertheblack.menu.Assets;

import java.awt.Graphics2D;

public class Projectile {
	BufferedImage proj;
	double x, y;
	double vx, vy;
	double α;
	int r;
	double mass;
	int lifeTime;
	int type;
	double guiding;
	public Projectile(Ship source, int slotX, int slotY, int type) {
		this.type = type;
		x = source.x + source.r - 2;
		y = source.y + source.r - 2;
		x += Math.sqrt((slotX*slotX + slotY*slotY)) * Math.sin(source.α + Math.atan((slotX / slotY)));
		y += -Math.sqrt((slotX*slotX + slotY*slotY)) * Math.cos(source.α + Math.atan((slotX / slotY)));
		vy = -(Assets.getWeaponStat(type, 4) / 10) * Math.cos(source.α) + source.vy;
		vx = (Assets.getWeaponStat(type, 4) / 10) * Math.sin(source.α) + source.vx;
		α = source.α;
		proj = Assets.getProjectile(type);
		r = 10;
		guiding = -Assets.getWeaponStat(type, 5)/1000.0;
		/*
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		r = radius;
		mass = 0;
		this.type = type;*/
	}
	// Returns true if lifetime limit reached or target is it.
	public boolean update(Ship en) {
		x += vx;
		y += vy;
		lifeTime++;
		if (lifeTime >= Assets.getWeaponStat(type, 1)) {
			return true;
		}
		if (x - r < en.x + en.size && y - r < en.y + en.size && x + r > en.x && y + r > en.y) {
			en.health = en.health - Assets.getWeaponStat(type, 0);
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
		g2d.drawImage(proj, (int)x - r, (int)y - r, r*2, r*2, null);
		g2d.translate((int)(x), (int)(y));
		g2d.rotate(-α);
		g2d.translate(-((int)(x)), -((int)(y)));
	}
}
