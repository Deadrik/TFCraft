package com.bioxx.tfc.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.DataBlockPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class NetworkTileEntity extends TileEntity
{
	public boolean shouldSendInitData = true;
	public EntityPlayer entityplayer;
	/**
	 * Create an initialization packet to be sent when the block loads.
	 * @param nbt
	 */
	public abstract void handleInitPacket(NBTTagCompound nbt);
	public abstract void handleDataPacket(NBTTagCompound nbt);
	public abstract void createDataNBT(NBTTagCompound nbt);
	public abstract void createInitNBT(NBTTagCompound nbt);

	public DataBlockPacket createDataPacket()
	{
		return this.createDataPacket(createDataNBT());
	}

	public DataBlockPacket createDataPacket(NBTTagCompound nbt)
	{
		return new DataBlockPacket(this.xCoord, this.yCoord, this.zCoord, nbt);
	}

	private NBTTagCompound createDataNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		createDataNBT(nbt);
		return nbt;
	}

	private NBTTagCompound createInitNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		createInitNBT(nbt);
		return nbt;
	}	

	@Override
	public Packet getDescriptionPacket()
	{
		if(shouldSendInitData)
			return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, createInitNBT());
		return null;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		handleInitPacket(pkt.func_148857_g());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void broadcastPacketInRange()
	{
		int dim = worldObj.provider.dimensionId;
		TerraFirmaCraft.proxy.sendCustomPacketNearTarget(this.createDataPacket(), 
				new TargetPoint(dim, xCoord,yCoord,zCoord,256));
	}

	public void broadcastPacketInRange(AbstractPacket packet)
	{
		TerraFirmaCraft.proxy.sendCustomPacketNearTarget(packet, 
				new TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,256));
	}

}
