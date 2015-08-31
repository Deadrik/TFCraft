package com.bioxx.tfc.Entities.AI;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Entities.IAnimal;

public class AIEatGrass extends EntityAIBase
{
	private EntityLiving theEntity;
	private World theWorld;

	/** A decrementing tick used for the sheep's head offset and animation. */
	private int eatGrassTick;

	public AIEatGrass(IAnimal animal)
	{
		this.theEntity = (EntityLiving)animal;
		this.theWorld = theEntity.worldObj;
		this.setMutexBits(7);
	}

	@Override
	public boolean shouldExecute() 
	{
		IAnimal animal = (IAnimal)theEntity;
		if(animal.getHunger() < 144000 && theWorld.rand.nextInt(10) == 0)
		{
			int i = MathHelper.floor_double(this.theEntity.posX);
			int j = MathHelper.floor_double(this.theEntity.posY);
			int k = MathHelper.floor_double(this.theEntity.posZ);
			boolean isGrass = TFC_Core.isLushGrass(theWorld.getBlock(i, j-1, k));
			boolean isTallGrass = this.theWorld.getBlock(i, j, k) == TFCBlocks.tallGrass && this.theWorld.getBlockMetadata(i, j, k) == 1;
			return isGrass || isTallGrass;
		}
		return false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.eatGrassTick = 40;
		this.theWorld.setEntityState(this.theEntity, (byte)10);
		this.theEntity.getNavigator().clearPathEntity();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		this.eatGrassTick = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting()
	{
		return this.eatGrassTick > 0;
	}

	public int getEatGrassTick()
	{
		return this.eatGrassTick;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		this.eatGrassTick = Math.max(0, this.eatGrassTick - 1);

		if (this.eatGrassTick == 1)
		{
			int i = MathHelper.floor_double(this.theEntity.posX);
			int j = MathHelper.floor_double(this.theEntity.posY);
			int k = MathHelper.floor_double(this.theEntity.posZ);

			Block grass = this.theWorld.getBlock(i, j - 1, k);

			if (this.theWorld.getBlock(i, j, k) == TFCBlocks.tallGrass)
			{
				this.theWorld.func_147480_a/*destroyBlock*/(i, j, k, false);
				this.theEntity.eatGrassBonus();
			}
			else if (TFC_Core.isLushGrass(grass))
			{
				this.theWorld.playAuxSFX(2001, i, j - 1, k, Block.getIdFromBlock(Blocks.grass));
				TFC_Core.convertGrassToDirt(theWorld, i, j-1, k);
				this.theEntity.eatGrassBonus();
			}
		}
	}
}
