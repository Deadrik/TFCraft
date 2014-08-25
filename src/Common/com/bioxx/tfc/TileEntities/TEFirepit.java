package com.bioxx.tfc.TileEntities;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Vector3f;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumWoodMaterial;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TEFirepit extends TEFireEntity implements IInventory
{
	public ItemStack fireItemStacks[];

	private int externalFireCheckTimer;
	private int externalWoodCount;
	private int oldWoodCount;
	private boolean logPileChecked;
	public int charcoalCounter;
	public boolean hasCookingPot;
	private Map<Integer, int[]> smokeMap = new HashMap<Integer, int[]>(); // Coords where smoke will be generated
	private boolean scanSmokeLayer;
	public final int FIREBURNTIME = (int) ((TFC_Time.hourLength * TFCOptions.charcoalPitBurnTime) / 100);//default 18 hours

	public TEFirepit()
	{
		fuelTimeLeft = 375;
		fuelBurnTemp =  613;
		fireTemp = 350;
		maxFireTempScale = 2000;
		fireItemStacks = new ItemStack[11];
		externalFireCheckTimer = 0;
		externalWoodCount = 0;
		oldWoodCount = 0;
		charcoalCounter = 0;
		hasCookingPot = true;
		scanSmokeLayer = true;
	}

	@Override
	public void closeInventory()
	{
	}

	public void combineMetals(ItemStack InputItem, ItemStack DestItem)
	{
		int D1 = 100 - InputItem.getItemDamage();
		int D2 = 100 - DestItem.getItemDamage();
		//This was causing the infinite amounts possibly
		DestItem.setItemDamage(100 - (D1 + D2));
	}

	public void CookItem()
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random R = new Random();
		if(fireItemStacks[1] != null)
		{
			HeatIndex index = manager.findMatchingIndex(fireItemStacks[1]);
			if(index != null && TFC_ItemHeat.GetTemp(fireItemStacks[1]) > index.meltTemp)
			{
				float temp = TFC_ItemHeat.GetTemp(fireItemStacks[1]);
				ItemStack output = index.getOutput(fireItemStacks[1], R);
				int damage = output.getItemDamage();
				if(output.getItem() == fireItemStacks[1].getItem())
					damage = fireItemStacks[1].getItemDamage();
				ItemStack mold = null;

				//If the input is unshaped metal
				if(fireItemStacks[1].getItem() instanceof ItemMeltedMetal)
				{
					//if both output slots are empty then just lower the input item into the first output slot
					if(fireItemStacks[7] == null && fireItemStacks[8] == null)
					{
						fireItemStacks[7] = fireItemStacks[1].copy();
						fireItemStacks[1] = null;
						return;
					}
					//Otherwise if the first output has an item that doesnt match the input item then put the item in the second output slot
					else if(fireItemStacks[7] != null && fireItemStacks[7].getItem() != TFCItems.CeramicMold && 
							(fireItemStacks[7].getItem() != fireItemStacks[1].getItem() || fireItemStacks[7].getItemDamage() == 0))
					{
						if(fireItemStacks[8] == null)
						{
							fireItemStacks[8] = fireItemStacks[1].copy();
							fireItemStacks[1] = null;
							return;
						}
					}
					mold = new ItemStack(TFCItems.CeramicMold, 1);
					mold.stackSize = 1;
					mold.setItemDamage(1);
				}
				//Morph the input
				fireItemStacks[1] = index.getMorph();
				if(fireItemStacks[1] != null && manager.findMatchingIndex(fireItemStacks[1]) != null)
				{
					//if the input is a new item, then apply the old temperature to it
					TFC_ItemHeat.SetTemp(fireItemStacks[1], temp);
				}

				//Check if we should combine the output with a pre-existing output
				if(output != null && output.getItem() instanceof ItemMeltedMetal)
				{
					int leftover = 0;
					boolean addLeftover = false;
					int fromSide = 0;
					if(fireItemStacks[7] != null && output != null && output.getItem() == fireItemStacks[7].getItem() && fireItemStacks[7].getItemDamage() > 0)
					{
						int amt1 = 100 - damage;//the percentage of the output
						int amt2 = 100 - fireItemStacks[7].getItemDamage();//the percentage currently in the out slot
						int amt3 = amt1 + amt2;//combined amount
						leftover = amt3 - 100;//assign the leftover so that we can add to the other slot if applicable
						if(leftover > 0)
							addLeftover = true;
						int amt4 = 100 - amt3;//convert the percent back to mc damage
						if(amt4 < 0)
							amt4 = 0;//stop the infinite glitch
						fireItemStacks[7] = output.copy();
						fireItemStacks[7].setItemDamage(amt4);

						TFC_ItemHeat.SetTemp(fireItemStacks[7], temp);

						if(fireItemStacks[1] == null && mold != null)
							fireItemStacks[1] = mold;
					}
					else if(fireItemStacks[8] != null && output != null && output.getItem() == fireItemStacks[8].getItem() && fireItemStacks[8].getItemDamage() > 0)
					{
						int amt1 = 100 - damage;//the percentage of the output
						int amt2 = 100 - fireItemStacks[8].getItemDamage();//the percentage currently in the out slot
						int amt3 = amt1 + amt2;//combined amount
						leftover = amt3 - 100;//assign the leftover so that we can add to the other slot if applicable
						if(leftover > 0)
							addLeftover = true;
						fromSide = 1;
						int amt4 = 100 - amt3;//convert the percent back to mc damage
						if(amt4 < 0)
							amt4 = 0;//stop the infinite glitch
						fireItemStacks[8] = output.copy();
						fireItemStacks[8].setItemDamage(amt4);

						TFC_ItemHeat.SetTemp(fireItemStacks[8], temp);

						if(fireItemStacks[1] == null && mold != null)
							fireItemStacks[1] = mold;
					}
					else if(output != null && fireItemStacks[7] != null && fireItemStacks[7].getItem() == TFCItems.CeramicMold)
					{
						fireItemStacks[7] = output.copy();
						fireItemStacks[7].setItemDamage(damage);

						TFC_ItemHeat.SetTemp(fireItemStacks[7], temp);
					}
					else if(output != null && fireItemStacks[8] != null && fireItemStacks[8].getItem() == TFCItems.CeramicMold)
					{
						fireItemStacks[8] = output.copy();
						fireItemStacks[8].setItemDamage(damage);

						TFC_ItemHeat.SetTemp(fireItemStacks[8], temp);
					}

					if(addLeftover)
					{
						int dest = fromSide == 1 ? 7 : 8;
						if(fireItemStacks[dest] != null && output.getItem() == fireItemStacks[dest].getItem() && fireItemStacks[dest].getItemDamage() > 0)
						{
							int amt1 = 100 - leftover;//the percentage of the output
							int amt2 = 100 - fireItemStacks[dest].getItemDamage();//the percentage currently in the out slot
							int amt3 = amt1 + amt2;//combined amount
							int amt4 = 100 - amt3;//convert the percent back to mc damage
							if(amt4 < 0)
								amt4 = 0;//stop the infinite glitch
							fireItemStacks[dest] = output.copy();
							fireItemStacks[dest].setItemDamage(amt4);

							TFC_ItemHeat.SetTemp(fireItemStacks[dest], temp);
						}
						else if(fireItemStacks[dest] != null && fireItemStacks[dest].getItem() == TFCItems.CeramicMold)
						{
							fireItemStacks[dest] = output.copy();
							fireItemStacks[dest].setItemDamage(100 - leftover);
							TFC_ItemHeat.SetTemp(fireItemStacks[dest], temp);
						}
					}
				}
				else
				{
					if(fireItemStacks[7] != null && 
							fireItemStacks[7].getItem() == output.getItem() && 
							fireItemStacks[7].stackSize + output.stackSize <= fireItemStacks[7].getMaxStackSize())
					{
						fireItemStacks[7].stackSize += output.stackSize; 
					}
					else if(fireItemStacks[8] != null && 
							fireItemStacks[8].getItem() == output.getItem() && 
							fireItemStacks[8].stackSize + output.stackSize <= fireItemStacks[8].getMaxStackSize())
					{
						fireItemStacks[8].stackSize += output.stackSize; 
					}
					else if(fireItemStacks[7] == null)
					{
						fireItemStacks[7] = output.copy();
					}
					else if(fireItemStacks[8] == null)
					{
						fireItemStacks[8] = output.copy();
					}
					else if((fireItemStacks[7].stackSize == fireItemStacks[7].getMaxStackSize() && fireItemStacks[8].stackSize == fireItemStacks[8].getMaxStackSize())
							|| (fireItemStacks[7].getItem() != output.getItem() && fireItemStacks[8].getItem() != output.getItem())
							|| (fireItemStacks[7].stackSize == fireItemStacks[7].getMaxStackSize() && fireItemStacks[8].getItem() != output.getItem())
							|| (fireItemStacks[7].getItem() != output.getItem() && fireItemStacks[8].stackSize == fireItemStacks[8].getMaxStackSize()))
					{
						fireItemStacks[1] = output.copy();
					}
				}
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if(fireItemStacks[slot] != null)
		{
			if(fireItemStacks[slot].stackSize <= amount)
			{
				ItemStack itemstack = fireItemStacks[slot];
				fireItemStacks[slot] = null;
				return itemstack;
			}
			ItemStack itemstack1 = fireItemStacks[slot].splitStack(amount);
			if(fireItemStacks[slot].stackSize == 0)
				fireItemStacks[slot] = null;
			return itemstack1;
		}
		else
			return null;
	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 0.8F + 0.3F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(fireItemStacks[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, fireItemStacks[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
				fireItemStacks[i] = null;
			}
		}
	}

	public void externalFireCheck()
	{
		if(externalFireCheckTimer == 0)
		{
			if(!logPileChecked)
			{
				logPileChecked = true;
				oldWoodCount = externalWoodCount;
				externalWoodCount = 0;
				ProcessPile(xCoord, yCoord, zCoord, false);
				if(oldWoodCount != externalWoodCount)
					charcoalCounter = 0;
			}

			//This is where we handle the counter for producing charcoal.
			if(charcoalCounter == 0)
				charcoalCounter = (int) TFC_Time.getTotalTicks();

			if(charcoalCounter > 0 && charcoalCounter + (FIREBURNTIME * 100) < TFC_Time.getTotalTicks())
			{
				logPileChecked = false;
				charcoalCounter = 0;
				ProcessPile(xCoord, yCoord, zCoord, true);
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
	}

	private void ProcessPile(int i, int j, int k, boolean empty)
	{
		int x = i;
		int y = 0;
		int z = k;
		boolean checkArray[][][] = new boolean[25][13][25];
		boolean reachedTop = false;

		while(!reachedTop && j + y >= 0 && y < 13)
		{
			if(worldObj.getBlock(x, j + y + 1, z) != TFCBlocks.LogPile)
				reachedTop = true;
			checkOut(i, j + y, k, empty);
			scanLogs(i, j + y, k, checkArray, (byte)12, (byte)y, (byte)12, empty, false);
			y++;
		}
	}

	private boolean checkOut(int x, int y, int z, boolean empty)
	{
		if(worldObj.getBlock(x, y, z) == TFCBlocks.LogPile)
		{
			TELogPile logPile = (TELogPile)worldObj.getTileEntity(x, y, z);

			int count = 0;
			if(logPile != null)
			{
				if(!empty)
				{
					Block block;
					Queue<Vector3f> blocksOnFire = new ArrayDeque<Vector3f>();

					block = worldObj.getBlock(x + 1, y, z);
					if(!TFC_Core.isValidCharcoalPitCover(block))
						blocksOnFire.add(new Vector3f(x + 1, y, z));

					block = worldObj.getBlock(x - 1, y, z);
					if(!TFC_Core.isValidCharcoalPitCover(block))
						blocksOnFire.add(new Vector3f(x - 1, y, z));

					block = worldObj.getBlock(x, y, z + 1);
					if(!TFC_Core.isValidCharcoalPitCover(block))
						blocksOnFire.add(new Vector3f(x, y, z + 1));

					block = worldObj.getBlock(x, y, z - 1);
					if(!TFC_Core.isValidCharcoalPitCover(block))
						blocksOnFire.add(new Vector3f(x, y, z - 1));

					block = worldObj.getBlock(x, y + 1, z);
					if(!TFC_Core.isValidCharcoalPitCover(block))
						blocksOnFire.add(new Vector3f(x, y + 1, z));

					block = worldObj.getBlock(x, y - 1, z);
					if(!TFC_Core.isValidCharcoalPitCover(block))
						blocksOnFire.add(new Vector3f(x, y - 1, z));

					logPile.blocksToBeSetOnFire = blocksOnFire;
					logPile.setCharcoalFirepit(this);
				}
				else
					logPile.setCharcoalFirepit(null);

				if(logPile.storage[0] != null)
				{
					if(!empty)
						externalWoodCount += logPile.storage[0].stackSize;
					else
					{
						count += logPile.storage[0].stackSize;
						logPile.storage[0] = null;
					}
				}
				if(logPile.storage[1] != null)
				{
					if(!empty)
						externalWoodCount += logPile.storage[1].stackSize;
					else
					{
						count += logPile.storage[1].stackSize;
						logPile.storage[1] = null;
					}
				}
				if(logPile.storage[2] != null)
				{
					if(!empty)
						externalWoodCount += logPile.storage[2].stackSize;
					else
					{
						count += logPile.storage[2].stackSize;
						logPile.storage[2] = null;
					}
				}
				if(logPile.storage[3] != null)
				{
					if(!empty)
						externalWoodCount += logPile.storage[3].stackSize;
					else
					{
						count += logPile.storage[3].stackSize;
						logPile.storage[3] = null;
					}
				}
			}

			if(empty)
			{
				float percent = 25 + worldObj.rand.nextInt(25);
				count = (int) (count * (percent / 100));
				worldObj.setBlock(x, y, z, TFCBlocks.Charcoal, count, 0x2);
				/* Trick to make the block fall or start the combining "chain" with other blocks.
				 * We don't notify the bottom block because it may be air so this block won't fall */
				worldObj.notifyBlockOfNeighborChange(x, y, z, TFCBlocks.Charcoal);
			}
			return true;
		}
		return false;
	}

	private void scanLogs(int i, int j, int k, boolean[][][] checkArray, byte x, byte y, byte z, boolean empty, boolean addSmoke)
	{
		if(y >= 0)
		{
			checkArray[x][y][z] = true;
			int offsetX = 0;int offsetZ = 0;
			for (offsetX = -1; offsetX <= 1; offsetX++)
			{
				for (offsetZ = -1; offsetZ <= 1; offsetZ++)
				{
					if(x + offsetX < 25 && x + offsetX >= 0 && z + offsetZ < 25 && z + offsetZ >= 0 && y < 13 && y >= 0)
					{
						if(!checkArray[x + offsetX][y][z + offsetZ] && checkOut(i + offsetX, j, k + offsetZ, empty))
						{
							scanLogs(i + offsetX, j, k + offsetZ, checkArray, (byte)(x + offsetX), y, (byte)(z + offsetZ), empty, addSmoke);
							if(addSmoke)
							{
								if(worldObj.getBlock(i + offsetX, j, k + offsetZ) == TFCBlocks.LogPile && worldObj.getBlock(i + offsetX, j + 2, k + offsetZ) == Blocks.air)
									smokeMap.put(smokeMap.size(), new int[] {i + offsetX, j + 2, k + offsetZ});
							}
						}
					}
				}
			}
		}
	}

	public void logPileUpdate(int woodChanges)
	{
		oldWoodCount = externalWoodCount;
		externalWoodCount += woodChanges;
		if(oldWoodCount != externalWoodCount)
			charcoalCounter = 0;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Firepit";
	}

	public float getOutput1Temp()
	{
		return TFC_ItemHeat.GetTemp(fireItemStacks[7]);
	}

	public float getOutput2Temp()
	{
		return TFC_ItemHeat.GetTemp(fireItemStacks[8]);
	}

	@Override
	public int getSizeInventory()
	{
		return fireItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return fireItemStacks[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}

	public int getSurroundedByWood(int x, int y, int z)
	{
		int count = 0;
		if(worldObj.getBlock(x + 1, y, z).getMaterial() == Material.wood)
			count++;
		if(worldObj.getBlock(x - 1, y, z).getMaterial() == Material.wood)
			count++;
		if(worldObj.getBlock(x, y + 1, z).getMaterial() == Material.wood)
			count++;
		if(worldObj.getBlock(x, y, z + 1).getMaterial() == Material.wood)
			count++;
		if(worldObj.getBlock(x, y, z - 1).getMaterial() == Material.wood)
			count++;
		return count;
	}

	public void HandleFuelStack()
	{
		if(fireItemStacks[3] == null && fireItemStacks[0] != null)
		{
			fireItemStacks[3] = fireItemStacks[0];
			fireItemStacks[0] = null;
		}
		if(fireItemStacks[4] == null && fireItemStacks[3] != null)
		{
			fireItemStacks[4] = fireItemStacks[3];
			fireItemStacks[3] = null;
		}
		if(fireItemStacks[5] == null && fireItemStacks[4] != null)
		{
			fireItemStacks[5] = fireItemStacks[4];
			fireItemStacks[4] = null;
		}
		else if(fireItemStacks[5] == null && fireItemStacks[4] == null)
		{
			if(charcoalCounter > 0 && fireTemp < 50 && worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.LogPile)
			{
				TELogPile logPile = (TELogPile)worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
				if(logPile.getStackInSlot(0) != null)
					fireItemStacks[5] = logPile.takeLog(0);
				else if(logPile.getStackInSlot(1) != null)
					fireItemStacks[5] = logPile.takeLog(1);
				else if(logPile.getStackInSlot(2) != null)
					fireItemStacks[5] = logPile.takeLog(2);
				else if(logPile.getStackInSlot(3) != null)
					fireItemStacks[5] = logPile.takeLog(3);
			}
		}
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
	public void setInventorySlotContents(int slot, ItemStack is)
	{
		fireItemStacks[slot] = is;
		if(is != null && is.stackSize > getInventoryStackLimit())
			is.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		if(fireTemp > 1 && worldObj.getBlock(xCoord, yCoord + 1, zCoord) == TFCBlocks.LogPile)
		{
			if(externalFireCheckTimer <= 0)
			{
				if(!worldObj.isRemote)
					externalFireCheck();
				externalFireCheckTimer = 100;
			}
			externalFireCheckTimer--;

			if(worldObj.rand.nextInt(5) == 0 && worldObj.isRemote)
				GenerateSmoke();
		}
		else
		{
			charcoalCounter = 0;
			logPileChecked = false;
		}

		if(!worldObj.isRemote && charcoalCounter == 0)
		{
			//Here we take care of the item that we are cooking in the fire
			careForInventorySlot(fireItemStacks[1]);
			careForInventorySlot(fireItemStacks[7]);
			careForInventorySlot(fireItemStacks[8]);

			hasCookingPot = (fireItemStacks[1] != null && fireItemStacks[1].getItem() == TFCItems.PotteryPot && 
					fireItemStacks[1].getItemDamage() == 1);

			//Now we cook the input item
			CookItem();

			//push the input fuel down the stack
			HandleFuelStack();

			if((fireTemp < 1) && (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0))
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else if((fireTemp >= 1) && (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1))
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			int Surrounded = getSurroundedByWood(xCoord, yCoord, zCoord);
			//If the fire is still burning and has fuel
			if(fuelTimeLeft > 0 && fireTemp >= 1 && Surrounded != 5)
			{
				if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2)
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			else if(fuelTimeLeft <= 0 && fireTemp >= 1 && fireItemStacks[5] != null &&
					(!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || !worldObj.isRaining()))
			{
				if(fireItemStacks[5] != null)
				{
					EnumWoodMaterial m = TFC_Core.getWoodMaterial(fireItemStacks[5]);
					fireItemStacks[5] = null;
					fuelTimeLeft = m.burnTimeMax;
					fuelBurnTemp = m.burnTempMax;
				}
			}

			//Calculate the fire temp
			float desiredTemp = 0;
			if(Surrounded != 5)
				desiredTemp = handleTemp();
			else
				desiredTemp = 1000;

			handleTempFlux(desiredTemp);

			//Here we handle the bellows
			handleAirReduction();

			//do a last minute check to verify stack size
			if(fireItemStacks[7] != null)
			{
				if(fireItemStacks[7].stackSize <= 0)
					fireItemStacks[7].stackSize = 1;
			}

			if(fireItemStacks[8] != null)
			{
				if(fireItemStacks[8].stackSize <= 0)
					fireItemStacks[8].stackSize = 1;
			}

			if(fuelTimeLeft <= 0)
				TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		charcoalCounter = nbttagcompound.getInteger("charcoalCounter");
		externalWoodCount = nbttagcompound.getInteger("externalWoodCount");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		fireItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < fireItemStacks.length)
				fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("charcoalCounter", charcoalCounter);
		nbttagcompound.setInteger("externalWoodCount", externalWoodCount);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < fireItemStacks.length; i++)
		{
			if(fireItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				fireItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is)
	{
		return false;
	}

	public boolean isInactiveCharcoalFirepit()
	{
		return logPileChecked == false && charcoalCounter == 0;
	}

	@SideOnly(Side.CLIENT)
	public void GenerateSmoke()
	{
		if(scanSmokeLayer)
		{
			int y = 0;
			int topY = yCoord;
			boolean checkArray[][][] = new boolean[25][13][25];
			while(worldObj.getBlock(xCoord, topY + 1, zCoord) == TFCBlocks.LogPile)
			{
				scanLogs(xCoord, topY, zCoord, checkArray, (byte)12, (byte)y, (byte)12, false, true);
				++topY;
				++y;
			}
			scanLogs(xCoord, topY, zCoord, checkArray, (byte)12, (byte)y, (byte)12, false, true);
			smokeMap.put(smokeMap.size(), new int[] {xCoord, topY + 2, zCoord});
			scanSmokeLayer = false;
		}

		if(smokeMap.size() > 0)
		{
			int[] sb = smokeMap.get(worldObj.rand.nextInt(smokeMap.size()));
			float f = sb[0] + 0.5F;
			float f1 = sb[1] + 0.1F + worldObj.rand.nextFloat() * 6F / 16F;
			float f2 = sb[2] + 0.5F;
			float f4 = worldObj.rand.nextFloat() * 0.6F;
			float f5 = worldObj.rand.nextFloat() * -0.6F;
			worldObj.spawnParticle("smoke", f+f4 - 0.3F, f1, f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("smoke", f+f4 - 0.2F, f1, f2 + f5 + 0.4F, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("smoke", f+f4 - 0.1F, f1, f2 + f5 + 0.1F, 0.0D, 0.0D, 0.0D);
			if(worldObj.rand.nextInt(10) == 0) worldObj.spawnParticle("largesmoke", f+f4 - 0.2F, f1, f2 + f5 + 0.2F, 0.0D, 0.0D, 0.0D);
		}
	}
}
