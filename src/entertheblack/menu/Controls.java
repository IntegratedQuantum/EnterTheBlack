package entertheblack.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.Util.Graphics;
import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;
import entertheblack.gui.components.KeyButton;

// SettingsScreen to change Controls.

public class Controls extends Screen implements ActionListener {
	ButtonHandler buttons = new ButtonHandler();
	boolean showMessage = false;
	public Controls() {
		buttons.add(new KeyButton(690, 190, 200, 65, this, 1, "Player 1: Turn left", 0), 0);
		buttons.add(new KeyButton(690, 340, 200, 65, this, 2, "Player 1: Turn right", 1), 0);
		buttons.add(new KeyButton(690, 490, 200, 65, this, 3, "Player 1: Shoot Primary", 2), 0);
		buttons.add(new KeyButton(690, 640, 200, 65, this, 4, "Player 1: Shoot Secondary", 3), 0);
		buttons.add(new KeyButton(690, 790, 200, 65, this, 5, "Player 1: Forward", 4), 0);
		buttons.add(new Button(690, 940, 200, 50, this, -1, "Back to Menu"), 0);
		buttons.add(new KeyButton(940, 190, 200, 65, this, 6, "Player 2: Turn left", 5), 1);
		buttons.add(new KeyButton(940, 340, 200, 65, this, 7, "Player 2: Turn right", 6), 1);
		buttons.add(new KeyButton(940, 490, 200, 65, this, 8, "Player 2: Shoot Primary", 7), 1);
		buttons.add(new KeyButton(940, 640, 200, 65, this, 9, "Player 2: Shoot Secondary", 8), 1);
		buttons.add(new KeyButton(940, 790, 200, 65, this, 10, "Player 2: Forward", 9), 1);
	}
	
	int buttonsel = 1;
	int controlchange = 0;

	@Override
	public void keyPressed(KeyEvent e) {		
		if (this.controlchange > 0) {
			Assets.Controls[this.controlchange - 1] = e.getKeyCode();
			this.controlchange = 0;
			showMessage = false;
		}
		if(!showMessage)
			buttons.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!showMessage)
			buttons.keyReleased(e);
	}
	
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		if(!showMessage)
			buttons.mouseUpdate(x, y, pressed);
	}

	@Override
	public void paint(Graphics2D g) {
		buttons.paint(g);
		if(showMessage) {
			g.setColor(Color.BLACK);
			g.fillRect(640, 480, 640, 120);
			g.setColor(Assets.text);
			Graphics.drawStringCentered(g, "Please select a key", 50, 960, 540);
		}
	}

	@Override
	public void pressed(int id) {
		if(id == -1) {
			Assets.screen = new Options();
		}
		else {
			controlchange = id;
			showMessage = true;
		}
	}
}
