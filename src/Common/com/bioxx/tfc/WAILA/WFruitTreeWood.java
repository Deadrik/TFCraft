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

import com.bioxx.tfc.Blocks.Flora.BlockFruitWood;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEFruitTreeWood;

public class WFruitTreeWood implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		FloraIndex index = FloraManager.getInstance().findMatchingIndex(BlockFruitWood.getType(accessor.getMetadata()));

		if (index != null)
			return ItemFoodTFC.createTag(index.getOutput());
		else
			return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		String type = BlockFruitWood.getType(accessor.getMetadata());

		currenttip.set(0, EnumChatFormatting.WHITE.toString() + TFC_Core.translate("gui." + type));

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
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
		reg.registerStackProvider(new WFruitTreeWood(), TEFruitTreeWood.class);
		reg.registerHeadProvider(new WFruitTreeWood(), TEFruitTreeWood.class);
		reg.registerBodyProvider(new WFruitTreeWood(), TEFruitTreeWood.class);
		reg.registerNBTProvider(new WFruitTreeWood(), TEFruitTreeWood.class);
	}
}