package com.mr208.CraftArcanum.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.mr208.CraftArcanum.CraftArcanum;
import com.mr208.CraftArcanum.Reference;

public class ItemCABase extends Item {

	public String itemName;
	public String[] subItemName;
	public IIcon[] icons;
	public ItemCABase(String name, int stackSize, String... subNames)
	{

		this.setUnlocalizedName(Reference.MODID+"."+name);
		this.setHasSubtypes(subNames != null && subNames.length > 0);
		this.setCreativeTab(CraftArcanum.creativeTab);
		this.setMaxStackSize(stackSize);
		this.itemName = name;
		this.subItemName = subNames!=null&&subNames.length<1?null:subNames;
		this.icons = new IIcon[this.subItemName!=null?this.subItemName.length:1];

		GameRegistry.registerItem(this,name);
	}

	public String[] getSubNames()
	{
		return subItemName;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		if(getSubNames()!=null)
			for(int itemIndex=0;itemIndex<icons.length;itemIndex++)
				this.icons[itemIndex] = iconRegister.registerIcon(Reference.MODID+":"+itemName+"_"+getSubNames()[itemIndex]);
		else
			this.icons[0] = iconRegister.registerIcon(Reference.MODID+":"+itemName);
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if(getSubNames()!=null)
			if(meta>=0&& meta<icons.length)
				return this.icons[meta];
		return icons[0];
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		if(getSubNames()!=null)
			for(int i=0;i<getSubNames().length;i++)
				list.add(new ItemStack(this,1,i));
		else
			list.add(new ItemStack(this));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if(getSubNames()!=null)
		{
			String subName= stack.getItemDamage()<getSubNames().length?getSubNames()[stack.getItemDamage()]:"";
			return this.getUnlocalizedName()+"."+subName;
		}
		return this.getUnlocalizedName();
	}
}
