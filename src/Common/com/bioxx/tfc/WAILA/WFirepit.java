package com.bioxx.tfc.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class WFirepit implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TEFirepit)
		{
			TEFirepit te = (TEFirepit) accessor.getTileEntity();
			NBTTagCompound tag = accessor.getNBTData();
			NBTTagList taglist = tag.getTagList("Items", 10);
			ItemStack fireItemStacks[] = new ItemStack[te.fireItemStacks.length];
			for (int i = 0; i < taglist.tagCount(); i++)
			{
				NBTTagCompound nbt = taglist.getCompoundTagAt(i);
				byte slot = nbt.getByte("Slot");
				if (slot >= 0 && slot < fireItemStacks.length)
					fireItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbt);
			}

			if (fireItemStacks != null)
			{
				int fuelCount = 0;
				for (ItemStack is : fireItemStacks)
				{
					if (is != null && is.getItem() != null && (is.getItem() == TFCItems.Logs || is.getItem() == Item.getItemFromBlock(TFCBlocks.Peat)))
						fuelCount++;
				}

				if (fuelCount > 0)
					currenttip.add(TFC_Core.translate("gui.fuel") + " : " + fuelCount + "/4");
			}
		}

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
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerBodyProvider(new WFirepit(), TEFirepit.class);
		reg.registerNBTProvider(new WFirepit(), TEFirepit.class);
	}
}