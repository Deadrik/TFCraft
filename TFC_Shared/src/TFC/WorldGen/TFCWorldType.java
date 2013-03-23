package TFC.WorldGen;

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
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.feature.*;

public class TFCWorldType extends WorldType
{
	public static TFCWorldType DEFAULT;
	public static TFCWorldType FLAT;
	
	public static BiomeGenBase[] overworldBiomeArray = new BiomeGenBase[] {TFCBiome.HighHills, TFCBiome.swampland, TFCBiome.plains, TFCBiome.plains, 
		TFCBiome.rollingHills, TFCBiome.Mountains};
	
	public TFCWorldType(int par1, String par2Str, int par3) 
	{
		super(par1, par2Str, par3);
	}
	
	public TFCWorldType(int par1, String par2Str) 
	{
		super(par1, par2Str);
	}
	
	@Override
	public WorldChunkManager getChunkManager(World world)
    {
		if (this == FLAT)
        {
            FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
            return new TFCWorldChunkManagerHell(BiomeGenBase.biomeList[var1.getBiome()], 0.5F, 0.5F);
        }
        else
        {
        	return new TFCWorldChunkManager(world);
        }
    }

	@Override
	public IChunkProvider getChunkGenerator(World world, String generatorOptions)
    {
        return new TFCChunkProviderGenerate(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
    }

    @Override
    public int getMinimumSpawnHeight(World world)
    {
        return 145;
    }

    @Override
    public double getHorizon(World world)
    {
        return 144.0D;
    }

}
