package com.titanium.ebaybottom.run.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

// superdealsyysi:Stupid123456###tom tom xl||| iPhone 5S
// supercharger###[293, 3270, 156955]###10:50|||100:150

public class Group {

	private static final String CSV_SEPARATOR_1 = "###";
	private static final String CSV_SEPARATOR_2 = "\\|\\|\\|";
	private static final String CSV_SEPARATOR_3 = "%%%";
	private static final String EMPTY_USER = "???:???";

	public Pair<String, String> userAccount;
	public List<Pair<String, Pair<Integer, Integer>>> keywordsWithPrices = new ArrayList<>();
	public List<Integer> categories = new ArrayList<>();

	public Group(Pair<String, String> userAccount,
			List<Pair<String, Pair<Integer, Integer>>> keywordsWithPrices,
			List<Integer> categories) {
		super();
		this.userAccount = userAccount;
		this.keywordsWithPrices = keywordsWithPrices;
		this.categories = categories;
	}

	public Group(String row) {
		String[] columns = row.split(CSV_SEPARATOR_1);

		parseUserCredentials(columns);
		parseKeywordsWithPrices(columns);
		parseCategories(columns);
	}

	public void parseUserCredentials(String[] columns) {
		if (!columns[0].equals(EMPTY_USER)) {
			String[] params = columns[0].split(Pair.VALUE_SPLITTER);
			userAccount = new Pair<String, String>(params[0], params[1]);
		} else {
			userAccount = null;
		}
	}

	public void parseKeywordsWithPrices(String[] columns) {
		for (int i = 1; i < columns.length - 1; i++) {
			String[] keywordColumns = columns[i].split(Pair.VALUE_SPLITTER);

			String keyword = keywordColumns[0];

			Pair<Integer, Integer> prices = new Pair<Integer, Integer>(
					Integer.parseInt(keywordColumns[1]),
					Integer.parseInt(keywordColumns[2]));

			keywordsWithPrices.add(new Pair<String, Pair<Integer, Integer>>(
					keyword, prices));
		}
	}

	public void parseCategories(String[] columns) {
//		String rawString = columns[columns.length - 1].replace("[", "")
//				.replace("]", "").trim();
		String[] categoriesRaw = columns[columns.length - 1].split(",");

		for (String s : categoriesRaw)
			categories.add(Integer.parseInt(s.trim()));
	}

	@Override
	public String toString() {

		String userAccountReady = userAccount == null ? EMPTY_USER
				: userAccount.toString();

		List<String> keywords = new ArrayList<String>();
		for (Pair p : keywordsWithPrices) {
			keywords.add(p.toString());
		}
		String keywordsReady = StringUtils.join(keywords, CSV_SEPARATOR_1);
		
		String categoriesReady = categories.toString();

		return StringUtils.join(new String[] { userAccountReady, keywordsReady,
				categoriesReady }, CSV_SEPARATOR_1);
	}

}
