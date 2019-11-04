package entertheblack.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entertheblack.Util.Noise;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;

public class Surface extends Screen {
	LandingScreen previous;
	Color [][] map;
	Planet planet;
	
	public Surface(LandingScreen prev, Planet p) {
		previous = prev;
		planet = p;
		double[][] dMap = Noise.generateNoiseMap(0, 800, 400);
		map = new Color[800][400];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				int d = (int)(dMap[i][j]*128)+128;
				if(d > 255) {
					System.out.println(d);
					d = 255;
				}
				if(d < 0) {
					System.out.println(d);
					d = 255;
				}
				map[i][j] = new Color(d, d, d);
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Assets.screen = new MainMenu();
		}
	}

	@Override
	public void paint(Graphics2D g) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				g.setColor(map[i][j]);
				g.fillRect(i, j+1080-map[i].length, 1, 1);
			}
		}
	}

}
