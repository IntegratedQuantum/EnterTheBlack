package entertheblack.fight;

import entertheblack.ai.AI;
import entertheblack.ai.Simple;
import entertheblack.storage.Variant;

// Single player fighting area.
// TODO: Add fighting against multiple ships at ocne.

public class SPGame extends Game {
	AI STANDARD = new Simple();
	AI enemyBehaviour = STANDARD;
	public SPGame(Variant v1, Variant v2) {
		sh1 = new Ship(v1, 1600, 490);
		sh2 = new Ship(v2, 400, 490);
	}
	public void update() {
		super.update();
		enemyBehaviour.updateBehaviour(this);
	}
	
	public void setAI(AI ai) {
		enemyBehaviour = ai;
	}
}
