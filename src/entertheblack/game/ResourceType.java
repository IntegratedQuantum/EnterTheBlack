package entertheblack.game;

import java.awt.Image;
import java.util.ArrayList;

import entertheblack.menu.Assets;

public class ResourceType {
	public String name;
	public String[] subTypes;
	int value;
	Image img;
	public ResourceType(String n, int v) {
		name = n;
		value = v;
		img = Assets.getImage("resources/"+name);
		if(img == null) {
			img = Assets.getPlanetImg("");
			System.err.println("Couldn't find Resource image "+name+"!");
		}
	}
	
	public ResourceType(String data) {
		String[] entries = data.split("\n");
		ArrayList<String> subNames = new ArrayList<>();
		for(int i = 0; i < entries.length; i++) {
			String[] val = entries[i].split("=");
			if(val.length < 2) {
				val = entries[i].split(":");
				if(val.length < 2)
					continue;
				if(val[0].equals("Names")) {
					val = val[1].split(",");
					for(int j = 0; j < val.length; j++) {
						subNames.add(val[j]);
					}
				}
				continue;
			}
			if(val[0].equals("Value"))
				value = Integer.parseInt(val[1]);
			else if(val[0].equals("Name"))
				name = val[1];
			else if(val[0].equals("Image")) {
				img = Assets.getImage("resources/"+val[1]+".png");
				if(img == null) {
					img = Assets.getPlanetImg("");
					System.err.println("Couldn't find Resource image "+val[1]+"!");
				}
			}
		}
		subTypes = subNames.toArray(new String[0]);
		// Add spaces to name again to revert to the status most likely seen before trimming.
		name = Assets.readdSpaces(name);
		// If no image specified take the image that corresponds to the name.
		if(img == null) {
			img = Assets.getImage("resources/"+name+".png");
			if(img == null) {
				img = Assets.getPlanetImg("");
				System.err.println("Couldn't find Resource image "+name+"!");
			}
		}
		System.out.println("Loaded Resource "+name+".");
	}
}
