package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.Util.Graphics;
import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;
import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;

public class Pause extends Screen implements ActionListener {
	public Screen previous;
	ButtonHandler buttons = new ButtonHandler();
	boolean enterText = false;
	String text = "";
	public Pause(Screen prev) {
		previous = prev;
		buttons.add(new Button(690, 190, 500, 50, this, 1, "Continue"), 0);
		buttons.add(new Button(690, 340, 500, 50, this, 2, "Save Game"), 0);
		buttons.add(new Button(690, 490, 500, 50, this, 3, "Exit To Menu"), 0);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(!enterText) {
			buttons.keyPressed(e);
		} else {
			if(e.getKeyChar() == '') {
				text = text.substring(0, text.length()-1);
			} else if(e.getKeyChar() == '\n') { // Save when enter pressed
				Assets.saveGame(Assets.curWorld, text);
				enterText = false;
				text = "";
			} else if(e.getKeyChar() != '￿') {
				text += e.getKeyChar();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(!enterText)
			buttons.keyReleased(e);
		if(e.getKeyCode() == 27) {
			if(enterText) {
				enterText = false;
				text = "";
			} else {
				Assets.screen = previous;
			}
		}
	}
	@Override
	public void paint(Graphics2D g) {
		previous.paint(g);
		buttons.paint(g);
		if(enterText) {
			g.setColor(Assets.btnbg);
			g.fillRect(100, 150, 1720, 100);
			g.setColor(Assets.text);
			Graphics.drawStringCentered(g, "Enter save name(if it already exists, it will be overwritten)", 50, 960, 200);
			g.setColor(Assets.btnbg);
			g.fillRect(0, 550, 1920, 100);
			g.setColor(Assets.text);
			// Add a blinking cursor:
			String toDraw = text;
			if(System.currentTimeMillis() % 1000 < 500)
				toDraw = " "+toDraw+"_"; // Use special whitespace character "U+2000" in front of the word to compensate shifts in drawString.
			Graphics.drawStringCentered(g, toDraw, 50, 960, 600);
		}
	}
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		if(!enterText)
			buttons.mouseUpdate(x, y, pressed);
	}
	@Override
	public void pressed(int id) {
		if(id == 1) {
			Assets.screen = previous;
		} else if(id == 2) {
			enterText = true;
		} else if(id == 3) {
			Assets.screen = new MainMenu();
		}
	}
	
}
