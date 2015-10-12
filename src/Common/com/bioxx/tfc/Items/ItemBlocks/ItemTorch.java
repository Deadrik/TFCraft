package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.api.TFCBlocks;

public class ItemTorch extends ItemTerraBlock
{
	public ItemTorch(Block b)
	{
		super(b);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if(entityItem.worldObj.getBlock((int)Math.floor(entityItem.posX), (int)Math.floor(entityItem.posY)-1, (int)Math.floor(entityItem.posZ)) == TFCBlocks.logPile)
		{
			int count = entityItem.getEntityData().getInteger("torchCount");
			if(count > 160)
			{
				TELogPile te = (TELogPile) entityItem.worldObj.getTileEntity((int)Math.floor(entityItem.posX), (int)Math.floor(entityItem.posY)-1, (int)Math.floor(entityItem.posZ));
				te.activateCharcoal();
				te.lightNeighbors();
				entityItem.setDead();
			}
			else
			{
				if(entityItem.worldObj.rand.nextInt(10) < 2)
					entityItem.worldObj.spawnParticle("lava", entityItem.posX, entityItem.posY, entityItem.posZ, -0.5F + entityItem.worldObj.rand.nextFloat(), -0.5F + entityItem.worldObj.rand.nextFloat(), -0.5F + entityItem.worldObj.rand.nextFloat());
				entityItem.getEntityData().setInteger("torchCount", count+1);
			}
		}
		if(entityItem.worldObj.getBlock((int)Math.floor(entityItem.posX), (int)Math.floor(entityItem.posY)-1, (int)Math.floor(entityItem.posZ)) == TFCBlocks.pottery)
		{
			int count = entityItem.getEntityData().getInteger("torchCount");
			if(count > 80)
			{
				TEPottery tepot = (TEPottery) entityItem.worldObj.getTileEntity((int)Math.floor(entityItem.posX), (int)Math.floor(entityItem.posY)-1, (int)Math.floor(entityItem.posZ));
				if(!entityItem.worldObj.isRemote && tepot.wood == 8 && tepot.burnStart == 0)
					tepot.startPitFire();
			}
			else
			{
				if(entityItem.worldObj.rand.nextInt(10) < 2)
					entityItem.worldObj.spawnParticle("lava", entityItem.posX, entityItem.posY, entityItem.posZ, -0.5F + entityItem.worldObj.rand.nextFloat(), -0.5F + entityItem.worldObj.rand.nextFloat(), -0.5F + entityItem.worldObj.rand.nextFloat());
				entityItem.getEntityData().setInteger("torchCount", count+1);
			}
		}
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int xCoord = x; int yCoord = y; int zCoord = z;
		if (side == 0) --yCoord;
		if (side == 1) ++yCoord;
		if (side == 2) --zCoord;
		if (side == 3) ++zCoord;
		if (side == 4) --xCoord;
		if (side == 5) ++xCoord;
		Block block = world.getBlock(xCoord, yCoord, zCoord);
		if (block != TFCBlocks.torch && block != TFCBlocks.torchOff)
			return super.onItemUse(is, player, world, x, y, z, side, hitX, hitY, hitZ);

		return false;
	}
}
