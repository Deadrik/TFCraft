package TFC.Entities.Mobs;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.AIEatGrass;
import TFC.Entities.AI.EntityAIMateTFC;

public class EntityCowTFC extends EntityCow implements IAnimal
{
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
	public boolean inLove;

	public EntityCowTFC(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + entityId;
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime = 4 * TFC_Time.daysInMonth;
		conception = 0;
		mateSizeMod = 0;
		sex = rand.nextInt(2);
		int degreeOfDiversion = 1;
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex);
		this.setSize(0.9F, 1.3F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 0.2F));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.itemID, false));
		this.tasks.addTask(6, this.aiEatGrass);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		this.setGrowingAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntityCowTFC(World par1World, IAnimal mother, float father_size)
	{
		this(par1World);
		this.posX = ((EntityLivingBase)mother).posX;
		this.posY = ((EntityLivingBase)mother).posY;
		this.posZ = ((EntityLivingBase)mother).posZ;
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, Integer.valueOf(0));
		this.dataWatcher.addObject(14, Float.valueOf(1.0f));
	}

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


		if(isPregnant()) 
		{
			if(TFC_Time.getTotalTicks() >= conception + pregnancyRequiredTime)
			{
				EntityCowTFC baby = (EntityCowTFC) createChild(this);
				baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				worldObj.spawnEntityInWorld(baby);
				baby.setGrowingAge((int)TFC_Time.getTotalDays());
				pregnant = false;
			}
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 * */
		int age = this.getGrowingAge();
		super.onLivingUpdate();
		this.setGrowingAge(age);

		if (hunger > 144000 && rand.nextInt (100) == 0 && func_110143_aJ() < TFC_Core.getEntityMaxHealth(this) && !isDead)
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
				size_mod = this.dataWatcher.func_111145_d(14);
			}
		}
	}

	private float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = (int) TFC_Time.getTotalDays();
		float percent =(time-birth)/animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(500);//MaxHealth
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger ("Sex", sex);
		par1NBTTagCompound.setLong ("Animal ID", animalID);
		par1NBTTagCompound.setFloat ("Size Modifier", size_mod);
		par1NBTTagCompound.setInteger ("Hunger", hunger);
		par1NBTTagCompound.setBoolean("Pregnant", pregnant);
		par1NBTTagCompound.setFloat("MateSize", mateSizeMod);
		par1NBTTagCompound.setLong("ConceptionTime",conception);
		par1NBTTagCompound.setInteger("Age", getBirthDay());
		par1NBTTagCompound.setLong("HasMilkTime", hasMilkTime);
	}

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
		conception = nbt.getLong("ConceptionTime");
		hasMilkTime = nbt.getLong("HasMilkTime");
		this.setGrowingAge(nbt.getInteger ("Age"));
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return Item.leather.itemID;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		if(isAdult())
		{
			this.dropItem(TFCItems.Hide.itemID,1);
		}

		if (this.isBurning()) {
			this.dropItem(Item.beefCooked.itemID, (int) (getSize() * (15+this.rand.nextInt(10))));
		} else {
			this.dropItem(Item.beefRaw.itemID, (int) (getSize() * (15+this.rand.nextInt(10))));
		}

	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		if(getGender() == GenderEnum.FEMALE && isAdult() && hasMilkTime < TFC_Time.getTotalTicks())
		{
			ItemStack var2 = player.inventory.getCurrentItem();
			if (var2 != null && var2.itemID == TFCItems.WoodenBucketEmpty.itemID) 
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(TFCItems.WoodenBucketMilk));
				hasMilkTime = TFC_Time.getTotalTicks() + TFC_Time.dayLength;//Can be milked once every day
				return true;
			}
		}
		return super.interact(player);
	}

	@Override
	public void setGrowingAge(int par1)
	{
		this.dataWatcher.updateObject(12, Integer.valueOf(par1));
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}

	@Override
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[this.getEntityData().getInteger("Sex")];
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return new EntityCowTFC(worldObj, this, entityageable.getEntityData().getFloat("MateSize"));
	}

	@Override
	public int getBirthDay() 
	{
		return this.dataWatcher.getWatchableObjectInt(12);
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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityCowTFC) {
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
}
