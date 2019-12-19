package entertheblack.storage;

import java.util.Arrays;

// Weapon slot(point where a weapon is released).

public class Slot {
	public double x;
	public double y;
	public double alpha = 0;
	public Slot(String[] data, String file, int line) { // The first position of the array is irrelevant!
		if(data.length < 2) {
			System.err.println("Error in "+file+" in line "+(line+1)+":");
			System.err.println("Data for Slot has insufficient length: "+Arrays.toString(data));
			return;
		}
		x = Integer.parseInt(data[0]);
		y = Integer.parseInt(data[1]);
		if(data.length == 3)
			alpha = Math.toRadians(Double.parseDouble(data[2]));
	}
}
