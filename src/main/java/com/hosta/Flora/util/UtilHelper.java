package com.hosta.Flora.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class UtilHelper {

	private static final String INI = "";

	public static String[] getStringArray(JsonArray json)
	{
		List<String> list = new ArrayList<String>();
		for (JsonElement element : json)
		{
			list.add(element.getAsString());
		}
		return list.toArray(new String[list.size()]);
	}

	public static String connect(String[] source)
	{
		return connect(source, ";");
	}

	public static String connect(String[] source, String connecter)
	{
		String result = "";
		for (int i = 0; i < source.length; ++i)
		{
			result += source[i] + (i + 1 < source.length ? connecter : INI);
		}
		return result;
	}

	public static int search(String[] source, String target)
	{
		for (int i = 0; i < source.length; ++i)
		{
			if (target.equals(source[i]))
			{
				return i;
			}
		}
		return -1;
	}
}
