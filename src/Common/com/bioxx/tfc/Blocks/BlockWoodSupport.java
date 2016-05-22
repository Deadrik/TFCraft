package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BlockWoodSupport extends BlockTerra
{
	protected String[] woodNames;
	protected IIcon[] icons;

	public BlockWoodSupport(Material material)
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		icons = new IIcon[woodNames.length];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		if(TFCBlocks.isBlockVSupport(this))
		{
			for(int i = 0; i < woodNames.length; i++)
				list.add(new ItemStack(this, 1, i));
		}
	}

	public static boolean hasSupportsInRange(World world, int x, int y, int z, int range)
	{
		return getSupportsInRangeDir(world, x, y, z, range, false) != null;
	}

	public static boolean isSupportConnected(World world, int x, int y, int z)
	{
		return getSupportsInRangeDir(world, x, y, z, 5, true) != null;
	}

	public static ForgeDirection getSupportDirection(World world, int x, int y, int z)
	{
		int[] r = getSupportsInRangeDir(world, x, y, z, 5, false);
		if (r != null) // Fixes NPE
		{
			if (r[2] > r[3])
				return ForgeDirection.NORTH;
			if (r[3] > r[2])
				return ForgeDirection.SOUTH;
			if (r[5] > r[4])
				return ForgeDirection.EAST;
			if (r[4] > r[5])
				return ForgeDirection.WEST;
		}

		return ForgeDirection.UNKNOWN;
	}

	public static int getDistanceFromDirection(ForgeDirection dir, int[] dist)
	{
		switch(dir)
		{
		case NORTH: return dist[2];
		case SOUTH: return dist[3];
		case WEST: return dist[4];
		case EAST: return dist[5];
		default: return Integer.MAX_VALUE;
		}

	}

	public static int[] getSupportsInRangeDir(World world, int x, int y, int z, int range, boolean checkConnection)
	{
		int n = 0; boolean foundNV = false; boolean foundNH = true;
		int s = 0; boolean foundSV = false; boolean foundSH = true;
		int e = 0; boolean foundEV = false; boolean foundEH = true;
		int w = 0; boolean foundWV = false; boolean foundWH = true;
		boolean clearNorthPath = true;
		boolean clearSouthPath = true;
		boolean clearEastPath = true;
		boolean clearWestPath = true;

		for (int i = 1; i <= range; i++)
		{
			if (!foundEV)
			{
				if (!checkConnection)
				{
					if (world.isAirBlock(x + i, y, z) || TFCBlocks.isBlockVSupport(world.getBlock(x + i, y, z)))
						e++;
					else
						clearEastPath = false;
				}
				else if (checkConnection && !TFCBlocks.isBlockHSupport(world.getBlock(x + i, y, z)) && !TFCBlocks.isBlockVSupport(world.getBlock(x + i, y, z)))
					foundEH = false;
				else
					e++;
				if (clearEastPath && TFCBlocks.isBlockVSupport(world.getBlock(x + i, y, z)) && (e >= 0 || i == 1))
				{
					if (scanVert(world, x + i, y, z))
						foundEV = true;
					else
						e -= 50;
				}
			}
			if (!foundWV)
			{
				if (!checkConnection)
				{
					if (world.isAirBlock(x - i, y, z) || TFCBlocks.isBlockVSupport(world.getBlock(x - i, y, z)))
						w++;
					else
						clearWestPath = false;
				}
				else if (checkConnection && !TFCBlocks.isBlockHSupport(world.getBlock(x - i, y, z)) && !TFCBlocks.isBlockVSupport(world.getBlock(x - i, y, z)))
					foundWH = false;
				else
					w++;
				if (clearWestPath && TFCBlocks.isBlockVSupport(world.getBlock(x - i, y, z)) && (w >= 0 || i == 1))
				{
					if (scanVert(world, x - i, y, z))
						foundWV = true;
					else
						w -= 50;
				}
			}
			if (!foundSV)
			{
				if (!checkConnection)
				{
					if (world.isAirBlock(x, y, z + i) || TFCBlocks.isBlockVSupport(world.getBlock(x, y, z + i)))
						s++;
					else
						clearSouthPath = false;

				}
				else if (checkConnection && !TFCBlocks.isBlockHSupport(world.getBlock(x, y, z + i)) && !TFCBlocks.isBlockVSupport(world.getBlock(x, y, z + i)))
					foundSH = false;
				else
					s++;
				if (clearSouthPath && TFCBlocks.isBlockVSupport(world.getBlock(x, y, z + i)) && (s >= 0 || i == 1))
				{
					if (scanVert(world, x, y, z + i))
						foundSV = true;
				}
			}
			if (!foundNV)
			{
				if (!checkConnection)
				{
					if (world.isAirBlock(x, y, z - i) || TFCBlocks.isBlockVSupport(world.getBlock(x, y, z - i)))
						n++;
					else
						clearNorthPath = false;
				}
				else if (checkConnection && !TFCBlocks.isBlockHSupport(world.getBlock(x, y, z - i)) && !TFCBlocks.isBlockVSupport(world.getBlock(x, y, z - i)))
					foundNH = false;
				else
					n++;
				if (clearNorthPath && TFCBlocks.isBlockVSupport(world.getBlock(x, y, z - i)) && (n >= 0 || i == 1))
				{
					if (scanVert(world, x, y, z - i))
						foundNV = true;
				}
			}
		}

		if (foundEV && foundEH && foundWV && foundWH)
			return new int[] { 0, 0, 0, 0, w, e };
		if (foundSV && foundSH && foundNV && foundNH)
			return new int[] { 0, 0, n, s, 0, 0 };
		return null;
	}

	private static boolean scanVert(World world, int x, int y, int z)
	{
		int out = 1;
		while(TFCBlocks.isBlockVSupport(world.getBlock(x, y-out, z)))
			out++;

		return out > 2;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return true;
	}

	public static int isNextToSupport(World world, int x, int y, int z)
	{
		if(TFCBlocks.isBlockVSupport(world.getBlock(x+1, y, z)) || TFCBlocks.isBlockHSupport(world.getBlock(x+1, y, z)))
			return 5;
		if(TFCBlocks.isBlockVSupport(world.getBlock(x-1, y, z)) || TFCBlocks.isBlockHSupport(world.getBlock(x-1, y, z)))
			return 4;
		if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z+1)) || TFCBlocks.isBlockHSupport(world.getBlock(x, y, z+1)))
			return 3;
		if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z-1)) || TFCBlocks.isBlockHSupport(world.getBlock(x, y, z-1)))
			return 2;
		return 0;
	}

	/*private Boolean isNearVerticalSupport(World world, int i, int j, int k)
	{
		for(int y = -1; y < 0; y++)
		{
			for(int x = -6; x < 4; x++)
			{
				for(int z = -6; z < 4; z++)
				{
					if(TFCBlocks.isBlockVSupport(world.getBlock(i+x, j+y, k+z)))
						return true;
				}
			}
		}
		return false;
	}*/

	@Override
	public int damageDropped(int j)
	{
		return j;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		Block b = world.getBlock(x, y, z);
		if(b == TFCBlocks.woodSupportH || b == TFCBlocks.woodSupportV)
			ret.add(new ItemStack(TFCBlocks.woodSupportV, 1, metadata));
		else if(b == TFCBlocks.woodSupportH2 || b == TFCBlocks.woodSupportV2)
			ret.add(new ItemStack(TFCBlocks.woodSupportV2, 1, metadata));
		return ret;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta < 0)
			return icons[0];
		if(meta<icons.length)
			return icons[meta];
		return TFCBlocks.woodSupportH2.getIcon(side, meta-16);
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/WoodSheet/" + woodNames[i]);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return getCollisionBoundingBoxFromPoolIBlockAccess(world, x, y, z).getOffsetBoundingBox(x, y, z);
	}

	private AxisAlignedBB getCollisionBoundingBoxFromPoolIBlockAccess(IBlockAccess blockAccess, int x, int y, int z)
	{
		Boolean isHorizontal = TFCBlocks.isBlockHSupport(blockAccess.getBlock(x, y, z));
		//Boolean isVertical = TFCBlocks.isBlockVSupport(blockAccess.getBlock(x, y, z));

		double minX = 0.25; double minY = 0.0; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;

		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x+1, y, z)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x+1, y, z)))
				maxX = 1;
			if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x-1, y, z)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x-1, y, z)))
				minX = 0;
			if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x, y, z+1)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x, y, z+1)))
				maxZ = 1;
			if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x, y, z-1)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x, y, z-1)))
				minZ = 0;
			/*if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x, y-1, z)))
				minY = 0;*/
		}
		else
		{
			minY = 0;
			maxY = 1;
			/*if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x+1, y, z)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x+1, y, z)))
				maxX = 1;
			if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x-1, y, z)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x-1, y, z)))
				minX = 0;
			if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x, y, z+1)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x, y, z+1)))
				maxZ = 1;
			if(TFCBlocks.isBlockVSupport(blockAccess.getBlock(x, y, z-1)) || TFCBlocks.isBlockHSupport(blockAccess.getBlock(x, y, z-1)))
				minZ = 0;*/
		}

		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
	{
		AxisAlignedBB aabb = getCollisionBoundingBoxFromPoolIBlockAccess(blockAccess, x, y, z);
		this.setBlockBounds((float)aabb.minX, (float)aabb.minY, (float)aabb.minZ, (float)aabb.maxX, (float)aabb.maxY, (float)aabb.maxZ);
	}

	@Override
	public int getRenderType()
	{
		if(TFCBlocks.isBlockVSupport(this))
			return TFCBlocks.woodSupportRenderIdV;
		else
			return TFCBlocks.woodSupportRenderIdH;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		Boolean isHorizontal = TFCBlocks.isBlockHSupport(world.getBlock(x, y, z));
		//Boolean isVertical = TFCBlocks.isBlockVSupport(world.getBlock(x, y, z));

		double minX = 0.25; double minY = 0.0; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;


		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x+1, y, z)) || TFCBlocks.isBlockHSupport(world.getBlock(x+1, y, z)))
				maxX = 1;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x-1, y, z)) || TFCBlocks.isBlockHSupport(world.getBlock(x-1, y, z)))
				minX = 0;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z+1)) || TFCBlocks.isBlockHSupport(world.getBlock(x, y, z+1)))
				maxZ = 1;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z-1)) || TFCBlocks.isBlockHSupport(world.getBlock(x, y, z-1)))
				minZ = 0;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x, y-1, z)))
				minY = 0;
		}
		else
		{
			minY = 0;
			maxY = 1;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x+1, y, z)) || TFCBlocks.isBlockHSupport(world.getBlock(x+1, y, z)))
				maxX = 1;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x-1, y, z)) || TFCBlocks.isBlockHSupport(world.getBlock(x-1, y, z)))
				minX = 0;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z+1)) || TFCBlocks.isBlockHSupport(world.getBlock(x, y, z+1)))
				maxZ = 1;
			if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z-1)) || TFCBlocks.isBlockHSupport(world.getBlock(x, y, z-1)))
				minZ = 0;
		}

		return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		if(this == TFCBlocks.woodSupportH)
			dropBlockAsItem(world, i, j, k, new ItemStack(TFCBlocks.woodSupportV, 1, l));
		else if(this == TFCBlocks.woodSupportH2)
			dropBlockAsItem(world, i, j, k, new ItemStack(TFCBlocks.woodSupportV2, 1, l));
		else
			dropBlockAsItem(world, i, j, k, new ItemStack(this, 1, l));
	}

	@Override
	public boolean isBlockNormalCube()
	{
		return false;
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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack is)
	{
		super.onBlockPlacedBy(world, i, j, k, entity, is);
		//if(!world.isRemote)
		//	onNeighborBlockChange(world, i, j, k, world.getBlock(i, j, k));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{
		boolean isOtherHorizontal = TFCBlocks.isBlockHSupport(l);
		//boolean isOtherVertical = TFCBlocks.isBlockVSupport(l);
		boolean isHorizontal = TFCBlocks.isBlockHSupport(world.getBlock(i, j, k));
		boolean isVertical = TFCBlocks.isBlockVSupport(world.getBlock(i, j, k));

		int meta = world.getBlockMetadata(i, j, k);

		if(isVertical && !isOtherHorizontal)//Vertical Beam
		{
			//if the block directly beneath the support is not solid or a support then break the support
			if(!world.getBlock(i, j-1, k).isOpaqueCube() && !TFCBlocks.isBlockVSupport(world.getBlock(i, j-1, k)))
			{
				harvestBlock(world, null, i, j, k, meta);
				world.setBlockToAir(i, j, k);
			}
		}
		else if(isHorizontal)//Horizontal Beam
		{
			boolean b1 = !isSupportConnected(world,i,j,k);
			if( b1)
			{
				harvestBlock(world, null, i, j, k, meta);
				world.setBlockToAir(i, j, k);
			}
			else if(TFCBlocks.isBlockVSupport(world.getBlock(i, j-1, k)))
			{
				if(this == TFCBlocks.woodSupportH)
					world.setBlock(i, j, k, TFCBlocks.woodSupportV, meta, 0x2);
				else if(this == TFCBlocks.woodSupportH2)
					world.setBlock(i, j, k, TFCBlocks.woodSupportV2, meta, 0x2);
			}
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		Block downBlock = world.getBlock(x, y-1, z);
		//Block block = world.getBlock(x, y, z);
		//bottom
		if(!TFCBlocks.isBlockVSupport(downBlock))
		{
			if(side == 0 && world.isAirBlock(x, y-1, z))
			{
				return true;
			}
			else if (side == 1 && downBlock.isOpaqueCube())
			{
				return true;
			}
			else if(side == 2)
			{
				if(isNextToSupport(world,x,y,z) != 0 && hasSupportsInRange(world, x,y,z,5))
					return true;
			}
			else if(side == 3)
			{
				if(isNextToSupport(world,x,y,z) != 0 && hasSupportsInRange(world, x,y,z,5))
					return true;
			}
			else if(side == 4)
			{
				if(isNextToSupport(world,x,y,z) != 0  && hasSupportsInRange(world, x,y,z,5))
					return true;
			}
			else if(side == 5)
			{
				if(isNextToSupport(world,x,y,z) != 0 && hasSupportsInRange(world, x,y,z,5))
					return true;
			}
		}
		else if(TFCBlocks.isBlockVSupport(downBlock) || downBlock.isOpaqueCube())
		{
			if(side == 1 && world.isAirBlock(x, y, z))
				return true;
			else if(side == 2 && (TFCBlocks.isBlockVSupport(world.getBlock(x, y, z-1)) || world.getBlock(x, y, z-1).isOpaqueCube()) && world.isAirBlock(x, y, z-1))
				return true;
			else if(side == 3 && (TFCBlocks.isBlockVSupport(world.getBlock(x, y, z+1)) || world.getBlock(x, y, z+1).isOpaqueCube()) && world.isAirBlock(x, y, z+1))
				return true;
			else if(side == 4 && (TFCBlocks.isBlockVSupport(world.getBlock(x-1, y, z)) || world.getBlock(x-1, y, z).isOpaqueCube()) && world.isAirBlock(x-1, y, z))
				return true;
			else if(side == 5 && (TFCBlocks.isBlockVSupport(world.getBlock(x+1, y, z)) || world.getBlock(x+1, y, z).isOpaqueCube()) && world.isAirBlock(x+1, y, z))
				return true;
		}

		return false;
	}
}
