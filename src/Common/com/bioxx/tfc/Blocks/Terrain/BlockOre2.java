package com.bioxx.tfc.Blocks.Terrain;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockOre2 extends BlockOre
{
	public BlockOre2(Material mat)
	{
		super(mat);
		blockNames = Global.ORE_METAL2;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg + Global.ORE_METAL.length;
	}

	public static Item getDroppedItem(int meta)
	{
		if(meta < (Global.ORE_METAL.length + 1))
			return TFCItems.smallOreChunk;
		else
			return null;
	}
}
