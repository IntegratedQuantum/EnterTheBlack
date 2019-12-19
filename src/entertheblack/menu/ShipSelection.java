package entertheblack.menu;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.Util.Graphics;
import entertheblack.gui.Screen;
import entertheblack.storage.ShipData;
import entertheblack.storage.WeaponData;

// Select a ship for multiplayer fight.

public class ShipSelection extends Screen {
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
		if(e.getKeyCode() == Assets.Controls[0] && ship1 < Assets.shipData.size()) {
			ship1++;
		}
		if(e.getKeyCode() == Assets.Controls[1] && ship1 > 0) {
			ship1--;
		}
		if(e.getKeyCode() == Assets.Controls[5] && ship2 < Assets.shipData.size()) {
			ship2++;
		}
		if(e.getKeyCode() == Assets.Controls[6] && ship2 > 0) {
			ship2--;
		}
	}
	
	private void drawWeaponStats(Graphics2D g, WeaponData wd, int x, int y) {
		if(wd == null)
			return;
		g.drawString(wd.name+":", x, y-25);
		g.drawString("Damage:", x, y+50);
		g.drawString(""+wd.dmg, x+200, y+50);
		g.drawString("Range:", x, y+75);
		g.drawString(""+wd.range, x+200, y+75);
		g.drawString("Energy Cost:", x, y+100);
		g.drawString(""+wd.cost, x+200, y+100);
		g.drawString("Reload:", x, y+125);
		g.drawString(""+wd.reload, x+200, y+125);
		g.drawString("Velocity:", x, y+150);
		g.drawString(""+wd.velocity, x+200, y+150);
	}
	
	// x coordinate is hard coded.
	private void drawShip(Graphics2D g, ShipData sd, int y) {
		g.drawImage(sd.img, 760, y, 200, 200, null);
		g.drawString(sd.name, 820, y-25);
		Graphics.drawDynamicTextBox(g, sd.description, 20, 400, (int)(y + 50), 360);
		int x = 1000;
		g.drawString("Hull:", x, y+50);
		g.drawString(""+sd.health, x+200, y+50);
		g.drawString("Energy Storage:", x, y+75);
		g.drawString(""+sd.energy, x+200, y+75);
		g.drawString("Energy Generation:", x, y+100);
		g.drawString(""+sd.energyGeneration, x+200, y+100);
		g.drawString("Mass:", x, y+125);
		g.drawString(""+sd.mass, x+200, y+125);
		g.drawString("Maximum Velocity:", x, y+150);
		g.drawString(""+sd.vmax, x+200, y+150);
		g.drawString("Turn Rate:", x, y+175);
		g.drawString(""+sd.turnRate, x+200, y+175);
		g.drawString("Acceleration:", x, y+200);
		g.drawString(""+sd.acceleration, x+200, y+200);
		drawWeaponStats(g, sd.wd1, x+300, y+50);
		drawWeaponStats(g, sd.wd2, x+600, y+50);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Assets.light);
		g.setFont(new Font("Sansserif", 0, 15));
		drawShip(g, Assets.shipData.get(ship1), 150);
		drawShip(g, Assets.shipData.get(ship2), 650);
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// TODO: Clickable arrows to select previous/next ship.
	}

}
