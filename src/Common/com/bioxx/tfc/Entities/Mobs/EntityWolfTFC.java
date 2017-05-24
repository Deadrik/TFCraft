package com.bioxx.tfc.Entities.Mobs;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIMateTFC;
import com.bioxx.tfc.Entities.AI.EntityAISitTFC;
import com.bioxx.tfc.Entities.AI.EntityAITargetNonTamedTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Util.Helper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class EntityWolfTFC extends EntityWolf implements IAnimal, IInnateArmor, ICausesDamage
{
	private static final float GESTATION_PERIOD = 2.25f;
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	 * the square root of the ratio of female mass : male mass
	 */
	private static final float DIMORPHISM = 0.0786f;
	private static final int DEGREE_OF_DIVERSION = 1;
	private static final int FAMILIARITY_CAP = 35;

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
	private int familiarity;
	private long lastFamiliarityUpdate;
	private boolean familiarizedToday;
	private int happyTicks;
	private boolean wasRoped;

	protected EntityAITargetNonTamedTFC targetChicken;
	protected EntityAITargetNonTamedTFC targetPheasant;
	protected EntityAITargetNonTamedTFC targetPig;
	protected EntityAITargetNonTamedTFC targetCow;
	protected EntityAITargetNonTamedTFC targetDeer;
	protected EntityAITargetNonTamedTFC targetHorse;
	private boolean peacefulAI;

	public EntityWolfTFC(World par1World)
	{
		super(par1World);
		this.tasks.addTask(6, new EntityAIMateTFC(this, worldObj, 1));
		this.targetTasks.removeTask(this.aiSit);
		this.aiSit = new EntityAISitTFC(this);
		this.tasks.addTask(2, this.aiSit);

		// TFC Targeting is affected by animal familiarity
		this.targetChicken = new EntityAITargetNonTamedTFC(this, EntityChickenTFC.class, 200, false);
		this.targetPheasant = new EntityAITargetNonTamedTFC(this, EntityPheasantTFC.class, 200, false);
		this.targetPig = new EntityAITargetNonTamedTFC(this, EntityPigTFC.class, 200, false);
		this.targetCow = new EntityAITargetNonTamedTFC(this, EntityCowTFC.class, 200, false);
		this.targetDeer = new EntityAITargetNonTamedTFC(this, EntityDeer.class, 200, false);
		this.targetHorse = new EntityAITargetNonTamedTFC(this, EntityHorseTFC.class, 200, false);
		if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL)
		{
			peacefulAI = false;
			this.targetTasks.addTask(7, targetChicken);
			this.targetTasks.addTask(7, targetPheasant);
			this.targetTasks.addTask(7, targetPig);
			this.targetTasks.addTask(7, targetCow);
			this.targetTasks.addTask(7, targetDeer);
			this.targetTasks.addTask(7, targetHorse);
		}
		else
			peacefulAI = true;

		hunger = 168000;
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		pregnant = false;
		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);
		timeOfConception = 0;
		mateSizeMod = 1f;
		sex = rand.nextInt(2);
		sizeMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt((DEGREE_OF_DIVERSION + 1) * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1F) * (1.0F - DIMORPHISM * sex));
		strengthMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + sizeMod));
		aggressionMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1));
		obedienceMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1f / aggressionMod));

		/*
		 * We hijack the growingAge to hold the day of birth rather than the number of ticks to the next growth event.
		 * We want spawned animals to be adults, so we set their birthdays far enough back in time such that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());

	}
	public EntityWolfTFC(World par1World, IAnimal mother, List<Float> data)
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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.WOLF_HEALTH);//MaxHealth
	}
	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		int damage = (int) (TFC_MobData.WOLF_DAMAGE * getStrengthMod() * getAggressionMod() * (getSizeMod() / 2 + 0.5F));
		//TerraFirmaCraft.log.info(var2+", s: "+getStrength()+", a: "+ getAggression());
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
	}
	@Override
	protected boolean canDespawn()
	{
		if (!this.isAdult()) // Babies can't despawn
			return false;
		if (this.getOwner() != null) // Can't despawn if fed bones
			return false;
		if (this.wasRoped) // Can't despawn if ever roped
			return false;

		return ticksExisted > 20000;
	}
	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}
	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityWolfTFC;
	}

	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player)
	{
		boolean flag = false;
		switch (interaction)
		{
		case BREED:
			flag = familiarity > 20;
			break;
		case NAME:
			flag = familiarity > 40;
			break; // 5 higher than adult cap
		default:
			break;
		}
		if (!flag && player != null && !player.worldObj.isRemote)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.notFamiliar"));
		}
		return flag;
	}

	@Override
	public EntityWolf createChild(EntityAgeable entityageable)
	{
		return (EntityWolf) createChildTFC(entityageable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityWolfTFC(worldObj, this, data);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		this.entityDropItem(new ItemStack(TFCItems.hide, 1, Math.max(0, Math.min(2, (int) (sizeMod * ageMod * 0.9)))), 0);
		this.dropItem(Items.bone, (int) ((rand.nextInt(3) + 1) * ageMod));
	}
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, Integer.valueOf(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0)); //age

		this.dataWatcher.addObject(22, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, happy ticks
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of conception, stored as a string since we can't do long
	}

	@Override
	public void familiarize(EntityPlayer ep)
	{
		if (happyTicks == 0 && familiarity >= 5 && !familiarizedToday && canFamiliarize())
		{
			familiarizedToday = true;
			this.getLookHelper().setLookPositionWithEntity(ep, 0, 0);
			this.playLivingSound();
			this.happyTicks = 40;
		}
		if (this.familiarity > 80 && this.getOwner() != null)
		{
			this.setTamed(true);
		}
	}
	@Override
	public float getAggressionMod()
	{
		return aggressionMod;
	}

	public long getAnimalID()
	{
		return animalID;
	}

	@Override
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("wolf");
	}
	@Override
	public Vec3 getAttackedVec()
	{
		return null;
	}
	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}
	@Override
	public int getCrushArmor()
	{
		return -335;
	}

	@Override
	public EnumDamageType getDamageType()
	{
		return EnumDamageType.CRUSHING;
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
	public int getFamiliarity()
	{
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
		return null;
	}

	@Override
	public GenderEnum getGender()
	{
		return GenderEnum.GENDERS[dataWatcher.getWatchableObjectInt(13)];
	}


	public int getHappyTicks()
	{
		return this.happyTicks;
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

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 9);
	}

	@Override
	public float getObedienceMod()
	{
		return obedienceMod;
	}

	@Override
	public int getPierceArmor()
	{
		return 250;
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
	public int getSlashArmor()
	{
		return 250;
	}

	@Override
	public float getStrengthMod()
	{
		return strengthMod;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getTailRotation()
	{
		float scale = this.getMaxHealth() / 20.0F; // Vanilla wolves use a value of 20.0F
		if (this.isAngry())
			return 1.5393804F;
		else if (this.getOwner() != null)
			return (0.55F - ((this.getMaxHealth() - this.dataWatcher.getWatchableObjectFloat(18)) / scale) * 0.02F) * (float) Math.PI;
		else
			return (float) Math.PI / 5F;
	}

	public long getTimeOfConception()
	{
		return timeOfConception;
	}

	@Override
	public void handleFamiliarityUpdate()
	{
		int totalDays = TFC_Time.getTotalDays();
		if (lastFamiliarityUpdate < totalDays)
		{
			if (familiarizedToday && familiarity < 100)
			{
				lastFamiliarityUpdate = totalDays;
				familiarizedToday = false;
				float familiarityChange = 6 * obedienceMod / aggressionMod;
				if (this.isAdult() && familiarity >= 5 && familiarity <= FAMILIARITY_CAP)
				{
					familiarity += familiarityChange;
				}
				else if (!this.isAdult())
				{
					float ageMod = 2f / (1f + TFC_Core.getPercentGrown(this));
					familiarity += ageMod * familiarityChange;
					if (familiarity > 70)
					{
						obedienceMod *= 1.01f;
					}
				}
			}
			else if (familiarity < 30)
			{
				familiarity -= 2 * (TFC_Time.getTotalDays() - lastFamiliarityUpdate);
				lastFamiliarityUpdate = totalDays;
			}
		}
		if (familiarity > 100)
			familiarity = 100;
		if (familiarity < (this.getOwner() != null ? 5 : 0))
			familiarity = (this.getOwner() != null ? 5 : 0);
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if (player.isSneaking() && this.getOwner() != null && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}
			if(player.getHeldItem() != null)
			{
				ItemStack is = player.getHeldItem();
				if(isFood(is))
				{
					Item item = is.getItem();
					if(item instanceof ItemFoodTFC && hunger <= 160000)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ((ItemFoodTFC) item).onConsumedByEntity(player.getHeldItem(), worldObj, this));
						this.hunger += 24000;
						return true;
					}
				}
			}

			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation(getGender() == GenderEnum.FEMALE ? "entity.female" : "entity.male"));
			if(getGender() == GenderEnum.FEMALE && pregnant)
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));
		}

		ItemStack itemstack = player.inventory.getCurrentItem();

		if (itemstack != null)
		{
			if (this.isBreedingItemTFC(itemstack) && checkFamiliarity(InteractionEnum.BREED, player) && this.getGrowingAge() == 0 && !super.isInLove())
			{
				if (!player.capabilities.isCreativeMode)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, ((ItemFoodTFC) itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this));
				}

				this.func_146082_f(player);
				return true;
			}
			else if (itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName"))
			{
				if (this.trySetName(itemstack.stackTagCompound.getString("ItemName"), player))
				{
					itemstack.stackSize--;
				}
				return true;
			}
			else if (itemstack.getItem() == Items.bone && !this.isAngry())
			{
				if (this.getOwner() == null)
				{
					if (!player.capabilities.isCreativeMode)
					{
						--itemstack.stackSize;
					}

					if (itemstack.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
					}

					if (!this.worldObj.isRemote)
					{
						if (this.rand.nextInt(3) == 0)
						{
							this.setTamed(true);
							this.setPathToEntity((PathEntity) null);
							this.setAttackTarget((EntityLivingBase) null);
							this.func_152115_b(player.getUniqueID().toString());
							this.playTameEffect(true);
							this.worldObj.setEntityState(this, (byte) 7);
						}
						else
						{
							this.playTameEffect(false);
							this.worldObj.setEntityState(this, (byte) 6);
						}
					}
				}

				return true;
			}

			else if (isTamed() && (itemstack.getItem() == Items.dye || itemstack.getItem() == TFCItems.dye))
			{
				int i = BlockColored.func_150032_b(itemstack.getItemDamage());

				if (i != this.getCollarColor())
				{
					this.setCollarColor(i);

					if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
					}
				}

				return true;
			}
		}

		return super.interact(player);
	}

	@Override
	public boolean isAdult()
	{
		return getBirthDay() + getNumberOfDaysToAdult() <= TFC_Time.getTotalDays();
	}

	@Override
	public boolean isBreedingItem(ItemStack is)
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
	public boolean isFood(ItemStack item)
	{
		return item != null &&
				(item.getItem() == TFCItems.beefRaw ||item.getItem() == TFCItems.chickenRaw || item.getItem() == TFCItems.fishRaw ||
					item.getItem() == TFCItems.horseMeatRaw || item.getItem() == TFCItems.muttonRaw || item.getItem() == TFCItems.porkchopRaw ||
					item.getItem() == TFCItems.venisonRaw); // All meat except for calamari.
	}

	public boolean isPeacefulAI()
	{
		return peacefulAI;
	}

	@Override
	public boolean isPregnant()
	{
		return pregnant;
	}

	public boolean hasBeenRoped()
	{
		return wasRoped;
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
		//Handle Hunger ticking
		if (hunger > 168000)
			hunger = 168000;
		if (hunger > 0)
			hunger--;

		if (this.getLeashed())
		{
			Entity leashedTo = getLeashedToEntity();
			// Wolves who have been given a bone, are tied to a fence, and are not angry/targeting another animal must sit
			if (leashedTo instanceof EntityLeashKnot && familiarity >= 5 && !this.isAngry())
			{
				this.aiSit.setSitting(true);
				this.setSitting(true);
				this.isJumping = false;
				// Set everything to null to prevent butt scooching
				this.setPathToEntity((PathEntity) null);
				this.setTarget((Entity) null);
				this.setAttackTarget((EntityLivingBase) null);
			}
			// Animals who are tied to a player, or are tied to a fence and are angry/targeting another animal are allowed to stand up and move about
			else if (leashedTo instanceof EntityPlayer || leashedTo instanceof EntityLeashKnot && this.isAngry())
			{
				this.aiSit.setSitting(false);
				this.setSitting(false);
			}

			if (!wasRoped)
				wasRoped = true;
		}
		// Unleashed, but still sitting, and angry/targeting another animal
		else if (this.isAngry() && this.isSitting())
		{
			this.aiSit.setSitting(false);
			this.setSitting(false);
		}

		if (super.isInLove())
			setInLove(true);

		if (isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		this.handleFamiliarityUpdate();

		if (happyTicks > 0)
			happyTicks--;

		syncData();

		if (!this.worldObj.isRemote && isPregnant())
		{
			if (TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
				int i = rand.nextInt(2) + 1;
				for (int x = 0; x < i; x++)
				{
					ArrayList<Float> data = new ArrayList<Float>();
					data.add(mateSizeMod);
					EntityWolfTFC baby = (EntityWolfTFC) this.createChildTFC(this);
					baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
					baby.rotationYawHead = baby.rotationYaw;
					baby.renderYawOffset = baby.rotationYaw;
					baby.setAge(TFC_Time.getTotalDays());
					worldObj.spawnEntityInWorld(baby);
				}
				pregnant = false;
			}
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

		if (hunger > 144000 && rand.nextInt(100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		else if (hunger < 144000 && super.isInLove())
		{
			this.setInLove(false);
		}

		// Owners can leash a dog to themselves to calm it down
		if (this.getLeashed() && this.isAngry() && getLeashedToEntity() == this.getOwner())
		{
			this.setAngry(false);
			this.setPathToEntity((PathEntity) null);
			this.setTarget((Entity) null);
			this.setAttackTarget((EntityLivingBase) null);
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!this.worldObj.isRemote)
		{
			if (!peacefulAI && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
			{
				peacefulAI = true;
				this.targetTasks.removeTask(targetChicken);
				this.targetTasks.removeTask(targetPheasant);
				this.targetTasks.removeTask(targetPig);
				this.targetTasks.removeTask(targetCow);
				this.targetTasks.removeTask(targetDeer);
				this.targetTasks.removeTask(targetHorse);
			}
			else if (peacefulAI && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL)
			{
				peacefulAI = false;
				this.targetTasks.addTask(7, targetChicken);
				this.targetTasks.addTask(7, targetPheasant);
				this.targetTasks.addTask(7, targetPig);
				this.targetTasks.addTask(7, targetCow);
				this.targetTasks.addTask(7, targetDeer);
				this.targetTasks.addTask(7, targetHorse);
			}
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setAngry(nbt.getBoolean("Angry"));
		animalID = nbt.getLong("Animal ID");
		sex = nbt.getInteger("Sex");
		sizeMod = nbt.getFloat("Size Modifier");

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");

		strengthMod = nbt.getFloat("Strength Modifier");
		aggressionMod = nbt.getFloat("Aggression Modifier");
		obedienceMod = nbt.getFloat("Obedience Modifier");

		this.dataWatcher.updateObject(16, nbt.getByte("tamed"));
		this.happyTicks = nbt.getInteger("happy");

		wasRoped = nbt.getBoolean("wasRoped");

		hunger = nbt.getInteger("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		mateStrengthMod = nbt.getFloat("MateStrength");
		mateAggroMod = nbt.getFloat("MateAggro");
		mateObedMod = nbt.getFloat("MateObed");
		timeOfConception = nbt.getLong("ConceptionTime");
		this.dataWatcher.updateObject(15, nbt.getInteger("Age"));
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

	public void setAnimalID(long animalID)
	{
		this.animalID = animalID;
	}

	@Override
	public void setAttackedVec(Vec3 attackedVec)
	{
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
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if (!TFC_Core.preventEntityDataUpdate)
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
	}

	public void setHappyTicks(int happyTicks)
	{
		this.happyTicks = happyTicks;
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

	public void setPeacefulAI(boolean isPeacefulAI)
	{
		this.peacefulAI = isPeacefulAI;
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

	@Override
	public void setTamed(boolean par1)
	{
		if (this.familiarity > 80 && !this.isTamed())
		{
			super.setTamed(par1);

			double healthRatio = this.getHealth() / this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();

			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.WOLF_HEALTH);
			float h = (float) (healthRatio * this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
			this.setHealth(h);
		}
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
		if (dataWatcher != null)
		{
			if (!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));

				byte[] values = { TFC_Core.getByteFromSmallFloat(sizeMod), TFC_Core.getByteFromSmallFloat(strengthMod), TFC_Core.getByteFromSmallFloat(aggressionMod), TFC_Core.getByteFromSmallFloat(obedienceMod), (byte) familiarity, (byte) (familiarizedToday
						? 1 : 0), (byte) (pregnant ? 1 : 0), (byte) happyTicks
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
				happyTicks = values[7];

				try
				{
					timeOfConception = Long.parseLong(this.dataWatcher.getWatchableObjectString(24));
				} catch (NumberFormatException e)
				{
				}
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
		this.playSound("mob.wolf.growl", 6, rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		return false;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Angry", this.isAngry());
		nbt.setInteger("Familiarity", familiarity);
		nbt.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbt.setBoolean("Familiarized Today", familiarizedToday);
		nbt.setInteger("Sex", sex);
		nbt.setLong("Animal ID", animalID);
		nbt.setFloat("Size Modifier", sizeMod);

		nbt.setByte("tamed", this.dataWatcher.getWatchableObjectByte(16));
		nbt.setInteger("happy", happyTicks);

		nbt.setBoolean("wasRoped", wasRoped);

		nbt.setFloat("Strength Modifier", getStrengthMod());
		nbt.setFloat("Aggression Modifier", getAggressionMod());
		nbt.setFloat("Obedience Modifier", obedienceMod);

		nbt.setInteger("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setFloat("MateStrength", mateStrengthMod);
		nbt.setFloat("MateAggro", mateAggroMod);
		nbt.setFloat("MateObed", mateObedMod);
		nbt.setLong("ConceptionTime", timeOfConception);
		nbt.setInteger("Age", getBirthDay());
	}
}
