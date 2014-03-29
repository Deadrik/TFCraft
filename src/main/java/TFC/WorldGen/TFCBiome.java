package TFC.WorldGen;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityCreeperTFC;
import TFC.Entities.Mobs.EntityEndermanTFC;
import TFC.Entities.Mobs.EntitySkeletonTFC;
import TFC.Entities.Mobs.EntitySlimeTFC;
import TFC.Entities.Mobs.EntitySpiderTFC;
import TFC.Entities.Mobs.EntitySquidTFC;
import TFC.Entities.Mobs.EntityZombieTFC;
import TFC.WorldGen.Biomes.BiomeGenBeachTFC;
import TFC.WorldGen.Biomes.BiomeGenDesertTFC;
import TFC.WorldGen.Biomes.BiomeGenForestTFC;
import TFC.WorldGen.Biomes.BiomeGenHellTFC;
import TFC.WorldGen.Biomes.BiomeGenHillsEdgeTFC;
import TFC.WorldGen.Biomes.BiomeGenHillsTFC;
import TFC.WorldGen.Biomes.BiomeGenJungleTFC;
import TFC.WorldGen.Biomes.BiomeGenOceanTFC;
import TFC.WorldGen.Biomes.BiomeGenPlainsTFC;
import TFC.WorldGen.Biomes.BiomeGenRiverTFC;
import TFC.WorldGen.Biomes.BiomeGenSwampTFC;
import TFC.WorldGen.Biomes.BiomeGenTaigaTFC;
import TFC.WorldGen.Generators.WorldGenCustomShrub;
import TFC.WorldGen.Generators.Trees.WorldGenAcaciaKoaTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomBigTree;
import TFC.WorldGen.Generators.Trees.WorldGenCustomCedarTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomHugeTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomMapleShortTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomMapleTallTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomShortTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomTallTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomWillowTrees;
import TFC.WorldGen.Generators.Trees.WorldGenDouglasFir;
import TFC.WorldGen.Generators.Trees.WorldGenPineShort;
import TFC.WorldGen.Generators.Trees.WorldGenPineTall;
import TFC.WorldGen.Generators.Trees.WorldGenRedwoodXL;

public class TFCBiome extends BiomeGenBase
{
	public static int SwampWater = 0x228855;//0x644c27;
	public static int FreshWater = 0x80b280;
	public static int DarkWater = 0x354d35;
	public static float riverDepthMin = -0.5F;
	public static float riverDepthMax = -0.3F;
	public float temperatureTFC;

	//public static TFCBiome[] biomeList = new TFCBiome[256];

