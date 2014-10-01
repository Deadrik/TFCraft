package com.bioxx.tfc.Blocks.Liquids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLilyPad;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockCustomLiquid extends BlockFluidClassic
{
	protected Fluid fluidType;
	protected IIcon[] icons;

	public BlockCustomLiquid(Fluid fluid, Material mat)
	{
		super(fluid, mat);
		float f = 0.0F;
		float f1 = 0.0F;
		this.setBlockBounds(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
		this.setTickRandomly(true);
		fluidType = fluid;
		if(mat == Material.lava) setTickRate(20); else setTickRate(3);
	}

	public int getDensityDir()
	{
		return this.densityDir;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		if(block.getMaterial() == this.getMaterial())
		{
			return false;
		}

		return super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public int getQuantaValue(IBlockAccess world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z) == Blocks.air)
		{
			return 0;
		}

		if (world.getBlock(x, y, z) != this && world.getBlock(x, y, z) != this.getInverseBlock())
		{
			return -1;
		}

		int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
		return quantaRemaining;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
		int expQuanta = -101;

		// check adjacent block levels if non-source
		if (quantaRemaining < quantaPerBlock)
		{
			int y2 = y - densityDir;

			if (	world.getBlock(x,     y2, z    ) == this ||
					world.getBlock(x - 1, y2, z    ) == this ||
					world.getBlock(x + 1, y2, z    ) == this ||
					world.getBlock(x,     y2, z - 1) == this ||
					world.getBlock(x,     y2, z + 1) == this)
			{
				expQuanta = quantaPerBlock - 1;
			}
			else
			{
				int maxQuanta = -100;
				maxQuanta = getLargerQuanta(world, x - 1, y, z,     maxQuanta);
				maxQuanta = getLargerQuanta(world, x + 1, y, z,     maxQuanta);
				maxQuanta = getLargerQuanta(world, x,     y, z - 1, maxQuanta);
				maxQuanta = getLargerQuanta(world, x,     y, z + 1, maxQuanta);

				expQuanta = maxQuanta - 1;
			}

			// decay calculation
			if (expQuanta != quantaRemaining)
			{
				quantaRemaining = expQuanta;

				if (expQuanta <= 0)
				{
					world.setBlock(x, y, z, Blocks.air);
				}
				else
				{
					world.setBlockMetadataWithNotify(x, y, z, quantaPerBlock - expQuanta, 3);
					world.scheduleBlockUpdate(x, y, z, this, tickRate);
					world.notifyBlocksOfNeighborChange(x, y, z, this);
				}
			}
		}
		// This is a "source" block, set meta to zero, and send a server only update
		else if (quantaRemaining >= quantaPerBlock)
		{
			world.setBlock(x, y, z, getInverseBlock(), 0, 2);
		}

		// Flow vertically if possible
		if (canDisplace(world, x, y + densityDir, z))
		{
			flowIntoBlock(world, x, y + densityDir, z, 1);
			return;
		}

		// Flow outward if possible
		int flowMeta = quantaPerBlock - quantaRemaining + 1;
		if (flowMeta >= quantaPerBlock)
		{
			return;
		}

		if (isSourceBlock(world, x, y, z) || !isFlowingVertically(world, x, y, z))
		{
			if (world.getBlock(x, y - densityDir, z) == this)
			{
				flowMeta = 1;
			}
			boolean flowTo[] = getOptimalFlowDirections(world, x, y, z);

			if (flowTo[0]) flowIntoBlock(world, x - 1, y, z,     flowMeta);
			if (flowTo[1]) flowIntoBlock(world, x + 1, y, z,     flowMeta);
			if (flowTo[2]) flowIntoBlock(world, x,     y, z - 1, flowMeta);
			if (flowTo[3]) flowIntoBlock(world, x,     y, z + 1, flowMeta);
		}

		if(!world.isRemote)
		{
			if(this.getMaterial() == Material.lava)
			{
				if(world.getBlock(x, y+1, z) == Blocks.air)
				{
					int i = x-2+rand.nextInt(5);
					int j = y+1+rand.nextInt(4);
					int k = z-2+rand.nextInt(5);
					if(world.getBlock(i, j, k) == Blocks.air && 
							(world.isSideSolid(i, j+1, k, ForgeDirection.DOWN) || world.isSideSolid(i, j-1, k, ForgeDirection.UP) ||
									world.isSideSolid(i-1, j, k, ForgeDirection.EAST) || world.isSideSolid(i+1, j, k, ForgeDirection.WEST) ||
									world.isSideSolid(i, j, k+1, ForgeDirection.NORTH) || world.isSideSolid(i, j, k-1, ForgeDirection.SOUTH)))
					{
						world.setBlock(i, j, k, TFCBlocks.Sulfur, world.rand.nextInt(4), 3);
					}
				}
			}

		}
	}

	@Override
	public boolean isSourceBlock(IBlockAccess world, int x, int y, int z)
	{
		return (world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 0) || world.getBlock(x, y, z) == this.getInverseBlock();
	}

	@Override
	protected void flowIntoBlock(World world, int x, int y, int z, int meta)
	{
		if (meta < 0) return;
		if (displaceIfPossible(world, x, y, z))
		{
			world.setBlock(x, y, z, this, meta, 3);
		}
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).isAir(world, x, y, z))
		{
			return true;
		}

		Block block = world.getBlock(x, y, z);
		if (block == this || block == this.getInverseBlock() || block.getMaterial() == this.getMaterial())
		{
			return false;
		}

		if (displacements.containsKey(block))
		{
			if (displacements.get(block))
			{
				block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				return true;
			}
			return false;
		}

		Material material = block.getMaterial();
		if (material.blocksMovement() || material == Material.portal)
		{
			return false;
		}

		int density = getDensity(world, x, y, z);
		if (density == Integer.MAX_VALUE) 
		{
			block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			return true;
		}

		if (this.density > density)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	protected boolean canFlowInto(IBlockAccess world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).isAir(world, x, y, z)) return true;

		Block block = world.getBlock(x, y, z);
		if (block == this || block == this.getInverseBlock())
		{
			return true;
		}

		if (displacements.containsKey(block))
		{
			return displacements.get(block);
		}

		Material material = block.getMaterial();
		if (material.blocksMovement()  ||
				material == Material.water ||
				material == Material.lava  ||
				material == Material.portal)
		{
			return false;
		}

		int density = getDensity(world, x, y, z);
		if (density == Integer.MAX_VALUE) 
		{
			return true;
		}

		if (this.density > density)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	protected abstract Block getInverseBlock();

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return 0x354d35;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		if(getFlowMeta(world, x, y, z) != 0)
		{
			int total = 0;
			if(getFlowMeta(world, x+1, y, z) == 0) total++;
			if(getFlowMeta(world, x-1, y, z) == 0) total++;
			if(getFlowMeta(world, x, y, z+1) == 0) total++;
			if(getFlowMeta(world, x, y, z-1) == 0) total++;

			if(total >= 2)
				world.setBlock(x, y, z, getInverseBlock(), 0, 0x3);
		}

		this.checkForHarden(world, x, y, z);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		if(getFlowMeta(world, x, y, z) != 0)
		{
			int total = 0;
			if(getFlowMeta(world, x+1, y, z) == 0) total++;
			if(getFlowMeta(world, x-1, y, z) == 0) total++;
			if(getFlowMeta(world, x, y, z+1) == 0) total++;
			if(getFlowMeta(world, x, y, z-1) == 0) total++;

			if(total >= 2)
				world.setBlock(x, y, z, getInverseBlock(), 0, 0x3);
		}
		this.checkForHarden(world, x, y, z);
	}

	protected int getFlowMeta(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z).getMaterial() == this.blockMaterial ? world.getBlockMetadata(x, y, z) : -1;
	}
	/**
	 * Forces lava to check to see if it is colliding with water, and then decide what it should harden to.
	 */
	private void checkForHarden(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getBlock(par2, par3, par4) == this)
			if (this.blockMaterial == Material.lava)
			{
				boolean flag = false;

				if (flag || par1World.getBlock(par2, par3, par4 - 1).getMaterial() == Material.water)
					flag = true;

				if (flag || par1World.getBlock(par2, par3, par4 + 1).getMaterial() == Material.water)
					flag = true;

				if (flag || par1World.getBlock(par2 - 1, par3, par4).getMaterial() == Material.water)
					flag = true;

				if (flag || par1World.getBlock(par2 + 1, par3, par4).getMaterial() == Material.water)
					flag = true;

				if (flag || par1World.getBlock(par2, par3 + 1, par4).getMaterial() == Material.water)
					flag = true;

				if (flag)
				{
					int l = par1World.getBlockMetadata(par2, par3, par4);

					if (l == 0)
						setBlockforLava(par1World, par2,  par3,  par4,0);
					else if (l <= 4)
						setBlockforLava(par1World, par2,  par3,  par4,1);

					//triggerLavaMixEffects(par1World, par2, par3, par4);
				}
			}
	}

	@Override
	/**
	 * Determines if this block can support the passed in plant, allowing it to be planted and grow.
	 * Some examples:
	 *   Reeds check if its a reed, or if its sand/dirt/grass and adjacent to water
	 *   Cacti checks if its a cacti, or if its sand
	 *   Nether types check for soul sand
	 *   Crops check for tilled soil
	 *   Caves check if it's a colid surface
	 *   Plains check if its grass or dirt
	 *   Water check if its still water
	 *
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z position
	 * @param direction The direction relative to the given position the plant wants to be, typically its UP
	 * @param plant The plant that wants to check
	 * @return True to allow the plant to be planted/stay.
	 */
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{
		Block plant = plantable.getPlant(world, x, y + 1, z);
		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
		int meta = world.getBlockMetadata(x,y,z);

		if (plant == TFCBlocks.Cactus && this == TFCBlocks.Cactus)
		{
			return true;
		}

		if (plant == TFCBlocks.Reeds && this == TFCBlocks.Reeds)
		{
			return true;
		}

		if (plant instanceof BlockCustomLilyPad && ((BlockCustomLilyPad)plant).canThisPlantGrowOnThisBlock(this, meta))
		{
			return true;
		}

		switch (plantType)
		{
		case Desert: return TFC_Core.isSand(this);
		case Nether: return this == Blocks.soul_sand;
		case Crop:   return TFC_Core.isFarmland(this);
		case Cave:   return isSideSolid(world, x, y, z, ForgeDirection.UP);
		case Plains: return this == TFCBlocks.Grass || this == TFCBlocks.Grass2 || this == TFCBlocks.Dirt || this == TFCBlocks.Dirt2;
		case Water:  return world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0;
		case Beach:
			boolean isBeach = TFC_Core.isGround(this);
			boolean hasWater = (
					world.getBlock(x - 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x + 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x,     y, z - 1).getMaterial() == Material.water ||
					world.getBlock(x,     y, z + 1).getMaterial() == Material.water);
			return isBeach && hasWater;
		}

		return false;
	}

	public void setBlockforLava(World world, int par2, int par3, int par4, int typeOfLava)
	{
		DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(par2, par3, 2);
		int blockId = rockLayer3.data1;
		int meta = rockLayer3.data2;
		Random rand = new Random();
		boolean felsicLava = true;

		if(this == TFCBlocks.StoneIgIn && (meta == 2 || meta == 1))
			felsicLava = false;
		else if(this == TFCBlocks.StoneIgEx && (meta == 1 || meta == 2))
			felsicLava = false;
		if (typeOfLava == 0 || typeOfLava == 2) //non flowing rock
		{
			if(felsicLava)
			{
				if(rand.nextInt(10)==0 && typeOfLava == 0)
					world.setBlock(par2, par3, par4, Blocks.obsidian);
				else
				{
					world.setBlock(par2, par3, par4, TFCBlocks.StoneIgEx);
					world.setBlockMetadataWithNotify(par2, par3, par4, 0, 0);
				}
			}
			else
			{
				world.setBlock(par2, par3, par4, TFCBlocks.StoneIgEx);
				world.setBlockMetadataWithNotify(par2, par3, par4, 1, 0);
			}
		}
		else if (typeOfLava == 1)
		{
			world.setBlock(par2, par3, par4, TFCBlocks.StoneIgExCobble);
			if(felsicLava)
				world.setBlockMetadataWithNotify(par2, par3, par4, 0, 0);
			else
				world.setBlockMetadataWithNotify(par2, par3, par4, 1, 0);
		}
	}

	@Override
	public int getRenderType()
	{
		return 4; //return TFCBlocks.fluidRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		if (this.blockMaterial == Material.lava)
		{
			this.getFluid().setIcons(register.registerIcon("lava_still"), register.registerIcon("lava_flow"));
			icons = new IIcon[]{register.registerIcon("lava_still"), register.registerIcon("lava_flow")};
		}
		else
		{
			this.getFluid().setIcons(register.registerIcon("water_still"), register.registerIcon("water_flow"));
			icons = new IIcon[]{register.registerIcon("water_still"), register.registerIcon("water_flow")};
		}
	}

	@Override public Fluid getFluid(){ return fluidType; }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if((side & 2) > 0)
			return icons[1];
		return icons[0];

	}

}
