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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBerryBush extends BlockTerraContainer
{
	public static IIcon[] icons;
	public static IIcon[] iconsBerries;
	public static String[] MetaNames;

	public static final int Wintergreen = 0;
	public static final int Blueberry = 1;
	public static final int Raspberry = 2;
	public static final int Strawberry = 3;
	public static final int Blackberry = 4;
	public static final int Bunchberry = 5;
	public static final int Cranberry = 6;
	public static final int Snowberry = 7;
	public static final int Elderberry = 8;
	public static final int Gooseberry = 9;
	public static final int Cloudberry = 10;

	public BlockBerryBush()
	{
		super(Material.plants);
		MetaNames = new String[]{"Wintergreen", "Blueberry", "Raspberry", "Strawberry", "Blackberry", "Bunchberry", "Cranberry", "Snowberry", "Elderberry", "Gooseberry", "Cloudberry"};
		icons = new IIcon[MetaNames.length];
		iconsBerries = new IIcon[MetaNames.length];
		this.setTickRandomly(true);
		this.setCreativeTab(TFCTabs.TFCDecoration);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++)
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
		int _meta = access.getBlockMetadata(x, y, z);

		float minX = 0.1f;
		float minZ = 0.1f;
		float maxX = 0.9f;
		float maxZ = 0.9f;
		float maxY = 1f;

		if(isSamePlant(access, x - 1, y, z, _meta)) minX = 0;
		if(isSamePlant(access, x + 1, y, z, _meta)) maxX = 1;
		if(isSamePlant(access, x, y, z - 1, _meta)) minZ = 0;
		if(isSamePlant(access, x, y, z + 1, _meta)) maxZ = 1;
		if(isSamePlant(access, x, y + 1, z, _meta)) maxY = 1;

		switch(_meta)
		{
		case Wintergreen:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Blueberry:
		{
			maxY = 0.85f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Raspberry:
		{
			maxY = 0.85f;
			if(isSamePlant(access, x, y + 1, z, _meta))
				maxY = 1;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Strawberry:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Blackberry:
		{
			maxY = 0.85f;
			if(isSamePlant(access, x, y + 1, z, _meta))
				maxY = 1;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Bunchberry:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Cranberry:
		{
			maxY = 0.6f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Snowberry:
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Elderberry:
		{
			maxY = 0.85f;
			if(isSamePlant(access, x, y + 1, z, _meta))
				maxY = 1;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Gooseberry:
		{
			maxY = 0.75f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case Cloudberry:
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
		if(bAccess.getBlock(x, y, z) == this && bAccess.getBlockMetadata(x, y, z) == meta)
			return true;
		return false;
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
			if(te != null && te instanceof TEBerryBush)
				tebb = (TEBerryBush) world.getTileEntity(x, y, z);
			if(tebb != null)
			{
				FloraIndex _fi = FloraManager.getInstance().findMatchingIndex(getType(world.getBlockMetadata(x, y, z)));
				float _temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);

				if(_temp >= _fi.minTemp && _temp < _fi.maxTemp)
				{
					if(!tebb.hasFruit && _fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) && TFC_Time.getMonthsSinceDay(tebb.dayHarvested) > 0)
					{
						tebb.hasFruit = true;
						tebb.dayFruited = TFC_Time.getTotalDays();
						world.markBlockForUpdate(x, y, z);
					}
				}
				else if(_temp < _fi.minTemp - 5 || _temp > _fi.maxTemp + 5)
				{
					if(tebb.hasFruit)
					{
						tebb.hasFruit = false;
						world.markBlockForUpdate(x, y, z);
					}
				}

				if(tebb.hasFruit && TFC_Time.getMonthsSinceDay(tebb.dayFruited) > _fi.fruitHangTime)
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
		return BlockBerryBush.MetaNames[meta];
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
			icons[i] = register.registerIcon(Reference.ModID + ":" + "plants/" + MetaNames[i]);
			iconsBerries[i] = register.registerIcon(Reference.ModID + ":" + "plants/" + MetaNames[i] + " Berry");
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
						(isSamePlant(world, x, y - 1, z, world.getBlockMetadata(x, y, z)) && canStack(meta)));
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
		int _meta = world.getBlockMetadata(x, y, z);
		if(_meta == Blueberry ||_meta == Raspberry || _meta == Blackberry || _meta == Elderberry || _meta == Gooseberry)
		{
			entity.motionX *= 0.7D;
			entity.motionZ *= 0.7D;
		}

		if(_meta == Raspberry || _meta == Blackberry)
		{
			if(entity instanceof EntityLivingBase)
				entity.attackEntityFrom(DamageSource.cactus, 5);
		}
	}

	private boolean canStack(int meta) {
		return meta == Raspberry || meta == Blackberry || meta == Elderberry;
	}
}
