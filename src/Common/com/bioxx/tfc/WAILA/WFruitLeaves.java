package com.bioxx.tfc.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;

public class WFruitLeaves implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockFruitLeaves.getType(accessor.getBlock(), accessor.getMetadata() % 8));

		if (index != null)
			return ItemFoodTFC.createTag(index.getOutput());
		else
			return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		NBTTagCompound tag = accessor.getNBTData();
		boolean hasFruit = tag.getBoolean("hasFruit");
		String type = BlockFruitLeaves.getType(accessor.getBlock(), accessor.getMetadata());
		
		if (!hasFruit)
			currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("gui." + type));

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockFruitLeaves.getType(accessor.getBlock(), accessor.getMetadata()));
		if (index != null)
			currenttip.add(TFC_Time.SEASONS[index.harvestStart] + " - " + TFC_Time.SEASONS[index.harvestFinish]);
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
		reg.registerStackProvider(new WFruitLeaves(), TEFruitLeaves.class);
		reg.registerHeadProvider(new WFruitLeaves(), TEFruitLeaves.class);
		reg.registerBodyProvider(new WFruitLeaves(), TEFruitLeaves.class);
		reg.registerNBTProvider(new WFruitLeaves(), TEFruitLeaves.class);
	}
}