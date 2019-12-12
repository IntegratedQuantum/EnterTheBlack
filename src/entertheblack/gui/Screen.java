package entertheblack.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entertheblack.gui.components.Button;
import entertheblack.menu.Assets;

// General screen to access multiple different screens with the same interface.

public abstract class Screen {
	protected List<Button> buttons = new ArrayList<>();
	
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	
	public void mouseUpdate(int x, int y, boolean pressed) {
		for(Button b : buttons) {
			b.mouseUpdate(x, y, pressed);
		}
	}
	
	public void paintScreen(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080); // Removing this line strangely results in lags. USE THIS IN EVERY DYNAMIC PAINT!
		g.drawImage(Assets.bg, 0, 0, 1920, 1080, null);
		g.setFont(new Font("Sansserif", 0, 20));
		for(Button b : buttons) {
			b.paint(g);
		}
		paint(g);
	}
	
	public abstract void paint(Graphics2D g);
	
	public void update() {}
}
