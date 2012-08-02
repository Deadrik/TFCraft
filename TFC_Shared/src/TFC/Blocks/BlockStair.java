package TFC.Blocks;

import java.util.ArrayList;

import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;

public class BlockStair extends BlockPartial
{

    public BlockStair(int par1)
    {
        super(par1, Material.rock);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getRenderType()
    {
        return mod_TFC_Core.stairRenderId;
    }

    /**
     * Adds to the supplied array any colliding bounding boxes with the passed in bounding box. Args: world, x, y, z,
     * axisAlignedBB, arrayList
     */
    public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList)
    {
        int var7 = par1World.getBlockMetadata(par2, par3, par4);
        int var8 = var7 & 3;
        float var9 = 0.0F;
        float var10 = 0.5F;
        float var11 = 0.5F;
        float var12 = 1.0F;

        if ((var7 & 4) != 0)
        {
            var9 = 0.5F;
            var10 = 1.0F;
            var11 = 0.0F;
            var12 = 0.5F;
        }

        this.setBlockBounds(0.0F, var9, 0.0F, 1.0F, var10, 1.0F);
        super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);

        if (var8 == 0)
        {
            this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 1.0F);
            super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
        }
        else if (var8 == 1)
        {
            this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 1.0F);
            super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
        }
        else if (var8 == 2)
        {
            this.setBlockBounds(0.0F, var11, 0.5F, 1.0F, var12, 1.0F);
            super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
        }
        else if (var8 == 3)
        {
            this.setBlockBounds(0.0F, var11, 0.0F, 1.0F, var12, 0.5F);
            super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
    {
        if(!world.isRemote)
        {
        }
    }
    
    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {   

    }

}
