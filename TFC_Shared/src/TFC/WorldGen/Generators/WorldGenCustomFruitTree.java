package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.TileEntities.TileEntityFruitTreeWood;

import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenCustomFruitTree extends WorldGenerator
{
    private int leavesId;
    private int metaId;

    public WorldGenCustomFruitTree(boolean flag, int id, int meta)
    {
        super(flag);
        leavesId=id;
        metaId = meta;

    }
    
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) == 0 && j < 250)
        {
            world.setBlockAndMetadata(i, j, k, TFCBlocks.fruitTreeWood.blockID, metaId);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setHeight(0);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setBirth();
            
            if(world.getBlockId(i, j+1, k) == 0)
            {
                world.setBlockAndMetadata(i, j+1, k, TFCBlocks.fruitTreeWood.blockID, metaId);
                ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setTrunk(true);
                ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setHeight(1);
                ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setBirth();
                
                if(world.getBlockId(i, j+2, k) == 0)
                {
                    world.setBlockAndMetadata(i, j+2, k, TFCBlocks.fruitTreeWood.blockID, metaId);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+2, k)).setTrunk(true);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+2, k)).setHeight(2);
                    ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+2, k)).setBirth();

                    if(world.getBlockId(i+1, j+2, k) == 0 || world.getBlockId(i+1, j+2, k) == leavesId)
                    {
                        world.setBlockAndMetadata(i+1, j+2, k, TFCBlocks.fruitTreeWood.blockID, metaId);
                        SurroundWithLeaves(world,i+1,j+2,k);
                    }
                    if(world.getBlockId(i-1, j+2, k) == 0 || world.getBlockId(i-1, j+2, k-1) == leavesId)
                    {
                        world.setBlockAndMetadata(i-1, j+2, k, TFCBlocks.fruitTreeWood.blockID, metaId);
                        SurroundWithLeaves(world,i-1,j+2,k);
                    }
                    if(world.getBlockId(i, j+2, k+1) == 0 || world.getBlockId(i, j+2, k+1) == leavesId)
                    {
                        world.setBlockAndMetadata(i, j+2, k+1, TFCBlocks.fruitTreeWood.blockID, metaId);
                        SurroundWithLeaves(world,i,j+2,k+1);
                    }
                    if(world.getBlockId(i, j+2, k-1) == 0 || world.getBlockId(i, j+2, k-1) == leavesId)
                    {
                        world.setBlockAndMetadata(i, j+2, k-1, TFCBlocks.fruitTreeWood.blockID, metaId);
                        SurroundWithLeaves(world,i,j+2,k-1);
                    }
                    
                    if(world.getBlockId(i, j+3, k) == 0 || world.getBlockId(i, j+3, k) == leavesId)
                    {
                        world.setBlockAndMetadata(i, j+3, k, TFCBlocks.fruitTreeWood.blockID, metaId);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+3, k)).setTrunk(true);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+3, k)).setHeight(3);
                        ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+3, k)).setBirth();
                        SurroundWithLeaves(world,i,j+3,k);
                    }
                }         
            }  
        }
        return true;
    }

    public void SurroundWithLeaves(World world, int i, int j, int k)
    {
        for (int y = 1; y >= 0; y--)
        {
            for (int x = 1; x >= -1; x--)
            {
                for (int z = 1; z >= -1; z--)
                {
                    if(world.getBlockId(i+x, j+y, k+z) == 0) {
                        world.setBlockAndMetadata(i+x, j+y, k+z, leavesId, metaId);
                    }
                }
            }
        }
    }
}
