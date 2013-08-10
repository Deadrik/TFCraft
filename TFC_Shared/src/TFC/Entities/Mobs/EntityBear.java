package TFC.Entities.Mobs;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.Entities.IAnimal;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobDamage;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;

public class EntityBear extends EntityTameable implements ICausesDamage, IAnimal
{
	/**
	 * This flag is set when the bear is looking at a player with interest, i.e. with tilted head. This happens when
	 * tamed wolf is wound and player holds porkchop (raw or cooked), or when wild wolf sees bone in player's hands.
	 */
	private float field_25048_b;
	private float field_25054_c;
	private Random rand = new Random ();
	private float moveSpeed = 0;

	/** true is the wolf is wet else false */
	private boolean field_25052_g;

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


	public EntityBear (World par1World)
	{
		super (par1World);
		setSize (1.2F, 1.2F);
		moveSpeed = 0.2F;
		getNavigator ().setAvoidsWater (true);
		tasks.addTask (1, new EntityAISwimming (this));
		tasks.addTask (4, new EntityAIAttackOnCollide (this, moveSpeed * 1.5F, true));
		if (getGender() == GenderEnum.MALE)
		{
			tasks.addTask (6, new EntityAIMate (this, moveSpeed));
		}
		tasks.addTask (7, new EntityAIWander (this, moveSpeed));
		tasks.addTask (8, new EntityAIWatchClosest (this, EntityPlayer.class, 8F));
		tasks.addTask (9, new EntityAILookIdle (this));
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntitySheepTFC.class,200, false));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntityPigTFC.class, 200, false));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntityPlayer.class, 200, false));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAIPanic(this,moveSpeed*1.5F));
		/*fooditems.add(Item.beefRaw.itemID);
		fooditems.add(Item.porkRaw.itemID);
		fooditems.add(Item.fishRaw.itemID);*/
	}


	public EntityBear (World par1World, IAnimal mother, float father_size)
	{
		this(par1World);
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);
	}


	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled ()
	{
		return true;
	}

	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITick ()
	{
		dataWatcher.updateObject (18, func_110143_aJ());
	}

	@Override
	protected void entityInit ()
	{
		super.entityInit ();
		dataWatcher.addObject (18,func_110143_aJ());
	}

	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		//this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(TFC_MobDamage.BearDamage);
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1200);//MaxHealth
	}


	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking ()
	{
		return true;
	}


	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT (NBTTagCompound nbt)
	{
		super.writeEntityToNBT (nbt);
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
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn ()
	{
		return true;
	}


	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound ()
	{
		return "mob.wolf.growl";
	}


	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound ()
	{
		if(!isChild()){
			return "mob.wolf.growl";
		}
		else{
			return "mob.wolf.whine";
		}
	}


	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound ()
	{
		return "mob.wolf.death";
	}


	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume ()
	{
		return 0.4F;
	}


	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId ()
	{
		return -1;
	}


	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate ()
	{
		super.onLivingUpdate ();
		//		float t = (1.0F-(getGrowingAge()/(TFC_Time.getYearRatio() * adultAge * -TFC_Settings.dayLength)));
		if (!worldObj.isRemote && !field_25052_g && !hasPath () && onGround)
		{
			field_25052_g = true;
			worldObj.setEntityState (this, (byte) 8);
		}
		if(this.isPregnant()){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength){
				int i = rand.nextInt(3) + 1;
				for (int x = 0; x<i;x++){
					EntityBear baby = new EntityBear(worldObj, this,mateSizeMod);
					worldObj.spawnEntityInWorld(baby);
				}
				pregnant = false;
			}
		}



		/*if (TFC_Time.getTotalTicks() == birthTime + 60 && this instanceof EntityBear && this.sex == 1&& rand.nextInt(10) == 0 && getGrowingAge() >= 0){
			int i = rand.nextInt(3);
			if (mateSizeMod == 0){
				this.mateSizeMod = ((rand.nextInt (5) - 2) / 10f) + 1F;
			}
			for (int x = 0; x<i;x++){
				giveBirth(new EntityBear(this.worldObj,this,this.mateSizeMod));
			}
		}*/
	}


	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate ()
	{
		super.onUpdate ();
		field_25054_c = field_25048_b;
		field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
	}

	@Override
	public float getEyeHeight ()
	{
		return height * 0.8F;
	}

	@Override
	public boolean attackEntityAsMob (Entity par1Entity)
	{
		int dam =  TFC_MobDamage.BearDamage;
		return par1Entity.attackEntityFrom (DamageSource.causeMobDamage (this), dam);
	}

	@Override
	public void handleHealthUpdate (byte par1)
	{
		if (par1 == 8)
		{
			field_25052_g = true;
		}
		else
		{
			super.handleHealthUpdate (par1);
		}
	}


	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk ()
	{
		return 2;
	}

	@Override
	public boolean canMateWith (EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		if (!(par1EntityAnimal instanceof EntityBear))
		{
			return false;
		}
		EntityBear entitybear = (EntityBear) par1EntityAnimal;
		return isInLove () && entitybear.isInLove ();
	}

	@Override
	public EnumDamageType GetDamageType() {
		return EnumDamageType.SLASHING;
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
