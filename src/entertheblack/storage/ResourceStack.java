package entertheblack.storage;

import entertheblack.game.ResourceType;

public class ResourceStack {
	ResourceType type;
	int amount;
	public ResourceStack(ResourceType t) {
		type = t;
		amount = 0;
	}
	
	public boolean add(int add) {
		if(add+amount < 0) { // Return false if insufficient resources found.
			return false;
		}
		amount += add;
		return true;
	}
}
