package entertheblack.game;

import java.awt.Image;

import entertheblack.menu.Assets;

public class ResourceType {
	String name;
	int value;
	Image img;
	public ResourceType(String n, int v) {
		name = n;
		value = v;
		img = Assets.getImage("resources/"+name);
		if(img == null) {
			System.err.println("Couldn't find Resource image "+name+"!");
		}
	}
}
