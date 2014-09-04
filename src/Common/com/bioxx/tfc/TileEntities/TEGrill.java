package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.ICookableFood;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class TEGrill extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[6];

	@Override
	public void updateEntity()
	{
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
		boolean oven = isOven();
		for (int i = 0; i < 6; i++)
		{
			careForInventorySlot(storage[i]);
			cookItem(i);
		}
	}

	public boolean isOven()
	{
		int wallCount = 0;
		if(TFC_Core.isWestFaceSolid(worldObj, xCoord-1, yCoord, zCoord))//East Block
			wallCount++;
		if(TFC_Core.isEastFaceSolid(worldObj, xCoord+1, yCoord, zCoord))//West Block
			wallCount++;
		if(TFC_Core.isNorthFaceSolid(worldObj, xCoord, yCoord, zCoord+1))//South Block
			wallCount++;
		if(TFC_Core.isSouthFaceSolid(worldObj, xCoord, yCoord, zCoord-1))//North Block
			wallCount++;

		if(TFC_Core.isBottomFaceSolid(worldObj, xCoord, yCoord+1, zCoord))//Top Block
			wallCount++;

		if(worldObj.getBlock(xCoord-1, yCoord, zCoord) == TFCBlocks.MetalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
			if(te.getSide() == 4)
				wallCount++;
		}
		else if(worldObj.getBlock(xCoord+1, yCoord, zCoord) == TFCBlocks.MetalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
			if(te.getSide() == 5)
				wallCount++;
		}
		else if(worldObj.getBlock(xCoord, yCoord, zCoord-1) == TFCBlocks.MetalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
			if(te.getSide() == 2)
				wallCount++;
		}
		else if(worldObj.getBlock(xCoord, yCoord, zCoord+1) == TFCBlocks.MetalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
			if(te.getSide() == 3)
				wallCount++;
		}

		if(wallCount < 5)
			return false;

		return true;
	}

	public boolean isDoor(int x, int y, int z)
	{

		return false;
	}

	public void careForInventorySlot(ItemStack is)
	{
		if(is != null && worldObj.getTileEntity(xCoord, yCoord-1, zCoord) != null)
		{
			TEFireEntity te = (TEFireEntity)worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
			float temp = TFC_ItemHeat.GetTemp(is);
			if(te.fuelTimeLeft > 0 && is.getItem() instanceof IFood)
			{
				float inc = is.getTagCompound().getFloat("cookedLevel")+(te.fireTemp/700);
				is.getTagCompound().setFloat("cookedLevel", inc);
				temp = inc;
			}
			else if(te.fireTemp > temp)
			{
				temp += TFC_ItemHeat.getTempIncrease(is);
			}

			if(te.fireTemp > temp)
				temp += TFC_ItemHeat.getTempIncrease(is);
			else
				temp -= TFC_ItemHeat.getTempDecrease(is);
			TFC_ItemHeat.SetTemp(is, temp);
		}
	}

	public void cookItem(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random R = new Random();
		if(storage[i] != null)
		{
			TEFireEntity te = (TEFireEntity) worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
			HeatIndex index = manager.findMatchingIndex(storage[i]);
			if(index != null && storage[i].hasTagCompound() && storage[i].getTagCompound().hasKey("cookedLevel"))
			{
				float cookedLevel = storage[i].getTagCompound().getFloat("cookedLevel");
				if(cookedLevel > 600)
				{
					int[] fuelTasteProfile = new int[] {0,0,0,0,0};
					int[] cookedTasteProfile = new int[] {0,0,0,0,0};
					R = new Random(((ICookableFood)storage[i].getItem()).getFoodID()+((int)cookedLevel/100));
					cookedTasteProfile[0] = R.nextInt(30)-15;
					cookedTasteProfile[1] = R.nextInt(30)-15;
					cookedTasteProfile[2] = R.nextInt(30)-15;
					cookedTasteProfile[3] = R.nextInt(30)-15;
					cookedTasteProfile[4] = R.nextInt(30)-15;
					storage[i].getTagCompound().setIntArray("cookedTasteProfile", cookedTasteProfile);
					if(te != null)
					{
						fuelTasteProfile = te.fuelTasteProfile;
						float mod = ((ICookableFood)storage[i].getItem()).getSmokeAbsorbMultiplier();
						fuelTasteProfile[0] *= mod;
						fuelTasteProfile[1] *= mod;
						fuelTasteProfile[2] *= mod;
						fuelTasteProfile[3] *= mod;
						fuelTasteProfile[4] *= mod;
						storage[i].getTagCompound().setIntArray("fuelTasteProfile", fuelTasteProfile);
					}
				}
			}

			if(index != null && TFC_ItemHeat.GetTemp(storage[i]) > index.meltTemp)
			{
				float temp = TFC_ItemHeat.GetTemp(storage[i]);
				ItemStack output = index.getOutput(storage[i], R);

				ItemCookEvent eventMelt = new ItemCookEvent(storage[i], output, this);
				MinecraftForge.EVENT_BUS.post(eventMelt);
				output = eventMelt.result;		

				//Morph the input
				storage[i] = output;
				if(storage[i] != null && manager.findMatchingIndex(storage[i]) != null)
				{
					//if the input is a new item, then apply the old temperature to it
					TFC_ItemHeat.SetTemp(storage[i], temp);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(storage[i] != null)
		{
			if(storage[i].stackSize <= j)
			{
				ItemStack itemstack = storage[i];
				storage[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = storage[i].splitStack(j);
			if(storage[i].stackSize == 0)
				storage[i] = null;
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
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(storage[i]!= null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
				worldObj.spawnEntityInWorld(entityitem);
				storage[i] = null;
			}
		}
	}

	public void ejectItem(int index)
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		if(storage[index]!= null)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[index]);
			worldObj.spawnEntityInWorld(entityitem);
		}
	}

	@Override
	public int getSizeInventory()
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return storage[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		storage[i] = itemstack;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public String getInventoryName()
	{
		return "grill";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
		if(worldObj.isRemote)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
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
	public void handleInitPacket(NBTTagCompound nbt) {
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < storage.length; i++)
		{
			if(storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

}
