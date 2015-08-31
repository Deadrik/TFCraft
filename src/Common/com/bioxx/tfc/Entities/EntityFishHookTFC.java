package com.bioxx.tfc.Entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityFishTFC;
import com.bioxx.tfc.Items.Tools.ItemCustomFishingRod;

public class EntityFishHookTFC extends EntityFishHook
{
	/** The tile this entity is on, X position */
	private int xTile;

	/** The tile this entity is on, Y position */
	private int yTile;

	/** The tile this entity is on, Z position */
	private int zTile;
	private Block inTile;
	private boolean inGround;

	private int ticksInGround;
	private int ticksInAir;

	/** the number of ticks remaining until this fish can no longer be caught */
	private int ticksCatchable;

	private int fishPosRotationIncrements;
	private double fishX;
	private double fishY;
	private double fishZ;
	private double fishYaw;
	private double fishPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	private double maxDistance = -1;

	private boolean canCatchFish;

	public double pullX,pullY,pullZ;

	private int lineTension;
	private static final int MAX_LINE_TENSION = 800;

	private int reelCounter;
	private int lastCheckTick;
	
	private boolean lineTensionSnap;

	public EntityFishHookTFC(World par1World)
	{
		super(par1World);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.setSize(0.25F, 0.25F);
		this.ignoreFrustumCheck = true;
	}

	@SideOnly(Side.CLIENT)
	public EntityFishHookTFC(World par1World, double par2, double par4, double par6, EntityPlayer par8EntityPlayer)
	{
		this(par1World);
		this.setPosition(par2, par4, par6);
		this.ignoreFrustumCheck = true;
		this.field_146042_b = par8EntityPlayer;
		par8EntityPlayer.fishEntity = this;
	}

	public double getMaxDistance(){
		return maxDistance;
	}

