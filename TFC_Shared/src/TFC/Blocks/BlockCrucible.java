package TFC.Blocks;

import TFC.TFCBlocks;
import TFC.TileEntities.TileEntityToolRack;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockCrucible extends BlockTerraContainer
{
	public BlockCrucible(int par1) 
	{
		super(par1, Material.rock);
		this.setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.9375f, 0.9375f);
		this.blockIndexInTexture = 79;
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
	
//	@Override
//	public int getRenderType()
//	{
//		return TFCBlocks.toolRackRenderId;
//	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 0 || i == 1)
			return blockIndexInTexture - 16;
		
		return blockIndexInTexture;
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{

		}
		return false;
	}
	
}
