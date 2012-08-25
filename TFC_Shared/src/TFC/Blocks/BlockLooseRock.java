package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.WorldGen.TFCBiome;
import net.minecraft.src.*;

public class BlockLooseRock extends BlockTerra
{

	public BlockLooseRock(int par1) 
	{
		super(par1, Material.wood);
		this.setBlockBounds(0.40F, 0.00F, 0.4F, 0.6F, 0.10F, 0.7F);
	}

	public int getRenderType()
	{
		return mod_TFC.looseRockRenderId;
	}
	
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        return true;
    }

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
	    TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(i, k);
	    int off = 0;
	    
	    if(biome.SurfaceType == mod_TFC.terraStoneSed.blockID) off = 3;
	    else if(biome.SurfaceType == mod_TFC.terraStoneIgEx.blockID) off = 13;
	    else if(biome.SurfaceType == mod_TFC.terraStoneMM.blockID) off = 17;
	    
	    ArrayList coreSample = new ArrayList<Item>();
	    ArrayList coreSampleStacks = new ArrayList<ItemStack>();
	    
	    for(int x = -15; x <= 15; x++)
        {
            for(int z = -15; z <= 15; z++)
            {
                for(int y = j; y > j-35; y--)
                {
                    if(world.getBlockId(i+x, y, k+z) == mod_TFC.terraOre.blockID)
                    {
                        int m = world.getBlockMetadata(i+x, y, k+z);
                        if(!coreSample.contains(BlockTerraOre.getDroppedItem(m)))
                        {
                            //coreSample.add(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC_Core.terraOre).damageDropped(meta)));
                            if(m!= 14 && m != 15)
                            {
                                coreSample.add(BlockTerraOre.getDroppedItem(m));
                                coreSampleStacks.add(new ItemStack(BlockTerraOre.getDroppedItem(m), 1, m));
                            }
                        }
                    }
                }
            }
        }
	    Random R = new Random();
	    
	    if(!coreSampleStacks.isEmpty() && R.nextInt(3) == 0)
	    {
	        dropBlockAsItem_do(world, i, j, k,(ItemStack)coreSampleStacks.toArray()[R.nextInt(coreSampleStacks.toArray().length)]);
	    }
	    else
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

	public void onNeighborBlockChange(World world, int i, int j, int k, int par5)
	{
		if (world.getBlockId(i, j-1, k) == 0)
		{
		    this.harvestBlock(world,null,i,j,k,par5);
			world.setBlockWithNotify(i, j, k, 0);
			return;
		}
		if (!Block.blocksList[world.getBlockId(i, j-1, k)].isOpaqueCube())
		{
		    this.harvestBlock(world,null,i,j,k,par5);
			world.setBlockWithNotify(i, j, k, 0);
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
