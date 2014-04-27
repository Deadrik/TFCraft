package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFenceGate extends TileEntity
{
	private boolean open = false;
	private byte direction = 0;

	public void setOpen(boolean value)
	{
		open = value;
		//TerraFirmaCraft.proxy.sendCustomPacket(createUpdatePacket());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setDirection(byte value)
	{
		direction = value;
		//TerraFirmaCraft.proxy.sendCustomPacket(createUpdatePacket());
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
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		open = nbttagcompound.getBoolean("open");
		direction = nbttagcompound.getByte("dir");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("open", open);
		nbttagcompound.setByte("dir", direction);
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
