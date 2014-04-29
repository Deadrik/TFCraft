package com.bioxx.tfc.TileEntities;

import java.util.Random;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityForge extends TileEntityFireEntity implements IInventory
{
	public boolean isSmokeStackValid;
	public ItemStack fireItemStacks[];
	private int prevStackSize;
	private ItemStack prevWorkItemStack;
	private int externalFireCheckTimer;
	public Boolean canCreateFire;
	private int externalWoodCount;
	private int charcoalCounter;

	public TileEntityForge()
	{
		super();
		fuelTimeLeft = 200;
		fuelBurnTemp =  200;
		fireTemp = 20;
		isSmokeStackValid = false;
		fireItemStacks = new ItemStack[14];
		externalFireCheckTimer = 0;
		externalWoodCount = 0;
		charcoalCounter = 0;
		maxFireTempScale = 2500;

	}

	private Boolean CheckSmokeStackValidity() 
	{
		if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && !worldObj.isRaining())
			return true;
		else if(!worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord + 1, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord - 1, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord + 1))
			return true;
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).isOpaqueCube() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord - 1))
			return true;
		else if(!worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).isOpaqueCube() && !worldObj.getBlock(xCoord+2, yCoord + 1, zCoord).isOpaqueCube() && 
				worldObj.canBlockSeeTheSky(xCoord+2, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).isOpaqueCube() && !worldObj.getBlock(xCoord-2, yCoord + 1, zCoord).isOpaqueCube() && 
				worldObj.canBlockSeeTheSky(xCoord-2, yCoord + 1, zCoord))
			return true;
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).isOpaqueCube() && !worldObj.getBlock(xCoord, yCoord + 1, zCoord+2).isOpaqueCube() && 
				worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord+2))
			return true;
		else if(!worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).isOpaqueCube() && !worldObj.getBlock(xCoord, yCoord + 1, zCoord-2).isOpaqueCube() && 
				worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord-2))
			return true;
		else
			return false;
	}

	@Override
	public void closeInventory()
	{
	}

	public void combineMetals(ItemStack InputItem, ItemStack DestItem)
	{
		int D1 = 100-InputItem.getItemDamage();
		int D2 = 100-DestItem.getItemDamage();
		DestItem.setItemDamage(100 - (D1 + D2));
	}

	public void CookItem(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random R = new Random();
		if(fireItemStacks[i] != null)
		{
			HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
			ItemStack inputCopy = fireItemStacks[i].copy();
			if(index != null && TFC_ItemHeat.GetTemp(fireItemStacks[i]) > index.meltTemp)
			{
				float temp = TFC_ItemHeat.GetTemp(fireItemStacks[i]);
				int dam = fireItemStacks[i].getItemDamage();
				ItemStack is = fireItemStacks[i].copy();
				//Morph the input
				if(!(fireItemStacks[i].getItem() instanceof ItemMeltedMetal))
					fireItemStacks[i] = index.getMorph();

				if(fireItemStacks[i] != null)
				{
					HeatIndex index2 = manager.findMatchingIndex(fireItemStacks[i]);
					if(index2 != null)
					{
						//if the input is a new item, then apply the old temperature to it
						TFC_ItemHeat.SetTemp(fireItemStacks[i], TFC_ItemHeat.GetTemp(fireItemStacks[i]));
					}
				}
				else if(index.hasOutput())
				{
					ItemStack output = index.getOutput(inputCopy, R);
					int count = 1;
					Boolean useCount = false;
					if(output.getItem() instanceof ItemMeltedMetal)
					{
						if(output.stackSize >= 1)
						{
							count = 0;
							int c = output.stackSize;
							for(int iterations = 0; c > 0 && iterations <= 20; iterations++)
							{
								if(fireItemStacks[10] != null && fireItemStacks[10].getItem() == TFCItems.CeramicMold)
								{
									fireItemStacks[10].stackSize--;
									if(fireItemStacks[10].stackSize == 0)
										fireItemStacks[10] = null;
									c--;
									count++;
								}
								else if(fireItemStacks[11] != null && fireItemStacks[11].getItem() == TFCItems.CeramicMold)
								{
									fireItemStacks[11].stackSize--;
									if(fireItemStacks[11].stackSize == 0)
										fireItemStacks[11] = null;
									c--;
									count++;
								}
								else if(fireItemStacks[12] != null && fireItemStacks[12].getItem() == TFCItems.CeramicMold)
								{
									fireItemStacks[12].stackSize--;
									if(fireItemStacks[12].stackSize == 0)
										fireItemStacks[12] = null;
									c--;
									count++;
								}
								else if(fireItemStacks[13] != null && fireItemStacks[13].getItem() == TFCItems.CeramicMold)
								{
									fireItemStacks[13].stackSize--;
									if(fireItemStacks[13].stackSize == 0)
										fireItemStacks[13] = null;
									c--;
									count++;
								}
							}
						}
						useCount = true;
					}

					fireItemStacks[i] = output;
					if(useCount)
					{
						if(count > 0)
							fireItemStacks[i].stackSize = count;
						else
							fireItemStacks[i] = null;
					}

					HeatIndex index2 = manager.findMatchingIndex(fireItemStacks[i]);
					if(TFC_ItemHeat.IsCookable(fireItemStacks[i]) > -1)
					{
						//if the input is a new item, then apply the old temperature to it
						TFC_ItemHeat.SetTemp(fireItemStacks[i], temp);
					}

					if(fireItemStacks[i] != null && fireItemStacks[i].getItem() instanceof ItemMeltedMetal && is.getItem() instanceof ItemMeltedMetal)
						fireItemStacks[i].setItemDamage(dam);
				}
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(fireItemStacks[i] != null)
		{
			if(fireItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = fireItemStacks[i];
				fireItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = fireItemStacks[i].splitStack(j);
			if(fireItemStacks[i].stackSize == 0)
				fireItemStacks[i] = null;
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
		if(fireItemStacks[10] != null && fireItemStacks[10].getItem() == TFCItems.CeramicMold)
			return 10;
		if(fireItemStacks[11] != null && fireItemStacks[11].getItem() == TFCItems.CeramicMold)
			return 11;
		if(fireItemStacks[12] != null && fireItemStacks[12].getItem() == TFCItems.CeramicMold)
			return 12;
		if(fireItemStacks[13] != null && fireItemStacks[13].getItem() == TFCItems.CeramicMold)
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

	public void HandleFuelStack()
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
		charcoalCounter = nbt.getInteger("charcoalCounter");
		isSmokeStackValid = nbt.getBoolean("isValid");

		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		fireItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < fireItemStacks.length)
				fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
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
		isSmokeStackValid = CheckSmokeStackValidity();

		if(!worldObj.isRemote)
		{
			//Here we take care of the items that we are cooking in the fire
			careForInventorySlot(fireItemStacks[0]);
			careForInventorySlot(fireItemStacks[1]);
			careForInventorySlot(fireItemStacks[2]);
			careForInventorySlot(fireItemStacks[3]);
			careForInventorySlot(fireItemStacks[4]);

			ItemStack[] FuelStack = new ItemStack[9];
			FuelStack[0] = fireItemStacks[5];
			FuelStack[1] = fireItemStacks[6];
			FuelStack[2] = fireItemStacks[7];
			FuelStack[3] = fireItemStacks[8];
			FuelStack[4] = fireItemStacks[9];
			FuelStack[5] = fireItemStacks[10];
			FuelStack[6] = fireItemStacks[11];
			FuelStack[7] = fireItemStacks[12];
			FuelStack[8] = fireItemStacks[13];

			//Now we cook the input item
			CookItem(0);
			CookItem(1);
			CookItem(2);
			CookItem(3);
			CookItem(4);

			//push the input fuel down the stack
			HandleFuelStack();

			//Play the fire sound
			Random R = new Random();
			if(R.nextInt(10) == 0 && fireTemp > 20)
				worldObj.playSoundEffect(xCoord,yCoord,zCoord, "fire.fire", 0.4F + (R.nextFloat()/2), 0.7F + R.nextFloat());

			if(fireTemp >= 20 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=1)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
			else if(fireTemp < 20 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);

			//If the fire is still burning and has fuel
			if(fuelTimeLeft > 0 && fireTemp >= 1 && (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || !worldObj.isRaining()))
			{
				float desiredTemp = handleTemp();
				handleTempFlux(desiredTemp);

				if(TFCOptions.enableDebugMode)
					fuelTimeLeft = 9999;

				TFC_Core.handleItemTicking(FuelStack, worldObj, xCoord, yCoord, zCoord);
			}
			else if(fuelTimeLeft <= 0 && fireTemp >= 1 && fireItemStacks[7] != null && isSmokeStackValid)
			{
				//here we set the temp and burn time based on the fuel in the bottom slot.
				if(fireItemStacks[7].getItem() == Items.coal && fireItemStacks[7].getItemDamage() == 0)
				{
					fuelTimeLeft = 2200;
					fuelBurnTemp = 1400;
				}
				if(fireItemStacks[7].getItem() == Items.coal && fireItemStacks[7].getItemDamage() == 1)
				{
					fuelTimeLeft = 1800;
					fuelBurnTemp = 1350;
				}
				fireItemStacks[7] = null;
			}
			else
			{
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
		nbt.setInteger("charcoalCounter", charcoalCounter);
		nbt.setBoolean("isValid", isSmokeStackValid);

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
		nbt.setTag("Items", nbttaglist);
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
	}
	
}
