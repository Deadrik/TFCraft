package TFC.WorldGen;

import TFC.Core.TFCSettings;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Game;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldType;

public class TFCProvider extends WorldProvider
{
	
	public WorldType terrainType;
	
	@Override
	protected void registerWorldChunkManager()
    {
        this.worldChunkMgr = this.terrainType.getChunkManager(this.worldObj);
        //TFC_Core.SetupWorld(this.worldObj);
        TFC_Game.registerAnvilRecipes(this.worldObj.rand, this.worldObj);
    }
	
	@Override
	/**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int par1, int par2)
    {
        int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
        //return var3 == Block.grass.blockID;
        return var3 == TFCBlocks.terraGrass.blockID || var3 == TFCBlocks.terraGrass2.blockID;
    }

	@Override
    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float calculateCelestialAngle(long par1, float par3)
    {
        int var4 = (int)(par1 % (long)TFCSettings.dayLength);
        float var5 = ((float)var4 + par3) / (float)TFCSettings.dayLength - 0.25F;

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
    public int getMoonPhase(long par1, float par3)
    {
        return (int)(par1 / (long)TFCSettings.dayLength) % 8;
    }
	
	@Override
	public float getCloudHeight()
    {
        return 256.0F;
    }
    
	@Override
	public String func_80007_l() {
		return null;
	}

}
