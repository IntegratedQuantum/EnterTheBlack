package entertheblack.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;

import entertheblack.Util.Logger;
import entertheblack.game.SGame;
import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;

public class LoadOrNew extends Screen implements ActionListener {
	ButtonHandler buttons = new ButtonHandler();
	String[] files;
	public LoadOrNew() {
		buttons.add(new Button(690, 190, 500, 50, this, -1, "New Game"), 0);
		// Find all save files and add a button for each of them.:
		String fileName = Assets.takeCareOfWindows("assets/saves/");
		//Attempt to find save files.
		try {
			File[] f = (new File(fileName)).listFiles();
			int curY = 340;
			files = new String[f.length];
			for (int i = 0; i < f.length; i++) {
				File file = f[i];
				if (file != null) {
					String name = file.getName();
					files[i] = name;
					buttons.add(new Button(690, curY, 500, 50, this, i, "Load World "+name), 0);
					curY += 75;
				}
			}
		//Prevent game crashing if there are no saves yet.
		} catch (NullPointerException e) {
			Logger.logWarning("No Saves found!");
		}
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		buttons.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		buttons.keyReleased(e);
	}
	
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		buttons.mouseUpdate(x, y, pressed);
	}

	@Override
	public void paint(Graphics2D g) {
		buttons.paint(g);
	}

	@Override
	public void pressed(int id) {
		if(id == -1) {
			Assets.screen = new SGame(-1);
		}
		else {
			Assets.screen = new SGame(Assets.readFile("saves/"+files[id]), "assets/saves/"+files[id]);
		}
	}
}
