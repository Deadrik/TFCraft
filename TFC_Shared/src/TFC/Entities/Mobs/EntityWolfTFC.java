package TFC.Entities.Mobs;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_MobDamage;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAIBegTFC;
import TFC.Entities.AI.EntityAIHurtByTargetTFC;
import TFC.Entities.AI.EntityAIMateTFC;

public class EntityWolfTFC extends EntityWolf implements IAnimal
{
	/**
	 * This flag is set when the wolf is looking at a player with interest, i.e. with tilted head. This happens when
	 * tamed wolf is wound and player holds porkchop (raw or cooked), or when wild wolf sees bone in player's hands.
	 */
	private boolean looksWithInterest = false;
	private float field_25048_b;
	private float field_25054_c;

	/** true is the wolf is wet else false */
	private boolean isShaking;
	private boolean field_25052_g;
	private long warning;

	/**
	 * This time increases while wolf is shaking and emitting water particles.
	 */
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected int age;
	protected boolean pregnant;
	protected int pregnancyTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod;
	public boolean inLove;

	public EntityWolfTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.6F, 0.8F);
		warning = -121;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1, true));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMateTFC(this, worldObj, 1));
		this.tasks.addTask(7, new EntityAIWander(this, 1));
		this.tasks.addTask(8, new EntityAIBegTFC(this, 8.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTargetTFC(this, true));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheepTFC.class, 200, false));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityPigTFC.class, 200, false));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityDeer.class, 200, false));
	}
	public EntityWolfTFC(World par1World, IAnimal mother, float F_size)
	{
		this(par1World);
		size_mod = (((rand.nextInt (1+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + F_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITick()
	{
		this.dataWatcher.updateObject(18, Float.valueOf(this.func_110143_aJ()));
	}
	@Override
	public int getMaxHealth()
	{
		return this.isTamed() ? 1000 : 400;
	}
	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Angry", this.isAngry());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn()
	{
		return this.isAngry();
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return this.isAngry() ? "mob.wolf.growl" : (this.rand.nextInt(3) == 0 ? (this.isTamed() && this.dataWatcher.getWatchableObjectInt(18) < 10 ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.wolf.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.wolf.death";
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return -1;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (!this.worldObj.isRemote && this.isShaking && !this.field_25052_g && !this.hasPath() && this.onGround)
		{
			this.field_25052_g = true;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
			this.worldObj.setEntityState(this, (byte)8);
		}
		if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength){
				int i = rand.nextInt(5) + 3;
				for (int x = 0; x<i;x++){
					EntityWolfTFC baby = new EntityWolfTFC(worldObj, this,mateSizeMod);
					baby.setOwner(this.getOwnerName());
					baby.setTamed(true);
					worldObj.spawnEntityInWorld(baby);
				}
				pregnant = false;
			}
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.field_25054_c = this.field_25048_b;

		if (getLooksWithInterest())
		{
			this.field_25048_b += (1.0F - this.field_25048_b) * 0.4F;
		}
		else
		{
			this.field_25048_b += (0.0F - this.field_25048_b) * 0.4F;
		}

		if (getLooksWithInterest())
		{
			this.numTicksToChaseTarget = 10;
		}
		if (!isAngry() && getOwner() != null){
			float f = 8F;
			List list = worldObj.getEntitiesWithinAABB (EntityPlayer.class, boundingBox.expand (f, f, f));
			boolean target = false;
			for (Iterator iterator = list.iterator () ; iterator.hasNext () ;)
			{

				Entity entity = (Entity) iterator.next ();

				if (entity instanceof EntityPlayer)
				{
					target = true;
					if(rand.nextInt(20) == 0)
					{
						/**
						 * Removed to hopefully stop the annoying constant growl at owners
						 */
						//this.worldObj.playSoundAtEntity(this, "mob.wolf.growl", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

					}
					if (warning == -121){
						warning = TFC_Time.getTotalTicks();
					}
					//getLookHelper().setLookPosition(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ, 10.0F, (float)getVerticalFaceSpeed());
				}
			}
			if (target == false){
				warning = -61;
			}
		}
		if (TFC_Time.getTotalTicks() == warning + 120){
			this.worldObj.playSoundAtEntity(this, "mob.wolf.bark", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			setAngry(true);
		}

		if (this.isWet())
		{
			this.isShaking = true;
			this.field_25052_g = false;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		}
		else if ((this.isShaking || this.field_25052_g) && this.field_25052_g)
		{
			if (this.timeWolfIsShaking == 0.0F)
			{
				this.worldObj.playSoundAtEntity(this, "mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
			this.timeWolfIsShaking += 0.05F;

			if (this.prevTimeWolfIsShaking >= 2.0F)
			{
				this.isShaking = false;
				this.field_25052_g = false;
				this.prevTimeWolfIsShaking = 0.0F;
				this.timeWolfIsShaking = 0.0F;
			}

			if (this.timeWolfIsShaking > 0.4F)
			{
				float var1 = (float)this.boundingBox.minY;
				int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

				for (int var3 = 0; var3 < var2; ++var3)
				{
					float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					this.worldObj.spawnParticle("splash", this.posX + var4, var1 + 0.8F, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
				}
			}
		}
	}

	@Override
	public boolean getWolfShaking()
	{
		return this.isShaking;
	}

	/**
	 * Used when calculating the amount of shading to apply while the wolf is shaking.
	 */
	@Override
	public float getShadingWhileShaking(float par1)
	{
		return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1) / 2.0F * 0.25F;
	}

	@Override
	public float getShakeAngle(float par1, float par2)
	{
		float var3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1 + par2) / 1.8F;

		if (var3 < 0.0F)
		{
			var3 = 0.0F;
		}
		else if (var3 > 1.0F)
		{
			var3 = 1.0F;
		}

		return MathHelper.sin(var3 * (float)Math.PI) * MathHelper.sin(var3 * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
	}

	@Override
	public float getInterestedAngle(float par1)
	{
		return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * par1) * 0.15F * (float)Math.PI;
	}
	@Override
	public float getEyeHeight()
	{
		return this.height * 0.8F;
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
	 * use in wolves.
	 */
	@Override
	public int getVerticalFaceSpeed()
	{
		return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
	}


	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		int var2 = TFC_MobDamage.WolfDamage;
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

		if (!this.isTamed())
		{
			if (var2 != null && var2.itemID == Item.bone.itemID && !this.isAngry())
			{
				if (!par1EntityPlayer.capabilities.isCreativeMode)
				{
					--var2.stackSize;
				}

				if (var2.stackSize <= 0)
				{
					par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
				}

				if (!this.worldObj.isRemote)
				{
					if (this.rand.nextInt(3) == 0)
					{
						this.setTamed(true);
						this.setPathToEntity((PathEntity)null);
						this.setAttackTarget((EntityLiving)null);
						this.aiSit.setSitting(true);
						this.setEntityHealth(20);
						this.setOwner(par1EntityPlayer.username);
						this.playTameEffect(true);
						this.worldObj.setEntityState(this, (byte)7);
					}
					else
					{
						this.playTameEffect(false);
						this.worldObj.setEntityState(this, (byte)6);
					}
				}

				return true;
			}
		}
		else
		{
			if (var2 != null && Item.itemsList[var2.itemID] instanceof ItemFood)
			{
				ItemFood var3 = (ItemFood)Item.itemsList[var2.itemID];

				if (var3.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectInt(18) < 20)
				{
					if (!par1EntityPlayer.capabilities.isCreativeMode)
					{
						--var2.stackSize;
					}

					this.heal(var3.getHealAmount());

					if (var2.stackSize <= 0)
					{
						par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
					}

					return true;
				}
			}

			if (par1EntityPlayer.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isBreedingItem(var2))
			{
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.setPathToEntity((PathEntity)null);
			}
		}

		return super.interact(par1EntityPlayer);
	}
	@Override
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 8)
		{
			this.field_25052_g = true;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}
	@Override
	public float getTailRotation()
	{
		return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (20 - this.dataWatcher.getWatchableObjectInt(18)) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F));
	}

	/**
	 * Checks if the parameter is an wheat item.
	 */
	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? false : (!(Item.itemsList[par1ItemStack.itemID] instanceof ItemFood) ? false : ((ItemFood)Item.itemsList[par1ItemStack.itemID]).isWolfsFavoriteMeat());
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 5;
	}

	/**
	 * Determines whether this wolf is angry or not.
	 */
	@Override
	public boolean isAngry()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	/**
	 * Sets whether this wolf is angry or not.
	 */

	@Override
	public void setAngry(boolean par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
		}
		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
		}
	}

	/*@Override
	public void procreate(EntityAnimal par1EntityAnimal)
	{
		EntityWolfTFC var2 = new EntityWolfTFC(this.worldObj);
		var2.setOwner(this.getOwnerName());
		var2.setTamed(true);
		worldObj.spawnEntityInWorld(var2);
	}*/

	public boolean getLooksWithInterest()
	{
		return looksWithInterest;
	}

	public void setLooksWithInterest(boolean b)
	{
		looksWithInterest = b;
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		else if (!this.isTamed())
		{
			return false;
		}
		else if (!(par1EntityAnimal instanceof EntityWolfTFC))
		{
			return false;
		}
		else
		{
			EntityWolfTFC var2 = (EntityWolfTFC)par1EntityAnimal;
			return !var2.isTamed() ? false : (var2.isSitting() ? false : this.isInLove() && var2.isInLove());
		}
	}
	@Override
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[this.getEntityData().getInteger("Sex")];
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return new EntityChickenTFC(worldObj, this);
	}

	@Override
	public int getAge() 
	{
		return this.dataWatcher.getWatchableObjectInt(12);
	}

	@Override
	public int getNumberOfDaysToAdult() 
	{
		return TFC_Time.daysInMonth * 3;
	}

	@Override
	public boolean isAdult() 
	{
		return getAge() >= getNumberOfDaysToAdult();
	}

	@Override
	public float getSize() 
	{
		return 0.5f;
	}

	@Override
	public boolean isPregnant() 
	{
		return pregnant;
	}

	@Override
	public EntityLiving getEntity() 
	{
		return this;
	}

	@Override
	public boolean canMateWith(IAnimal animal) 
	{
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityWolfTFC) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mate(IAnimal otherAnimal) 
	{
		if (sex == 0)
		{
			otherAnimal.mate(this);
			return;
		}
		conception = TFC_Time.getTotalTicks();
		pregnant = true;
		//targetMate.setGrowingAge (TFC_Settings.dayLength);
		resetInLove();
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSize();
	}

	@Override
	public void setInLove(boolean b) 
	{
		this.inLove = b;
	}

	@Override
	public long getAnimalID() 
	{
		return animalID;
	}

	@Override
	public void setAnimalID(long id) 
	{
		animalID = id;
	}

	@Override
	public int getHunger() {
		return hunger;
	}

	@Override
	public void setHunger(int h) 
	{
		hunger = h;
	}
}
