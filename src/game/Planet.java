package game;

import java.awt.Graphics2D;
import java.awt.Image;

import entertheblack.menu.Assets;

// Planets are in general all kinds of solar bodys that fly around somewhere.
public class Planet {
	private static final double MIN = 1000, MAX = 10000, ρ = 20, G = 1;
	double m, r, d, ω, α;
	int x, y;
	int lastDate;
	Planet orbiting;
	Image img;
	
	public Planet(double d, Planet orbiting) {
		this.orbiting = orbiting;
		this.d = d;
		m = MIN + (MAX-MIN)*Math.random();
		r = Math.cbrt(m/ρ);
		double v = Math.sqrt(G*orbiting.m/d); // F_R  = F_G
		ω = 2*Math.PI*v/d;
		α = 2*Math.PI*Math.random();
		lastDate = -1;
		updateOrbit(0);
		img = Assets.randPlanetImg(d/orbiting.m); // TODO: moons
	}
	
	public Planet() { // Star
		orbiting = null;
		d = 0;
		m = 1000*(MIN + (MAX-MIN)*Math.random()); // Suns are a lot heavier.
		r = Math.cbrt(m/ρ);
		ω = α = 0;
		lastDate = 0;
		updateOrbit(0);
		img = Assets.randStarImg(m); // TODO: moons
	}
	
	public void updateOrbit(int date) { // TODO: Planet creation after exact start.
		if(date == lastDate)
			return;
		orbiting.updateOrbit(date); // Ensure the center is up to date to prevent glitches.
		date -= lastDate;
		α += ω*date;
		x = (int)(Math.cos(α)*d + orbiting.x);
		y = (int)(Math.sin(α)*d + orbiting.x);
		lastDate = date;
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(img, (int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r), null);
	}
}
