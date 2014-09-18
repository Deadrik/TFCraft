package com.bioxx.tfc.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockWoodSupport;
import com.bioxx.tfc.Core.TFCTabs;

public class ItemWoolYarn extends ItemTerra
{
	protected final int[][] sidesMap = new int[][]{{0,-1,0},{0,1,0},{0,0,-1},{0,0,1},{-1,0,0},{1,0,0}};

	public ItemWoolYarn()
	{
		super();
		this.hasSubtypes = false;
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFCMaterials);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && side > 1 && !world.getBlock(x, y, z).equals(TFCBlocks.Loom))
		{
			int length = 0;
			int[] map = sidesMap[side];
			ForgeDirection opp = ForgeDirection.VALID_DIRECTIONS[side].getOpposite();
			for(int i = 1; i < 6; i++)
			{
				int xCoord = x+(map[0] * i);
				int yCoord = y+(map[1] * i);
				int zCoord = z+(map[2] * i);
				Block b = world.getBlock(xCoord, yCoord, zCoord);

				if(b.getMaterial().isReplaceable())
				{
					length++;
				}
				else if(!b.isSideSolid(world, xCoord, yCoord, zCoord, opp) && !(b instanceof BlockWoodSupport))
				{
					return false;
				}
				else
				{
					break;
				}
			}
			if(length == 5)
			{
				int xCoord = x+(map[0] * 6);
				int yCoord = y+(map[1] * 6);
				int zCoord = z+(map[2] * 6);
				Block b = world.getBlock(xCoord, yCoord, zCoord);
				if(!b.isSideSolid(world, xCoord, yCoord, zCoord, opp) && !(b instanceof BlockWoodSupport))
				{
					return false;
				}
			}

			if(is.stackSize < length)
				return false;

			for(int i = 1; i <= length; i++)
			{
				int xCoord = x+(map[0] * i);
				int yCoord = y+(map[1] * i);
				int zCoord = z+(map[2] * i);
				int meta = ((side & 4) >> 2);
				world.setBlock(xCoord, yCoord, zCoord, TFCBlocks.SmokeRack, meta, 2);
				is.stackSize--;
			}

			return true;
		}
		return false;
	}
}
