package com.bioxx.tfc.Entities;

import com.bioxx.tfc.Blocks.Terrain.BlockCollapsable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFallingDirt extends Entity
{
	public Block block;
	public int meta;
	public int fallTime;
	public int pTime;

	public EntityFallingDirt(World world)
	{
		super(world);
		fallTime = 0;
		meta = 1;
		block = Block.getBlockById(209);
		fallTime = 0;
		pTime = 0;
	}

	public EntityFallingDirt(World world, double d, double d1, double d2, Block b, int m, int processTime)
	{
		super(world);
		fallTime = 0;
		block = b;
		meta = m;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
		setPosition(d, d1, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
		pTime = processTime;
		this.dataWatcher.updateObject(21, Block.getIdFromBlock(block));
		this.dataWatcher.updateObject(22, meta);
	}

	public boolean canBeCollidedWith()
	{
		return !isDead;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(21, Integer.valueOf(Block.getIdFromBlock(block)));
		this.dataWatcher.addObject(22, Integer.valueOf(meta));
	}

	public float getShadowSize()
	{
		return 0.0F;
	}

	public World getWorld()
	{
		return worldObj;
	}

	public void onUpdate()
	{
		if (worldObj.isRemote && fallTime == 1)
		{
			block = Block.getBlockById(dataWatcher.getWatchableObjectInt(21));
			meta = dataWatcher.getWatchableObjectInt(22);
		}
		if(pTime == 0)
		{
			if (block == Blocks.air)
			{
				setDead();
				return;
			}
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			fallTime++;
			motionY -= 0.039999999105930328D;
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.98000001907348633D;
			motionY *= 0.98000001907348633D;
			motionZ *= 0.98000001907348633D;
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			if (fallTime == 1 && worldObj.getBlock(i, j, k) == block)
			{
				worldObj.setBlockToAir(i, j, k);
			}
			else if (!worldObj.isRemote && fallTime == 1)
			{
				//setDead();
			}
			if (onGround)
			{
				motionX *= 0.69999998807907104D;
				motionZ *= 0.69999998807907104D;
				motionY *= -0.5D;
				if (worldObj.getBlock(i, j, k) != Blocks.piston_extension)
				{
					setDead();
					if (!worldObj.isRemote && ((!worldObj.canPlaceEntityOnSide(block, i, j, k, true, 1, this, (ItemStack)null) && BlockCollapsable.canFallBelow(worldObj, i, j - 1, k)) || 
							!worldObj.setBlock(i, j, k, block, meta, 2)))
					{
						ItemStack itemstack = new ItemStack(block, 1, meta);
						EntityItem entityitem = new EntityItem(worldObj, posX, posY+0.5, posZ, itemstack);
						entityitem.delayBeforeCanPickup = 10;
						worldObj.spawnEntityInWorld(entityitem);
					}
				}
			}
			else if (fallTime > 100 && !worldObj.isRemote)
			{
				ItemStack itemstack = new ItemStack(block, 1, meta);
				EntityItem entityitem = new EntityItem(worldObj, posX, posY+0.5, posZ, itemstack);
				entityitem.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(entityitem);
				setDead();
			}
		}

		if(pTime > 0)
			pTime--;
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		block = Block.getBlockById(nbttagcompound.getByte("Tile") & 0xff);
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setByte("Tile", (byte)Block.getIdFromBlock(block));
	}

	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) 
	{
		double var2 = par1EntityPlayer.posX - this.posX;
		double var4 = par1EntityPlayer.posY - this.posY;
		double var6 = par1EntityPlayer.posZ - this.posZ;
		double d =  (double)MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
		if(d <= 1)
		{
			if(par1EntityPlayer.inventory.armorInventory[3] != null)
				par1EntityPlayer.attackEntityFrom(DamageSource.cactus, 1);
			else
				par1EntityPlayer.attackEntityFrom(DamageSource.cactus, 2);
		}
	}
}