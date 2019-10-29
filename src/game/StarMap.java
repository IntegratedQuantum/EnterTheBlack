package game;

import java.util.ArrayList;
import java.util.List;

public class StarMap {
	public List<Star> systems = new ArrayList<>();
	
	public StarMap(String file) {
		char [] data = file.toCharArray();
		int depth = 0;
		StringBuilder stb = new StringBuilder();
		String name = "";
		for(int i = 0; i < data.length; i++) {
			switch(depth) {
			case 0:
				if(data[i] == '{') {
					name = stb.toString();
					stb = new StringBuilder();
					depth = 1;
				} else if(data[i] != ' ' && data[i] != '\n' && data[i] != '	') {
					stb.append(data[i]);
				}
				break;
			default:
				if(data[i] == '}') {
					depth--;
					if(depth == 0) {
						systems.add(new Star(name, stb.toString()));
					}
					else
						stb.append(data[i]);
				} else if(data[i] != ' ' && data[i] != '	') {
					stb.append(data[i]);
					if(data[i] == '{')
						depth++;
				}
				break;
			}
		}
	}
}
