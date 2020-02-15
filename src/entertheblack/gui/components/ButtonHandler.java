package entertheblack.gui.components;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

// Handles all buttons for the display.
// Handles mainly button selection.

public class ButtonHandler {
	List<List<Button>> buttonArea;
	int x, y;
	boolean pressed;
	public ButtonHandler() {
		buttonArea = new ArrayList<>();
		x = y = 0;
		pressed = false;
	}
	// Adds a button to the bottom of the list at index x:
	public void add(Button b, int x) {
		if(buttonArea.size() > x) {
			buttonArea.get(x).add(b);
			if(x == 0 && buttonArea.get(x).size() == 1)
				b.selectedB = true;
		} else {
			buttonArea.add(new ArrayList<Button>());
			add(b, x);
		}
	}
	public void keyPressed(KeyEvent e) {
		if(pressed || buttonArea.size() == 0)
			return;
		if(e.getKeyCode() == 38 && y > 0 && buttonArea.get(x).size() > 0) {
			buttonArea.get(x).get(y).selectedB = false;
			y--;
			buttonArea.get(x).get(y).selectedB = true;
		}
		else if(e.getKeyCode() == 40 && y < buttonArea.get(x).size()-1) {
			buttonArea.get(x).get(y).selectedB = false;
			y++;
			buttonArea.get(x).get(y).selectedB = true;
		}
		else if((e.getKeyCode() == 17 || e.getKeyCode() == 10) && buttonArea.get(x).size() > 0) {
			buttonArea.get(x).get(y).pressedB = true;
			pressed = true;
		}
		else if(e.getKeyCode() == 39 && x < buttonArea.size()-1) {
			buttonArea.get(x).get(y).selectedB = false;
			x++;
			if(buttonArea.get(x).size() <= y)
				y = buttonArea.get(x).size()-1;
			buttonArea.get(x).get(y).selectedB = true;
		}
		else if(e.getKeyCode() == 37 && x > 0) {
			buttonArea.get(x).get(y).selectedB = false;
			x--;
			if(buttonArea.get(x).size() <= y)
				y = buttonArea.get(x).size()-1;
			buttonArea.get(x).get(y).selectedB = true;
		}
	}
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == 17 || e.getKeyCode() == 10) && pressed) {
			buttonArea.get(x).get(y).trigger();
			buttonArea.get(x).get(y).pressedB = false;
			pressed = false;
		}
	}
	public void mouseUpdate(int x, int y, boolean pressed) {
		for(List<Button> l : buttonArea) {
			for(Button b : l) {
				b.mouseUpdate(x, y, pressed);
			}
		}
	}
	public void paint(Graphics2D g) {
		for(List<Button> l : buttonArea) {
			for(Button b : l) {
				b.paint(g);
			}
		}
	}
}
