package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TEBloom extends TileEntity
{
	public int size;

	public TEBloom()
	{
		size = 0;
	}

	public void setSize(int iron)
	{
		size = iron;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("size", size);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		size = nbttagcompound.getInteger("size");
	}
}
