package TFC.Entities.Mobs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAIMateTFC;

public class EntityDeer extends EntityAnimal implements IAnimal
{    

	/** The eat grass AI task for this mob. */
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	private boolean running;

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected int age;
	protected boolean pregnant;
	protected int pregnancyTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod = 1f;
	public boolean inLove;

	public EntityDeer(World par1World)
	{
		super(par1World);
		running = false;
		this.setSize(0.9F, 1.3F);
		float var2 = 0.23F / 1.1F;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMateTFC(this,worldObj, var2));
		//this.tasks.addTask(3, new EntityAIPanicTFC(this, var2*2, false, true));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 12.0F, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityBear.class, 16f, 0.25F, 0.3F));
		//this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.itemID, false));
		//this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(5, this.aiEatGrass);
		//this.tasks.addTask(5, new EntityAIRutt(this, var2));
		this.tasks.addTask(1, new EntityAIWander(this, var2));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		//this.tasks.addTask(8, new EntityAILookIdle(this));

		int degreeOfDiversion = 1;
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex);
	}
	public EntityDeer(World par1World, IAnimal mother, float F_size)
	{
		this(par1World);
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + F_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);
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
	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	public boolean getRunning(){
		return running;
	}

	public void setRunning(boolean r){
		running = r;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		/*if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime * TFC_Settings.dayLength){
				EntityDeer baby = new EntityDeer(worldObj, this,mateSizeMod);
				giveBirth(baby);
				pregnant = false;
			}
		}*/

		super.onLivingUpdate();
	}

	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(400);//MaxHealth
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte)0));
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return Block.cloth.blockID;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		return super.interact(par1EntityPlayer);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
		par1NBTTagCompound.setByte("Color", (byte)this.getFleeceColor());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
		this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.sheep";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.sheep";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.sheep";
	}

	public int getFleeceColor()
	{
		return this.dataWatcher.getWatchableObjectByte(16) & 15;
	}

	public void setFleeceColor(int par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 240 | par1 & 15)));
	}

	/**
	 * returns true if a sheeps wool has been sheared
	 */
	public boolean getSheared()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
	}

	/**
	 * make a sheep sheared if set to true
	 */
	public void setSheared(boolean par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 16)));
		}
		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -17)));
		}
	}

	/**
	 * This method is called when a sheep spawns in the world to select the color of sheep fleece.
	 */
	public static int getRandomFleeceColor(Random par0Random)
	{
		int var1 = par0Random.nextInt(100);
		return var1 < 5 ? 15 : (var1 < 10 ? 7 : (var1 < 15 ? 8 : (var1 < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 6 : 0))));
	}

	/**
	 * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
	 * function is used in the AIEatGrass)
	 */
	@Override
	public void eatGrassBonus()
	{
		this.setSheared(false);

		if (this.isChild())
		{
			int var1 = this.getGrowingAge() + 1200;

			if (var1 > 0)
			{
				var1 = 0;
			}

			this.setGrowingAge(var1);
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
		return new EntityDeer(worldObj, this, entityageable.getEntityData().getInteger("Size Modifier"));
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
