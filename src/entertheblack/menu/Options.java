package entertheblack.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;

public class Options extends Screen {
	static int[] buttons = {190, 340, 490, 640, 790, 940};
	String[] btn = {"Controls", /*"Graphic",*/ "Back to Menu", "Exit"};
	
	int buttonsel = 1;

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 38 && buttonsel > 0) {
			buttonsel--;
		}
		
		if (e.getKeyCode() == 40 && buttonsel < btn.length) {
			buttonsel++;
		}
		
		if (e.getKeyCode() == 17 && buttonsel > 0) {
			buttonsel *= -1;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 17) {
			if (this.buttonsel == -1) {
				Assets.screen = new Controls();
			}
			if (this.buttonsel == -2) {
				Assets.screen = new MainMenu();
			}
			if (this.buttonsel == -3) {
				System.exit(1);
			}
			this.buttonsel = 1;
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
