package entertheblack.game;

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
	
	public Star getStar(int i) {
		return map.systems.get(i);
	}
	
	public void save(StringBuilder sb) {
		
	}
}
