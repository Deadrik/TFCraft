package com.bioxx.tfc.Entities;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.TFCBlocks;

public class EntityBarrel extends Entity
{
	public int fuse;
	public ItemStack origBarrel;
	private int gunpowder;

	public EntityBarrel(World par1World)
	{
		super(par1World);
		this.fuse = 60;

		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
	}

	public EntityBarrel(World world, double x, double y, double z, ItemStack is, int gunpowderCount)
	{
		this(world);
		this.gunpowder = gunpowderCount;
		this.setPosition(x, y, z);
		float f = (float)(Math.random() * Math.PI * 2.0D);
		this.motionX = -((float)Math.sin(f)) * 0.02F;
		this.motionY = 0.20000000298023224D;
		this.motionZ = -((float)Math.cos(f)) * 0.02F;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		origBarrel = is;
		this.dataWatcher.updateObject(20, origBarrel.getItemDamage());
	}

	public void setFuse(int f)
	{
		fuse = f;
	}

	@Override
	public void onUpdate()
	{
		fuse--;
		if(fuse <= 0)
		{
			if(!worldObj.isRemote)
				explode();
			setDead();
		}
		worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, new Random().nextFloat(), 1.0D, new Random().nextFloat());
	}

	@Override
	public boolean isEntityInvulnerable()
	{
		return true;
	}

	public void explode()
	{
		float f = gunpowder / 12.0F; // Full barrel = the old large strength of 64
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(20, Integer.valueOf(0));
	}

	@Override
	public boolean interactFirst(EntityPlayer player)
	{
		int x = (int)Math.floor(posX); int y = (int)Math.floor(posY); int z = (int)Math.floor(posZ);
		if(!worldObj.isRemote && worldObj.setBlock(x, y, z, TFCBlocks.barrel, getBarrelType()&15, 0x2))
		{
			TEBarrel te = (TEBarrel)worldObj.getTileEntity(x, y, z);
			te.readFromItemNBT(this.origBarrel.getTagCompound());
			this.setDead();
		}
		return true;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity)
	{
		return this.boundingBox;
	}

	public int getBarrelType()
	{
		return this.dataWatcher.getWatchableObjectInt(20);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.fuse = nbt.getByte("Fuse");
		this.origBarrel = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("item"));
		this.dataWatcher.updateObject(20, origBarrel.getItemDamage());
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setByte("Fuse", (byte)this.fuse);
		nbt.setTag("item", this.origBarrel.writeToNBT(new NBTTagCompound()));
	}
}
