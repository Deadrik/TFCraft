package TFC.Handlers;

import java.util.ArrayList;

import TFC.Entities.Mobs.EntitySheepTFC;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpecialSpawnEvent;

public class EntitySpawnHandler
{
	@ForgeSubscribe
	public void onEntityLivingUpdate(LivingSpecialSpawnEvent event) 
	{
		EntityLiving entity = event.entityLiving;
		
		if (entity instanceof EntitySheepTFC)
		{
			((EntitySheepTFC)entity).setFleeceColor(EntitySheepTFC.getRandomFleeceColor(entity.worldObj.rand));
		}
	}
}
