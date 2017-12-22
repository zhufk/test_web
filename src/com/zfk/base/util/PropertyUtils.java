package com.zfk.base.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyUtils {
	private static final String PRO_APPLICATION = "application";

	public static String getString(String key) {
		try {
			return ResourceBundle.getBundle("application").getString(key);
		} catch (MissingResourceException e) {
			throw new RuntimeException("! config : " + key + '!');
		}
	}

	public static String getConfig(String key) {
		try {
			return ResourceBundle.getBundle("application").getString(key);
		} catch (MissingResourceException e) {
		}
		return "";
	}

	public static String getConfig(String resourceBundle, String key) {
		try {
			return ResourceBundle.getBundle(resourceBundle).getString(key);
		} catch (MissingResourceException e) {
		}
		return "";
	}

}
