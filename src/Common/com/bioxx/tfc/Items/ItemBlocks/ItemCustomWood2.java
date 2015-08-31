package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.Constant.Global;

public class ItemCustomWood2 extends ItemCustomWood
{
	public ItemCustomWood2(Block b)
	{
		super(b);
		metaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		try
		{
			int meta = is.getItemDamage();
			if(meta > 15) meta -= 16;
			if(metaNames != null && meta < metaNames.length)
				return getUnlocalizedName().concat("." + metaNames[meta]);
		}
		catch(Exception ex)
		{
			TerraFirmaCraft.LOG.error(ex.getLocalizedMessage());
		}
		return "Unknown";
	}
}
