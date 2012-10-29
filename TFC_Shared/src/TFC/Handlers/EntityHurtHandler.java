package TFC.Handlers;

import java.util.ArrayList;

import TFC.Entities.Mobs.EntitySheepTFC;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EntityHurtHandler
{
	@ForgeSubscribe
	public void onEntityHurt(LivingHurtEvent event) 
	{
		EntityLiving entity = event.entityLiving;
		
		if(event.source == DamageSource.onFire)
		{
			event.ammount = 50;
		}
		else if(event.source == DamageSource.fall)
		{
			event.ammount *= 50;
		}
		else if(event.source == DamageSource.drown)
        {
			event.ammount = 50;
        }
        else if(event.source == DamageSource.lava)
        {
        	event.ammount = 100;
        }
	}
}
