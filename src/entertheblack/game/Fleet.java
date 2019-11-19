package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import entertheblack.fight.SPGame;
import entertheblack.fight.Ship;
import entertheblack.menu.Assets;
import entertheblack.storage.ShipData;

public class Fleet {
	static int radius = 50;
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
		v = sd.vmax/110; // Let it fly slightly slower than the equivalent ship would.
	}
	public void update(Ship player) {
		double Δx = player.x - x;
		double Δy = player.y - y;
		double r = Math.sqrt(Δx*Δx + Δy*Δy);
		if(r <= (radius+player.r)) {
			// Enter fight when it hits the player 
			Assets.screen = new SPGame(player.sd, sd);
		}
		x += Δx/r*v;
		y += Δy/r*v;
	}
	
	public void paint(Graphics2D g) {
		if(img != null)
			g.drawImage(img, (int)x-radius, (int)y-radius, 2*radius, 2*radius, null);
		else {
			g.setColor(Color.BLUE);
			g.fillOval((int)x-radius, (int)y-radius, 2*radius, 2*radius);
		}
	}
}
