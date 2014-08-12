package com.bioxx.tfc.Entities.Mobs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.AI.AIEatGrass;
import com.bioxx.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.bioxx.tfc.Entities.AI.EntityAIMateTFC;
import com.bioxx.tfc.Entities.AI.EntityAIPanicTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Util.Helper;

public class EntityHorseTFC extends EntityHorse implements IInvBasic, IAnimal
{
	private static final IEntitySelector horseBreedingSelector = new EntityHorseBredSelector();
	private static final IAttribute horseJumpStrength = (new RangedAttribute("horse.jumpStrengthTFC", 0.7D, 0.0D, 2.0D)).setDescription("Jump StrengthTFC").setShouldWatch(true);
	private AnimalChest horseChest;

	public int inLove;

	private final AIEatGrass aiEatGrass = new AIEatGrass(this);
	protected long animalID;
	protected int sex = 0;
	protected int hunger = 0;
	protected boolean pregnant;
	protected int pregnancyRequiredTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod;
	public float strength_mod = 1;
	public float aggression_mod = 1;
	public float obedience_mod = 1;
	public float colour_mod = 1;
	public float climate_mod = 1;
	public float hard_mod = 1;
	public boolean isInLove;
	public Vec3 attackedVec = null;
	public Entity fearSource = null;

	int degreeOfDiversion = 2;

	private String field_110286_bQ;
	private String[] field_110280_bR = new String[3];


