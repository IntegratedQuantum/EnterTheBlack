package entertheblack.storage;

import java.awt.Image;
import java.util.ArrayList;

import entertheblack.Util.Logger;
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
		String[] lines = data.lines;
		StringBuilder text = new StringBuilder();
		for(int i = 0; i < lines.length; i++) {
			String [] val = lines[i].split("=");
			if(val.length < 2) {
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
			if(val[0].equals("Hull")) {
				hull = Integer.parseInt(val[1]);
			} else if(val[0].equals("Speed")) {
				speed = Integer.parseInt(val[1]);
			} else if(val[0].equals("Force")) {
				force = Integer.parseInt(val[1]);
			} else if(val[0].equals("Mass")) {
				mass = Integer.parseInt(val[1]);
			} else if(val[0].equals("Hull")) {
				hull = Integer.parseInt(val[1]);
			} else if(val[0].equals("PowerProduction")) {
				powerProd = Double.parseDouble(val[1]);
			} else if(val[0].equals("PowerConsumtion")) {
				passivePowerConsumption = Double.parseDouble(val[1]);
			} else if(val[0].equals("Turn")) {
				turnSpeed = Double.parseDouble(val[1]);
			} else if(val[0].equals("Cost")) {
				cost = Integer.parseInt(val[1]);
			} else if(val[0].equals("Name")) {
				name = val[1];
			} else if(val[0].equals("Tech")) {
				techLevel = Integer.parseInt(val[1]);
			} else if(val[0].equals("Image")) {
				img = Assets.getImage("parts/"+val[1]+".png");
				if(img == null) {
					Logger.logError(file, data.lineNumber[i], "Could not find Part image "+val[1]+".png in assets/parts!");
				}
			} else {
				// Only give error message when the string isn't empty:
				if(val.length > 1 || val[0].length() > 0) {
					Logger.logWarning(file, data.lineNumber[i], "Unknown argument for Part \""+val[0]+"\" with value \""+val[1]+"\". Skipping line!");
				}
			}
		}
		Node[] textNodes = data.nextNodes;
		for(Node n : textNodes) {
			text.append(n.toString());
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
