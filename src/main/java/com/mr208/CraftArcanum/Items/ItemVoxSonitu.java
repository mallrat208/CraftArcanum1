package com.mr208.CraftArcanum.Items;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import thaumcraft.common.tiles.TileSensor;

public class ItemVoxSonitu extends ItemCABase {
	public ItemVoxSonitu(String name, int stackSize, String... subNames) {
		super(name, stackSize, subNames);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		if(nbtTagCompound==null)
		{
			nbtTagCompound = new NBTTagCompound();
			stack.setTagCompound(nbtTagCompound);
		}

		if(!world.isRemote && player.isSneaking())
		{
			TileEntity tile;
			tile = world.getTileEntity(x,y,z);
			if(tile != null && tile instanceof TileEntityNote)
			{
				int instrument = 0;
				Material material = world.getBlock(x,y-1,z).getMaterial();



				if (material.equals(Material.rock)) instrument = 1;
				if (material.equals(Material.sand)) instrument = 2;
				if (material.equals(Material.glass)) instrument = 3;
				if (material.equals(Material.wood)) instrument = 4;

				nbtTagCompound.setByte("note", ((TileEntityNote) tile).note);
				nbtTagCompound.setInteger("instrument",instrument);
				return true;
				}
			else
			{
				if(tile!=null && tile instanceof TileSensor)
				{
					((TileSensor)tile).note = nbtTagCompound.getByte("note");
					tile.markDirty();
					return true;
				}
			}

			}


		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		if(nbtTagCompound == null) return stack;
		int note = 0;
		int instrument = 0;
		note = nbtTagCompound.getByte("note");
		instrument = nbtTagCompound.getInteger("instrument");

		net.minecraftforge.event.world.NoteBlockEvent.Play event = new net.minecraftforge.event.world.NoteBlockEvent.Play(world,(int) player.posX, (int) player.posY, (int) player.posZ,0,note, instrument);
		if(MinecraftForge.EVENT_BUS.post(event)) return stack;

		instrument = event.instrument.ordinal();
		note = event.getVanillaNoteId();

		float f = (float) Math.pow(2.0D, (double) (note - 12)/ 12.0D);
		String sound = "harp";
		if(instrument ==1 ) sound = "bd";
		if(instrument ==2 ) sound = "snare";
		if(instrument ==3 ) sound = "hat";
		if(instrument ==4 ) sound = "bassattack";

		world.playSoundEffect(player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D,"note." + sound, 3.0F, f);
		world.spawnParticle("note", player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, (double) note / 24.0D, 0.0D, 0.0D );

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("Unchecked")
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.craftarcanum.vox.sonitus.desc"));
		NBTTagCompound nbtTagCompound = stack.getTagCompound();		
		if(nbtTagCompound != null)
		{
				list.add(EnumChatFormatting.BOLD + StatCollector.translateToLocal("item.craftarcanum.vox.sonitus.instrument")+": " + EnumChatFormatting.RESET + nbtTagCompound.getInteger("instrument"));
				list.add(EnumChatFormatting.BOLD + StatCollector.translateToLocal("item.craftarcanum.vox.sonitus.note")+": " + EnumChatFormatting.RESET + nbtTagCompound.getByte("note"));
		}
	}

	@Override
	public void onCreated(ItemStack stack,World world, EntityPlayer player)
	{
		stack.stackTagCompound = new NBTTagCompound();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}



}
