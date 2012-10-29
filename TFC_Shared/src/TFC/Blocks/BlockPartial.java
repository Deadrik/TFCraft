package TFC.Blocks;

import java.util.ArrayList;

import TFC.*;
import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemChisel;
import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockPartial extends BlockContainer
{
    public BlockPartial(int par1, Material m)
    {
        super(par1, m);
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
	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityPartial();
	}

}
