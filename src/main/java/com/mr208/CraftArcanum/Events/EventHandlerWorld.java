package com.mr208.CraftArcanum.Events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import blusunrize.immersiveengineering.common.items.ItemDrill;
import thaumcraft.common.lib.utils.Utils;
import com.mr208.CraftArcanum.Items.ItemDrillHead;

public class EventHandlerWorld {

	public EventHandlerWorld()
	{}

	@SubscribeEvent
	public void harvestEvent(BlockEvent.HarvestDropsEvent event)
	{

		if(event.drops != null && event.drops.size() != 0 && event.harvester != null)
		{
			ItemStack heldItemStack = event.harvester.inventory.getCurrentItem();
			if(heldItemStack != null && (heldItemStack.getItem() instanceof ItemDrill)) {
				ItemDrill drillItem = ((ItemDrill) heldItemStack.getItem());
				ItemStack drillHead = ((ItemDrill) heldItemStack.getItem()).getHead(heldItemStack);
				int fortuneModifier = drillItem.getUpgrades(heldItemStack).getInteger("fortune");

				if (event.fortuneLevel == 0 && fortuneModifier != 0 && !event.isSilkTouching) {
					event.drops.clear();
					event.block.dropBlockAsItem(event.world, event.x, event.y, event.z, event.blockMetadata, fortuneModifier);
					return;
				}

				if (drillHead.getItem() instanceof ItemDrillHead) {
					if (((ItemDrillHead) drillHead.getItem()).subItemName[drillHead.getItemDamage()].equals("coredrill")) {
						float clusterChance = 0.2F + event.fortuneLevel * 0.15F;
						for (int i = 0; i < event.drops.size(); ++i) {
							ItemStack droppedItemStack = (ItemStack) event.drops.get(i);
							ItemStack clusterItemStack = Utils.findSpecialMiningResult(droppedItemStack, clusterChance, event.world.rand);
							if (!droppedItemStack.isItemEqual(clusterItemStack)) {
								event.drops.set(i, clusterItemStack);
								if (!event.world.isRemote) {
									event.world.playSoundEffect((double) ((float) event.x + 0.5F), (double) ((float) event.y + 0.5F), (double) ((float) event.z + 0.5F), "random.orb", 0.2F, 0.7F + event.world.rand.nextFloat() * 0.2F);
								}
							}
						}
					}

				}

			}
		}
	}


}
