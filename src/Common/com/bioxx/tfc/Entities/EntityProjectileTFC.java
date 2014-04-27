package com.bioxx.tfc.Entities;

import com.bioxx.tfc.api.ICausesDamage;
import com.bioxx.tfc.api.Enums.EnumDamageType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class EntityProjectileTFC extends EntityArrow implements ICausesDamage
{
	public short damageTaken = 0;

	public EntityProjectileTFC(World par1World)
	{
		super(par1World);
	}

	public EntityProjectileTFC(World world, double i, double j, double k)
	{
		super(world, i, j, k);
	}

	public EntityProjectileTFC(World world, EntityLivingBase shooter, EntityLivingBase victim, float force, float forceVariation)
	{
		super(world, shooter, victim, force, forceVariation);
	}

	public EntityProjectileTFC(World world, EntityLivingBase par2EntityLivingBase, float force)
	{
		super(world, par2EntityLivingBase, force);
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
				boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && player.capabilities.isCreativeMode;

				EntityItem ei = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.arrow, 1, this.damageTaken));
				EntityItemPickupEvent event = new EntityItemPickupEvent(player, ei);

				if (MinecraftForge.EVENT_BUS.post(event))
					return;

				ItemStack itemstack = ei.getEntityItem();
				if(itemstack.stackSize <= 0) 
					flag = true;
				else if (this.canBePickedUp == 1 && !player.inventory.addItemStackToInventory(itemstack))
					flag = false;

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
			if(this.ticksExisted < 1200)
				this.isDead = false;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
//		if (nbt.hasKey("itemID"))
//			this.itemID = nbt.getInteger("itemID");
		this.damageTaken = nbt.getShort("damageTaken");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
//		nbt.setInteger("itemID", itemID);
		nbt.setShort("damageTaken", this.damageTaken);
	}

	@Override
	public EnumDamageType GetDamageType()
	{
		return EnumDamageType.PIERCING;
	}
}
