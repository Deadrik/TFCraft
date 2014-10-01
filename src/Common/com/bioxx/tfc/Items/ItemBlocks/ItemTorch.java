package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int xCoord = x; int yCoord = y; int zCoord = z;
		if (side == 0) --zCoord;
		if (side == 1) ++zCoord;
		if (side == 2) --zCoord;
		if (side == 3) ++zCoord;
		if (side == 4) --xCoord;
		if (side == 5) ++xCoord;
		Block block = world.getBlock(xCoord, yCoord, zCoord);
		if(block != TFCBlocks.Torch)
			return super.onItemUse(is, player, world, x, y, z, side, hitX, hitY, hitZ);

		return false;
	}
}
