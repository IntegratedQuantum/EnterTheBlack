package entertheblack.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import entertheblack.Util.Graphics;
import entertheblack.Util.Graphics3D.Camera;
import entertheblack.Util.Graphics3D.Image3D;
import entertheblack.Util.Graphics3D.Triangle;
import entertheblack.Util.Graphics3D.Vector;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;

// Screen that is shown when getting in orbit of a planet. You can land here using 'L'.
// TODO: Scanning animation on opening(also useful to disguise noise generation time)!
// TODO: Better GUI(Draw a starbase if existent, buttons, better info screen, more info on minimap).

public class LandingScreen extends Screen {
	Planet planet;
	Star last;
	
	Image3D planetView = new Image3D(400, 400);
	Camera camera = new Camera(5000);
	Vector[] vectors;
	Triangle[] triangles;
	
	public LandingScreen(Planet p, Star last) {
		this.last = last;
		if(p.species != null) {
			// TODO: Interaction screen.
		}
		planet = p;
		// Generate the 3d planet model starting with an icosahedron:
		double t = (1.0 + Math.sqrt(5.0))/2.0;
		double u = 1;
		double len = Math.sqrt(t*t+u*u);
		t /= len;
		u /= len;
		t *= 400;
		u *= 400;
		vectors = new Vector[]{
			new Vector(-u, t, 0),
			new Vector(u, t, 0),
			new Vector(-u, -t, 0),
			new Vector(u, -t, 0),
			
			new Vector(0, -u, t),
			new Vector(0, u, t),
			new Vector(0, -u, -t),
			new Vector(0, u, -t),
			
			new Vector(t, 0, -u),
			new Vector(t, 0, u),
			new Vector(-t, 0, -u),
			new Vector(-t, 0, u),
		};
		triangles = new Triangle[]{
			new Triangle(vectors[0], vectors[11], vectors[5]),
			new Triangle(vectors[0], vectors[5], vectors[1]),
			new Triangle(vectors[0], vectors[1], vectors[7]),
			new Triangle(vectors[0], vectors[7], vectors[10]),
			new Triangle(vectors[0], vectors[10], vectors[11]),
			
			new Triangle(vectors[1], vectors[5], vectors[9]),
			new Triangle(vectors[5], vectors[11], vectors[4]),
			new Triangle(vectors[11], vectors[10], vectors[2]),
			new Triangle(vectors[10], vectors[7], vectors[6]),
			new Triangle(vectors[7], vectors[1], vectors[8]),
			
			new Triangle(vectors[3], vectors[9], vectors[4]),
			new Triangle(vectors[3], vectors[4], vectors[2]),
			new Triangle(vectors[3], vectors[2], vectors[6]),
			new Triangle(vectors[3], vectors[6], vectors[8]),
			new Triangle(vectors[3], vectors[8], vectors[9]),
			
			new Triangle(vectors[4], vectors[9], vectors[5]),
			new Triangle(vectors[2], vectors[4], vectors[11]),
			new Triangle(vectors[6], vectors[2], vectors[10]),
			new Triangle(vectors[8], vectors[6], vectors[7]),
			new Triangle(vectors[9], vectors[8], vectors[1]),
		};
		int depth = 6;
		int[] index = new int[]{0, vectors.length}; // Stores how much of the triangles/vectors array is filled.
		Vector[] vectors2 = new Vector[10*(1<<(2*depth))+2]; // The length formula comes from sequence A122973 from the OEIS using 4^n = 1 << (2*n) to simplify the power operation.
		System.arraycopy(vectors, 0, vectors2, 0, vectors.length);
		vectors = vectors2;
		for(int i = 0; i < depth; i++) {
			index[0] = 0;
			Triangle[] triangles2 = new Triangle[triangles.length*4];
			Triangle.iterate(triangles, triangles2, vectors, index, new Random(planet.seed));
			triangles = triangles2;
			for(int j = 0; j < index[1]; j++) {
				vectors[j].clear();
			}
		}
		// Level out the water surface:
		for(int i = 0; i < index[1]; i++) {
			Vector v = vectors[i];
			if(v.value() < 400) {
				v.timesEquals(400/v.value());
			}
		}
		// Precalculate the orthogonals so they don't need to be calculated at rendering time.
		for(Triangle tr : triangles) {
			tr.calculateOrthogonal();
		}
		// TODO: more planet types.
		// Rivers/Lakes: TODO
		// Calculate the new normals of the triangles: TODO
		// Clouds: TODO
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'm') {
			Assets.screen = Assets.curWorld.map;
			Assets.curWorld.map.activate(this, last.x, last.y);
		}
		if(e.getKeyChar() == 'l') {
			Assets.screen = new Surface(this, planet);
		}
		if(e.getKeyChar() == 's') {
			Assets.screen = new StarbaseScreen(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Assets.screen = last;
		}
	}

	@Override
	public void paint(Graphics2D g) {
		//planet.drawNoiseMap(g);
		// Draw the planet model:
		//long t1 = System.nanoTime();
		planetView.flush();
		camera.updateAngle(0.01, 0);
		camera.update(0, 0, 0);
		for(Vector v : vectors) {
			v.project(camera);
		}
		for(Triangle t : triangles) {
			t.paint(planetView, camera);
		}
		g.drawImage(planetView.drawToImage(), 760, 340, 400, 400, null);
		//long t2 = System.nanoTime();
		//System.out.println(t2-t1);
		g.setColor(Assets.text);
		Graphics.drawStringCentered(g, planet.name, 80, 960, 360);
		Graphics.drawStringLeft(g, "Temperature = "+planet.T+" K", 20, 1100, 500);
		Graphics.drawStringRight(g, planet.species == null ? "No Government" : "Government: "+planet.species, 20, 800, 500);
		if(planet.hasStarBase)
			Graphics.drawStringRight(g, "Press 'S' to enter starbase.", 20, 800, 540);
		Graphics.drawStringLeft(g, planet.techLevel > Assets.curWorld.player.techLevel ? "Insufficient technology to Land" : "Ready to land!", 20, 1100, 550);
	}

	@Override
	public void mouseUpdate(int x, int y, boolean pressed) {
		// TODO Buttons for scan, land, …
	}

}