	/** An array of all the biomes, indexed by biome id. */
	public static final TFCBiome ocean = new BiomeGenOceanTFC(0).setBiomeName("Ocean").setMinMaxHeight(-0.9F, 0.1F);
	public static final TFCBiome river = new BiomeGenRiverTFC(7).setBiomeName("River").setMinMaxHeight(riverDepthMin, riverDepthMax);
	public static final TFCBiome hell = (new BiomeGenHellTFC(8)).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
	public static final TFCBiome beach = (new BiomeGenBeachTFC(16)).setColor(0xfade55).SetWaterMult(DarkWater).setBiomeName("Beach").setMinMaxHeight(0.0F, 0.02F);
	public static final TFCBiome jungle = (new BiomeGenJungleTFC(21)).setColor(5470985).setBiomeName("Jungle").setMinMaxHeight(0.2F, 0.4F);
	public static final TFCBiome jungleHills = (new BiomeGenJungleTFC(22)).setColor(2900485).setBiomeName("JungleHills").setMinMaxHeight(0.3F, 0.8F);
	public static final TFCBiome desert = (new BiomeGenDesertTFC(2)).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(36F, 0.0F).setMinMaxHeight(0.1F, 0.15F);
	public static final TFCBiome HighHills = (new BiomeGenHillsTFC(3)).setBiomeName("High Hills").setMinMaxHeight(0.8F, 1.6F);
	public static final TFCBiome forest = (new BiomeGenForestTFC(4)).setBiomeName("Forest").setMinMaxHeight(0.1F, 0.3F);
	public static final TFCBiome plains = (new BiomeGenPlainsTFC(1)).setBiomeName("TFC Plains").setMinMaxHeight(0.1F, 0.2F);
	public static final TFCBiome taiga = (new BiomeGenTaigaTFC(5)).setBiomeName("Taiga").setMinMaxHeight(0.2F, 0.5F);
	public static final TFCBiome swampland = (new BiomeGenSwampTFC(6)).setBiomeName("Swamp").setMinMaxHeight(-0.1F, 0.1F);
	public static final TFCBiome HighHillsEdge = (new BiomeGenHillsEdgeTFC(20)).setBiomeName("High Hills Edge").setMinMaxHeight(0.2F, 0.4F);
	public static final TFCBiome rollingHills = (new TFCBiome(30)).setBiomeName("Rolling Hills").setMinMaxHeight(0.1F, 0.4F);
	public static final TFCBiome Mountains = (new TFCBiome(31)).setBiomeName("Mountains").setMinMaxHeight(0.8F, 1.6F);
	public static final TFCBiome MountainsEdge = (new TFCBiome(32)).setBiomeName("Mountains Edge").setMinMaxHeight(0.4F, 0.8F);
	public static final TFCBiome MountainsSeismic = (new TFCBiome(33)).setBiomeName("Mountains Seismic").setMinMaxHeight(0.8F, 1.6F);
	public static final TFCBiome MountainsEdgeSeismic = (new TFCBiome(34)).setBiomeName("Mountains Edge Seismic").setMinMaxHeight(0.4F, 0.8F);
	public static final TFCBiome PlainsSeismic = (new BiomeGenPlainsTFC(35)).setBiomeName("TFC Plains Seismic").setMinMaxHeight(0.1F, 0.2F);

	protected static WorldGenAcaciaKoaTrees worldGenAcaciaKoaTrees;
	protected static WorldGenCustomTallTrees worldGenAshTallTrees;
	protected static WorldGenCustomTallTrees worldGenAspenTallTrees;
	protected static WorldGenCustomTallTrees worldGenBirchTallTrees;
	protected static WorldGenCustomTallTrees worldGenChestnutTallTrees;
	protected static WorldGenDouglasFir worldGenDouglasFirTallTrees;
	protected static WorldGenCustomTallTrees worldGenHickoryTallTrees;
	protected static WorldGenCustomMapleTallTrees worldGenMapleTallTrees;
	protected static WorldGenCustomTallTrees worldGenOakTallTrees;
	protected static WorldGenPineTall worldGenPineTallTrees;
	protected static WorldGenRedwoodXL worldGenRedwoodTallTrees;
	protected static WorldGenCustomTallTrees worldGenSpruceTallTrees;
	protected static WorldGenCustomTallTrees worldGenSycamoreTallTrees;
	protected static WorldGenCustomCedarTrees worldGenWhiteCedarTallTrees;
	protected static WorldGenCustomTallTrees worldGenWhiteElmTallTrees;

	protected static WorldGenCustomShortTrees worldGenAshShortTrees;
	protected static WorldGenCustomShortTrees worldGenAspenShortTrees;
	protected static WorldGenCustomShortTrees worldGenBirchShortTrees;
	protected static WorldGenCustomShortTrees worldGenChestnutShortTrees;
	protected static WorldGenDouglasFir worldGenDouglasFirShortTrees;
	protected static WorldGenCustomShortTrees worldGenHickoryShortTrees;
	protected static WorldGenCustomMapleShortTrees worldGenMapleShortTrees;
	protected static WorldGenCustomShortTrees worldGenOakShortTrees;
	protected static WorldGenPineShort worldGenPineShortTrees;
	protected static WorldGenRedwoodXL worldGenRedwoodShortTrees;
	protected static WorldGenCustomShortTrees worldGenSpruceShortTrees;
	protected static WorldGenCustomShortTrees worldGenSycamoreShortTrees;
	protected static WorldGenCustomShortTrees worldGenWhiteElmShortTrees;
	protected static WorldGenCustomWillowTrees worldGenWillowShortTrees;

