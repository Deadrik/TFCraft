package TFC.Handlers;

import net.minecraftforge.event.world.ChunkEvent;
import TFC.Chunkdata.ChunkDataManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChunkEventHandler 
{
	@SubscribeEvent
	public void onLoad(ChunkEvent.Load event)
	{
	}
	
	@SubscribeEvent
	public void onUnload(ChunkEvent.Unload event)
	{
		if(!event.world.isRemote)
			ChunkDataManager.removeData(event.getChunk().xPosition, event.getChunk().zPosition);
	}
}
