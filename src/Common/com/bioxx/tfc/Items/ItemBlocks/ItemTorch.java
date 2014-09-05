package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TileEntities.TELogPile;

public class ItemTorch extends ItemTerraBlock
{
	public ItemTorch(Block b)
	{
		super(b);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if(entityItem.worldObj.getBlock((int)Math.floor(entityItem.posX), (int)Math.floor(entityItem.posY)-1, (int)Math.floor(entityItem.posZ)) == TFCBlocks.LogPile)
		{
			int count = entityItem.getEntityData().getInteger("torchCount");
			if(count > 160)
			{
				TELogPile te = (TELogPile) entityItem.worldObj.getTileEntity((int)Math.floor(entityItem.posX), (int)Math.floor(entityItem.posY)-1, (int)Math.floor(entityItem.posZ));
				te.activateCharcoal();
				te.neighborChanged();
				entityItem.setDead();
			}
			else entityItem.getEntityData().setInteger("torchCount", count+1);
		}
		return false;
	}
}
