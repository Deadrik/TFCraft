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
		this.setLightValue(1F);
	}
	
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
    {
		int meta = world.getBlockMetadata(x, y, z);
        return Math.min(meta, 8);
    }
	
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
		if(i < 2)
			return blockIndexInTexture+9;
		
		return blockIndexInTexture+j;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntitySpawnMeter();
	}
}
