package com.bioxx.tfc.WorldGen;

import java.util.List;
import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TESeaWeed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCProvider extends WorldProvider
{
	public IRenderHandler skyprovider;

	@Override
	protected void registerWorldChunkManager()
	{
		worldChunkMgr = new TFCWorldChunkManager(this.worldObj);
		TFC_Climate.manager = (TFCWorldChunkManager) worldChunkMgr;
		TFC_Climate.worldObj = worldObj;
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new TFCChunkProviderGenerate(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		int y = worldObj.getTopSolidOrLiquidBlock(x, z)-1;
		Block b = worldObj.getBlock(x, y, z);
		return y > 144 && TFC_Core.isGrass(b);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMoonPhase(long par1)
	{
		return (int)(par1 / TFC_Time.dayLength) % 8;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
	{
		return worldObj.getSkyColorBody(cameraEntity, partialTicks);
	}

	@Override
	public float getCloudHeight()
	{
		return 256.0F;
	}

	@Override
	public TFCBiome getBiomeGenForCoords(int x, int z)
	{
		TFCBiome biome = TFCBiome.ocean;
		try
		{
			biome = (TFCBiome) worldObj.getBiomeGenForCoordsBody(x, z);
			/*if(canSnowAtTemp(x, 145, z))
				biome.temperature = 0;
			else
				biome.temperature = 0.21f;*/
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		return biome;
	}

	@Override
	public ChunkCoordinates getRandomizedSpawnPoint()
	{
		TFCWorldChunkManager chunkManager = (TFCWorldChunkManager) worldChunkMgr;
		List var3 = chunkManager.getBiomesToSpawnIn();
		long seed = worldObj.getWorldInfo().getSeed();
		Random rand = new Random(seed);

		ChunkPosition chunkcoordinates = null;
		int xOffset = 0;
		int xCoord = 0;
		int yCoord = 145;
		int zCoord = 10000;
		int startingZ = 3000 + rand.nextInt(12000);

		while(chunkcoordinates == null)
		{
			chunkcoordinates = chunkManager.findBiomePosition(xOffset, -startingZ, 64, var3, rand);
			if (chunkcoordinates != null)
			{
				xCoord = chunkcoordinates.chunkPosX;
				zCoord = chunkcoordinates.chunkPosZ;
			}
			else
			{
				xOffset += 512;
				//System.out.println("Unable to find spawn biome");
			}
		}

		int var9 = 0;
		while (!canCoordinateBeSpawn(xCoord, zCoord))
		{
			xCoord += rand.nextInt(64) - rand.nextInt(64);
			zCoord += rand.nextInt(64) - rand.nextInt(64);
			++var9;
			if (var9 == 1000)
				break;
		}

		WorldInfo info = worldObj.getWorldInfo();
		info.setSpawnPosition(xCoord, worldObj.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
		return new ChunkCoordinates(xCoord, worldObj.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
	}

	@Override
	public ChunkCoordinates getSpawnPoint()
	{
		WorldInfo info = worldObj.getWorldInfo();
		if(info.getSpawnZ() > -2999)
			return getRandomizedSpawnPoint();
		return super.getSpawnPoint();
		//return new ChunkCoordinates(info.getSpawnX(), info.getSpawnY(), info.getSpawnZ());
	}

	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	{
		TileEntity te = (worldObj.getTileEntity(x, y, z));
		Block id = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		if (TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0)
		{
			Material mat = worldObj.getBlock(x, y, z).getMaterial();
			boolean salty = TFC_Core.isSaltWaterIncludeIce(id, meta, mat);

			if(te!=null && te instanceof TESeaWeed)
			{
				//in case the block is salty sea grass, we don't want that to freeze when it's too warm
				salty = salty || (((TESeaWeed)te).getType() != 1 && ((TESeaWeed)te).getType() != 2);
			}

			if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= -2)
			{
				salty = false;
			}

			if((mat == Material.water || mat == Material.ice) && !salty)
			{
				if(id == TFCBlocks.FreshWaterStill && meta == 0/* || id == TFCBlocks.FreshWaterFlowing.blockID*/)
				{
					worldObj.setBlock(x, y, z, Blocks.ice, 1, 2);
				}
				else if(id == TFCBlocks.SaltWaterStill && meta == 0/* || id == Block.waterMoving.blockID*/)
				{
					worldObj.setBlock(x, y, z, Blocks.ice, 0, 2);
				}
				else if(id == TFCBlocks.SeaGrassStill || id == TFCBlocks.SeaGrassFlowing)
				{
					int type = -1;
					if(te !=null)
					{
						type = ((TESeaWeed)te).getType();
					}
					worldObj.setBlock(x, y, z, TFCBlocks.SeaGrassFrozen, type, 2);
					te = worldObj.getTileEntity(x, y, z);
					if(te!=null)
					{
						((TESeaWeed)te).setType(type);
					}
				}
			}
			return false;//(mat == Material.water) && !salty;
		}
		else
		{
			if(id == Blocks.ice)
			{
				if (worldObj.getBlock(x, y + 1, z) == Blocks.snow)
				{
					int m = worldObj.getBlockMetadata(x, y + 1, z);
					if (m > 0) {
						worldObj.setBlockMetadataWithNotify(x, y + 1, z, m - 1, 2);
					} else {
						worldObj.setBlockToAir(x, y + 1, z);
					}
				}
				else
				{
					if((meta & 1) == 0)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.SaltWaterStill, 0, 3);
					}
					else if((meta & 1) == 1)
					{
						worldObj.setBlock(x, y, z, TFCBlocks.FreshWaterStill, 0, 3);
					}
				}
			}
			else if(id == TFCBlocks.SeaGrassFrozen)
			{
				worldObj.setBlock(x, y, z, id, meta, 3);
				te = worldObj.getTileEntity(x, y, z);
				if(te!=null)
				{
					((TESeaWeed)te).setType(meta);
				}
			}
		}
		return false;
	}

	//We use this in place of the vanilla method, for the vanilla, it allows us to stop it from doing things we don't like.
	public boolean canBlockFreezeTFC(int x, int y, int z, boolean byWater)
	{
		if (TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0)
		{
			Material mat = worldObj.getBlock(x, y, z).getMaterial();
			Block id = worldObj.getBlock(x, y, z);
			int meta = worldObj.getBlockMetadata(x, y, z);
			boolean salty = TFC_Core.isSaltWaterIncludeIce(id, meta, mat);
			TileEntity te = worldObj.getTileEntity(x, y, z);
			if(te!=null && te instanceof TESeaWeed)
			{
				//in case the block is salty sea grass, we don't want that to freeze when it's too warm
				salty = salty || (((TESeaWeed)te).getType() != 1 && ((TESeaWeed)te).getType() != 2);
			}
			if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= -2)
				salty = false;
			return (mat == Material.water || mat == Material.ice) && !salty;
		}
		return false;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk)
	{
		return true;
	}

	@Override
	public boolean canSnowAt(int x, int y, int z, boolean checkLight)
	{
		if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0 &&
				Blocks.snow.canPlaceBlockAt(worldObj, x, y, z) &&
				worldObj.getBlock(x, y, z).getMaterial().isReplaceable())
		{
			return true;
		}
		return false;
	}

	private boolean canSnowAtTemp(int x, int y, int z)
	{
		if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0)
			return true;
		return false;
	}

	@Override
	public String getDimensionName()
	{
		return "DEFAULT";
	}

}
