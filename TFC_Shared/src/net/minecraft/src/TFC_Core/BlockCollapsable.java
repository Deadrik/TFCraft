package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.General.TFCSettings;

public class BlockCollapsable extends BlockTerra
{
	public int dropBlock;

	protected BlockCollapsable(int par1, Material material, int d)
	{
		super(par1,  material);
		dropBlock = d;
	}

	public boolean canFallBelow(World world, int i, int j, int k)
	{		
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
		if (material == Material.water || material == Material.lava)
		{
			return true;
		}
		return false;
	}

	public void DropCarvedStone(World world, int i, int j, int k)
	{
		if(world.isBlockOpaqueCube(i+1, j, k)) {
			return;
		} else if(world.isBlockOpaqueCube(i-1, j, k)) {
			return;
		} else if(world.isBlockOpaqueCube(i, j, k+1)) {
			return;
		} else if(world.isBlockOpaqueCube(i, j, k-1)) {
			return;
		} else if(world.isBlockOpaqueCube(i, j+1, k)) {
			return;
		} else if(world.isBlockOpaqueCube(i, j-1, k)) {
			return;
		}

		dropBlockAsItem_do(world, i, j, k, new ItemStack(blockID, 1, damageDropped(world.getBlockMetadata(i, j, k))));
		world.setBlockWithNotify(i, j, k, 0);

		//            int x = 0;
		//            int y = 0;
		//            int z = 0;
		//            
		//            if(world.isBlockOpaqueCube(i+1, j, k))
		//                x++;
		//            if(world.isBlockOpaqueCube(i-1, j, k))
		//                x--;
		//            if(world.isBlockOpaqueCube(i, j, k+1))
		//                z++;
		//            if(world.isBlockOpaqueCube(i, j, k-1))
		//                z--;
		//            if(world.isBlockOpaqueCube(i, j+1, k))
		//                y++;
		//            if(world.isBlockOpaqueCube(i, j-1, k))
		//                y--;
		// 
		//            //first we check if there is only 1 block cut out. If there is, we give it to the player
		//            if (x == 0 && y == 0 && z == 0)
		//            {
		//                    dropBlockAsItem_do(world, i, j, k, new ItemStack(blockID, 1, world.getBlockMetadata(i, j, k)));
		//                    world.setBlock(i, j, k, 0);
		//            }
	}

