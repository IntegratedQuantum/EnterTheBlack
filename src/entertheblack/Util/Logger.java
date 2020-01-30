package entertheblack.Util;

import java.util.Date;

// Log some activities and error messages.
// TODO: catch exceptions in main and save log file.

public class Logger {
	private static StringBuilder logFile = new StringBuilder();
	public static String getTime() { // D:M:Y:h:min:s:ms
		Date date = new Date(System.currentTimeMillis());
		return date.toString();
	}
	public static void log(String message) {
		String output = "["+getTime()+"]: "+message+"\n";
		// Use ANSI coloring to color the terminal output(doesn't work in eclipse).
		System.out.print("\033[0m"+output);
		logFile.append(output);
	}
	public static void logWarning(String file, int line, String message) {
		String output = "["+getTime()+"] WARNING("+file+":"+line+"): "+message+"\n";
		// Use ANSI coloring to color the terminal output(doesn't work in eclipse).
		System.out.print("\033[33;1m"+output);
		logFile.append(output);
	}
	public static void logWarning(String message) {
		String output = "["+getTime()+"] WARNING: "+message+"\n";
		// Use ANSI coloring to color the terminal output(doesn't work in eclipse).
		System.out.print("\033[33;1m"+output);
		logFile.append(output);
	}
	public static void logError(String file, int line, String message) {
		String output = "["+getTime()+"] ERROR("+file+":"+line+"): "+message+"\n";
		// Use ANSI coloring to color the terminal output(doesn't work in eclipse).
		System.err.print("\033[31;1m"+output);
		logFile.append(output);
	}
	public static void logError(String activity, String message) {
		String output = "["+getTime()+"] ERROR in "+activity+": "+message+"\n";
		// Use ANSI coloring to color the terminal output(doesn't work in eclipse).
		System.err.print("\033[31;1m"+output);
		logFile.append(output);
	}
	public static void logError(String message) {
		String output = "["+getTime()+"] ERROR: "+message+"\n";
		// Use ANSI coloring to color the terminal output(doesn't work in eclipse).
		System.err.print("\033[31;1m"+output);
		logFile.append(output);
	}
	public static void logFatal(String file, int line, String message) {
		String output = "["+getTime()+"] FATAL ERROR("+file+":"+line+"): "+message+"\n";
		// Use ANSI coloring to color the terminal output(doesn't work in eclipse).
		System.err.print("\033[31;1m"+output);
		logFile.append(output);
	}
}
