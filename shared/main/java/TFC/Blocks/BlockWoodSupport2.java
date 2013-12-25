package TFC.Blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import TFC.API.Constant.TFCBlockID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWoodSupport2 extends BlockTerra
{
	String[] woodNames;
	Icon[] icons;
	public BlockWoodSupport2(int i, Material material) 
	{
		super(i, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		woodNames = new String[Global.WOOD_ALL.length-16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length-16);
		icons = new Icon[woodNames.length];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++) {
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	public static Boolean getSupportInRange(World world, int x, int y, int z, int range, boolean isVerticalSupport)
	{
		if(isVerticalSupport){
			for(int i = -range; i < range; i++)
			{
				if(TFCBlocks.isIdVSupport(world.getBlockId(x+i, y, z)))
				{
					return true;
				}
				if(TFCBlocks.isIdVSupport(world.getBlockId(x-i, y, z)))
				{
					return true;
				}
				if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z+i)))
				{
					return true;
				}
				if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z-i)))
				{
					return true;
				}
			}
		}
		else{
			for(int i = -range; i < range; i++)
			{
				if(TFCBlocks.isIdHSupport(world.getBlockId(x+i, y, z)))
				{
					return true;
				}
				if(TFCBlocks.isIdHSupport(world.getBlockId(x-i, y, z)))
				{
					return true;
				}
				if(TFCBlocks.isIdHSupport(world.getBlockId(x, y, z+i)))
				{
					return true;
				}
				if(TFCBlocks.isIdHSupport(world.getBlockId(x, y, z-i)))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return true;
	}

	public static int isNextToSupport(World world, int x, int y, int z)
	{
		if(TFCBlocks.isIdVSupport(world.getBlockId(x+1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x+1, y, z)))
		{
			return 5;
		}
		if(TFCBlocks.isIdVSupport(world.getBlockId(x-1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x-1, y, z)))
		{
			return 4;
		}
		if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z+1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z+1)))
		{
			return 3;
		}
		if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z-1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z-1)))
		{
			return 2;
		}
		return 0;
	}

	private Boolean isNearVerticalSupport(World world, int i, int j, int k)
	{
		for(int y = -1; y < 0; y++)
		{
			for(int x = -6; x < 4; x++)
			{
				for(int z = -6; z < 4; z++)
				{

					if(TFCBlocks.isIdVSupport(world.getBlockId(i+x, j+y, k+z)))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		return icons[meta];
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/WoodSheet/WoodSheet"+(i+16));
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		Boolean isHorizontal = TFCBlocks.isIdHSupport(world.getBlockId(x, y, z));
		Boolean isVertical = TFCBlocks.isIdVSupport(world.getBlockId(x, y, z));

		double minX = 0.25; double minY = 0.0; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;


		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(TFCBlocks.isIdVSupport(world.getBlockId(x+1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x+1, y, z)))
			{
				maxX = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x-1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x-1, y, z)))
			{
				minX = 0;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z+1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z+1)))
			{
				maxZ = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z-1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z-1)))
			{
				minZ = 0;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y-1, z)))
			{
				minY = 0;
			}
		}
		else
		{
			minY = 0;
			maxY = 1;
			if(TFCBlocks.isIdVSupport(world.getBlockId(x+1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x+1, y, z)))
			{
				maxX = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x-1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x-1, y, z)))
			{
				minX = 0;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z+1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z+1)))
			{
				maxZ = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z-1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z-1)))
			{
				minZ = 0;
			}
		}

		return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
	}

	@Override
	public int getRenderType()
	{
		if(TFCBlocks.isIdVSupport(blockID)) {
			return TFCBlocks.woodSupportRenderIdV;
		} else {
			return TFCBlocks.woodSupportRenderIdH;
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		Boolean isHorizontal = TFCBlocks.isIdHSupport(world.getBlockId(x, y, z));
		Boolean isVertical = TFCBlocks.isIdVSupport(world.getBlockId(x, y, z));

		double minX = 0.25; double minY = 0.0; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;


		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(TFCBlocks.isIdVSupport(world.getBlockId(x+1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x+1, y, z)))
			{
				maxX = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x-1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x-1, y, z)))
			{
				minX = 0;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z+1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z+1)))
			{
				maxZ = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z-1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z-1)))
			{
				minZ = 0;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y-1, z)))
			{
				minY = 0;
			}
		}
		else
		{
			minY = 0;
			maxY = 1;
			if(TFCBlocks.isIdVSupport(world.getBlockId(x+1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x+1, y, z)))
			{
				maxX = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x-1, y, z)) || TFCBlocks.isIdHSupport(world.getBlockId(x-1, y, z)))
			{
				minX = 0;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z+1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z+1)))
			{
				maxZ = 1;
			}
			if(TFCBlocks.isIdVSupport(world.getBlockId(x, y, z-1)) || TFCBlocks.isIdHSupport(world.getBlockId(x, y, z-1)))
			{
				minZ = 0;
			}
		}

		return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		dropBlockAsItem_do(world, i, j, k, new ItemStack(this, 1, l));
	}

	@Override
	public boolean isBlockNormalCube(World world, int i, int j, int k)
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
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack is) 
	{
		super.onBlockPlacedBy(world, i, j, k, entity, is);
		if(!world.isRemote)
		{
			onNeighborBlockChange(world, i, j, k, is.getItemDamage());
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{

		Boolean isHorizontal = TFCBlocks.isIdHSupport(world.getBlockId(i, j, k));
		Boolean isVertical = TFCBlocks.isIdVSupport(world.getBlockId(i, j, k));

		int meta = world.getBlockMetadata(i, j, k);
		
		if(isVertical)//Vertical Beam
		{
			//if the block directly beneath the support is not solid or a support then break the support
			if(!world.isBlockOpaqueCube(i, j-1, k) && !TFCBlocks.isIdVSupport(world.getBlockId(i, j-1, k)))
			{	
				harvestBlock(world, null, i, j, k,  meta);
				world.setBlock(i, j, k, 0);
			}
		}
		else if(isHorizontal)//Horizontal Beam
		{
			Boolean b1 = !getSupportInRange(world,i,j,k,5,true);
			Boolean b2 = !getSupportInRange(world,i,j-1,k,5,true);
			Boolean support = isNextToSupport(world,i,j,k) == 0;
			//if the block on any side is not a support then break
			if(support  || b2 || b1 && b2)
			{
				harvestBlock(world, null, i, j, k,  meta);
				world.setBlock(i, j, k, 0);
			}
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		if(!world.isRemote && canPlaceBlockAt(world, x, y, z))
		{
			int id0 = world.getBlockId(x, y-1, z);
			Boolean vSupport = TFCBlocks.isIdVSupport(id0);
			Boolean b1 = world.isBlockOpaqueCube(x, y-2, z);
			//bottom
			if(!TFCBlocks.isIdVSupport(blockID))
			{
				if(side == 0 && world.getBlockId(x, y-1, z) == 0 /*&& world.isBlockOpaqueCube(x, y, z)*/)
				{
					boolean nextToSupport = isNextToSupport(world,x,y-1,z) != 0;
					boolean SupportRange1 = getSupportInRange(world, x,y-1,z,5,true);
					boolean SupportRange2 = getSupportInRange(world, x,y-2,z,5,true);
					if(nextToSupport && (SupportRange1 || SupportRange2) || TFCBlocks.isIdVSupport(world.getBlockId(x, y-2, z)))
					{
						return true;
					}
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0 /*&& world.isBlockOpaqueCube(x, y+1, z-1)*/)
				{
					if(isNextToSupport(world,x,y,z-1) != 0 && 
							(getSupportInRange(world, x,y,z-1,5,true) || 
									getSupportInRange(world, x,y-1,z-1,5,true)) || 
									TFCBlocks.isIdVSupport(world.getBlockId(x, y-1, z-1)))
					{
						return true;
					}
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0 /*&& world.isBlockOpaqueCube(x, y+1, z+1)*/)
				{
					if(isNextToSupport(world,x,y,z+1) != 0 && 
							(getSupportInRange(world, x,y,z+1,5,true) || 
									getSupportInRange(world, x,y-1,z+1,5,true)) || 
									TFCBlocks.isIdVSupport(world.getBlockId(x, y-1, z+1)))
					{
						return true;
					}
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0 /*&& world.isBlockOpaqueCube(x-1, y+1, z)*/)
				{
					if(isNextToSupport(world,x-1,y,z) != 0  && 
							(getSupportInRange(world, x-1,y,z,5,true) || 
									getSupportInRange(world, x-1,y-1,z,5,true)) || 
									TFCBlocks.isIdVSupport(world.getBlockId(x-1, y-1, z)))
					{
						return true;
					}
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0 /*&& world.isBlockOpaqueCube(x+1, y+1, z)*/)
				{
					if(isNextToSupport(world,x+1,y,z) != 0 && 
							(getSupportInRange(world, x+1,y,z,5,true) || 
									getSupportInRange(world, x+1,y-1,z,5,true)) || 
									TFCBlocks.isIdVSupport(world.getBlockId(x+1, y-1, z)))
					{
						return true;
					}
				}
			}
			else if(TFCBlocks.isIdVSupport(blockID))
			{

				//if the block beneath is opaque or is another support
				if(!vSupport && !b1)
				{
					return false;
				}

				//top
				else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
				{
					return true;
				}
				else if(side == 2 && (TFCBlocks.isIdVSupport(world.getBlockId(x, y-1, z-1)) || world.isBlockOpaqueCube(x, y-1, z-1)) &&
						world.getBlockId(x, y, z-1) == 0)
				{
					return true;
				}
				else if(side == 3 && (TFCBlocks.isIdVSupport(world.getBlockId(x, y-1, z+1)) || world.isBlockOpaqueCube(x, y-1, z+1)) &&
						world.getBlockId(x, y, z+1) == 0)
				{
					return true;
				}
				else if(side == 4 && (TFCBlocks.isIdVSupport(world.getBlockId(x-1, y-1, z)) || world.isBlockOpaqueCube(x-1, y-1, z)) &&
						world.getBlockId(x-1, y, z) == 0)
				{
					return true;
				}
				else if(side == 5 && (TFCBlocks.isIdVSupport(world.getBlockId(x+1, y-1, z)) || world.isBlockOpaqueCube(x+1, y-1, z)) &&
						world.getBlockId(x+1, y, z) == 0)
				{
					return true;
				}
			}
		}
		return true;
	}
}
