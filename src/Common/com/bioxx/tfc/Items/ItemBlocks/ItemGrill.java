package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class ItemGrill extends ItemTerraBlock
{
	public ItemGrill(Block par1)
	{
		super(par1);
		this.setCreativeTab(TFCTabs.TFC_TOOLS);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(side == 1 && world.isAirBlock(x, y+1, z))
			{
				int out = side;
				int hinge = 0;

				if (hitX < 0.2)
					hinge = 0;
				else if (hitZ < 0.2)
					hinge = 1;
				else if (hitX > 0.8)
					hinge = 2;
				else if (hitZ > 0.8)
					hinge = 3;
				else
					hinge = 0; //Default

				out += hinge << 4;

				TileEntity teFire = world.getTileEntity(x, y, z);
				if(teFire != null && teFire instanceof TEFireEntity && checkSides(world, x, y, z))
				{
					if (world.setBlock( x, y+1, z, TFCBlocks.grill, itemstack.getItemDamage(), 0x2))
					{
						TEGrill teGrill = (TEGrill) world.getTileEntity(x, y+1, z);
						teGrill.data = (byte) out;
					}
					
				}
				else if(world.isAirBlock(x, y+2, z) && checkSides(world, x, y+1, z))
				{
					if (world.setBlock( x, y+2, z, TFCBlocks.grill, itemstack.getItemDamage(), 0x2))
					{
						TEGrill teGrill = (TEGrill) world.getTileEntity(x, y + 2, z);
						teGrill.data = (byte) out;
					}
				}
				else
					return false;  // don't delete misplaced Grill
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
				return true;
			}
		}
		return false;
	}

	public boolean checkSides(World world, int x, int y, int z)
	{
		int count = 0;
		if(world.getBlock(x-1, y, z).isSideSolid(world, x-1, y, z, ForgeDirection.WEST))//Check the East Block if the West Side is solid
		{
			count++;
		}
		if(world.getBlock(x+1, y, z).isSideSolid(world, x+1, y, z, ForgeDirection.EAST))//Check the West Block if the East Side is solid
		{
			count++;
		}
		if(world.getBlock(x, y, z-1).isSideSolid(world, x, y, z-1, ForgeDirection.SOUTH))//Check the North Block if the South Side is solid
		{
			count++;
		}
		if(world.getBlock(x, y, z+1).isSideSolid(world, x, y, z+1, ForgeDirection.NORTH))//Check the South Block if the North Side is solid
		{
			count++;
		}
		return count >= 2;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}
}
