package com.bioxx.tfc.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.Devices.BlockSluice;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemSluice extends ItemTerra
{

	public ItemSluice()
	{
		super();
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.HUGE);
	}


	public int getPlacedBlockMetadata(int i) {

		//TerraFirmaCraft.log.info(new StringBuilder().append(this.getItemStackDisplayName(new ItemStack(this,1,i))).toString());

		return i;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			//int xCoord = i;
			//int yCoord = j;
			//int zCoord = k;
			int r = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
			byte byte0 = 0;
			byte byte1 = 0;
			if(r == 0)//+z
			{
				byte1 = 1;
			}
			else if(r == 1)//-x
			{
				byte0 = -1;
			}
			else if(r == 2)//-z
			{
				byte1 = -1;
			}
			else if(r == 3)//+x
			{
				byte0 = 1;
			}
			if(((BlockSluice)TFCBlocks.sluice).canPlace(world, i, j+1, k,r))
			{
				world.setBlock(i, j+1, k, TFCBlocks.sluice, r, 0x2);
				if(world.getBlock(i, j+1, k) == TFCBlocks.sluice)
				{
					world.setBlock(i + byte0, j+1, k + byte1, TFCBlocks.sluice, r + 8, 0x2);
					if (!entityplayer.capabilities.isCreativeMode)
						entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
				}
				return true;
			}
		}
		return false;
	}
}
