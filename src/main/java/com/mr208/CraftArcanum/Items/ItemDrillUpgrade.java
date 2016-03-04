package com.mr208.CraftArcanum.Items;

import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.tool.IUpgrade;

public class ItemDrillUpgrade extends ItemCABase implements IUpgrade {

	public ItemDrillUpgrade()
	{
		super("drillupgrade",1,"drillFortune");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool)
	{
		if(itemStack.getItemDamage()<getSubNames().length)
		{
			String[] flavour = ImmersiveEngineering.proxy.splitStringOnWidth(StatCollector.translateToLocal("item.craftarcanum." + this.itemName + "." + this.getSubNames()[itemStack.getItemDamage()] + ".desc"), 200);
			for(String s : flavour)
				list.add(s);
		}
	}

	@Override
	public Set<String> getUpgradeTypes(ItemStack upgradeIS) {
		return ImmutableSet.of("DRILL");
	}

	@Override
	public boolean canApplyUpgrades(ItemStack itemStack, ItemStack itemStack1) {
		return true;
	}

	@Override
	public void applyUpgrades(ItemStack drillIS, ItemStack upgradeIS, HashMap<String, Object> mods) {

		Integer mod;
		switch (upgradeIS.getItemDamage())
		{
			case 0:
				mod = (Integer)mods.get("fortune");
				mods.put("fortune", (mod==null?0:mod)+upgradeIS.stackSize);
				break;

			case 1:
				mods.put("silktouch", true);
				break;
			default:
				break;
		}

	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		switch (stack.getItemDamage())
		{
			case 0:
				return 3;
			default:
				return 1;
		}
	}
}