	public EntityHorseTFC(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime =(int)(4 * TFC_Time.ticksInMonth);
		conception = 0;
		mateSizeMod = 0;
		sex = rand.nextInt(2);
		size_mod =(float)Math.sqrt((((rand.nextInt (degreeOfDiversion + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.1f) + 1F) * (1.0F - 0.1F * sex));
		this.setSize(1.4F, 1.6F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWander(this, 0.7D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 1.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16f, 0.25F, 0.3F));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RyeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RiceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.BarleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.OatGrain, false));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(1, new EntityAIPanicTFC(this, 1.2D,true));
		this.updateChestSaddle();

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		//this.setGrowingAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		this.setAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		//For Testing Only(makes spawned animals into babies)
		//this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	public EntityHorseTFC(World par1World, IAnimal mother,  ArrayList<Float> data)
	{
		this(par1World);
		float father_size = data.get(0);
		this.posX = ((EntityLivingBase)mother).posX;
		this.posY = ((EntityLivingBase)mother).posY;
		this.posZ = ((EntityLivingBase)mother).posZ;
		size_mod = (float)Math.sqrt((((rand.nextInt ((degreeOfDiversion + 1) * 10) * (rand.nextBoolean() ? 1 : -1)) / 100f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size) / 1.95F));

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}

	@Override
	public void onLivingUpdate()
	{
		//Handle Hunger ticking
		if (hunger > 168000)
			hunger = 168000;
		if (hunger > 0)
			hunger--;

		syncData();
		if(isAdult())
			setGrowingAge(0);
		else
			setGrowingAge(-1);

		if(isPregnant())
		{
			if(TFC_Time.getTotalTicks() >= conception + pregnancyRequiredTime)
			{
				EntityHorseTFC baby = (EntityHorseTFC) createChildTFC(this);
				baby.setLocationAndAngles (posX + (rand.nextFloat() - 0.5F) * 2F, posY, posZ + (rand.nextFloat() - 0.5F) * 2F, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				worldObj.spawnEntityInWorld(baby);
				baby.setAge((int)TFC_Time.getTotalDays());
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
			this.heal(1);
	}

	private float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = (int) TFC_Time.getTotalDays();
		float percent =(time - birth) / animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
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

	private int func_110225_cC()
	{
		int i = this.getHorseType();
		return this.isChested() && (i == 1 || i == 2) ? 17 : 2;
	}

	public void updateChestSaddle()
	{
		AnimalChest animalchest = this.horseChest;
		this.horseChest = new AnimalChest("HorseChest", this.func_110225_cC());
		this.horseChest.func_110133_a(this.getCommandSenderName());

		if (animalchest != null)
		{
			animalchest.func_110132_b(this);
			int i = Math.min(animalchest.getSizeInventory(), this.horseChest.getSizeInventory());

			for (int j = 0; j < i; ++j)
			{
				ItemStack itemstack = animalchest.getStackInSlot(j);
				if (itemstack != null)
					this.horseChest.setInventorySlotContents(j, itemstack.copy());
			}

			animalchest = null;
		}

		this.horseChest.func_110134_a(this);
		this.func_110232_cE();
	}

	private void func_110232_cE()
	{
		if (!this.worldObj.isRemote)
		{
			this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
			if (this.func_110259_cr())
				this.func_146086_d(this.horseChest.getStackInSlot(1));
		}
	}

	@Override
	protected EntityHorseTFC getClosestHorse(Entity entity, double par2)
	{
		double d1 = Double.MAX_VALUE;
		Entity entity1 = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(par2, par2, par2), horseBreedingSelector);
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			Entity entity2 = (Entity)iterator.next();
			double d2 = entity2.getDistanceSq(entity.posX, entity.posY, entity.posZ);
			if (d2 < d1)
			{
				entity1 = entity2;
				d1 = d2;
			}
		}

		return (EntityHorseTFC)entity1;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(horseJumpStrength);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250);//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D);
		this.setHealth(this.getMaxHealth());
	}

	@Override
	public void openGUI(EntityPlayer player)
	{
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player) && this.isTame())
		{
			this.horseChest.func_110133_a(this.getCommandSenderName());
			//player.displayGUIHorse(this, this.horseChest);
			player.openGui(TerraFirmaCraft.instance, 42, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
	}

	public AnimalChest getHorseChest()
	{
		return this.horseChest;
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
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(getGender() == GenderEnum.FEMALE ? "entity.animal.female" : "entity.animal.male")));
			if(getGender()==GenderEnum.FEMALE && pregnant)
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("entity.animal.pregnant")));
			//player.addChatMessage("12: " + dataWatcher.getWatchableObjectInt(12) + ", 15: " + dataWatcher.getWatchableObjectInt(15));
		}

		if (itemstack != null && this.isBreedingItemTFC(itemstack) && this.getGrowingAge() == 0 && !super.isInLove())
		{
			if (!player.capabilities.isCreativeMode)
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (((ItemFoodTFC)itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this)));

			this.setInLove(true);
			return true;
		}

		if (itemstack != null && itemstack.getItem() == Items.spawn_egg)
		{
			return super.interact(player);
		}
		else if (!this.isTame() && this.func_110256_cu())
		{
			return false;
		}
		else if (this.isTame() && this.isAdultHorse() && player.isSneaking() && !this.getLeashed())
		{
			this.openGUI(player);
			return true;
		}
		else if (this.isTame() && this.isAdultHorse() && player.isSneaking() && this.getLeashed())
		{
			this.clearLeashed(true, true);
			return true;
		}
		else if (this.func_110253_bW() && this.riddenByEntity != null)
		{
			return super.interact(player);
		}
		else
		{
			if (itemstack != null)
			{
				boolean flag = false;

				if (this.func_110259_cr())
				{
					byte b0 = -1;

					if (itemstack.getItem() == Items.iron_horse_armor)
					{
						b0 = 1;
					}
					else if (itemstack.getItem() == Items.golden_horse_armor)
					{
						b0 = 2;
					}
					else if (itemstack.getItem() == Items.diamond_horse_armor)
					{
						b0 = 3;
					}

					if (b0 >= 0)
					{
						if (!this.isTame())
						{
							this.makeHorseRearWithSound();
							return true;
						}

						this.openGUI(player);
						return true;
					}
				}

				if (!this.isTame() && !flag)
				{
					if (itemstack != null && itemstack.interactWithEntity(player, this))
					{
						return true;
					}

					this.makeHorseRearWithSound();
				}

				if (!flag && this.func_110229_cs() && !this.isChested() && itemstack.getItem() == Item.getItemFromBlock(Blocks.chest))
				{
					this.setChested(true);
					this.playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					flag = true;
					this.updateChestSaddle();
				}

				if (!flag && this.func_110253_bW() && !this.isHorseSaddled() && itemstack.getItem() == Items.saddle)
				{
					this.openGUI(player);
					return true;
				}

				if (flag)
				{
					if (!player.capabilities.isCreativeMode && --itemstack.stackSize == 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
					}

					return true;
				}
			}
			if (this.func_110253_bW() && this.riddenByEntity == null)
			{
				if (itemstack != null && itemstack.interactWithEntity(player, this))
				{
					return true;
				}
				else
				{

					if(this.getLeashedToEntity() != null &&
							this.getLeashedToEntity() instanceof EntityPlayer &&
							this.getLeashedToEntity() == player)
					{
						this.func_110237_h(player);
					}
					return true;
				}
			}
		}

		return super.interact(player);
	}

	//We use this to catch the EntityLiving check, so that other interactions can be performed on leashed animals
	@Override
	public boolean getLeashed()
	{
		if(super.getLeashed() && getLeashedToEntity() instanceof EntityPlayer &&
				((EntityPlayer)getLeashedToEntity()).inventory.getCurrentItem() == null && func_110174_bM() != -1)
		{
			return false;
		}
		return super.getLeashed();
	}

	@Override
	public void clearLeashed(boolean par1, boolean par2)
	{
		Entity entity = getLeashedToEntity();
		if(entity!= null && entity instanceof EntityPlayer)
		{
			ItemStack item = ((EntityPlayer)entity).inventory.getCurrentItem();
			if(entity.isSneaking())
				super.clearLeashed(par1, par2);
		}
		else
		{
			super.clearLeashed(par1, par2);
		}
	}

	private void func_110237_h(EntityPlayer player)
	{
		player.rotationYaw = this.rotationYaw;
		player.rotationPitch = this.rotationPitch;
		this.setEatingHaystack(false);
		this.setRearing(false);

		if (!this.worldObj.isRemote)
			player.mountEntity(this);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		this.entityDropItem(new ItemStack(TFCItems.Hide, 1, Math.max(0, Math.min(2, (int)(ageMod * 3 - 1)))), 0);
		this.dropItem(Items.bone, (int) ((rand.nextInt(8) + 3) * ageMod));

		float foodWeight = ageMod*(this.size_mod * 4000);//528 oz (33lbs) is the average yield of lamb after slaughter and processing

		TFC_Core.animalDropMeat(this, TFCItems.horseMeatRaw, foodWeight);
	}

	private boolean func_110200_cJ()
	{
		return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && !this.func_110222_cv() && this.getHealth() >= this.getMaxHealth();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttc)
	{
		super.writeEntityToNBT(nbttc);
		nbttc.setInteger ("Sex", sex);
		nbttc.setLong ("Animal ID", animalID);
		nbttc.setFloat ("Size Modifier", size_mod);


		NBTTagCompound nbt = nbttc;
		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		nbt.setFloat ("Colour Modifier", colour_mod);
		nbt.setFloat ("Climate Adaptation Modifier", climate_mod);
		nbt.setFloat ("Hardiness Modifier", hard_mod);

		nbttc.setInteger ("Hunger", hunger);
		nbttc.setBoolean("Pregnant", pregnant);
		nbttc.setFloat("MateSize", mateSizeMod);
		nbttc.setLong("ConceptionTime",conception);
		nbttc.setInteger("Age", getBirthDay());

		if (this.isChested())
		{
			NBTTagList nbttaglist = new NBTTagList();

			for (int i = 2; i < this.horseChest.getSizeInventory(); ++i)
			{
				ItemStack itemstack = this.horseChest.getStackInSlot(i);

				if (itemstack != null)
				{
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte)i);
					itemstack.writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}

			nbttc.setTag("Items", nbttaglist);
		}

		if (this.horseChest.getStackInSlot(1) != null)
		{
			nbttc.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
		}

		if (this.horseChest.getStackInSlot(0) != null)
		{
			nbttc.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttc)
	{
		super.readEntityFromNBT(nbttc);
		NBTTagCompound nbt = nbttc;
		animalID = nbt.getLong ("Animal ID");
		sex = nbt.getInteger ("Sex");
		size_mod = nbt.getFloat ("Size Modifier");

		strength_mod = nbt.getFloat ("Strength Modifier");
		aggression_mod = nbt.getFloat ("Aggression Modifier");
		obedience_mod = nbt.getFloat ("Obedience Modifier");
		colour_mod = nbt.getFloat ("Colour Modifier");
		climate_mod = nbt.getFloat ("Climate Adaptation Modifier");
		hard_mod = nbt.getFloat ("Hardiness Modifier");

		hunger = nbt.getInteger ("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		conception = nbt.getLong("ConceptionTime");
		this.setAge(nbt.getInteger ("Age"));

		if (this.isChested())
		{
			NBTTagList nbttaglist = nbttc.getTagList("Items", 10);
			this.updateChestSaddle();

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
				int j = nbt1.getByte("Slot") & 255;

				if (j >= 2 && j < this.horseChest.getSizeInventory())
				{
					this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbt1));
				}
			}
		}

		ItemStack itemstack;

		if (nbttc.hasKey("ArmorItem"))
		{
			itemstack = ItemStack.loadItemStackFromNBT(nbttc.getCompoundTag("ArmorItem"));

			if (itemstack != null && func_146085_a(itemstack.getItem()))
			{
				this.horseChest.setInventorySlotContents(1, itemstack);
			}
		}

		if (nbttc.hasKey("SaddleItem"))
		{
			itemstack = ItemStack.loadItemStackFromNBT(nbttc.getCompoundTag("SaddleItem"));

			if (itemstack != null && itemstack.getItem() == Items.saddle)
			{
				this.horseChest.setInventorySlotContents(0, itemstack);
			}
		}
		else if (nbttc.getBoolean("Saddle"))
		{
			this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
		}

		this.func_110232_cE();
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal animal)
	{
		if (animal == this)
		{
			return false;
		}
		else if (animal.getClass() != this.getClass())
		{
			return false;
		}
		else
		{
			EntityHorseTFC entityhorse = (EntityHorseTFC) animal;

			if (this.func_110200_cJ() && entityhorse.func_110200_cJ())
			{
				int i = this.getHorseType();
				int j = entityhorse.getHorseType();
				return i == j || i == 0 && j == 1 || i == 1 && j == 0;
			}
			else
			{
				return false;
			}
		}
	}

	private float func_110267_cL()
	{
		return 1000 + (float)this.rand.nextInt(101) + this.rand.nextInt(151);
	}

	private double func_110245_cM()
	{
		return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
	}

	private double func_110203_cN()
	{
		return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityHorseTFC && this.isAdult())
			return true;
		else
			return false;
	}

	@Override
	public void mate(IAnimal otherAnimal)
	{
		if (getGender() == GenderEnum.MALE)
		{
			otherAnimal.mate(this);
			setInLove(false);
			resetInLove();
			return;
		}
		conception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		setInLove(false);
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSize();
	}

	@Override
	public boolean getInLove()
	{
		return isInLove;
	}

	@Override
	public void setInLove(boolean b)
	{
		this.isInLove = b;
		if(b)
		{
			this.entityToAttack = null;
			this.worldObj.setEntityState(this, (byte)18);
		}
	}

	@Override
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("horse");
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
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	public boolean isBreedingItemTFC(ItemStack par1ItemStack)
	{
		return !pregnant && (
				par1ItemStack.getItem() == TFCItems.WheatGrain ||
				par1ItemStack.getItem() == TFCItems.OatGrain ||
				par1ItemStack.getItem() == TFCItems.RiceGrain ||
				par1ItemStack.getItem() == TFCItems.BarleyGrain ||
				par1ItemStack.getItem() == TFCItems.RyeGrain);
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
	public void setAge(int par1)
	{
		//if(!TFC_Core.PreventEntityDataUpdate) {
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
		//}
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
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
	public EntityAgeable createChild(EntityAgeable eAgeable)
	{
		return null;
	}


	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		EntityHorseTFC entityhorse = (EntityHorseTFC) eAgeable;
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		EntityHorseTFC entityhorse1 = new EntityHorseTFC(worldObj, this, data);
		int i = this.getHorseType();
		int j = entityhorse.getHorseType();
		int k = 0;

		if (i == j)
		{
			k = i;
		}
		else if (i == 0 && j == 1 || i == 1 && j == 0)
		{
			k = 2;
		}

		if (k == 0)
		{
			int l = this.rand.nextInt(9);
			int i1;

			if (l < 4)
			{
				i1 = this.getHorseVariant() & 255;
			}
			else if (l < 8)
			{
				i1 = entityhorse.getHorseVariant() & 255;
			}
			else
			{
				i1 = this.rand.nextInt(7);
			}

			int j1 = this.rand.nextInt(5);

			if (j1 < 4)
			{
				i1 |= this.getHorseVariant() & 65280;
			}
			else if (j1 < 8)
			{
				i1 |= entityhorse.getHorseVariant() & 65280;
			}
			else
			{
				i1 |= this.rand.nextInt(5) << 8 & 65280;
			}

			entityhorse1.setHorseVariant(i1);
		}

		entityhorse1.setHorseType(k);
		double d0 = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + eAgeable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + this.func_110267_cL();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(d0 / 3.0D);
		double d1 = this.getEntityAttribute(horseJumpStrength).getBaseValue() + eAgeable.getEntityAttribute(horseJumpStrength).getBaseValue() + this.func_110245_cM();
		entityhorse1.getEntityAttribute(horseJumpStrength).setBaseValue(d1 / 3.0D);
		double d2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + eAgeable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.func_110203_cN();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(d2 / 3.0D);
		return entityhorse1;
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
		return this.attackedVec;
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
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData livingData)
	{
		IEntityLivingData data = super.onSpawnWithEgg(livingData);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1250);
		this.heal(1250);
		return data;
	}
}
