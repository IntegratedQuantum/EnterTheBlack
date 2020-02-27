package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import entertheblack.fight.SPGame;
import entertheblack.fight.Ship;
import entertheblack.menu.Assets;
import entertheblack.storage.Variant;

// Represents a group of ships in a system or hyperspace.
// TODO: Proper graphics.
// TODO: Multiple different ships inside one fleet.
// TODO: Open diplomacy screen on contact.

public class Fleet {
	int radius;
	double x, y;
	double vel;
	double alpha;
	String gov;
	Variant v;
	Image img;
	public Fleet(String government, int x, int y, Variant v) {
		gov = government;
		this.x = x;
		this.y = y;
		this.v = v;
		radius = v.size; // 2*v.radius
		vel = v.vmax/1.1; // Let it fly slightly slower than the equivalent ship would.
		img = v.img; // Give it the image of the ship it represents.
	}
	public boolean update(Ship player, Star cur) {
		double deltax = player.x - x;
		double deltay = player.y - y;
		double r = Math.sqrt(deltax*deltax + deltay*deltay);
		if(r <= (radius+player.r)) {
			// Enter fight when it hits the player. TODO: interaction screen.
			Assets.screen = new SPGame(player.v, v, cur);
			return true;
		}
		x += deltax/r*vel;
		y += deltay/r*vel;
		alpha = Math.atan(deltay/deltax);
		alpha += Math.PI/2;
		if(deltax < 0) alpha += Math.PI;
		return false;
	}
	
	public void paint(Graphics2D g) {
		if(img != null) {
			g.translate(x, y);
			g.rotate(alpha);
			g.drawImage(img, -radius, -radius, 2*radius, 2*radius, null);
			g.rotate(-alpha);
			g.translate(-x, -y);
		} else {
			g.setColor(Color.BLUE);
			g.fillOval((int)x-radius, (int)y-radius, 2*radius, 2*radius);
		}
	}
}
