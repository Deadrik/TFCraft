package TFC.Entities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Entities.AI.EntityAITargetTFC;

public class EntityAnimalTFC extends EntityAnimal
{
	private int inLove = 0;
	public ItemStack item = null;
	public int sex;
	public long animalID;
	public boolean mateForLife;
	public int panic;
	public int degreeOfDiversion = 1;
	public EntityAnimalTFC mate;
	public EntityAnimalTFC parent;
	public int pregnancyTime;
	protected long conception;
	protected float mateSizeMod;
	public boolean pregnant;
	public float size_mod;
	private int pickUp;
	public int hunger;
	public boolean terrified = false;
	public Entity fearSource = null;
	public List <EntityAnimalTFC> children = new ArrayList <EntityAnimalTFC>(0);
	public boolean rutting;
	public int matingStart;
	public int matingEnd;
	public int adultAge;
	public boolean ouch = false;
	public boolean ruttVictor;
	public List < Integer > fooditems = new ArrayList < Integer > (0);

	public int breeding;

	public long adultTime;
	protected long birthTime;
	
	protected long hasMilkTime;

	public EntityAnimalTFC(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + entityId;
		hunger = 168000;
		pickUp = 0;
		panic = 0;
		breeding = 0;
		matingStart = 0;
		matingEnd = 0;		
		mateForLife = false;
		rutting = false;
		ruttVictor = false;
		pregnant = false;
		pregnancyTime = 4 * TFC_Time.daysInMonth;
		conception = 0;
		mateSizeMod = 0;
		parent = null;
		mate = null;
		sex = rand.nextInt (2);
		//tasks.addTask (1, new EntityAIMoveTowardsFood (this, 0.4F, 20F));
		tasks.addTask(3, new EntityAITargetTFC(this,12.0F,false));
		//tasks.addTask(2, new EntityAIFollowParentTFC(this,0.2F));
		size_mod = (((rand.nextInt (degreeOfDiversion+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex);
		birthTime = TFC_Time.getTotalTicks();
		adultAge = 3 * TFC_Time.daysInMonth;
		adultTime = birthTime;
		
		hasMilkTime = 0;
	}

	public EntityAnimalTFC(World par1World,EntityAnimalTFC mother, float F_size)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + entityId;
		hunger = 168000;
		pickUp = 0;
		breeding = 0;
		matingStart = 0;
		matingEnd = 0;
		mateForLife = false;
		rutting = false;
		ruttVictor = false;
		pregnant = false;
		pregnancyTime = 4 * TFC_Time.daysInMonth;
		conception = 0;
		mateSizeMod = 0;
		parent = mother;
		mate = null;
		sex = rand.nextInt (2);
		//tasks.addTask (1, new EntityAIMoveTowardsFood (this, 0.4F, 20F));
		tasks.addTask(3, new EntityAITargetTFC(this,12.0F,false));
		//tasks.addTask(2, new EntityAIFollowParentTFC(this,0.2F));
		size_mod = (((rand.nextInt (getDegree()+1)*(rand.nextBoolean()?1:-1)) / 10f) + 1F) * (1.0F - 0.1F * sex) * (float)Math.sqrt((mother.size_mod + F_size)/1.9F);
		size_mod = Math.min(Math.max(size_mod, 0.7F),1.3f);
		birthTime = TFC_Time.getTotalTicks();
		adultAge = 3 * TFC_Time.daysInMonth;
		adultTime = birthTime + TFC_Settings.dayLength * adultAge;
		
		hasMilkTime = 0;
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(30, new Integer(0));
        this.dataWatcher.addObject(31, new Integer(0));
    }
	
	public int getSex(){
		return this.dataWatcher.getWatchableObjectInt(30);
	}
	
	public float getSize(){
		return this.dataWatcher.getWatchableObjectInt(31)/10f;
	}

	@Override
	public void setGrowingAge(int par1)
	{
		adultTime = TFC_Time.getTotalTicks() - par1;
		dataWatcher.updateObject(12, Integer.valueOf(par1));
	}

	@Override
	public void setDead(){
		for (int i = 0; i < children.size();i++){
			children.get(i).parent = null;
		}
		super.setDead();
	}

	public int getDegree(){
		return degreeOfDiversion;
	}

	public boolean attackEntityfrom(DamageSource par1DamageSource, int par2)
	{
		Entity entity = par1DamageSource.getEntity();
		fearSource = par1DamageSource.getEntity();
		//System.out.println("yep");
		if (entity.getClass() == getClass() && (((EntityAnimalTFC)entity).rutting)){
			setAttackTarget((EntityLiving)entity);
			if (getHealth() <= getMaxHealth()/4){
				((EntityAnimalTFC)getAttackTarget()).ruttVictor = true;
			}
		}
		if (getGrowingAge()<0 && parent.children.contains(this) && par1DamageSource.getEntity() instanceof EntityLiving){
			parent.setAttackTarget((EntityLiving)par1DamageSource.getEntity());
		}
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public void onLivingUpdate()
	{
		if (hunger > 168000)
		{
			hunger = 168000;
		}
		if (hunger > 0)
		{
			hunger--;
		}
		
		if(!this.worldObj.isRemote){
			this.dataWatcher.updateObject(30, Integer.valueOf(sex));
			this.dataWatcher.updateObject(31, Integer.valueOf((int)(size_mod*10)));
		}
		else{
			sex = this.dataWatcher.getWatchableObjectInt(30);
			size_mod = this.dataWatcher.getWatchableObjectInt(31)/10f;
		}
		/**
		 * This Cancels out the growingAge from EntityAgeable
		 * */
		int var1 = this.getGrowingAge();
		super.onLivingUpdate();
		this.setGrowingAge(var1);

		if (panic > 0){
			panic--;
		}
		long i = TFC_Time.getTotalTicks() - adultTime;

		
		if (i < 0)
		{
			i++;
			setGrowingAge((int)i);
		}
		else if (i > 0)
		{
			i--;
			setGrowingAge((int)i);
		}


		/*if(pregnant){
			if(TFCSeasons.getTotalTicks() >= conception + pregnancyTime*TFCSettings.dayLength){
				EntityAnimalTFC baby = new EntityAnimalTFC(worldObj, this,mateSizeMod);
				giveBirth(baby);
				pregnant = false;
			}
		}*/

		if (this.getGrowingAge() != 0)
		{
			this.inLove = 0;
		}

		if (this.inLove > 0)
		{
			--this.inLove;
			String heart = "heart";

			if (this.inLove % 10 == 0)
			{
				double var2 = this.rand.nextGaussian() * 0.02D;
				double var4 = this.rand.nextGaussian() * 0.02D;
				double var6 = this.rand.nextGaussian() * 0.02D;
				this.worldObj.spawnParticle(heart, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var2, var4, var6);
			}
		}
		else
		{
			this.breeding = 0;
		}
		if (pickUp > 0)
		{
			pickUp--;
		}
		if (hunger > 144000 && rand.nextInt (100) == 0 && health < getMaxHealth () && !isDead)
		{
			health++;
		}
		for(EntityItem ei : this.capturedDrops)
		{
			ItemStack item = ei.getEntityItem();
			if (item != null)
			{
				if (hunger < 144000 && fooditems.contains (item.itemID))
				{
					renderBrokenItemStack (item);
					item = null;
					hunger += 24000;
					worldObj.playSoundAtEntity (this, "random.eat", 0.5F + 0.5F * rand.nextInt (2), (rand.nextFloat () - rand.nextFloat ()) * 0.2F + 1.0F);
				}
			}
		}
		if (getHealth () > 0)
		{
			List list = worldObj.getEntitiesWithinAABBExcludingEntity (this, boundingBox.expand (1.0D, 0.0D, 1.0D));

			if (list != null)
			{
				for (int j = 0 ; j < list.size () ; j++)
				{
					Entity entity = (Entity) list.get (j);

					if (!entity.isDead)
					{
						//entity.onCollideWithAnimal (this);
					}
				}
			}
		}

		if(TFC_Time.getTotalTicks() > (birthTime + adultAge*TFC_Settings.dayLength)){
			setGrowingAge(0);
		}
		else if (getGrowingAge()<0F){
			setGrowingAge((int)(TFC_Time.getTotalTicks() - (birthTime + adultAge*TFC_Settings.dayLength)));
		}
	}

	public void setInLove(boolean n){
		if(n){
			inLove=2400;
			return;
		}
		inLove=0;
	}

	public boolean wantsItem (ItemStack itemstack1)
	{
		if (fooditems.contains (itemstack1.itemID) && hunger < 200000 && pickUp == 0)
		{
			if (item != null)
			{
				dropItem (item.itemID, 1);
			}
			item = itemstack1.copy ();
			if (item.stackSize > 1)
			{
				dropItem (item.itemID, item.stackSize - 1);
			}
			pickUp = 600;
			return true;
		}
		return false;
	}

	@Override
	public void applyEntityCollision(Entity par1Entity){
		super.applyEntityCollision(par1Entity);
		if (par1Entity instanceof EntityItem){
			boolean y = wantsItem(((EntityItem)par1Entity).getEntityItem());
		}
	}
	
	@Override
	public boolean isChild(){
		return false;
	}

	@Override
	public int getMaxHealth()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack.itemID == TFCItems.WheatGrain.itemID || par1ItemStack.itemID == TFCItems.BarleyGrain.itemID ||par1ItemStack.itemID == TFCItems.OatGrain.itemID||par1ItemStack.itemID == TFCItems.RyeGrain.itemID;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT (NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT (par1NBTTagCompound);
		par1NBTTagCompound.setLong ("Animal ID", animalID);
		par1NBTTagCompound.setInteger ("Sex", sex);
		par1NBTTagCompound.setFloat ("Size Modifier", size_mod);
		par1NBTTagCompound.setInteger ("Hunger", hunger);
		par1NBTTagCompound.setBoolean("Pregnant", pregnant);
		par1NBTTagCompound.setFloat("MateSize", mateSizeMod);
		par1NBTTagCompound.setLong("ConceptionTime",conception);
		for (int i = 0; i < children.size();i++){
			String n = "Child"+i;
			par1NBTTagCompound.setLong(n,children.get(i).animalID);
		}
		if (mate != null)
		{
			par1NBTTagCompound.setLong ("Mate number", mate.animalID);
		}
		else
		{
			par1NBTTagCompound.setLong ("Mate number", -1);
		}
		if (getGrowingAge()<0&& parent != null)
		{
			par1NBTTagCompound.setLong ("Parent", parent.animalID);
		}
		else
		{
			par1NBTTagCompound.setLong ("Parent", -1);
		}

		par1NBTTagCompound.setLong("AdultTime",adultTime);
		par1NBTTagCompound.setLong("BirthTime",birthTime);
		par1NBTTagCompound.setLong("HasMilkTime", hasMilkTime);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT (NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT (par1NBTTagCompound);
		animalID = par1NBTTagCompound.getLong ("Animal ID");
		sex = par1NBTTagCompound.getInteger ("Sex");
		size_mod = par1NBTTagCompound.getFloat ("Size Modifier");
		hunger = par1NBTTagCompound.getInteger ("Hunger");
		pregnant = par1NBTTagCompound.getBoolean("Pregnant");
		mateSizeMod = par1NBTTagCompound.getFloat("MateSize");
		conception = par1NBTTagCompound.getLong("ConceptionTime");
		int m = 0;
		String n = "Child"+m;
		while (par1NBTTagCompound.hasKey(n)){
			n = "Child"+m;
			m++;
			for (int j = 0 ; j < worldObj.loadedEntityList.size () ; j++)
			{
				Entity k = (Entity) worldObj.loadedEntityList.get (j);
				if (k instanceof EntityAnimalTFC)
				{
					long i = par1NBTTagCompound.getLong (n);

					if (i == ((EntityAnimalTFC) k).animalID)
					{
						children.add((EntityAnimalTFC)k);
					}
				}
			}
		}
		long i = par1NBTTagCompound.getLong ("Mate number");
		if (i != -1)
		{
			for (int j = 0 ; j < worldObj.loadedEntityList.size () ; j++)
			{
				Entity k = (Entity) worldObj.loadedEntityList.get (j);
				if (k instanceof EntityAnimalTFC)
				{
					if (i == ((EntityAnimalTFC) k).animalID)
					{
						mate = (EntityAnimalTFC) k;
						break;
					}
				}
			}
		}
		if (getGrowingAge()<0 && par1NBTTagCompound.getLong ("Parent") != -1)
		{
			i = par1NBTTagCompound.getLong ("Parent");
			for (int j = 0 ; j < worldObj.loadedEntityList.size () ; j++)
			{
				Entity k = (Entity) worldObj.loadedEntityList.get (j);
				if (k instanceof EntityAnimalTFC)
				{
					if (i == ((EntityAnimalTFC) k).animalID)
					{
						parent = (EntityAnimalTFC) k;
						break;
					}
				}
			}
		}
		adultTime = par1NBTTagCompound.getLong("AdultTime");
		birthTime = par1NBTTagCompound.getLong("BirthTime");
		hasMilkTime = par1NBTTagCompound.getLong("HasMilkTime");
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere ()
	{
		int i = MathHelper.floor_double (posX);
		int j = MathHelper.floor_double (boundingBox.minY);
		int k = MathHelper.floor_double (posZ);
		return worldObj.getBlockId (i, j - 1, k) == Block.grass.blockID && worldObj.getFullBlockLightValue (i, j, k) > 8 && super.getCanSpawnHere ();
	}
	

	public boolean canMateWith (EntityAnimalTFC par1EntityAnimal)
	{
		return super.canMateWith(par1EntityAnimal) && par1EntityAnimal.sex != sex;
	}
	
	public void procreate(EntityAnimal var1)
	{

	}

	public int getDamageMod(){
		int i = rand.nextInt((int)(size_mod*10));
		if (i >= 5){
			return 10;
		}
		return 0;
	}

	public void mate(EntityAnimalTFC targetMate)
	{
		if (sex == 0){
			targetMate.mate(this);
			return;
		}
		conception = TFC_Time.getTotalTicks();
		pregnant = true;
		targetMate.setGrowingAge (TFC_Settings.dayLength);
		resetInLove ();
		targetMate.resetInLove ();
		mateSizeMod = targetMate.size_mod;
	}

	public void giveBirth (EntityAnimalTFC entityanimal){
		
		
		//System.out.println("yep");
		//System.out.println(posX);
		entityanimal.setLocationAndAngles (posX+(rand.nextFloat()-0.5F)*2F,posY,posZ+(rand.nextFloat()-0.5F)*2F, 0.0F, 0.0F);
		entityanimal.rotationYawHead = entityanimal.rotationYaw;
		entityanimal.renderYawOffset = entityanimal.rotationYaw;
		entityanimal.initCreature();
		worldObj.spawnEntityInWorld(entityanimal);
		entityanimal.setGrowingAge(entityanimal.adultAge * -TFC_Settings.dayLength);
		//if(worldObj.spawnEntityInWorld (entityanimal)){
		//	children.add(entityanimal);
		//}
		
	}

	@Override
	public void eatGrassBonus()
	{
		hunger+=24000;
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(!par1EntityPlayer.worldObj.isRemote){
			if (sex == 0){
				par1EntityPlayer.addChatMessage("Male");
			}
			else{
				par1EntityPlayer.addChatMessage("Female");
				if(pregnant){
					par1EntityPlayer.addChatMessage("Pregnant");
				}
			}
		}
		return super.interact(par1EntityPlayer);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
