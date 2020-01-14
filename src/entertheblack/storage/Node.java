package entertheblack.storage;

import java.util.ArrayList;

// Used to build the tree of a data file.

public class Node {
	public Node[] nextNodes;
	public String value;
	public Node(String text) {
		StringBuilder top = new StringBuilder(); // Put all data from the top level in one consecutive String.
		ArrayList<Node> nodes = new ArrayList<>();
		char[] data = text.toCharArray();
		int depth = 0;
		boolean inPar = false;
		for(int i = 0; i < data.length; i++) {
			char c = data[i];
			if(c == '\"') { // Ignore parenthesis and change mode.
				inPar = !inPar;
			} else if(inPar) {
				if(depth == 0) top.append(c);
			} else if(c == '{') {
				depth++;
				nodes.add(new Node(data, i+1));
			} else if(c == '}') {
				depth--;
				//if(depth == 0) {
				//	nodes.add(new Node(cur.toString()));
				//	cur = new StringBuilder();
				//}
			} else if(depth == 0) {
				if(c != ' ' && c != '	') top.append(c);
			} else {
				continue;
			}
		}
		value = top.toString();
		nextNodes = nodes.toArray(new Node[nodes.size()]);
	}
	public Node(char[] data, int start) {
		StringBuilder top = new StringBuilder(); // Put all data from the top level in one consecutive String.
		ArrayList<Node> nodes = new ArrayList<>();
		int depth = 0;
		boolean inPar = false;
		for(int i = start; i < data.length; i++) {
			char c = data[i];
			if(c == '\"') { // Ignore parenthesis and change mode.
				inPar = !inPar;
			} else if(inPar) {
				if(depth == 0) top.append(c);
			} else if(c == '{') {
				depth++;
				if(depth == 1)
					nodes.add(new Node(data, i+1));
			} else if(c == '}') {
				depth--;
				//if(depth == 0) {
				//	nodes.add(new Node(cur.toString()));
				//	cur = new StringBuilder();
				//}
				if(depth == -1) {
					break;
				}
			} else if(depth == 0) {
				if(c != ' ' && c != '	') top.append(c);
			} else {
				continue;
			}
		}
		value = top.toString();
		nextNodes = nodes.toArray(new Node[nodes.size()]);
	}
	
	public String recString(String tab) {
		String ret = "";
		String[] lines = value.split("\n");
		for(int i = 0; i < lines.length; i++) {
			ret += tab+lines[i]+"\n";
		}
		for(Node next : nextNodes) {
			ret += next.recString(tab+"	");
		}
		return ret;
	}
	public String toString() {
		return recString("");
	}
}
