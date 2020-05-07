package com.hosta.Flora.util;

import net.minecraft.item.DyeColor;
import net.minecraft.util.text.TextFormatting;

public enum ColorHelper
{
	WHITE(0, DyeColor.WHITE, TextFormatting.WHITE),
	ORANGE(1, DyeColor.ORANGE, TextFormatting.GOLD),
	MAGENTA(2, DyeColor.MAGENTA, TextFormatting.AQUA),
	LIGHT_BLUE(3, DyeColor.LIGHT_BLUE, TextFormatting.BLUE),
	YELLOW(4, DyeColor.YELLOW, TextFormatting.YELLOW),
	LIME(5, DyeColor.LIME, TextFormatting.GREEN),
	PINK(6, DyeColor.PINK, TextFormatting.LIGHT_PURPLE),
	GRAY(7, DyeColor.GRAY, TextFormatting.DARK_GRAY),
	LIGHT_GRAY(8, DyeColor.LIGHT_GRAY, TextFormatting.GRAY),
	CYAN(9, DyeColor.CYAN, TextFormatting.DARK_AQUA),
	PURPLE(10, DyeColor.PURPLE, TextFormatting.DARK_PURPLE),
	BLUE(11, DyeColor.BLUE, TextFormatting.DARK_BLUE),
	BROWN(12, DyeColor.BROWN, TextFormatting.GOLD),
	GREEN(13, DyeColor.GREEN, TextFormatting.DARK_GREEN),
	RED(14, DyeColor.RED, TextFormatting.DARK_RED),
	BLACK(15, DyeColor.BLACK, TextFormatting.BLACK);

	private final DyeColor			DYE;
	private final TextFormatting	FORMAT;

	ColorHelper(int id, DyeColor dye, TextFormatting format)
	{
		this.DYE = dye;
		this.FORMAT = format;
	}

	public TextFormatting getTextFormatting()
	{
		return FORMAT;
	}

	public static ColorHelper getColor(DyeColor dye)
	{
		for (ColorHelper color : values())
		{
			if (color.DYE == dye)
			{
				return color;
			}
		}
		return ColorHelper.WHITE;
	}
}
