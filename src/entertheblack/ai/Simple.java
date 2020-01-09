package entertheblack.ai;

import entertheblack.fight.Game;
import entertheblack.fight.Ship;

// A simple AI. Nothing good, but it is enough for alpha.

public class Simple implements AI {
	@Override
	public void updateBehaviour(Game g) {
		// Set all instructions to false.
		g.move2 = g.rocketshoot2 = g.shootingactive2 = g.turnleft2 = g.turnright2 = false;
		Ship sh = g.sh2;
		Ship en = g.sh1;
		double deltaX = sh.x - en.x;
		double deltaY = sh.y - en.y;
		double dist = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		// Simulate own and enemy movement for the time the laser would need to go the current distance between this and enemy. Taking into account "doppler effect" on projectile "wavelength".
		
		// Calculate projection of enemy movement vector on (normalized) distance vector with scalar product:
		double vRel = (en.vx*deltaX/dist + en.vy*deltaY/dist);
		double t = dist/(sh.v.wd1.velocity/10 - vRel);
		// Simulate enemy ship movement during given time:
		deltaX -= en.vx*t;
		deltaY -= en.vy*t;
		
		double alpha = Math.atan(deltaY/deltaX);
		if(deltaX < 0)
			alpha += Math.PI;
		if(alpha < 0)
			alpha += 2*Math.PI;
		
		double flightTime1 = sh.v.wd1.range/sh.v.wd1.velocity*10; // Maximum Flight time of primary weapon.
		if(t < flightTime1/3 && dist < sh.v.wd1.range/3) { // Only flee when enemy is actually close
			// Flee
			double targetalpha = alpha - Math.PI;
			if(targetalpha < 0)
				targetalpha += 2*Math.PI;
			double deltaalpha = targetalpha - sh.alpha - Math.PI/2;
			if(deltaalpha < -Math.PI)
				deltaalpha += 2*Math.PI;
			if(deltaalpha > Math.PI)
				deltaalpha -= 2*Math.PI;
			if(Math.abs(deltaalpha) > 0.2) {
				if(deltaalpha < 0)
					g.turnleft2 = true;
				else
					g.turnright2 = true;
			}
			if(Math.abs(deltaalpha) < 0.5)
				g.move2 = true;
		}
		else if(t < 3*flightTime1/4) { // Only attack if the target stays within effective weapon range
			// Attack
			double deltaalpha = alpha - sh.alpha - Math.PI/2;
			if(deltaalpha < -Math.PI)
				deltaalpha += 2*Math.PI;
			if(deltaalpha > Math.PI)
				deltaalpha -= 2*Math.PI;
			if(Math.abs(deltaalpha) > 0.01) {
				if(deltaalpha < 0)
					g.turnleft2 = true;
				else
					g.turnright2 = true;
			}
			if(Math.abs(deltaalpha) < 0.5)
				g.shootingactive2 = true;
			if(Math.abs(deltaalpha) < 1)
				g.rocketshoot2 = true;
		}
		else {
			// Approach
			approach(sh, en, g);
		}
	}
	public void approach(Ship sh, Ship en, Game g) {
		double deltaX = sh.x - en.x;
		double deltaY = sh.y - en.y;
		double alpha = Math.atan(deltaY/deltaX);
		if(deltaX < 0)
			alpha += Math.PI;
		if(alpha < 0)
			alpha += 2*Math.PI;
		
		double deltaalpha = alpha - sh.alpha - Math.PI/2;
		if(deltaalpha < -Math.PI)
			deltaalpha += 2*Math.PI;
		if(deltaalpha > Math.PI)
			deltaalpha -= 2*Math.PI;
		if(Math.abs(deltaalpha) > 0.01) {
			if(deltaalpha < 0)
				g.turnleft2 = true;
			else
				g.turnright2 = true;
		}
		if(Math.abs(deltaalpha) < 0.5)
			g.move2 = true;
	}
}
