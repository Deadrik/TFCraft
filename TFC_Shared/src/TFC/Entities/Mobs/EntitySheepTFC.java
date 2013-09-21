package TFC.Entities.Mobs;

import java.util.ArrayList;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.AIEatGrass;
import TFC.Entities.AI.EntityAIMateTFC;
import TFC.Items.Tools.ItemCustomKnife;

public class EntitySheepTFC extends EntitySheep implements IShearable, IAnimal
{
	/**
	 * Holds the RGB table of the sheep colors - in OpenGL glColor3f values - used to render the sheep colored fleece.
	 */
	public static final float[][] fleeceColorTable = new float[][] {{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

	/**
	 * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
	 * tick.
	 */
	private int sheepTimer;

	/** The eat grass AI task for this mob. */
	public AIEatGrass aiEatGrass = new AIEatGrass(this);

	int degreeOfDiversion = 4;

	protected long animalID;
	protected int sex = 0;
	protected int hunger;
	protected long hasMilkTime;
	protected boolean pregnant;
	protected int pregnancyRequiredTime;
	protected long timeOfConception;
	protected float mateSizeMod;
	public float size_mod = 1f;
	public boolean inLove;

	public EntitySheepTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.9F, 1.3F);
		float var2 = 0.23F;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(2, new EntityAIMateTFC(this,worldObj, var2));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain.itemID, false));
		this.tasks.addTask(6, this.aiEatGrass);

		hunger = 168000;
		animalID = TFC_Time.getTotalTicks() + entityId;
		pregnant = false;
		pregnancyRequiredTime = (int) (4 * TFC_Time.ticksInMonth);
		timeOfConception = 0;
		mateSizeMod = 0;
		sex = rand.nextInt(2);
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		this.setAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}
	public EntitySheepTFC(World par1World,IAnimal mother, float F_size)
	{
		this(par1World);
		this.posX = ((EntityLivingBase)mother).posX;
		this.posY = ((EntityLivingBase)mother).posY;
		this.posZ = ((EntityLivingBase)mother).posZ;
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + F_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, new Integer(0));
		this.dataWatcher.addObject(14, new Float(1));
		this.dataWatcher.addObject(15, Integer.valueOf(0));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(400);//MaxHealth
	}

	@Override
	protected void updateAITasks()
	{
		this.sheepTimer = this.aiEatGrass.getEatGrassTick();
		super.updateAITasks();
	}

	private float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = (int) TFC_Time.getTotalDays();
		float percent =(time-birth)/animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		if (this.worldObj.isRemote)
		{
			this.sheepTimer = Math.max(0, this.sheepTimer - 1);
		}

		//Handle Hunger ticking
		if (hunger > 168000)
		{
			hunger = 168000;
		}
		if (hunger > 0)
		{
			hunger--;
		}

		if(super.inLove > 0){
			super.inLove = 0;
			setInLove(true);
		}

		syncData();
		if(isAdult()){
			setGrowingAge(0);
		}
		else{
			setGrowingAge(-1);
		}

		if(isPregnant())
		{
			if(TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
				int i = rand.nextInt(3) + 1;
				for (int x = 0; x<i;x++)
				{
					EntitySheepTFC baby = new EntitySheepTFC(worldObj, this, mateSizeMod);
					baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
					baby.rotationYawHead = baby.rotationYaw;
					baby.renderYawOffset = baby.rotationYaw;
					worldObj.spawnEntityInWorld(baby);
					baby.setAge((int)TFC_Time.getTotalDays());
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

		if (hunger > 144000 && rand.nextInt (100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
	}

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(!this.worldObj.isRemote){
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));
				this.dataWatcher.updateObject(14, Float.valueOf(size_mod));
			}
			else{
				sex = this.dataWatcher.getWatchableObjectInt(13);
				size_mod = this.dataWatcher.getWatchableObjectFloat(14);
			}
		}
	}

	@Override
	public void eatGrassBonus()
	{
		this.setSheared(false);
		hunger += 24000;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		if(this.isAdult()){
			if(!this.getSheared()) {
				this.entityDropItem(new ItemStack(TFCItems.SheepSkin,1), 0.0F);
			} else {
				this.dropItem(TFCItems.Hide.itemID,1);
			}
			this.dropItem(Item.bone.itemID, rand.nextInt(5)+2);
		}

		if (this.isBurning()) {
			this.dropItem(TFCItems.muttonCooked.itemID,(int)(ageMod*this.size_mod *(5+rand.nextInt(5))));
		} else {
			this.dropItem(TFCItems.muttonRaw.itemID,(int)(ageMod*this.size_mod *(5+rand.nextInt(5))));
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return !pregnant&&(par1ItemStack.getItem() == TFCItems.WheatGrain ||par1ItemStack.getItem() == TFCItems.OatGrain||par1ItemStack.getItem() == TFCItems.RiceGrain||
				par1ItemStack.getItem() == TFCItems.BarleyGrain||par1ItemStack.getItem() == TFCItems.RyeGrain);
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return TFCItems.Wool.itemID;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(!worldObj.isRemote){
			par1EntityPlayer.addChatMessage(getGender()==GenderEnum.FEMALE?"Female":"Male");
			if(getGender()==GenderEnum.FEMALE && pregnant){
				par1EntityPlayer.addChatMessage("Pregnant");
			}
			//par1EntityPlayer.addChatMessage("12: "+dataWatcher.getWatchableObjectInt(12)+", 15: "+dataWatcher.getWatchableObjectInt(15));

			if(par1EntityPlayer.getHeldItem()!=null&&par1EntityPlayer.getHeldItem().getItem() instanceof ItemCustomKnife && !getSheared() && getPercentGrown(this) > 0.95F){
				setSheared(true);
				this.entityDropItem(new ItemStack(TFCItems.Wool,1), 0.0F);
				if(!par1EntityPlayer.capabilities.isCreativeMode){
					par1EntityPlayer.getHeldItem().damageItem(1, par1EntityPlayer);
				}
			}
		}
		return super.interact(par1EntityPlayer);
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
		nbt.setInteger ("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
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
		animalID = nbt.getLong ("Animal ID");
		sex = nbt.getInteger ("Sex");
		size_mod = nbt.getFloat ("Size Modifier");
		hunger = nbt.getInteger ("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		timeOfConception = nbt.getLong("ConceptionTime");
		this.dataWatcher.updateObject(15, nbt.getInteger ("Age"));
		this.setAge(nbt.getInteger ("Age"));
	}


	@Override
	public boolean isShearable(ItemStack item, World world, int X, int Y, int Z) 
	{
		return !getSheared() && isAdult();
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int X, int Y, int Z, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		setSheared(true);

		ret.add(new ItemStack(TFCItems.Wool.itemID, 2, getFleeceColor()));

		this.worldObj.playSoundAtEntity(this, "mob.sheep.shear", 1.0F, 1.0F);
		return ret;
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.PreventEntityDataUpdate) {
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
		return null;
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
	public EntityLiving getEntity() 
	{
		return this;
	}

	@Override
	public boolean canMateWith(IAnimal animal) 
	{
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntitySheepTFC) {
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
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[getSex()];
	}
	@Override
	public int getSex() {
		return dataWatcher.getWatchableObjectInt(13);
	}
	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable) {
		return new EntitySheepTFC(worldObj, this, entityageable.getEntityData().getFloat("MateSize"));
	}
	@Override
	public void setAge(int par1) {
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}
}
