package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import com.bioxx.tfc.api.Constant.Global;

public class ItemCustomWoodH3 extends ItemTerraBlock
{
	public ItemCustomWoodH3(Block b)
	{
		super(b);
		int size = Global.WOOD_ALL.length - 16;
		MetaNames = new String[size * 2];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, size);
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, size, size);
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