	public TFCBiome(int par1)
	{
		super(par1);

		this.topBlock = Blocks.grass;
		this.fillerBlock = Blocks.dirt;
		this.rootHeight = 0.1F;
		this.heightVariation = 0.3F;
		temperatureTFC = 0.5F;
		this.rainfall = 0.5F;
		this.spawnableMonsterList = new ArrayList();
		this.spawnableCreatureList = new ArrayList();
		this.spawnableWaterCreatureList = new ArrayList();

		worldGenAcaciaKoaTrees = new WorldGenAcaciaKoaTrees(false,0);
		worldGenAshTallTrees = new WorldGenCustomTallTrees(false,7);
		worldGenAspenTallTrees = new WorldGenCustomTallTrees(false,1);
		worldGenBirchTallTrees = new WorldGenCustomTallTrees(false,2);
		worldGenChestnutTallTrees = new WorldGenCustomTallTrees(false,3);
		worldGenDouglasFirTallTrees = new WorldGenDouglasFir(false,4, true);
		worldGenHickoryTallTrees = new WorldGenCustomTallTrees(false,5);
		worldGenMapleTallTrees = new WorldGenCustomMapleTallTrees(false,6);
		worldGenOakTallTrees = new WorldGenCustomTallTrees(false,0);
		worldGenPineTallTrees = new WorldGenPineTall(8);
		worldGenRedwoodTallTrees = new WorldGenRedwoodXL(false);
		worldGenSpruceTallTrees = new WorldGenCustomTallTrees(false,10);
		worldGenSycamoreTallTrees = new WorldGenCustomTallTrees(false,11);
		worldGenWhiteCedarTallTrees = new WorldGenCustomCedarTrees(false,12);
		worldGenWhiteElmTallTrees = new WorldGenCustomTallTrees(false,13);

		worldGenAshShortTrees = new WorldGenCustomShortTrees(false,7);
		worldGenAspenShortTrees = new WorldGenCustomShortTrees(false,1);
		worldGenBirchShortTrees = new WorldGenCustomShortTrees(false,2);
		worldGenChestnutShortTrees = new WorldGenCustomShortTrees(false,3);
		worldGenDouglasFirShortTrees = new WorldGenDouglasFir(false,4, false);
		worldGenHickoryShortTrees = new WorldGenCustomShortTrees(false,5);
		worldGenMapleShortTrees = new WorldGenCustomMapleShortTrees(false,6);
		worldGenOakShortTrees = new WorldGenCustomShortTrees(false,0);
		worldGenPineShortTrees = new WorldGenPineShort(false, 8);
		worldGenRedwoodShortTrees = new WorldGenRedwoodXL(false);
		worldGenSpruceShortTrees = new WorldGenCustomShortTrees(false,10);
		worldGenSycamoreShortTrees = new WorldGenCustomShortTrees(false,11);
		worldGenWhiteElmShortTrees = new WorldGenCustomShortTrees(false,13);
		worldGenWillowShortTrees = new WorldGenCustomWillowTrees(false,14);

		this.waterColorMultiplier = DarkWater;
		//Default spawns. I didn't delete them so they could be referenced in the future. Nerfing animal spawns.
		/*this.spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 12, 4, 6));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 10, 2, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 10, 2, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 8, 2, 4));*/
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 12, 2, 4));
		//This is to balance out the spawning, so that entities with weight 1 spawn less
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class,16,0,0));
		this.spawnableWaterCreatureList.clear();

		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpiderTFC.class, 10, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieTFC.class, 10, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonTFC.class, 10, 1, 3));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeperTFC.class, 3, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySlimeTFC.class, 8, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEndermanTFC.class, 1, 1, 2));
		
		getBiomeGenArray()[par1] = this;
		//biomeList[par1] = this;
	}

	/**
	 * Allocate a new BiomeDecorator for this BiomeGenBase
	 */
	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeDecoratorTFC(this);
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		this.theBiomeDecorator.decorateChunk(par1World, par2Random, this, par3, par4);
	}

	/**
	 * Sets the minimum and maximum height of this biome. Seems to go from -2.0 to 2.0.
	 */
	//@Override
	public TFCBiome setMinMaxHeight(float par1, float par2)
	{
		this.rootHeight = par1-2.7F;
		this.heightVariation = par2-2.7F;
		return this;
	}

	@Override
	public TFCBiome setTemperatureRainfall(float par1, float par2)
	{
		temperatureTFC = par1;
		this.rainfall = par2;
		return this;
	}

	@Override
	public TFCBiome setBiomeName(String par1Str)
	{
		this.biomeName = par1Str;
		return this;
	}

	public TFCBiome SetWaterMult(int par1)
	{
		this.waterColorMultiplier = par1;
		return this;
	}
	@Override
	public TFCBiome setColor(int par1)
	{
		this.color = par1;
		return this;
	}

	/**
	 * Disable the rain for the biome.
	 */
	@Override
	public TFCBiome setDisableRain()
	{
//		ObfuscationReflectionHelper.setPrivateValue(BiomeGenBase.class, this, false, 39);
		this.enableRain = false;
		return this;
	}

	public static WorldGenerator getTreeGen(int i, Boolean j)
	{
		Random R = new Random();
		switch(i)
		{
		case 7:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,7) : worldGenAshTallTrees;
				else
					return worldGenAshShortTrees;
		}
		case 1:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,1) :worldGenAspenTallTrees;
				else
					return worldGenAspenShortTrees;
		}
		case 2:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,2) :worldGenBirchTallTrees;
				else
					return worldGenBirchShortTrees;
		}
		case 3:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,3) :worldGenChestnutTallTrees;
				else
					return worldGenChestnutShortTrees;
		}
		case 4:
		{
			if(j)
				return worldGenDouglasFirTallTrees;
			else
				return worldGenDouglasFirShortTrees;
		}
		case 5:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,5) :worldGenHickoryTallTrees;
				else
					return worldGenHickoryShortTrees;
		}
		case 6:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,6) :worldGenMapleTallTrees;
				else
					return worldGenMapleShortTrees;
		}
		case 0:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,0) :worldGenOakTallTrees;
				else
					return worldGenOakShortTrees;
		}
		case 8:
		{
			if(j)
				return worldGenPineTallTrees;
			else
				return worldGenPineShortTrees;
		}
		case 9:
		{
			if(j)
				return worldGenRedwoodTallTrees;
			else
				return worldGenRedwoodShortTrees;
		}
		case 10:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,10) :worldGenSpruceTallTrees;
				else
					return worldGenSpruceShortTrees;
		}
		case 11:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,11) :worldGenSycamoreTallTrees;
				else
					return worldGenSycamoreShortTrees;
		}
		case 12:
		{
			return worldGenWhiteCedarTallTrees;
		}
		case 13:
		{
			if(j)
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,13) :worldGenWhiteElmTallTrees;
				else
					return worldGenWhiteElmShortTrees;
		}
		case 14:
		{
			return worldGenWillowShortTrees;
		}
		case 15:
		{
			return ( (R.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) : (R.nextInt(3) == 0 ? new WorldGenCustomHugeTrees(false, 10 + R.nextInt(20), 15, 15) : new WorldGenCustomShortTrees(false, 15))));
		}
		case 16:
		{
			return worldGenAcaciaKoaTrees;
		}
		}
		return null;
	}

	public TFCBiome GetBiomeByName(String name)
	{
		for (int i = 0; i < this.getBiomeGenArray().length; i++)
			if(getBiomeGenArray()[i] != null)
			{
				String n = getBiomeGenArray()[i].biomeName.toLowerCase();
				if(n.equalsIgnoreCase(name))
					return (TFCBiome) getBiomeGenArray()[i];
			}
		return null;
	}

	public static int getSurfaceRockLayer(World world, int i, int k)
	{
		return ((TFCWorldChunkManager)world.provider.worldChunkMgr).getRockLayerAt(i, k, 0).data2;
	}

}
