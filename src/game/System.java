package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;

public class System extends Screen {
	World world;
	Star reference;
	public System(World w, int sys) {
		world = w;
		reference = world.getStar(sys);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void paint(Graphics2D g) {
		g.translate(960, 540);
		reference.paint(g);
	}

}
