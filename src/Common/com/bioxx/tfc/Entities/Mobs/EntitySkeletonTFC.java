package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Entities.EntityJavelin;
import com.bioxx.tfc.Entities.EntityProjectileTFC;
import com.bioxx.tfc.Items.Tools.ItemCustomBow;
import com.bioxx.tfc.Items.Tools.ItemJavelin;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;
import com.bioxx.tfc.api.Interfaces.IProjectile;

public class EntitySkeletonTFC extends EntityMob implements IRangedAttackMob, ICausesDamage, IInnateArmor
{
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 120, 15.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
	private static final float[] armorProbability = new float[] {0.0F, 0.5F, 0.10F, 0.15F};

	public EntitySkeletonTFC(World par1World)
	{
		super(par1World);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIRestrictSun(this));
		this.tasks.addTask(3, new EntityAIFleeSun(this, 1D));
		this.tasks.addTask(5, new EntityAIWander(this, 1D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

		if (par1World != null && !par1World.isRemote)
			this.setCombatTask();
	}

	public void setCombatTask()
	{
		this.tasks.removeTask(this.aiAttackOnCollide);
		this.tasks.removeTask(this.aiArrowAttack);
		ItemStack itemstack = this.getHeldItem();

		if (itemstack != null && (itemstack.getItem() == TFCItems.Bow || (itemstack.getItem() instanceof ItemJavelin)))
		{
			this.tasks.addTask(4, this.aiArrowAttack);
		}
		else
		{
			/*if (itemstack != null && itemstack.getItem() instanceof ItemJavelin) 
			{
				this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(((ItemJavelin)itemstack.getItem()).weaponDamage);
			}*/
			this.tasks.addTask(4, this.aiAttackOnCollide);
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

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}

	/**
	 * Plays step sound at given x, y, z for the entity
	 */
	@Override
	protected void func_145780_a/*playStepSound*/(int par1, int par2, int par3, Block par4)
	{
		this.playSound("mob.skeleton.step", 0.15F, 1.0F);
		super.func_145780_a(par1, par2, par3, par4);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if (par1NBTTagCompound.hasKey("SkeletonType"))
		{
			byte b0 = par1NBTTagCompound.getByte("SkeletonType");
			this.setSkeletonType(b0);
		}
		this.setCombatTask();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.SkeletonHealth);//MaxHealth
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, new Byte((byte)rand.nextInt(2)));
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (this.isBurning())
			this.attackEntityFrom(DamageSource.onFire, 50);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if (super.attackEntityAsMob(par1Entity))
			return true;
		else
			return false;
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void onLivingUpdate()
	{
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
		{
			float f = this.getBrightness(1.0F);

			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				boolean flag = true;
				ItemStack itemstack = this.getEquipmentInSlot(4);
				if (itemstack != null)
				{
					if (itemstack.isItemStackDamageable())
					{
						itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + this.rand.nextInt(2));
						if (itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage())
						{
							this.renderBrokenItemStack(itemstack);
							this.setCurrentItemOrArmor(4, (ItemStack)null);
						}
					}
					flag = false;
				}

				if (flag)
					this.setFire(8);
			}
		}

		if (this.worldObj.isRemote && this.getSkeletonType() == 1)
			this.setSize(0.72F, 2.34F);

