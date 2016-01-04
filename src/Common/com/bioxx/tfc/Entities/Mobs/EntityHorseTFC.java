package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.AIEatGrass;
import com.bioxx.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.bioxx.tfc.Entities.AI.EntityAIMateTFC;
import com.bioxx.tfc.Entities.AI.EntityAIPanicTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Util.Helper;

public class EntityHorseTFC extends EntityHorse implements IInvBasic, IAnimal
{
	private static final IEntitySelector HORSE_BREEDING_SELECTOR = new EntityHorseBredSelector();
	private static final IAttribute HORSE_JUMP_STRENGTH = new RangedAttribute("horse.jumpStrengthTFC", 0.7D, 0.0D, 2.0D).setDescription("Jump StrengthTFC").setShouldWatch(true);

	private static final float GESTATION_PERIOD = 11.17f;
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	 * the square root of the ratio of female mass : male mass
	 */
	private static final float DIMORPHISM = 0.0813f;
	private static final int DEGREE_OF_DIVERSION = 2;
	private static final int FAMILIARITY_CAP = 35;
	protected final AIEatGrass aiEatGrass = new AIEatGrass(this);

	private long animalID;
	private int sex;
	private int hunger;
	private boolean pregnant;
	private int pregnancyRequiredTime;
	private long timeOfConception;
	private float sizeMod; //How large the animal is
	private float strengthMod; //how strong the animal is
	private float aggressionMod = 1;//How aggressive / obstinate the animal is
	private float obedienceMod = 1; //How well the animal responds to commands.

	private float mateSizeMod;
	private float mateStrengthMod;
	private float mateAggroMod;
	private float mateObedMod;
	private int mateType;
	private int mateVariant;
	private double mateMaxHealth = this.calcMaxHealth();
	private double mateJumpStrength = this.calcJumpStrength();
	private double mateMoveSpeed = this.calcMoveSpeed();
	private boolean inLove;
	private Vec3 attackedVec;
	private Entity fearSource;

	private int familiarity;
	private long lastFamiliarityUpdate;
	private boolean familiarizedToday;

