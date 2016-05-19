package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class TEForge extends TEFireEntity implements IInventory
{
	public boolean isSmokeStackValid;
	public ItemStack fireItemStacks[];
	private int smokeTimer;

	public TEForge()
	{
		super();
		fuelTimeLeft = 200;
		fuelBurnTemp = 200;
		fireTemp = 20;
		isSmokeStackValid = false;
		fireItemStacks = new ItemStack[14];
		maxFireTempScale = 2500;
	}

	private boolean validateSmokeStack()
	{
		if (!TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord))
			return true;
		else if (checkChimney(xCoord + 1, yCoord + 1, zCoord))
			return true;
		else if (checkChimney(xCoord - 1, yCoord + 1, zCoord))
			return true;
		else if (checkChimney(xCoord, yCoord + 1, zCoord + 1))
			return true;
		else if (checkChimney(xCoord, yCoord + 1, zCoord - 1))
			return true;
		else if (notOpaque(xCoord + 1, yCoord + 1, zCoord) && checkChimney(xCoord + 2, yCoord + 1, zCoord))
			return true;
		else if (notOpaque(xCoord - 1, yCoord + 1, zCoord) && checkChimney(xCoord - 2, yCoord + 1, zCoord))
			return true;
		else if (notOpaque(xCoord, yCoord + 1, zCoord + 1) && checkChimney(xCoord, yCoord + 1, zCoord + 2))
			return true;
		else
			return notOpaque(xCoord, yCoord + 1, zCoord - 1) && checkChimney(xCoord, yCoord + 1, zCoord - 2);
	}

	private boolean checkChimney(int x, int y, int z)
	{
		return notOpaque(x, y, z) && worldObj.canBlockSeeTheSky(x, y, z);
	}

	private boolean notOpaque(int x, int y, int z)
	{
		return worldObj.blockExists(x, y, z) && !worldObj.getBlock(x, y, z).isOpaqueCube();
	}

	private void genSmokeRoot(int x, int y, int z)
	{
		if(fuelTimeLeft >= 0)
		{
			if(worldObj.getBlock(x,y,z) != TFCBlocks.smoke)
				worldObj.setBlock(x, y, z, TFCBlocks.smoke);
		}
		else
		{
			worldObj.setBlockToAir(x, y, z);
		}
	}

	/*private class CoordDirection
	{
		int x; int y; int z; ForgeDirection dir;
		public CoordDirection(int x, int y, int z, ForgeDirection d)
		{
			this.x = x;this.y = y;this.z = z;this.dir = d;
		}
	}*/

	@Override
	public void closeInventory()
	{
	}

	public void combineMetals(ItemStack inputItem, ItemStack destItem)
	{
		int d1 = 100 - inputItem.getItemDamage();
		int d2 = 100 - destItem.getItemDamage();
		destItem.setItemDamage(100 - (d1 + d2));
	}

	public void cookItem(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random r = new Random();
		if(fireItemStacks[i] != null)
		{
			HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
			ItemStack inputCopy = fireItemStacks[i].copy();
			
			if(index != null && TFC_ItemHeat.getTemp(fireItemStacks[i]) > index.meltTemp)
			{
				float temperature = TFC_ItemHeat.getTemp(fireItemStacks[i]);
				//int dam = fireItemStacks[i].getItemDamage();

				// If not unshaped metal, morph the input to the output. If not an input with direct morph (sand, sticks, etc) this deletes the input item from the slot.
				if(!(fireItemStacks[i].getItem() instanceof ItemMeltedMetal))
					fireItemStacks[i] = index.getMorph();

				// Handle items that had a direct morph.
				if(fireItemStacks[i] != null)
				{
					HeatIndex morphIndex = manager.findMatchingIndex(fireItemStacks[i]);
					if(morphIndex != null)
					{
						// Apply old temperature to direct morphs that can continue to be heated.
						TFC_ItemHeat.setTemp(fireItemStacks[i], temperature);
					}
				}
				else if(index.hasOutput())
				{
					ItemStack output = index.getOutput(inputCopy, r);
					if (inputCopy.getItem() instanceof ISmeltable)
					{
						ISmeltable smelt = (ISmeltable)inputCopy.getItem();
						ItemStack meltedItem = new ItemStack(smelt.getMetalType(inputCopy).meltedItem);
						TFC_ItemHeat.setTemp(meltedItem, temperature);

						int units = smelt.getMetalReturnAmount(inputCopy);
						// Raw/Refined Blooms give at max 100 units to force players to split using the anvil
						if (inputCopy.getItem() instanceof ItemBloom)
							units = Math.min(100, units);

						while(units > 0 && getMold() != null)
						{
							ItemStack moldIS = this.getMold();
							ItemStack outputCopy = meltedItem.copy();

							if (units > 100)
							{
								units-= 100;
								moldIS.stackSize--;
								if(!addToStorage(outputCopy.copy()))
								{
									EntityItem ei = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, outputCopy);
									ei.motionX = 0; ei.motionY = 0; ei.motionZ = 0;
									worldObj.spawnEntityInWorld(ei);
								}
							}
							else if (units > 0) // Put the last item in the forge cooking slot, replacing the input.
							{
								outputCopy.setItemDamage(100-units);
								units = 0;
								moldIS.stackSize--;
								fireItemStacks[i] = outputCopy.copy();
							}
						}
					}
					else
					{
						fireItemStacks[i] = output;
					}


					if(TFC_ItemHeat.isCookable(fireItemStacks[i]) > -1)
					{
						//if the input is a new item, then apply the old temperature to it
						TFC_ItemHeat.setTemp(fireItemStacks[i], temperature);
					}
				}
			}
		}
	}

	public boolean addToStorage(ItemStack is)
	{
		if(this.getStackInSlot(10) == null)
		{
			this.setInventorySlotContents(10, is);
			return true;
		}
		if(this.getStackInSlot(11) == null)
		{
			this.setInventorySlotContents(11, is);
			return true;
		}
		if(this.getStackInSlot(12) == null)
		{
			this.setInventorySlotContents(12, is);
			return true;
		}
		if(this.getStackInSlot(13) == null)
		{
			this.setInventorySlotContents(13, is);
			return true;
		}
		return false;
	}

	private ItemStack getMold()
	{
		if(fireItemStacks[10] != null && fireItemStacks[10].getItem() == TFCItems.ceramicMold && fireItemStacks[10].stackSize > 0)
		{
			return fireItemStacks[10];
		}
		else if(fireItemStacks[11] != null && fireItemStacks[11].getItem() == TFCItems.ceramicMold && fireItemStacks[11].stackSize > 0)
		{
			return fireItemStacks[11];
		}
		else if(fireItemStacks[12] != null && fireItemStacks[12].getItem() == TFCItems.ceramicMold && fireItemStacks[12].stackSize > 0)
		{
			return fireItemStacks[12];
		}
		else if(fireItemStacks[13] != null && fireItemStacks[13].getItem() == TFCItems.ceramicMold && fireItemStacks[13].stackSize > 0)
		{
			return fireItemStacks[13];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(fireItemStacks[i] != null)
		{
			if(fireItemStacks[i].stackSize <= j)
			{
				ItemStack is = fireItemStacks[i];
				fireItemStacks[i] = null;
				return is;
			}

			ItemStack isSplit = fireItemStacks[i].splitStack(j);
			if(fireItemStacks[i].stackSize == 0)
				fireItemStacks[i] = null;
			return isSplit;
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
		float f1 = rand.nextFloat() * 0.8F + 0.4F;
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

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Forge";
	}

	public int getMoldIndex()
	{
		if(fireItemStacks[10] != null && fireItemStacks[10].getItem() == TFCItems.ceramicMold)
			return 10;
		if(fireItemStacks[11] != null && fireItemStacks[11].getItem() == TFCItems.ceramicMold)
			return 11;
		if(fireItemStacks[12] != null && fireItemStacks[12].getItem() == TFCItems.ceramicMold)
			return 12;
		if(fireItemStacks[13] != null && fireItemStacks[13].getItem() == TFCItems.ceramicMold)
			return 13;
		return -1;
	}

	@Override
	public int getSizeInventory()
	{
		return fireItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return fireItemStacks[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	public void handleFuelStack()
	{
		Random random = new Random();
		if(fireItemStacks[7] == null)
		{
			if(random.nextBoolean() && fireItemStacks[6] != null)
			{
				fireItemStacks[7] = fireItemStacks[6];
				fireItemStacks[6] = null;
			}
			else
			{
				fireItemStacks[7] = fireItemStacks[8];
				fireItemStacks[8] = null;
			}
		}

		if(fireItemStacks[6] == null)
		{
			if(fireItemStacks[5] != null)
			{
				fireItemStacks[6] = fireItemStacks[5];
				fireItemStacks[5] = null;
			}
		}

		if(fireItemStacks[8] == null)
		{
			if(fireItemStacks[9] != null)
			{
				fireItemStacks[8] = fireItemStacks[9];
				fireItemStacks[9] = null;
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
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		isSmokeStackValid = nbt.getBoolean("isValid");

		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		fireItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbt1.getByte("Slot");
			if(byte0 >= 0 && byte0 < fireItemStacks.length)
				fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbt1);
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		fireItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		//Here we make sure that the forge is valid
		isSmokeStackValid = validateSmokeStack();

		if(!worldObj.isRemote)
		{
			//Here we take care of the items that we are cooking in the fire
			careForInventorySlot(fireItemStacks[0]);
			careForInventorySlot(fireItemStacks[1]);
			careForInventorySlot(fireItemStacks[2]);
			careForInventorySlot(fireItemStacks[3]);
			careForInventorySlot(fireItemStacks[4]);

			ItemStack[] fuelStack = new ItemStack[9];
			fuelStack[0] = fireItemStacks[5];
			fuelStack[1] = fireItemStacks[6];
			fuelStack[2] = fireItemStacks[7];
			fuelStack[3] = fireItemStacks[8];
			fuelStack[4] = fireItemStacks[9];
			fuelStack[5] = fireItemStacks[10];
			fuelStack[6] = fireItemStacks[11];
			fuelStack[7] = fireItemStacks[12];
			fuelStack[8] = fireItemStacks[13];

			//Now we cook the input item
			cookItem(0);
			cookItem(1);
			cookItem(2);
			cookItem(3);
			cookItem(4);

			//push the input fuel down the stack
			handleFuelStack();

			//Play the fire sound
			Random r = new Random();
			if(r.nextInt(10) == 0 && fireTemp > 20)
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "fire.fire", 0.4F + (r.nextFloat() / 2), 0.7F + r.nextFloat());

			if(fireTemp >= 20 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
			else if(fireTemp < 20 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);

			//If the fire is still burning and has fuel
			if (fuelTimeLeft > 0 && fireTemp >= 1 && !TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord))
			{
				float desiredTemp = handleTemp();
				handleTempFlux(desiredTemp);
				smokeTimer++;
				if(smokeTimer > 60)
				{
					smokeTimer = 0;
					createSmoke();
				}
				if(TFCOptions.enableDebugMode)
				{
					fireTemp = 2000;
					fuelTimeLeft = 9999;
				}

				TFC_Core.handleItemTicking(fuelStack, worldObj, xCoord, yCoord, zCoord);
			}
			else if(fuelTimeLeft <= 0 && fireTemp >= 1 && fireItemStacks[7] != null && isSmokeStackValid)
			{
				//here we set the temp and burn time based on the fuel in the bottom slot.
				EnumFuelMaterial m = TFC_Core.getFuelMaterial(fireItemStacks[7]);
				fuelTimeLeft = m.burnTimeMax;
				fuelBurnTemp = m.burnTempMax;
				fuelTasteProfile = m.ordinal();
				fireItemStacks[7] = null;
			}
			else
			{
				removeSmoke();

				handleTempFlux(0);
				TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
			}

			//Here we handle the bellows
			handleAirReduction();

			//do a last minute check to verify stack size
			for(int c = 0; c < 5; c++)
			{
				if(fireItemStacks[c] != null)
				{
					if(fireItemStacks[c].stackSize <= 0)
						fireItemStacks[c].stackSize = 1;
				}
			}
		}
	}

	private void createSmoke()
	{
		if(!TFCOptions.generateSmoke)
			return;

		if (checkChimney(xCoord + 1, yCoord + 1, zCoord))
			genSmokeRoot(xCoord+1, yCoord+1, zCoord);
		else if (checkChimney(xCoord - 1, yCoord + 1, zCoord))
			genSmokeRoot(xCoord-1, yCoord+1, zCoord);
		else if (checkChimney(xCoord, yCoord + 1, zCoord + 1))
			genSmokeRoot(xCoord, yCoord+1, zCoord+1);
		else if (checkChimney(xCoord, yCoord + 1, zCoord - 1))
			genSmokeRoot(xCoord, yCoord+1, zCoord-1);
		else if (notOpaque(xCoord + 1, yCoord + 1, zCoord) && checkChimney(xCoord + 2, yCoord + 1, zCoord))
			genSmokeRoot(xCoord+2, yCoord+1, zCoord);
		else if (notOpaque(xCoord - 1, yCoord + 1, zCoord) && checkChimney(xCoord - 2, yCoord + 1, zCoord))
			genSmokeRoot(xCoord-2, yCoord+1, zCoord);
		else if (notOpaque(xCoord, yCoord + 1, zCoord + 1) && checkChimney(xCoord, yCoord + 1, zCoord + 2))
			genSmokeRoot(xCoord, yCoord+1, zCoord+2);
		else if (notOpaque(xCoord, yCoord + 1, zCoord - 1) && checkChimney(xCoord, yCoord + 1, zCoord - 2))
			genSmokeRoot(xCoord, yCoord+1, zCoord-2);
	}

	public void removeSmoke() {
		if (isSmoke(xCoord, yCoord + 1, zCoord))
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord);
		else if (isSmoke(xCoord + 1, yCoord + 1, zCoord))
			worldObj.setBlockToAir(xCoord+1, yCoord+1, zCoord);
		else if (isSmoke(xCoord - 1, yCoord + 1, zCoord))
			worldObj.setBlockToAir(xCoord-1, yCoord+1, zCoord);
		else if (isSmoke(xCoord, yCoord + 1, zCoord + 1))
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord+1);
		else if (isSmoke(xCoord, yCoord + 1, zCoord - 1))
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord-1);
		else if (isSmoke(xCoord + 2, yCoord + 1, zCoord))
			worldObj.setBlockToAir(xCoord+2, yCoord+1, zCoord);
		else if (isSmoke(xCoord - 2, yCoord + 1, zCoord))
			worldObj.setBlockToAir(xCoord-2, yCoord+1, zCoord);
		else if (isSmoke(xCoord, yCoord + 1, zCoord + 2))
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord+2);
		else if (isSmoke(xCoord, yCoord + 1, zCoord - 2))
			worldObj.setBlockToAir(xCoord, yCoord+1, zCoord-2);
	}

	private boolean isSmoke(int x, int y, int z)
	{
		return worldObj.blockExists(x, y, z) && worldObj.getBlock(x, y, z) == TFCBlocks.smoke;
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

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("isValid", isSmokeStackValid);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < fireItemStacks.length; i++)
		{
			if(fireItemStacks[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte)i);
				fireItemStacks[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}
}
