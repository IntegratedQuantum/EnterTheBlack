package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import entertheblack.Util.Logger;
import entertheblack.menu.Assets;
import entertheblack.storage.Inventory;

// Vehicle to explore surface.
// TODO: Add proper graphics.

public class LandingVehicle {
	private static double MAX_HEALTH = 100;
	private static double omega = 0.05;
	private static int r = 50;
	static Image img = Assets.getImage("LV.png");
	private static int INV_SIZE = 100;
	static {
		if(img == null) {
			Logger.logError("LV loading", "Couldn't find assets/LV.png");
			img = Assets.getPlanetImg(""); // Take a random planet as landing vehicle :D
		}
	}
	public double x, y;
	double alpha;
	double vMax = 0.5;
	double vx, vy;
	double health = MAX_HEALTH;
	Inventory inv;
	// TODO: Resources.
	public LandingVehicle(int x0, int y0, int alpha0) {
		x = x0;
		y = y0;
		alpha = alpha0;
		inv = new Inventory(INV_SIZE);
	}
	
	public void update(boolean move, boolean right, boolean left) {
		if(move) {
			x += vx;
			y += vy;
			if(x > 800)
				x -= 800;
			else if(x < 0)
				x += 800;
			if(y > 400)
				y = 400;
			else if(y < 0)
				y = 0;
		}
		double alphaNew = alpha;
		if(right && !left) {
			if (alpha >= 2*Math.PI) {
				alpha -= 2*Math.PI;
			}
			alpha += omega;
		}
		else if(left && !right) {
			if (alpha <= 0) {
				alpha += 2*Math.PI;
			}
			alpha -= omega;
		}
		if(alphaNew != alpha) {
			vx = vMax*Math.cos(alpha - Math.PI/2);
			vy = vMax*Math.sin(alpha - Math.PI/2);
		}
	}
	
	public void collect(List<Resource> resources) {
		for(int i = 0; i < resources.size(); i++) {
			double deltax = x - resources.get(i).x;
			double deltay = y - resources.get(i).y;
			if(Math.sqrt(deltax*deltax + deltay*deltay) < 7) {
				inv.add(resources.get(i));
				if(resources.get(i).amount == 0) {
					resources.remove(i);
				}
			}
		}
	}
	
	public void paint(Graphics2D g) {
		g.translate(960, 340);
		g.rotate(alpha);
		g.drawImage(img, -r, -r, r+r, r+r, null);
		g.rotate(-alpha);
		g.translate(-960, -340);
		// Draw position on minimap:
		g.translate(x, y + 680);
		g.rotate(alpha);
		g.drawImage(img, -8, -8, 16, 16, null);
		g.rotate(-alpha);
		g.translate(-x, -y - 680);
		g.setColor(Color.BLACK);
		g.drawString(""+inv.total+"/"+INV_SIZE, 1800, 680);
	}
}
