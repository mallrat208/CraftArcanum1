package com.mr208.CraftArcanum.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import blusunrize.immersiveengineering.api.tool.IDrillHead;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import blusunrize.immersiveengineering.common.util.Utils;
import thaumcraft.api.IRepairableExtended;

public class ItemDrillHead extends ItemCABase implements IDrillHead, IRepairableExtended {


	public ItemDrillHead() {
		super("drillhead",1,"thaumium","void","coredrill");
		perms = new DrillHeadPerm[this.subItemName.length];
		addPerm(0, new DrillHeadPerm("ingotThaumium", 2, 1, 3, 9, 6, 3200, "craftarcanum:drill_thaumium"));
		addPerm(1, new DrillHeadPerm("ingotVoid", 3, 1, 4, 13, 7, 1200, "craftarcanum:drill_void"));
		addPerm(2, new DrillHeadPerm("ingotThaumium", 3, 1, 4, 11, 7, 4100, "craftarcanum:drill_coredrill"));
	}

	DrillHeadPerm[] perms;
	private void addPerm(int i, DrillHeadPerm permutation)
	{
		if(i<perms.length)
			perms[i]=permutation;
	}

	private DrillHeadPerm getHeadPerm(ItemStack stack)
	{
		if(stack.getItemDamage()>=0 && stack.getItemDamage()<perms.length)
			return perms[stack.getItemDamage()];
		return new DrillHeadPerm("",0,0,0,0,0,0,"immersiveengineering:textures/models/drill_diesel.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv)
	{
		String ieDesc = "desc.ImmersiveEngineering.flavour.";
		list.add(StatCollector.translateToLocalFormatted(ieDesc+ "drillhead.size", getMiningSize(stack),getMiningDepth(stack)));
		list.add(StatCollector.translateToLocalFormatted(ieDesc+ "drillhead.level", Utils.getHarvestLevelName(getMiningLevel(stack))));
		list.add(StatCollector.translateToLocalFormatted(ieDesc+ "drillhead.speed",Utils.formatDouble(getMiningSpeed(stack),"0.###")));
		list.add(StatCollector.translateToLocalFormatted(ieDesc+ "drillhead.damage", Utils.formatDouble(getAttackDamage(stack),"0.###")));

		int maxDmg = getMaximumHeadDamage(stack);
		int dmg = maxDmg - getHeadDamage(stack);
		float quote = dmg/(float)maxDmg;
		String status = ""+(quote<.1?EnumChatFormatting.RED: quote<.3?EnumChatFormatting.GOLD: quote<.6?EnumChatFormatting.YELLOW: EnumChatFormatting.GREEN);
		String s = status+(getMaximumHeadDamage(stack)-getHeadDamage(stack))+"/"+getMaximumHeadDamage(stack);
		list.add( StatCollector.translateToLocalFormatted("desc.ImmersiveEngineering.info.durability", s));
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for(int i=0;i<getSubNames().length;i++)
		{
			ItemStack s = new ItemStack(this,1,i);
			if(!OreDictionary.getOres(getHeadPerm(s).repairMaterial).isEmpty());
				list.add(s);
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material)
	{
		return Utils.compareToOreName(material, getHeadPerm(stack).repairMaterial);
	}

	@Override
	public boolean beforeBlockbreak(ItemStack itemStack, ItemStack itemStack1, EntityPlayer entityPlayer) {
		return false;
	}

	@Override
	public void afterBlockbreak(ItemStack itemStack, ItemStack itemStack1, EntityPlayer entityPlayer) {

	}

	@Override
	public int getMiningSize(ItemStack itemStack)
	{
		return getHeadPerm(itemStack).drillSize;
	}

	@Override
	public int getMiningDepth(ItemStack itemStack)
	{
		return getHeadPerm(itemStack).drillDepth;
	}

	@Override
	public int getMiningLevel(ItemStack itemStack) {
		return getHeadPerm(itemStack).drillLevel;
	}

	@Override
	public float getMiningSpeed(ItemStack itemStack) {
		return getHeadPerm(itemStack).drillSpeed;
	}

	@Override
	public float getAttackDamage(ItemStack itemStack) {
		return getHeadPerm(itemStack).drillAttack;
	}

	@Override
	public int getHeadDamage(ItemStack itemStack) {
		return ItemNBTHelper.getInt(itemStack,"headDamage");
	}

	@Override
	public int getMaximumHeadDamage(ItemStack itemStack) {
		return getHeadPerm(itemStack).maxDamage;
	}

	@Override
	public void damageHead(ItemStack itemStack, int i)
	{
		ItemNBTHelper.setInt(itemStack,"headDamage",ItemNBTHelper.getInt(itemStack,"headDamage")+i);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return (double)ItemNBTHelper.getInt(stack,"headDamage") / (double)getMaximumHeadDamage(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return ItemNBTHelper.getInt(stack,"headDamage")>0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iIconReg)
	{
		super.registerIcons(iIconReg);
		for (DrillHeadPerm permutation : this.perms)
				permutation.icon = iIconReg.registerIcon(permutation.texture);
	}


	@Override
	public IIcon getDrillTexture(ItemStack isDrill, ItemStack isHead) {
		DrillHeadPerm permutation = getHeadPerm(isHead);
		return permutation.icon!=null?permutation.icon: IEContent.itemDrill.icons[0];
	}

	@Override
	public boolean doRepair(ItemStack itemStack, EntityPlayer entityPlayer, int i) {
		if(getHeadDamage(itemStack)>0) {
			ItemNBTHelper.setInt(itemStack, "headDamage", ItemNBTHelper.getInt(itemStack, "headDamage") - i);
			return true;
		}
		return false;
	}

	static class DrillHeadPerm
	{
		final String repairMaterial;
		final int drillSize;
		final int drillDepth;
		final int drillLevel;
		final float drillSpeed;
		final float drillAttack;
		final int maxDamage;
		final String texture;
		public IIcon icon;

		public DrillHeadPerm(String repairMaterial, int drillSize, int drillDepth, int drillLevel, float drillSpeed, int drillAttack, int maxDamage, String texture)
		{
			this.repairMaterial=repairMaterial;
			this.drillSize=drillSize;
			this.drillDepth=drillDepth;
			this.drillLevel=drillLevel;
			this.drillSpeed=drillSpeed;
			this.drillAttack=drillAttack;
			this.maxDamage=maxDamage;
			this.texture=texture;
		}
	}
}
