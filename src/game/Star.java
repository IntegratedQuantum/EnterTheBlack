package game;

public class Star {
	public Planet[] planets;
	public Star(int planets) {
		this.planets = new Planet[planets];
		planets[0] = new Planet();
		for(int i = 1; i < planets; i++) {
			
		}
	}
}
