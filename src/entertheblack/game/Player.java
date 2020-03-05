package entertheblack.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entertheblack.Util.Logger;
import entertheblack.menu.Assets;
import entertheblack.storage.Inventory;
import entertheblack.storage.Node;
import entertheblack.storage.Species;
import entertheblack.storage.Variant;

// Some main info about a player.
// TODO: Add main ship here.

public class Player {
	public int techLevel = 0;
	public Inventory inv;
	public int credits = 1000;
	public Variant mainShip;
	List<Species> species = new ArrayList<>();
	List<Integer> speciesReputation = new ArrayList<>(); // Store the reputation of the player towards given species. May be altered by missions/events/conversation. A reputation of Integer.MAX_VALUE means undiscovered.
	public Player(Variant main, StarMap map) {
		inv = new Inventory(); // TODO: Make finite!
		mainShip = main;
		species = Assets.species;
		for(Species spec : species) {
			spec.initializeGameSpecifics(map.systems);
		}
	}
	
	public Player(Node data, String file) {
		String[] lines = data.lines;
		for(int i = 0; i < lines.length; i++) {
			String[] val = lines[i].split("=");
			if(val[0].equals("TechLevel"))
				techLevel = Integer.parseInt(val[1]);
			else if(val[0].equals("Credits"))
				credits = Integer.parseInt(val[1]);
			else if(val[0].equals("MainShip"))
				mainShip = Assets.getVariant(val[1]);
			else {
				// Only give error message when the string isn't empty:
				if(val.length > 1 || val[0].length() > 0) {
					String message = "";
					if(val.length >= 2) {
						message = "Unknown argument for Player \""+val[0]+"\" with values: \""+val[1]+"\". Skipping line!";
					} else
						message = "Unknown argument for Player \""+val[0]+"\" without value. Skipping line!";
					Logger.logWarning(file, data.lineNumber[i], message);
				}
			}
		}
		inv = new Inventory(data.nextNodes[0], file);
		// TODO: Load species:
	}
	
	// Used for adding and removing credits. Returns if transaction was successful.
	public boolean addCredits(int amount) {
		if(credits+amount < 0)
			return false;
		credits += amount;
		return true;
	}
	
	public void save(StringBuilder sb) {
		sb.append("\nTechLevel=");
		sb.append(techLevel);
		sb.append("\nCredits=");
		sb.append(credits);
		sb.append("\nMainShip=");
		sb.append(mainShip.name);
		sb.append("{");
		inv.save(sb);
		sb.append("}");
		sb.append("{");
		for(int i = 0; i < species.size(); i++) {
			sb.append("{");
			species.get(i).saveGameSpecifics(sb);
			// TODO: save reputation
			sb.append("}");
		}
		sb.append("}");
	}
	
	public void drawSpecies(Graphics2D g) {
		for(int i = 0; i < species.size(); i++) {
			species.get(i).markHome(g);
		}
	}
}
