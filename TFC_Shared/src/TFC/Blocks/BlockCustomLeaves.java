package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.Core.TFCSettings;
import TFC.Items.ItemCustomScythe;
import net.minecraft.src.*;
import net.minecraft.src.forge.IShearable;
import net.minecraft.src.forge.ITextureProvider;

public class BlockCustomLeaves extends BlockLeaves implements ITextureProvider, IShearable
{
    private int baseIndexInPNG;
    int adjacentTreeBlocks[];

    public BlockCustomLeaves(int i, int j) 
    {
        super(i, j);
        baseIndexInPNG = j;
        this.setTickRandomly(false);
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return mod_TFC.leavesRenderId;
    }
    
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return true;
    }
    
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
         return mod_TFC.proxy.foliageColorMultiplier(par1IBlockAccess, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.motionX *= 0.8D;
        par5Entity.motionZ *= 0.8D;
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return TFCSettings.enableInnerGrassFix;
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        return;
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

                                if (var15 == mod_TFC.terraWood.blockID)
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                                else if (var15 == mod_TFC.terraLeaves.blockID && var6 == par1World.getBlockMetadata(par2 + var12, par3 + var13, par4 + var14))
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
                    if(par1World.getChunkFromBlockCoords(par2, par4) != null)
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
        dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
        if(new Random().nextInt(100) < 30)
            dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.stick, 1));
        world.setBlockWithNotify(i, j, k, 0);
    }

    public int quantityDropped(Random random)
    {
        return random.nextInt(20) != 0 ? 0 : 1;
    }
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return mod_TFC.terraSapling.blockID;
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
        if (!world.isRemote && itemstack != null && itemstack.getItem() instanceof ItemCustomScythe)
        {
            for(int x = -1; x < 2; x++)
            {
                for(int z = -1; z < 2; z++)
                {
                    for(int y = -1; y < 2; y++)
                    {
                        if(world.getBlockMaterial( i+x, j+y, k+z) == Material.leaves && entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem) != null)
                        {
                            entityplayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
                            entityplayer.addExhaustion(0.045F);
                            if(new Random().nextInt(100) < 11)
                                dropBlockAsItem_do(world, i+x, j+y, k+z, new ItemStack(Item.stick, 1));
                            else if(new Random().nextInt(100) < 4 && l != 9 && l != 15)
                                dropBlockAsItem_do(world, i+x, j+y, k+z, new ItemStack(mod_TFC.terraSapling, 1, l));
                            removeLeaves(world, i+x, j+y, k+z);
                            super.harvestBlock(world, entityplayer, i+x, j+y, k+z, l);
                            
                            int ss = itemstack.stackSize;
                            int dam = itemstack.getItemDamage()+2;
                            
                            if(dam >= itemstack.getItem().getMaxDamage())
                            {
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
                                        null);
                            }
                            else
                            {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
                                    new ItemStack(itemstack.getItem(),ss,dam));
                            }
                        }
                    }
                }
            }
        }
        else if(!world.isRemote)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            entityplayer.addExhaustion(0.025F);
            if(new Random().nextInt(100) < 28)
                dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.stick, 1));
            else if(new Random().nextInt(100) < 6 && l != 9 && l != 15)
                dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC.terraSapling, 1, l));

            super.harvestBlock(world, entityplayer, i, j, k, l);
        }
    }

    protected int damageDropped(int i)
    {
        return i;
    }

    public boolean isOpaqueCube()
    {
        if(mod_TFC.proxy != null)
            return !mod_TFC.proxy.getGraphicsLevel();
        else
            return false;
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        if (mod_TFC.proxy.getGraphicsLevel())
        {
            return baseIndexInPNG + j;
        }
        else
        {
            return baseIndexInPNG + j + 16;
        }
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
        super.onEntityWalking(world, i, j, k, entity);
    }

    @Override
    public String getTextureFile() 
    {
        return "/bioxx/terrablocks.png";
    }

    public void addCreativeItems(java.util.ArrayList list)
    {
        for(int i = 0; i < 16; i++)
            list.add(new ItemStack(this,1,i));
    }

    @Override
    public boolean isShearable(ItemStack item, World world, int x, int y, int z) 
    {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) 
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
        return ret;
    }

}
