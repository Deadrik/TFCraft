package com.bioxx.tfc.TileEntities;

import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;

public class TESpawnMeter extends NetworkTileEntity
{
	private long timer;
	private int protection;
	public TESpawnMeter()
	{
		timer = TFC_Time.getTotalTicks();
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(timer < TFC_Time.getTotalTicks())
			{
				timer += TFC_Time.HOUR_LENGTH;
				if (TFC_Core.getCDM(worldObj) != null)
				{
					ChunkData cd = TFC_Core.getCDM(worldObj).getData(xCoord >> 4, zCoord >> 4);
					if (cd != null)
					{
						protection = cd.spawnProtection;
						int meta = 0;
						if (protection > 0) // Meta should be 0 (non-lit meter) if there is still a buffer
						{
							// Meter is fully lit with 16 days of protection, regardless of config settings
							meta = protection > 384 ? 8 : protection / 48;
						}

						if (meta != worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
						{
							worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 0x3);
						}
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		protection = nbt.getInteger("protectionHours");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("protectionHours", protection);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		protection = nbt.getInteger("protectionHours");
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		protection = nbt.getInteger("protectionHours");
	}

	@Override
	public void createDataNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("protectionHours", protection);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("protectionHours", protection);
	}
}
