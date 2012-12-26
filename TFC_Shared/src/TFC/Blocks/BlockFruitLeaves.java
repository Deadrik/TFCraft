package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Textures;
import TFC.Core.TFC_Time;
import TFC.Core.TFC_Settings;
import TFC.Food.FloraIndex;
import TFC.Food.FloraManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BlockFruitLeaves extends Block
{
	private int baseIndexInPNG;
	int adjacentTreeBlocks[];

	public BlockFruitLeaves(int i, int j) 
	{
		super(i, Material.leaves);
		baseIndexInPNG = j;
		this.setTickRandomly(true);
	}

	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{

			return TerraFirmaCraft.proxy.foliageColorMultiplier(par1IBlockAccess, par2, par3, par4);

		//return 0xFFFFFF;
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
	public int getBlockTextureFromSideAndMetadata(int i, int meta)
	{
		int index = baseIndexInPNG;

		if (TerraFirmaCraft.proxy.getGraphicsLevel())
		{
			index = baseIndexInPNG+(meta & 7);
		}
		else
		{
			index = baseIndexInPNG+(meta & 7) + 16;
		}

		if(TFC_Time.currentMonth >= 3 && TFC_Time.currentMonth < 9)
		{
			return index;
		}
		else
		{
			return index - 32;
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
		return TFC_Settings.enableInnerGrassFix;
	}
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if(!world.isRemote)
		{
			if(!canStay(world,i,j,k,blockID))
			{
				world.setBlock(i, j, k, 0);
				world.markBlockForUpdate(i, j, k);
				return;
			}

			int meta = world.getBlockMetadata(i, j, k);
			int m = meta - 8;

			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(blockID, m));
			FloraIndex fi2 = FloraManager.getInstance().findMatchingIndex(getType(blockID, meta));
			
			float temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);
			
			if(fi2 != null)
			{
				if(temp >= fi2.minTemp && temp < fi2.maxTemp)
				{
					if(fi2.inHarvest(TFC_Time.currentMonth))
					{
						if(rand.nextInt(10) == 0)
						{
							if(meta < 8)
							{
								meta += 8;
							}
							world.setBlockMetadata(i, j, k, meta); 
							world.markBlockForUpdate(i, j, k);
						}
					}
				}
				else
				{
					if(meta >= 8 && rand.nextInt(10) == 0)
					{
						world.setBlockMetadata(i, j, k, meta-8); 
						world.markBlockForUpdate(i, j, k);
					}
				}
			}
			if(fi != null)
			{
				if(!fi.inHarvest(TFC_Time.currentMonth))
				{
					if(world.getBlockMetadata(i, j, k) >= 8)
					{
						world.setBlockMetadata(i, j, k, meta-8); 
						world.markBlockForUpdate(i, j, k);
					}
				}
			}
			if(rand.nextInt(100) > 50)
				world.markBlockForUpdate(i, j, k);
		}
		return;
	}

	public static boolean canStay(World world, int i, int j, int k, int id)
	{
		if((world.getBlockId(i, j+1, k) != 0 && world.getBlockId(i, j+2, k) != 0 && world.getBlockId(i, j+2, k) == id) ||
				world.getBlockId(i, j+1, k) == TFCBlocks.fruitTreeWood.blockID || 
				world.getBlockId(i, j+2, k) == TFCBlocks.fruitTreeWood.blockID)
		{
			return false;
		}
		return true;
	}

	public static String getType(int id, int meta)
	{
		if(id == TFCBlocks.fruitTreeLeaves.blockID)
		{
			switch(meta)
			{
			case 0:
			{
				return "red apple";
			}
			case 1:
			{
				return "banana";
			}
			case 2:
			{
				return "orange";
			}
			case 3:
			{
				return "green apple";
			}
			case 4:
			{
				return "lemon";
			}
			case 5:
			{
				return "olive";
			}
			case 6:
			{
				return "cherry";
			}
			case 7:
			{
				return "peach";
			}
			}
		}
		else
		{
			switch(meta)
			{
			case 0:
			{
				return "plum";
			}
			case 1:
			{
				return "cacao";
			}
			}
		}
		return "";
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int l)
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
				{
					this.adjacentTreeBlocks = new int[var9 * var9 * var9];
				}

				int var12;

				if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
				{
					int var13;
					int var14;
					int var15;

					for (var12 = -var7; var12 <= var7; ++var12)
					{
						for (var13 = -var7; var13 <= var7; ++var13)
						{
							for (var14 = -var7; var14 <= var7; ++var14)
							{
								var15 = par1World.getBlockId(par2 + var12, par3 + var13, par4 + var14);

								if (var15 == TFCBlocks.fruitTreeWood.blockID)
								{
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
								}
								else if (var15 == blockID && var6 == par1World.getBlockMetadata(par2 + var12, par3 + var13, par4 + var14))
								{
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
								}
								else
								{
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
								}
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
										{
											this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
										}
									}
								}
							}
						}
					}
				}

				var12 = this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];

				if (var12 >= 0)
				{
					//par1World.setBlockMetadata(par2, par3, par4, var6 & -9);
				}
				else
				{
					this.destroyLeaves(par1World, par2, par3, par4);
				}
			}
		}
	}

	private void destroyLeaves(World world, int i, int j, int k)
	{
		world.setBlockWithNotify(i, j, k, 0);
	}

	private void removeLeaves(World world, int i, int j, int k)
	{
		//        dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
		//        if(new Random().nextInt(100) < 30)
		//            dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.stick, 1));
		world.setBlockWithNotify(i, j, k, 0);
	}

	public int quantityDropped(Random random)
	{
		return random.nextInt(20) != 0 ? 0 : 1;
	}
	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TFCBlocks.Sapling.blockID;
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
			FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(blockID, world.getBlockMetadata(i, j, k) & 7));

			if(fi != null && (fi.inHarvest(TFC_Time.currentMonth) || fi.inHarvest(TFC_Time.lastMonth)) && (meta & 8) == 8)
			{
				world.setBlockMetadata(i, j, k, meta - 8);
				dropBlockAsItem_do(world, i, j, k, fi.getOutput());
				return true;
			}
		}
		return false;
	}

	public void onEntityWalking(World world, int i, int j, int k, Entity entity)
	{
		super.onEntityWalking(world, i, j, k, entity);
	}

	@Override
	public String getTextureFile() 
	{
		return TFC_Textures.VegetationSheet;
	}

}
