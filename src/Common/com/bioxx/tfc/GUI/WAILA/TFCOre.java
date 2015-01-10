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

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.api.TFCOptions;

public class TFCOre implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int dmg = accessor.getBlock().damageDropped(accessor.getMetadata());
		if(dmg == 0 && (accessor.getMetadata() == 14 || accessor.getMetadata() == 15))
			return new ItemStack(TFCItems.OreChunk, 0, accessor.getMetadata());
		return new ItemStack(accessor.getBlock().getItemDropped(dmg, null, 0), 0, dmg);
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int dmg = accessor.getBlock().damageDropped(accessor.getMetadata());
		if(dmg == 0 && (accessor.getMetadata() == 14 || accessor.getMetadata() == 15))
		{
			currenttip.add("Coal");
			return currenttip;
		}

		TEOre te = (TEOre) accessor.getTileEntity();
		int ore = accessor.getMetadata();
		if(te != null)
		{
			int grade = te.extraData & 7;
			if(grade == 1)
				ore += 35;
			else if(grade == 2)
				ore += 49;
		}
		int units = ore < 14 ? TFCOptions.normalOreUnits : ore < 49 ? TFCOptions.richOreUnits : ore < 63 ? TFCOptions.poorOreUnits : 0;
		if(units > 0) currenttip.add(StatCollector.translateToLocal("gui.units") + ": " + units);
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerStackProvider(new TFCOre(), TFCBlocks.Ore.getClass());
		reg.registerBodyProvider(new TFCOre(), TFCBlocks.Ore.getClass());
	}
}
