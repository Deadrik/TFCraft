package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityZombieTFC extends EntityZombie implements ICausesDamage
{
	private int field_82234_d = 0;

	public EntityZombieTFC(World par1World)
	{
		super(par1World);
	}
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(TFC_MobData.ZombieDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.ZombieHealth);//MaxHealth
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20)
		{
			var1 = 20;
		}

		return var1;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(TFCItems.WroughtIronIngot.itemID, 1);
			break;
		case 1:
			this.dropItem(TFCItems.Carrot.itemID, 1);
			break;
		case 2:
			this.dropItem(TFCItems.Potato.itemID, 1);
		}
	}

	@Override
	protected void addRandomArmor()
	{
		super.addRandomArmor();
		this.setCurrentItemOrArmor(1, null); 
		this.setCurrentItemOrArmor(2, null); 
		this.setCurrentItemOrArmor(3, null); 
		this.setCurrentItemOrArmor(4, null); 

		if (this.rand.nextFloat() < (this.worldObj.difficultySetting == 3 ? 0.05F : 0.01F))
		{
			int var1 = this.rand.nextInt(3);

			if (var1 == 0)
			{
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzePick));
			}
			else
			{
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzeShovel));
			}
		}
	}


	@Override
	protected void enchantEquipment()
	{

	}


	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 16)
		{
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}
	@Override
	public EnumDamageType GetDamageType() 
	{
		return EnumDamageType.SLASHING;
	}
}
