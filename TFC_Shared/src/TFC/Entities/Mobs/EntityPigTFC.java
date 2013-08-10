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
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAIMateTFC;

public class EntityPigTFC extends EntityPig implements IAnimal
{
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	protected long animalID;
	protected int sex;
	protected int hunger;
	protected int age;
	protected boolean pregnant;
	protected int pregnancyTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod = 1f;
	public boolean inLove;

	public EntityPigTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.9F, 0.9F);
		this.getNavigator().setAvoidsWater(true);
		float var2 = 0.25F;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMateTFC(this, worldObj, var2));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.itemID, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
		this.tasks.addTask(5, new EntityAIWander(this, var2));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
	}

	public EntityPigTFC(World par1World, IAnimal mate)
	{
		this(par1World);
		mateSizeMod = mate.getSize();
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
	public void onLivingUpdate ()
	{
		super.onLivingUpdate ();
		if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength){
				int i = rand.nextInt(5) + 8;
				for (int x = 0; x < i;x++)
				{
					EntityPigTFC baby = (EntityPigTFC) createChild(this);
					baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
					baby.rotationYawHead = baby.rotationYaw;
					baby.renderYawOffset = baby.rotationYaw;
					//entityanimal.initCreature();
					worldObj.spawnEntityInWorld(baby);
					baby.setGrowingAge( (int)TFC_Time.getTotalDays() + this.getNumberOfDaysToAdult());
				}			
				pregnant = false;
			}
		}

	}
	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

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
		nbt.setLong("ConceptionTime",conception);
		nbt.setInteger("Age", getAge());
		nbt.setBoolean("Saddle", this.getSaddled());
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
		this.dataWatcher.updateObject(12, nbt.getInteger ("Age"));
		this.setSaddled(nbt.getBoolean("Saddle"));
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if (super.interact(par1EntityPlayer))
		{
			return true;
		}
		else if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
		{
			par1EntityPlayer.mountEntity(this);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return this.isBurning() ? Item.porkCooked.itemID : Item.porkRaw.itemID;
	}
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = 1;
		int var4;
		float ga = getGrowingAge();
		float ageMod = ga<0 ? 1+(ga/(getAge()*TFC_Time.dayLength)) : 1;

		for (var4 = 0; var4 < var3; ++var4)
		{
			if(ageMod > 0.9){
				this.dropItem(TFCItems.Hide.itemID,1);
			}
		}

		var3 = 1;

		for (var4 = 0; var4 < var3; ++var4)
		{
			if (this.isBurning())
			{

				this.dropItem(Item.porkCooked.itemID, (int) (ageMod*this.size_mod *(10+this.rand.nextInt(8))));
			}
			else
			{
				this.dropItem(Item.porkRaw.itemID, (int) (ageMod*this.size_mod *(10+this.rand.nextInt(8))));
			}
		}
	}

	/**
	 * Returns true if the pig is saddled.
	 */
	@Override
	public boolean getSaddled()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	 * Set or remove the saddle of the pig.
	 */
	@Override
	public void setSaddled(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
		}
		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
		}
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	@Override
	public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
	{
		if (!this.worldObj.isRemote)
		{
			EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
			var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.worldObj.spawnEntityInWorld(var2);
			this.setDead();
		}
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	protected void fall(float par1)
	{
		super.fall(par1);

		if (par1 > 5.0F && this.riddenByEntity instanceof EntityPlayer)
		{
			((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
		}
	}

	@Override
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[this.getEntityData().getInteger("Sex")];
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return new EntityPigTFC(worldObj, this);
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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityPigTFC) {
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
