package game;

import java.awt.Color;
import java.awt.Graphics2D;

import entertheblack.fight.Ship;
import entertheblack.menu.Assets;

public class Star {
	private static final int size = 4096;
	double zoom = 540.0/size;
	public Planet[] planets;
	Planet zoomLock; // The planet that is in the center of zoom. Used to smoothly close into planets.
	double lockStrength = 0; // Factor of how planet-centered and how star-centered the current camera position is.
	Ship ship;
	public Star(int p, int mainShip) {
		planets = new Planet[p];
		planets[0] = new Planet();
		for(int i = 1; i < p; i++) {
			planets[i] = new Planet(size/(p+1)*i + size/(p+1)*Math.random(), planets[0]);
		}
		ship = new Ship(mainShip, 0, size - (size >> 2));
		zoomLock = planets[0];
	}
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080); // Removing this line strangely results in lags. USE THIS IN EVERY DYNAMIC PAINT!
		g.scale(zoom, zoom);
		g.translate(-(int)(lockStrength*zoomLock.x), -(int)(lockStrength*zoomLock.y));
		g.drawImage(Assets.bg, -size, -size, size*2, size*2, null);
		g.drawImage(Assets.bg, -size*3, -size, size*2, size*2, null);
		g.drawImage(Assets.bg, size, -size, size*2, size*2, null);
		for(int i = 0; i < planets.length; i++) {
			planets[i].paint(g);
		}
		ship.paint(g);
		g.translate((int)(lockStrength*zoomLock.x), (int)(lockStrength*zoomLock.y));
		g.scale(-zoom, -zoom);
	}
	
	public boolean update(boolean move, boolean right, boolean left) {
		ship.fly(move, right, left);
		
		// Determine the zoomLock which is the closest planet to the ship.
		double r = 1.0e300;
		for(int i = 1; i < planets.length; i++) {
			Planet p = planets[i];
			double Δx = ship.x-p.x;
			double Δy = ship.y-p.y;
			double r2 = Math.sqrt(Δx*Δx+Δy*Δy);
			if(r2 < r) {
				r = r2;
				zoomLock = p;
			}
		}
		// Create the lockstrength which should smoothly transition from 0 to 1 as the ship gets closer.
		if(r > zoomLock.r*32) {
			lockStrength = 0;
		} else if (r < zoomLock.r*8) {
			lockStrength = 1;
		} else {
			lockStrength = 1-(r - zoomLock.r*8)/24/zoomLock.r;
		}
		
		r += zoomLock.r*4;
		double r2 = Math.sqrt(ship.x*ship.x + ship.y*ship.y) + planets[0].r*4;
		zoom = lockStrength*0.125*size/r + (1 - lockStrength)*0.125*size/r2;
		lockStrength *= 2; // Lock the position faster to ensure the ship is always visible.
		if(lockStrength > 1)
			lockStrength = 1;
		return ship.y-ship.r < -size || ship.x-ship.r < -size || ship.x+ship.r > size || ship.y+ship.r > size;
	}
}
