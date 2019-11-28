package entertheblack.storage;

import java.awt.Color;
import java.awt.Graphics2D;

// Stores information about the modules present in a certain part of the ship.

public class ShipSlot {
	boolean weapon = false; // If can carry weapons.
	boolean engine = false; // Can carry engines.
	boolean reactor = false; // Can be equipped with reactors. Should always be the core section.
	Part part;
	int x;
	int y;
	ShipSlot(String[] data) {
		for(int i = 0; i < data.length; i++) {
			String[] entries = data[i].split(":");
			if(data[i].equals("Weapon")) {
				weapon = true;
			}
			if(data[i].equals("Engine")) {
				engine = true;
			}
			if(data[i].equals("Reactor")) {
				reactor = true;
			}
			if(entries.length <= 1)
				continue;
			if(entries[0].equals("Part")) {
				// TODO get part info.
			}
			else if(entries[0].equals("X")) {
				x = Integer.parseInt(entries[1]);
			}
			else if(entries[0].equals("Y")) {
				y = Integer.parseInt(entries[1]);
			}
		}
	}
	
	public void paint(Graphics2D g, int x, int y, int sx, int sy, int size) {
		g.setColor(new Color(weapon ? 255 : 127, engine ? 255 : 127, reactor ? 255 : 127, 200));
		g.fillRect(x+size*this.x/sx+1, y+size*this.y/sy+1, size/sx-2, size/sy-2);
	}
}
