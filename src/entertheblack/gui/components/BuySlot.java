package entertheblack.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import entertheblack.game.Player;
import entertheblack.storage.Part;

public class BuySlot extends PartSlot {
	Part underlying;
	Player buyer;
	public BuySlot(int x, int y, int width, int height, Part part, CarriedPart c, Player player) {
		super(x, y, width, height, null, c);
		underlying = part;
		toolTip = part.toolTip;
		buyer = player;
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
		if(underlying != null)
			g.drawImage(underlying.img, x, y, width, height, null);
		g.setColor(new Color(underlying.weapon ? 255 : (selectedM ? 128 : 64), underlying.engine ? 255 : (selectedM ? 128 : 64), underlying.reactor ? 255 : (selectedM ? 128 : 64), selectedM ? 200 : 100));
		g.fillRect(x, y, width, height);
	}
	
	// Remove the current Part from the slot and attach it to the mouse, if the currently carried part can be placed in this slot.
	@Override
	public void trigger() {
		Part p = carrier.part;
		if(p == null && buyer.credits >= underlying.cost) {
			carrier.part = underlying;
			buyer.credits -= underlying.cost;
		}
	}
}
