package entertheblack.storage;

import java.util.ArrayList;

// Used to build the tree of a data file.

public class Node {
	public Node[] nextNodes;
	public String[] lines; // Stores all lines that are part of the top level node.
	public int[] lineNumber; // Store the line number of each line to be able to more exactly display issues in the file.
	public Node(String text) {
		this(text.toCharArray(), 0, 0);
	}
	public Node(char[] data, int start, int line) {
		ArrayList<Node> nodes = new ArrayList<>();
		ArrayList<String> lns = new ArrayList<>();
		int[] number = new int[10]; // Create an ArrayList-like structure locally, because ints are no Objects.
		int curIndex = 0;
		StringBuilder curLine = new StringBuilder();
		int depth = 0;
		boolean inPar = false;
		for(int i = start; i < data.length; i++) {
			char c = data[i];
			if(c == '\n') {
				line++;
				if(curLine.length() != 0) {
					lns.add(curLine.toString());
					curLine.delete(0, curLine.length());
					if(curIndex == number.length) { // Resize list.
						int[] old = number;
						number = new int[old.length<<1];
						System.arraycopy(old, 0, number, 0, old.length);
					}
					number[curIndex] = line;
					curIndex++;
				}
			}
			else if(c == '\"') { // Ignore parenthesis and change mode.
				inPar = !inPar;
			} else if(inPar) {
				if(depth == 0) curLine.append(c);
			} else if(c == '{') {
				depth++;
				if(depth == 1)
					nodes.add(new Node(data, i+1, line));
			} else if(c == '}') {
				depth--;
				if(depth == -1) {
					break;
				}
			} else if(depth == 0) {
				if(c != ' ' && c != '	') curLine.append(c);
			} else {
				continue;
			}
		}
		if(curLine.length() != 0) {
			lns.add(curLine.toString());
			curLine.delete(0, curLine.length());
			if(curIndex == number.length) { // Resize list
				int[] old = number;
				number = new int[old.length<<1];
				System.arraycopy(old, 0, number, 0, old.length);
			}
			number[curIndex] = line;
			curIndex++;
		}
		lines = lns.toArray(new String[lns.size()]);
		lineNumber = new int[curIndex];
		System.arraycopy(number, 0, lineNumber, 0, curIndex);
		nextNodes = nodes.toArray(new Node[nodes.size()]);
	}
	
	public String recString(String tab) {
		StringBuilder ret = new StringBuilder();
		for(int i = 0; i < lines.length; i++) {
			ret.append(tab+lines[i]+"\n");
		}
		for(Node next : nextNodes) {
			ret.append(next.recString(tab+"	"));
		}
		return ret.toString();
	}
	public String toString() {
		return recString("");
	}
}
