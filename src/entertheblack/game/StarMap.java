package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// A map of systems.
// TODO: Use for automatic hyperspace navigation.

public class StarMap extends Screen {
	public List<Star> systems = new ArrayList<>();

	public StarMap(Node data, String file) {
		for(Node node : data.nextNodes) {
			systems.add(new Star(node, file));
		}
	}
	
	// Save all data from this system:
	public void save(StringBuilder file) {
		for(Star star : systems) {
			file.append("{");
			star.save(file);
			file.append("}");
		}
	}
	
	Screen previous;
	public void activate(Screen prev) {
		previous = prev; // Store the previous screen upon opening the map.
	}
	
	private static int getRadius(Planet p) {
		double r = Math.sqrt(p.r/10);
		if(r > 10)
			r = 9+Math.log(r); // Prevent r from growing too fast.
		return (int)r;
	}
	
	public void paint(Graphics2D g) {
		g.translate(960, 540);
		for(Star system : systems) {
			int r = getRadius(system.planets[0]);
			g.drawImage(system.planets[0].img, system.x/100-r, system.y/100-r, r*2, r*2, null);
		}
		g.translate(-960, -540);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'm') {
			Assets.screen = previous;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Assets.screen = previous;
		}
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// TODO: Show Information when hovering above with mouse.
	}
}
