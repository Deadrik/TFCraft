package TFC.Entities.Mobs;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAIMateTFC;

public class EntityCowTFC extends EntityAnimal implements IAnimal
{
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	protected long animalID;
	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected boolean pregnant;
	protected int pregnancyTime;
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
		pregnancyTime = 4 * TFC_Time.daysInMonth;
		conception = 0;
		mateSizeMod = 0;
		int degreeOfDiversion = 1;
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex);
		this.setSize(0.9F, 1.3F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 0.2F));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.itemID, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(5, new EntityAIWander(this, 0.2F));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
	}

	public EntityCowTFC(World par1World, IAnimal mother, float father_size)
	{
		this(par1World);
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(pregnant) 
		{
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength)
			{
				EntityCowTFC baby = (EntityCowTFC) createChild(this);
				baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				//entityanimal.initCreature();
				worldObj.spawnEntityInWorld(baby);
				baby.setGrowingAge( (int)TFC_Time.getTotalDays() + this.getNumberOfDaysToAdult());
				pregnant = false;
			}
		}
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
		par1NBTTagCompound.setInteger("Age", getAge());
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
		this.dataWatcher.updateObject(12, nbt.getInteger ("Age"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.cow.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.cow.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.cow.hurt";
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
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
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[this.getEntityData().getInteger("Sex")];
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return new EntityCowTFC(worldObj);
	}

	@Override
	public int getAge() 
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
		return getAge() >= getNumberOfDaysToAdult();
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
		if (sex == 0)
		{
			otherAnimal.mate(this);
			return;
		}
		conception = TFC_Time.getTotalTicks();
		pregnant = true;
		//targetMate.setGrowingAge (TFC_Settings.dayLength);
		resetInLove();
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSize();
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
