package com.bioxx.tfc.Blocks.Terrain;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.ColorizerGrassTFC;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.Util.BlockMeta;
import com.bioxx.tfc.WorldGen.Generators.WorldGenGrowTrees;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrass extends net.minecraft.block.BlockGrass
{
	protected int textureOffset = 0;

	@SideOnly(Side.CLIENT)
	public IIcon GrassTopTexture;
	@SideOnly(Side.CLIENT)
	public IIcon iconSnowSide;
	@SideOnly(Side.CLIENT)
	public IIcon iconGrassSideOverlay;

	public BlockGrass()
	{
		super();
		this.setTickRandomly(true);
	}

	public BlockGrass(int texOff)
	{
		this();
		textureOffset = texOff;
	}

	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		GrassTopTexture = registerer.registerIcon(Reference.ModID + ":" + "GrassTop");

		iconSnowSide = registerer.registerIcon(Reference.ModID + ":" + "snow");
		iconGrassSideOverlay = registerer.registerIcon(Reference.ModID + ":" + "GrassSide");

		TFC_Textures.InvisibleTexture = registerer.registerIcon(Reference.ModID + ":" + "Invisible");
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return side == 1 ? GrassTopTexture : (side == 0 ? TFC_Textures.InvisibleTexture : iconGrassSideOverlay);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		if(world.isAirBlock(i,j-1,k)){
			int meta = world.getBlockMetadata(i,j,k);
			int y = j-1;
			while(!world.getBlock(i, y--, k).isOpaqueCube()){}
			world.setBlock(i, y, k, this, meta, 0x2);
			world.setBlockToAir(i, j, k);
		}
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public IIcon getIcon(IBlockAccess access, int xCoord, int yCoord, int zCoord, int side)
	{
		if (side == 1)
			return GrassTopTexture;
		else if (side == 0)
			return TFC_Textures.InvisibleTexture;
		else if (side == 2) //-Z
		{
			if(TFCOptions.enableBetterGrass == true && access.getBlock(xCoord, yCoord-1, zCoord-1).getMaterial() == Material.grass)
				return isSnow(access, xCoord, yCoord-1, zCoord-1) ? Blocks.snow.getBlockTextureFromSide(0) : GrassTopTexture;
		}
		else if (side == 3) //+Z
		{
			if(TFCOptions.enableBetterGrass == true && access.getBlock(xCoord, yCoord-1, zCoord+1).getMaterial() == Material.grass)
				return isSnow(access, xCoord, yCoord-1, zCoord+1) ? Blocks.snow.getBlockTextureFromSide(0) : GrassTopTexture;
		}
		else if (side == 4) //-X
		{
			if(TFCOptions.enableBetterGrass == true && access.getBlock(xCoord-1, yCoord-1, zCoord).getMaterial() == Material.grass)
				return isSnow(access, xCoord-1, yCoord-1, zCoord) ? Blocks.snow.getBlockTextureFromSide(0) : GrassTopTexture;
		}
		else if (side == 5) //+X
		{
			if(TFCOptions.enableBetterGrass == true && access.getBlock(xCoord+1, yCoord-1, zCoord).getMaterial() == Material.grass)
				return isSnow(access, xCoord+1, yCoord-1, zCoord) ? Blocks.snow.getBlockTextureFromSide(0) : GrassTopTexture;
		}

		return iconGrassSideOverlay;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
	{
		if(side == 0)
			return false;
		else
			return super.shouldSideBeRendered(access, x, y, z, side);
	}

	private boolean isSnow(IBlockAccess access, int i, int j, int k)
	{
		Material material = access.getBlock(i, j, k).getMaterial();
		return material == Material.snow || material == Material.craftedSnow;
	}


	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return TerraFirmaCraft.proxy.grassColorMultiplier(par1IBlockAccess, par2, par3, par4);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.grassRenderId;
	}

	@Override
	public int getBlockColor()
	{
		double var1 = 0.5D;
		double var3 = 1.0D;
		return ColorizerGrassTFC.getGrassColor(var1, var3);
	}

	/**
	 * Returns the color this block should be rendered. Used by leaves.
	 */
	@Override
	public int getRenderColor(int par1)
	{
		return this.getBlockColor();
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!world.isRemote)
		{
			if(world.getBlock(i, j+1, k) == Blocks.snow)
			{
				world.setBlock(i, j, k, TFC_Core.getTypeForDryGrassFromSoil(world.getBlock(i, j, k)), world.getBlockMetadata(i, j, k), 0x2);
			}
			else if (world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlock(i, j + 1, k).getLightOpacity() > 2)
			{
				world.setBlock(i, j, k, TFC_Core.getTypeForDirt(world.getBlockMetadata(i, j, k) + textureOffset), world.getBlockMetadata(i, j, k), 0x2);
			}
			else if (world.getBlockLightValue(i, j + 1, k) >= 4)
			{
				for (int var6 = 0; var6 < 4; ++var6)
				{
					int x = i + rand.nextInt(3) - 1;
					int y = j + rand.nextInt(5) - 3;
					int z = k + rand.nextInt(3) - 1;

					float rain = TFC_Climate.getRainfall(x, y + 1, z);

					Block id = world.getBlock(x, y, z);
					int meta = world.getBlockMetadata(x, y, z);

					if (TFC_Core.isDirt(id) && rand.nextInt(10) == 0 && world.getBlock(x, y + 1, z).getMaterial() != Material.water)
						world.setBlock(x, y, z, TFC_Core.getTypeForGrassWithRainByBlock(id, rain), meta, 0x2);
					else if (TFC_Core.isClay(id) && rand.nextInt(10) == 0 && world.getBlock(x, y + 1, z).getMaterial() != Material.water)
						world.setBlock(x, y, z, TFC_Core.getTypeForClayGrass(meta), meta, 0x2);
					else if (TFC_Core.isPeat(id) && rand.nextInt(10) == 0 && world.getBlock(x, y + 1, z).getMaterial() != Material.water)
						world.setBlock(x, y, z, TFCBlocks.PeatGrass);
				}

				float rain = TFC_Climate.getRainfall(i, j + 1, k);
				float temp = TFC_Climate.getHeightAdjustedTemp(i, j+1, k);
				Block id = world.getBlock(i, j, k);

				if (TFC_Core.isGrass(id) && !TFC_Core.isDryGrass(id) && world.getBlock(i, j + 1, k).getMaterial() != Material.water && world.isAirBlock(i, j + 1, k))
				{
					if(rand.nextInt((int) ((16800-rain)/4)) == 0 && temp > 20)
						world.setBlock(i, j + 1, k, TFCBlocks.TallGrass, (world.rand.nextInt(30) == 0 ? 1 : 0), 0x2); // 1/30 chance to spawn fern
					else if (rand.nextInt(15000) == 0 && temp > 20 && world.canBlockSeeTheSky(i, j + 1, k))
						new WorldGenGrowTrees().generate(world, rand, i, j, k);
				}

				boolean nearWater = false;

				for(int y = 0; y < 2 && !nearWater; y++)
				{
					for(int x = -4; x < 5 && !nearWater; x++)
					{
						for(int z = -4; z < 5 && !nearWater; z++)
						{
							if(world.getBlock(i+x, j-y, k+z).getMaterial() == Material.water)
								nearWater = true;
						}
					}
				}

				BlockMeta rock1 = TFC_Climate.getRockLayer(i, j, k, 0);
				if(TFC_Core.isGrass(id) && !TFC_Core.isDryGrass(id) && !nearWater && rain < 500)
				{
					int meta = TFC_Core.getSoilMetaFromStone(rock1.block, rock1.meta);
					world.setBlock(i, j, k, TFC_Core.getTypeForDryGrass(meta), meta, 2);
				}
				else if(TFC_Core.isGrass(id) && TFC_Core.isDryGrass(id) && (nearWater || rain>=500) && world.getBlock(i, j+1, k) != Blocks.snow)
				{
					int meta = TFC_Core.getSoilMetaFromStone(rock1.block, rock1.meta);
					world.setBlock(i, j, k, TFC_Core.getTypeForGrass(meta), meta, 2);
				}
			}

			//            if(!(this.blockID >= 2080 && this.blockID < 2088))
			//            {
			//            	boolean hasBeenSet = false;
			//            	int meta = world.getBlockMetadata(i, j, k);
			//            	for(int x = i-1; x <= i+1 && !hasBeenSet; x++)
			//            	{
			//            		for(int z = k-1; z <= k+1 && !hasBeenSet; z++)
			//                	{
			//            			if(!world.isBlockNormalCube(x, j, z))
			//            			{
			//            				hasBeenSet = true;
			//            				world.setBlockAndMetadataWithNotify(i, j, k, TFC_Core.getTypeForRaisedGrass(meta), meta);
			//            			}
			//                	}
			//            	}
			//            }

			world.markBlockForUpdate(i, j, k);
		}
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity par5Entity) 
	{
		if (!world.isRemote && this != TFCBlocks.ClayGrass2 && this != TFCBlocks.ClayGrass && this != TFCBlocks.PeatGrass)
		{
			Random R = new Random();
			if(!BlockCollapsable.isNearSupport(world, i, j, k, 4, 0) && BlockDirt.canFallBelow(world, i, j - 1, k) && R.nextInt(10) == 0)
			{
				int meta = world.getBlockMetadata(i, j, k);
				world.setBlock(i, j, k, TFC_Core.getTypeForDirtFromGrass(this), meta, 0x2);
			}
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return TFC_Core.getTypeForDirtFromGrass(this).getItemDropped(par1, par2Random, par3);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{
		if(!world.blockExists(i, j-1, k))
		{
			int meta = world.getBlockMetadata(i, j, k);
			world.setBlock(i, j, k, TFC_Core.getTypeForDirtFromGrass(this), meta, 0x2);
		}
	}
}
