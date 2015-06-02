package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
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
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Util.Helper;

public class EntityHorseTFC extends EntityHorse implements IInvBasic, IAnimal
{
	private static final IEntitySelector horseBreedingSelector = new EntityHorseBredSelector();
	private static final IAttribute horseJumpStrength = (new RangedAttribute("horse.jumpStrengthTFC", 0.7D, 0.0D, 2.0D)).setDescription("Jump StrengthTFC").setShouldWatch(true);

	public int inLove;

	private final AIEatGrass aiEatGrass = new AIEatGrass(this);
	protected long animalID;
	protected int sex = 0;
	protected int hunger = 0;
	protected boolean pregnant;
	protected int pregnancyRequiredTime;
	protected long conception;
	public float size_mod;			//How large the animal is
	public float strength_mod;		//how strong the animal is
	public float aggression_mod = 1;//How aggressive / obstinate the animal is
	public float obedience_mod = 1;	//How well the animal responds to commands.
	public float colour_mod = 1;	//what the animal looks like
	public float climate_mod = 1;	//climate adaptability
	public float hard_mod = 1;		//hardiness

	protected float mateSizeMod = 0;
	protected float mateStrengthMod = 0;
	protected float mateAggroMod = 0;
	protected float mateObedMod = 0;
	protected float mateColMod = 0;
	protected float mateClimMod = 0;
	protected float mateHardMod = 0;
	protected int mateType = 0;
	protected int mateVariant = 0;
	protected double mateMaxHealth = this.calcMaxHealth();
	protected double mateJumpStrength = this.calcJumpStrength();
	protected double mateMoveSpeed = this.calcMoveSpeed();
	public boolean isInLove;
	public Vec3 attackedVec = null;
	public Entity fearSource = null;

	int degreeOfDiversion = 2;

	private String field_110286_bQ;
	private String[] field_110280_bR = new String[3];

	private int familiarity = 0;
	private long lastFamiliarityUpdate = 0;
	private boolean familiarizedToday = false;

	protected float avgAdultWeight = 550;			//The average weight of adult males in kg
	protected float dimorphism = 0.0813f;		//1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	//the square root of the ratio of female mass : male mass

