package com.bioxx.tfc.Blocks.Flora;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEFruitTreeWood;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;

public class BlockFruitWood extends BlockTerraContainer
{
	private IIcon[] icons = new IIcon[Global.FRUIT_META_NAMES.length];

	public BlockFruitWood()
	{
		super(Material.wood);
		this.setBlockBounds(0.3f, 0, 0.3f, 0.7f, 1, 0.7f); //Default block bounds set to trunk state
	}

	private boolean checkOut(World world, int i, int j, int k, int l)
	{
		return world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == l;
	}

	@Override
	public int damageDropped(int j)
	{
		return j;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return icons[j];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < 9; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + Global.FRUIT_META_NAMES[i] + " Wood");
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//we need to make sure the player has the correct tool out
		boolean isAxeorSaw = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if (equip != null)
		{
			int[] equipIDs = OreDictionary.getOreIDs(equip);
			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				if (name.startsWith("itemAxe") || name.startsWith("itemSaw"))
				{
					isAxeorSaw = true;
					break;
				}
			}
		}
		if(isAxeorSaw)
		{
			int x = i;
			int y = 0;
			int z = k;
			//int count = 0;

			if(world.getBlock(i, j+1, k) == this || world.getBlock(i, j-1, k) == this)
			{
				//super.harvestBlock(world, entityplayer, i, j, k, l);
				boolean checkArray[][][] = new boolean[11][50][11];

				if(TFC_Core.isGrass(world.getBlock(i, j+y-1, k)) || TFC_Core.isDirt(world.getBlock(i, j+y-1, k)))
				{
					boolean reachedTop = false;
					while(!reachedTop)
					{
						if (world.isAirBlock(x, j+y+1, z))
						{
							reachedTop = true;
						}
						scanLogs(world,i,j+y,k,l,checkArray,6,y,6);
						y++;
					}
				}
			}
			else if(world.getBlock(i + 1, j, k) == this ||
					world.getBlock(i - 1, j, k) == this ||
					world.getBlock(i, j, k + 1) == this ||
					world.getBlock(i, j, k - 1) == this)
			{
				Random r = new Random();
				if(r.nextInt(100) > 50 && isAxeorSaw)
				{
					if(l < 8 && (
							world.getBlock(i + 1, j, k) == TFCBlocks.fruitTreeLeaves2 ||
							world.getBlock(i - 1, j, k) == TFCBlocks.fruitTreeLeaves2 ||
							world.getBlock(i, j, k + 1) == TFCBlocks.fruitTreeLeaves2 ||
							world.getBlock(i, j, k - 1) == TFCBlocks.fruitTreeLeaves2 ||
							world.getBlock(i, j + 1, k) == TFCBlocks.fruitTreeLeaves2 ||
							world.getBlock(i, j - 1, k) == TFCBlocks.fruitTreeLeaves2))
					{
						l += 8;
					}
					dropBlockAsItem(world, i, j, k, new ItemStack(TFCBlocks.fruitTreeSapling, 1, l));
				}
			}
		}
		/*else
		{
			//world.setBlock(i, j, k, this, l, 0x2);
		}*/
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return TFCItems.logs;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		boolean check = false;
		for(int h = -1; h <= 1; h++)
		{
			for(int g = -1; g <= 1; g++)
			{
				for(int f = -1; f <= 1; f++)
				{
					if(world.getBlock(i + h, j + g, k + f) == this && world.getBlockMetadata(i + h, j + g, k + f) == world.getBlockMetadata(i, j, k))
						check = true;
				}
			}
		}
		if(!check)
			world.setBlockToAir(i, j, k);
	}

	private void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray,int x, int y, int z)
	{
		if(y >= 0)
		{
			checkArray[x][y][z] = true;
			int offsetX = 0;
			int offsetY = 0;
			int offsetZ = 0;

			for (offsetY = 0; offsetY <= 1; offsetY++)
			{
				for (offsetX = -1; offsetX <= 1; offsetX++)
				{
					for (offsetZ = -1; offsetZ <= 1; offsetZ++)
					{
						if(x + offsetX < 11 && x + offsetX >= 0 && z + offsetZ < 11 && z + offsetZ >= 0 && y + offsetY < 50 && y + offsetY >= 0)
						{
							if(checkOut(world, i + offsetX, j + offsetY, k + offsetZ, l) && !checkArray[x + offsetX][y + offsetY][z + offsetZ])
								scanLogs(world,i + offsetX, j + offsetY, k + offsetZ, l, checkArray, x + offsetX, y + offsetY, z + offsetZ);
						}
					}
				}
			}
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.woodFruitRenderId;
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
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		if(world.getBlock(i, j-1, k) == this || world.getBlock(i, j-1, k).isOpaqueCube())
			return AxisAlignedBB.getBoundingBox(i+0.3, j, k+0.3, i+0.7, j+1, k+0.7);
		return AxisAlignedBB.getBoundingBox(i, j + 0.4, k, i + 1, j + 0.6, k + 1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		if(world.getBlock(i, j-1, k) == this || world.getBlock(i, j-1, k).isOpaqueCube())
			return AxisAlignedBB.getBoundingBox(i+0.3, j, k+0.3, i+0.7, j+1, k+0.7);
		return AxisAlignedBB.getBoundingBox(i, j+0.4, k, i+1, j+0.6, k+1);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k)
	{
		if (world.getBlock(i, j - 1, k) == this || world.getBlock(i, j - 1, k).isOpaqueCube())
			this.setBlockBounds(0.3f, 0, 0.3f, 0.7f, 1, 0.7f);
		else
			this.setBlockBounds(0, 0.4f, 0, 1, 0.6f, 1);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		// Growth is handled in TEFruitTreeWood
		/*if (!world.isRemote)
		{
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(BlockFruitWood.getType(world.getBlockMetadata(i, j, k)));

			float temp = TFC_Climate.getHeightAdjustedTemp(world, i, j, k);

			if (world.getTileEntity(i, j, k) instanceof TEFruitTreeWood &&
				TFC_Time.getSeasonAdjustedMonth(k) < 6 &&
				fi != null && temp >= fi.minTemp && temp < fi.maxTemp)
			{
				TEFruitTreeWood te = (TEFruitTreeWood) world.getTileEntity(i, j, k);
				int t = 1;
				if (TFC_Time.getSeasonAdjustedMonth(k) < 3)
					t = 2;

				int leafGrowthRate = 20;
				int trunkGrowTime = 30;
				int branchGrowTime = 20;

				//grow upward
				if (te.birthTimeWood + trunkGrowTime < TFC_Time.getTotalDays() &&
					te.height < 3 && te.isTrunk && rand.nextInt(16 / t) == 0 &&
					(world.isAirBlock(i, j + 1, k) || world.getBlock(i, j + 1, k) == TFCBlocks.fruitTreeLeaves))
				{
					world.setBlock(i, j + 1, k, this, world.getBlockMetadata(i, j, k), 0x2);
					if (world.getTileEntity(i, j + 1, k) instanceof TEFruitTreeWood)
					{
						TEFruitTreeWood trunkTE = ((TEFruitTreeWood) world.getTileEntity(i, j + 1, k));
						trunkTE.setTrunk(true);
						trunkTE.setHeight(te.height + 1);
						trunkTE.initBirth();
						te.increaseBirthWood(trunkGrowTime);
					}
				}
				else if (te.birthTimeWood + branchGrowTime < TFC_Time.getTotalDays() &&te.height == 2 && te.isTrunk && rand.nextInt(16 / t) == 0 &&
							world.getBlock(i, j + 1, k) != this)
				{
					int r = rand.nextInt(4);
					if (r == 0 && world.blockExists(i + 1, j, k) && (world.isAirBlock(i + 1, j, k) || world.getBlock(i + 1, j, k) == TFCBlocks.fruitTreeLeaves))
					{
						world.setBlock(i + 1, j, k, this, world.getBlockMetadata(i, j, k), 0x2);
						if (world.getTileEntity(i + 1, j, k) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = ((TEFruitTreeWood) world.getTileEntity(i + 1, j, k));
							branchTE.setTrunk(false);
							branchTE.setHeight(te.height);
							branchTE.initBirth();
						}
					}
					else if (r == 1 && world.blockExists(i, j, k - 1) && (world.isAirBlock(i, j, k - 1) || world.getBlock(i, j, k - 1) == TFCBlocks.fruitTreeLeaves))
					{
						world.setBlock(i, j, k - 1, this, world.getBlockMetadata(i, j, k), 0x2);
						if (world.getTileEntity(i, j, k - 1) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = ((TEFruitTreeWood) world.getTileEntity(i, j, k - 1));
							branchTE.setTrunk(false);
							branchTE.setHeight(te.height);
							branchTE.initBirth();
						}
					}
					else if (r == 2 && world.blockExists(i - 1, j, k) && (world.isAirBlock(i - 1, j, k) || world.getBlock(i - 1, j, k) == TFCBlocks.fruitTreeLeaves))
					{
						world.setBlock(i - 1, j, k, this, world.getBlockMetadata(i, j, k), 0x2);
						if (world.getTileEntity(i - 1, j, k) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = (TEFruitTreeWood) world.getTileEntity(i - 1, j, k);
							branchTE.setTrunk(false);
							branchTE.setHeight(te.height);
							branchTE.initBirth();
						}
					}
					else if (r == 3 && world.blockExists(i, j, k + 1) && (world.isAirBlock(i, j, k + 1) || world.getBlock(i, j, k + 1) == TFCBlocks.fruitTreeLeaves))
					{
						world.setBlock(i, j, k + 1, this, world.getBlockMetadata(i, j, k), 0x2);
						if (world.getTileEntity(i, j, k + 1) instanceof TEFruitTreeWood)
						{
							TEFruitTreeWood branchTE = (TEFruitTreeWood) world.getTileEntity(i, j, k + 1);
							branchTE.setTrunk(false);
							branchTE.setHeight(te.height);
							branchTE.initBirth();
						}
					}

					te.increaseBirthWood(branchGrowTime);
				}
				else if (te.birthTimeWood + 1 < TFC_Time.getTotalDays() && rand.nextInt(leafGrowthRate) == 0 && world.getBlock(i, j + 2, k) != this)
				{
					if (world.isAirBlock(i, j + 1, k) && world.isAirBlock(i, j + 2, k) && BlockFruitLeaves.canStay(world, i, j + 1, k)) //above
						world.setBlock(i, j + 1, k, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i + 1, j, k) && world.isAirBlock(i + 1, j, k) && world.isAirBlock(i + 1, j + 1, k) && BlockFruitLeaves.canStay(world, i + 1, j, k)) //+x
						world.setBlock(i + 1, j, k, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i - 1, j, k) && world.isAirBlock(i - 1, j, k) && world.isAirBlock(i - 1, j + 1, k) && BlockFruitLeaves.canStay(world, i - 1, j, k)) //-x
						world.setBlock(i - 1, j, k, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i, j, k + 1) && world.isAirBlock(i, j, k + 1) && world.isAirBlock(i, j + 1, k + 1) && BlockFruitLeaves.canStay(world, i, j, k + 1)) //+z
						world.setBlock(i, j, k + 1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i, j, k - 1) && world.isAirBlock(i, j, k - 1) && world.isAirBlock(i, j + 1, k - 1) && BlockFruitLeaves.canStay(world, i, j, k - 1)) //-z
						world.setBlock(i, j, k - 1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i + 1, j, k - 1) && world.isAirBlock(i + 1, j, k - 1) && world.isAirBlock(i + 1, j + 1, k - 1) && BlockFruitLeaves.canStay(world, i + 1, j, k - 1)) //+x/-z
						world.setBlock(i + 1, j, k - 1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i + 1, j, k + 1) && world.isAirBlock(i + 1, j, k + 1) && world.isAirBlock(i + 1, j + 1, k + 1) && BlockFruitLeaves.canStay(world, i + 1, j, k + 1)) //+x/+z
						world.setBlock(i + 1, j, k + 1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i - 1, j, k - 1) && world.isAirBlock(i - 1, j, k - 1) && world.isAirBlock(i - 1, j + 1, k - 1) && BlockFruitLeaves.canStay(world, i - 1, j, k - 1)) //-x/-z
						world.setBlock(i - 1, j, k - 1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
					else if (world.blockExists(i - 1, j, k + 1) && world.isAirBlock(i - 1, j, k + 1) && world.isAirBlock(i - 1, j + 1, k + 1) && BlockFruitLeaves.canStay(world, i - 1, j, k + 1)) //-x/+z
						world.setBlock(i - 1, j, k + 1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				}
			}
		}*/
	}

	/*public void surroundWithLeaves(World world, int i, int j, int k)
	{
		for (int y = 0; y <= 1; y++)
		{
			for (int x = 1; x >= -1; x--)
			{
				for (int z = 1; z >= -1; z--)
				{
					if(world.isAirBlock(i+x, j+y, k+z) && (world.isAirBlock(i+x, j+y+1, k+z) || world.isAirBlock(i+x, j+y+2, k+z)))
					{
						int meta = world.getBlockMetadata(i, j, k);
						Block block = meta < 8 ? TFCBlocks.fruitTreeLeaves : TFCBlocks.fruitTreeLeaves2;
						if(world.getBlock(i, j, k) != TFCBlocks.fruitTreeWood)
							block = Blocks.air;
						world.setBlock(i+x, j+y, k+z, block, world.getBlockMetadata(i, j, k), 0x2);
					}
				}
			}
		}
	}*/

	public static String getType(int meta)
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
		case 8: return Global.FRUIT_META_NAMES[8];
		}
		return "";
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEFruitTreeWood();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		if(!world.isRemote && checkOut(world,x,y-1,z,metadata) && world.getTileEntity(x, y-1, z) != null)
			((TEFruitTreeWood)world.getTileEntity(x, y-1, z)).initBirth();
		super.breakBlock(world, x, y, z, block, metadata);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
