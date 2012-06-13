package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StatList;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.EntityFallingStone;
import net.minecraft.src.TFC_Core.General.TFCSettings;

public class BlockCollapsable extends BlockTerra
{
    public int dropBlock;

    protected BlockCollapsable(int par1,int par2, Material material, int d)
    {
        super(par1, par2, material);
        dropBlock = d;
    }

    public boolean canFallBelow(World world, int i, int j, int k)
    {		
        int l = world.getBlockId(i, j, k);

        if (l == 0)
        {
            return true;
        }
        if (l == Block.bedrock.blockID)
        {
            return false;
        }
        if (l == Block.fire.blockID)
        {
            return true;
        }
        if (l == Block.torchWood.blockID)
        {
            return true;
        }
        Material material = Block.blocksList[l].blockMaterial;
        if (material == Material.water || material == Material.lava)
        {
            return true;
        }
        return false;
    }

    public void DropCarvedStone(World world, int i, int j, int k)
    {
        if(world.isBlockOpaqueCube(i+1, j, k)) {
            return;
        } else if(world.isBlockOpaqueCube(i-1, j, k)) {
            return;
        } else if(world.isBlockOpaqueCube(i, j, k+1)) {
            return;
        } else if(world.isBlockOpaqueCube(i, j, k-1)) {
            return;
        } else if(world.isBlockOpaqueCube(i, j+1, k)) {
            return;
        } else if(world.isBlockOpaqueCube(i, j-1, k)) {
            return;
        }

        dropBlockAsItem_do(world, i, j, k, new ItemStack(blockID, 1, damageDropped(world.getBlockMetadata(i, j, k))));
        world.setBlockWithNotify(i, j, k, 0);

    }

    public Boolean hasNaturalSupport(World world, int i, int j, int k)
    {
        //Make sure that the block beneath the one we're checking is not a solid, if it is then return true and don't waste time here.
        if(world.getBlockId(i, j-1, k) != 0)
        {
            return true;
        }

        if(world.isBlockOpaqueCube(i+1, j, k))
        {
            if(world.isBlockOpaqueCube(i+1, j-1, k))
            {
                return true;
            }
        }

        if(world.isBlockOpaqueCube(i-1, j, k))
        {
            if(world.isBlockOpaqueCube(i-1, j-1, k))
            {
                return true;
            }
        }

        if(world.isBlockOpaqueCube(i, j, k+1))
        {
            if(world.isBlockOpaqueCube(i, j-1, k+1))
            {
                return true;
            }
        }

        if(world.isBlockOpaqueCube(i, j, k-1))
        {
            if(world.isBlockOpaqueCube(i, j-1, k-1))
            {
                return true;
            }
        }

        //Diagonals		
        if(world.isBlockOpaqueCube(i+1, j, k-1))
        {
            if(world.isBlockOpaqueCube(i+1, j-1, k-1))
            {
                return true;
            }
        }

        if(world.isBlockOpaqueCube(i-1, j, k-1))
        {
            if(world.isBlockOpaqueCube(i-1, j-1, k-1))
            {
                return true;
            }
        }

        if(world.isBlockOpaqueCube(i+1, j, k+1))
        {
            if(world.isBlockOpaqueCube(i+1, j-1, k+1))
            {
                return true;
            }
        }

        if(world.isBlockOpaqueCube(i-1, j, k+1))
        {
            if(world.isBlockOpaqueCube(i-1, j-1, k+1))
            {
                return true;
            }
        }

        return false;
    }

    public static Boolean isNearSupport(World world, int i, int j, int k)
    {
        for(int y = -1; y < 1; y++)
        {
            for(int x = -4; x < 5; x++)
            {
                for(int z = -4; z < 5; z++)
                {
                    if(world.getBlockId(i+x, j+y, k+z) == mod_TFC_Core.terraWoodSupportH.blockID)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean isUnderLoad(World world, int i, int j, int k)
    {
        for(int x = 1; x <= TFCSettings.minimumRockLoad; x++)
        {
            if(!world.isBlockOpaqueCube(i, j+x, k))
            {
                return false;
            }
        }
        return true;
    }


    public Boolean tryToFall(World world, int i, int j, int k, int l)
    {
        int xCoord = i;
        int yCoord = j;
        int zCoord = k;
        int fallingBlockID = -1;
        
        Block B = Block.blocksList[world.getBlockId(i, j, k)];
        if(B instanceof BlockTerraIgIn || B instanceof BlockTerraIgEx || B instanceof BlockTerraMM || B instanceof BlockTerraSed)
            fallingBlockID = ((BlockCollapsable)Block.blocksList[world.getBlockId(i, j, k)]).dropBlock;

        if(world.getBlockId(xCoord, yCoord, zCoord) == Block.bedrock.blockID || world.getBlockId(xCoord, yCoord, zCoord) == fallingBlockID)
        {
            return false;
        }

        if (canFallBelow(world, xCoord, yCoord - 1, zCoord)  && !isNearSupport(world, i,j,k)  && isUnderLoad(world, i,j,k) /*&& !hasNaturalSupport(world,i,j,k)*/)
        {
            if (!world.isRemote && fallingBlockID != -1)
            {
                Random R = new Random();

                world.spawnEntityInWorld(new EntityFallingStone(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, fallingBlockID, l, 5));
                world.setBlock(i, j, k, 0);
                world.markBlockNeedsUpdate(i, j, k);

                return true;
            }
        }
        return false;
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {   
        //super.harvestBlock(world, entityplayer, i, j, k, l);
        if(entityplayer != null)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
            entityplayer.addExhaustion(0.025F);
        }
        Random R = new Random();
        if(R.nextInt(TFCSettings.initialCollapseRatio) == 0)
        {
            for(int x1 = -1; x1 < 2; x1++)
            {
                for(int z1 = -1; z1 < 2; z1++)
                {
                    if(tryToFall(world, i+x1, j, k+z1,l))
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
        }
    }
    
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4) 
    {
        harvestBlock(par1World, null, par2,par3,par4,par1World.getBlockMetadata(par2, par3, par4));
    }

}
