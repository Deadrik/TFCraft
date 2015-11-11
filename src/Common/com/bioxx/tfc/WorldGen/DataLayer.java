package com.bioxx.tfc.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import com.bioxx.tfc.api.TFCBlocks;

public class DataLayer
{
	public static DataLayer[] layers = new DataLayer[256];

	public static final DataLayer GRANITE = new DataLayer(0, TFCBlocks.stoneIgIn, 0, 0, "Granite");
	public static final DataLayer DIORITE = new DataLayer(1, TFCBlocks.stoneIgIn, 1, 1, "Diorite");
	public static final DataLayer GABBRO = new DataLayer(2, TFCBlocks.stoneIgIn, 2, 2, "Gabbro");
	public static final DataLayer SHALE = new DataLayer(5, TFCBlocks.stoneSed, 0, 3, "Shale");
	public static final DataLayer CLAYSTONE = new DataLayer(6, TFCBlocks.stoneSed, 1, 4, "Claystone");
	public static final DataLayer ROCKSALT = new DataLayer(7, TFCBlocks.stoneSed, 2, 5, "Rock Salt");
	public static final DataLayer LIMESTONE = new DataLayer(8, TFCBlocks.stoneSed, 3, 6, "Limestone");
	public static final DataLayer CONGLOMERATE = new DataLayer(9, TFCBlocks.stoneSed, 4, 7, "Conglomerate");
	public static final DataLayer DOLOMITE = new DataLayer(10, TFCBlocks.stoneSed, 5, 8, "Dolomite");
	public static final DataLayer CHERT = new DataLayer(11, TFCBlocks.stoneSed, 6, 9, "Chert");
	public static final DataLayer CHALK = new DataLayer(12, TFCBlocks.stoneSed, 7, 10, "Chalk");
	public static final DataLayer RHYOLITE = new DataLayer(13, TFCBlocks.stoneIgEx, 0, 11, "Rhyolite");
	public static final DataLayer BASALT = new DataLayer(14, TFCBlocks.stoneIgEx, 1, 12, "Basalt");
	public static final DataLayer ANDESITE = new DataLayer(15, TFCBlocks.stoneIgEx, 2, 13, "Andesite");
	public static final DataLayer DACITE = new DataLayer(16, TFCBlocks.stoneIgEx, 3, 14, "Dacite");
	public static final DataLayer QUARTZITE = new DataLayer(17, TFCBlocks.stoneMM, 0, 15, "Quartzite");
	public static final DataLayer SLATE = new DataLayer(18, TFCBlocks.stoneMM, 1, 16, "Slate");
	public static final DataLayer PHYLLITE = new DataLayer(19, TFCBlocks.stoneMM, 2, 17, "Phyllite");
	public static final DataLayer SCHIST = new DataLayer(20, TFCBlocks.stoneMM, 3, 18, "Schist");
	public static final DataLayer GNEISS = new DataLayer(21, TFCBlocks.stoneMM, 4, 19, "Gneiss");
	public static final DataLayer MARBLE = new DataLayer(22, TFCBlocks.stoneMM, 5, 20, "Marble");

	public static final DataLayer NO_TREE = new DataLayer(29, "No Tree", -1);
	public static final DataLayer ASH = new DataLayer(30, "Ash", 7);
	public static final DataLayer ASPEN = new DataLayer(31, "Aspen", 1);
	public static final DataLayer BIRCH = new DataLayer(32, "Birch", 2);
	public static final DataLayer CHESTNUT = new DataLayer(33, "Chestnut", 3);
	public static final DataLayer DOUGLASFIR = new DataLayer(34, "Douglas Fir", 4);
	public static final DataLayer HICKORY = new DataLayer(35, "Hickory", 5);
	public static final DataLayer KOA = new DataLayer(45, "Acacia Koa", 0);
	public static final DataLayer MAPLE = new DataLayer(36, "Maple", 6);
	public static final DataLayer OAK = new DataLayer(37, "Oak", 0);
	public static final DataLayer PINE = new DataLayer(38, "Pine", 8);
	public static final DataLayer REDWOOD = new DataLayer(39, "Sequoia", 9);
	public static final DataLayer SPRUCE = new DataLayer(40, "Spruce", 10);
	public static final DataLayer SYCAMORE = new DataLayer(41, "Sycamore", 11);
	public static final DataLayer SAVANNAHACACIA = new DataLayer(46, "Acacia Savannah", 0);
	public static final DataLayer WHITECEDAR = new DataLayer(42, "White Cedar", 12);
	public static final DataLayer WHITEELM = new DataLayer(43, "White Elm", 13);
	public static final DataLayer WILLOW = new DataLayer(44, "Willow", 14);

