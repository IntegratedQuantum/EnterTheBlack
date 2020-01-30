package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

import entertheblack.Util.Logger;
import entertheblack.Util.Noise;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// Cost type of resource. Contains multiple sub-groups of names,

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
			Logger.logError("resource loading", "Couldn't find Resource image "+name+"!");
		}
	}
	
	public ResourceType(Node data, String file) {
		String[] entries = data.lines;
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
			else if(val[0].equals("Color")) {
				img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = (Graphics2D)img.getGraphics();
				// Get main color:
				int r = new Scanner(""+val[1].charAt(0)+val[1].charAt(1)).nextInt(16);
				int g = new Scanner(""+val[1].charAt(2)+val[1].charAt(3)).nextInt(16);
				int b = new Scanner(""+val[1].charAt(4)+val[1].charAt(5)).nextInt(16);
				for(int j = 0; j < 16; j++) {
					double alpha = Noise.sCurve(j/15.0);
					g2d.setColor(new Color(r, g, b, (int)(alpha*255)));
					g2d.fillOval(j, j, 32-2*j, 32-2*j);
				}
				g2d.dispose();
			}
			else {
				// Only give error message when the string isn't empty:
				if(val.length > 1 || val[0].length() > 0) {
					String message;
					if(val.length >= 2)
						message = "Unknown argument for ResourceType \""+val[0]+"\" with value \""+val[1]+"\". Skipping line!";
					else
						message = "Unknown argument for resourceType \""+val[0]+"\" without value. Skipping line!";
					Logger.logWarning(file, data.lineNumber[i], message);
				}
			}
		}
		subTypes = subNames.toArray(new String[0]);
		// If no image specified take the image that corresponds to the name.
		if(img == null) {
			img = Assets.getImage("resources/"+name+".png");
			if(img == null) {
				img = Assets.getPlanetImg("");
				Logger.logError("Couldn't find Resource image "+name+"!");
			}
		}
		Logger.log("Loaded Resource "+name+".");
	}
}
