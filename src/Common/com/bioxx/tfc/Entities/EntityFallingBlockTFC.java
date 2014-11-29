package com.bioxx.tfc.Entities;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityFallingBlockTFC extends Entity implements IEntityAdditionalSpawnData
{
	private Block block;
	public int blockMeta;
	public int aliveTimer;
	public boolean shouldDropItem;
	private boolean field_145808_f;
	private boolean field_145809_g;
	public int maxDamage;
	public float damage;
	public NBTTagCompound tileEntityData;

	public EntityFallingBlockTFC(World p_i1706_1_)
	{
		super(p_i1706_1_);
		this.shouldDropItem = true;
		this.maxDamage = 2000;
		this.damage = 100F;
	}

	public EntityFallingBlockTFC(World world, double x, double y, double z, Block b)
	{
		this(world, x, y, z, b, 0);
	}

	public EntityFallingBlockTFC(World world, double x, double y, double z, Block b, int meta)
	{
		this(world);
		this.shouldDropItem = false;
		this.maxDamage = 40;
		this.damage = 2.0F;
		this.block = b;
		this.blockMeta = meta;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit() {}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		if(block == null)
			return;
		if (this.block.getMaterial() == Material.air)
		{
			this.setDead();
		}
		else
		{
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			++this.aliveTimer;
			this.motionY -= 0.03999999910593033D;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.9800000190734863D;

			if (!this.worldObj.isRemote)
			{
				int i = MathHelper.floor_double(this.posX);
				int j = MathHelper.floor_double(this.posY);
				int k = MathHelper.floor_double(this.posZ);

				if (this.aliveTimer == 1)
				{
					this.worldObj.setBlockToAir(i, j, k);
				}

				if (this.onGround)
				{
					if(canReplace(worldObj, i, j, k))
					{
						worldObj.setBlockToAir(i, j, k);
					}
					else if(worldObj.getBlock(i, j, k).getBlockBoundsMaxY() < 1)
					{
						j++;
					}
					if(canReplace(worldObj, i, j-1, k))
					{
						//TFC_Core.setBlockToAirWithDrops(worldObj, i, j-1, k);
						worldObj.setBlockToAir(i, j-1, k);
						this.onGround = false;
					}
				}

				if (this.onGround)
				{
					this.motionX *= 0.699999988079071D;
					this.motionZ *= 0.699999988079071D;
					this.motionY *= -0.5D;

					if (this.worldObj.getBlock(i, j, k) != Blocks.piston_extension)
					{
						this.setDead();


						if (!this.field_145808_f && canPlaceEntityOnSide(worldObj, this.block, i, j, k, true, 1, (Entity)null, (ItemStack)null) && !BlockFalling.func_149831_e(this.worldObj, i, j - 1, k))
						{

							if (this.tileEntityData != null && this.block instanceof ITileEntityProvider)
							{
								TileEntity tileentity = this.worldObj.getTileEntity(i, j, k);

								if (tileentity != null)
								{
									NBTTagCompound nbttagcompound = new NBTTagCompound();
									tileentity.writeToNBT(nbttagcompound);
									Iterator iterator = this.tileEntityData.func_150296_c().iterator();

									while (iterator.hasNext())
									{
										String s = (String)iterator.next();
										NBTBase nbtbase = this.tileEntityData.getTag(s);

										if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
										{
											nbttagcompound.setTag(s, nbtbase.copy());
										}
									}

									tileentity.readFromNBT(nbttagcompound);
									tileentity.markDirty();
								}
							}
						}
						else if (this.shouldDropItem && !this.field_145808_f)
						{
							this.entityDropItem(new ItemStack(this.block, 1, this.block.damageDropped(this.blockMeta)), 0.0F);
						}
					}
				}
				else if (this.aliveTimer > 100 && !this.worldObj.isRemote && (j < 1 || j > 256) || this.aliveTimer > 600)
				{
					if (this.shouldDropItem)
					{
						this.entityDropItem(new ItemStack(this.block, 1, this.block.damageDropped(this.blockMeta)), 0.0F);
					}

					this.setDead();
				}
			}
		}
	}

	public boolean canPlaceEntityOnSide(World world, Block fallingBlock, int x, int y, int z, boolean skipEntityCheck, int side, Entity thisEntity, ItemStack is)
	{
		Block block1 = world.getBlock(x, y, z);
		AxisAlignedBB axisalignedbb = null;
		if(!skipEntityCheck)
		{
			axisalignedbb = fallingBlock.getCollisionBoundingBoxFromPool(world, x, y, z);
			if(!world.checkNoEntityCollision(axisalignedbb, thisEntity))//If we found an entity that blocks us
				return false;
		}

		return (block1.getMaterial() == Material.circuits ? true : canReplace(world, x, y, z));
	}

	public boolean canReplace(World world, int x, int y, int z)
	{
		Block b = world.getBlock(x, y, z);
		if (canDestroy(b) && (b.isAir(world, x, y, z) || !worldObj.isSideSolid(x, y, z, ForgeDirection.UP)))
			return TFC_Core.setBlockWithDrops(worldObj, x, y, z, getBlock(), this.blockMeta);
		return false;
	}

	private boolean canDestroy(Block b)
	{
		if(b == TFCBlocks.Charcoal)
			return false;
		return true;
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	protected void fall(float p_70069_1_)
	{
		if (this.field_145809_g)
		{
			int i = MathHelper.ceiling_float_int(p_70069_1_ - 1.0F);

			if (i > 0)
			{
				ArrayList arraylist = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
				boolean flag = this.block == Blocks.anvil;
				DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
				Iterator iterator = arraylist.iterator();

				while (iterator.hasNext())
				{
					Entity entity = (Entity)iterator.next();
					entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor_float((float)i * this.damage), this.maxDamage));
				}

				if (flag && (double)this.rand.nextFloat() < 0.05000000074505806D + (double)i * 0.05D)
				{
					int j = this.blockMeta >> 2;
				int k = this.blockMeta & 3;
				++j;

				if (j > 2)
				{
					this.field_145808_f = true;
				}
				else
				{
					this.blockMeta = k | j << 2;
				}
				}
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setByte("Tile", (byte)Block.getIdFromBlock(this.block));
		nbt.setInteger("TileID", Block.getIdFromBlock(this.block));
		nbt.setByte("Data", (byte)this.blockMeta);
		nbt.setByte("Time", (byte)this.aliveTimer);
		nbt.setBoolean("DropItem", this.shouldDropItem);
		nbt.setBoolean("HurtEntities", this.field_145809_g);
		nbt.setFloat("FallHurtAmount", this.damage);
		nbt.setInteger("FallHurtMax", this.maxDamage);

		if (this.tileEntityData != null)
		{
			nbt.setTag("TileEntityData", this.tileEntityData);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		if (p_70037_1_.hasKey("TileID", 99))
		{
			this.block = Block.getBlockById(p_70037_1_.getInteger("TileID"));
		}
		else
		{
			this.block = Block.getBlockById(p_70037_1_.getByte("Tile") & 255);
		}

		this.blockMeta = p_70037_1_.getByte("Data") & 255;
		this.aliveTimer = p_70037_1_.getByte("Time") & 255;

		if (p_70037_1_.hasKey("HurtEntities", 99))
		{
			this.field_145809_g = p_70037_1_.getBoolean("HurtEntities");
			this.damage = p_70037_1_.getFloat("FallHurtAmount");
			this.maxDamage = p_70037_1_.getInteger("FallHurtMax");
		}
		else if (this.block == Blocks.anvil)
		{
			this.field_145809_g = true;
		}

		if (p_70037_1_.hasKey("DropItem", 99))
		{
			this.shouldDropItem = p_70037_1_.getBoolean("DropItem");
		}

		if (p_70037_1_.hasKey("TileEntityData", 10))
		{
			this.tileEntityData = p_70037_1_.getCompoundTag("TileEntityData");
		}

		if (this.block.getMaterial() == Material.air)
		{
			this.block = Blocks.sand;
		}
	}

	public void func_145806_a(boolean p_145806_1_)
	{
		this.field_145809_g = p_145806_1_;
	}

	@Override
	public void addEntityCrashInfo(CrashReportCategory p_85029_1_)
	{
		super.addEntityCrashInfo(p_85029_1_);
		p_85029_1_.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(this.block)));
		p_85029_1_.addCrashSection("Immitating block data", Integer.valueOf(this.blockMeta));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@SideOnly(Side.CLIENT)
	public World getWorld()
	{
		return this.worldObj;
	}

	/**
	 * Return whether this entity should be rendered as on fire.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	public Block getBlock()
	{
		return this.block;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(Block.getIdFromBlock(block));
		buffer.writeByte(this.blockMeta & 15);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.block = Block.getBlockById(additionalData.readInt());
		this.blockMeta = additionalData.readByte();
	}
}