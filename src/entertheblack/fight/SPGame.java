package entertheblack.fight;

import entertheblack.ai.AI;
import entertheblack.ai.Simple;
import entertheblack.gui.Screen;
import entertheblack.menu.Assets;
import entertheblack.menu.MainMenu;
import entertheblack.storage.Variant;

// Single player fighting area.
// TODO: Add fighting against multiple ships at ocne.

public class SPGame extends Game {
	Screen previous;
	AI STANDARD = new Simple();
	AI enemyBehaviour = STANDARD;
	public SPGame(Variant v1, Variant v2, Screen prev) {
		sh1 = new Ship(v1, 1600, 490);
		sh2 = new Ship(v2, 400, 490);
		previous = prev;
	}
	public void update() {
		super.update();
		enemyBehaviour.updateBehaviour(this);
	}
	
	public void setAI(AI ai) {
		enemyBehaviour = ai;
	}
	@Override
	void end(boolean win) {
		if(win) {
			Assets.screen = previous;
			Assets.curWorld.player.credits += 100; // TODO: ship specific amount.
		}
		else {
			// TODO: Game Over screen.
			Assets.screen = new MainMenu();
		}
	}
}
