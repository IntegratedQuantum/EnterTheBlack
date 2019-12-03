package entertheblack.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;

// Screen that is shown when getting in orbit of a planet. You can land here using 'L'
// TODO: Scanning animation on opening!

public class LandingScreen extends Screen {
	Planet planet;
	Star last;
	
	public LandingScreen(Planet p, Star last) {
		this.last = last;
		if(p.species != null) {
			// TODO: Interaction screen.
		}
		planet = p;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'm') {
			Assets.screen = Assets.curWorld.map;
			Assets.curWorld.map.activate(this);
		}
		if(e.getKeyCode() == 27) {
			Assets.screen = last;
		}
		if(e.getKeyChar() == 'l') {
			Assets.screen = new Surface(this, planet);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawStringCentered(Graphics2D g, String str, int size, int x, int y) {
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = g.getFontMetrics(font);
		x = x - metrics.stringWidth(str)/2;
		y = y - metrics.getHeight()/2 + metrics.getAscent();
		g.setFont(font);
		g.drawString(str, x, y);
	}
	
	public void drawStringRight(Graphics2D g, String str, int size, int x, int y) {
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = g.getFontMetrics(font);
		x = x - metrics.stringWidth(str);
		y = y - metrics.getHeight()/2 + metrics.getAscent();
		g.setFont(font);
		g.drawString(str, x, y);
	}
	
	public void drawStringLeft(Graphics2D g, String str, int size, int x, int y) {
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = g.getFontMetrics(font);
		y = y - metrics.getHeight()/2 + metrics.getAscent();
		g.setFont(font);
		g.drawString(str, x, y);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  1920,  1080);
		g.drawImage(Assets.bg, 0, 0, 1920, 1080, null);
		planet.drawNoiseMap(g);
		g.drawImage(planet.img, 860, 440, 200, 200, null);
		g.setColor(Color.WHITE);
		drawStringCentered(g, planet.name, 80, 960, 360);
		drawStringLeft(g, "Temperature = "+planet.T+" K", 20, 1100, 500);
		drawStringRight(g, planet.species == null ? "No Government" : "Government: "+planet.species, 20, 800, 500);
		drawStringLeft(g, planet.techLevel > Assets.curWorld.player.techLevel ? "Insufficient technology to Land" : "Ready to land!", 20, 1100, 550);
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// TODO Buttons for scan, land, …
	}

}
