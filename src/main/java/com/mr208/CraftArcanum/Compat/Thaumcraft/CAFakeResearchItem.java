package com.mr208.CraftArcanum.Compat.Thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class CAFakeResearchItem extends ResearchItem
{
	private ResearchItem original = null;

	public CAFakeResearchItem(String key, String category, String originalKey, String originalCategory, int posX, int posY, ResourceLocation icon)
	{
		super(key, category, new AspectList(), posX, posY, 1, icon);
		this.original = ResearchCategories.researchCategories.get(originalCategory).research.get(originalKey);
		this.setStub().setHidden();
		this.setupOriginal();

	}

	public CAFakeResearchItem(String key, String category, String originalKey, String originalCategory, int posX, int posY, ItemStack icon)
	{
		super(key, category, new AspectList(), posX, posY, 1, icon);
		this.original = ResearchCategories.researchCategories.get(originalCategory).research.get(originalKey);
		this.setStub().setHidden();
		this.setupOriginal();
	}

	private void setupOriginal()
	{
		if(this.original.siblings == null)
			this.original.setSiblings(new String[]{this.key});

		else
		{
			String[] newSiblings = new String[this.original.siblings.length+1];
			System.arraycopy(this.original.siblings,0, newSiblings,0, this.original.siblings.length);
			newSiblings[this.original.siblings.length] = this.key;
			this.original.setSiblings(newSiblings);
		}

	}
	@SideOnly(Side.CLIENT)
	@Override
	public String getName() {
		return this.original.getName();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getText() {
		return this.original.getText();
	}

	@Override
	public ResearchPage[] getPages() {
		return this.original.getPages();
	}
}
