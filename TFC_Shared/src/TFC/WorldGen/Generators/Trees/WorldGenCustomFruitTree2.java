package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.TileEntities.TileEntityFruitTreeWood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class WorldGenCustomFruitTree2 extends WorldGenerator
{
    private int leavesId;
    private int metaId;

    public WorldGenCustomFruitTree2(boolean flag, int id, int meta)
    {
        super(flag);
        leavesId=id;
        metaId = meta;

    }
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) == 0 && j < 250)
        {
            world.setBlockAndMetadataWithNotify(i, j, k, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setHeight(0);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j, k)).setBirth();
        }
        if(world.getBlockId(i, j+1, k) == 0)
        {
            world.setBlockAndMetadataWithNotify(i, j+1, k, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setHeight(1);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+1, k)).setBirth();
        }
        if(world.getBlockId(i, j+2, k) == 0)
        {
            world.setBlockAndMetadataWithNotify(i, j+2, k, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+2, k)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+2, k)).setHeight(2);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+2, k)).setBirth();

            if(world.getBlockId(i+1, j+2, k) == 0)
            {
                world.setBlockAndMetadataWithNotify(i+1, j+2, k, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
                SurroundWithLeaves(world,i+1,j+2,k);
            }
            if(world.getBlockId(i-1, j+2, k) == 0)
            {
                world.setBlockAndMetadataWithNotify(i-1, j+2, k, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
                SurroundWithLeaves(world,i-1,j+2,k);
            }
            if(world.getBlockId(i, j+2, k+1) == 0)
            {
                world.setBlockAndMetadataWithNotify(i, j+2, k+1, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
                SurroundWithLeaves(world,i,j+2,k+1);
            }
            if(world.getBlockId(i, j+2, k-1) == 0)
            {
                world.setBlockAndMetadataWithNotify(i, j+2, k-1, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
                SurroundWithLeaves(world,i,j+2,k-1);
            }

        }
        if(world.getBlockId(i, j+3, k) == 0 || world.getBlockId(i, j+3, k) == leavesId)
        {
            world.setBlockAndMetadataWithNotify(i, j+3, k, TFCBlocks.fruitTreeWood.blockID, metaId+8, 3);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+3, k)).setTrunk(true);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+3, k)).setHeight(3);
            ((TileEntityFruitTreeWood)world.getBlockTileEntity(i, j+3, k)).setBirth();
            SurroundWithLeaves(world,i,j+3,k);
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
                        world.setBlockAndMetadataWithNotify(i+x, j+y, k+z, leavesId, metaId, 3);
                    }
                }
            }
        }
    }
}
