package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;

// Travelling around on the planet surface.
// TODO: Add hostile environment.

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
		if(e.getKeyChar() == 'm') {
			Assets.screen = Assets.curWorld.map;
			Assets.curWorld.map.activate(this, previous.last.x, previous.last.y);
		}
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
			Assets.curWorld.player.inv.merge(lv.inv);
		}
	}
	
	public void update() {
		lv.update(move, right, left);
		lv.collect(planet.resources);
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(planet.groundMap, (int)(-lv.x*8000/800 + 960), (int)(-lv.y*4000/400 + 340), 8000, 4000, null); // TODO make it smoother.
		if(lv.x > 600)
			g.drawImage(planet.groundMap, (int)(8000 - lv.x*8000/800 + 960), (int)(-lv.y*4000/400 + 340), 8000, 4000, null);
		if(lv.x < 200)
			g.drawImage(planet.groundMap, (int)(-lv.x*8000/800 + 960 - 8000), (int)(-lv.y*4000/400 + 340), 8000, 4000, null);
		for(int i = 0; i < planet.resources.size(); i++) {
			planet.resources.get(i).paint(g, lv.x, lv.y);
		}
		planet.drawNoiseMap(g);
		for(int i = 0; i < planet.resources.size(); i++) {
			planet.resources.get(i).paintOnMap(g);
		}
		lv.paint(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(800, 680, 1120, 400);
		g.drawImage(Assets.hb, 800, 680, null);
		g.drawImage(LandingVehicle.img, 920, 700, 320, 320, null);
		//TODO: Second rect on the right side.
	}

}
