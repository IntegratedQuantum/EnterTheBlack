package entertheblack.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;

public class Options extends Screen implements ActionListener {
	ButtonHandler buttons = new ButtonHandler();
	
	public Options() {
		buttons.add(new Button(690, 190, 500, 50, this, 1, "Controls"), 0);
		buttons.add(new Button(690, 340, 500, 50, this, 2, "Graphics"), 0);
		buttons.add(new Button(690, 490, 500, 50, this, 3, "Reset Settings"), 0);
		buttons.add(new Button(690, 640, 500, 50, this, 4, "Back To Menu and Save"), 0);
		buttons.add(new Button(690, 790, 500, 50, this, 5, "Back To Menu(without Saving)"), 0);
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
		if(id == 1)
			Assets.screen = new Controls();
		if(id == 2)
			Assets.screen = new GraphicsMenu();
		if(id == 3)
			Assets.resetSettings();
		if(id == 4) {
			Assets.screen = new MainMenu();
			Assets.saveSettings();
		}
		if(id == 5)
			Assets.screen = new MainMenu();
	}

}
