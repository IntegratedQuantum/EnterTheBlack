package entertheblack.menu;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.imageio.ImageIO;

import entertheblack.fight.Game;
import entertheblack.fight.MPGame;
import entertheblack.game.Animation;
import entertheblack.game.Player;
import entertheblack.game.ResourceType;
import entertheblack.game.Star;
import entertheblack.game.World;
import entertheblack.gui.Screen;
import entertheblack.storage.ShipData;
import entertheblack.storage.Species;
import entertheblack.storage.WeaponData;

// Several static things used everywhere.

public class Assets {
	public static Screen screen = new MainMenu();
	public static Game game = new MPGame();
	private static HashMap<String, Image> stars = new HashMap<>(), planets = new HashMap<>();
	
	public static World curWorld;
	
	public static List<ShipData> shipData = new ArrayList<>();
	public static List<WeaponData> weaponData = new ArrayList<>();
	public static List<Animation> animations = new ArrayList<>();
	public static List<Species> species = new ArrayList<>();
	
	public static ResourceType[] resources;
	
	public static int[] Controls;
	
	public static int gamemode = 0;
	
	//static BufferedImage ships = new BufferedImage(100, 50, 2);
	public static Color btn, btnpr, btnbg, btnsl; // Button colors
	public static Color light = new Color(200, 200, 200); // text color.
	public static BufferedImage bg, hb;
	
	public static Animation getAnimation(String name) {
		for(Animation a : animations) {
			if(a.name.equals(name))
				return a;
		}
		return null;
	}
	
	// Some people seem to use windows which for unknown reasons still uses "\" as path separator.
	public static String takeCareOfWindows(String path) {
		String[] sep = path.split("/");
		StringBuilder ret = new StringBuilder();
		for(int i = 0; i < sep.length; i++) {
			if(i != 0) {
				ret.append(File.separator);
			}
			ret.append(sep[i]);
		}
		return ret.toString();
	}

	public static String readFile(String fileName) {
		fileName = takeCareOfWindows("assets/"+fileName);
		try {
			return new Scanner(new File(fileName)).useDelimiter("\\Z").next();
		} catch(Exception e) {e.printStackTrace();}
		return "";
	}
	
