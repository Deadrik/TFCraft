package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

public class ItemStone extends ItemTerraBlock
{
	public ItemStone(Block b)
	{
		super(b);
		if(TFC_Core.isStoneIgEx(b)) metaNames = Global.STONE_IGEX;
		else if(TFC_Core.isStoneIgIn(b)) metaNames = Global.STONE_IGIN;
		else if(TFC_Core.isStoneSed(b)) metaNames = Global.STONE_SED;
		else if(TFC_Core.isStoneMM(b)) metaNames = Global.STONE_MM;
	}
}
