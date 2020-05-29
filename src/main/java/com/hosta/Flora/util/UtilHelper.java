package com.hosta.Flora.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class UtilHelper {

	public static String[] getStringArray(JsonArray json)
	{
		List<String> list = new ArrayList<String>();
		for (JsonElement element : json)
		{
			list.add(element.getAsString());
		}
		return list.toArray(new String[list.size()]);
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

	public static Field getAccesable(Class<?> clazz, String fieldName) throws NoSuchFieldException, SecurityException
	{
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field;
	}

	public static Method getAccesable(Class<?> clazz, String fieldName, Class<?>... args) throws NoSuchMethodException, SecurityException
	{
		Method method = clazz.getDeclaredMethod(fieldName, args);
		method.setAccessible(true);
		return method;
	}
}
