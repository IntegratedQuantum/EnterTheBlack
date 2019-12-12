package entertheblack.game;

import entertheblack.storage.Inventory;
import entertheblack.storage.ShipData;

// Some main info about a player.
// TODO: Add main ship here.

public class Player {
	public int techLevel = 0;
	public Inventory inv;
	public int credits = 1000;
	public ShipData mainShip;
	public Player(ShipData main) { // TODO: Read from file.
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
	}
}
