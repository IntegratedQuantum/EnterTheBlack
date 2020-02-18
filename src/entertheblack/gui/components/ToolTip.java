package entertheblack.gui.components;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entertheblack.Util.Graphics;
import entertheblack.menu.Assets;

// Show more information about something(a part) the mouse is hovering above.

public class ToolTip {
	public int x, y, width, height, size;
	String[] lines;
	public ToolTip(String str, int size) {
		char[] data = str.toCharArray();
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = new Canvas().getFontMetrics(font);
		// There are a few special cases:
		// Additional spaces, tabs and new-line characters are trimmed later.
		// "\" forces a new line.
		ArrayList<String> lines = new ArrayList<>();
		StringBuilder curLine = new StringBuilder();
		boolean space = true;
		int maxWidth = 0;
		for(int i = 0; i < data.length; i++) {
			if(space) {
				// Ignore space and similar characters, if the last one was already a space.
				if(data[i] == ' ' || data[i] == '	' || data[i] == '\n')
					continue;
				space = false;
			}
			if(data[i] == '\\') {
				lines.add(curLine.toString());
				curLine = new StringBuilder();
				int w = Graphics.textWidth(lines.get(lines.size()-1), metrics);
				if(w > maxWidth)
					maxWidth = w;
				space = true;
				continue;
			}
			if(data[i] == ' ' || data[i] == '	' || data[i] == '\n') {
				curLine.append(' ');
				space = true;
				continue;
			}
			curLine.append(data[i]);
		}
		lines.add(curLine.toString());
		this.lines = lines.toArray(new String[lines.size()]);
		width = maxWidth+size*2;
		height = lines.size()*(size+10)+5;
		this.size = size;
	}
	public ToolTip(String str, int size, List<String> additionalData) {
		this(str, size);
		Font font = new Font("serif", 0, size);
		FontMetrics metrics = new Canvas().getFontMetrics(font);
		// Increase the lines area size:
		String[] newLines = new String[lines.length+additionalData.size()];
		System.arraycopy(lines, 0, newLines, 0, lines.length);
		int maxWidth = width;
		// Increase approximate width and height:
		for(int i = 0; i < additionalData.size(); i++) {
			int w = Graphics.textWidth(additionalData.get(additionalData.size()-1), metrics);
			if(w > maxWidth)
				maxWidth = w;
			height += size+10;
		}
		int i = lines.length;
		lines = newLines;
		for(String line : additionalData) {
			lines[i] = line;
			i++;
		}
	}
	public void updatePosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void paint(Graphics2D g) {
		g.setColor(Assets.toolTipbg);
		g.fillRect(x, y, width, height);
		g.setColor(Assets.toolTiptext);
		for(int i = 0; i < lines.length; i++) {
			Graphics.drawStringLeft(g, lines[i], size, x+5, y+i*(size+10)+size/2+5);
		}
	}
}
