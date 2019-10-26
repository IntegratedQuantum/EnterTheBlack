package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;

public class System extends Screen {
	boolean move = false;
	boolean turnleft = false;
	boolean turnright = false; // No shooting in systems allowed!
	World world;
	Star reference;
	public System(World w, int sys) {
		world = w;
		reference = world.getStar(sys);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Assets.Controls[1]) {
			turnright = true;
		}
		if (e.getKeyCode() == Assets.Controls[0]) {
			turnleft = true;
		}
		if (e.getKeyCode() == Assets.Controls[4]) {
			move = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Assets.Controls[1]) {
			turnright = false;
		}
		if (e.getKeyCode() == Assets.Controls[0]) {
			turnleft = false;
		}
		if (e.getKeyCode() == Assets.Controls[4]) {
			move = false;
		}
	}
	

	
	@Override
	public void update() {
		if(reference.update(move, turnright, turnleft)) {
			Assets.screen = new MainMenu(); // TODO go to hyperspace.
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.translate(960, 540);
		reference.paint(g);
	}

}
