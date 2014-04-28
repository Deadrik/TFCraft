package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.TFCProvider;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomIce extends BlockIce
{
	IIcon seaIce;

	public BlockCustomIce()
	{
		super();
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		Material var7 = par1World.getBlock(par3, par4 - 1, par5).getMaterial();

		if (var7.blocksMovement() || var7.isLiquid())
			par1World.setBlock(par3, par4, par5, getBlockMelt(par1World, par3, par4, par5, true), 0, 2);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block id, int l)
	{
		/*
		if(id == this.blockID){
			if(world.getBlockId(i,j,k)==Block.waterStill.blockID && l != 0){
				world.setBlock(i,j,k,TFCBlocks.FreshWaterStill.blockID);
			}
			else if( world.getBlockId(i,j,k)==Block.waterMoving.blockID && l != 0){
				world.setBlock(i,j,k,TFCBlocks.FreshWaterFlowing.blockID);
			}
			else if(world.getBlockId(i,j,k)==TFCBlocks.FreshWaterStill.blockID && l != 1){
				world.setBlock(i,j,k,Block.waterStill.blockID);
			}
			else if( world.getBlockId(i,j,k)==TFCBlocks.FreshWaterFlowing.blockID && l != 1){
				world.setBlock(i,j,k,Block.waterMoving.blockID);
			}
		}*/
		super.breakBlock(world, i, j, k, id, l);
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
			return true;

		if (plant == TFCBlocks.Reeds && this == TFCBlocks.Reeds)
			return true;

		if (plantable instanceof BlockCustomLilyPad && ((BlockCustomLilyPad)plant).canThisPlantGrowOnThisBlock(this, meta))
			return true;

		switch (plantType)
		{
		case Desert: return TFC_Core.isSand(this);
		case Nether: return this == Blocks.soul_sand;
		case Crop:   return TFC_Core.isFarmland(this);
		case Cave:   return isSideSolid(world, x, y, z, ForgeDirection.UP);
		case Plains: return this == TFCBlocks.Grass || this == TFCBlocks.Grass2 || this == TFCBlocks.Dirt || this == TFCBlocks.Dirt2;
		case Water:  return world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0;
		case Beach:
			boolean isBeach = TFC_Core.isDirt(this) || TFC_Core.isSand(this);
			boolean hasWater = (
					world.getBlock(x - 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x + 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x,     y, z - 1).getMaterial() == Material.water ||
					world.getBlock(x,     y, z + 1).getMaterial() == Material.water);
			return isBeach && hasWater;
		}
		return false;
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x,y,z);
		if (meta == 0)
			return 9;//sea ice is cloudy
		return this.getLightOpacity();
	}

	protected Block getBlockMelt(World world, int i, int j, int k, boolean moving)
	{
		int meta = world.getBlockMetadata(i,j,k);
		Block block = world.getBlock(i,j,k);

		if(block != this)
			return block;

		switch(meta){
		case 0: return moving ? TFCBlocks.SaltWaterFlowing : TFCBlocks.SaltWaterStill;
		case 1: return moving ? TFCBlocks.FreshWaterFlowing : TFCBlocks.FreshWaterStill;
		default: return TFCBlocks.SaltWaterStill;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		seaIce = registerer.registerIcon(Reference.ModID + ":seaIce");
		super.registerBlockIcons(registerer);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		if(par2 == 0)
			return this.seaIce;
		else
			return super.getIcon(par1, par2);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if((world.provider) instanceof TFCProvider && !world.isRemote && world.getBlock(i, j, k) == this)
		{
			if(world.getBlockMetadata(i, j, k) == 1)
			{
				if(j== 144 && scanForOcean(world, i, j, k))
				{
					world.setBlockMetadataWithNotify(i, j, k, 0, 3);
				}
			}
			if (!((TFCProvider)(world.provider)).canBlockFreeze(i, j, k, false))
			{
				/*if (world.getBlock(i, j+1, k) == Blocks.snow)
				{
					int meta = world.getBlockMetadata(i, j+1, k);
					if (meta > 0)
						world.setBlockMetadataWithNotify(i, j+1, k, meta-1, 2);
					else
						world.setBlockToAir(i, j+1, k);
				}
				else
				{
					this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
					world.setBlock(i, j, k, getBlockMelt(world,i,j,k,false), 0, 2);
				}
				this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				if(j > 143)
					world.setBlock(i, j, k, getBlockMelt(world,i,j,k,true), 0, 2);
				else
					world.setBlock(i, j, k, getBlockMelt(world,i,j,k,false), 0, 2);
				*/
			}
		}
	}

	private boolean scanForOcean(World world, int i, int j, int k)
	{
		if(world.getBiomeGenForCoords(i + 5, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i + 10, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i + 20, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i - 5, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i - 10, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i - 20, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k + 5).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k + 10).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k + 20).biomeID == TFCBiome.ocean.biomeID||
				world.getBiomeGenForCoords(i, k - 5).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k - 10).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k - 20).biomeID == TFCBiome.ocean.biomeID)
		{
			return true;
		}
		return false;
	}
}
