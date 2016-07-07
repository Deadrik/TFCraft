package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.bioxx.tfc.Entities.AI.EntityAIMateTFC;
import com.bioxx.tfc.Entities.AI.EntityAIPanicTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Util.Helper;

public class EntityDeer extends EntityAnimal implements IAnimal
{
	private static final float GESTATION_PERIOD = 7.0f;
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	 * the square root of the ratio of female mass : male mass
	 */
	private static final float DIMORPHISM = 0.1728f;
	private static final int DEGREE_OF_DIVERSION = 1;
	private static final int FAMILIARITY_CAP = 70; // Adult caps at 70 since babies are currently impossible
	/** The eat grass AI task for this mob. */
	protected final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

	private boolean running;
	private long animalID;
	private int sex;
	private int hunger;
	private boolean pregnant;
	private int pregnancyRequiredTime;
	private long timeOfConception;
	private float mateSizeMod;
	private float mateStrengthMod;
	private float mateAggroMod;
	private float mateObedMod;
	private float sizeMod; //How large the animal is
	private float strengthMod; //how strong the animal is
	private float aggressionMod = 1;//How aggressive / obstinate the animal is
	private float obedienceMod = 1; //How well the animal responds to commands.
	private boolean inLove;
	private Vec3 attackedVec;
	private Entity fearSource;
	
	private boolean wasRoped;
	
	private int familiarity;
	private long lastFamiliarityUpdate;
	private boolean familiarizedToday;
	
