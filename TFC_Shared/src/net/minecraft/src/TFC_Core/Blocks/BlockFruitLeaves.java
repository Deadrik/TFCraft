package net.minecraft.src.TFC_Core.Blocks;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TFCSeasons;
import net.minecraft.src.TFC_Core.General.FloraIndex;
import net.minecraft.src.TFC_Core.General.FloraManager;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.forge.IShearable;
import net.minecraft.src.forge.ITextureProvider;

public class BlockFruitLeaves extends Block implements ITextureProvider
{
    private int baseIndexInPNG;
    int adjacentTreeBlocks[];

    public BlockFruitLeaves(int i, int j) 
    {
        super(i, Material.leaves);
        baseIndexInPNG = j;
        this.setTickRandomly(true);
    }


    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if(TFCSeasons.currentMonth >= 3 && TFCSeasons.currentMonth < 9)
            return mod_TFC_Core.proxy.foliageColorMultiplier(par1IBlockAccess, par2, par3, par4);
        else return 0xFFFFFF;
    }

    public int getRenderType()
    {
        return mod_TFC_Core.leavesFruitRenderId;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getBlockTextureFromSideAndMetadata(int i, int meta)
    {
        int index = baseIndexInPNG;

        if (mod_TFC_Core.proxy.getGraphicsLevel())
        {
            index = baseIndexInPNG+(meta & 7);
        }
        else
        {
            index = baseIndexInPNG+(meta & 7) + 16;
        }

        if(TFCSeasons.currentMonth >= 3 && TFCSeasons.currentMonth < 9)
        {
            return index;
        }
        else
        {
            return index - 32;
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return TFCSettings.enableInnerGrassFix;
    }

    public void updateTick(World world, int i, int j, int k, Random rand)
    {
        if(!world.isRemote)
        {
            if(!canStay(world,i,j,k,blockID))
            {
                world.setBlock(i, j, k, 0);
                world.markBlockNeedsUpdate(i, j, k);
                return;
            }

            int meta = world.getBlockMetadata(i, j, k);
            int m = meta - 8;
            //((TileEntityFruitTreeLeaves)world.getBlockTileEntity(i, j, k)).updateEntity();
            FloraManager manager = FloraManager.getInstance();
            FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(blockID, m));
            FloraIndex fi2 = FloraManager.getInstance().findMatchingIndex(getType(blockID, meta));
            if(fi2 != null)
            {
                if(world.getBiomeGenForCoords(i, k).getFloatTemperature() >= fi2.minTemp && world.getBiomeGenForCoords(i, k).getFloatTemperature() < fi2.maxTemp)
                {
                    if(fi2.inHarvest(TFCSeasons.currentMonth))
                    {
                        if(rand.nextInt(1) == 0)
                        {
                            if(meta < 8)
                            {
                                meta += 8;
                            }
                            world.setBlockMetadata(i, j, k, meta); 
                            world.markBlockNeedsUpdate(i, j, k);
                        }
                    }
                }
                else
                {
                    if(meta >= 8 && rand.nextInt(10) == 0)
                    {
                        world.setBlockMetadata(i, j, k, meta-8); 
                        world.markBlockNeedsUpdate(i, j, k);
                    }
                }
            }
            if(fi != null)
            {
                if(!fi.inHarvest(TFCSeasons.currentMonth))
                {
                    if(world.getBlockMetadata(i, j, k) >= 8)
                    {
                        world.setBlockMetadata(i, j, k, meta-8); 
                        world.markBlockNeedsUpdate(i, j, k);
                    }
                }
            }
            if(rand.nextInt(100) > 50)
                world.markBlockNeedsUpdate(i, j, k);
        }
        return;
    }
    
    public static boolean canStay(World world, int i, int j, int k, int id)
    {
        if((world.getBlockId(i, j+1, k) != 0 && world.getBlockId(i, j+2, k) != 0 && world.getBlockId(i, j+2, k) != id) ||
                world.getBlockId(i, j+1, k) == mod_TFC_Core.fruitTreeWood.blockID || 
                world.getBlockId(i, j+2, k) == mod_TFC_Core.fruitTreeWood.blockID)
        {
            return false;
        }
        return true;
    }

    public static String getType(int id, int meta)
    {
        if(id == mod_TFC_Core.fruitTreeLeaves.blockID)
        {
            switch(meta)
            {
                case 0:
                {
                    return "red apple";
                }
                case 1:
                {
                    return "banana";
                }
                case 2:
                {
                    return "orange";
                }
                case 3:
                {
                    return "green apple";
                }
                case 4:
                {
                    return "lemon";
                }
                case 5:
                {
                    return "olive";
                }
                case 6:
                {
                    return "cherry";
                }
                case 7:
                {
                    return "peach";
                }
            }
        }
        else
        {
            switch(meta)
            {
                case 0:
                {
                    return "plum";
                }
                case 1:
                {
                    return "cacao";
                }
            }
        }
        return "";
    }
    /**onBlockRemoval needs to remain here in order to override the Block Leaves implementation 
     * of the method which causes leaves to change metadata*/
    @Override
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {

    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int l)
    {
        Random R = new Random();
        if (!par1World.isRemote)
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);

            if (true)
            {
                byte var7 = 4;
                int var8 = var7 + 1;
                byte var9 = 32;
                int var10 = var9 * var9;
                int var11 = var9 / 2;
                adjacentTreeBlocks = null;
                if (this.adjacentTreeBlocks == null)
                {
                    this.adjacentTreeBlocks = new int[var9 * var9 * var9];
                }

                int var12;

                if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
                {
                    int var13;
                    int var14;
                    int var15;

                    for (var12 = -var7; var12 <= var7; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                var15 = par1World.getBlockId(par2 + var12, par3 + var13, par4 + var14);

                                if (var15 == mod_TFC_Core.fruitTreeWood.blockID)
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                                else if (var15 == blockID && var6 == par1World.getBlockMetadata(par2 + var12, par3 + var13, par4 + var14))
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                }
                                else
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                }
                            }
                        }
                    }

                    for (var12 = 1; var12 <= 4; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                for (var15 = -var7; var15 <= var7; ++var15)
                                {
                                    if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
                                    {
                                        if (this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var12 = this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];

                if (var12 >= 0)
                {
                    //par1World.setBlockMetadata(par2, par3, par4, var6 & -9);
                }
                else
                {
                    this.destroyLeaves(par1World, par2, par3, par4);
                }
            }
        }
    }

    private void destroyLeaves(World world, int i, int j, int k)
    {
        world.setBlockWithNotify(i, j, k, 0);
    }

    private void removeLeaves(World world, int i, int j, int k)
    {
        //        dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
        //        if(new Random().nextInt(100) < 30)
        //            dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.stick, 1));
        world.setBlockWithNotify(i, j, k, 0);
    }

    public int quantityDropped(Random random)
    {
        return random.nextInt(20) != 0 ? 0 : 1;
    }
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return mod_TFC_Core.terraSapling.blockID;
    }
    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1)
    {
        if (!world.isRemote)
        {

        }
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if(!world.isRemote)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            entityplayer.addExhaustion(0.025F);

            FloraManager manager = FloraManager.getInstance();
            FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(blockID, l & 7));

            if(fi != null && (fi.inHarvest(TFCSeasons.currentMonth) || fi.inHarvest(TFCSeasons.lastMonth)))
            {
                
                if(fi != null)
                    dropBlockAsItem_do(world, i, j, k, fi.getOutput());
            }

            super.harvestBlock(world, entityplayer, i, j, k, l);
        }
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
        super.onEntityWalking(world, i, j, k, entity);
    }

    @Override
    public String getTextureFile() 
    {
        return "/bioxx/Vegetation.png";
    }

    public void addCreativeItems(java.util.ArrayList list)
    {
        //for(int i = 0; i < 16; i++)
        list.add(new ItemStack(this,1,0));
    }

}
