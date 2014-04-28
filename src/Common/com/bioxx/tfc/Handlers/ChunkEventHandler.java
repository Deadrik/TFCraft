package com.bioxx.tfc.Handlers;

import java.util.Random;

import net.minecraftforge.event.world.ChunkEvent;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Chunkdata.ChunkDataManager;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.WorldGen.Generators.WorldGenGrowCrops;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChunkEventHandler
{
	@SubscribeEvent
	public void onLoad(ChunkEvent.Load event)
	{
		if (!event.world.isRemote)
		{
			ChunkData cd = ChunkDataManager.getData(event.getChunk().xPosition, event.getChunk().zPosition);
			if(cd== null)
				return;
			int month = TFC_Time.getSeasonAdjustedMonth(event.getChunk().zPosition << 4);
			if (TFC_Time.getYear() > cd.lastSpringGen && month > 1 && month < 6)
			{
				int chunk_X = event.getChunk().xPosition;
				int chunk_Z = event.getChunk().zPosition;
				cd.lastSpringGen = TFC_Time.getYear();

				Random rand = new Random(event.world.getSeed() + ((chunk_X >> 3) - (chunk_Z >> 3)) * (chunk_Z >> 3));
				int cropid = rand.nextInt(24);
				CropIndex crop = CropManager.getInstance().getCropFromId(cropid);
				if (event.world.rand.nextInt(25) == 0 && crop != null)
				{
					int num = 1 + event.world.rand.nextInt(5);
					WorldGenGrowCrops cropGen = new WorldGenGrowCrops(cropid);
					int x = (chunk_X << 4) + event.world.rand.nextInt(16) + 8;
					int z = (chunk_Z << 4) + event.world.rand.nextInt(16) + 8;
					cropGen.generate(event.world, event.world.rand, x, z, num);
				}
			}
		}
	}

	@SubscribeEvent
	public void onUnload(ChunkEvent.Unload event)
	{
		//ll if (!event.world.isRemote)
		ChunkDataManager.removeData(event.getChunk().xPosition, event.getChunk().zPosition);
	}
}
