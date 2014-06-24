package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

public class ItemStone extends ItemTerraBlock
{
	public ItemStone(Block b)
	{
		super(b);
		if(TFC_Core.isStoneIgEx(b)) MetaNames = Global.STONE_IGEX;
		else if(TFC_Core.isStoneIgIn(b)) MetaNames = Global.STONE_IGIN;
		else if(TFC_Core.isStoneSed(b)) MetaNames = Global.STONE_SED;
		else if(TFC_Core.isStoneMM(b)) MetaNames = Global.STONE_MM;
	}
}
