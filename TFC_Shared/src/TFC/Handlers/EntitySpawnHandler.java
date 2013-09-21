package TFC.Handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;

public class EntitySpawnHandler
{

	@ForgeSubscribe
	public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) 
	{
		EntityLivingBase entity = event.entityLiving;

		int x = (int)entity.posX >> 4;
		int z = (int)entity.posZ >> 4;

		ChunkData data = (ChunkData) ChunkDataManager.chunkmap.get(x + "," + z);
		if(!(data == null || data.getSpawnProtectionWithUpdate() <= 0))
		{
			event.setResult(Result.DENY);
		}
	}

	@ForgeSubscribe
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer && !event.entity.getEntityData().hasKey("hasSpawned"))
		{
			((EntityPlayer)event.entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1000);
			((EntityPlayer)event.entity).setHealth(1000);
			event.entity.getEntityData().setBoolean("hasSpawned", true);
		}
	}
}
