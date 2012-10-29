package TFC.Blocks;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraFirepit;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

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

}
