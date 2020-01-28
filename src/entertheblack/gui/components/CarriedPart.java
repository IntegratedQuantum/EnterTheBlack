package entertheblack.gui.components;

import java.awt.Graphics2D;

import entertheblack.storage.Part;

public class CarriedPart extends Component {
	int x, y, width, height;
	public Part part;
	public CarriedPart(int width, int height) {
		this.width = width;
		this.height = height;
	}
	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		this.x = x-width/2;
		this.y = y-height/2;
	}
	@Override
	public void paint(Graphics2D g) {
		if(part != null)
			g.drawImage(part.img, x, y, width, height, null);
	}
}
