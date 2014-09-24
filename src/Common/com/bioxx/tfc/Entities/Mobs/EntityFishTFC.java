package com.bioxx.tfc.Entities.Mobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
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

	private boolean hooked = false;
	private int energy = 200;
	private int tiredTicks = 0;

	private int numRecoveries = 0;

	private int inGroundTimer = 0;

	public float currentRenderRoll = 0;
	public float currentRenderYaw = 0;
	public float currentRenderPitch = 0;

	private double renderMotionY, renderMotionX, renderMotionZ;

	private double fishStrength;

	double pullX,pullY,pullZ;

	public EntityFishTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.4F, 0.4F);
		this.fishStrength = ((rand.nextInt(40) /100d)) + 1d;
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
	protected void entityInit(){
		super.entityInit();
		this.dataWatcher.addObject(21, new Float(1));
		this.dataWatcher.addObject(22, new Float(1));
		this.dataWatcher.addObject(23, new Float(1));

		this.dataWatcher.addObject(26, new Float(1));
		this.dataWatcher.addObject(27, new Float(1));
		this.dataWatcher.addObject(28, new Float(1));
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
		if(!worldObj.isRemote){
			this.prevSquidPitch = this.squidPitch;
			this.prevSquidYaw = this.squidYaw;
			this.prevSquidRotation = this.squidRotation;
			this.lastTentacleAngle = this.tentacleAngle;
			this.squidRotation += this.rotationVelocity;

			if(this.riddenByEntity != null){
				if(!hooked){
					energy = 1000;
					hooked = true;
				}
			}
			else{
				hooked = false;
			}

			if(hooked && this.riddenByEntity instanceof EntityFishHookTFC && !this.worldObj.isRemote && this.isInWater()){
				EntityFishHookTFC fh = ((EntityFishHookTFC)(this.riddenByEntity));
				if(Vec3.createVectorHelper(fh.pullX, fh.pullY, fh.pullZ).lengthVector() != 0){
					Vec3 dirVec = Vec3.createVectorHelper(fh.pullX, fh.pullY, fh.pullZ).normalize();
					pullX = dirVec.xCoord*0.2;
					pullY = dirVec.yCoord*0.2;
					pullZ = dirVec.zCoord*0.2;
				}

				if(pullX == pullY && pullY == pullZ && pullZ == 0){
					Vec3 temp = fh.getNormalDirectionOfPlayer(posX, posY, posZ);
					pullX = temp.xCoord * 0.2;
					pullY = temp.yCoord * 0.2;
					pullZ = temp.zCoord * 0.2;
				}
				Vec3 pullVec = Vec3.createVectorHelper(pullX, pullY, pullZ);


				double pullForce = pullVec.lengthVector();

				double randX, randY, randZ;
				if(pullX != 0 && TFC_Core.isWater(worldObj.getBlock((int)(this.posX - (pullX / Math.abs(pullX))), (int)this.posY, (int)this.posZ))){
					randX = (rand.nextDouble() * 0.5) + 0.16;
				}
				else{
					randX = 0;
				}

				if(pullZ != 0 && TFC_Core.isWater(worldObj.getBlock((int)this.posX, (int)this.posY, (int)(this.posZ - (pullZ / Math.abs(pullZ)))))){
					randZ = (rand.nextDouble() * (1-randX - 0.09))+0.16;
				}
				else{
					randZ = 0;
				}
				if(pullX != 0 && TFC_Core.isWater(worldObj.getBlock((int)this.posX, (int)(this.posY - (pullY / (Math.abs(pullY)))), (int)this.posZ))){
					randY = 1 - (randX +randZ);
				}
				else{
					randY = 0;
				}
				randX *=0.33;
				randY *= 0.33;
				randZ *= 0.33;
				if(randY == randX && randX == randZ && randZ == 0){
					int choice = rand.nextInt(3);
					switch(choice){
					case 0:randX = -1; break;
					case 1:randY = -1; break;
					case 2:randZ = -1; break;
					default: randY = -1;break;
					}
				}
				randX += (randX != 0?0.7:0);
				randY += (randY != 0?0.7:0);
				randZ += (randZ != 0?0.7:0);
				double energyStrength = 0.05;

				if(energy>0){
					energyStrength = 1;
					tiredTicks = 0;
				}
				else if(tiredTicks > 80 && numRecoveries < 5){
					numRecoveries++;
					energy = (int)(1000 * Math.pow(0.9, numRecoveries));
				}
				else{
					tiredTicks++;
				}
				Vec3 fishVec = Vec3.createVectorHelper(-(pullX * randX)*fishStrength*energyStrength, -(pullY * randY)*fishStrength*energyStrength,-(pullZ * randZ)*fishStrength*energyStrength);
				renderMotionX = fishVec.xCoord;
				renderMotionY = fishVec.yCoord;
				renderMotionZ = fishVec.zCoord;
				if(energy > 0){
					energy -= fishVec.lengthVector();
				}
				Vec3 calcVec = ((EntityFishHookTFC)(this.riddenByEntity)).applyEntityForce(fishVec,posX, posY, posZ);

				this.randomMotionVecX += (float) calcVec.xCoord;
				this.randomMotionVecY += (float) calcVec.yCoord - 0.008f;
				this.randomMotionVecY *= 0.9800000190734863D;
				this.randomMotionVecZ += (float) calcVec.zCoord;
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
					this.motionX = (double)(this.randomMotionVecX * (hooked?1:this.randomMotionSpeed));
					this.motionY = (double)(this.randomMotionVecY * (hooked?1:this.randomMotionSpeed));
					this.motionZ = (double)(this.randomMotionVecZ * (hooked?1:this.randomMotionSpeed));
					this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ = 0;
				}

				f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI - this.renderYawOffset) * 0.1F;
				this.rotationYaw = this.renderYawOffset;
				this.squidYaw += (float)Math.PI * this.field_70871_bB * 1.5F;
				this.squidPitch += (-((float)Math.atan2((double)f, this.motionY)) * 180.0F / (float)Math.PI - this.squidPitch) * 0.1F;
			}
			else
			{

				this.motionX *= 0.8;//(double)(this.randomMotionVecX * 0.7);
				this.motionY -= 0.08D;
				this.motionY *= 0.9800000190734863D;
				this.motionZ *= 0.8;//(double)(this.randomMotionVecZ * 0.7);

				this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;

				if (!this.worldObj.isRemote)
				{
					if(this.inGroundTimer > 100 + rand.nextInt(20) && this.onGround){
						this.inGroundTimer = 0;
						this.motionY = 0.65;
						this.motionX = rand.nextDouble()*0.65 * (rand.nextBoolean()?1:-1);
						this.motionZ = rand.nextDouble()*0.65 * (rand.nextBoolean()?1:-1);
					}
					else if(this.onGround){
						if(this.riddenByEntity != null){
							this.dismountEntity(this.riddenByEntity);
						}
						this.inGroundTimer++;
						this.motionX *= 0.01;
						this.motionZ *= 0.01;
					}
				}

				this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
			}
			if(!hooked || !isInWater()){
				renderMotionX = posX != prevPosX?(this.posX - this.prevPosX):renderMotionX;
				renderMotionY = posY != prevPosY?(this.posY - this.prevPosY):renderMotionY;
				renderMotionZ = posZ != prevPosZ?(this.posZ - this.prevPosZ):renderMotionZ;
			}
			if(hooked && this.riddenByEntity instanceof EntityFishHookTFC){
				EntityFishHookTFC fh = ((EntityFishHookTFC)(this.riddenByEntity));
				for(int i = 0; i < 1; i++){
					if(fh.isTooFarFromPlayer(posX, posY, posZ)){
						Vec3 dirVec = fh.getTooFarAdjustedVec(Vec3.createVectorHelper(motionX, motionY, motionZ),posX, posY, posZ);
						this.moveEntity(dirVec.xCoord, dirVec.yCoord, dirVec.zCoord);
						this.motionX *= 0.7;
						this.motionY *= 0.7;
						this.motionZ *= 0.7;
					}
				}
			}
			double xzMotion = Math.sqrt(renderMotionX * renderMotionX + renderMotionZ * renderMotionZ);
			this.currentRenderPitch = xzMotion!= 0?(float)((180/Math.PI) * -Math.atan(renderMotionY / xzMotion)):currentRenderPitch*0.4F;
			this.currentRenderYaw = xzMotion!= 0?(float)((180/Math.PI) *(float)Math.asin(renderMotionX / xzMotion)):currentRenderYaw;
			if(worldObj.isAirBlock((int)posX,(int)posY-1,(int)posZ)){
				currentRenderRoll = 90;
			}
			else{
				currentRenderRoll = 0;
			}
			this.dataWatcher.updateObject(26, currentRenderPitch);
			this.dataWatcher.updateObject(27, currentRenderYaw);
			this.dataWatcher.updateObject(28, currentRenderRoll);

			this.dataWatcher.updateObject(21, (float)motionX);
			this.dataWatcher.updateObject(22, (float)motionY);
			this.dataWatcher.updateObject(23, (float)motionZ);
		}
		else{
			if(!this.onGround){
				currentRenderPitch = this.dataWatcher.getWatchableObjectFloat(26);
			}
			else{
				currentRenderPitch = 0;
			}
			currentRenderYaw = this.dataWatcher.getWatchableObjectFloat(27);
			currentRenderRoll = this.dataWatcher.getWatchableObjectFloat(28);


			motionX = this.dataWatcher.getWatchableObjectFloat(21);
			motionY = this.dataWatcher.getWatchableObjectFloat(22);
			motionZ = this.dataWatcher.getWatchableObjectFloat(23);
		}
		//this.renderMotionX = renderMotionY = renderMotionZ = 0;
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
		else if (this.rand.nextInt(50) == 0 || !this.inWater || this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F && !hooked)
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
		boolean tooCloseToPlayer = false;
		while(needsNewLocation && numAttempts < 255){
			numAttempts++;

			for(EntityPlayer p : nearbyPlayers){
				if(p.getDistance((double)destX, (double)destY, (double)destZ)<8){
					tooCloseToPlayer = true;
				}
			}
			int tempX = 0;
			int tempY = 0;
			int tempZ = 0;
			for(EntityPlayer p : nearbyPlayers){
				if(p.getDistance((double)destX, (double)destY, (double)destZ)<8){
					tempX+= p.posX;
					tempY+= p.posY;
					tempZ+= p.posZ;
				}
			}
			if(nearbyPlayers.size() >0){
				tempX /= nearbyPlayers.size();
				tempY /= nearbyPlayers.size();
				tempZ /= nearbyPlayers.size();

				destX = (int) (2*x - tempX);
				destY = (int) (2*y - tempY);
				destZ = (int) (2*z - tempZ);
			}
			else{
				destX = (int) x + (this.rand.nextInt(10) * (this.rand.nextBoolean()?-1:1));;
				destY = (int) y + (this.rand.nextInt(3) * (this.rand.nextBoolean()?-1:1));;
				destZ = (int) z + (this.rand.nextInt(10) * (this.rand.nextBoolean()?-1:1));;
			}
			if(!(TFC_Core.isWater(this.worldObj.getBlock(destX, destY-1, destZ))) && TFC_Core.isWater(this.worldObj.getBlock(destX, destY+1, destZ))){
				destY++;
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
