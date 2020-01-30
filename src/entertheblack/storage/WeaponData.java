package entertheblack.storage;

import java.awt.Image;

import entertheblack.Util.Logger;
import entertheblack.menu.Assets;

// Contains data(damage velocity, ...) for a weaapon.

public class WeaponData {
	public double dmg;
	public double range;
	public double cost;
	public int reload;
	public double velocity;
	public double tracking;
	public Image img;
	public int size;
	public String name;
	public WeaponData(Node data, String file) { // Only accepts trimmed data.
		String[] lines = data.lines;
		for(int i = 0; i < lines.length; i++) {
			String [] parts = lines[i].split("=");
			if(parts.length < 2)
				continue;
			if(parts[0].equals("Damage")) {
				dmg = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Range")) {
				range = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Cost")) {
				cost = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Reload")) {
				reload = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Velocity")) {
				velocity = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Tracking")) {
				tracking = Double.parseDouble(parts[1]);
			} else if(parts[0].equals("Size")) {
				size = Integer.parseInt(parts[1]);
			} else if(parts[0].equals("Name")) {
				name = parts[1];
			} else if(parts[0].equals("Image")) {
				img = Assets.getImage("weapons/"+parts[1]+".png");
				if(img == null) {
					Logger.logError(file, data.lineNumber[i], "Could not find weapon image "+parts[1]+".png in assets/weapons!");
				}
			} else {
				Logger.logWarning(file, data.lineNumber[i], "Unknown argument for type Weapon \"" + parts[0] + "\" with value" + parts[1] + ". Skipping line!");
				return;
			}
		}
	}
}
