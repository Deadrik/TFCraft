package TFC.Core;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Food.FoodStatsTFC;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.FoodStats;
import net.minecraft.src.ItemInWorldManager;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.ServerPlayerAPI;
import net.minecraft.src.ServerPlayerBase;
import net.minecraft.src.World;

public class TFC_Player extends ServerPlayerBase
{
	//Last Visited Chunk
	public int lastChunkX;
	public int lastChunkY;
	public int lastChunkZ;
	
	//Last time the spawn protection was updated
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
	public void afterLocalConstructing(MinecraftServer var1, World var2, String var3, ItemInWorldManager var4) 
	{
		this.player.setFoodStatsField(new FoodStatsTFC());
	}
	
	@Override
	public void beforeOnLivingUpdate() 
	{
		
	}

	@Override
	public void afterOnLivingUpdate() 
	{
		if(!this.player.worldObj.isRemote)
		{
			
			if (this.player.worldObj.difficultySetting == 0 && this.player.getHealthField() < this.getMaxHealth() && this.player.ticksExisted % 20 * 12 == 0)
	        {
	            this.heal(1);
	        }
			
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
	
	public boolean shouldHeal()
    {
		int health = this.player.getHealthField();
        return health > 0 && health < getMaxHealth();
    }
	
	@Override
	public void heal(int var1)
    {
		int health = this.player.getHealthField();
		if (health > 0)
        {
            this.player.setHealthField(health + var1);

            if (this.player.getHealthField() > this.getMaxHealth())
            {
            	this.player.setHealthField(getMaxHealth());
            }

            this.player.hurtResistantTime = this.player.maxHurtResistantTime / 2;
        }
    }
	
	public int getMaxHealth()
    {
        return 100;
    }
	
	public void beforeReadEntityFromNBT(NBTTagCompound var1) 
	{
		if (!var1.hasKey("Health"))
        {
			this.player.setHealthField(getMaxHealth());
        }
	}
}
