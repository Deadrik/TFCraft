package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.*;

public class BlockTerraClay extends BlockTerra2
{
	public BlockTerraClay(int par1, int par2)
	{
		super(par1, par2, Material.clay);
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return this.blockIndexInTexture + par1IBlockAccess.getBlockMetadata(par2, par3, par4);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return this.blockIndexInTexture + par2;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.clay.shiftedIndex;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(4);
	}
	
	public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList)
    {
        if((!world.isBlockOpaqueCube(i+1, j, k) || !world.isBlockOpaqueCube(i-1, j, k) || 
                !world.isBlockOpaqueCube(i, j, k+1) || !world.isBlockOpaqueCube(i, j, k-1)) && 
                !world.isBlockOpaqueCube(i, j+1, k))
        {
            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i +1,j + 0.5f,k + 1));

            double minX = 0.25;
            double minZ = 0.25;
            double maxX = 0.75;
            double maxZ = 0.75;

            if(!world.isBlockOpaqueCube(i+1, j, k))
                maxX = 0.5;
            if(!world.isBlockOpaqueCube(i-1, j, k))
                minX = 0.5;
            if(!world.isBlockOpaqueCube(i, j, k+1))
                maxZ = 0.5;
            if(!world.isBlockOpaqueCube(i, j, k-1))
                minZ = 0.5;

            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i + minX, j + 0.5, k + minZ, i + maxX, j + 1, k + maxZ));

        }
        else
            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i + 1,j + 1,k +1));
    }

}
