package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
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
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIMateTFC;
import com.bioxx.tfc.Entities.AI.EntityAISitTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Util.Helper;

public class EntityWolfTFC extends EntityWolf implements IAnimal, IInnateArmor, ICausesDamage
{
	private final float GESTATION_PERIOD = 2.25f;
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
	private int degreeOfDiversion = 1;
	private int familiarity = 0;
	private long lastFamiliarityUpdate = 0;
	private boolean familiarizedToday = false;
	public int happyTicks;
	private boolean wasRoped = false;

	protected float avgAdultWeight = 44f;	//The average weight of adult males in kg
	protected float dimorphism = 0.0786f;	//1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	//the square root of the ratio of female mass : male mass

	public EntityWolfTFC(World par1World)
	{
		super(par1World);
		this.tasks.addTask(6, new EntityAIMateTFC(this, worldObj, 1));
		//this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheepTFC.class, 200, false));
		this.targetTasks.removeTask(this.aiSit);
		this.aiSit = new EntityAISitTFC(this);
		this.tasks.addTask(2, this.aiSit);
		this.targetTasks.addTask(7, new EntityAITargetNonTamed(this, EntityChickenTFC.class, 200, false));
		this.targetTasks.addTask(7, new EntityAITargetNonTamed(this, EntityPheasantTFC.class, 200, false));
		this.targetTasks.addTask(7, new EntityAITargetNonTamed(this, EntityPigTFC.class, 200, false));
		this.targetTasks.addTask(7, new EntityAITargetNonTamed(this, EntityCowTFC.class, 200, false));
		this.targetTasks.addTask(7, new EntityAITargetNonTamed(this, EntityDeer.class, 200, false));
		this.targetTasks.addTask(7, new EntityAITargetNonTamed(this, EntityHorseTFC.class, 200, false));

		hunger = 168000;
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		pregnant = false;
		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);
		timeOfConception = 0;
		mateSizeMod = 1f;
		sex = rand.nextInt(2);
		size_mod =(float)Math.sqrt((((rand.nextInt (rand.nextInt((degreeOfDiversion + 1)*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1F) * (1.0F - dimorphism * sex));
		strength_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + size_mod));
		aggression_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1));
		obedience_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + (1f/aggression_mod)));

		/*
		 * We hijack the growingAge to hold the day of birth rather than the number of ticks to the next growth event.
		 * We want spawned animals to be adults, so we set their birthdays far enough back in time such that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());

	}
	public EntityWolfTFC(World par1World, IAnimal mother,  ArrayList<Float> data)
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


	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.WolfHealth);//MaxHealth
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();	
		this.dataWatcher.addObject(13, new Integer(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age

		this.dataWatcher.addObject(22, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, happy ticks
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of conception, stored as a string since we can't do long
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
		nbt.setInteger ("Sex", sex);
		nbt.setLong ("Animal ID", animalID);
		nbt.setFloat ("Size Modifier", size_mod);

		nbt.setByte("tamed", this.dataWatcher.getWatchableObjectByte(16));
		nbt.setInteger("happy", happyTicks);

		nbt.setBoolean("wasRoped",wasRoped);

		nbt.setFloat ("Strength Modifier", getStrength());
		nbt.setFloat ("Aggression Modifier", getAggression());
		nbt.setFloat ("Obedience Modifier", obedience_mod);

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
		this.setAngry(nbt.getBoolean("Angry"));
		animalID = nbt.getLong ("Animal ID");
		sex = nbt.getInteger ("Sex");
		size_mod = nbt.getFloat ("Size Modifier");

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");

		strength_mod = nbt.getFloat ("Strength Modifier");
		aggression_mod = nbt.getFloat ("Aggression Modifier");
		obedience_mod = nbt.getFloat ("Obedience Modifier");

		this.dataWatcher.updateObject(16, nbt.getByte("tamed"));
		this.happyTicks = nbt.getInteger("happy");

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
	public void setTamed(boolean par1)
	{
		if (this.familiarity > 80 && !this.isTamed())
		{
			super.setTamed(par1);

			double healthRatio = this.getHealth() / this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() ;

			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.WolfHealth);
			float h = (float)(healthRatio * this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
			this.setHealth(h);
		}
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

		if(this.getLeashed()){
			if(this.getLeashedToEntity() instanceof EntityLeashKnot && familiarity >= 5){
				this.setSitting(true);
			}
			else if(this.getLeashedToEntity() instanceof EntityPlayer){
				this.setSitting(false);
			}
		}

		if(this.getLeashed()&&!wasRoped)wasRoped = true;

		if(super.isInLove())
			setInLove(true);

		if(isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		this.handleFamiliarityUpdate();

		if (happyTicks > 0)
			happyTicks--;

		syncData();

		if (!this.worldObj.isRemote && isPregnant())
		{
			if(TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
				int i = rand.nextInt(2) + 1;
				for (int x = 0; x<i;x++)
				{
					ArrayList<Float> data = new ArrayList<Float>();
					data.add(mateSizeMod);
					EntityWolfTFC baby = (EntityWolfTFC) this.createChildTFC(this);
					baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
					baby.rotationYawHead = baby.rotationYaw;
					baby.renderYawOffset = baby.rotationYaw;
					baby.setAge(TFC_Time.getTotalDays());
					//baby.func_152115_b(this.func_152113_b());//setOwner
					//baby.setTamed(true);
					worldObj.spawnEntityInWorld(baby);
				}
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

		if(this.getLeashed() && this.isAngry() && getLeashedToEntity() == this.getOwner())
		{
			this.setAngry(false);
			this.setPathToEntity((PathEntity)null);
			this.setTarget((Entity)null);
			this.setAttackTarget((EntityLivingBase)null);
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
						(byte) happyTicks
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
				happyTicks = values[7];
				
				try
				{
					timeOfConception = Long.parseLong(this.dataWatcher.getWatchableObjectString(24));
				} catch (NumberFormatException e){}
			}
		}
	}

	@Override
	public void handleFamiliarityUpdate(){
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
				else if(this.isAdult() && familiarity >= 5){
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
		if(familiarity < (this.getOwner()!=null?5:0))familiarity = (this.getOwner()!=null?5:0);
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

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		int var2 = (int)(TFC_MobData.WolfDamage * getStrength() * getAggression() * (getSize()/2 + 0.5F));
		//TerraFirmaCraft.log.info(var2+", s: "+getStrength()+", a: "+ getAggression());
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
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
	public boolean isFood(ItemStack item) {
		return item != null &&
				(item.getItem() == TFCItems.beefRaw || item.getItem() == TFCItems.chickenRaw || item.getItem() == TFCItems.fishRaw || 
					item.getItem() == TFCItems.horseMeatRaw || item.getItem() == TFCItems.muttonRaw || item.getItem() == TFCItems.porkchopRaw || 
					item.getItem() == TFCItems.venisonRaw); // All meat except for calamari.
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
	public EntityWolf createChild(EntityAgeable entityageable)
	{
		return (EntityWolf) createChildTFC(entityageable);
	}

	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 9);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		this.entityDropItem(new ItemStack(TFCItems.Hide, 1, Math.max(0, Math.min(2, (int)(size_mod * ageMod * 0.9)))), 0);
		this.dropItem(Items.bone, (int)((rand.nextInt(3) + 1) * ageMod));
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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityWolfTFC)
			return true;
		else
			return false;
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
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("wolf");
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
		return new EntityWolfTFC(worldObj, this, data);
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
	public boolean interact(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if (player.isSneaking() && this.getOwner() != null)
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
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (((ItemFoodTFC)item).onConsumedByEntity(player.getHeldItem(), worldObj, this)));
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
					player.inventory.setInventorySlotContents(player.inventory.currentItem, (((ItemFoodTFC) itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this)));
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

			else if (isTamed() && (itemstack.getItem() == Items.dye || itemstack.getItem() == TFCItems.Dye))
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
		return 250;
	}

	@Override
	public int GetSlashArmor()
	{
		return 250;
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
	public void familiarize(EntityPlayer ep) {
		if(happyTicks == 0 && familiarity >= 5){
			familiarizedToday = true;
			this.getLookHelper().setLookPositionWithEntity(ep, 0, 0);
			this.playLivingSound();
			this.happyTicks = 40;
		}
		if(this.familiarity > 80 && this.getOwner() != null){
			this.setTamed(true);
		}
	}

	@Override
	public boolean trySetName(String name, EntityPlayer player) {
		if (this.checkFamiliarity(InteractionEnum.NAME, player))
		{
			this.setCustomNameTag(name);
			return true;
		}
		this.playSound("mob.wolf.growl",  6, (rand.nextFloat()/2F)+(isChild()?1.25F:0.75F));
		return false;
	}

	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player) {
		boolean flag = false;
		switch(interaction){
		case BREED: flag = familiarity > 20;break;
		case NAME:
			flag = familiarity > 40;
			break; // 5 higher than adult cap
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

	@Override
	public EnumDamageType GetDamageType() 
	{
		return EnumDamageType.SLASHING;
	}
}
