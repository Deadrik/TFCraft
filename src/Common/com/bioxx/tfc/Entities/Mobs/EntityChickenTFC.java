package com.bioxx.tfc.Entities.Mobs;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.EntityAIFindNest;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.InteractionEnum;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Util.Helper;

public class EntityChickenTFC extends EntityChicken implements IAnimal
{
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected int age;
	protected float mateSizeMod;
	public float size_mod;			//How large the animal is
	public float strength_mod;		//how strong the animal is
	public float aggression_mod = 1;//How aggressive / obstinate the animal is
	public float obedience_mod = 1;	//How well the animal responds to commands.
	public float colour_mod = 1;	//what the animal looks like
	public float climate_mod = 1;	//climate adaptability
	public float hard_mod = 1;		//hardiness
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
		mateSizeMod = 1f;
		sex = rand.nextInt(2);

		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.tasks.addTask(6, this.aiEatGrass);
		addAI();

		size_mod =(float)Math.sqrt((((rand.nextInt (rand.nextInt((degreeOfDiversion + 1)*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1F) * (1.0F - dimorphism * sex));
		strength_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + size_mod));
		aggression_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1));
		obedience_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + (1f/aggression_mod)));
		colour_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt((degreeOfDiversion+2)*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + 1));
		hard_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + size_mod));
		climate_mod = (float)Math.sqrt((((rand.nextInt (rand.nextInt(degreeOfDiversion*10)+1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f) + hard_mod));

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		this.setAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntityChickenTFC(World world, IAnimal mother,  ArrayList<Float> data)
	{
		this(world);
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
		this.setAge((int) TFC_Time.getTotalDays());
	}

	public EntityChickenTFC(World world, double posX, double posY, double posZ, NBTTagCompound genes)
	{
		this(world);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		float m_size = genes.getFloat("m_size");
		float f_size = genes.getFloat("f_size");
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((m_size + f_size)/1.9F);
		//size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}

	public void addAI()
	{
		if(sex==0)
		{
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		}
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain, false));
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
	}

	public void roosterCrow()
	{
		if((TFC_Time.getTotalTicks()-15)%TFC_Time.dayLength == 0 && getGender() == GenderEnum.MALE && isAdult() && this.worldObj.canBlockSeeTheSky((int)this.posX, (int)this.posY,(int)this.posZ)){
			this.playSound(TFC_Sounds.ROOSTERCROW, 6, (rand.nextFloat()/2F)+0.75F);
		}
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

		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		nbt.setFloat ("Colour Modifier", colour_mod);
		nbt.setFloat ("Climate Adaptation Modifier", climate_mod);
		nbt.setFloat ("Hardiness Modifier", hard_mod);

		nbt.setInteger ("Hunger", hunger);
		nbt.setFloat("MateSize", mateSizeMod);
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
		lastFamiliarityUpdate = nbt.getLong("lastFamiliarityUpdate");
		
		strength_mod = nbt.getFloat ("Strength Modifier");
		aggression_mod = nbt.getFloat ("Aggression Modifier");
		obedience_mod = nbt.getFloat ("Obedience Modifier");
		colour_mod = nbt.getFloat ("Colour Modifier");
		climate_mod = nbt.getFloat ("Climate Adaptation Modifier");
		hard_mod = nbt.getFloat ("Hardiness Modifier");

		hunger = nbt.getInteger ("Hunger");
		mateSizeMod = nbt.getFloat("MateSize");
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
	public EntityChicken createChild(EntityAgeable EntityAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(mateSizeMod);
		return new EntityChickenTFC(worldObj, this, data);
//		return super.createChild(EntityAgeable);
	}

	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int)(TFC_Time.daysInMonth * 4.14);
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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal.getAnimalTypeID() == this.getAnimalTypeID()) 
			return true;
		return false;
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
		return GenderEnum.genders[getSex()];
	}

	@Override
	public int getSex()
	{
		return dataWatcher.getWatchableObjectInt(13);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(entityageable.getEntityData().getFloat("MateSize"));
		return new EntityChickenTFC(worldObj, this, data);
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
			if(player.isSneaking()){
				this.familiarize(player);
				return true;
			}
			/*if(!player.isSneaking())
			{
				player.addChatMessage(new ChatComponentText(getGender()==GenderEnum.FEMALE?"Female":"Male"));
			}*/
		}
		//player.addChatMessage("12: "+dataWatcher.getWatchableObjectInt(12)+", 15: "+dataWatcher.getWatchableObjectInt(15));
		if(!worldObj.isRemote && isAdult()&& player.isSneaking() && attackEntityFrom(DamageSource.generic, 5))
		{
			player.inventory.addItemStackToInventory(new ItemStack(Items.feather, 1));
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
	public float getStrength()
	{
		return this.getDataWatcher().getWatchableObjectFloat(24);
	}

	@Override
	public float getAggression()
	{
		return this.getDataWatcher().getWatchableObjectFloat(25);
	}

	@Override
	public float getObedience()
	{
		return this.getDataWatcher().getWatchableObjectFloat(26);
	}

	@Override
	public float getColour()
	{
		return this.getDataWatcher().getWatchableObjectFloat(27);
	}

	@Override
	public float getClimateAdaptation()
	{
		return this.getDataWatcher().getWatchableObjectFloat(28);
	}

	@Override
	public float getHardiness()
	{
		return this.getDataWatcher().getWatchableObjectFloat(29);
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
		// TODO Auto-generated method stub
		return familiarity;
	}

	@Override
	public void handleFamiliarityUpdate() {
		if(lastFamiliarityUpdate < TFC_Time.getTotalDays()){
			if(familiarizedToday && familiarity < 100){
				lastFamiliarityUpdate = TFC_Time.getTotalDays();
				familiarizedToday = false;
				float familiarityChange = (6 * obedience_mod / aggression_mod);
				if(this.isAdult() && (familiarity > 30 && familiarity < 80)){
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
			}
		}
		if(familiarity > 100)familiarity = 100;
		if(familiarity < 0)familiarity = 0;
	}

	@Override
	public void familiarize(EntityPlayer ep) {
		ItemStack stack = ep.getHeldItem();
		if(stack != null && stack.getItem() != null && stack.getItem() instanceof ItemFoodTFC && ((ItemFoodTFC)stack.getItem()).getFoodGroup().equals(EnumFoodGroup.Grain)){
			if (!ep.capabilities.isCreativeMode)
			{
				ep.inventory.setInventorySlotContents(ep.inventory.currentItem,(((ItemFoodTFC)stack.getItem()).onConsumedByEntity(ep.getHeldItem(), worldObj, this)));
			}
			else
			{
				worldObj.playSoundAtEntity(this, "random.burp", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			familiarizedToday = true;
			this.getLookHelper().setLookPositionWithEntity(ep, 0, 0);
			this.playLivingSound();
		}
	}
	
	@Override
	public boolean trySetName(String name, EntityPlayer player) {
		if(this.checkFamiliarity(InteractionEnum.NAME, player) && !this.hasCustomNameTag()){
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
		case SHEAR: flag = familiarity > 10;break;
		case MILK: flag = familiarity > 10;break;
		case NAME: flag = familiarity > 20;break;
		default: break;
		}
		if(!flag && !player.worldObj.isRemote){
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("entity.notFamiliar")));
		}
		return flag;
	}
}
