package TFC.Blocks;

import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraFirepit;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.TerraFirmaCraft;
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
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k) 
	{
		int id = access.getBlockId(i, j-1, k);

		if(Block.isNormalCube(id))
			this.setBlockBounds(0F, 0F, 0F, 1F, 0.3F, 1F);
		else
		{
			if(id == TFCBlocks.stoneSlabs.blockID)
			{
				int h = BlockSlab.getTopChiselLevel(((TileEntityPartial)access.getBlockTileEntity(i, j-1, k)).extraData);
				this.setBlockBounds(0F, 0F-(h*0.1F), 0F, 1F, 0.1F-(h*0.1F), 1F);
			}
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

}
