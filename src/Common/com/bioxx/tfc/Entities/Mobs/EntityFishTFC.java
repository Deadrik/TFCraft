package com.bioxx.tfc.Entities.Mobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.EntityFishHookTFC;

public class EntityFishTFC extends EntitySquid
{

	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;

	private float randomMotionSpeed;

	/** change in squidRotation in radians. */
	private float rotationVelocity;
	private float field_70871_bB;

	private float prevDirection = 0;

	private List<EntityPlayer> nearbyPlayers;
	private List<EntityFishHookTFC> nearbyLures;

	private boolean hooked = false;
	private int energy = 200;

	double pullX,pullY,pullZ;

	public EntityFishTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.4F, 0.4F);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere()
	{
		return this.posY > 128.0D && this.posY < 145.0D && this.worldObj.checkNoEntityCollision(this.boundingBox);
	}

	@Override
	public boolean isInWater()
	{
		return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.0200000238418579D, 0.0D), Material.water, this);
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		this.prevSquidPitch = this.squidPitch;
		this.prevSquidYaw = this.squidYaw;
		this.prevSquidRotation = this.squidRotation;
		this.lastTentacleAngle = this.tentacleAngle;
		this.squidRotation += this.rotationVelocity;

		if(this.riddenByEntity != null){
			if(!hooked){
				energy = 200;
				hooked = true;
			}
		}
		else{
			hooked = false;
		}
		
		if(hooked && this.riddenByEntity instanceof EntityFishHookTFC){
			pullX = ((EntityFishHookTFC)(this.riddenByEntity)).pullX;
			pullY = ((EntityFishHookTFC)(this.riddenByEntity)).pullY;
			pullZ = ((EntityFishHookTFC)(this.riddenByEntity)).pullZ;
			
			energy -= pullX;
			energy -= pullY;
			energy -= pullZ;
			if(energy>0){
				if(this.randomMotionVecX * pullX >0){
					this.randomMotionVecX*=-1;
				}
				if(this.randomMotionVecY * pullY >0){
					this.randomMotionVecY*=-1;
				}
				if(this.randomMotionVecZ * pullZ >0){
					this.randomMotionVecZ*=-1;
				}
				
				this.randomMotionVecX*=1.1;
				this.randomMotionVecY*=1.1;
				this.randomMotionVecZ*=1.1;
				
				((EntityFishHookTFC)(this.riddenByEntity)).pullX = 0;
				((EntityFishHookTFC)(this.riddenByEntity)).pullY = 0;
				((EntityFishHookTFC)(this.riddenByEntity)).pullZ = 0;
			}
				this.randomMotionVecX += (float) pullX;
				this.randomMotionVecY += (float) pullY;
				this.randomMotionVecZ += (float) pullZ;

			if(!this.isInWater()){
				this.motionX = (double)(this.randomMotionVecX * 0.7);
				this.motionY = (double)(this.randomMotionVecY * 0.7);
				this.motionZ = (double)(this.randomMotionVecZ * 0.7);
			}
		}

		if (this.squidRotation > ((float)Math.PI * 2F))
		{
			this.squidRotation -= ((float)Math.PI * 2F);

			if (this.rand.nextInt(10) == 0)
			{
				this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
			}
		}

		if (this.isInWater())
		{
			float f;

			if (this.squidRotation < (float)Math.PI)
			{
				f = this.squidRotation / (float)Math.PI;
				this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;

				if ((double)f > 0.75D)
				{
					this.randomMotionSpeed = 1.0F;
					this.field_70871_bB = 1.0F;
				}
				else
				{
					this.field_70871_bB *= 0.8F;
				}
			}
			else
			{
				this.tentacleAngle = 0.0F;
				this.randomMotionSpeed *= 0.9F;
				this.field_70871_bB *= 0.99F;
			}

			if (!this.worldObj.isRemote)
			{
				this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
				this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
				this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
			}

			f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI - this.renderYawOffset) * 0.1F;
			this.rotationYaw = this.renderYawOffset;
			this.squidYaw += (float)Math.PI * this.field_70871_bB * 1.5F;
			this.squidPitch += (-((float)Math.atan2((double)f, this.motionY)) * 180.0F / (float)Math.PI - this.squidPitch) * 0.1F;
		}
		else
		{
			this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;

			if (!this.worldObj.isRemote)
			{
				this.motionX = 0.0D;
				this.motionY -= 0.08D;
				this.motionY *= 0.9800000190734863D;
				this.motionZ = 0.0D;
			}

			this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
		}
	}

	/**
	 * Moves the entity based on the specified heading.  Args: strafe, forward
	 */
	public void moveEntityWithHeading(float par1, float par2)
	{
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}

	protected void updateEntityActionState()
	{
		++this.entityAge;

		int[] destination = getRandomDestination(this.posX,this.posY,this.posZ);

		if (this.entityAge > 100)
		{
			this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ = 0.0F;
		}
		else if (this.rand.nextInt(50) == 0 || !this.inWater || this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F)
		{
			/*float f = (this.rand.nextFloat() * (float)Math.PI * 0.0833F)*(this.rand.nextBoolean()?-1:1)+prevDirection;
            this.randomMotionVecX = MathHelper.cos(f) * 0.2F;
            this.randomMotionVecY = -0.1F + this.rand.nextFloat() * 0.2F;
            this.randomMotionVecZ = MathHelper.sin(f) * 0.2F;
            this.prevDirection = f;*/
			double distance = this.getDistance((double)destination[0], (double)destination[1],(double) destination[2]);
			if(distance!=0){
				this.randomMotionVecX = (float) ((destination[0]-this.posX)/distance)*0.2f;
				this.randomMotionVecY = (float) ((destination[1]-this.posY)/distance)*0.2f;
				this.randomMotionVecZ = (float) ((destination[2]-this.posZ)/distance)*0.2f;
			}
			else{
				this.randomMotionVecX = 0f;
				this.randomMotionVecY = 0f;
				this.randomMotionVecZ = 0f;
			}
		}

		this.despawnEntity();
	}

	//Given the entities current position, find a new location for it to move towards. Allows us to define
	//locations that the entity doesn't want to be in.
	private int[] getRandomDestination(double x, double y, double z){
		int destX,destY,destZ;
		destX = (int)x;destY = (int)y; destZ = (int)z;
		boolean needsNewLocation = (this.rand.nextInt(25)>3) || !(TFC_Core.isWater(this.worldObj.getBlock(destX, destY-1, destZ)));
		int numAttempts = 0;
		nearbyPlayers = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(16, 8, 16));
		nearbyLures = this.worldObj.getEntitiesWithinAABB(EntityFishHookTFC.class, this.boundingBox.expand(8, 8, 8));
		ArrayList<EntityFishHookTFC> unavailableLures = new ArrayList<EntityFishHookTFC>();
		for(EntityFishHookTFC fh : nearbyLures){
			if(fh.ridingEntity!=null){
				unavailableLures.add(fh);
			}
		}
		nearbyLures.removeAll(unavailableLures);
		boolean tooCloseToPlayer = false;
		while(needsNewLocation && numAttempts < 255){
			numAttempts++;
			if(nearbyLures.size()>0 && !tooCloseToPlayer){
				EntityFishHookTFC desiredLure = nearbyLures.get(this.rand.nextInt(nearbyLures.size()));
				destX = (int)desiredLure.posX;
				destY = (int)desiredLure.posY;
				destZ = (int)desiredLure.posZ;

				if(this.getDistanceToEntity(desiredLure)<1){
					destX += (desiredLure.posX-posX)*2;
					destY += (desiredLure.posY-posY)*2;
					destZ += (desiredLure.posY-posY)*2;
				}
				if(this.getDistanceToEntity(desiredLure)<0.45){
					desiredLure.mountEntity(this);
				}
			}
			else{
				destX = (int)x + (this.rand.nextInt(10) * (this.rand.nextBoolean()?-1:1));
				destY = (int)y + (this.rand.nextInt(6) * (this.rand.nextBoolean()?-1:1));
				destZ = (int)z + (this.rand.nextInt(10) * (this.rand.nextBoolean()?-1:1));
			}
			for(EntityPlayer p : nearbyPlayers){
				if(p.getDistance((double)destX, (double)destY, (double)destZ)<8){
					tooCloseToPlayer = true;
				}
			}
			needsNewLocation = !(TFC_Core.isWater(this.worldObj.getBlock(destX, destY, destZ))) ||
					!(TFC_Core.isWater(this.worldObj.getBlock(destX, destY-1, destZ))) || tooCloseToPlayer;
		}
		return new int[]{destX,destY,destZ};
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400);//MaxHealth
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	 * par2 - Level of Looting used to kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		TFC_Core.animalDropMeat(this, TFCItems.fishRaw,32);
	}
}
