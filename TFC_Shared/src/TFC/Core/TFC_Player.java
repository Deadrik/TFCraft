package TFC.Core;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import net.minecraft.src.ServerPlayerAPI;
import net.minecraft.src.ServerPlayerBase;

public class TFC_Player extends ServerPlayerBase
{
	public int lastChunkX;
	public int lastChunkY;
	public int lastChunkZ;

	private long spawnProtectionTimer = 0;

	public TFC_Player(ServerPlayerAPI var1) {
		super(var1);
	}

	@Override
	public void beforeMoveEntity(double x, double y, double z) 
	{
		if(!this.player.worldObj.isRemote)
		{
			lastChunkX = (int)player.posX >> 4;
			lastChunkY = (int)player.posY >> 4;
			lastChunkZ = (int)player.posZ >> 4;
		}
	}

	@Override
	public void afterMoveEntity(double x, double y, double z) 
	{
		if(!this.player.worldObj.isRemote)
		{
			int currentChunkX = (int)player.posX >> 4;
			int currentChunkY = (int)player.posY >> 4;
			int currentChunkZ = (int)player.posZ >> 4;

			if(currentChunkX != lastChunkX || currentChunkZ != lastChunkZ)
			{
				ChunkDataManager.setLastVisted(lastChunkX, lastChunkZ);
				//Reset the timer since we've entered a new chunk
				spawnProtectionTimer = TFC_Time.getTotalTicks();

			}
		}
	}

	@Override
	public void afterOnLivingUpdate() 
	{
		if(!this.player.worldObj.isRemote)
		{
			if(spawnProtectionTimer + TFC_Time.hourLength < TFC_Time.getTotalTicks())
			{
				int t = (int) ((TFC_Time.getTotalTicks() - spawnProtectionTimer) / TFC_Time.hourLength);

				//Add protection time to the chunks
				for(int i = -1; i < 2; i++)
				{
					for(int k = -1; k < 2; k++)
					{
						ChunkDataManager.addProtection(lastChunkX + i, lastChunkZ + k, 7 * t);
					}
				}
				
				spawnProtectionTimer += TFC_Time.hourLength;
			}
		}
	}

}
