package com.mr208.CraftArcanum;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import com.mr208.CraftArcanum.Compat.ImmersiveEngineering.CompatIEManual;

public class ClientProxy extends CommonProxy {

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);

		CompatIEManual.registerManualPages();

	}
}
