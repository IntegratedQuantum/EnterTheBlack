package entertheblack.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import entertheblack.Util.Graphics;
import entertheblack.gui.ActionListener;
import entertheblack.gui.Screen;
import entertheblack.gui.components.Button;
import entertheblack.gui.components.ButtonHandler;

// SettingsScreen to change Controls.

public class GraphicsMenu extends Screen implements ActionListener {
	ButtonHandler buttons = new ButtonHandler();
	Button ok;
	boolean showColorCircle = false;
	int colorToChange = 0;
	public GraphicsMenu() {
		buttons.add(new Button(690, 190, 400, 65, this, 1, "Button Background Color"), 0);
		buttons.add(new Button(690, 290, 400, 65, this, 2, "Button Frame Color"), 0);
		buttons.add(new Button(690, 390, 400, 65, this, 3, "Button Selection Color"), 0);
		buttons.add(new Button(690, 490, 400, 65, this, 4, "Button Press Color"), 0);
		buttons.add(new Button(690, 590, 400, 65, this, 5, "Text Color"), 0);
		buttons.add(new Button(690, 690, 400, 65, this, 6, "Tooltip Background Color"), 0);
		buttons.add(new Button(690, 790, 400, 65, this, 7, "Tooltip Text Color"), 0);
		buttons.add(new Button(690, 940, 200, 50, this, -1, "Back to Menu"), 0);
		ok = new Button(860, 800, 200, 65, this, -2, "Apply");
		
		// Draw a color circle which is used for color selection:
		colorCircle = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)colorCircle.getGraphics();
		for(int i = 0; i < 400; i++) {
			for(int j = 0; j < 400; j++) {
				int x = i-200;
				int y = j-200;
				if(x*x+y*y < 40000 && x*x+y*y > 160*160) {
					float alpha = (float)Math.atan2(i-200, j-200);
					float H = alpha/3.141592653589f/2;
					Color c = Color.getHSBColor(H, 1, 1);
					g.setColor(c);
					g.fillRect(i, j, 1, 1);
				}
			}
		}
	}
	float selectedH = 1;
	float selectedS = 1;
	float selectedB = 1;
	BufferedImage colorCircle;
	
	int buttonsel = 1;

	@Override
	public void keyPressed(KeyEvent e) {
		if(!showColorCircle)
			buttons.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!showColorCircle)
			buttons.keyReleased(e);
	}
	
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		if(showColorCircle)
			ok.mouseUpdate(x, y, pressed);
		if(!showColorCircle)
			buttons.mouseUpdate(x, y, pressed);
		else if(pressed) {
			x -= 960;
			y -= 540;
			double r = Math.sqrt(x*x+y*y);
			if(r <= 200 && r >= 160) {
				float alpha = (float)Math.atan2(x, y);
				selectedH = alpha/3.141592653589f/2;
			}
			int xy = (int)(Math.sqrt(0.5)*160);
			if(x > -xy && x < xy && y > -xy && y < xy) {
				selectedS = (float)(x+xy)/(2*xy);
				selectedB = (float)(y+xy)/(2*xy);
			}
		}
	}

	@Override
	public void paint(Graphics2D g) {
		buttons.paint(g);
		if(showColorCircle) {
			ok.paint(g);
			// Fill the background with the current selected HSB color:
			g.setColor(Color.GRAY);
			g.fillOval(761, 341, 398, 398);
			// draw the circle:
			g.drawImage(colorCircle, 760, 340, 400, 400, null);
			// Draw the current H selection on the circle:
			g.setColor(Color.BLACK);
			double x = Math.sin(selectedH*2*Math.PI);
			double y = Math.cos(selectedH*2*Math.PI);
			g.drawLine(960+(int)(x*160), 540+(int)(y*160), 960+(int)(x*200), 540+(int)(y*200));
			// Draw the inner rect which shows all combinations of S and B:
			int xy = (int)(Math.sqrt(0.5)*160);
			for(int i = -xy; i < xy; i++) {
				float S = (float)(i+xy)/(2*xy);
				for(int j = -xy; j < xy; j++) {
					float B = (float)(j+xy)/(2*xy);
					g.setColor(Color.getHSBColor(selectedH, S, B));
					g.fillRect(i+960, j+540, 1, 1);
				}
			}
			// Draw the position of the current selection:
			int S = (int)(selectedS*2*xy)-xy;
			int B = (int)(selectedB*2*xy)-xy;
			g.setColor(B < 0.5f ? Color.white : Color.BLACK);
			g.drawOval(960+S-5, 540+B-5, 10, 10);
			
			g.setColor(Color.BLACK);
			g.fillRect(640, 200, 640, 120);
			g.setColor(Assets.text);
			Graphics.drawStringCentered(g, "Please select a color:", 50, 960, 260);
		}
	}

	@Override
	public void pressed(int id) {
		if(id == -1) {
			Assets.screen = new Options();
		}
		else if(id == -2) {
			showColorCircle = false;
			Color c = Color.getHSBColor(selectedH, selectedS, selectedB);
			switch(colorToChange) {
			case 0: Assets.btnbg = c; break;
			case 1: Assets.btn = c; break;
			case 2: Assets.btnsl = c; break;
			case 3: Assets.btnpr = c; break;
			case 4: Assets.text = c; break;
			case 5: Assets.toolTipbg = c; break;
			case 6: Assets.toolTiptext = c; break;
			}
		}
		else {
			showColorCircle = true;
			colorToChange = id-1;
		}
	}
}
