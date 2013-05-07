package com.titanium.ebaybottom.run.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Group {

	private static final String CSV_SEPARATOR = "###";
	private static final String ITEM_SEPARATOR = "|||";
	private static final String EMPTY_USER = "???:???";

	public Pair<String, String> userAccount;
	public List<String> keywords = new ArrayList<>();
	public List<List<Integer>> categoryGroups = new ArrayList<List<Integer>>();
	public List<Pair<Integer, Integer>> prices = new ArrayList<Pair<Integer, Integer>>();

	public Group(Pair<String, String> userAccount, List<String> keywords,
			List<List<Integer>> categoryGroups,
			List<Pair<Integer, Integer>> prices) {
		this.userAccount = userAccount;
		this.keywords = keywords;
		this.categoryGroups = categoryGroups;
		this.prices = prices;
	}

	public Group(String row) {
		// superdealsyysi:Stupid123456###tom tom xl||| iPhone 5S
		// supercharger###[293, 3270, 156955]###10:50|||100:150

		String[] columns = row.split("###");

		if (!columns[0].equals(EMPTY_USER)) {
			String username = columns[0].split(":")[0];
			String password = columns[0].split(":")[1];
			userAccount = new Pair(username, password);
		} else {
			userAccount = null;
		}

		keywords = Arrays.asList(columns[1].split("\\|\\|\\|"));

		// categorie groups
		String[] categoriesGroups = columns[2].split("\\|\\|\\|");

		for (String string : categoriesGroups) {
			String rawString = string.replace("[", "").replace("]", "").trim();
			String[] categories = rawString.split(",");

			List<Integer> category = new ArrayList<Integer>();
			for (String string2 : categories) {
				category.add(Integer.parseInt(string2.trim()));
			}

			categoryGroups.add(category);
		}

		// price
		String[] pricesRaw = columns[3].split("\\|\\|\\|");
		for (int i = 0; i < pricesRaw.length; i++) {
			prices.add(new Pair<Integer, Integer>(Integer.parseInt(pricesRaw[i]
					.split(Pair.VALUE_SPLITTER)[0]), Integer
					.parseInt(pricesRaw[i].split(Pair.VALUE_SPLITTER)[1])));
		}

	}

	@Override
	public String toString() {

		String keywordsReady = StringUtils.join(keywords, ITEM_SEPARATOR);
		String categoriesReady = StringUtils.join(categoryGroups,
				ITEM_SEPARATOR);
		String pricesReady = StringUtils.join(prices, ITEM_SEPARATOR);
		String userAccountReady = userAccount == null ? EMPTY_USER
				: userAccount.toString();

		return StringUtils.join(new String[] { userAccountReady, keywordsReady,
				categoriesReady, pricesReady }, CSV_SEPARATOR);
	}

}
