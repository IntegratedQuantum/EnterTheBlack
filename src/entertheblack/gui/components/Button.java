package entertheblack.gui.components;

import java.awt.Graphics2D;

import entertheblack.gui.ActionListener;
import entertheblack.menu.Assets;

// Just a simple button. Can be ppressed with mouse.
// Selection by keyboard is only possible through corresponding screen.

public class Button extends Component {
	int id; // Used for actionlistening;
	boolean selectedM; // Mouse is floating above
	public boolean selectedB; // Selected by buttons
	boolean pressedM; // Mouse is floating above
	public boolean pressedB; // Selected by buttons
	ActionListener listener;
	String text;
	public Button(int x, int y, int width, int height, ActionListener l, int id, String t) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		listener = l;
		selectedM = selectedB = pressedM = pressedB = false;
		text = t;
	}
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		selectedM = liesIn(x, y);
		if(pressedM && !pressed) {
			if(selectedM)
				trigger();
			pressedM = false;
		}
		if(selectedM && pressed)
			pressedM = true;
	}
	@Override
	public void paint(Graphics2D g) {
		g.setColor((selectedM || selectedB) ? Assets.btnsl : Assets.btn);
		g.fillRect(x, y, width, height);
		g.setColor((pressedM || pressedB) ? Assets.btnpr : Assets.btnbg);
		g.fillRect(x + 5, y + 5, width - 10, height - 10);
		g.setColor(Assets.text);
		g.drawString(text, x + 10, y + 25);
	}
	
	// Trigger a press signal to the ActionListener.
	public void trigger() {
		listener.pressed(id);
	}

	public boolean liesIn(int x, int y) { // Tests if a point lies inside the button.
		return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
	}
}
