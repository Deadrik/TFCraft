package TFC.WorldGen;

import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Game;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.Vec3;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldType;
import net.minecraftforge.client.SkyProvider;

public class TFCProvider extends WorldProvider
{	
	SkyProvider skyprovider;
	@Override
	protected void registerWorldChunkManager()
    {
        this.worldChunkMgr = terrainType.getChunkManager(this.worldObj);
        //TFC_Core.SetupWorld(this.worldObj);
        TFC_Game.registerAnvilRecipes(this.worldObj.rand, this.worldObj);
        skyprovider = new TFCSkyProvider(ModLoader.getMinecraftInstance());
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
    public int getMoonPhase(long par1, float par3)
    {
        return (int)(par1 / (long)TFC_Settings.dayLength) % 8;
    }
	
	@SideOnly(Side.CLIENT)

    /**
     * Calculates the color for the skybox
     */
    public Vec3 getSkyColor(Entity par1Entity, float par2)
    {
        float var3 = worldObj.getCelestialAngle(par2);
        float var4 = MathHelper.cos(var3 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (var4 < 0.0F)
        {
            var4 = 0.0F;
        }

        if (var4 > 1.0F)
        {
            var4 = 1.0F;
        }

        int Y = MathHelper.floor_double(par1Entity.posY);
        int Z = MathHelper.floor_double(par1Entity.posZ);
        
        float var8 = TFC_Climate.getHeightAdjustedTemp(Y, Z);
        
        //Adjust to 0-1
        var8 = (var8+35)/77;
        
        int var9 = ((TFCSkyProvider)this.getSkyProvider()).getSkyColorByTemp(var8);
        
        float var10 = (float)(var9 >> 16 & 255) / 255.0F;
        float var11 = (float)(var9 >> 8 & 255) / 255.0F;
        float var12 = (float)(var9 & 255) / 255.0F;
        var10 *= var4;
        var11 *= var4;
        var12 *= var4;
        float var13 = worldObj.getRainStrength(par2);
        float var14;
        float var15;

        if (var13 > 0.0F)
        {
            var14 = (var10 * 0.3F + var11 * 0.59F + var12 * 0.11F) * 0.6F;
            var15 = 1.0F - var13 * 0.75F;
            var10 = var10 * var15 + var14 * (1.0F - var15);
            var11 = var11 * var15 + var14 * (1.0F - var15);
            var12 = var12 * var15 + var14 * (1.0F - var15);
        }

        var14 = worldObj.getWeightedThunderStrength(par2);

        if (var14 > 0.0F)
        {
            var15 = (var10 * 0.3F + var11 * 0.59F + var12 * 0.11F) * 0.2F;
            float var16 = 1.0F - var14 * 0.75F;
            var10 = var10 * var16 + var15 * (1.0F - var16);
            var11 = var11 * var16 + var15 * (1.0F - var16);
            var12 = var12 * var16 + var15 * (1.0F - var16);
        }

        if (worldObj.lightningFlash > 0)
        {
            var15 = (float)worldObj.lightningFlash - par2;

            if (var15 > 1.0F)
            {
                var15 = 1.0F;
            }

            var15 *= 0.45F;
            var10 = var10 * (1.0F - var15) + 0.8F * var15;
            var11 = var11 * (1.0F - var15) + 0.8F * var15;
            var12 = var12 * (1.0F - var15) + 1.0F * var15;
        }

        return Vec3.getVec3Pool().getVecFromPool((double)var10, (double)var11, (double)var12);
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
	
	@SideOnly(Side.CLIENT)
    public SkyProvider getSkyProvider()
    {
        return skyprovider;
    }

    @SideOnly(Side.CLIENT)
    public void setSkyProvider(SkyProvider skyProvider)
    {
        skyprovider = skyProvider;
    }

}
