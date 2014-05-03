package TFC.Entities.Mobs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.Entities.IAnimal;
import TFC.API.Util.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.AIEatGrass;
import TFC.Entities.AI.EntityAIAvoidEntityTFC;
import TFC.Entities.AI.EntityAIMateTFC;
import TFC.Entities.AI.EntityAIPanicTFC;
import TFC.Food.ItemFoodTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityHorseTFC extends EntityHorse implements IInvBasic, IAnimal
{
	private static final IEntitySelector horseBreedingSelector = new EntityHorseBredSelector();
	private static final Attribute horseJumpStrength = (new RangedAttribute("horse.jumpStrengthTFC", 0.7D, 0.0D, 2.0D)).func_111117_a("Jump StrengthTFC").setShouldWatch(true);
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
		animalID = TFC_Time.getTotalTicks() + entityId;
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime =(int)(4 * TFC_Time.ticksInMonth);
		conception = 0;
		mateSizeMod = 0;
		sex = rand.nextInt(2);
		size_mod =(float)Math.sqrt((((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) * 0.1f) + 1F) * (1.0F - 0.1F * sex));
		this.setSize(1.4F, 1.6F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.taskEntries.clear();this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWander(this, 0.7D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIMateTFC(this,this.worldObj, 1.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16f, 0.25F, 0.3F));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.WheatGrain.itemID, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RyeGrain.itemID, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.RiceGrain.itemID, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.BarleyGrain.itemID, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.OatGrain.itemID, false));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(1, new EntityAIPanicTFC(this, 1.2D,true));
		this.func_110226_cD();

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
		size_mod = (float)Math.sqrt((((rand.nextInt ((degreeOfDiversion+1)*10)*(rand.nextBoolean()?1:-1)) / 100f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size)/1.95F));

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
			if(TFC_Time.getTotalTicks() >= conception + pregnancyRequiredTime)
			{
				EntityHorseTFC baby = (EntityHorseTFC) createChildTFC(this);
				baby.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
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
		{
			this.heal(1);
		}
	}

	private float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = (int) TFC_Time.getTotalDays();
		float percent =(time-birth)/animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(!this.worldObj.isRemote){
				this.dataWatcher.updateObject(13, Integer.valueOf(sex));
				this.dataWatcher.updateObject(14, Float.valueOf(size_mod));

				this.dataWatcher.updateObject(24, Float.valueOf(strength_mod));
				this.dataWatcher.updateObject(25, Float.valueOf(aggression_mod));
				this.dataWatcher.updateObject(26, Float.valueOf(obedience_mod));
				this.dataWatcher.updateObject(27, Float.valueOf(colour_mod));
				this.dataWatcher.updateObject(28, Float.valueOf(climate_mod));
				this.dataWatcher.updateObject(29, Float.valueOf(hard_mod));
			}
			else{
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

	private void func_110226_cD()
	{
		AnimalChest animalchest = this.horseChest;
		this.horseChest = new AnimalChest("HorseChest", this.func_110225_cC());
		this.horseChest.func_110133_a(this.getEntityName());

		if (animalchest != null)
		{
			animalchest.func_110132_b(this);
			int i = Math.min(animalchest.getSizeInventory(), this.horseChest.getSizeInventory());

			for (int j = 0; j < i; ++j)
			{
				ItemStack itemstack = animalchest.getStackInSlot(j);

				if (itemstack != null)
				{
					this.horseChest.setInventorySlotContents(j, itemstack.copy());
				}
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
			{
				this.func_110236_r(this.getHorseArmorIndex(this.horseChest.getStackInSlot(1)));
			}
		}
	}

	@Override
	protected EntityHorseTFC getClosestHorse(Entity par1Entity, double par2)
	{
		double d1 = Double.MAX_VALUE;
		Entity entity1 = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par1Entity.boundingBox.addCoord(par2, par2, par2), horseBreedingSelector);
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			Entity entity2 = (Entity)iterator.next();
			double d2 = entity2.getDistanceSq(par1Entity.posX, par1Entity.posY, par1Entity.posZ);

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
		this.getAttributeMap().func_111150_b(horseJumpStrength);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1250);//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.22499999403953552D);
	}

	@Override
	public void openGUI(EntityPlayer par1EntityPlayer)
	{
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer) && this.isTame())
		{
			this.horseChest.func_110133_a(this.getEntityName());
			//par1EntityPlayer.displayGUIHorse(this, this.horseChest);
			par1EntityPlayer.openGui(TerraFirmaCraft.instance, 42, par1EntityPlayer.worldObj, (int)par1EntityPlayer.posX, (int)par1EntityPlayer.posY, (int)par1EntityPlayer.posZ);
		}
	}

	public AnimalChest getHorseChest(){
		return this.horseChest;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		if(!worldObj.isRemote){
			par1EntityPlayer.addChatMessage(getGender()==GenderEnum.FEMALE?"Female":"Male");
			if(getGender()==GenderEnum.FEMALE && pregnant){
				par1EntityPlayer.addChatMessage("Pregnant");
			}
			//player.addChatMessage("12: "+dataWatcher.getWatchableObjectInt(12)+", 15: "+dataWatcher.getWatchableObjectInt(15));
		}

		if (itemstack != null && this.isBreedingItem(itemstack) && this.getGrowingAge() == 0 && this.inLove <= 0)
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;

				if (itemstack.stackSize <= 0)
				{
					par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
				}
			}

			this.func_110196_bT();
			return true;
		}
		else
		{
			//return super.interact(par1EntityPlayer);
		}

		if (itemstack != null && itemstack.itemID == Item.monsterPlacer.itemID)
		{
			return super.interact(par1EntityPlayer);
		}
		else if (!this.isTame() && this.func_110256_cu())
		{
			return false;
		}
		else if (this.isTame() && this.isAdultHorse() && par1EntityPlayer.isSneaking() && !this.getLeashed())
		{
			this.openGUI(par1EntityPlayer);
			return true;
		}
		else if (this.isTame() && this.isAdultHorse() && par1EntityPlayer.isSneaking() && this.getLeashed())
		{
			this.clearLeashed(true, true);
			return true;
		}
		else if (this.func_110253_bW() && this.riddenByEntity != null)
		{
			return super.interact(par1EntityPlayer);
		}
		else
		{
			if (itemstack != null)
            {
                boolean flag = false;

                if (this.func_110259_cr())
                {
                    byte b0 = -1;

                    if (itemstack.itemID == Item.horseArmorIron.itemID)
                    {
                        b0 = 1;
                    }
                    else if (itemstack.itemID == Item.horseArmorGold.itemID)
                    {
                        b0 = 2;
                    }
                    else if (itemstack.itemID == Item.horseArmorDiamond.itemID)
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

                        this.openGUI(par1EntityPlayer);
                        return true;
                    }
                }

                if (!this.isTame() && !flag)
                {
                    if (itemstack != null && itemstack.func_111282_a(par1EntityPlayer, this))
                    {
                        return true;
                    }

                    this.makeHorseRearWithSound();
                    return true;
                }

                if (!flag && this.func_110229_cs() && !this.isChested() && itemstack.itemID == Block.chest.blockID)
                {
                    this.setChested(true);
                    this.playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                    flag = true;
                    this.func_110226_cD();
                }

                if (!flag && this.func_110253_bW() && !this.isHorseSaddled() && itemstack.itemID == Item.saddle.itemID)
                {
                    this.openGUI(par1EntityPlayer);
                    return true;
                }

                if (flag)
                {
                    if (!par1EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize == 0)
                    {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }

                    return true;
                }
            }
			if (this.func_110253_bW() && this.riddenByEntity == null)
			{
				if (itemstack != null && itemstack.func_111282_a(par1EntityPlayer, this))
				{
					return true;
				}
				else
				{

					if(this.getLeashedToEntity() != null && this.getLeashedToEntity() instanceof EntityPlayer &&
							this.getLeashedToEntity() == par1EntityPlayer){
						this.func_110237_h(par1EntityPlayer);
					}
					return true;
				}
			}
		}

		if (itemstack != null && this.isBreedingItemTFC(itemstack) && this.getGrowingAge() == 0 && super.inLove <= 0)
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem,(((ItemFoodTFC)itemstack.getItem()).onConsumedByEntity(par1EntityPlayer.getHeldItem(), worldObj, this)));
			}

			this.func_110196_bT();
			return true;
		}
		else
		{
			return super.interact(par1EntityPlayer);
		}
	}

	//We use this to catch the EntityLiving check, so that other interactions can be performed on leashed animals
	@Override
	public boolean getLeashed(){
		if(super.getLeashed() && getLeashedToEntity() instanceof EntityPlayer && 
				((EntityPlayer)getLeashedToEntity()).inventory.getCurrentItem() == null && func_110174_bM() != -1){
			return false;
		}
		return super.getLeashed();
	}

	@Override
	public void clearLeashed(boolean par1, boolean par2)
	{
		Entity entity = getLeashedToEntity();
		if(entity!= null && entity instanceof EntityPlayer){
			ItemStack item = ((EntityPlayer)entity).inventory.getCurrentItem();
			if(entity.isSneaking()){
				super.clearLeashed(par1, par2);
			}
		}
		else{
			super.clearLeashed(par1, par2);
		}
	}

	private void func_110237_h(EntityPlayer par1EntityPlayer)
	{
		par1EntityPlayer.rotationYaw = this.rotationYaw;
		par1EntityPlayer.rotationPitch = this.rotationPitch;
		this.setEatingHaystack(false);
		this.setRearing(false);

		if (!this.worldObj.isRemote)
		{
			par1EntityPlayer.mountEntity(this);
		}
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		this.entityDropItem(new ItemStack(TFCItems.Hide.itemID,1,Math.max(0,Math.min(2,(int)(ageMod*3-1)))),0);
		this.dropItem(Item.bone.itemID,(int) ((rand.nextInt(8)+3)*ageMod));

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
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger ("Sex", sex);
		par1NBTTagCompound.setLong ("Animal ID", animalID);
		par1NBTTagCompound.setFloat ("Size Modifier", size_mod);


		NBTTagCompound nbt = par1NBTTagCompound;
		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		nbt.setFloat ("Colour Modifier", colour_mod);
		nbt.setFloat ("Climate Adaptation Modifier", climate_mod);
		nbt.setFloat ("Hardiness Modifier", hard_mod);

		par1NBTTagCompound.setInteger ("Hunger", hunger);
		par1NBTTagCompound.setBoolean("Pregnant", pregnant);
		par1NBTTagCompound.setFloat("MateSize", mateSizeMod);
		par1NBTTagCompound.setLong("ConceptionTime",conception);
		par1NBTTagCompound.setInteger("Age", getBirthDay());
		
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

            par1NBTTagCompound.setTag("Items", nbttaglist);
        }

        if (this.horseChest.getStackInSlot(1) != null)
        {
            par1NBTTagCompound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound("ArmorItem")));
        }

        if (this.horseChest.getStackInSlot(0) != null)
        {
            par1NBTTagCompound.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound("SaddleItem")));
        }
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		NBTTagCompound nbt = par1NBTTagCompound;
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
            NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
            this.func_110226_cD();

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 255;

                if (j >= 2 && j < this.horseChest.getSizeInventory())
                {
                    this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }

        ItemStack itemstack;

        if (par1NBTTagCompound.hasKey("ArmorItem"))
        {
            itemstack = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("ArmorItem"));

            if (itemstack != null && func_110211_v(itemstack.itemID))
            {
                this.horseChest.setInventorySlotContents(1, itemstack);
            }
        }

        if (par1NBTTagCompound.hasKey("SaddleItem"))
        {
            itemstack = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("SaddleItem"));

            if (itemstack != null && itemstack.itemID == Item.saddle.itemID)
            {
                this.horseChest.setInventorySlotContents(0, itemstack);
            }
        }
        else if (par1NBTTagCompound.getBoolean("Saddle"))
        {
            this.horseChest.setInventorySlotContents(0, new ItemStack(Item.saddle));
        }

        this.func_110232_cE();
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		else if (par1EntityAnimal.getClass() != this.getClass())
		{
			return false;
		}
		else
		{
			EntityHorseTFC entityhorse = (EntityHorseTFC)par1EntityAnimal;

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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityHorseTFC && this.isAdult()) {
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
		return isInLove;
	}

	@Override
	public void setInLove(boolean b) 
	{
		this.isInLove = b;
	}

	@Override
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("horse");
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
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	public boolean isBreedingItemTFC(ItemStack par1ItemStack)
	{
		return !pregnant&&(par1ItemStack.getItem() == TFCItems.WheatGrain ||par1ItemStack.getItem() == TFCItems.OatGrain||par1ItemStack.getItem() == TFCItems.RiceGrain||
				par1ItemStack.getItem() == TFCItems.BarleyGrain||par1ItemStack.getItem() == TFCItems.RyeGrain);
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if(!TFC_Core.PreventEntityDataUpdate) {
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
	public int getSex(){
		return dataWatcher.getWatchableObjectInt(13);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		return null;
	}


	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable) 
	{
		EntityHorseTFC entityhorse = (EntityHorseTFC)entityageable;
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(entityageable.getEntityData().getFloat("MateSize"));
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
		double d0 = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + entityageable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + this.func_110267_cL();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(d0 / 3.0D);
		double d1 = this.getEntityAttribute(horseJumpStrength).getBaseValue() + entityageable.getEntityAttribute(horseJumpStrength).getBaseValue() + this.func_110245_cM();
		entityhorse1.getEntityAttribute(horseJumpStrength).setAttribute(d1 / 3.0D);
		double d2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + entityageable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.func_110203_cN();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(d2 / 3.0D);
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
	public EntityLiving getEntity() {
		return this;
	}

	@Override
	public float getStrength() {
		return this.getDataWatcher().getWatchableObjectFloat(24);
	}


	@Override
	public float getAggression() {
		return this.getDataWatcher().getWatchableObjectFloat(25);
	}


	@Override
	public float getObedience() {
		return this.getDataWatcher().getWatchableObjectFloat(26);
	}


	@Override
	public float getColour() {
		return this.getDataWatcher().getWatchableObjectFloat(27);
	}


	@Override
	public float getClimateAdaptation() {
		return this.getDataWatcher().getWatchableObjectFloat(28);
	}


	@Override
	public float getHardiness() {
		return this.getDataWatcher().getWatchableObjectFloat(29);
	}

	@Override
	public Vec3 getAttackedVec() {
		return this.attackedVec;
	}

	@Override
	public void setAttackedVec(Vec3 attackedVec) {
		this.attackedVec = attackedVec;
	}

	@Override
	public Entity getFearSource() {
		return this.fearSource;
	}

	@Override
	public void setFearSource(Entity fearSource) {
		this.fearSource = fearSource;
	}
}