	public EntityFishHookTFC(World par1World, EntityPlayer par2EntityPlayer)
	{
		super(par1World);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.ignoreFrustumCheck = true;
		this.field_146042_b = par2EntityPlayer;
		this.field_146042_b.fishEntity = this;
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(par2EntityPlayer.posX, par2EntityPlayer.posY + 1.62D - par2EntityPlayer.yOffset, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float f = 0.4F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.func_146035_c(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	public EntityFishHookTFC(World par1World, EntityPlayer par2EntityPlayer, int ticks)
	{
		super(par1World);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.ignoreFrustumCheck = true;
		this.field_146042_b = par2EntityPlayer;
		this.field_146042_b.fishEntity = this;
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(par2EntityPlayer.posX, par2EntityPlayer.posY + 1.62D - par2EntityPlayer.yOffset, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float f = 0.4F;
		float tickRatio = Math.min(ticks,60) / 20f;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * f;
		this.func_146035_c(this.motionX, this.motionY, this.motionZ, tickRatio/*1.5F*/, 1.0F);
	}

	@Override
	protected void entityInit() {}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
	 * length * 64 * renderDistanceWeight Args: distance
	 */
	public boolean isInRangeToRenderDist(double par1)
	{
		double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return par1 < d1 * d1;
	}

	@Override
	public void func_146035_c/*calculateVelocity*/(double par1, double par3, double par5, float par7, float par8)
	{
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		par1 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par3 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par5 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, f3) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		this.fishX = par1;
		this.fishY = par3;
		this.fishZ = par5;
		this.fishYaw = par7;
		this.fishPitch = par8;
		this.fishPosRotationIncrements = par9;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double par1, double par3, double par5)
	{
		this.velocityX = this.motionX = par1;
		this.velocityY = this.motionY = par3;
		this.velocityZ = this.motionZ = par5;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		this.onEntityUpdate();

		if(this.getDistanceToEntity(this.field_146042_b)<1){
			this.setDead();
			if(this.field_146042_b.getHeldItem() != null){
				ItemStack itemstack = this.field_146042_b.getHeldItem();
				if(itemstack.stackTagCompound == null){
					itemstack.stackTagCompound = new NBTTagCompound();
				}
				itemstack.stackTagCompound.setLong("tickReeledIn", TFC_Time.getTotalTicks());
			}
		}

		if (this.fishPosRotationIncrements > 0)
		{
			double d0 = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
			double d1 = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
			double d2 = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
			double d3 = MathHelper.wrapAngleTo180_double(this.fishYaw - this.rotationYaw);
			this.rotationYaw = (float)(this.rotationYaw + d3 / this.fishPosRotationIncrements);
			this.rotationPitch = (float)(this.rotationPitch + (this.fishPitch - this.rotationPitch) / this.fishPosRotationIncrements);
			--this.fishPosRotationIncrements;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
		else
		{
			if (!this.worldObj.isRemote)
			{
				ItemStack itemstack = this.field_146042_b.getCurrentEquippedItem();

				if (this.field_146042_b.isDead || !this.field_146042_b.isEntityAlive() || itemstack == null || !(itemstack.getItem() instanceof ItemCustomFishingRod) || this.getDistanceSqToEntity(this.field_146042_b) > 2500.0D)
				{
					this.setDead();
					this.field_146042_b.fishEntity = null;
					return;
				}

				if (this.field_146043_c != null)
				{
					if (!this.field_146043_c.isDead)
					{
						this.posX = this.field_146043_c.posX;
						this.posY = this.field_146043_c.boundingBox.minY + this.field_146043_c.height * 0.8D;
						this.posZ = this.field_146043_c.posZ;
						return;
					}
					this.field_146043_c = null;
				}
			}

			if (this.field_146044_a > 0)
			{
				--this.field_146044_a;
			}

			if (this.inGround)
			{
				if (this.worldObj.getBlock(this.xTile, this.yTile, this.zTile) == this.inTile)
				{
					++this.ticksInGround;
					if (this.ticksInGround == 1200)
						this.setDead();
					return;
				}

				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
			else
			{
				++this.ticksInAir;
			}

			Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
			vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (movingobjectposition != null)
				vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);

			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d4 = 0.0D;
			double d5;

			for (int j = 0; j < list.size(); ++j)
			{
				Entity entity1 = (Entity)list.get(j);
				if (entity1.canBeCollidedWith() && (entity1 != this.field_146042_b || this.ticksInAir >= 5))
				{
					float f = 0.3F;
					AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
					if (movingobjectposition1 != null)
					{
						d5 = vec3.distanceTo(movingobjectposition1.hitVec);
						if (d5 < d4 || d4 == 0.0D)
						{
							entity = entity1;
							d4 = d5;
						}
					}
				}
			}

			if (entity != null)
				movingobjectposition = new MovingObjectPosition(entity);

			if (movingobjectposition != null)
			{
				/*if (movingobjectposition.entityHit != null)
				{
					//if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.field_146042_b), 0.0F))
					//this.field_146043_c = movingobjectposition.entityHit;
				}
				else*/ if (movingobjectposition.entityHit == null)
				{
					this.inGround = true;
				}
			}

			if (!this.inGround)
			{
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

				for (this.rotationPitch = (float)(Math.atan2(this.motionY, f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
					;

				while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
					this.prevRotationPitch += 360.0F;

				while (this.rotationYaw - this.prevRotationYaw < -180.0F)
					this.prevRotationYaw -= 360.0F;

				while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
					this.prevRotationYaw += 360.0F;

				this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
				this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
				float f2 = 0.92F;

				if (this.onGround || this.isCollidedHorizontally)
					f2 = 0.5F;

				byte b0 = 5;
				double d6 = 0.0D;

				for (int k = 0; k < b0; ++k)
				{
					double d7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (k + 0) / b0 - 0.125D + 0.125D;
					double d8 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (k + 1) / b0 - 0.125D + 0.125D;
					AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d7, this.boundingBox.minZ, this.boundingBox.maxX, d8, this.boundingBox.maxZ);

					if (this.worldObj.isAABBInMaterial(axisalignedbb1, Material.water))
						d6 += 1.0D / b0;
				}

				if (this.ticksCatchable > 0)
					this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2D;

				d5 = d6 * 2.0D - 1.0D;
				this.motionY += 0.03999999910593033D * d5;

				if (d6 > 0.0D)
				{
					if(this.maxDistance == -1){
						this.maxDistance = this.getDistanceToEntity(field_146042_b);
						this.canCatchFish = true;
					}
					if(canCatchFish && !this.worldObj.isRemote){
						this.attemptToCatch();
					}
					f2 = (float)(f2 * 0.9D);
					this.motionY *= 0.8D;
				}

				this.motionX *= f2;
				this.motionY *= f2;
				this.motionZ *= f2;

				double distance = this.getDistanceToEntity(this.field_146042_b);
				if(distance > this.maxDistance && maxDistance != -1){
					Vec3 distVec = Vec3.createVectorHelper(this.posX - this.field_146042_b.posX,this.posY - this.field_146042_b.posY,this.posZ - this.field_146042_b.posZ);
					double distRatio = maxDistance / distance;
					this.posX = this.field_146042_b.posX + (distVec.xCoord * distRatio);
					this.posY = this.field_146042_b.posY + (distVec.yCoord * distRatio);
					this.posZ = this.field_146042_b.posZ + (distVec.zCoord * distRatio);
				}
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}
		/*double distance = this.getDistanceToEntity(this.field_146042_b);
		if(distance > this.maxDistance){
			Vec3 distVec = Vec3.createVectorHelper(this.posX - this.field_146042_b.posX,this.posY - this.field_146042_b.posY,this.posZ - this.field_146042_b.posZ);
			double distRatio = maxDistance / distance;
			this.posX = this.field_146042_b.posX + (distVec.xCoord * distRatio);
			this.posY = this.field_146042_b.posY + (distVec.yCoord * distRatio);
			this.posZ = this.field_146042_b.posZ + (distVec.zCoord * distRatio);
		}*/
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)this.xTile);
		par1NBTTagCompound.setShort("yTile", (short)this.yTile);
		par1NBTTagCompound.setShort("zTile", (short)this.zTile);
		par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.inTile));
		par1NBTTagCompound.setByte("shake", (byte)this.field_146044_a/*shake*/);
		par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");
		this.inTile = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
		this.field_146044_a/*shake*/ = par1NBTTagCompound.getByte("shake") & 255;
		this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
	}

	public Vec3 applyEntityForce(Vec3 entityForce, double x, double y, double z){
		Vec3 pullVec = Vec3.createVectorHelper(pullX, pullY, pullZ);

		double force = pullVec.distanceTo(entityForce);
		Vec3 netForceVec = entityForce.addVector(pullX, pullY, pullZ);
		double forceRatio = (force * 30) / netForceVec.lengthVector();

		if(TFC_Time.getTotalTicks()%40 == 0){
			force += 0d;
		}
		lineTension += (forceRatio -31 > 1 ? Math.sqrt(forceRatio - 31) : forceRatio - 31);

		lineTension = Math.max(lineTension, 0);

		ItemStack is = this.field_146042_b.getHeldItem();
		if(is != null && is.getItem() instanceof ItemCustomFishingRod){
			if(!is.hasTagCompound()){
				is.setTagCompound(new NBTTagCompound());
			}
			if(this.reelCounter > 2){
				is.stackTagCompound.setInteger("tension", (int) (((forceRatio)-29) + (Math.pow(entityForce.lengthVector() / 0.2,3)*2))*100);
			}
			else{
				this.reelCounter++;
			}
		}
		if(forceRatio != 30){
			reelCounter = 0;
		}
		if(lineTension >= MAX_LINE_TENSION / 2)
		{
			this.maxDistance += pullVec.lengthVector() * 0.3;
		}
		if(lineTension > MAX_LINE_TENSION * 0.8 && !lineTensionSnap){
			lineTensionSnap = true;
			TFC_Core.sendInfoMessage(this.field_146042_b, new ChatComponentTranslation("fishingRod.lineTension"));
		}
		else if(lineTension < MAX_LINE_TENSION * 0.8){
			lineTensionSnap = false;
		}
		if(lineTension >= MAX_LINE_TENSION){
			this.field_146042_b.getCurrentEquippedItem().damageItem(20, field_146042_b);
			this.ridingEntity.riddenByEntity = null;
			this.ridingEntity = null;
			//((EntityLiving)this.ridingEntity).dismountEntity(this);
			TFC_Core.sendInfoMessage(this.field_146042_b, new ChatComponentTranslation("fishingRod.lineSnap"));
			this.setDead();
		}
		this.pullX = 0;
		this.pullY = 0;
		this.pullZ = 0;

		//this.motionX = netForceVec.xCoord;
		//this.motionY = netForceVec.yCoord;
		//this.motionZ = netForceVec.zCoord;

		//this.posX += netForceVec.xCoord;
		//this.posY += worldObj.isAirBlock((int)posX, (int)posY +1, (int)posZ)?0:netForceVec.yCoord;
		//this.posZ += netForceVec.zCoord;

		//netForceVec.addVector(distVec.xCoord * distRatio, distVec.yCoord * distRatio, distVec.zCoord * distRatio);
		//this.field_146042_b.addChatMessage(new ChatComponentText("tension: " + lineTension + ", force ratio: " + forceRatio +", distance: " + this.getDistanceToEntity(field_146042_b) + ", max: "+this.maxDistance));
		return Vec3.createVectorHelper(netForceVec.xCoord, (worldObj.isAirBlock((int)x, (int)y +1, (int)z) && netForceVec.yCoord > 0?0:netForceVec.yCoord), netForceVec.zCoord);
	}

	public Vec3 getNormalDirectionOfPlayer(double x, double y, double z){
		Vec3 dirVec = Vec3.createVectorHelper(this.field_146042_b.posX - x, this.field_146042_b.posY - y, this.field_146042_b.posZ - z);
		return dirVec.normalize();
	}

	public Vec3 getTooFarAdjustedVec(Vec3 motionVec, double x, double y, double z){
		Vec3 playerMotion = Vec3.createVectorHelper(this.field_146042_b.motionX, this.field_146042_b.motionY, this.field_146042_b.motionZ);
		double subractedRatio = Math.max(1 - (this.maxDistance / this.field_146042_b.getDistance(x + playerMotion.xCoord, y + playerMotion.yCoord, z + playerMotion.zCoord)),0);
		return Vec3.createVectorHelper((this.field_146042_b.posX + playerMotion.xCoord - (motionVec.xCoord + x)) *
										subractedRatio, (this.field_146042_b.posY + playerMotion.yCoord - (motionVec.yCoord + y)) *
														subractedRatio, (this.field_146042_b.posZ + playerMotion.zCoord - (motionVec.zCoord + z)) *
																		subractedRatio);
	}

	public void attemptToCatch(){
		int fishPopulation = this.getAverageFishPopFromChunks();
		if(this.lastCheckTick == 0){
			int maxValue = (int)(ChunkData.FISH_POP_MAX * 1.2f);
			int minValue = 0;
			int hour = TFC_Time.getHour();
			if (hour >= 3 && hour <= 9 || hour >= 17 && hour < 22)
			{
				minValue = 1;
			}
			if(this.rand.nextInt(maxValue - fishPopulation) <= minValue){
				this.func_146034_e();
			}
			lastCheckTick = 20;
		}
		else{
			this.lastCheckTick--;
		}
	}

	public boolean isTooFarFromPlayer(double x, double y, double z){
		return this.field_146042_b.getDistance(x,y,z) > this.maxDistance;
	}

	public void reelInBobber(Entity entity, ItemStack itemstack){
		double distance = this.getDistanceToEntity(entity);
		if(distance < maxDistance){
			this.maxDistance-= 0.2;
			//this.pullX = 0;
			//this.pullY = 0;
			//this.pullZ = 0;
		}
		if(distance > 1.5){

			this.pullX = (entity.posX - posX)/distance;
			this.pullY = (entity.posY - posY)/distance;
			this.pullZ = (entity.posZ - posZ)/distance;

			if(this.ridingEntity==null){
				this.motionX += pullX * 0.2;
				this.motionY += pullY * 0.2;
				this.motionZ += pullZ * 0.2;
			}
		}
		else{
			this.setDeadKill();
			if(itemstack.stackTagCompound == null){
				itemstack.stackTagCompound = new NBTTagCompound();
			}
			itemstack.stackTagCompound.setLong("tickReeledIn", TFC_Time.getTotalTicks());
		}
	}

	public int getAverageFishPopFromChunks(){
		if (this.worldObj.isRemote)
		{
			return 0;
		}
		else
		{
			EntityPlayer player = this.field_146042_b;
			int lastChunkX = ((int) Math.floor(player.posX)) >> 4;
			int lastChunkZ = ((int) Math.floor(player.posZ)) >> 4;

			int chunksVisited = 0;
			int totalFish = TFC_Core.getCDM(worldObj).getFishPop(lastChunkX, lastChunkZ);
			if(totalFish > 0){
				chunksVisited++;
			}
			else{
				return 0;
			}

			int maxChunksVisitable = 20;
			for(int radius = 1; radius < 5 && chunksVisited < maxChunksVisitable; radius++){
				for(int i = -radius; i <= radius; i++)
				{
					for(int k = -radius; k <= radius; k+=(Math.abs(i)==radius?1:radius*2))
					{
						int tempFish = TFC_Core.getCDM(worldObj).getFishPop(lastChunkX + i, lastChunkZ + k);
						if(tempFish > 0){
							chunksVisited++;
							totalFish += tempFish;
						}
					}
				}
			}
			return totalFish / chunksVisited;
		}
	}

	@Override
	public int func_146034_e/*catchFish*/()
	{
		if (this.worldObj.isRemote)
		{
			return 0;
		}
		else
		{
			EntityPlayer player = this.field_146042_b;
			EntityFishTFC fish = new EntityFishTFC(this.worldObj);
			fish.setPosition(posX, posY-0.3, posZ);
			this.worldObj.spawnEntityInWorld(fish);
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("fishingRod.bite"));
			this.mountEntity(fish);

			this.canCatchFish = false;

			int lastChunkX = ((int) Math.floor(player.posX)) >> 4;
			int lastChunkZ = ((int) Math.floor(player.posZ)) >> 4;
			int maxChunksVisitable = 20;
			TFC_Core.getCDM(worldObj).catchFish(lastChunkX, lastChunkZ);
			int chunksVisited = 1;
			for(int radius = 1; radius < 5 && chunksVisited < maxChunksVisitable; radius++){
				for(int i = -radius; i <= radius; i++)
				{
					for(int k = -radius; k <= radius; k+=(Math.abs(i)==radius?1:radius*2))
					{
						if(TFC_Core.getCDM(worldObj).catchFish(lastChunkX + i, lastChunkZ + k)){
							chunksVisited++;
						}
					}
				}
			}
			return 0;
		}
	}

	public void setDeadKill(){
		if(this.ridingEntity!=null && this.ridingEntity instanceof EntityLiving){
			((EntityLiving)(this.ridingEntity)).setHealth(1);
			((EntityLiving)(this.ridingEntity)).attackEntityFrom(new EntityDamageSource("fishing", field_146042_b), 1);
			this.field_146042_b.addStat(StatList.fishCaughtStat, 1);
		}
		this.ridingEntity = null;
		this.setDead();
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead()
	{
		if(this.ridingEntity != null){
			EntityLiving e = ((EntityLiving)this.ridingEntity);
			e.setDead();
		}
		super.setDead();
		this.lineTension = 0;
		this.maxDistance = -1;
		if (this.field_146042_b != null)
			this.field_146042_b.fishEntity = null;
	}
}
