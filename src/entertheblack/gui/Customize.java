package entertheblack.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.components.PartSlot;
import entertheblack.gui.components.ToolTip;
import entertheblack.menu.Assets;
import entertheblack.storage.ShipSlot;
import entertheblack.storage.Variant;

// Menu to customize ship with modules.

public class Customize extends Screen {
	Variant mainShip;
	PartSlot[] slots;
	Screen previous;
	ToolTip tt;
	private static final int size = 800;
	
	public Customize(Variant v, Screen prev) {
		mainShip = v;
		slots = new PartSlot[mainShip.slots.size()];
		previous = prev;
		for(int i = 0; i < slots.length; i++) {
			ShipSlot sl = mainShip.slots.get(i);
			slots[i] = new PartSlot(960-size/2+size*sl.x/mainShip.x+1, 540-size/2+size*sl.y/mainShip.y+1, size/mainShip.x-2, size/mainShip.y-2, sl);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {} // Not needed.

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Assets.screen = previous;
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(mainShip.img, 960-size/2, 540-size/2, size, size, null);
		for(PartSlot sl : slots) {
			sl.paint(g);
		}
		g.setFont(new Font("sanserif", 0, 40));
		// Draw help:
		g.setColor(new Color(64, 64, 64, 100));
		g.drawString("No special parts", 0, 60);
		g.setColor(new Color(64, 64, 255, 100));
		g.drawString("Reactor", 0, 120);
		g.setColor(new Color(64, 255, 64, 100));
		g.drawString("Engine", 0, 180);
		g.setColor(new Color(64, 255, 255, 100));
		g.drawString("Reactor/Engine", 0, 240);
		g.setColor(new Color(255, 64, 64, 100));
		g.drawString("Weapon", 0, 300);
		g.setColor(new Color(255, 64, 255, 100));
		g.drawString("Weapon/Reactor", 0, 360);
		g.setColor(new Color(255, 255, 64, 100));
		g.drawString("Weapon/Engine", 0, 420);
		g.setColor(new Color(255, 255, 255, 100));
		g.drawString("All", 0, 480);
		if(tt != null) {
			tt.paint(g);
		}
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		tt = null;
		for(PartSlot sl : slots) {
			sl.mouseUpdate(x, y, pressed);
			if(sl.selectedM) {
				tt = sl.toolTip;
				if(tt != null)
					tt.updatePosition(x, y);
			}
		}
	}

}
