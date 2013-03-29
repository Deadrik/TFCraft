package TFC.Blocks;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.TFCBlocks;

public class BlockDryGrass extends BlockGrass
{
    public BlockDryGrass(int par1, int par2)
    {
        super(par1, par2);
    }

    @Override
    public void registerIcons(IconRegister registerer)
    {
    	super.registerIcons(registerer);
    	GrassTopTexture = registerer.registerIcon("GrassSparseOverlay");
    }
    
    @Override
	public int getRenderType()
	{
		return TFCBlocks.grassRenderId;
	}
    
    /*@Override
    public Icon getBlockTexture(IBlockAccess access, int xCoord, int yCoord, int zCoord, int par5)
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
