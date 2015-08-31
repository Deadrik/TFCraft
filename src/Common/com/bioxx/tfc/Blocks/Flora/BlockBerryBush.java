package com.bioxx.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEBerryBush;
import com.bioxx.tfc.api.Util.Helper;

public class BlockBerryBush extends BlockTerraContainer
{
	public static IIcon[] icons;
	public static IIcon[] iconsBerries;
	public static String[] metaNames;

	public static final int WINTERGREEN = 0;
	public static final int BLUEBERRY = 1;
	public static final int RASPBERRY = 2;
	public static final int STRAWBERRY = 3;
	public static final int BLACKBERRY = 4;
	public static final int BUNCHBERRY = 5;
	public static final int CRANBERRY = 6;
	public static final int SNOWBERRY = 7;
	public static final int ELDERBERRY = 8;
	public static final int GOOSEBERRY = 9;
	public static final int CLOUDBERRY = 10;

	public BlockBerryBush()
	{
		super(Material.plants);
		metaNames = new String[]{"Wintergreen", "Blueberry", "Raspberry", "Strawberry", "Blackberry", "Bunchberry", "Cranberry", "Snowberry", "Elderberry", "Gooseberry", "Cloudberry"};
		icons = new IIcon[metaNames.length];
		iconsBerries = new IIcon[metaNames.length];
		this.setTickRandomly(true);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		int meta = access.getBlockMetadata(x, y, z);

		float minX = 0.1f;
		float minZ = 0.1f;
		float maxX = 0.9f;
		float maxZ = 0.9f;
		float maxY = 1f;

		if(isSamePlant(access, x - 1, y, z, meta)) minX = 0;
		if(isSamePlant(access, x + 1, y, z, meta)) maxX = 1;
		if(isSamePlant(access, x, y, z - 1, meta)) minZ = 0;
		if(isSamePlant(access, x, y, z + 1, meta)) maxZ = 1;
		if(isSamePlant(access, x, y + 1, z, meta)) maxY = 1;

		switch(meta)
		{
		case WINTERGREEN:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case BLUEBERRY:
		{
			maxY = 0.85f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case RASPBERRY:
		{
			maxY = 0.85f;
			if(isSamePlant(access, x, y + 1, z, meta))
				maxY = 1;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case STRAWBERRY:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case BLACKBERRY:
		{
			maxY = 0.85f;
			if(isSamePlant(access, x, y + 1, z, meta))
				maxY = 1;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case BUNCHBERRY:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case CRANBERRY:
		{
			maxY = 0.6f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case SNOWBERRY:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case ELDERBERRY:
		{
			maxY = 0.85f;
			if(isSamePlant(access, x, y + 1, z, meta))
				maxY = 1;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case GOOSEBERRY:
		{
			maxY = 0.75f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case CLOUDBERRY:
		{
			maxY = 0.35f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		default:
		{
			setBlockBounds(minX, 0, minZ, maxX, 1f, maxZ);
			return;
		}
		}
	}

	private boolean isSamePlant(IBlockAccess bAccess, int x, int y, int z, int meta)
	{
		return bAccess.getBlock(x, y, z) == this && bAccess.getBlockMetadata(x, y, z) == meta;
	}

	/* Left-Click Harvest Berries */
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		if (!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getType(meta));

			TEBerryBush te = (TEBerryBush) world.getTileEntity(x, y, z);
			if (te != null && te.hasFruit)
			{
				te.hasFruit = false;
				te.dayHarvested = TFC_Time.getTotalDays();
				world.markBlockForUpdate(x, y, z);
				dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(3 + world.rand.nextFloat() * 5, 10)));
			}
		}
	}

	/* Right-Click Harvest Berries */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getType(meta));

			TEBerryBush te = (TEBerryBush) world.getTileEntity(x, y, z);
			if(te != null && te.hasFruit)
			{
				te.hasFruit = false;
				te.dayHarvested = TFC_Time.getTotalDays();
				world.markBlockForUpdate(x, y, z);
				dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(3 + world.rand.nextFloat() * 5, 10)));
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		lifeCycle(world, x, y, z);
	}

