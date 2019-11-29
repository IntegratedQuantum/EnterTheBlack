package entertheblack.gui.components;

import java.awt.Graphics2D;

public abstract class Component {
	int x, y, width, height;
	public abstract void mouseUpdate(int x, int y, boolean pressed);
	public abstract void paint(Graphics2D g);
}
