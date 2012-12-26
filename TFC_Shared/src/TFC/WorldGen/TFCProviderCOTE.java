package TFC.WorldGen;

import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
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
