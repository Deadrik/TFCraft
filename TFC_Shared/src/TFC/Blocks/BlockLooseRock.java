package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
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
		return TFCBlocks.looseRockRenderId;
	}
	
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        return true;
    }

	public void harvestBlock(World world, EntityPlayer entityplayer, int xCoord, int yCoord, int zCoord, int l)
	{		
	    TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(xCoord, zCoord);
	    
	    DataLayer rockLayer = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
	    
	    ArrayList coreSample = new ArrayList<Item>();
	    ArrayList coreSampleStacks = new ArrayList<ItemStack>();
	    
	    for(int x = -15; x <= 15; x++)
        {
            for(int z = -15; z <= 15; z++)
            {
                for(int y = yCoord; y > yCoord-35; y--)
                {
                    if(world.getBlockId(xCoord+x, y, zCoord+z) == TFCBlocks.terraOre.blockID)
                    {
                        int m = world.getBlockMetadata(xCoord+x, y, zCoord+z);
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
	        dropBlockAsItem_do(world, xCoord, yCoord, zCoord,(ItemStack)coreSampleStacks.toArray()[R.nextInt(coreSampleStacks.toArray().length)]);
	    }
	    else
	        dropBlockAsItem_do(world, xCoord, yCoord, zCoord, new ItemStack(TFCItems.LooseRock, 1, TFC_Core.getMetaForGrass(rockLayer.data1, rockLayer.data2)));
	    
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
