package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Core.Direction;
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

public class WorldGenCustomRedwoodTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomRedwoodTrees (boolean flag, int id)
	{
		super (flag);
		treeId = id;
	}


	public boolean generate (World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int treeHeight = random.nextInt (7) + 20;
		int w = treeHeight / 24;
		int treeType = random.nextInt (2);
		boolean flag = true;
		if (yCoord < 1 || yCoord + treeHeight + 4 > world.getHeight())
		{
			return false;
		}
		for (int i1 = yCoord ; i1 <= yCoord + 4 + treeHeight ; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
			{
				byte0 = 0;
			}
			if (i1 >= yCoord + 4 + treeHeight - 2)
			{
				byte0 = 2;
			}
			for (int l1 = xCoord - byte0 ; l1 <= xCoord + byte0 && flag ; l1++)
			{
				for (int k2 = zCoord - byte0 ; k2 <= zCoord + byte0 && flag ; k2++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						int j3 = world.getBlockId (l1, i1, k2);
						if (j3 != 0 && j3 != Block.leaves.blockID)
						{
							flag = false;
						}
					}
					else
					{
						flag = false;
					}
				}

			}

		}

		if (!flag)
		{
			return false;
		}
		int var3 = world.getBlockId (xCoord, yCoord - 1, zCoord);
		if (!(var3 == TFCBlocks.Dirt.blockID || var3 == TFCBlocks.Dirt2.blockID || var3 == TFCBlocks.Grass.blockID || var3 == TFCBlocks.Grass2.blockID ||
				var3 == TFCBlocks.ClayGrass.blockID || var3 == TFCBlocks.ClayGrass2.blockID)|| yCoord >= world.getHeight() - treeHeight - 1)
		{
			return false;
		}

		if (treeType == 1)
		{
			int covTrunk = 16;
			int lay3 = 6;
			int lay4 = 5;
			int lay5 = 4;
			int lay6 = 3;
			for (int yPos = yCoord + treeHeight - covTrunk ; yPos <= yCoord + treeHeight + 4 ; yPos++)
			{
				int leafLayer = yPos - (yCoord + treeHeight - covTrunk);
				int width = leafLayer + 2;
				if (leafLayer <= lay3 + 1 && leafLayer > 1)
				{
					width = 4;
				}
				else if (leafLayer <= lay3 + 1 + lay4 && leafLayer > lay3 + 1)
				{
					width = 3;
				}
				else if (leafLayer <= lay3 + 1 + lay4 + lay5 && leafLayer > lay3 + 1 + lay4)
				{
					width = 2;
				}
				else if (leafLayer <= lay3 + 1 + lay4 + lay5 + lay6 && leafLayer > lay3 + 1 + lay4 + lay5)
				{
					width = 1;
				}
				else if (leafLayer > lay3 + 1 + lay4 + lay5 + lay6)
				{
					width = 0;
				}
				for (int xPos = xCoord - width ; xPos <= xCoord + width + w ; xPos++)
				{
					int l3 = xPos - xCoord;
					for (int zPos = zCoord - width ; zPos <= zCoord + width + w ; zPos++)
					{
						int j4 = zPos - zCoord;
						if ((Math.abs(l3) != width || Math.abs(j4) != width ||random.nextInt (2) != 0) && 
								!Block.opaqueCubeLookup [world.getBlockId (xPos, yPos, zPos)]&&
										world.getBlockId (xPos, yPos, zPos) != TFCBlocks.Wood.blockID)
						{
							setBlockAndMetadata(world, xPos, yPos, zPos, Block.leaves.blockID, treeId);
						}
					}

				}

			}
		}
		else if (treeType != 1)
		{
			int covTrunk = treeHeight * 2 / 3;
			covTrunk = 16;
			int lay2 = 3;
			int lay3 = 6;
			int lay4 = 4;
			int lay5 = 4;
			for (int yPos = yCoord + covTrunk ; yPos <= yCoord + treeHeight + 4 ; yPos++)
			{
				byte byte1 = 2;
				int leafLayer = yPos - (yCoord + treeHeight - covTrunk);
				int width = leafLayer + 1;
				if (leafLayer <= lay2 && leafLayer > 0)
				{
					width = 2;
				}
				else if (leafLayer <= lay2 +  lay3 && leafLayer > lay2)
				{
					width = 3;
				}
				else if (leafLayer <= lay2 +  lay4 + lay3 && leafLayer > lay3 +  lay2)
				{
					width = 2;
				}
				else if (leafLayer <= lay3 +  lay4 + lay5 + lay2 && leafLayer > lay3 +  lay4 + lay2)
				{
					width = 1;
				}
				else if (leafLayer > lay3 +  lay4 + lay5 + lay2)
				{
					width = 0;
				}
				for (int xPos = xCoord - width ; xPos <= xCoord + width + w ; xPos++)
				{
					int l3 = xPos - xCoord;
					for (int zPos = zCoord - width ; zPos <= zCoord + width + w ; zPos++)
					{
						int j4 = zPos - zCoord;
						if ((Math.abs(l3) != width || Math.abs(j4) != width ||random.nextInt (2) != 0) && 
								!Block.opaqueCubeLookup [world.getBlockId (xPos, yPos, zPos)]&&
										world.getBlockId (xPos, yPos, zPos) != TFCBlocks.Wood.blockID)
						{
							setBlockAndMetadata(world, xPos, yPos, zPos, Block.leaves.blockID, treeId);
						}
					}

				}

			}
		}
		//Create the tree Trunk
		for (int j2 = 0 ; j2 < treeHeight ; j2++)
		{
			int i3 = world.getBlockId (xCoord, yCoord + j2, zCoord);
			if (i3 == 0 || i3 == Block.leaves.blockID)
			{
				for (int x = xCoord ; x <= xCoord + w ; x++)
				{
					for (int z = zCoord ; z <= zCoord + w ; z++)
					{
						setBlockAndMetadata(world, x, yCoord + j2, z, Block.wood.blockID, treeId);
					}
				}
			}
		}
//		//Create the branches
//		for (int j2 = 0 ; j2 < treeHeight ; j2+=2)
//		{
//			int i3 = world.getBlockId (xCoord, yCoord + j2, zCoord);
//			if (i3 == Block.wood.blockID)
//			{
//				for (int x = xCoord ; x <= xCoord; x++)
//				{
//					for (int z = zCoord ; z <= zCoord; z++)
//					{
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.PosX, random);
//						}
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.NegX, random);
//						}
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.PosZ, random);
//						}
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.NegZ, random);
//						}
//					}
//				}
//			}
//		}

		return true;
	}
}