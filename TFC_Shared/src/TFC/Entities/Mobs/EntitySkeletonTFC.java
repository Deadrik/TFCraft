package TFC.Entities.Mobs;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import TFC.TFCItems;
import TFC.Entities.EntityArrowTFC;

public class EntitySkeletonTFC extends EntitySkeleton
{
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);

	public EntitySkeletonTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

	}
	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000);//MaxHealth
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (this.isBurning())
		{
			this.attackEntityFrom(DamageSource.onFire, 50);
		}
	}
	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if (super.attackEntityAsMob(par1Entity))
		{
			if (this.getSkeletonType() == 1 && par1Entity instanceof EntityLiving)
			{
				((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return Item.arrow.itemID;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3;
		int var4;

		if (this.getSkeletonType() == 1)
		{
			var3 = this.rand.nextInt(3 + par2) - 1;

			for (var4 = 0; var4 < var3; ++var4)
			{
				this.dropItem(Item.coal.itemID, 1);
			}
		}
		else
		{
			var3 = this.rand.nextInt(3 + par2);

			for (var4 = 0; var4 < var3; ++var4)
			{
				this.dropItem(Item.arrow.itemID, 1);
			}
		}

		var3 = this.rand.nextInt(3 + par2);

		for (var4 = 0; var4 < var3; ++var4)
		{
			this.dropItem(Item.bone.itemID, 1);
		}
	}
	@Override
	protected void dropRareDrop(int par1)
	{
		if (this.getSkeletonType() == 1)
		{
			this.entityDropItem(new ItemStack(Item.skull.itemID, 1, 1), 0.0F);
		}
	}
	@Override
	protected void addRandomArmor()
	{
		superAddRandomArmor();
		this.setCurrentItemOrArmor(0, new ItemStack(Item.itemsList[Item.bow.itemID]));
	}

	public static Item getArmorItemForSlot(int par0, int par1)
	{
		switch (par0)
		{
		case 4:
			if (par1 == 0)
			{
				return Item.itemsList[Item.helmetLeather.itemID];
			}
			else if (par1 == 1)
			{
				return TFCItems.CopperHelmet;
			}
			else if (par1 == 2)
			{
				return TFCItems.BronzeHelmet;
			}
			else if (par1 == 3)
			{
				return TFCItems.WroughtIronHelmet;
			}
			else if (par1 == 4)
			{
				return TFCItems.SteelHelmet;
			}
		case 3:
			if (par1 == 0)
			{
				return Item.itemsList[Item.plateLeather.itemID];
			}
			else if (par1 == 1)
			{
				return TFCItems.CopperChestplate;
			}
			else if (par1 == 2)
			{
				return TFCItems.BronzeChestplate;
			}
			else if (par1 == 3)
			{
				return TFCItems.WroughtIronChestplate;
			}
			else if (par1 == 4)
			{
				return TFCItems.SteelChestplate;
			}
		case 2:
			if (par1 == 0)
			{
				return Item.itemsList[Item.legsLeather.itemID];
			}
			else if (par1 == 1)
			{
				return TFCItems.CopperGreaves;
			}
			else if (par1 == 2)
			{
				return TFCItems.BronzeGreaves;
			}
			else if (par1 == 3)
			{
				return TFCItems.WroughtIronGreaves;
			}
			else if (par1 == 4)
			{
				return TFCItems.SteelGreaves;
			}
		case 1:
			if (par1 == 0)
			{
				return Item.itemsList[Item.bootsLeather.itemID];
			}
			else if (par1 == 1)
			{
				return TFCItems.CopperBoots;
			}
			else if (par1 == 2)
			{
				return TFCItems.BronzeBoots;
			}
			else if (par1 == 3)
			{
				return TFCItems.WroughtIronBoots;
			}
			else if (par1 == 4)
			{
				return TFCItems.SteelBoots;
			}
		default:
			return null;
		}
	}

	private static final float[] armorProbability = new float[] {0.0F, 0.5F, 0.10F, 0.15F};

	private void superAddRandomArmor()
	{
		if (this.rand.nextFloat() < armorProbability[this.worldObj.difficultySetting])
		{
			int i = this.rand.nextInt(2);
			float f = this.worldObj.difficultySetting == 3 ? 0.1F : 0.25F;

			if (this.rand.nextFloat() < 0.095F)
			{
				++i;
			}

			if (this.rand.nextFloat() < 0.095F)
			{
				++i;
			}

			if (this.rand.nextFloat() < 0.095F)
			{
				++i;
			}

			for (int j = 3; j >= 0; --j)
			{
				ItemStack itemstack = this.getCurrentItemOrArmor(j);

				if (j < 3 && this.rand.nextFloat() < f)
				{
					break;
				}

				if (itemstack == null)
				{
					Item item = getArmorItemForSlot(j + 1, i);

					if (item != null)
					{
						this.setCurrentItemOrArmor(j + 1, new ItemStack(item));
					}
				}
			}
		}
	}

	@Override
	public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.func_110161_a(par1EntityLivingData);

		if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
		{
			this.tasks.addTask(4, this.aiAttackOnCollide);
			this.setSkeletonType(1);
			this.setCurrentItemOrArmor(0, new ItemStack(Item.swordStone));
			this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
		}
		else
		{
			this.tasks.addTask(4, this.aiArrowAttack);
			this.addRandomArmor();
			this.enchantEquipment();
		}

		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_110746_b(this.posX, this.posY, this.posZ));

		if (this.getCurrentItemOrArmor(4) == null)
		{
			Calendar calendar = this.worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
				this.equipmentDropChances[4] = 0.0F;
			}
		}

		return par1EntityLivingData;
	}
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving,float par2)
	{
		EntityArrowTFC arrow = new EntityArrowTFC(this.worldObj, this, par1EntityLiving, 1.6F, 12.0F);
		int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
		int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
		arrow.setDamage(arrow.getDamage() * 1.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting * 0.11F);


		if (var3 > 0)
		{
			arrow.setDamage(arrow.getDamage() + var3 * 0.5D + 0.5D);
		}

		if (var4 > 0)
		{
			arrow.setKnockbackStrength(var4);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1)
		{
			arrow.setFire(100);
		}

		this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(arrow);
	}
}
