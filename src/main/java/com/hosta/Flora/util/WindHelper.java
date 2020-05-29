package com.hosta.Flora.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WindHelper {

	private static int			worldTick;
	private static final Wind	WIND	= new Wind(1300, 1700);

	public static Wind getWind(World worldIn)
	{
		setWind(getWorldTick(worldIn));
		return WIND;
	}

	private static int getWorldTick(World worldIn)
	{
		return (int) (worldIn.getGameTime() % WIND.FIX);
	}

	private static void setWind(int tick)
	{
		if (worldTick != tick)
		{
			worldTick = tick;
			WIND.setWind(tick);
		}
	}

	public static class Wind {

		private static final double DOUBLE_PI = Math.PI * 2;

		private final int	FIX_NORTH;
		private final int	FIX_WEST;
		private final int	FIX;

		private double	windNorth	= 0.0D;
		private double	windWest	= 0.0D;
		private double	windUp		= 0.0D;

		public Wind(int north, int west)
		{
			this.FIX_NORTH = north;
			this.FIX_WEST = west;
			this.FIX = (int) (DOUBLE_PI * FIX_NORTH * FIX_WEST);
		}

		public double getWindNorth()
		{
			return windNorth;
		}

		public double getWindWest()
		{
			return windWest;
		}

		public double getWindUp()
		{
			return windUp;
		}

		public double getAngle()
		{
			return windWest <= 0 ? Math.acos(getCos()) : DOUBLE_PI - Math.acos(getCos());
		}

		private void setWind(int tick)
		{
			windNorth = functionWind(tick, FIX_NORTH);
			windWest = functionWind(tick, FIX_WEST);
		}

		private double getCos()
		{
			return windNorth / getSpeed(false);
		}

		public double getSpeed(boolean enable3d)
		{
			return Math.sqrt((windNorth * windNorth) + (windWest * windWest) + (enable3d ? (windUp * windUp) : 0.0D));
		}

		private double functionWind(int tick, int rare)
		{
			float rareX = ((float) tick) / rare;
			double funcX = MathHelper.cos(rareX) + MathHelper.cos(rareX * 2) + MathHelper.cos(rareX * 5);
			return funcX / 20;
		}
	}
}
