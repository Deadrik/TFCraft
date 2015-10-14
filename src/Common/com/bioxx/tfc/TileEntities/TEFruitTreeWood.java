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
	private static final long LEAF_GROWTH_RATE = 3;
	private static final long GROW_TIME = 30;
	private static final long BRANCH_GROW_TIME = 20;

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
			if(birthTimeWood + GROW_TIME < TFC_Time.getTotalDays() && height < 3)
			{
				float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(worldObj, TFC_Time.getDayOfYearFromDays(birthTimeWood + GROW_TIME), xCoord, yCoord, zCoord);
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
					((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).setup(true, height + 1, GROW_TIME);

					setBirthWood(GROW_TIME);
				}
				//Otherwise we try to grow the branches outward
				else if(height == 2 && isTrunk && worldObj.rand.nextInt(16/t) == 0)
				{
					int r = worldObj.rand.nextInt(4);
					if (r == 0 && worldObj.blockExists(xCoord + 1, yCoord, zCoord) && BlockFalling.func_149831_e(worldObj, xCoord + 1, yCoord, zCoord) ||
						worldObj.getBlock(xCoord + 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord + 1, yCoord, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord + 1, yCoord, zCoord)).setupBirth(false, height);
					}
					else if (r == 1 && worldObj.blockExists(xCoord, yCoord, zCoord - 1) && BlockFalling.func_149831_e(worldObj, xCoord, yCoord, zCoord - 1) ||
								worldObj.getBlock(xCoord, yCoord, zCoord - 1) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord, yCoord, zCoord - 1, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord, zCoord - 1)).setupBirth(false, height);
					}
					else if (r == 2 && worldObj.blockExists(xCoord - 1, yCoord, zCoord) && BlockFalling.func_149831_e(worldObj, xCoord - 1, yCoord, zCoord) ||
								worldObj.getBlock(xCoord - 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord - 1, yCoord, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord - 1, yCoord, zCoord)).setupBirth(false, height);
					}
					else if (r == 3 && worldObj.blockExists(xCoord, yCoord, zCoord + 1) && BlockFalling.func_149831_e(worldObj, xCoord, yCoord, zCoord + 1) ||
								worldObj.getBlock(xCoord, yCoord, zCoord + 1) == TFCBlocks.fruitTreeLeaves)
					{
						worldObj.setBlock(xCoord, yCoord, zCoord + 1, TFCBlocks.fruitTreeWood, meta, 0x2);
						((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord, zCoord + 1)).setupBirth(false, height);
					}

					((TEFruitTreeWood)worldObj.getTileEntity(xCoord, yCoord, zCoord)).setBirthWood(BRANCH_GROW_TIME);
				}
			}

			if(birthTimeLeaves + 2 < TFC_Time.getTotalDays() && worldObj.rand.nextInt((int) LEAF_GROWTH_RATE) == 0 && worldObj.getBlock(xCoord, yCoord+2, zCoord) != TFCBlocks.fruitTreeWood)
			{
				int m = meta & 7;
				Block bid = meta < 8 ? TFCBlocks.fruitTreeLeaves : TFCBlocks.fruitTreeLeaves2;

				if (checkLeaves(xCoord, yCoord + 1, zCoord)) //above
				{
					worldObj.setBlock(xCoord, yCoord + 1, zCoord, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
				}
				else if (checkLeaves(xCoord + 1, yCoord, zCoord)) //+x
				{
					worldObj.setBlock(xCoord + 1, yCoord, zCoord, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord);
				}
				else if (checkLeaves(xCoord - 1, yCoord, zCoord)) //-x
				{
					worldObj.setBlock(xCoord - 1, yCoord, zCoord, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord);
				}
				else if (checkLeaves(xCoord, yCoord, zCoord + 1)) //+z
				{
					worldObj.setBlock(xCoord, yCoord, zCoord + 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord + 1);
				}
				else if (checkLeaves(xCoord, yCoord, zCoord - 1)) //-z
				{
					worldObj.setBlock(xCoord, yCoord, zCoord - 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord - 1);
				}
				else if (checkLeaves(xCoord + 1, yCoord, zCoord - 1)) //+x/-z
				{
					worldObj.setBlock(xCoord + 1, yCoord, zCoord - 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord - 1);
				}
				else if (checkLeaves(xCoord + 1, yCoord, zCoord + 1)) //+x/+z
				{
					worldObj.setBlock(xCoord + 1, yCoord, zCoord + 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord + 1);
				}
				else if (checkLeaves(xCoord - 1, yCoord, zCoord - 1)) //-x/-z
				{
					worldObj.setBlock(xCoord - 1, yCoord, zCoord - 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord - 1);
				}
				else if (checkLeaves(xCoord - 1, yCoord, zCoord + 1)) //-x/+z
				{
					worldObj.setBlock(xCoord - 1, yCoord, zCoord + 1, bid, m, 0x2);
					worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord + 1);
				}
				setBirthLeaves(2);
			}
		}
	}

	private boolean checkLeaves(int xCoord, int yCoord, int zCoord)
	{
		return worldObj.getChunkProvider().chunkExists(xCoord >> 4, zCoord >> 4) &&worldObj.isAirBlock(xCoord, yCoord, zCoord) &&
				worldObj.isAirBlock(xCoord, yCoord + 1, zCoord) && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord, zCoord);
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
