package TFC.Entities.Mobs;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.ICausesDamage;
import TFC.API.IInnateArmor;
import TFC.API.Entities.IAnimal;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_MobData;
import TFC.Core.TFC_Time;

public class EntityBear extends EntityTameable implements ICausesDamage, IAnimal, IInnateArmor
{
	/**
	 * This flag is set when the bear is looking at a player with interest, i.e. with tilted head. This happens when
	 * tamed wolf is wound and player holds porkchop (raw or cooked), or when wild wolf sees bone in player's hands.
	 */
	private float field_25048_b;
	private float field_25054_c;
	private Random rand = new Random ();
	private float moveSpeed = 0;

	/** true is the wolf is wet else false */
	private boolean field_25052_g;

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected int age;
	protected boolean pregnant;
	protected int pregnancyTime;
	protected long conception;
	protected float mateSizeMod;
	public float size_mod;
	public float strength_mod = 1;
	public float aggression_mod = 1;
	public float obedience_mod = 1;
	public float colour_mod = 1;
	public float climate_mod = 1;
	public float hard_mod = 1;
	public boolean inLove;


	public EntityBear (World par1World)
	{
		super (par1World);
		setSize (1.2F, 1.2F);
		moveSpeed = 0.2F;
		getNavigator ().setAvoidsWater (true);
		tasks.addTask (1, new EntityAISwimming (this));
		tasks.addTask (4, new EntityAIAttackOnCollide (this, moveSpeed * 1.5F, true));
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex);
		if (getGender() == GenderEnum.MALE)
		{
			tasks.addTask (6, new EntityAIMate (this, moveSpeed));
		}
		tasks.addTask (7, new EntityAIWander (this, moveSpeed));
		tasks.addTask (8, new EntityAIWatchClosest (this, EntityPlayer.class, 8F));
		tasks.addTask (9, new EntityAILookIdle (this));
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntitySheepTFC.class,200, false));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntityDeer.class,200, false));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntityPigTFC.class, 200, false));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntityPlayer.class, 200, false));
		targetTasks.addTask (4, new EntityAITargetNonTamed(this, EntityHorseTFC.class, 200, false));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAIPanic(this,moveSpeed*1.5F));

		mateSizeMod = 1f;
		/*fooditems.add(Item.beefRaw.itemID);
		fooditems.add(Item.porkRaw.itemID);
		fooditems.add(Item.fishRaw.itemID);*/

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event. We want spawned
		//	animals to be adults, so we set their birthdays far enough back
		//	in time such that they reach adulthood now.
		//
		this.setAge((int) TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
	}


	public EntityBear (World par1World, IAnimal mother, ArrayList<Float> data)
	{
		this(par1World);
		float father_size = data.get(0);
		size_mod = (((rand.nextInt (4+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.getSize() + father_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);

		//	We hijack the growingAge to hold the day of birth rather
		//	than number of ticks to next growth event.
		//
		this.setAge((int) TFC_Time.getTotalDays());
	}


	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled ()
	{
		return true;
	}

	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITick ()
	{
		dataWatcher.updateObject (18, getHealth());
	}

	@Override
	protected void entityInit ()
	{
		super.entityInit ();
		dataWatcher.addObject (18, getHealth());
		this.dataWatcher.addObject(13, new Integer(0));
		this.dataWatcher.addObject(14, new Float(1));
		this.dataWatcher.addObject(15, Integer.valueOf(0));

		this.dataWatcher.addObject(24, new Float(1));
		this.dataWatcher.addObject(25, new Float(1));
		this.dataWatcher.addObject(26, new Float(1));
		this.dataWatcher.addObject(27, new Float(1));
		this.dataWatcher.addObject(28, new Float(1));
		this.dataWatcher.addObject(29, new Float(1));
	}


	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.BearHealth);//MaxHealth
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking ()
	{
		return true;
	}


	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT (NBTTagCompound nbt)
	{
		super.writeEntityToNBT (nbt);
		nbt.setInteger ("Sex", sex);
		nbt.setLong ("Animal ID", animalID);
		nbt.setFloat ("Size Modifier", size_mod);

		nbt.setFloat ("Strength Modifier", strength_mod);
		nbt.setFloat ("Aggression Modifier", aggression_mod);
		nbt.setFloat ("Obedience Modifier", obedience_mod);
		nbt.setFloat ("Colour Modifier", colour_mod);
		nbt.setFloat ("Climate Adaptation Modifier", climate_mod);
		nbt.setFloat ("Hardiness Modifier", hard_mod);

		nbt.setInteger ("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setLong("ConceptionTime",conception);
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
		this.dataWatcher.updateObject(15, nbt.getInteger ("Age"));
	}


	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn ()
	{
		return true;
	}


	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound ()
	{
		return "mob.wolf.growl";
	}


	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound ()
	{
		if(!isChild()){
			return "mob.wolf.growl";
		}
		else{
			return "mob.wolf.whine";
		}
	}


	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound ()
	{
		return "mob.wolf.death";
	}


	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume ()
	{
		return 0.4F;
	}


	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId ()
	{
		return -1;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		this.entityDropItem(new ItemStack(TFCItems.Hide.itemID,1,Math.max(0,Math.min(2,(int)(ageMod*3-1))),0);
		this.dropItem(Item.bone.itemID,(int) ((rand.nextInt(6)+2)*ageMod));
	}


	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate ()
	{
		TFC_Core.PreventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.PreventEntityDataUpdate = false;
		//		float t = (1.0F-(getGrowingAge()/(TFC_Time.getYearRatio() * adultAge * -TFC_Settings.dayLength)));
		if (!worldObj.isRemote && !field_25052_g && !hasPath () && onGround)
		{
			field_25052_g = true;
			worldObj.setEntityState (this, (byte) 8);
		}
		if(this.isPregnant()){
			if(TFC_Time.getTotalTicks() >= conception + pregnancyTime*TFC_Time.dayLength){
				int i = rand.nextInt(3) + 1;
				for (int x = 0; x<i;x++){
					ArrayList<Float> data = new ArrayList<Float>();
					data.add(mateSizeMod);
					EntityBear baby = new EntityBear(worldObj, this,data);
					worldObj.spawnEntityInWorld(baby);
				}
				pregnant = false;
			}
		}



		/*if (TFC_Time.getTotalTicks() == birthTime + 60 && this instanceof EntityBear && this.sex == 1&& rand.nextInt(10) == 0 && getGrowingAge() >= 0){
			int i = rand.nextInt(3);
			if (mateSizeMod == 0){
				this.mateSizeMod = ((rand.nextInt (5) - 2) / 10f) + 1F;
			}
			for (int x = 0; x<i;x++){
				giveBirth(new EntityBear(this.worldObj,this,this.mateSizeMod));
			}
		}*/
	}


	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate ()
	{
		super.onUpdate ();
		field_25054_c = field_25048_b;
		field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
	}

	@Override
	public float getEyeHeight ()
	{
		return height * 0.8F;
	}

	@Override
	public boolean attackEntityAsMob (Entity par1Entity)
	{
		int dam =  (int)(TFC_MobData.BearDamage * getStrength() * getAggression() * (getSize()/2 + 0.5F));
		return par1Entity.attackEntityFrom (DamageSource.causeMobDamage (this), dam);
	}

	@Override
	public void handleHealthUpdate (byte par1)
	{
		if (par1 == 8)
		{
			field_25052_g = true;
		}
		else
		{
			super.handleHealthUpdate (par1);
		}
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


	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk ()
	{
		return 2;
	}

	@Override
	public boolean canMateWith (EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		if (!(par1EntityAnimal instanceof EntityBear))
		{
			return false;
		}
		EntityBear entitybear = (EntityBear) par1EntityAnimal;
		return getInLove () && entitybear.getInLove ();
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
	public EnumDamageType GetDamageType() {
		return EnumDamageType.SLASHING;
	}



	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) 
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(mateSizeMod);
		return new EntityBear(worldObj, this,data);
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
		if(animal.getGender() != this.getGender() && animal.isAdult() && animal instanceof EntityBear) {
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
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(entityageable.getEntityData().getFloat("MateSize"));
		return new EntityBear(worldObj, this, data);
	}
	@Override
	public void setAge(int par1) {
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(!worldObj.isRemote){
			par1EntityPlayer.addChatMessage(getGender()==GenderEnum.FEMALE?"Female":"Male");
			if(getGender()==GenderEnum.FEMALE && pregnant){
				par1EntityPlayer.addChatMessage("Pregnant");
			}
			//par1EntityPlayer.addChatMessage("12: "+dataWatcher.getWatchableObjectInt(12)+", 15: "+dataWatcher.getWatchableObjectInt(15));
		}
		return true;
	}


	@Override
	public float getStrength() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(24);
	}


	@Override
	public float getAggression() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(25);
	}


	@Override
	public float getObedience() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(26);
	}


	@Override
	public float getColour() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(27);
	}


	@Override
	public float getClimateAdaptation() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(28);
	}


	@Override
	public float getHardiness() {
		// TODO Auto-generated method stub
		return this.getDataWatcher().getWatchableObjectFloat(29);
	}


	@Override
	public Vec3 getAttackedVec() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setAttackedVec(Vec3 attackedVec) {
		// TODO Auto-generated method stub

	}


	@Override
	public Entity getFearSource() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setFearSource(Entity fearSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public int GetCrushArmor() {
		return 0;
	}
	@Override
	public int GetSlashArmor() {
		return 0;
	}
	@Override
	public int GetPierceArmor() {
		return -335;
	}
}
