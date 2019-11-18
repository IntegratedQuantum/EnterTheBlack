package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.Image;

import entertheblack.fight.Ship;
import entertheblack.storage.ShipData;

public class Fleet {
	static int radius = 10;
	double x, y;
	double v;
	String gov;
	ShipData sd;
	Image img;
	public Fleet(String government, int x, int y, ShipData sd) {
		gov = government;
		this.x = x;
		this.y = y;
		this.sd = sd;
	}
	public void update(Ship player) {
		double Δx = player.x - x;
		double Δy = player.y - y;
		double r = Math.sqrt(Δx*Δx + Δy*Δy);
		x += Δx/r*v;
		y += Δy/r*v;
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(img, (int)x-radius, (int)y-radius, 2*radius, 2*radius, null);
	}
}
