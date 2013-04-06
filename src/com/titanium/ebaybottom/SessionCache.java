package com.titanium.ebaybottom;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.apache.commons.io.FileUtils;
import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;
import org.openqa.selenium.Cookie;

import com.titanium.ebaybottom.model.KeyValuePair;

public class SessionCache {

	private static final String CHACHE_DIRECTORY = "chached";

	public static void Write(KeyValuePair userAccount, Set<Cookie> cookies) {

		File f = new File(CHACHE_DIRECTORY, userAccount.getValue() + ".txt");

		if (f.exists()) {
			return;
		} else {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (Cookie cookie : cookies) {

			String toWrite = cookie.getName() + "=" + cookie.getValue();
			try {
				FileUtils.writeStringToFile(f, toWrite);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static Set<Cookie> Load(KeyValuePair userAccount) {

		Set<Cookie> cookiesToReturn = new HashSet<>();

		File f = new File(CHACHE_DIRECTORY + "/" + userAccount.getValue()
				+ ".txt");

		if (!f.exists()) {
			return null;
		} else {
			try {
				List<String> cookies = FileUtils.readLines(f);

				for (int i = 0; i < cookies.size(); i++) {
					String cName = cookies.get(i).split("=")[0];
					String cValue = cookies.get(i).split("=")[1];

					cookiesToReturn.add(new Cookie(cName, cValue));
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return cookiesToReturn;
	}

}
