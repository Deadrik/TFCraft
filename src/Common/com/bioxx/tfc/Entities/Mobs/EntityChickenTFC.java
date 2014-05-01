package com.bioxx.tfc.Entities.Mobs;

import java.util.ArrayList;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIFindNest;
import com.bioxx.tfc.api.Entities.IAnimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityChickenTFC extends EntityChicken implements IAnimal
{
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected int age;
	protected float mateSizeMod;
	public float size_mod;
	public float strength_mod = 1;
	public float aggression_mod = 1;
	public float obedience_mod = 1;
	public float colour_mod = 1;
	public float climate_mod = 1;
	public float hard_mod = 1;
	public boolean inLove;

	int degreeOfDiversion = 2;
	/** The time until the next egg is spawned. */
	public long nextEgg;
	public int EggTime = TFC_Time.dayLength*2;

	public EntityChickenTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.3F, 0.7F);
		this.timeUntilNextEgg = this.rand.nextInt(6000) + 24000;

		hunger = 168000;
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		mateSizeMod = 1f;
		sex = rand.nextInt(2);

		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		addAI();

		size_mod = (((rand.nextInt ((degreeOfDiversion+1)*10)*(rand.nextBoolean()?1:-1)) / 100f) + 1F) * (1.0F - 0.1F * sex);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		this.setAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntityChickenTFC(World world, IAnimal mother,  ArrayList<Float> data)
	{
		this(world);
		float father_size = data.get(0);
		this.posX = ((EntityLivingBase)mother).posX;
		this.posY = ((EntityLivingBase)mother).posY;
		this.posZ = ((EntityLivingBase)mother).posZ;
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*10*(rand.nextBoolean()?1:-1)) / 100f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}

	public EntityChickenTFC(World world, double posX, double posY, double posZ, NBTTagCompound genes)
	{
		this(world);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		float m_size = genes.getFloat("m_size");
		float f_size = genes.getFloat("f_size");
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((m_size + f_size)/1.9F);
		//size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}

	public void addAI()
	{
		if(sex==0)
		{
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		}
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain, false));
		this.tasks.addTask(3, new EntityAIFindNest(this,1.2F));
	}

	@Override
	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, Integer.valueOf(0));
		this.dataWatcher.addObject(14, Float.valueOf(1.0f));
		this.dataWatcher.addObject(15, Integer.valueOf(0));

		this.dataWatcher.addObject(24, new Float(1));
		this.dataWatcher.addObject(25, new Float(1));
		this.dataWatcher.addObject(26, new Float(1));
		this.dataWatcher.addObject(27, new Float(1));
		this.dataWatcher.addObject(28, new Float(1));
		this.dataWatcher.addObject(29, new Float(1));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50);//MaxHealth
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (hunger > 168000)
		{
			hunger = 168000;
		}
		if (hunger > 0)
		{
			hunger--;
		}

		syncData();
		if(isAdult())
		{
			setGrowingAge(0);
		}
		else
		{
			setGrowingAge(-1);
		}

		roosterCrow();

		if (--this.timeUntilNextEgg < 0)
		{
			this.timeUntilNextEgg = 0;
		}
		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.PreventEntityDataUpdate = true;
		if(getGender()==GenderEnum.MALE)
		{
			timeUntilNextEgg=10000;
		}
		else if(timeUntilNextEgg == 0)
		{
			//This prevents the vanilla chicken from ever laying an egg.
			timeUntilNextEgg = 2;
		}

		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
	}

	public void roosterCrow()
	{
		if((TFC_Time.getTotalTicks()-15)%TFC_Time.dayLength == 0 && getGender() == GenderEnum.MALE && isAdult()){
			this.playSound(TFC_Sounds.ROOSTERCROW, 10, rand.nextFloat()+0.5F);
		}
	}

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));
				this.dataWatcher.updateObject(14, Float.valueOf(size_mod));

				this.dataWatcher.updateObject(24, Float.valueOf(strength_mod));
				this.dataWatcher.updateObject(25, Float.valueOf(aggression_mod));
				this.dataWatcher.updateObject(26, Float.valueOf(obedience_mod));
				this.dataWatcher.updateObject(27, Float.valueOf(colour_mod));
				this.dataWatcher.updateObject(28, Float.valueOf(climate_mod));
				this.dataWatcher.updateObject(29, Float.valueOf(hard_mod));
			}
			else
			{
				sex = this.dataWatcher.getWatchableObjectInt(13);
				size_mod = this.dataWatcher.getWatchableObjectFloat(14);

				strength_mod = this.dataWatcher.getWatchableObjectFloat(24);
				aggression_mod = this.dataWatcher.getWatchableObjectFloat(25);
				obedience_mod = this.dataWatcher.getWatchableObjectFloat(26);
				colour_mod = this.dataWatcher.getWatchableObjectFloat(27);
				climate_mod = this.dataWatcher.getWatchableObjectFloat(28);
				hard_mod = this.dataWatcher.getWatchableObjectFloat(29);
			}
		}
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

		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		nbt.setFloat ("Colour Modifier", colour_mod);
		nbt.setFloat ("Climate Adaptation Modifier", climate_mod);
		nbt.setFloat ("Hardiness Modifier", hard_mod);

		nbt.setInteger ("Hunger", hunger);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setInteger("Age", getBirthDay());
		nbt.setLong("nextEgg", nextEgg);
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

		strength_mod = nbt.getFloat ("Strength Modifier");
		aggression_mod = nbt.getFloat ("Aggression Modifier");
		obedience_mod = nbt.getFloat ("Obedience Modifier");
		colour_mod = nbt.getFloat ("Colour Modifier");
		climate_mod = nbt.getFloat ("Climate Adaptation Modifier");
		hard_mod = nbt.getFloat ("Hardiness Modifier");

		hunger = nbt.getInteger ("Hunger");
		mateSizeMod = nbt.getFloat("MateSize");
		this.dataWatcher.updateObject(15, nbt.getInteger ("Age"));
		nextEgg = nbt.getLong("nextEgg");
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return Items.feather;
	}

	public ItemStack getEggs()
	{
		if(TFC_Time.getTotalTicks() >= this.nextEgg)
		{
			this.nextEgg = TFC_Time.getTotalTicks() + EggTime;
			return new ItemStack(TFCItems.Egg, 1);
		}
		return null;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		this.dropItem(Items.feather,(int) (ageMod * this.size_mod * (5+this.rand.nextInt(10))));

		if(isAdult())
		{
			float foodWeight = ageMod*(this.size_mod * 40);//528 oz (33lbs) is the average yield of lamb after slaughter and processing
			TFC_Core.animalDropMeat(this, TFCItems.chickenRaw, foodWeight);
			this.dropItem(Items.bone, rand.nextInt(2)+1);
		}
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.PreventEntityDataUpdate)
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}

	@Override
	public EntityChicken createChild(EntityAgeable EntityAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(mateSizeMod);
		return new EntityChickenTFC(worldObj, this, data);
//		return super.createChild(EntityAgeable);
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
		return false;
	}

	@Override
	public EntityLiving getEntity() 
	{
		return this;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityChickenTFC)
			return true;
		else
			return false;
	}

	@Override
	public void mate(IAnimal otherAnimal)
	{
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
	public int getHunger()
	{
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
	public int getSex()
	{
		return dataWatcher.getWatchableObjectInt(13);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(entityageable.getEntityData().getFloat("MateSize"));
		return new EntityChickenTFC(worldObj, this, data);
	}

	@Override
	public void setAge(int par1)
	{
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(!worldObj.isRemote)
		{
			if(!par1EntityPlayer.isSneaking())
			{
				par1EntityPlayer.addChatMessage(new ChatComponentText(getGender()==GenderEnum.FEMALE?"Female":"Male"));
			}
			//if(getGender()==GenderEnum.FEMALE && pregnant)
			//	par1EntityPlayer.addChatMessage("Pregnant");
		}
		//par1EntityPlayer.addChatMessage("12: "+dataWatcher.getWatchableObjectInt(12)+", 15: "+dataWatcher.getWatchableObjectInt(15));
		if(!worldObj.isRemote && isAdult()&& par1EntityPlayer.isSneaking() && attackEntityFrom(DamageSource.generic, 25) )
		{
			par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.feather, 1));
		}
		return super.interact(par1EntityPlayer);
	}

	@Override
	public float getStrength()
	{
		return this.getDataWatcher().getWatchableObjectFloat(24);
	}

	@Override
	public float getAggression()
	{
		return this.getDataWatcher().getWatchableObjectFloat(25);
	}

	@Override
	public float getObedience()
	{
		return this.getDataWatcher().getWatchableObjectFloat(26);
	}

	@Override
	public float getColour()
	{
		return this.getDataWatcher().getWatchableObjectFloat(27);
	}

	@Override
	public float getClimateAdaptation()
	{
		return this.getDataWatcher().getWatchableObjectFloat(28);
	}

	@Override
	public float getHardiness()
	{
		return this.getDataWatcher().getWatchableObjectFloat(29);
	}

	@Override
	public Vec3 getAttackedVec()
	{
		return null;
	}

	@Override
	public void setAttackedVec(Vec3 attackedVec)
	{
	}

	@Override
	public Entity getFearSource()
	{
		return null;
	}

	@Override
	public void setFearSource(Entity fearSource)
	{
	}
}
