package TFC.Blocks;

import java.math.MathContext;
import java.util.Random;

import net.minecraft.src.*;

public class BlockFiniteWater extends BlockFluid
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

    public BlockFiniteWater(int par1)
    {
        super(par1, Material.water);
        this.setTickRandomly(false);
    }
    
    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck(int par1, boolean par2)
    {
        return par2;
    }
    
    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return mod_TFC_Core.finiteWaterRenderId;
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
        //Try to freeze
        if (world.getBiomeGenForCoords(x, z).temperature < 0.2)
        {
            if (y == 145 && world.getBlockMetadata(x, y, z) == 0)
            {
                world.setBlockAndMetadataWithNotify(x, y, z, Block.ice.blockID, world.getBlockMetadata(x, y, z));
                return;
            }
            else if (random.nextInt(200) == 0 && world.canBlockSeeTheSky(x, y, z) && world.getBlockMetadata(x, y, z) == 0)
            {
                world.setBlockAndMetadataWithNotify(x, y, z, Block.ice.blockID, world.getBlockMetadata(x, y, z));
                return;
            }
        }
        
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

        if (meta == world.getBlockMetadata(x, y, z) && meta > 5)
          {
              if(random.nextInt(100) < 10)
              {
                  if(meta > 1)
                  {
                      world.setBlockMetadata(x, y, z, meta-1);
                      world.markBlockNeedsUpdate(x, y, z);
                  }
                  else
                  {
                      world.setBlock(x, y, z, 0);
                      world.markBlockNeedsUpdate(x, y, z);
                  }
              }
          }
    }

    private boolean moveToBlock(World world, int x, int y, int z, int x2, int y2, int z2)
    {
        int blockID2 = world.getBlockId(x2, y2, z2);
        int blockID3 = world.getBlockId(x + (x - x2), y + (y - y2), z + (z - z2));
        int blockMeta = world.getBlockMetadata(x, y, z);
        int blockMeta2 = world.getBlockMetadata(x2, y2, z2);
        int blockMeta3 = world.getBlockMetadata(x + (x - x2), y + (y - y2), z + (z - z2));
        
        if(world.getBlockId(x, y-1, z) == Block.waterStill.blockID)
        {
            world.setBlock(x, y, z, 0);
            return true;
        }
        if (this.blockID == mod_TFC_Core.finiteWater.blockID && blockID2 == mod_TFC_Core.finiteWater.blockID)
        {
            world.setBlockAndMetadataWithNotify(x2, y2, z2, mod_TFC_Core.finiteWater.blockID, blockMeta2);
        }
        
        if (blockID2 == this.blockID)
        {
            if (blockMeta2 > blockMeta + 1 || y > y2)
            {
                if (blockMeta < 7 && blockMeta2 > 0)
                {
                    world.setBlockMetadataWithNotify(x, y, z, blockMeta + 1);
                    world.setBlockMetadataWithNotify(x2, y2, z2, blockMeta2 - 1);
                    return true;
                }
                else if (blockMeta2 > 0)
                {
                    world.setBlockWithNotify(x, y, z, 0);
                    world.setBlockMetadataWithNotify(x2, y2, z2, blockMeta2 - 1);
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (blockID3 == this.blockID)
            {
                if (blockMeta2 < blockMeta && blockMeta < blockMeta3)
                {
                    world.setBlockMetadataWithNotify(x2, y2, z2, blockMeta);
                    world.setBlockMetadataWithNotify(x + (x - x2), y + (y - y2), z + (z - z2), blockMeta);
                    return true;
                }
                else if (blockMeta3 < blockMeta && blockMeta < blockMeta2)
                {
                    world.setBlockMetadataWithNotify(x2, y2, z2, blockMeta);
                    world.setBlockMetadataWithNotify(x + (x - x2), y + (y - y2), z + (z - z2), blockMeta);
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
        else if (liquidCanDisplaceBlock(world, x2, y2, z2) || (y2 < y && y2 == 145 && world.getBlockMaterial(x2, y2, z2) == Material.water))
        {
            if (blockID2 > 0)
            {
                if (blockID2 == Block.waterMoving.blockID || blockID2 == Block.waterStill.blockID)
                {
                    if (blockMeta < 7)
                    {
                        world.setBlockMetadataWithNotify(x, y, z, blockMeta + 1);
                        return true;
                    }
                    else
                    {
                        world.setBlockWithNotify(x, y, z, 0);
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
                world.setBlockAndMetadataWithNotify(x2, y2, z2, blockID, blockMeta);
                return true;
            }
            if (blockMeta < 7)
            {
                world.setBlockMetadataWithNotify(x, y, z, blockMeta + 1);
                world.setBlockAndMetadataWithNotify(x2, y2, z2, blockID, 7);
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
                world.setBlockAndMetadataWithNotify(x2, y2, z2, blockID, 7);
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
    private int calculateFlowCost(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5, int par6)
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

                if (!this.blockBlocksFlow(par1iBlockAccess, var9, par3, var11) && ((par1iBlockAccess.getBlockMaterial(var9, par3, var11) != this.blockMaterial ||
                        par1iBlockAccess.getBlockId(var9, par3, var11) == this.blockID) || par1iBlockAccess.getBlockMetadata(var9, par3, var11) != 0))
                {
                    if (!this.blockBlocksFlow(par1iBlockAccess, var9, par3 - 1, var11))
                    {
                        return par5;
                    }

                    if (par5 < 4)
                    {
                        int var12 = this.calculateFlowCost(par1iBlockAccess, var9, par3, var11, par5 + 1, var8);

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
    private boolean[] getOptimalFlowDirections(IBlockAccess par1iBlockAccess, int par2, int par3, int par4)
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

            if (!this.blockBlocksFlow(par1iBlockAccess, var6, par3, var8) && (par1iBlockAccess.getBlockMaterial(var6, par3, var8) != this.blockMaterial || par1iBlockAccess.getBlockMetadata(var6, par3, var8) != 0))
            {
                if (!this.blockBlocksFlow(par1iBlockAccess, var6, par3 - 1, var8))
                {
                    this.flowCost[var5] = 0;
                }
                else
                {
                    this.flowCost[var5] = this.calculateFlowCost(par1iBlockAccess, var6, par3, var8, 1, var5);
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
    private boolean blockBlocksFlow(IBlockAccess par1iBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1iBlockAccess.getBlockId(par2, par3, par4);

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
        return material == Material.water ? false : (material == Material.lava ? false : !this.blockBlocksFlow(par1World, par2, par3, par4));
    }
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockId(par2, par3, par4) == this.blockID)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
        }
    }
    
    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3D par6Vec3D)
    {
        Vec3D var7 = this.getFlowVector(par1World, par2, par3, par4);
        par6Vec3D.xCoord += var7.xCoord;
        par6Vec3D.yCoord += var7.yCoord;
        par6Vec3D.zCoord += var7.zCoord;
    }
    
    public static double func_293_a(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, Material par4Material)
    {
        Vec3D var5 = null;
        var5 = ((BlockFiniteWater)mod_TFC_Core.finiteWater).getFlowVector(par0IBlockAccess, par1, par2, par3);
        return var5.xCoord == 0.0D && var5.zCoord == 0.0D ? -1000.0D : Math.atan2(var5.zCoord, var5.xCoord) - (Math.PI / 2D);
    }
    
    /**
     * Returns a vector indicating the direction and intensity of fluid flow.
     */
    private Vec3D getFlowVector(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        Vec3D var5 = Vec3D.createVector(0.0D, 0.0D, 0.0D);
        if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 7 && par1IBlockAccess.getBlockId(par2, par3 - 1, par4) != this.blockID)
        {
            boolean[] optimal = getOptimalFlowDirections(par1IBlockAccess, par2, par3, par4);
            if (optimal[0])
            {
                var5 = var5.addVector(-1.0D, 0.0D, 0.0D);
            }
            else if (optimal[1])
            {
                var5 = var5.addVector(1.0D, 0.0D, 0.0D);
            }
            else if (optimal[2])
            {
                var5 = var5.addVector(0.0D, 0.0D, -1.0D);
            }
            else if (optimal[3])
            {
                var5 = var5.addVector(0.0D, 0.0D, 1.0D);
            }
        }
        var5 = var5.normalize();
        return var5;
    }
    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6;
        if (par5Random.nextInt(10) == 0)
        {
            var6 = par1World.getBlockMetadata(par2, par3, par4);

            if (var6 <= 0 || var6 >= 8)
            {
                par1World.spawnParticle("suspended", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + par5Random.nextFloat()), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
            }
        }

        for (var6 = 0; var6 < 0; ++var6)
        {
            int var7 = par5Random.nextInt(4);
            int var8 = par2;
            int var9 = par4;

            if (var7 == 0)
            {
                var8 = par2 - 1;
            }

            if (var7 == 1)
            {
                ++var8;
            }

            if (var7 == 2)
            {
                var9 = par4 - 1;
            }

            if (var7 == 3)
            {
                ++var9;
            }

            if (par1World.getBlockMaterial(var8, par3, var9) == Material.air && (par1World.getBlockMaterial(var8, par3 - 1, var9).blocksMovement() || par1World.getBlockMaterial(var8, par3 - 1, var9).isLiquid()))
            {
                float var10 = 0.0625F;
                double var11 = (double)((float)par2 + par5Random.nextFloat());
                double var13 = (double)((float)par3 + par5Random.nextFloat());
                double var15 = (double)((float)par4 + par5Random.nextFloat());

                if (var7 == 0)
                {
                    var11 = (double)((float)par2 - var10);
                }

                if (var7 == 1)
                {
                    var11 = (double)((float)(par2 + 1) + var10);
                }

                if (var7 == 2)
                {
                    var15 = (double)((float)par4 - var10);
                }

                if (var7 == 3)
                {
                    var15 = (double)((float)(par4 + 1) + var10);
                }

                double var17 = 0.0D;
                double var19 = 0.0D;

                if (var7 == 0)
                {
                    var17 = (double)(-var10);
                }

                if (var7 == 1)
                {
                    var17 = (double)var10;
                }

                if (var7 == 2)
                {
                    var19 = (double)(-var10);
                }

                if (var7 == 3)
                {
                    var19 = (double)var10;
                }

                par1World.spawnParticle("splash", var11, var13, var15, var17, 0.0D, var19);
            }
        }

        if (par5Random.nextInt(64) == 0)
        {
            var6 = par1World.getBlockMetadata(par2, par3, par4);

            if (var6 == 7)
            {
                par1World.playSoundEffect((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), "liquid.water", par5Random.nextFloat() * 0.25F + 0.75F, par5Random.nextFloat() * 1.0F + 0.5F);
            }
        }

        double var21;
        double var23;
        double var22;

        if (par5Random.nextInt(10) == 0 && par1World.isBlockNormalCube(par2, par3 - 1, par4) && !par1World.getBlockMaterial(par2, par3 - 2, par4).blocksMovement())
        {
            var21 = (double)((float)par2 + par5Random.nextFloat());
            var22 = (double)par3 - 1.05D;
            var23 = (double)((float)par4 + par5Random.nextFloat());
            par1World.spawnParticle("dripWater", var21, var22, var23, 0.0D, 0.0D, 0.0D);
        }
    }
}
