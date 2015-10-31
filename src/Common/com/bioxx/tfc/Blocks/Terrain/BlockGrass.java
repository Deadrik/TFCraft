package com.bioxx.tfc.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.*;
import com.bioxx.tfc.WorldGen.Generators.WorldGenLooseRocks;
import com.bioxx.tfc.WorldGen.Generators.WorldGenSaplings;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

public class BlockGrass extends BlockTerra
{
	protected int textureOffset;

	@SideOnly(Side.CLIENT)
	public IIcon grassTopTexture;
	@SideOnly(Side.CLIENT)
	public IIcon iconSnowSide;
	@SideOnly(Side.CLIENT)
	public IIcon iconGrassSideOverlay;

	public BlockGrass()
	{
		super(Material.grass);
		this.setTickRandomly(true);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	public BlockGrass(int texOff)
	{
		this();
		textureOffset = texOff;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// Change to false if this block should not be added to the creative tab
		Boolean addToCreative = true;

		if(addToCreative)
		{
			int count;
			if(textureOffset == 0) count = 16;
			else count = Global.STONE_ALL.length - 16;

			for(int i = 0; i < count; i++)
				list.add(new ItemStack(item, 1, i));
		}
	}

	public static IIcon getIconSideOverlay()
	{
		return ((BlockGrass)TFCBlocks.grass).iconGrassSideOverlay;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		grassTopTexture = registerer.registerIcon(Reference.MOD_ID + ":" + "GrassTop");

		iconSnowSide = registerer.registerIcon(Reference.MOD_ID + ":" + "snow");
		iconGrassSideOverlay = registerer.registerIcon(Reference.MOD_ID + ":" + "GrassSide");

		TFC_Textures.invisibleTexture = registerer.registerIcon(Reference.MOD_ID + ":" + "Invisible");
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1)
			return grassTopTexture;
		else if (side == 0)
			return TFC_Textures.invisibleTexture;
		else
			return iconGrassSideOverlay;
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if (side == 1)
			return grassTopTexture;
		else if (side == 0)
			return TFC_Textures.invisibleTexture;
		else if (side == 2) //-Z
		{
			if (TFCOptions.enableBetterGrass && TFC_Core.isGrass(access.getBlock(x, y - 1, z - 1)))
				return isSnow(access, x, y - 1, z - 1) ? Blocks.snow.getBlockTextureFromSide(0) : grassTopTexture;
		}
		else if (side == 3) //+Z
		{
			if (TFCOptions.enableBetterGrass && TFC_Core.isGrass(access.getBlock(x, y - 1, z + 1)))
				return isSnow(access, x, y - 1, z + 1) ? Blocks.snow.getBlockTextureFromSide(0) : grassTopTexture;
		}
		else if (side == 4) //-X
		{
			if (TFCOptions.enableBetterGrass && TFC_Core.isGrass(access.getBlock(x - 1, y - 1, z)))
				return isSnow(access, x - 1, y - 1, z) ? Blocks.snow.getBlockTextureFromSide(0) : grassTopTexture;
		}
		else if (side == 5) //+X
		{
			if (TFCOptions.enableBetterGrass && TFC_Core.isGrass(access.getBlock(x + 1, y - 1, z)))
				return isSnow(access, x + 1, y - 1, z) ? Blocks.snow.getBlockTextureFromSide(0) : grassTopTexture;
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

	private boolean isSnow(IBlockAccess access, int x, int y, int z)
	{
		Material material = access.getBlock(x, y, z).getMaterial();
		return material == Material.snow || material == Material.craftedSnow;
	}


	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return TerraFirmaCraft.proxy.grassColorMultiplier(bAccess, x, y, z);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.grassRenderId;
	}

	@Override
	public int getBlockColor()
	{
		double d0 = 0.5D;
		double d1 = 1.0D;
		return ColorizerGrassTFC.getGrassColor(d0, d1);
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
			int meta = world.getBlockMetadata(i, j, k);
			if(world.getBlock(i, j + 1, k) == Blocks.snow && !TFC_Core.isDryGrass(this))
			{
				world.setBlock(i, j, k, TFC_Core.getTypeForDryGrassFromSoil(this), meta, 0x2);
			}
			else if (world.getBlock(i, j+1, k).isSideSolid(world, i, j+1, k, ForgeDirection.DOWN))
			{
				world.setBlock(i, j, k, TFC_Core.getTypeForDirtFromGrass(this), meta, 0x2);
			}
			else if (world.canBlockSeeTheSky(i, j + 1, k))
			{
				spreadGrass(world, i, j, k, rand);

				float rain = TFC_Climate.getRainfall(world, i, j + 1, k);
				float temp = TFC_Climate.getHeightAdjustedTemp(world, i, j+1, k);

				if (TFC_Core.isGrass(this)  && world.getBlock(i, j + 1, k).getMaterial() != Material.water)
				{
					int chunkX = (int)Math.floor(i) >> 4;
					int chunkZ = (int)Math.floor(k) >> 4;

					if (TFC_Core.getCDM(world) != null)
					{
						ChunkData data = TFC_Core.getCDM(world).getData(chunkX, chunkZ);
						if (data == null || data.getSpawnProtectionWithUpdate() <= 1320)
						{
							if(temp > 20 && !TFC_Core.isDryGrass(this) && world.isAirBlock(i, j + 1, k) )
							{
								if (rand.nextInt((int) ((16800-rain)/4)) == 0)
									world.setBlock(i, j + 1, k, TFCBlocks.tallGrass, (world.rand.nextInt(30) == 0 ? 1 : 0), 0x2); // 1/30 chance to spawn fern	
								else if (rand.nextInt(15000) == 0)
									new WorldGenSaplings().generate(world, rand, i, j, k);	
							}
							else if (rand.nextInt(15000) == 0 && !WorldGenLooseRocks.rocksNearby(world, i, j, k) && temp < 15)
								new WorldGenLooseRocks().generateRocks(world, rand, i, j, k);
							else if (rand.nextInt(15000) == 0 && temp < 15)
								new WorldGenLooseRocks().generateSticks(world, rand, i, j, k);

						}
						
					}
					
				}

				boolean nearWater = false;

				for(int y = 0; y < 2 && !nearWater; y++)
				{
					for(int x = -4; x < 5 && !nearWater; x++)
					{
						for(int z = -4; z < 5 && !nearWater; z++)
						{
							if (j < 250 && j > Global.SEALEVEL && world.blockExists(i + x, j - y, k + z) && world.getBlock(i + x, j - y, k + z).getMaterial() == Material.water)
								nearWater = true;
						}
					}
				}

				if(TFC_Core.isGrass(this) && !TFC_Core.isDryGrass(this) && !nearWater && rain < 500)
				{
					world.setBlock(i, j, k, TFC_Core.getTypeForDryGrassFromSoil(this), meta, 2);
				}
				else if(TFC_Core.isGrass(this) && TFC_Core.isDryGrass(this) && (nearWater || rain>=500) && world.getBlock(i, j+1, k) != Blocks.snow)
				{
					world.setBlock(i, j, k, TFC_Core.getTypeForGrassFromSoil(this), meta, 2);
				}
			}

			world.markBlockForUpdate(i, j, k);
		}
	}

