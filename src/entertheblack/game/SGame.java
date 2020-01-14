package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// Currently only used as redirect.
// TODO: Structure it better.

public class SGame extends Screen {
	public SGame(int save) {
		if(save >= 0) {
			loadWorld();
		} else {
			generateWorld();
		}
	}

	public SGame(Node data) {
		Assets.curWorld = new World(data);
		Assets.curWorld.player = new Player(Assets.variants.get(0));
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
		Assets.getAnimation("Intro").activate(Assets.curWorld.getStar(0));
	}

}
