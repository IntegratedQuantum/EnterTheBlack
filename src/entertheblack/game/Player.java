package entertheblack.game;

import java.util.ArrayList;
import java.util.List;

import entertheblack.storage.Inventory;
import entertheblack.storage.Species;
import entertheblack.storage.Variant;

// Some main info about a player.
// TODO: Add main ship here.

public class Player {
	public int techLevel = 0;
	public Inventory inv;
	public int credits = 1000;
	public Variant mainShip;
	List<Species> discoveredSpecies = new ArrayList<>(); // Store what species have been discovered so far by the player.
	List<Integer> speciesReputation = new ArrayList<>(); // Store the reputation of the player towards given species. May be altered by missions/events/conversation.
	public Player(Variant main) { // TODO: Read from file.
		inv = new Inventory(); // TODO: Make finite!
		mainShip = main;
	}
	
	// Used for adding and removing credits. Returns if transaction was successful.
	public boolean addCredits(int amount) {
		if(credits+amount < 0)
			return false;
		credits += amount;
		return true;
	}
	
	public void save(StringBuilder sb) {
		sb.append(techLevel);
		sb.append(",");
		sb.append(credits);
		sb.append("{");
		inv.save(sb);
		sb.append("}");
		sb.append("{");
		for(int i = 0; i < discoveredSpecies.size(); i++) {
			sb.append(discoveredSpecies.get(i).name);
			sb.append("=");
			sb.append(speciesReputation.get(i));
			sb.append("\n");
		}
		sb.append("}");
	}
}