	public static void writeFile(String data, String fileName) {
		try {
			FileWriter f = new FileWriter(takeCareOfWindows("assets/"+fileName));
			f.write(data);
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getImage(String fileName) {
		fileName = takeCareOfWindows("assets/"+fileName);
		try {
			return ImageIO.read(new File(fileName));
		} catch(Exception e) {}//e.printStackTrace();}
		return null;
	}
	
	private static void loadStars() {
		String fileName = takeCareOfWindows("assets/stars/");
		File[] f = (new File(fileName)).listFiles();
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
		String fileName = takeCareOfWindows("assets/planets/");
		File[] f = (new File(fileName)).listFiles();
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
	
	// TODO: don't remove " " written inside "".
	static String[] divideAndTrim(String file) {
		List<String> ret = new ArrayList<>();
		char [] data = file.toCharArray();
		int depth = 0;
		boolean inPar = false;
		StringBuilder stb = new StringBuilder();
		for(int i = 0; i < data.length; i++) {
			if(data[i] == '\"') { // Ignore parenthesis and change mode.
				inPar = !inPar;
			}
			else if(inPar) { // Don't do any changes in the data inside parenthesis. Also ignore brackets.
				stb.append(data[i]);
			} else if(depth == 0) {
				if(data[i] == '{') {
					stb = new StringBuilder();
					depth = 1;
				}
			} else {
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
			}
		}
		if(inPar) {
			System.err.println("Found opening, but no closing parenthesis in file.");
		}
		
		return ret.toArray(new String[0]);
	}
	
	public static List<ShipData> createShipData(String data) {
		List<ShipData> list = new ArrayList<>();
		String[] ships = divideAndTrim(data);
		for(int i = 0; i < ships.length; i++) {
			list.add(new ShipData(ships[i]));
		}
		return list;
	}
	
	static void createWeaponData(String data) {
		String[] weapons = divideAndTrim(data);
		for(int i = 0; i < weapons.length; i++) {
			weaponData.add(new WeaponData(weapons[i]));
		}
	}
	
	static void loadResources() {
		String res = readFile("resources.txt");
		String[] data = divideAndTrim(res);
		resources = new ResourceType[data.length];
		for(int i = 0; i < data.length; i++) {
			resources[i] = new ResourceType(data[i]);
		}
	}
	
	static void loadSettings() {
		// Initialize standard settings in case the settings file is corrupted/incomplete:
		btn = new Color(111, 111, 111);
		btnsl = new Color(204, 198, 24);
		btnpr = new Color(169, 159, 0);
		btnbg = new Color(0, 0, 0);
		Controls = new int[]{ 37, 39, 17, 16, 38, 65, 68, 70, 71, 87 };
		// Read the data file and overwrite the standard settings.
		try {
			String file = readFile("settings.txt");
			String [] lines = file.split("\n");
			for(String line : lines) {
				String [] val = line.split("=");
				if(val.length <= 1)
					continue;
				if(val[0].equals("btn")) {
					String[] rgb = val[1].split(",");
					btn = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
				}
				else if(val[0].equals("btnpr")) {
					String[] rgb = val[1].split(",");
					btnpr = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
				}
				else if(val[0].equals("btnsl")) {
					String[] rgb = val[1].split(",");
					btnsl = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
				}
				else if(val[0].equals("btnbg")) {
					String[] rgb = val[1].split(",");
					btnbg = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
				}
				else if(val[0].equals("keys")) {
					String[] keys = val[1].split(",");
					if(keys.length == Controls.length) {
						for(int j = 0; j < Controls.length; j++) {
							Controls[j] = Integer.parseInt(keys[j]);
						}
					} else {
						System.err.println("Wrong number of arguments in settings.txt for keys: "+keys.length+" instead of "+Controls.length+".");
					}
				}
			}
		} catch(Exception e) { // Catch any error if occuring when reading the settings and revert to standard settings:
			System.err.println("File \"settings.txt\" corrupted:");
			e.printStackTrace();
			System.err.println("Reverting to standard settings.");
		}
	}
	
	private static void addColors(StringBuilder sb, Color c) {
		sb.append(c.getRed());
		sb.append(",");
		sb.append(c.getGreen());
		sb.append(",");
		sb.append(c.getBlue());
	}
	
	static void saveSettings() {
		// Generate the file:
		StringBuilder sb = new StringBuilder();
		// Add button colors:
		sb.append("btn=");
		addColors(sb, btn);
		sb.append("\n");
		sb.append("btnsl=");
		addColors(sb, btnsl);
		sb.append("\n");
		sb.append("btnpr=");
		addColors(sb, btnpr);
		sb.append("\n");
		sb.append("btnbg=");
		addColors(sb, btnbg);
		sb.append("\n");
		// Add key preferences:
		sb.append("keys=");
		for(int i = 0; i < Controls.length; i++) {
			if(i != 0)
				sb.append(",");
			sb.append(Controls[i]);
		}
		sb.append("\n");
		
		writeFile(sb.toString(), "settings.txt");
	}
	
	private static void loadAnimations() {
		String res = readFile("animations.txt");
		String[] data = divideAndTrim(res);
		for(int i = 0; i < data.length; i++) {
			animations.add(new Animation(data[i]));
		}
	}

	static void loadData() {
		System.out.println((int)'\t');
		bg = getImage("bg.png");
		// TODO: Load color from settings.txt
		hb = getImage("hb.png");
		loadSettings();
		loadStars();
		loadPlanets();
		loadResources();
		loadAnimations();
		String [] spec = readFile("species.txt").split("\n");
		for(int i = 0; i < spec.length; i++) {
			Species local = new Species(spec[i]);
			species.add(local);
			createWeaponData(readFile(spec[i]+"/weapons"));
			System.out.println("Registered species: "+spec[i]+".");
		}
		for(ShipData sd : shipData) {
			sd.assignWeaponData(weaponData);
		}
		game.reset(0,  0);
	}
	
	public static void saveGame(World curWorld, Star curSystem) {
		StringBuilder file = new StringBuilder();
		Player p = curWorld.player;
		file.append(curSystem.x+","+curSystem.y);
		file.append("{");
		p.save(file);
		file.append("}");
		file.append("{");
		curWorld.save(file);
		file.append("}");
		writeFile(file.toString(), "save.txt");
	}
}