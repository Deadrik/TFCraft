package com.bioxx.tfc.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.api.TFCBlocks;

public class TEMetalTrapDoor extends NetworkTileEntity
{
	public ItemStack sheetStack;
	public byte data;

	public TEMetalTrapDoor()
	{

	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public int getSide()
	{
		return data & 7;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		sheetStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("sheetType"));
		data = nbt.getByte("data");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setByte("data", data);
		NBTTagCompound st = new NBTTagCompound();
		if(sheetStack != null)
		{
			sheetStack.writeToNBT(st);
			nbt.setTag("sheetType", st);
		}
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		data = nbt.getByte("data");
		sheetStack = new ItemStack(TFCBlocks.metalTrapDoor, 1, nbt.getInteger("metalID"));
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setByte("data", this.data);
		nbt.setInteger("metalID", sheetStack != null ? sheetStack.getItemDamage() : 0);
	}
}
