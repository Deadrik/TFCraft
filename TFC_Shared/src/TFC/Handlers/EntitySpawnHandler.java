package TFC.Handlers;

import java.util.ArrayList;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Entities.Mobs.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class EntitySpawnHandler
{
	@ForgeSubscribe
	public void onEntityLivingUpdate(LivingSpawnEvent event) 
	{
		EntityLiving entity = event.entityLiving;

		if (entity instanceof EntitySheepTFC)
		{
			((EntitySheepTFC)entity).setFleeceColor(EntitySheepTFC.getRandomFleeceColor(entity.worldObj.rand));
		}
	}

	@ForgeSubscribe
	public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) 
	{
		EntityLiving entity = event.entityLiving;

		int x = (int)entity.posX >> 4;
		int z = (int)entity.posZ >> 4;

		ChunkData data = (ChunkData) ChunkDataManager.chunkmap.get(x + "," + z);
		if(!(data == null || data.getSpawnProtectionWithUpdate() <= 0))
		{
			event.setResult(Result.DENY);
		}
	}
}
