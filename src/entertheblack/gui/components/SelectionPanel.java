package entertheblack.gui.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Put Components inside to allow scrolling up/down.

public class SelectionPanel extends Component {
	public ArrayList<Component> components = new ArrayList<>();
	int deltaY = 0;
	BufferedImage content;
	Graphics2D g;

	public SelectionPanel(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)content.getGraphics();
	}
	
	public void add(Component c) {
		components.add(c);
		repaint();
	}
	
	public void repaint() {
		g.translate(0, deltaY);
		for(Component c : components) {
			c.paint(g);
			g.translate(0, c.y);
		}
		g.translate(0, -deltaY);
	}
	
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		x -= this.x;
		y -= this.y-deltaY;
		for(Component c : components) {
			c.mouseUpdate(x, y, pressed);
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(content, x, y, null);
	}

}
