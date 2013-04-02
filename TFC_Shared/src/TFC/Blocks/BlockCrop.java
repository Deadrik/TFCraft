package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.Items.ItemCustomScythe;
import TFC.TileEntities.TileEntityCrop;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockCrop extends BlockContainer
{
	Icon[] iconsCarrots = new Icon[5];
	Icon[] iconsGarlic = new Icon[5];
	Icon[] iconsCorn = new Icon[6];
	Icon[] iconsCabbage = new Icon[6];
	Icon[] iconsTomato = new Icon[8];
	Icon[] iconsPepperRed = new Icon[7];
	Icon[] iconsPepperYellow = new Icon[7];
	Icon[] iconsWheat = new Icon[8];
	Icon[] iconsRye = new Icon[8];
	Icon[] iconsBarley = new Icon[8];
	Icon[] iconsOat = new Icon[8];
	Icon[] iconsRice = new Icon[8];
	Icon[] iconsGreenbean = new Icon[8];
	Icon[] iconsOnion = new Icon[8];
	Icon[] iconsPotato = new Icon[8];
	Icon[] iconsSoybean = new Icon[8];
	Icon[] iconsSquash = new Icon[8];

	public BlockCrop(int par1, int id)
	{
		super(par1, Material.plants);
		this.setBlockBounds(0, 0, 0, 1, 0.2f, 1);
	}
	@Override
	public int getRenderType()
	{
		return TFCBlocks.cropRenderId;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		for(int i = 1; i < 6; i++)
		{
			iconsCarrots[i-1] = iconRegisterer.registerIcon("plants/crops/Carrots ("+i+")");
			iconsGarlic[i-1] = iconRegisterer.registerIcon("plants/crops/Garlic ("+i+")");
		}
		for(int i = 1; i < 7; i++)
		{
			iconsCorn[i-1] = iconRegisterer.registerIcon("plants/crops/Corn ("+i+")");
			iconsCabbage[i-1] = iconRegisterer.registerIcon("plants/crops/Cabbage ("+i+")");
		}
		for(int i = 1; i < 9; i++)
		{
			iconsTomato[i-1] = iconRegisterer.registerIcon("plants/crops/Tomato ("+i+")");
		}
		for(int i = 1; i < 8; i++)
		{
			iconsPepperRed[i-1] = iconRegisterer.registerIcon("plants/crops/PepperRed ("+i+")");
			iconsPepperYellow[i-1] = iconRegisterer.registerIcon("plants/crops/PepperYellow ("+i+")");
			iconsWheat[i-1] = iconRegisterer.registerIcon("plants/crops/Wheat ("+i+")");
			iconsRye[i-1] = iconRegisterer.registerIcon("plants/crops/Rye ("+i+")");
			iconsBarley[i-1] = iconRegisterer.registerIcon("plants/crops/Barley ("+i+")");
			iconsOat[i-1] = iconRegisterer.registerIcon("plants/crops/Oat ("+i+")");
			iconsRice[i-1] = iconRegisterer.registerIcon("plants/crops/Rice ("+i+")");
			iconsGreenbean[i-1] = iconRegisterer.registerIcon("plants/crops/Greenbean ("+i+")");
			iconsOnion[i-1] = iconRegisterer.registerIcon("plants/crops/Onion ("+i+")");
			iconsPotato[i-1] = iconRegisterer.registerIcon("plants/crops/Potato ("+i+")");
			iconsSquash[i-1] = iconRegisterer.registerIcon("plants/crops/Squash ("+i+")");
			iconsSoybean[i-1] = iconRegisterer.registerIcon("plants/crops/Soybean ("+i+")");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int i, int j, int k, int meta)
	{
		TileEntityCrop te = (TileEntityCrop) access.getBlockTileEntity(i, j, k);
		CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);

		int stage = (int) Math.floor(te.growth);
		if(stage > crop.numGrowthStages)
			stage = crop.numGrowthStages;

		switch(te.cropId)
		{
		case 0:
		case 1:
			return iconsWheat[stage];
		case 2:
		case 3:
			return iconsCorn[stage];
		case 4:
			return iconsTomato[stage];
		case 5://Barley
		case 6://Wild Barley
		{
			return iconsBarley[stage];
		}
		case 7://Rye
		case 8://Wild Rye
		{
			return iconsRye[stage];
		}
		case 9://Oat
		case 10://Wild Oat
		{
			return iconsOat[stage];
		}
		case 11://Rice
		case 12://Wild Rice
		{
			return iconsRice[stage];
		}
		case 13://Potato
		case 14://Wild Potato
		{                
			return iconsPotato[stage];
		}
		case 15://Onion
		{                
			return iconsOnion[stage];
		}
		case 16://Cabbage
		{                
			return iconsCabbage[stage];
		}
		case 17://Garlic
		{                
			return iconsGarlic[stage];
		}
		case 18://Carrots
		{                
			return iconsCarrots[stage];
		}
		case 19://Yellow Bell
		{                
			return iconsPepperYellow[stage];
		}
		case 20://Red Bell
		{                
			return iconsPepperRed[stage];
		}
		case 21://Soybean
		{                
			return iconsSoybean[stage];
		}
		case 22://Greenbean
		{                
			return iconsGreenbean[stage];
		}
		case 23://Squash
		{                
			return iconsSquash[stage];
		}
		}
		return iconsCorn[6];
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
						world.setBlockToAir(i+x, j, k+z);

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
	public void breakBlock(World world, int i, int j, int k, int blockID, int metadata) 
	{
		TileEntityCrop te = (TileEntityCrop) world.getBlockTileEntity(i, j, k);
		if(te!= null && !world.isRemote)
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
		super.breakBlock(world, i, j, k, blockID, metadata);
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
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityCrop();
	}
}
