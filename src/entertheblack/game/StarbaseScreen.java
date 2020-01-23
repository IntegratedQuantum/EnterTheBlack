package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.ActionListener;
import entertheblack.gui.Customize;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;
import entertheblack.menu.Assets;
import entertheblack.storage.Inventory;

// Screen where you can sell resources/buy fuel(TODO)/enter ship outfitter

public class StarbaseScreen extends Screen implements ActionListener {
	private static final int y0 = 300;
	private static final int deltaY = 50;
	private static final int x = 900;
	
	Screen previous;
	Inventory inv;
	ButtonHandler buttons = new ButtonHandler();
	
	public StarbaseScreen(Screen prev) {
		previous = prev;
		buttons.add(new Button(690, 190, 500, 50, this, 1, "Outfit ship"), 2);
		for(int i = 0; i < Assets.resources.length; i++) {
			buttons.add(new Button(x-390, y0+i*deltaY, 130, deltaY-10, this, 2*i+4, "Buy("+Assets.resources[i].value+")"), 0);
			buttons.add(new Button(x-240, y0+i*deltaY, 130, deltaY-10, this, 2*i+5, "Sell("+Assets.resources[i].value+")"), 1);
		}
		buttons.add(new Button(690, 840, 500, 50, this, 3, "Sell All"), 2);
		buttons.add(new Button(690, 940, 500, 50, this, 2, "Exit Starbase"), 2);
		inv = Assets.curWorld.player.inv;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		buttons.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		buttons.keyReleased(e);
		if(e.getKeyCode() == 27) {
			Assets.screen = previous;
		}
	}
	
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		buttons.mouseUpdate(x, y, pressed);
	}

	@Override
	public void paint(Graphics2D g) {
		// Draw the buttons:
		buttons.paint(g);
		// Draw all resources and how much the player owns:
		for(int i = 0; i < Assets.resources.length; i++) {
			ResourceType res = Assets.resources[i];
			int y = y0+i*deltaY;
			g.drawImage(res.img, x, y, deltaY-10, deltaY-10, null);
			g.drawString(res.name, x+deltaY, y+25);
			g.drawString(""+inv.getAmount(res), x-90, y+25);
		}
		// Credits can be interpreted as a resource, so put it at the top of the list:
		g.drawString("Credits", x+deltaY, y0+25-deltaY);
		g.drawString(""+Assets.curWorld.player.credits, x-90, y0+25-deltaY);
	}

	@Override
	public void pressed(int id) {
		switch(id) {
		case 1:
			Assets.screen = new Customize(this, Assets.curWorld.player);
			return;
		case 2:
			Assets.screen = previous;
			return;
		case 3:// Sell all
			for(int i = 0; i < Assets.resources.length; i++) {
				ResourceType res = Assets.resources[i];
				int amount = inv.getAmount(res);
				Assets.curWorld.player.addCredits(res.value*amount);
				inv.remove(res, amount);
			}
			return;
		}
		// Sell items
		if((id & 1) == 1) {
			ResourceType res = Assets.resources[(id>>1)-2];
			if(inv.getAmount(res) > 0) {
				Assets.curWorld.player.addCredits(res.value);
				inv.remove(res, 1);
			}
		}
		// Buy items:
		else {
			ResourceType res = Assets.resources[(id>>1)-2];
			boolean success = Assets.curWorld.player.addCredits(-res.value);
			if(success)
				inv.add(res, 1);
		}
	}

}
