package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;

public class TEFenceGate extends NetworkTileEntity
{
	private boolean open;
	private byte direction;

	@Override
	public boolean canUpdate()
	{
		return false;
	}
	
	public void setOpen(boolean value)
	{
		open = value;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setDirection(byte value)
	{
		direction = value;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean getOpen()
	{
		return open;
	}

	public byte getDirection()
	{
		return direction;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		open = nbt.getBoolean("open");
		direction = nbt.getByte("dir");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("open", open);
		nbt.setByte("dir", direction);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		open = nbt.getBoolean("open");
		direction = nbt.getByte("dir");
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setBoolean("open", open);
		nbt.setByte("dir", direction);
	}

}
