package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraIgIn extends BlockCollapsable
{
    public BlockTerraIgIn(int i, Material material,int id) {
        super(i,0, material, id);
    }

    public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 3; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }

    /*
     * Mapping from metadata value to damage value
     */
    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
        return blockIndexInTexture + j;
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {	
        //super.harvestBlock(world, entityplayer, i, j, k, l);
        dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Core.terraStoneIgInCobble, 1, l));
        Random R = new Random();
        if(R.nextInt(TFCSettings.initialCollapseRatio) == 0)
        {
            if(tryToFall(world, i, j, k,l))
            {
                int height = 4;
                int range = R.nextInt(20);
                for(int y = -4; y <= 1; y++)
                {
                    for(int x = -range; x <= range; x++)
                    {
                        for(int z = -range; z <= range; z++)
                        {
                            if(R.nextInt(100) < TFCSettings.propogateCollapseChance)
                            {
                                if(tryToFall(world, i+x, j+y, k+z,world.getBlockMetadata( i+x, j+y, k+z)))
                                {
                                    int done = 0;
                                    while(done < height)
                                    {
                                        done++;
                                        if(R.nextInt(100) < TFCSettings.propogateCollapseChance) {
                                            tryToFall(world, i+x, j+y+done, k+z,world.getBlockMetadata( i+x, j+y+done, k+z));
                                        } else {
                                            done = height;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
        return mod_TFC_Core.terraStoneIgInCobble.blockID;
    }
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
    {if(!world.isRemote)
    {
        Random random = new Random();

        ItemStack is = null;

        is = TFC_Core.RandomGem(random, 0);

        if(is != null)
        {
            EntityItem item = new EntityItem(world, i, j, k, is);
            world.spawnEntityInWorld(item);
        }
    }
    }

    public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
    {
        if(!world.isRemote)
        {
            Random random = new Random();
            if(true)
            {
                ItemStack is = null;

                is = TFC_Core.RandomGem(random,2);

                if(is != null)
                {
                    EntityItem item = new EntityItem(world, i, j, k, is);
                    world.spawnEntityInWorld(item);
                }

            }
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        DropCarvedStone(world, i, j, k);
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terraRock.png";
    }
}
