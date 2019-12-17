package entertheblack.Util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Graphics {
	public static void drawStringCentered(Graphics2D g, String str, int size, int x, int y) {
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = g.getFontMetrics(font);
		x = x - metrics.stringWidth(str)/2;
		y = y - metrics.getHeight()/2 + metrics.getAscent();
		g.setFont(font);
		g.drawString(str, x, y);
	}
	
	public static void drawStringRight(Graphics2D g, String str, int size, int x, int y) {
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = g.getFontMetrics(font);
		x = x - metrics.stringWidth(str);
		y = y - metrics.getHeight()/2 + metrics.getAscent();
		g.setFont(font);
		g.drawString(str, x, y);
	}
	
	public static void drawStringLeft(Graphics2D g, String str, int size, int x, int y) {
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = g.getFontMetrics(font);
		y = y - metrics.getHeight()/2 + metrics.getAscent();
		g.setFont(font);
		g.drawString(str, x, y);
	}
}
