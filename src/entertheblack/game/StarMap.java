package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entertheblack.Util.Graphics;
import entertheblack.Util.Logger;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// A map of systems.
// Also a GUI element for displaying these systems.
// TODO: Use for automatic hyperspace navigation.
// TODO: Enable/disable species location marking.

public class StarMap extends Screen {
	private static final double MAX_ZOOM = 1, MIN_ZOOM = 0.001;
	public ArrayList<Star> systems = new ArrayList<>();
	double x=0, y=0;
	private int lastMouseX = 0;
	private int lastMouseY = 0;
	private double playerX, playerY; // Position of player when opening the map.
	private double zoom;
	private Star hovered;

	public StarMap(Node data, String file, int constellations, int averageDistance, Node starNames) {
		// Generate the stars of the data file:
		for(Node node : data.nextNodes) {
			systems.add(new Star(node, file));
		}
		// Generate an additional amount of random stars.
		if(constellations > 0) {
			// The total radius of the map:
			double maxRadius = averageDistance*Math.sqrt(constellations);
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
					int xStar, yStar, size;
					boolean toClose;
					// Choose random star coordinates. Discard the positions, if they are to close to another star.
					do {
						toClose = false;
						double randomX = 2*Assets.random.nextDouble()-1;
						randomX = randomX*Math.sqrt(Math.abs(randomX));
						double randomY = 2*Assets.random.nextDouble()-1;
						randomY = randomY*Math.sqrt(Math.abs(randomY)); // The number of stars is proportional to 1/r^1.5
						xStar = (int)(averageDistance*randomX*0.7)+x;
						yStar = (int)(averageDistance*randomY*0.7)+y;
						size = 100+(int)(200*Assets.random.nextDouble());
						for(Star other : systems) {
							long deltaX = xStar-other.x;
							long deltaY = yStar-other.y;
							if(deltaX*deltaX + deltaY*deltaY <= 8*(size+other.planets[0].r)*(size+other.planets[0].r)) {
								toClose = true;
								break;
							}
						}
					} while(toClose);
					systems.add(new Star(name, xStar, yStar, size));
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
	public void activate(Screen prev, double px, double py) {
		previous = prev; // Store the previous screen upon opening the map.
		playerX = px;
		playerY = py;
		x = y = 0;
		zoom = 0.01;
	}
	
	public static int getRadius(Planet p) {
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

	public Star getStar(String name) {
		for(Star star : systems) {
			if(star.name.equals(name)) {
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
			g.drawImage(system.planets[0].img, system.x-r*100, system.y-r*100, r*200, r*200, null);
		}
		Assets.curWorld.player.drawSpecies(g);
		g.scale(1/zoom, 1/zoom);
		g.translate(-960-x, -540-y);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(1720, 0, 200, 1080);
		// Display a bigger image and some useful information on the system hovered by the cursor:
		if(hovered != null) {
			g.drawImage(hovered.planets[0].img, 1770, 50, 100, 100, null);
			g.setColor(Assets.text);
			Graphics.drawStringCentered(g, hovered.name, 20, 1820, 30);
			Graphics.drawStringLeft(g, "Distance:", 20, 1730, 240);
			Graphics.drawStringLeft(g, String.format("%.2f pc", Math.sqrt((hovered.x-playerX)*(hovered.x-playerX) + (hovered.y-playerY)*(hovered.y-playerY))/1000.0), 20, 1730, 260);
			Graphics.drawStringLeft(g, "Known planets:", 20, 1730, 300);
			Graphics.drawStringLeft(g, ""+(hovered.planets.length - 1), 20, 1730, 320);
		}
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
		if(zoom > MAX_ZOOM) {
			x *= MAX_ZOOM/zoom;
			y *= MAX_ZOOM/zoom;
			zoom = MAX_ZOOM;
		}
		if(zoom < MIN_ZOOM) {
			x *= MIN_ZOOM/zoom;
			y *= MIN_ZOOM/zoom;
			zoom = MIN_ZOOM;
		}
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// Move map if mouse is dragged:
		if(pressed) {
			this.x += x-lastMouseX;
			this.y += y-lastMouseY;
		} else { // Show Information when hovering above a star with mouse.
			hovered = null;
			// Find the closest star to the cursor:
			double xInMap = (x - 960 - this.x)/zoom;
			double yInMap = (y - 540 - this.y)/zoom;
			double closest = Double.MAX_VALUE;
			Star closestStar = null;
			for(Star star : systems) {
				double dist = (xInMap - star.x)*(xInMap - star.x) + (yInMap - star.y)*(yInMap - star.y);
				if(dist < closest) {
					closest = dist;
					closestStar = star;
				}
			}
			if(closestStar != null) {
				int r = getRadius(closestStar.planets[0])*100;
				if(closest < r*r*4)
					hovered = closestStar;
			}
		}
		lastMouseX = x;
		lastMouseY = y;
	}
}
