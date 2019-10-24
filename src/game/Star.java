package game;

import java.awt.Graphics2D;

public class Star {
	public Planet[] planets;
	public Star(int p) {
		planets = new Planet[p];
		planets[0] = new Planet();
		for(int i = 1; i < p; i++) {
			planets[i] = new Planet(i*100 + 250*Math.random(), planets[0]);
		}
	}
	
	public void paint(Graphics2D g) {
		for(int i = 0; i < planets.length; i++) {
			planets[i].paint(g);
		}
	}
}
