package entertheblack.fight;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entertheblack.menu.Assets;

public class Ship {
	static final int[] grs = { 0, 100, 200, 300, 400, 450 };
	int type;
	double x, y;
	double vx, vy;
	double size;
	double r;
	double a;
	double vmax;
	double ω;
	double gen; // energy generation.
	int health;
	double energy;
	int cooldownturn;
	int shoot1cd;
	int shoot2cd;
	double α;
	List<Projectile> projectiles;
	public Ship(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		vx = vy = 0;
		health = Assets.getShipStat(type, Assets.HEALTH);
		energy = Assets.getShipStat(type, Assets.ENERGY);
		a = Assets.getShipStat(type, Assets.ACC);
		vmax = Assets.getShipStat(type, Assets.VMAX)/100.0;
		ω = Assets.getShipStat(type, Assets.TURN)/1000.0;
		gen = Assets.getShipStat(type, Assets.GEN)/60.0;
		α = 0;
		cooldownturn = 0;
		size = Assets.getShipStat(type, Assets.SIZE);
		r = size/2;
		projectiles = new ArrayList<>();
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
		while (x < en.x + en.size && x > en.x + en.size - 5 && y + size > en.y + 1 && y < en.y + en.size - 1) {
			x++;
			vx = 0;
		}
		while (y < en.y + en.size && y > en.y + en.size - 5 && x + size > en.x + 1 && x < en.x + en.size - 1) {
			y++;
			vy = 0;
		}
		while (en.x < x + size && en.x > x + size - 5 && en.y + en.size > y + 1 && en.y < y + size - 1) {
			x--;
			vx = 0;
		}
		while (en.y < y + size && en.y > y + size - 5 && en.x + en.size > x + 1 && en.x < x + size - 1) {
			y--;
			vy = 0;
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
		
		if(energy > Assets.getShipStat(type, Assets.ENERGY))
			energy = Assets.getShipStat(type, Assets.ENERGY);
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
		if (type != 3 && energy >= Assets.getWeaponStat(Assets.getShipStat(type, Assets.PRIMARY), 2) * Assets.getShipStat(type, Assets.SLOTS) && shoot1cd <= 0) {
			for (int i = 0; i < Assets.getShipStat(type, Assets.SLOTS); i++) {
				projectiles.add(new Projectile(this, Assets.getShipStat(type, Assets.SLOTS + 2 + i*2), Assets.getShipStat(type, Assets.SLOTS + 3 + i*2), Assets.getShipStat(type, Assets.PRIMARY)));
				shoot1cd = Assets.getWeaponStat(Assets.getShipStat(type, Assets.PRIMARY), 3);
				energy -= Assets.getWeaponStat(Assets.getShipStat(type, Assets.PRIMARY), 2);
			}
		}
	}

	private void shoot2() {
		if (Assets.getShipStat(type, 1) >= 0 && energy >= Assets.getWeaponStat(Assets.getShipStat(type, Assets.SECONDARY), 2) * Assets.getShipStat(type, Assets.SLOTS+1) && shoot2cd <= 0) {
			for (int i = Assets.getShipStat(type, Assets.SLOTS); i < Assets.getShipStat(type, Assets.SLOTS+1)+Assets.getShipStat(type, Assets.SLOTS); i++) {
				projectiles.add(new Projectile(this, Assets.getShipStat(type, Assets.SLOTS+2 + i*2), Assets.getShipStat(type, Assets.SLOTS+3 + i*2), Assets.getShipStat(type, Assets.SECONDARY)));
				shoot2cd = Assets.getWeaponStat(Assets.getShipStat(type, Assets.SECONDARY), 3);
				energy -= Assets.getWeaponStat(Assets.getShipStat(type, Assets.SECONDARY), 2);
			}
		}
	}
	
	public void paint(Graphics2D g2d) {
		g2d.translate((int)((x + r)), (int)((y + r)));
		g2d.rotate(α);
		g2d.translate(-((int)((x + r))), -((int)((y + r))));
		g2d.drawImage(Assets.ships.get(type), (int)(x), (int)(y), (int)size, (int)size, null);
		g2d.translate((int)((x + r)), (int)((y + r)));
		g2d.rotate(-α);
		g2d.translate(-((int)((x + r))), -((int)((y + r))));
		for(Projectile p : projectiles) {
			p.paint(g2d);
		}
	}
}
