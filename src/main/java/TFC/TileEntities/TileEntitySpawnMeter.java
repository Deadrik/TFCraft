package TFC.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import TFC.TFCBlocks;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Time;

public class TileEntitySpawnMeter extends TileEntity
{
	private long timer;
	public TileEntitySpawnMeter()
	{
		timer = 0;
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(timer < TFC_Time.getTotalTicks())
			{
				timer += TFC_Time.hourLength;
				TFC.Chunkdata.ChunkData cd = ChunkDataManager.getData(xCoord >> 4, zCoord >> 4);
				if(cd != null)
				{
					int protection = cd.spawnProtection;
					int meta = 0;
					meta = protection > 384 ? 8 : protection / 48;
					worldObj.setBlock(xCoord, yCoord, zCoord, TFCBlocks.SpawnMeter, meta, 0x2);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}
}
