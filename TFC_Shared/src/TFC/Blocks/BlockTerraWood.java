package TFC.Blocks;

import java.util.Random;

import TFC.Core.TFC_Core;
import TFC.Core.TFC_Core.Direction;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraWood extends Block implements ITextureProvider
{

    public BlockTerraWood(int i) 
    {
        super(i, Material.wood);
    }

    public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 16; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }


    private boolean checkOut(World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) == blockID && world.getBlockMetadata(i, j, k) == l)
        {
            return true;
        }
        return false;
    }

    @Override
    protected int damageDropped(int j) {
        return j;
    }	

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
        if (i == 1)
        {
            return j+144;
        }
        if (i == 0)
        {
            return j+144;
        }
        return j+128;
    }
    public float getHardness(int md)
    {
        switch(md)
        {
            case 5:
            {
                return 5.0F;
            }
            case 6:
            {
                return 4.0F;
            }
            case 0:
            {
                return 5.0F;
            }
            case 11:
            {
                return 3.5F;
            }
            case 13:
            {
                return 3.5F;
            }
            default:
            {
                return 2.0F;
            }
        }
    }

    @Override
    public String getTextureFile() 
    {
        return "/bioxx/terrablocks.png";
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {		
        //we need to make sure teh palyer has the correct tool out
        boolean isAxeorSaw = false;
        ItemStack equip = entityplayer.getCurrentEquippedItem();
        if(equip!=null)
        {
            for(int cnt = 0; cnt < TFC_Core.Axes.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == TFC_Core.Axes[cnt])
                {
                    isAxeorSaw = true;
                }
            }
            for(int cnt = 0; cnt < TFC_Core.Saws.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == TFC_Core.Saws[cnt])
                {
                    isAxeorSaw = true;
                }
            }
            if(!isAxeorSaw && equip.getItem() == TFCItems.FlintPaxel)
            {
                isAxeorSaw = true;
            }
        }
        if(isAxeorSaw)
        {
            ProcessTree(world, i, j, k, l);
        }
        else
        {
            world.setBlockAndMetadata(i, j, k, blockID, l);
        }
    }

    public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
    {
        ProcessTree(world, i, j, k, world.getBlockMetadata(i, j, k));
    }

    private void ProcessTree(World world, int i, int j, int k, int l)
    {
        int x = i;
        int y = 0;
        int z = k;
        boolean checkArray[][][] = new boolean[11][50][11];


        boolean reachedTop = false;
        while(!reachedTop)
        {
            if(l != 9 && l != 15 && world.getBlockId(x, j+y+1, z) == 0)
            {
                reachedTop = true;
            }
            else if((l == 9 || l == 15) && world.getBlockId(x, j+y+1, z) == 0
                    && world.getBlockId(x+1, j+y+1, z) != blockID && world.getBlockId(x-1, j+y+1, z) != blockID && world.getBlockId(x, j+y+1, z+1) != blockID &&
                    world.getBlockId(x, j+y+1, z-1) != blockID && world.getBlockId(x-1, j+y+1, z-1) != blockID && world.getBlockId(x-1, j+y+1, z+1) != blockID && 
                    world.getBlockId(x+1, j+y+1, z+1) != blockID && world.getBlockId(x+1, j+y+1, z-1) != blockID)
            {
                reachedTop = true;
            }

            scanLogs(world,i,j+y,k,l,checkArray,6,y,6);

            y++;
        }
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCItems.Logs.shiftedIndex;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        boolean check = false;
        for(int h = -1; h <= 1; h++)
        {
            for(int g = -1; g <= 1; g++)
            {
                for(int f = -1; f <= 1; f++)
                {
                    if(world.getBlockId(i+h, j+g, k+f) == blockID && world.getBlockMetadata(i+h, j+g, k+f) == world.getBlockMetadata(i, j, k))
                    {
                        check = true;
                    }
                }
            }
        }
        if(!check)
        {
            world.setBlock(i, j, k, 0);
            dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.itemsList[TFCItems.Logs.shiftedIndex],1,l));
        }
    }

    private void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray,int x, int y, int z)
    {
        if(y >= 0)
        {
            checkArray[x][y][z] = true;
            int offsetX = 0;int offsetY = 0;int offsetZ = 0;

            for (offsetY = 0; offsetY <= 1; offsetY++)
            {
                for (offsetX = -2; offsetX <= 2; offsetX++)
                {
                    for (offsetZ = -2; offsetZ <= 2; offsetZ++)
                    {
                        if(x+offsetX < 11 && x+offsetX >= 0 && z+offsetZ < 11 && z+offsetZ >= 0 && y+offsetY < 50 && y+offsetY >= 0)
                        {
                            if(checkOut(world, i+offsetX, j+offsetY, k+offsetZ, l) && !checkArray[x+offsetX][y+offsetY][z+offsetZ])
                            {
                                scanLogs(world,i+offsetX, j+offsetY, k+offsetZ, l, checkArray,x+offsetX,y+offsetY,z+offsetZ);
                            }
                        }
                    }
                }
            }

            world.setBlockWithNotify(i, j, k, 0);
            world.markBlockNeedsUpdate(i, j, k);
            dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.itemsList[TFCItems.Logs.shiftedIndex],1,l));
        }
    }

}
