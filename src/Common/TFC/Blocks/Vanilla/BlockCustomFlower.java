package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import TFC.Core.TFC_Core;

public class BlockCustomFlower extends BlockFlower
{
	public BlockCustomFlower(int par1)
	{
		super(par1);
		this.setTickRandomly(true);
		float var4 = 0.2F;
		this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && this.canThisPlantGrowOnThisBlock(par1World.getBlock(par2, par3 - 1, par4));
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		Block var5 = par1World.getBlock(par2, par3, par4);
		return (par1World.isAirBlock(par2, par3, par4) || var5.getMaterial().isReplaceable()) && this.canThisPlantGrowOnThisBlock(var5);
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isSoil(block);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public int getRenderType()
	{
		return 1;
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
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		this.checkAndDropBlock(par1World, par2, par3, par4);
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		this.checkAndDropBlock(par1World, par2, par3, par4);
	}
}
