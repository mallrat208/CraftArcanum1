package com.mr208.CraftArcanum.Compat.ImmersiveEngineering;

import net.minecraft.item.ItemStack;

import blusunrize.immersiveengineering.api.ManualHelper;
import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.lib.manual.ManualInstance;
import blusunrize.lib.manual.ManualPages;
import com.mr208.CraftArcanum.Items.ItemsCA;

public class CompatIEManual {

	public static void registerManualPages()
	{
		ManualInstance manual = ManualHelper.getManual();

		int blueprint = BlueprintCraftingRecipe.blueprintCategories.indexOf("craftarcanum");
		manual.addEntry("cadrillaugments","craftarcanum",
				new ManualPages.Crafting(manual, "cadrillaugments0", new ItemStack(IEContent.itemBlueprint,1, blueprint)),
				new ManualPages.ItemDisplay(manual, "cadrillaugments1", new ItemStack(ItemsCA.ItemDrillUpgrade,1,0)));
	}
}
