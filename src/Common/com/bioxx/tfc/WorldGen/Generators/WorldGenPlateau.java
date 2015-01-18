package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;

public class WorldGenPlateau
{
	public WorldGenPlateau()
	{
	}

	public boolean generate(World par1World, Random rand, int x, int y, int z, int radiusStart, int radiusTop, int height, int centers, int radiusJitter, int taperChance)
	{
		// template call// (new WorldGenPlateau()).generate(/*currentWorld*/   , /*randomGenerator*/   ,/*x*/   ,/*y*/   ,/*z*/   ,/*radiusStart*/   ,/*radiusTop*/   ,
		// /*height*/   ,/*centers*/   ,/*radiusJitter*/   ,/*taperChance*/   );

		//defaults i used for testing
		//radiusStart = 8; //this makes the starting radius at the bottom of a center
		//radiusTop = 4; //this is the smallest the top radius can get
		//height = 40; // makes this many layers
		//centers=5;  // makes more of the stacks in a tight area defined by the starting radius, adds asymmetry
		//radiusJitter=2; //this makes the radius of a disk shift in or out by X amount, makes it less smooth, 1 is off, 2+ is more chaotic 
		//taperChance=5; // chance that the radius will be decreased this layer, height/(radiusStart-radiusTOP) will give you a good starting number for this

		//int tempX = x; 
		//int tempY = y;
		//int tempZ = z;
		int radius = radiusStart;

		y = par1World.getTopSolidOrLiquidBlock(x, z);
		for(int centerRun=0; centerRun <= centers; centerRun++) // make the seperate towers
		{
			radius = radiusStart;
			//tempX = x+rand.nextInt(radius)-rand.nextInt(radius);
			//tempZ = z+rand.nextInt(radius)-rand.nextInt(radius);
			/*for(int baseDepth=1; baseDepth <=3; baseDepth++) // make some blocks down for a base
			{
				drawCircle(par1World,radius+rand.nextInt(radiusJitter)-rand.nextInt(radiusJitter),tempX,y-baseDepth,tempZ);
			}*/
			for(int run=0; run<=height; run++) // make the stack
			{
				//drawCircle(par1World,radius+rand.nextInt(radiusJitter)-rand.nextInt(radiusJitter),tempX,y+run,tempZ);
				if(radiusTop <= radius && rand.nextInt(taperChance)==1)
					radius--;
			}
		}
		return true;
	}

	/*public boolean drawCircle(World par1World, int radius, int x, int y, int z)
	{
		//int aTrig = 0;
		//int bTrig = 0;

		for(int loopCount = 0; loopCount <= radius; loopCount++)
		{
			// draw quadrants
	//			aTrig = (radius - loopCount);
	//			bTrig = (int)(Math.sqrt((radius*radius - aTrig*aTrig)));
			// top right
	//            par1World.setBlock(x + aTrig, y, z + bTrig, ECBlocks.StonePerm.blockID);
	//            for(int i=z+bTrig; i >= z; i--)
	//            {
	//                par1World.setBlock(x + aTrig, y, i, ECBlocks.StonePerm.blockID);
	//            }
	//        // bottom left
	//
	//            par1World.setBlock(x - aTrig, y, z - bTrig, ECBlocks.StonePerm.blockID);
	//            for(int i=z-bTrig; i <= z; i++)
	//            {
	//                par1World.setBlock(x - aTrig, y, i, ECBlocks.StonePerm.blockID);
	//            }
	//        // top left
	//
	//            par1World.setBlock(x - aTrig, y, z + bTrig, ECBlocks.StonePerm.blockID);
	//            for(int i=z+bTrig; i >= z; i--)
	//            {
	//                par1World.setBlock(x - aTrig, y, i, ECBlocks.StonePerm.blockID);
	//            }
	//    
	//        // bottom right
	//            par1World.setBlock(x + aTrig, y, z - bTrig, ECBlocks.StonePerm.blockID);
	//            for(int i=z-bTrig; i <= z; i++)
	//            {
	//                par1World.setBlock(x + aTrig, y, i, ECBlocks.StonePerm.blockID);
	//            }
		}
		return true;
	}*/
}
