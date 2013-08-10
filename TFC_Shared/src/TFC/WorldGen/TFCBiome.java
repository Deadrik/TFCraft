package TFC.WorldGen;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.Helper;
import TFC.Core.TFC_Time;
import TFC.Core.WeatherManager;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Entities.Mobs.EntityCreeperTFC;
import TFC.Entities.Mobs.EntityEndermanTFC;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.Entities.Mobs.EntitySheepTFC;
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
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCBiome extends BiomeGenBase
{
	public static int ForestColor = 0x56621;
	public static int ForestWater = 0x228855;
	public static int PlainsColor = 0x56621;
	public static int PlainsWater = 0x228855;
	public static int TaigaColor = 0xb6659;
	public static int TaigaWater = 0x228855;
	public static int SwampColor = 0x168e6b;
	public static int SwampWater = 0x228855;//0x644c27;
	public static int DesertColor = 0xb6659;
	public static int DesertWater = 0x228855;
	public static int HillsColor = 0xb6659;
	public static int HillsWater = 0x228855;

	public static float riverDepthMin = -0.5F;
	public static float riverDepthMax = -0.3F;
	public float temperatureTFC;

	//public static TFCBiome[] biomeList = new TFCBiome[256];

	/** An array of all the biomes, indexed by biome id. */
	public static final TFCBiome ocean = new BiomeGenOceanTFC(0).setBiomeName("Ocean").setMinMaxHeight(-0.9F, 0.1F);
	public static final TFCBiome river = new BiomeGenRiverTFC(7).setColor(ForestColor).setBiomeName("River").setMinMaxHeight(riverDepthMin, riverDepthMax);

	public static final TFCBiome hell = (new BiomeGenHellTFC(8)).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);

	public static final TFCBiome beach = (new BiomeGenBeachTFC(16)).setColor(0xfade55).setBiomeName("Beach").setMinMaxHeight(0.0F, 0.1F);

	public static final TFCBiome jungle = (new BiomeGenJungleTFC(21)).setColor(5470985).setBiomeName("Jungle").setMinMaxHeight(0.2F, 0.4F);

	public static final TFCBiome jungleHills = (new BiomeGenJungleTFC(22)).setColor(2900485).setBiomeName("JungleHills").setMinMaxHeight(0.3F, 0.8F);

	public static final TFCBiome desert = (new BiomeGenDesertTFC(2)).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(36F, 0.0F).setMinMaxHeight(0.1F, 0.15F);

	public static final TFCBiome HighHills = (new BiomeGenHillsTFC(3)).setColor(HillsColor).setBiomeName("High Hills").setMinMaxHeight(0.8F, 1.6F);

	public static final TFCBiome forest = (new BiomeGenForestTFC(4)).setColor(ForestColor).setBiomeName("Forest").setMinMaxHeight(0.1F, 0.3F);

	public static final TFCBiome plains = (new BiomeGenPlainsTFC(1)).setColor(PlainsColor).setBiomeName("TFC Plains").setMinMaxHeight(0.1F, 0.2F);

	public static final TFCBiome taiga = (new BiomeGenTaigaTFC(5)).setColor(TaigaColor).setBiomeName("Taiga").setMinMaxHeight(0.2F, 0.5F);

	public static final TFCBiome swampland = (new BiomeGenSwampTFC(6)).setColor(SwampColor).setBiomeName("Swamp").setMinMaxHeight(-0.1F, 0.1F);

	public static final TFCBiome HighHillsEdge = (new BiomeGenHillsEdgeTFC(20)).setColor(HillsColor).setBiomeName("High Hills Edge").setMinMaxHeight(0.2F, 0.4F);

	public static final TFCBiome rollingHills = (new TFCBiome(30)).setColor(PlainsColor).setBiomeName("Rolling Hills").setMinMaxHeight(0.1F, 0.4F);
	public static final TFCBiome Mountains = (new TFCBiome(31)).setColor(HillsColor).setBiomeName("Mountains").setMinMaxHeight(0.8F, 1.6F);
	public static final TFCBiome MountainsEdge = (new TFCBiome(32)).setColor(HillsColor).setBiomeName("Mountains Edge").setMinMaxHeight(0.4F, 0.8F);


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

		this.topBlock = (byte)Block.grass.blockID;
		this.fillerBlock = (byte)Block.dirt.blockID;
		this.minHeight = 0.1F;
		this.maxHeight = 0.3F;
		temperatureTFC = 0.5F;
		this.rainfall = 0.5F;
		this.spawnableMonsterList = new ArrayList();
		this.spawnableCreatureList = new ArrayList();
		this.spawnableWaterCreatureList = new ArrayList();

		super.biomeList[par1] = this;

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

		this.waterColorMultiplier = ForestWater;

		this.spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 12, 4, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 10, 4, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 10, 4, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 8, 4, 4));
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 10, 4, 4));

		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpiderTFC.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieTFC.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonTFC.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeperTFC.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySlimeTFC.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEndermanTFC.class, 1, 1, 4));
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
		this.theBiomeDecorator.decorate(par1World, par2Random, par3, par4);
	}

	/**
	 * Sets the minimum and maximum height of this biome. Seems to go from -2.0 to 2.0.
	 */
	@Override
	public TFCBiome setMinMaxHeight(float par1, float par2)
	{
		this.minHeight = par1-2.7F;
		this.maxHeight = par2-2.7F;
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
		ObfuscationReflectionHelper.setPrivateValue(BiomeGenBase.class, this, false, 39);
		return this;
	}

	public int getIntRain()
	{
		if(TFC_Time.getMonth() < 3 || TFC_Time.currentMonth >= 10) {
			return (int)((this.rainfall+0.1F) * 65536.0F);
		} else {
			return (int)(this.rainfall * 65536.0F);
		}
	}

	/**
	 * Gets an integer representation of this biome's temperature
	 */
	public int getIntTemp()
	{
		return (int) (getFloatTemp() * 65536.0F);   
	}

	/**
	 * Gets a floating point representation of this biome's temperature
	 */    
	public float getFloatTemp()
	{
		float temp = getTemp();
		if(temp <= 0)
		{
			return 0;
		} else {
			return 0.3f+temp/40;
		}
	}

	private float getTemp(float day)
	{
		Random R = new Random((int)day * TFC_Time.currentYear);
		float mod = getMonthTemp(TFC_Time.currentMonth);
		float modLast = getMonthTemp(TFC_Time.lastMonth);
		int day2 = TFC_Time.getDayOfMonth();
		int hour = (int) TFC_Time.getHour()-3;
		if(hour < 0) {
			hour = 23 + hour;
		}
		float hourMod = 0;
		if(hour < 12) {
			hourMod = ((float)hour/11) * 0.2F;
		} else {
			hourMod = 0.2F - (((float)(hour-12)/11) * 0.2F);
		}

		float m = 0;
		float temp = 0;

		if(modLast > mod)
		{
			m = ((modLast-mod)/30)*day2;
			m = (modLast - m);
			temp = ((temperatureTFC+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(temperatureTFC+WeatherManager.getInstance().getDailyTemp())); 
		}
		else
		{
			m = ((mod-modLast)/30)*day2;
			m = (modLast + m);
			temp = ((temperatureTFC+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(temperatureTFC+WeatherManager.getInstance().getDailyTemp()));
		}
		return temp;
	}

	private float getTemp()
	{
		return getTemp((float)TFC_Time.getDayOfYear()+1);
	}

	public final float getHeightAdjustedTemperature(int y)
	{
		float temp = getTemp();

		if(y > 180) {
			temp -= temp * (y-180)/90;
		}
		return temp;
	}

	/**
	 * Gets a floating point representation of this biome's rainfall
	 */
	public float getFloatRain()
	{
		if(TFC_Time.currentMonth < 3 || TFC_Time.currentMonth >= 9) {
			return this.rainfall+0.1F;
		} else {
			return this.rainfall;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic grass color based on the biome temperature and rainfall
	 */
	public int getBiomeGrassColor()
	{
		if(TFC_Time.currentMonth < 6)
		{
			double var1 = Helper.clamp_float(this.getFloatTemp(), 0.0F, 1.0F);
			double var3 = Helper.clamp_float(this.getFloatRain(), 0.0F, 1.0F);
			return ColorizerGrassTFC.getGrassColor(var1, var3);
		} else {
			return ColorizerGrassTFC.getGrassDead();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	public int getBiomeFoliageColor()
	{
		if(TFC_Time.currentMonth < 9)
		{
			double var1 = Helper.clamp_float(this.getFloatTemp(), 0.0F, 1.0F);
			double var3 = Helper.clamp_float(this.getFloatRain(), 0.0F, 1.0F);
			return ColorizerFoliageTFC.getFoliageColor(var1, var3);
		} else {
			return ColorizerFoliageTFC.getFoliageDead();
		}
	}

	/**
	 * All TerraFirmaCraft Methods should be added below this point.
	 * */
	protected float getMonthTemp(int month)
	{
		switch(month)
		{
		case 11:
			return 0.266F;
		case 0:
			return 0.412F;
		case 1:
			return 0.588F;
		case 2:
			return 0.704F;
		case 3:
			return 0.85F; 
		case 4:
			return 1F;
		case 5:
			return 0.85F;
		case 6:
			return 0.704F;
		case 7:
			return 0.588F;
		case 8:
			return 0.412F;
		case 9:
			return 0.266F;
		case 10:
			return 0.12F;
		default:
			return 1F;
		}
	}

	public static WorldGenerator getTreeGen(int i, Boolean j)
	{
		Random R = new Random();
		switch(i)
		{
		case 7:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,7) : worldGenAshTallTrees;
			}
			else
			{
				return worldGenAshShortTrees;
			}
		}
		case 1:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,1) :worldGenAspenTallTrees;
			}
			else
			{
				return worldGenAspenShortTrees;
			}
		}
		case 2:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,2) :worldGenBirchTallTrees;
			}
			else
			{
				return worldGenBirchShortTrees;
			}
		}
		case 3:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,3) :worldGenChestnutTallTrees;
			}
			else
			{
				return worldGenChestnutShortTrees;
			}
		}
		case 4:
		{
			if(j)
			{
				return worldGenDouglasFirTallTrees;
			}
			else
			{
				return worldGenDouglasFirShortTrees;
			}
		}
		case 5:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,5) :worldGenHickoryTallTrees;
			}
			else
			{
				return worldGenHickoryShortTrees;
			}
		}
		case 6:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,6) :worldGenMapleTallTrees;
			}
			else
			{
				return worldGenMapleShortTrees;
			}
		}
		case 0:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,0) :worldGenOakTallTrees;
			}
			else
			{
				return worldGenOakShortTrees;
			}
		}
		case 8:
		{
			if(j)
			{
				return worldGenPineTallTrees;
			}
			else
			{
				return worldGenPineShortTrees;
			}
		}
		case 9:
		{
			if(j)
			{
				return worldGenRedwoodTallTrees;
			}
			else
			{
				return worldGenRedwoodShortTrees;
			}
		}
		case 10:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,10) :worldGenSpruceTallTrees;
			}
			else
			{
				return worldGenSpruceShortTrees;
			}
		}
		case 11:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,11) :worldGenSycamoreTallTrees;
			}
			else
			{
				return worldGenSycamoreShortTrees;
			}
		}
		case 12:
		{
			return worldGenWhiteCedarTallTrees;
		}
		case 13:
		{
			if(j)
			{
				return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,13) :worldGenWhiteElmTallTrees;
			}
			else
			{
				return worldGenWhiteElmShortTrees;
			}
		}
		case 14:
		{
			return worldGenWillowShortTrees;
		}
		case 15:
		{
			return ( (R.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) : (R.nextInt(3) == 0 ? new WorldGenCustomHugeTrees(false, 10 + R.nextInt(20), 15, 15) : new WorldGenCustomShortTrees(false, 15))));
		}
		}
		return null;
	}

	public WorldGenerator getRandomWorldGenForTrees(Random randomGenerator,World currentWorld) 
	{
		return randomGenerator.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
	}

	public TFCBiome GetBiomeByName(String name)
	{
		for (int i = 0; i < this.biomeList.length; i++)
		{
			if(biomeList[i] != null)
			{
				String n = biomeList[i].biomeName.toLowerCase();
				if(n.equalsIgnoreCase(name)) {
					return (TFCBiome) biomeList[i];
				}
			}
		}
		return null;
	}

	public static int getSurfaceRockLayer(World world, int i, int k)
	{
		return ((TFCWorldChunkManager)world.provider.worldChunkMgr).getRockLayerAt(i, k, 0).data2;
	}

}