	public static final DataLayer EVT_0_125 = new DataLayer(80, "0.125", 0.125f);
	public static final DataLayer EVT_0_25 = new DataLayer(81, "0.25", 0.25f);
	public static final DataLayer EVT_0_5 = new DataLayer(82, "0.5", 0.5f);
	public static final DataLayer EVT_1 = new DataLayer(83, "1", 1f);
	public static final DataLayer EVT_2 = new DataLayer(84, "2", 2f);
	public static final DataLayer EVT_4 = new DataLayer(85, "4", 4f);
	public static final DataLayer EVT_8 = new DataLayer(86, "8", 8f);
	public static final DataLayer EVT_16 = new DataLayer(87, "16", 16f);

	public static final DataLayer RAIN_62_5 = new DataLayer(90, "62.5", 62.5f);
	public static final DataLayer RAIN_125 = new DataLayer(91, "125", 125f);
	public static final DataLayer RAIN_250 = new DataLayer(92, "250", 250f);
	public static final DataLayer RAIN_500 = new DataLayer(93, "500", 500f);
	public static final DataLayer RAIN_1000 = new DataLayer(94, "1000", 1000f);
	public static final DataLayer RAIN_2000 = new DataLayer(95, "2000", 2000f);
	public static final DataLayer RAIN_4000 = new DataLayer(96, "4000", 4000f);
	public static final DataLayer RAIN_8000 = new DataLayer(97, "8000", 8000f);

	public static final DataLayer SEISMIC_STABLE = new DataLayer(110, 0);
	public static final DataLayer SEISMIC_UNSTABLE = new DataLayer(111, 1);

	public static final DataLayer DRAINAGE_NONE = new DataLayer(120, "None", 0);
	public static final DataLayer DRAINAGE_VERY_POOR = new DataLayer(121, "Very Poor", 1);
	public static final DataLayer DRAINAGE_POOR = new DataLayer(122, "Poor", 2);
	public static final DataLayer DRAINAGE_NORMAL = new DataLayer(123, "Normal", 3);
	public static final DataLayer DRAINAGE_GOOD = new DataLayer(124, "Good", 4);
	public static final DataLayer DRAINAGE_VERY_GOOD = new DataLayer(125, "Very Good", 5);

	public static final DataLayer PH_ACID_HIGH = new DataLayer(130, "High Acidity", 0);
	public static final DataLayer PH_ACID_LOW = new DataLayer(131, "Low acidity", 1);
	public static final DataLayer PH_NEUTRAL = new DataLayer(132, "Neutral", 2);
	public static final DataLayer PH_ALKALINE_LOW = new DataLayer(133, "Low Alkalinity", 3);
	public static final DataLayer PH_ALKALINE_HIGH = new DataLayer(134, "High Alkalinity", 4);

	public int layerID;
	public Block block = Blocks.air;
	public int data1;
	public int data2;//used as metadata in rocks
	public float floatdata1;
	private String name = "";

	public DataLayer(int index, int i)
	{
		layerID = index;
		data1 = i;
		data2 = 0;
		floatdata1 = 0;
		layers[index] = this;
	}

	public DataLayer(int index, Block b, int meta)
	{
		layerID = index;
		block = b;
		data1 = 0;
		data2 = meta;
		floatdata1 = 0;
		layers[index] = this;
	}

	public DataLayer(int index, Block b, int meta, int altMeta, String n)
	{
		layerID = index;
		block = b;
		data1 = altMeta;
		data2 = meta;
		floatdata1 = 0;
		name = n;
		layers[index] = this;
	}

	public DataLayer(int index, String n, Block b)
	{
		layerID = index;
		block = b;
		data1 = 0;
		data2 = 0;
		floatdata1 = 0;
		name = n;
		layers[index] = this;
	}

	public DataLayer(int index, String n, int i)
	{
		layerID = index;
		floatdata1 = 0;
		data1 = i;
		data2 = 0;
		name = n;
		layers[index] = this;
	}

	public DataLayer(int index, String n, float f)
	{
		layerID = index;
		floatdata1 = f;
		data1 = 0;
		data2 = 0;
		name = n;
		layers[index] = this;
	}

	public String getName()
	{
		return name;
	}
}