	public EntityDeer(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);
		timeOfConception = 0;
		mateSizeMod = 0;
		sex = rand.nextInt(2);
		sizeMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt((DEGREE_OF_DIVERSION + 1) * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1F) * (1.0F - DIMORPHISM * sex));
		strengthMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + sizeMod));
		aggressionMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1));
		obedienceMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1f / aggressionMod));
		running = false;

		this.setSize(0.9F, 1.3F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanicTFC(this, 0.68F,true));
		this.tasks.addTask(2, new EntityAIMateTFC(this,worldObj, 1.0f));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityPlayer.class, 12.0F, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16f, 0.25F, 0.3F));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(5, this.aiEatGrass);
		this.tasks.addTask(1, new EntityAIWander(this, 0.5));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		/*
		 * We hijack the growingAge to hold the day of birth rather than the number of ticks to the next growth event.
		 * We want spawned animals to be adults, so we set their birthdays far enough back in time such that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
	}

	public EntityDeer(World par1World, IAnimal mother, List<Float> data)
	{
		this(par1World);
		float fatherSize = 1;
		float fatherStr = 1;
		float fatherAggro = 1;
		float fatherObed = 1;
		for(int i = 0; i < data.size(); i++){
			switch(i){
			case 0:fatherSize = data.get(i);break;
			case 1:fatherStr = data.get(i);break;
			case 2:fatherAggro = data.get(i);break;
			case 3:fatherObed = data.get(i);break;
			default:break;
			}
		}
		this.posX = ((EntityLivingBase)mother).posX;
		this.posY = ((EntityLivingBase)mother).posY;
		this.posZ = ((EntityLivingBase)mother).posZ;
		float invSizeRatio = 1f / (2 - DIMORPHISM);
		sizeMod = (float)Math.sqrt(sizeMod * sizeMod * (float)Math.sqrt((mother.getSizeMod() + fatherSize) * invSizeRatio));
		strengthMod = (float)Math.sqrt(strengthMod * strengthMod * (float)Math.sqrt((mother.getStrengthMod() + fatherStr) * 0.5F));
		aggressionMod = (float)Math.sqrt(aggressionMod * aggressionMod * (float)Math.sqrt((mother.getAggressionMod() + fatherAggro) * 0.5F));
		obedienceMod = (float)Math.sqrt(obedienceMod * obedienceMod * (float)Math.sqrt((mother.getObedienceMod() + fatherObed) * 0.5F));
		
		this.familiarity = (int) (mother.getFamiliarity()<90?mother.getFamiliarity()/2:mother.getFamiliarity()*0.9f);
		
		// We hijack the growingAge to hold the day of birth rather than number of ticks to next growth event.
		this.setAge(TFC_Time.getTotalDays());
	}
	

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400);//MaxHealth
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		Entity entity = par1DamageSource.getEntity();
		if(entity != null)
		{
			setAttackedVec(Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ));
			setFearSource(entity);
		}
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	protected boolean canDespawn()
	{
		return this.ticksExisted > 10000 && !wasRoped;
	}

	/*@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
	}*/

	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityDeer;
	}

	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player) {
		boolean flag = false;
		switch(interaction){
		case BREED: flag = familiarity > 20;break;
		case NAME: flag = familiarity > 60;break;
		case TOLERATEPLAYER: flag = familiarity > 40;break;
		default: break;
		}
		if(!flag && player != null && !player.worldObj.isRemote){
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.notFamiliar"));
		}
		return flag;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return createChildTFC(entityageable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityDeer(worldObj, this, data);
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);	
		this.entityDropItem(new ItemStack(TFCItems.hide, 1, Math.max(0, Math.min(2, (int)(ageMod * sizeMod * 1.84)))), 0);
		this.dropItem(Items.bone, (int)((rand.nextInt(4) + 2) * ageMod));
		float foodWeight = ageMod * (this.sizeMod * 2400);

		TFC_Core.animalDropMeat(this, TFCItems.venisonRaw, foodWeight);
	}

	@Override
	public void eatGrassBonus()
	{
		hunger += 24000;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();	
		this.dataWatcher.addObject(13, Integer.valueOf(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age
		
		this.dataWatcher.addObject(22, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, empty slot
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of conception, stored as a string since we can't do long
	}

	@Override
	public void familiarize(EntityPlayer ep) {
		ItemStack stack = ep.getHeldItem();
		if (stack != null && stack.getItem() != null && stack.getItem().equals(TFCItems.powder) && stack.getItemDamage() == 9 &&
			!familiarizedToday && canFamiliarize())
		{
			if (!ep.capabilities.isCreativeMode)
			{
				stack.stackSize--;
				ep.inventory.setInventorySlotContents(ep.inventory.currentItem,stack);
			}
			worldObj.playSoundAtEntity(this, "random.burp", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			this.hunger += 24000;
			familiarizedToday = true;
			this.getLookHelper().setLookPositionWithEntity(ep, 0, 0);
			this.playLivingSound();
		}
	}

	@Override
	public float getAggressionMod()
	{
		return aggressionMod;
	}

	@Override
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("deer");
	}

	@Override
	public Vec3 getAttackedVec()
	{
		return attackedVec;
	}

	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return TFC_Sounds.DEERDEATH;
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return Items.leather;
	}

	@Override
	public int getDueDay()
	{
		return TFC_Time.getDayFromTotalHours((timeOfConception + pregnancyRequiredTime) / 1000);
	}
	@Override
	public EntityLiving getEntity()
	{
		return this;
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
	public Entity getFearSource()
	{
		return this.fearSource;
	}

	@Override
	public GenderEnum getGender()
	{
		return GenderEnum.GENDERS[dataWatcher.getWatchableObjectInt(13)];
	}

	@Override
	public int getHunger()
	{
		return hunger;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return TFC_Sounds.DEERHURT;
	}

	@Override
	public boolean getInLove()
	{
		return inLove;
	}

	public long getLastFamiliarityUpdate()
	{
		return lastFamiliarityUpdate;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		if(getGender() == GenderEnum.MALE && isAdult() && worldObj.rand.nextInt(100) < 5)
			return TFC_Sounds.DEERCRY;
		return TFC_Sounds.DEERSAY;
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 24);
	}

	@Override
	public float getObedienceMod()
	{
		return obedienceMod;
	}

	public int getPregnancyRequiredTime()
	{
		return pregnancyRequiredTime;
	}

	public boolean getRunning()
	{
		return running;
	}

	public int getSex()
	{
		return sex;
	}

	@Override
	public float getSizeMod()
	{
		return sizeMod;
	}

	@Override
	public float getStrengthMod()
	{
		return strengthMod;
	}

	public long getTimeOfConception()
	{
		return timeOfConception;
	}

	@Override
	public void handleFamiliarityUpdate() {
		int totalDays = TFC_Time.getTotalDays();
		if(lastFamiliarityUpdate < totalDays){
			if(familiarizedToday && familiarity < 100){
				lastFamiliarityUpdate = totalDays;
				familiarizedToday = false;
				float familiarityChange = 6 * obedienceMod / aggressionMod;
				if (this.isAdult() && familiarity <= FAMILIARITY_CAP)
				{
					familiarity += familiarityChange;
				}
				else if(!this.isAdult()){
					float ageMod = 2f/(1f + TFC_Core.getPercentGrown(this));
					familiarity += ageMod * familiarityChange;
					if(familiarity > 70){
						obedienceMod *= 1.01f;
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

	public boolean hasBeenRoped()
	{
		return wasRoped;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if (player.isSneaking() && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}
			if(getGender()==GenderEnum.FEMALE && pregnant)
			{
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));
			}
		}
		ItemStack itemstack = player.getHeldItem();
		if(itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName")){
			if(this.trySetName(itemstack.stackTagCompound.getString("ItemName"), player)){
				itemstack.stackSize--;
			}
			return true;
		}
		return super.interact(player);
	}

	@Override
	public boolean isAdult()
	{
		return getBirthDay()+getNumberOfDaysToAdult() <= TFC_Time.getTotalDays();
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
	public boolean isChild()
	{
		return !isAdult();
	}

	//Unused for now
	@Override
	public boolean isFood(ItemStack item) {
		return false;
	}

	@Override
	public boolean isPregnant()
	{
		return pregnant;
	}

	@Override
	public void mate(IAnimal otherAnimal) 
	{
		if (sex == 0)
		{
			otherAnimal.mate(this);
			return;
		}
		timeOfConception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		otherAnimal.setInLove(false);
		mateAggroMod = otherAnimal.getAggressionMod();
		mateObedMod = otherAnimal.getObedienceMod();
		mateSizeMod = otherAnimal.getSizeMod();
		mateStrengthMod = otherAnimal.getStrengthMod();
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		if(super.isInLove())
		{
			super.resetInLove();
			setInLove(true);
		}

		if(this.getLeashed()&&!wasRoped)wasRoped = true;
		
		syncData();
		if(isAdult())
		{
			setGrowingAge(0);
		}
		else
		{
			setGrowingAge(-1);
		}
		
		this.handleFamiliarityUpdate();

		if (!this.worldObj.isRemote && isPregnant())
		{
			if(TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
				EntityDeer baby = (EntityDeer) createChildTFC(this);
				baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				worldObj.spawnEntityInWorld(baby);
				baby.setAge(TFC_Time.getTotalDays());
				pregnant = false;
			}
		}

		if(attackedVec != null)
		{
			//TerraFirmaCraft.log.info(this.entityId+", Vec: "+attackedVec.xCoord+", "+attackedVec.yCoord+", "+attackedVec.zCoord);
			Vec3 positionVec = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			if(this.getFearSource() != null && this.getDistanceSqToEntity(this.getFearSource()) > Global.SEALEVEL)
			{
				this.setFearSource(null);
			}
			else if(positionVec.distanceTo(getAttackedVec()) > 16)
			{
				this.setAttackedVec(null);
			}
		}

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		else if(hunger < 144000 && super.isInLove()){
			this.setInLove(false);
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;
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
		sizeMod = nbt.getFloat ("Size Modifier");

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");
		
		strengthMod = nbt.getFloat ("Strength Modifier");
		aggressionMod = nbt.getFloat ("Aggression Modifier");
		obedienceMod = nbt.getFloat ("Obedience Modifier");

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
	
	@Override
	public void setAge(int par1)
	{
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}
	
	@Override
	public void setAggressionMod(float aggressionMod)
	{
		this.aggressionMod = aggressionMod;
	}
	
	@Override
	public void setAttackedVec(Vec3 attackedVec)
	{
		this.attackedVec = attackedVec;
	}

	@Override
	public void setBirthDay(int day)
	{
		this.dataWatcher.updateObject(15, day);
	}

	@Override
	public void setFamiliarity(int familiarity)
	{
		this.familiarity = familiarity;
	}

	public void setFamiliarizedToday(boolean familiarizedToday)
	{
		this.familiarizedToday = familiarizedToday;
	}

	@Override
	public void setFearSource(Entity fearSource)
	{
		this.fearSource = fearSource;
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.preventEntityDataUpdate)
		{
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
		}
	}

	@Override
	public void setHunger(int h)
	{
		hunger = h;
	}

	@Override
	public void setInLove(boolean b)
	{
		this.inLove = b;
	}

	public void setLastFamiliarityUpdate(long lastFamiliarityUpdate)
	{
		this.lastFamiliarityUpdate = lastFamiliarityUpdate;
	}

	@Override
	public void setObedienceMod(float obedienceMod)
	{
		this.obedienceMod = obedienceMod;
	}

	public void setPregnancyRequiredTime(int pregnancyRequiredTime)
	{
		this.pregnancyRequiredTime = pregnancyRequiredTime;
	}

	public void setPregnant(boolean pregnant)
	{
		this.pregnant = pregnant;
	}

	public void setRunning(boolean r)
	{
		running = r;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	@Override
	public void setSizeMod(float sizeMod)
	{
		this.sizeMod = sizeMod;
	}

	@Override
	public void setStrengthMod(float strengthMod)
	{
		this.strengthMod = strengthMod;
	}

	public void setTimeOfConception(long timeOfConception)
	{
		this.timeOfConception = timeOfConception;
	}

	public void setWasRoped(boolean wasRoped)
	{
		this.wasRoped = wasRoped;
	}

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));

				byte[] values = {
						TFC_Core.getByteFromSmallFloat(sizeMod),
						TFC_Core.getByteFromSmallFloat(strengthMod),
						TFC_Core.getByteFromSmallFloat(aggressionMod),
						TFC_Core.getByteFromSmallFloat(obedienceMod),
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
				
				sizeMod = TFC_Core.getSmallFloatFromByte(values[0]);
				strengthMod = TFC_Core.getSmallFloatFromByte(values[1]);
				aggressionMod = TFC_Core.getSmallFloatFromByte(values[2]);
				obedienceMod = TFC_Core.getSmallFloatFromByte(values[3]);
				
				familiarity = values[4];
				familiarizedToday = values[5] == 1;
				pregnant = values[6] == 1;
				
				try
				{
					timeOfConception = Long.parseLong(this.dataWatcher.getWatchableObjectString(24));
				} catch (NumberFormatException e){}
			}
		}
	}

	@Override
	public boolean trySetName(String name, EntityPlayer player) {
		if (this.checkFamiliarity(InteractionEnum.NAME, player))
		{
			this.setCustomNameTag(name);
			return true;
		}
		this.playSound(TFC_Sounds.DEERCRY, 6, rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		return false;
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
		nbt.setFloat ("Size Modifier", sizeMod);
		
		nbt.setInteger("Familiarity", familiarity);
		nbt.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbt.setBoolean("Familiarized Today", familiarizedToday);

		nbt.setFloat ("Strength Modifier", strengthMod);
		nbt.setFloat ("Aggression Modifier", aggressionMod);
		nbt.setFloat ("Obedience Modifier", obedienceMod);
		
		nbt.setBoolean("wasRoped", wasRoped);

		nbt.setInteger ("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setFloat("MateStrength", mateStrengthMod);
		nbt.setFloat("MateAggro", mateAggroMod);
		nbt.setFloat("MateObed", mateObedMod);
		nbt.setLong("ConceptionTime", timeOfConception);
		nbt.setInteger("Age", getBirthDay());
	}
}
