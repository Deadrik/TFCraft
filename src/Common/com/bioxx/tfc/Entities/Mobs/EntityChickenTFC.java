package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityChicken;
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
import com.bioxx.tfc.Entities.AI.EntityAIFindNest;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Util.Helper;

public class EntityChickenTFC extends EntityChicken implements IAnimal
{
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

	protected int sex;
	protected int hunger;
	protected int age;
	public float size_mod;			//How large the animal is
	public float strength_mod;		//how strong the animal is
	public float aggression_mod = 1;//How aggressive / obstinate the animal is
	public float obedience_mod = 1;	//How well the animal responds to commands.
	public boolean inLove;

	int degreeOfDiversion = 2;
	/** The time until the next egg is spawned. */
	public long nextEgg;
	public int EggTime = TFC_Time.dayLength;
	
	protected int familiarity = 0;
	private long lastFamiliarityUpdate = 0;
	private boolean familiarizedToday = false;
	
	protected float avgAdultWeight = 2;			//The average weight of adult males in kg
	protected float dimorphism = 0.0606f;		//1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
											//the square root of the ratio of female mass : male mass

	public EntityChickenTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.3F, 0.7F);
		this.timeUntilNextEgg = 9999;//Here we set the vanilla egg timer to 9999
		this.nextEgg = TFC_Time.getTotalTicks() + EggTime;
		hunger = 168000;
		sex = rand.nextInt(2);

		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityChickenTFC.class, 6.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.tasks.addTask(6, this.aiEatGrass);
		addAI();

		size_mod = (float) Math.sqrt((rand.nextInt(rand.nextInt((degreeOfDiversion + 1) * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + 1F) * (1.0F - dimorphism * sex));
		strength_mod = (float) Math.sqrt((rand.nextInt(rand.nextInt(degreeOfDiversion * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + size_mod));
		aggression_mod = (float) Math.sqrt((rand.nextInt(rand.nextInt(degreeOfDiversion * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + 1));
		obedience_mod = (float) Math.sqrt((rand.nextInt(rand.nextInt(degreeOfDiversion * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + 1f / aggression_mod));

		/*
		 * We hijack the growingAge to hold the day of birth rather than the number of ticks to the next growth event.
		 * We want spawned animals to be adults, so we set their birthdays far enough back in time such that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	// Chickens hatching from a nestbox
	public EntityChickenTFC(World world, double posX, double posY, double posZ, NBTTagCompound genes)
	{
		this(world);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		float m_size = genes.getFloat("m_size");
		float f_size = genes.getFloat("f_size");
		size_mod = (rand.nextInt(degreeOfDiversion + 1) * (rand.nextBoolean() ? 1 : -1) / 10f + 1F) * (1.0F - 0.1F * sex) * (float) Math.sqrt((m_size + f_size) / 1.9F);

		// We hijack the growingAge to hold the day of birth rather than number of ticks to next growth event.
		this.setAge(TFC_Time.getTotalDays());
	}

	public void addAI()
	{
		if(sex==0)
		{
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		}
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RyeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RiceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.BarleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.OatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.MaizeEar, false));
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
		this.dataWatcher.addObject(13, new Integer(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age
		
		this.dataWatcher.addObject(22, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, empty slot, empty slot
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
		this.handleFamiliarityUpdate();

		//Make sure that the vanilla egg timer is never after to reach 0 but always setting it back to 9999
		this.timeUntilNextEgg = 9999;
		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.PreventEntityDataUpdate = true;
		if(getGender()==GenderEnum.MALE)
		{
			nextEgg=10000;
		}

		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		else if(hunger < 144000 && super.isInLove()){
			this.setInLove(false);
		}
	}

	public void roosterCrow()
	{
		if((TFC_Time.getTotalTicks()-15)%TFC_Time.dayLength == 0 && getGender() == GenderEnum.MALE && isAdult() && this.worldObj.canBlockSeeTheSky((int)this.posX, (int)this.posY,(int)this.posZ)){
			this.playSound(TFC_Sounds.ROOSTERCROW, 6, rand.nextFloat() / 2F + 0.75F);
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
						(byte) 0, // Empty
						(byte) 0 // Empty
				};
				ByteBuffer buf = ByteBuffer.wrap(values);
				this.dataWatcher.updateObject(22, buf.getInt());
				this.dataWatcher.updateObject(23, buf.getInt());
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
				familiarizedToday = values[5] == 1;
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
		nbt.setFloat ("Size Modifier", size_mod);
		
		nbt.setInteger("Familiarity", familiarity);
		nbt.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbt.setBoolean("Familiarized Today", familiarizedToday);

		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);

		nbt.setInteger ("Hunger", hunger);
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
		sex = nbt.getInteger ("Sex");
		size_mod = nbt.getFloat ("Size Modifier");

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");
		
		strength_mod = nbt.getFloat ("Strength Modifier");
		aggression_mod = nbt.getFloat ("Aggression Modifier");
		obedience_mod = nbt.getFloat ("Obedience Modifier");

		hunger = nbt.getInteger ("Hunger");
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
	public EntityChicken createChild(EntityAgeable entityAgeable)
	{
		return (EntityChicken) createChildTFC(entityAgeable);
	}

	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 4.14);
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
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal.getAnimalTypeID() == this.getAnimalTypeID();
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
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("chicken");
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

	// This should only be called when spawning baby chickens with a spawn egg, so both size values come from the animal clicked on.
	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable)
	{
		if (entityageable instanceof IAnimal)
		{
			IAnimal animal = (IAnimal) entityageable;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("m_size", animal.getSize());
			nbt.setFloat("f_size", animal.getSize());
			return new EntityChickenTFC(worldObj, posX, posY, posZ, nbt);
		}

		return null;
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
		ItemStack itemstack = player.getHeldItem();

		if(!worldObj.isRemote)
		{
			if (isAdult() && player.isSneaking() && !isFood(itemstack) && attackEntityFrom(DamageSource.generic, 5))
			{
				player.inventory.addItemStackToInventory(new ItemStack(Items.feather, 1));
				familiarity -= 4; //Plucking feathers decreases familiarity
				return true;
			}

			if(player.isSneaking() && !familiarizedToday){
				this.familiarize(player);
				return true;
			}
		}

		if(itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName")){
			if(this.trySetName(itemstack.stackTagCompound.getString("ItemName"), player)){
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
				float familiarityChange = 6 * obedience_mod / aggression_mod;
				if(this.isAdult() && familiarity <= 45) // Adult caps at 45
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
		return item != null && (item.getItem() == TFCItems.WheatGrain || item.getItem() == TFCItems.OatGrain || item.getItem() == TFCItems.RiceGrain ||
				item.getItem() == TFCItems.BarleyGrain || item.getItem() == TFCItems.RyeGrain || item.getItem() == TFCItems.MaizeEar);
	}

	@Override
	public void familiarize(EntityPlayer ep) {
		ItemStack stack = ep.getHeldItem();
		if(stack != null && stack.getItem() != null && isFood(stack) && !familiarizedToday){
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
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player) {
		boolean flag = false;
		switch(interaction){
		case NAME:
			flag = familiarity > 50;
			break; //Set 5 higher than adult cap.
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
		return 0; // Chickens don't get pregnant
	}
}
