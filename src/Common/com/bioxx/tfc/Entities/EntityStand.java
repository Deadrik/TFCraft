package com.bioxx.tfc.Entities;

import com.bioxx.tfc.TileEntities.TEStand;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityStand extends EntityLiving
{
	public TEStand standTE;
	public EntityStand(World par1World)
	{
		super(par1World);
		setSize(0.125f,2f);
		this.setHealth(1);
		noClip = true;
		ignoreFrustumCheck = true;
	}

//	@Override
//	protected void doBlockCollisions()
//	{
//	}

	public EntityStand(World par1World, TEStand TE)
	{
		this(par1World);
		standTE = TE;
		posX = TE.xCoord+0.5;
		posY = TE.yCoord;
		posZ = TE.zCoord+0.5;
	}

	@Override
	public void setHealth(float par1)
	{
		this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(par1, 0.0F, 1)));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	public void moveEntity(double d1,double d2,double d3)
	{
		this.isCollided = false;
	}

	@Override
	public void onUpdate()
	{
		if(this.worldObj.isRemote)
			setSize(0.125f,2f);
		else
			setSize(0,0);

		if(standTE == null)
		{
			TileEntity t = worldObj.getTileEntity((int)posX, (int)posY, (int)posZ);
			if(t instanceof TEStand)
				standTE = (TEStand)t;
			if(standTE == null)
				setDead();
		}

		if(standTE != null)
		{
			this.rotationYaw = ((int)(standTE.yaw/90))*90 - 180;
			this.rotationYawHead = this.rotationYaw;
			this.posX = standTE.xCoord + 0.5;
			this.posZ = standTE.zCoord + 0.5;
		}

		float lookX;
		float lookZ;
		lookZ = -MathHelper.cos(this.rotationYaw);
		lookX = MathHelper.sin(this.rotationYaw);
		//this.getLookHelper().setLookPosition(this.posX + lookX, this.posY + (double)this.getEyeHeight(), this.posZ + lookZ, 10.0F, (float)this.getVerticalFaceSpeed());
		//this.getNavigator().tryMoveToXYZ(this.posX + lookX, this.posY, this.posZ + lookZ, 0.5);
	}

	@Override
	public boolean isEntityInvulnerable()
	{
		return true;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		//posX = nbttagcompound.getDouble("X");
		//posY = nbttagcompound.getDouble("Y");
		//posZ = nbttagcompound.getDouble("Z");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		//nbttagcompound.setDouble("X", posX);
		//nbttagcompound.setDouble("Y", posY);
		//nbttagcompound.setDouble("Z", posZ);
	}

	@Override
	public ItemStack getHeldItem()
	{
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int i)
	{
		if(standTE !=null)
		{
			if(standTE.getWorldObj().isRemote)
				standTE.validate();
			return standTE.getStackInSlot(i);
		}

		TileEntity te = worldObj.getTileEntity((int)posX, (int)posY, (int)posZ);
		if(te instanceof TEStand)
		{
			standTE = (TEStand)te;
			if(standTE.getWorldObj().isRemote)
				standTE.validate();
			return standTE.getStackInSlot(i);
		}
		return null;
		
		//return super.getEquipmentInSlot(par1);
	}

	@Override
	public void setCurrentItemOrArmor(int i, ItemStack itemstack)
	{
		if(standTE!=null)
			standTE.setInventorySlotContents(i, itemstack);
	}

	@Override
	public ItemStack[] getLastActiveItems()
	{
		ItemStack[] items = new ItemStack[5];
		for(int i = 0; i < 5; i++)
			items[i] = this.getEquipmentInSlot(i);
		return items;
	}

}
