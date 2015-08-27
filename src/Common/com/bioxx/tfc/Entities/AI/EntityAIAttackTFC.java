package com.bioxx.tfc.Entities.AI;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

import com.bioxx.tfc.api.Entities.IAnimal;

public class EntityAIAttackTFC extends  EntityAINearestAttackableTarget{
	//private final IAnimal theAnimal;

	public EntityAIAttackTFC(IAnimal par1EntityTameable, Class par2Class, int par3, boolean par4)
	{
		super((EntityCreature)par1EntityTameable, par2Class, par3, par4);
		//this.theAnimal = par1EntityTameable;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	/*@Override
	public boolean shouldExecute()
	{
		return  super.shouldExecute();
	}*/
}
