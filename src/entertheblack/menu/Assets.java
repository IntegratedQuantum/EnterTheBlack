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
import java.util.Set;

import javax.imageio.ImageIO;

import entertheblack.fight.Game;
import entertheblack.gui.Screen;

public class Assets {
	private static final int SHIP = 0, WEAPON = 1;
	private static List<int []> shipstatsint = new ArrayList<>();
	public static final int PRIMARY = 0, SECONDARY = 1; // for ship stats
	public static final int HEALTH = 2;
	public static final int ENERGY = 3;
	public static final int MASS = 4;
	public static final int SIZE = 5;
	public static final int ACC = 6;
	public static final int VMAX = 7;
	public static final int TURN = 8;
	public static final int GEN = 9;
	public static final int SLOTS = 10;
	public static final int DAMAGE = 0, RANGE = 1, COST = 2, RELOAD = 3, VELOCITY = 4, TRACKING = 5; // for weapon stats
	public static Screen screen = new MainMenu();
	public static Game game = new Game();
	private static HashMap<String, Integer> shipArgs = new HashMap<>(), weaponArgs = new HashMap<>();
	private static HashMap<String, Image> stars = new HashMap<>(), planets = new HashMap<>();
	static {
		shipArgs.put("SlotSecn", -2);
		shipArgs.put("SlotPrim", -1);
		shipArgs.put("Primary", 0);
		shipArgs.put("Secondary", 1);
		shipArgs.put("Health", 2);
		shipArgs.put("Energy", 3);
		shipArgs.put("Mass", 4);
		shipArgs.put("Size", 5);
		shipArgs.put("Acceleration", 6);
		shipArgs.put("Vmax", 7);
		shipArgs.put("Turn", 8);
		shipArgs.put("Generation", 9);

		weaponArgs.put("Damage", 0);
		weaponArgs.put("Range", 1);
		weaponArgs.put("Cost", 2);
		weaponArgs.put("Reload", 3);
		weaponArgs.put("Velocity", 4);
		weaponArgs.put("Tracking", 5);
		weaponArgs.put("Size", 6);
	}
	// {primary weapon, secondary, health, energy, ?, size, acceleration, numOfPrSlot, numOfSecSlot}
	/*static int[][] shipstatsint = {
		{0, 1, 120, 1000, 10, 100, 100, 2, 1, -25, -5, 25, -5, 0, 50 },
		{2, 3, 150, 5000, 20, 100, 100, 2, 1, -25, -5, 25, -5, 0, 50 },
		{4, 5, 50, 2000, 40, 100, 100, 2, 1, -25, -5, 25, -5, 0, 50 },
		{6, 0, 50, 4000, 50, 100, 100, 2, 1, -25, -5, 25, -5, 0, 50 },
		{7, -1, 60, 2000, 20, 50, 50, 2, 0, -10, -3, 10, -3 },
		{8, -1, 40, 2500, 25, 50, 50, 2, 0, -10, -3, 10, -3 }};*/
		
	static String[][] shipstatsstring = { 
		{"Stats:", "    Hull:", "    Energy Storage:", "    Energy Regeneration:", "    Size:", "    First weapon:", "    Second Weapon:", "        Damage:", "        range:", "        Energy Cost:", "        Reload:", "        Speed:" },
		{"star", "This is the first intergalactic spaceship build from", "humans. It has the best technology known at this", "time. It's not the best ship, but it points with its", "strong hull and its strong missiles.", "", "", "", "" },
		{"Blue Starlight", "This is the first figther from the Starlight", "collection. It has a fast shooting weapon with", "medium damage, but high energy cost. It has big", "Energy storages under the strong hull. Because", "of its low range its often used in the front of big fleets.", "", "", "" },
		{"Red Starlight", "This is the second figther from the Starlight", "collection. It has a slow shooting weapon with high", "damage and medium energy cost. It has strong energy", "reactors, but just a small tank and a weak hull.", "Because of its high range it is used in big fleets in the", "back protected from Blue Starlights.", "", "" },
		{"Yellow Starlight", "This is the last ship in the Starlight collection.", "It has a weapon with low damage on a high area, ", "which is useful against enemies with a big ship.", "It has the best reactors and big energy tanks, but", "a weak hull. It has a medium range and is not useful", "against ships with a higher range.", "", ""},
		{"Starfigther", "This is a small ship from the Starfighter collection.", "It has a medium shooting weapon with medium damage", "and low Energy cost ,which makes it useful for the", "small Starfighter, that has small energy tanks and", "a slow energy production. Without this weapon the", "Starfighter would be the worst ship. It is useful against", "ships with area weapons.", "" },
		{"White Starfighter", "This is a ship like the starfighter, but this ship has", "a weaker hull and better energy tanks and reactors.", "Its weapon is also improved. The laser has a lower", "cooldown, but a little higher energy cost.", "The weapon also has a higher range. This ship is weak", "against ships with high damage weapons or slow fiering", "weapons.", ""}};
	
