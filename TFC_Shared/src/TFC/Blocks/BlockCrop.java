package TFC.Blocks;

import java.util.Random;

import TFC.Core.CropIndex;
import TFC.Core.CropManager;
import TFC.Core.TFCSettings;
import TFC.TileEntities.TileEntityCrop;
import TFC.TileEntities.TileEntityFarmland;
import TFC.WorldGen.TFCBiome;
import net.minecraft.src.*;


public class BlockCrop extends BlockContainer
{
    public BlockCrop(int par1, int id)
    {
        super(par1, Material.plants);
    }

    public int getRenderType()
    {
        return mod_TFC.cropRenderId;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        return true;
    }
    
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        if(crop != null)
        {
            if(crop.cropId == 4 && te.growth >= 7)
            {
                ItemStack is1 = crop.getOutput1();
                if(is1 != null)
                    world.spawnEntityInWorld(new EntityItem(world, i, j, k, is1));
                te.growth = 4;
            }
            if(TFCSettings.enableDebugMode)
            {
                System.out.println("Crop ID: " + te.cropId);
                System.out.println("Growth: " + te.growth);
            }
        }
        
        return false;
    }

    public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int l)
    {
        player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        player.addExhaustion(0.025F);

    }
    
    public void onBlockRemoval(World world, int i, int j, int k) 
    {
        TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        if(crop != null && te.growth >= crop.numGrowthStages)
        {
            ItemStack is1 = crop.getOutput1();
            ItemStack is2 = crop.getOutput2();

            if(is1 != null)
                world.spawnEntityInWorld(new EntityItem(world, i, j, k, is1));

            if(is2 != null)
                world.spawnEntityInWorld(new EntityItem(world, i, j, k, is2));
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
        int meta = world.getBlockMetadata(i, j, k); 
        int type = 0;
        if(te != null)
        {
            type = te.cropId;
        }

        if(meta == 0)
        {
            if(type == 4)
                return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+2, k+1);
        }
        else if(meta == 1)
        {
            if(type == 4)
                return AxisAlignedBB.getBoundingBox(i, j-1, k, i+1, j+1, k+1);
        }
        return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
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
    public void onNeighborBlockChange(World world, int i, int j, int k, int par5)
    {
        super.onNeighborBlockChange(world, i, j, k, par5);

        if (!(world.getBlockId(i, j-1, k) == mod_TFC.tilledSoil.blockID || world.getBlockId(i, j-1, k) == mod_TFC.tilledSoil2.blockID))
        {
            world.setBlockWithNotify(i, j, k, 0);
        }
        
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/TFC_Plants.png";
    }

    @Override
    public TileEntity getBlockEntity()
    {
        try
        {
            return TileEntityCrop.class.newInstance();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
