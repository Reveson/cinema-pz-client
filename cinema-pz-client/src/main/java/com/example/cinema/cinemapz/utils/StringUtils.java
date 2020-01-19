package com.example.cinema.cinemapz.utils;

public class StringUtils {

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}


	public static boolean isNullOrEmptyTrimWhiteSpaces(String string) {
		return isNullOrEmpty(string) || string.trim().isEmpty();
	}



	public static String getPpeCodeWithPrefix(String prefix, String code) {
		if (isNullOrEmpty(code)) {
			return prefix;
		}
		return prefix + code.substring(2);
	}

	public static boolean equalsIgnoreCase(String a, String b) {
		return a != null && a.equalsIgnoreCase(b);
	}

	public static String toLowerCase(String a) {
		return a == null ? null : a.toLowerCase();
	}

	public static String toUpperCase(String a) {
		return a == null ? null : a.toUpperCase();
	}



}
