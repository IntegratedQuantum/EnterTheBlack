package entertheblack.game;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// A world contains all star systems and their planets.
// TODO: Make this better.

public class World {
	public StarMap map;
	public Player player;
	public World(int size) {
		map = new StarMap(Assets.readFile("systems.txt"), "Assets/systems.txt");
		player = new Player(Assets.variants.get(0));
	}
	
	public World(Node save) {
		map = new StarMap(save.nextNodes[0], "Assets/saves");
		player = new Player(save.nextNodes[1]);
		// TODO!
	}
	public Screen getStart(Node save) {
		// Get the coordinates of the player in the star map:
		String[] lines = save.value.split("\n");
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
