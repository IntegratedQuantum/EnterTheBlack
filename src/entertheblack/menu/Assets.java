package entertheblack.menu;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.imageio.ImageIO;

import entertheblack.fight.Game;
import entertheblack.game.World;
import entertheblack.gui.Screen;
import entertheblack.storage.ShipData;
import entertheblack.storage.WeaponData;

public class Assets {
	public static Screen screen = new MainMenu();
	public static Game game = new Game();
	private static HashMap<String, Image> stars = new HashMap<>(), planets = new HashMap<>();
	
	public static World curWorld;
		
	static String[][] shipstatsstring = { 
		{"Stats:", "    Hull:", "    Energy Storage:", "    Energy Regeneration:", "    Size:", "    First weapon:", "    Second Weapon:", "        Damage:", "        range:", "        Energy Cost:", "        Reload:", "        Speed:" },
		{"star", "This is the first intergalactic spaceship build from", "humans. It has the best technology known at this", "time. It's not the best ship, but it points with its", "strong hull and its strong missiles.", "", "", "", "" },
		{"Blue Starlight", "This is the first figther from the Starlight", "collection. It has a fast shooting weapon with", "medium damage, but high energy cost. It has big", "Energy storages under the strong hull. Because", "of its low range its often used in the front of big fleets.", "", "", "" },
		{"Red Starlight", "This is the second figther from the Starlight", "collection. It has a slow shooting weapon with high", "damage and medium energy cost. It has strong energy", "reactors, but just a small tank and a weak hull.", "Because of its high range it is used in big fleets in the", "back protected from Blue Starlights.", "", "" },
		{"Yellow Starlight", "This is the last ship in the Starlight collection.", "It has a weapon with low damage on a high area, ", "which is useful against enemies with a big ship.", "It has the best reactors and big energy tanks, but", "a weak hull. It has a medium range and is not useful", "against ships with a higher range.", "", ""},
		{"Starfigther", "This is a small ship from the Starfighter collection.", "It has a medium shooting weapon with medium damage", "and low Energy cost ,which makes it useful for the", "small Starfighter, that has small energy tanks and", "a slow energy production. Without this weapon the", "Starfighter would be the worst ship. It is useful against", "ships with area weapons.", "" },
		{"White Starfighter", "This is a ship like the starfighter, but this ship has", "a weaker hull and better energy tanks and reactors.", "Its weapon is also improved. The laser has a lower", "cooldown, but a little higher energy cost.", "The weapon also has a higher range. This ship is weak", "against ships with high damage weapons or slow fiering", "weapons.", ""}};
	
	public static List<ShipData> shipData = new ArrayList<>();
	public static List<WeaponData> weaponData = new ArrayList<>();
	
	public static String[] weaponname = { "Red Laser", "Missile", "Blue Laser", "More Blue Laser", "Big Red Laser", "Fireball", "More red Laser", "Red Laser", "Green Laser", "Advanced Green Laser" };
	
	public static int[] Controls = { 37, 39, 17, 16, 38, 65, 68, 70, 71, 87 };
	
	public static int gamemode = 0;
	
	//static BufferedImage ships = new BufferedImage(100, 50, 2);
	public static BufferedImage bg, btn, btnpr, btnsl, hb;

	public static String readFile(String fileName) {
		try {
			return new Scanner(new File("assets/"+fileName)).useDelimiter("\\Z").next();
		} catch(Exception e) {e.printStackTrace();}
		return "";
	}

	public static BufferedImage getImage(String fileName) {
		try {
			return ImageIO.read(new File("assets/"+fileName));
		} catch(Exception e) {e.printStackTrace();}
		return null;
	}
	
	private static void loadStars() {
		 File[] f = (new File("assets/stars/")).listFiles();
		for (File file : f) {
			if (file != null) {
				String name = file.getName();
				try {
					stars.put(name, ImageIO.read(file));
					System.out.println("Loaded image "+name+".");
				} catch (IOException e) {}
			}
		}
	}
	
	private static void loadPlanets() {
		 File[] f = (new File("assets/planets/")).listFiles();
		for (File file : f) {
			if (file != null) {
				String name = file.getName();
				try {
					planets.put(name, ImageIO.read(file));
					System.out.println("Loaded image "+name+".");
				} catch (IOException e) {}
			}
		}
	}
	
	public static Image getPlanetImg(String key) {
		Image ret = stars.get(key);
		if(ret == null)
			ret = planets.get(key);
		if(ret == null) {
			System.err.println("No Planet/Star Image named \""+key+"\" found!");
			return randPlanetImg(1);
		}
		return ret;
	}
	
	public static Image randPlanetImg(double eng) {
		Entry<String, Image> [] imgs = planets.entrySet().toArray(new Entry[0]);
		int selection = (int)(Math.random()*imgs.length);
		return imgs[selection].getValue();
	}
	
	public static Image randStarImg(double eng) {
		Entry<String, Image> [] imgs = stars.entrySet().toArray(new Entry[0]);
		int selection = (int)(Math.random()*imgs.length);
		return imgs[selection].getValue();
	}

	static int parseInt(String str) {
		return Integer.parseInt(str.trim());
	}
	
	static String[] divideAndTrim(String file) {
		List<String> ret = new ArrayList<>();
		char [] data = file.toCharArray();
		int depth = 0;
		StringBuilder stb = new StringBuilder();
		for(int i = 0; i < data.length; i++) {
			switch(depth) {
			case 0:
				if(data[i] == '{') {
					stb = new StringBuilder();
					depth = 1;
				}
				break;
			default:
				if(data[i] == '}') {
					depth--;
					if(depth == 0) {
						ret.add(stb.toString());
					}
					else
						stb.append(data[i]);
				} else if(data[i] != ' ' && data[i] != '	') {
					stb.append(data[i]);
					if(data[i] == '{')
						depth++;
				}
				break;
			}
		}
		
		return ret.toArray(new String[0]);
	}
	
	static void createShipData(String data) {
		String[] ships = divideAndTrim(data);
		for(int i = 0; i < ships.length; i++) {
			shipData.add(new ShipData(ships[i]));
		}
	}
	
	static void createWeaponData(String data) {
		String[] weapons = divideAndTrim(data);
		for(int i = 0; i < weapons.length; i++) {
			weaponData.add(new WeaponData(weapons[i]));
		}
	}

	static void loadData() {
		bg = getImage("bg.png");
		btn = getImage("btn.png");
		btnsl = getImage("btnsl.png");
		btnpr = getImage("btnpr.png");
		hb = getImage("hb.png");
		loadStars();
		loadPlanets();
		String [] species = readFile("species.txt").split("\n");
		for(int i = 0; i < species.length; i++) {
			createShipData(readFile(species[i]+"/ships"));
			createWeaponData(readFile(species[i]+"/weapons"));
			System.out.println("Registered species: "+species[i]+".");
		}
		for(ShipData sd : shipData) {
			sd.assignWeaponData(weaponData);
		}
		game.reset(0,  0);
	}
}