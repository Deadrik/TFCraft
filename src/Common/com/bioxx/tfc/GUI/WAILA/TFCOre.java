package com.bioxx.tfc.GUI.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

public class TFCOre implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TEOre)
		{
			int meta = accessor.getMetadata();
			TEOre te = (TEOre) accessor.getTileEntity();

			if (accessor.getBlock() == TFCBlocks.Ore) // Metals & Coal
			{
				ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, getOreGrade(te, meta));
				if (meta == 14 || meta == 15) // Bituminous Coal & Lignite
					itemstack = new ItemStack(TFCItems.Coal);

				return itemstack;
			}
			else if (accessor.getBlock() == TFCBlocks.Ore2) // Minerals
			{
				ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, meta + Global.ORE_METAL.length);
				if (meta == 5)
					itemstack = new ItemStack(TFCItems.GemDiamond); // Kaolinite
				else if (meta == 13)
					itemstack = new ItemStack(TFCItems.Powder, 1, 4); // Saltpeter

				return itemstack;
			}
			else if (accessor.getBlock() == TFCBlocks.Ore3) // Minerals
			{
				ItemStack itemstack = new ItemStack(TFCItems.OreChunk, 1, meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length);
				return itemstack;
			}
		}

		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int meta = accessor.getMetadata();

		if (accessor.getBlock() == TFCBlocks.Ore)
		{
			if (meta == 14)
			{
				currenttip.set(0, StatCollector.translateToLocal("item.Ore.Bituminous Coal.name"));
			}
			else if (meta == 15)
			{
				currenttip.set(0, StatCollector.translateToLocal("item.Ore.Lignite.name"));
			}
		}
		else if (accessor.getBlock() == TFCBlocks.Ore2)
		{
			if (meta == 5)
			{
				currenttip.set(0, StatCollector.translateToLocal("item.Ore.Kimberlite.name"));
			}
			else if (meta == 13)
			{
				currenttip.set(0, StatCollector.translateToLocal("item.Ore.Saltpeter.name"));
			}
		}

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TEOre)
		{
			int meta = accessor.getMetadata();

			if (accessor.getBlock() == TFCBlocks.Ore)
			{
				switch (meta)
				{
				case 0:
				case 9:
				case 13:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Copper"));
					break;
				case 1:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Gold"));
					break;
				case 2:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Platinum") + " - " + StatCollector.translateToLocal("gui.useless"));
					break;
				case 3:
				case 10:
				case 11:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Iron"));
					break;
				case 4:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Silver"));
					break;
				case 5:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Tin"));
					break;
				case 6:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Lead") + " - " + StatCollector.translateToLocal("gui.useless"));
					break;
				case 7:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Bismuth"));
					break;
				case 8:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Nickel"));
					break;
				case 12:
					currenttip.add(StatCollector.translateToLocal("gui.metal.Zinc"));
					break;
				case 14:
				case 15:
					currenttip.add(StatCollector.translateToLocal("item.coal.coal.name"));
					return currenttip;
				}

				TEOre te = (TEOre) accessor.getTileEntity();

				int ore = getOreGrade(te, meta);

				int units = ore < 14 ? TFCOptions.normalOreUnits : ore < 49 ? TFCOptions.richOreUnits : ore < 63 ? TFCOptions.poorOreUnits : 0;
				if (units > 0)
					currenttip.add(StatCollector.translateToLocal("gui.units") + " : " + units);
			}
			else if (accessor.getBlock() == TFCBlocks.Ore2)
			{
				switch (meta)
				{
				case 1:
				case 2:
				case 3:
				case 6:
				case 8:
				case 9:
				case 10:
				case 14:
					currenttip.add(StatCollector.translateToLocal("gui.useless"));
					break;
				case 5:
					currenttip.add(StatCollector.translateToLocal("item.Diamond.Normal.name"));
					break;
				case 11:
				case 12:
					currenttip.add(StatCollector.translateToLocal("item.redstone.name"));
					break;
				case 15:
					currenttip.add(StatCollector.translateToLocal("item.Fertilizer.name"));
					break;
				}
			}
			else if (accessor.getBlock() == TFCBlocks.Ore3)
			{
				switch (meta)
				{
				case 0:
					currenttip.add(StatCollector.translateToLocal("item.Powder.Flux.name"));
					break;
				case 1:
					currenttip.add(StatCollector.translateToLocal("gui.useless"));
					break;
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerStackProvider(new TFCOre(), TEOre.class);
		reg.registerHeadProvider(new TFCOre(), TEOre.class);
		reg.registerBodyProvider(new TFCOre(), TEOre.class);
	}

	private int getOreGrade(TEOre te, int ore)
	{
		if (te != null)
		{
			int grade = (te.extraData & 7);
			if (grade == 1)
				ore += 35;
			else if (grade == 2)
				ore += 49;
		}
		return ore;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		return tag;
	}
}
