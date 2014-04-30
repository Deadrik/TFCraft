package com.bioxx.tfc.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.api.IPipeConnectable;

public class BlockPipeValve extends BlockPipeBasic implements IPipeConnectable
{
	public BlockPipeValve(Material material)
	{
		super(material);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.pipeValveRenderId;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		toggle(world, x, y, z, "Right click activation");
		return true;
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int l = getSide(par1IBlockAccess,par2, par3, par4);
		if(l == 0 || l == 5)
		{
			if(par5 == 0 || par5 == 1)
			{
				return icon[0];
			}
		}
		if(l == 1 || l == 4)
		{
			if(par5 == 4 || par5 == 5)
			{
				return icon[0];
			}
		}
		if(l == 2 || l == 3)
		{
			if(par5 == 2 || par5 == 3)
			{
				return icon[0];
			}
		}
		return icon[1];
	}

	private void toggle(World world, int x, int y, int z, String source)
	{
		if(!world.isRemote)
		{
			int l = world.getBlockMetadata(x, y, z);
			System.out.println(source + "; before: " + l);
			world.setBlockMetadataWithNotify(x, y, z, (l + 8)%16, 0);
			System.out.println(source + "; after: " + world.getBlockMetadata(x, y, z));
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.click", 0.3F, 0.5F);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		if (world.isBlockIndirectlyGettingPowered(i, j, k))
		{
			toggle(world, i, j, k, "Neighbour power");
		}
	}
}
