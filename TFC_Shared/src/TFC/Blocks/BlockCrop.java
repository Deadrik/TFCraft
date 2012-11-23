package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Textures;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.Items.ItemCustomScythe;
import TFC.TileEntities.TileEntityCrop;
import TFC.TileEntities.TileEntityFarmland;
import TFC.WorldGen.TFCBiome;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StatList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;


public class BlockCrop extends BlockContainer
{
    public BlockCrop(int par1, int id)
    {
        super(par1, Material.plants);
    }
    @Override
    public int getRenderType()
    {
        return TFCBlocks.cropRenderId;
    }
    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        return true;
    }
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
    {
        TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        if(crop != null && !world.isRemote)
        {
            if(crop.cropId == 4 && te.growth >= 7)
            {
                ItemStack is1 = crop.getOutput1(te.growth);
                if(is1 != null)
                    world.spawnEntityInWorld(new EntityItem(world, i, j, k, is1));
                te.growth = 4;
                te.broadcastPacketInRange(te.createCropUpdatePacket());
                return true;
            }
            else if((crop.cropId == 19 || crop.cropId == 20) && te.growth >= 5 && te.growth < 6)
            {
                ItemStack is1 = crop.getOutput1(te.growth);
                if(is1 != null)
                    world.spawnEntityInWorld(new EntityItem(world, i, j, k, is1));
                te.growth = 3;
                te.broadcastPacketInRange(te.createCropUpdatePacket());
                return true;
            }
            else if((crop.cropId == 19 || crop.cropId == 20) && te.growth >= 6)
            {
                ItemStack is1 = crop.getOutput2(te.growth);
                if(is1 != null)
                    world.spawnEntityInWorld(new EntityItem(world, i, j, k, is1));
                te.growth = 3;
                te.broadcastPacketInRange(te.createCropUpdatePacket());
                return true;
            }
            
            
        }
        
        if(TFC_Settings.enableDebugMode)
        {
            System.out.println("Crop ID: " + te.cropId);
            System.out.println("Growth: " + te.growth);
            System.out.println("Est Growth: " + te.getEstimatedGrowth(crop));
        }
        
        return false;
    }
    @Override
    public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int l)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if(!world.isRemote && itemstack != null && itemstack.getItem() instanceof ItemCustomScythe)
        {
        	for(int x = -1; x < 2; x++)
            {
                for(int z = -1; z < 2; z++)
                {
                        if(world.getBlockId( i+x, j, k+z) == this.blockID && player.inventory.getStackInSlot(player.inventory.currentItem) != null)
                        {
                        	player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
                        	player.addExhaustion(0.045F);

                        	//breakBlock(world, i+x, j, k+z, l, 0);
                        	world.setBlock(i+x, j, k+z, 0);
                            
                            int ss = itemstack.stackSize;
                            int dam = itemstack.getItemDamage()+2;
                            
                            if(dam >= itemstack.getItem().getMaxDamage())
                            {
                            	player.inventory.setInventorySlotContents(player.inventory.currentItem, 
                                        null);
                            }
                            else
                            {
                            	player.inventory.setInventorySlotContents(player.inventory.currentItem, 
                                    new ItemStack(itemstack.getItem(),ss,dam));
                            }
                        }
                }
            }
        }

    }
    @Override
    public void breakBlock(World world, int i, int j, int k, int l, int m) 
    {
    	TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        if(crop != null && te.growth >= crop.numGrowthStages-1)
        {
            ItemStack is1 = crop.getOutput1(te.growth);
            ItemStack is2 = crop.getOutput2(te.growth);

            if(is1 != null)
                world.spawnEntityInWorld(new EntityItem(world, i, j, k, is1));

            if(is2 != null)
                world.spawnEntityInWorld(new EntityItem(world, i, j, k, is2));
        }
        else if (crop != null)
        {
        	ItemStack is = crop.getSeed();

            if(is != null)
                world.spawnEntityInWorld(new EntityItem(world, i, j, k, is));
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
        int meta = world.getBlockMetadata(i, j, k); 
        int type = 0;
        if(te != null)
        {
            type = te.cropId;
        }
        return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.3, k+1);
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }


    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int par5)
    {
        super.onNeighborBlockChange(world, i, j, k, par5);

        if (!(world.getBlockId(i, j-1, k) == TFCBlocks.tilledSoil.blockID || world.getBlockId(i, j-1, k) == TFCBlocks.tilledSoil2.blockID))
        {
            world.setBlockWithNotify(i, j, k, 0);
        }
        
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    @Override
    public boolean canBlockStay(World world, int i, int j, int k)
    {
    	if (!(world.getBlockId(i, j-1, k) == TFCBlocks.tilledSoil.blockID || world.getBlockId(i, j-1, k) == TFCBlocks.tilledSoil2.blockID ||
    			TFC_Core.isSoil(world.getBlockId(i, j-1, k))))
        {
    		return false;
        }
    	return true;
    }

    @Override
    public String getTextureFile()
    {
        return TFC_Textures.PlantsSheet;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityCrop();
	}
}
