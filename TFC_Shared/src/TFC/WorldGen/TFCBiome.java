package TFC.WorldGen;

import java.util.ArrayList;
import java.util.Random;

import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.Helper;
import TFC.Core.TFCSeasons;
import TFC.Core.WeatherManager;
import TFC.Entities.*;
import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenDesert;
import net.minecraft.src.BiomeGenEnd;
import net.minecraft.src.BiomeGenHell;
import net.minecraft.src.BiomeGenMushroomIsland;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.mod_TFC;
import net.minecraft.client.*;

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
    
    //public static TFCBiome[] biomeList = new TFCBiome[256];

    /** An array of all the biomes, indexed by biome id. */
    public static final TFCBiome ocean = new BiomeGenOceanTFC(0).setBiomeName("Ocean").setMinMaxHeight(-0.9F, 0.1F).SetWaterMult(ForestWater).setTemperatureRainfall(26, 0.4F);
    public static final TFCBiome river = new BiomeGenRiverTFC(7).setColor(ForestColor).setBiomeName("River Hills1").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    
    public static final TFCBiome beach = (new BiomeGenBeachTFC(16)).setColor(0xfade55).setBiomeName("Beach").setTemperatureRainfall(1.9F, 0.4F).setMinMaxHeight(0.0F, 0.1F).SetWaterMult(ForestWater);

    public static final TFCBiome jungle = (new BiomeGenJungleTFC(21)).setColor(5470985).setBiomeName("Jungle").SetWaterMult(5470985).setTemperatureRainfall(42F, 0.9F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome jungleHills = (new BiomeGenJungleTFC(22)).setColor(2900485).setBiomeName("JungleHills").SetWaterMult(5470985).setTemperatureRainfall(42F, 0.9F).setMinMaxHeight(0.3F, 0.8F);

    public static final TFCBiome desert = (new BiomeGenDesertTFC(2)).setBiomeName("Desert1").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(36F, 0.0F).setMinMaxHeight(0.1F, 0.15F);
    public static final TFCBiome desert2 = (new BiomeGenDesertTFC(53)).setBiomeName("Desert2").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(37F, 0.0F).setMinMaxHeight(0.1F, 0.25F);
    public static final TFCBiome desert3 = (new BiomeGenDesertTFC(59)).setBiomeName("Desert3").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(38F, 0.0F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome desert4 = (new BiomeGenDesertTFC(60)).setBiomeName("Desert4").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(39F, 0.0F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome desert5 = (new BiomeGenDesertTFC(61)).setBiomeName("Desert5").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(40F, 0.0F).setMinMaxHeight(0.1F, 0.25F);
    public static final TFCBiome desert6 = (new BiomeGenDesertTFC(62)).setBiomeName("Desert6").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(41F, 0.0F).setMinMaxHeight(0.1F, 0.3F);
    public static final TFCBiome desert7 = (new BiomeGenDesertTFC(63)).setBiomeName("Desert7").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(42F, 0.0F).setMinMaxHeight(0.1F, 0.25F);
    public static final TFCBiome desert8 = (new BiomeGenDesertTFC(64)).setBiomeName("Desert8").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(43F, 0.0F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome desert9 = (new BiomeGenDesertTFC(65)).setBiomeName("Desert9").SetWaterMult(DesertWater).setDisableRain().setTemperatureRainfall(44F, 0.0F).setMinMaxHeight(0.1F, 0.2F);

    public static final TFCBiome extremeHills = (new BiomeGenHillsTFC(3)).setColor(HillsColor).setBiomeName("Hills1").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.3F).setTemperatureRainfall(18, 0.3F);
    public static final TFCBiome hills2 = (new BiomeGenHillsTFC(112)).setColor(HillsColor).setBiomeName("Hills2").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.4F).setTemperatureRainfall(18, 0.3F);
    public static final TFCBiome hills3 = (new BiomeGenHillsTFC(23)).setColor(HillsColor).setBiomeName("Hills3").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.8F).setTemperatureRainfall(18, 0.3F);
    public static final TFCBiome hills4 = (new BiomeGenHillsTFC(24)).setColor(HillsColor).setBiomeName("Hills4").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.45F).setTemperatureRainfall(18F, 0.3F);
    public static final TFCBiome hills5 = (new BiomeGenHillsTFC(25)).setColor(HillsColor).setBiomeName("Hills5").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.4F).setTemperatureRainfall(18F, 0.3F);
    public static final TFCBiome hills6 = (new BiomeGenHillsTFC(26)).setColor(HillsColor).setBiomeName("Hills6").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.5F).setTemperatureRainfall(18F, 0.3F);
    public static final TFCBiome hills7 = (new BiomeGenHillsTFC(27)).setColor(HillsColor).setBiomeName("Hills7").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.8F).setTemperatureRainfall(19F, 0.3F);
    public static final TFCBiome hills8 = (new BiomeGenHillsTFC(28)).setColor(HillsColor).setBiomeName("Hills8").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.5F).setTemperatureRainfall(20F, 0.3F);
    public static final TFCBiome hills9 = (new BiomeGenHillsTFC(29)).setColor(HillsColor).setBiomeName("Hills9").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.8F).setTemperatureRainfall(21F, 0.3F);
    public static final TFCBiome hills10 = (new BiomeGenHillsTFC(30)).setColor(HillsColor).setBiomeName("Hills10").SetWaterMult(HillsWater).setMinMaxHeight(0.4F, 1.3F).setTemperatureRainfall(22F, 0.3F);

    public static final TFCBiome forest = (new BiomeGenForestTFC(4)).setColor(ForestColor).setBiomeName("Forest1").SetWaterMult(ForestWater).setTemperatureRainfall(24, 0.8F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome forest2 = (new BiomeGenForestTFC(31)).setColor(ForestColor).setBiomeName("Forest2").SetWaterMult(ForestWater).setTemperatureRainfall(25, 0.8F).setMinMaxHeight(0.3F, 0.5F);
    public static final TFCBiome forest3 = (new BiomeGenForestTFC(32)).setColor(ForestColor).setBiomeName("Forest3").SetWaterMult(ForestWater).setTemperatureRainfall(26, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome forest4 = (new BiomeGenForestTFC(33)).setColor(ForestColor).setBiomeName("Forest4").SetWaterMult(ForestWater).setTemperatureRainfall(27, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome forest5 = (new BiomeGenForestTFC(34)).setColor(ForestColor).setBiomeName("Forest5").SetWaterMult(ForestWater).setTemperatureRainfall(28, 0.8F).setMinMaxHeight(0.3F, 0.5F);
    public static final TFCBiome forest6 = (new BiomeGenForestTFC(35)).setColor(ForestColor).setBiomeName("Forest6").SetWaterMult(ForestWater).setTemperatureRainfall(29, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome forest7 = (new BiomeGenForestTFC(36)).setColor(ForestColor).setBiomeName("Forest7").SetWaterMult(ForestWater).setTemperatureRainfall(30, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome forest8 = (new BiomeGenForestTFC(37)).setColor(ForestColor).setBiomeName("Forest8").SetWaterMult(ForestWater).setTemperatureRainfall(31, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome forest9 = (new BiomeGenForestTFC(38)).setColor(ForestColor).setBiomeName("Forest9").SetWaterMult(ForestWater).setTemperatureRainfall(32, 0.8F).setMinMaxHeight(0.2F, 0.4F);

    public static final TFCBiome plains = (new BiomeGenPlainsTFC(1)).setColor(PlainsColor).setBiomeName("Plains1").SetWaterMult(ForestWater).setTemperatureRainfall(28, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains2 = (new BiomeGenPlainsTFC(39)).setColor(PlainsColor).setBiomeName("Plains2").SetWaterMult(PlainsWater).setTemperatureRainfall(28, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains3 = (new BiomeGenPlainsTFC(40)).setColor(PlainsColor).setBiomeName("Plains3").SetWaterMult(PlainsWater).setTemperatureRainfall(28, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains4 = (new BiomeGenPlainsTFC(41)).setColor(PlainsColor).setBiomeName("Plains4").SetWaterMult(PlainsWater).setTemperatureRainfall(28, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains5 = (new BiomeGenPlainsTFC(42)).setColor(PlainsColor).setBiomeName("Plains5").SetWaterMult(PlainsWater).setTemperatureRainfall(29, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains6 = (new BiomeGenPlainsTFC(43)).setColor(PlainsColor).setBiomeName("Plains6").SetWaterMult(PlainsWater).setTemperatureRainfall(30, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains7 = (new BiomeGenPlainsTFC(44)).setColor(PlainsColor).setBiomeName("Plains7").SetWaterMult(PlainsWater).setTemperatureRainfall(31, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains8 = (new BiomeGenPlainsTFC(45)).setColor(PlainsColor).setBiomeName("Plains8").SetWaterMult(PlainsWater).setTemperatureRainfall(32, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains9 = (new BiomeGenPlainsTFC(46)).setColor(PlainsColor).setBiomeName("Plains9").SetWaterMult(PlainsWater).setTemperatureRainfall(33, 0.4F).setMinMaxHeight(0.1F, 0.2F);
    public static final TFCBiome plains10 = (new BiomeGenPlainsTFC(47)).setColor(PlainsColor).setBiomeName("Plains10").SetWaterMult(PlainsWater).setTemperatureRainfall(34, 0.4F).setMinMaxHeight(0.1F, 0.2F);

    public static final TFCBiome taiga = (new BiomeGenTaigaTFC(5)).setColor(TaigaColor).setBiomeName("Taiga1").SetWaterMult(TaigaWater).setTemperatureRainfall(6F, 0.8F).setMinMaxHeight(0.2F, 0.5F);
    public static final TFCBiome taiga2 = (new BiomeGenTaigaTFC(49)).setColor(TaigaColor).setBiomeName("Taiga2").SetWaterMult(TaigaWater).setTemperatureRainfall(7F, 0.9F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome taiga3 = (new BiomeGenTaigaTFC(50)).setColor(TaigaColor).setBiomeName("Taiga3").SetWaterMult(TaigaWater).setTemperatureRainfall(8F, 0.9F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome taiga4 = (new BiomeGenTaigaTFC(51)).setColor(TaigaColor).setBiomeName("Taiga4").SetWaterMult(TaigaWater).setTemperatureRainfall(9F, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome taiga5 = (new BiomeGenTaigaTFC(52)).setColor(TaigaColor).setBiomeName("Taiga5").SetWaterMult(TaigaWater).setTemperatureRainfall(10F, 0.8F).setMinMaxHeight(0.3F, 0.5F);
    public static final TFCBiome taiga6 = (new BiomeGenTaigaTFC(66)).setColor(TaigaColor).setBiomeName("Taiga6").SetWaterMult(TaigaWater).setTemperatureRainfall(11F, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome taiga7 = (new BiomeGenTaigaTFC(67)).setColor(TaigaColor).setBiomeName("Taiga7").SetWaterMult(TaigaWater).setTemperatureRainfall(12F, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome taiga8 = (new BiomeGenTaigaTFC(68)).setColor(TaigaColor).setBiomeName("Taiga8").SetWaterMult(TaigaWater).setTemperatureRainfall(13F, 0.8F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome taiga9 = (new BiomeGenTaigaTFC(69)).setColor(TaigaColor).setBiomeName("Taiga9").SetWaterMult(TaigaWater).setTemperatureRainfall(14F, 0.95F).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome taiga10 = (new BiomeGenTaigaTFC(48)).setColor(TaigaColor).setBiomeName("Taiga10").SetWaterMult(TaigaWater).setTemperatureRainfall(15F, 0.9F).setMinMaxHeight(0.2F, 0.4F);

    public static final TFCBiome swampland = (new BiomeGenSwampTFC(6)).setColor(SwampColor).setBiomeName("Swamp1").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(26, 0.9F);
    public static final TFCBiome swamp2 = (new BiomeGenSwampTFC(55)).setColor(SwampColor).setBiomeName("Swamp2").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(27, 0.9F);
    public static final TFCBiome swamp3 = (new BiomeGenSwampTFC(56)).setColor(SwampColor).setBiomeName("Swamp3").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(28, 0.9F);
    public static final TFCBiome swamp4 = (new BiomeGenSwampTFC(57)).setColor(SwampColor).setBiomeName("Swamp4").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(29, 0.9F);
    public static final TFCBiome swamp5 = (new BiomeGenSwampTFC(58)).setColor(SwampColor).setBiomeName("Swamp5").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(30, 0.9F);
    public static final TFCBiome swamp6 = (new BiomeGenSwampTFC(70)).setColor(SwampColor).setBiomeName("Swamp6").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(31, 0.9F);
    public static final TFCBiome swamp7 = (new BiomeGenSwampTFC(71)).setColor(SwampColor).setBiomeName("Swamp7").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(32, 0.9F);
    public static final TFCBiome swamp8 = (new BiomeGenSwampTFC(72)).setColor(SwampColor).setBiomeName("Swamp8").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(33, 0.9F);
    public static final TFCBiome swamp9 = (new BiomeGenSwampTFC(73)).setColor(SwampColor).setBiomeName("Swamp9").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(34, 0.9F);
    public static final TFCBiome swamp10 = (new BiomeGenSwampTFC(54)).setColor(SwampColor).setBiomeName("Swamp10").SetWaterMult(SwampWater).setMinMaxHeight(-0.1F, 0.15F).setTemperatureRainfall(35, 0.9F);

    public static final TFCBiome extremeHillsEdge = (new BiomeGenHillsEdgeTFC(20)).setColor(HillsColor).setBiomeName("Hills1 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge2 = (new BiomeGenHillsEdgeTFC(74)).setColor(HillsColor).setBiomeName("Hills2 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge3 = (new BiomeGenHillsEdgeTFC(75)).setColor(HillsColor).setBiomeName("Hills3 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge4 = (new BiomeGenHillsEdgeTFC(76)).setColor(HillsColor).setBiomeName("Hills4 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge5 = (new BiomeGenHillsEdgeTFC(77)).setColor(HillsColor).setBiomeName("Hills5 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge6 = (new BiomeGenHillsEdgeTFC(78)).setColor(HillsColor).setBiomeName("Hills6 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge7 = (new BiomeGenHillsEdgeTFC(79)).setColor(HillsColor).setBiomeName("Hills7 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge8 = (new BiomeGenHillsEdgeTFC(80)).setColor(HillsColor).setBiomeName("Hills8 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge9 = (new BiomeGenHillsEdgeTFC(81)).setColor(HillsColor).setBiomeName("Hills9 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);
    public static final TFCBiome HillsEdge10 = (new BiomeGenHillsEdgeTFC(82)).setColor(HillsColor).setBiomeName("Hills10 Edge").SetWaterMult(HillsWater).setMinMaxHeight(0.2F, 0.4F);

    public static final TFCBiome river2 = (new BiomeGenRiverTFC(83)).setColor(SwampColor).setBiomeName("River Swamp1").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river3 = (new BiomeGenRiverTFC(84)).setColor(SwampColor).setBiomeName("River Swamp2").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river4 = (new BiomeGenRiverTFC(85)).setColor(SwampColor).setBiomeName("River Swamp3").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river5 = (new BiomeGenRiverTFC(86)).setColor(SwampColor).setBiomeName("River Swamp4").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river6 = (new BiomeGenRiverTFC(87)).setColor(SwampColor).setBiomeName("River Swamp5").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river7 = (new BiomeGenRiverTFC(88)).setColor(SwampColor).setBiomeName("River Swamp6").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river8 = (new BiomeGenRiverTFC(89)).setColor(SwampColor).setBiomeName("River Swamp7").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river9 = (new BiomeGenRiverTFC(90)).setColor(SwampColor).setBiomeName("River Swamp8").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river10 = (new BiomeGenRiverTFC(91)).setColor(SwampColor).setBiomeName("River Swamp9").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river11 = (new BiomeGenRiverTFC(92)).setColor(SwampColor).setBiomeName("River Swamp10").SetWaterMult(SwampWater).setMinMaxHeight(riverDepthMin, riverDepthMax);

    public static final TFCBiome river12 = (new BiomeGenRiverTFC(93)).setColor(TaigaColor).setBiomeName("River Taiga1").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river13 = (new BiomeGenRiverTFC(94)).setColor(TaigaColor).setBiomeName("River Taiga2").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river14 = (new BiomeGenRiverTFC(95)).setColor(TaigaColor).setBiomeName("River Taiga3").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river15 = (new BiomeGenRiverTFC(96)).setColor(TaigaColor).setBiomeName("River Taiga4").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river16 = (new BiomeGenRiverTFC(97)).setColor(TaigaColor).setBiomeName("River Taiga5").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river17 = (new BiomeGenRiverTFC(98)).setColor(TaigaColor).setBiomeName("River Taiga6").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river18 = (new BiomeGenRiverTFC(99)).setColor(TaigaColor).setBiomeName("River Taiga7").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river19 = (new BiomeGenRiverTFC(100)).setColor(TaigaColor).setBiomeName("River Taiga8").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river20 = (new BiomeGenRiverTFC(101)).setColor(TaigaColor).setBiomeName("River Taiga9").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river21 = (new BiomeGenRiverTFC(102)).setColor(TaigaColor).setBiomeName("River Taiga10").SetWaterMult(TaigaWater).setMinMaxHeight(riverDepthMin, riverDepthMax);

    public static final TFCBiome river22 = (new BiomeGenRiverTFC(103)).setColor(ForestColor).setBiomeName("River Forest1").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river23 = (new BiomeGenRiverTFC(104)).setColor(ForestColor).setBiomeName("River Forest2").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river24 = (new BiomeGenRiverTFC(105)).setColor(ForestColor).setBiomeName("River Forest3").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river25 = (new BiomeGenRiverTFC(106)).setColor(ForestColor).setBiomeName("River Forest4").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river26 = (new BiomeGenRiverTFC(107)).setColor(ForestColor).setBiomeName("River Forest5").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river27 = (new BiomeGenRiverTFC(108)).setColor(ForestColor).setBiomeName("River Forest6").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river28 = (new BiomeGenRiverTFC(109)).setColor(ForestColor).setBiomeName("River Forest7").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river29 = (new BiomeGenRiverTFC(110)).setColor(ForestColor).setBiomeName("River Forest8").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river30 = (new BiomeGenRiverTFC(111)).setColor(ForestColor).setBiomeName("River Forest9").SetWaterMult(ForestWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    //<-----------------------------------------------------------------------------------Hills2---------------------------------------------------------------------------------->
    public static final TFCBiome river31 = (new BiomeGenRiverTFC(113)).setColor(PlainsColor).setBiomeName("River plains1").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river32 = (new BiomeGenRiverTFC(114)).setColor(PlainsColor).setBiomeName("River plains2").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river33 = (new BiomeGenRiverTFC(115)).setColor(PlainsColor).setBiomeName("River plains3").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river34 = (new BiomeGenRiverTFC(116)).setColor(PlainsColor).setBiomeName("River plains4").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river35 = (new BiomeGenRiverTFC(117)).setColor(PlainsColor).setBiomeName("River plains5").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river36 = (new BiomeGenRiverTFC(118)).setColor(PlainsColor).setBiomeName("River plains6").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river37 = (new BiomeGenRiverTFC(119)).setColor(PlainsColor).setBiomeName("River plains7").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river38 = (new BiomeGenRiverTFC(120)).setColor(PlainsColor).setBiomeName("River plains8").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river39 = (new BiomeGenRiverTFC(121)).setColor(PlainsColor).setBiomeName("River plains9").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river40 = (new BiomeGenRiverTFC(122)).setColor(PlainsColor).setBiomeName("River plains10").SetWaterMult(PlainsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);

    public static final TFCBiome river41 = (new BiomeGenRiverTFC(123)).setColor(HillsColor).setBiomeName("River hills1").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river42 = (new BiomeGenRiverTFC(124)).setColor(HillsColor).setBiomeName("River hills2").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river43 = (new BiomeGenRiverTFC(125)).setColor(HillsColor).setBiomeName("River hills3").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river44 = (new BiomeGenRiverTFC(126)).setColor(HillsColor).setBiomeName("River hills4").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river45 = (new BiomeGenRiverTFC(127)).setColor(HillsColor).setBiomeName("River hills5").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river46 = (new BiomeGenRiverTFC(128)).setColor(HillsColor).setBiomeName("River hills6").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river47 = (new BiomeGenRiverTFC(129)).setColor(HillsColor).setBiomeName("River hills7").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river48 = (new BiomeGenRiverTFC(130)).setColor(HillsColor).setBiomeName("River hills8").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river49 = (new BiomeGenRiverTFC(131)).setColor(HillsColor).setBiomeName("River hills9").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river50 = (new BiomeGenRiverTFC(132)).setColor(HillsColor).setBiomeName("River hills10").SetWaterMult(HillsWater).setMinMaxHeight(riverDepthMin, riverDepthMax);

    public static final TFCBiome river51 = (new BiomeGenRiverTFC(133)).setColor(DesertColor).setBiomeName("River desert1").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river52 = (new BiomeGenRiverTFC(134)).setColor(DesertColor).setBiomeName("River desert2").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river53 = (new BiomeGenRiverTFC(135)).setColor(DesertColor).setBiomeName("River desert3").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river54 = (new BiomeGenRiverTFC(136)).setColor(DesertColor).setBiomeName("River desert4").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river55 = (new BiomeGenRiverTFC(137)).setColor(DesertColor).setBiomeName("River desert5").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river56 = (new BiomeGenRiverTFC(138)).setColor(DesertColor).setBiomeName("River desert6").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river57 = (new BiomeGenRiverTFC(139)).setColor(DesertColor).setBiomeName("River desert7").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river58 = (new BiomeGenRiverTFC(140)).setColor(DesertColor).setBiomeName("River desert8").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    public static final TFCBiome river59 = (new BiomeGenRiverTFC(141)).setColor(DesertColor).setBiomeName("River desert9").setDisableRain().SetWaterMult(DesertWater).setMinMaxHeight(riverDepthMin, riverDepthMax);
    
    protected WorldGenCustomTallTrees worldGenAshTallTrees;
    protected WorldGenCustomTallTrees worldGenAspenTallTrees;
    protected WorldGenCustomTallTrees worldGenBirchTallTrees;
    protected WorldGenCustomTallTrees worldGenChestnutTallTrees;
    protected WorldGenDouglasFir worldGenDouglasFirTallTrees;
    protected WorldGenCustomTallTrees worldGenHickoryTallTrees;
    protected WorldGenCustomMapleTallTrees worldGenMapleTallTrees;
    protected WorldGenCustomTallTrees worldGenOakTallTrees;
    protected WorldGenCustomTallTrees worldGenPineTallTrees;
    protected WorldGenCustomRedwoodTrees worldGenRedwoodTallTrees;
    protected WorldGenCustomTallTrees worldGenSpruceTallTrees;
    protected WorldGenCustomTallTrees worldGenSycamoreTallTrees;
    protected WorldGenCustomCedarTrees worldGenWhiteCedarTallTrees;
    protected WorldGenCustomTallTrees worldGenWhiteElmTallTrees;

    protected WorldGenCustomShortTrees worldGenAshShortTrees;
    protected WorldGenCustomShortTrees worldGenAspenShortTrees;
    protected WorldGenCustomShortTrees worldGenBirchShortTrees;
    protected WorldGenCustomShortTrees worldGenChestnutShortTrees;
    protected WorldGenDouglasFir worldGenDouglasFirShortTrees;
    protected WorldGenCustomShortTrees worldGenHickoryShortTrees;
    protected WorldGenCustomMapleShortTrees worldGenMapleShortTrees;
    protected WorldGenCustomShortTrees worldGenOakShortTrees;
    protected WorldGenCustomShortTrees worldGenPineShortTrees;
    protected WorldGenCustomRedwoodTrees worldGenRedwoodShortTrees;
    protected WorldGenCustomShortTrees worldGenSpruceShortTrees;
    protected WorldGenCustomShortTrees worldGenSycamoreShortTrees;
    protected WorldGenCustomShortTrees worldGenWhiteElmShortTrees;
    protected WorldGenCustomWillowTrees worldGenWillowShortTrees;

    public int SurfaceType;
    public int SurfaceMeta;
    public int Layer1;
    public int Layer1Type;
    public int Layer1Meta;
    public int Layer2;
    public int Layer2Type;
    public int Layer2Meta;
    public int Layer3;
    public int Layer3Type;
    public int Layer3Meta;
    
    public TFCBiome(int par1)
    {
        super(par1);
        
        this.topBlock = (byte)Block.grass.blockID;
        this.fillerBlock = (byte)Block.dirt.blockID;
        this.minHeight = 0.1F;
        this.maxHeight = 0.3F;
        this.temperature = 0.5F;
        this.rainfall = 0.5F;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.enableRain = true;
        
        super.biomeList[par1] = this;
        SurfaceType = mod_TFC.terraStoneIgIn.blockID;
        SurfaceMeta = 0;
        Layer1 = 56;
        Layer1Type = mod_TFC.terraStoneIgIn.blockID;
        Layer1Meta = 0;
        Layer2 = 40;
        Layer2Type = mod_TFC.terraStoneIgIn.blockID;
        Layer2Meta = 0;
        Layer3 = 20;
        Layer3Type = mod_TFC.terraStoneIgIn.blockID;
        Layer3Meta = 0;

        worldGenAshTallTrees = new WorldGenCustomTallTrees(false,7);
        worldGenAspenTallTrees = new WorldGenCustomTallTrees(false,1);
        worldGenBirchTallTrees = new WorldGenCustomTallTrees(false,2);
        worldGenChestnutTallTrees = new WorldGenCustomTallTrees(false,3);
        worldGenDouglasFirTallTrees = new WorldGenDouglasFir(false,4);
        worldGenHickoryTallTrees = new WorldGenCustomTallTrees(false,5);
        worldGenMapleTallTrees = new WorldGenCustomMapleTallTrees(false,6);
        worldGenOakTallTrees = new WorldGenCustomTallTrees(false,0);
        worldGenPineTallTrees = new WorldGenCustomTallTrees(false,8);
        worldGenRedwoodTallTrees = new WorldGenCustomRedwoodTrees(false,9);
        worldGenSpruceTallTrees = new WorldGenCustomTallTrees(false,10);
        worldGenSycamoreTallTrees = new WorldGenCustomTallTrees(false,11);
        worldGenWhiteCedarTallTrees = new WorldGenCustomCedarTrees(false,12);
        worldGenWhiteElmTallTrees = new WorldGenCustomTallTrees(false,13);

        worldGenAshShortTrees = new WorldGenCustomShortTrees(false,7);
        worldGenAspenShortTrees = new WorldGenCustomShortTrees(false,1);
        worldGenBirchShortTrees = new WorldGenCustomShortTrees(false,2);
        worldGenChestnutShortTrees = new WorldGenCustomShortTrees(false,3);
        worldGenDouglasFirShortTrees = new WorldGenDouglasFir(false,4);
        worldGenHickoryShortTrees = new WorldGenCustomShortTrees(false,5);
        worldGenMapleShortTrees = new WorldGenCustomMapleShortTrees(false,6);
        worldGenOakShortTrees = new WorldGenCustomShortTrees(false,0);
        worldGenPineShortTrees = new WorldGenCustomShortTrees(false,8);
        worldGenRedwoodShortTrees = new WorldGenCustomRedwoodTrees(false,9);
        worldGenSpruceShortTrees = new WorldGenCustomShortTrees(false,10);
        worldGenSycamoreShortTrees = new WorldGenCustomShortTrees(false,11);
        worldGenWhiteElmShortTrees = new WorldGenCustomShortTrees(false,13);
        worldGenWillowShortTrees = new WorldGenCustomWillowTrees(false,14);
        
        this.biomeDecorator = this.createBiomeDecorator();
        
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 12, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 8, 4, 4));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 10, 4, 4));
    }
    
    /**
     * Allocate a new BiomeDecorator for this BiomeGenBase
     */
    public BiomeDecoratorTFC createBiomeDecorator()
    {
        return new BiomeDecoratorTFC(this);
    }
    
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        this.biomeDecorator.decorate(par1World, par2Random, par3, par4);
    }
    
    /**
     * Sets the minimum and maximum height of this biome. Seems to go from -2.0 to 2.0.
     */
    public TFCBiome setMinMaxHeight(float par1, float par2)
    {
        this.minHeight = par1-2.7F;
        this.maxHeight = par2-2.7F;
        return this;
    }
    
    private TFCBiome setTemperatureRainfall(float par1, float par2)
    {
            this.temperature = par1;
            this.rainfall = par2;
            return this;
    }
    
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
    
    public TFCBiome setColor(int par1)
    {
        this.color = par1;
        return this;
    }
    
    /**
     * Disable the rain for the biome.
     */
    protected TFCBiome setDisableRain()
    {
        this.enableRain = false;
        return this;
    }
    
    public int getIntRainfall()
    {
        if(TFCSeasons.getMonth() < 3)
            return (int)((this.rainfall+0.1F) * 65536.0F);
        else
            return (int)(this.rainfall * 65536.0F);
    }
    
    /**
     * Gets an integer representation of this biome's temperature
     */
    public int getIntTemperature()
    {
        return (int) (getFloatTemperature() * 65536.0F);   
    }

    /**
     * Gets a floating point representation of this biome's temperature
     */    
    public float getFloatTemperature()
    {
        float temp = getTemp();
        if(temp <= 0)
        {
            return 0;
        }
        else
            return 0.3f+temp/40;
    }
    
    private float getTemp()
    {
        float day = (float)TFCSeasons.getDayOfYear()+1;
        Random R = new Random((int)day * TFCSeasons.currentYear);
        float mod = getMonthTemp(TFCSeasons.currentMonth);
        float modLast = getMonthTemp(TFCSeasons.lastMonth);
        int day2 = TFCSeasons.getDayOfMonth();
        int hour = (int) TFCSeasons.getHour()-3;
        if(hour < 0)
            hour = 23 + hour;
        float hourMod = 0;
        if(hour < 12)
            hourMod = ((float)hour/11) * 0.2F;
        else
            hourMod = 0.2F - (((float)(hour-12)/11) * 0.2F);
        
        float m = 0;
        float temp = 0;
        
        if(modLast > mod)
        {
            m = ((modLast-mod)/30)*day2;
            m = (modLast - m);
            temp = ((this.temperature+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(this.temperature+WeatherManager.getInstance().getDailyTemp())); 
        }
        else
        {
            m = ((mod-modLast)/30)*day2;
            m = (modLast + m);
            temp = ((this.temperature+WeatherManager.getInstance().getDailyTemp()) * m)+(hourMod*(this.temperature+WeatherManager.getInstance().getDailyTemp()));
        }
        
        return temp;
    }
    
    public final float getHeightAdjustedTemperature(int y)
    {
        float temp = getTemp();
        
        if(y > 180)
            temp -= temp * (y-180)/90;
        return temp;
    }
    
    /**
     * Gets a floating point representation of this biome's rainfall
     */
    public float getFloatRainfall()
    {
        if(TFCSeasons.currentMonth < 3 || TFCSeasons.currentMonth >= 9)
            return this.rainfall+0.1F;
        else
            return this.rainfall;
    }
    
    /**
     * Provides the basic grass color based on the biome temperature and rainfall
     */
    public int getBiomeGrassColor()
    {
        if(TFCSeasons.currentMonth < 6)
        {
        double var1 = (double)Helper.clamp_float(this.getFloatTemperature(), 0.0F, 1.0F);
        double var3 = (double)Helper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
        return ColorizerGrassTFC.getGrassColor(var1, var3);
        }
        else
            return ColorizerGrassTFC.getGrassDead();
    }

    /**
     * Provides the basic foliage color based on the biome temperature and rainfall
     */
    public int getBiomeFoliageColor()
    {
        if(TFCSeasons.currentMonth < 9)
        {
        double var1 = (double)Helper.clamp_float(this.getFloatTemperature(), 0.0F, 1.0F);
        double var3 = (double)Helper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
        return ColorizerFoliageTFC.getFoliageColor(var1, var3);
        }
        else
            return ColorizerFoliageTFC.getFoliageDead();
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
    
    public WorldGenerator getTreeGen(int i, Boolean j)
    {
        Random R = new Random();
        if(i == 15)
            i = 1;
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
                    return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,4) :worldGenDouglasFirTallTrees;
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
                    return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,8) :worldGenPineTallTrees;
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
                    return R.nextInt(20) == 0 ? new WorldGenCustomBigTree(false,9) :worldGenRedwoodTallTrees;
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
        }
        return worldGenAshShortTrees;
    }

    public int GrassID = mod_TFC.terraGrass.blockID;
    public int DirtID = mod_TFC.terraDirt.blockID;
    public int ClayID = mod_TFC.terraClay.blockID;
    public int ClayGrassID = mod_TFC.terraClayGrass.blockID;
    public int TopSoilMetaID = 0;

    protected TFCBiome setTopSoil(int grassID, int dirtID, int clayID, int clayGrassID, int meta)
    {
        this.GrassID = grassID;
        this.DirtID = dirtID;
        this.ClayID = clayID;
        this.ClayGrassID = clayGrassID;
        return this;
    }

    public WorldGenerator getRandomWorldGenForTrees(Random randomGenerator,World currentWorld) 
    {
        return (WorldGenerator)(randomGenerator.nextInt(10) == 0 ? this.worldGenBigTree : this.worldGenTrees);
    }

    public static boolean[] deck = new boolean[24];
    public void SetupStone(World world, Random R)
    {
        int s = R.nextInt(24);
        int l1 = R.nextInt(24);
        int l2 = R.nextInt(24);
        int l3 = R.nextInt(24);
        
        deck[l1] = true;
        deck[l2] = true;
        deck[l3] = true;
        
        int[] surface = getRock(l1);
        int[] layer1 = getRock(l1);
        int[] layer2 = getRock(l2);
        int[] layer3 = getRock(l3);

        SurfaceType =   surface[0];
        SurfaceMeta =   surface[1];  
        Layer1Type =    layer1[0];
        Layer1Meta =    layer1[1];
        Layer2Type =    layer2[0];
        Layer2Meta =    layer2[1];
        Layer3Type =    layer3[0];
        Layer3Meta =    layer3[1];

        Layer3 = 55 + R.nextInt(10);
        Layer2 = 110 + R.nextInt(10);
        Layer1 = 130;
        
        if(SurfaceType == mod_TFC.terraStoneIgIn.blockID)
            TopSoilMetaID = layer1[1];
        else if(SurfaceType == mod_TFC.terraStoneSed.blockID)
            TopSoilMetaID = layer1[1]+3;
        else if(SurfaceType == mod_TFC.terraStoneIgEx.blockID)
            TopSoilMetaID = layer1[1]+13;
        else
            TopSoilMetaID = layer1[1]+17;
            

        if(TopSoilMetaID < 16)
        {
            this.GrassID = mod_TFC.terraGrass.blockID;
            this.DirtID = mod_TFC.terraDirt.blockID;
            this.ClayID = mod_TFC.terraClay.blockID;
            this.ClayGrassID = mod_TFC.terraClayGrass.blockID;
        }
        else
        {
            this.GrassID = mod_TFC.terraGrass2.blockID;
            this.DirtID = mod_TFC.terraDirt2.blockID;
            this.ClayID = mod_TFC.terraClay2.blockID;
            this.ClayGrassID = mod_TFC.terraClayGrass2.blockID;
        }
        if(this.biomeName.toLowerCase().contains("desert"))
        {
            this.GrassID = Block.sand.blockID;
            this.DirtID = Block.sand.blockID;
        }
    }

    private int[] getRock(int m)
    {
        int[][] d = {
                {mod_TFC.terraStoneIgIn.blockID,0},
                {mod_TFC.terraStoneIgIn.blockID,0},
                {mod_TFC.terraStoneIgIn.blockID,1},
                {mod_TFC.terraStoneIgIn.blockID,2},
                {mod_TFC.terraStoneSed.blockID,0},
                {mod_TFC.terraStoneSed.blockID,1},
                {mod_TFC.terraStoneSed.blockID,2},
                {mod_TFC.terraStoneSed.blockID,3},
                {mod_TFC.terraStoneSed.blockID,4},
                {mod_TFC.terraStoneSed.blockID,5},
                {mod_TFC.terraStoneSed.blockID,6},
                {mod_TFC.terraStoneSed.blockID,7},
                {mod_TFC.terraStoneSed.blockID,8},
                {mod_TFC.terraStoneSed.blockID,9},
                {mod_TFC.terraStoneIgEx.blockID,0},
                {mod_TFC.terraStoneIgEx.blockID,1},
                {mod_TFC.terraStoneIgEx.blockID,2},
                {mod_TFC.terraStoneIgEx.blockID,3},
                {mod_TFC.terraStoneMM.blockID,0},
                {mod_TFC.terraStoneMM.blockID,1},
                {mod_TFC.terraStoneMM.blockID,2},
                {mod_TFC.terraStoneMM.blockID,3},
                {mod_TFC.terraStoneMM.blockID,4},
                {mod_TFC.terraStoneMM.blockID,5}};

        return d[m];
    }

    public TFCBiome GetBiomeByName(String name)
    {
        for (int i = 0; i < this.biomeList.length; i++)
        {
            if(biomeList[i] != null)
            {
                String n = biomeList[i].biomeName.toLowerCase();
                if(n.equalsIgnoreCase(name))
                    return (TFCBiome) biomeList[i];
            }
        }
        return null;
    }

    public void copyBiomeRocks(String name)
    {
        TFCBiome base = GetBiomeByName(name);
        SurfaceType =   base.Layer1Type;
        SurfaceMeta =   base.Layer1Meta;
        TopSoilMetaID = base.TopSoilMetaID;
        Layer1Type =    base.Layer1Type;
        Layer1Meta =    base.Layer1Meta;
        Layer2Type =    base.Layer2Type;
        Layer2Meta =    base.Layer2Meta;
        Layer3Type =    base.Layer3Type;
        Layer3Meta =    base.Layer3Meta;
        Layer3 = base.Layer3;
        Layer2 = base.Layer2;
        Layer1 = base.Layer1;
        if(TopSoilMetaID < 16)
        {
            this.GrassID = mod_TFC.terraGrass.blockID;
            this.DirtID = mod_TFC.terraDirt.blockID;
            this.ClayID = mod_TFC.terraClay.blockID;
            this.ClayGrassID = mod_TFC.terraClayGrass.blockID;
        }
        else
        {
            this.GrassID = mod_TFC.terraGrass2.blockID;
            this.DirtID = mod_TFC.terraDirt2.blockID;
            this.ClayID = mod_TFC.terraClay2.blockID;
            this.ClayGrassID = mod_TFC.terraClayGrass2.blockID;
        }
        if(this.biomeName.toLowerCase().contains("desert"))
        {
            this.GrassID = Block.sand.blockID;
            this.DirtID = Block.sand.blockID;
        }
        
        this.temperature = base.temperature;
        this.rainfall = base.rainfall;
    }
    
    public void SetupTrees(World world, Random R)
    {
        
    }
    
    public int getRockLayer(int y)
    {
        if(y < Layer3)
            return Layer3Type;
        else if(y < Layer2)
            return Layer2Type;
        else if(y < Layer1)
            return Layer1Type;
        else
            return Layer1Type;
    }
}
