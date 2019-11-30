package entertheblack.ai;

import entertheblack.fight.Game;
import entertheblack.fight.Ship;

public class Simple implements AI {
	@Override
	public void updateBehaviour(Game g) {
		// Set all instructions to false.
		g.move2 = g.rocketshoot2 = g.shootingactive2 = g.turnleft2 = g.turnright2 = false;
		Ship sh = g.sh2;
		Ship en = g.sh1;
		double deltax = sh.x - en.x;
		double deltay = sh.y - en.y;
		double dist = Math.sqrt(deltax*deltax + deltay*deltay);
		double alpha = Math.atan(deltay/deltax);
		if(deltax < 0)
			alpha += Math.PI;
		if(alpha < 0)
			alpha += 2*Math.PI;
		if(dist < 500) {
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
		else if(dist < 1000) {
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
			// TODO: Take into account enemy movement.
			if(Math.abs(deltaalpha) < 0.5)
				g.shootingactive2 = true;
			if(Math.abs(deltaalpha) < 1)
				g.rocketshoot2 = true;
		}
		else {
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
	
}
