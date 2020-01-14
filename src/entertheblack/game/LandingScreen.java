package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.Util.Graphics;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;

// Screen that is shown when getting in orbit of a planet. You can land here using 'L'
// TODO: Scanning animation on opening(also useful to disguise noise generation time)!
// TODO: Better GUI(Draw a starbase if existent, buttons, better info screen, more info on minimap).

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
		if(e.getKeyChar() == 'l') {
			Assets.screen = new Surface(this, planet);
		}
		if(e.getKeyChar() == 's') {
			Assets.screen = new StarbaseScreen(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Assets.screen = last;
		}
	}

	@Override
	public void paint(Graphics2D g) {
		planet.drawNoiseMap(g);
		g.drawImage(planet.img, 860, 440, 200, 200, null);
		g.setColor(Assets.text);
		Graphics.drawStringCentered(g, planet.name, 80, 960, 360);
		Graphics.drawStringLeft(g, "Temperature = "+planet.T+" K", 20, 1100, 500);
		Graphics.drawStringRight(g, planet.species == null ? "No Government" : "Government: "+planet.species, 20, 800, 500);
		if(planet.hasStarBase)
			Graphics.drawStringRight(g, "Press 'S' to enter starbase.", 20, 800, 540);
		Graphics.drawStringLeft(g, planet.techLevel > Assets.curWorld.player.techLevel ? "Insufficient technology to Land" : "Ready to land!", 20, 1100, 550);
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// TODO Buttons for scan, land, …
	}

}
