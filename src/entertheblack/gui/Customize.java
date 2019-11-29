package entertheblack.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entertheblack.storage.ShipData;
import entertheblack.storage.ShipSlot;

// Menu to customize ship with modules.

public class Customize extends Screen {
	ShipData mainShip;
	private static final int size = 400;
	
	public Customize(ShipData sd) {
		mainShip = sd;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(mainShip.img, 960-size/2, 540-size/2, size, size, null);
		for(ShipSlot sl : mainShip.slots) {
			sl.paint(g, 960-size/2, 540-size/2, mainShip.x, mainShip.y, size);
		}
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// TODO Auto-generated method stub
	}

}