	public void DropCarvedStone2(World world, int i, int j, int k)
	{
		int cnt = 0;
		if(world.isBlockOpaqueCube(i+1, j, k)) {
			cnt++;
		}
		if(world.isBlockOpaqueCube(i-1, j, k)) {
			cnt++;
		}
		if(world.isBlockOpaqueCube(i, j, k+1)) {
			cnt++;
		}
		if(world.isBlockOpaqueCube(i, j, k-1)) {
			cnt++;
		}
		if(world.isBlockOpaqueCube(i, j+1, k)) {
			cnt++;
		}
		if(world.isBlockOpaqueCube(i, j-1, k)) {
			cnt++;
		}

		//first we check if there is only 1 block cut out. If there is, we give it to the player
		if (cnt == 0)
		{
			dropBlockAsItem_do(world, i, j, k, new ItemStack(blockID, 1, world.getBlockMetadata(i, j, k)));
			world.setBlock(i, j, k, 0);
		}
		else if(cnt == 3)//Otherwise we check for a 2x2x2 block
		{
			//do a world height check to prevent issues
			if(j+3 > world.getHeight() || j - 3 < 0) {
				return;
			}
			//First we check +x and +z
			int rockcounter = 0;
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i+x, j+y, k+z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C = Block.blocksList[id].getClass();
						}
						if(world.isBlockOpaqueCube(i+x, j+y, k+z) && C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{
							if(world.isBlockOpaqueCube(i+x, j+y, k+z))
							{
								dropBlockAsItem_do(world,i+x, j+y, k+z, new ItemStack(blockID, 1, world.getBlockMetadata(i, j, k)));
								world.setBlock(i+x, j+y, k+z, 0);
							}
						}
					}
				}
				return;
			}
			rockcounter = 0;
			//Now we check -x and -z
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i-x, j+y, k-z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C =  Block.blocksList[world.getBlockId(i-x, j+y, k-z)].getClass();
						}
						if(world.isBlockOpaqueCube(i-x, j+y, k-z) && C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{

							if(world.isBlockOpaqueCube(i-x, j+y, k-z))
							{
								dropBlockAsItem_do(world,i-x, j+y, k-z, new ItemStack(blockID, 1, world.getBlockMetadata(i-x, j+y, k-z)));
								world.setBlock(i-x, j+y, k-z, 0);
							}
						}
					}
				}
				return;
			}
			rockcounter = 0;
			//Now we check +x and -z
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i+x, j+y, k-z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C =  Block.blocksList[world.getBlockId(i+x, j+y, k-z)].getClass();
						}
						if(world.isBlockOpaqueCube(i+x, j+y, k-z) && C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{
							if(world.isBlockOpaqueCube(i+x, j+y, k-z))
							{
								dropBlockAsItem_do(world,i+x, j+y, k-z, new ItemStack(blockID, 1, world.getBlockMetadata(i+x, j+y, k-z)));
								world.setBlock(i+x, j+y, k-z, 0);
							}
						}
					}
				}
				return;
			}
			rockcounter = 0;
			//Now we check -x and +z
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i-x, j+y, k+z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C =  Block.blocksList[world.getBlockId(i-x, j+y, k+z)].getClass();
						}
						if(world.isBlockOpaqueCube(i-x, j-y, k+z) &&  C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{
							if(world.isBlockOpaqueCube(i-x, j+y, k+z))
							{
								dropBlockAsItem_do(world,i-x, j+y, k+z, new ItemStack(blockID, 1, world.getBlockMetadata(i-x, j+y, k+z)));
								world.setBlock(i-x, j+y, k+z, 0);
							}
						}
					}
				}
				return;
			}
			rockcounter = 0;


			//Now we are going to fly the Y and check the other direction
			//First we check +x and +z
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i+x, j-y, k+z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C =  Block.blocksList[world.getBlockId(i+x, j-y, k+z)].getClass();
						}
						if(world.isBlockOpaqueCube(i+x, j-y, k+z) &&   C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{
							if(world.isBlockOpaqueCube(i+x, j-y, k+z))
							{
								dropBlockAsItem_do(world,i+x, j-y, k+z, new ItemStack(blockID, 1, world.getBlockMetadata(i, j, k)));
								world.setBlock(i+x, j-y, k+z, 0);
							}
						}
					}
				}
				return;
			}
			rockcounter = 0;
			//Now we check -x and -z
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i-x, j-y, k-z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C =  Block.blocksList[world.getBlockId(i-x, j-y, k-z)].getClass();
						}
						if(world.isBlockOpaqueCube(i-x, j-y, k-z) &&   C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{

							if(world.isBlockOpaqueCube(i-x, j-y, k-z))
							{
								dropBlockAsItem_do(world,i-x, j-y, k-z, new ItemStack(blockID, 1, world.getBlockMetadata(i-x, j-y, k-z)));
								world.setBlock(i-x, j-y, k-z, 0);
							}
						}
					}
				}
				return;
			}
			rockcounter = 0;
			//Now we check +x and -z
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i+x, j-y, k-z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C =   Block.blocksList[world.getBlockId(i+x, j-y, k-z)].getClass();
						}
						if(world.isBlockOpaqueCube(i+x, j-y, k-z) &&   C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{
							if(world.isBlockOpaqueCube(i+x, j-y, k-z))
							{
								dropBlockAsItem_do(world,i+x, j-y, k-z, new ItemStack(blockID, 1, world.getBlockMetadata(i+x, j-y, k-z)));
								world.setBlock(i+x, j-y, k-z, 0);
							}
						}
					}
				}
				return;
			}
			rockcounter = 0;
			//Now we check -x and +z
			for(int y = -1; y < 4; y++)
			{
				for(int x = -1; x < 4; x++)
				{
					for(int z = -1; z < 4; z++)
					{
						int id = world.getBlockId(i-x, j-y, k+z);
						Class C = null;
						if(Block.blocksList[id]!=null) {
							C =  Block.blocksList[world.getBlockId(i-x, j-y, k+z)].getClass();
						}
						if(world.isBlockOpaqueCube(i-x, j-y, k+z) &&   C!=null && (C == BlockTerraIgEx.class || C == BlockTerraIgIn.class || C == BlockTerraSed.class || C == BlockTerraMM.class)) {
							rockcounter++;
						}
					}
				}
			}
			if(rockcounter == 8)
			{
				for(int y = -1; y < 4; y++)
				{
					for(int x = -1; x < 4; x++)
					{
						for(int z = -1; z < 4; z++)
						{
							if(world.isBlockOpaqueCube(i-x, j-y, k+z))
							{
								dropBlockAsItem_do(world,i-x, j-y, k+z, new ItemStack(blockID, 1, world.getBlockMetadata(i-x, j-y, k+z)));
								world.setBlock(i-x, j-y, k+z, 0);
							}
						}
					}
				}
				return;
			}
		}
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


	public Boolean tryToFall(World world, int i, int j, int k, int l)
	{
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		int fallingBlockID = dropBlock;

		if(world.getBlockId(xCoord, yCoord, zCoord) == Block.bedrock.blockID || world.getBlockId(xCoord, yCoord, zCoord)  == fallingBlockID)
		{
			return false;
		}

		if (canFallBelow(world, xCoord, yCoord - 1, zCoord)  && !isNearSupport(world, i,j,k)  && isUnderLoad(world, i,j,k) /*&& !hasNaturalSupport(world,i,j,k)*/)
		{
			byte byte0 = 32;

			if (!world.isRemote)
			{
				Random R = new Random();

				if(fallingBlockID == mod_TFC_Core.terraStoneMMCobble.blockID)
				{
					l+=17;
				}

				world.spawnEntityInWorld(new EntityFallingStone(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, fallingBlockID, l, 5));
				world.setBlock(i, j, k, 0);

				return true;
			}
		}
		return false;
	}
}
