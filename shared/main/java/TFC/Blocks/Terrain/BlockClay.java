package TFC.Blocks.Terrain;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import TFC.Reference;
import TFC.Blocks.BlockTerra;

public class BlockClay extends BlockTerra
{
	Icon[] DirtTexture = new Icon[22];
	
	public BlockClay(int par1)
	{
		super(par1, Material.clay);
	}
	
	@Override
	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
	
	@Override
    public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < 22; i++)
		{
			DirtTexture[i] = registerer.registerIcon(Reference.ModID + ":" + "clay/Clay"+i);
		}
    }
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return DirtTexture[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return DirtTexture[par2];
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.clay.itemID;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(4);
	}
	
	@Override
    public int damageDropped(int i) {
        return 0;
    }
	
//	public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList)
//    {
//        if((!world.isBlockOpaqueCube(i+1, j, k) || !world.isBlockOpaqueCube(i-1, j, k) || 
//                !world.isBlockOpaqueCube(i, j, k+1) || !world.isBlockOpaqueCube(i, j, k-1)) && 
//                !world.isBlockOpaqueCube(i, j+1, k))
//        {
//            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i +1,j + 0.5f,k + 1));
//
//            double minX = 0.25;
//            double minZ = 0.25;
//            double maxX = 0.75;
//            double maxZ = 0.75;
//
//            if(!world.isBlockOpaqueCube(i+1, j, k))
//                maxX = 0.5;
//            if(!world.isBlockOpaqueCube(i-1, j, k))
//                minX = 0.5;
//            if(!world.isBlockOpaqueCube(i, j, k+1))
//                maxZ = 0.5;
//            if(!world.isBlockOpaqueCube(i, j, k-1))
//                minZ = 0.5;
//
//            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i + minX, j + 0.5, k + minZ, i + maxX, j + 1, k + maxZ));
//
//        }
//        else
//            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i + 1,j + 1,k +1));
//    }

}
