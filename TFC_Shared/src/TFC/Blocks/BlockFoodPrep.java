package TFC.Blocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityFoodPrep;

public class BlockFoodPrep extends BlockTerraContainer {

	public BlockFoodPrep(int par1) {
		super(par1);
		this.setBlockBounds(0, 0, 0, 1, 0.15f, 1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			entityplayer.openGui(TerraFirmaCraft.instance, 32, world, i, j, k);
		}
		
		return true;
	}
	
	@Override
	public int getRenderType()
	{
		return TFCBlocks.foodPrepRenderId;
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
	public void registerIcons(IconRegister iconRegisterer)
    {

    }
	
	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityFoodPrep();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int i, int j, int k)
    {
		return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.15, k+1);
    }
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int id)
	{
		if(!world.isRemote)
		{
			if(!world.isBlockOpaqueCube(i, j-1, k))
			{
				((TileEntityFoodPrep)world.getBlockTileEntity(i, j, k)).ejectContents();
				world.setBlock(i, j, k, 0);
				return;
			}
		}
	}
	
	@Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {       
        Eject(world,i,j,k);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion ex) {
        Eject(par1World,par2,par3,par4);
    }

    @Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
        Eject(par1World,par2,par3,par4);
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	Eject(par1World,par2,par3,par4);
    }

    //public void onBlockRemoval(World par1World, int par2, int par3, int par4) {Eject(par1World,par2,par3,par4);}
    
    public void Eject(World par1World, int par2, int par3, int par4)
    {
        if((TileEntityFoodPrep)par1World.getBlockTileEntity(par2, par3, par4)!=null)
        {
        	TileEntityFoodPrep te = (TileEntityFoodPrep)par1World.getBlockTileEntity(par2, par3, par4);
            te.ejectContents();
            par1World.removeBlockTileEntity(par2, par3, par4);
        }
    }

}
