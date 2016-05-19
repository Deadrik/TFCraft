package com.bioxx.tfc.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.Food;

public class TESmokeRack extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage = new ItemStack[2];
	public int[] driedCounter = new int[]{0,0};
	private int dryTimer;

	//temporary smoke timer that should not be saved
	public int lastSmokedTime;

	public TESmokeRack()
	{
	}

	@Override
	public void updateEntity()
	{
		float env = 1.0f;
		float base = 1.0f;

		if(TFC_Climate.getRainfall(worldObj, xCoord, yCoord, zCoord) < 500)
		{
			env = 0.75f; base = 0.75f;
		}

		this.dryTimer++;
		if (dryTimer > 1000)
		{
			dryTimer = 0;
			dryFoods();
		}

		if (!TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord) && TFC_Time.getTotalHours() > this.lastSmokedTime + 1)
			TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord, env, base);
		else if(TFC_Climate.getHeightAdjustedTemp(worldObj, xCoord, yCoord, zCoord) > 0)
			TFC_Core.handleItemTicking(this, worldObj, xCoord, yCoord, zCoord, env*2, base*2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		TFC_Core.readInventoryFromNBT(nbt, storage);
		driedCounter = nbt.getIntArray("driedCounter");
		if(driedCounter.length == 0)
			driedCounter = new int[] {0,0};
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		TFC_Core.writeInventoryToNBT(nbt, storage);
		nbt.setIntArray("driedCounter", driedCounter);
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
	public ItemStack decrStackSize(int i, int j)
	{
		storage[i].stackSize -= j;
		return storage[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		boolean flag = false;
		if(!TFC_Core.areItemsEqual(storage[i], itemstack))
		{
			flag = true;
		}

		if(itemstack != null && !ItemStack.areItemStacksEqual(itemstack, storage[i]))
		{
			if(Food.getDried(itemstack) > 0)
				driedCounter[i] = (int) (TFC_Time.getTotalHours() - Food.getDried(itemstack));
			else
				driedCounter[i] = (int)TFC_Time.getTotalHours();//Reset the counter if its a new item
			flag = true;
		}
		if(flag)
		{
			storage[i] = itemstack;
			broadcastPacketInRange();
		}
	}

	public ItemStack removeStackInSlot(int i)
	{
		ItemStack is = this.getStackInSlot(i).copy();
		Food.setDried(is, (int)TFC_Time.getTotalHours()-this.driedCounter[i]);
		this.setInventorySlotContents(i, null);
		return is;
	}

	public void dryFoods()
	{
		for (int i = 0; i < storage.length; i++)
		{
			if (getStackInSlot(i) != null)
			{
				ItemStack is = getStackInSlot(i);
				Food.setDried(is, (int) TFC_Time.getTotalHours() - this.driedCounter[i]);
				driedCounter[i] = (int) (TFC_Time.getTotalHours() - Food.getDried(is));
			}

		}
	}

	@Override
	public String getInventoryName()
	{
		return "";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
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
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		TFC_Core.readInventoryFromNBT(nbt, storage);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) 
	{
		storage = new ItemStack[storage.length];
		TFC_Core.readInventoryFromNBT(nbt, storage);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) 
	{
		TFC_Core.writeInventoryToNBT(nbt, storage);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		TFC_Core.writeInventoryToNBT(nbt, storage);
	}
}
