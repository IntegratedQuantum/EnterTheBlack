package entertheblack.game;

import entertheblack.menu.Assets;

// A world contains all star systems and their planets.
// TODO: Make this better.

public class World {
	StarMap map;
	public Player player;
	public World(int size) {
		map = new StarMap(Assets.readFile("systems.txt"));
		player = new Player(Assets.shipData.get(0));
	}
	
	public World(String save) {
		// TODO!
	}
	
	public Star getStar(int i) {
		return map.systems.get(i);
	}
	
	public void save(StringBuilder sb) {
		for(Star s : map.systems) {
			sb.append("{");
			s.save(sb);
			sb.append("}");
		}
	}
}
