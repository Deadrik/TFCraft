package TFC.WorldGen;

import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import net.minecraft.src.ModLoader;
import net.minecraft.world.WorldProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;

public class TFCProvider extends WorldProvider
{	
	public IRenderHandler skyprovider;
	
	@Override
	protected void registerWorldChunkManager()
	{
		worldChunkMgr = terrainType.getChunkManager(this.worldObj);
		TFC_Climate.manager = (TFCWorldChunkManager) worldChunkMgr;
		TFC_Climate.worldObj = worldObj;
	}

	@Override
	/**
	 * Will check if the x, z position specified is alright to be set as the map spawn point
	 */
	public boolean canCoordinateBeSpawn(int par1, int par2)
	{
		int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
		//return var3 == Block.grass.blockID;
		return TFC_Core.isGrass(var3);
	}

	@Override
	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
	 */
	public float calculateCelestialAngle(long par1, float par3)
	{
		int var4 = (int)(par1 % (long)TFC_Settings.dayLength);
		float var5 = ((float)var4 + par3) / (float)TFC_Settings.dayLength - 0.25F;

		if (var5 < 0.0F)
		{
			++var5;
		}

		if (var5 > 1.0F)
		{
			--var5;
		}

		float var6 = var5;
		var5 = 1.0F - (float)((Math.cos((double)var5 * Math.PI) + 1.0D) / 2.0D);
		var5 = var6 + (var5 - var6) / 3.0F;
		return var5;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMoonPhase(long par1)
	{
		return (int)(par1 / (long)TFC_Settings.dayLength) % 8;
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
	public ChunkCoordinates getSpawnPoint()
    {
		if (!canRespawnHere())
		{
			return new ChunkCoordinates(0, worldObj.getHeightValue(0, 0), 0);
		}
		else
		{

			TFCWorldChunkManager var2 = (TFCWorldChunkManager) worldChunkMgr;
			List var3 = var2.getBiomesToSpawnIn();
			long seed = worldObj.getWorldInfo().getSeed();
			Random var4 = new Random(seed);

			ChunkPosition var5 = null;
			int xOffset = 0;
			int var6 = 0;
			int var7 = getAverageGroundLevel();
			int var8 = 10000;
			int startingZ = 3000 + var4.nextInt(12000);

			while(var5 == null)
			{
				var5 = var2.findBiomePosition(xOffset, -startingZ, 64, var3, var4);

				if (var5 != null)
				{
					var6 = var5.x;
					var8 = var5.z;
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

			worldObj.getWorldInfo().setSpawnPosition(var6, worldObj.getHeightValue(var6, var8), var8);
			
			return new ChunkCoordinates(var6, worldObj.getHeightValue(var6, var8), var8);
		}
    }
	
	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
    {
		if(TFC_Climate.getHeightAdjustedTemp(x, y, z) <= 0 && (worldObj.getBlockMaterial(x, y, z) == Material.water || worldObj.getBlockMaterial(x, y, z) == Material.ice))
			return true;
		return false;
    }

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
