package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.Util.Noise;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;

public class Surface extends Screen {
	boolean move = false;
	boolean left = false;
	boolean right = false;
	
	LandingScreen previous;
	LandingVehicle lv;
	Planet planet;
	
	public Surface(LandingScreen prev, Planet p) {
		previous = prev;
		planet = p;
		lv = new LandingVehicle((int)(Math.random()*planet.groundMap.getWidth(null)), (int)(Math.random()*planet.groundMap.getHeight(null)), 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Assets.Controls[1]) {
			right = true;
		}
		if (e.getKeyCode() == Assets.Controls[0]) {
			left = true;
		}
		if (e.getKeyCode() == Assets.Controls[4]) {
			move = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Assets.Controls[1]) {
			right = false;
		}
		if (e.getKeyCode() == Assets.Controls[0]) {
			left = false;
		}
		if (e.getKeyCode() == Assets.Controls[4]) {
			move = false;
		}
		if(e.getKeyCode() == 27) {
			Assets.screen = previous;
		}
	}
	
	public void update() {
		lv.update(move, right, left);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080);
		g.drawImage(planet.groundMap, (int)( - lv.x*100), (int)( - lv.y*100), 800000, 400000, null);
		planet.drawNoiseMap(g);
		lv.paint(g);
	}

}
