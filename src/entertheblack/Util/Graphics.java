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
	
	public static int textWidth(String str, FontMetrics metrics) {
		return metrics.stringWidth(str);
	}
	
	// Height depends on text length
	// Returns the end y position.
	public static int drawDynamicTextBox(Graphics2D g, String str, int deltaY, int x, int y, int width) {
		FontMetrics metrics = g.getFontMetrics();
		y = y - metrics.getHeight()/2 + metrics.getAscent();
		String[] words = str.split(" ");
		int curIndex = 0;
		int spaceLength = textWidth(" ", metrics);
		while(curIndex < words.length) {
			// Draw at least one word every time:
			int number = 1;
			int curLength = textWidth(words[curIndex], metrics);
			for(int i = curIndex+1; i < words.length; i++) {
				curLength += spaceLength+textWidth(words[i], metrics);
				if(curLength > width) {
					break;
				}
				number++;
			}
			// Put the words back together:
			String line = "";
			for(int i = 0; i < number; i++) {
				line += words[curIndex+i];
				line += " ";
			}
			// Draw the line:
			g.drawString(line, x, y);
			
			// Go to next row:
			curIndex += number;
			y += deltaY;
		}
		return y;
	}
}
