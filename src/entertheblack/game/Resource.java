package entertheblack.game;

import java.awt.Graphics2D;

// Collectible on the surface. Has a certain value and might be needed for certain modules.
// TODO: Add proper graphics.
// TODO: Show amount and name when collected.

public class Resource {
	double x, y;
	public ResourceType type;
	public int amount;
	public Resource(double x, double y, ResourceType t, int amount) {
		this.x = x;
		this.y = y;
		type = t;
		this.amount = amount;
	}
	
	public void paint(Graphics2D g, double lvx, double lvy) {
		g.drawImage(type.img, (int)(-lvx*8000/800 + 960 + x*8000/800), (int)(-lvy*4000/400 + 340 + y*4000/400), 80, 80, null);
	}
	public void paintOnMap(Graphics2D g) {
		g.drawImage(type.img, (int)(x-4), (int)(y-4) + 680, 8, 8, null);
	}
}