	public void spreadGrass(World world, int i, int j, int k, Random rand)
	{
		for (int var6 = 0; var6 < 4; ++var6)
		{
			int x = i + rand.nextInt(3) - 1;
			int z = k + rand.nextInt(3) - 1;
			int y = world.getTopSolidOrLiquidBlock(x, z) - 1;

			if (world.blockExists(x, y, z) && world.getBlock(x, y + 1, z).getMaterial() != Material.water)
			{
				float rain = TFC_Climate.getRainfall(world, x, y, z);

				Block id = world.getBlock(x, y, z);
				int meta = world.getBlockMetadata(x, y, z);

				//Spread to other blocks
				if (TFC_Core.isDirt(id) && rand.nextInt(10) == 0)
					world.setBlock(x, y, z, TFC_Core.getTypeForGrassWithRainByBlock(id, rain), meta, 0x2);
				else if (TFC_Core.isClay(id) && rand.nextInt(10) == 0)
					world.setBlock(x, y, z, TFC_Core.getTypeForClayGrass(meta), meta, 0x2);
				else if (TFC_Core.isPeat(id) && rand.nextInt(10) == 0)
					world.setBlock(x, y, z, TFCBlocks.peatGrass, 0, 0x2);
			}
		}
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity)
	{
		if (!world.isRemote && this != TFCBlocks.clayGrass2 && this != TFCBlocks.clayGrass && this != TFCBlocks.peatGrass)
		{
			Random r = new Random();
			if (BlockCollapsible.canFallBelow(world, x, y - 1, z) && r.nextInt(10) == 0 && !BlockCollapsible.isNearSupport(world, x, y, z, 4, 0))
			{
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y, z, TFC_Core.getTypeForDirtFromGrass(this), meta, 0x2);
			}
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return TFC_Core.getTypeForDirtFromGrass(this).getItemDropped(metadata, rand, fortune);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(world.isAirBlock(x, y - 1, z))
		{
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlock(x, y, z, TFC_Core.getTypeForDirtFromGrass(this), meta, 0x2);
			world.scheduleBlockUpdate(x, y, z, TFC_Core.getTypeForDirtFromGrass(this), 5);
		}
	}
}
