package me.unknown.utilities.util;

public class BaseUtil {

	public static boolean isBoolean(String s) {
		if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
			return true;
		}else {
			return false;
		}
	}

	public static Boolean getBoolean(String s) {
		if(!isBoolean(s))
			return null;
		return Boolean.parseBoolean(s);
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Double getDouble(String s) {
		if(!isDouble(s))
			return null;
		return Double.parseDouble(s);
	}

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Integer getInt(String s) {
		if(!isInt(s))
			return null;
		return Integer.parseInt(s);
	}

}
