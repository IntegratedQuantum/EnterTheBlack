package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import entertheblack.Util.Noise;
import entertheblack.menu.Assets;

// Planets are in general all kinds of solar bodys that fly around somewhere around a star.
// TODO: Add proper graphics.


public class Planet {
	private static final String MASS = "Mass", RADIUS = "Radius", DIST = "Distance", IMAGE = "Image", TEMP = "T", LIFE = "Life", GOV = "Government", TECH = "TechLevel", COLOR = "Color", STARBASE = "Starbase";
	
	private static final double MIN = 1000, MAX = 10000, rho = 1, G = 1;
	double m, r, d, omega, alpha, omegaSelf, alphaSelf;
	int T; // Temperature in Kelvin.
	int x, y;
	int techLevel; // Tech level needed to land here.
	int life = 0; // How many specimen can live here.
	int lastDate;
	int[] color = new int[6]; // Color specifications for planets surface map.
	boolean hasStarBase = false; // All starbases can equip the ship, sell all unlocked parts and buy materials. 
	public String species = null; // Species governing this planet.
	String name;
	Planet orbiting;
	Image img;
	
	public Planet(double d, Planet orbiting) {
		this.orbiting = orbiting;
		this.d = d;
		m = MIN + (MAX-MIN)*Math.random();
		r = Math.cbrt(m/rho);
		double v = Math.sqrt(G*orbiting.m/d); // F_R  = F_G
		omega = 2*Math.PI*v/d;
		alpha = 2*Math.PI*Math.random();
		lastDate = -1;
		updateOrbit(0);
		img = Assets.randPlanetImg(d/orbiting.m); // TODO: moons
		omegaSelf = Math.random()/100;
		alphaSelf = 2*Math.PI*Math.random();
	}
	
	public Planet() { // Star
		orbiting = null;
		d = 0;
		m = 1000*(MIN + (MAX-MIN)*Math.random()); // Suns are a lot heavier.
		r = Math.cbrt(m/rho);
		omega = alpha = 0;
		lastDate = 0;
		updateOrbit(0);
		img = Assets.randStarImg(m); // TODO: moons
		omegaSelf = Math.random()/100;
		alphaSelf = 2*Math.PI*Math.random();
	}
	
	public Planet(String name, String str, Planet orbiting, String file) {
		this.orbiting = orbiting;
		this.name = name;
		String[] entries = str.split("\n");
		for(int i = 0; i < entries.length; i++) {
			String[] val = entries[i].split("=");
			if(val.length < 2) {
				if(val[0].equals(STARBASE))
					hasStarBase = true;
				continue;
			}
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
			} else if(val[0].equals(COLOR)) {
				for(int j = 0; j < 6; j++) {
					color[j] = new Scanner(""+val[1].charAt(2*j)+val[1].charAt(2*j+1)).nextInt(16);
				}
			} else {
				System.err.println("Error in "+file+" in planet definition of "+name+ " in line "+(i+1)+":");
				System.err.println("Unknown argument for type Planet \"" + val[0] + "\" with value \"" + val[1] + "\". Skipping line!");
			}
		}
		omega = alpha = 0;
		if(orbiting != null) {
			double v = Math.sqrt(G*orbiting.m/d); // F_R  = F_G
			omega = 2*Math.PI*v/d;
			alpha = 2*Math.PI*Math.random();
		}
		lastDate = -1;
		updateOrbit(0);
		omegaSelf = Math.random()/100;
		alphaSelf = 2*Math.PI*Math.random();
	}

	public void updateOrbit(int date) { // TODO: Planet creation after exact start.
		if(date == lastDate || orbiting == null)
			return;
		orbiting.updateOrbit(date); // Ensure the center is up to date to prevent glitches.
		lastDate = date;
		date -= lastDate;
		double date2 = date/10000000.0;
		alpha += omega*date2;
		x = (int)(Math.cos(alpha)*d + orbiting.x);
		y = (int)(Math.sin(alpha)*d + orbiting.y);
		alphaSelf += omegaSelf*date2;
	}
	
	public void paint(Graphics2D g) {
		g.translate(x, y);
		g.rotate(alphaSelf);
		g.drawImage(img, (int)(-r), (int)(-r), (int)(2*r), (int)(2*r), null);
		g.rotate(-alphaSelf);
		g.translate(-x, -y);
	}
	
	Image groundMap;
	
	List<Resource> resources = new ArrayList<>();
	
	public void generateSurfaceMap() {
		double[][] dMap = Noise.generateNoiseMap(0, 800, 400);
		groundMap = new BufferedImage(800, 400, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)groundMap.getGraphics();
		Color[][] noiseMap = new Color[800][400];
		for(int i = 0; i < noiseMap.length; i++) {
			for(int j = 0; j < noiseMap[i].length; j++) {
				double value = dMap[i][j];
				// Place resources:
				Random rand = new Random();
				rand.setSeed(Double.doubleToRawLongBits(value));
				if(rand.nextDouble() <= 0.00005) {
					resources.add(new Resource(i, j, Assets.resources[(int)(Math.random()*Assets.resources.length)], (int)(20*rand.nextDouble())));
				}
				// Use noise value to linear interpolate between the two color values specified in data file.
				int r = (int)((value+1)*(color[3]-color[0]))/2 + color[0];
				int gr = (int)((value+1)*(color[4]-color[1]))/2 + color[1];
				int b = (int)((value+1)*(color[5]-color[2]))/2 + color[2];
				int d = (int)(value*128)+128;
				g.setColor(new Color(r, gr, b));
				g.fillRect(i, j, 1, 1);
				noiseMap[i][j] = new Color(d, d, d);
			}
		}
	}
	
	public void drawNoiseMap(Graphics2D g) {
		if(groundMap == null)
			generateSurfaceMap();
		g.drawImage(groundMap, 0, 1080-groundMap.getHeight(null), null);
	}
}
