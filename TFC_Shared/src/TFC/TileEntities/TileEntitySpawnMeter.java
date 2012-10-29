package TFC.TileEntities;

import TFC.TFCBlocks;
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
				TFC.Chunkdata.ChunkData cd = ChunkDataManager.getData(xCoord >> 4, zCoord >> 4);
				if(cd != null)
				{
					int protection = cd.spawnProtection;
					int meta = 0;
					
					meta = protection > 384 ? 8 : protection / 48;
					
					worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord, TFCBlocks.SpawnMeter.blockID, meta);
					worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}
}
