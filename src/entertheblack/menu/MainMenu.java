package entertheblack.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;
import game.SGame;

public class MainMenu extends Screen {
	static int[] buttons = {190, 340, 490, 640, 790, 940};
	static String[] btn = {"Singleplayer", "Change ship", "Multiplayer", "Options", "DO NOT PRESS!", "Quit"};
	int buttonsel = 1;
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (Assets.gamemode == 0) {
			if (e.getKeyCode() == 38 && buttonsel != 1) {
				this.buttonsel--;
			}
			
			if (e.getKeyCode() == 40 && buttonsel < btn.length) {
				this.buttonsel++;
			}
			
			if (e.getKeyCode() == 17 && buttonsel > 0) {
				this.buttonsel *= -1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 17) {
			if (buttonsel == -1) {
				Assets.screen = new SGame(-1);
			}
			if (buttonsel == -2) {
				Assets.screen = new ShipSelection();
			}
			if (buttonsel == -3) {
				Assets.screen = Assets.game;
				Assets.game.reset();
			}
			if (buttonsel == -4) {
				Assets.screen = new Options();
			}
			if (buttonsel == -5) {
				// Credits?
			}
			if (buttonsel == -6) {
				System.exit(1);
			}
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(Assets.bg, 0, 0, 1920, 1080, null);
		g.setFont(new Font("Sansserif", 0, 20));
		for (int i = 0; i < btn.length; i++) {
			g.setColor(Color.BLACK);
			g.drawImage(Assets.btn, 690, buttons[i], 500, 50, null);
			if (i + 1 == buttonsel * -1 || i + 1 == buttonsel) {
				g.drawImage(Assets.btnsl, 690, buttons[i], 500, 50, null);
			}
			g.fillRect(695, (buttons[i] + 5), 490, 40);
			if (i + 1 == buttonsel * -1) {
				g.drawImage(Assets.btnpr, 695, (buttons[i] + 5), 490, 40, null);
			}
			g.setColor(Color.WHITE);
			g.drawString(btn[i], 700, (buttons[i] + 25));
		}
	}
}
