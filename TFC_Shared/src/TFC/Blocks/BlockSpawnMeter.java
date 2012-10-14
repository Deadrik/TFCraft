package TFC.Blocks;

import TFC.Chunkdata.ChunkDataManager;
import TFC.TileEntities.TileEntitySpawnMeter;
import net.minecraft.src.*;

public class BlockSpawnMeter extends BlockTerraContainer
{
	public BlockSpawnMeter(int par1) {
		super(par1, Material.rock);
		this.blockIndexInTexture = 48;
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setLightValue(0.25F);
	}
	
	public int getRenderType()
	{
		return TFCBlocks.spawnMeterRenderId;
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
	public int damageDropped(int j) 
	{
		return j;
	}
	
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {  
		
    }

	@Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
		if(i < 2)
			return blockIndexInTexture+4;
		
		return blockIndexInTexture+j;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntitySpawnMeter();
	}
}
