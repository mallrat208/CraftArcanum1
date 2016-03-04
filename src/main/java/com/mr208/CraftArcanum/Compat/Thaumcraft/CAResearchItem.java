package com.mr208.CraftArcanum.Compat.Thaumcraft;

import org.apache.logging.log4j.Level;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.mr208.CraftArcanum.CraftArcanum;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;

public class CAResearchItem extends ResearchItem
{
	public CAResearchItem(String key, String category, AspectList aspects, int x, int y, int complexity, ResourceLocation icon)
	{
		super(key,category,aspects,x,y,complexity,icon);
	}

	public CAResearchItem(String key, String category, AspectList aspects, int x, int y, int complexity, ItemStack icon)
	{
		super(key,category,aspects,x,y,complexity,icon);
	}

	@Override
	public String getName()
	{
		return StatCollector.translateToLocal("ca.research_name." +key);
	}

	@Override
	public String getText()
	{
		return StatCollector.translateToLocal("ca.research_text."+key);
	}

	@Override
	public ResearchItem setParents(String... parents)
	{
		for(String parent:parents)
		{
			if(ResearchCategories.getResearch(parent)==null)
			{
				CraftArcanum.logger.log(Level.ERROR,"Unable to find a Parent for "+this.key+". Have you seen my "+parent+"?");
				return null;
			}
		}
		this.parents = parents;
		return this;
	}

	@Override
	public ResearchItem setParentsHidden(String... parents)
	{
		for(String parent:parents)
		{
			if(ResearchCategories.getResearch(parent)==null)
			{
				CraftArcanum.logger.log(Level.ERROR,"Unable to find a HiddenParent for "+this.key+". Have you seen my "+parent+"?");
				return null;
			}
		}
		this.parentsHidden = parents;
		return this;
	}

	public ResearchItem addWarp(int warp)
	{
		ThaumcraftApi.addWarpToResearch(this.key, warp);
		return this;
	}

}
