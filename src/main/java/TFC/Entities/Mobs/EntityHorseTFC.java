package TFC.Entities.Mobs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.AIEatGrass;
import TFC.Entities.AI.EntityAIAvoidEntityTFC;
import TFC.Entities.AI.EntityAIMateTFC;
import TFC.Entities.AI.EntityAIPanicTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityHorseTFC extends EntityHorse implements IInvBasic, IAnimal
{
	private static final IEntitySelector horseBreedingSelector = new EntityHorseBredSelector();
	private static final IAttribute horseJumpStrength = (new RangedAttribute("horse.jumpStrengthTFC", 0.7D, 0.0D, 2.0D)).setDescription("Jump StrengthTFC").setShouldWatch(true);
	private static final String[] horseArmorTextures = new String[] {null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png"};
	private static final String[] field_110273_bx = new String[] {"", "meo", "goo", "dio"};
	private static final int[] armorValues = new int[] {0, 5, 7, 11};
	private static final String[] horseTextures = new String[] {"textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
	private static final String[] field_110269_bA = new String[] {"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
	private static final String[] horseMarkingTextures = new String[] {null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
	private static final String[] field_110292_bC = new String[] {"", "wo_", "wmo", "wdo", "bdo"};
	private int eatingHaystackCounter;
	private int openMouthCounter;
	private int jumpRearingCounter;
	private AnimalChest horseChest;
	private boolean hasReproduced;

	public int inLove;
	private int breeding;

	private final AIEatGrass aiEatGrass = new AIEatGrass(this);
	protected long animalID;
	protected int sex = 0;
	protected int hunger = 0;
	protected long hasMilkTime;
	protected boolean pregnant;
	protected int pregnancyRequiredTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod;
	public float strength_mod = 1;
	public float aggression_mod = 1;
	public float obedience_mod = 1;
	public float colour_mod = 1;
	public float climate_mod = 1;
	public float hard_mod = 1;
	public boolean isInLove;
	public Vec3 attackedVec = null;
	public Entity fearSource = null;

	int degreeOfDiversion = 2;

	/**
	 * "The higher this value, the more likely the horse is to be tamed next time a player rides it."
	 */
	private boolean field_110294_bI;
	private float headLean;
	private float prevHeadLean;
	private float rearingAmount;
	private float prevRearingAmount;
	private float mouthOpenness;
	private float prevMouthOpenness;
	private int field_110285_bP;
	private String field_110286_bQ;
	private String[] field_110280_bR = new String[3];


	public EntityHorseTFC(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime =(int)(4 * TFC_Time.ticksInMonth);
		conception = 0;
		mateSizeMod = 0;
		sex = rand.nextInt(2);
		size_mod =(float)Math.sqrt((((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) * 0.1f) + 1F) * (1.0F - 0.1F * sex));
		this.setSize(0.9F, 1.3F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 1.0F));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16f, 0.25F, 0.3F));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RyeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RiceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.BarleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.OatGrain, false));
		this.tasks.addTask(6, this.aiEatGrass);
		for(Object aiTask : this.tasks.taskEntries)
		{
			if(aiTask instanceof EntityAIPanic)
				this.tasks.removeTask((EntityAIBase)aiTask);
		}
		this.tasks.addTask(1, new EntityAIPanicTFC(this, 1.2D,true));
		this.func_110226_cD();

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		//this.setGrowingAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		this.setAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntityHorseTFC(World par1World, IAnimal mother,  ArrayList<Float> data)
	{
		this(par1World);
		float father_size = data.get(0);
		this.posX = ((EntityLivingBase)mother).posX;
		this.posY = ((EntityLivingBase)mother).posY;
		this.posZ = ((EntityLivingBase)mother).posZ;
		size_mod = (float)Math.sqrt((((rand.nextInt ((degreeOfDiversion+1)*10)*(rand.nextBoolean()?1:-1)) / 100f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size)/1.95F));

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}

	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (hunger > 168000)
			hunger = 168000;
		if (hunger > 0)
			hunger--;

		if(super.isInLove())
		{
			super.resetInLove();;
			setInLove(true);
		}

		syncData();
		if(isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		if(isPregnant())
		{
			if(TFC_Time.getTotalTicks() >= conception + pregnancyRequiredTime)
			{
				EntityHorseTFC baby = (EntityHorseTFC) createChildTFC(this);
				baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				worldObj.spawnEntityInWorld(baby);
				baby.setAge((int)TFC_Time.getTotalDays());
				pregnant = false;
			}
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.PreventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
			this.heal(1);
	}

	private float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = (int) TFC_Time.getTotalDays();
		float percent =(time-birth)/animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
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

	public void setHorseType(int par1)
	{
		this.dataWatcher.updateObject(19, Byte.valueOf((byte)par1));
		this.func_110230_cF();
	}

	/**
	 * returns the horse type
	 */
	public int getHorseType()
	{
		return this.dataWatcher.getWatchableObjectByte(19);
	}

	public void setHorseVariant(int par1)
	{
		this.dataWatcher.updateObject(20, Integer.valueOf(par1));
		this.func_110230_cF();
	}

	public int getHorseVariant()
	{
		return this.dataWatcher.getWatchableObjectInt(20);
	}

	/**
	 * Gets the username of the entity.
	 */
	public String getEntityName()
	{
		if (this.hasCustomNameTag())
		{
			return this.getCustomNameTag();
		}
		else
		{
			int i = this.getHorseType();

			switch (i)
			{
			case 0:
			default:
				return StatCollector.translateToLocal("entity.horse.name");
			case 1:
				return StatCollector.translateToLocal("entity.donkey.name");
			case 2:
				return StatCollector.translateToLocal("entity.mule.name");
			case 3:
				return StatCollector.translateToLocal("entity.zombiehorse.name");
			case 4:
				return StatCollector.translateToLocal("entity.skeletonhorse.name");
			}
		}
	}

	private boolean getHorseWatchableBoolean(int par1)
	{
		return (this.dataWatcher.getWatchableObjectInt(16) & par1) != 0;
	}

	private void setHorseWatchableBoolean(int par1, boolean par2)
	{
		int j = this.dataWatcher.getWatchableObjectInt(16);

		if (par2)
			this.dataWatcher.updateObject(16, Integer.valueOf(j | par1));
		else
			this.dataWatcher.updateObject(16, Integer.valueOf(j & ~par1));
	}

	public boolean isAdultHorse()
	{
		return !this.isChild();
	}

	public boolean isTame()
	{
		return this.getHorseWatchableBoolean(2);
	}

	public boolean func_110253_bW()
	{
		return this.isAdultHorse();
	}

	public String getOwnerName()
	{
		return this.dataWatcher.getWatchableObjectString(21);
	}

	public void setOwnerName(String par1Str)
	{
		this.dataWatcher.updateObject(21, par1Str);
	}

	public float getHorseSize()
	{
		int i = this.getGrowingAge();
		return i >= 0 ? 1.0F : 0.5F + (float)(-24000 - i) / -24000.0F * 0.5F;
	}

	/**
	 * "Sets the scale for an ageable entity according to the boolean parameter, which says if it's a child."
	 */
	public void setScaleForAge(boolean par1)
	{
		if (par1)
			this.setScale(this.getHorseSize());
		else
			this.setScale(1.0F);
	}

	public boolean isHorseJumping()
	{
		return this.horseJumping;
	}

	public void setHorseTamed(boolean par1)
	{
		this.setHorseWatchableBoolean(2, par1);
	}

	public void setHorseJumping(boolean par1)
	{
		this.horseJumping = par1;
	}

	public boolean allowLeashing()
	{
		return !this.func_110256_cu() && super.allowLeashing();
	}

	protected void func_142017_o(float par1)
	{
		if (par1 > 6.0F && this.isEatingHaystack())
			this.setEatingHaystack(false);
	}

	public boolean isChested()
	{
		return this.getHorseWatchableBoolean(8);
	}

	public int func_110241_cb()
	{
		return this.dataWatcher.getWatchableObjectInt(22);
	}

	/**
	 * 0 = iron, 1 = gold, 2 = diamond
	 */
	public int getHorseArmorIndex(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? 0 : (par1ItemStack.getItem() == Items.iron_horse_armor ? 1 : (par1ItemStack.getItem() == Items.golden_horse_armor ? 2 : (par1ItemStack.getItem() == Items.diamond_horse_armor ? 3 : 0)));
	}

	public boolean isEatingHaystack()
	{
		return this.getHorseWatchableBoolean(32);
	}

	public boolean isRearing()
	{
		return this.getHorseWatchableBoolean(64);
	}

	public boolean func_110205_ce()
	{
		return this.getHorseWatchableBoolean(16);
	}

	public boolean getHasReproduced()
	{
		return this.hasReproduced;
	}

	public void func_110236_r(int par1)
	{
		this.dataWatcher.updateObject(22, Integer.valueOf(par1));
		this.func_110230_cF();
	}

	public void func_110242_l(boolean par1)
	{
		this.setHorseWatchableBoolean(16, par1);
	}

	public void setChested(boolean par1)
	{
		this.setHorseWatchableBoolean(8, par1);
	}

	public void setHasReproduced(boolean par1)
	{
		this.hasReproduced = par1;
	}

	public void setHorseSaddled(boolean par1)
	{
		this.setHorseWatchableBoolean(4, par1);
	}

	public int getTemper()
	{
		return this.temper;
	}

	public void setTemper(int par1)
	{
		this.temper = par1;
	}

	public int increaseTemper(int par1)
	{
		int j = MathHelper.clamp_int(this.getTemper() + par1, 0, this.getMaxTemper());
		this.setTemper(j);
		return j;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		Entity entity = par1DamageSource.getEntity();
		if(entity != null)
		{
			setAttackedVec(Vec3.fakePool.getVecFromPool(entity.posX, entity.posY, entity.posZ));
			setFearSource(entity);
		}
		return this.riddenByEntity != null && this.riddenByEntity.equals(entity) ? false : super.attackEntityFrom(par1DamageSource, par2);
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	public int getTotalArmorValue()
	{
		return armorValues[this.func_110241_cb()];
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	public boolean canBePushed()
	{
		return this.riddenByEntity == null;
	}

	public boolean prepareChunkForSpawn()
	{
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);
		this.worldObj.getBiomeGenForCoords(i, j);
		return true;
	}

	public void dropChests()
	{
		if (!this.worldObj.isRemote && this.isChested())
		{
			this.dropItem(Item.getItemFromBlock(TFCBlocks.Chest), 1);
			this.setChested(false);
		}
	}

	private void func_110266_cB()
	{
		this.openHorseMouth();
		this.worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	protected void fall(float par1)
	{
		if (par1 > 1.0F)
			this.playSound("mob.horse.land", 0.4F, 1.0F);

		int i = MathHelper.ceiling_float_int(par1 * 0.5F - 3.0F);

		if (i > 0)
		{
			this.attackEntityFrom(DamageSource.fall, (float)i);

			if (this.riddenByEntity != null)
				this.riddenByEntity.attackEntityFrom(DamageSource.fall, (float)i);

			Block j = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.2D - (double)this.prevRotationYaw), MathHelper.floor_double(this.posZ));
			if (j != Blocks.air)
			{
				SoundType stepsound = j.stepSound;
				this.worldObj.playSoundAtEntity(this, stepsound.getStepResourcePath(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
			}
		}
	}

	private int func_110225_cC()
	{
		int i = this.getHorseType();
		return this.isChested() && (i == 1 || i == 2) ? 17 : 2;
	}

	private void func_110226_cD()
	{
		AnimalChest animalchest = this.horseChest;
		this.horseChest = new AnimalChest("HorseChest", this.func_110225_cC());
		this.horseChest.func_110133_a(this.getEntityName());

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
		this.func_110232_cE();
	}

	private void func_110232_cE()
	{
		if (!this.worldObj.isRemote)
		{
			this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
			if (this.func_110259_cr())
				this.func_110236_r(this.getHorseArmorIndex(this.horseChest.getStackInSlot(1)));
		}
	}

	/**
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	 */
	public void onInventoryChanged(InventoryBasic par1InventoryBasic)
	{
		int i = this.func_110241_cb();
		boolean flag = this.isHorseSaddled();
		this.func_110232_cE();

		if (this.ticksExisted > 20)
		{
			if (i == 0 && i != this.func_110241_cb())
				this.playSound("mob.horse.armor", 0.5F, 1.0F);
			if (!flag && this.isHorseSaddled())
				this.playSound("mob.horse.leather", 0.5F, 1.0F);
		}
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	public boolean getCanSpawnHere()
	{
		this.prepareChunkForSpawn();
		return super.getCanSpawnHere();
	}

	protected EntityHorseTFC getClosestHorse(Entity par1Entity, double par2)
	{
		double d1 = Double.MAX_VALUE;
		Entity entity1 = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par1Entity.boundingBox.addCoord(par2, par2, par2), horseBreedingSelector);
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			Entity entity2 = (Entity)iterator.next();
			double d2 = entity2.getDistanceSq(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
			if (d2 < d1)
			{
				entity1 = entity2;
				d1 = d2;
			}
		}

		return (EntityHorseTFC)entity1;
	}

	public double getHorseJumpStrength()
	{
		return this.getEntityAttribute(horseJumpStrength).getAttributeValue();
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		this.openHorseMouth();
		int i = this.getHorseType();
		return i == 3 ? "mob.horse.zombie.death" : (i == 4 ? "mob.horse.skeleton.death" : (i != 1 && i != 2 ? "mob.horse.death" : "mob.horse.donkey.death"));
	}

	protected Item getDropItem()
	{
		boolean flag = this.rand.nextInt(4) == 0;
		int i = this.getHorseType();
		return i == 4 ? Items.bone : (i == 3 ? (flag ? Item.getItemById(0) : Items.rotten_flesh) : Items.leather);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		this.openHorseMouth();

		if (this.rand.nextInt(3) == 0)
			this.makeHorseRear();

		int i = this.getHorseType();
		return i == 3 ? "mob.horse.zombie.hit" : (i == 4 ? "mob.horse.skeleton.hit" : (i != 1 && i != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit"));
	}

	public boolean isHorseSaddled()
	{
		return this.getHorseWatchableBoolean(4);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		this.openHorseMouth();

		if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked())
			this.makeHorseRear();

		int i = this.getHorseType();
		return i == 3 ? "mob.horse.zombie.idle" : (i == 4 ? "mob.horse.skeleton.idle" : (i != 1 && i != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle"));
	}

	protected String getAngrySoundName()
	{
		this.openHorseMouth();
		this.makeHorseRear();
		int i = this.getHorseType();
		return i != 3 && i != 4 ? (i != 1 && i != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
	}

	/**
	 * Plays step sound at given x, y, z for the entity
	 */
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		SoundType stepsound = par4.stepSound;

		if (this.worldObj.getBlock(par1, par2 + 1, par3) == Blocks.snow_layer)
			stepsound = Blocks.snow_layer.stepSound;

		if (!par4.getMaterial().isLiquid())
		{
			int i1 = this.getHorseType();

			if (this.riddenByEntity != null && i1 != 1 && i1 != 2)
			{
				++this.field_110285_bP;
				if (this.field_110285_bP > 5 && this.field_110285_bP % 3 == 0)
				{
					this.playSound("mob.horse.gallop", stepsound.getVolume() * 0.15F, stepsound.getPitch());
					if (i1 == 0 && this.rand.nextInt(10) == 0)
						this.playSound("mob.horse.breathe", stepsound.getVolume() * 0.6F, stepsound.getPitch());
				}
				else if (this.field_110285_bP <= 5)
				{
					this.playSound("mob.horse.wood", stepsound.getVolume() * 0.15F, stepsound.getPitch());
				}
			}
			else if (stepsound == Block.soundTypeWood)
			{
				this.playSound("mob.horse.soft", stepsound.getVolume() * 0.15F, stepsound.getPitch());
			}
			else
			{
				this.playSound("mob.horse.wood", stepsound.getVolume() * 0.15F, stepsound.getPitch());
			}
		}
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(horseJumpStrength);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250);//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D);
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	public int getMaxSpawnedInChunk()
	{
		return 6;
	}

	public int getMaxTemper()
	{
		return 100;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume()
	{
		return 0.8F;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	public int getTalkInterval()
	{
		return 400;
	}

	@SideOnly(Side.CLIENT)
	public boolean func_110239_cn()
	{
		return this.getHorseType() == 0 || this.func_110241_cb() > 0;
	}

	private void func_110230_cF()
	{
		this.field_110286_bQ = null;
	}

	@SideOnly(Side.CLIENT)
	private void setHorseTexturePaths()
	{
		this.field_110286_bQ = "horse/";
		this.field_110280_bR[0] = null;
		this.field_110280_bR[1] = null;
		this.field_110280_bR[2] = null;
		int i = this.getHorseType();
		int j = this.getHorseVariant();
		int k;

		if (i == 0)
		{
			k = j & 255;
			int l = (j & 65280) >> 8;
			this.field_110280_bR[0] = horseTextures[k];
			this.field_110286_bQ = this.field_110286_bQ + field_110269_bA[k];
			this.field_110280_bR[1] = horseMarkingTextures[l];
			this.field_110286_bQ = this.field_110286_bQ + field_110292_bC[l];
		}
		else
		{
			this.field_110280_bR[0] = "";
			this.field_110286_bQ = this.field_110286_bQ + "_" + i + "_";
		}

		k = this.func_110241_cb();
		this.field_110280_bR[2] = horseArmorTextures[k];
		this.field_110286_bQ = this.field_110286_bQ + field_110273_bx[k];
	}

	@SideOnly(Side.CLIENT)
	public String getHorseTexture()
	{
		if (this.field_110286_bQ == null)
			this.setHorseTexturePaths();
		return this.field_110286_bQ;
	}

	@SideOnly(Side.CLIENT)
	public String[] getVariantTexturePaths()
	{
		if (this.field_110286_bQ == null)
			this.setHorseTexturePaths();
		return this.field_110280_bR;
	}

	public void openGUI(EntityPlayer par1EntityPlayer)
	{
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer) && this.isTame())
		{
			this.horseChest.func_110133_a(this.getEntityName());
			//par1EntityPlayer.displayGUIHorse(this, this.horseChest);
			par1EntityPlayer.openGui(TerraFirmaCraft.instance, 42, par1EntityPlayer.worldObj, (int)par1EntityPlayer.posX, (int)par1EntityPlayer.posY, (int)par1EntityPlayer.posZ);
		}
	}

	public AnimalChest getHorseChest()
	{
		return this.horseChest;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		if(!worldObj.isRemote)
		{
			par1EntityPlayer.addChatMessage(new ChatComponentText(getGender()==GenderEnum.FEMALE?"Female":"Male"));
			if(getGender()==GenderEnum.FEMALE && pregnant)
				par1EntityPlayer.addChatMessage(new ChatComponentText("Pregnant"));
			//player.addChatMessage("12: "+dataWatcher.getWatchableObjectInt(12)+", 15: "+dataWatcher.getWatchableObjectInt(15));
		}

		if (itemstack != null && this.isBreedingItem(itemstack) && this.getGrowingAge() == 0 && this.inLove <= 0)
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0)
					par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
			}

			this.func_146082_f(par1EntityPlayer);
			return true;
		}
		else
		{
			//return super.interact(par1EntityPlayer);
		}

		if (itemstack != null && itemstack.getItem() == Items.spawn_egg)
		{
			return super.interact(par1EntityPlayer);
		}
		else if (!this.isTame() && this.func_110256_cu())
		{
			return false;
		}
		else if (this.isTame() && this.isAdultHorse() && par1EntityPlayer.isSneaking() && !this.getLeashed())
		{
			this.openGUI(par1EntityPlayer);
			return true;
		}
		else if (this.func_110253_bW() && this.riddenByEntity != null)
		{
			return super.interact(par1EntityPlayer);
		}
		else
		{
			if (itemstack != null)
			{
				boolean flag = false;

				if (this.func_110259_cr())
				{
					byte b0 = -1;

					if (itemstack.getItem() == Items.iron_horse_armor)
					{
						b0 = 1;
					}
					else if (itemstack.getItem() == Items.golden_horse_armor)
					{
						b0 = 2;
					}
					else if (itemstack.getItem() == Items.diamond_horse_armor)
					{
						b0 = 3;
					}

					if (b0 >= 0)
					{
						if (!this.isTame())
						{
							this.makeHorseRearWithSound();
							return true;
						}

						this.openGUI(par1EntityPlayer);
						return true;
					}
				}

				if (!this.isTame() && !flag)
				{
					if (itemstack != null && itemstack.interactWithEntity(par1EntityPlayer, this))
					{
						return true;
					}

					this.makeHorseRearWithSound();
					return true;
				}

				if (!flag && this.func_110229_cs() && !this.isChested() && itemstack.getItem() == Item.getItemFromBlock(TFCBlocks.Chest))
				{
					this.setChested(true);
					this.playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					flag = true;
					this.func_110226_cD();
				}

				if (!flag && this.func_110253_bW() && !this.isHorseSaddled() && itemstack.getItem() == Items.saddle)
				{
					this.openGUI(par1EntityPlayer);
					return true;
				}

				if (flag)
				{
					if (!par1EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize == 0)
					{
						par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
					}

					return true;
				}
			}
			if (this.func_110253_bW() && this.riddenByEntity == null)
			{
				if (itemstack != null && itemstack.interactWithEntity(par1EntityPlayer, this))
				{
					return true;
				}
				else
				{

					if(this.getLeashedToEntity() != null && this.getLeashedToEntity() instanceof EntityPlayer &&
							this.getLeashedToEntity() == par1EntityPlayer)
					{
						this.func_110237_h(par1EntityPlayer);
					}
					return true;
				}
			}
			else
			{
				return super.interact(par1EntityPlayer);
			}
		}
	}

	//We use this to catch the EntityLiving check, so that other interactions can be performed on leashed animals
	@Override
	public boolean getLeashed(){
		if(super.getLeashed() && getLeashedToEntity() instanceof EntityPlayer && 
				((EntityPlayer)getLeashedToEntity()).inventory.getCurrentItem() == null && func_110174_bM() != -1)
		{
			return false;
		}
		return super.getLeashed();
	}

	@Override
	public void clearLeashed(boolean par1, boolean par2)
	{
		Entity entity = getLeashedToEntity();
		if(entity!= null && entity instanceof EntityPlayer)
		{
			ItemStack item = ((EntityPlayer)entity).inventory.getCurrentItem();
			if(entity.isSneaking())
				super.clearLeashed(par1, par2);
		}
		else
		{
			super.clearLeashed(par1, par2);
		}
	}

	private void func_110237_h(EntityPlayer par1EntityPlayer)
	{
		par1EntityPlayer.rotationYaw = this.rotationYaw;
		par1EntityPlayer.rotationPitch = this.rotationPitch;
		this.setEatingHaystack(false);
		this.setRearing(false);

		if (!this.worldObj.isRemote)
			par1EntityPlayer.mountEntity(this);
	}

	public boolean func_110259_cr()
	{
		return this.getHorseType() == 0;
	}

	public boolean func_110229_cs()
	{
		int i = this.getHorseType();
		return i == 2 || i == 1;
	}

	/**
	 * Dead and sleeping entities cannot move
	 */
	protected boolean isMovementBlocked()
	{
		return this.riddenByEntity != null && this.isHorseSaddled() ? true : this.isEatingHaystack() || this.isRearing();
	}

	public boolean func_110256_cu()
	{
		int i = this.getHorseType();
		return i == 3 || i == 4;
	}

	public boolean func_110222_cv()
	{
		return this.func_110256_cu() || this.getHorseType() == 2;
	}

	private void func_110210_cH()
	{
		this.field_110278_bp = 1;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);
		if (!this.worldObj.isRemote)
			this.dropChestItems();
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		this.entityDropItem(new ItemStack(TFCItems.Hide, 1, Math.max(0, Math.min(2, (int)(ageMod * 3 - 1)))), 0);
		this.dropItem(Items.bone, (int) ((rand.nextInt(8) + 3) * ageMod));

		float foodWeight = ageMod*(this.size_mod * 4000);//528 oz (33lbs) is the average yield of lamb after slaughter and processing

		TFC_Core.animalDropMeat(this, TFCItems.horseMeatRaw, foodWeight);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();

		if (this.worldObj.isRemote && this.dataWatcher.hasChanges())
		{
			this.dataWatcher.func_111144_e();
			this.func_110230_cF();
		}

		if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30)
		{
			this.openMouthCounter = 0;
			this.setHorseWatchableBoolean(128, false);
		}

		if (!this.worldObj.isRemote && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20)
		{
			this.jumpRearingCounter = 0;
			this.setRearing(false);
		}

		if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8)
		{
			this.field_110278_bp = 0;
		}

		if (this.field_110279_bq > 0)
		{
			++this.field_110279_bq;

			if (this.field_110279_bq > 300)
			{
				this.field_110279_bq = 0;
			}
		}

		if (this.inLove > 0)
		{
			--this.inLove;
			String s = "heart";

			if (this.inLove % 10 == 0)
			{
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				double d2 = this.rand.nextGaussian() * 0.02D;
				this.worldObj.spawnParticle(s, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
			}
		}
		else
		{
			this.breeding = 0;
		}

		this.prevHeadLean = this.headLean;

		if (this.isEatingHaystack())
		{
			this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;

			if (this.headLean > 1.0F)
			{
				this.headLean = 1.0F;
			}
		}
		else
		{
			this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;

			if (this.headLean < 0.0F)
			{
				this.headLean = 0.0F;
			}
		}

		this.prevRearingAmount = this.rearingAmount;

		if (this.isRearing())
		{
			this.prevHeadLean = this.headLean = 0.0F;
			this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;

			if (this.rearingAmount > 1.0F)
			{
				this.rearingAmount = 1.0F;
			}
		}
		else
		{
			this.field_110294_bI = false;
			this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;

			if (this.rearingAmount < 0.0F)
			{
				this.rearingAmount = 0.0F;
			}
		}

		this.prevMouthOpenness = this.mouthOpenness;

		if (this.getHorseWatchableBoolean(128))
		{
			this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;

			if (this.mouthOpenness > 1.0F)
			{
				this.mouthOpenness = 1.0F;
			}
		}
		else
		{
			this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;

			if (this.mouthOpenness < 0.0F)
			{
				this.mouthOpenness = 0.0F;
			}
		}
	}

	private void openHorseMouth()
	{
		if (!this.worldObj.isRemote)
		{
			this.openMouthCounter = 1;
			this.setHorseWatchableBoolean(128, true);
		}
	}

	private boolean func_110200_cJ()
	{
		return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && !this.func_110222_cv() && this.getHealth() >= this.getMaxHealth();
	}

	public void setEating(boolean par1)
	{
		this.setHorseWatchableBoolean(32, par1);
	}

	public void setEatingHaystack(boolean par1)
	{
		this.setEating(par1);
	}

	public void setRearing(boolean par1)
	{
		if (par1)
		{
			this.setEatingHaystack(false);
		}

		this.setHorseWatchableBoolean(64, par1);
	}

	private void makeHorseRear()
	{
		if (!this.worldObj.isRemote)
		{
			this.jumpRearingCounter = 1;
			this.setRearing(true);
		}
	}

	public void makeHorseRearWithSound()
	{
		this.makeHorseRear();
		String s = this.getAngrySoundName();

		if (s != null)
		{
			this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
		}
	}

	public void dropChestItems()
	{
		this.dropItemsInChest(this, this.horseChest);
		this.dropChests();
	}

	private void dropItemsInChest(Entity par1Entity, AnimalChest par2AnimalChest)
	{
		if (par2AnimalChest != null && !this.worldObj.isRemote)
		{
			for (int i = 0; i < par2AnimalChest.getSizeInventory(); ++i)
			{
				ItemStack itemstack = par2AnimalChest.getStackInSlot(i);

				if (itemstack != null)
				{
					this.entityDropItem(itemstack, 0.0F);
				}
			}
		}
	}

	public boolean setTamedBy(EntityPlayer par1EntityPlayer)
	{
		this.setOwnerName(par1EntityPlayer.getCommandSenderName());
		this.setHorseTamed(true);
		return true;
	}

	/**
	 * Moves the entity based on the specified heading.  Args: strafe, forward
	 */
	public void moveEntityWithHeading(float par1, float par2)
	{
		if (this.riddenByEntity != null && this.isHorseSaddled())
		{
			this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
			this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
			par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;

			if (par2 <= 0.0F)
			{
				par2 *= 0.25F;
				this.field_110285_bP = 0;
			}

			if (this.onGround && this.jumpPower == 0.0F && this.isRearing() && !this.field_110294_bI)
			{
				par1 = 0.0F;
				par2 = 0.0F;
			}

			if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround)
			{
				this.motionY = this.getHorseJumpStrength() * (double)this.jumpPower;

				if (this.isPotionActive(Potion.jump))
				{
					this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
				}

				this.setHorseJumping(true);
				this.isAirBorne = true;

				if (par2 > 0.0F)
				{
					float f2 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
					float f3 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
					this.motionX += (double)(-0.4F * f2 * this.jumpPower);
					this.motionZ += (double)(0.4F * f3 * this.jumpPower);
					this.playSound("mob.horse.jump", 0.4F, 1.0F);
				}

				this.jumpPower = 0.0F;
			}

			this.stepHeight = 1.0F;
			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

			if (!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(par1, par2);
			}

			if (this.onGround)
			{
				this.jumpPower = 0.0F;
				this.setHorseJumping(false);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d0 = this.posX - this.prevPosX;
			double d1 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

			if (f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(par1, par2);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("EatingHaystack", this.isEatingHaystack());
		par1NBTTagCompound.setBoolean("ChestedHorse", this.isChested());
		par1NBTTagCompound.setBoolean("HasReproduced", this.getHasReproduced());
		par1NBTTagCompound.setBoolean("Bred", this.func_110205_ce());
		par1NBTTagCompound.setInteger("Type", this.getHorseType());
		par1NBTTagCompound.setInteger("Variant", this.getHorseVariant());
		par1NBTTagCompound.setInteger("Temper", this.getTemper());
		par1NBTTagCompound.setBoolean("Tame", this.isTame());
		par1NBTTagCompound.setString("OwnerName", this.getOwnerName());
		par1NBTTagCompound.setInteger ("Sex", sex);
		par1NBTTagCompound.setLong ("Animal ID", animalID);
		par1NBTTagCompound.setFloat ("Size Modifier", size_mod);


		NBTTagCompound nbt = par1NBTTagCompound;
		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		nbt.setFloat ("Colour Modifier", colour_mod);
		nbt.setFloat ("Climate Adaptation Modifier", climate_mod);
		nbt.setFloat ("Hardiness Modifier", hard_mod);

		par1NBTTagCompound.setInteger ("Hunger", hunger);
		par1NBTTagCompound.setBoolean("Pregnant", pregnant);
		par1NBTTagCompound.setFloat("MateSize", mateSizeMod);
		par1NBTTagCompound.setLong("ConceptionTime",conception);
		par1NBTTagCompound.setInteger("Age", getBirthDay());

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

			par1NBTTagCompound.setTag("Items", nbttaglist);
		}

		if (this.horseChest.getStackInSlot(1) != null)
		{
			par1NBTTagCompound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
		}

		if (this.horseChest.getStackInSlot(0) != null)
		{
			par1NBTTagCompound.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setEatingHaystack(par1NBTTagCompound.getBoolean("EatingHaystack"));
		this.func_110242_l(par1NBTTagCompound.getBoolean("Bred"));
		this.setChested(par1NBTTagCompound.getBoolean("ChestedHorse"));
		this.setHasReproduced(par1NBTTagCompound.getBoolean("HasReproduced"));
		this.setHorseType(par1NBTTagCompound.getInteger("Type"));
		this.setHorseVariant(par1NBTTagCompound.getInteger("Variant"));
		this.setTemper(par1NBTTagCompound.getInteger("Temper"));
		this.setHorseTamed(par1NBTTagCompound.getBoolean("Tame"));
		NBTTagCompound nbt = par1NBTTagCompound;
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
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		conception = nbt.getLong("ConceptionTime");
		this.setAge(nbt.getInteger ("Age"));

		if (par1NBTTagCompound.hasKey("OwnerName"))
		{
			this.setOwnerName(par1NBTTagCompound.getString("OwnerName"));
		}

		IAttributeInstance attributeinstance = this.getAttributeMap().getAttributeInstanceByName("Speed");

		if (attributeinstance != null)
		{
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(attributeinstance.getBaseValue() * 0.25D);
		}

		if (this.isChested())
		{
			NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
			this.func_110226_cD();

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound1.getByte("Slot") & 255;

				if (j >= 2 && j < this.horseChest.getSizeInventory())
				{
					this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}

		ItemStack itemstack;

		if (par1NBTTagCompound.hasKey("ArmorItem", 10))
		{
			itemstack = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("ArmorItem"));

			if (itemstack != null && func_146085_a(itemstack.getItem()))
			{
				this.horseChest.setInventorySlotContents(1, itemstack);
			}
		}

		if (par1NBTTagCompound.hasKey("SaddleItem", 10))
		{
			itemstack = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("SaddleItem"));

			if (itemstack != null && itemstack.getItem() == Items.saddle)
			{
				this.horseChest.setInventorySlotContents(0, itemstack);
			}
		}
		else if (par1NBTTagCompound.getBoolean("Saddle"))
		{
			this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
		}

		this.func_110232_cE();
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		else if (par1EntityAnimal.getClass() != this.getClass())
		{
			return false;
		}
		else
		{
			EntityHorseTFC entityhorse = (EntityHorseTFC)par1EntityAnimal;

			if (this.func_110200_cJ() && entityhorse.func_110200_cJ())
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

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
		boolean flag = false;
		int i = 0;
		int j;

		if (par1EntityLivingData1 instanceof EntityHorseGroupData)
		{
			j = ((EntityHorseGroupData)par1EntityLivingData1).field_111107_a;
			i = ((EntityHorseGroupData)par1EntityLivingData1).field_111106_b & 255 | this.rand.nextInt(5) << 8;
		}
		else
		{
			if (this.rand.nextInt(10) == 0)
			{
				j = 1;
			}
			else
			{
				int k = this.rand.nextInt(7);
				int l = this.rand.nextInt(5);
				j = 0;
				i = k | l << 8;
			}

			par1EntityLivingData1 = new EntityHorseGroupData(j, i);
		}

		this.setHorseType(j);
		this.setHorseVariant(i);

		if (this.rand.nextInt(5) == 0)
		{
			this.setGrowingAge(-24000);
		}

		if (j != 4 && j != 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)this.func_110267_cL());

			if (j == 0)
			{
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.func_110203_cN());
			}
			else
			{
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776D);
			}
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
		}

		if (j != 2 && j != 1)
		{
			this.getEntityAttribute(horseJumpStrength).setBaseValue(this.func_110245_cM());
		}
		else
		{
			this.getEntityAttribute(horseJumpStrength).setBaseValue(0.5D);
		}

		this.setHealth(this.getMaxHealth());
		return (IEntityLivingData)par1EntityLivingData1;
	}

	@SideOnly(Side.CLIENT)
	public float getGrassEatingAmount(float par1)
	{
		return this.prevHeadLean + (this.headLean - this.prevHeadLean) * par1;
	}

	@SideOnly(Side.CLIENT)
	public float getRearingAmount(float par1)
	{
		return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * par1;
	}

	@SideOnly(Side.CLIENT)
	public float func_110201_q(float par1)
	{
		return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * par1;
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	protected boolean isAIEnabled()
	{
		return true;
	}

	public void setJumpPower(int par1)
	{
		if (this.isHorseSaddled())
		{
			if (par1 < 0)
			{
				par1 = 0;
			}
			else
			{
				this.field_110294_bI = true;
				this.makeHorseRear();
			}

			if (par1 >= 90)
			{
				this.jumpPower = 1.0F;
			}
			else
			{
				this.jumpPower = 0.4F + 0.4F * (float)par1 / 90.0F;
			}
		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * "Spawns particles for the horse entity. par1 tells whether to spawn hearts. If it is false, it spawns smoke."
	 */
	protected void spawnHorseParticles(boolean par1)
	{
		String s = par1 ? "heart" : "smoke";

		for (int i = 0; i < 7; ++i)
		{
			double d0 = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.worldObj.spawnParticle(s, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 7)
		{
			this.spawnHorseParticles(true);
		}
		else if (par1 == 6)
		{
			this.spawnHorseParticles(false);
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}

	public void updateRiderPosition()
	{
		super.updateRiderPosition();

		if (this.prevRearingAmount > 0.0F)
		{
			float f = MathHelper.sin(this.renderYawOffset * (float)Math.PI / 180.0F);
			float f1 = MathHelper.cos(this.renderYawOffset * (float)Math.PI / 180.0F);
			float f2 = 0.7F * this.prevRearingAmount;
			float f3 = 0.15F * this.prevRearingAmount;
			this.riddenByEntity.setPosition(this.posX + (double)(f2 * f), this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (double)f3, this.posZ - (double)(f2 * f1));

			if (this.riddenByEntity instanceof EntityLivingBase)
			{
				((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
			}
		}
	}

	private float func_110267_cL()
	{
		return 1000 + (float)this.rand.nextInt(101) + (float)this.rand.nextInt(151);
	}

	private double func_110245_cM()
	{
		return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
	}

	private double func_110203_cN()
	{
		return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
	}

	public static boolean func_146085_a(Item par0)
	{
		return par0 == Items.iron_horse_armor || par0 == Items.golden_horse_armor || par0 == Items.diamond_horse_armor;
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	public boolean canMateWith(IAnimal animal) 
	{
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityHorseTFC) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mate(IAnimal otherAnimal) 
	{
		if (getGender() == GenderEnum.MALE)
		{
			otherAnimal.mate(this);
			return;
		}
		conception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSize();
	}

	@Override
	public boolean getInLove()
	{
		return isInLove;
	}

	@Override
	public void setInLove(boolean b) 
	{
		this.isInLove = b;
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

	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return !pregnant&&(par1ItemStack.getItem() == TFCItems.WheatGrain ||par1ItemStack.getItem() == TFCItems.OatGrain||par1ItemStack.getItem() == TFCItems.RiceGrain||
				par1ItemStack.getItem() == TFCItems.BarleyGrain||par1ItemStack.getItem() == TFCItems.RyeGrain);
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.PreventEntityDataUpdate) {
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
		}
	}

	@Override
	public void setAge(int par1)
	{
		//if(!TFC_Core.PreventEntityDataUpdate) {
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
		//}
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}

	@Override
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[getSex()];
	}

	@Override
	public int getSex(){
		return dataWatcher.getWatchableObjectInt(13);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return null;
	}


	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable) 
	{
		EntityHorseTFC entityhorse = (EntityHorseTFC)entityageable;
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(entityageable.getEntityData().getFloat("MateSize"));
		EntityHorseTFC entityhorse1 = new EntityHorseTFC(worldObj, this, data);
		int i = this.getHorseType();
		int j = entityhorse.getHorseType();
		int k = 0;

		if (i == j)
		{
			k = i;
		}
		else if (i == 0 && j == 1 || i == 1 && j == 0)
		{
			k = 2;
		}

		if (k == 0)
		{
			int l = this.rand.nextInt(9);
			int i1;

			if (l < 4)
			{
				i1 = this.getHorseVariant() & 255;
			}
			else if (l < 8)
			{
				i1 = entityhorse.getHorseVariant() & 255;
			}
			else
			{
				i1 = this.rand.nextInt(7);
			}

			int j1 = this.rand.nextInt(5);

			if (j1 < 4)
			{
				i1 |= this.getHorseVariant() & 65280;
			}
			else if (j1 < 8)
			{
				i1 |= entityhorse.getHorseVariant() & 65280;
			}
			else
			{
				i1 |= this.rand.nextInt(5) << 8 & 65280;
			}

			entityhorse1.setHorseVariant(i1);
		}

		entityhorse1.setHorseType(k);
		double d0 = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + entityageable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + (double)this.func_110267_cL();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(d0 / 3.0D);
		double d1 = this.getEntityAttribute(horseJumpStrength).getBaseValue() + entityageable.getEntityAttribute(horseJumpStrength).getBaseValue() + this.func_110245_cM();
		entityhorse1.getEntityAttribute(horseJumpStrength).setBaseValue(d1 / 3.0D);
		double d2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + entityageable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.func_110203_cN();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(d2 / 3.0D);
		return entityhorse1;
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
	public EntityLiving getEntity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public float getStrength() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(24);
	}


	@Override
	public float getAggression() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(25);
	}


	@Override
	public float getObedience() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(26);
	}


	@Override
	public float getColour() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(27);
	}


	@Override
	public float getClimateAdaptation() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(28);
	}


	@Override
	public float getHardiness() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(29);
	}

	@Override
	public Vec3 getAttackedVec() {
		return this.attackedVec;
	}

	@Override
	public void setAttackedVec(Vec3 attackedVec) {
		this.attackedVec = attackedVec;
	}

	@Override
	public Entity getFearSource() {
		return this.fearSource;
	}

	@Override
	public void setFearSource(Entity fearSource) {
		this.fearSource = fearSource;
	}
}
