package TFC.Entities.Mobs;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAIEatGrassTFC;
import TFC.Entities.AI.EntityAIMateTFC;

public class EntityChickenTFC extends EntityAnimal implements IAnimal
{
	public boolean field_753_a = false;
	public float field_752_b = 0.0F;
	public float destPos = 0.0F;
	public float field_757_d;
	public float field_756_e;
	public float field_755_h = 1.0F;

	/** The time until the next egg is spawned. */
	public int timeUntilNextEgg;
	private final EntityAIEatGrassTFC aiEatGrass = new EntityAIEatGrassTFC(this);

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected int age;
	protected boolean pregnant;
	protected int pregnancyTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod;
	public boolean inLove;

	public EntityChickenTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.3F, 0.7F);
		this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
		float var2 = 0.25F;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMateTFC(this, worldObj, var2));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.itemID, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(5, new EntityAIWander(this, var2));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
	}

	public EntityChickenTFC(World world, IAnimal mate)
	{
		this(world);
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
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200);//MaxHealth
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		this.field_756_e = this.field_752_b;
		this.field_757_d = this.destPos;
		this.destPos = (float)(this.destPos + (this.onGround ? -1 : 4) * 0.3D);

		if(pregnant)
		{
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength)
			{
				int i = rand.nextInt(4) + 9;
				for (int x = 0; x<i;x++)
				{
					EntityChickenTFC baby = (EntityChickenTFC) createChild(this);
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

		if (this.destPos < 0.0F)
		{
			this.destPos = 0.0F;
		}

		if (this.destPos > 1.0F)
		{
			this.destPos = 1.0F;
		}

		if (!this.onGround && this.field_755_h < 1.0F)
		{
			this.field_755_h = 1.0F;
		}

		this.field_755_h = (float)(this.field_755_h * 0.9D);

		if (!this.onGround && this.motionY < 0.0D)
		{
			this.motionY *= 0.6D;
		}

		this.field_752_b += this.field_755_h * 2.0F;

		if (isAdult() && getGender() == GenderEnum.FEMALE && !this.worldObj.isRemote && --this.timeUntilNextEgg <= 0)
		{
			this.worldObj.playSoundAtEntity(this, "mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.dropItem(Item.egg.itemID, 1);
			this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
		}
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	protected void fall(float par1) {}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.chicken.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.chicken.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.chicken.hurt";
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return Item.feather.itemID;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{

		this.dropItem(Item.feather.itemID,(int) (this.size_mod * (5+this.rand.nextInt(10))));

		if(isAdult())
		{
			if (this.isBurning())
			{
				this.dropItem(Item.chickenCooked.itemID, 1);
			}
			else
			{
				this.dropItem(Item.chickenRaw.itemID, 1);
			}
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
		return new EntityChickenTFC(worldObj, this);
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
		return 0.5f;
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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityChickenTFC) {
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
