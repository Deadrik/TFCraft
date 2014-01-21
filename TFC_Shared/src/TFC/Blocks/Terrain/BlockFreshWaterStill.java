package TFC.Blocks.Terrain;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.Vanilla.BlockCustomStationary;

public class BlockFreshWaterStill extends BlockCustomStationary 
{
	public BlockFreshWaterStill(int par1) 
	{
		super(par1, Material.water);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return TerraFirmaCraft.proxy.waterColorMultiplier(par1IBlockAccess, par2, par3, par4);
	}
	
	@Override
	public void breakBlock(World world, int i, int j, int k, int id, int l)
	{
		Material m = world.getBlockMaterial(i, j, k);
		int blockID = world.getBlockId(i,j,k);
		if(blockID == Block.ice.blockID){
			//world.setBlockMetadataWithNotify(i,j,k,1,1);
		}
		super.breakBlock(world, i, j, k, id, l);
		
	}

	@Override
	protected void setFreezeBlock(World world, int i, int j, int k, Random rand){
		Material mat = world.getBlockMaterial(i,j,k);
		if(mat == Material.water){
			world.setBlock(i,j,k, Block.ice.blockID,1,1);
		}
	}
	
}
