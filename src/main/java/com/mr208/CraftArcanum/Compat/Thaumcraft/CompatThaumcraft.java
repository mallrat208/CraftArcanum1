package com.mr208.CraftArcanum.Compat.Thaumcraft;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import com.mr208.CraftArcanum.Items.ItemsCA;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class CompatThaumcraft {

	public static void postInit()
	{
		OreDictionary.registerOre("blockThaumium",new ItemStack(ConfigBlocks.blockCosmeticSolid,1,4));

		ResearchCategories.registerCategory("CRAFTARCANUM",new ResourceLocation("craftarcanum:textures/misc/icon.png"), new ResourceLocation("craftarcanum:textures/misc/gui_researchback.png"));

		setupFakeResearchItem("THAUMIUM", "ALCHEMY", 0 , -5, new ResourceLocation("thaumcraft:textures/items/thaumiumingot.png")).registerResearchItem();
		setupFakeResearchItem("VOIDMETAL", "ELDRITCH", -5, 0, new ResourceLocation("thaumcraft:textures/items/voidingot.png")).registerResearchItem();
		setupFakeResearchItem("ELEMENTALPICK","ARTIFICE",-3,-3, new ResourceLocation("thaumcraft:textures/items/elementalpick.png")).registerResearchItem();


		AspectList aspects = new AspectList().add(Aspect.TOOL,4).add(Aspect.MECHANISM,4);
		ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe("THAUMIUMDRILL",
				new ItemStack(ItemsCA.ItemDrillHead,1,0),
				new AspectList().add(Aspect.EARTH,5),
				"ii ", "bbi","ii ", 'i', "ingotThaumium", 'b', "blockThaumium");
		ResearchItem research = setupResearchItem("THAUMIUMDRILL","CRAFTARCANUM", aspects,0,-3,1,new ItemStack(ItemsCA.ItemDrillHead)).registerResearchItem().setSecondary();
		ResearchPage[] pages = {new ResearchPage("ca.research_page.THAUMIUMDRILL.1"),new ResearchPage(recipe)};
		research.setPages(pages).setParents("CAFAKE|THAUMIUM");
		/*
		aspects = new AspectList().add(Aspect.TOOL,4).add(Aspect.MECHANISM,4).add(Aspect.MINE,4).add(Aspect.ELDRITCH,6);
		recipe = ThaumcraftApi.addArcaneCraftingRecipe("VOIDDRILL",
				new ItemStack(ItemsCA.ItemDrillHead,1,1),
				new AspectList().add(Aspect.EARTH,5),
				"ii ", "bbi","ii ", 'i', "ingotVoid", 'b', "blockVoid");
		research = setupResearchItem("VOIDDRILL","CRAFTARCANUM",aspects,-3,0,1,new ItemStack(ItemsCA.ItemDrillHead,1,1)).registerResearchItem().setSecondary();
		pages = new ResearchPage[] {new ResearchPage("CRAFTARCANUM.research.page.VOIDDRILL.1"), new ResearchPage(recipe)};
		research.setPages(pages).setParents(new String[]{"THAUMIUMDRILL","CAFAKE|VOIDMETAL"});
		*/
		aspects = new AspectList().add(Aspect.TOOL,4).add(Aspect.MECHANISM,4).add(Aspect.MINE,4).add(Aspect.FIRE,6);
		AspectList aspectsinfusion = new AspectList().add(Aspect.FIRE,24).add(Aspect.MINE,24).add(Aspect.SENSES,16);
		research = setupResearchItem("COREDRILL","CRAFTARCANUM",aspects,-2,-2,1,new ItemStack(ItemsCA.ItemDrillHead,1,2)).registerResearchItem().setSecondary();
		ItemStack[] recipeList = new ItemStack[]{new ItemStack(ConfigItems.itemShard,1,1),new ItemStack(ConfigItems.itemShard,1,1),new ItemStack(ConfigItems.itemShard,1,1),new ItemStack(ConfigItems.itemShard,1,1), new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(ConfigBlocks.blockMagicalLog,1,0),new ItemStack(ConfigBlocks.blockMagicalLog,1,0)};
		InfusionRecipe recipeInfusion = ThaumcraftApi.addInfusionCraftingRecipe("COREDRILL",new ItemStack(ItemsCA.ItemDrillHead,1,2),2,aspectsinfusion,new ItemStack(ItemsCA.ItemDrillHead,1,0),recipeList);
		pages = new ResearchPage[] {new ResearchPage("ca.research_page.COREDRILL.1"), new ResearchPage(recipeInfusion)};
		research.setPages(pages).setParents(new String[]{"THAUMIUMDRILL","CAFAKE|ELEMENTALPICK"});

	}

	private static CAResearchItem setupResearchItem(String tag, String category, AspectList aspects, int x, int y, int complexity, Object icon)
	{
		CAResearchItem item = null;
		if(icon instanceof ItemStack)
		{
			item = new CAResearchItem(tag,category,aspects,x,y,complexity,(ItemStack)icon);
		}
		if(icon instanceof ResourceLocation)
		{
			item = new CAResearchItem(tag,category,aspects,x,y,complexity,(ResourceLocation)icon);
		}
		return item;
	}

	private static CAFakeResearchItem setupFakeResearchItem(String originalKey, String originalCategory, int x, int y, Object icon)
	{
		CAFakeResearchItem item;
		if(icon instanceof ItemStack)
			return item = new CAFakeResearchItem("CAFAKE|" + originalKey, "CRAFTARCANUM", originalKey, originalCategory, x, y, (ItemStack)icon);

		if(icon instanceof ResourceLocation)
			return item = new CAFakeResearchItem("CAFAKE|" + originalKey, "CRAFTARCANUM", originalKey, originalCategory, x, y, (ResourceLocation)icon);

		return null;
	}

}
