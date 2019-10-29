package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import entertheblack.menu.Assets;

// Planets are in general all kinds of solar bodys that fly around somewhere.
public class Planet {
	private static final String MASS = "Mass", RADIUS = "Radius", DIST = "Distance", IMAGE = "Image", TEMP = "T", LIFE = "Life", GOV = "Government", TECH = "TechLevel";
	
	private static final double MIN = 1000, MAX = 10000, ρ = 1, G = 1;
	double m, r, d, ω, α, ωSelf, αSelf;
	int T; // Temperature in Kelvin.
	int x, y;
	int techLevel; // Tech level needed to land here.
	int life = 0; // How many specimen can live here.
	int lastDate;
	public String species = null; // Species governing this planet.
	String name;
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
		ωSelf = Math.random()/100;
		αSelf = 2*Math.PI*Math.random();
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
		ωSelf = Math.random()/100;
		αSelf = 2*Math.PI*Math.random();
	}
	
	public Planet(String name, String file, Planet orbiting) {
		this.orbiting = orbiting;
		this.name = name;
		String[] entries = file.split("\n");
		for(int i = 0; i < entries.length; i++) {
			String[] val = entries[i].split("=");
			if(val.length < 2)
				continue;
			if(val[0].equals(MASS))
				m = Double.parseDouble(val[1]);
			else if(val[0].equals(RADIUS))
				r = Double.parseDouble(val[1]);
			else if(val[0].equals(DIST))
				d = Double.parseDouble(val[1]);
			else if(val[0].equals(IMAGE))
				img = Assets.getPlanetImg(val[1]);
			else if(val[0].equals(TEMP))
				T = Integer.parseInt(val[1]);
			else if(val[0].equals(GOV)) {
				species = val[1];
				java.lang.System.out.println("Loaded planet "+name+" governed by "+species+".");
			} else if(val[0].equals(TECH))
				techLevel = Integer.parseInt(val[1]);
			else if(val[0].equals(LIFE)) {
				life = Integer.parseInt(val[1]);
			}
		}
		ω = α = 0;
		if(orbiting != null) {
			double v = Math.sqrt(G*orbiting.m/d); // F_R  = F_G
			ω = 2*Math.PI*v/d;
			α = 2*Math.PI*Math.random();
		}
		lastDate = -1;
		updateOrbit(0);
		ωSelf = Math.random()/100;
		αSelf = 2*Math.PI*Math.random();
	}

	public void updateOrbit(int date) { // TODO: Planet creation after exact start.
		if(date == lastDate || orbiting == null)
			return;
		orbiting.updateOrbit(date); // Ensure the center is up to date to prevent glitches.
		lastDate = date;
		date -= lastDate;
		double date2 = date/10000000.0;
		α += ω*date2;
		x = (int)(Math.cos(α)*d + orbiting.x);
		y = (int)(Math.sin(α)*d + orbiting.y);
		αSelf += ωSelf*date2;
	}
	
	public void paint(Graphics2D g) {
		g.translate(x, y);
		g.rotate(αSelf);
		g.drawImage(img, (int)(-r), (int)(-r), (int)(2*r), (int)(2*r), null);
		g.rotate(-αSelf);
		g.translate(-x, -y);
	}
}
