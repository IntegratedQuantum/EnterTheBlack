package entertheblack.storage;

import java.util.ArrayList;
import java.util.List;

import entertheblack.Util.Logger;
import entertheblack.game.Resource;
import entertheblack.game.ResourceType;

// Contains resources.

public class Inventory {
	int limit = -1;
	public int total = 0;
	List<ResourceStack> resources;
	public Inventory() {
		resources = new ArrayList<>();
	}
	public Inventory(int lim) {
		limit = lim;
		resources = new ArrayList<>();
	}
	public Inventory(Node data, String file) {
		resources = new ArrayList<>();
		String[] lines = data.lines;
		for(int i = 0; i < lines.length; i++) {
			String[] val = lines[i].split("=");
			if(val[0].equals("Limit"))
				limit = Integer.parseInt(val[1]);
			else {
				// Only give error message when the string isn't empty:
				if(val.length > 1 || val[0].length() > 0) {
					Logger.logWarning(file, data.lineNumber[i], "Unknown argument for Inventory \""+val[0]+"\" with value \""+val[1]+"\". Skipping line!");
				}
			}
		}
		for(Node node : data.nextNodes) {
			resources.add(new ResourceStack(node, file));
		}
	}
	
	public void merge(Inventory inv) { // Merge all items from the second inventory to this one.
		outer:
		for(int i = 0; i < inv.resources.size(); i++) {
			ResourceType t = inv.resources.get(i).type;
			for(int j = 0; j < resources.size(); j++) {
				if(resources.get(j).type == t) {
					resources.get(j).add(inv.resources.get(j).amount);
					total += inv.resources.get(j).amount;
					continue outer;
				}
			}
			resources.add(new ResourceStack(t));
			i--; // TODO: Dont use lazy method.
			continue;
		}
	}
	
	public void add(Resource r) {
		for(int i = 0; i < resources.size(); i++) {
			if(r.type == resources.get(i).type) {
				if(limit >= 0 && limit - total < r.amount) {
					resources.get(i).add(limit-total);
					r.amount -= limit-total;
					total = limit;
					return;
				}
				resources.get(i).add(r.amount);
				total += r.amount;
				r.amount = 0;
				return;
			}
		}
		resources.add(new ResourceStack(r.type));
		add(r); // TODO: Don't go through that array again!
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for(ResourceStack r : resources) {
			ret.append(r.type.name + ": "+r.amount);
		}
		
		return ret.toString();
	}
	
	public void save(StringBuilder sb) {
		sb.append("\nLimit=");
		sb.append(limit);
		for(ResourceStack rs : resources) {
			sb.append("{");
			rs.save(sb);
			sb.append("}");
		}
	}
	
	public int getAmount(ResourceType res) {
		for(ResourceStack stack : resources) {
			if(stack.type == res)
				return stack.amount;
		}
		return 0;
	}
	// WARNING: Do all necessary checks before calling!
	public void remove(ResourceType res, int amount) {
		for(ResourceStack stack : resources) {
			if(stack.type == res) {
				stack.amount -= amount;
				total -= amount;
			}
		}
	}
	// WARNING: Do all necessary checks before calling!
	public void add(ResourceType res, int amount) {
		for(ResourceStack stack : resources) {
			if(stack.type == res) {
				stack.amount += amount;
				total += amount;
				return;
			}
		}
		ResourceStack stack = new ResourceStack(res);
		stack.amount += amount;
		total += amount;
		resources.add(stack);
	}
}
