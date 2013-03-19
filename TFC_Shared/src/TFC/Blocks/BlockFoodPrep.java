package TFC.Blocks;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraFirepit;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BlockFoodPrep extends BlockTerraContainer {

	public BlockFoodPrep(int par1) {
		super(par1);
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
