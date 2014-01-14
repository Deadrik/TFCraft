package TFC.Blocks.Vanilla;

import java.util.Random;

import TFC.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockCustomIce extends BlockIce
{
	public BlockCustomIce(int par1)
	{
		super(par1);
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		Material var7 = par1World.getBlockMaterial(par3, par4 - 1, par5);

		if (var7.blocksMovement() || var7.isLiquid())
		{
			par1World.setBlock(par3, par4, par5, getBlockMeltId(par1World,par3,par4,par5,true), 0, 2);
		}
	}

	public int getBlockMeltId(World world, int i, int j, int k, boolean moving){
		int meta = world.getBlockMetadata(i,j,k);
		switch(meta){
		case 0: return moving? Block.waterMoving.blockID : Block.waterStill.blockID;
		case 1: return moving? TFCBlocks.FreshWaterFlowing.blockID : TFCBlocks.FreshWaterStill.blockID;
		default: return 0;
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!world.canBlockFreeze(i, j, k, false))
		{
			if (world.getBlockId(i, j+1, k) == Block.snow.blockID)
			{
				int meta = world.getBlockMetadata(i, j+1, k);
				if (meta > 0) {
					world.setBlockMetadataWithNotify(i, j+1, k, meta-1, 2);
				} else {
					world.setBlockToAir(i, j+1, k);
				}
			} else {
				this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				world.setBlock(i, j, k, getBlockMeltId(world,i,j,k,false), 0, 2);
			}
			this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			if(j > 143){
				world.setBlock(i, j, k, getBlockMeltId(world,i,j,k,true), 0, 2);
			} else {
				world.setBlock(i, j, k, getBlockMeltId(world,i,j,k,false), 0, 2);
			}
		}
	}
}
