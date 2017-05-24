package com.bioxx.tfc.WorldGen;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Entities.Mobs.*;
import com.bioxx.tfc.WorldGen.Generators.Trees.*;

public class TFCBiome extends BiomeGenBase
{
	public static float riverDepthMin = -0.5F;
	public static float riverDepthMax = -0.3F;
	public float temperatureTFC;

	public BiomeDecoratorTFC theBiomeDecorator;

	public static TFCBiome[] biomeList = new TFCBiome[256];

	/** An array of all the biomes, indexed by biome id. */
	public static final TFCBiome OCEAN = new TFCBiome(0).setBiomeName("Ocean").setMinMaxHeight(-0.9F, 0.00001F).setBiomeColor(0x0000ff);
	public static final TFCBiome RIVER = new TFCBiome(7).setBiomeName("River").setMinMaxHeight(riverDepthMin, riverDepthMax).setBiomeColor(0xffffff);
	public static final TFCBiome HELL = new TFCBiome(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
	public static final TFCBiome BEACH = new TFCBiome(16).setColor(0xfade55).setBiomeName("Beach").setMinMaxHeight(0.01F, 0.02F).setBiomeColor(0xffb873);
	public static final TFCBiome GRAVEL_BEACH = new TFCBiome(17).setColor(0xfade55).setBiomeName("Gravel Beach").setMinMaxHeight(0.01F, 0.02F).setBiomeColor(0x8f7963);
	public static final TFCBiome HIGH_HILLS = new TFCBiome(3).setBiomeName("High Hills").setMinMaxHeight(0.8F, 1.6F).setBiomeColor(0x044f27);
	public static final TFCBiome PLAINS = new TFCBiome(1).setBiomeName("Plains").setMinMaxHeight(0.1F, 0.16F).setBiomeColor(0x69dfa0);
	public static final TFCBiome SWAMPLAND = new TFCBiome(6).setBiomeName("Swamp").setMinMaxHeight(-0.1F, 0.1F).setBiomeColor(0x1f392b).setLilyPads(8).setWaterPlants(45);
	public static final TFCBiome HIGH_HILLS_EDGE = new TFCBiome(20).setBiomeName("High Hills Edge").setMinMaxHeight(0.2F, 0.4F).setBiomeColor(0x30a767);
	public static final TFCBiome ROLLING_HILLS = new TFCBiome(30).setBiomeName("Rolling Hills").setMinMaxHeight(0.1F, 0.4F).setBiomeColor(0x87b434);
	public static final TFCBiome MOUNTAINS = new TFCBiome(31).setBiomeName("Mountains").setMinMaxHeight(0.8F, 1.6F).setBiomeColor(0x707960);
	public static final TFCBiome MOUNTAINS_EDGE = new TFCBiome(32).setBiomeName("Mountains Edge").setMinMaxHeight(0.4F, 0.8F).setBiomeColor(0xb2bc9f);
	public static final TFCBiome HIGH_PLAINS = new TFCBiome(35).setBiomeName("High Plains").setMinMaxHeight(0.4F, 0.43F).setBiomeColor(0xa6a41c);
	public static final TFCBiome DEEP_OCEAN = new TFCBiome(36).setBiomeName("Deep Ocean").setMinMaxHeight(-1.5F, 0.00001F).setBiomeColor(0x0e055a);
	public static final TFCBiome LAKE = new TFCBiome(2).setBiomeName("Lake").setMinMaxHeight(-0.5F, 0.001F).setBiomeColor(0x4a8e9e).setLilyPads(2);

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

	protected int biomeColor;
	public TFCBiome(int par1)
	{
		super(par1);

		this.topBlock = Blocks.grass;
		this.fillerBlock = Blocks.dirt;
		this.rootHeight = 0.1F;
		this.heightVariation = 0.3F;
		temperatureTFC = 0.5F;
		this.rainfall = 0.5F;
		this.spawnableMonsterList = new ArrayList<SpawnListEntry>();
		this.spawnableCreatureList = new ArrayList<SpawnListEntry>();
		this.spawnableWaterCreatureList = new ArrayList<SpawnListEntry>();

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

		//Default spawns. I didn't delete them so they could be referenced in the future. Nerfing animal spawns.
		this.spawnableCreatureList.clear();
		/*
		this.spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 12, 4, 6));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 10, 2, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 10, 2, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 8, 2, 4));
		 */
		//This is to balance out the spawning, so that entities with weight 1 spawn less
		//this.spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 16, 0, 0));

		this.spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 6, 1, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 4, 1, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityOcelotTFC.class, 4, 1, 1));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));

		this.spawnableWaterCreatureList.clear();
		switch(par1){
		case 0: this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 8, 1, 1)); break;
		case 2: this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityFishTFC.class, 7, 1, 2));
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityFishTFC.class, 12, 0, 0));break;
		default: break;
		}

		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpiderTFC.class, 5, 1, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieTFC.class, 10, 2, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonTFC.class, 8, 1, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeperTFC.class, 3, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySlimeTFC.class, 8, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEndermanTFC.class, 1, 1, 2));

		//getBiomeGenArray()[par1] = this;
		biomeList[par1] = this;
		this.theBiomeDecorator = this.createBiomeDecorator();
	}

	public int getBiomeColor()
	{
		return biomeColor;
	}

	public TFCBiome setBiomeColor(int c)
	{
		biomeColor = c;
		return this;
	}

	/**
	 * Allocate a new BiomeDecorator for this BiomeGenBase
	 */
	@Override
	public BiomeDecoratorTFC createBiomeDecorator()
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

	public TFCBiome setWaterMult(int par1)
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
		this.enableRain = false;
		return this;
	}

	public static WorldGenerator getTreeGen(int i, Boolean j)
	{
		Random r = new Random();
		switch(i)
		{
		case 7:
		{
			if(j)
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,7) : worldGenAshTallTrees;
				else
					return worldGenAshShortTrees;
		}
		case 1:
		{
			if(j)
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,1) :worldGenAspenTallTrees;
				else
					return worldGenAspenShortTrees;
		}
		case 2:
		{
			if(j)
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,2) :worldGenBirchTallTrees;
				else
					return worldGenBirchShortTrees;
		}
		case 3:
		{
			if(j)
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,3) :worldGenChestnutTallTrees;
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
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,5) :worldGenHickoryTallTrees;
				else
					return worldGenHickoryShortTrees;
		}
		case 6:
		{
			if(j)
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,6) :worldGenMapleTallTrees;
				else
					return worldGenMapleShortTrees;
		}
		case 0:
		{
			if(j)
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,0) :worldGenOakTallTrees;
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
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,10) :worldGenSpruceTallTrees;
				else
					return worldGenSpruceShortTrees;
		}
		case 11:
		{
			if(j)
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,11) :worldGenSycamoreTallTrees;
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
				return r.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,13) :worldGenWhiteElmTallTrees;
				else
					return worldGenWhiteElmShortTrees;
		}
		case 14:
		{
			return worldGenWillowShortTrees;
		}
		case 15:
		{
			return new WorldGenCustomShortTrees(false, 15);
		}
		case 16:
		{
			return worldGenAcaciaKoaTrees;
		}
		}
		return null;
	}

	/**
	 * return the biome specified by biomeID, or 0 (ocean) if out of bounds
	 */
	public static TFCBiome getBiome(int id)
	{
		if(biomeList[id] == null)
		{
			TerraFirmaCraft.LOG.warn("Biome ID is null: " + id);
		}
		if (id >= 0 && id <= biomeList.length && biomeList[id] != null)
		{
			return biomeList[id];
		}
		else
		{
			TerraFirmaCraft.LOG.warn("Biome ID is out of bounds: " + id + ", defaulting to 0 (Ocean)");
			return OCEAN;
		}
	}

	public static TFCBiome getBiomeByName(String name)
	{
		for (int i = 0; i < getBiomeGenArray().length; i++)
		{
			if(getBiomeGenArray()[i] != null)
			{
				String n = getBiomeGenArray()[i].biomeName.toLowerCase();
				if(n.equalsIgnoreCase(name))
					return getBiomeGenArray()[i];
			}
		}
		return null;
	}

	public static TFCBiome[] getBiomeGenArray()
	{
		return biomeList.clone();
	}

	public TFCBiome setLilyPads(int i)
	{
		this.theBiomeDecorator.lilyPadPerChunk = i;
		return this;
	}

	public TFCBiome setWaterPlants(int i)
	{
		this.theBiomeDecorator.waterPlantsPerChunk = i;
		return this;
	}
}