		super.onLivingUpdate();
	}

	/**
	 * Handles updating while being ridden by an entity
	 */
	@Override
	public void updateRidden()
	{
		super.updateRidden();
		if (this.ridingEntity instanceof EntityCreature)
		{
			EntityCreature entitycreature = (EntityCreature)this.ridingEntity;
			this.renderYawOffset = entitycreature.renderYawOffset;
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityArrow && par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			double d0 = entityplayer.posX - this.posX;
			double d1 = entityplayer.posZ - this.posZ;

			if (d0 * d0 + d1 * d1 >= 2500.0D)
				entityplayer.triggerAchievement(AchievementList.snipeSkeleton);
		}
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int amnt;
		int count;

		if (this.getSkeletonType() == 1)
		{
			if(this.getHeldItem().getItem() instanceof ItemJavelin && this.rand.nextFloat() < 0.03f)
				this.dropItem(getHeldItem().getItem(), 1);
		}
		else
		{
			if(this.getHeldItem().getItem() instanceof ItemCustomBow)
			{
				amnt = this.rand.nextInt(3 + par2);
				this.dropItem(TFCItems.Arrow, amnt);
			}
		}

		amnt = this.rand.nextInt(3 + par2);
		this.dropItem(Items.bone, amnt);
	}

	@Override
	protected void dropRareDrop(int par1)
	{
	}

	@Override
	protected void dropEquipment(boolean p_82160_1_, int p_82160_2_)
	{
		for (int j = 0; j < this.getLastActiveItems().length; ++j)
		{
			ItemStack itemstack = this.getEquipmentInSlot(j);
			boolean flag1 = this.equipmentDropChances[j] > 1.0F;

			if (itemstack != null && (p_82160_1_ || flag1) && this.rand.nextFloat() - (float)p_82160_2_ * 0.01F < this.equipmentDropChances[j])
			{
				if (!flag1 && itemstack.isItemStackDamageable())
				{
					int k = Math.max(itemstack.getMaxDamage() - 25, 1);
					int l = itemstack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(k) + 1);

					if (l > k)
					{
						l = k;
					}

					if (l < 1)
					{
						l = 1;
					}

					itemstack.setItemDamage(l);
				}

				this.entityDropItem(itemstack, 0.0F);
			}
		}
	}

	@Override
	protected void addRandomArmor()
	{
		superAddRandomArmor();
		if(this.getSkeletonType() == 0)
		{
			this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.Bow));
		}
		else if(this.getSkeletonType() == 1)
		{
			int i = this.rand.nextInt(2);
			if (this.rand.nextFloat() < 0.095F)
				++i;
			if (this.rand.nextFloat() < 0.095F)
				++i;
			if (this.rand.nextFloat() < 0.095F)
				++i;

			if(i == 0)
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.SedStoneJavelin));
			else if(i == 1)
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.IgExStoneJavelin));
			else if(i == 2)
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.CopperJavelin));
			else if(i == 3)
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzeJavelin));
			else if(i == 4)
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.WroughtIronJavelin));
		}
	}

	@Override
	protected void enchantEquipment()
	{
	}

	public static Item getArmorItemForSlot(int par0, int par1)
	{
		switch (par0)
		{
		case 4:
			if (par1 == 0)
				return TFCItems.LeatherHelmet;
			else if (par1 == 1)
				return TFCItems.CopperHelmet;
			else if (par1 == 2)
				return TFCItems.BronzeHelmet;
			else if (par1 == 3)
				return TFCItems.WroughtIronHelmet;
			else if (par1 == 4)
				return TFCItems.SteelHelmet;
		case 3:
			if (par1 == 0)
				return TFCItems.LeatherChestplate;
			else if (par1 == 1)
				return TFCItems.CopperChestplate;
			else if (par1 == 2)
				return TFCItems.BronzeChestplate;
			else if (par1 == 3)
				return TFCItems.WroughtIronChestplate;
			else if (par1 == 4)
				return TFCItems.SteelChestplate;
		case 2:
			if (par1 == 0)
				return TFCItems.LeatherLeggings;
			else if (par1 == 1)
				return TFCItems.CopperGreaves;
			else if (par1 == 2)
				return TFCItems.BronzeGreaves;
			else if (par1 == 3)
				return TFCItems.WroughtIronGreaves;
			else if (par1 == 4)
				return TFCItems.SteelGreaves;
		case 1:
			if (par1 == 0)
				return TFCItems.LeatherBoots;
			else if (par1 == 1)
				return TFCItems.CopperBoots;
			else if (par1 == 2)
				return TFCItems.BronzeBoots;
			else if (par1 == 3)
				return TFCItems.WroughtIronBoots;
			else if (par1 == 4)
				return TFCItems.SteelBoots;
		default:
			return null;
		}
	}

	private void superAddRandomArmor()
	{
		if (this.rand.nextFloat() < armorProbability[this.worldObj.difficultySetting.getDifficultyId()])
		{
			int i = this.rand.nextInt(2);
			float f = this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.1F : 0.25F;

			if (this.rand.nextFloat() < 0.095F)
				++i;
			if (this.rand.nextFloat() < 0.095F)
				++i;
			if (this.rand.nextFloat() < 0.095F)
				++i;

			for (int j = 3; j >= 0; --j)
			{
				ItemStack itemstack = this.getEquipmentInSlot(j);
				if (j < 3 && this.rand.nextFloat() < f)
					break;

				if (itemstack == null)
				{
					Item item = getArmorItemForSlot(j + 1, i);
					if (item != null)
						this.setCurrentItemOrArmor(j + 1, new ItemStack(item));
				}
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		int skelType = rand.nextInt(2);
		this.setSkeletonType(skelType);
		this.addRandomArmor();
		this.enchantEquipment();
		setCombatTask();
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b/*getLocationTensionFactor*/(this.posX, this.posY, this.posZ));

		return par1EntityLivingData;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving,float par2)
	{
		EntityProjectileTFC projectile = null;
		if(getSkeletonType() == 0)
		{
			projectile = new EntityProjectileTFC(this.worldObj, this, par1EntityLiving, 1.6F, 12.0F);
			projectile.setDamage(65.0);
		}
		else if(getSkeletonType() == 1)
		{
			ItemStack is = getHeldItem();
			if(is!= null && is.getItem() instanceof IProjectile)
			{
				projectile = new EntityJavelin(this.worldObj, this, par1EntityLiving, 1.6F, 12.0F);
				double dam = ((IProjectile)is.getItem()).getRangedDamage();
				projectile.setDamage(dam);
			}
		}

		if(projectile != null)
		{
			int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
			int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
			projectile.setDamage(projectile.getDamage() * 1.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);

			if (var3 > 0)
				projectile.setDamage(projectile.getDamage() + var3 * 0.5D);
			if (var4 > 0)
				projectile.setKnockbackStrength(var4);
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0)
				projectile.setFire(100);

			this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
			this.worldObj.spawnEntityInWorld(projectile);
		}
	}

	/**
	 * Return this skeleton's type.
	 */
	public int getSkeletonType()
	{
		return this.dataWatcher.getWatchableObjectByte(13);
	}

	public void setSkeletonType(int par1)
	{
		this.dataWatcher.updateObject(13, Byte.valueOf((byte)par1));
		this.setSize(0.6F, 1.8F);
	}

	@Override
	public EnumDamageType GetDamageType()
	{
		return EnumDamageType.PIERCING;
	}

	@Override
	public int GetCrushArmor() {
		return -335;
	}
	@Override
	public int GetSlashArmor() {
		return 1000;
	}
	@Override
	public int GetPierceArmor() {
		return 500000;//this is not an error. this makes piercing damage useless.
	}

	@Override
	public boolean getCanSpawnHere()
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);
		Block b = this.worldObj.getBlock(x, y, z);

		if(b == TFCBlocks.Leaves || b == TFCBlocks.Leaves2 || b == TFCBlocks.Thatch)
			return false;

		return super.getCanSpawnHere();
	}
}
