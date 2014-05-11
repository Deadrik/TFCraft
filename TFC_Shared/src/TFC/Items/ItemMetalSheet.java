package TFC.Items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.TFC_ItemHeat;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TEMetalSheet;

public class ItemMetalSheet extends ItemTerra
{
	protected int[][] sidesMap = new int[][]{{0,-1,0},{0,1,0},{0,0,-1},{0,0,+1},{-1,0,0},{1,0,0}};
	public int metalID;
	public ItemMetalSheet(int i, int mID) 
	{
		super(i);
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
		boolean isSuccessful = false;
		if(!world.isRemote)
		{
			TEMetalSheet te = null;
			int[] sides = sidesMap[side];
			if(world.getBlockId(x, y, z) == TFCBlocks.MetalSheet.blockID)
			{
				te = (TEMetalSheet)world.getBlockTileEntity(x, y, z);
				switch(side)
				{
				case 0:
					if(!te.BottomExists())
					{
						te.toggleBottom(true);
						isSuccessful = true;
						break;
					}
				case 1:
					if(!te.TopExists())
					{
						te.toggleTop(true);
						isSuccessful = true;
						break;
					}
				case 2:
					if(!te.NorthExists())
					{
						te.toggleNorth(true);
						isSuccessful = true;
						break;
					}
				case 3:
					if(!te.SouthExists())
					{
						te.toggleSouth(true);
						isSuccessful = true;
						break;
					}
				case 4:
					if(!te.EastExists())
					{
						te.toggleEast(true);
						isSuccessful = true;
						break;
					}
				case 5:
					if(!te.WestExists())
					{
						te.toggleWest(true);
						isSuccessful = true;
						break;
					}
				}
			}
			else if(isValid(world, sides[0] + x, sides[1] + y, sides[2] + z))
			{
				if(world.getBlockId(x, y, z) != TFCBlocks.MetalSheet.blockID)
				{
					world.setBlock( sides[0] + x, sides[1] + y, sides[2] + z, TFCBlocks.MetalSheet.blockID);
					te = (TEMetalSheet)world.getBlockTileEntity( sides[0] + x, sides[1] + y, sides[2] + z);
					te.metalID = this.metalID;
					te.sheetStack = itemstack.copy();
					te.sheetStack.stackSize = 1;
					te.toggleBySide(flipSide(side), true);
					isSuccessful = true;
				}
			}
			else
			{
				isSuccessful = false;
			}

			if(isSuccessful)
			{
				itemstack.stackSize--;
			}

		}
		return isSuccessful;
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
		int bid = world.getBlockId(i, j, k);
		if(bid == 0)
			return true;
		if(bid == TFCBlocks.MetalSheet.blockID)
		{
			TEMetalSheet te = (TEMetalSheet)world.getBlockTileEntity(i, j, k);
			if(te.metalID == this.metalID)
				return true;
		}

		return false;
	}
	
}
