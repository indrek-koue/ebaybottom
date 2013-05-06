package com.titanium.ebaybottom.run;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.apache.commons.io.FileUtils;
import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;
import org.joda.time.DateTime;
import org.openqa.selenium.Cookie;

import com.titanium.ebaybottom.run.model.Pair;
import com.titanium.ebaybottom.run.util.UI;

public class SessionCache {

	private static final String CACHE_DIRECTORY = "app_data/chached";

	public static void Write(Pair userAccount, Set<Cookie> cookies) {

		if (cookies == null || cookies.size() == 0) {
			UI.printError("No cookies to write to session file");
			return;
		}

		File f = null;
		try {
			f = new File(cobineCacheFilePath(userAccount));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (f.exists()) {
			UI.printDebug("no need to create session file, already has active");
			return;
		} else {
			try {
				f.getParentFile().mkdir();
				f.createNewFile();
				UI.printDebug("create session file: " + f.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (Cookie cookie : cookies) {
			String toWrite = cookie.getName() + "=" + cookie.getValue() + "\n";
			try {
				FileUtils.writeStringToFile(f, toWrite, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Set<Cookie> Load(Pair userAccount) {

		Set<Cookie> cookiesToReturn = new HashSet<>();
		File f = null;
		try {
			f = new File(cobineCacheFilePath(userAccount));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (!f.exists()) {
			return null;
		} else {
			try {
				List<String> cookies = FileUtils.readLines(f);

				for (int i = 0; i < cookies.size(); i++) {
					String cName = cookies.get(i).split("=")[0];
					String cValue = cookies.get(i).split("=")[1];

					Calendar c = Calendar.getInstance();
					c.set(3000, 1, 1);
					cookiesToReturn.add(new Cookie(cName, cValue, ".ebay.com",
							"/", c.getTime()));
					// cookiesToReturn.add(new Cookie(cName, cValue));
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return cookiesToReturn;
	}

	private static String cobineCacheFilePath(Pair userAccount)
			throws IOException {
		String path = new File(".").getCanonicalPath() + "\\" + CACHE_DIRECTORY
				+ "\\" + userAccount.getKey() + ".txt";
		return path;
	}

}