	public EntityHorseTFC(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime =(int)(11.17 * TFC_Time.ticksInMonth);
		conception = 0;
		sex = rand.nextInt(2);
		size_mod =(float)Math.sqrt((((rand.nextInt (rand.nextInt((degreeOfDiversion + 1)*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1F) * (1.0F - dimorphism * sex));
		strength_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + size_mod));
		aggression_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1));
		obedience_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + (1f/aggression_mod)));
		colour_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt((degreeOfDiversion+2)*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1));
		hard_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + size_mod));
		climate_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + hard_mod));
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
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RyeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RiceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.BarleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.OatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.MaizeEar, false));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(1, new EntityAIPanicTFC(this, 1.2D,true));
		this.updateChestSaddle();

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		//this.setGrowingAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntityHorseTFC(World par1World, IAnimal mother, ArrayList<Float> data, int type, int variant)
	{
		this(par1World);
		float father_size = 1;
		float father_str = 1;
		float father_aggro = 1;
		float father_obed = 1;
		float father_col = 1;
		float father_clim = 1;
		float father_hard = 1;
		for(int i = 0; i < data.size(); i++){
			switch(i){
			case 0:father_size = data.get(i);break;
			case 1:father_str = data.get(i);break;
			case 2:father_aggro = data.get(i);break;
			case 3:father_obed = data.get(i);break;
			case 4:father_col = data.get(i);break;
			case 5:father_clim = data.get(i);break;
			case 6:father_hard = data.get(i);break;
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
		colour_mod = (float)Math.sqrt(colour_mod * colour_mod * (float)Math.sqrt((mother.getColour() + father_col) * 0.5F));
		hard_mod = (float)Math.sqrt(hard_mod * hard_mod * (float)Math.sqrt((mother.getHardiness() + father_hard) * 0.5F));
		climate_mod = (float)Math.sqrt(climate_mod * climate_mod * (float)Math.sqrt((mother.getClimateAdaptation() + father_clim) * 0.5F));

		this.familiarity = (int) (mother.getFamiliarity()<90?mother.getFamiliarity()/2:mother.getFamiliarity()*0.9f);
		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge(TFC_Time.getTotalDays());
		this.setHorseType(type);
		this.setHorseVariant(variant);
	}

	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (hunger > 168000)
			hunger = 168000;
		if (hunger > 0)
			hunger--;

		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && rand.nextInt(600) == 0 && !familiarizedToday){
			this.familiarize(((EntityPlayer)this.riddenByEntity));
		}

		syncData();
		if(isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		this.handleFamiliarityUpdate();
		if(isPregnant())
		{
			if(TFC_Time.getTotalTicks() >= conception + pregnancyRequiredTime)
			{
				EntityHorseTFC baby = (EntityHorseTFC) createChildTFC(this);
				baby.setLocationAndAngles (posX + (rand.nextFloat() - 0.5F) * 2F, posY, posZ + (rand.nextFloat() - 0.5F) * 2F, 0.0F, 0.0F);
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
		TFC_Core.PreventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead){

			this.heal(1);
		}
		else if(hunger < 144000 && super.isInLove()){
			this.setInLove(false);
		}
	}

	@Override
	public int increaseTemper(int p_110198_1_)
	{
		p_110198_1_*=5; //This is because we want obedience_mod and aggression_mod to have an effect
		int j = MathHelper.clamp_int(this.getTemper() + (int)(p_110198_1_ * obedience_mod * (1f/aggression_mod)), 0, this.getMaxTemper());
		this.setTemper(j);
		return j;
	}

	@Override
	public int getMaxTemper()
	{
		return (int)(500 * aggression_mod);
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
						TFC_Core.getByteFromSmallFloat(colour_mod),
						TFC_Core.getByteFromSmallFloat(climate_mod),
						TFC_Core.getByteFromSmallFloat(hard_mod),
						(byte)familiarity
				};
				ByteBuffer buf = ByteBuffer.wrap(values);
				this.dataWatcher.updateObject(24, buf.getInt());
				this.dataWatcher.updateObject(23, buf.getInt());
			}
			else
			{
				sex = this.dataWatcher.getWatchableObjectInt(13);

				ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
				buf.putInt(this.dataWatcher.getWatchableObjectInt(24));
				buf.putInt(this.dataWatcher.getWatchableObjectInt(23));
				byte[] values = buf.array();

				size_mod = TFC_Core.getSmallFloatFromByte(values[0]);
				strength_mod = TFC_Core.getSmallFloatFromByte(values[1]);
				aggression_mod = TFC_Core.getSmallFloatFromByte(values[2]);
				obedience_mod = TFC_Core.getSmallFloatFromByte(values[3]);
				colour_mod = TFC_Core.getSmallFloatFromByte(values[4]);
				climate_mod = TFC_Core.getSmallFloatFromByte(values[5]);
				hard_mod = TFC_Core.getSmallFloatFromByte(values[6]);

				familiarity = values[7];
			}
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();	
		this.dataWatcher.addObject(13, new Integer(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age

		this.dataWatcher.addObject(24, Integer.valueOf(0));	//Size, strength, aggression, obedience. EntityHorse uses 22
		this.dataWatcher.addObject(23, Integer.valueOf(0));	//Colour, climate, hardiness, familiarity
	}

	private int getNumChestSlots()
	{
		int i = this.getHorseType();
		return this.isChested() && (i == 1 || i == 2) ? 17 : 2; // 2 (Armor & Saddle) + 15 Donkey Chest Slots
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

	@Override
	protected EntityHorseTFC getClosestHorse(Entity entity, double range)
	{
		double maxDistance = Double.MAX_VALUE;
		EntityHorseTFC closestHorse = null;
		List<EntityHorseTFC> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(range, range, range), horseBreedingSelector);
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
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(horseJumpStrength);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250 * size_mod * strength_mod * hard_mod);//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D * strength_mod * obedience_mod * hard_mod / (size_mod * size_mod));
		this.setHealth(this.getMaxHealth());
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

	public AnimalChest getHorseChest()
	{
		return this.horseChest;
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
			if (player.isSneaking() && !familiarizedToday && itemstack != null)
			{
				this.familiarize(player);
				return true;
			}
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation(getGender() == GenderEnum.FEMALE ? "entity.female" : "entity.male"));
			if (getGender() == GenderEnum.FEMALE && pregnant)
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));

		}

		if (itemstack != null && this.isBreedingItemTFC(itemstack) && checkFamiliarity(InteractionEnum.BREED,player) &&this.familiarizedToday && this.getGrowingAge() == 0 && !super.isInLove())
		{
			if (!player.capabilities.isCreativeMode)
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (((ItemFoodTFC)itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this)));
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
		else if (!this.isTame() && this.func_110256_cu()) // Zombie or Skeleton Horse
		{
			return false;
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
				boolean flag = false;

				if (!this.isTame())
				{
					if (itemstack != null && itemstack.interactWithEntity(player, this))
					{
						return true;
					}

					this.makeHorseRearWithSound();
				}

				if (this.func_110229_cs()/*Donkey or Mule*/&& !this.isChested() && itemstack.getItem() == Item.getItemFromBlock(TFCBlocks.Chest))
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
	public void clearLeashed(boolean par1, boolean par2)
	{
		Entity entity = getLeashedToEntity();
		if(entity!= null && entity instanceof EntityPlayer)
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
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		this.entityDropItem(new ItemStack(TFCItems.Hide, 1, Math.max(0, Math.min(2, (int)(ageMod * 3 - 1)))), 0);
		this.dropItem(Items.bone, (int) ((rand.nextInt(8) + 3) * ageMod));
		if(this.getLeashed()){
			this.entityDropItem(new ItemStack(TFCItems.Rope), 0);
		}

		float foodWeight = ageMod * (this.size_mod * 4000);

		TFC_Core.animalDropMeat(this, TFCItems.horseMeatRaw, foodWeight);
	}

	@Override
	public void dropChests()
	{
		if (!this.worldObj.isRemote && this.isChested())
		{
			this.setChested(false);
		}
	}

	private boolean getBreedable()
	{
		return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && 
				!this.func_110222_cv()/*Not a Mule, Zombie or Skeleton*/&& this.getHealth() >= this.getMaxHealth();
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
		nbttc.setFloat ("Size Modifier", size_mod);

		nbttc.setInteger("Familiarity", familiarity);
		nbttc.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbttc.setBoolean("Familiarized Today", familiarizedToday);

		NBTTagCompound nbt = nbttc;
		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		nbt.setFloat ("Colour Modifier", colour_mod);
		nbt.setFloat ("Climate Adaptation Modifier", climate_mod);
		nbt.setFloat ("Hardiness Modifier", hard_mod);

		nbttc.setInteger ("Hunger", hunger);
		nbttc.setBoolean("Pregnant", pregnant);
		nbttc.setFloat("MateSize", mateSizeMod);
		nbttc.setFloat("MateStrength", mateStrengthMod);
		nbttc.setFloat("MateAggro", mateAggroMod);
		nbttc.setFloat("MateObed", mateObedMod);
		nbttc.setFloat("MateCol", mateColMod);
		nbttc.setFloat("MateClim", mateClimMod);
		nbttc.setFloat("MateHard", mateHardMod);
		nbttc.setInteger("MateType", mateType);
		nbttc.setInteger("MateVariant", mateVariant);
		nbttc.setDouble("MateHealth", mateMaxHealth);
		nbttc.setDouble("MateJump", mateJumpStrength);
		nbttc.setDouble("MateSpeed", mateMoveSpeed);
		nbttc.setLong("ConceptionTime",conception);
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
		size_mod = nbt.getFloat ("Size Modifier");

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");

		strength_mod = nbt.getFloat ("Strength Modifier");
		aggression_mod = nbt.getFloat ("Aggression Modifier");
		obedience_mod = nbt.getFloat ("Obedience Modifier");
		colour_mod = nbt.getFloat ("Colour Modifier");
		climate_mod = nbt.getFloat ("Climate Adaptation Modifier");
		hard_mod = nbt.getFloat ("Hardiness Modifier");

		hunger = nbt.getInteger ("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		mateStrengthMod = nbt.getFloat("MateStrength");
		mateAggroMod = nbt.getFloat("MateAggro");
		mateObedMod = nbt.getFloat("MateObed");
		mateColMod = nbt.getFloat("MateCol");
		mateClimMod = nbt.getFloat("MateClim");
		mateHardMod = nbt.getFloat("MateHard");
		mateType = nbt.getInteger("MateType");
		mateVariant = nbt.getInteger("MateVariant");
		mateMaxHealth = nbt.getDouble("MateHealth");
		mateJumpStrength = nbt.getDouble("MateJump");
		mateMoveSpeed = nbt.getDouble("MateSpeed");
		conception = nbt.getLong("ConceptionTime");
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

			if (itemstack != null && func_146085_a/*isHorseArmor*/(itemstack.getItem()))
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

	private float calcMaxHealth()
	{
		return 1000 + (float)this.rand.nextInt(101) + this.rand.nextInt(151);
	}

	private double calcJumpStrength()
	{
		return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
	}

	private double calcMoveSpeed()
	{
		return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityHorseTFC && this.isAdult())
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
			setInLove(false);
			resetInLove();
			return;
		}
		conception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		setInLove(false);
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSize();
		mateStrengthMod = otherAnimal.getStrength();
		mateAggroMod = otherAnimal.getAggression();
		mateObedMod = otherAnimal.getObedience();
		mateColMod = otherAnimal.getColour();
		mateClimMod = otherAnimal.getClimateAdaptation();
		mateHardMod = otherAnimal.getHardiness();
		mateType = ((EntityHorse) otherAnimal).getHorseType();
		mateVariant = ((EntityHorse) otherAnimal).getHorseVariant();
		mateMaxHealth = ((EntityLivingBase) otherAnimal).getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
		mateJumpStrength = ((EntityLivingBase) otherAnimal).getEntityAttribute(horseJumpStrength).getBaseValue();
		mateMoveSpeed = ((EntityLivingBase) otherAnimal).getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
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
		if(b)
		{
			this.entityToAttack = null;
			this.worldObj.setEntityState(this, (byte)18);
		}
	}

	@Override
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("horse");
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
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	public boolean isBreedingItemTFC(ItemStack item)
	{
		return !pregnant && isFood(item);
	}

	@Override
	public boolean isFood(ItemStack item) {
		return item != null && (item.getItem() == TFCItems.WheatGrain ||item.getItem() == TFCItems.OatGrain||item.getItem() == TFCItems.RiceGrain||
				item.getItem() == TFCItems.BarleyGrain||item.getItem() == TFCItems.RyeGrain||item.getItem() == TFCItems.MaizeEar);
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.PreventEntityDataUpdate)
		{
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
	public int getSex()
	{
		return dataWatcher.getWatchableObjectInt(13);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable eAgeable)
	{
		return null;
	}


	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(this.mateSizeMod);
		data.add(this.mateStrengthMod);
		data.add(this.mateAggroMod);
		data.add(this.mateObedMod);
		data.add(this.mateColMod);
		data.add(this.mateClimMod);
		data.add(this.mateHardMod);
		
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
		double jumpSum = this.getEntityAttribute(horseJumpStrength).getBaseValue() + this.mateJumpStrength + this.calcJumpStrength();
		baby.getEntityAttribute(horseJumpStrength).setBaseValue(jumpSum / 3.0D);

		// Average Mom + Dad + Random Move Speed
		double speedSum = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.mateMoveSpeed + this.calcMoveSpeed();
		baby.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speedSum / 3.0D);

		baby.setHealth((float)baby.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
		return baby;
	}

	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return TFC_Time.daysInMonth * 30;
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
	public float getColour()
	{
		return colour_mod;
	}

	@Override
	public float getClimateAdaptation()
	{
		return climate_mod;
	}

	@Override
	public float getHardiness()
	{
		return hard_mod;
	}

	@Override
	public Vec3 getAttackedVec()
	{
		return this.attackedVec;
	}

	@Override
	public void setAttackedVec(Vec3 attackedVec)
	{
		this.attackedVec = attackedVec;
	}

	@Override
	public Entity getFearSource()
	{
		return this.fearSource;
	}

	@Override
	public void setFearSource(Entity fearSource)
	{
		this.fearSource = fearSource;
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
	public int getFamiliarity() {
		// TODO Auto-generated method stub
		return familiarity;
	}

	@Override
	public void handleFamiliarityUpdate() {
		int totalDays = TFC_Time.getTotalDays();
		if(lastFamiliarityUpdate < totalDays){
			if(familiarizedToday && familiarity < 100){
				lastFamiliarityUpdate = totalDays;
				familiarizedToday = false;
				float familiarityChange = (6 * obedience_mod / aggression_mod);
				if (this.isAdult() && familiarity > 35) // Adult caps at 35
				{
					//Nothing
				}
				else if(this.isAdult()){
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
	public void familiarize(EntityPlayer ep) {
		ItemStack stack = ep.getHeldItem();
		if((this.riddenByEntity == null || !this.riddenByEntity.equals(ep)) && !familiarizedToday && stack != null && this.isFood(stack)  && ((isAdult() && familiarity < 50) || !isAdult())){
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
		if(this.riddenByEntity != null && this.riddenByEntity.equals(ep) && isAdult()){
			familiarizedToday = true;
			this.getLookHelper().setLookPositionWithEntity(ep, 0, 0);
			this.playLivingSound();
		}
	}

	@Override
	public boolean trySetName(String name, EntityPlayer player) {
		if (checkFamiliarity(InteractionEnum.NAME, player))
		{
			this.setCustomNameTag(name);
			return true;
		}
		this.playSound(this.getHurtSound(),  6, (rand.nextFloat()/2F)+(isChild()?1.25F:0.75F));
		return false;
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

}
