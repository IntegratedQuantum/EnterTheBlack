package entertheblack.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.menu.Assets;

// General screen to access multiple different screens with the same interface.

public abstract class Screen {
	
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);

	public void mouseUpdate(int x, int y, boolean pressed) {}
	public void mouseWheel(int num) {}
	
	public void paintScreen(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080); // Removing this line strangely results in lags. USE THIS IN EVERY DYNAMIC PAINT!
		g.drawImage(Assets.bgMenu, 0, 0, 1920, 1080, null);
		g.setFont(new Font("Sansserif", 0, 20));
		paint(g);
	}
	
	public abstract void paint(Graphics2D g);
	
	public void update() {}
}
