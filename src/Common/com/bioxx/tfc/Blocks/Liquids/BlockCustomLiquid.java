package com.bioxx.tfc.Blocks.Liquids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomDoor;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLilyPad;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

public abstract class BlockCustomLiquid extends BlockDynamicLiquid implements IFluidBlock
{
	protected Fluid fluidType;
	protected IIcon[] icons;
	protected int sourceBlockCount;
	protected boolean[] canFlowDirections = new boolean[4];
	protected int[] flowPriorities = new int[4];

	public BlockCustomLiquid(Fluid fluid, Material mat)
	{
		super(mat);
		float f = 0.0F;
		float f1 = 0.0F;
		this.setBlockBounds(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
		this.setTickRandomly(true);
		fluidType = fluid;
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
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e) 
	{
		if (this.blockMaterial == Material.lava)
		{
			if(e instanceof EntityItem)
			{
				e.setFire(15);
			}
		}
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
		//super.onBlockAdded(world, x, y, z);
		if (world.getBlock(x, y, z) == this)
		{
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
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
		//super.onNeighborBlockChange(world, x, y, z, block);
		this.checkForHarden(world, x, y, z);
	}

	/**
	 * Forces lava to check to see if it is colliding with water, and then decide what it should harden to.
	 */
	private void checkForHarden(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z) == this)
			if (this.blockMaterial == Material.lava)
			{
				boolean flag = false;

				if (flag || world.getBlock(x, y, z - 1).getMaterial() == Material.water)
					flag = true;

				if (flag || world.getBlock(x, y, z + 1).getMaterial() == Material.water)
					flag = true;

				if (flag || world.getBlock(x - 1, y, z).getMaterial() == Material.water)
					flag = true;

				if (flag || world.getBlock(x + 1, y, z).getMaterial() == Material.water)
					flag = true;

				if (flag || world.getBlock(x, y + 1, z).getMaterial() == Material.water)
					flag = true;

				if (flag)
				{
					int l = world.getBlockMetadata(x, y, z);

					if (l == 0)
						setBlockforLava(world, x,  y,  z,0);
					else if (l <= 4)
						setBlockforLava(world, x,  y,  z,1);

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
		if (plant == TFCBlocks.cactus && this == TFCBlocks.cactus)
		{
			return true;
		}
		if (plant == TFCBlocks.reeds && this == TFCBlocks.reeds)
		{
			return true;
		}

		int meta = world.getBlockMetadata(x, y, z);
		if (plant instanceof BlockCustomLilyPad && ((BlockCustomLilyPad)plant).canThisPlantGrowOnThisBlock(this, meta))
		{
			return true;
		}

		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
		switch (plantType)
		{
		case Desert: return TFC_Core.isSand(this);
		case Nether: return this == Blocks.soul_sand;
		case Crop:   return TFC_Core.isFarmland(this);
		case Cave:   return isSideSolid(world, x, y, z, ForgeDirection.UP);
		case Plains: return this == TFCBlocks.grass || this == TFCBlocks.grass2 || this == TFCBlocks.dirt || this == TFCBlocks.dirt2;
		case Water:  return world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0;
		case Beach:
			boolean isBeach = TFC_Core.isGround(this);
			boolean hasWater = 
					world.getBlock(x - 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x + 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x,     y, z - 1).getMaterial() == Material.water ||
					world.getBlock(x,     y, z + 1).getMaterial() == Material.water;
			return isBeach && hasWater;
		}

		return false;
	}

	public void setBlockforLava(World world, int x, int y, int z, int typeOfLava)
	{
		DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(x, y, 2);
		//int blockId = rockLayer3.data1;
		int meta = rockLayer3.data2;
		Random rand = new Random();
		boolean felsicLava = true;

		if(this == TFCBlocks.stoneIgIn && (meta == 2 || meta == 1))
			felsicLava = false;
		else if(this == TFCBlocks.stoneIgEx && (meta == 1 || meta == 2))
			felsicLava = false;
		if (typeOfLava == 0 || typeOfLava == 2) //non flowing rock
		{
			if(felsicLava)
			{
				if(rand.nextInt(10)==0 && typeOfLava == 0)
					world.setBlock(x, y, z, Blocks.obsidian);
				else
				{
					world.setBlock(x, y, z, TFCBlocks.stoneIgEx);
					world.setBlockMetadataWithNotify(x, y, z, 0, 0);
				}
			}
			else
			{
				world.setBlock(x, y, z, TFCBlocks.stoneIgEx);
				world.setBlockMetadataWithNotify(x, y, z, 1, 0);
			}
		}
		else if (typeOfLava == 1)
		{
			world.setBlock(x, y, z, TFCBlocks.stoneIgExCobble);
			if(felsicLava)
				world.setBlockMetadataWithNotify(x, y, z, 0, 0);
			else
				world.setBlockMetadataWithNotify(x, y, z, 1, 0);
		}
	}

	@Override
	public int tickRate(World world)
	{
		if(this.getMaterial() == Material.lava)
			return 10;
		return 3;
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		if (this.blockMaterial == Material.lava)
		{
			this.getFluid().setIcons(register.registerIcon("lava_still"), register.registerIcon("lava_flow"));
		}
		else
		{
			this.getFluid().setIcons(register.registerIcon("water_still"), register.registerIcon("water_flow"));
		}
		icons = new IIcon[]{getFluid().getStillIcon(), getFluid().getFlowingIcon()};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side != 0 && side != 1 ? this.icons[1] : this.icons[0];
	}

	@Override public Fluid getFluid(){ return fluidType; }

	@Override
	public FluidStack drain(World world, int x, int y, int z, boolean doDrain) {
		return null;
	}
	@Override
	public boolean canDrain(World world, int x, int y, int z) {
		return false;
	}
	@Override
	public float getFilledPercentage(World world, int x, int y, int z) {
		return 1;
	}

	public boolean canStay(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if(block == TFCBlocks.thatch || block == TFCBlocks.barrel || block == TFCBlocks.vessel || block == TFCBlocks.berryBush || 
				block == TFCBlocks.smokeRack || block instanceof BlockCustomDoor || block == TFCBlocks.ingotPile)
			return false;
		else
			return super.func_149807_p(world, x, y, z);
	}

	protected int getLiquidHeight(World world, int x, int y, int z, int count)
	{
		int liquidHeight = this.getMetaData(world, x, y, z);
	
		// If the block materials don't match.
		if (liquidHeight < 0)
		{
			return count;
		}
		else
		{
			//Source Block
			if (liquidHeight == 0)
				this.sourceBlockCount++;
			else if (liquidHeight >= 8)
				liquidHeight = 0;
	
			return count >= 0 && liquidHeight >= count ? count : liquidHeight;
		}
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		int meta = this.getMetaData(world, x, y, z);
		byte b0 = 1;
	
		if (this.blockMaterial == Material.lava && !world.provider.isHellWorld)
		{
			b0 = 2;
		}
	
		boolean flag = true;
		int tickRate = this.tickRate(world);
		int newHeight;
	
		if (meta > 0)
		{
			this.sourceBlockCount = 0;
			int liquidHeight = this.getLiquidHeight(world, x - 1, y, z, -100);
			liquidHeight = this.getLiquidHeight(world, x + 1, y, z, liquidHeight);
			liquidHeight = this.getLiquidHeight(world, x, y, z - 1, liquidHeight);
			liquidHeight = this.getLiquidHeight(world, x, y, z + 1, liquidHeight);
			newHeight = liquidHeight + b0;
	
			if (newHeight >= 8 || liquidHeight < 0)
			{
				newHeight = -1;
			}
	
			// Same Liquid Above
			if (this.getMetaData(world, x, y + 1, z) >= 0)
			{
				int metaAbove = this.getMetaData(world, x, y + 1, z);
	
				if (metaAbove >= 8)
				{
					newHeight = metaAbove;
				}
				else
				{
					newHeight = metaAbove + 8;
				}
			}
	
			if (this.sourceBlockCount >= 2 && this.blockMaterial == Material.water && !TFCOptions.enableFiniteWater)
			{
				// Solid block below, or source block of same liquid below.
				if (world.getBlock(x, y - 1, z).getMaterial().isSolid() ||
					world.getBlock(x, y - 1, z).getMaterial() == this.blockMaterial && world.getBlockMetadata(x, y - 1, z) == 0)
				{
					newHeight = 0;
				}
			}
	
			if (this.blockMaterial == Material.lava && meta < 8 && newHeight < 8 && newHeight > meta && rand.nextInt(4) != 0)
			{
				tickRate *= 4;
			}
	
			if (newHeight == meta)
			{
				if (flag)
				{
					this.convertFlowingToSource(world, x, y, z);
				}
			}
			else
			{
				meta = newHeight;
	
				if (newHeight < 0)
				{
					world.setBlockToAir(x, y, z);
				}
				else
				{
					world.setBlockMetadataWithNotify(x, y, z, newHeight, 2);
					world.scheduleBlockUpdate(x, y, z, this, tickRate);
					world.notifyBlocksOfNeighborChange(x, y, z, this);
				}
			}
		}
		else
		{
			this.convertFlowingToSource(world, x, y, z);
		}
	
		if (this.canReplace(world, x, y - 1, z)) // Can flow directly down
		{
			if (this.blockMaterial == Material.lava && world.getBlock(x, y - 1, z).getMaterial() == Material.water)
			{
				setBlockforLava(world, x, y - 1, z, 2);
				this.func_149799_m(world, x, y - 1, z); // Sizzling Sound & Particles
				return;
			}
	
			if (meta >= 8)
			{
				this.flow(world, x, y - 1, z, meta);
			}
			else
			{
				this.flow(world, x, y - 1, z, meta + 8);
			}
		}
		else if (meta >= 0 && (meta == 0 || this.canStay(world, x, y - 1, z))) // Can't flow down
		{
			newHeight = meta + b0;
	
			if (meta >= 8)
			{
				newHeight = 1;
			}
	
			if (newHeight >= 8)
			{
				return;
			}

			boolean[] flowDirections = this.getFlowDirections(world, x, y, z);
			if (flowDirections[0])
			{
				this.flow(world, x - 1, y, z, newHeight);
			}
	
			if (flowDirections[1])
			{
				this.flow(world, x + 1, y, z, newHeight);
			}
	
			if (flowDirections[2])
			{
				this.flow(world, x, y, z - 1, newHeight);
			}
	
			if (flowDirections[3])
			{
				this.flow(world, x, y, z + 1, newHeight);
			}
		}
	}

	private void flow(World world, int x, int y, int z, int meta)
	{
		if (this.canReplace(world, x, y, z))
		{
			Block block = world.getBlock(x, y, z);

			if (this.blockMaterial == Material.lava)
			{
				setBlockforLava(world, x, y, z, 0);
				this.func_149799_m(world, x, y - 1, z); // Sizzling Sound & Particles
			}
			else
			{
				block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			}

			world.setBlock(x, y, z, this, meta, 3);
		}
	}

	private boolean canReplace(World world, int x, int y, int z)
	{
		Material material = world.getBlock(x, y, z).getMaterial();
		if (material == this.blockMaterial || material == Material.lava)
			return false;
		else
			return !this.canStay(world, x, y, z);
	}

	private boolean[] getFlowDirections(World world, int x, int y, int z)
	{
		int side;
		int newX;

		for (side = 0; side < 4; ++side)
		{
			this.flowPriorities[side] = 1000;
			newX = x;
			int newZ = z;

			if (side == 0)
				newX = x - 1;
			else if (side == 1)
				++newX;
			else if (side == 2)
				newZ = z - 1;
			else if (side == 3)
				++newZ;

			if (!this.canStay(world, newX, y, newZ) && (world.getBlock(newX, y, newZ).getMaterial() != this.blockMaterial || world.getBlockMetadata(newX, y, newZ) != 0))
			{
				if (this.canStay(world, newX, y - 1, newZ)) // Can't flow down
				{
					this.flowPriorities[side] = this.getFlowPriorities(world, newX, y, newZ, 1, side);
				}
				else
				{
					this.flowPriorities[side] = 0;
				}
			}
		}

		side = this.flowPriorities[0];

		for (newX = 1; newX < 4; ++newX)
		{
			if (this.flowPriorities[newX] < side)
			{
				side = this.flowPriorities[newX];
			}
		}

		for (newX = 0; newX < 4; ++newX)
		{
			this.canFlowDirections[newX] = this.flowPriorities[newX] == side;
		}

		return this.canFlowDirections;
	}

	private void convertFlowingToSource(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlock(x, y, z, Block.getBlockById(Block.getIdFromBlock(this) + 1), meta, 2);
	}

	private int getFlowPriorities(World world, int x, int y, int z, int distance, int side)
	{
		int priority = 1000;

		for (int side2 = 0; side2 < 4; ++side2)
		{
			if ((side2 != 0 || side != 1) && (side2 != 1 || side != 0) && (side2 != 2 || side != 3) && (side2 != 3 || side != 2))
			{
				int xCoord = x;
				int zCoord = z;

				if (side2 == 0)
					xCoord = x - 1;
				else if (side2 == 1)
					++xCoord;
				else if (side2 == 2)
					zCoord = z - 1;
				else if (side2 == 3)
					++zCoord;

				if (!this.canStay(world, xCoord, y, zCoord) && (world.getBlock(xCoord, y, zCoord).getMaterial() != this.blockMaterial || world.getBlockMetadata(xCoord, y, zCoord) != 0))
				{
					if (!this.canStay(world, xCoord, y - 1, zCoord))
					{
						return distance;
					}

					if (distance < 4)
					{
						int newDistance = this.getFlowPriorities(world, xCoord, y, zCoord, distance + 1, side2);

						if (newDistance < priority)
						{
							priority = newDistance;
						}
					}
				}
			}
		}

		return priority;
	}

	protected int getMetaData(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z).getMaterial() == this.blockMaterial ? world.getBlockMetadata(x, y, z) : -1;
	}
}