	// {damage, lifeTime, energy, cooldown, velocity, tracking, TODO: size}:
	private static List<int[]> weaponint = new ArrayList<>();
	/*static int[][] weaponint = {
		{1, 1000, 50, 66, 20, 0},
		{7, 2000, 400, 500, 25, 4},
		{2, 750, 250, 30, 25, 0},
		{44, 750, 5000, 2000, 25, 0},
		{5, 1500, 250, 150, 15, 0},
		{0, 0, 0, 0, 15, 0},
		{20, 1000, 750, 400, 20, 0},
		{3, 1000, 100, 75, 30, 0},
		{3, 1100, 125, 60, 30, 0},
	};*/
	
	public static String[] weaponname = { "Red Laser", "Missile", "Blue Laser", "More Blue Laser", "Big Red Laser", "Fireball", "More red Laser", "Red Laser", "Green Laser", "Advanced Green Laser" };
	
	public static int[] Controls = { 37, 39, 17, 16, 38, 65, 68, 70, 71, 87 };
	
	public static int gamemode = 0;
	
	//static BufferedImage ships = new BufferedImage(100, 50, 2);
	public static BufferedImage bg, btn, btnpr, btnsl, hb;

	private static List<BufferedImage> projectiles = new ArrayList<>();

	public static List<BufferedImage> ships = new ArrayList<>();

	public static BufferedImage getProjectile(int type) {
		return projectiles.get(type);
	}

	// Get all ship/weapon stats:
	public static int getShipStat(int type, int stat) {
		return shipstatsint.get(type)[stat];
	}
	public static int getWeaponStat(int type, int stat) {
		return weaponint.get(type)[stat];
	}

	static String readFile(String fileName) {
		try {
			return new Scanner(new File("assets/"+fileName)).useDelimiter("\\Z").next();
		} catch(Exception e) {e.printStackTrace();}
		return "";
	}

	static BufferedImage getImage(String fileName) {
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

	static void fetchShipStat(String line, int[] ret) {
		String [] parts = line.split("=");
		Integer res = (Integer)shipArgs.get(parts[0].trim());
		if(res == null) {
			System.err.println("Unknown argument \"" + parts[0].trim() + "\". Skipping line!");
			return;
		}
		if(res < 0) { // Slots
			parts = parts[1].split(",");
			if(res == -1) {
				int freeSlot = SLOTS+2 + 2*ret[SLOTS] + 2*ret[SLOTS+1];
				ret[SLOTS]++;
				ret[freeSlot] = Integer.parseInt(parts[0].trim());
				ret[freeSlot+1] = Integer.parseInt(parts[1].trim());
			}
			else if(res == -2) {
				int freeSlot = SLOTS+2 + 2*ret[SLOTS] + 2*ret[SLOTS+1];
				ret[SLOTS+1]++;
				ret[freeSlot] = Integer.parseInt(parts[0].trim());
				ret[freeSlot+1] = Integer.parseInt(parts[1].trim());
			}
		}
		else { // Base stats
			ret[res] = Integer.parseInt(parts[1].trim());
		}
	}

	static void fetchWeaponStat(String line, int[] ret) {
		String [] parts = line.split("=");
		Integer res = (Integer)weaponArgs.get(parts[0].trim());
		if(res == null) {
			System.err.println("Unknown argument \"" + parts[0].trim() + "\". Skipping line!");
			return;
		}
		ret[res] = Integer.parseInt(parts[1].trim());
	}

	static int[] process(String file, int type) {
		String[] parts = file.split("\n");
		int len = parts.length;
		if(type == SHIP) {
			len = SLOTS+2 + (len - SLOTS)*2; // Information on the weapon slots is delivered differently.
		}
		int[] ret = new int[len];
		for(int i = 0; i < parts.length; i++) {
			switch(type) {
				case SHIP:
				fetchShipStat(parts[i], ret);
				break;
				case WEAPON:
				fetchWeaponStat(parts[i], ret);
				break;
			}
		}
		return ret;
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
		int nShip = 0;
		int nWeapon = 0;
		for(int i = 0; i < species.length; i++) {
			String [] generalData = readFile(species[i]+"/data").split(";");
			int numShips = parseInt(generalData[0]);
			int numWeapons = parseInt(generalData[1]);
			System.out.println("Registered species: "+species[i]+" which has "+numShips+" ships and "+numWeapons+" weapons.");
			for(int j = 0; j < numShips; j++) {
				ships.add(getImage(species[i]+"/"+j+".png"));
				int[] shipData = process(readFile(species[i]+"/data"+j), SHIP);
				shipstatsint.add(shipData);
			}
			for(int j = 0; j < numWeapons; j++) {
				projectiles.add(getImage(species[i]+"/w"+j+".png"));
				int[] weaponData = process(readFile(species[i]+"/dataw"+j), WEAPON);
				weaponint.add(weaponData);
			}
			nShip += numShips;
			nWeapon += numWeapons;
		}
		System.out.println("Loaded a total of "+nShip+" ships and "+nWeapon+" weapons.");
		game.reset(0,  0);
	}
}