package entertheblack.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.game.Player;
import entertheblack.gui.ActionListener;
import entertheblack.gui.Customize;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;

public class MainMenu extends Screen implements ActionListener {
	ButtonHandler buttons = new ButtonHandler();
	public MainMenu() {
		buttons.add(new Button(690, 190, 500, 50, this, 1, "Singleplayer"), 0);
		buttons.add(new Button(690, 340, 500, 50, this, 2, "Change Ship"), 0);
		buttons.add(new Button(690, 490, 500, 50, this, 3, "Multiplayer"), 0);
		buttons.add(new Button(690, 640, 500, 50, this, 4, "Options"), 0);
		buttons.add(new Button(690, 790, 500, 50, this, 5, "Test"), 0); // Used to test new features.
		buttons.add(new Button(690, 940, 500, 50, this, 6, "Quit"), 0);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		buttons.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		buttons.keyReleased(e);
	}
	
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		buttons.mouseUpdate(x, y, pressed);
	}

	@Override
	public void paint(Graphics2D g) {
		buttons.paint(g);
	}

	@Override
	public void pressed(int id) {
		switch(id) {
		case 1:
			Assets.screen = new LoadOrNew();
			break;
		case 2:
			Assets.screen = new ShipSelection();
			break;
		case 3:
			Assets.screen = Assets.game;
			Assets.game.reset();
			break;
		case 4:
			Assets.screen = new Options();
			break;
		case 5:
			break;
		case 6:
			System.exit(1);
			break;
		}
	}
}
