package TFC.Entities.Mobs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAIMateTFC;

public class EntityDeer extends EntityAnimal implements IAnimal
{    

	/** The eat grass AI task for this mob. */
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	private boolean running;

	protected long animalID;
	protected int sex = 0;
	protected int hunger;
	protected long hasMilkTime;
	protected int age;
	protected boolean pregnant;
	protected int pregnancyTime;
	protected int pregnancyRequiredTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod = 1f;
	public boolean inLove;

	public EntityDeer(World par1World)
	{
		super(par1World);
		running = false;
		this.setSize(0.9F, 1.3F);
		sex = rand.nextInt(2);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMateTFC(this,worldObj, 1.0f));
		//this.tasks.addTask(3, new EntityAIPanicTFC(this, var2*2, false, true));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 12.0F, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityBear.class, 16f, 0.25F, 0.3F));
		//this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.itemID, false));
		//this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(5, this.aiEatGrass);
		//this.tasks.addTask(5, new EntityAIRutt(this, var2));
		this.tasks.addTask(1, new EntityAIWander(this, 0.75));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		//this.tasks.addTask(8, new EntityAILookIdle(this));

		pregnancyRequiredTime = 4 * TFC_Time.daysInMonth;

		hunger = 168000;
		int degreeOfDiversion = 1;
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		this.setAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
	}
	public EntityDeer(World par1World, IAnimal mother, float F_size)
	{
		this(par1World);
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + F_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}
	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	public boolean getRunning(){
		return running;
	}

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(!this.worldObj.isRemote){
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));
				this.dataWatcher.updateObject(14, Float.valueOf(size_mod));
			}
			else{
				sex = this.dataWatcher.getWatchableObjectInt(13);
				size_mod = this.dataWatcher.getWatchableObjectFloat(14);
			}
		}
	}

	public void setRunning(boolean r){
		running = r;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		/*if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime * TFC_Settings.dayLength){
				EntityDeer baby = new EntityDeer(worldObj, this,mateSizeMod);
				giveBirth(baby);
				pregnant = false;
			}
		}*/

		syncData();
		if(super.inLove > 0){
			super.inLove = 0;
			setInLove(true);
		}

		if(isAdult()){
			setGrowingAge(0);
		}
		else{
			setGrowingAge(-1);
		}
		if(isPregnant()) 
		{
			if(TFC_Time.getTotalTicks() >= conception + pregnancyRequiredTime)
			{
				EntityDeer baby = (EntityDeer) createChildTFC(this);
				baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				worldObj.spawnEntityInWorld(baby);
				baby.setAge((int)TFC_Time.getTotalDays());
				pregnant = false;
			}
		}

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		TFC_Core.PreventEntityDataUpdate = true;
		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(400);//MaxHealth
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte)0));
		this.dataWatcher.addObject(13, new Integer(0));
		this.dataWatcher.addObject(14, new Float(1));
		this.dataWatcher.addObject(15, Integer.valueOf(0));
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		if(isAdult())
		{
			this.dropItem(TFCItems.Hide.itemID,1);
			this.dropItem(Item.bone.itemID, rand.nextInt(4)+2);
		}
		if (this.isBurning())
		{

			this.dropItem(TFCItems.venisonCooked.itemID, (int) (ageMod*this.size_mod *(6+this.rand.nextInt(5))));
		}
		else
		{
			this.dropItem(TFCItems.venisonRaw.itemID, (int) (ageMod*this.size_mod *(6+this.rand.nextInt(5))));
		}
	}



	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return Block.cloth.blockID;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(!worldObj.isRemote){
			par1EntityPlayer.addChatMessage(getGender()==GenderEnum.FEMALE?"Female":"Male");
			if(getGender()==GenderEnum.FEMALE && pregnant){
				par1EntityPlayer.addChatMessage("Pregnant");
			}
			//par1EntityPlayer.addChatMessage("12: "+dataWatcher.getWatchableObjectInt(12)+", 15: "+dataWatcher.getWatchableObjectInt(15));
		}
		return super.interact(par1EntityPlayer);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger ("Sex", sex);
		nbt.setLong ("Animal ID", animalID);
		nbt.setFloat ("Size Modifier", size_mod);
		nbt.setInteger ("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setLong("ConceptionTime",conception);
		nbt.setInteger("Age", getBirthDay());
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
		this.dataWatcher.updateObject(15, nbt.getInteger ("Age"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.sheep";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.sheep";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.sheep";
	}



	@Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.PreventEntityDataUpdate) {
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
		}
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}


	/*
	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return new EntityDeer(worldObj, this, entityageable.getEntityData().getInteger("Size Modifier"));
	}*/

	//Commented out old method, the third variable should be the size modifier of the father, not the mother
	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return new EntityDeer(worldObj, this, mateSizeMod);
	}

	@Override
	public int getBirthDay() 
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	@Override
	public int getNumberOfDaysToAdult() 
	{
		return TFC_Time.daysInMonth * 3;
	}

	@Override
	public boolean isAdult() 
	{
		return getBirthDay()+getNumberOfDaysToAdult() <= TFC_Time.getTotalDays();
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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityDeer) {
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
	public boolean getInLove()
	{
		return inLove;
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
	public void eatGrassBonus()
	{
		hunger += 24000;
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
	@Override
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[getSex()];
	}
	@Override
	public int getSex() {
		return dataWatcher.getWatchableObjectInt(13);
	}
	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable) {
		return new EntityDeer(worldObj, this, entityageable.getEntityData().getFloat("MateSize"));
	}
	@Override
	public void setAge(int par1) {
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}

}
