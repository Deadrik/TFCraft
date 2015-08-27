package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;

public class TEFruitLeaves extends NetworkTileEntity
{
	public int dayHarvested = -1000;
	public int dayFruited = -1000;
	public boolean hasFruit;

	public TEFruitLeaves()
	{
		this.shouldSendInitData = true;
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
		dayHarvested = nbt.getInteger("dayHarvested");
		dayFruited = nbt.getInteger("dayFruited");
		hasFruit = nbt.getBoolean("hasFruit");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dayHarvested", dayHarvested);
		nbt.setInteger("dayFruited", dayFruited);
		nbt.setBoolean("hasFruit", hasFruit);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		hasFruit = nbt.getBoolean("hasFruit");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
		hasFruit = nbt.getBoolean("hasFruit");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
		nbt.setBoolean("hasFruit", hasFruit);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setBoolean("hasFruit", hasFruit);
	}
}
