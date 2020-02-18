package entertheblack.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entertheblack.fight.MPGame;
import entertheblack.gui.Screen;

// Contains main method and JFrame stuff.

public class Main extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	static MPGame game = new MPGame();
	static Screen screen;

	static int curWidth = 1600;
	static int optimalHeight = curWidth*9/16;
	static double scale = curWidth/1920.0;
	
	public Main(JFrame frame) {
		frame.addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		frame.add(this);
	}
	public void keyTyped(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		Assets.screen.keyPressed(e);
		if(Assets.gamemode == 1 || Assets.gamemode == 2) {
			game.keyPressed(e);
		}
	}
	public void keyReleased(KeyEvent e) {
		Assets.screen.keyReleased(e);
		if(Assets.gamemode == 1 || Assets.gamemode == 2) {
			game.keyReleased(e);
		}
	}
	
	// Wait deltat relative to the point in time t0.
	// A good way to do precision timing.
	public static void wait(long t0, long deltat) {
		long t = t0 + deltat - System.nanoTime() - 200000;
		int tnano = (int)(t%1000000);
		long tmilli = t/1000000;
		try {Thread.sleep(tmilli, tnano);} catch(Exception e) {} // I hate how it is that long.
	}

	static volatile boolean threadStarted = false;
	
	public void paint(Graphics g) {
		threadStarted = true;
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); TOO laggy and doesn't really help!
		g2d.scale(scale, scale);
		Assets.screen.paintScreen(g2d);
	}
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Enter The Black");
		Main main = new Main(frame);
		main.repaint();
		frame.add(main);
		main.setPreferredSize(new Dimension(curWidth, optimalHeight));
		frame.pack();
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		Assets.loadData();
		long start = System.nanoTime();
		long deltat = 1000000000/60; // Play the game at 60 fps always!
		while (true) {
			// Resize the window to keep aspect ratio if the user changed the size.
			Rectangle panelBounds = main.getBounds();
			if (curWidth != panelBounds.width || optimalHeight != panelBounds.height) {
				curWidth = panelBounds.width;
				optimalHeight = curWidth*9/16;
				scale = curWidth/1920.0;
				main.setPreferredSize(new Dimension(curWidth, optimalHeight));
				frame.pack();
			}
			Assets.screen.update();
			main.repaint();
			wait(start, deltat);
			start += deltat;
		} 
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		Assets.screen.mouseUpdate((int)(e.getX()/scale), (int)(e.getY()/scale), true);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		Assets.screen.mouseUpdate((int)(e.getX()/scale), (int)(e.getY()/scale), false);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		Assets.screen.mouseUpdate((int)(e.getX()/scale), (int)(e.getY()/scale), true);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		Assets.screen.mouseUpdate((int)(e.getX()/scale), (int)(e.getY()/scale), false);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		Assets.screen.mouseWheel(arg0.getWheelRotation());
	}
}