	private void lifeCycle(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			if(!canBlockStay(world, x, y, z))
			{
				this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.setBlockToAir(x, y, z);
				return;
			}

			TileEntity te = world.getTileEntity(x, y, z);
			TEBerryBush tebb = null;
			if (te instanceof TEBerryBush)
				tebb = (TEBerryBush) world.getTileEntity(x, y, z);
			if(tebb != null)
			{
				FloraIndex floraIndex = FloraManager.getInstance().findMatchingIndex(getType(world.getBlockMetadata(x, y, z)));
				float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);

				if(temp >= floraIndex.minTemp && temp < floraIndex.maxTemp)
				{
					if(!tebb.hasFruit && floraIndex.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) && TFC_Time.getMonthsSinceDay(tebb.dayHarvested) > 0)
					{
						tebb.hasFruit = true;
						tebb.dayFruited = TFC_Time.getTotalDays();
						world.markBlockForUpdate(x, y, z);
					}
				}
				else if(temp < floraIndex.minTemp - 5 || temp > floraIndex.maxTemp + 5)
				{
					if(tebb.hasFruit)
					{
						tebb.hasFruit = false;
						world.markBlockForUpdate(x, y, z);
					}
				}

				if(tebb.hasFruit && TFC_Time.getMonthsSinceDay(tebb.dayFruited) > floraIndex.fruitHangTime)
				{
					tebb.hasFruit = false;
					world.markBlockForUpdate(x, y, z);
				}
			}
		}
		else
		{
			world.getTileEntity(x, y, z).validate();
		}
	}

	public String getType(int meta)
	{
		return BlockBerryBush.metaNames[meta];
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		for (int i = 0; i < icons.length; ++i)
		{
			icons[i] = register.registerIcon(Reference.MOD_ID + ":" + "plants/" + metaNames[i]);
			iconsBerries[i] = register.registerIcon(Reference.MOD_ID + ":" + "plants/" + metaNames[i] + " Berry");
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		int meta = access.getBlockMetadata(x, y, z);

		TEBerryBush te = (TEBerryBush) access.getTileEntity(x, y, z);
		if(te != null && te.hasFruit)
			return iconsBerries[meta];

		return icons[meta];
	}

	@Override
	public IIcon getIcon(int i, int meta)
	{
		return iconsBerries[meta];
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && 
				(this.canThisPlantGrowOnThisBlock(world.getBlock(x, y - 1, z)) || 
				isSamePlant(world, x, y - 1, z, world.getBlockMetadata(x, y, z)) && canStack(meta));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack is)
	{
		super.onBlockPlacedBy(world, x, y, z, entityliving, is);
		if(!canBlockStay(world, x, y, z))
		{
			onNeighborBlockChange(world, x, y, z, world.getBlock(x, y, z));
		}
		else
		{
			TEBerryBush te = (TEBerryBush)world.getTileEntity(x, y, z);
			te.dayHarvested = TFC_Time.getTotalDays();
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		lifeCycle(world, x, y, z);
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isGrass(block);
	}

	@Override
	public Item getItemDropped(int i1, Random rand, int i2)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public TileEntity createNewTileEntity(World i, int meta)
	{
		return new TEBerryBush();
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == BLUEBERRY ||meta == RASPBERRY || meta == BLACKBERRY || meta == ELDERBERRY || meta == GOOSEBERRY)
		{
			entity.motionX *= 0.7D;
			entity.motionZ *= 0.7D;
		}

		if(meta == RASPBERRY || meta == BLACKBERRY)
		{
			if(entity instanceof EntityLivingBase)
				entity.attackEntityFrom(DamageSource.cactus, 5);
		}
	}

	private boolean canStack(int meta) {
		return meta == RASPBERRY || meta == BLACKBERRY || meta == ELDERBERRY;
	}
}
