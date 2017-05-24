package com.bioxx.tfc.Entities.Mobs;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIMateTFC;
import com.bioxx.tfc.Entities.AI.EntityAIOcelotSitTFC;
import com.bioxx.tfc.Entities.AI.EntityAISitTFC;
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


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityOcelotTFC extends EntityTameable implements IAnimal, IInnateArmor, ICausesDamage
{


    private static final float GESTATION_PERIOD = 6.0f;
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is calculated by cube-square law from
	 * the square root of the ratio of female mass : male mass
	 */
	private static final float DIMORPHISM = 0.1822f;
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
	private int mateColor;
	private float sizeMod; //How large the animal is
	private float strengthMod; //how strong the animal is

	private float aggressionMod = 1;//How aggressive / obstinate the animal is
	private float obedienceMod = 1; //How well the animal responds to commands.
	private boolean inLove;
    private boolean adultTaskSet = false;
    
	private int familiarity;
	private long lastFamiliarityUpdate;
	private boolean familiarizedToday;
	private EntityAITempt aiTempt;
	private Vec3 attackedVec;
	private Entity fearSource;
	protected EntityAITargetNonTamedTFC targetChicken;
	
    
	public EntityOcelotTFC(World p_i1688_1_) {
		super(p_i1688_1_);


        this.setSize(0.6F, 0.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.aiSit = new EntityAISit(this);  
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6D, TFCItems.fishRaw, false));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.tasks.addTask(6, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(7, new EntityAIOcelotSitTFC(this, 1.33D));
        this.tasks.addTask(8, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(9, new EntityAIOcelotAttack(this));
        this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 1.0F));
        this.tasks.addTask(11, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(12, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.targetChicken = new EntityAITargetNonTamedTFC(this, EntityChickenTFC.class, 200, false);
        

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
		
	}
	public EntityOcelotTFC(World par1World, IAnimal mother, List<Float> data)
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
   
    
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

	/**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float p_70097_2_)
    {

        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
        	this.aiSit.setSitting(false);
            return super.attackEntityFrom(par1DamageSource, p_70097_2_);
        }
    }
	
	@Override
	protected boolean canDespawn()
	{
		if (!this.isAdult()) // Babies can't despawn
			return false;
		if (this.getOwner() != null) // Can't despawn if yeah gots an owner!
			return false;
		if (this.isTamed())
			return false;
		return ticksExisted > 40000;
	}

	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || (isAdult() && this.familiarity <= FAMILIARITY_CAP);
	}
	
	
	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() &&this.isAdult() && animal.isAdult() &&
				animal instanceof EntityOcelotTFC;
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
	public EntityOcelotTFC createChild(EntityAgeable entityageable)
	{
		return (EntityOcelotTFC) createChildTFC(entityageable);
	}
	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable) 
		{
			ArrayList<Float> data = new ArrayList<Float>();
			data.add(eAgeable.getEntityData().getFloat("MateSize"));
			data.add(eAgeable.getEntityData().getFloat("MateStrength"));
			data.add(eAgeable.getEntityData().getFloat("MateAggro"));
			data.add(eAgeable.getEntityData().getFloat("MateObed"));
			EntityOcelotTFC baby = new EntityOcelotTFC(worldObj, this, data);
			
			return baby;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		this.entityDropItem(new ItemStack(TFCItems.hide, 1, 0),0);
		
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
        this.dataWatcher.addObject(26, Integer.valueOf(0));  //set skin
		this.dataWatcher.addObject(13, Integer.valueOf(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age
		
		this.dataWatcher.addObject(22, Integer.valueOf(0)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, empty slot
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of conception, stored as a string since we can't do long
	}
	
    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
	@Override
	protected void fall(float p_70069_1_) {}


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
	public int getAge() {
	
		return getBirthDay();
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
		return Helper.stringToInt("ocelot");
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
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
	public boolean getCanSpawnHere()
    {
    	
    	if(this.worldObj.rand.nextInt(30) == 0)
    	{
    		float temperatureAvg = TFC_Climate.getBioTemperature(this.worldObj,(int) this.posX, (int) this.posZ);
    		float rainfall = TFC_Climate.getRainfall(this.worldObj, (int) this.posX, 0, (int) this.posZ);
    		
    		if(		rainfall >= 1000 &&
    				temperatureAvg >= 23 &&
    				temperatureAvg <= 44)
    		{
    			return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    		}
    	}
    	return false;
        
    }

	/**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getCommandSenderName()
    {
        return this.hasCustomNameTag() ? this.getCustomNameTag() : (this.isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getCommandSenderName());
    }
	
	/**
     * Returns the sound this mob makes on death.
     */
	@Override
    protected String getDeathSound()
    {
        return TFC_Sounds.CATHITT;
    }
	protected Item getDropItem()
    {
        return TFCItems.hide;
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
	public Entity getFearSource() {

		return this.fearSource;
	}
	
	@Override
	public GenderEnum getGender() {
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
		return TFC_Sounds.CATHISS;
    }

	@Override
	public boolean getInLove()
	{
		return inLove;
	}
	
	/**
     * Returns the sound this mob makes while it's alive.
     */
	@Override
    protected String getLivingSound()
    {
		if (this.isTamed())
		{
			if (this.isAdult())
				return (this.isInLove() ? TFC_Sounds.CATPURR : (this.rand.nextInt(4) == 0 ? TFC_Sounds.CATPURREOW : TFC_Sounds.CATMEOW ));
			else
			{
				return TFC_Sounds.KITTENMEOW;
			}
		}
		return null;
		
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
	
	@Override
	public float getSizeMod()
	{
		return sizeMod;
	}
	
	/**
     * Returns the volume for the sounds this mob makes.
     */
	@Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }
	@Override
	public float getStrengthMod()
	{
		return strengthMod;
	}

	public int getTameSkin()
    {
        return this.dataWatcher.getWatchableObjectInt(26);
    }
	// Coughs up a ball of wool with a nice phlegmy sound.
	public void Hairball()
	{
		this.worldObj.playSoundAtEntity(this, TFC_Sounds.CATCOUGH, this.getSoundVolume(), 1.0F);
		ItemStack item = new ItemStack(TFCItems.wool, 1);
		this.entityDropItem(item, 1.0f);
		
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
        ItemStack itemstack = player.inventory.getCurrentItem();

        if(!worldObj.isRemote)
		{
			if (!this.aiTempt.isRunning() && player.isSneaking() && this.getOwner() == null && canFamiliarize() && player.getDistanceSqToEntity(this) < 9.0D)
			{
				this.familiarize(player);
				return true;
			}
			
			if(player.getHeldItem() != null)
			{
				
				if(isFood(itemstack))
				{
					Item item = itemstack.getItem();
					if(item instanceof ItemFoodTFC && hunger <= 160000)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ((ItemFoodTFC) item).onConsumedByEntity(player.getHeldItem(), worldObj, this));
						this.hunger += 24000;
						this.familiarity += 1;
						worldObj.playSoundAtEntity(this, TFC_Sounds.CATPURR, this.getSoundVolume(), worldObj.rand.nextFloat() * 0.1F + 0.9F);
						return true;
					}
				}
			}
			else if(this.isTamed())
	        {
	            if (this.func_152114_e(player))
	            {
	                this.aiSit.setSitting(!this.isSitting());

	                this.isJumping = false;
	                this.setPathToEntity((PathEntity)null);
	                this.setTarget((Entity)null);
	                this.setAttackTarget((EntityLivingBase)null);
	                return true;
	            }
	        }
			if(getGender() == GenderEnum.FEMALE && pregnant)
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));
			
			if (itemstack != null && this.isBreedingItemTFC(itemstack) && this.checkFamiliarity(InteractionEnum.BREED, player) && this.getGrowingAge() == 0 && !super.isInLove() &&
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
            if (this.familiarity > 20 && !this.isTamed())
            {
            	this.setFamiliarity(35);
                this.setTamed(true);
                this.setTameSkin(1 + this.worldObj.rand.nextInt(5));
                this.func_152115_b(player.getUniqueID().toString());
                this.playTameEffect(true);
                worldObj.playSoundAtEntity(this, TFC_Sounds.CATPURR, this.getSoundVolume(), worldObj.rand.nextFloat() * 0.1F + 0.9F);
                this.aiSit.setSitting(true);
                this.worldObj.setEntityState(this, (byte)7);
    			return true;
            }
            if(itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName"))
            {
    			if(this.trySetName(itemstack.stackTagCompound.getString("ItemName"),player))
    			{
    				itemstack.stackSize--;
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
	/**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }
	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}
	public boolean isBreedingItemTFC(ItemStack item)
	{
		return !pregnant && (item.getItem() == TFCItems.beefRaw);
	}
	@Override
	public boolean isFood(ItemStack item)
	{
		return item != null && (item.getItem() == TFCItems.fishRaw);
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
			return;
		}
		timeOfConception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		otherAnimal.setInLove(false);
		this.setInLove(false);
		mateAggroMod = otherAnimal.getAggressionMod();
		mateObedMod = otherAnimal.getObedienceMod();
		mateSizeMod = otherAnimal.getSizeMod();
		mateStrengthMod = otherAnimal.getStrengthMod();
		
	}
	@Override
	public void onLivingUpdate()
	{

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
		{
			if(!adultTaskSet){
				setAdultTasks();
				adultTaskSet = true;
			}
			setGrowingAge(0);	
		}
		else
		{
			setGrowingAge(-1);
		}
		//hair ball
		if(!this.worldObj.isRemote && rand.nextInt(120000)== 0  & this.isSitting() && this.isTamed() && isAdult())
		{
			this.Hairball();
		}
			
		
		
		//baby time?
		if (!this.worldObj.isRemote && isPregnant())
		{
			
			if (TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
					EntityOcelotTFC baby = (EntityOcelotTFC) createChildTFC(this);
					baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
					worldObj.spawnEntityInWorld(baby);
					baby.setAge(TFC_Time.getTotalDays());
					baby.setTamed(true);
					baby.setTameSkin(this.getTameSkin());
					setKittenTasks(baby);
					this.pregnant = false;
			}
			
		}


		// heal and reset love.
		if (hunger > 144000 && rand.nextInt(100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		else if (hunger < 144000 && super.isInLove())
		{
			this.setInLove(false);
		}
		
		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

	}
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {
        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);

        EntityOcelotTFC entityocelot = new EntityOcelotTFC(this.worldObj);
        entityocelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        entityocelot.setGrowingAge(-24000);
        this.worldObj.spawnEntityInWorld(entityocelot);
        return p_110161_1_;
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
		adultTaskSet =nbt.getBoolean("AdultTaskSet");
		strengthMod = nbt.getFloat("Strength Modifier");
		aggressionMod = nbt.getFloat("Aggression Modifier");
		obedienceMod = nbt.getFloat("Obedience Modifier");
		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");
		this.dataWatcher.updateObject(26, nbt.getInteger("CatType"));
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

	
	public void setAdultTasks()
	{
        this.getNavigator().setAvoidsWater(true);
        this.aiSit = new EntityAISitTFC(this); 
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6D, TFCItems.fishRaw, false));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.tasks.addTask(6, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(7, new EntityAIOcelotSitTFC(this, 1.33D));
        this.tasks.addTask(8, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(9, new EntityAIOcelotAttack(this));
        this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 1.0F));
        this.tasks.addTask(11, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(12, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChickenTFC.class, 750, true));	
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
	public void setAttackedVec(Vec3 attackedVec) {
		// None
		
	}
	
	@Override
	public void setBirthDay(int day) {
		this.dataWatcher.updateObject(15, day);
		
	}
	

    @Override
	public void setFamiliarity(int f) {
		this.familiarity = f;
		
	}

    
    @Override
	public void setFearSource(Entity fearSource) {
    	
    	this.fearSource = fearSource;
		
	}
    
    @Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.preventEntityDataUpdate)
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
	}

    @Override
	public void setHunger(int h) {
		hunger = h;
		
	}
	
    @Override
	public void setInLove(boolean b) {
		this.inLove = b;
		
	}

    public void setKittenTasks(EntityOcelotTFC entity)
	{
	    entity.getNavigator().setAvoidsWater(true);
        this.aiSit = new EntityAISitTFC(this); 
        this.tasks.addTask(2, this.aiSit);
	    entity.tasks.addTask(1, new EntityAIFollowParent(this, 1.1D));
        entity.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        entity.aiSit.setSitting(false);

	}
	@Override
	public void setObedienceMod(float obedience) {
		this.obedienceMod = obedienceMod;
		
	}		
    @Override
	public void setSizeMod(float size) {
		this.sizeMod = size;
		
	}

    @Override
	public void setStrengthMod(float strength) {
		this.strengthMod = strength;
		
	}
    public void setTameSkin(int skin)
    {
        this.dataWatcher.updateObject(26, skin);
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
				this.dataWatcher.updateObject(26, this.getTameSkin());
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
				this.setTameSkin(this.dataWatcher.getWatchableObjectInt(26));
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
		this.playSound(this.getHurtSound(), this.getSoundVolume(), rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		return false;
	}

    @Override
	protected void updateAITasks()
	{
		super.updateAITasks();
	}
    
    /**
     * main AI tick function, replaces updateEntityActionState
     */
    public void updateAITick()
    {
        if (this.getMoveHelper().isUpdating())
        {
            double d0 = this.getMoveHelper().getSpeed();

            if (d0 == 0.6D)
            {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (d0 == 1.33D)
            {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else
            {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else
        {
            this.setSneaking(false);
            this.setSprinting(false);
        }
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
		nbt.setBoolean("AdultTaskSet", adultTaskSet);
		nbt.setInteger("Familiarity", familiarity);
		nbt.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbt.setBoolean("Familiarized Today", familiarizedToday);
		nbt.setFloat("Strength Modifier", strengthMod);
		nbt.setFloat("Aggression Modifier", aggressionMod);
		nbt.setFloat("Obedience Modifier", obedienceMod);
		nbt.setInteger("CatType", this.dataWatcher.getWatchableObjectInt(26));
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
	@Override
	public EnumDamageType getDamageType() {
		return EnumDamageType.SLASHING;
	}
	@Override
	public int getCrushArmor() {
		return 250;
	}
	@Override
	public int getSlashArmor() {

		return 250;
	}
	@Override
	public int getPierceArmor() {
		return -335;
	}

}
