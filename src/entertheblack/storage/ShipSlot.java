package entertheblack.storage;

// Stores information about the modules present in a certain part of the ship.

public class ShipSlot {
	public boolean weapon = false; // If can carry weapons.
	public boolean engine = false; // Can carry engines.
	public boolean reactor = false; // Can be equipped with reactors. Should always be the core section.
	public Part part;
	public int x;
	public int y;
	// Makes a copy of this object.
	public ShipSlot copy() {
		ShipSlot cp = new ShipSlot();
		cp.weapon = weapon;
		cp.engine = engine;
		cp.reactor = reactor;
		cp.part = null;
		cp.x = x;
		cp.y = y;
		return cp;
	}
	private ShipSlot() {}
	ShipSlot(String[] data) {
		for(int i = 0; i < data.length; i++) {
			String[] entries = data[i].split(":");
			if(data[i].equals("Weapon")) {
				weapon = true;
			}
			if(data[i].equals("Engine")) {
				engine = true;
			}
			if(data[i].equals("Reactor")) {
				reactor = true;
			}
			if(entries.length <= 1)
				continue;
			if(entries[0].equals("Part")) {
				// TODO get part info.
			}
			else if(entries[0].equals("X")) {
				x = Integer.parseInt(entries[1]);
			}
			else if(entries[0].equals("Y")) {
				y = Integer.parseInt(entries[1]);
			}
		}
	}
}
