package com.bioxx.tfc.Blocks.Flora;

import java.util.Random;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.TileEntities.TileEntityFruitTreeWood;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFruitWood extends BlockTerraContainer
{
	String[] WoodNames = {"Red Apple","Banana","Orange","Green Apple","Lemon","Olive","Cherry","Peach","Plum"};
	IIcon[] icons = new IIcon[9];

	public BlockFruitWood()
	{
		super(Material.wood);
	}

	private boolean checkOut(World world, int i, int j, int k, int l)
	{
		if(world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == l)
			return true;
		return false;
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
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/"+WoodNames[i]+" Wood");
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//we need to make sure the player has the correct tool out
		boolean isAxeorSaw = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if(equip!=null)
		{
			for(int cnt = 0; cnt < Recipes.Axes.length && !isAxeorSaw; cnt++)
			{
				if(equip.getItem() == Recipes.Axes[cnt])
					isAxeorSaw = true;
			}

			for(int cnt = 0; cnt < Recipes.Saws.length && !isAxeorSaw; cnt++)
			{
				if(equip.getItem() == Recipes.Saws[cnt])
					isAxeorSaw = true;
			}
		}
		if(isAxeorSaw)
		{
			int x = i;
			int y = 0;
			int z = k;
			int count = 0;

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
				Random R = new Random();
				if(R.nextInt(100) > 50 && isAxeorSaw)
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
					dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.FruitTreeSapling, 1, l));
				}
			}
		}
		else
		{
			world.setBlock(i, j, k, this, l, 0x2);
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return TFCItems.Logs;
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
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		if(world.getBlock(i, j-1, k) == this || world.getBlock(i, j-1, k).isOpaqueCube())
			return AxisAlignedBB.getBoundingBox(i+0.3, j, k+0.3, i+0.7, j+1, k+0.7);
		return AxisAlignedBB.getBoundingBox(i, j+0.4, k, i+1, j+0.6, k+1);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		FloraManager manager = FloraManager.getInstance();
		FloraIndex fi = manager.findMatchingIndex(this.getType(world.getBlockMetadata(i, j, k)));

		float temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);

		if(!world.isRemote && world.getTileEntity(i, j, k) != null &&
				TFC_Time.getSeasonAdjustedMonth(k) < 6 &&
				fi != null && temp >= fi.minTemp && temp < fi.maxTemp)
		{
			TileEntityFruitTreeWood te = (TileEntityFruitTreeWood)world.getTileEntity(i, j, k);
			int t = 1;
			if(TFC_Time.getSeasonAdjustedMonth(k) < 3)
				t = 2;

			int leafGrowthRate = 20;
			int trunkGrowTime = 30;
			int branchGrowTime = 20;

			//grow upward
			if(te.birthTimeWood + trunkGrowTime < TFC_Time.getTotalDays() &&
					te.height < 3 && te.isTrunk && rand.nextInt(16/t) == 0 &&
					(world.isAirBlock(i, j+1, k) || world.getBlock(i, j+1, k) == TFCBlocks.fruitTreeLeaves))
			{
				world.setBlock(i, j+1, k, this, world.getBlockMetadata(i, j, k), 0x2);
				((TileEntityFruitTreeWood)world.getTileEntity(i, j+1, k)).setTrunk(true);
				((TileEntityFruitTreeWood)world.getTileEntity(i, j+1, k)).setHeight(te.height+1);
				((TileEntityFruitTreeWood)world.getTileEntity(i, j+1, k)).setBirth();
				((TileEntityFruitTreeWood)world.getTileEntity(i, j, k)).setBirthWood(trunkGrowTime);
			}
			else if(te.birthTimeWood + branchGrowTime < TFC_Time.getTotalDays() && te.height == 2 && te.isTrunk && rand.nextInt(16/t) == 0 &&
					world.getBlock(i, j+1, k) != this)
			{
				int r = rand.nextInt(4);
				if(r == 0 && world.isAirBlock(i+1, j, k) || world.getBlock(i+1, j, k) == TFCBlocks.fruitTreeLeaves)
				{
					world.setBlock(i+1, j, k, this, world.getBlockMetadata(i, j, k), 0x2);
					((TileEntityFruitTreeWood)world.getTileEntity(i+1, j, k)).setTrunk(false);
					((TileEntityFruitTreeWood)world.getTileEntity(i+1, j, k)).setHeight(te.height);
					((TileEntityFruitTreeWood)world.getTileEntity(i+1, j, k)).setBirth();
				}
				else if(r == 1 && world.isAirBlock(i, j, k-1) || world.getBlock(i, j, k-1) == TFCBlocks.fruitTreeLeaves)
				{
					world.setBlock(i, j, k-1, this, world.getBlockMetadata(i, j, k), 0x2);
					((TileEntityFruitTreeWood)world.getTileEntity(i, j, k-1)).setTrunk(false);
					((TileEntityFruitTreeWood)world.getTileEntity(i, j, k-1)).setHeight(te.height);
					((TileEntityFruitTreeWood)world.getTileEntity(i, j, k-1)).setBirth();
				}
				else if(r == 2 && world.isAirBlock(i-1, j, k) || world.getBlock(i-1, j, k) == TFCBlocks.fruitTreeLeaves)
				{
					world.setBlock(i-1, j, k, this, world.getBlockMetadata(i, j, k), 0x2);
					((TileEntityFruitTreeWood)world.getTileEntity(i-1, j, k)).setTrunk(false);
					((TileEntityFruitTreeWood)world.getTileEntity(i-1, j, k)).setHeight(te.height);
					((TileEntityFruitTreeWood)world.getTileEntity(i-1, j, k)).setBirth();
				}
				else if(r == 3 && world.isAirBlock(i, j, k+1) || world.getBlock(i, j, k+1) == TFCBlocks.fruitTreeLeaves)
				{
					world.setBlock(i, j, k+1, this, world.getBlockMetadata(i, j, k), 0x2);
					((TileEntityFruitTreeWood)world.getTileEntity(i, j, k+1)).setTrunk(false);
					((TileEntityFruitTreeWood)world.getTileEntity(i, j, k+1)).setHeight(te.height);
					((TileEntityFruitTreeWood)world.getTileEntity(i, j, k+1)).setBirth();
				}

				((TileEntityFruitTreeWood)world.getTileEntity(i, j, k)).setBirthWood(branchGrowTime);
			}
			else if(te.birthTimeWood + 1 < TFC_Time.getTotalDays() && rand.nextInt(leafGrowthRate) == 0 && world.getBlock(i, j+2, k) != this)
			{
				if(world.isAirBlock(i, j+1, k) && world.isAirBlock(i, j+2, k) && BlockFruitLeaves.canStay(world, i, j+1, k, TFCBlocks.fruitTreeLeaves))//above
					world.setBlock(i, j+1, k, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i+1, j, k) && world.isAirBlock(i+1, j+1, k) && BlockFruitLeaves.canStay(world, i+1, j, k, TFCBlocks.fruitTreeLeaves))//+x
					world.setBlock(i+1, j, k, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i-1, j, k) && world.isAirBlock(i-1, j+1, k) && BlockFruitLeaves.canStay(world, i-1, j, k, TFCBlocks.fruitTreeLeaves))//-x
					world.setBlock(i-1, j, k, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i, j, k+1) && world.isAirBlock(i, j+1, k+1) && BlockFruitLeaves.canStay(world, i, j, k+1, TFCBlocks.fruitTreeLeaves))//+z
					world.setBlock(i, j, k+1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i, j, k-1) && world.isAirBlock(i, j+1, k-1) && BlockFruitLeaves.canStay(world, i, j, k-1, TFCBlocks.fruitTreeLeaves))//-z
					world.setBlock(i, j, k-1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i+1, j, k-1) && world.isAirBlock(i+1, j+1, k-1) && BlockFruitLeaves.canStay(world, i+1, j, k-1, TFCBlocks.fruitTreeLeaves))//+x/-z
					world.setBlock(i+1, j, k-1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i+1, j, k+1) && world.isAirBlock(i+1, j+1, k+1) && BlockFruitLeaves.canStay(world, i+1, j, k+1, TFCBlocks.fruitTreeLeaves))//+x/+z
					world.setBlock(i+1, j, k+1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i-1, j, k-1) && world.isAirBlock(i-1, j+1, k-1) && BlockFruitLeaves.canStay(world, i-1, j, k-1, TFCBlocks.fruitTreeLeaves))//-x/-z
					world.setBlock(i-1, j, k-1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
				else if(world.isAirBlock(i-1, j, k+1) && world.isAirBlock(i-1, j+1, k+1) && BlockFruitLeaves.canStay(world, i-1, j, k+1, TFCBlocks.fruitTreeLeaves))//-x/+z
					world.setBlock(i-1, j, k+1, TFCBlocks.fruitTreeLeaves, world.getBlockMetadata(i, j, k), 0x2);
			}
		}
	}

	public void SurroundWithLeaves(World world, int i, int j, int k)
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
	}

	public String getType(int meta)
	{
		switch(meta)
		{
		case 0: return "red apple";
		case 1: return "banana";
		case 2: return "orange";
		case 3: return "green apple";
		case 4: return "lemon";
		case 5: return "olive";
		case 6: return "cherry";
		case 7: return "peach";
		case 8: return "plum";
		case 9: return "cacao";
		}
		return "";
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityFruitTreeWood();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		if(!world.isRemote && checkOut(world,x,y-1,z,metadata) && world.getTileEntity(x, y-1, z) != null)
			((TileEntityFruitTreeWood)world.getTileEntity(x, y-1, z)).setBirth();
		super.breakBlock(world, x, y, z, block, metadata);
	}
}
