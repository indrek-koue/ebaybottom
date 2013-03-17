package com.titanium.ebaybottom.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

public class ConfigWorker {

	public int minPrice;
	public int maxPrice;
	public int resultCount;
	public int feedbackLimit;

	public String[] categories;
	public String[] blackListedUsers;
	public String[] messagesToUsers;

	public String locale;
	public boolean isDebug;

	public boolean load(String path) {
		try {
			Util.printUI("Load config.ini from:" + path);

			File f = new File(path);

			if (!f.exists()) {
				Util.printError(".ini config file not found:" + path);
				f.createNewFile();
				return false;
			}

			Ini ini = new Ini(f);

			Section main = ini.get("BotMain");
			minPrice = main.get("MIN_PRICE", Integer.class);
			maxPrice = main.get("MAX_PRICE", Integer.class);
			resultCount = main.get("RESULT_COUNT", Integer.class);
			feedbackLimit = main.get("FEEDBACK_LIMIT_FILTER", Integer.class);
			locale = main.get("LOCALE");

			Section lists = ini.get("CSVLists");
			categories = lists.get("CATEGORIES").split(",");
			blackListedUsers = lists.get("BLACKLISTEDUSERS").split(",");
			messagesToUsers = lists.get("MESSAGESTOUSERS").split(",");
			
			isDebug = ini.get("Dev").get("DEBUG", Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Util.printError(e);
		}

		return true;
	}

	@Override
	public String toString() {
		
		return String
				.format("MIN_PRICE=%s\nMAX_PRICE=%s\nRESULT_COUNT=%s\nLOCALE=%s\nDEBUG=%s\n"
						+ "Categories=%s\nBlackListedUsers=%s\nMessagesToUsers=%s\nFeedbackLimit=%s",
						minPrice, maxPrice, resultCount, locale, isDebug,
						categories.length, blackListedUsers.length, messagesToUsers.length,
						feedbackLimit);
	}

}
