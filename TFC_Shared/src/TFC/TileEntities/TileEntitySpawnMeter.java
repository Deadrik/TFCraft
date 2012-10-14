package TFC.TileEntities;

import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Time;
import net.minecraft.src.TileEntity;

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
				TFC.Chunkdata.ChunkData cd = ChunkDataManager.getData(xCoord, zCoord);
				if(cd != null)
				{
					int protection = cd.spawnProtection;
					if(protection <= 0)
						worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 0);
					else if(protection > 0 && protection < 24)
						worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 1);
					else if(protection >= 24 && protection < 168)
						worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 2);
					else
						worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 3);
				}
			}
		}
	}
}
