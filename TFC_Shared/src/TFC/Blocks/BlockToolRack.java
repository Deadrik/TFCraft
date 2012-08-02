package TFC.Blocks;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockToolRack extends BlockContainer implements ITextureProvider
{

    public BlockToolRack(int par1, Material par2Material)
    {
        super(par1, par2Material);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getTextureFile()
    {
        // TODO Auto-generated method stub
        return "/bioxx/terrablocks.png";
    }

    @Override
    public TileEntity getBlockEntity()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void Eject(World par1World, int par2, int par3, int par4)
    {
//        if((TileEntityTerraLogPile)par1World.getBlockTileEntity(par2, par3, par4)!=null)
//        {
//            TileEntityTerraLogPile tileentityanvil;
//            tileentityanvil = (TileEntityTerraLogPile)par1World.getBlockTileEntity(par2, par3, par4);
//            tileentityanvil.ejectContents();
//            par1World.removeBlockTileEntity(par2, par3, par4);
//        }
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {       
        Eject(world,i,j,k);
    }

    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4) {
        Eject(par1World,par2,par3,par4);
    }

    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
        Eject(par1World,par2,par3,par4);
    }

    public void onBlockRemoval(World par1World, int par2, int par3, int par4) {Eject(par1World,par2,par3,par4);}

}
