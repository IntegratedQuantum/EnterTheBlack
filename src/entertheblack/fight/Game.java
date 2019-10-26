package entertheblack.fight;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Color;

import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;

public class Game extends Screen {
	Ship sh1, sh2;
	int bgx;
	int bgy;
	boolean move = false;
	boolean move2 = false;
	boolean turnleft = false;
	boolean turnright = false;
	boolean turnleft2 = false;
	boolean turnright2 = false;
	boolean shootingactive = false;
	boolean shootingactive2 = false;
	boolean rocketshoot = false;
	boolean rocketshoot2 = false;
	int xmiddle = 960;
	int ymiddle = 540;
	public int die = 0; // Which player won.
	
	public void keyPressed(KeyEvent e) {
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
		if (e.getKeyCode() == Assets.Controls[3]) {
			rocketshoot = true;
		}
		if (e.getKeyCode() == Assets.Controls[2]) {
			shootingactive = true;
		}
		if (e.getKeyCode() == Assets.Controls[1]) {
			turnright = true;
		}
		if (e.getKeyCode() == Assets.Controls[0]) {
			turnleft = true;
		}
		if (e.getKeyCode() == Assets.Controls[4]) {
			move = true;
		}
		if (e.getKeyCode() == 27) {
			Assets.screen = new MainMenu();
			die = 0;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Assets.Controls[2]) {
			shootingactive = false;
		}
		if (e.getKeyCode() == Assets.Controls[4]) {
			move = false;
		}
		if (e.getKeyCode() == Assets.Controls[7]) {
			shootingactive2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[8]) {
			rocketshoot2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[9]) {
			move2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[0]) {
			turnleft = false;
		}
		if (e.getKeyCode() == Assets.Controls[1]) {
			turnright = false;
		}
		if (e.getKeyCode() == Assets.Controls[5]) {
			turnleft2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[6]) {
			turnright2 = false;
		}
		if (e.getKeyCode() == Assets.Controls[3]) {
			rocketshoot = false;
		}
	}
	
	public void update() {
		if(die != 0)
			return;
		sh1.fly(sh2, move, turnright, turnleft);
		sh1.shoot(shootingactive, rocketshoot);
		sh2.fly(sh1, move2, turnright2, turnleft2);
		sh2.shoot(shootingactive2, rocketshoot2);
		
		if (sh1.health <= 0) {
			this.die = 2;
			return;
		}
		if (sh2.health <= 0) {
			this.die = 1;
			return;
		}
		if (this.bgx <= -1000) {
			this.bgx += 1000;
		}
		else if (this.bgx >= 1000) {
			this.bgx -= 1000;
		}
		if (this.bgy <= -1000) {
			this.bgy += 1000;
		}
		else if (this.bgy >= 1000) {
			this.bgy -= 1000;
		}
		
		// have the game always centered around one point.
		this.xmiddle = (int)(sh1.x + sh2.x) / 2;
		this.ymiddle = (int)(sh1.y + sh2.y) / 2;
		int px = 900 - this.xmiddle;
		int nx = this.xmiddle - 940;
		int py = 480 - this.ymiddle;
		int ny = this.ymiddle - 520;
		sh1.shift(px, nx, py, ny);
		sh2.shift(px, nx, py, ny);
		if(px > 0)
			bgx += px;
		else if(nx > 0)
			bgx -= nx;
		if(py > 0)
			bgy += py;
		else if(ny > 0)
			bgy -= ny;
		
		// Change positions if touching the border.
		if (sh1.x < 0) {
			sh1.x = sh2.x;
			sh2.x = 1;
		} 
		if (sh1.y < 0) {
			sh1.y = sh2.y;
			sh2.y = 1;
		} 
		if (sh2.x < 0) {
			sh2.x = sh1.x;
			sh1.x = 1;
		} 
		if (sh2.y < 0) {
			sh2.y = sh1.y;
			sh1.y = 1;
		} 
	}
	int xyz = 0;
	public void paint(Graphics2D g2d) {
		xyz++;
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 1920, 1080);
		int[][] arrayOfInt = { { -1000, -1000 }, { -1000, 0 }, { -1000, 1000 }, { 0, -1000 }, { 0, 1000 }, { 1000, -1000 }, { 1000, 0 }, { 1000, 1000 }, { 2000, -1000 }, { 2000, 0 }, { 2000, 1000 }, { 2000, 2000 }, { -1000, 2000 }, { 0, 2000 }, { 1000, 2000 }, { 0, 0 } };
		for (int i = 0; i < 16; i++) {
			g2d.drawImage(Assets.bg, xyz%1920+bgx + arrayOfInt[i][0], bgy + arrayOfInt[i][1], null);
		}
		sh1.paint(g2d);
		sh2.paint(g2d);
		g2d.drawImage(Assets.hb, 50, 50, 100, 390, null);
		g2d.drawImage(Assets.hb, 50, 550, 100, 390, null);
		g2d.setColor(Color.GRAY);
		g2d.fillRect(58, 58, 54, (int)((374 - sh1.health*370.0/Assets.getShipStat(sh1.type, Assets.HEALTH))));
		g2d.fillRect(58, 558, 54, (int)((374 - sh2.health*370.0/Assets.getShipStat(sh2.type, Assets.HEALTH))));
		g2d.fillRect(118, 58, 24, (int)((374 - sh1.energy*370.0/Assets.getShipStat(sh1.type, Assets.ENERGY))));
		g2d.fillRect(118, 558, 24, (int)((374 - sh2.energy*370.0/Assets.getShipStat(sh2.type, Assets.ENERGY))));
		g2d.setColor(Color.WHITE);
		g2d.drawString("Player 1", 50, 40);
		g2d.drawString("Player 2", 50, 540);
		g2d.drawString("Player 1", (int)(sh1.x), (int)((sh1.y - 10)));
		g2d.drawString("Player 2", (int)(sh2.x), (int)((sh2.y - 10)));
		if (die != 0) {
			g2d.drawString("Player " + die + " won the game!", 900, 500);
			g2d.drawString("Press escape to quit to title.", 900, 525);
		}
	}
	
	public void reset(int type1, int type2) {
		bgx = bgy = 0;
		sh1 = new Ship(type1, 1600, 490);
		sh2 = new Ship(type2, 400, 490);
	}
	
	public void reset() {
		reset(sh1.type, sh2.type);
	}
}
