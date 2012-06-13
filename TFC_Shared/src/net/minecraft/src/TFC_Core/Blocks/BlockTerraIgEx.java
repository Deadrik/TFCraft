package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.TFC_Core;
import net.minecraft.src.TFC_Core.General.TFCSettings;

public class BlockTerraIgEx extends BlockCollapsable
{
    public BlockTerraIgEx(int i, Material material, int id) {
        super(i,3, material, id);
    }


    public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 4; i++) {
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
        dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Core.terraStoneIgExCobble, 1, l));
        if(TFCSettings.enableDebugMode)
        {
            System.out.println("Harvest Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(l).toString());  
        }
        super.harvestBlock(world, entityplayer, i, j, k, l);
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
        return mod_TFC_Core.terraStoneIgExCobble.blockID;
    }
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
    {
        if(!world.isRemote)
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

            ItemStack is = null;

            is = TFC_Core.RandomGem(random, 0);

            if(is != null)
            {
                EntityItem item = new EntityItem(world, i, j, k, is);
                world.spawnEntityInWorld(item);
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
