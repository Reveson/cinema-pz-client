package com.example.cinema.cinemapz.panel;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public abstract class AbstractPanel extends JPanel {

	private Map<String, String> cachedInfo = new HashMap<>();

	String getCachedItem(String key) {
		return cachedInfo.get(key);
	}

	void addCachedItem(String key, String value) {
		cachedInfo.put(key, value);
	}

	void setCache(Map<String, String> cache) {
		this.cachedInfo = cache;
	}

	Map<String, String> getCache() {
		return cachedInfo;
	}

}
