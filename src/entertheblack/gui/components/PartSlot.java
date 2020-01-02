package entertheblack.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import entertheblack.storage.ShipSlot;

// GUI for ShipSlot.
// TODO: Add interaction.

public class PartSlot extends Component {
	public boolean selectedM; // Mouse is floating above
	public boolean pressedM; // Mouse is floating above
	public ToolTip toolTip;
	ShipSlot underlying;
	public PartSlot(int x, int y, int width, int height, ShipSlot slot) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		selectedM = pressedM = false;
		underlying = slot;
		if(slot.part != null)
			toolTip = slot.part.toolTip;
	}
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		selectedM = liesIn(x, y);
		if(pressedM && !pressed) {
			if(selectedM)
				trigger();
			pressedM = false;
		}
		if(selectedM && pressed)
			pressedM = true;
	}
	@Override
	public void paint(Graphics2D g) {
		if(underlying.part != null)
			g.drawImage(underlying.part.img, x, y, width, height, null);
		g.setColor(new Color(underlying.weapon ? 255 : (selectedM ? 128 : 64), underlying.engine ? 255 : (selectedM ? 128 : 64), underlying.reactor ? 255 : (selectedM ? 128 : 64), selectedM ? 200 : 100));
		g.fillRect(x, y, width, height);
	}
	
	// Trigger what action is supposed to happen.
	public void trigger() {
		
	}

	public boolean liesIn(int x, int y) { // Tests if a point lies inside the button.
		return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
	}
}
