package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class ItemGrill extends ItemTerraBlock
{
	public ItemGrill(Block par1)
	{
		super(par1);
		this.setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(side == 1 && world.isAirBlock(x, y+1, z))
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te != null && te instanceof TEFireEntity && checkSides(world, x, y, z))
					world.setBlock( x, y+1, z, TFCBlocks.Grill, itemstack.getItemDamage(), 0x2);
				else if(world.isAirBlock(x, y+2, z) && checkSides(world, x, y+1, z))
					world.setBlock( x, y+2, z, TFCBlocks.Grill, itemstack.getItemDamage(), 0x2);
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
		if(count >= 2)
			return true;

		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}
}
