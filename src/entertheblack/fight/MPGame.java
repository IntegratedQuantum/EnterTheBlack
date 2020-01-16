package entertheblack.fight;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;

// Offline Multiplayer fighting area.

public class MPGame extends Game {
	
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (e.getKeyCode() == Assets.Controls[5]) {
			turnleft2 = true;
		}
		if (e.getKeyCode() == Assets.Controls[6]) {
			turnright2 = true;
		}
		if (e.getKeyCode() == Assets.Controls[7]) {
			shootingactive2 = true;
		}
		if (e.getKeyCode() == Assets.Controls[8]) {
			rocketshoot2 = true;
		}
		if (e.getKeyCode() == Assets.Controls[9]) {
			move2 = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		if (e.getKeyCode() == Assets.Controls[7]) {
			shootingactive2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[8]) {
			rocketshoot2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[9]) {
			move2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[5]) {
			turnleft2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[6]) {
			turnright2 = false;
		}
		if (e.getKeyCode() == 27) {
			Assets.screen = new MainMenu();
			die = 0;
		}
	}
	
	public void paint(Graphics2D g) {
		super.paint(g);
		g.setColor(Assets.text);
		g.drawString("Player 1", 50, 40);
		g.drawString("Player 2", 50, 540);
		g.drawString("Player 1", (int)(sh1.x), (int)((sh1.y - 10)));
		g.drawString("Player 2", (int)(sh2.x), (int)((sh2.y - 10)));
		if (die != 0) {
			g.drawString("Player " + die + " won the game!", 900, 500);
			g.drawString("Press escape to quit to title.", 900, 525);
		}
	}

	@Override
	void end(boolean win) {} // Not needed.
}
