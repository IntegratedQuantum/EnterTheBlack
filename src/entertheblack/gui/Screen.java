package entertheblack.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

// General screen to access multiple different screens with the same interface.

public abstract class Screen {
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	public abstract void mouseUpdate(int x, int y, boolean pressed);
	public abstract void paint(Graphics2D g);
	
	public void update() {}
}
