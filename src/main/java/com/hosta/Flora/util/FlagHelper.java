package com.hosta.Flora.util;

public class FlagHelper {

	public static boolean flag(short flag, int key)
	{
		return (flag & key) == key;
	}

	public static short getSizeKey(int n)
	{
		short flag = 0;
		for (int i = 0; i < n; ++i)
		{
			flag |= 1 << i;
		}
		return flag;
	}
}
