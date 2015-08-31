package com.bioxx.tfc.Blocks.Flora;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Util.Helper;

public class BlockFruitLeaves extends BlockTerraContainer
{
	//private int adjacentTreeBlocks[];
	private String[] woodNames = Global.FRUIT_META_NAMES;
	private IIcon[] icons = new IIcon[16];
	//private IIcon[] iconsDead = new IIcon[16];
	public static IIcon[] iconsFruit = new IIcon[16];
	private IIcon[] iconsOpaque = new IIcon[16];
	//private IIcon[] iconsDeadOpaque = new IIcon[16];
	public static IIcon[] iconsFlowers = new IIcon[16];

	//private int offset;

	public BlockFruitLeaves(int offset)
	{
		super(Material.leaves);
		this.setTickRandomly(true);
		//this.offset = offset;
	}

	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return TerraFirmaCraft.proxy.foliageColorMultiplier(bAccess, x, y, z);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.leavesFruitRenderId;
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

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (TerraFirmaCraft.proxy.getGraphicsLevel())
			return icons[meta & 7];
		else
			return iconsOpaque[meta & 7];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < 9; i++)
		{
			icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + woodNames[i] + " Leaves");
			iconsOpaque[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + woodNames[i] + " Leaves Opaque");
			//iconsDead[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Leaves");
			//iconsDeadOpaque[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Leaves Opaque");
			iconsFruit[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + woodNames[i] + " Fruit");
			iconsFlowers[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + woodNames[i] + " Flowers");
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		this.lifeCycle(world, x, y, z);
	}

	private void lifeCycle(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			if (!canStay(world, x, y, z))
			{
				destroyLeaves(world, x, y, z);
				return;
			}

			Random rand = new Random();
			int meta = world.getBlockMetadata(x, y, z);
			int m = meta - 8;

			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getType(this, m));
			FloraIndex fi2 = manager.findMatchingIndex(getType(this, meta));

			float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
			TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(x, y, z);
			if(te != null)
			{
				if(fi2 != null)
				{
					if(temp >= fi2.minTemp && temp < fi2.maxTemp)
					{
						if (fi2.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) && !te.hasFruit && TFC_Time.getMonthsSinceDay(te.dayHarvested) > 1)
						{
							if(meta < 8)
							{
								meta += 8;
								te.hasFruit = true;
								te.dayFruited = TFC_Time.getTotalDays();
							}
							world.setBlockMetadataWithNotify(x, y, z, meta, 0x2);
						}
					}
					else
					{
						if(meta >= 8 && rand.nextInt(10) == 0)
						{
							if(te.hasFruit)
							{
								te.hasFruit = false;
								world.setBlockMetadataWithNotify(x, y, z, meta - 8, 0x2);
							}
						}
					}
				}

				if(fi != null)
				{
					if(!fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)))
					{
						if(world.getBlockMetadata(x, y, z) >= 8)
						{
							if(te.hasFruit)
							{
								te.hasFruit = false;
								world.setBlockMetadataWithNotify(x, y, z, meta-8, 0x2); 
							}
						}
					}
				}

				if (rand.nextInt(100) > 50)
					world.markBlockForUpdate(x, y, z);
			}
		}
	}

	public static boolean canStay(World world, int x, int y, int z)
	{
		//Only leaf blocks that are within one block and on the same level or 1 above a branch or the top of the trunk
		for (int i = 1; i >= -1; i--)
		{
			for (int j = 0; j >= -1; j--)
			{
				for (int k = 1; k >= -1; k--)
				{
					if (world.getBlock(i + x, j + y, k + z) == TFCBlocks.fruitTreeWood &&
							world.getBlock(i + x, j + y + 1, k + z) != TFCBlocks.fruitTreeWood) // Only branches or the top of the trunk
						return true;
				}
			}
		}

		return false;
	}

	public static String getType(Block block, int meta)
	{
		if(block == TFCBlocks.fruitTreeLeaves)
		{
			switch(meta)
			{
			case 0: return Global.FRUIT_META_NAMES[0];
			case 1: return Global.FRUIT_META_NAMES[1];
			case 2: return Global.FRUIT_META_NAMES[2];
			case 3: return Global.FRUIT_META_NAMES[3];
			case 4: return Global.FRUIT_META_NAMES[4];
			case 5: return Global.FRUIT_META_NAMES[5];
			case 6: return Global.FRUIT_META_NAMES[6];
			case 7: return Global.FRUIT_META_NAMES[7];
			}
		}
		else
		{
			switch(meta)
			{
			case 0: return Global.FRUIT_META_NAMES[8];
			}
		}
		return "";
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		super.onNeighborBlockChange(world, x, y, z, b);
		lifeCycle(world, x, y, z);
	}

	private void destroyLeaves(World world, int x, int y, int z)
	{
		world.setBlockToAir(x, y, z);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float dropChance, int fortune)
	{
		// Intentionally Blank
	}

	/* Left-Click Harvest Fruit */
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		if (!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getType(this, world.getBlockMetadata(x, y, z) & 7));

			if (fi != null && (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) || fi.inHarvest((TFC_Time.getSeasonAdjustedMonth(z) + 11) % 12) && (meta & 8) == 8))
			{
				TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(x, y, z);
				if (te != null && te.hasFruit)
				{
					te.hasFruit = false;
					te.dayHarvested = TFC_Time.getTotalDays();
					world.setBlockMetadataWithNotify(x, y, z, meta - 8, 3);
					dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(4 + (world.rand.nextFloat() * 12), 10)));
				}
			}
		}
	}

	/* Right-Click Harvest Fruit */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getType(this, world.getBlockMetadata(x, y, z) & 7));

			if (fi != null && (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) || fi.inHarvest((TFC_Time.getSeasonAdjustedMonth(z) + 11) % 12) && (meta & 8) == 8))
			{
				TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(x, y, z);
				if(te != null && te.hasFruit)
				{
					te.hasFruit = false;
					te.dayHarvested = TFC_Time.getTotalDays();
					world.setBlockMetadataWithNotify(x, y, z, meta - 8, 3);
					dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(4 + (world.rand.nextFloat() * 12), 10)));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEFruitLeaves();
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
