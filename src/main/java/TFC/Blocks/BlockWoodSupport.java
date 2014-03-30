package TFC.Blocks;

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
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWoodSupport extends BlockTerra
{
	String[] woodNames;
	IIcon[] icons;

	public BlockWoodSupport(Material material)
	{
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		icons = new IIcon[woodNames.length];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	public static int getSupportInRangeHeight(World world, int x, int y, int z, int range)
	{
		int n = 0;
		int s = 0;
		int e = 0;
		int w = 0;
		for(int i = 1; i < range; i++)
		{
			if(TFCBlocks.isBlockHSupport(world.getBlock(x+i, y, z)))
				e++;
			else if(TFCBlocks.isBlockVSupport(world.getBlock(x+i, y, z)) && e >= 0)
				return scanVert(world, x+i, y, z);
			else e -= 50;

			if(TFCBlocks.isBlockHSupport(world.getBlock(x-i, y, z)))
				w++;
			else if(TFCBlocks.isBlockVSupport(world.getBlock(x-i, y, z)) && w >= 0)
				return scanVert(world, x-i, y, z);
			else w -= 50;

			if(TFCBlocks.isBlockHSupport(world.getBlock(x, y, z+i)))
				s++;
			else if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z+i)) && s >= 0)
				return scanVert(world, x, y, z+i);
			else s -= 50;

			if(TFCBlocks.isBlockHSupport(world.getBlock(x, y, z-i)))
				n++;
			else if(TFCBlocks.isBlockVSupport(world.getBlock(x, y, z-i)) && n >= 0)
				return scanVert(world, x, y, z-i);
			else n -= 50;
		}
		return 0;
	}

	private static int scanVert(World world, int x, int y, int z)
	{
		int out = 1;
		while(TFCBlocks.isBlockVSupport(world.getBlock(x, y-out, z)))
			out++;

		return out;
	}

	public static Boolean getSupportInRange(World world, int x, int y, int z, int range)
	{
		int r = getSupportInRangeHeight(world, x,y,z,range);
		if(r > 0)
			return true;
		else
			return false;
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

	private Boolean isNearVerticalSupport(World world, int i, int j, int k)
	{
		for(int y = -1; y < 0; y++)
			for(int x = -6; x < 4; x++)
				for(int z = -6; z < 4; z++)
					if(TFCBlocks.isBlockVSupport(world.getBlock(i+x, j+y, k+z)))
						return true;

		return false;
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/WoodSheet/WoodSheet"+i);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		Boolean isHorizontal = TFCBlocks.isBlockHSupport(world.getBlock(x, y, z));
		Boolean isVertical = TFCBlocks.isBlockVSupport(world.getBlock(x, y, z));

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
		Boolean isVertical = TFCBlocks.isBlockVSupport(world.getBlock(x, y, z));

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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack is) 
	{
		super.onBlockPlacedBy(world, i, j, k, entity, is);
		if(!world.isRemote)
			onNeighborBlockChange(world, i, j, k, world.getBlock(i, j, k));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{

		Boolean isHorizontal = TFCBlocks.isBlockHSupport(l);
		Boolean isVertical = TFCBlocks.isBlockVSupport(l);

		int meta = world.getBlockMetadata(i, j, k);

		if(isVertical)//Vertical Beam
		{
			//if the block directly beneath the support is not solid or a support then break the support
			if(!world.getBlock(i, j-1, k).isOpaqueCube() && !TFCBlocks.isBlockVSupport(world.getBlock(i, j-1, k)))
			{	
				harvestBlock(world, null, i, j, k,  meta);
				world.setBlockToAir(i, j, k);
			}
		}
		else if(isHorizontal)//Horizontal Beam
		{
			Boolean b1 = !(getSupportInRangeHeight(world,i,j,k,5) >= 3);
			Boolean support = isNextToSupport(world,i,j,k) == 0;
			//if the block on any side is not a support then break
			if(support || b1)
			{
				harvestBlock(world, null, i, j, k,  meta);
				world.setBlockToAir(i, j, k);
			}
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		if(!world.isRemote && canPlaceBlockAt(world, x, y, z))
		{
			Block block = world.getBlock(x, y-1, z);
			Boolean vSupport = TFCBlocks.isBlockVSupport(block);
			Boolean b1 = world.getBlock(x, y-2, z).isOpaqueCube();
			//bottom
			if(!TFCBlocks.isBlockVSupport(block))
			{
				if(side == 0 && world.isAirBlock(x, y-1, z) /*&& world.getBlock(x, y, z).isOpaqueCube()*/)
				{
					boolean nextToSupport = isNextToSupport(world,x,y-1,z) != 0;
					boolean SupportRange1 = getSupportInRange(world, x,y-1,z,5);
					boolean SupportRange2 = getSupportInRange(world, x,y-2,z,5);
					if(nextToSupport && (SupportRange1 || SupportRange2) || TFCBlocks.isBlockVSupport(world.getBlock(x, y-2, z)))
						return true;
				}
				else if(side == 2 && world.isAirBlock(x, y, z-1) /*&& world.getBlock(x, y+1, z-1).isOpaqueCube()*/)
					if(isNextToSupport(world,x,y,z-1) != 0 && (getSupportInRange(world, x,y,z-1,5) || getSupportInRange(world, x,y-1,z-1,5)) || TFCBlocks.isBlockVSupport(world.getBlock(x, y-1, z-1)))
						return true;
				else if(side == 3 && world.isAirBlock(x, y, z+1) /*&& world.getBlock(x, y+1, z+1).isOpaqueCube()*/)
					if(isNextToSupport(world,x,y,z+1) != 0 && (getSupportInRange(world, x,y,z+1,5) || getSupportInRange(world, x,y-1,z+1,5)) || TFCBlocks.isBlockVSupport(world.getBlock(x, y-1, z+1)))
						return true;
				else if(side == 4 && world.isAirBlock(x-1, y, z) /*&& world.getBlock(x-1, y+1, z).isOpaqueCube()*/)
					if(isNextToSupport(world,x-1,y,z) != 0  && (getSupportInRange(world, x-1,y,z,5) || getSupportInRange(world, x-1,y-1,z,5)) || TFCBlocks.isBlockVSupport(world.getBlock(x-1, y-1, z)))
						return true;
				else if(side == 5 && world.isAirBlock(x+1, y, z) /*&& world.getBlock(x+1, y+1, z).isOpaqueCube()*/)
					if(isNextToSupport(world,x+1,y,z) != 0 && (getSupportInRange(world, x+1,y,z,5) || getSupportInRange(world, x+1,y-1,z,5)) || TFCBlocks.isBlockVSupport(world.getBlock(x+1, y-1, z)))
						return true;
			}
			else if(TFCBlocks.isBlockVSupport(block))
				//if the block beneath is opaque or is another support
				if(!vSupport && !b1)
					return false;
				else if(side == 1 && world.isAirBlock(x, y+1, z))
					return true;
				else if(side == 2 && (TFCBlocks.isBlockVSupport(world.getBlock(x, y-1, z-1)) || world.getBlock(x, y-1, z-1).isOpaqueCube()) && world.isAirBlock(x, y, z-1))
					return true;
				else if(side == 3 && (TFCBlocks.isBlockVSupport(world.getBlock(x, y-1, z+1)) || world.getBlock(x, y-1, z+1).isOpaqueCube()) && world.isAirBlock(x, y, z+1))
					return true;
				else if(side == 4 && (TFCBlocks.isBlockVSupport(world.getBlock(x-1, y-1, z)) || world.getBlock(x-1, y-1, z).isOpaqueCube()) && world.isAirBlock(x-1, y, z))
					return true;
				else if(side == 5 && (TFCBlocks.isBlockVSupport(world.getBlock(x+1, y-1, z)) || world.getBlock(x+1, y-1, z).isOpaqueCube()) && world.isAirBlock(x+1, y, z))
					return true;
		}
		return true;
	}
}
