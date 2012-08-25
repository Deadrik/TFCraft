package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.Core.ColorizerGrassTFC;
import TFC.Core.TFCSettings;
import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.*;

public class BlockTerraGrass extends BlockGrass
{
    public int grassID = 0;
    public int grass2ID = 0;
    public int dirtID = 0;
    public int dirt2ID = 0;
    public int clayID = 0;
    public int clay2ID = 0;
    public int clayGrassID = 0;
    public int clayGrass2ID = 0;
    public int peatID = 0;
    public int peatGrassID = 0;

    private Block blk;
    public BlockTerraGrass(int par1, int par2, Block par3)
    {
        super(par1);
        this.setTickRandomly(true);
        this.blockIndexInTexture = par2;
        blk = par3;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terrablocks2.png";
    }

    public void setIDs(int grass, int grass2,int dirt, int dirt2,int clay, int clay2,int claygrass, int claygrass2, int peat, int peatgrass)
    {
        grassID = grass;
        grass2ID = grass2;
        dirtID = dirt;
        dirt2ID = dirt2;
        clayID = clay;
        clay2ID = clay2;
        clayGrassID = claygrass;
        clayGrass2ID = claygrass2;
        peatID = peat;
        peatGrassID = peatgrass;
    }

    @Override
    protected int damageDropped(int i) {
        return i;
    }

//    public boolean isBlockNormalCube(World world, int i, int j, int k) 
//    {
//        if(world.isAirBlock(i, j+1, k))
//        {
//            if(world.isAirBlock(i+1, j, k))
//                return false;
//            if(world.isAirBlock(i-1, j, k))
//                return false;
//            if(world.isAirBlock(i, j, k+1))
//                return false;
//            if(world.isAirBlock(i, j, k-1))
//                return false;
//        }
//
//        return true;
//    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 == 1 ? 255 : (par1 == 0 ? (this.blockIndexInTexture+16) : (this.blockIndexInTexture + par2));
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)//top
        {
            return 255;
        }
        else if (par5 == 0)//bottom
        {
            return this.blockIndexInTexture + par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        }
        else if (par5 == 2)//-Z
        {
            if(TFCSettings.enableBetterGrass == true && par1IBlockAccess.getBlockMaterial(par2, par3-1, par4-1) == Material.grass)
                return 255;
            else
                return blk.blockIndexInTexture + par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        }
        else if (par5 == 3)//+Z
        {
            if(TFCSettings.enableBetterGrass == true && par1IBlockAccess.getBlockMaterial(par2, par3-1, par4+1) == Material.grass)
                return 255;
            else
                return blk.blockIndexInTexture + par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        }
        else if (par5 == 4)//-X
        {
            if(TFCSettings.enableBetterGrass == true && par1IBlockAccess.getBlockMaterial(par2-1, par3-1, par4) == Material.grass)
                return 255;
            else
                return blk.blockIndexInTexture + par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        }
        else if (par5 == 5)//+X
        {
            if(TFCSettings.enableBetterGrass == true && par1IBlockAccess.getBlockMaterial(par2+1, par3-1, par4) == Material.grass)
                return 255;
            else
                return blk.blockIndexInTexture + par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        }
        return this.blockIndexInTexture;
    }


    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return mod_TFC.proxy.grassColorMultiplier(par1IBlockAccess, par2, par3, par4);
    }

    public int getBlockColor()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrassTFC.getGrassColor(var1, var3);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        return this.getBlockColor();
    }
    @Override
    public int getRenderType()
    {
        return mod_TFC.grassRenderId;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && Block.lightOpacity[par1World.getBlockId(par2, par3 + 1, par4)] > 2)
            {
                par1World.setBlockAndMetadataWithNotify(par2, par3, par4, dirtID, par1World.getBlockMetadata(par2, par3, par4));
            }
            else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
            {
                for (int var6 = 0; var6 < 4; ++var6)
                {
                    int var7 = par2 + par5Random.nextInt(3) - 1;
                    int var8 = par3 + par5Random.nextInt(5) - 3;
                    int var9 = par4 + par5Random.nextInt(3) - 1;
                    int var10 = par1World.getBlockId(var7, var8 + 1, var9);

                    if (par1World.getBlockId(var7, var8, var9) == dirtID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2 && par1World.getBlockMaterial(var7, var8 + 1, var9) != Material.water)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, grassID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == dirt2ID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2 && par1World.getBlockMaterial(var7, var8 + 1, var9) != Material.water)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, grass2ID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == clayID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2 && par1World.getBlockMaterial(var7, var8 + 1, var9) != Material.water)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, clayGrassID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == clay2ID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2 && par1World.getBlockMaterial(var7, var8 + 1, var9) != Material.water)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, clayGrass2ID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == peatID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2 && par1World.getBlockMaterial(var7, var8 + 1, var9) != Material.water)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, peatGrassID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                }
            }
            par1World.markBlockNeedsUpdate(par2, par3, par4);
        }
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity par5Entity) 
    {
        if (!world.isRemote)
        {
            Random R = new Random();
            if(!BlockCollapsable.isNearSupport(world, i, j, k) && BlockTerraDirt.canFallBelow(world, i, j - 1, k) && R.nextInt(10) == 0)
            {
                int meta = world.getBlockMetadata(i, j, k);
                world.setBlockAndMetadataWithNotify(i, j, k, mod_TFC.terraDirt.blockID, meta);
            }
        }
    }

    //    public boolean renderAsNormalBlock()
    //    {
    //        return false;
    //    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return mod_TFC.terraDirt.idDropped(0, par2Random, par3);
    }

//    public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList)
//    {
//        if(world.isRemote && (!world.isBlockOpaqueCube(i+1, j, k) || !world.isBlockOpaqueCube(i-1, j, k) || 
//                !world.isBlockOpaqueCube(i, j, k+1) || !world.isBlockOpaqueCube(i, j, k-1)) && 
//                !world.isBlockOpaqueCube(i, j+1, k))
//        {
//            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i +1,j + 0.5f,k + 1));
//
//            double minX = 0.25;
//            double minZ = 0.25;
//            double maxX = 0.75;
//            double maxZ = 0.75;
//
//            if(!world.isBlockOpaqueCube(i+1, j, k))
//                maxX = 0.5;
//            if(!world.isBlockOpaqueCube(i-1, j, k))
//                minX = 0.5;
//            if(!world.isBlockOpaqueCube(i, j, k+1))
//                maxZ = 0.5;
//            if(!world.isBlockOpaqueCube(i, j, k-1))
//                minZ = 0.5;
//
//            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i + minX, j + 0.5, k + minZ, i + maxX, j + 1, k + maxZ));
//
//        }
//        else
//            par6ArrayList.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k,i + 1,j + 1,k +1));
//    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!world.blockExists(i, j-1, k))
        {
            int meta = world.getBlockMetadata(i, j, k);
            world.setBlockAndMetadataWithNotify(i, j, k, mod_TFC.terraDirt.blockID, meta);
        }
    }

}
