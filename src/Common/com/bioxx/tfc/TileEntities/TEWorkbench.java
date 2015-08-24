package com.bioxx.tfc.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEWorkbench extends TileEntity implements IInventory
{
	public ItemStack[] craftingMatrix;

	public TEWorkbench()
	{
		craftingMatrix = new ItemStack[9];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(craftingMatrix[i] != null)
		{
			if(craftingMatrix[i].stackSize <= j)
			{
				ItemStack itemstack = craftingMatrix[i];
				craftingMatrix[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = craftingMatrix[i].splitStack(j);
			if(craftingMatrix[i].stackSize == 0)
				craftingMatrix[i] = null;
			return itemstack1;
		}
		else
			return null;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public int getSizeInventory()
	{
		return craftingMatrix.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return craftingMatrix[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		craftingMatrix[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}

	@Override
	public String getInventoryName()
	{
		return "Workbench";
	}

	@Override
	public boolean hasCustomInventoryName()
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
	}

	/*@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
	}*/

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
