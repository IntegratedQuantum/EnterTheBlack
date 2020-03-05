package entertheblack.game;

import entertheblack.Util.Logger;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// A world contains all star systems and their planets.
// TODO: Make this better.

public class World {
	public StarMap map;
	public Player player;
	public World(int size) {
		map = new StarMap(Assets.readFile("systems.txt"), "Assets/systems.txt", 40, 10000, Assets.readFile("star_names.txt"));
		player = new Player(Assets.variants.get(0), map);
	}
	
	public World(Node save, String file) {
		map = new StarMap(save.nextNodes[0], "Assets/saves", 0, 0, null);
		player = new Player(save.nextNodes[1], file);
		// TODO!
	}
	public Screen getStart(Node save, String file) {
		// Get the coordinates of the player in the star map:
		String[] lines = save.lines;
		int x = 0;
		int y = 0;
		int xSys = 0;
		int ySys = 0;
		for(int i = 0; i < lines.length; i++) {
			String[] val = lines[i].split("=");
			if(val.length < 2)
				continue;
			if(val[0].equals("X")) { // In milli parsec.
				x = Integer.parseInt(val[1]);
			} else if(val[0].equals("Y")) {  // In milli parsec.
				y = Integer.parseInt(val[1]);
			} else if(val[0].equals("XSystem")) {
				xSys = Integer.parseInt(val[1]);
			} else if(val[0].equals("YSystem")) {
				ySys = Integer.parseInt(val[1]);
			} else {
				// Only give error message when the string isn't empty:
				if(val.length > 1 || val[0].length() > 0) {
					Logger.logWarning(file, save.lineNumber[i], "Unknown argument for World Coordinates \""+val[0]+"\" with value \""+val[1]+"\". Skipping line!");
				}
			}
		}
		// Check if the coordinates correspond to a system. Otherwise start in hyperspace.
		Star star = map.getStar(x, y);
		if(star != null) {
			star.ship.x = xSys;
			star.ship.y = ySys;
			return star;
		}
		// Start in hyperspace:
		return new HyperSpace(map, player.mainShip, x, y);
	}
	
	public Star getStar(int i) {
		return map.systems.get(i);
	}
	
	public void save(StringBuilder file) {
		file.append("{");
		map.save(file);
		file.append("}");
		file.append("{");
		player.save(file);
		file.append("}");
	}
}
