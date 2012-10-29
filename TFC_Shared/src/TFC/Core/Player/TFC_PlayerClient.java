package TFC.Core.Player;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Time;
import TFC.Food.FoodStatsTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.FoodStats;
import net.minecraft.src.ItemInWorldManager;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBase;
import net.minecraft.src.ServerPlayerAPI;
import net.minecraft.src.ServerPlayerBase;
import net.minecraft.src.Session;
import net.minecraft.src.StepSound;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeHooks;

public class TFC_PlayerClient extends PlayerBase
{
	//Last Visited Chunk
	public int lastChunkX;
	public int lastChunkY;
	public int lastChunkZ;
	
	//Last time the spawn protection was updated
	private long spawnProtectionTimer = -1;
	
	private FoodStatsTFC foodstats;
	private FoodStats oldFood;

	public TFC_PlayerClient(PlayerAPI var1) {
		super(var1);
		foodstats = new FoodStatsTFC();
	}
	
	@Override
	public void beforeOnLivingUpdate() 
	{
		oldFood = this.player.getFoodStats();
	}

	@Override
	public void afterOnLivingUpdate() 
	{
		this.player.setFoodStatsField(oldFood);			
		//this.foodstats.onUpdate(player);
	}
	
	public FoodStatsTFC getFoodStatsTFC()
	{
		return this.foodstats;
	}
	
	public static TFC_PlayerClient getFromEntityPlayer(EntityPlayer p)
	{
		EntityClientPlayerMP pmp = (EntityClientPlayerMP) p;
		return (TFC_PlayerClient) pmp.getPlayerBase("TFC Player Client");
	}
	
	public int getMaxHealth()
    {
        return 1000+(this.player.experienceLevel*25);
    }
	
	public static int getStartingMaxHealth()
    {
        return 1000;
    }

}
