package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenOre implements IWorldGenerator
{
	int Min;
	int Max;
	public WorldGenOre(int min, int max)
	{
		Min = min;
		Max = max;
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		int height = Min-Max;
		//============Copper
		createOreVein(TFCBlocks.Ore.blockID, 0,new int[]{TFCBlocks.StoneIgEx.blockID,-1,Block.sandStone.blockID,-1},//IgEx and Sandstone, veins
				/*rarity*/100,/*veinSize*/80,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Native Copper");

		//============Gold
		createOreVein(TFCBlocks.Ore.blockID, 1,new int[]{TFCBlocks.StoneIgEx.blockID,-1,TFCBlocks.StoneIgIn.blockID,-1},//Ig veins
				/*rarity*/130,/*veinSize*/35,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/20,         world, rand, chunkX, chunkZ, Min, Max, "Native Gold");

		//============Hematite
		createOreVein(TFCBlocks.Ore.blockID, 3,new int[]{TFCBlocks.StoneIgEx.blockID,-1},//IgEx veins
				/*rarity*/100,/*veinSize*/80,/*veinAmt*/42,/*height*/height,/*diameter*/100,/*vDensity*/40,/*hDensity*/30,         world, rand, chunkX, chunkZ, Min, Max, "Hematite");

		//============Silver
		createOreVein(TFCBlocks.Ore.blockID, 4,new int[]{TFCBlocks.StoneIgIn.blockID,0,TFCBlocks.StoneMM.blockID,4},//granite and gneiss, veins
				/*rarity*/100,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/30,         world, rand, chunkX, chunkZ, Min, Max, "Native Silver");

		//============Cassiterite
		createOreVein(TFCBlocks.Ore.blockID, 5,new int[]{TFCBlocks.StoneIgIn.blockID,0},//Granite Veins
				/*rarity*/100,/*veinSize*/85,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/50,         world, rand, chunkX, chunkZ, Min, Max, "Cassiterite");

		//============Cassiterite2
		createOreVein(TFCBlocks.Ore.blockID, 5,new int[]{TFCBlocks.StoneIgEx.blockID,-1},//IgEx Veins
				/*rarity*/140,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Cassiterite");

		//============Galena
		createOreVein(TFCBlocks.Ore.blockID, 6,new int[]{TFCBlocks.StoneIgEx.blockID,-1,TFCBlocks.StoneMM.blockID,-1,
				TFCBlocks.StoneIgIn.blockID,0,TFCBlocks.StoneSed.blockID,5},//igex, mm, granite, limestone as veins
				/*rarity*/120,/*veinSize*/80,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Galena");

		//============Bismuthinite
		createOreVein(TFCBlocks.Ore.blockID, 7,new int[]{TFCBlocks.StoneIgIn.blockID,-1,TFCBlocks.StoneSed.blockID,-1},//Granite Veins
				/*rarity*/120,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Bismuthinite");

		//============Garnierite
		createOreVein(TFCBlocks.Ore.blockID, 8,new int[]{TFCBlocks.StoneIgIn.blockID,2},//Gabbro Veins
				/*rarity*/160,/*veinSize*/40,/*veinAmt*/35,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/10,         world, rand, chunkX, chunkZ, Min, Max, "Garnierite");

		//============Malachite
		createOreVein(TFCBlocks.Ore.blockID, 9,new int[]{TFCBlocks.StoneSed.blockID,5,TFCBlocks.StoneMM.blockID,5},//limestone and marble veins
				/*rarity*/140,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/20,         world, rand, chunkX, chunkZ, Min, Max, "Malachite");

		//============Magnetite
		createOreVein(TFCBlocks.Ore.blockID, 10,new int[]{TFCBlocks.StoneSed.blockID,-1},//Sedimentary, Large Cluster
				/*rarity*/180,/*veinSize*/80,/*veinAmt*/36,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Magnetite");

		//============Limonite
		createOreVein(TFCBlocks.Ore.blockID, 11,new int[]{TFCBlocks.StoneSed.blockID,-1},//Sedimentary, Large Cluster
				/*rarity*/180,/*veinSize*/85,/*veinAmt*/40,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Limonite");

		//============Sphalerite
		createOreVein(TFCBlocks.Ore.blockID, 12,new int[]{TFCBlocks.StoneMM.blockID,-1},//mm, veins
				/*rarity*/140,/*veinSize*/80,/*veinAmt*/38,/*height*/height,/*diameter*/100,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Sphalerite");

		//============Tetrahedrite
		createOreVein(TFCBlocks.Ore.blockID, 13,new int[]{TFCBlocks.StoneIgEx.blockID,-1,TFCBlocks.StoneMM.blockID,-1,
				TFCBlocks.StoneIgIn.blockID,-1,TFCBlocks.StoneSed.blockID,-1},//everything, veins
				/*rarity*/120,/*veinSize*/85,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/30,         world, rand, chunkX, chunkZ, Min, Max, "Tetrahedrite");

		//============Bituminous Coal
		createOre(TFCBlocks.Ore.blockID, 14,new int[]{TFCBlocks.StoneSed.blockID,-1},//sedimentary, veins
				/*rarity*/80,/*veinSize*/80,/*veinAmt*/60,/*height*/height,/*diameter*/200,/*vDensity*/60,/*hDensity*/80,         world, rand, chunkX, chunkZ, Min, Max, "Bituminous Coal");

		//============Lignite
		createOre(TFCBlocks.Ore.blockID, 15,new int[]{TFCBlocks.StoneSed.blockID,-1},//sedimentary, veins
				/*rarity*/80,/*veinSize*/80,/*veinAmt*/60,/*height*/height,/*diameter*/200,/*vDensity*/10,/*hDensity*/80,         world, rand, chunkX, chunkZ, Min, Max, "Lignite");

		//============Kaolinite
		createOre(TFCBlocks.Ore2.blockID, 0,new int[]{TFCBlocks.StoneSed.blockID,-1},//sedimentary, large clusters
				/*rarity*/90,/*veinSize*/20,/*veinAmt*/2,/*height*/height,/*diameter*/40,/*vDensity*/80,/*hDensity*/90,         world, rand, chunkX, chunkZ, Min, Max, "Kaolinite");

		//============Gypsum
		createOre(TFCBlocks.Ore2.blockID, 1,new int[]{TFCBlocks.StoneSed.blockID,-1},//sedimentary, large clusters
				/*rarity*/110,/*veinSize*/40,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/50,/*hDensity*/90,         world, rand, chunkX, chunkZ, Min, Max, "Gypsum");

		//============Satinspar
		createOreVein(TFCBlocks.Ore2.blockID, 2,new int[]{TFCBlocks.Ore2.blockID,1},//gypsum, small clusters
				/*rarity*/2,/*veinSize*/6,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Satinspar");

		//============Selenite
		createOreVein(TFCBlocks.Ore2.blockID, 3,new int[]{TFCBlocks.Ore2.blockID,1},//gypsum, small clusters
				/*rarity*/2,/*veinSize*/6,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Selenite");

		//============Graphite
		createOreVein(TFCBlocks.Ore2.blockID, 4,new int[]{TFCBlocks.StoneMM.blockID,4,TFCBlocks.StoneMM.blockID,0,
				TFCBlocks.StoneMM.blockID,5, TFCBlocks.StoneMM.blockID,3},//gneiss, quartzite, marble, schist, small clusters
				/*rarity*/100,/*veinSize*/6,/*veinAmt*/24,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Graphite");

		//============Kimberlite
		createOreVein(TFCBlocks.Ore2.blockID, 5,new int[]{TFCBlocks.StoneIgIn.blockID,2},//Gabbro, large clusters
				/*rarity*/200,/*veinSize*/40,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/90,         world, rand, chunkX, chunkZ, Min, Max, "Kimberlite");

		//============Petrified Wood
		createOre(TFCBlocks.Ore2.blockID, 6,new int[]{TFCBlocks.StoneSed.blockID,-1},//Sedimentary, small clusters 
				/*rarity*/200,/*veinSize*/10,/*veinAmt*/5,/*height*/height,/*diameter*/20,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Petrified Wood");

		//============Sulfur
		//      createOre(mod_TFCraft.terraOre.blockID, 14,new int[]{mod_TFCraft.terraStoneIgEx.blockID,-1,mod_TFCraft.terraOre2.blockID,8},//igex, gypsum small clusters
		//              /*rarity*/4,/*veinSize*/6,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Jet
		createOre(TFCBlocks.Ore2.blockID, 8,new int[]{TFCBlocks.StoneSed.blockID,-1},//Sedimentary, med clusters 
				/*rarity*/100,/*veinSize*/30,/*veinAmt*/10,/*height*/height,/*diameter*/40,/*vDensity*/60,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Jet");

		//============Microcline
		//        createOre(mod_TFC_Core.terraOre2.blockID, 9,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, large clusters 
		//                /*rarity*/45,/*veinSize*/64,/*veinAmt*/2,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max);

		//============Pitchblende
		createOre(TFCBlocks.Ore2.blockID, 10,new int[]{TFCBlocks.StoneIgIn.blockID,0},//granite, small clusters 
				/*rarity*/100,/*veinSize*/10,/*veinAmt*/10,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Pithcblende");

		//============Cinnabar
		createOreVein(TFCBlocks.Ore2.blockID, 11,new int[]{TFCBlocks.StoneIgEx.blockID,-1,TFCBlocks.StoneSed.blockID,2,
				TFCBlocks.StoneMM.blockID,0},//igex, shale, quartzite small clusters
				/*rarity*/60,/*veinSize*/35,/*veinAmt*/30,/*height*/height,/*diameter*/50,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Cinnabar");

		//============Cryolite
		createOre(TFCBlocks.Ore2.blockID, 12,new int[]{TFCBlocks.StoneIgIn.blockID,0},//granite, small clusters 
				/*rarity*/100,/*veinSize*/10,/*veinAmt*/20,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Cryolite");

		//============Saltpeter
		createOre(TFCBlocks.Ore2.blockID, 13,new int[]{TFCBlocks.StoneSed.blockID,-1},//sed, small clusters 
				/*rarity*/100,/*veinSize*/10,/*veinAmt*/20,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Saltpeter");

		//============Olivine(Out of Order) must come before serpentine
		createOre(TFCBlocks.Ore3.blockID, 1,new int[]{TFCBlocks.StoneIgIn.blockID,2},//gabbro, large clusters 
				/*rarity*/80,/*veinSize*/30,/*veinAmt*/14,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Olivine");

		//============Serpentine
		createOre(TFCBlocks.Ore2.blockID, 14,new int[]{TFCBlocks.Ore3.blockID,1},//Olivine, small clusters 
				/*rarity*/2,/*veinSize*/10,/*veinAmt*/8,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Serpentine");

		//============Sylvite
		createOre(TFCBlocks.Ore2.blockID, 15,new int[]{TFCBlocks.StoneSed.blockID,4},//Rock Salt, large clusters 
				/*rarity*/80,/*veinSize*/40,/*veinAmt*/14,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Sylvite");

		//============Borax
		createOre(TFCBlocks.Ore3.blockID, 0,new int[]{TFCBlocks.StoneSed.blockID,4},//Rock Salt, large clusters 
				/*rarity*/50,/*veinSize*/50,/*veinAmt*/24,/*height*/height,/*diameter*/200,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, Min, Max, "Borax");
		createOre(TFCBlocks.Ore3.blockID, 0,new int[]{TFCBlocks.Ore2.blockID,1},//Gypsum, small clusters 
				/*rarity*/3,/*veinSize*/12,/*veinAmt*/22,/*height*/height,/*diameter*/200,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Borax");
		//============Lapis Lazuli
		createOre(TFCBlocks.Ore3.blockID, 2,new int[]{TFCBlocks.StoneMM.blockID,5},//Marble, small clusters 
				/*rarity*/90,/*veinSize*/20,/*veinAmt*/26,/*height*/height,/*diameter*/60,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Lapis Lazuli");

		//============Platinum -- (out of order) must follow magnetite and olivine
		createOre(TFCBlocks.Ore.blockID, 2,new int[]{TFCBlocks.Ore.blockID,1,TFCBlocks.Ore3.blockID,8},//magnetite, veins
				/*rarity*/10,/*veinSize*/8,/*veinAmt*/10,/*height*/height,/*diameter*/25,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ, Min, Max, "Platinum");

	}

	private static void createOre(int i, int j, int[] Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max, String name)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(int n = 0; n < Layers.length/2;)
			{
				DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 0);
				DataLayer rockLayer2 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 1);
				DataLayer rockLayer3 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 2);
				if((rockLayer1.data1 == Layers[n] && (rockLayer1.data2 == Layers[n+1] || Layers[n+1] == -1)) || 
						(rockLayer2.data1 == Layers[n] && (rockLayer2.data2 == Layers[n+1] || Layers[n+1] == -1)) ||
						(rockLayer3.data1 == Layers[n] && (rockLayer3.data2 == Layers[n+1] || Layers[n+1] == -1)))
				{
					new WorldGenMinableTFC(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity).generate(
							world, rand, chunkX, chunkZ, min, max, name);
				}
				n+=2;
			}
		}
	}

	private static void createOreVein(int i, int j, int[] Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max, String name)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(int n = 0; n < Layers.length/2;)
			{
				DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 0);
				DataLayer rockLayer2 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 1);
				DataLayer rockLayer3 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 2);
				if((rockLayer1.data1 == Layers[n] && (rockLayer1.data2 == Layers[n+1] || Layers[n+1] == -1)) || 
						(rockLayer2.data1 == Layers[n] && (rockLayer2.data2 == Layers[n+1] || Layers[n+1] == -1)) ||
						(rockLayer3.data1 == Layers[n] && (rockLayer3.data2 == Layers[n+1] || Layers[n+1] == -1)))
				{
					new WorldGenMinableTFC(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity).generateVein(
							world, rand, chunkX, chunkZ, min, max, name);
				}
				n+=2;
			}
		}
	}
}
