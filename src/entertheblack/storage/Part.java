package entertheblack.storage;

import java.awt.Image;
import java.util.ArrayList;

import entertheblack.gui.components.ToolTip;
import entertheblack.menu.Assets;

// A type of weapon(TODO) or structure that can be put on a ship.

public class Part {
	public int cost; // Cost in credits
	// TODO: Add resource costs.
	public String name;
	String info; // A small info text that will be displayed in the customize screen.
	public Image img;
	
	public boolean weapon;
	public boolean engine;
	public boolean reactor;
	int techLevel; // Tech level needed to use.
	// structure Stats(mostly 0):
	int hull;
	int speed; // Max speed. Scales with 1/m_Ship
	int force; // Or engine strength.
	int mass;
	double powerProd; // Power produced per tick
	double passivePowerConsumption; // Power used per tick.
	double turnSpeed; // How fast the ship turns. Scales with 1/m_Ship
	// TODO weapon type.
	
	public ToolTip toolTip;
	public Part(Node data, String file) {
		String[] lines = data.value.split("\n");
		StringBuilder text = new StringBuilder();
		for(int i = 0; i < lines.length; i++) {
			String [] parts = lines[i].split("=");
			if(parts.length < 2) {
				if(lines[i].equals("Weapon")) {
					weapon = true;
				}
				if(lines[i].equals("Engine")) {
					engine = true;
				}
				if(lines[i].equals("Reactor")) {
					reactor = true;
				}
				continue;
			}
			if(parts[0].equals("Hull")) {
				hull = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Speed")) {
				speed = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Force")) {
				force = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Mass")) {
				mass = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Hull")) {
				hull = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("PowerProduction")) {
				powerProd = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("PowerConsumtion")) {
				passivePowerConsumption = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Turn")) {
				turnSpeed = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Cost")) {
				cost = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Name")) {
				name = parts[1];
			} else if(parts[0].equals("Tech")) {
				techLevel = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Image")) {
				img = Assets.getImage("parts/"+parts[1]+".png");
				if(img == null) {
					System.err.println("Error in "+file+" in line "+(i+1)+":");
					System.err.println("Could not find part image "+parts[1]+".png in assets/parts!");
				}
			} else {
				System.err.println("Error in "+file+" in line "+(i+1)+":");
				System.err.println("Unknown argument for type Part \"" + parts[0] + "\" with value" + parts[1] + ". Skipping line!");
				return;
			}
		}
		Node[] textNodes = data.nextNodes;
		for(Node n : textNodes) {
			text.append(n.value);
		}
		ArrayList<String> dat = new ArrayList<>();
		if(weapon) dat.add(" Weapon");
		if(reactor && dat.size() != 0) dat.set(0, dat.get(0)+", "+"Reactor");
		if(reactor && dat.size() == 0) dat.add("Reactor");
		if(engine && dat.size() != 0) dat.set(0, dat.get(0)+", "+"Engine");
		if(engine && dat.size() == 0) dat.add("Engine");
		if(dat.size() == 0) dat.add("Structure");
		if(cost != 0) dat.add("Cost: "+cost);
		if(mass != 0) dat.add("Mass: "+mass);
		if(hull != 0) dat.add("Hull Strength: "+hull);
		if(speed != 0) dat.add("Speed Increase: "+speed);
		if(force != 0) dat.add("Acceleration Increase: "+force);
		if(turnSpeed != 0) dat.add("Turning Speed Increase: "+turnSpeed);
		if(powerProd != 0) dat.add("Power Production: "+powerProd);
		if(passivePowerConsumption != 0) dat.add("Power Consumption: "+passivePowerConsumption);
		
		toolTip = new ToolTip(name+":\\"+text.toString(), 20, dat);
	}
	
	// Test if this part can be put in a slot, based on the players credits.
	public boolean fitsIn(ShipSlot sl) {
		if(weapon && !sl.weapon)
			return false;
		if(engine && !sl.engine)
			return false;
		if(reactor && !sl.reactor)
			return false;
		return true;
	}
}
