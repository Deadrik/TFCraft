package com.bioxx.tfc.WorldGen;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.storage.WorldInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Climate;

public class TFCProviderHell extends TFCProvider
{
	@Override
	protected void registerWorldChunkManager()
	{
		worldChunkMgr = new TFCWorldChunkManagerHell(TFCBiome.HELL, 1F, 1F, this.worldObj);
		if(worldObj.isRemote)
			TFC_Climate.worldPair.put(worldObj, new WorldCacheManager(worldObj));
		else
			TFC_Climate.worldPair.put(worldObj, new WorldCacheManager(worldObj));
	}

	@Override
	protected void generateLightBrightnessTable()
	{
		float var1 = 0.1F;
		for (int var2 = 0; var2 <= 15; ++var2)
		{
			float var3 = 1.0F - var2 / 15.0F;
			this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderHell(this.worldObj, this.worldObj.getSeed());
	}

	@Override
	public ChunkCoordinates getSpawnPoint()
	{
		WorldInfo info = worldObj.getWorldInfo();
		return new ChunkCoordinates(info.getSpawnX(), info.getSpawnY(), info.getSpawnZ());
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}

	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2)
	{
		return false;
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3)
	{
		return 0.5F;
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesXZShowFog(int par1, int par2)
	{
		return true;
	}

	@Override
	public String getDimensionName()
	{
		return "Nether";
	}

	@Override
	public Vec3 getFogColor(float par1, float par2)
	{
		return Vec3.createVectorHelper(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
	}

}
