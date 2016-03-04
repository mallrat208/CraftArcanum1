package com.mr208.CraftArcanum;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import com.mr208.CraftArcanum.Compat.Thaumcraft.CompatThaumcraft;
import com.mr208.CraftArcanum.Events.EventHandlerWorld;
import com.mr208.CraftArcanum.Items.ItemsCA;


@Mod(modid = Reference.MODID, version = Reference.VERSION, name= Reference.NAME, dependencies = Reference.DEPEND)
public class CraftArcanum
{

	@Mod.Instance(Reference.MODID)
	public static CraftArcanum instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY,serverSide = Reference.SERVER_PROXY, modId = Reference.MODID)
	public static CommonProxy proxy;

	public static final Logger logger = LogManager.getLogger(Reference.MODID);
	public EventHandlerWorld worldEventHandler;


	@EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		this.worldEventHandler = new EventHandlerWorld();
		MinecraftForge.EVENT_BUS.register(this.worldEventHandler);
		proxy.preInit(event);

	}

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.init(event);
    }

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}

	public static CreativeTabs creativeTab = new CreativeTabs(Reference.MODID)
	{
		@Override
		public Item getTabIconItem()
		{
			return null;
		}
		@Override
		public ItemStack getIconItemStack()
		{
			return new ItemStack(ItemsCA.ItemVoxSonitu);
		}
	};
}
