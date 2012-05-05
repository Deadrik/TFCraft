package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.General.TFCSettings;

public class BlockCollapsable2 extends BlockTerra2
{

	protected BlockCollapsable2(int par1, Material material)
	{
		super(par1,  material);
	}

	public boolean canFallBelow(World world, int i, int j, int k)
	{
		if(j < 2) {
			return false;
		}

		int l = world.getBlockId(i, j, k);

		if (l == 0)
		{
			return true;
		}
		if (l == Block.bedrock.blockID)
		{
			return false;
		}
		if (l == Block.fire.blockID)
		{
			return true;
		}
		if (l == Block.torchWood.blockID)
		{
			return true;
		}
		Material material = Block.blocksList[l].blockMaterial;
		if (material == Material.water)
		{
			return true;
		}
		return material == Material.lava;
	}

	public Boolean hasNaturalSupport(World world, int i, int j, int k)
	{
		//Make sure that the block beneath the one we're checking is not a solid, if it is then return true and don't waste time here.
		if(world.getBlockId(i, j-1, k) != 0)
		{
			return true;
		}

		if(world.isBlockOpaqueCube(i+1, j, k))
		{
			if(world.isBlockOpaqueCube(i+1, j-1, k))
			{
				return true;
			}
		}

		if(world.isBlockOpaqueCube(i-1, j, k))
		{
			if(world.isBlockOpaqueCube(i-1, j-1, k))
			{
				return true;
			}
		}

		if(world.isBlockOpaqueCube(i, j, k+1))
		{
			if(world.isBlockOpaqueCube(i, j-1, k+1))
			{
				return true;
			}
		}

		if(world.isBlockOpaqueCube(i, j, k-1))
		{
			if(world.isBlockOpaqueCube(i, j-1, k-1))
			{
				return true;
			}
		}

		//Diagonals		
		if(world.isBlockOpaqueCube(i+1, j, k-1))
		{
			if(world.isBlockOpaqueCube(i+1, j-1, k-1))
			{
				return true;
			}
		}

		if(world.isBlockOpaqueCube(i-1, j, k-1))
		{
			if(world.isBlockOpaqueCube(i-1, j-1, k-1))
			{
				return true;
			}
		}

		if(world.isBlockOpaqueCube(i+1, j, k+1))
		{
			if(world.isBlockOpaqueCube(i+1, j-1, k+1))
			{
				return true;
			}
		}

		if(world.isBlockOpaqueCube(i-1, j, k+1))
		{
			if(world.isBlockOpaqueCube(i-1, j-1, k+1))
			{
				return true;
			}
		}

		return false;
	}

	public Boolean isNearSupport(World world, int i, int j, int k)
	{
		for(int y = -1; y < 1; y++)
		{
			for(int x = -4; x < 5; x++)
			{
				for(int z = -4; z < 5; z++)
				{
					if(world.getBlockId(i+x, j+y, k+z) == mod_TFC_Core.terraWoodSupportH.blockID)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public Boolean isUnderLoad(World world, int i, int j, int k)
	{
		for(int x = 1; x < TFCSettings.minimumRockLoad; x++)
		{
			if(!world.isBlockOpaqueCube(i, j+x, k))
			{
				return false;
			}
		}
		return true;
	}

	public Boolean tryToFall(World world, int i, int j, int k, int fallingBlockID, int metaID)
	{
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;

		if (canFallBelow(world, xCoord, yCoord - 1, zCoord) && yCoord >= 2 && !isNearSupport(world, i,j,k) && isUnderLoad(world, i,j,k) &&world.getBlockId(i, j, k) != Block.bedrock.blockID /*&& !hasNaturalSupport(world,i,j,k)*/) {
			;
		}
		{
			byte byte0 = 32;

			if (!world.isRemote)
			{
				Random R = new Random();

				int meta = world.getBlockMetadata(i, j, k);

				if(fallingBlockID == mod_TFC_Core.terraStoneMMCobble.blockID)
				{
					meta+=17;
				}

				world.spawnEntityInWorld(new EntityFallingStone(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, fallingBlockID, metaID, 5));
				world.setBlock(i, j, k, 0);

				return true;
			}
		}
		return false;
	}

}
