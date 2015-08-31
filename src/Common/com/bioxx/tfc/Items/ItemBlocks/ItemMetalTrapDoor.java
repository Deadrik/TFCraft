package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TileEntities.TEMetalTrapDoor;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemMetalTrapDoor extends ItemTerraBlock
{
	public ItemMetalTrapDoor(Block par1)
	{
		super(par1);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int out = side;
			int hinge = 0;
			switch(side)
			{
			case 0:
			{
				y--;
				if(hitX < 0.2)
					hinge = 0;
				else if(hitZ < 0.2)
					hinge = 1;
				else if(hitX > 0.8)
					hinge = 2;
				else if(hitZ > 0.8)
					hinge = 3;
				else return false;

				out += hinge<<4;
				break;
			}
			case 1:
			{
				y++;
				if(hitX < 0.2)
					hinge = 0;
				else if(hitZ < 0.2)
					hinge = 1;
				else if(hitX > 0.8)
					hinge = 2;
				else if(hitZ > 0.8)
					hinge = 3;
				else return false;

				out += hinge<<4;
				break;
			}
			case 2://North
			{
				z--;
				if(hitX < 0.2)
					hinge = 0;
				else if(hitY < 0.2)
					hinge = 1;
				else if(hitX > 0.8)
					hinge = 2;
				else if(hitY > 0.8)
					hinge = 3;
				else return false;

				out += hinge<<4;
				break;
			}
			case 3://South
			{
				z++;
				if(hitX < 0.2)
					hinge = 0;
				else if(hitY < 0.2)
					hinge = 1;
				else if(hitX > 0.8)
					hinge = 2;
				else if(hitY > 0.8)
					hinge = 3;
				else return false;

				out += hinge<<4;
				break;
			}
			case 4://East
				x--;
				if(hitY < 0.2)
					hinge = 0;
				else if(hitZ < 0.2)
					hinge = 1;
				else if(hitY > 0.8)
					hinge = 2;
				else if(hitZ > 0.8)
					hinge = 3;
				else return false;

				out += hinge<<4;
				break;
			case 5://West
			{
				x++;
				if(hitY < 0.2)
					hinge = 0;
				else if(hitZ < 0.2)
					hinge = 1;
				else if(hitY > 0.8)
					hinge = 2;
				else if(hitZ > 0.8)
					hinge = 3;
				else return false;

				out += hinge<<4;
				break;
			}
			}
			if(world.getBlock(x, y, z).getMaterial().isReplaceable())
			{
				if(world.setBlock(x, y, z, TFCBlocks.metalTrapDoor))
				{
					TEMetalTrapDoor te = (TEMetalTrapDoor) world.getTileEntity(x, y, z);
					te.sheetStack = itemstack.copy();
					te.sheetStack.stackSize = 1;
					te.data = (byte) out;
					itemstack.stackSize--;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}
}
