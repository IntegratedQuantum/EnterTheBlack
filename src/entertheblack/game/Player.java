package entertheblack.game;

import entertheblack.storage.Inventory;

// Some main info about a player.
// TODO: Add main ship here.

public class Player {
	public int techLevel = 0;
	public Inventory inv;
	public int credits = 0;
	public Player() { // TODO: Read from file.
		inv = new Inventory(); // TODO: Make finite!
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
