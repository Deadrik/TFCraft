package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TEOre extends NetworkTileEntity
{
	public int baseBlockID = -1000;
	public int baseBlockMeta = -1000;
	public byte extraData = 0;

	public TEOre()
	{
		this.shouldSendInitData = true;
	}

	@Override
	public void validate()
	{
		super.validate();

		if(worldObj.isRemote && this.shouldSendInitData && (extraData & 8) == 1)
			;//TODO
			/*try {
				requestInitialization();
			} catch (IOException e) {}*/
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public void setVisible()
	{
		if((extraData & 8) == 0)
			extraData += 8;

		//TODO
		/*try
		{
			TerraFirmaCraft.proxy.sendCustomPacket(sendInitPacket());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}*/
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		baseBlockID = nbt.getInteger("baseBlockID");
		baseBlockMeta = nbt.getInteger("baseBlockMeta");
		extraData = nbt.getByte("grade");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setInteger("baseBlockID", baseBlockID);
		nbt.setInteger("baseBlockMeta", baseBlockMeta);
		nbt.setByte("grade", extraData);
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

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		baseBlockID = nbt.getInteger("baseBlockID");
		baseBlockMeta = nbt.getInteger("baseBlockMeta");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("baseBlockID", baseBlockID);
		nbt.setInteger("baseBlockMeta", baseBlockMeta);
	}
}
