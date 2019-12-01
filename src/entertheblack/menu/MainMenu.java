package entertheblack.menu;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entertheblack.game.SGame;
import entertheblack.gui.ActionListener;
import entertheblack.gui.Customize;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;

public class MainMenu extends Screen implements ActionListener {
	List<Button> buttons = new ArrayList<>();
	int buttonsel = 1;
	
	public MainMenu() {
		buttons.add(new Button(690, 190, 500, 50, this, 1, "Singleplayer"));
		buttons.add(new Button(690, 340, 500, 50, this, 2, "Change Ship"));
		buttons.add(new Button(690, 490, 500, 50, this, 3, "Multiplayer"));
		buttons.add(new Button(690, 640, 500, 50, this, 4, "Options"));
		buttons.add(new Button(690, 790, 500, 50, this, 5, "Test")); // Used to test new features.
		buttons.add(new Button(690, 940, 500, 50, this, 6, "Quit"));
		buttons.get(0).selectedB = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(buttonsel < 0 && e.getKeyCode() != 17) {
			this.buttonsel *= -1;
			buttons.get(buttonsel-1).pressedB = false;
		}
		
		if (e.getKeyCode() == 38 && buttonsel > 1) {
			buttons.get(buttonsel-1).selectedB = false;
			this.buttonsel--;
			buttons.get(buttonsel-1).selectedB = true;
		}
		
		if (e.getKeyCode() == 40 && buttonsel < buttons.size() && buttonsel > 0) {
			buttons.get(buttonsel-1).selectedB = false;
			this.buttonsel++;
			buttons.get(buttonsel-1).selectedB = true;
		}
		
		if (e.getKeyCode() == 17 && buttonsel > 0) {
			buttons.get(buttonsel-1).pressedB = true;
			this.buttonsel *= -1;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 17 && buttonsel < 0) {
			buttons.get(-buttonsel-1).trigger();
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(Assets.bg, 0, 0, 1920, 1080, null);
		g.setFont(new Font("Sansserif", 0, 20));
		for(Button b : buttons) {
			b.paint(g);
		}
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		for(Button b : buttons) {
			b.mouseUpdate(x, y, pressed);
		}
	}

	@Override
	public void pressed(int id) {
		switch(id) {
		case 1:
			Assets.screen = new SGame(-1);
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
			Assets.screen = new Customize(Assets.shipData.get(0));
			break;
		case 6:
			System.exit(1);
			break;
		}
	}
}
