package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.forge.ITextureProvider;

public class BlockTerraWoodSupport extends Block implements ITextureProvider
{
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

	public static int isNextToSupport(World world, int x, int y, int z)
	{
		if(world.getBlockId(x+1, y, z) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(x+1, y, z) == mod_TFC_Core.terraWoodSupportH.blockID)
		{
			return 5;
		}
		if(world.getBlockId(x-1, y, z) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(x-1, y, z) == mod_TFC_Core.terraWoodSupportH.blockID)
		{
			return 4;
		}
		if(world.getBlockId(x, y, z+1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(x, y, z+1) == mod_TFC_Core.terraWoodSupportH.blockID)
		{
			return 3;
		}
		if(world.getBlockId(x, y, z-1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(x, y, z-1) == mod_TFC_Core.terraWoodSupportH.blockID)
		{
			return 2;
		}
		return 0;
	}

	public BlockTerraWoodSupport(int i, Material material) 
	{
		super(i, Material.wood);
	}

	@Override
	protected int damageDropped(int j) 
	{
		return j;
	}
	
	@Override
    public int idDropped(int i, Random random, int j)
    {
        return 0;
    }

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return j+96;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int hSupportID = mod_TFC_Core.terraWoodSupportH.blockID;
		int vSupportID = mod_TFC_Core.terraWoodSupportV.blockID;

		Boolean isHorizontal = world.getBlockId(i, j, k) == hSupportID;
		Boolean isVertical = world.getBlockId(i, j, k) == vSupportID;

		double minX = 0.25; double minY = 0.0; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;


		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minZ = 0;
			}
			if(world.getBlockId(i, j-1, k) == mod_TFC_Core.terraWoodSupportV.blockID)
			{
				minY = 0;
			}
		}
		else
		{
			minY = 0;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minZ = 0;
			}
		}

		return AxisAlignedBB.getBoundingBoxFromPool((double)i + minX, (double)j + minY, (double)k + minZ, (double)i + maxX, (double)j + maxY, (double)k + maxZ);
	}

	public float getHardness(int md)
	{
		switch(md)
		{
		case 5:
		{
			return 3.5F;
		}
		case 6:
		{
			return 3.0F;
		}
		case 0:
		{
			return 3.5F;
		}
		case 11:
		{
			return 2.5F;
		}
		case 13:
		{
			return 2.5F;
		}
		default:
		{
			return 2.0F;
		}
		}
	}
	public int getRenderType()
	{
		if(this.blockID == mod_TFC_Core.terraWoodSupportV.blockID) {
			return mod_TFC_Core.woodSupportRenderIdV;
		} else {
			return mod_TFC_Core.woodSupportRenderIdH;
		}
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int hSupportID = mod_TFC_Core.terraWoodSupportH.blockID;
		int vSupportID = mod_TFC_Core.terraWoodSupportV.blockID;

		Boolean isHorizontal = world.getBlockId(i, j, k) == hSupportID;
		Boolean isVertical = world.getBlockId(i, j, k) == vSupportID;

		double minX = 0.25; double minY = 0.25; double minZ = 0.25;
		double maxX = 0.75; double maxY = 0.75; double maxZ = 0.75;


		if(isHorizontal)
		{
			minY = 0.5;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minZ = 0;
			}
			if(world.getBlockId(i, j-1, k) == mod_TFC_Core.terraWoodSupportV.blockID)
			{
				minY = 0;
			}
		}
		else
		{
			minY = 0;
			maxY = 1;
			if(world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i+1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxX = 1;
			}
			if(world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i-1, j, k) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minX = 0;
			}
			if(world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k+1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				maxZ = 1;
			}
			if(world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportV.blockID || world.getBlockId(i, j, k-1) == mod_TFC_Core.terraWoodSupportH.blockID)
			{
				minZ = 0;
			}
		}

		return AxisAlignedBB.getBoundingBoxFromPool((double)i + minX, (double)j + minY, (double)k + minZ, (double)i + maxX, (double)j + maxY, (double)k + maxZ);
	}

	@Override
	public String getTextureFile() 
	{
		return "/bioxx/terrablocks2.png";
	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//super.harvestBlock(world, entityplayer, i, j, k, l);
		if(blockID == mod_TFC_Core.terraWoodSupportH.blockID)
		{
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.terraWoodSupportItemH, 1, l));
		}

		if(blockID == mod_TFC_Core.terraWoodSupportV.blockID)
		{
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.terraWoodSupportItemV, 1, l));
		}
	}

	public boolean isBlockNormalCube(World world, int i, int j, int k)
	{
		return false;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5) 
	{
		onNeighborBlockChange(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4));
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{

		int hSupportID = mod_TFC_Core.terraWoodSupportH.blockID;
		int vSupportID = mod_TFC_Core.terraWoodSupportV.blockID;

		Boolean isHorizontal = world.getBlockId(i, j, k) == hSupportID;
		Boolean isVertical = world.getBlockId(i, j, k) == vSupportID;

		int meta = world.getBlockMetadata(i, j, k);

		if(isVertical)//Vertical Beam
		{
			//if the block directly beneath the support is not solid or a support then break the support
			if(!world.isBlockOpaqueCube(i, j-1, k) && world.getBlockId(i, j-1, k) != vSupportID)
			{	
				harvestBlock(world, null, i, j, k,  meta);
				world.setBlockWithNotify(i, j, k, 0);
			}
		}
		else if(isHorizontal)//Horizontal Beam
		{
			Boolean b1 = !getSupportInRange(world,i,j,k,5,mod_TFC_Core.terraWoodSupportV.blockID);
			Boolean b2 = !getSupportInRange(world,i,j-1,k,5,mod_TFC_Core.terraWoodSupportV.blockID);
			Boolean support = isNextToSupport(world,i,j,k) == 0;
			//if the block on any side is not a support then break
			if(support  || b2 || b1 && b2)
			{
				harvestBlock(world, null, i, j, k,  meta);
				world.setBlockWithNotify(i, j, k, 0);
			}
		}
	}
}
