package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entertheblack.Util.Graphics;
import entertheblack.Util.Logger;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// Display a series of animated images and text. Used for intro and others.

public class Animation extends Screen {
	// One frame containing an image a timer and a list of texts with coordinates.
	private class Scene {
		int time; // in ms
		Image image;
		List<String> texts = new ArrayList<>();
		List<int[]> textsPos = new ArrayList<>();
		List<Color> textsColor = new ArrayList<>();
		public Scene(Node data, String file) {
			String[] entries = data.lines;
			for(int i = 0; i < entries.length; i++) {
				String[] val = entries[i].split("=");
				if(val.length < 2) {
					continue;
				}
				if(val[0].equals("Time"))
					time = Integer.parseInt(val[1]);
				else if(val[0].equals("Image"))
					image = Assets.getImage(val[1]);
				else if(val[0].equals("Text")) {
					// Expects "entry,x,y,size,color(hex)".
					String[] values = val[1].split(",");
					if(values.length < 4)
						continue;
					texts.add(values[0]);
					textsPos.add(new int[]{Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3])});
					if(values.length < 5) {
						textsColor.add(Color.WHITE); // since this is a space game the background will usually be black, so text color should be white unless specified otherwise.
						continue;
					}
					int r = new Scanner(""+values[4].charAt(0)+values[4].charAt(1)).nextInt(16);
					int g = new Scanner(""+values[4].charAt(2)+values[4].charAt(3)).nextInt(16);
					int b = new Scanner(""+values[4].charAt(4)+values[4].charAt(5)).nextInt(16);
					textsColor.add(new Color(r, g, b));
				} else {
					Logger.logWarning(file, data.lineNumber[i], "Unknown argument for type Animation \"" + val[0] + "\" with value \"" + val[1] + "\". Skipping line!");
				}
			}
		}
		
		public boolean display(Graphics2D g, long startTime) {
			g.drawImage(image, 0, 0, 1920, 1080, null);
			for(int i = 0; i < texts.size(); i++) {
				g.setColor(textsColor.get(i));
				Graphics.drawStringCentered(g, texts.get(i), textsPos.get(i)[2], textsPos.get(i)[0], textsPos.get(i)[1]);
			}
			return (System.currentTimeMillis() - startTime) > time; // Returns true if next frame should be displayed.
		}
	}
	Screen following; // Whatever screen should come next.
	public String name;
	List<Scene> scenes = new ArrayList<>();
	// Load animation from file:
	public Animation(Node node, String file) {
		String[] entries = node.lines;
		for(int i = 0; i < entries.length; i++) {
			String[] val = entries[i].split("=");
			if(val.length < 2) {
				continue;
			}
			if(val[0].equals("Name"))
				name = val[1];
		}
		Logger.log("Loading Animation \""+name+"\".");
		Node[] scene = node.nextNodes;
		for(Node n : scene) {
			scenes.add(new Scene(n, file));
		}
	}
	long t;
	int curIndex;
	public void activate(Screen f) {
		Assets.screen = this;
		following = f;
		t = System.currentTimeMillis();
		curIndex = 0;
	}
	// returns true if completed.
	public void paint(Graphics2D g) {
		if(scenes.get(curIndex).display(g, t)) {
			t += scenes.get(curIndex).time;
			curIndex++;
			if(curIndex == scenes.size())
				Assets.screen = following;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {} // Not needed.
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			// Skip animation when escape is pressed:
			Assets.screen = following;
		}
	}
}
