package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;
import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;

public class Pause extends Screen implements ActionListener {
	Screen previous;
	ButtonHandler buttons = new ButtonHandler();
	public Pause(Screen prev) {
		previous = prev;
		buttons.add(new Button(690, 190, 500, 50, this, 1, "Continue"), 0);
		buttons.add(new Button(690, 340, 500, 50, this, 2, "Save Game"), 0);
		buttons.add(new Button(690, 490, 500, 50, this, 3, "Exit To Menu"), 0);
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
	public void paint(Graphics2D g) {
		previous.paint(g);
		buttons.paint(g);
	}
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		buttons.mouseUpdate(x, y, pressed);
	}
	@Override
	public void pressed(int id) {
		if(id == 1) {
			Assets.screen = previous;
		} else if(id == 2) {
			Assets.saveGame(Assets.curWorld);
		} else if(id == 3) {
			Assets.screen = new MainMenu();
		}
	}
	
}
