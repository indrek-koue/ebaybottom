package com.titanium.ebaybottom.util;

public class Util {

	public static String cleanString(String s) {
		return s.trim().toLowerCase();
	}
	
	public static String padLeftAndCutIfNeeded(String toPad, int charCount) {
		if (toPad.length() > charCount)
			toPad = toPad.substring(0, charCount - 1);

		return String.format("%" + charCount + "s", toPad);
	}
	
}
