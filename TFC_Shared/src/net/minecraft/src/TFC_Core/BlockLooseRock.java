package net.minecraft.src.TFC_Core;

import java.util.Random;
import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class BlockLooseRock extends BlockTerra implements ITextureProvider
{

	public BlockLooseRock(int par1) 
	{
		super(par1, Material.wood);
		this.setBlockBounds(0.40F, 0.00F, 0.4F, 0.6F, 0.10F, 0.7F);
	}

	public int getRenderType()
	{
		return mod_TFC_Core.looseRockRenderId;
	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	public int idDropped(int i, Random random, int j)
	{
		return Item.flint.shiftedIndex;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if (par1World.getBlockId(par2, par3-1, par4) == 0)
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return;
		}
		if (!Block.blocksList[par1World.getBlockId(par2, par3-1, par4)].isOpaqueCube())
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return;
		}
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

}
