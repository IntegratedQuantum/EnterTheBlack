package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;

public class SGame extends Screen {
	public SGame(int save) {
		if(save >= 0) {
			loadWorld();
		} else {
			generateWorld();
		}
	}

	public SGame() {
		// Use the old world.
	}

	private void generateWorld() {
		Assets.curWorld = new World(1);
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
		Assets.screen = Assets.curWorld.getStar(0);
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {} // Not needed.

}
