package com.bioxx.tfc.Blocks.Flora;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Util.Helper;

public class BlockFruitLeaves extends BlockTerraContainer
{
	int adjacentTreeBlocks[];
	String[] WoodNames = Global.FRUIT_META_NAMES;
	IIcon[] icons = new IIcon[16];
	IIcon[] iconsDead = new IIcon[16];
	public static IIcon[] iconsFruit = new IIcon[16];
	IIcon[] iconsOpaque = new IIcon[16];
	IIcon[] iconsDeadOpaque = new IIcon[16];
	public static IIcon[] iconsFlowers = new IIcon[16];
	
	int Offset = 0;
	
	public BlockFruitLeaves(int offset)
	{
		super(Material.leaves);
		this.setTickRandomly(true);
		Offset = offset;
	}

	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return TerraFirmaCraft.proxy.foliageColorMultiplier(par1IBlockAccess, par2, par3, par4);
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
	public IIcon getIcon(int i, int meta)
	{
		if (TerraFirmaCraft.proxy.getGraphicsLevel())
			return icons[(meta & 7)];
		else
			return iconsOpaque[(meta & 7)];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < 9; i++)
		{
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Leaves");
			iconsOpaque[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Leaves Opaque");
			iconsDead[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Leaves");
			iconsDeadOpaque[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Leaves Opaque");
			iconsFruit[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Fruit");
			iconsFlowers[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + WoodNames[i] + " Flowers");
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		return TFCOptions.enableInnerGrassFix;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if(!world.isRemote)
		{
			if(!canStay(world, i, j, k, this))
			{
				world.setBlockToAir(i, j, k);
				return;
			}

			int meta = world.getBlockMetadata(i, j, k);
			int m = meta - 8;

			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(this, m));
			FloraIndex fi2 = FloraManager.getInstance().findMatchingIndex(getType(this, meta));
			
			float temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);
			TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(i, j, k);
			if(te != null)
			{
				if(fi2 != null)
				{
					if(temp >= fi2.minTemp && temp < fi2.maxTemp)
					{
						if(fi2.inHarvest(TFC_Time.getSeasonAdjustedMonth(k)) && !te.hasFruit && TFC_Time.getMonthsSinceDay(te.dayHarvested) > 2)
						{
							if(meta < 8)
							{
								meta += 8;
								te.hasFruit = true;
								te.dayFruited = (int) TFC_Time.getTotalDays();
							}
							world.setBlockMetadataWithNotify(i, j, k, meta, 0x2);
						}
					}
					else
					{
						if(meta >= 8 && rand.nextInt(10) == 0)
						{
							if(te.hasFruit)
							{
								te.hasFruit = false;
								world.setBlockMetadataWithNotify(i, j, k, meta - 8, 0x2);
							}
						}
					}
				}

				if(fi != null)
				{
					if(!fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(k)))
					{
						if(world.getBlockMetadata(i, j, k) >= 8)
						{
							if(te.hasFruit)
							{
								te.hasFruit = false;
								world.setBlockMetadataWithNotify(i, j, k, meta-8, 0x2); 
							}
						}
					}
				}

				if(rand.nextInt(100) > 50)
					world.markBlockForUpdate(i, j, k);
			}
		}
	}

	public static boolean canStay(World world, int i, int j, int k, Block block)
	{
		if((!world.isAirBlock(i, j + 1, k) && !world.isAirBlock(i, j + 2, k) &&
				world.getBlock(i, j + 2, k) == block) ||
				world.getBlock(i, j + 1, k) == TFCBlocks.fruitTreeWood ||
				world.getBlock(i, j + 2, k) == TFCBlocks.fruitTreeWood)
		{
			return false;
		}
		return true;
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
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block l)
	{
		Random R = new Random();
		if (!par1World.isRemote)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);

			if (true)
			{
				byte var7 = 1;
				int var8 = var7 + 1;
				byte var9 = 32;
				int var10 = var9 * var9;
				int var11 = var9 / 2;
				adjacentTreeBlocks = null;
				if (this.adjacentTreeBlocks == null)
					this.adjacentTreeBlocks = new int[var9 * var9 * var9];

				int var12;

				if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
				{
					int var13;
					int var14;
					int var15;
					Block block;

					for (var12 = -var7; var12 <= var7; ++var12)
					{
						for (var13 = -var7; var13 <= var7; ++var13)
						{
							for (var14 = -var7; var14 <= var7; ++var14)
							{
								block = par1World.getBlock(par2 + var12, par3 + var13, par4 + var14);
								if (block == TFCBlocks.fruitTreeWood)
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
								else if (block == this && var6 == par1World.getBlockMetadata(par2 + var12, par3 + var13, par4 + var14))
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
								else
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
							}
						}
					}

					for (var12 = 1; var12 <= 4; ++var12)
					{
						for (var13 = -var7; var13 <= var7; ++var13)
						{
							for (var14 = -var7; var14 <= var7; ++var14)
							{
								for (var15 = -var7; var15 <= var7; ++var15)
								{
									if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
									{
										if (this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
											this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;

										if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
											this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
									}
								}
							}
						}
					}
				}

				var12 = this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];

				if (var12 >= 0)
					;//par1World.setBlockMetadata(par2, par3, par4, var6 & -9);
				else
					this.destroyLeaves(par1World, par2, par3, par4);
			}
		}
	}

	private void destroyLeaves(World world, int i, int j, int k)
	{
		world.setBlockToAir(i, j, k);
	}

	private void removeLeaves(World world, int i, int j, int k)
	{
		//        dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
		//        if(new Random().nextInt(100) < 30)
		//            dropBlockAsItem(world, i, j, k, new ItemStack(Item.stick, 1));
		world.setBlockToAir(i, j, k);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(20) != 0 ? 0 : 1;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return Item.getItemFromBlock(TFCBlocks.Sapling);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1)
	{
		if (!world.isRemote)
		{
		}
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(this, world.getBlockMetadata(i, j, k) & 7));

			if(fi != null && (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(k)) || fi.inHarvest(((TFC_Time.getSeasonAdjustedMonth(k) - 1) + 12)%12) && (meta & 8) == 8))
			{
				TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(i, j, k);
				if(te != null && te.hasFruit)
				{
					te.hasFruit = false;
					te.dayHarvested = (int) TFC_Time.getTotalDays();
					world.setBlockMetadataWithNotify(i, j, k, meta - 8, 3);
					dropBlockAsItem(world, i, j, k, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(5 + (world.rand.nextFloat() * 20), 10)));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity entity)
	{
		super.onEntityWalking(world, i, j, k, entity);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEFruitLeaves();
	}

}
