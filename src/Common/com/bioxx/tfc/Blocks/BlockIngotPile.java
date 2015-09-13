package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TileEntities.TEIngotPile;

public class BlockIngotPile extends BlockTerraContainer
{
	private Random random = new Random();

	public BlockIngotPile()
	{
		super(Material.iron);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			world.getTileEntity(x, y, z).validate();
			return true;
		}
		else
		{
			if((TEIngotPile)world.getTileEntity(x, y, z) != null)
			{
				TEIngotPile tileentityingotpile;
				tileentityingotpile = (TEIngotPile)world.getTileEntity(x, y, z);

				if (tileentityingotpile.getStackInSlot(0)==null)
				{
					world.setBlockToAir(x, y, z);
					return false;
				}

				if (!entityplayer.isSneaking() && tileentityingotpile.getStackInSlot(0) != null)
				{
					if (tileentityingotpile.getStackInSlot(0).stackSize > 0)
						tileentityingotpile.injectContents(0, -1);

					world.spawnEntityInWorld(new EntityItem(world,tileentityingotpile.xCoord,
							tileentityingotpile.yCoord + 1, tileentityingotpile.zCoord, new ItemStack(tileentityingotpile.getStackInSlot(0).getItem(), 1, tileentityingotpile.getStackInSlot(0).getItemDamage())));
					world.notifyBlockOfNeighborChange(x, y + 1, z, this);

					if (tileentityingotpile.getStackInSlot(0).stackSize < 1)
						world.setBlockToAir(x, y, z);

					world.markBlockForUpdate(x, y, z);
					//tileentityingotpile.broadcastPacketInRange(tileentityingotpile.createUpdatePacket());
				}
			}
			return true;
		}
	}

	public void combineIngotsDown(World world, int x, int y, int z)
	{
		TEIngotPile teip = (TEIngotPile)world.getTileEntity(x, y, z);
		TEIngotPile teipBottom = (TEIngotPile)world.getTileEntity(x, y - 1, z);

		int bottomSize = teipBottom.getStackInSlot(0).stackSize;
		int topSize = teip.getStackInSlot(0).stackSize;

		if(bottomSize < 64)
		{
			bottomSize = bottomSize + topSize;
			int m2 = 0;
			if(bottomSize > 64)
			{
				m2 = bottomSize - 64;
				bottomSize = 64;
			}
			teipBottom.storage[0] = new ItemStack(teipBottom.storage[0].getItem(), bottomSize, teipBottom.storage[0].getItemDamage());

			if(m2 > 0)
			{
				teip.injectContents(0, m2-topSize);
				world.notifyBlockOfNeighborChange(x, y + 1, z, this);
				world.markBlockForUpdate(teip.xCoord, teip.yCoord, teip.zCoord);
				//teip.broadcastPacketInRange(teip.createUpdatePacket());
			}
			else
			{
				teip.storage[0] = null;
				world.setBlockToAir(x, y, z);
			}
		}
	}

	public void combineIngotsUp(World world, int x, int y, int z)
	{
		TEIngotPile teip = (TEIngotPile)world.getTileEntity(x, y + 1, z);
		TEIngotPile teipBottom = (TEIngotPile)world.getTileEntity(x, y, z);

		int bottomSize = teipBottom.getStackInSlot(0).stackSize;
		int topSize = teip.getStackInSlot(0).stackSize;

		if(bottomSize < 64)
		{
			bottomSize = bottomSize + topSize;
			int m2 = 0;
			if(bottomSize > 64)
			{
				m2 = bottomSize - 64;
				bottomSize = 64;
			}
			teipBottom.storage[0] = new ItemStack(teipBottom.storage[0].getItem(), bottomSize, teipBottom.storage[0].getItemDamage());

			if(m2 > 0)
			{
				teip.injectContents(0, m2 - topSize);
				world.notifyBlockOfNeighborChange(x, y + 2, z, this);
				world.markBlockForUpdate(teip.xCoord, teip.yCoord, teip.zCoord);
				//teip.broadcastPacketInRange(teip.createUpdatePacket());
			}
			else
				world.setBlockToAir(x, y + 1, z);
		}
	}
	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		//int meta = world.getBlockMetadata(x, y, z);
		//int direction = getDirectionFromMetadata(meta);
		TEIngotPile te = (TEIngotPile)world.getTileEntity(x, y, z);

		if (te != null && te.getStackInSlot(0) != null)
			return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + ((te.getStackInSlot(0).stackSize + 7) / 8) * 0.125, (double)z + 1);
		//else

		return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + 0.25, (double)z + 1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		//int meta = world.getBlockMetadata(x, y, z);
		//int direction = getDirectionFromMetadata(meta);
		TEIngotPile te = (TEIngotPile)world.getTileEntity(x, y, z);

		if (te.getStackInSlot(0)!=null)
			return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + ((te.getStackInSlot(0).stackSize + 7) / 8) * 0.125, (double)z + 1);
		else
			return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + 0.25, (double)z + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		//int meta = bAccess.getBlockMetadata(x, y, z);
		//int direction = getDirectionFromMetadata(meta);
		TEIngotPile te = (TEIngotPile)bAccess.getTileEntity(x, y, z);

		if (te.getStackInSlot(0)!=null)
			this.setBlockBounds(0f, 0f, 0f, 1f, (float) (((te.getStackInSlot(0).stackSize + 7)/8)*0.125), 1f);
		else
			this.setBlockBounds(0f, 0f, 0f, 0f, 0.25f, 0f);
	}

	/*@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		int meta = getAnvilTypeFromMeta(j);
		int offset = meta;

		return blockIndexInTexture + getDamage(world,(TileEntityIngotPile)TE);
		/*if(i %2 == 0) {
			return blockIndexInTexture+ 1 + offset;
		} else if(i == 1) {
			return blockIndexInTexture+ 1 + offset;
		} else if(i == 2) {
			return blockIndexInTexture + offset;
		} else if(i == 3) {
			return blockIndexInTexture + offset;
		} else if(i == 4) {
			return blockIndexInTexture + offset;
		} else {
			return blockIndexInTexture + offset;
		}
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	public int getRenderType()
	{
		return 22;//TFCBlocks.IngotPileRenderId;//ingotpileId;
	}

	public int getStack(World world,TEIngotPile tt)
	{
		if (world.getTileEntity(tt.xCoord, tt.yCoord, tt.zCoord) instanceof TEIngotPile)
		{
			TEIngotPile te = ((TEIngotPile) world.getTileEntity(tt.xCoord, tt.yCoord, tt.zCoord));

			return te.getStackInSlot(0) != null ? te.getStackInSlot(0).stackSize : 0;
		}

		return 0;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int side)
	{
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack is)
	{
		super.onBlockPlacedBy(world, x, y, z, entityliving, is);
		int meta = world.getBlockMetadata(x, y, z);

		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
			byte0 = 8;
		if(l == 1)//-x
			byte0 = 0;
		if(l == 2)//-z
			byte0 = 8;
		if(l == 3)//+x
			byte0 = 0;

		byte0 += meta;
		world.setBlockMetadataWithNotify(x, y, z, byte0, 0x2);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int meta)
	{
		TEIngotPile te = (TEIngotPile)world.getTileEntity(x, y, z);
		if (te != null)
		{
			for (int var6 = 0; var6 < te.getSizeInventory(); ++var6)
			{
				ItemStack var7 = te.getStackInSlot(var6);

				if (var7 != null)
				{
					float var8 = this.random.nextFloat() * 0.8F + 0.1F;
					float var9 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = this.random.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; world.spawnEntityInWorld(var12))
					{
						int var11 = this.random.nextInt(21) + 10;

						if (var11 > var7.stackSize)
							var11 = var7.stackSize;

						var7.stackSize -= var11;
						var12 = new EntityItem(world, x + var8, y + var9, z + var10, new ItemStack(var7.getItem(), var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)this.random.nextGaussian() * var13;
						var12.motionY = (float)this.random.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)this.random.nextGaussian() * var13;

						if (var7.hasTagCompound())
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
					}
				}
			}
			super.breakBlock(world, x, y, z, b, meta);
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		if(!world.isRemote)
		{
			TEIngotPile te = (TEIngotPile)world.getTileEntity(i, j, k);
			if(te != null && te.getStackInSlot(0) != null)
			{
				EntityItem ei = new EntityItem(world, i, j, k, te.getStackInSlot(0));
				world.spawnEntityInWorld(ei);
			}
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public static int getAnvilTypeFromMeta(int j)
	{
		int l = 7;
		return j & l;
	}

	public static int getDirectionFromMetadata(int i)
	{
		int d = i >> 3;

		if (d == 1)
			return 1;
		else
			return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEIngotPile();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			if ( !world.isSideSolid(x, y - 1, z, ForgeDirection.UP) && world.getTileEntity(x, y, z) instanceof TEIngotPile)
			{
				TEIngotPile ingotPile = (TEIngotPile) world.getTileEntity(x, y, z);
				Item ingot = ingotPile.storage[0] != null ? ingotPile.storage[0].getItem() : null;

				if (world.getBlock(x, y - 1, z) == this && world.getTileEntity(x, y - 1, z) instanceof TEIngotPile)
				{
					TEIngotPile lowerPile = (TEIngotPile) world.getTileEntity(x, y - 1, z);
					Item lowerIngot = lowerPile.storage[0] != null ? lowerPile.storage[0].getItem() : null;

					if (ingot == lowerIngot)
						combineIngotsDown(world, x, y, z);
				}
				else if (world.getBlock(x, y + 1, z) == this && world.getTileEntity(x, y + 1, z) instanceof TEIngotPile)
				{
					TEIngotPile upperPile = (TEIngotPile) world.getTileEntity(x, y + 1, z);
					Item upperIngot = upperPile.storage[0] != null ? upperPile.storage[0].getItem() : null;

					if (ingot == upperIngot)
						combineIngotsUp(world, x, y, z);
				}
				else
				{
					ingotPile.ejectContents();
					world.setBlockToAir(x, y, z);
					return;
				}
			}
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
