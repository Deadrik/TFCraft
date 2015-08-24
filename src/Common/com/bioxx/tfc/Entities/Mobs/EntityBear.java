package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAITargetNonTamedTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;
import com.bioxx.tfc.api.Util.Helper;

public class EntityBear extends EntityTameable implements ICausesDamage, IAnimal, IInnateArmor
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
	private final float GESTATION_PERIOD = 7.0f;

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected int age;
	protected boolean pregnant;
	protected int pregnancyRequiredTime;
	protected long timeOfConception;
	protected float mateSizeMod = 0;
	protected float mateStrengthMod = 0;
	protected float mateAggroMod = 0;
	protected float mateObedMod = 0;
	public float size_mod;			//How large the animal is
	public float strength_mod;		//how strong the animal is
	public float aggression_mod = 1;//How aggressive / obstinate the animal is
	public float obedience_mod = 1;	//How well the animal responds to commands.
	public boolean inLove;
	private int degreeOfDiversion = 4;
	
	private EntityAIAttackOnCollide attackAI;
	private EntityAILeapAtTarget leapAI;
	private EntityAITargetNonTamed targetSheep;
	private EntityAITargetNonTamed targetDeer;
	private EntityAITargetNonTamed targetPig;
	private EntityAITargetNonTamed targetHorse;
	private EntityAITargetNonTamedTFC targetPlayer;
	private EntityAIHurtByTarget hurtAI;
	private boolean isPeacefulAI;

	private boolean wasRoped = false;
	
	private int familiarity = 0;
	private long lastFamiliarityUpdate = 0;
	private boolean familiarizedToday = false;

	protected float avgAdultWeight = 270F;			//The average weight of adult males in kg
	protected float dimorphism = 0.2182f;		//1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
											//the square root of the ratio of female mass : male mass

	public EntityBear (World par1World)
	{
		super (par1World);
		setSize (1.2F, 1.2F);
		moveSpeed = 0.2F;
		getNavigator ().setAvoidsWater (true);
		tasks.addTask (1, new EntityAISwimming (this));
		size_mod =(float)Math.sqrt((((rand.nextInt (rand.nextInt((degreeOfDiversion + 1)*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1F) * (1.0F - dimorphism * sex));
		strength_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + size_mod));
		aggression_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1));
		obedience_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + (1f/aggression_mod)));
		sex = rand.nextInt(2);
		if (getGender() == GenderEnum.MALE)
			tasks.addTask (6, new EntityAIMate (this, moveSpeed));
		tasks.addTask (7, new EntityAIWander (this, moveSpeed));
		tasks.addTask (8, new EntityAIWatchClosest (this, EntityPlayer.class, 8F));
		tasks.addTask (9, new EntityAILookIdle (this));
		this.attackAI = new EntityAIAttackOnCollide(this, moveSpeed * 1.5F, true);
		this.leapAI = new EntityAILeapAtTarget(this, 0.4F);
		this.targetSheep = new EntityAITargetNonTamed(this, EntitySheepTFC.class, 200, false);
		this.targetDeer = new EntityAITargetNonTamed(this, EntityDeer.class, 200, false);
		this.targetPig = new EntityAITargetNonTamed(this, EntityPigTFC.class, 200, false);
		this.targetHorse = new EntityAITargetNonTamed(this, EntityHorseTFC.class, 200, false);
		this.targetPlayer = new EntityAITargetNonTamedTFC(this, EntityPlayer.class, 200, false);
		this.hurtAI = new EntityAIHurtByTarget(this, true);

		if (par1World.difficultySetting != EnumDifficulty.PEACEFUL)
		{
			isPeacefulAI = false;
			tasks.addTask(4, attackAI);
			tasks.addTask(3, leapAI);
			targetTasks.addTask(4, targetSheep);
			targetTasks.addTask(4, targetDeer);
			targetTasks.addTask(4, targetPig);
			targetTasks.addTask(4, targetHorse);
			targetTasks.addTask(4, targetPlayer);
			targetTasks.addTask(3, hurtAI);
		}
		else
			isPeacefulAI = true;
		//targetTasks.addTask(2, new EntityAIPanic(this,moveSpeed*1.5F));

		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);

		/*
		 * We hijack the growingAge to hold the day of birth rather than the number of ticks to the next growth event.
		 * We want spawned animals to be adults, so we set their birthdays far enough back in time such that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
	}


	public EntityBear (World par1World, IAnimal mother, ArrayList<Float> data)
	{
		this(par1World);
		float father_size = 1;
		float father_str = 1;
		float father_aggro = 1;
		float father_obed = 1;
		for(int i = 0; i < data.size(); i++){
			switch(i){
			case 0:father_size = data.get(i);break;
			case 1:father_str = data.get(i);break;
			case 2:father_aggro = data.get(i);break;
			case 3:father_obed = data.get(i);break;
			default:break;
			}
		}
		this.posX = ((EntityLivingBase)mother).posX;
		this.posY = ((EntityLivingBase)mother).posY;
		this.posZ = ((EntityLivingBase)mother).posZ;
		float invSizeRatio = 1f / (2 - dimorphism);
		size_mod = (float)Math.sqrt(size_mod * size_mod * (float)Math.sqrt((mother.getSize() + father_size) * invSizeRatio));
		strength_mod = (float)Math.sqrt(strength_mod * strength_mod * (float)Math.sqrt((mother.getStrength() + father_str) * 0.5F));
		aggression_mod = (float)Math.sqrt(aggression_mod * aggression_mod * (float)Math.sqrt((mother.getAggression() + father_aggro) * 0.5F));
		obedience_mod = (float)Math.sqrt(obedience_mod * obedience_mod * (float)Math.sqrt((mother.getObedience() + father_obed) * 0.5F));
		
		this.familiarity = (int) (mother.getFamiliarity()<90?mother.getFamiliarity()/2:mother.getFamiliarity()*0.9f);

		// We hijack the growingAge to hold the day of birth rather than number of ticks to next growth event.
		this.setAge(TFC_Time.getTotalDays());
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
		dataWatcher.updateObject (18, getHealth());
	}

	@Override
	protected void entityInit ()
	{
		super.entityInit ();
		dataWatcher.addObject (18, getHealth());
		this.dataWatcher.addObject(13, new Integer(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age
		this.dataWatcher.addObject(22, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, empty slot
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of conception, stored as a string since we can't do long
	}


	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.BearHealth);//MaxHealth
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
		
		nbt.setInteger("Familiarity", familiarity);
		nbt.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbt.setBoolean("Familiarized Today", familiarizedToday);

		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		
		nbt.setBoolean("wasRoped", wasRoped);

		nbt.setInteger ("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setFloat("MateStrength", mateStrengthMod);
		nbt.setFloat("MateAggro", mateAggroMod);
		nbt.setFloat("MateObed", mateObedMod);
		nbt.setLong("ConceptionTime",timeOfConception);
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

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");
		
		strength_mod = nbt.getFloat ("Strength Modifier");
		aggression_mod = nbt.getFloat ("Aggression Modifier");
		obedience_mod = nbt.getFloat ("Obedience Modifier");

		wasRoped = nbt.getBoolean("wasRoped");
		
		hunger = nbt.getInteger ("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		mateStrengthMod = nbt.getFloat("MateStrength");
		mateAggroMod = nbt.getFloat("MateAggro");
		mateObedMod = nbt.getFloat("MateObed");
		timeOfConception = nbt.getLong("ConceptionTime");
		this.dataWatcher.updateObject(15, nbt.getInteger ("Age"));
	}


	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn ()
	{
		return !wasRoped && this.ticksExisted > 30000;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound ()
	{
		if(isAdult() && worldObj.rand.nextInt(100) < 5)
			return TFC_Sounds.BEARCRY;
		else if(isChild() && worldObj.rand.nextInt(100) < 5)
			return TFC_Sounds.BEARCUBCRY;

		return isChild() ? null : TFC_Sounds.BEARSAY;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound ()
	{
		if(!isChild())
			return TFC_Sounds.BEARHURT;
		else
			return TFC_Sounds.BEARCUBCRY;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound ()
	{
		if(!isChild())
			return TFC_Sounds.BEARDEATH;
		else
			return TFC_Sounds.BEARCUBCRY;
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
	protected Item getDropItem()
	{
		return Item.getItemById(0);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		this.entityDropItem(new ItemStack(TFCItems.Hide, 1, Math.max(0, Math.min(2, (int)(ageMod * 3 - 1)))), 0);
		this.dropItem(Items.bone, (int) ((rand.nextInt(6) + 2) * ageMod));
	}


	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		TFC_Core.PreventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;

		if (!worldObj.isRemote)
		{
			if (!field_25052_g && !hasPath() && onGround)
			{
				field_25052_g = true;
				worldObj.setEntityState(this, (byte) 8);
			}

			if (isPregnant())
			{
				if (TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
				{
					int i = rand.nextInt(3) + 1;
					for (int x = 0; x < i; x++)
					{
						EntityBear baby = (EntityBear) createChildTFC(this);//new EntityBear(worldObj, this,data);
						worldObj.spawnEntityInWorld(baby);
					}
					pregnant = false;
				}
			}
		}

		if (this.getLeashed() && !wasRoped)
			wasRoped = true;

		this.handleFamiliarityUpdate();
		this.syncData();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		field_25054_c = field_25048_b;
		field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
		if (!this.worldObj.isRemote)
		{
			if (!isPeacefulAI && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
			{
				isPeacefulAI = true;
				tasks.removeTask(attackAI);
				tasks.removeTask(leapAI);
				targetTasks.removeTask(targetSheep);
				targetTasks.removeTask(targetDeer);
				targetTasks.removeTask(targetPig);
				targetTasks.removeTask(targetHorse);
				targetTasks.removeTask(targetPlayer);
				targetTasks.removeTask(hurtAI);
			}
			else if (isPeacefulAI && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL)
			{
				isPeacefulAI = false;
				tasks.addTask(4, attackAI);
				tasks.addTask(3, leapAI);
				targetTasks.addTask(4, targetSheep);
				targetTasks.addTask(4, targetDeer);
				targetTasks.addTask(4, targetPig);
				targetTasks.addTask(4, targetHorse);
				targetTasks.addTask(4, targetPlayer);
				targetTasks.addTask(3, hurtAI);
			}
		}
	}

	@Override
	public float getEyeHeight ()
	{
		return height * 0.8F;
	}

	@Override
	public boolean attackEntityAsMob (Entity par1Entity)
	{
		int dam =  (int)(TFC_MobData.BearDamage * getStrength() * getAggression() * (getSize()/2 + 0.5F));
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

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));

				byte[] values = {
						TFC_Core.getByteFromSmallFloat(size_mod),
						TFC_Core.getByteFromSmallFloat(strength_mod),
						TFC_Core.getByteFromSmallFloat(aggression_mod),
						TFC_Core.getByteFromSmallFloat(obedience_mod),
						(byte) familiarity,
						(byte) (familiarizedToday ? 1 : 0),
						(byte) (pregnant ? 1 : 0),
						(byte) 0 // Empty
				};
				ByteBuffer buf = ByteBuffer.wrap(values);
				this.dataWatcher.updateObject(22, buf.getInt());
				this.dataWatcher.updateObject(23, buf.getInt());
				this.dataWatcher.updateObject(24, String.valueOf(timeOfConception));
			}
			else
			{
				sex = this.dataWatcher.getWatchableObjectInt(13);
				
				ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
				buf.putInt(this.dataWatcher.getWatchableObjectInt(22));
				buf.putInt(this.dataWatcher.getWatchableObjectInt(23));
				byte[] values = buf.array();
				
				size_mod = TFC_Core.getSmallFloatFromByte(values[0]);
				strength_mod = TFC_Core.getSmallFloatFromByte(values[1]);
				aggression_mod = TFC_Core.getSmallFloatFromByte(values[2]);
				obedience_mod = TFC_Core.getSmallFloatFromByte(values[3]);
				
				familiarity = values[4];
				familiarizedToday = (values[5] == 1);
				pregnant = (values[6] == 1);
				
				try
				{
					timeOfConception = Long.parseLong(this.dataWatcher.getWatchableObjectString(24));
				} catch (NumberFormatException e){}
			}
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
			return false;
		if (!(par1EntityAnimal instanceof EntityBear))
			return false;
		EntityBear entitybear = (EntityBear) par1EntityAnimal;
		return getInLove () && entitybear.getInLove ();
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
	public EnumDamageType GetDamageType()
	{
		return EnumDamageType.SLASHING;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return createChildTFC(entityageable);
	}

	@Override
	public int getBirthDay() 
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	@Override
	public int getNumberOfDaysToAdult() 
	{
		return TFC_Time.daysInMonth * 60;
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
		if (animal.getGender() != this.getGender() && this.isAdult() && animal.isAdult() && 
				animal instanceof EntityBear)
			return true;
		else
			return false;
	}

	@Override
	public void mate(IAnimal otherAnimal)
	{
		if (getGender() == GenderEnum.MALE)
		{
			otherAnimal.mate(this);
			return;
		}
		timeOfConception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSize();
		mateStrengthMod = otherAnimal.getStrength();
		mateAggroMod = otherAnimal.getAggression();
		mateObedMod = otherAnimal.getObedience();
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
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("bear");
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
		return GenderEnum.genders[dataWatcher.getWatchableObjectInt(13)];
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityBear(worldObj, this, data);
	}

	@Override
	public void setAge(int par1)
	{
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if (player.isSneaking() && !familiarizedToday)
			{
				this.familiarize(player);
				return true;
			}
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation(getGender() == GenderEnum.FEMALE ? "entity.female" : "entity.male"));
			if(getGender()==GenderEnum.FEMALE && pregnant)
			{
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));
			}
		}
		ItemStack itemstack = player.getHeldItem();
		if (itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName"))
		{
			if (this.trySetName(itemstack.stackTagCompound.getString("ItemName"), player))
			{
				itemstack.stackSize--;
			}
			return true;
		}
		return super.interact(player);
	}

	@Override
	public float getStrength()
	{
		return strength_mod;
	}

	@Override
	public float getAggression()
	{
		return aggression_mod;
	}

	@Override
	public float getObedience()
	{
		return obedience_mod;
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

	@Override
	public int GetCrushArmor()
	{
		return 0;
	}

	@Override
	public int GetSlashArmor()
	{
		return 0;
	}

	@Override
	public int GetPierceArmor()
	{
		return -335;
	}


	@Override
	public int getFamiliarity() {
		return familiarity;
	}

	@Override
	public boolean getFamiliarizedToday()
	{
		return familiarizedToday;
	}

	@Override
	public void handleFamiliarityUpdate() {
		int totalDays = TFC_Time.getTotalDays();
		if(lastFamiliarityUpdate < totalDays){
			if(familiarizedToday && familiarity < 100){
				lastFamiliarityUpdate = totalDays;
				familiarizedToday = false;
				float familiarityChange = (3 * obedience_mod / aggression_mod); //Changed from 6 to 3 so bears are harder to tame by default. -Kitty
				if(this.isAdult() && familiarity <= 80) //Adult bears cap out at 80 since it is currently impossible to get baby bears.
				{
					familiarity += familiarityChange;
				}
				else if(!this.isAdult()){
					float ageMod = 2f/(1f + TFC_Core.getPercentGrown(this));
					familiarity += ageMod * familiarityChange;
					if(familiarity > 70){
						obedience_mod *= 1.01f;
					}
				}
			}
			else if(familiarity < 30){
				familiarity -= 2*(TFC_Time.getTotalDays() - lastFamiliarityUpdate);
				lastFamiliarityUpdate = totalDays;
			}
		}
		if(familiarity > 100)familiarity = 100;
		if(familiarity < 0)familiarity = 0;
	}
	
	@Override
	public boolean isFood(ItemStack item) {
		return  item != null && item.getItem().equals(TFCItems.fishRaw);
	}

	@Override
	public void familiarize(EntityPlayer ep) {
		ItemStack stack = ep.getHeldItem();
		if (stack != null && isFood(stack) && !familiarizedToday)
		{
			if (!ep.capabilities.isCreativeMode)
			{
				ep.inventory.setInventorySlotContents(ep.inventory.currentItem,(((ItemFoodTFC)stack.getItem()).onConsumedByEntity(ep.getHeldItem(), worldObj, this)));
			}
			else
			{
				worldObj.playSoundAtEntity(this, "random.burp", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			this.hunger += 24000;
			familiarizedToday = true;
			this.getLookHelper().setLookPositionWithEntity(ep, 0, 0);
			this.playLivingSound();
		}
	}

	@Override
	public boolean trySetName(String name, EntityPlayer player) {
		if (this.checkFamiliarity(InteractionEnum.NAME, player))
		{
			this.setCustomNameTag(name);
			return true;
		}
		this.playSound((isChild()?TFC_Sounds.BEARCUBCRY:TFC_Sounds.BEARCRY),  6, (rand.nextFloat()/2F)+0.75F);
		return false;
	}
	
	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player) {
		boolean flag = false;
		switch(interaction){
		case MOUNT: flag = familiarity > 15;break;
		case BREED: flag = familiarity > 20;break;
		case NAME: flag = familiarity > 70;break;
		case TOLERATEPLAYER: flag = familiarity > 75; break;
		default: break;
		}
		if(!flag && player != null && !player.worldObj.isRemote){
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.notFamiliar"));
		}
		return flag;
	}

	@Override
	public int getDueDay()
	{
		return TFC_Time.getDayFromTotalHours((timeOfConception + pregnancyRequiredTime) / 1000);
	}
}
