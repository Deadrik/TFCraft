package net.minecraft.src.TFC_Core.Blocks;

import java.math.MathContext;
import java.util.Random;

import net.minecraft.src.*;

public class BlockBucketWater extends BlockCustomFluid
{
	/**
     * Indicates whether the flow direction is optimal. Each array index corresponds to one of the four cardinal
     * directions.
     */
    boolean[] isOptimalFlowDirection = new boolean[4];

    /**
     * The estimated cost to flow in a given direction from the current point. Each array index corresponds to one of
     * the four cardinal directions.
     */
    int[] flowCost = new int[4];

	public BlockBucketWater(int par1)
    {
        super(par1, Material.water);
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
    	if (par1World.getBlockId(par2, par3, par4) == this.blockID)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
        }
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	//Initialize variables
		int volume = 0;
		int remainder = 0;
		int direction = 0;
		boolean[] optimal = new boolean[4];
		
		int meta = world.getBlockMetadata(x, y, z);
		
		//Try to move down
		if (moveToBlock(world, x, y, z, x, y - 1, z))
		{
			return;
		}
		
		//Get optimal flow direction
		optimal = getOptimalFlowDirections(world, x, y, z);

		//Move
		if (optimal[0])
		{
			if (!moveToBlock(world, x, y, z, x - 1, y, z))
				if (!moveToBlock(world, x, y, z, x, y, z + 1))
					if (!moveToBlock(world, x, y, z, x, y, z - 1))
						if (!moveToBlock(world, x, y, z, x + 1, y, z));
		}
		else if (optimal[1])
		{
		    if (!moveToBlock(world, x, y, z, x + 1, y, z))
                if (!moveToBlock(world, x, y, z, x, y, z + 1))
                    if (!moveToBlock(world, x, y, z, x, y, z - 1))
                        if (!moveToBlock(world, x, y, z, x - 1, y, z));
		}
		else if (optimal[2])
		{
		    if (!moveToBlock(world, x, y, z, x, y, z - 1))
                if (!moveToBlock(world, x, y, z, x - 1, y, z))
                    if (!moveToBlock(world, x, y, z, x + 1, y, z))
                        if (!moveToBlock(world, x, y, z, x, y, z + 1));
		}
		else if (optimal[3])
		{
		    if (!moveToBlock(world, x, y, z, x, y, z + 1))
                if (!moveToBlock(world, x, y, z, x - 1, y, z))
                    if (!moveToBlock(world, x, y, z, x + 1, y, z))
                        if (!moveToBlock(world, x, y, z, x, y, z - 1));
		}
		
//		if (meta == world.getBlockMetadata(x, y, z) && meta > 5)
//		{
//		    if(random.nextInt(100) < 10)
//		    {
//		        if(meta > 1)
//		            world.setBlockMetadata(x, y, z, meta-1);
//		        else
//		            world.setBlock(x, y, z, 0);
//		    }
//		}
    }

	private boolean moveToBlock(World world, int x, int y, int z, int x2, int y2, int z2)
	{
		int blockID2 = world.getBlockId(x2, y2, z2);
		int originMeta = world.getBlockMetadata(x, y, z);
		int destMeta = world.getBlockMetadata(x2, y2, z2);
		if (blockID2 == this.blockID)
		{
			if (destMeta > originMeta || y > y2)
			{
				if (originMeta < 7 && destMeta > 0)
				{
					world.setBlockMetadata(x, y, z, originMeta + 1);
					world.markBlockNeedsUpdate(x, y, z);
					world.setBlockMetadata(x2, y2, z2, destMeta - 1);
					world.markBlockNeedsUpdate(x2, y2, z2);
					return true;
				}
				else if (destMeta > 0)
				{
					world.setBlockWithNotify(x, y, z, 0);
					world.markBlockNeedsUpdate(x, y, z);
					world.setBlockMetadata(x2, y2, z2, destMeta - 1);
					world.markBlockNeedsUpdate(x2, y2, z2);
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else if (liquidCanDisplaceBlock(world, x2, y2, z2))
		{
            if (blockID2 > 0)
            {
                if (this.blockMaterial == Material.lava)
                {
                    this.triggerLavaMixEffects(world, x2, y2, z2);
                }
                else if (blockID2 == Block.waterMoving.blockID || blockID2 == Block.waterStill.blockID)
                {
                	if (originMeta < 7)
                	{
        				world.setBlockMetadataWithNotify(x, y, z, originMeta + 1);
        				world.markBlockNeedsUpdate(x, y, z);
                    	return true;
                	}
        			else
        			{
        				world.setBlockWithNotify(x, y, z, 0);
        				world.markBlockNeedsUpdate(x, y, z);
                    	return true;
        			}
                }
                else
                {
                    Block.blocksList[blockID2].dropBlockAsItem(world, x2, y2, z2, world.getBlockMetadata(x2, y2, z2), 0);
                }
            }
            
            if (y2 < y)
            {
            	world.setBlockWithNotify(x, y, z, 0);
            	world.markBlockNeedsUpdate(x, y, z);
    			world.setBlockAndMetadataWithNotify(x2, y2, z2, blockID, originMeta);
    			world.markBlockNeedsUpdate(x2, y2, z2);
    			return true;
            }
            if (originMeta < 7)
            {
				world.setBlockMetadataWithNotify(x, y, z, originMeta + 1);
				world.markBlockNeedsUpdate(x, y, z);
				world.setBlockAndMetadataWithNotify(x2, y2, z2, blockID, 7);
				world.markBlockNeedsUpdate(x2, y2, z2);
	        	return true;
            }
			else if (world.getBlockId(x - 1, y, z) != this.blockID &&
					world.getBlockId(x + 1, y, z) != this.blockID &&
					world.getBlockId(x, y + 1, z) != this.blockID &&
					world.getBlockId(x, y, z - 1) != this.blockID &&
					world.getBlockId(x, y, z + 1) != this.blockID)
			{
				world.setBlockWithNotify(x, y, z, 0);
				return true;
			}
			else
			{
				world.setBlockWithNotify(x, y, z, 0);
				world.markBlockNeedsUpdate(x, y, z);
				world.setBlockAndMetadataWithNotify(x2, y2, z2, blockID, 7);
				world.markBlockNeedsUpdate(x2, y2, z2);
	        	return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	/**
     * calculateFlowCost(World world, int x, int y, int z, int accumulatedCost, int previousDirectionOfFlow) - Used to
     * determine the path of least resistance, this method returns the lowest possible flow cost for the direction of
     * flow indicated. Each necessary horizontal flow adds to the flow cost.
     */
    private int calculateFlowCost(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = 1000;

        for (int var8 = 0; var8 < 4; ++var8)
        {
            if ((var8 != 0 || par6 != 1) && (var8 != 1 || par6 != 0) && (var8 != 2 || par6 != 3) && (var8 != 3 || par6 != 2))
            {
                int var9 = par2;
                int var11 = par4;

                if (var8 == 0)
                {
                    var9 = par2 - 1;
                }

                if (var8 == 1)
                {
                    ++var9;
                }

                if (var8 == 2)
                {
                    var11 = par4 - 1;
                }

                if (var8 == 3)
                {
                    ++var11;
                }

                if (!this.blockBlocksFlow(par1World, var9, par3, var11) && ((par1World.getBlockMaterial(var9, par3, var11) != this.blockMaterial /*||
                		par1World.getBlockId(var9, par3, var11) == this.blockID) || par1World.getBlockMetadata(var9, par3, var11) != 0*/)))
                {
                    if (!this.blockBlocksFlow(par1World, var9, par3 - 1, var11))
                    {
                        return par5;
                    }

                    if (par5 < 4)
                    {
                        int var12 = this.calculateFlowCost(par1World, var9, par3, var11, par5 + 1, var8);

                        if (var12 < var7)
                        {
                            var7 = var12;
                        }
                    }
                }
            }
        }

        return var7;
    }

    /**
     * Returns a boolean array indicating which flow directions are optimal based on each direction's calculated flow
     * cost. Each array index corresponds to one of the four cardinal directions. A value of true indicates the
     * direction is optimal.
     */
    private boolean[] getOptimalFlowDirections(World par1World, int par2, int par3, int par4)
    {
        int var5;
        int var6;

        for (var5 = 0; var5 < 4; ++var5)
        {
            this.flowCost[var5] = 1000;
            var6 = par2;
            int var8 = par4;

            if (var5 == 0)
            {
                var6 = par2 - 1;
            }

            if (var5 == 1)
            {
                ++var6;
            }

            if (var5 == 2)
            {
                var8 = par4 - 1;
            }

            if (var5 == 3)
            {
                ++var8;
            }

            if (!this.blockBlocksFlow(par1World, var6, par3, var8) && (par1World.getBlockId(var6, par3, var8) != this.blockID || par1World.getBlockMetadata(var6, par3, var8) != 0))
            {
                if (!this.blockBlocksFlow(par1World, var6, par3 - 1, var8))
                {
                    this.flowCost[var5] = 0;
                }
                else
                {
                    this.flowCost[var5] = this.calculateFlowCost(par1World, var6, par3, var8, 1, var5);
                }
            }
        }

        var5 = this.flowCost[0];

        for (var6 = 1; var6 < 4; ++var6)
        {
            if (this.flowCost[var6] < var5)
            {
                var5 = this.flowCost[var6];
            }
        }

        for (var6 = 0; var6 < 4; ++var6)
        {
            this.isOptimalFlowDirection[var6] = this.flowCost[var6] == var5;
        }

        return this.isOptimalFlowDirection;
    }

	/**
     * Returns true if block at coords blocks fluids
     */
    private boolean blockBlocksFlow(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockId(par2, par3, par4);

        if (var5 != Block.doorWood.blockID && var5 != Block.doorSteel.blockID && var5 != Block.signPost.blockID && var5 != Block.ladder.blockID && var5 != Block.reed.blockID)
        {
            if (var5 == 0)
            {
                return false;
            }
            else
            {
                Material var6 = Block.blocksList[var5].blockMaterial;
                return var6 == Material.portal ? true : var6.blocksMovement();
            }
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Returns true if the block at the coordinates can be displaced by the liquid.
     */
    private boolean liquidCanDisplaceBlock(World par1World, int par2, int par3, int par4)
    {
        Material material = par1World.getBlockMaterial(par2, par3, par4);
        return material == this.blockMaterial ? true : (material == Material.lava ? false : !this.blockBlocksFlow(par1World, par2, par3, par4));
    }
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);

//        if (par1World.getBlockId(par2, par3, par4) == this.blockID)
//        {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
//        }
    }
    
    public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this,1,0));
	}
}