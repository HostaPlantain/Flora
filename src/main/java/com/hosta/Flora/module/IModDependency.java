package com.hosta.Flora.module;

import net.minecraftforge.fml.ModList;

public interface IModDependency {

	public void setMod(String modName);

	public String getModName();

	public default Object getModInstance()
	{
		return getModInstance(getModName());
	}

	public static boolean isModLoaded(String modName)
	{
		return ModList.get().isLoaded(modName);
	}

	public static Object getModInstance(String modName)
	{
		return ModList.get().getModContainerById(modName).get().getMod();
	}
}
