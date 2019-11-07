package entertheblack.game;

import java.awt.Graphics2D;

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
		if(type == null) { // Test case. Remove later!
			g.fillOval((int)(x-2), (int)(y-2) + 680, 4, 4);
			g.fillOval((int)(-lvx*8000/800 + 960 + x*8000/800), (int)(-lvy*4000/400 + 340 + y*4000/400), 40, 40);
		}
	}
}
