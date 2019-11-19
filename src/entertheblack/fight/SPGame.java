package entertheblack.fight;

import entertheblack.ai.AI;
import entertheblack.ai.Simple;
import entertheblack.menu.Assets;
import entertheblack.storage.ShipData;

public class SPGame extends Game {
	AI STANDARD = new Simple();
	AI enemyBehaviour = STANDARD;
	public SPGame(ShipData sd1, ShipData sd2) {
		sh1 = new Ship(sd1, 1600, 490);
		sh2 = new Ship(sd2, 400, 490);
	}
	public void update() {
		super.update();
		enemyBehaviour.updateBehaviour(this);
	}
	
	public void setAI(AI ai) {
		enemyBehaviour = ai;
	}
}
