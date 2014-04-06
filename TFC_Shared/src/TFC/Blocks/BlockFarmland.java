package TFC.Blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import TFC.Reference;
import TFC.TileEntities.TileEntityFarmland;

public class BlockFarmland extends BlockTerraContainer
{
	int dirtID;
	Icon[] DirtTexture = new Icon[20];
	int textureOffset = 0;
	
	public BlockFarmland(int par1, int id, int tex)
	{
		super(par1, Material.ground);
		this.setTickRandomly(true);
		dirtID = id;
		textureOffset = tex;
	}

	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IconRegister registerer)
    {
		for(int i = textureOffset; i < (textureOffset == 0 ? 16 : 20); i++)
		{
			DirtTexture[i] = registerer.registerIcon(Reference.ModID + ":" + "farmland/Farmland"+(i));
		}
    }
    
	@SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess access, int xCoord, int yCoord, int zCoord, int side)
    {
    	Block blk = Block.blocksList[dirtID];
    	int meta = access.getBlockMetadata(xCoord, yCoord, zCoord);
        if (side == 1)//top
        {
            return DirtTexture[meta+textureOffset];
        }
        else
        {
            return blk.getIcon(side, meta);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta)
    {
        Block blk = Block.blocksList[dirtID];
        
        if (side == ForgeDirection.UP.ordinal())
        {
            return DirtTexture[meta + textureOffset];
        }
        else
        {
            return blk.getIcon(0, meta);
        }
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + 0, par3 + 0, par4 + 0, par2 + 1, par3 + 1, par4 + 1);
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
