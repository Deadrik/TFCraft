package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityFarmland;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class BlockFarmland extends BlockContainer
{
	int dirtID;
	Icon[] DirtTexture = new Icon[23];
	int textureOffset = 0;
	
	public BlockFarmland(int par1, int id, int tex)
	{
		super(par1, Material.ground);
		this.setTickRandomly(true);
		dirtID = id;
		textureOffset = tex;
	}

	@Override
    public void func_94332_a(IconRegister registerer)
    {
		for(int i = 0; i < 23; i++)
		{
			DirtTexture[i] = registerer.func_94245_a("farmland/Farmland"+(i+dirtID));
		}
    }
    
    @Override
    public Icon getBlockTexture(IBlockAccess access, int xCoord, int yCoord, int zCoord, int par5)
    {
    	Block blk = Block.blocksList[dirtID];
    	
        if (par5 == 1)//top
        {
            return DirtTexture[access.getBlockMetadata(xCoord, yCoord, zCoord)+textureOffset];
        }
        else
        {
            return blk.getBlockTextureFromSideAndMetadata(0, access.getBlockMetadata(xCoord, yCoord, zCoord));
        }
    }

    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 0), (double)(par2 + 1), (double)(par3 + 1), (double)(par4 + 1));
	}

	@Override
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
    public static boolean isWaterNearby(World world, int i, int j, int k)
    {
        for (int x = i - 4; x <= i + 4; ++x)
        {
            for (int y = j; y <= j + 1; ++y)
            {
                for (int z = k - 4; z <= k + 4; ++z)
                {
                    if (world.getBlockMaterial(x, y, z) == Material.water)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityFarmland();
	}
}
