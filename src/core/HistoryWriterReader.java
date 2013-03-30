package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.titanium.ebaybottom.Main;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.KeyValuePair;
import com.titanium.ebaybottom.util.Util;

public class HistoryWriterReader {
	public static String csvSeparator = "|";

	public static void write(List<Item> items, List<KeyValuePair> messages) {

		for (int i = 0; i < items.size(); i++) {
			try {
				FileUtils.writeStringToFile(new File(Main.HISTORY_FILE), items
						.get(i).toLog(csvSeparator, messages.get(i)), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<String> loadItemIds() {
		List<String> result = new ArrayList<String>();

		try {

			File f = new File(Main.HISTORY_FILE);
			if (!f.exists()) {
				f.createNewFile();
				return result;
			}

			List<String> log = FileUtils.readLines(f);

			for (String string : log) {
				result.add(string.split("\\"+csvSeparator)[3]);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
