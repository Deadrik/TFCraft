package TFC.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;

public class EntityProjectileTFC extends EntityArrow implements ICausesDamage
{
	public int itemID;
	public short damageTaken = 0;

	public EntityProjectileTFC(World par1World) 
	{
		super(par1World);
	}

	public EntityProjectileTFC(World world, double i, double j, double k)
	{
		super(world, i, j, k);
	}

	public EntityProjectileTFC(World world, EntityLivingBase shooter, EntityLivingBase victim, float force, float forceVariation, int itemid)
	{
		super(world, shooter, victim, force, forceVariation);
		itemID = itemid;
	}

	public EntityProjectileTFC(World world, EntityLivingBase par2EntityLivingBase, float force, int itemid)
	{
		super(world, par2EntityLivingBase, force);
		itemID = itemid;
	}

	public void setDamageTaken(short d)
	{
		damageTaken = d;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player)
	{
		if (!this.worldObj.isRemote)
		{	
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);

			boolean inground = nbt.hasKey("inGround") && nbt.getByte("inGround") == 1;
			if(inground)
			{
				boolean flag = inground && this.canBePickedUp == 1 || this.canBePickedUp == 2 && player.capabilities.isCreativeMode;
				boolean pickedUp = player.inventory.addItemStackToInventory(new ItemStack(Item.itemsList[this.itemID], 1, damageTaken));

				if (this.canBePickedUp == 1 && !pickedUp)
				{
					flag = false;
				}

				if (flag)
				{
					this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					player.onItemPickup(this, 1);
					this.setDead();
				}
			}
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if(!worldObj.isRemote && this.isDead)
		{
			if(this.ticksExisted < 1200)
			{
				this.isDead = false;
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("itemID"))
		{
			this.itemID = nbt.getInteger("itemID");
		}
		this.damageTaken = nbt.getShort("damageTaken");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("itemID", itemID);
		nbt.setShort("damageTaken", this.damageTaken);
	}

	@Override
	public EnumDamageType GetDamageType() {
		return EnumDamageType.PIERCING;
	}
}
