package entertheblack.storage;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import entertheblack.Util.Logger;
import entertheblack.menu.Assets;

// A ship equipped with special parts.

public class Variant {
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
	public String description; // description for the ShipSelection Screen.
	public List<Slot> prim = new ArrayList<>();
	public List<Slot> secn = new ArrayList<>();
	public List<ShipSlot> slots = new ArrayList<>(); // Slots that can be put things into.
	public ShipData sd;
	public Variant(Node data, String file) { // Only accepts trimmed data!
		String[] lines = data.lines;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].length() == 0) continue; // Skip empty lines to prevent unnecessary error report.
			String [] parts = lines[i].split("=");
			if(parts[0].equals("Name")) {
				name = parts[1];
			} else if(parts[0].equals("Ship")) {
				useShip(Assets.getShipData(parts[1]));
			} else if(parts[0].matches(".*,.*")) {
				String[] coords = parts[0].split(",");
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);
				for(ShipSlot slot : slots) {
					if(slot.x == x && slot.y == y) {
						slot.part = Assets.getPart(parts[1]);
						break;
					}
				}
			} else {
				String message;
				if(parts.length >= 2)
					message = "Unknown argument for type Variant \"" + parts[0] + "\" with value \"" + parts[1] + "\". Skipping line!";
				else
					message = "Unknown argument for type Variant \"" + parts[0] + "\" without value. Skipping line!";
				Logger.logWarning(file, data.lineNumber[i], message);
			}
		}
		finalize();
	}
	
	public void useShip(ShipData sd) {
		this.sd = sd;
		for(ShipSlot s : sd.slots) {
			slots.add(s.copy());
		}
	}
	
	// Pick up all data and sum it together.
	public void finalize() {
		primary = sd.primary;
		secondary = sd.secondary;
		wd1 = sd.wd1;
		wd2 = sd.wd2;
		health = sd.health;
		energy = sd.energy;
		mass = sd.mass;
		size = sd.size;
		acceleration = sd.acceleration;
		vmax = sd.vmax;
		turnRate = sd.turnRate;
		energyGeneration = sd.energyGeneration;
		img = sd.img;
		x = sd.x;
		y = sd.y;
		description = sd.description;
		prim = sd.prim;
		secn = sd.secn;
		for(ShipSlot s : slots) {
			Part p = s.part;
			if(p == null) continue;
			health += p.hull;
			energy += 0; // TODO!
			mass += p.mass;
			acceleration += p.force;
			vmax += p.speed;
			turnRate += p.turnSpeed;
			energyGeneration += p.powerProd-p.passivePowerConsumption;
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
