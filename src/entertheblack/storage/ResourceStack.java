package entertheblack.storage;

import entertheblack.game.ResourceType;
import entertheblack.menu.Assets;

public class ResourceStack {
	ResourceType type;
	int amount;
	public ResourceStack(ResourceType t) {
		type = t;
		amount = 0;
	}
	
	public ResourceStack(Node data) {
		String[] lines = data.value.split("\n");
		for(String line : lines) {
			String[] val = line.split("=");
			if(val[0].equals("Amount"))
				amount = Integer.parseInt(val[1]);
			else if(val[0].equals("Type")) {
				type = Assets.getResourceType(val[1]);
			}
		}
	}
	
	public boolean add(int add) {
		if(add+amount < 0) { // Return false if insufficient resources found.
			return false;
		}
		amount += add;
		return true;
	}
	
	public void save(StringBuilder sb) {
		sb.append("\nType=\"");
		sb.append(type.name);
		sb.append("\"");
		sb.append("\nAmount=");
		sb.append(amount);
	}
}
