package entertheblack.storage;

import java.util.List;

import entertheblack.menu.Assets;

// Stores all important data of a species. This includes ships, parts, dialogs(TODO).
// There is no reputation between species, since species will never fight under the eyes of the player.

public class Species {
	public ShipData[] ships;
	public String name;
	public Part[] parts;
	//TODO: Add a region of influence which can be displayed in star map when discovered.
	public Species(String name) {
		this.name = name;
		// Load parts(still TODO) and ships:
		List<ShipData> list = Assets.createShipData(Assets.readFile(name+"/ships"), "Assets/"+name+"/ships");
		ships = list.toArray(new ShipData[0]);
		Assets.shipData.addAll(list); // Also add the ships to the array in assets.
	}
	
}
