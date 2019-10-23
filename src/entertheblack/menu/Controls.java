package entertheblack.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;

public class Controls extends Screen {
	static int[] buttons = {190, 340, 490, 640, 790, 940};
	String[] btn2 = {"Player 1: Turn left", "Player 1: Turn rigth", "Player 1: shoot", "Player 1: shoot(2)", "Player 1: forward", "Back to menu", "Player 2: Turn left", "Player 2: Turn rigth", "Player 2: shoot", "Player 2: shoot(2)", "Player 2: forward"};
	
	
	int buttonsel = 1;
	int controlchange = 0;

	@Override
	public void keyPressed(KeyEvent e) {
		if (this.controlchange > 0) {
			Assets.Controls[this.controlchange - 1] = e.getKeyCode();
			this.buttonsel *= -1;
			this.controlchange = 0;
		}
		if (e.getKeyCode() == 38 && buttonsel > 0) {
			buttonsel--;
		}
		
		if (e.getKeyCode() == 40 && 
			buttonsel < 11) {
			buttonsel++;
		}
		
		if (e.getKeyCode() == 17 && buttonsel > 0) {
			buttonsel *= -1;
		}
		
		if (e.getKeyCode() == 39 && buttonsel <= 5) {
			buttonsel += 6;
		}
		
		if (e.getKeyCode() == 37 && buttonsel >= 7) {
			buttonsel -= 6;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 17) {
			if (buttonsel == -1) {
				controlchange = 1;
			}
			if (buttonsel == -2) {
				controlchange = 2;
			}
			if (buttonsel == -3) {
				controlchange = 3;
			}
			if (buttonsel == -4) {
				controlchange = 4;
			}
			if (buttonsel == -5) {
				controlchange = 5;
			}
			if (buttonsel == -6) {
				Assets.screen = new Options();
			}
			if (buttonsel == -7) {
				controlchange = 6;
			}
			if (buttonsel == -8) {
				controlchange = 7;
			}
			if (buttonsel == -9) {
				controlchange = 8;
			}
			if (buttonsel == -10) {
				controlchange = 9;
			}
			if (buttonsel == -11) {
				controlchange = 10;
			}
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(Assets.bg, 0, 0, 1920, 1080, null);
		g.setFont(new Font("Sansserif", 0, 20));
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); byte b;
		for (int i = 0; i < 6; i++) {
			g.setColor(Color.BLACK);
			g.drawImage(Assets.btn, 690, buttons[i], 200, 50, null);
			if (i + 1 == buttonsel * -1 || i + 1 == buttonsel) {
				g.drawImage(Assets.btnsl, 690, buttons[i], 200, 50, null);
			}
			g.fillRect(695, (buttons[i] + 5), 190, 40);
			if (i + 1 == buttonsel * -1) {
				g.drawImage(Assets.btnpr, 695, (buttons[i] + 5), 190, 40, null);
			}
			g.setColor(Color.WHITE);
			g.drawString(btn2[i], 700, (buttons[i] + 25));
		}
		for (int i = 0; i < 5; i++) {
			g.setColor(Color.BLACK);
			g.drawImage(Assets.btn, 990, buttons[i], 200, 50, null);
			if (i + 7 == buttonsel * -1 || i + 7 == buttonsel) {
				g.drawImage(Assets.btnsl, 990, buttons[i], 200, 50, null);
			}
			g.fillRect(995, (buttons[i] + 5), 190, 40);
			if (i + 7 == buttonsel * -1) {
				g.drawImage(Assets.btnpr, 995, (buttons[i] + 5), 190, 40, null);
			}
			g.setColor(Color.WHITE);
			g.drawString(btn2[i + 6], 1000, (buttons[i] + 25));
		}
	}

}
