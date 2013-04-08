package TFC.Handlers;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkEvent;
import TFC.Chunkdata.ChunkDataManager;

public class ChunkEventHandler 
{
	@ForgeSubscribe
	public void onLoad(ChunkEvent.Load event) 
	{
	}
	
	@ForgeSubscribe
	public void onUnload(ChunkEvent.Unload event) 
	{
		if(!event.world.isRemote)
			ChunkDataManager.removeData(event.getChunk().xPosition, event.getChunk().zPosition);
	}
}
