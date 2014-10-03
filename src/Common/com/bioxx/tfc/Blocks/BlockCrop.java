package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Items.Tools.ItemCustomScythe;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrop extends BlockContainer
{
	IIcon[] iconsCarrots = new IIcon[5];
	IIcon[] iconsGarlic = new IIcon[5];
	IIcon[] iconsCorn = new IIcon[6];
	IIcon[] iconsCabbage = new IIcon[6];
	IIcon[] iconsTomato = new IIcon[8];
	IIcon[] iconsPepperRed = new IIcon[7];
	IIcon[] iconsPepperYellow = new IIcon[7];
	IIcon[] iconsWheat = new IIcon[8];
	IIcon[] iconsRye = new IIcon[8];
	IIcon[] iconsBarley = new IIcon[8];
	IIcon[] iconsOat = new IIcon[8];
	IIcon[] iconsRice = new IIcon[8];
	IIcon[] iconsGreenbean = new IIcon[7];
	IIcon[] iconsOnion = new IIcon[7];
	IIcon[] iconsPotato = new IIcon[7];
	IIcon[] iconsSoybean = new IIcon[7];
	IIcon[] iconsSquash = new IIcon[7];
	IIcon[] iconsJute = new IIcon[6];
	IIcon[] iconsSugarcane = new IIcon[8];

	public IIcon iconInfest;

	public BlockCrop()
	{
		super(Material.plants);
		this.setBlockBounds(0, 0, 0, 1, 0.2f, 1);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.cropRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		for(int i = 1; i < 6; i++)
		{
			iconsCarrots[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Carrots (" + i + ")");
			iconsGarlic[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Garlic (" + i + ")");
		}
		for(int i = 1; i < 7; i++)
		{
			iconsCorn[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Corn (" + i + ")");
			iconsCabbage[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Cabbage (" + i + ")");
			iconsJute[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Jute (" + i + ")");
		}
		for(int i = 1; i < 8; i++)
		{
			iconsPepperRed[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/PepperRed (" + i + ")");
			iconsPepperYellow[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/PepperYellow (" + i + ")");
			iconsGreenbean[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Greenbean (" + i + ")");
			iconsOnion[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Onion (" + i + ")");
			iconsPotato[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Potato (" + i + ")");
			iconsSquash[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Squash (" + i + ")");
			iconsSoybean[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Soybean (" + i + ")");
		}
		for(int i = 1; i < 9; i++)
		{
			iconsTomato[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Tomato (" + i + ")");
			iconsWheat[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Wheat (" + i + ")");
			iconsRye[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Rye (" + i + ")");
			iconsBarley[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Barley (" + i + ")");
			iconsOat[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Oat (" + i + ")");
			iconsRice[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Rice (" + i + ")");
			iconsSugarcane[i - 1] = register.registerIcon(Reference.ModID + ":" + "plants/crops/Sugarcane (" + i + ")");
		}

		iconInfest = register.registerIcon(Reference.ModID + ":bugs");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta)
	{
		TECrop te = (TECrop) access.getTileEntity(x, y, z);
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
			return iconsBarley[stage];
		case 7://Rye
		case 8://Wild Rye
			return iconsRye[stage];
		case 9://Oat
		case 10://Wild Oat
			return iconsOat[stage];
		case 11://Rice
		case 12://Wild Rice
			return iconsRice[stage];
		case 13://Potato
		case 14://Wild Potato
			return iconsPotato[stage];
		case 15://Onion
			return iconsOnion[stage];
		case 16://Cabbage
			return iconsCabbage[stage];
		case 17://Garlic
			return iconsGarlic[stage];
		case 18://Carrots
			return iconsCarrots[stage];
		case 19://Yellow Bell
			return iconsPepperYellow[stage];
		case 20://Red Bell
			return iconsPepperRed[stage];
		case 21://Soybean
			return iconsSoybean[stage];
		case 22://Greenbean
			return iconsGreenbean[stage];
		case 23://Squash
			return iconsSquash[stage];
		case 24://Jute
			return iconsJute[stage];
		case 25://Sugarcane
			return iconsSugarcane[stage];
		}
		return iconsCorn[6];
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		TECrop te = (TECrop) world.getTileEntity(x, y, z);
		CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);

		/*if(crop != null && !world.isRemote)
		{
			if(crop.cropId == 4 && te.growth >= 7)
			{
				te.onHarvest(world, entityplayer, false);
				te.growth = 4;
				world.markBlockForUpdate(i, j, k);
				te.broadcastPacketInRange();
				return true;
			}
			else if((crop.cropId == 19 || crop.cropId == 20) && te.growth >= 5 && te.growth < 6)
			{
				te.onHarvest(world, entityplayer, false);
				te.growth = 3;
				world.markBlockForUpdate(i, j, k);
				te.broadcastPacketInRange();
				return true;
			}
			else if((crop.cropId == 19 || crop.cropId == 20) && te.growth >= 6)
			{
				te.onHarvest(world, entityplayer, false);
				te.growth = 3;
				world.markBlockForUpdate(i, j, k);
				te.broadcastPacketInRange();
				return true;
			}

			te.broadcastPacketInRange();
		}*/

		if(TFCOptions.enableDebugMode)
		{
			System.out.println("Crop ID: " + te.cropId);
			System.out.println("Growth: " + te.growth);
			System.out.println("Est Growth: " + te.getEstimatedGrowth(crop));
		}

		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int l, EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		TECrop te = (TECrop) world.getTileEntity(i, j, k);

		//Handle Scythe
		if(!world.isRemote && itemstack != null && itemstack.getItem() instanceof ItemCustomScythe)
		{
			for(int x = -1; x < 2; x++)
			{
				for(int z = -1; z < 2; z++)
				{
					if(world.getBlock( i+x, j, k+z) == this && player.inventory.getStackInSlot(player.inventory.currentItem) != null)
					{
						player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
						TECrop teX = (TECrop) world.getTileEntity(i + x, j, k + z);
						teX.onHarvest(world, player, true);

						//breakBlock(world, i + x, j, k + z, l, 0);
						world.setBlockToAir(i + x, j, k + z);

						int ss = itemstack.stackSize;
						int dam = itemstack.getItemDamage() + 2;

						if(dam >= itemstack.getItem().getMaxDamage())
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						else
							player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(itemstack.getItem(), ss, dam));
					}
				}
			}
		}
		else
			//Handle Loot Drop
			te.onHarvest(world, player, true);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.3, z + 1);
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		super.onNeighborBlockChange(world, x, y, z, b);

		if (!canBlockStay(world, x, y, z))
			world.setBlockToAir(x, y, z);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		if (!(world.getBlock(x, y - 1, z) == TFCBlocks.tilledSoil || world.getBlock(x, y - 1, z) == TFCBlocks.tilledSoil2 || TFC_Core.isSoil(world.getBlock(x, y - 1, z))))
			return false;
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TECrop();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
