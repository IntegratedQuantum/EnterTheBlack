package entertheblack.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;

public class Options extends Screen implements ActionListener {
	int buttonsel = 1;
	
	public Options() {
		buttons.add(new Button(690, 190, 500, 50, this, 1, "Controls"));
		buttons.add(new Button(690, 340, 500, 50, this, 2, "Graphics(WIP)"));
		buttons.add(new Button(690, 490, 500, 50, this, 3, "Back To Menu and Save"));
		buttons.add(new Button(690, 640, 500, 50, this, 4, "Back To Menu(without Saving)"));
		buttons.get(0).selectedB = true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 38 && buttonsel > 1) {
			buttons.get(buttonsel-1).selectedB = false;
			buttonsel--;
			buttons.get(buttonsel-1).selectedB = true;
		}
		
		if (e.getKeyCode() == 40 && buttonsel < buttons.size() && buttonsel > 0) {
			buttons.get(buttonsel-1).selectedB = false;
			buttonsel++;
			buttons.get(buttonsel-1).selectedB = true;
		}
		
		if ((e.getKeyCode() == 17 || e.getKeyCode() == 10) && buttonsel > 0) {
			buttons.get(buttonsel-1).pressedB = true;
			buttonsel *= -1;
		}
		
		if (e.getKeyCode() != 17 && e.getKeyCode() != 10 && buttonsel < 0) {
			buttonsel *= -1;
			buttons.get(buttonsel-1).pressedB = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == 17 || e.getKeyCode() == 10) && buttonsel < 0) {
			buttons.get(-buttonsel-1).trigger();
			this.buttonsel = 1;
		}
	}

	@Override
	public void paint(Graphics2D g) {}

	@Override
	public void pressed(int id) {
		if(id == 1)
			Assets.screen = new Controls();
		// TODO graphics settings.
		if(id == 3) {
			Assets.screen = new MainMenu();
			Assets.saveSettings();
		}
		if(id == 4)
			Assets.screen = new MainMenu();
	}

}
