package TFC.Entities.AI;

import java.util.*;

import TFC.*;
import TFC.Entities.EntityAnimalTFC;
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

public class EntityAIMoveTowardsFood extends EntityAIBase
{
	private EntityAnimalTFC theEntity;
	private Entity targetEntity;
	private double movePosX;
	private double movePosY;
	private double movePosZ;
	private float field_48330_f;
	private float field_48331_g;
	private Random rand = new Random();

	public EntityAIMoveTowardsFood (EntityAnimalTFC par1EntityCreature, float par2, float par3)
	{
		theEntity = par1EntityCreature;
		field_48330_f = par2;
		field_48331_g = par3;
		setMutexBits (1);
	}


	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute ()
	{
		targetEntity = null;
		List <EntityItem> closeItems = theEntity.worldObj.getEntitiesWithinAABB (EntityItem.class, theEntity.boundingBox.expand (10D, 2D, 10D));
		for (EntityItem first:
			closeItems)
		{
			for (int second:
				theEntity.fooditems)
			{
				if (((int)first.func_92014_d().itemID) == second)
				{
					targetEntity = first;
				}
			}
			if (targetEntity != null){
				break;
			}
		}

		if (targetEntity == null || theEntity.hunger > 200000)
		{
			return false;
		}

		if (targetEntity.getDistanceSqToEntity (theEntity) > (double) (field_48331_g * field_48331_g))
		{
			return false;
		}

		if (targetEntity.getDistanceSqToEntity(theEntity)<=1D){
			if(theEntity.wantsItem(((EntityItem)targetEntity).func_92014_d())){
				targetEntity.worldObj.playSoundAtEntity(targetEntity, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				targetEntity.setDead();
			}
		}
		
		Vec3 vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 10, 10, Vec3.createVectorHelper(targetEntity.posX, targetEntity.posY, targetEntity.posZ));

		if (vec3d == null)
		{
			return false;
		}
		else
		{
			movePosX = vec3d.xCoord;
			movePosY = vec3d.yCoord;
			movePosZ = vec3d.zCoord;
			return true;
		}
	}


	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting ()
	{
		
		return !theEntity.getNavigator ().noPath () && targetEntity.getDistanceSqToEntity (theEntity) < (double) (field_48331_g * field_48331_g);
	}


	/**
	 * Resets the task
	 */
	public void resetTask ()
	{
		targetEntity = null;
	}


	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting ()
	{
		theEntity.getNavigator ().tryMoveToXYZ /*(movePosX, movePosY, movePosZ*/(targetEntity.posX, targetEntity.posY, targetEntity.posZ, field_48330_f);
	}
}
