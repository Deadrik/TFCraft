package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;

public class EntitySpiderTFC extends EntitySpider implements ICausesDamage
{
	public EntitySpiderTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.SPIDER_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.SPIDER_HEALTH);//MaxHealth
	}

	@Override
	public EnumDamageType getDamageType()
	{
		return EnumDamageType.PIERCING;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntitySkeleton && !worldObj.isRemote)
		{
			EntitySkeleton es = (EntitySkeleton)this.riddenByEntity;
			es.dismountEntity(es.ridingEntity);
			es.setDead();
		}
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
