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
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import TFC.TFCItems;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAIMateTFC;

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
	public EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

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

	public EntitySheepTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.9F, 1.3F);
		float var2 = 0.23F;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, new EntityAIMateTFC(this,worldObj, var2));
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, TFCItems.WheatGrain.itemID, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(5, new EntityAIWander(this, var2));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
	}
	public EntitySheepTFC(World par1World,IAnimal mother, float F_size)
	{
		super(par1World);
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + F_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);
	}

	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(400);//MaxHealth
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
		this.sheepTimer = this.aiEatGrass.getEatGrassTick();
		super.updateAITasks();
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

		float negDay = -TFC_Settings.dayLength;
		float ratio = TFC_Time.getYearRatio();
		//float t = (1.0F-(getGrowingAge()/(ratio * adultAge * negDay)));
		//float t = (1.0F-(getGrowingAge()/(-24000*adultAge)));
		if(pregnant){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Settings.dayLength){
				int i = rand.nextInt(3) + 1;
				for (int x = 0; x<i;x++){
					EntitySheepTFC baby = new EntitySheepTFC(worldObj, this,mateSizeMod);
					//giveBirth(baby);
				}
				pregnant = false;
			}
		}

		super.onLivingUpdate();
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		if(this.isAdult()){
			if(!this.getSheared()) {
				this.entityDropItem(new ItemStack(TFCItems.SheepSkin,1), 0.0F);
			} else {
				this.dropItem(TFCItems.Hide.itemID,1);
			}
		}

		if (this.isBurning()) {
			this.dropItem(TFCItems.muttonCooked.itemID,(int)(size_mod*(5+rand.nextInt(5))));
		} else {
			this.dropItem(TFCItems.muttonRaw.itemID,(int)(size_mod*(5+rand.nextInt(5))));
		}
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

		if(getGender() == GenderEnum.FEMALE && isAdult() && hasMilkTime < TFC_Time.getTotalTicks()){
			ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
			if (var2 != null && var2.itemID == TFCItems.WoodenBucketEmpty.itemID) {
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(TFCItems.WoodenBucketMilk));
				hasMilkTime = TFC_Time.getTotalTicks() + (3*TFC_Time.dayLength); //Can be milked ones every 3 days
				return true;
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
		nbt.setBoolean("Sheared", this.getSheared());
		nbt.setByte("Color", (byte)this.getFleeceColor());
		nbt.setInteger ("Sex", sex);
		nbt.setLong ("Animal ID", animalID);
		nbt.setFloat ("Size Modifier", size_mod);
		nbt.setInteger ("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setLong("ConceptionTime",conception);
		nbt.setInteger("Age", getAge());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSheared(nbt.getBoolean("Sheared"));
		this.setFleeceColor(nbt.getByte("Color"));
		animalID = nbt.getLong ("Animal ID");
		sex = nbt.getInteger ("Sex");
		size_mod = nbt.getFloat ("Size Modifier");
		hunger = nbt.getInteger ("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		conception = nbt.getLong("ConceptionTime");
	}


	@Override
	public boolean isShearable(ItemStack item, World world, int X, int Y, int Z) 
	{
		return !getSheared() && isAdult();
	}

	@Override
	public GenderEnum getGender() 
	{
		return GenderEnum.genders[this.getEntityData().getInteger("Sex")];
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return new EntitySheepTFC(worldObj, this, entityageable.getEntityData().getInteger("Size Modifier"));
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
