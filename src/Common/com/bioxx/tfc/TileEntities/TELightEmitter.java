package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.bioxx.tfc.Core.TFC_Time;

public class TELightEmitter extends TileEntity
{
	public int hourPlaced = -1000;

	public TELightEmitter()
	{
	}

	public void create()
	{
		hourPlaced = (int) TFC_Time.getTotalHours();
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		hourPlaced = nbt.getInteger("hourPlaced");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("hourPlaced", hourPlaced);
	}
}
