package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.storage.Node;

// Currently only used as redirect.
// TODO: Structure it better.

public class SGame extends Screen {
	Screen following = null;
	public SGame(int save) {
		if(save >= 0) {
			loadWorld();
		} else {
			generateWorld();
		}
	}

	public SGame(Node data, String file) {
		Assets.curWorld = new World(data.nextNodes[0], file);
		following = Assets.curWorld.getStart(data.nextNodes[1], file);
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
		if(following == null) // new World
			Assets.getAnimation("Intro").activate(Assets.curWorld.getStar(0));
		else // Loaded world
			Assets.screen = following;
	}

}
