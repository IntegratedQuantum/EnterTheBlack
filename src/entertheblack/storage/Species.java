package entertheblack.storage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entertheblack.game.Star;
import entertheblack.game.StarMap;
import entertheblack.menu.Assets;

// Stores all important data of a species. This includes ships, parts, dialogs(TODO).
// There is no reputation between species, since species will never fight under the eyes of the player.

public class Species {
	public ShipData[] ships;
	public String name;
	public Part[] parts;
	public ArrayList<Star> systems;
	public Color color;
	//TODO: Add a region of influence which can be displayed in star map when discovered.
	public Species(String name) {
		this.name = name;
		color = new Color((int)(Math.random()*128)+128, (int)(Math.random()*128)+128, (int)(Math.random()*128)+128, 127); // TODO: Use a color list or something else.
		// Load parts(still TODO) and ships:
		ArrayList<ShipData> list = Assets.createShipData(Assets.readFile(name+"/ships"), "Assets/"+name+"/ships");
		ships = list.toArray(new ShipData[0]);
		Assets.shipData.addAll(list); // Also add the ships to the array in assets.
	}
	
	// Initializes game specific data like a list of systems governed by this species.
	//TODO: Character traits, initial reputation, reputation with other species.
	public void initializeGameSpecifics(Node data) {
		systems = new ArrayList<>();
		for(String name : data.lines) {
			systems.add(Assets.curWorld.map.getStar(name));
		}
	}
	public void initializeGameSpecifics(ArrayList<Star> sys) {
		systems = new ArrayList<>();
		int len = sys.size();
		Star home = null;
		do {
			home = sys.get((int)(Assets.random.nextDouble()*len));
			 // Don't have the home planet near earth(a circle of 10 pc is empty):
			if(home.x*home.x+home.y*home.y <= 100000000) {
				home = null;
			}
		} while(home == null);
		int influence = (int)(Math.random()*10000)+10000;
		// Just assign all planets within the influence to this species.
		// TODO: Simulate past alien wars if two species get close to each other.
		// TODO: Idea: small outposts at critical points outside the area of influence.
		for(Star star : sys) {
			if(Math.sqrt(((double)home.x-star.x)*(home.x-star.x) + ((double)home.y-star.y)*(home.y-star.y)) <= influence) {
				systems.add(star);
			}
		}
	}
	
	public void saveGameSpecifics(StringBuilder sb) {
		sb.append("\nName=\""+name+"\"");
		sb.append("{");
		for(int i = 0; i < systems.size(); i++) {
			sb.append("\n"+systems.get(i).name);
		}
		sb.append("}");
	}
	
	public void markHome(Graphics2D g) {
		for(Star star : systems) {
			int r = StarMap.getRadius(star.planets[0]);
			g.setColor(color);
			g.fillOval(star.x-r*200, star.y-r*200, r*400, r*400);
		}
	}
}
