package com.bioxx.tfc.Entities.Mobs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class EntityIronGolemTFC extends EntityIronGolem
{
	private int attackTimer;

	public EntityIronGolemTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.IRON_GOLEM_HEALTH);//MaxHealth
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.attackTimer > 0)
			--this.attackTimer;

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0)
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D - this.yOffset);
			int z = MathHelper.floor_double(this.posZ);
			Block block = this.worldObj.getBlock(x, y, z);
			int meta = this.worldObj.getBlockMetadata(x, y, z);
			// Fix for invisible grass texture
			if(block == TFCBlocks.grass || block == TFCBlocks.grass2 ||
					block == TFCBlocks.clayGrass || block == TFCBlocks.clayGrass2 ||
					block == TFCBlocks.peatGrass ||
					block == TFCBlocks.dryGrass || block == TFCBlocks.dryGrass2)
			{
				block = TFCBlocks.dirt;
				meta = 1;
			}

			if (block.getMaterial() != Material.air)
				this.worldObj.spawnParticle("tilecrack_" + Block.getIdFromBlock(block) + "_" + meta, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.boundingBox.minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte)4);
		boolean var2 = entity.attackEntityFrom(DamageSource.causeMobDamage(this), TFC_MobData.IRON_GOLEM_DAMAGE + this.rand.nextInt(150));

		if (var2)
			entity.motionY += 0.4000000059604645D;

		this.worldObj.playSoundAtEntity(this, "mob.irongolem.throw", 1.0F, 1.0F);
		return var2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte par1)
	{
		super.handleHealthUpdate(par1);
		if (par1 == 4)
			this.attackTimer = 10;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getAttackTimer()
	{
		return this.attackTimer;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		Random ran = new Random();
		int k = 3 + ran.nextInt(3);
		for (int l = 0; l < k; ++l)
			this.dropItem(TFCItems.wroughtIronIngot, 1);
	}
}
