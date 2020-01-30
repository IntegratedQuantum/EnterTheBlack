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
		String[] lines = data.lines;
		for(int i = 0; i < lines.length; i++) {
			String[] val = lines[i].split("=");
			if(val[0].equals("Amount"))
				amount = Integer.parseInt(val[1]);
			else if(val[0].equals("Type")) {
				type = Assets.getResourceType(val[1]);
			} else {
				// Only give error message when the string isn't empty:
				if(val.length > 1 || val[0].length() > 0) {
					System.err.println("Error in save file in line "+data.lineNumber[i]+":");
					System.err.println("Unknown argument for ResourceStack \""+val[0]+"\" with value \""+val[1]+"\". Skipping line!");
				}
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
