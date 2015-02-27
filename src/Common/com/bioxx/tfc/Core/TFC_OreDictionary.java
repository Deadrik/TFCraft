package com.bioxx.tfc.Core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class TFC_OreDictionary
{
	public static void registerOreDict()
	{
		final int WILD = OreDictionary.WILDCARD_VALUE;

		//Wood & Trees
		OreDictionary.registerOre("logWood", new ItemStack(TFCItems.Logs, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.LogNatural, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.LogNatural2, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.WoodHoriz, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.WoodHoriz2, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.WoodHoriz3, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.WoodHoriz4, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.WoodVert, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.WoodVert2, 1, WILD));

		OreDictionary.registerOre("plankWood", new ItemStack(TFCBlocks.Planks, 1, WILD));
		OreDictionary.registerOre("plankWood", new ItemStack(TFCBlocks.Planks2, 1, WILD));

		OreDictionary.registerOre("woodLumber", new ItemStack(TFCItems.SinglePlank, 1, WILD));

		OreDictionary.registerOre("stickWood", new ItemStack(TFCItems.Stick));

		OreDictionary.registerOre("treeSapling", new ItemStack(TFCBlocks.Sapling, 1, WILD));
		OreDictionary.registerOre("treeSapling", new ItemStack(TFCBlocks.Sapling2, 1, WILD));

		OreDictionary.registerOre("treeLeaves", new ItemStack(TFCBlocks.Leaves, 1, WILD));
		OreDictionary.registerOre("treeLeaves", new ItemStack(TFCBlocks.Leaves2, 1, WILD));

		//Ores
		OreDictionary.registerOre("oreNormalCopper", new ItemStack(TFCItems.OreChunk, 1, 0)); //Native Copper
		OreDictionary.registerOre("oreNormalCopper", new ItemStack(TFCItems.OreChunk, 1, 9)); //Malachite
		OreDictionary.registerOre("oreNormalCopper", new ItemStack(TFCItems.OreChunk, 1, 13)); //Tetrahedrite		
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.SmallOreChunk, 1, 0)); //Native Copper
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.SmallOreChunk, 1, 9)); //Malachite
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.SmallOreChunk, 1, 13)); //Tetrahedrite		
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.OreChunk, 1, 35)); //Native Copper
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.OreChunk, 1, 44)); //Malachite
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.OreChunk, 1, 48)); //Tetrahedrite		
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.OreChunk, 1, 49)); //Native Copper
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.OreChunk, 1, 58)); //Malachite
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.OreChunk, 1, 62)); //Tetrahedrite

		OreDictionary.registerOre("oreNormalGold", new ItemStack(TFCItems.OreChunk, 1, 1)); //Native Gold
		OreDictionary.registerOre("oreSmallGold", new ItemStack(TFCItems.SmallOreChunk, 1, 1)); //Native Gold
		OreDictionary.registerOre("oreRichGold", new ItemStack(TFCItems.OreChunk, 1, 36)); //Native Gold
		OreDictionary.registerOre("orePoorGold", new ItemStack(TFCItems.OreChunk, 1, 50)); //Native Gold

		OreDictionary.registerOre("oreNormalPlatinum", new ItemStack(TFCItems.OreChunk, 1, 2)); //Native Platinum
		OreDictionary.registerOre("oreSmallPlatinum", new ItemStack(TFCItems.SmallOreChunk, 1, 2)); //Native Platinum
		OreDictionary.registerOre("oreRichPlatinum", new ItemStack(TFCItems.OreChunk, 1, 37)); //Native Platinum
		OreDictionary.registerOre("orePoorPlatinum", new ItemStack(TFCItems.OreChunk, 1, 51)); //Native Platinum

		OreDictionary.registerOre("oreNormalIron", new ItemStack(TFCItems.OreChunk, 1, 3)); //Hematite
		OreDictionary.registerOre("oreNormalIron", new ItemStack(TFCItems.OreChunk, 1, 10)); //Magnetite
		OreDictionary.registerOre("oreNormalIron", new ItemStack(TFCItems.OreChunk, 1, 11)); //Limonite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.SmallOreChunk, 1, 3)); //Hematite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.SmallOreChunk, 1, 10)); //Magnetite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.SmallOreChunk, 1, 11)); //Limonite		
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.OreChunk, 1, 38)); //Hematite
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.OreChunk, 1, 45)); //Magnetite
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.OreChunk, 1, 46)); //Limonite		
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.OreChunk, 1, 52)); //Hematite
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.OreChunk, 1, 59)); //Magnetite
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.OreChunk, 1, 60)); //Limonite

		OreDictionary.registerOre("oreNormalSilver", new ItemStack(TFCItems.OreChunk, 1, 4)); //Native Silver
		OreDictionary.registerOre("oreSmallSilver", new ItemStack(TFCItems.SmallOreChunk, 1, 4)); //Native Silver
		OreDictionary.registerOre("oreRichSilver", new ItemStack(TFCItems.OreChunk, 1, 39)); //Native Silver
		OreDictionary.registerOre("orePoorSilver", new ItemStack(TFCItems.OreChunk, 1, 53)); //Native Silver

		OreDictionary.registerOre("oreNormalTin", new ItemStack(TFCItems.OreChunk, 1, 5)); //Cassiterite
		OreDictionary.registerOre("oreSmallTin", new ItemStack(TFCItems.SmallOreChunk, 1, 5)); //Cassiterite
		OreDictionary.registerOre("oreRichTin", new ItemStack(TFCItems.OreChunk, 1, 40)); //Cassiterite
		OreDictionary.registerOre("orePoorTin", new ItemStack(TFCItems.OreChunk, 1, 54)); //Cassiterite

		OreDictionary.registerOre("oreNormalLead", new ItemStack(TFCItems.OreChunk, 1, 6)); //Galena
		OreDictionary.registerOre("oreSmallLead", new ItemStack(TFCItems.SmallOreChunk, 1, 6)); //Galena
		OreDictionary.registerOre("oreRichLead", new ItemStack(TFCItems.OreChunk, 1, 41)); //Galena
		OreDictionary.registerOre("orePoorLead", new ItemStack(TFCItems.OreChunk, 1, 55)); //Galena

		OreDictionary.registerOre("oreNormalBismuth", new ItemStack(TFCItems.OreChunk, 1, 7)); //Bismuthinite
		OreDictionary.registerOre("oreSmallBismuth", new ItemStack(TFCItems.SmallOreChunk, 1, 7)); //Bismuthinite
		OreDictionary.registerOre("oreRichBismuth", new ItemStack(TFCItems.OreChunk, 1, 42)); //Bismuthinite
		OreDictionary.registerOre("orePoorBismuth", new ItemStack(TFCItems.OreChunk, 1, 56)); //Bismuthinite

		OreDictionary.registerOre("oreNormalNickel", new ItemStack(TFCItems.OreChunk, 1, 8)); //Garnierite
		OreDictionary.registerOre("oreSmallNickel", new ItemStack(TFCItems.SmallOreChunk, 1, 8)); //Garnierite
		OreDictionary.registerOre("oreRichNickel", new ItemStack(TFCItems.OreChunk, 1, 43)); //Garnierite
		OreDictionary.registerOre("orePoorNickel", new ItemStack(TFCItems.OreChunk, 1, 57)); //Garnierite

		OreDictionary.registerOre("oreNormalZinc", new ItemStack(TFCItems.OreChunk, 1, 12)); //Sphalerite
		OreDictionary.registerOre("oreSmallZinc", new ItemStack(TFCItems.SmallOreChunk, 1, 12)); //Sphalerite
		OreDictionary.registerOre("oreRichZinc", new ItemStack(TFCItems.OreChunk, 1, 47)); //Sphalerite
		OreDictionary.registerOre("orePoorZinc", new ItemStack(TFCItems.OreChunk, 1, 61)); //Sphalerite

		OreDictionary.registerOre("oreCoal", new ItemStack(TFCItems.OreChunk, 1, 14)); //Bituminous Coal
		OreDictionary.registerOre("oreCoal", new ItemStack(TFCItems.OreChunk, 1, 15)); //Lignite

		OreDictionary.registerOre("oreKaolinite", new ItemStack(TFCItems.OreChunk, 1, 16));
		OreDictionary.registerOre("oreGypsum", new ItemStack(TFCItems.OreChunk, 1, 17));
		OreDictionary.registerOre("oreSatinspar", new ItemStack(TFCItems.OreChunk, 1, 18));
		OreDictionary.registerOre("oreSelenite", new ItemStack(TFCItems.OreChunk, 1, 19));
		OreDictionary.registerOre("oreGraphite", new ItemStack(TFCItems.OreChunk, 1, 20));
		OreDictionary.registerOre("oreDiamond", new ItemStack(TFCItems.OreChunk, 1, 21)); //Kimberlite
		OreDictionary.registerOre("orePetrifiedWood", new ItemStack(TFCItems.OreChunk, 1, 22));
		OreDictionary.registerOre("oreSulfur", new ItemStack(TFCItems.OreChunk, 1, 23));
		OreDictionary.registerOre("oreJet", new ItemStack(TFCItems.OreChunk, 1, 24));
		OreDictionary.registerOre("oreMicrocline", new ItemStack(TFCItems.OreChunk, 1, 25));
		OreDictionary.registerOre("oreUranium", new ItemStack(TFCItems.OreChunk, 1, 26)); //Pitchblende

		OreDictionary.registerOre("oreRedstone", new ItemStack(TFCItems.OreChunk, 1, 27)); //Cinnabar
		OreDictionary.registerOre("oreRedstone", new ItemStack(TFCItems.OreChunk, 1, 28)); //Cryolite

		OreDictionary.registerOre("oreSaltpeter", new ItemStack(TFCItems.OreChunk, 1, 29));
		OreDictionary.registerOre("oreSerpentine", new ItemStack(TFCItems.OreChunk, 1, 30));
		OreDictionary.registerOre("oreSylvite", new ItemStack(TFCItems.OreChunk, 1, 31));
		OreDictionary.registerOre("oreBorax", new ItemStack(TFCItems.OreChunk, 1, 32));
		OreDictionary.registerOre("oreOlivine", new ItemStack(TFCItems.OreChunk, 1, 33));
		OreDictionary.registerOre("oreLapis", new ItemStack(TFCItems.OreChunk, 1, 34));

		//Ore Powders
		OreDictionary.registerOre("dustFlux", new ItemStack(TFCItems.Powder, 1, 0)); //Flux
		OreDictionary.registerOre("dustKaolinite", new ItemStack(TFCItems.Powder, 1, 1)); //Kaolinite
		OreDictionary.registerOre("dustGraphite", new ItemStack(TFCItems.Powder, 1, 2)); //Graphite
		OreDictionary.registerOre("dustSulfur", new ItemStack(TFCItems.Powder, 1, 3)); //Sulfur
		OreDictionary.registerOre("dustSaltpeter", new ItemStack(TFCItems.Powder, 1, 4)); //Saltpeter
		OreDictionary.registerOre("dustIron", new ItemStack(TFCItems.Powder, 1, 5)); //Hematite
		OreDictionary.registerOre("dustLapis", new ItemStack(TFCItems.Powder, 1, 6)); //Lapis
		OreDictionary.registerOre("dustIron", new ItemStack(TFCItems.Powder, 1, 7)); //Limonite
		OreDictionary.registerOre("dustCopper", new ItemStack(TFCItems.Powder, 1, 8)); //Malachite
		OreDictionary.registerOre("dustSalt", new ItemStack(TFCItems.Powder, 1, 9)); //Salt

		//Ingots
		OreDictionary.registerOre("ingotBismuth", new ItemStack(TFCItems.BismuthIngot));
		OreDictionary.registerOre("ingotTin", new ItemStack(TFCItems.TinIngot));
		OreDictionary.registerOre("ingotZinc", new ItemStack(TFCItems.ZincIngot));
		OreDictionary.registerOre("ingotCopper", new ItemStack(TFCItems.CopperIngot));
		OreDictionary.registerOre("ingotBronze", new ItemStack(TFCItems.BronzeIngot));
		OreDictionary.registerOre("ingotBismuthBronze", new ItemStack(TFCItems.BismuthBronzeIngot));
		OreDictionary.registerOre("ingotBlackBronze", new ItemStack(TFCItems.BlackBronzeIngot));
		OreDictionary.registerOre("ingotBrass", new ItemStack(TFCItems.BrassIngot));
		OreDictionary.registerOre("ingotLead", new ItemStack(TFCItems.LeadIngot));
		OreDictionary.registerOre("ingotGold", new ItemStack(TFCItems.GoldIngot));
		OreDictionary.registerOre("ingotRoseGold", new ItemStack(TFCItems.RoseGoldIngot));
		OreDictionary.registerOre("ingotSilver", new ItemStack(TFCItems.SilverIngot));
		OreDictionary.registerOre("ingotSterlingSilver", new ItemStack(TFCItems.SterlingSilverIngot));
		OreDictionary.registerOre("ingotPlatinum", new ItemStack(TFCItems.PlatinumIngot));
		OreDictionary.registerOre("ingotWroughtIron", new ItemStack(TFCItems.WroughtIronIngot));
		OreDictionary.registerOre("ingotIron", new ItemStack(TFCItems.WroughtIronIngot));
		OreDictionary.registerOre("ingotNickel", new ItemStack(TFCItems.NickelIngot));
		OreDictionary.registerOre("ingotPigIron", new ItemStack(TFCItems.PigIronIngot));
		OreDictionary.registerOre("ingotSteel", new ItemStack(TFCItems.SteelIngot));
		OreDictionary.registerOre("ingotBlackSteel", new ItemStack(TFCItems.BlackSteelIngot));
		OreDictionary.registerOre("ingotRedSteel", new ItemStack(TFCItems.RedSteelIngot));
		OreDictionary.registerOre("ingotBlueSteel", new ItemStack(TFCItems.BlueSteelIngot));
		OreDictionary.registerOre("ingotUnknown", new ItemStack(TFCItems.UnknownIngot));

		//Double Ingots
		OreDictionary.registerOre("ingotDoubleBismuth", new ItemStack(TFCItems.BismuthIngot2x));
		OreDictionary.registerOre("ingotDoubleTin", new ItemStack(TFCItems.TinIngot2x));
		OreDictionary.registerOre("ingotDoubleZinc", new ItemStack(TFCItems.ZincIngot2x));
		OreDictionary.registerOre("ingotDoubleCopper", new ItemStack(TFCItems.CopperIngot2x));
		OreDictionary.registerOre("ingotDoubleBronze", new ItemStack(TFCItems.BronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleBismuthBronze", new ItemStack(TFCItems.BismuthBronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleBlackBronze", new ItemStack(TFCItems.BlackBronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleBrass", new ItemStack(TFCItems.BrassIngot2x));
		OreDictionary.registerOre("ingotDoubleLead", new ItemStack(TFCItems.LeadIngot2x));
		OreDictionary.registerOre("ingotDoubleGold", new ItemStack(TFCItems.GoldIngot2x));
		OreDictionary.registerOre("ingotDoubleRoseGold", new ItemStack(TFCItems.RoseGoldIngot2x));
		OreDictionary.registerOre("ingotDoubleSilver", new ItemStack(TFCItems.SilverIngot2x));
		OreDictionary.registerOre("ingotDoubleSterlingSilver", new ItemStack(TFCItems.SterlingSilverIngot2x));
		OreDictionary.registerOre("ingotDoublePlatinum", new ItemStack(TFCItems.PlatinumIngot2x));
		OreDictionary.registerOre("ingotDoubleWroughtIron", new ItemStack(TFCItems.WroughtIronIngot2x));
		OreDictionary.registerOre("ingotDoubleNickel", new ItemStack(TFCItems.NickelIngot2x));
		OreDictionary.registerOre("ingotDoublePigIron", new ItemStack(TFCItems.PigIronIngot2x));
		OreDictionary.registerOre("ingotDoubleSteel", new ItemStack(TFCItems.SteelIngot2x));
		OreDictionary.registerOre("ingotDoubleBlackSteel", new ItemStack(TFCItems.BlackSteelIngot2x));
		OreDictionary.registerOre("ingotDoubleRedSteel", new ItemStack(TFCItems.RedSteelIngot2x));
		OreDictionary.registerOre("ingotDoubleBlueSteel", new ItemStack(TFCItems.BlueSteelIngot2x));

		//Sheets
		OreDictionary.registerOre("plateBismuth", new ItemStack(TFCItems.BismuthSheet));
		OreDictionary.registerOre("plateTin", new ItemStack(TFCItems.TinSheet));
		OreDictionary.registerOre("plateZinc", new ItemStack(TFCItems.ZincSheet));
		OreDictionary.registerOre("plateCopper", new ItemStack(TFCItems.CopperSheet));
		OreDictionary.registerOre("plateBronze", new ItemStack(TFCItems.BronzeSheet));
		OreDictionary.registerOre("plateBismuthBronze", new ItemStack(TFCItems.BismuthBronzeSheet));
		OreDictionary.registerOre("plateBlackBronze", new ItemStack(TFCItems.BlackBronzeSheet));
		OreDictionary.registerOre("plateBrass", new ItemStack(TFCItems.BrassSheet));
		OreDictionary.registerOre("plateLead", new ItemStack(TFCItems.LeadSheet));
		OreDictionary.registerOre("plateGold", new ItemStack(TFCItems.GoldSheet));
		OreDictionary.registerOre("plateRoseGold", new ItemStack(TFCItems.RoseGoldSheet));
		OreDictionary.registerOre("plateSilver", new ItemStack(TFCItems.SilverSheet));
		OreDictionary.registerOre("plateSterlingSilver", new ItemStack(TFCItems.SterlingSilverSheet));
		OreDictionary.registerOre("platePlatinum", new ItemStack(TFCItems.PlatinumSheet));
		OreDictionary.registerOre("plateWroughtIron", new ItemStack(TFCItems.WroughtIronSheet));
		OreDictionary.registerOre("plateNickel", new ItemStack(TFCItems.NickelSheet));
		OreDictionary.registerOre("platePigIron", new ItemStack(TFCItems.PigIronSheet));
		OreDictionary.registerOre("plateSteel", new ItemStack(TFCItems.SteelSheet));
		OreDictionary.registerOre("plateBlackSteel", new ItemStack(TFCItems.BlackSteelSheet));
		OreDictionary.registerOre("plateRedSteel", new ItemStack(TFCItems.RedSteelSheet));
		OreDictionary.registerOre("plateBlueSteel", new ItemStack(TFCItems.BlueSteelSheet));

		//Double Sheets
		OreDictionary.registerOre("plateDoubleBismuth", new ItemStack(TFCItems.BismuthSheet2x));
		OreDictionary.registerOre("plateDoubleTin", new ItemStack(TFCItems.TinSheet2x));
		OreDictionary.registerOre("plateDoubleZinc", new ItemStack(TFCItems.ZincSheet2x));
		OreDictionary.registerOre("plateDoubleCopper", new ItemStack(TFCItems.CopperSheet2x));
		OreDictionary.registerOre("plateDoubleBronze", new ItemStack(TFCItems.BronzeSheet2x));
		OreDictionary.registerOre("plateDoubleBismuthBronze", new ItemStack(TFCItems.BismuthBronzeSheet2x));
		OreDictionary.registerOre("plateDoubleBlackBronze", new ItemStack(TFCItems.BlackBronzeSheet2x));
		OreDictionary.registerOre("plateDoubleBrass", new ItemStack(TFCItems.BrassSheet2x));
		OreDictionary.registerOre("plateDoubleLead", new ItemStack(TFCItems.LeadSheet2x));
		OreDictionary.registerOre("plateDoubleGold", new ItemStack(TFCItems.GoldSheet2x));
		OreDictionary.registerOre("plateDoubleRoseGold", new ItemStack(TFCItems.RoseGoldSheet2x));
		OreDictionary.registerOre("plateDoubleSilver", new ItemStack(TFCItems.SilverSheet2x));
		OreDictionary.registerOre("plateDoubleSterlingSilver", new ItemStack(TFCItems.SterlingSilverSheet2x));
		OreDictionary.registerOre("plateDoublePlatinum", new ItemStack(TFCItems.PlatinumSheet2x));
		OreDictionary.registerOre("plateDoubleWroughtIron", new ItemStack(TFCItems.WroughtIronSheet2x));
		OreDictionary.registerOre("plateDoubleNickel", new ItemStack(TFCItems.NickelSheet2x));
		OreDictionary.registerOre("plateDoublePigIron", new ItemStack(TFCItems.PigIronSheet2x));
		OreDictionary.registerOre("plateDoubleSteel", new ItemStack(TFCItems.SteelSheet2x));
		OreDictionary.registerOre("plateDoubleBlackSteel", new ItemStack(TFCItems.BlackSteelSheet2x));
		OreDictionary.registerOre("plateDoubleRedSteel", new ItemStack(TFCItems.RedSteelSheet2x));
		OreDictionary.registerOre("plateDoubleBlueSteel", new ItemStack(TFCItems.BlueSteelSheet2x));

		OreDictionary.registerOre("plateDoubleAnyBronze", new ItemStack(TFCItems.BronzeSheet2x));
		OreDictionary.registerOre("plateDoubleAnyBronze", new ItemStack(TFCItems.BismuthBronzeSheet2x));
		OreDictionary.registerOre("plateDoubleAnyBronze", new ItemStack(TFCItems.BlackBronzeSheet2x));

		//Gems
		OreDictionary.registerOre("gemChippedAgate", new ItemStack(TFCItems.GemAgate));
		OreDictionary.registerOre("gemChippedAmethyst", new ItemStack(TFCItems.GemAmethyst));
		OreDictionary.registerOre("gemChippedBeryl", new ItemStack(TFCItems.GemBeryl));
		OreDictionary.registerOre("gemChippedDiamond", new ItemStack(TFCItems.GemDiamond));
		OreDictionary.registerOre("gemChippedEmerald", new ItemStack(TFCItems.GemEmerald));
		OreDictionary.registerOre("gemChippedGarnet", new ItemStack(TFCItems.GemGarnet));
		OreDictionary.registerOre("gemChippedJade", new ItemStack(TFCItems.GemJade));
		OreDictionary.registerOre("gemChippedJasper", new ItemStack(TFCItems.GemJasper));
		OreDictionary.registerOre("gemChippedOpal", new ItemStack(TFCItems.GemOpal));
		OreDictionary.registerOre("gemChippedRuby", new ItemStack(TFCItems.GemRuby));
		OreDictionary.registerOre("gemChippedSapphire", new ItemStack(TFCItems.GemSapphire));
		OreDictionary.registerOre("gemChippedTopaz", new ItemStack(TFCItems.GemTopaz));
		OreDictionary.registerOre("gemChippedTourmaline", new ItemStack(TFCItems.GemTourmaline));

		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemAgate));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemAmethyst));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemBeryl));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemDiamond));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemEmerald));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemGarnet));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemJade));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemJasper));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemOpal));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemRuby));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemSapphire));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemTopaz));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.GemTourmaline));

		OreDictionary.registerOre("gemFlawedAgate", new ItemStack(TFCItems.GemAgate, 1, 1));
		OreDictionary.registerOre("gemFlawedAmethyst", new ItemStack(TFCItems.GemAmethyst, 1, 1));
		OreDictionary.registerOre("gemFlawedBeryl", new ItemStack(TFCItems.GemBeryl, 1, 1));
		OreDictionary.registerOre("gemFlawedDiamond", new ItemStack(TFCItems.GemDiamond, 1, 1));
		OreDictionary.registerOre("gemFlawedEmerald", new ItemStack(TFCItems.GemEmerald, 1, 1));
		OreDictionary.registerOre("gemFlawedGarnet", new ItemStack(TFCItems.GemGarnet, 1, 1));
		OreDictionary.registerOre("gemFlawedJade", new ItemStack(TFCItems.GemJade, 1, 1));
		OreDictionary.registerOre("gemFlawedJasper", new ItemStack(TFCItems.GemJasper, 1, 1));
		OreDictionary.registerOre("gemFlawedOpal", new ItemStack(TFCItems.GemOpal, 1, 1));
		OreDictionary.registerOre("gemFlawedRuby", new ItemStack(TFCItems.GemRuby, 1, 1));
		OreDictionary.registerOre("gemFlawedSapphire", new ItemStack(TFCItems.GemSapphire, 1, 1));
		OreDictionary.registerOre("gemFlawedTopaz", new ItemStack(TFCItems.GemTopaz, 1, 1));
		OreDictionary.registerOre("gemFlawedTourmaline", new ItemStack(TFCItems.GemTourmaline, 1, 1));

		OreDictionary.registerOre("gemAgate", new ItemStack(TFCItems.GemAgate, 1, 2));
		OreDictionary.registerOre("gemAmethyst", new ItemStack(TFCItems.GemAmethyst, 1, 2));
		OreDictionary.registerOre("gemBeryl", new ItemStack(TFCItems.GemBeryl, 1, 2));
		OreDictionary.registerOre("gemDiamond", new ItemStack(TFCItems.GemDiamond, 1, 2));
		OreDictionary.registerOre("gemEmerald", new ItemStack(TFCItems.GemEmerald, 1, 2));
		OreDictionary.registerOre("gemGarnet", new ItemStack(TFCItems.GemGarnet, 1, 2));
		OreDictionary.registerOre("gemJade", new ItemStack(TFCItems.GemJade, 1, 2));
		OreDictionary.registerOre("gemJasper", new ItemStack(TFCItems.GemJasper, 1, 2));
		OreDictionary.registerOre("gemOpal", new ItemStack(TFCItems.GemOpal, 1, 2));
		OreDictionary.registerOre("gemRuby", new ItemStack(TFCItems.GemRuby, 1, 2));
		OreDictionary.registerOre("gemSapphire", new ItemStack(TFCItems.GemSapphire, 1, 2));
		OreDictionary.registerOre("gemTopaz", new ItemStack(TFCItems.GemTopaz, 1, 2));
		OreDictionary.registerOre("gemTourmaline", new ItemStack(TFCItems.GemTourmaline, 1, 2));

		OreDictionary.registerOre("gemFlawlessAgate", new ItemStack(TFCItems.GemAgate, 1, 3));
		OreDictionary.registerOre("gemFlawlessAmethyst", new ItemStack(TFCItems.GemAmethyst, 1, 3));
		OreDictionary.registerOre("gemFlawlessBeryl", new ItemStack(TFCItems.GemBeryl, 1, 3));
		OreDictionary.registerOre("gemFlawlessDiamond", new ItemStack(TFCItems.GemDiamond, 1, 3));
		OreDictionary.registerOre("gemFlawlessEmerald", new ItemStack(TFCItems.GemEmerald, 1, 3));
		OreDictionary.registerOre("gemFlawlessGarnet", new ItemStack(TFCItems.GemGarnet, 1, 3));
		OreDictionary.registerOre("gemFlawlessJade", new ItemStack(TFCItems.GemJade, 1, 3));
		OreDictionary.registerOre("gemFlawlessJasper", new ItemStack(TFCItems.GemJasper, 1, 3));
		OreDictionary.registerOre("gemFlawlessOpal", new ItemStack(TFCItems.GemOpal, 1, 3));
		OreDictionary.registerOre("gemFlawlessRuby", new ItemStack(TFCItems.GemRuby, 1, 3));
		OreDictionary.registerOre("gemFlawlessSapphire", new ItemStack(TFCItems.GemSapphire, 1, 3));
		OreDictionary.registerOre("gemFlawlessTopaz", new ItemStack(TFCItems.GemTopaz, 1, 3));
		OreDictionary.registerOre("gemFlawlessTourmaline", new ItemStack(TFCItems.GemTourmaline, 1, 3));

		OreDictionary.registerOre("gemExquisiteAgate", new ItemStack(TFCItems.GemAgate, 1, 4));
		OreDictionary.registerOre("gemExquisiteAmethyst", new ItemStack(TFCItems.GemAmethyst, 1, 4));
		OreDictionary.registerOre("gemExquisiteBeryl", new ItemStack(TFCItems.GemBeryl, 1, 4));
		OreDictionary.registerOre("gemExquisiteDiamond", new ItemStack(TFCItems.GemDiamond, 1, 4));
		OreDictionary.registerOre("gemExquisiteEmerald", new ItemStack(TFCItems.GemEmerald, 1, 4));
		OreDictionary.registerOre("gemExquisiteGarnet", new ItemStack(TFCItems.GemGarnet, 1, 4));
		OreDictionary.registerOre("gemExquisiteJade", new ItemStack(TFCItems.GemJade, 1, 4));
		OreDictionary.registerOre("gemExquisiteJasper", new ItemStack(TFCItems.GemJasper, 1, 4));
		OreDictionary.registerOre("gemExquisiteOpal", new ItemStack(TFCItems.GemOpal, 1, 4));
		OreDictionary.registerOre("gemExquisiteRuby", new ItemStack(TFCItems.GemRuby, 1, 4));
		OreDictionary.registerOre("gemExquisiteSapphire", new ItemStack(TFCItems.GemSapphire, 1, 4));
		OreDictionary.registerOre("gemExquisiteTopaz", new ItemStack(TFCItems.GemTopaz, 1, 4));
		OreDictionary.registerOre("gemExquisiteTourmaline", new ItemStack(TFCItems.GemTourmaline, 1, 4));

		//Fuel
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.coal, 1, 0));
		OreDictionary.registerOre("gemCoal", new ItemStack(TFCItems.Coal, 1, 0));
		OreDictionary.registerOre("gemCharcoal", new ItemStack(Items.coal, 1, 1));
		OreDictionary.registerOre("gemCharcoal", new ItemStack(TFCItems.Coal, 1, 1));

		//Stone
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneIgEx, 1, WILD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneIgIn, 1, WILD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneMM, 1, WILD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneSed, 1, WILD));

		//Cobblestone
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneIgExCobble, 1, WILD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneIgInCobble, 1, WILD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneMMCobble, 1, WILD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneSedCobble, 1, WILD));

		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.StoneIgExCobble, 1, WILD));
		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.StoneIgInCobble, 1, WILD));
		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.StoneMMCobble, 1, WILD));
		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.StoneSedCobble, 1, WILD));

		//Stone Bricks
		OreDictionary.registerOre("stoneBricks", new ItemStack(Blocks.stonebrick));
		OreDictionary.registerOre("stoneBricks", new ItemStack(TFCBlocks.StoneIgExBrick, 1, WILD));
		OreDictionary.registerOre("stoneBricks", new ItemStack(TFCBlocks.StoneIgInBrick, 1, WILD));
		OreDictionary.registerOre("stoneBricks", new ItemStack(TFCBlocks.StoneMMBrick, 1, WILD));
		OreDictionary.registerOre("stoneBricks", new ItemStack(TFCBlocks.StoneSedBrick, 1, WILD));

		//Smooth Stone
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneIgExSmooth, 1, WILD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneIgInSmooth, 1, WILD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneMMSmooth, 1, WILD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneSedSmooth, 1, WILD));

		//Crafting Table
		OreDictionary.registerOre("craftingTableWood", new ItemStack(TFCBlocks.Workbench));
		OreDictionary.registerOre("craftingTableWood", new ItemStack(Blocks.crafting_table));

		//Dyes
		OreDictionary.registerOre("dyeBlack", new ItemStack(TFCItems.Dye, 1, 0));
		OreDictionary.registerOre("dyeRed", new ItemStack(TFCItems.Powder, 1, 5));
		OreDictionary.registerOre("dyeRed", new ItemStack(TFCItems.Dye, 1, 1));
		OreDictionary.registerOre("dyeGreen", new ItemStack(TFCItems.Powder, 1, 8));
		OreDictionary.registerOre("dyeGreen", new ItemStack(TFCItems.Dye, 1, 2));
		OreDictionary.registerOre("dyeBrown", new ItemStack(TFCItems.Dye, 1, 3));
		OreDictionary.registerOre("dyeBlue", new ItemStack(TFCItems.Powder, 1, 6));
		OreDictionary.registerOre("dyeBlue", new ItemStack(TFCItems.Dye, 1, 4));
		OreDictionary.registerOre("dyePurple", new ItemStack(TFCItems.Dye, 1, 5));
		OreDictionary.registerOre("dyeCyan", new ItemStack(TFCItems.Dye, 1, 6));
		OreDictionary.registerOre("dyeLightGray", new ItemStack(TFCItems.Dye, 1, 7));
		OreDictionary.registerOre("dyeGray", new ItemStack(TFCItems.Dye, 1, 8));
		OreDictionary.registerOre("dyePink", new ItemStack(TFCItems.Dye, 1, 9));
		OreDictionary.registerOre("dyeLime", new ItemStack(TFCItems.Dye, 1, 10));
		OreDictionary.registerOre("dyeYellow", new ItemStack(TFCItems.Powder, 1, 7));
		OreDictionary.registerOre("dyeYellow", new ItemStack(TFCItems.Dye, 1, 11));
		OreDictionary.registerOre("dyeLightBlue", new ItemStack(TFCItems.Dye, 1, 12));
		OreDictionary.registerOre("dyeMagenta", new ItemStack(TFCItems.Dye, 1, 13));
		OreDictionary.registerOre("dyeOrange", new ItemStack(TFCItems.Dye, 1, 14));
		OreDictionary.registerOre("dyeWhite", new ItemStack(TFCItems.Dye, 1, 15));

		//Materials
		OreDictionary.registerOre("materialLeather", new ItemStack(Items.leather));
		OreDictionary.registerOre("materialLeather", new ItemStack(TFCItems.Leather));

		OreDictionary.registerOre("materialString", new ItemStack(Items.string));
		OreDictionary.registerOre("materialString", new ItemStack(TFCItems.WoolYarn));

		OreDictionary.registerOre("materialCloth", new ItemStack(TFCItems.WoolCloth));
		OreDictionary.registerOre("materialCloth", new ItemStack(TFCItems.SilkCloth));
		OreDictionary.registerOre("materialWool", new ItemStack(TFCItems.Wool, 1, WILD));

		//Tools
		for (Item chisel : Recipes.Chisels)
			OreDictionary.registerOre("itemChisel", new ItemStack(chisel, 1, WILD));

		for (Item hammer : Recipes.Hammers)
			OreDictionary.registerOre("itemHammer", new ItemStack(hammer, 1, WILD));

		for (Item knife : Recipes.Knives)
			OreDictionary.registerOre("itemKnife", new ItemStack(knife, 1, WILD));

		for (Item saw : Recipes.Saws)
			OreDictionary.registerOre("itemSaw", new ItemStack(saw, 1, WILD));

		//Miscellaneous Items
		OreDictionary.registerOre("lumpClay", new ItemStack(Items.clay_ball));
		OreDictionary.registerOre("lumpClay", new ItemStack(TFCItems.ClayBall, 1, 0));

		OreDictionary.registerOre("itemArrow", new ItemStack(Items.arrow));
		OreDictionary.registerOre("itemArrow", new ItemStack(TFCItems.Arrow));

		OreDictionary.registerOre("itemReed", new ItemStack(Items.reeds));
		OreDictionary.registerOre("itemReed", new ItemStack(TFCItems.Reeds));

		OreDictionary.registerOre("itemRock", new ItemStack(TFCItems.LooseRock, 1, WILD));

		OreDictionary.registerOre("bucketEmpty", new ItemStack(Items.bucket));
		OreDictionary.registerOre("bucketEmpty", new ItemStack(TFCItems.WoodenBucketEmpty));
		OreDictionary.registerOre("bucketEmpty", new ItemStack(TFCItems.RedSteelBucketEmpty));
		OreDictionary.registerOre("bucketEmpty", new ItemStack(TFCItems.BlueSteelBucketEmpty));

		OreDictionary.registerOre("bucketWater", new ItemStack(Items.water_bucket));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.WoodenBucketWater, 1, WILD));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.RedSteelBucketWater, 1, WILD));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.WoodenBucketSaltWater, 1, WILD));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.RedSteelBucketSaltWater, 1, WILD));

		OreDictionary.registerOre("bucketFreshWater", new ItemStack(TFCItems.WoodenBucketWater, 1, WILD));
		OreDictionary.registerOre("bucketFreshWater", new ItemStack(TFCItems.RedSteelBucketWater, 1, WILD));

		OreDictionary.registerOre("bucketSaltWater", new ItemStack(TFCItems.WoodenBucketSaltWater, 1, WILD));
		OreDictionary.registerOre("bucketSaltWater", new ItemStack(TFCItems.RedSteelBucketSaltWater, 1, WILD));

		OreDictionary.registerOre("bucketLava", new ItemStack(Items.lava_bucket));
		OreDictionary.registerOre("bucketLava", new ItemStack(TFCItems.BlueSteelBucketLava));

		OreDictionary.registerOre("bucketMilk", new ItemStack(Items.milk_bucket));
		OreDictionary.registerOre("bucketMilk", new ItemStack(TFCItems.WoodenBucketMilk));

		//Miscellaneous Blocks
		OreDictionary.registerOre("blockSand", new ItemStack(Blocks.sand));
		OreDictionary.registerOre("blockSand", new ItemStack(TFCBlocks.Sand, 1, WILD));
		OreDictionary.registerOre("blockSand", new ItemStack(TFCBlocks.Sand2, 1, WILD));

		OreDictionary.registerOre("blockDirt", new ItemStack(Blocks.dirt));
		OreDictionary.registerOre("blockDirt", new ItemStack(TFCBlocks.Dirt, 1, WILD));
		OreDictionary.registerOre("blockDirt", new ItemStack(TFCBlocks.Dirt2, 1, WILD));

		OreDictionary.registerOre("blockTorch", new ItemStack(Blocks.torch));
		OreDictionary.registerOre("blockTorch", new ItemStack(TFCBlocks.Torch));

		OreDictionary.registerOre("blockPumpkin", new ItemStack(Blocks.pumpkin));
		OreDictionary.registerOre("blockPumpkin", new ItemStack(TFCBlocks.Pumpkin));
	}
}
