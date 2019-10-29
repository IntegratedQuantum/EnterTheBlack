package game;

import entertheblack.menu.Assets;

// A world contains all star systems and their planets.
public class World {
	
	Star [] systems;
	StarMap map;
	public World(int size) {
		map = new StarMap(Assets.readFile("systems.txt"));
	}
	
	public World(String save) {
		// TODO!
	}
	
	public Star getStar(int i) {
		return map.systems.get(i);
	}
}
