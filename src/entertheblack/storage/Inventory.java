package entertheblack.storage;

import java.util.ArrayList;
import java.util.List;

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
			if(r.type != null)
				ret.append(r.type.name + ": "+r.amount);
			else
				ret.append("null: "+r.amount);
		}
		
		return ret.toString();
	}
	
	public void save(StringBuilder sb) {
		sb.append(limit);
		for(ResourceStack rs : resources) {
			sb.append("{");
			rs.save(sb);
			sb.append("}");
		}
	}
}
