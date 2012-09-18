package TFC.Handlers;

import net.minecraft.src.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import TFC.Chunkdata.ChunkData;
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
		ChunkDataManager.removeData(event.getChunk().xPosition, event.getChunk().zPosition);
	}
}
