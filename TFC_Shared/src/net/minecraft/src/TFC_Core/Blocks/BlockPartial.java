package net.minecraft.src.TFC_Core.Blocks;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TileEntityPartial;
import net.minecraft.src.forge.ITextureProvider;

public class BlockPartial extends BlockContainer implements ITextureProvider
{
    protected BlockPartial(int par1)
    {
        super(par1, Material.wood);
    }

    @Override
    public TileEntity getBlockEntity()
    {
        try
        {
            return (TileEntityPartial) TileEntityPartial.class.newInstance();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getRenderType()
    {
        return mod_TFC_Core.partialRenderId;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public String getTextureFile()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
