package entertheblack.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.gui.Screen;

// Select a ship for multiplayer fight.

public class ShipSelection extends Screen {
	static Color light = new Color(200, 200, 200);
	int ship1 = 0, ship2 = 0;
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Assets.screen = new MainMenu();
			Assets.game.reset(ship1, ship2);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == Assets.Controls[0] && ship1 < 5) {
			ship1++;
		}
		if(e.getKeyCode() == Assets.Controls[1] && ship1 > 0) {
			ship1--;
		}
		if(e.getKeyCode() == Assets.Controls[5] && ship2 < 5) {
			ship2++;
		}
		if(e.getKeyCode() == Assets.Controls[6] && ship2 > 0) {
			ship2--;
		}
	}

	@Override
	public void paint(Graphics2D g) { // TODO!!!!!
		g.setColor(light);
		g.setFont(new Font("Sansserif", 0, 15));
		g.drawImage(Assets.shipData.get(ship1).img, 760, 150, 200, 200, null);
		g.drawString(Assets.shipstatsstring[ship1 + 1][0], 820, 125);
		for (int i = 0; i < 8; i++) {
			g.drawString(Assets.shipstatsstring[ship1 + 1][i + 1], 400, (int)((200 + i * 25)));
		}
		for (int i = 0; i < 5; i++) {
			g.drawString(Assets.shipstatsstring[0][i], 1000, (int)((200 + i * 25)));
		}
		g.drawString(Assets.shipstatsstring[0][5], 1300, 175);
		for (int i = 7; i < 12; i++) {
			g.drawString(Assets.shipstatsstring[0][i], 1300, (int)((200 + (i - 5) * 25)));
		}
		if (ship1 != 4 && ship1 != 5) {
			g.drawString(Assets.shipstatsstring[0][6], 1600, 175);
			for (int i = 7; i < 12; i++) {
				g.drawString(Assets.shipstatsstring[0][i], 1600, (int)((200 + (i - 5) * 25)));
			}
			for (int i = 0; i < 5; i++) {
				//TODO: g.drawString("" + Assets.getWeaponStat(Assets.getShipStat(ship1, Assets.SECONDARY), i), 1800, (int)((200 + i * 25)));
			}
			g.drawString(Assets.shipData.get(ship1).wd2.name, 1800, 175);
		} 
		for (int i = 1; i < 5; i++) {
			//g.drawString("" + Assets.getShipStat(ship1, i + 1), 1200, (int)((200 + i * 25)));
		}
		for (int i = 0; i < 5; i++) {
			//g.drawString("" + Assets.getWeaponStat(Assets.getShipStat(ship1, Assets.PRIMARY), i), 1500, (int)((200 + i * 25)));
		}
		g.drawString(Assets.shipData.get(ship1).wd1.name, 1500, 175);
		g.drawImage(Assets.shipData.get(ship2).img, 760, 650, 200, 200, null);
		g.drawString(Assets.shipstatsstring[ship2 + 1][0], 820, 625);
		for (int i = 0; i < 8; i++) {
			g.drawString(Assets.shipstatsstring[ship2 + 1][i + 1], 400, (int)((700 + i * 25)));
		}
		for (int i = 0; i < 5; i++) {
			g.drawString(Assets.shipstatsstring[0][i], 1000, (int)((700 + i * 25)));
		}
		g.drawString(Assets.shipstatsstring[0][5], 1300, 675);
		for (int i = 7; i < 12; i++) {
			g.drawString(Assets.shipstatsstring[0][i], 1300, (int)((700 + (i - 5) * 25)));
		}
		if (ship2 != 4 && ship2 != 5) {
			g.drawString(Assets.shipstatsstring[0][6], 1600, 675);
			for (int i = 7; i < 12; i++) {
				g.drawString(Assets.shipstatsstring[0][i], 1600, (int)((700 + (i - 5) * 25)));
			}
			for (int i = 0; i < 5; i++) {
				//TODO: g.drawString("" + Assets.getWeaponStat(Assets.getShipStat(ship2, Assets.SECONDARY), i), 1800, (int)((700 + i * 25)));
			}
			g.drawString(Assets.shipData.get(ship2).wd2.name, 1800, 675);
		} 
		for (int i = 0; i < 5; i++) {
			//g.drawString("" + Assets.getWeaponStat(Assets.getShipStat(ship2, Assets.PRIMARY), i), 1500, (int)((700 + i * 25)));
		}
		for (int i = 1; i < 5; i++) {
			//g.drawString("" + Assets.getShipStat(ship2, i + 1), 1200, (int)((700 + i * 25)));
		}
		g.drawString(Assets.shipData.get(ship2).wd1.name, 1500, 675);
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// TODO: Clickable arrows to select previous/next ship.
	}

}
