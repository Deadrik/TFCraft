package TFC.Blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.TileEntities.TileEntityCrop;

public class BlockCropMultiBlock extends BlockTerraContainer 
{

	public BlockCropMultiBlock(int id) 
	{
		super(id);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		if(this.isCropUpper(world.getBlockMetadata(i, j, k)))
			return null;
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
			world.setBlockToAir(i, j, k);
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
				TFC_Core.isSoil(world.getBlockId(i, j-1, k)) || world.getBlockId(i, j-1, k) == this.blockID))
		{
			return false;
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int blockID, int metadata) 
	{
		if(isCropUpper(metadata))
		{
			if(world.getBlockId(i, j-1, k) == blockID)
				breakBlock(world, i, j-1, k, blockID, metadata);
		}
		else
		{
			TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
			if(te!= null)
			{
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
		}
	}

	public static boolean isCropUpper(int meta)
	{
		return (meta & 8) == 1;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityCrop();
	}

	@Override
    @SideOnly(Side.CLIENT)
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
        return true;
    }
}
