package com.bioxx.tfc.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFarmland;

public class ItemFertilizer extends ItemTerra
{
	public ItemFertilizer() 
	{
		super();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(TFC_Core.isFarmland(world.getBlock(x, y, z)))
			{
				TEFarmland tef = (TEFarmland)world.getTileEntity(x, y, z);
				if (tef.nutrients[3] != tef.getSoilMax())
				{
					return tef.fertilize(itemstack, false);
				}
			}
			else if(world.getTileEntity(x, y, z) instanceof TECrop && TFC_Core.isFarmland(world.getBlock(x, y - 1, z)))
			{
				TEFarmland tef = (TEFarmland)world.getTileEntity(x, y - 1, z);
				if (tef.nutrients[3] != tef.getSoilMax())
				{
					return tef.fertilize(itemstack, false);
				}
			}
		}
		return false;
	}
}