	public EntityHorseTFC(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);
		timeOfConception = 0;
		sex = rand.nextInt(2);
		sizeMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt((DEGREE_OF_DIVERSION + 1) * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1F) * (1.0F - DIMORPHISM * sex));
		strengthMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + sizeMod));
		aggressionMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1));
		obedienceMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1f / aggressionMod));
		this.setSize(1.4F, 1.6F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWander(this, 0.7D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 1.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16f, 0.25F, 0.3F));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.wheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.ryeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.riceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.barleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.oatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.maizeEar, false));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(1, new EntityAIPanicTFC(this, 1.2D,true));
		this.updateChestSaddle();

		/*
		 * We hijack the growingAge to hold the day of birth rather than the number of ticks to the next growth event.
		 * We want spawned animals to be adults, so we set their birthdays far enough back in time such that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntityHorseTFC(World par1World, IAnimal mother, List<Float> data, int type, int variant)
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
		this.setHorseType(type);
		this.setHorseVariant(variant);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(HORSE_JUMP_STRENGTH);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250 * sizeMod * strengthMod);//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D * strengthMod * obedienceMod / (sizeMod * sizeMod));
		this.setHealth(this.getMaxHealth());
	}

	private double calcJumpStrength()
	{
		return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
	}

	private float calcMaxHealth()
	{
		return 1000 + (float)this.rand.nextInt(101) + this.rand.nextInt(151);
	}

	private double calcMoveSpeed()
	{
		return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
	}

	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal animal)
	{
		if (animal == this)
		{
			return false;
		}
		else if (animal.getClass() != this.getClass())
		{
			return false;
		}
		else
		{
			EntityHorseTFC entityhorse = (EntityHorseTFC) animal;

			if (this.getBreedable() && entityhorse.getBreedable())
			{
				int i = this.getHorseType();
				int j = entityhorse.getHorseType();
				return i == j || i == 0 && j == 1 || i == 1 && j == 0;
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityHorseTFC;
	}

	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player) {
		boolean flag = false;
		switch(interaction){
		case MOUNT: flag = familiarity > 15;break;
		case BREED: flag = familiarity > 20;break;
		case NAME: flag = familiarity > 40;break; // 5 Higher than adult cap
		default: break;
		}
		if(!flag && player != null && !player.worldObj.isRemote){
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.notFamiliar"));
		}
		return flag;
	}

	@Override
	public void clearLeashed(boolean par1, boolean par2)
	{
		Entity entity = getLeashedToEntity();
		if (entity instanceof EntityPlayer)
		{
			//ItemStack item = ((EntityPlayer)entity).inventory.getCurrentItem();
			if(entity.isSneaking())
				super.clearLeashed(par1, true);
		}
		else
		{
			super.clearLeashed(par1, true);
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable eAgeable)
	{
		return createChildTFC(eAgeable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(this.mateSizeMod);
		data.add(this.mateStrengthMod);
		data.add(this.mateAggroMod);
		data.add(this.mateObedMod);
		
		int momType = this.getHorseType();
		int dadType = this.mateType;
		int babyType = 0;
		int babyVariant = 0;

		if (momType == dadType)
		{
			babyType = momType;
		}
		else if (momType == 0 && dadType == 1 || momType == 1 && dadType == 0)
		{
			babyType = 2; // Create Mule
		}

		if (babyType == 0)
		{
			int l = this.rand.nextInt(9);

			if (l < 4)
			{
				babyVariant = this.getHorseVariant() & 255;
			}
			else if (l < 8)
			{
				babyVariant = this.mateVariant & 255;
			}
			else
			{
				babyVariant = this.rand.nextInt(7);
			}

			int j1 = this.rand.nextInt(5);

			if (j1 < 4)
			{
				babyVariant |= this.getHorseVariant() & 65280;
			}
			else if (j1 < 8)
			{
				babyVariant |= this.mateVariant & 65280;
			}
			else
			{
				babyVariant |= this.rand.nextInt(5) << 8 & 65280;
			}
		}

		EntityHorseTFC baby = new EntityHorseTFC(worldObj, this, data, babyType, babyVariant);

		// Average Mom + Dad + Random Max Health
		double healthSum = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + this.mateMaxHealth + this.calcMaxHealth();
		baby.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthSum / 3.0D);

		// Average Mom + Dad + Random Jump Strength
		double jumpSum = this.getEntityAttribute(HORSE_JUMP_STRENGTH).getBaseValue() + this.mateJumpStrength + this.calcJumpStrength();
		baby.getEntityAttribute(HORSE_JUMP_STRENGTH).setBaseValue(jumpSum / 3.0D);

		// Average Mom + Dad + Random Move Speed
		double speedSum = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.mateMoveSpeed + this.calcMoveSpeed();
		baby.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speedSum / 3.0D);

		baby.setHealth((float)baby.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
		return baby;
	}

	@Override
	public void dropChests()
	{
		if (!this.worldObj.isRemote && this.isChested())
		{
			this.setChested(false);
		}
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		this.entityDropItem(new ItemStack(TFCItems.hide, 1, Math.max(0, Math.min(2, (int)(ageMod * 3 - 1)))), 0);
		this.dropItem(Items.bone, (int) ((rand.nextInt(8) + 3) * ageMod));
		if(this.getLeashed()){
			this.entityDropItem(new ItemStack(TFCItems.rope), 0);
		}

		float foodWeight = ageMod * (this.sizeMod * 4000);

		TFC_Core.animalDropMeat(this, TFCItems.horseMeatRaw, foodWeight);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();	
		this.dataWatcher.addObject(13, Integer.valueOf(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age
		// EntityHorse uses object 22
		this.dataWatcher.addObject(26, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(24, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, empty slot
		this.dataWatcher.addObject(25, String.valueOf("0")); // Time of conception, stored as a string since we can't do long

	}

	@Override
	public void familiarize(EntityPlayer ep) {
		ItemStack stack = ep.getHeldItem();
		if ((this.riddenByEntity == null || !this.riddenByEntity.equals(ep)) && !familiarizedToday && stack != null && this.isFood(stack) && canFamiliarize())
		{
			if (!ep.capabilities.isCreativeMode)
			{
				ep.inventory.setInventorySlotContents(ep.inventory.currentItem, ((ItemFoodTFC) stack.getItem()).onConsumedByEntity(ep.getHeldItem(), worldObj, this));
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
		if (this.riddenByEntity != null && this.riddenByEntity.equals(ep) && isAdult())
		{
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
		return Helper.stringToInt("horse");
	}

	@Override
	public Vec3 getAttackedVec()
	{
		return this.attackedVec;
	}

	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	private boolean getBreedable()
	{
		return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && 
				!this.func_110222_cv()/*Not a Mule, Zombie or Skeleton*/&& this.getHealth() >= this.getMaxHealth();
	}

	@Override
	protected EntityHorseTFC getClosestHorse(Entity entity, double range)
	{
		double maxDistance = Double.MAX_VALUE;
		EntityHorseTFC closestHorse = null;
		List<EntityHorseTFC> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(range, range, range), HORSE_BREEDING_SELECTOR);
		Iterator<EntityHorseTFC> iterator = list.iterator();

		while (iterator.hasNext())
		{
			EntityHorseTFC nearbyHorse = iterator.next();
			double distance = nearbyHorse.getDistanceSq(entity.posX, entity.posY, entity.posZ);
			if (distance < maxDistance)
			{
				closestHorse = nearbyHorse;
				maxDistance = distance;
			}
		}

		return closestHorse;
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

	public AnimalChest getHorseChest()
	{
		return this.horseChest;
	}

	@Override
	public int getHunger()
	{
		return hunger;
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

	//We use this to catch the EntityLiving check, so that other interactions can be performed on leashed animals
	@Override
	public boolean getLeashed()
	{
		if(super.getLeashed() && getLeashedToEntity() instanceof EntityPlayer &&
				((EntityPlayer) getLeashedToEntity()).inventory.getCurrentItem() == null && func_110174_bM()/*maximumHomeDistance*/!= -1)
		{
			return false;
		}
		return super.getLeashed();
	}

	@Override
	public int getMaxTemper()
	{
		return (int)(500 * aggressionMod);
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	@Override
	public double getMountedYOffset()
	{
		float offset = this.sizeMod * this.height * 0.75F;
		int type = this.getHorseType();

		if (type == 1) // Donkey
			offset *= 0.87F;
		else if (type == 2) // Mule
			offset *= 0.92F;
		
		return offset;
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 30);
	}

	private int getNumChestSlots()
	{
		int i = this.getHorseType();
		return this.isChested() && (i == 1 || i == 2) ? 17 : 2; // 2 (Armor & Saddle) + 15 Donkey Chest Slots
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

	@Override
	public int increaseTemper(int temper)
	{
		temper*=5; //This is because we want obedience_mod and aggression_mod to have an effect
		int j = MathHelper.clamp_int(this.getTemper() + (int)(temper * obedienceMod * (1f/aggressionMod)), 0, this.getMaxTemper());
		this.setTemper(j);
		return j;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		if(!worldObj.isRemote)
		{
			if (player.isSneaking() && !familiarizedToday && itemstack != null && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation(getGender() == GenderEnum.FEMALE ? "entity.female" : "entity.male"));
			if (getGender() == GenderEnum.FEMALE && pregnant)
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));

		}

		if (itemstack != null && this.isBreedingItemTFC(itemstack) && checkFamiliarity(InteractionEnum.BREED, player) && this.getGrowingAge() == 0 && !super.isInLove() &&
			(this.familiarizedToday || !canFamiliarize()))
		{
			if (!player.capabilities.isCreativeMode)
				player.inventory.setInventorySlotContents(player.inventory.currentItem, ((ItemFoodTFC) itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this));
			this.hunger += 24000;
			this.setInLove(true);
			return true;
		}
		else if(itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName")){
			if(this.trySetName(itemstack.stackTagCompound.getString("ItemName"),player)){
				itemstack.stackSize--;
			}
			return true;
		}
		else if (itemstack != null && itemstack.getItem() == Items.spawn_egg)
		{
			return super.interact(player);
		}
		else if (this.isTame() && this.isAdultHorse() && player.isSneaking() && !this.getLeashed())
		{
			this.openGUI(player);
			return true;
		}
		else if (this.isTame() && this.isAdultHorse() && player.isSneaking() && this.getLeashed())
		{
			this.clearLeashed(true, true);
			return true;
		}
		else if (this.isAdultHorse() && this.riddenByEntity != null)
		{
			return super.interact(player);
		}
		else
		{
			if (itemstack != null)
			{
				if (!this.isTame())
				{
					if (itemstack.interactWithEntity(player, this))
					{
						return true;
					}

					this.makeHorseRearWithSound();
				}

				boolean flag = false;

				if (this.func_110229_cs()/*Donkey or Mule*/&& !this.isChested() && itemstack.getItem() == Item.getItemFromBlock(TFCBlocks.chest))
				{
					this.setChested(true);
					this.playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					flag = true;
					this.updateChestSaddle();
				}

				if (flag)
				{
					if (!player.capabilities.isCreativeMode && --itemstack.stackSize == 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
					}

					return true;
				}
			}
			if (this.isAdultHorse() && this.riddenByEntity == null)
			{
				if (itemstack != null && itemstack.interactWithEntity(player, this))
				{
					return true;
				}
				else
				{
					if (this.getLeashedToEntity() instanceof EntityPlayer && this.getLeashedToEntity() == player)
					{
						this.mountHorse(player);
					}
					return true;
				}
			}
		}

		return super.interact(player);
	}

	@Override
	public boolean isAdult()
	{
		return getBirthDay()+getNumberOfDaysToAdult() <= TFC_Time.getTotalDays();
	}

	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	public boolean isBreedingItemTFC(ItemStack item)
	{
		return !pregnant && isFood(item);
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}

	@Override
	public boolean isFood(ItemStack item) {
		return item != null && (item.getItem() == TFCItems.wheatGrain ||item.getItem() == TFCItems.oatGrain||item.getItem() == TFCItems.riceGrain||
				item.getItem() == TFCItems.barleyGrain||item.getItem() == TFCItems.ryeGrain||item.getItem() == TFCItems.maizeEar);
	}

	@Override
	public boolean isPregnant()
	{
		return pregnant;
	}

	@Override
	public void mate(IAnimal otherAnimal)
	{
		if (getGender() == GenderEnum.MALE)
		{
			otherAnimal.mate(this);
			setInLove(false);
			resetInLove();
			return;
		}
		timeOfConception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		setInLove(false);
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSizeMod();
		mateStrengthMod = otherAnimal.getStrengthMod();
		mateAggroMod = otherAnimal.getAggressionMod();
		mateObedMod = otherAnimal.getObedienceMod();
		mateType = ((EntityHorse) otherAnimal).getHorseType();
		mateVariant = ((EntityHorse) otherAnimal).getHorseVariant();
		mateMaxHealth = ((EntityLivingBase) otherAnimal).getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
		mateJumpStrength = ((EntityLivingBase) otherAnimal).getEntityAttribute(HORSE_JUMP_STRENGTH).getBaseValue();
		mateMoveSpeed = ((EntityLivingBase) otherAnimal).getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
	}

	private void mountHorse(EntityPlayer player)
	{
		player.rotationYaw = this.rotationYaw;
		player.rotationPitch = this.rotationPitch;
		this.setEatingHaystack(false);
		this.setRearing(false);

		if (!this.worldObj.isRemote && checkFamiliarity(InteractionEnum.MOUNT, player))
			player.mountEntity(this);
	}

	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (hunger > 168000)
			hunger = 168000;
		if (hunger > 0)
			hunger--;

		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && rand.nextInt(600) == 0 && !familiarizedToday && canFamiliarize())
		{
			this.familiarize(((EntityPlayer)this.riddenByEntity));
		}

		syncData();
		if(isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		this.handleFamiliarityUpdate();
		if (!this.worldObj.isRemote && isPregnant())
		{
			if (TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
				EntityHorseTFC baby = (EntityHorseTFC) createChildTFC(this);
				baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				worldObj.spawnEntityInWorld(baby);
				baby.setAge(TFC_Time.getTotalDays());
				pregnant = false;
			}
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead){

			this.heal(1);
		}
		else if(hunger < 144000 && super.isInLove()){
			this.setInLove(false);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData livingData)
	{
		IEntityLivingData data = super.onSpawnWithEgg(livingData);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250);
		this.heal(1250);
		return data;
	}

	@Override
	public void openGUI(EntityPlayer player)
	{
		Entity temp = player.ridingEntity; // Trick the player into thinking they are riding the horse so we can track that specific horse

		if (temp == null) // Shift-right click while not riding to open inventory
		{
			player.ridingEntity = this; // Only applies to EntityPlayerSP
			if (player instanceof EntityPlayerMP)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP) player;
				playerMP.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, playerMP, this)); // Sets ridingEntity to this for playerMP
				openHorseContainer(player);
				playerMP.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, playerMP, temp)); // Resets ridingEntity for playerMP
			}
			else
			{
				openHorseContainer(player);
			}
			player.ridingEntity = null; // Reset EntityPlayerSP
		}

		if (temp != null && temp == this) // Opening the inventory while riding
		{
			openHorseContainer(player);
		}
	}

	private void openHorseContainer(EntityPlayer player)
	{
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player) && this.isTame())
		{
			player.openGui(TerraFirmaCraft.instance, 42, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttc)
	{
		super.readEntityFromNBT(nbttc);
		NBTTagCompound nbt = nbttc;
		animalID = nbt.getLong ("Animal ID");
		sex = nbt.getInteger ("Sex");
		sizeMod = nbt.getFloat ("Size Modifier");

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");

		strengthMod = nbt.getFloat ("Strength Modifier");
		aggressionMod = nbt.getFloat ("Aggression Modifier");
		obedienceMod = nbt.getFloat ("Obedience Modifier");

		hunger = nbt.getInteger ("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		mateStrengthMod = nbt.getFloat("MateStrength");
		mateAggroMod = nbt.getFloat("MateAggro");
		mateObedMod = nbt.getFloat("MateObed");
		mateType = nbt.getInteger("MateType");
		mateVariant = nbt.getInteger("MateVariant");
		mateMaxHealth = nbt.getDouble("MateHealth");
		mateJumpStrength = nbt.getDouble("MateJump");
		mateMoveSpeed = nbt.getDouble("MateSpeed");
		timeOfConception = nbt.getLong("ConceptionTime");
		this.setAge(nbt.getInteger ("Age"));

		if (this.isChested())
		{
			NBTTagList nbttaglist = nbttc.getTagList("Items", 10);
			this.updateChestSaddle();

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
				int j = nbt1.getByte("Slot") & 255;

				if (j >= 2 && j < this.horseChest.getSizeInventory())
				{
					this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbt1));
				}
			}
		}

		ItemStack itemstack;

		if (nbttc.hasKey("ArmorItem", 10))
		{
			itemstack = ItemStack.loadItemStackFromNBT(nbttc.getCompoundTag("ArmorItem"));

			if (itemstack != null && EntityHorse.func_146085_a/*isHorseArmor*/(itemstack.getItem()))
			{
				this.horseChest.setInventorySlotContents(1, itemstack);
			}
		}

		if (nbttc.hasKey("SaddleItem", 10))
		{
			itemstack = ItemStack.loadItemStackFromNBT(nbttc.getCompoundTag("SaddleItem"));
			if (itemstack != null && itemstack.getItem() == Items.saddle)
			{
				this.horseChest.setInventorySlotContents(0, itemstack);
			}
		}
		else if (nbttc.getBoolean("Saddle"))
		{
			this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
		}

		this.updateSaddle();
	}

	@Override
	public void setAge(int par1)
	{
		//if(!TFC_Core.PreventEntityDataUpdate) {
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
		//}
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
		if(b)
		{
			this.entityToAttack = null;
			this.worldObj.setEntityState(this, (byte)18);
		}
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
				this.dataWatcher.updateObject(26, buf.getInt());
				this.dataWatcher.updateObject(24, buf.getInt());
				this.dataWatcher.updateObject(25, String.valueOf(timeOfConception));
			}
			else
			{
				sex = this.dataWatcher.getWatchableObjectInt(13);
				
				ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
				buf.putInt(this.dataWatcher.getWatchableObjectInt(26));
				buf.putInt(this.dataWatcher.getWatchableObjectInt(24));
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
					timeOfConception = Long.parseLong(this.dataWatcher.getWatchableObjectString(25));
				} catch (NumberFormatException e){}
			}
		}
	}

	@Override
	public boolean trySetName(String name, EntityPlayer player) {
		if (checkFamiliarity(InteractionEnum.NAME, player))
		{
			this.setCustomNameTag(name);
			return true;
		}
		this.playSound(this.getHurtSound(), 6, rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		return false;
	}

	public void updateChestSaddle()
	{
		AnimalChest animalchest = this.horseChest;
		this.horseChest = new AnimalChest("HorseChest", this.getNumChestSlots());

		if (animalchest != null)
		{
			animalchest.func_110132_b(this);
			int i = Math.min(animalchest.getSizeInventory(), this.horseChest.getSizeInventory());
			for (int j = 0; j < i; ++j)
			{
				ItemStack itemstack = animalchest.getStackInSlot(j);
				if (itemstack != null)
					this.horseChest.setInventorySlotContents(j, itemstack.copy());
			}
			animalchest = null;
		}

		this.horseChest.func_110134_a(this);
		this.updateSaddle();
	}

	private void updateSaddle()
	{
		if (!this.worldObj.isRemote)
		{
			this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
			if (this.getHorseType() == 0)
				this.func_146086_d(this.horseChest.getStackInSlot(1));
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttc)
	{
		super.writeEntityToNBT(nbttc);
		nbttc.setInteger ("Sex", sex);
		nbttc.setLong ("Animal ID", animalID);
		nbttc.setFloat ("Size Modifier", sizeMod);

		nbttc.setInteger("Familiarity", familiarity);
		nbttc.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbttc.setBoolean("Familiarized Today", familiarizedToday);

		NBTTagCompound nbt = nbttc;
		nbt.setFloat ("Strength Modifier", strengthMod);
		nbt.setFloat ("Aggression Modifier", aggressionMod);
		nbt.setFloat ("Obedience Modifier", obedienceMod);

		nbttc.setInteger ("Hunger", hunger);
		nbttc.setBoolean("Pregnant", pregnant);
		nbttc.setFloat("MateSize", mateSizeMod);
		nbttc.setFloat("MateStrength", mateStrengthMod);
		nbttc.setFloat("MateAggro", mateAggroMod);
		nbttc.setFloat("MateObed", mateObedMod);
		nbttc.setInteger("MateType", mateType);
		nbttc.setInteger("MateVariant", mateVariant);
		nbttc.setDouble("MateHealth", mateMaxHealth);
		nbttc.setDouble("MateJump", mateJumpStrength);
		nbttc.setDouble("MateSpeed", mateMoveSpeed);
		nbttc.setLong("ConceptionTime", timeOfConception);
		nbttc.setInteger("Age", getBirthDay());

		if (this.isChested())
		{
			NBTTagList nbttaglist = new NBTTagList();

			for (int i = 2; i < this.horseChest.getSizeInventory(); ++i)
			{
				ItemStack itemstack = this.horseChest.getStackInSlot(i);

				if (itemstack != null)
				{
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte)i);
					itemstack.writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}

			nbttc.setTag("Items", nbttaglist);
		}

		if (this.horseChest.getStackInSlot(1) != null)
		{
			nbttc.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
		}

		if (this.horseChest.getStackInSlot(0) != null)
		{
			nbttc.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
		}
	}
}
