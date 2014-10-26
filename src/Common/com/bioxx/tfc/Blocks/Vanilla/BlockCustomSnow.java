package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFC_Climate;

public class BlockCustomSnow extends BlockTerra
{
	public BlockCustomSnow()
	{
		super(Material.snow);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		Block b = world.getBlock(i, j - 1, k);
		boolean flag = false;
		if (b == TFCBlocks.Ice)
			return false;
		if (World.doesBlockHaveSolidTopSurface(world, i, j-1, k))
			flag =  true;
		if (b == TFCBlocks.Leaves || b == TFCBlocks.Leaves2)
			flag =  true;
		return flag;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int l = world.getBlockMetadata(x, y, z) & 7;
		float f = 0.125F;
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + f, z + this.maxZ);
	}
	@Override
	public int getRenderType()
	{
		return TFCBlocks.snowRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		dropBlockAsItem(world, x, y, z, meta, 0);
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
	}

	@Override
	public Item getItemDropped(int par1, Random R, int par3)
	{
		return Items.snowball;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		int var5 = world.getBlockMetadata(x, y, z);
		if(var5 > 0)
		{
			double speed = 0.58D + 0.4D * (15 / var5 / 15);
			entity.motionX *= speed;
			entity.motionZ *= speed;
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!canPlaceBlockAt(world, x, y, z))
		{
			world.setBlock(x, y, z, Blocks.air, 0, 2);
		}
	}

	@Override
	public int quantityDropped(Random R)
	{
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		int var5 = bAccess.getBlockMetadata(x, y, z) & 7;
		float var6 = 2 * (1 + var5) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
	}

	@Override
	public int tickRate(World world)
	{
		return 50;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random R)
	{
		if(!canPlaceBlockAt(world, x, y, z))
		{
			world.setBlock(x, y, z, Blocks.air, 0, 2);
			return;
		}
		int meta = world.getBlockMetadata(x, y, z);
		if (world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > 11)
		{
			if(meta > 1 && R.nextInt(5) == 0)
				world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
			else if(meta == 1 && R.nextInt(5) == 0)
				world.setBlock(x, y, z, Blocks.air, 0, 0x2);
		}
		float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
		if(world.isRaining() && temp <= 0)//Raining and Below Freezing
		{
			if(meta < 15 && R.nextInt(20) == 0 && world.getBlock(x, y - 1, z).getMaterial() != Material.leaves)
			{
				if (canAddSnow(world, x, y, z, meta))
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
			}
			else if(meta < 3 && R.nextInt(20) == 0 && world.getBlock(x, y - 1, z).getMaterial() == Material.leaves)
			{
				if (canAddSnow(world, x, y, z, meta))
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
			}
		}
		else if(world.isRaining() && temp > 0)//Raining and above freezing
		{
			if(meta <= 15 && world.getBlock(x, y - 1, z).getMaterial() != Material.leaves)
			{
				if(meta > 1)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
			else if(meta <= 15 && world.getBlock(x, y-1, z).getMaterial() == Material.leaves)
			{
				if(meta > 1)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
		else if(TFC_Climate.getHeightAdjustedTemp(world, x, y, z) >= 0F)//Above fReezing
		{
			if(meta > 0 )
				world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
			else
				world.setBlock(x, y, z, Blocks.air, 0, 0x2);
		}
		//else//Below Freezing
		//{
		//	if(meta > 1 && par5Random.nextInt(5) == 0)
		//	{
		//		world.setBlockMetadataWithNotify(par2, par3, par4, meta-1, 2);
		//	}
		//	else if(meta == 1 && par5Random.nextInt(5) == 0)
		//	{
		//		world.setBlockToAir(par2, par3, par4);
		//	}
		//}
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		this.blockIcon = registerer.registerIcon(Reference.ModID + ":snow");
	}

	private boolean canAddSnowCheckNeighbors(World world, int x, int y, int z, int meta)
	{
		if (!this.canPlaceBlockAt(world, x, y, z))
			return true;
		if (world.getBlock(x, y, z).getMaterial() != Material.snow)
			return false;
		if ( world.getBlock(x, y, z).getMaterial() == Material.snow && meta > world.getBlockMetadata(x, y, z))
			return false;

		return true;
	}

	private boolean canAddSnow(World world, int x, int y, int z, int meta)
	{
		if (!canAddSnowCheckNeighbors(world, x + 1, y, z, meta))
			return false;
		if (!canAddSnowCheckNeighbors(world, x - 1, y, z, meta))
			return false;
		if (!canAddSnowCheckNeighbors(world, x, y, z + 1, meta))
			return false;
		if (!canAddSnowCheckNeighbors(world, x, y, z - 1, meta))
			return false;

		return true;
	}
}
