package entertheblack.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entertheblack.fight.MPGame;
import entertheblack.gui.Screen;

public class Main extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	static MPGame game = new MPGame();
	static Screen screen;
	Color grey = new Color(127, 127, 127);
	Color light = new Color(200, 200, 200);
	public static int gamestart = 0;
		
	int ship1 = 0;
	int ship2 = 0;
	int buttonsel = 1;
	int controlchange = 0;
	int[] buttons = { 190, 340, 490, 640, 790, 940 };
	int what = 0;
	int shipsel = this.ship1;
	int shipsel2 = this.ship2;
	String fileNameships = "fly.EnterTheBlack";
	int graphiccqualityx = 1600;
	int graphiccqualityy = this.graphiccqualityx * 9 / 16;
	double graphiccvarx = this.graphiccqualityx / 1920.0;
	double graphiccvary = this.graphiccqualityy / 1080.0;
	int[] buttonint = new int[20];
	String[] btn0 = { "Singleplayer", "Change ship", "Multiplayer", "Options", "DO NOT PRESS!", "Quit" };
	String[] btn1 = { "Controls", "Graphic", "Back to Menu" };
	String[] btn2 = { 
			"Player 1: Turn left", "Player 1: Turn rigth", "Player 1: shoot", "Player 1: shoot(2)", "Player 1: forward", "Back to menu", "Player 2: Turn left", "Player 2: Turn rigth", "Player 2: shoot", "Player 2: shoot(2)", "Player 2: forward" };
	
	public void regraph() {
		this.graphiccqualityy = this.graphiccqualityx * 9 / 16;
		this.graphiccvarx = this.graphiccqualityx / 1920.0;
		this.graphiccvary = this.graphiccqualityy / 1080.0;
	}
	public void change() {
		if (this.shipsel != this.ship1) {
			this.ship1 = this.shipsel;
		} 
		if (this.shipsel2 != this.ship2) {
			this.ship2 = this.shipsel2;
		} 
	}
	
	public Main(JFrame frame) {
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.add(this);
	}
	public void keyTyped(KeyEvent paramKeyEvent) {}
	
	public void keyPressed(KeyEvent paramKeyEvent) {
		Assets.screen.keyPressed(paramKeyEvent);
		if(Assets.gamemode == 1 || Assets.gamemode == 2) {
			game.keyPressed(paramKeyEvent);
		}
		if (Assets.gamemode == -3) {
			if (paramKeyEvent.getKeyCode() == 38 && 
				this.buttonsel != 1) {
				this.buttonsel--;
			}
			
			if (paramKeyEvent.getKeyCode() == 40 && 
				this.buttonsel < 3) {
				this.buttonsel++;
			}
			
			if (paramKeyEvent.getKeyCode() == 17 && 
				this.buttonsel > 0) {
				this.buttonsel *= -1;
			}
		}
	}
	public void keyReleased(KeyEvent paramKeyEvent) {
		Assets.screen.keyReleased(paramKeyEvent);
		if(Assets.gamemode == 1 || Assets.gamemode == 2) {
			game.keyReleased(paramKeyEvent);
		}
		if (Assets.gamemode == -3 && 
			paramKeyEvent.getKeyCode() == 17) {
			if (this.buttonsel == -1) {
				Assets.gamemode = 2;
			}
			if (this.buttonsel == -2) {
				Assets.gamemode = 2;
			}
			if (this.buttonsel == -3) {
				Assets.gamemode = 0;
			}
			this.buttonsel = 1;
		}
	}
	
	// Wait deltat relative to the point in time t0.
	// A good way to do precision timing.
	public static void wait(long t0, long deltat) {
		long t = t0+deltat-System.nanoTime() - 200000;
		int tnano = (int)(t % 1000000);
		long tmilli = t / 1000000;
		try {Thread.sleep(tmilli, tnano);} catch(Exception e) {} // I hate how it is that long.
	}

	static volatile boolean threadStarted = false;
	
	public void paint(Graphics g) {
		threadStarted = true;
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); TOO laggy and doesn't really help!
		g2d.scale(graphiccvarx, graphiccvary);
		Assets.screen.paint(g2d);
	}
	public static void main(String[] paramArrayOfString) throws InterruptedException {
		JFrame frame = new JFrame("Enter The Black");
		Main main = new Main(frame);
		main.repaint();
		frame.add(main);
		frame.setDefaultCloseOperation(3);
		frame.setSize(main.graphiccqualityx, main.graphiccqualityy);
		frame.setVisible(true);
		Rectangle rectangle1 = new Rectangle();
		Rectangle rectangle2 = new Rectangle();
		Assets.loadData();
		long start = System.nanoTime();
		long deltat = 1000000000/60; // Play the game at 60 fps always!
		while (true) {
			rectangle1 = frame.getBounds();
			if (rectangle1 != rectangle2) {
				main.graphiccqualityx = rectangle1.width;
				main.regraph();
				rectangle1.height = main.graphiccqualityy;
				rectangle2 = rectangle1;
				frame.setSize(rectangle1.width, rectangle1.height);
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
		Assets.screen.mouseUpdate((int)(e.getX()/graphiccvarx), (int)(e.getY()/graphiccvarx), true);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		Assets.screen.mouseUpdate((int)(e.getX()/graphiccvarx), (int)(e.getY()/graphiccvarx), false);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		Assets.screen.mouseUpdate((int)(e.getX()/graphiccvarx), (int)(e.getY()/graphiccvarx), true);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		Assets.screen.mouseUpdate((int)(e.getX()/graphiccvarx), (int)(e.getY()/graphiccvarx), false);
	}
}
