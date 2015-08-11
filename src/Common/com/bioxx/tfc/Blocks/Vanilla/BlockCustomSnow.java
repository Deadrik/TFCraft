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

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.TFCBlocks;

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
		Block block = world.getBlock(i, j - 1, k);
		
		if (block == TFCBlocks.Ice)
			return false;
		if (block == TFCBlocks.Leaves || block == TFCBlocks.Leaves2)
			return true;
		if (block == TFCBlocks.Thatch)
			return true;
		if (World.doesBlockHaveSolidTopSurface(world, i, j-1, k))
			return true;
		
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
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
		int meta = world.getBlockMetadata(x, y, z) & 7;
		if(meta > 0)
		{
			double speed = 0.58D + 0.4D * (15 / meta / 15);  // CH: intentional? same as  = (meta == 1) ? 0.98 : 0.58;
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
		int meta = bAccess.getBlockMetadata(x, y, z) & 7;
		float top = (meta + 1) / 8.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, top, 1.0F);
	}

	@Override
	public int tickRate(World world)
	{
		return 50;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random R)
	{
		if (!canPlaceBlockAt(world, x, y, z))
		{
			world.setBlock(x, y, z, Blocks.air, 0, 2);
			return;
		}
		
		int meta = world.getBlockMetadata(x, y, z) & 7;
		
		if (world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > 11)
		{
			if (R.nextInt(5) == 0)
			{
				if(meta > 0)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
		
		float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
		
		if (temp <= 0 && world.isRaining())  //Raining and Below Freezing
		{
			if (R.nextInt(20) == 0)
			{
				int max = (world.getBlock(x, y - 1, z).getMaterial() == Material.leaves) ? 3 : 7;
				if(meta < max && canAddSnow(world, x, y, z, meta))
				{
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
				}
			}
		}
		else if (temp > 10)  // to hot for snow (probably chunk loading error)
		{
			world.setBlock(x, y, z, Blocks.air, 0, 0x2);
		}
		else if (temp > 0 && world.isRaining())  //Raining and above freezing
		{
			if (R.nextInt(5) == 0)
			{
				if (meta > 0)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
		else if (temp > 0)  //Above freezing, not raining
		{
			if (R.nextInt(20) == 0)
			{
				if(meta > 0)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		this.blockIcon = registerer.registerIcon(Reference.ModID + ":snow");
	}

	// CH: this is confusing me: snow accumulates on the top of a step (one block higher than another)
	// but not on the bottom of the step
	private boolean canAddSnowCheckNeighbors(World world, int x, int y, int z, int meta)
	{
		if (!this.canPlaceBlockAt(world, x, y, z))
			return true;
		if (world.getBlock(x, y, z).getMaterial() != Material.snow)
			return false;
		if (meta > (world.getBlockMetadata(x, y, z) & 7))
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
