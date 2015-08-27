package com.bioxx.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;

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
	/** The eat grass AI task for this mob. */
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	private static final float GESTATION_PERIOD = 7.0f;
	//private static final float avgAdultWeight = 95; //The average weight of adult males in kg
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	 * the square root of the ratio of female mass : male mass
	 */
	private static final float dimorphism = 0.1728f;
	private static final int degreeOfDiversion = 1;

	private boolean running;
	private long animalID;
	private int sex;
	private int hunger;
	//private int age;
	private boolean pregnant;
	private int pregnancyRequiredTime;
	private long timeOfConception;
	private float mateSizeMod;
	private float mateStrengthMod;
	private float mateAggroMod;
	private float mateObedMod;
	private float size_mod; //How large the animal is
	private float strength_mod; //how strong the animal is
	private float aggression_mod = 1;//How aggressive / obstinate the animal is
	private float obedience_mod = 1; //How well the animal responds to commands.
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
		size_mod = (float) Math.sqrt(((rand.nextInt(rand.nextInt((degreeOfDiversion + 1) * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1F) * (1.0F - dimorphism * sex));
		strength_mod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(degreeOfDiversion * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + size_mod));
		aggression_mod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(degreeOfDiversion * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1));
		obedience_mod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(degreeOfDiversion * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1f / aggression_mod));
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

	public EntityDeer(World par1World, IAnimal mother,  ArrayList<Float> data)
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
	protected void entityInit()
	{
		super.entityInit();	
		this.dataWatcher.addObject(13, new Integer(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age
		
		this.dataWatcher.addObject(22, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, empty slot
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of conception, stored as a string since we can't do long
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
	protected boolean canDespawn()
	{
		return this.ticksExisted > 10000 && !wasRoped;
	}

	/*@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
	}*/

	public boolean getRunning()
	{
		return running;
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
				familiarizedToday = values[5] == 1;
				pregnant = values[6] == 1;
				
				try
				{
					timeOfConception = Long.parseLong(this.dataWatcher.getWatchableObjectString(24));
				} catch (NumberFormatException e){}
			}
		}
	}

	public void setRunning(boolean r)
	{
		running = r;
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
				baby.setLocationAndAngles(posX + (rand.nextFloat() - 0.5F) * 2F, posY, posZ + (rand.nextFloat() - 0.5F) * 2F, 0.0F, 0.0F);
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
		TFC_Core.PreventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;
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
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400);//MaxHealth
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);	
		this.entityDropItem(new ItemStack(TFCItems.Hide, 1, Math.max(0, Math.min(2, (int)(ageMod * size_mod * 1.84)))), 0);
		this.dropItem(Items.bone, (int)((rand.nextInt(4) + 2) * ageMod));
		float foodWeight = ageMod * (this.size_mod * 2400);//528 oz (33lbs) is the average yield of lamb after slaughter and processing

		TFC_Core.animalDropMeat(this, TFCItems.venisonRaw, foodWeight);
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return Items.leather;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		if(!worldObj.isRemote)
		{
			if(player.isSneaking()){
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
		nbt.setLong("ConceptionTime", timeOfConception);
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
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		if(getGender() == GenderEnum.MALE && isAdult() && worldObj.rand.nextInt(100) < 5)
			return TFC_Sounds.DEERCRY;
		return TFC_Sounds.DEERSAY;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return TFC_Sounds.DEERHURT;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return TFC_Sounds.DEERDEATH;
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
	public boolean isChild()
	{
		return !isAdult();
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
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 24);
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
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityDeer;
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
		return Helper.stringToInt("deer");
	}

	@Override
	public void eatGrassBonus()
	{
		hunger += 24000;
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
		return new EntityDeer(worldObj, this, data);
	}

	@Override
	public void setAge(int par1)
	{
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
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
		return attackedVec;
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
				if(this.isAdult() && familiarity <= 70) // Adult caps at 70 since babies are currently impossible
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
	public void familiarize(EntityPlayer ep) {
		ItemStack stack = ep.getHeldItem();
		if(stack != null && stack.getItem()!= null && stack.getItem().equals(TFCItems.Powder) && stack.getItemDamage() == 9){
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
	
	//Unused for now
	@Override
	public boolean isFood(ItemStack item) {
		return false;
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
	public int getDueDay()
	{
		return TFC_Time.getDayFromTotalHours((timeOfConception + pregnancyRequiredTime) / 1000);
	}
}
