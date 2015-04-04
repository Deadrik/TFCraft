package com.bioxx.tfc.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCBlocks;

public class TEFruitTreeWood extends TileEntity implements IInventory
{
	public boolean isTrunk;
	public int height;
	public long birthTimeWood;
	public long birthTimeLeaves;
	final long leafGrowthRate = 3;
	final long GrowTime = 30;
	final long branchGrowTime = 20;

	public TEFruitTreeWood()
	{
		height = 0;
		isTrunk = false;
		birthTimeWood = 0;
		birthTimeLeaves = 0;
	}

	public void setBirth()
	{
		birthTimeWood = TFC_Time.getTotalDays();
		birthTimeLeaves = TFC_Time.getTotalDays();
	}

	public void setBirthWood(long t)
	{
		birthTimeWood += t;
	}

	public void setBirthLeaves(long t)
	{
		birthTimeLeaves += t;
	}

	public void setTrunk(boolean b)
	{
		isTrunk = b;
	}

	public void setHeight(int h)
	{
		height = h;
	}

	public void setup(boolean isTrunk, int h, long growTime)
	{
		setTrunk(isTrunk);
		setHeight(h);
		setBirthWood(growTime);
	}

	public void setupBirth(boolean isTrunk, int h)
	{
		setTrunk(isTrunk);
		setHeight(h);
		setBirth();
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(birthTimeWood + GrowTime < TFC_Time.getTotalDays() && height < 3)
			{
				float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(worldObj, TFC_Time.getDayOfYearFromDays(birthTimeWood + GrowTime), xCoord, yCoord, zCoord);
				int t = 1;
				if(temp > 8 && temp < 22)
					t = 2;

				//First we attempt to grow the trunk of the tree higher
				if(height <= 2 && isTrunk && worldObj.rand.nextInt(16/t) == 0 && (
						worldObj.isAirBlock(xCoord, yCoord + 1, zCoord) || 
						worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.fruitTreeLeaves ||
						worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.fruitTreeLeaves2))
				{
					worldObj.setBlock(xCoord, yCoord + 1, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
					((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).setup(true, height + 1, GrowTime);

					setBirthWood(GrowTime);
				}
				//Otherwise we try to grow the branches outward
				else if(height == 2 && isTrunk && worldObj.rand.nextInt(16/t) == 0)
				{
					int r = worldObj.rand.nextInt(4);
					if(r == 0 && BlockFalling.func_149831_e(worldObj, xCoord + 1, yCoord, zCoord) || worldObj.getBlock(xCoord + 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord + 1, yCoord, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord + 1, yCoord, zCoord)).setupBirth(false, height);
					}
					else if(r == 1 && BlockFalling.func_149831_e(worldObj, xCoord, yCoord, zCoord - 1) || worldObj.getBlock(xCoord, yCoord, zCoord - 1) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord, yCoord, zCoord - 1, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord, zCoord - 1)).setupBirth(false, height);
					}
					else if(r == 2 && BlockFalling.func_149831_e(worldObj, xCoord - 1, yCoord, zCoord) || worldObj.getBlock(xCoord - 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord - 1, yCoord, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord - 1, yCoord, zCoord)).setupBirth(false, height);
					}
					else if(r == 3 && BlockFalling.func_149831_e(worldObj, xCoord, yCoord, zCoord + 1) || worldObj.getBlock(xCoord, yCoord, zCoord + 1) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord, yCoord, zCoord + 1, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord, zCoord + 1)).setupBirth(false, height);
					}

					((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord, zCoord)).setBirthWood(branchGrowTime);
				}
			}

			if(birthTimeLeaves + 2 < TFC_Time.getTotalDays() && worldObj.rand.nextInt((int) leafGrowthRate) == 0 && worldObj.getBlock(xCoord, yCoord+2, zCoord) != TFCBlocks.fruitTreeWood)
			{
				int m = meta & 7;
				Block bid = meta < 8 ? TFCBlocks.fruitTreeLeaves : TFCBlocks.fruitTreeLeaves2;

				if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord) && worldObj.isAirBlock(xCoord, yCoord + 2, zCoord) && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord + 1, zCoord))//above
				{
					worldObj.setBlock(xCoord, yCoord + 1, zCoord, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
				}
				else if (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord) && worldObj.isAirBlock(xCoord + 1, yCoord + 1, zCoord) && BlockFruitLeaves.canStay(worldObj, xCoord + 1, yCoord, zCoord))//+x
				{
					worldObj.setBlock(xCoord + 1, yCoord, zCoord, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord);
				}
				else if (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord) && worldObj.isAirBlock(xCoord - 1, yCoord + 1, zCoord) && BlockFruitLeaves.canStay(worldObj, xCoord - 1, yCoord, zCoord))//-x
				{
					worldObj.setBlock(xCoord - 1, yCoord, zCoord, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord);
				}
				else if (worldObj.isAirBlock(xCoord, yCoord, zCoord + 1) && worldObj.isAirBlock(xCoord, yCoord + 1, zCoord + 1) && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord, zCoord + 1))//+z
				{
					worldObj.setBlock(xCoord, yCoord, zCoord + 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord + 1);
				}
				else if (worldObj.isAirBlock(xCoord, yCoord, zCoord - 1) && worldObj.isAirBlock(xCoord, yCoord + 1, zCoord - 1) && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord, zCoord - 1))//-z
				{
					worldObj.setBlock(xCoord, yCoord, zCoord - 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord - 1);
				}
				else if (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord - 1) && worldObj.isAirBlock(xCoord + 1, yCoord + 1, zCoord - 1) && BlockFruitLeaves.canStay(worldObj, xCoord + 1, yCoord, zCoord - 1))//+x/-z
				{
					worldObj.setBlock(xCoord + 1, yCoord, zCoord - 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord - 1);
				}
				else if (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord + 1) && worldObj.isAirBlock(xCoord + 1, yCoord + 1, zCoord + 1) && BlockFruitLeaves.canStay(worldObj, xCoord + 1, yCoord, zCoord + 1))//+x/+z
				{
					worldObj.setBlock(xCoord + 1, yCoord, zCoord + 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord + 1);
				}
				else if (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord - 1) && worldObj.isAirBlock(xCoord - 1, yCoord + 1, zCoord - 1) && BlockFruitLeaves.canStay(worldObj, xCoord - 1, yCoord, zCoord - 1))//-x/-z
				{
					worldObj.setBlock(xCoord - 1, yCoord, zCoord - 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord - 1);
				}
				else if (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord + 1) && worldObj.isAirBlock(xCoord - 1, yCoord + 1, zCoord + 1) && BlockFruitLeaves.canStay(worldObj, xCoord - 1, yCoord, zCoord + 1))//-x/+z
				{
					worldObj.setBlock(xCoord - 1, yCoord, zCoord + 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord + 1);
				}
				setBirthLeaves(2);
			}
		}
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public String getInventoryName()
	{
		return "Fruit Tree Wood";
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		birthTimeWood = nbttagcompound.getLong("birthTime");
		birthTimeLeaves = nbttagcompound.getLong("birthTimeLeaves");
		isTrunk = nbttagcompound.getBoolean("isTrunk");
		height = nbttagcompound.getInteger("height");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setLong("birthTime", birthTimeWood);
		nbttagcompound.setLong("birthTimeLeaves", birthTimeLeaves);
		nbttagcompound.setBoolean("isTrunk", isTrunk);
		nbttagcompound.setInteger("height", height);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
		//TEFruitTreeWood pile = this;
	}

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}
}
