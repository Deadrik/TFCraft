package com.bioxx.tfc.Items;

import com.bioxx.tfc.Items.ItemTerra;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class ItemMetalSheet extends ItemTerra implements ISmeltable
{
	protected int[][] sidesMap = new int[][]{{0,-1,0},{0,1,0},{0,0,-1},{0,0,+1},{-1,0,0},{1,0,0}};
	public int metalID;
	private String metal;
	protected short metalAmount;
	private boolean smeltable = true;

	public ItemMetalSheet(int mID)
	{
		super();
		setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
		setFolder("ingots/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.MEDIUM);
		metalID = mID;
		metalAmount = 200;
	}

	public ItemTerra setMetal(String m, int amt)
	{
		metal = m;
		metalAmount = (short) amt;
		return this;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		boolean isSuccessful = false;
		if(!world.isRemote)
		{
			// Sheets that have temperature or have been worked cannot be placed.
			if(itemstack.hasTagCompound())
				return false;

			TEMetalSheet te = null;
			int[] sides = sidesMap[side];

			// Adding to a sheet block with the same type of sheet
			if (world.getBlock(x, y, z) == TFCBlocks.metalSheet && isValid(world, x, y, z))
			{
				te = (TEMetalSheet)world.getTileEntity(x, y, z);
				switch(side)
				{
				case 0:
					if(!te.bottomExists())
					{
						te.toggleBottom(true);
						isSuccessful = true;
					}
					break; // Break must always be called so it doesn't loop through all the sides
				case 1:
					if(!te.topExists())
					{
						te.toggleTop(true);
						isSuccessful = true;
					}
					break;
				case 2:
					if(!te.northExists())
					{
						te.toggleNorth(true);
						isSuccessful = true;
					}
					break;
				case 3:
					if(!te.southExists())
					{
						te.toggleSouth(true);
						isSuccessful = true;
					}
					break;
				case 4:
					if(!te.eastExists())
					{
						te.toggleEast(true);
						isSuccessful = true;
					}
					break;
				case 5:
					if(!te.westExists())
					{
						te.toggleWest(true);
						isSuccessful = true;
					}
					break;
				}

				// Update block so it properly renders the newly placed side.
				if (isSuccessful)
					world.markBlockForUpdate(x, y, z);
			}
			// Creating a new sheet block. Cannot click on a sheet block to make a new adjacent one.
			else if (world.getBlock(x, y, z) != TFCBlocks.metalSheet && isValid(world, sides[0] + x, sides[1] + y, sides[2] + z))
			{
				world.setBlock(sides[0] + x, sides[1] + y, sides[2] + z, TFCBlocks.metalSheet);
				te = (TEMetalSheet) world.getTileEntity(sides[0] + x, sides[1] + y, sides[2] + z); //isValid prevents ClassCastException
				te.metalID = this.metalID;
				te.sheetStack = itemstack.copy();
				te.sheetStack.stackSize = 1; // stackSize is always 1 until the block is broken, and then updated based on the sides.
				te.toggleBySide(flipSide(side), true);
				isSuccessful = true;
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
		Block block = world.getBlock(i, j, k);
		if (block.isAir(world, i, j, k))
			return true;
		if (block == TFCBlocks.metalSheet && world.getTileEntity(i, j, k) instanceof TEMetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet)world.getTileEntity(i, j, k);
			if(te.metalID == this.metalID)
				return true;
		}
		return false;
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		if (metal == null)
		{
			return MetalRegistry.instance.getMetalFromItem(this);
		}
		else
		{
			return MetalRegistry.instance.getMetalFromString(metal);
		}
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		// TODO Auto-generated method stub
		return metalAmount;
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		// TODO Auto-generated method stub
		return smeltable;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		// hot or worked sheets cannot stack
		if (is.hasTagCompound())
		{
			NBTTagCompound tag = is.getTagCompound();
			if (TFC_ItemHeat.hasTemp(is) || tag.hasKey(TEAnvil.ITEM_CRAFTING_VALUE_TAG) || tag.hasKey(TEAnvil.ITEM_CRAFTING_RULE_1_TAG))
			{
				return 1;
			}
		}

		return super.getItemStackLimit(is);
	}
}
