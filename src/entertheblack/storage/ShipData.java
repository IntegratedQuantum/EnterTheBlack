package entertheblack.storage;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import entertheblack.menu.Assets;

// Data(hull, energy, ...) for each ship type.

public class ShipData {
	public String primary, secondary; // Names of the two weapons or systems.
	public WeaponData wd1, wd2;
	public int health;
	public int energy;
	public int mass;
	public int size;
	public double acceleration;
	public double vmax;
	public double turnRate;
	public double energyGeneration;
	public String name;
	public Image img;
	public int x, y;
	public List<String> description = new ArrayList<>(); // description for the ShipSelection Screen.
	public List<Slot> prim = new ArrayList<>();
	public List<Slot> secn = new ArrayList<>();
	public List<ShipSlot> slots = new ArrayList<>(); // Slots that can be put things into. 
	public ShipData(String data) { // Only accepts trimmed data!
		String[] lines = data.split("\n");
		for(int i = 0; i < lines.length; i++) {
			String [] parts = lines[i].split("=");
			if(parts.length < 2)
				continue;
			if(parts[0].equals("Primary")) {
				primary = parts[1];
			} else if(parts[0].equals("Secondary")) {
				secondary = parts[1];
			} else if(parts[0].equals("Health")) {
				health = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Energy")) {
				energy = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Mass")) {
				mass = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Size")) {
				size = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Acceleration")) {
				acceleration = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Vmax")) {
				vmax = Double.parseDouble(parts[1])/100.0;
			} else if(parts[0].equals("Turn")) {
				turnRate = Double.parseDouble(parts[1])/1000.0;
			} else if(parts[0].equals("Generation")) {
				energyGeneration = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Name")) {
				name = parts[1];
			} else if(parts[0].equals("SlotPrim")) {
				prim.add(new Slot(parts[1].split(",")));
			} else if(parts[0].equals("SlotSecn")) {
				secn.add(new Slot(parts[1].split(",")));
			} else if(parts[0].equals("Slot")) {
				slots.add(new ShipSlot(parts[1].split(",")));
			} else if(parts[0].equals("X")) {
				x = Integer.parseInt(parts[1]); // Maximum number of slots in x dimension
			} else if(parts[0].equals("Y")) {
				y = Integer.parseInt(parts[1]); // Maximum number of slots in y dimension
			} else if(parts[0].equals("Text")) {
				description.add(parts[1]); // Maximum number of slots in y dimension
			} else if(parts[0].equals("Image")) {
				img = Assets.getImage("ships/"+parts[1]+".png");
				if(img == null) {
					System.err.println("Could not find ship image "+parts[1]+".png in assets/ships!");
				}
			} else {
				System.err.println("Unknown argument for type ship \"" + parts[0] + "\" with value \"" + parts[1] + "\". Skipping line!");
				return;
			}
		}
	}
	
	public void assignWeaponData(List<WeaponData> list) {
		for(WeaponData w : list) {
			if(w.name.equals(primary))
				wd1 = w;
			if(w.name.equals(secondary))
				wd2 = w;
		}
	}
}
