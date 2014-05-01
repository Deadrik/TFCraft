package com.bioxx.tfc.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Blocks.BlockTerra;

public class BlockClay extends BlockTerra
{
	IIcon[] DirtTexture = new IIcon[22];

	public BlockClay()
	{
		super(Material.clay);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < 16; i++)
			list.add(new ItemStack(this,1,i));
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < 20; i++)
			DirtTexture[i] = registerer.registerIcon(Reference.ModID + ":" + "clay/Clay"+i);
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return DirtTexture[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return DirtTexture[par2];
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return TFCItems.ClayBall;
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
	public int damageDropped(int i)
	{
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
