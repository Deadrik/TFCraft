package com.bioxx.tfc.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Blocks.Flora.BlockFruitWood;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.api.TFCBlocks;

public class TEFruitTreeWood extends TileEntity implements IInventory
{
	public boolean isTrunk;
	public int height;
	public long birthTimeWood;
	public long birthTimeLeaves;
	private static final long LEAF_GROWTH_RATE = 20;
	private static final long TRUNK_GROW_TIME = (long) (1.5F * TFC_Time.daysInMonth);
	private static final long BRANCH_GROW_TIME = TFC_Time.daysInMonth;

	public TEFruitTreeWood()
	{
		height = 0;
		isTrunk = false;
		birthTimeWood = 0;
		birthTimeLeaves = 0;
	}

	public void initBirth()
	{
		birthTimeWood = TFC_Time.getTotalDays();
		birthTimeLeaves = TFC_Time.getTotalDays();
	}

	public void setBirthWood(long t)
	{
		birthTimeWood = t;
	}

	public void increaseBirthWood(long t)
	{
		birthTimeWood += t;
	}

	public void setBirthLeaves(long t)
	{
		birthTimeLeaves = t;
	}
	public void increaseBirthLeaves(long t)
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

	public void setupBirth(boolean isTrunk, int h, long woodBirth, long leafBirth)
	{
		setTrunk(isTrunk);
		setHeight(h);
		initBirth();
		setBirthWood(woodBirth);
		setBirthLeaves(leafBirth);
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(BlockFruitWood.getType(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)));

			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			float temp = TFC_Climate.getHeightAdjustedTemp(worldObj, xCoord, yCoord, zCoord);

			int month = TFC_Time.getSeasonAdjustedMonth(zCoord); // 0 is Early Spring, 3 is Early Summer, 6 is Early Autumn
			if (month < 9 && fi != null && temp >= fi.minTemp && temp < fi.maxTemp) // Will not grow during winter months or out of temp range
			{
				int t = 1;
				if (month < 3) // Twice as likely to grow in the spring
					t = 2;

				//First we attempt to grow the trunk of the tree higher
				if (birthTimeWood + TRUNK_GROW_TIME < TFC_Time.getTotalDays() && height < 3 && isTrunk && worldObj.rand.nextInt(16 / t) == 0 &&
					(worldObj.isAirBlock(xCoord, yCoord + 1, zCoord) || worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.fruitTreeLeaves ||
						worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.fruitTreeLeaves2))
				{
					worldObj.setBlock(xCoord, yCoord + 1, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
					if (worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof TEFruitTreeWood)
					{
						TEFruitTreeWood trunkTE = ((TEFruitTreeWood) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord));
						trunkTE.setupBirth(true, height + 1, birthTimeWood + TRUNK_GROW_TIME, birthTimeLeaves);

						this.increaseBirthWood(TRUNK_GROW_TIME);
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
				//Otherwise we try to grow the branches outward
				else if (birthTimeWood + BRANCH_GROW_TIME < TFC_Time.getTotalDays() && height == 2 && isTrunk && worldObj.rand.nextInt(16 / t) == 0 &&
						worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.fruitTreeWood) // Only grow branches if the tree is full height
				{
					int r = worldObj.rand.nextInt(4);
					if (r == 0 && worldObj.blockExists(xCoord + 1, yCoord, zCoord) && (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord) ||
						worldObj.getBlock(xCoord + 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves || worldObj.getBlock(xCoord + 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves2))
					{
						worldObj.setBlock(xCoord + 1, yCoord, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
						if (worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = ((TEFruitTreeWood) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord));
							branchTE.setupBirth(false, height, birthTimeWood + BRANCH_GROW_TIME, birthTimeLeaves);
						}
					}
					else if (r == 1 && worldObj.blockExists(xCoord, yCoord, zCoord - 1) && (worldObj.isAirBlock(xCoord, yCoord, zCoord - 1) ||
							worldObj.getBlock(xCoord, yCoord, zCoord - 1) == TFCBlocks.fruitTreeLeaves || worldObj.getBlock(xCoord, yCoord, zCoord - 1) == TFCBlocks.fruitTreeLeaves2))
					{
						worldObj.setBlock(xCoord, yCoord, zCoord - 1, TFCBlocks.fruitTreeWood, meta, 0x2);
						if (worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = ((TEFruitTreeWood) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1));
							branchTE.setupBirth(false, height, birthTimeWood + BRANCH_GROW_TIME, birthTimeLeaves);
						}
					}
					else if (r == 2 && worldObj.blockExists(xCoord - 1, yCoord, zCoord) && (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord) ||
							worldObj.getBlock(xCoord - 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves || worldObj.getBlock(xCoord - 1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves2))
					{
						worldObj.setBlock(xCoord - 1, yCoord, zCoord, TFCBlocks.fruitTreeWood, meta, 0x2);
						if (worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = ((TEFruitTreeWood) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord));
							branchTE.setupBirth(false, height, birthTimeWood + BRANCH_GROW_TIME, birthTimeLeaves);
						}
					}
					else if (r == 3 && worldObj.blockExists(xCoord, yCoord, zCoord + 1) && (worldObj.isAirBlock(xCoord, yCoord, zCoord + 1) ||
							worldObj.getBlock(xCoord, yCoord, zCoord + 1) == TFCBlocks.fruitTreeLeaves || worldObj.getBlock(xCoord, yCoord, zCoord + 1) == TFCBlocks.fruitTreeLeaves2))
					{
						worldObj.setBlock(xCoord, yCoord, zCoord + 1, TFCBlocks.fruitTreeWood, meta, 0x2);
						if (worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = ((TEFruitTreeWood) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1));
							branchTE.setupBirth(false, height, birthTimeWood + BRANCH_GROW_TIME, birthTimeLeaves);
						}
					}

					this.increaseBirthWood(BRANCH_GROW_TIME);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}

				if (birthTimeLeaves + 2 < TFC_Time.getTotalDays() && worldObj.rand.nextInt((int) LEAF_GROWTH_RATE) == 0 && worldObj.getBlock(xCoord, yCoord + 2, zCoord) != TFCBlocks.fruitTreeWood)
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

					this.increaseBirthLeaves(2);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}

	private boolean checkLeaves(int xCoord, int yCoord, int zCoord)
	{
		return worldObj.blockExists(xCoord, yCoord, zCoord) && worldObj.isAirBlock(xCoord, yCoord, zCoord) &&
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
