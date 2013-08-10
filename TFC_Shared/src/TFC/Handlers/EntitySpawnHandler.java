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
import TFC.Entities.Mobs.EntitySheepTFC;

public class EntitySpawnHandler
{
	@ForgeSubscribe
	public void onEntitySpawn(LivingSpawnEvent event) 
	{
		EntityLivingBase entity = event.entityLiving;

		if (entity instanceof EntitySheepTFC)
		{
			((EntitySheepTFC)entity).setFleeceColor(EntitySheepTFC.getRandomFleeceColor(entity.worldObj.rand));
		}
	}

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
			((EntityPlayer)event.entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000);
			((EntityPlayer)event.entity).setEntityHealth(1000);
			event.entity.getEntityData().setBoolean("hasSpawned", true);
		}
	}
}
