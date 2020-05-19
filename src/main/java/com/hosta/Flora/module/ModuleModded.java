package com.hosta.Flora.module;

import net.minecraftforge.fml.ModList;

public abstract class ModuleModded extends Module {

	protected String modName;

	public void setMod(String modName)
	{
		this.modName = modName;
	}

	public Object getModInstance()
	{
		return getModInstance(modName);
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
