package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Variant;

public class HyperSpace extends Screen {
	StarMap map;
	boolean move = false;
	boolean left = false;
	boolean right = false;
	public double xShip, yShip, alphaShip, vmax, turn;
	Image shipImg;
	Variant v;
	Star cameFrom;
	
	ArrayList<Fleet> fleets;

	public HyperSpace(StarMap map, Variant mainShip, Star current) {
		this(map, mainShip, current.x, current.y);
		cameFrom = current;
	}
	public HyperSpace(StarMap map, Variant mainShip, int x, int y) {
		this.map = map;
		shipImg = mainShip.img;
		vmax = mainShip.vmax;
		turn = mainShip.turnRate;
		xShip = x;
		yShip = y;
		v = mainShip;
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
		if(e.getKeyChar() == 'm') {
			Assets.screen = Assets.curWorld.map;
			Assets.curWorld.map.activate(this, -xShip, -yShip);
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
			Assets.screen = new Pause(this);
		}
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {} // Unused.
	
	@Override
	public void update() {
		if(move) {
			xShip += -vmax*Math.sin(alphaShip);
			yShip += vmax*Math.cos(alphaShip);
		}
		if(left) {
			alphaShip -= turn;
		}
		if(right) {
			alphaShip += turn;
		}
		
		// Check if the ship is entering a star system:
		for(Star system : map.systems) {
			double deltaX = system.x + xShip;
			double deltaY = system.y + yShip;
			if(Math.sqrt(deltaX*deltaX+deltaY*deltaY) < system.planets[0].r/2) {
				if(system == cameFrom) // Don't enter system instantly again after leaving.
					continue;
				Assets.screen = system;
				system.activate(v);
			}
			else if(system == cameFrom) {
				// When the player left proximity of the star allow him to land there again:
				cameFrom = null;
			}
		}
	}

	@Override
	public void paint(Graphics2D g) {
		// TODO: Draw some better background:
		g.scale(0.5, 0.5);
		g.translate(xShip+960, yShip+540);
		for(Star star : map.systems) {
			g.drawImage(star.planets[0].img, (int)(star.x-star.planets[0].r/2), (int)(star.y-star.planets[0].r/2), (int)(star.planets[0].r), (int)(star.planets[0].r), null);
		}
		g.translate(-xShip, -yShip);
		g.rotate(alphaShip);
		g.drawImage(shipImg, -50, -50, 100, 100, null);
		g.rotate(-alphaShip);
		g.translate(-960, -540);
		g.scale(2, 2);
	}
	
}
