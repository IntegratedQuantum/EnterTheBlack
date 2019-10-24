package game;
// A world contains all star systems and their planets.
public class World {
	Star [] systems;
	public World(int size) {
		systems = new Star[size];
		for(int i = 0; i < size; i++) {
			systems[i] = new Star(5);
		}
	}
	
	public World(String save) {
		// TODO!
	}
	
	public Star getStar(int i) {
		return systems[i];
	}
}
