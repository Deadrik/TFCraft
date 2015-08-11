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

public abstract class BlockCustomLiquid extends BlockDynamicLiquid implements IFluidBlock
{
	protected Fluid fluidType;
	protected IIcon[] icons;

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
		//int blockId = rockLayer3.data1;
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

	@Override
	public boolean func_149807_p(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if(block == TFCBlocks.Thatch || block == TFCBlocks.Barrel || block == TFCBlocks.Vessel || block == TFCBlocks.BerryBush || 
				block == TFCBlocks.SmokeRack || block instanceof BlockCustomDoor || block == TFCBlocks.IngotPile)
			return false;
		return super.func_149807_p(world, x, y, z);
	}

}
