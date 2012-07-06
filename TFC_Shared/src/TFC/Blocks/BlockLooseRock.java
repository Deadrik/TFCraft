package TFC.Blocks;

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
	    BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
	    int off = 0;
	    
	    if(biome.SurfaceType == mod_TFC_Core.terraStoneSed.blockID) off = 3;
	    else if(biome.SurfaceType == mod_TFC_Core.terraStoneIgEx.blockID) off = 13;
	    else if(biome.SurfaceType == mod_TFC_Core.terraStoneMM.blockID) off = 17;
	    
	    dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.LooseRock, 1, biome.SurfaceMeta+off));
	    
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.LooseRock.shiftedIndex;
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
	
	@Override
    public String getTextureFile()
    {
        return "/bioxx/terraRock.png";
    }

}
