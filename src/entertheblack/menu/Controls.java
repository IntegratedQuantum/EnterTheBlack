package entertheblack.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.KeyButton;

// SettingsScreen to change Controls.

public class Controls extends Screen implements ActionListener {
	public Controls() {
		buttons.add(new KeyButton(690, 190, 200, 65, this, 1, "Player 1: Turn left", 0));
		buttons.add(new KeyButton(690, 340, 200, 65, this, 2, "Player 1: Turn right", 1));
		buttons.add(new KeyButton(690, 490, 200, 65, this, 3, "Player 1: Shoot Primary", 2));
		buttons.add(new KeyButton(690, 640, 200, 65, this, 4, "Player 1: Shoot Secondary", 3));
		buttons.add(new KeyButton(690, 790, 200, 65, this, 5, "Player 1: Forward", 4));
		buttons.add(new Button(690, 940, 200, 50, this, 6, "Back to Menu"));
		buttons.add(new KeyButton(940, 190, 200, 65, this, 7, "Player 2: Turn left", 5));
		buttons.add(new KeyButton(940, 340, 200, 65, this, 8, "Player 2: Turn right", 6));
		buttons.add(new KeyButton(940, 490, 200, 65, this, 9, "Player 2: Shoot Primary", 7));
		buttons.add(new KeyButton(940, 640, 200, 65, this, 10, "Player 2: Shoot Secondary", 8));
		buttons.add(new KeyButton(940, 790, 200, 65, this, 11, "Player 2: Forward", 9));
		buttons.get(0).selectedB = true;
	}
	
	int buttonsel = 1;
	int controlchange = 0;

	@Override
	public void keyPressed(KeyEvent e) {		
		if (this.controlchange > 0) {
			Assets.Controls[this.controlchange - 1] = e.getKeyCode();
			this.buttonsel *= -1;
			buttons.get(buttonsel-1).pressedB = false;
			this.controlchange = 0;
			return;
		}
		if(buttonsel < 0)
			return;
		if(e.getKeyCode() == 38 && buttonsel > 1) {
			buttons.get(buttonsel-1).selectedB = false;
			buttonsel--;
			buttons.get(buttonsel-1).selectedB = true;
		}
		else if(e.getKeyCode() == 40 && 
			buttonsel < 11) {
			buttons.get(buttonsel-1).selectedB = false;
			buttonsel++;
			buttons.get(buttonsel-1).selectedB = true;
		}
		else if((e.getKeyCode() == 17 || e.getKeyCode() == 10) && buttonsel > 0) {
			buttons.get(buttonsel-1).pressedB = true;
			buttonsel *= -1;
		}
		else if(e.getKeyCode() == 39 && buttonsel <= 5) {
			buttons.get(buttonsel-1).selectedB = false;
			buttonsel += 6;
			buttons.get(buttonsel-1).selectedB = true;
		}
		else if(e.getKeyCode() == 37 && buttonsel >= 7) {
			buttons.get(buttonsel-1).selectedB = false;
			buttonsel -= 6;
			buttons.get(buttonsel-1).selectedB = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == 17 || e.getKeyCode() == 10) && buttonsel < 0) {
			buttons.get(-buttonsel-1).trigger();
		}
	}

	@Override
	public void paint(Graphics2D g) {}

	@Override
	public void pressed(int id) {
		if(id == 6) {
			Assets.screen = new Options();
		}
		else {
			controlchange = id;
			if(buttonsel > 0) {
				buttons.get(buttonsel - 1).pressedB = false;
				buttons.get(buttonsel - 1).selectedB = false;
			}
			buttonsel = -id;
			buttons.get(id-1).pressedB = true;
			buttons.get(id-1).selectedB = true;
			if(id > 6)
				controlchange--;
		}
	}
}
