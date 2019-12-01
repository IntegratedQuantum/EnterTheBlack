package entertheblack.ai;

import entertheblack.fight.Game;

// Interface to access any kind of AI. AI's might be different from ship to ship because of different weapons and maybe systems.
// TODO: Add more and better kinds of AI.
// TODO: Neural networks.

public interface AI {
	abstract void updateBehaviour(Game g);
}
