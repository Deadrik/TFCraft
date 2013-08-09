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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWoodSupport extends BlockTerra
{
	Icon[] icons = new Icon[Global.WOOD_ALL.length];
	public BlockWoodSupport(int i, Material material) 
	{
		super(i, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	public static Boolean getSupportInRange(World world, int x, int y, int z, int range, int supportID)
	{
		for(int i = -range; i < range; i++)
		{
			if(world.getBlockId(x+i, y, z) == supportID)
			{
				return true;
			}
			if(world.getBlockId(x-i, y, z) == supportID)
			{
				return true;
			}
			if(world.getBlockId(x, y, z+i) == supportID)
			{
				return true;
			}
			if(world.getBlockId(x, y, z-i) == supportID)
			{
				return true;
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
		if(world.getBlockId(x+1, y, z) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x+1, y, z) == TFCBlocks.WoodSupportH.blockID)
		{
			return 5;
		}
		if(world.getBlockId(x-1, y, z) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x-1, y, z) == TFCBlocks.WoodSupportH.blockID)
		{
			return 4;
		}
		if(world.getBlockId(x, y, z+1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x, y, z+1) == TFCBlocks.WoodSupportH.blockID)
		{
			return 3;
		}
		if(world.getBlockId(x, y, z-1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(x, y, z-1) == TFCBlocks.WoodSupportH.blockID)
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

					if(world.getBlockId(i+x, j+y, k+z) == TFCBlocks.WoodSupportV.blockID)
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
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/WoodSheet/WoodSheet"+i);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int hSupportID = TFCBlocks.WoodSupportH.blockID;
		int vSupportID = TFCBlocks.WoodSupportV.blockID;

		Boolean isHorizontal = world.getBlockId(i, j, k) == hSupportID;
		Boolean isVertical = world.getBlockId(i, j, k) == vSupportID;

		double minX = 0.25; double minY = 0.0; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;


		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportH.blockID)
			{
				minZ = 0;
			}
			if(world.getBlockId(i, j-1, k) == TFCBlocks.WoodSupportV.blockID)
			{
				minY = 0;
			}
		}
		else
		{
			minY = 0;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportH.blockID)
			{
				minZ = 0;
			}
		}

		return AxisAlignedBB.getBoundingBox(i + minX, j + minY, k + minZ, i + maxX, j + maxY, k + maxZ);
	}

	@Override
	public int getRenderType()
	{
		if(this.blockID == TFCBlocks.WoodSupportV.blockID) {
			return TFCBlocks.woodSupportRenderIdV;
		} else {
			return TFCBlocks.woodSupportRenderIdH;
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int hSupportID = TFCBlocks.WoodSupportH.blockID;
		int vSupportID = TFCBlocks.WoodSupportV.blockID;

		Boolean isHorizontal = world.getBlockId(i, j, k) == hSupportID;
		Boolean isVertical = world.getBlockId(i, j, k) == vSupportID;

		double minX = 0.25; double minY = 0.25; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;


		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportH.blockID)
			{
				minZ = 0;
			}
			if(world.getBlockId(i, j-1, k) == TFCBlocks.WoodSupportV.blockID)
			{
				minY = 0;
			}
		}
		else
		{
			minY = 0;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i+1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i-1, j, k) == TFCBlocks.WoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k+1) == TFCBlocks.WoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportV.blockID || world.getBlockId(i, j, k-1) == TFCBlocks.WoodSupportH.blockID)
			{
				minZ = 0;
			}
		}

		return AxisAlignedBB.getBoundingBox(i + minX, j + minY, k + minZ, i + maxX, j + maxY, k + maxZ);
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

		int hSupportID = TFCBlocks.WoodSupportH.blockID;
		int vSupportID = TFCBlocks.WoodSupportV.blockID;

		Boolean isHorizontal = world.getBlockId(i, j, k) == hSupportID;
		Boolean isVertical = world.getBlockId(i, j, k) == vSupportID;

		int meta = world.getBlockMetadata(i, j, k);

		if(isVertical)//Vertical Beam
		{
			//if the block directly beneath the support is not solid or a support then break the support
			if(!world.isBlockOpaqueCube(i, j-1, k) && world.getBlockId(i, j-1, k) != vSupportID)
			{	
				harvestBlock(world, null, i, j, k,  meta);
				world.setBlock(i, j, k, 0);
			}
		}
		else if(isHorizontal)//Horizontal Beam
		{
			Boolean b1 = !getSupportInRange(world,i,j,k,5,TFCBlocks.WoodSupportV.blockID);
			Boolean b2 = !getSupportInRange(world,i,j-1,k,5,TFCBlocks.WoodSupportV.blockID);
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
			Boolean vSupport = id0 == TFCBlocks.WoodSupportV.blockID;
			Boolean b1 = world.isBlockOpaqueCube(x, y-2, z);
			//bottom
			if(blockID != TFCBlocks.WoodSupportV.blockID)
			{
				if(side == 0 && world.getBlockId(x, y-1, z) == 0 /*&& world.isBlockOpaqueCube(x, y, z)*/)
				{
					boolean nextToSupport = isNextToSupport(world,x,y-1,z) != 0;
					boolean SupportRange1 = getSupportInRange(world, x,y-1,z,5,TFCBlocks.WoodSupportV.blockID);
					boolean SupportRange2 = getSupportInRange(world, x,y-2,z,5,TFCBlocks.WoodSupportV.blockID);
					if(nextToSupport && (SupportRange1 || SupportRange2) || world.getBlockId(x, y-2, z) == TFCBlocks.WoodSupportV.blockID)
					{
						return true;
					}
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0 /*&& world.isBlockOpaqueCube(x, y+1, z-1)*/)
				{
					if(isNextToSupport(world,x,y,z-1) != 0 && 
							(getSupportInRange(world, x,y,z-1,5,TFCBlocks.WoodSupportV.blockID) || 
									getSupportInRange(world, x,y-1,z-1,5,TFCBlocks.WoodSupportV.blockID)) || 
									world.getBlockId(x, y-1, z-1) == TFCBlocks.WoodSupportV.blockID)
					{
						return true;
					}
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0 /*&& world.isBlockOpaqueCube(x, y+1, z+1)*/)
				{
					if(isNextToSupport(world,x,y,z+1) != 0 && 
							(getSupportInRange(world, x,y,z+1,5,TFCBlocks.WoodSupportV.blockID) || 
									getSupportInRange(world, x,y-1,z+1,5,TFCBlocks.WoodSupportV.blockID)) || 
									world.getBlockId(x, y-1, z+1) == TFCBlocks.WoodSupportV.blockID)
					{
						return true;
					}
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0 /*&& world.isBlockOpaqueCube(x-1, y+1, z)*/)
				{
					if(isNextToSupport(world,x-1,y,z) != 0  && 
							(getSupportInRange(world, x-1,y,z,5,TFCBlocks.WoodSupportV.blockID) || 
									getSupportInRange(world, x-1,y-1,z,5,TFCBlocks.WoodSupportV.blockID)) || 
									world.getBlockId(x-1, y-1, z) == TFCBlocks.WoodSupportV.blockID)
					{
						return true;
					}
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0 /*&& world.isBlockOpaqueCube(x+1, y+1, z)*/)
				{
					if(isNextToSupport(world,x+1,y,z) != 0 && 
							(getSupportInRange(world, x+1,y,z,5,TFCBlocks.WoodSupportV.blockID) || 
									getSupportInRange(world, x+1,y-1,z,5,TFCBlocks.WoodSupportV.blockID)) || 
									world.getBlockId(x+1, y-1, z) == TFCBlocks.WoodSupportV.blockID)
					{
						return true;
					}
				}
			}
			else if(blockID == TFCBlocks.WoodSupportV.blockID)
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
				else if(side == 2 && (world.getBlockId(x, y-1, z-1) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x, y-1, z-1)) &&
						world.getBlockId(x, y, z-1) == 0)
				{
					return true;
				}
				else if(side == 3 && (world.getBlockId(x, y-1, z+1) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x, y-1, z+1)) &&
						world.getBlockId(x, y, z+1) == 0)
				{
					return true;
				}
				else if(side == 4 && (world.getBlockId(x-1, y-1, z) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x-1, y-1, z)) &&
						world.getBlockId(x-1, y, z) == 0)
				{
					return true;
				}
				else if(side == 5 && (world.getBlockId(x+1, y-1, z) == TFCBlocks.WoodSupportV.blockID || world.isBlockOpaqueCube(x+1, y-1, z)) &&
						world.getBlockId(x+1, y, z) == 0)
				{
					return true;
				}
			}
		}
		return true;
	}
}
