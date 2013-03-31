package com.titanium.ebaybottom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.KeyValuePair;
import com.titanium.ebaybottom.util.UI;

public class History {
	public static String csvSeparator = "|";

	public static void write(List<Item> items, List<KeyValuePair> messages) {

		for (int i = 0; i < items.size(); i++) {
			try {
				String toWrite = items.get(i).toLog(csvSeparator,
						messages.get(i));
				
				FileUtils.writeStringToFile(new File(Main.HISTORY_FILE),
						toWrite, true);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<String> loadMessageSentItemIds() {
		List<String> result = new ArrayList<String>();

		try {

			File f = new File(Main.HISTORY_FILE);
			if (!f.exists()) {
				f.createNewFile();
				return result;
			}

			List<String> log = FileUtils.readLines(f);

			for (String string : log) {
				result.add(string.split("\\" + csvSeparator)[3]);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
