package TFC.WorldGen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
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
	public boolean canCoordinateBeSpawn(int par1, int par2)
	{
		int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
		//return var3 == Block.grass.blockID;
		return TFC_Core.isGrass(var3);
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3)
	{
		int var4 = (int)(par1 % TFC_Settings.dayLength);
		float var5 = (var4 + par3) / TFC_Settings.dayLength - 0.25F;

		if (var5 < 0.0F)
		{
			++var5;
		}

		if (var5 > 1.0F)
		{
			--var5;
		}

		float var6 = var5;
		var5 = 1.0F - (float)((Math.cos(var5 * Math.PI) + 1.0D) / 2.0D);
		var5 = var6 + (var5 - var6) / 3.0F;
		return var5;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMoonPhase(long par1)
	{
		return (int)(par1 / TFC_Settings.dayLength) % 8;
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
	public BiomeGenBase getBiomeGenForCoords(int x, int z)
    {
        BiomeGenBase biome = worldObj.getBiomeGenForCoordsBody(x, z);
        if(canSnowAt(x,145,z)){biome.temperature = 0;}
        else{biome.temperature = 0.16f;}
        return biome;
    }
	
	@Override
	public ChunkCoordinates getRandomizedSpawnPoint()
    {
		TFCWorldChunkManager var2 = (TFCWorldChunkManager) worldChunkMgr;
		List var3 = var2.getBiomesToSpawnIn();
		long seed = worldObj.getWorldInfo().getSeed();
		Random var4 = new Random(seed);

		ChunkPosition chunkcoordinates = null;
		int xOffset = 0;
		int var6 = 0;
		int var7 = getAverageGroundLevel();
		int var8 = 10000;
		int startingZ = 3000 + var4.nextInt(12000);

		while(chunkcoordinates == null)
		{
			chunkcoordinates = var2.findBiomePosition(xOffset, -startingZ, 64, var3, var4);

			if (chunkcoordinates != null)
			{
				var6 = chunkcoordinates.x;
				var8 = chunkcoordinates.z;
			}
			else
			{
				xOffset += 512;
				//System.out.println("Unable to find spawn biome");
			}
		}

		int var9 = 0;

		while (!canCoordinateBeSpawn(var6, var8))
		{
			var6 += var4.nextInt(64) - var4.nextInt(64);
			var8 += var4.nextInt(64) - var4.nextInt(64);
			++var9;

			if (var9 == 1000)
			{
				break;
			}
		}

        return new ChunkCoordinates(var6, this.worldObj.getHeightValue(var6, var8), var8);
    }
	
	@Override
	public ChunkCoordinates getSpawnPoint()
    {
		WorldInfo info = worldObj.getWorldInfo();
        return new ChunkCoordinates(info.getSpawnX(), info.getSpawnY(), info.getSpawnZ());
    }
	
	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
    {
		if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0 && (worldObj.getBlockMaterial(x, y, z) == Material.water || worldObj.getBlockMaterial(x, y, z) == Material.ice))
			return true;
		return false;
    }
	@Override
    public boolean canSnowAt(int x, int y, int z)
    {
    	if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0)
			return true;
		return false;
    }

	@Override
	public String getDimensionName() {
		// TODO Auto-generated method stub
		return "DEFAULT";
	}

}
