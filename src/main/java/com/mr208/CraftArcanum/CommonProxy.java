package com.mr208.CraftArcanum;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.mr208.CraftArcanum.Compat.Thaumcraft.CompatThaumcraft;
import com.mr208.CraftArcanum.Items.ItemsCA;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event)
	{
		ItemsCA.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		ItemsCA.init();
	}

	public void postInit(FMLPostInitializationEvent event)
	{
		if(Loader.isModLoaded("Thaumcraft")) CompatThaumcraft.postInit();
	}

}
