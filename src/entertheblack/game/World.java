package entertheblack.game;

import entertheblack.menu.Assets;

// A world contains all star systems and their planets.
public class World {
	
	Star [] systems;
	StarMap map;
	public Player player;
	public World(int size) {
		map = new StarMap(Assets.readFile("systems.txt"));
		player = new Player();
	}
	
	public World(String save) {
		// TODO!
	}
	
	public Star getStar(int i) {
		return map.systems.get(i);
	}
}
