package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import entertheblack.Util.Logger;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// A map of systems.
// TODO: Use for automatic hyperspace navigation.

public class StarMap extends Screen {
	
	public List<Star> systems = new ArrayList<>();
	double x=0, y=0;
	double zoom=1;

	public StarMap(Node data, String file, int constellations, int averageDistance, Node starNames) {
		// Generate the stars of the data file:
		for(Node node : data.nextNodes) {
			systems.add(new Star(node, file));
		}
		// Generate an additional amount of random stars.
		if(constellations > 0) {
			// The total radius of the map:
			double maxRadius = averageDistance*Math.sqrt(constellations)*2;
			int averageDistSqr = averageDistance*averageDistance;
			// Start with a sorted array of indexes and do some random swaps to randomly choose n out of m numbers(will be the first n in the array):
			int[] indexes = new int[starNames.nextNodes.length];
			for(int i = 0; i < indexes.length; i++) {
				indexes[i] = i;
			}
			// Do n random swaps:
			for(int i = 0; i < constellations; i++) {
				int local = indexes[i];
				int randomIndex = Assets.random.nextInt(indexes.length);
				indexes[i] = indexes[randomIndex];
				indexes[randomIndex] = local;
			}
			// Now the first n elements of the array are random selections from the array.
			int[] xGenerated = new int[constellations];
			int[] yGenerated = new int[constellations];
			for(int i = 0; i < constellations; i++) {
				// Select a random location on the map.
				// This will lead to constellations getting stuck in each other, but that is in my opinion no issue.
				int x = (int)(Assets.random.nextDouble()*2*maxRadius-maxRadius);
				int y = (int)(Assets.random.nextDouble()*2*maxRadius-maxRadius);
				xGenerated[i] = x;
				yGenerated[i] = y;
				// Generate all stars from this constellation:
				Node constellation = starNames.nextNodes[indexes[i]];
				for(String name : constellation.lines) {
					double randomX = 2*Assets.random.nextDouble()-1;
					randomX = randomX*randomX*Math.signum(randomX);
					double randomY = 2*Assets.random.nextDouble()-1;
					randomY = randomY*randomY*Math.signum(randomY);
					int xStar = (int)(averageDistance*randomX*0.7)+x;
					int yStar = (int)(averageDistance*randomY*0.7)+y;
					systems.add(new Star(name, xStar, yStar));
					System.out.println(name);
				}
				Logger.log("Generated constellation "+starNames.lines[indexes[i]]+" at ("+x+", "+y+").");
			}
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
		x = y = 0;
		zoom = 1;
	}
	
	private static int getRadius(Planet p) {
		double r = Math.sqrt(p.r/10);
		if(r > 10)
			r = 9+Math.log(r); // Prevent r from growing too fast.
		return (int)r;
	}
	
	public Star getStar(int x, int y) {
		for(Star star : systems) {
			if(star.x == x && star.y == y) {
				return star;
			}
		}
		return null;
	}
	
	public void paint(Graphics2D g) {
		g.translate(960+x, 540+y);
		g.scale(zoom, zoom);
		for(Star system : systems) {
			int r = getRadius(system.planets[0]);
			g.drawImage(system.planets[0].img, system.x/100-r, system.y/100-r, r*2, r*2, null);
		}
		g.scale(1/zoom, 1/zoom);
		g.translate(-960+x, -540+y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'm') {
			Assets.screen = previous;
		}
		// zoom
		if(e.getKeyChar() == '+') {
			zoom *= 1.1;
			x *= 1.1;
			y *= 1.1;
		}
		if(e.getKeyChar() == '-') {
			zoom /= 1.1;
			x /= 1.1;
			y /= 1.1;
		}
		// movement
		if(e.getKeyCode() == 37) {
			x -= 10;///zoom;
		}
		if(e.getKeyCode() == 38) {
			y -= 10;///zoom;
		}
		if(e.getKeyCode() == 39) {
			x += 10;//zoom;
		}
		if(e.getKeyCode() == 40) {
			y += 10;//zoom;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Assets.screen = previous;
		}
	}
	
	@Override
	public void mouseWheel(int num) {
		double change = Math.pow(1.1, -num);
		zoom *= change;
		x *= change;
		y *= change;
	}
	int lastMouseX = 0;
	int lastMouseY = 0;

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// Move map if mouse is dragged:
		if(pressed) {
			this.x += x-lastMouseX;
			this.y += y-lastMouseY;
		}
		// TODO: Show Information when hovering above with mouse.
		
		lastMouseX = x;
		lastMouseY = y;
	}
}
