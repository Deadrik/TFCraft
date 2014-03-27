package TFC.Blocks.Terrain;

import net.minecraft.client.renderer.texture.IIconRegister;
import TFC.Reference;
import TFC.TFCBlocks;

public class BlockDryGrass extends BlockGrass
{
	public BlockDryGrass(int texOff)
	{
		super(texOff);
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		super.registerBlockIcons(registerer);
		GrassTopTexture = registerer.registerIcon(Reference.ModID + ":" + "GrassSparseOverlay");
		iconGrassSideOverlay = registerer.registerIcon(Reference.ModID + ":" + "GrassSideSparse");
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.grassRenderId;
	}

	/*@Override
    public IIcon getIcon(IBlockAccess access, int xCoord, int yCoord, int zCoord, int par5)
    {
    	Block blk = Block.blocksList[TFC_Core.getTypeForDirt(access.getBlockMetadata(xCoord, yCoord, zCoord) + textureOffset)];

        if (par5 == 1)//top
        {
            return GrassTopTexture;
        }
        if (par5 == 0)//top
        {
            return TFC_Textures.InvisibleTexture;
        }
        else
        {
            return iconGrassSideOverlay;
        }
    }*/
}
