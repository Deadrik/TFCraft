package TFC.Blocks.Vanilla;

import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import TFC.Core.TFC_Core;


public class BlockCustomReed extends BlockReed implements IPlantable
{
	public BlockCustomReed(int par1)
	{
		super(par1);
		float var3 = 0.375F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3 - 1, par4);
		boolean correctSoil = TFC_Core.isSoil(var5) || TFC_Core.isSand(var5);
		return var5 == this.blockID ? true : !correctSoil ? false : par1World.getBlockMaterial(par2 - 1, par3 - 1, par4) == Material.water ? true : par1World.getBlockMaterial(par2 + 1, par3 - 1, par4) == Material.water ? true : par1World.getBlockMaterial(par2, par3 - 1, par4 - 1) == Material.water ? true : par1World.getBlockMaterial(par2, par3 - 1, par4 + 1) == Material.water;
	}

}
