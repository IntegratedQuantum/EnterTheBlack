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
		double Δx = sh.x - en.x;
		double Δy = sh.y - en.y;
		double dist = Math.sqrt(Δx*Δx + Δy*Δy);
		double α = Math.atan(Δy/Δx);
		if(Δx < 0)
			α += Math.PI;
		if(α < 0)
			α += 2*Math.PI;
		if(dist < 500) {
			// Flee
			double targetα = α - Math.PI;
			if(targetα < 0)
				targetα += 2*Math.PI;
			double Δα = targetα - sh.α - Math.PI/2;
			if(Δα < -Math.PI)
				Δα += 2*Math.PI;
			if(Δα > Math.PI)
				Δα -= 2*Math.PI;
			if(Math.abs(Δα) > 0.2) {
				if(Δα < 0)
					g.turnleft2 = true;
				else
					g.turnright2 = true;
			}
			if(Math.abs(Δα) < 0.5)
				g.move2 = true;
		}
		else if(dist < 1000) {
			// Attack
			double Δα = α - sh.α - Math.PI/2;
			if(Δα < -Math.PI)
				Δα += 2*Math.PI;
			if(Δα > Math.PI)
				Δα -= 2*Math.PI;
			if(Math.abs(Δα) > 0.01) {
				if(Δα < 0)
					g.turnleft2 = true;
				else
					g.turnright2 = true;
			}
			// TODO: Take into account enemy movement.
			if(Math.abs(Δα) < 0.5)
				g.shootingactive2 = true;
			if(Math.abs(Δα) < 1)
				g.rocketshoot2 = true;
		}
		else {
			double Δα = α - sh.α - Math.PI/2;
			if(Δα < -Math.PI)
				Δα += 2*Math.PI;
			if(Δα > Math.PI)
				Δα -= 2*Math.PI;
			if(Math.abs(Δα) > 0.01) {
				if(Δα < 0)
					g.turnleft2 = true;
				else
					g.turnright2 = true;
			}
			if(Math.abs(Δα) < 0.5)
				g.move2 = true;
		}
	}
	
}
