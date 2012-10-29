package TFC.Blocks;

import java.util.Random;

import TFC.*;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSulfur extends Block
{

	public BlockSulfur(int i, Material material)
	{
		super(i, material);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return 252+j;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}

	public int getRenderType()
	{
		return TFCBlocks.sulfurRenderId;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks.png";

	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	    dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.SulfurPowder, quantityDropped(new Random())));
	}
	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.SulfurPowder.shiftedIndex;
	}

	public boolean isBlockNormalCube(World world, int i, int j, int k)
	{
		return false;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		int num = 0;
		if(world.isBlockNormalCube(i, j, k+1))
		{
			num++;
		}
		if(world.isBlockNormalCube(i, j, k-1))
		{
			num++;
		}
		if(world.isBlockNormalCube(i+1, j, k))
		{
			num++;
		}
		if(world.isBlockNormalCube(i-1, j, k))
		{
			num++;
		}
		if(world.isBlockNormalCube(i, j+1, k))
		{
			num++;
		}
		if(world.isBlockNormalCube(i, j-1, k))
		{
			num++;
		}
		if(num == 0)
		{
			world.setBlock(i, j, k, 0);
			this.dropBlockAsItem(world, i, j, k, l, 0);
		}
	}


	public int quantityDropped(Random random)
	{
		return 1 + random.nextInt(5);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int num = 0;
		if(iblockaccess.isBlockNormalCube(i, j, k+1))
		{
			setBlockBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i, j, k-1))
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i+1, j, k))
		{
			setBlockBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i-1, j, k))
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i, j+1, k))
		{
			setBlockBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i, j-1, k))
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
			num++;
		}
		if(num > 1)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

}
