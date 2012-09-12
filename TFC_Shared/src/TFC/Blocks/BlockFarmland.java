package TFC.Blocks;

import java.util.Random;

import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityFarmland;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import net.minecraft.src.*;


public class BlockFarmland extends BlockContainer
{
	int dirtID;

	public BlockFarmland(int par1, int id, int tex)
	{
		super(par1, Material.ground);
		this.blockIndexInTexture = tex;
		this.setTickRandomly(true);
		//this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		dirtID = id;
		//this.setLightOpacity(255);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		int tex = par1 == 1 ? this.blockIndexInTexture+par2 : this.blockIndexInTexture+par2 - 64;
		return tex;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 0), (double)(par2 + 1), (double)(par3 + 1), (double)(par4 + 1));
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}

	/**
	 * returns true if there is at least one cropblock nearby (x-1 to x+1, y+1, z-1 to z+1)
	 */
	private boolean isCropsNearby(World par1World, int par2, int par3, int par4)
	{
		byte var5 = 0;

		for (int var6 = par2 - var5; var6 <= par2 + var5; ++var6)
		{
			for (int var7 = par4 - var5; var7 <= par4 + var5; ++var7)
			{
				int var8 = par1World.getBlockId(var6, par3 + 1, var7);

				if (var8 == Block.crops.blockID || var8 == Block.melonStem.blockID || var8 == Block.pumpkinStem.blockID)
				{
					return true;
				}
			}
		}

		return false;
	}
	
	/**
     * returns true if there's water nearby (x-4 to x+4, y to y+1, k-4 to k+4)
     */
    public static boolean isWaterNearby(World par1World, int par2, int par3, int par4)
    {
        for (int var5 = par2 - 4; var5 <= par2 + 4; ++var5)
        {
            for (int var6 = par3; var6 <= par3 + 1; ++var6)
            {
                for (int var7 = par4 - 4; var7 <= par4 + 4; ++var7)
                {
                    if (par1World.getBlockMaterial(var5, var6, var7) == Material.water)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
//		Material var6 = par1World.getBlockMaterial(par2, par3 + 1, par4);
//
//		if (var6.isSolid())
//		{
//			DataLayer rockLayer = ((TFCWorldChunkManager)par1World.getWorldChunkManager()).getRockLayerAt(par2, par4, 0);
//			int id = TFC_Core.getTypeForDirt(rockLayer.data2);
//			par1World.setBlockWithNotify(par2, par3, par4, id);
//		}
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return true;
	}
	
	@Override
    public String getTextureFile()
    {
        return "/bioxx/terrablocks2.png";
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityFarmland();
	}
}
