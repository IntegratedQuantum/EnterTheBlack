package entertheblack.fight;

import entertheblack.ai.AI;
import entertheblack.ai.Simple;

public class SPGame extends Game {
	AI STANDARD = new Simple();
	AI enemyBehaviour = STANDARD;
	public void update() {
		super.update();
		enemyBehaviour.updateBehaviour(this);
	}
	
	public void setAI(AI ai) {
		enemyBehaviour = ai;
	}
}
