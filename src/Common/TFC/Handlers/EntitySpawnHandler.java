package TFC.Handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Containers.ContainerPlayerTFC;
import TFC.Core.TFC_Core;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawnHandler
{
	@SubscribeEvent
	public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event)
	{
		EntityLivingBase entity = event.entityLiving;

		int x = (int)entity.posX >> 4;
		int z = (int)entity.posZ >> 4;

		ChunkData data = ChunkDataManager.getData(x, z);
		if(!(data == null || data.getSpawnProtectionWithUpdate() <= 0))
			event.setResult(Result.DENY);
	}

	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer && !event.entity.getEntityData().hasKey("hasSpawned"))
		{
			if(((EntityPlayer)event.entity).inventory.armorInventory.length !=5)
				((EntityPlayer)event.entity).inventory = TFC_Core.getNewInventory((EntityPlayer)event.entity);

			((EntityPlayer)event.entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000);
			((EntityPlayer)event.entity).setHealth(1000);
			event.entity.getEntityData().setBoolean("hasSpawned", true);
		}

		if (event.entity instanceof EntityPlayer)
		{
			if(((EntityPlayer)event.entity).inventory.armorInventory.length !=5)
				((EntityPlayer)event.entity).inventory = TFC_Core.getNewInventory((EntityPlayer)event.entity);

			((EntityPlayer)event.entity).inventoryContainer = new ContainerPlayerTFC(((EntityPlayer)event.entity).inventory, !event.world.isRemote, (EntityPlayer)event.entity);
			((EntityPlayer)event.entity).openContainer = ((EntityPlayer)event.entity).inventoryContainer;
		}
	}
}
