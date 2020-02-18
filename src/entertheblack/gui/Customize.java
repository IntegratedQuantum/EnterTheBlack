package entertheblack.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.Util.Graphics;
import entertheblack.game.Player;
import entertheblack.gui.components.BuySlot;
import entertheblack.gui.components.CarriedPart;
import entertheblack.gui.components.Component;
import entertheblack.gui.components.PartSlot;
import entertheblack.gui.components.SelectionPanel;
import entertheblack.gui.components.ToolTip;
import entertheblack.menu.Assets;
import entertheblack.storage.Part;
import entertheblack.storage.ShipSlot;
import entertheblack.storage.Variant;

// Menu to customize ship with modules.

public class Customize extends Screen {
	Variant mainShip;
	PartSlot[] slots;
	Screen previous;
	ToolTip tt;
	Player p;
	CarriedPart carrier;
	SelectionPanel buy;
	private static final int size = 800;
	
	public Customize(Screen prev, Player p) {
		mainShip = p.mainShip;
		this.p = p;
		slots = new PartSlot[mainShip.slots.size()];
		previous = prev;
		carrier = new CarriedPart(100, 100);
		for(int i = 0; i < slots.length; i++) {
			ShipSlot sl = mainShip.slots.get(i);
			slots[i] = new PartSlot(960-size/2+size*sl.x/mainShip.x+1, 540-size/2+size*sl.y/mainShip.y+1, size/mainShip.x-2, size/mainShip.y-2, sl, carrier);
		}
		buy = new SelectionPanel(1720, 100, 200, 600);
		for(Part part : Assets.parts) {
			BuySlot slot = new BuySlot(0, 0, 200, 200, part, carrier, p);
			buy.add(slot);
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
		buy.paint(g);
		carrier.paint(g);
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
		g.setColor(Assets.text);
		Graphics.drawStringRight(g, p.credits+" credits", 50, 1910, 50);
		if(tt != null) {
			tt.paint(g);
		}
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		boolean dropIt = pressed; // Drop the currently carried item(selling it) when the mouse clicks somewhere outside.
		tt = null;
		for(PartSlot sl : slots) {
			sl.mouseUpdate(x, y, pressed);
			if(sl.selectedM) {
				tt = sl.toolTip;
				if(tt != null)
					tt.updatePosition(x, y);
				dropIt = false;
			}
		}
		buy.mouseUpdate(x, y, pressed);
		for(Component c : buy.components) {
			PartSlot sl = (PartSlot) c;
			if(sl.selectedM) {
				tt = sl.toolTip;
				if(tt != null) {
					if(x+tt.width <= 1920)
						tt.updatePosition(x, y);
					else
						tt.updatePosition(x-tt.width, y);
				}
				dropIt = false;
			}
		}
		carrier.mouseUpdate(x, y, pressed);
		if(dropIt && carrier.part != null) {
			p.credits += carrier.part.cost;
			carrier.part = null;
		}
	}

}
