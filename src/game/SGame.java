package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;

public class SGame extends Screen {
	World world;
	public SGame(int save) {
		if(save >= 0) {
			loadWorld();
		} else {
			generateWorld();
		}
	}

	private void generateWorld() {
		world = new World(1);
	}

	private void loadWorld() {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void paint(Graphics2D g) {
		// TODO: Add intro before entering game!
		Assets.screen = new System(world, 0);
		
	}

}
