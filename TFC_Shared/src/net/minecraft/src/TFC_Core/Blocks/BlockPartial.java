package net.minecraft.src.TFC_Core.Blocks;

import java.util.ArrayList;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TFC_Core;
import net.minecraft.src.TFC_Core.TileEntityPartial;
import net.minecraft.src.TFC_Core.General.Helper;
import net.minecraft.src.TFC_Core.General.PlayerInfo;
import net.minecraft.src.TFC_Core.General.PlayerManagerTFC;
import net.minecraft.src.TFC_Core.Items.ItemChisel;
import net.minecraft.src.forge.ITextureProvider;

public class BlockPartial extends BlockContainer implements ITextureProvider
{
    public BlockPartial(int par1, Material m)
    {
        super(par1, m);
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
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terraRock.png";
    }

    public void onBlockAdded(World world, int par2, int par3, int par4)
    {
        super.onBlockAdded(world, par2, par3, par4);
        world.markBlockNeedsUpdate(par2, par3, par4);
    }

    public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 == 0)
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 4);
        }
    }
    
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
    {
        if(!world.isRemote)
        {
        }
    }
    
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {   

    }

}
