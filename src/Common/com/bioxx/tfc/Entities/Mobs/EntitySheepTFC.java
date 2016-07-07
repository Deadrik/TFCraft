package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.IShearable;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.AIEatGrass;
import com.bioxx.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.bioxx.tfc.Entities.AI.EntityAIMateTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Util.Helper;

public class EntitySheepTFC extends EntitySheep implements IShearable, IAnimal
{
	/**
	 * Holds the RGB table of the sheep colors - in OpenGL glColor3f values - used to render the sheep colored fleece.
	 */
	public static final float[][] FLEECE_COLOR_TABLE = new float[][] {{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

	private static final float GESTATION_PERIOD = 5.0f;

	/*
	 * 1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	 * the square root of the ratio of female mass : male mass
	 */
	private static final float DIMORPHISM = 0.1633f;
	private static final int DEGREE_OF_DIVERSION = 2;
	private static final int FAMILIARITY_CAP = 35;
	private final InventoryCrafting colorCrafting = new InventoryCrafting(new Container()
	{
		@Override
		public boolean canInteractWith(EntityPlayer p_75145_1_)
		{
			return false;
		}
	}, 2, 1);
	/**
	 * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
	 * tick.
	 */
	private int sheepTimer;
	/** The eat grass AI task for this mob. */
	protected final AIEatGrass aiEatGrass = new AIEatGrass(this);

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
	private int mateColor;
	private float sizeMod; //How large the animal is
	private float strengthMod; //how strong the animal is
	private float aggressionMod = 1;//How aggressive / obstinate the animal is
	private float obedienceMod = 1; //How well the animal responds to commands.
	private boolean inLove;
	private EntityPlayer shearer;
	private int familiarity;
	private long lastFamiliarityUpdate;
	private boolean familiarizedToday;

	public EntitySheepTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.9F, 1.3F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(2, new EntityAIMateTFC(this,worldObj, 1.0f));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.wheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.ryeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.riceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.barleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.oatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.maizeEar, false));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16f, 0.25F, 0.3F));
		this.tasks.addTask(6, this.aiEatGrass);

		this.colorCrafting.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
		this.colorCrafting.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));

		hunger = 168000;
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		pregnant = false;
		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);
		timeOfConception = 0;
		mateSizeMod = 0;
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
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntitySheepTFC(World par1World, IAnimal mother, List<Float> data)
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
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntitySheepTFC;
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
		case SHEAR:
			flag = familiarity > 10;
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

	public int combineColors(EntityAnimal parent, int mateColor)
	{
		int parent1Color = 15 - ((EntitySheep) parent).getFleeceColor();
		int parent2Color = 15 - mateColor;
		this.colorCrafting.getStackInSlot(0).setItemDamage(parent1Color);
		this.colorCrafting.getStackInSlot(1).setItemDamage(parent2Color);
		ItemStack itemstack = CraftingManager.getInstance().findMatchingRecipe(this.colorCrafting, ((EntitySheep) parent).worldObj);
		int babyColor;

		if (itemstack != null && itemstack.getItem() == Items.dye)
		{
			babyColor = itemstack.getItemDamage();
		}
		else
		{
			babyColor = this.worldObj.rand.nextBoolean() ? parent1Color : parent2Color;
		}

		return babyColor;
	}

	@Override
	public EntitySheep createChild(EntityAgeable entityageable)
	{
		return (EntitySheep) createChildTFC(entityageable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		EntitySheepTFC baby = new EntitySheepTFC(worldObj, this, data);
		int colorMeta = this.combineColors(this, ((EntitySheepTFC) eAgeable).mateColor);
		baby.setFleeceColor(15 - colorMeta);
		return baby;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		if (!this.getSheared())
			this.entityDropItem(new ItemStack(TFCItems.sheepSkin, 1, Math.max(0, Math.min(2, (int) (ageMod * sizeMod)))), 0.0F);
		else
			this.entityDropItem(new ItemStack(TFCItems.hide, 1, Math.max(0, Math.min(2, (int) (ageMod * sizeMod)))), 0.0F);

		this.dropItem(Items.bone, (int) ((rand.nextInt(5) + 2) * ageMod));

		float foodWeight = ageMod * (this.sizeMod * 640);
		TFC_Core.animalDropMeat(this, TFCItems.muttonRaw, foodWeight);
	}

	@Override
	public void eatGrassBonus()
	{
		this.setSheared(false);
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
	public void familiarize(EntityPlayer ep)
	{
		ItemStack stack = ep.getHeldItem();
		if (stack != null && !familiarizedToday && this.isFood(stack) && canFamiliarize())
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
		return Helper.stringToInt("sheep");
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

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return TFCItems.wool;
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
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 12);
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

	public EntityPlayer getShearer()
	{
		return shearer;
	}
	
	public int getSheepTimer()
	{
		return sheepTimer;
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
				if (this.isAdult() && familiarity <= FAMILIARITY_CAP)
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
		if (familiarity < 0)
			familiarity = 0;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if (player.isSneaking() && !familiarizedToday && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}

			if(getGender() == GenderEnum.FEMALE && pregnant)
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));

			this.shearer = player;

			// Shearing with a pair of shears is handled with ItemShears
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemKnife && !getSheared() && this.checkFamiliarity(InteractionEnum.SHEAR, player) && isAdult())
			{
				if (!familiarizedToday && this.familiarity <= FAMILIARITY_CAP)
				{
					familiarizedToday = true;
					this.getLookHelper().setLookPositionWithEntity(player, 0, 0);
					this.playLivingSound();
				}
				setSheared(true);
				this.entityDropItem(new ItemStack(TFCItems.wool, 1), 0.0F);
				if (!player.capabilities.isCreativeMode)
					player.getHeldItem().damageItem(1, player);
			}
		}

		ItemStack itemstack = player.inventory.getCurrentItem();

		if (itemstack != null && this.isBreedingItemTFC(itemstack) && checkFamiliarity(InteractionEnum.BREED, player) && this.getGrowingAge() == 0 && !super.isInLove() &&
			(this.familiarizedToday || !canFamiliarize()))
		{
			if (!player.capabilities.isCreativeMode)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, ((ItemFoodTFC) itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this));
			}

			this.hunger += 24000;
			this.func_146082_f(player);
			return true;
		}
		else if(itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName")){
			if(this.trySetName(itemstack.stackTagCompound.getString("ItemName"),player)){
				itemstack.stackSize--;
			}
			return true;
		}
		else
		{
			return super.interact(player);
		}
	}

	@Override
	public boolean isAdult()
	{
		return getBirthDay() + getNumberOfDaysToAdult() <= TFC_Time.getTotalDays();
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
	public boolean isFood(ItemStack item)
	{
		return item != null && (item.getItem() == TFCItems.wheatGrain ||item.getItem() == TFCItems.oatGrain || item.getItem() == TFCItems.riceGrain ||
								item.getItem() == TFCItems.barleyGrain || item.getItem() == TFCItems.ryeGrain || item.getItem() == TFCItems.maizeEar);
	}

	@Override
	public boolean isPregnant()
	{
		return pregnant;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z)
	{
		return !getSheared() && isAdult() && shearer != null && checkFamiliarity(InteractionEnum.SHEAR, shearer);
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
		mateAggroMod = otherAnimal.getAggressionMod();
		mateObedMod = otherAnimal.getObedienceMod();
		mateSizeMod = otherAnimal.getSizeMod();
		mateStrengthMod = otherAnimal.getStrengthMod();
		mateColor = ((EntitySheepTFC) otherAnimal).getFleeceColor();
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		if (this.worldObj.isRemote)
			this.sheepTimer = Math.max(0, this.sheepTimer - 1);

		//Handle Hunger ticking
		if (hunger > 168000)
			hunger = 168000;
		if (hunger > 0)
			hunger--;

		if (super.isInLove())
		{
			super.resetInLove();
			setInLove(true);
		}

		this.handleFamiliarityUpdate();

		syncData();
		if (isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		if (!this.worldObj.isRemote && isPregnant())
			if (TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
				int i = rand.nextInt(3) + 1;
				for (int x = 0; x < i; x++)
				{
					EntitySheepTFC baby = (EntitySheepTFC) createChildTFC(this);
					baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
					baby.rotationYawHead = baby.rotationYaw;
					baby.renderYawOffset = baby.rotationYaw;
					worldObj.spawnEntityInWorld(baby);
					baby.setAge(TFC_Time.getTotalDays());
				}
				pregnant = false;
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
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		setSheared(true);
		ret.add(new ItemStack(TFCItems.wool, 2));
		if (!familiarizedToday && this.familiarity <= FAMILIARITY_CAP)
		{
			familiarizedToday = true;
			this.playLivingSound();
		}
		this.worldObj.playSoundAtEntity(this, "mob.sheep.shear", 1.0F, 1.0F);
		return ret;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		animalID = nbt.getLong("Animal ID");
		sex = nbt.getInteger("Sex");
		sizeMod = nbt.getFloat("Size Modifier");

		strengthMod = nbt.getFloat("Strength Modifier");
		aggressionMod = nbt.getFloat("Aggression Modifier");
		obedienceMod = nbt.getFloat("Obedience Modifier");

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");

		hunger = nbt.getInteger("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		mateStrengthMod = nbt.getFloat("MateStrength");
		mateAggroMod = nbt.getFloat("MateAggro");
		mateObedMod = nbt.getFloat("MateObed");
		mateColor = nbt.getInteger("MateColor");
		timeOfConception = nbt.getLong("ConceptionTime");
		this.dataWatcher.updateObject(15, nbt.getInteger("Age"));
		this.setAge(nbt.getInteger("Age"));
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
		// None
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
		// None
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if (!TFC_Core.preventEntityDataUpdate)
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
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

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	public void setShearer(EntityPlayer shearer)
	{
		this.shearer = shearer;
	}

	public void setSheepTimer(int sheepTimer)
	{
		this.sheepTimer = sheepTimer;
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
		if (dataWatcher != null)
		{
			if (!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));

				byte[] values = { TFC_Core.getByteFromSmallFloat(sizeMod), TFC_Core.getByteFromSmallFloat(strengthMod), TFC_Core.getByteFromSmallFloat(aggressionMod), TFC_Core.getByteFromSmallFloat(obedienceMod), (byte) familiarity, (byte) (familiarizedToday
						? 1 : 0), (byte) (pregnant ? 1 : 0), (byte) 0 // Empty
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
		this.playSound(this.getHurtSound(), 6, rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		return false;
	}

	@Override
	protected void updateAITasks()
	{
		this.sheepTimer = this.aiEatGrass.getEatGrassTick();
		super.updateAITasks();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Sex", sex);
		nbt.setLong("Animal ID", animalID);
		nbt.setFloat("Size Modifier", sizeMod);

		nbt.setInteger("Familiarity", familiarity);
		nbt.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbt.setBoolean("Familiarized Today", familiarizedToday);

		nbt.setFloat("Strength Modifier", strengthMod);
		nbt.setFloat("Aggression Modifier", aggressionMod);
		nbt.setFloat("Obedience Modifier", obedienceMod);

		nbt.setInteger("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setFloat("MateStrength", mateStrengthMod);
		nbt.setFloat("MateAggro", mateAggroMod);
		nbt.setFloat("MateObed", mateObedMod);
		nbt.setInteger("MateColor", mateColor);
		nbt.setLong("ConceptionTime", timeOfConception);
		nbt.setInteger("Age", getBirthDay());
	}
}
