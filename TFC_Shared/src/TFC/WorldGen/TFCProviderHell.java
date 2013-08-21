package TFC.WorldGen;

import java.util.List;
import java.util.Random;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Climate;
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
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;

public class TFCProviderHell extends TFCProvider
{
	@Override
	protected void registerWorldChunkManager()
	{
		worldChunkMgr = new TFCWorldChunkManagerHell(BiomeGenBase.hell, 1F, 1F);
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
	public String getDimensionName() {
		// TODO Auto-generated method stub
		return "Nether";
	}
	
	@Override
	public Vec3 getFogColor(float par1, float par2)
    {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
    }

}
