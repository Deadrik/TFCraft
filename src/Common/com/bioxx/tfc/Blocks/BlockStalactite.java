package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;

public class BlockStalactite extends BlockTerra
{
	private Random r;

	public BlockStalactite()
	{
		super();
		this.setStepSound(soundTypeStone);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (isStalactite(world.getBlockMetadata(i, j, k)) && random.nextInt(80) == 0)
		{
			AxisAlignedBB aabb = getCollisionBoundingBoxFromPool(world, i, j, k);

			double xRand = random.nextFloat() * (aabb.maxX - aabb.minX) + aabb.minX;
			double zRand = random.nextFloat() * (aabb.maxZ - aabb.minZ) + aabb.minZ;

			world.spawnParticle("dripWater", xRand + 0.2, aabb.minY + 0.9,  zRand + 0.2, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k)
	{
		boolean isStalac = isStalactite(access.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(access.getBlockMetadata(i, j, k));
		//int style = access.getBlockMetadata(i, j, k) & 7;
		float f = 0.125F;
		r = new Random(i + (i * k));
		if(isStalac)
		{
			float height = TFC_Core.isRawStone(access.getBlock(i, j + 1, k)) ? 1 : TFC_Core.isRawStone(access.getBlock(i, j + 2, k)) ? 2 : 3;
			f = 0.1f + r.nextFloat()*0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f + r.nextFloat() * 0.5f;
			else height = 1;
			setBlockBounds(width, 1 - height, width, 1f - width, 1, 1F - width);
		}
		else if(isStalag)
		{
			float height = TFC_Core.isRawStone(access.getBlock(i, j - 1, k)) ? 1 : TFC_Core.isRawStone(access.getBlock(i, j - 2, k)) ? 2 : 3;
			f = 0.1f + r.nextFloat()*0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f + r.nextFloat() * 0.5f;
			else height = 1;
			setBlockBounds(width, 0.0F, width, 1f-width, height, 1F - width);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		boolean isStalac = isStalactite(world.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(world.getBlockMetadata(i, j, k));
		//int style = world.getBlockMetadata(i, j, k) & 7;
		float f = 0.125F;
		r = new Random(i + (i * k));
		if(isStalac)
		{
			float height = TFC_Core.isRawStone(world.getBlock(i, j + 1, k)) ? 1 : TFC_Core.isRawStone(world.getBlock(i, j + 2, k)) ? 2 : 3;
			f = 0.1f + r.nextFloat()*0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f + r.nextFloat() * 0.5f;
			else height = 1;

			return AxisAlignedBB.getBoundingBox(i + width, j - height, k + width, i + 1 - width, j + 1, k + 1 - width);
		}
		else if(isStalag)
		{
			float height = TFC_Core.isRawStone(world.getBlock(i, j - 1, k)) ? 1 : TFC_Core.isRawStone(world.getBlock(i, j - 2, k)) ? 2 : 3;
			f = 0.1f + r.nextFloat() * 0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f + r.nextFloat() * 0.5f;
			else height = 1;
			return AxisAlignedBB.getBoundingBox(i+ width, j, k + width, i + 1-width, j+height, k + 1 - width);
		}

		return AxisAlignedBB.getBoundingBox(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j+this.maxY, k + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		boolean isStalac = isStalactite(access.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(access.getBlockMetadata(i, j, k));
		if(isStalac)
		{
			if(TFC_Core.isRawStone(access.getBlock(i, j + 1, k)))
				return access.getBlock(i, j + 1, k).getIcon(0, access.getBlockMetadata(i, j + 1, k));
			else if(TFC_Core.isRawStone(access.getBlock(i, j + 2, k)))
				return access.getBlock(i, j + 2, k).getIcon(0,access.getBlockMetadata(i, j + 2, k));
			else if(TFC_Core.isRawStone(access.getBlock(i, j + 3, k)))
				return access.getBlock(i, j + 3, k).getIcon(0,access.getBlockMetadata(i, j + 3, k));
		}
		else if(isStalag)
		{
			if(TFC_Core.isRawStone(access.getBlock(i, j - 1, k)))
				return access.getBlock(i, j - 1, k).getIcon(0,access.getBlockMetadata(i, j - 1, k));
			else if(TFC_Core.isRawStone(access.getBlock(i, j-2, k)))
				return access.getBlock(i, j - 2, k).getIcon(0,access.getBlockMetadata(i, j - 2, k));
			else if(TFC_Core.isRawStone(access.getBlock(i, j-3, k)))
				return access.getBlock(i, j - 3, k).getIcon(0,access.getBlockMetadata(i, j - 3, k));
		}
		return TFC_Textures.invisibleTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		return TFC_Textures.invisibleTexture;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block b)
	{
		if(!world.isRemote)
		{
			if(!canBlockStay(world, i, j, k))
			{
				world.setBlockToAir(i, j, k);
				return;
			}
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		boolean isStalac = isStalactite(world.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(world.getBlockMetadata(i, j, k));
		int h = 0;
		if(isStalac)
		{
			if(TFC_Core.isRawStone(world.getBlock(i, j + 1, k)))
				h = 1;
			else if(TFC_Core.isRawStone(world.getBlock(i, j + 2, k)))
				h = 2;
			else if(TFC_Core.isRawStone(world.getBlock(i, j + 3, k)))
				h = 3;
			for(int y = h; y > 0; y--)
			{
				if(world.isAirBlock(i, j + y, k))
				{
					return false;
				}
			}
		}
		else if(isStalag)
		{
			if(TFC_Core.isRawStone(world.getBlock(i, j - 1, k)))
				h = 1;
			else if(TFC_Core.isRawStone(world.getBlock(i, j - 2, k)))
				h = 2;
			else if(TFC_Core.isRawStone(world.getBlock(i, j - 3, k)))
				h = 3;
			for(int y = h; y > 0; y--)
			{
				if(world.isAirBlock(i, j - y, k))
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean canReplace(World world, int x, int y, int z, int i, ItemStack is)
	{
		return false;
	}

	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	/**
	 * @return if bit 4 is flipped then this is a stalagmite (Bottom Formation)
	 */
	public boolean isStalagmite(int meta)
	{
		return (meta & 8) > 0;
	}

	/**
	 * @return if bit 4 is not flipped then this is a stalactite (Top Formation)
	 */
	public boolean isStalactite(int meta)
	{
		return (meta & 8) == 0;
	}

	@Override
	public boolean canDropFromExplosion(Explosion ex)
	{
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
