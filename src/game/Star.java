package game;

import java.awt.Color;
import java.awt.Graphics2D;

import entertheblack.fight.Ship;
import entertheblack.menu.Assets;

public class Star {
	private static final int size = 1024;
	double zoom = 540.0/size;
	public Planet[] planets;
	Ship ship;
	public Star(int p, int mainShip) {
		planets = new Planet[p];
		planets[0] = new Planet();
		for(int i = 1; i < p; i++) {
			planets[i] = new Planet(i*100 + 250*Math.random(), planets[0]);
		}
		ship = new Ship(mainShip, 0, size - (size >> 2));
	}
	int x = 0;
	public void paint(Graphics2D g) {
		x++;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080);
		/*int[][] arrayOfInt = { { -1000, -1000 }, { -1000, 0 }, { -1000, 1000 }, { 0, -1000 }, { 0, 1000 }, { 1000, -1000 }, { 1000, 0 }, { 1000, 1000 }, { 2000, -1000 }, { 2000, 0 }, { 2000, 1000 }, { 2000, 2000 }, { -1000, 2000 }, { 0, 2000 }, { 1000, 2000 }, { 0, 0 } };
		for (int i = 0; i < 16; i++) {
			g.drawImage(Assets.bg, x%1920 + arrayOfInt[i][0], arrayOfInt[i][1], null);
		}*/
		g.scale(zoom, zoom);
		g.drawImage(Assets.bg, -1024+x%2048, -1024, 2048, 2048, null);
		g.drawImage(Assets.bg, -1024-2048, -1024, 2048, 2048, null);
		g.drawImage(Assets.bg, 1024, -1024, 2048, 2048, null);
		for(int i = 0; i < planets.length; i++) {
			planets[i].paint(g);
		}
		ship.paint(g);
		g.scale(-zoom, -zoom);
	}
	
	public boolean update(boolean move, boolean right, boolean left) {
		ship.fly(move, right, left);
		double r = Math.sqrt(ship.x*ship.x+ship.y*ship.y)+200;
		zoom = 0.5*size/r;
		return ship.y-ship.r < -size || ship.x-ship.r < -size || ship.x+ship.r > size || ship.y+ship.r > size;
	}
}
