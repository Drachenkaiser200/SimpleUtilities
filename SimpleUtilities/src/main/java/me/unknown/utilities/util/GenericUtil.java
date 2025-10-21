package me.unknown.utilities.util;

import java.util.ArrayList;
import java.util.List;

public class GenericUtil {

	public static Integer getInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> asList(T...paste) {
		List<T> list = new ArrayList<>();
		for(int i = 0; i < paste.length; i++)
			list.add(paste[i]);
		return list;
	}
	
	public static char toChar(int i) {
		return Integer.valueOf(i).toString().toCharArray()[0];
	}

}
