package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;

public class EntityCreeperTFC extends EntityCreeper implements IInnateArmor
{
	public EntityCreeperTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500);//MaxHealth
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		Entity entity = par1DamageSource.getEntity();
		if(entity != null && entity instanceof EntityPlayer && 
				((EntityPlayer) entity).getHeldItem() != null && ((EntityPlayer) entity).getHeldItem().getItem().equals(TFCItems.stick)){
			((EntityPlayer)entity).triggerAchievement(TFC_Achievements.achPokeCreeper);
		}
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public int getCrushArmor() {
		return 1000;
	}
	@Override
	public int getSlashArmor() {
		return 0;
	}
	@Override
	public int getPierceArmor() {
		return -335;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);
		Block b = this.worldObj.getBlock(x, y, z);

		if(b == TFCBlocks.leaves || b == TFCBlocks.leaves2 || b == TFCBlocks.thatch)
			return false;

		return super.getCanSpawnHere();
	}
}
