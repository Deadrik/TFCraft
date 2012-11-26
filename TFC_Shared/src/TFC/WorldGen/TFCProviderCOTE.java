package TFC.WorldGen;

import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Vec3;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;
import net.minecraftforge.client.SkyProvider;

public class TFCProviderCOTE extends TFCProvider
{		
	@Override
	protected void registerWorldChunkManager()
	{
		this.worldChunkMgr = terrainType.getChunkManager(this.worldObj);
		TFC_Climate.manager = (TFCWorldChunkManager) worldChunkMgr;
		TFC_Climate.worldObj = worldObj;
	}
	
	@Override
	public int getHeight()
    {
        return 128;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float par1, float par2)
    {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(0.029999999329447746D, 0.029999999329447746D, 0.029999999329447746D);
    }

	@Override
    protected void generateLightBrightnessTable()
    {
        float var1 = 0.1F;

        for (int var2 = 0; var2 <= 15; ++var2)
        {
            float var3 = 1.0F - (float)var2 / 15.0F;
            this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }
    }
	
	@Override
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderCOTE(this.worldObj,this.worldObj.getSeed());
    }
	
	@Override
	public boolean isSurfaceWorld()
    {
        return false;
    }

	@Override
	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
	 */
	public float calculateCelestialAngle(long par1, float par3)
	{
		return 0.5F;
	}
	
	@Override
	public boolean canRespawnHere()
    {
        return false;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public int getMoonPhase(long par1, float par3)
	{
		return (int)(par1 / (long)TFC_Settings.dayLength) % 8;
	}
	
	@SideOnly(Side.CLIENT)

    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    public boolean doesXZShowFog(int par1, int par2)
    {
        return true;
    }

	@Override
	public float getCloudHeight()
	{
		return -5.0F;
	}

	@Override
	public String getDimensionName() {
		// TODO Auto-generated method stub
		return "COTE";
	}

}
