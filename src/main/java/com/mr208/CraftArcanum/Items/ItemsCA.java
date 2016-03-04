package com.mr208.CraftArcanum.Items;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;

import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.common.IEContent;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemsCA {

	public static Item ItemDrillHead;
	public static Item ItemDrillUpgrade;
	public static Item ItemVoxSonitu;

	public static void preInit()
	{
		ItemDrillHead = new ItemDrillHead();
		ItemDrillUpgrade = new ItemDrillUpgrade();
		ItemVoxSonitu = new ItemVoxSonitu("vox",1,"sonitus").setTextureName("voxsonitu");

	}

	public  static void init()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemVoxSonitu).copy(), Items.book, Blocks.noteblock);
		BlueprintCraftingRecipe.addRecipe("craftarcanum",new ItemStack(ItemDrillUpgrade,1,0).copy(),new ItemStack(IEContent.itemMaterial,3,12), new ItemStack(IEContent.itemToolUpgrades,1,1), "plateSteel", "stickSteel","stickSteel");
		BlueprintCraftingRecipe.addVillagerTrade("craftarcanum",new ItemStack(Items.emerald,4));

		int blueprint = BlueprintCraftingRecipe.blueprintCategories.indexOf("craftarcanum");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(IEContent.itemBlueprint,1,blueprint), "EDE","LLL","PPP", 'E', Items.emerald, 'D', Items.diamond, 'L', "dyeBlue", 'P', Items.paper));

	}


}
