package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.General.ColorizerGrass;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraPeatGrass extends BlockTerra2
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
	
    public BlockTerraPeatGrass(int par1, int par2, Block par3)
    {
        super(par1, Material.grass);
        this.setTickRandomly(true);
        this.blockIndexInTexture = par2;

        blk = par3;
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

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 == 1 ? 255 : (par1 == 0 ? (this.blockIndexInTexture+par2) : (this.blockIndexInTexture + par2));
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
    	return mod_TFC_Core.proxy.blockColorMultiplier(par1IBlockAccess, par2, par3, par4);
    }
    
    public int getBlockColor()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }
    
    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        return this.getBlockColor();
    }
    
    public int getRenderType()
	{
		return mod_TFC_Core.grassRenderId;
	}

    /**
     * Ticks the block if it's been scheduled
     */
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

                    if (par1World.getBlockId(var7, var8, var9) == dirtID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, grassID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == dirt2ID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, grass2ID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == clayID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, clayGrassID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == clay2ID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, clayGrass2ID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                    if (par1World.getBlockId(var7, var8, var9) == peatID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
                    {
                        par1World.setBlockAndMetadataWithNotify(var7, var8, var9, peatGrassID, par1World.getBlockMetadata(var7, var8, var9));
                    }
                }
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return peatID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
    
    public void addCreativeItems(java.util.ArrayList list)
	{
	    list.add(new ItemStack(this,1,0));
	}
}
