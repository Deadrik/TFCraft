package TFC.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.TileEntities.TEMetalSheet;

public class ItemMetalSheet extends ItemTerra
{
	protected int[][] sidesMap = new int[][]{{0,-1,0},{0,1,0},{0,0,-1},{0,0,+1},{-1,0,0},{1,0,0}};
	public int metalID;

	public ItemMetalSheet(int mID)
	{
		super();
		setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFCMaterials);
		setFolder("ingots/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.MEDIUM);
		metalID = mID;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TEMetalSheet te = null;
			int[] sides = sidesMap[side];
			if(world.getBlock(x, y, z) == TFCBlocks.MetalSheet)
			{
				te = (TEMetalSheet)world.getTileEntity(x, y, z);
				switch(side)
				{
				case 0:
					if(!te.BottomExists())
					{
						te.toggleBottom(true);
						return true;
					}
				case 1:
					if(!te.TopExists())
					{
						te.toggleTop(true);
						return true;
					}
				case 2:
					if(!te.NorthExists())
					{
						te.toggleNorth(true);
						return true;
					}
				case 3:
					if(!te.SouthExists())
					{
						te.toggleSouth(true);
						return true;
					}
				case 4:
					if(!te.EastExists())
					{
						te.toggleEast(true);
						return true;
					}
				case 5:
					if(!te.WestExists())
					{
						te.toggleWest(true);
						return true;
					}
				}
			}
			else if(isValid(world, sides[0] + x, sides[1] + y, sides[2] + z))
			{
				if(world.getBlock(x, y, z) != TFCBlocks.MetalSheet)
				{
					world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, TFCBlocks.MetalSheet);
					te = (TEMetalSheet)world.getTileEntity( sides[0] + x, sides[1] + y, sides[2] + z);
					te.metalID = this.metalID;
					te.sheetStack = itemstack.copy();
					te.sheetStack.stackSize = 1;
					te.toggleBySide(flipSide(side), true);
				}
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	public int flipSide(int side)
	{
		switch(side)
		{
		case 0: return 1;
		case 1: return 0;
		case 2: return 3;
		case 3: return 2;
		case 4: return 5;
		case 5: return 4;
		}
		return 0;
	}

	public boolean isValid(World world, int i, int j, int k)
	{
		Block bid = world.getBlock(i, j, k);
		if(bid == Blocks.air)
			return true;
		if(bid == TFCBlocks.MetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet)world.getTileEntity(i, j, k);
			if(te.metalID == this.metalID)
				return true;
		}
		return false;
	}

}
