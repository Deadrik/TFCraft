package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import com.bioxx.tfc.api.Constant.Global;

public class ItemCustomWood2 extends ItemCustomWood
{
	public ItemCustomWood2(Block b)
	{
		super(b);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		try
		{
			int meta = is.getItemDamage();
			if(meta > 15) meta -= 16;
			if(MetaNames != null && meta < MetaNames.length)
				return getUnlocalizedName().concat("." + MetaNames[meta]);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getLocalizedMessage());
		}
		return "Unknown";
	}
}
