package TFC.Blocks.Flora;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.API.Util.Helper;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Food.FloraIndex;
import TFC.Food.FloraManager;
import TFC.Food.ItemFoodTFC;
import TFC.TileEntities.TEFruitLeaves;

public class BlockFruitLeaves extends BlockTerraContainer
{
	int adjacentTreeBlocks[];

	Icon[] icons = new Icon[16];
	Icon[] iconsDead = new Icon[16];
	public static Icon[] iconsFruit = new Icon[16];
	Icon[] iconsOpaque = new Icon[16];
	Icon[] iconsDeadOpaque = new Icon[16];
	public static Icon[] iconsFlowers = new Icon[16];

	int Offset = 0;

	public BlockFruitLeaves(int i, int offset) 
	{
		super(i, Material.leaves);
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
	public Icon getIcon(int i, int meta)
	{
		if (TerraFirmaCraft.proxy.getGraphicsLevel())
		{
			return icons[(meta & 7)];
		}
		else
		{
			return iconsOpaque[(meta & 7)];
		}
	}

	String[] WoodNames = {"Red Apple","Banana","Orange","Green Apple","Lemon","Olive","Cherry","Peach","Plum"};

	@Override
	public void registerIcons(IconRegister iconRegisterer)
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
			if(!canStay(world,i,j,k,blockID))
			{
				world.setBlockToAir(i, j, k);
				return;
			}

			int meta = world.getBlockMetadata(i, j, k);
			int m = meta - 8;

			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(blockID, m));
			FloraIndex fi2 = FloraManager.getInstance().findMatchingIndex(getType(blockID, meta));

			float temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);
			TEFruitLeaves te = (TEFruitLeaves) world.getBlockTileEntity(i, j, k);
			if(te != null)
			{
				if(fi2 != null)
				{
					if(temp >= fi2.minTemp && temp < fi2.maxTemp)
					{
						if(fi2.inHarvest(TFC_Time.getSeason(k)) && !te.hasFruit && TFC_Time.getMonthsSinceDay(te.dayHarvested) > 2)
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
								world.setBlockMetadataWithNotify(i, j, k, meta-8, 0x2); 
							}
						}
					}
				}
				if(fi != null)
				{
					if(!fi.inHarvest(TFC_Time.getSeason(k)))
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
		world.setBlock(i, j, k, 0);
	}

	private void removeLeaves(World world, int i, int j, int k)
	{
		//        dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
		//        if(new Random().nextInt(100) < 30)
		//            dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.stick, 1));
		world.setBlock(i, j, k, 0);
	}

	@Override
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

			if(fi != null && (fi.inHarvest(TFC_Time.getSeason(k)) || fi.inHarvest(((TFC_Time.getSeason(k)-1)+12)%12) && (meta & 8) == 8))
			{
				TEFruitLeaves te = (TEFruitLeaves) world.getBlockTileEntity(i, j, k);
				if(te != null && te.hasFruit)
				{
					te.hasFruit = false;
					te.dayHarvested = (int) TFC_Time.getTotalDays();
					world.setBlockMetadataWithNotify(i, j, k, meta - 8, 3);
					dropBlockAsItem_do(world, i, j, k, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(5+(world.rand.nextFloat()*20),10)));
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
	public TileEntity createNewTileEntity(World var1) {
		return new TEFruitLeaves();
	}
}
