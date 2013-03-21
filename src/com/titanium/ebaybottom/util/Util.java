package com.titanium.ebaybottom.util;

import com.titanium.ebaybottom.Main;

public class Util {
	public static void printError(Object o) {
		printUI("ERROR:" + o);
	}

	public static void printDebug(Object o) {
		if (Config.isDebug)
			printUI("DEBUG:" + o.toString());
	}

	public static void printUI(Object o) {
		System.out.println("::" + o);
	}

	public static String getUserInput(String header) {
		System.out.print("\n++ " + header+"\n");
		return TextIO.getlnString();

	}
}
