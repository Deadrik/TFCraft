package com.bioxx.tfc.TileEntities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.ICookableFood;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class TEGrill extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[6];
	public byte data;

	@Override
	public void updateEntity()
	{
		TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord);
		//boolean oven = isOven();
		for (int i = 0; i < 6; i++)
		{
			careForInventorySlot(storage[i]);
			cookItem(i);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
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

		if(worldObj.getBlock(xCoord-1, yCoord, zCoord) == TFCBlocks.metalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
			if(te.getSide() == 4)
				wallCount++;
		}
		else if(worldObj.getBlock(xCoord+1, yCoord, zCoord) == TFCBlocks.metalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
			if(te.getSide() == 5)
				wallCount++;
		}
		else if(worldObj.getBlock(xCoord, yCoord, zCoord-1) == TFCBlocks.metalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
			if(te.getSide() == 2)
				wallCount++;
		}
		else if(worldObj.getBlock(xCoord, yCoord, zCoord+1) == TFCBlocks.metalTrapDoor)
		{
			TEMetalTrapDoor te = (TEMetalTrapDoor) worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
			if(te.getSide() == 3)
				wallCount++;
		}

		return wallCount >= 5;
	}

	public boolean isDoor(int x, int y, int z)
	{

		return false;
	}

	public void careForInventorySlot(ItemStack is)
	{
		TileEntity te = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		if(is != null && te instanceof TEFireEntity)
		{
			HeatRegistry manager = HeatRegistry.getInstance();
			HeatIndex index = manager.findMatchingIndex(is);

			if (index != null)
			{
				float temp = TFC_ItemHeat.getTemp(is);
				TEFireEntity fire = (TEFireEntity) te;
				if (fire.fuelTimeLeft > 0 && is.getItem() instanceof IFood)
				{
					float inc = Food.getCooked(is) + Math.min(fire.fireTemp / 700, 2f);
					Food.setCooked(is, inc);
					temp = inc;
				}
				else if (fire.fireTemp > temp)
				{
					temp += TFC_ItemHeat.getTempIncrease(is);
				}

				if (fire.fireTemp > temp)
					temp += TFC_ItemHeat.getTempIncrease(is);
				else
					temp -= TFC_ItemHeat.getTempDecrease(is);
				TFC_ItemHeat.setTemp(is, temp);
			}
		}
	}

	public void cookItem(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random r = new Random();
		if(storage[i] != null)
		{
			HeatIndex index = manager.findMatchingIndex(storage[i]);
			if(index != null && Food.isCooked(storage[i]))
			{
				//int[] fuelTasteProfile = new int[] {0,0,0,0,0};
				int[] cookedTasteProfile = new int[] {0,0,0,0,0};
				r = new Random(((ICookableFood)storage[i].getItem()).getFoodID()+(((int)Food.getCooked(storage[i])-600)/120));
				cookedTasteProfile[0] = r.nextInt(31) - 15;
				cookedTasteProfile[1] = r.nextInt(31) - 15;
				cookedTasteProfile[2] = r.nextInt(31) - 15;
				cookedTasteProfile[3] = r.nextInt(31) - 15;
				cookedTasteProfile[4] = r.nextInt(31) - 15;
				Food.setCookedProfile(storage[i], cookedTasteProfile);
				TileEntity te = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
				if(te instanceof TEFireEntity)
				{
					TEFireEntity fire = (TEFireEntity) te;
					Food.setFuelProfile(storage[i], EnumFuelMaterial.getFuelProfile(fire.fuelTasteProfile));
				}
			}

			if(index != null && TFC_ItemHeat.getTemp(storage[i]) > index.meltTemp)
			{
				float temp = TFC_ItemHeat.getTemp(storage[i]);
				ItemStack output = index.getOutput(storage[i], r);

				ItemCookEvent eventMelt = new ItemCookEvent(storage[i], output, this);
				MinecraftForge.EVENT_BUS.post(eventMelt);
				output = eventMelt.result;		

				//Morph the input
				storage[i] = output;
				if(storage[i] != null && manager.findMatchingIndex(storage[i]) != null)
				{
					//if the input is a new item, then apply the old temperature to it
					TFC_ItemHeat.setTemp(storage[i], temp);
				}
			}
		}
	}

	public int getSide()
	{
		return data & 7;
	}

	public boolean isEmpty()
	{
		for (ItemStack is : storage)
		{
			if (is != null)
				return false;
		}

		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		data = nbt.getByte("data");
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
		nbt.setByte("data", data);
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
		//float f3 = 0.05F;
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
		//float f3 = 0.05F;
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
		data = nbt.getByte("data");
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
		nbt.setByte("data", this.data);
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
