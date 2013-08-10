package TFC.Entities.Mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
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
import net.minecraft.world.World;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_MobDamage;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
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
		this.tasks.addTask(8, new EntityAIBeg(this, 8.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
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


	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000);//MaxHealth
	}


	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Angry", this.isAngry());
		nbt.setInteger ("Sex", sex);
		nbt.setLong ("Animal ID", animalID);
		nbt.setFloat ("Size Modifier", size_mod);
		nbt.setInteger ("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setLong("ConceptionTime",conception);
		nbt.setInteger("Age", getAge());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setAngry(nbt.getBoolean("Angry"));
		animalID = nbt.getLong ("Animal ID");
		sex = nbt.getInteger ("Sex");
		size_mod = nbt.getFloat ("Size Modifier");
		hunger = nbt.getInteger ("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		conception = nbt.getLong("ConceptionTime");
		this.dataWatcher.updateObject(12, nbt.getInteger ("Age"));
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
						this.setEntityHealth(1000);
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
		return new EntityWolfTFC(worldObj, this, entityageable.getEntityData().getInteger("Size Modifier"));
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
		return size_mod;
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
