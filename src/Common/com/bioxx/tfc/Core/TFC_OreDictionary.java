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
		OreDictionary.registerOre("logWood", new ItemStack(TFCItems.logs, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.logNatural, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.logNatural2, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.woodHoriz, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.woodHoriz2, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.woodHoriz3, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.woodHoriz4, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.woodVert, 1, WILD));
		OreDictionary.registerOre("logWood", new ItemStack(TFCBlocks.woodVert2, 1, WILD));

		OreDictionary.registerOre("plankWood", new ItemStack(TFCBlocks.planks, 1, WILD));
		OreDictionary.registerOre("plankWood", new ItemStack(TFCBlocks.planks2, 1, WILD));

		OreDictionary.registerOre("woodLumber", new ItemStack(TFCItems.singlePlank, 1, WILD));
		OreDictionary.registerOre("slabWood", new ItemStack(TFCItems.singlePlank, 1, WILD));

		OreDictionary.registerOre("stickWood", new ItemStack(TFCItems.stick));

		OreDictionary.registerOre("treeSapling", new ItemStack(TFCBlocks.sapling, 1, WILD));
		OreDictionary.registerOre("treeSapling", new ItemStack(TFCBlocks.sapling2, 1, WILD));

		OreDictionary.registerOre("treeLeaves", new ItemStack(TFCBlocks.leaves, 1, WILD));
		OreDictionary.registerOre("treeLeaves", new ItemStack(TFCBlocks.leaves2, 1, WILD));

		//Wood Crafts
		OreDictionary.registerOre("chestWood", new ItemStack(TFCBlocks.chest, 1, WILD));
		OreDictionary.registerOre("barrelWood", new ItemStack(TFCBlocks.barrel, 1, WILD));

		OreDictionary.registerOre("fenceGate", new ItemStack(Blocks.fence_gate, 1, WILD));
		OreDictionary.registerOre("fenceGate", new ItemStack(TFCBlocks.fenceGate, 1, WILD));
		OreDictionary.registerOre("fenceGate", new ItemStack(TFCBlocks.fenceGate2, 1, WILD));

		OreDictionary.registerOre("fenceWood", new ItemStack(Blocks.fence, 1, WILD));
		OreDictionary.registerOre("fenceWood", new ItemStack(TFCBlocks.fence, 1, WILD));
		OreDictionary.registerOre("fenceWood", new ItemStack(TFCBlocks.fence2, 1, WILD));

		//Ores

		OreDictionary.registerOre("dustBismuth", new ItemStack(TFCItems.metalDust, 1, 0)); //Bismuth
		OreDictionary.registerOre("dustCopper", new ItemStack(TFCItems.metalDust, 1, 1)); //Copper
		OreDictionary.registerOre("dustGold", new ItemStack(TFCItems.metalDust, 1, 2)); //Gold
		OreDictionary.registerOre("dustIron", new ItemStack(TFCItems.metalDust, 1, 3)); //Iron
		OreDictionary.registerOre("dustLead", new ItemStack(TFCItems.metalDust, 1, 4)); //Lead
		OreDictionary.registerOre("dustNickel", new ItemStack(TFCItems.metalDust, 1, 5)); //Nickel
		OreDictionary.registerOre("dustPigIron", new ItemStack(TFCItems.metalDust, 1, 6)); //PigIron
		OreDictionary.registerOre("dustPlatinum", new ItemStack(TFCItems.metalDust, 1, 7)); //Platinum
		OreDictionary.registerOre("dustSilver", new ItemStack(TFCItems.metalDust, 1, 8)); //Silver
		OreDictionary.registerOre("dustSteel", new ItemStack(TFCItems.metalDust, 1, 9)); //Steel
		OreDictionary.registerOre("dustTin", new ItemStack(TFCItems.metalDust, 1, 10)); //Tin
		OreDictionary.registerOre("dustZinc", new ItemStack(TFCItems.metalDust, 1, 11)); //Zinc
		OreDictionary.registerOre("dustOsmium", new ItemStack(TFCItems.metalDust, 1, 12)); //Osmium
		OreDictionary.registerOre("dustAluminum", new ItemStack(TFCItems.metalDust, 1, 13)); //Aluminum
		OreDictionary.registerOre("dustTungsten", new ItemStack(TFCItems.metalDust, 1, 14)); //Tungsten

		OreDictionary.registerOre("dustAnyBronze", new ItemStack(TFCItems.metalDust, 1, 15)); //BismuthBronze
		OreDictionary.registerOre("dustBismuthBronze", new ItemStack(TFCItems.metalDust, 1, 15)); //BismuthBronze
		OreDictionary.registerOre("dustAnyBronze", new ItemStack(TFCItems.metalDust, 1, 16)); //BlackBronze
		OreDictionary.registerOre("dustBlackBronze", new ItemStack(TFCItems.metalDust, 1, 16)); //BlackBronze
		OreDictionary.registerOre("dustBlackSteel", new ItemStack(TFCItems.metalDust, 1, 17)); //BlackSteel
		OreDictionary.registerOre("dustBlueSteel", new ItemStack(TFCItems.metalDust, 1, 18)); //BlueSteel
		OreDictionary.registerOre("dustBrass", new ItemStack(TFCItems.metalDust, 1, 19)); //Brass
		OreDictionary.registerOre("dustBronze", new ItemStack(TFCItems.metalDust, 1, 20)); //Bronze
		OreDictionary.registerOre("dustAnyBronze", new ItemStack(TFCItems.metalDust, 1, 20)); //Bronze
		OreDictionary.registerOre("dustHCBlackSteel", new ItemStack(TFCItems.metalDust, 1, 21)); //HCBlackSteel
		OreDictionary.registerOre("dustHCBlueSteel", new ItemStack(TFCItems.metalDust, 1, 22)); //HCBlueSteel
		OreDictionary.registerOre("dustHCRedSteel", new ItemStack(TFCItems.metalDust, 1, 23)); //HCRedSteel
		OreDictionary.registerOre("dustRedSteel", new ItemStack(TFCItems.metalDust, 1, 24)); //RedSteel
		OreDictionary.registerOre("dustRoseGold", new ItemStack(TFCItems.metalDust, 1, 25)); //RoseGold
		OreDictionary.registerOre("dustSterlingSilver", new ItemStack(TFCItems.metalDust, 1, 26)); //SterlingSilver
		OreDictionary.registerOre("dustWeakSteel", new ItemStack(TFCItems.metalDust, 1, 27)); //WeakSteel
		OreDictionary.registerOre("dustWeakBlueSteel", new ItemStack(TFCItems.metalDust, 1, 28)); //WeakBlueSteel
		OreDictionary.registerOre("dustWeakRedSteel", new ItemStack(TFCItems.metalDust, 1, 29)); //WeakRedSteel
		OreDictionary.registerOre("dustElectrum", new ItemStack(TFCItems.metalDust, 1, 30)); //Electrum
		OreDictionary.registerOre("dustCupronickel", new ItemStack(TFCItems.metalDust, 1, 31)); //Cupronickel
		OreDictionary.registerOre("dustConstantan", new ItemStack(TFCItems.metalDust, 1, 31)); //Cupronickel



		OreDictionary.registerOre("oreCopper", new ItemStack(TFCItems.orePile, 1, 0)); //Native Copper Pile
		OreDictionary.registerOre("oreCopper", new ItemStack(TFCItems.orePile, 1, 9)); //Malachite
		OreDictionary.registerOre("oreCopper", new ItemStack(TFCItems.orePile, 1, 13)); //Tetrahedrite
		OreDictionary.registerOre("oreGold", new ItemStack(TFCItems.orePile, 1, 1)); //Native Gold
		OreDictionary.registerOre("orePlatinum", new ItemStack(TFCItems.orePile, 1, 2)); //Native Platinum
		OreDictionary.registerOre("oreIron", new ItemStack(TFCItems.orePile, 1, 3)); //Hematite
		OreDictionary.registerOre("oreIron", new ItemStack(TFCItems.orePile, 1, 10)); //Magnetite
		OreDictionary.registerOre("oreIron", new ItemStack(TFCItems.orePile, 1, 11)); //Limonite
		OreDictionary.registerOre("oreSilver", new ItemStack(TFCItems.orePile, 1, 4)); //Native Silver
		OreDictionary.registerOre("oreTin", new ItemStack(TFCItems.orePile, 1, 5)); //Cassiterite
		OreDictionary.registerOre("oreLead", new ItemStack(TFCItems.orePile, 1, 6)); //Galena
		OreDictionary.registerOre("oreBismuth", new ItemStack(TFCItems.orePile, 1, 7)); //Bismuthinite
		OreDictionary.registerOre("oreNickel", new ItemStack(TFCItems.orePile, 1, 8)); //Garnierite
		OreDictionary.registerOre("oreZinc", new ItemStack(TFCItems.orePile, 1, 12)); //Sphalerite
		OreDictionary.registerOre("oreOsmium", new ItemStack(TFCItems.orePile, 1, 14)); //Native Osmium
		OreDictionary.registerOre("oreAluminum", new ItemStack(TFCItems.orePile, 1, 15)); //Bauxite
		OreDictionary.registerOre("oreTungsten", new ItemStack(TFCItems.orePile, 1, 16)); //Scheelite
		OreDictionary.registerOre("oreTungsten", new ItemStack(TFCItems.orePile, 1, 17)); //Wolframite

		OreDictionary.registerOre("oreNormalCopper", new ItemStack(TFCItems.oreChunk, 1, 0)); //Native Copper
		OreDictionary.registerOre("oreNormalCopper", new ItemStack(TFCItems.oreChunk, 1, 9)); //Malachite
		OreDictionary.registerOre("oreNormalCopper", new ItemStack(TFCItems.oreChunk, 1, 13)); //Tetrahedrite		
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.smallOreChunk, 1, 0)); //Native Copper
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.smallOreChunk, 1, 9)); //Malachite
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.smallOreChunk, 1, 13)); //Tetrahedrite		
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.oreChunk, 1, 18)); //Native Copper
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.oreChunk, 1, 27)); //Malachite
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.oreChunk, 1, 31)); //Tetrahedrite
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.oreChunk, 1, 36)); //Native Copper
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.oreChunk, 1, 45)); //Malachite
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.oreChunk, 1, 49)); //Tetrahedrite

		OreDictionary.registerOre("oreNormalGold", new ItemStack(TFCItems.oreChunk, 1, 1)); //Native Gold
		OreDictionary.registerOre("oreSmallGold", new ItemStack(TFCItems.smallOreChunk, 1, 1)); //Native Gold
		OreDictionary.registerOre("oreRichGold", new ItemStack(TFCItems.oreChunk, 1, 19)); //Native Gold
		OreDictionary.registerOre("orePoorGold", new ItemStack(TFCItems.oreChunk, 1, 37)); //Native Gold

		OreDictionary.registerOre("oreNormalPlatinum", new ItemStack(TFCItems.oreChunk, 1, 2)); //Native Platinum
		OreDictionary.registerOre("oreSmallPlatinum", new ItemStack(TFCItems.smallOreChunk, 1, 2)); //Native Platinum
		OreDictionary.registerOre("oreRichPlatinum", new ItemStack(TFCItems.oreChunk, 1, 20)); //Native Platinum
		OreDictionary.registerOre("orePoorPlatinum", new ItemStack(TFCItems.oreChunk, 1, 38)); //Native Platinum

		OreDictionary.registerOre("oreNormalIron", new ItemStack(TFCItems.oreChunk, 1, 3)); //Hematite
		OreDictionary.registerOre("oreNormalIron", new ItemStack(TFCItems.oreChunk, 1, 10)); //Magnetite
		OreDictionary.registerOre("oreNormalIron", new ItemStack(TFCItems.oreChunk, 1, 11)); //Limonite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.smallOreChunk, 1, 3)); //Hematite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.smallOreChunk, 1, 10)); //Magnetite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.smallOreChunk, 1, 11)); //Limonite		
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.oreChunk, 1, 21)); //Hematite
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.oreChunk, 1, 28)); //Magnetite
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.oreChunk, 1, 29)); //Limonite
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.oreChunk, 1, 39)); //Hematite
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.oreChunk, 1, 46)); //Magnetite
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.oreChunk, 1, 47)); //Limonite

		OreDictionary.registerOre("oreNormalSilver", new ItemStack(TFCItems.oreChunk, 1, 4)); //Native Silver
		OreDictionary.registerOre("oreSmallSilver", new ItemStack(TFCItems.smallOreChunk, 1, 4)); //Native Silver
		OreDictionary.registerOre("oreRichSilver", new ItemStack(TFCItems.oreChunk, 1, 22)); //Native Silver
		OreDictionary.registerOre("orePoorSilver", new ItemStack(TFCItems.oreChunk, 1, 40)); //Native Silver

		OreDictionary.registerOre("oreNormalTin", new ItemStack(TFCItems.oreChunk, 1, 5)); //Cassiterite
		OreDictionary.registerOre("oreSmallTin", new ItemStack(TFCItems.smallOreChunk, 1, 5)); //Cassiterite
		OreDictionary.registerOre("oreRichTin", new ItemStack(TFCItems.oreChunk, 1, 23)); //Cassiterite
		OreDictionary.registerOre("orePoorTin", new ItemStack(TFCItems.oreChunk, 1, 41)); //Cassiterite

		OreDictionary.registerOre("oreNormalLead", new ItemStack(TFCItems.oreChunk, 1, 6)); //Galena
		OreDictionary.registerOre("oreSmallLead", new ItemStack(TFCItems.smallOreChunk, 1, 6)); //Galena
		OreDictionary.registerOre("oreRichLead", new ItemStack(TFCItems.oreChunk, 1, 24)); //Galena
		OreDictionary.registerOre("orePoorLead", new ItemStack(TFCItems.oreChunk, 1, 42)); //Galena

		OreDictionary.registerOre("oreNormalBismuth", new ItemStack(TFCItems.oreChunk, 1, 7)); //Bismuthinite
		OreDictionary.registerOre("oreSmallBismuth", new ItemStack(TFCItems.smallOreChunk, 1, 7)); //Bismuthinite
		OreDictionary.registerOre("oreRichBismuth", new ItemStack(TFCItems.oreChunk, 1, 25)); //Bismuthinite
		OreDictionary.registerOre("orePoorBismuth", new ItemStack(TFCItems.oreChunk, 1, 43)); //Bismuthinite

		OreDictionary.registerOre("oreNormalNickel", new ItemStack(TFCItems.oreChunk, 1, 8)); //Garnierite
		OreDictionary.registerOre("oreSmallNickel", new ItemStack(TFCItems.smallOreChunk, 1, 8)); //Garnierite
		OreDictionary.registerOre("oreRichNickel", new ItemStack(TFCItems.oreChunk, 1, 26)); //Garnierite
		OreDictionary.registerOre("orePoorNickel", new ItemStack(TFCItems.oreChunk, 1, 44)); //Garnierite

		OreDictionary.registerOre("oreNormalZinc", new ItemStack(TFCItems.oreChunk, 1, 12)); //Sphalerite
		OreDictionary.registerOre("oreSmallZinc", new ItemStack(TFCItems.smallOreChunk, 1, 12)); //Sphalerite
		OreDictionary.registerOre("oreRichZinc", new ItemStack(TFCItems.oreChunk, 1, 30)); //Sphalerite
		OreDictionary.registerOre("orePoorZinc", new ItemStack(TFCItems.oreChunk, 1, 48)); //Sphalerite

		OreDictionary.registerOre("oreNormalOsmium", new ItemStack(TFCItems.oreChunk, 1, 14)); //Native Osmium
		OreDictionary.registerOre("oreSmallOsmium", new ItemStack(TFCItems.smallOreChunk, 1, 14)); //Native Osmium
		OreDictionary.registerOre("oreRichOsmium", new ItemStack(TFCItems.oreChunk, 1, 32)); //Native Osmium
		OreDictionary.registerOre("orePoorOsmium", new ItemStack(TFCItems.oreChunk, 1, 50)); //Native Osmium

		OreDictionary.registerOre("oreNormalAluminum", new ItemStack(TFCItems.oreChunk, 1, 15)); //Bauxite
		OreDictionary.registerOre("oreSmallAluminum", new ItemStack(TFCItems.smallOreChunk, 1, 15)); //Bauxite
		OreDictionary.registerOre("oreRichAluminum", new ItemStack(TFCItems.oreChunk, 1, 33)); //Bauxite
		OreDictionary.registerOre("orePoorAluminum", new ItemStack(TFCItems.oreChunk, 1, 51)); //Bauxite

		OreDictionary.registerOre("oreNormalTungsten", new ItemStack(TFCItems.oreChunk, 1, 16)); //Scheelite
		OreDictionary.registerOre("oreNormalTungsten", new ItemStack(TFCItems.oreChunk, 1, 17)); //Wolframite
		OreDictionary.registerOre("oreSmallTungsten", new ItemStack(TFCItems.smallOreChunk, 1, 16)); //Scheelite
		OreDictionary.registerOre("oreSmallTungsten", new ItemStack(TFCItems.smallOreChunk, 1, 17)); //Wolframite
		OreDictionary.registerOre("oreRichTungsten", new ItemStack(TFCItems.oreChunk, 1, 34)); //Scheelite
		OreDictionary.registerOre("oreRichTungsten", new ItemStack(TFCItems.oreChunk, 1, 35)); //Wolframite
		OreDictionary.registerOre("orePoorTungsten", new ItemStack(TFCItems.oreChunk, 1, 52)); //Scheelite
		OreDictionary.registerOre("orePoorTungsten", new ItemStack(TFCItems.oreChunk, 1, 53)); //Wolframite



		OreDictionary.registerOre("oreKaolinite", new ItemStack(TFCItems.oreMineralChunk, 1, 0));
		OreDictionary.registerOre("oreGypsum", new ItemStack(TFCItems.oreMineralChunk, 1, 1));
		OreDictionary.registerOre("oreSatinspar", new ItemStack(TFCItems.oreMineralChunk, 1, 2));
		OreDictionary.registerOre("oreSelenite", new ItemStack(TFCItems.oreMineralChunk, 1, 3));
		OreDictionary.registerOre("oreGraphite", new ItemStack(TFCItems.oreMineralChunk, 1, 4));
		OreDictionary.registerOre("oreDiamond", new ItemStack(TFCItems.oreMineralChunk, 1, 5)); //Kimberlite
		OreDictionary.registerOre("orePetrifiedWood", new ItemStack(TFCItems.oreMineralChunk, 1, 6));
		OreDictionary.registerOre("oreSulfur", new ItemStack(TFCItems.oreMineralChunk, 1, 7));
		OreDictionary.registerOre("oreJet", new ItemStack(TFCItems.oreMineralChunk, 1, 8));
		OreDictionary.registerOre("oreMicrocline", new ItemStack(TFCItems.oreMineralChunk, 1, 9));
		OreDictionary.registerOre("oreUranium", new ItemStack(TFCItems.oreMineralChunk, 1, 10)); //Pitchblende

		OreDictionary.registerOre("oreRedstone", new ItemStack(TFCItems.oreMineralChunk, 1, 11)); //Cinnabar
		OreDictionary.registerOre("oreRedstone", new ItemStack(TFCItems.oreMineralChunk, 1, 12)); //Cryolite
		OreDictionary.registerOre("oreSaltpeter", new ItemStack(TFCItems.oreMineralChunk, 1, 13));
		OreDictionary.registerOre("oreSerpentine", new ItemStack(TFCItems.oreMineralChunk, 1, 14));
		OreDictionary.registerOre("oreBorax", new ItemStack(TFCItems.oreMineralChunk, 1, 15));
		OreDictionary.registerOre("oreCoal", new ItemStack(TFCItems.oreMineralChunk, 1, 16)); //Bituminous Coal
		OreDictionary.registerOre("oreCoal", new ItemStack(TFCItems.oreMineralChunk, 1, 17)); //Lignite
		OreDictionary.registerOre("oreOlivine", new ItemStack(TFCItems.oreMineralChunk, 1, 18));
		OreDictionary.registerOre("oreLapis", new ItemStack(TFCItems.oreMineralChunk, 1, 19));
		OreDictionary.registerOre("oreSylvite", new ItemStack(TFCItems.oreMineralChunk, 1, 20));
		OreDictionary.registerOre("oreApatite", new ItemStack(TFCItems.oreMineralChunk, 1, 21));
		OreDictionary.registerOre("oreScapolite", new ItemStack(TFCItems.oreMineralChunk, 1, 22));
		OreDictionary.registerOre("oreStrontium", new ItemStack(TFCItems.oreMineralChunk, 1, 23));
		OreDictionary.registerOre("oreQuartz", new ItemStack(TFCItems.oreMineralChunk, 1, 24));
		OreDictionary.registerOre("gemQuartz", new ItemStack(TFCItems.oreMineralChunk, 1, 24));

		//Ore Powders
		OreDictionary.registerOre("dustFlux", new ItemStack(TFCItems.powder, 1, 0)); //Flux
		OreDictionary.registerOre("dustKaolinite", new ItemStack(TFCItems.powder, 1, 1)); //Kaolinite
		OreDictionary.registerOre("dustGraphite", new ItemStack(TFCItems.powder, 1, 2)); //Graphite
		OreDictionary.registerOre("dustSulfur", new ItemStack(TFCItems.powder, 1, 3)); //Sulfur
		OreDictionary.registerOre("dustSaltpeter", new ItemStack(TFCItems.powder, 1, 4)); //Saltpeter
		OreDictionary.registerOre("dustLapis", new ItemStack(TFCItems.powder, 1, 6)); //Lapis
		OreDictionary.registerOre("dustSalt", new ItemStack(TFCItems.powder, 1, 9)); //Salt

		//Nuggets
		OreDictionary.registerOre("nuggetBismuth", new ItemStack(TFCItems.smallMetalChunk, 1, 0)); //Bismuth
		OreDictionary.registerOre("nuggetCopper", new ItemStack(TFCItems.smallMetalChunk, 1, 1)); //Copper
		OreDictionary.registerOre("nuggetGold", new ItemStack(TFCItems.smallMetalChunk, 1, 2)); //Gold
		OreDictionary.registerOre("nuggetIron", new ItemStack(TFCItems.smallMetalChunk, 1, 3)); //Iron
		OreDictionary.registerOre("nuggetLead", new ItemStack(TFCItems.smallMetalChunk, 1, 4)); //Lead
		OreDictionary.registerOre("nuggetNickel", new ItemStack(TFCItems.smallMetalChunk, 1, 5)); //Nickel
		OreDictionary.registerOre("nuggetPigIron", new ItemStack(TFCItems.smallMetalChunk, 1, 6)); //PigIron
		OreDictionary.registerOre("nuggetPlatinum", new ItemStack(TFCItems.smallMetalChunk, 1, 7)); //Platinum
		OreDictionary.registerOre("nuggetSilver", new ItemStack(TFCItems.smallMetalChunk, 1, 8)); //Silver
		OreDictionary.registerOre("nuggetSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 9)); //Steel
		OreDictionary.registerOre("nuggetTin", new ItemStack(TFCItems.smallMetalChunk, 1, 10)); //Tin
		OreDictionary.registerOre("nuggetZinc", new ItemStack(TFCItems.smallMetalChunk, 1, 11)); //Zinc
		OreDictionary.registerOre("nuggetOsmium", new ItemStack(TFCItems.smallMetalChunk, 1, 12)); //Osmium
		OreDictionary.registerOre("nuggetAluminum", new ItemStack(TFCItems.smallMetalChunk, 1, 13)); //Aluminum
		OreDictionary.registerOre("nuggetTungsten", new ItemStack(TFCItems.smallMetalChunk, 1, 14)); //Tungsten

		OreDictionary.registerOre("nuggetBismuthBronze", new ItemStack(TFCItems.smallMetalChunk, 1, 15)); //BismuthBronze
		OreDictionary.registerOre("nuggetAnyBronze", new ItemStack(TFCItems.smallMetalChunk, 1, 15)); //BismuthBronze
		OreDictionary.registerOre("nuggetBlackBronze", new ItemStack(TFCItems.smallMetalChunk, 1, 16)); //BlackBronze
		OreDictionary.registerOre("nuggetAnyBronze", new ItemStack(TFCItems.smallMetalChunk, 1, 16)); //BlackBronze
		OreDictionary.registerOre("nuggetBlackSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 17)); //BlackSteel
		OreDictionary.registerOre("nuggetBlueSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 18)); //BlueSteel
		OreDictionary.registerOre("nuggetBrass", new ItemStack(TFCItems.smallMetalChunk, 1, 19)); //Brass
		OreDictionary.registerOre("nuggetBronze", new ItemStack(TFCItems.smallMetalChunk, 1, 20)); //Bronze
		OreDictionary.registerOre("nuggetAnyBronze", new ItemStack(TFCItems.smallMetalChunk, 1, 20)); //Bronze
		OreDictionary.registerOre("nuggetHCBlackSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 21)); //HCBlackSteel
		OreDictionary.registerOre("nuggetHCBlueSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 22)); //HCBlueSteel
		OreDictionary.registerOre("nuggetHCRedSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 23)); //HCRedSteel
		OreDictionary.registerOre("nuggetRedSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 24)); //RedSteel
		OreDictionary.registerOre("nuggetRoseGold", new ItemStack(TFCItems.smallMetalChunk, 1, 25)); //RoseGold
		OreDictionary.registerOre("nuggetSterlingSilver", new ItemStack(TFCItems.smallMetalChunk, 1, 26)); //SterlingSilver
		OreDictionary.registerOre("nuggetWeakSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 27)); //WeakSteel
		OreDictionary.registerOre("nuggetWeakBlueSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 28)); //WeakBlueSteel
		OreDictionary.registerOre("nuggetWeakRedSteel", new ItemStack(TFCItems.smallMetalChunk, 1, 29)); //WeakRedSteel
		OreDictionary.registerOre("nuggetElectrum", new ItemStack(TFCItems.smallMetalChunk, 1, 30)); //Electrum
		OreDictionary.registerOre("nuggetCupronickel", new ItemStack(TFCItems.smallMetalChunk, 1, 31)); //Cupronickel
		OreDictionary.registerOre("nuggetConstantan", new ItemStack(TFCItems.smallMetalChunk, 1, 31)); //Cupronickel

		//Ingots
		OreDictionary.registerOre("ingotBismuth", new ItemStack(TFCItems.bismuthIngot));
		OreDictionary.registerOre("ingotTin", new ItemStack(TFCItems.tinIngot));
		OreDictionary.registerOre("ingotZinc", new ItemStack(TFCItems.zincIngot));
		OreDictionary.registerOre("ingotCopper", new ItemStack(TFCItems.copperIngot));
		OreDictionary.registerOre("ingotAnyBronze", new ItemStack(TFCItems.bronzeIngot));
		OreDictionary.registerOre("ingotAnyBronze", new ItemStack(TFCItems.bismuthBronzeIngot));
		OreDictionary.registerOre("ingotAnyBronze", new ItemStack(TFCItems.blackBronzeIngot));
		OreDictionary.registerOre("ingotBronze", new ItemStack(TFCItems.bronzeIngot));
		OreDictionary.registerOre("ingotBismuthBronze", new ItemStack(TFCItems.bismuthBronzeIngot));
		OreDictionary.registerOre("ingotBlackBronze", new ItemStack(TFCItems.blackBronzeIngot));
		OreDictionary.registerOre("ingotBrass", new ItemStack(TFCItems.brassIngot));
		OreDictionary.registerOre("ingotLead", new ItemStack(TFCItems.leadIngot));
		OreDictionary.registerOre("ingotGold", new ItemStack(TFCItems.goldIngot));
		OreDictionary.registerOre("ingotRoseGold", new ItemStack(TFCItems.roseGoldIngot));
		OreDictionary.registerOre("ingotSilver", new ItemStack(TFCItems.silverIngot));
		OreDictionary.registerOre("ingotSterlingSilver", new ItemStack(TFCItems.sterlingSilverIngot));
		OreDictionary.registerOre("ingotPlatinum", new ItemStack(TFCItems.platinumIngot));
		OreDictionary.registerOre("ingotWroughtIron", new ItemStack(TFCItems.wroughtIronIngot));
		OreDictionary.registerOre("ingotIron", new ItemStack(TFCItems.wroughtIronIngot));
		OreDictionary.registerOre("ingotNickel", new ItemStack(TFCItems.nickelIngot));
		OreDictionary.registerOre("ingotPigIron", new ItemStack(TFCItems.pigIronIngot));
		OreDictionary.registerOre("ingotSteel", new ItemStack(TFCItems.steelIngot));
		OreDictionary.registerOre("ingotBlackSteel", new ItemStack(TFCItems.blackSteelIngot));
		OreDictionary.registerOre("ingotRedSteel", new ItemStack(TFCItems.redSteelIngot));
		OreDictionary.registerOre("ingotBlueSteel", new ItemStack(TFCItems.blueSteelIngot));
		OreDictionary.registerOre("ingotUnknown", new ItemStack(TFCItems.unknownIngot));
		OreDictionary.registerOre("ingotElectrum", new ItemStack(TFCItems.electrumIngot));
		OreDictionary.registerOre("ingotCupronickel", new ItemStack(TFCItems.cupronickelIngot));
		OreDictionary.registerOre("ingotConstantan", new ItemStack(TFCItems.cupronickelIngot));
		OreDictionary.registerOre("ingotOsmium", new ItemStack(TFCItems.osmiumIngot));
		OreDictionary.registerOre("ingotAluminum", new ItemStack(TFCItems.aluminumIngot));
		OreDictionary.registerOre("ingotTungsten", new ItemStack(TFCItems.tungstenIngot));

		//Double Ingots
		OreDictionary.registerOre("ingotDoubleBismuth", new ItemStack(TFCItems.bismuthIngot2x));
		OreDictionary.registerOre("ingotDoubleTin", new ItemStack(TFCItems.tinIngot2x));
		OreDictionary.registerOre("ingotDoubleZinc", new ItemStack(TFCItems.zincIngot2x));
		OreDictionary.registerOre("ingotDoubleCopper", new ItemStack(TFCItems.copperIngot2x));
		OreDictionary.registerOre("ingotDoubleAnyBronze", new ItemStack(TFCItems.bronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleAnyBronze", new ItemStack(TFCItems.bismuthBronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleAnyBronze", new ItemStack(TFCItems.blackBronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleBronze", new ItemStack(TFCItems.bronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleBismuthBronze", new ItemStack(TFCItems.bismuthBronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleBlackBronze", new ItemStack(TFCItems.blackBronzeIngot2x));
		OreDictionary.registerOre("ingotDoubleBrass", new ItemStack(TFCItems.brassIngot2x));
		OreDictionary.registerOre("ingotDoubleLead", new ItemStack(TFCItems.leadIngot2x));
		OreDictionary.registerOre("ingotDoubleGold", new ItemStack(TFCItems.goldIngot2x));
		OreDictionary.registerOre("ingotDoubleRoseGold", new ItemStack(TFCItems.roseGoldIngot2x));
		OreDictionary.registerOre("ingotDoubleSilver", new ItemStack(TFCItems.silverIngot2x));
		OreDictionary.registerOre("ingotDoubleSterlingSilver", new ItemStack(TFCItems.sterlingSilverIngot2x));
		OreDictionary.registerOre("ingotDoublePlatinum", new ItemStack(TFCItems.platinumIngot2x));
		OreDictionary.registerOre("ingotDoubleWroughtIron", new ItemStack(TFCItems.wroughtIronIngot2x));
		OreDictionary.registerOre("ingotDoubleIron", new ItemStack(TFCItems.wroughtIronIngot2x));
		OreDictionary.registerOre("ingotDoubleNickel", new ItemStack(TFCItems.nickelIngot2x));
		OreDictionary.registerOre("ingotDoublePigIron", new ItemStack(TFCItems.pigIronIngot2x));
		OreDictionary.registerOre("ingotDoubleSteel", new ItemStack(TFCItems.steelIngot2x));
		OreDictionary.registerOre("ingotDoubleBlackSteel", new ItemStack(TFCItems.blackSteelIngot2x));
		OreDictionary.registerOre("ingotDoubleRedSteel", new ItemStack(TFCItems.redSteelIngot2x));
		OreDictionary.registerOre("ingotDoubleBlueSteel", new ItemStack(TFCItems.blueSteelIngot2x));
		OreDictionary.registerOre("ingotDoubleElectrum", new ItemStack(TFCItems.electrumIngot2x));
		OreDictionary.registerOre("ingotDoubleCupronickel", new ItemStack(TFCItems.cupronickelIngot2x));
		OreDictionary.registerOre("ingotDoubleConstantan", new ItemStack(TFCItems.cupronickelIngot2x));
		OreDictionary.registerOre("ingotDoubleOsmium", new ItemStack(TFCItems.osmiumIngot2x));
		OreDictionary.registerOre("ingotDoubleAluminum", new ItemStack(TFCItems.aluminumIngot2x));
		OreDictionary.registerOre("ingotDoubleTungsten", new ItemStack(TFCItems.tungstenIngot2x));

		//Sheets
		OreDictionary.registerOre("plateBismuth", new ItemStack(TFCItems.bismuthSheet));
		OreDictionary.registerOre("plateTin", new ItemStack(TFCItems.tinSheet));
		OreDictionary.registerOre("plateZinc", new ItemStack(TFCItems.zincSheet));
		OreDictionary.registerOre("plateCopper", new ItemStack(TFCItems.copperSheet));
		OreDictionary.registerOre("plateAnyBronze", new ItemStack(TFCItems.bronzeSheet));
		OreDictionary.registerOre("plateAnyBronze", new ItemStack(TFCItems.bismuthBronzeSheet));
		OreDictionary.registerOre("plateAnyBronze", new ItemStack(TFCItems.blackBronzeSheet));
		OreDictionary.registerOre("plateBronze", new ItemStack(TFCItems.bronzeSheet));
		OreDictionary.registerOre("plateBismuthBronze", new ItemStack(TFCItems.bismuthBronzeSheet));
		OreDictionary.registerOre("plateBlackBronze", new ItemStack(TFCItems.blackBronzeSheet));
		OreDictionary.registerOre("plateBrass", new ItemStack(TFCItems.brassSheet));
		OreDictionary.registerOre("plateLead", new ItemStack(TFCItems.leadSheet));
		OreDictionary.registerOre("plateGold", new ItemStack(TFCItems.goldSheet));
		OreDictionary.registerOre("plateRoseGold", new ItemStack(TFCItems.roseGoldSheet));
		OreDictionary.registerOre("plateSilver", new ItemStack(TFCItems.silverSheet));
		OreDictionary.registerOre("plateSterlingSilver", new ItemStack(TFCItems.sterlingSilverSheet));
		OreDictionary.registerOre("platePlatinum", new ItemStack(TFCItems.platinumSheet));
		OreDictionary.registerOre("plateWroughtIron", new ItemStack(TFCItems.wroughtIronSheet));
		OreDictionary.registerOre("plateIron", new ItemStack(TFCItems.wroughtIronSheet));
		OreDictionary.registerOre("plateNickel", new ItemStack(TFCItems.nickelSheet));
		OreDictionary.registerOre("platePigIron", new ItemStack(TFCItems.pigIronSheet));
		OreDictionary.registerOre("plateSteel", new ItemStack(TFCItems.steelSheet));
		OreDictionary.registerOre("plateBlackSteel", new ItemStack(TFCItems.blackSteelSheet));
		OreDictionary.registerOre("plateRedSteel", new ItemStack(TFCItems.redSteelSheet));
		OreDictionary.registerOre("plateBlueSteel", new ItemStack(TFCItems.blueSteelSheet));
		OreDictionary.registerOre("plateElectrum", new ItemStack(TFCItems.electrumSheet));
		OreDictionary.registerOre("plateCupronickel", new ItemStack(TFCItems.cupronickelSheet));
		OreDictionary.registerOre("plateConstantan", new ItemStack(TFCItems.cupronickelSheet));
		OreDictionary.registerOre("plateOsmium", new ItemStack(TFCItems.osmiumSheet));
		OreDictionary.registerOre("plateAluminum", new ItemStack(TFCItems.aluminumSheet));
		OreDictionary.registerOre("plateTungsten", new ItemStack(TFCItems.tungstenSheet));

		//Double Sheets
		OreDictionary.registerOre("plateDoubleBismuth", new ItemStack(TFCItems.bismuthSheet2x));
		OreDictionary.registerOre("plateDoubleTin", new ItemStack(TFCItems.tinSheet2x));
		OreDictionary.registerOre("plateDoubleZinc", new ItemStack(TFCItems.zincSheet2x));
		OreDictionary.registerOre("plateDoubleCopper", new ItemStack(TFCItems.copperSheet2x));
		OreDictionary.registerOre("plateDoubleAnyBronze", new ItemStack(TFCItems.bronzeSheet2x));
		OreDictionary.registerOre("plateDoubleAnyBronze", new ItemStack(TFCItems.bismuthBronzeSheet2x));
		OreDictionary.registerOre("plateDoubleAnyBronze", new ItemStack(TFCItems.blackBronzeSheet2x));
		OreDictionary.registerOre("plateDoubleBronze", new ItemStack(TFCItems.bronzeSheet2x));
		OreDictionary.registerOre("plateDoubleBismuthBronze", new ItemStack(TFCItems.bismuthBronzeSheet2x));
		OreDictionary.registerOre("plateDoubleBlackBronze", new ItemStack(TFCItems.blackBronzeSheet2x));
		OreDictionary.registerOre("plateDoubleBrass", new ItemStack(TFCItems.brassSheet2x));
		OreDictionary.registerOre("plateDoubleLead", new ItemStack(TFCItems.leadSheet2x));
		OreDictionary.registerOre("plateDoubleGold", new ItemStack(TFCItems.goldSheet2x));
		OreDictionary.registerOre("plateDoubleRoseGold", new ItemStack(TFCItems.roseGoldSheet2x));
		OreDictionary.registerOre("plateDoubleSilver", new ItemStack(TFCItems.silverSheet2x));
		OreDictionary.registerOre("plateDoubleSterlingSilver", new ItemStack(TFCItems.sterlingSilverSheet2x));
		OreDictionary.registerOre("plateDoublePlatinum", new ItemStack(TFCItems.platinumSheet2x));
		OreDictionary.registerOre("plateDoubleWroughtIron", new ItemStack(TFCItems.wroughtIronSheet2x));
		OreDictionary.registerOre("plateDoubleIron", new ItemStack(TFCItems.wroughtIronSheet2x));
		OreDictionary.registerOre("plateDoubleNickel", new ItemStack(TFCItems.nickelSheet2x));
		OreDictionary.registerOre("plateDoublePigIron", new ItemStack(TFCItems.pigIronSheet2x));
		OreDictionary.registerOre("plateDoubleSteel", new ItemStack(TFCItems.steelSheet2x));
		OreDictionary.registerOre("plateDoubleBlackSteel", new ItemStack(TFCItems.blackSteelSheet2x));
		OreDictionary.registerOre("plateDoubleRedSteel", new ItemStack(TFCItems.redSteelSheet2x));
		OreDictionary.registerOre("plateDoubleBlueSteel", new ItemStack(TFCItems.blueSteelSheet2x));
		OreDictionary.registerOre("plateDoubleElectrum", new ItemStack(TFCItems.electrumSheet2x));
		OreDictionary.registerOre("plateDoubleCupronickel", new ItemStack(TFCItems.cupronickelSheet2x));
		OreDictionary.registerOre("plateDoubleConstantan", new ItemStack(TFCItems.cupronickelSheet2x));
		OreDictionary.registerOre("plateDoubleOsmium", new ItemStack(TFCItems.osmiumSheet2x));
		OreDictionary.registerOre("plateDoubleAluminum", new ItemStack(TFCItems.aluminumSheet2x));
		OreDictionary.registerOre("plateDoubleTungsten", new ItemStack(TFCItems.tungstenSheet2x));

		//Blocks
		OreDictionary.registerOre("blockBismuth", new ItemStack(TFCBlocks.metalBlock, 1, 0));
		OreDictionary.registerOre("blockCopper", new ItemStack(TFCBlocks.metalBlock, 1, 1));
		OreDictionary.registerOre("blockGold", new ItemStack(TFCBlocks.metalBlock, 1, 2));
		OreDictionary.registerOre("blockWroughtIron", new ItemStack(TFCBlocks.metalBlock, 1, 3));
		OreDictionary.registerOre("blockIron", new ItemStack(TFCBlocks.metalBlock, 1, 3));
		OreDictionary.registerOre("blockLead", new ItemStack(TFCBlocks.metalBlock, 1, 4));
		OreDictionary.registerOre("blockNickel", new ItemStack(TFCBlocks.metalBlock, 1, 5));
		OreDictionary.registerOre("blockPigIron", new ItemStack(TFCBlocks.metalBlock, 1, 6));
		OreDictionary.registerOre("blockPlatinum", new ItemStack(TFCBlocks.metalBlock, 1, 7));
		OreDictionary.registerOre("blockSilver", new ItemStack(TFCBlocks.metalBlock, 1, 8));
		OreDictionary.registerOre("blockSteel", new ItemStack(TFCBlocks.metalBlock, 1, 9));
		OreDictionary.registerOre("blockTin", new ItemStack(TFCBlocks.metalBlock, 1, 10));
		OreDictionary.registerOre("blockZinc", new ItemStack(TFCBlocks.metalBlock, 1, 11));
		OreDictionary.registerOre("blockOsmium", new ItemStack(TFCBlocks.metalBlock, 1, 12));
		OreDictionary.registerOre("blockAluminum", new ItemStack(TFCBlocks.metalBlock, 1, 13));
		OreDictionary.registerOre("blockTungsten", new ItemStack(TFCBlocks.metalBlock, 1, 14));

		OreDictionary.registerOre("blockAnyBronze", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 0));
		OreDictionary.registerOre("blockBismuthBronze", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 0));
		OreDictionary.registerOre("blockAnyBronze", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 1));
		OreDictionary.registerOre("blockBlackBronze", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 1));
		OreDictionary.registerOre("blockBlackSteel", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 2));
		OreDictionary.registerOre("blockBlueSteel", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 3));
		OreDictionary.registerOre("blockBrass", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 4));
		OreDictionary.registerOre("blockAnyBronze", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 5));
		OreDictionary.registerOre("blockBronze", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 5));
		OreDictionary.registerOre("blockRedSteel", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 6));
		OreDictionary.registerOre("blockRoseGold", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 7));
		OreDictionary.registerOre("blockSterlingSilver", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 8));
		OreDictionary.registerOre("blockElectrum", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 9));
		OreDictionary.registerOre("blockCupronickel", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 10));
		OreDictionary.registerOre("blockConstantan", new ItemStack(TFCBlocks.metalAlloyBlock, 1, 10));

		//Gems
		OreDictionary.registerOre("gemChippedAgate", new ItemStack(TFCItems.gemAgate));
		OreDictionary.registerOre("gemChippedAmethyst", new ItemStack(TFCItems.gemAmethyst));
		OreDictionary.registerOre("gemChippedBeryl", new ItemStack(TFCItems.gemBeryl));
		OreDictionary.registerOre("gemChippedDiamond", new ItemStack(TFCItems.gemDiamond));
		OreDictionary.registerOre("gemChippedEmerald", new ItemStack(TFCItems.gemEmerald));
		OreDictionary.registerOre("gemChippedGarnet", new ItemStack(TFCItems.gemGarnet));
		OreDictionary.registerOre("gemChippedJade", new ItemStack(TFCItems.gemJade));
		OreDictionary.registerOre("gemChippedJasper", new ItemStack(TFCItems.gemJasper));
		OreDictionary.registerOre("gemChippedOpal", new ItemStack(TFCItems.gemOpal));
		OreDictionary.registerOre("gemChippedRuby", new ItemStack(TFCItems.gemRuby));
		OreDictionary.registerOre("gemChippedSapphire", new ItemStack(TFCItems.gemSapphire));
		OreDictionary.registerOre("gemChippedTopaz", new ItemStack(TFCItems.gemTopaz));
		OreDictionary.registerOre("gemChippedTourmaline", new ItemStack(TFCItems.gemTourmaline));

		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemAgate));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemAmethyst));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemBeryl));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemDiamond));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemEmerald));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemGarnet));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemJade));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemJasper));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemOpal));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemRuby));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemSapphire));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemTopaz));
		OreDictionary.registerOre("gemChipped", new ItemStack(TFCItems.gemTourmaline));

		OreDictionary.registerOre("gemFlawedAgate", new ItemStack(TFCItems.gemAgate, 1, 1));
		OreDictionary.registerOre("gemFlawedAmethyst", new ItemStack(TFCItems.gemAmethyst, 1, 1));
		OreDictionary.registerOre("gemFlawedBeryl", new ItemStack(TFCItems.gemBeryl, 1, 1));
		OreDictionary.registerOre("gemFlawedDiamond", new ItemStack(TFCItems.gemDiamond, 1, 1));
		OreDictionary.registerOre("gemFlawedEmerald", new ItemStack(TFCItems.gemEmerald, 1, 1));
		OreDictionary.registerOre("gemFlawedGarnet", new ItemStack(TFCItems.gemGarnet, 1, 1));
		OreDictionary.registerOre("gemFlawedJade", new ItemStack(TFCItems.gemJade, 1, 1));
		OreDictionary.registerOre("gemFlawedJasper", new ItemStack(TFCItems.gemJasper, 1, 1));
		OreDictionary.registerOre("gemFlawedOpal", new ItemStack(TFCItems.gemOpal, 1, 1));
		OreDictionary.registerOre("gemFlawedRuby", new ItemStack(TFCItems.gemRuby, 1, 1));
		OreDictionary.registerOre("gemFlawedSapphire", new ItemStack(TFCItems.gemSapphire, 1, 1));
		OreDictionary.registerOre("gemFlawedTopaz", new ItemStack(TFCItems.gemTopaz, 1, 1));
		OreDictionary.registerOre("gemFlawedTourmaline", new ItemStack(TFCItems.gemTourmaline, 1, 1));

		OreDictionary.registerOre("gemAgate", new ItemStack(TFCItems.gemAgate, 1, 2));
		OreDictionary.registerOre("gemAmethyst", new ItemStack(TFCItems.gemAmethyst, 1, 2));
		OreDictionary.registerOre("gemBeryl", new ItemStack(TFCItems.gemBeryl, 1, 2));
		OreDictionary.registerOre("gemDiamond", new ItemStack(TFCItems.gemDiamond, 1, 2));
		OreDictionary.registerOre("gemEmerald", new ItemStack(TFCItems.gemEmerald, 1, 2));
		OreDictionary.registerOre("gemGarnet", new ItemStack(TFCItems.gemGarnet, 1, 2));
		OreDictionary.registerOre("gemJade", new ItemStack(TFCItems.gemJade, 1, 2));
		OreDictionary.registerOre("gemJasper", new ItemStack(TFCItems.gemJasper, 1, 2));
		OreDictionary.registerOre("gemOpal", new ItemStack(TFCItems.gemOpal, 1, 2));
		OreDictionary.registerOre("gemRuby", new ItemStack(TFCItems.gemRuby, 1, 2));
		OreDictionary.registerOre("gemSapphire", new ItemStack(TFCItems.gemSapphire, 1, 2));
		OreDictionary.registerOre("gemTopaz", new ItemStack(TFCItems.gemTopaz, 1, 2));
		OreDictionary.registerOre("gemTourmaline", new ItemStack(TFCItems.gemTourmaline, 1, 2));

		OreDictionary.registerOre("gemFlawlessAgate", new ItemStack(TFCItems.gemAgate, 1, 3));
		OreDictionary.registerOre("gemFlawlessAmethyst", new ItemStack(TFCItems.gemAmethyst, 1, 3));
		OreDictionary.registerOre("gemFlawlessBeryl", new ItemStack(TFCItems.gemBeryl, 1, 3));
		OreDictionary.registerOre("gemFlawlessDiamond", new ItemStack(TFCItems.gemDiamond, 1, 3));
		OreDictionary.registerOre("gemFlawlessEmerald", new ItemStack(TFCItems.gemEmerald, 1, 3));
		OreDictionary.registerOre("gemFlawlessGarnet", new ItemStack(TFCItems.gemGarnet, 1, 3));
		OreDictionary.registerOre("gemFlawlessJade", new ItemStack(TFCItems.gemJade, 1, 3));
		OreDictionary.registerOre("gemFlawlessJasper", new ItemStack(TFCItems.gemJasper, 1, 3));
		OreDictionary.registerOre("gemFlawlessOpal", new ItemStack(TFCItems.gemOpal, 1, 3));
		OreDictionary.registerOre("gemFlawlessRuby", new ItemStack(TFCItems.gemRuby, 1, 3));
		OreDictionary.registerOre("gemFlawlessSapphire", new ItemStack(TFCItems.gemSapphire, 1, 3));
		OreDictionary.registerOre("gemFlawlessTopaz", new ItemStack(TFCItems.gemTopaz, 1, 3));
		OreDictionary.registerOre("gemFlawlessTourmaline", new ItemStack(TFCItems.gemTourmaline, 1, 3));

		OreDictionary.registerOre("gemExquisiteAgate", new ItemStack(TFCItems.gemAgate, 1, 4));
		OreDictionary.registerOre("gemExquisiteAmethyst", new ItemStack(TFCItems.gemAmethyst, 1, 4));
		OreDictionary.registerOre("gemExquisiteBeryl", new ItemStack(TFCItems.gemBeryl, 1, 4));
		OreDictionary.registerOre("gemExquisiteDiamond", new ItemStack(TFCItems.gemDiamond, 1, 4));
		OreDictionary.registerOre("gemExquisiteEmerald", new ItemStack(TFCItems.gemEmerald, 1, 4));
		OreDictionary.registerOre("gemExquisiteGarnet", new ItemStack(TFCItems.gemGarnet, 1, 4));
		OreDictionary.registerOre("gemExquisiteJade", new ItemStack(TFCItems.gemJade, 1, 4));
		OreDictionary.registerOre("gemExquisiteJasper", new ItemStack(TFCItems.gemJasper, 1, 4));
		OreDictionary.registerOre("gemExquisiteOpal", new ItemStack(TFCItems.gemOpal, 1, 4));
		OreDictionary.registerOre("gemExquisiteRuby", new ItemStack(TFCItems.gemRuby, 1, 4));
		OreDictionary.registerOre("gemExquisiteSapphire", new ItemStack(TFCItems.gemSapphire, 1, 4));
		OreDictionary.registerOre("gemExquisiteTopaz", new ItemStack(TFCItems.gemTopaz, 1, 4));
		OreDictionary.registerOre("gemExquisiteTourmaline", new ItemStack(TFCItems.gemTourmaline, 1, 4));

		//Fuel
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.coal, 1, 0));
		OreDictionary.registerOre("gemCoal", new ItemStack(TFCItems.coal, 1, 0));
		OreDictionary.registerOre("gemCharcoal", new ItemStack(Items.coal, 1, 1));
		OreDictionary.registerOre("gemCharcoal", new ItemStack(TFCItems.coal, 1, 1));
		OreDictionary.registerOre("dustCoke", new ItemStack(TFCItems.coal, 1, 2));

		//Stone
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.stoneIgEx, 1, WILD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.stoneIgIn, 1, WILD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.stoneMM, 1, WILD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.stoneSed, 1, WILD));

		//Cobblestone
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.stoneIgExCobble, 1, WILD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.stoneIgInCobble, 1, WILD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.stoneMMCobble, 1, WILD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.stoneSedCobble, 1, WILD));

		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.stoneIgExCobble, 1, WILD));
		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.stoneIgInCobble, 1, WILD));
		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.stoneMMCobble, 1, WILD));
		OreDictionary.registerOre("stoneCobble", new ItemStack(TFCBlocks.stoneSedCobble, 1, WILD));

		//Stone Brick Items
		OreDictionary.registerOre("ingotStoneBrick", new ItemStack(TFCItems.stoneBrick, 1, WILD));

		//Stone Bricks
		OreDictionary.registerOre("stoneBrick", new ItemStack(Blocks.stonebrick));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.stoneIgExBrick, 1, WILD));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.stoneIgInBrick, 1, WILD));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.stoneMMBrick, 1, WILD));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.stoneSedBrick, 1, WILD));

		//Smooth Stone
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.stoneIgExSmooth, 1, WILD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.stoneIgInSmooth, 1, WILD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.stoneMMSmooth, 1, WILD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.stoneSedSmooth, 1, WILD));

		//Crafting Table
		OreDictionary.registerOre("craftingTableWood", new ItemStack(TFCBlocks.workbench));
		OreDictionary.registerOre("craftingTableWood", new ItemStack(Blocks.crafting_table));

		//Dyes
		OreDictionary.registerOre("dyeBlack", new ItemStack(TFCItems.dye, 1, 0));
		OreDictionary.registerOre("dyeRed", new ItemStack(TFCItems.powder, 1, 5));
		OreDictionary.registerOre("dyeRed", new ItemStack(TFCItems.dye, 1, 1));
		OreDictionary.registerOre("dyeGreen", new ItemStack(TFCItems.powder, 1, 8));
		OreDictionary.registerOre("dyeGreen", new ItemStack(TFCItems.dye, 1, 2));
		OreDictionary.registerOre("dyeBrown", new ItemStack(TFCItems.dye, 1, 3));
		OreDictionary.registerOre("dyeBlue", new ItemStack(TFCItems.powder, 1, 6));
		OreDictionary.registerOre("dyeBlue", new ItemStack(TFCItems.dye, 1, 4));
		OreDictionary.registerOre("dyePurple", new ItemStack(TFCItems.dye, 1, 5));
		OreDictionary.registerOre("dyeCyan", new ItemStack(TFCItems.dye, 1, 6));
		OreDictionary.registerOre("dyeLightGray", new ItemStack(TFCItems.dye, 1, 7));
		OreDictionary.registerOre("dyeGray", new ItemStack(TFCItems.dye, 1, 8));
		OreDictionary.registerOre("dyePink", new ItemStack(TFCItems.dye, 1, 9));
		OreDictionary.registerOre("dyeLime", new ItemStack(TFCItems.dye, 1, 10));
		OreDictionary.registerOre("dyeYellow", new ItemStack(TFCItems.powder, 1, 7));
		OreDictionary.registerOre("dyeYellow", new ItemStack(TFCItems.dye, 1, 11));
		OreDictionary.registerOre("dyeLightBlue", new ItemStack(TFCItems.dye, 1, 12));
		OreDictionary.registerOre("dyeMagenta", new ItemStack(TFCItems.dye, 1, 13));
		OreDictionary.registerOre("dyeOrange", new ItemStack(TFCItems.dye, 1, 14));
		OreDictionary.registerOre("dyeWhite", new ItemStack(TFCItems.dye, 1, 15));

		//Materials
		OreDictionary.registerOre("materialLeather", new ItemStack(Items.leather));
		OreDictionary.registerOre("materialLeather", new ItemStack(TFCItems.leather));

		OreDictionary.registerOre("materialString", new ItemStack(Items.string));
		OreDictionary.registerOre("materialString", new ItemStack(TFCItems.woolYarn));

		OreDictionary.registerOre("materialCloth", new ItemStack(TFCItems.woolCloth));
		OreDictionary.registerOre("materialCloth", new ItemStack(TFCItems.silkCloth));
		OreDictionary.registerOre("materialWool", new ItemStack(TFCItems.wool, 1, WILD));

		// Stone Toolheads
		OreDictionary.registerOre("itemAxeStoneHead", new ItemStack(TFCItems.igExStoneAxeHead, 1, WILD));
		OreDictionary.registerOre("itemAxeStoneHead", new ItemStack(TFCItems.igInStoneAxeHead, 1, WILD));
		OreDictionary.registerOre("itemAxeStoneHead", new ItemStack(TFCItems.mMStoneAxeHead, 1, WILD));
		OreDictionary.registerOre("itemAxeStoneHead", new ItemStack(TFCItems.sedStoneAxeHead, 1, WILD));

		OreDictionary.registerOre("itemHoeStoneHead", new ItemStack(TFCItems.igExStoneHoeHead, 1, WILD));
		OreDictionary.registerOre("itemHoeStoneHead", new ItemStack(TFCItems.igInStoneHoeHead, 1, WILD));
		OreDictionary.registerOre("itemHoeStoneHead", new ItemStack(TFCItems.mMStoneHoeHead, 1, WILD));
		OreDictionary.registerOre("itemHoeStoneHead", new ItemStack(TFCItems.sedStoneHoeHead, 1, WILD));

		OreDictionary.registerOre("itemShovelStoneHead", new ItemStack(TFCItems.igExStoneShovelHead, 1, WILD));
		OreDictionary.registerOre("itemShovelStoneHead", new ItemStack(TFCItems.igInStoneShovelHead, 1, WILD));
		OreDictionary.registerOre("itemShovelStoneHead", new ItemStack(TFCItems.mMStoneShovelHead, 1, WILD));
		OreDictionary.registerOre("itemShovelStoneHead", new ItemStack(TFCItems.sedStoneShovelHead, 1, WILD));

		OreDictionary.registerOre("itemJavelinStoneHead", new ItemStack(TFCItems.igExStoneJavelinHead, 1, WILD));
		OreDictionary.registerOre("itemJavelinStoneHead", new ItemStack(TFCItems.igInStoneJavelinHead, 1, WILD));
		OreDictionary.registerOre("itemJavelinStoneHead", new ItemStack(TFCItems.mMStoneJavelinHead, 1, WILD));
		OreDictionary.registerOre("itemJavelinStoneHead", new ItemStack(TFCItems.sedStoneJavelinHead, 1, WILD));

		// Tools are also registered with their material to help prevent issues with unification.

		OreDictionary.registerOre("itemHammerTier3", new ItemStack(TFCItems.steelHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerTier3", new ItemStack(TFCItems.blackSteelHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerTier3", new ItemStack(TFCItems.blueSteelHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerTier3", new ItemStack(TFCItems.redSteelHammer, 1, WILD));
		for (Item axe : Recipes.axes)
			OreDictionary.registerOre("itemAxe", new ItemStack(axe, 1, WILD));

		OreDictionary.registerOre("itemAxeStone", new ItemStack(TFCItems.sedAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeStone", new ItemStack(TFCItems.igInAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeStone", new ItemStack(TFCItems.igExAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeStone", new ItemStack(TFCItems.mMAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeBismuthBronze", new ItemStack(TFCItems.bismuthBronzeAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeBlackBronze", new ItemStack(TFCItems.blackBronzeAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeBlackSteel", new ItemStack(TFCItems.blackSteelAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeBlueSteel", new ItemStack(TFCItems.blueSteelAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeBronze", new ItemStack(TFCItems.bronzeAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeCopper", new ItemStack(TFCItems.copperAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeWroughtIron", new ItemStack(TFCItems.wroughtIronAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeRedSteel", new ItemStack(TFCItems.redSteelAxe, 1, WILD));
		OreDictionary.registerOre("itemAxeSteel", new ItemStack(TFCItems.steelAxe, 1, WILD));

		for (Item chisel : Recipes.chisels)
			OreDictionary.registerOre("itemChisel", new ItemStack(chisel, 1, WILD));

		OreDictionary.registerOre("itemChiselBismuthBronze", new ItemStack(TFCItems.bismuthBronzeChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselBlackBronze", new ItemStack(TFCItems.blackBronzeChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselBlackSteel", new ItemStack(TFCItems.blackSteelChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselBlueSteel", new ItemStack(TFCItems.blueSteelChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselBronze", new ItemStack(TFCItems.bronzeChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselCopper", new ItemStack(TFCItems.copperChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselWroughtIron", new ItemStack(TFCItems.wroughtIronChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselRedSteel", new ItemStack(TFCItems.redSteelChisel, 1, WILD));
		OreDictionary.registerOre("itemChiselSteel", new ItemStack(TFCItems.steelChisel, 1, WILD));

		for (Item hammer : Recipes.hammers)
			OreDictionary.registerOre("itemHammer", new ItemStack(hammer, 1, WILD));

		OreDictionary.registerOre("itemHammerStone", new ItemStack(TFCItems.stoneHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerBismuthBronze", new ItemStack(TFCItems.bismuthBronzeHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerBlackBronze", new ItemStack(TFCItems.blackBronzeHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerBlackSteel", new ItemStack(TFCItems.blackSteelHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerBlueSteel", new ItemStack(TFCItems.blueSteelHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerBronze", new ItemStack(TFCItems.bronzeHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerCopper", new ItemStack(TFCItems.copperHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerWroughtIron", new ItemStack(TFCItems.wroughtIronHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerRedSteel", new ItemStack(TFCItems.redSteelHammer, 1, WILD));
		OreDictionary.registerOre("itemHammerSteel", new ItemStack(TFCItems.steelHammer, 1, WILD));

		for (Item knife : Recipes.knives)
			OreDictionary.registerOre("itemKnife", new ItemStack(knife, 1, WILD));

		OreDictionary.registerOre("itemKnifeStone", new ItemStack(TFCItems.stoneKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeBismuthBronze", new ItemStack(TFCItems.bismuthBronzeKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeBlackBronze", new ItemStack(TFCItems.blackBronzeKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeBlackSteel", new ItemStack(TFCItems.blackSteelKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeBlueSteel", new ItemStack(TFCItems.blueSteelKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeBronze", new ItemStack(TFCItems.bronzeKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeCopper", new ItemStack(TFCItems.copperKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeWroughtIron", new ItemStack(TFCItems.wroughtIronKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeRedSteel", new ItemStack(TFCItems.redSteelKnife, 1, WILD));
		OreDictionary.registerOre("itemKnifeSteel", new ItemStack(TFCItems.steelKnife, 1, WILD));

		for (Item saw : Recipes.saws)
			OreDictionary.registerOre("itemSaw", new ItemStack(saw, 1, WILD));

		OreDictionary.registerOre("itemSawBismuthBronze", new ItemStack(TFCItems.bismuthBronzeSaw, 1, WILD));
		OreDictionary.registerOre("itemSawBlackBronze", new ItemStack(TFCItems.blackBronzeSaw, 1, WILD));
		OreDictionary.registerOre("itemSawBlackSteel", new ItemStack(TFCItems.blackSteelSaw, 1, WILD));
		OreDictionary.registerOre("itemSawBlueSteel", new ItemStack(TFCItems.blueSteelSaw, 1, WILD));
		OreDictionary.registerOre("itemSawBronze", new ItemStack(TFCItems.bronzeSaw, 1, WILD));
		OreDictionary.registerOre("itemSawCopper", new ItemStack(TFCItems.copperSaw, 1, WILD));
		OreDictionary.registerOre("itemSawWroughtIron", new ItemStack(TFCItems.wroughtIronSaw, 1, WILD));
		OreDictionary.registerOre("itemSawRedSteel", new ItemStack(TFCItems.redSteelSaw, 1, WILD));
		OreDictionary.registerOre("itemSawSteel", new ItemStack(TFCItems.steelSaw, 1, WILD));

		for (Item scythe : Recipes.scythes)
			OreDictionary.registerOre("itemScythe", new ItemStack(scythe, 1, WILD));

		OreDictionary.registerOre("itemScytheBismuthBronze", new ItemStack(TFCItems.bismuthBronzeScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheBlackBronze", new ItemStack(TFCItems.blackBronzeScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheBlackSteel", new ItemStack(TFCItems.blackSteelScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheBlueSteel", new ItemStack(TFCItems.blueSteelScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheBronze", new ItemStack(TFCItems.bronzeScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheCopper", new ItemStack(TFCItems.copperScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheWroughtIron", new ItemStack(TFCItems.wroughtIronScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheRedSteel", new ItemStack(TFCItems.redSteelScythe, 1, WILD));
		OreDictionary.registerOre("itemScytheSteel", new ItemStack(TFCItems.steelScythe, 1, WILD));

		for (Item pick : Recipes.picks)
			OreDictionary.registerOre("itemPick", new ItemStack(pick, 1, WILD));

		OreDictionary.registerOre("itemPickBismuthBronze", new ItemStack(TFCItems.bismuthBronzePick, 1, WILD));
		OreDictionary.registerOre("itemPickBlackBronze", new ItemStack(TFCItems.blackBronzePick, 1, WILD));
		OreDictionary.registerOre("itemPickBlackSteel", new ItemStack(TFCItems.blackSteelPick, 1, WILD));
		OreDictionary.registerOre("itemPickBlueSteel", new ItemStack(TFCItems.blueSteelPick, 1, WILD));
		OreDictionary.registerOre("itemPickBronze", new ItemStack(TFCItems.bronzePick, 1, WILD));
		OreDictionary.registerOre("itemPickCopper", new ItemStack(TFCItems.copperPick, 1, WILD));
		OreDictionary.registerOre("itemPickWroughtIron", new ItemStack(TFCItems.wroughtIronPick, 1, WILD));
		OreDictionary.registerOre("itemPickRedSteel", new ItemStack(TFCItems.redSteelPick, 1, WILD));
		OreDictionary.registerOre("itemPickSteel", new ItemStack(TFCItems.steelPick, 1, WILD));

		for (Item proPick : Recipes.proPicks)
			OreDictionary.registerOre("itemProPick", new ItemStack(proPick, 1, WILD));

		OreDictionary.registerOre("itemProPickBismuthBronze", new ItemStack(TFCItems.proPickBismuthBronze, 1, WILD));
		OreDictionary.registerOre("itemProPickBlackBronze", new ItemStack(TFCItems.proPickBlackBronze, 1, WILD));
		OreDictionary.registerOre("itemProPickBlackSteel", new ItemStack(TFCItems.proPickBlackSteel, 1, WILD));
		OreDictionary.registerOre("itemProPickBlueSteel", new ItemStack(TFCItems.proPickBlueSteel, 1, WILD));
		OreDictionary.registerOre("itemProPickBronze", new ItemStack(TFCItems.proPickBronze, 1, WILD));
		OreDictionary.registerOre("itemProPickCopper", new ItemStack(TFCItems.proPickCopper, 1, WILD));
		OreDictionary.registerOre("itemProPickWroughtIron", new ItemStack(TFCItems.proPickIron, 1, WILD));
		OreDictionary.registerOre("itemProPickRedSteel", new ItemStack(TFCItems.proPickRedSteel, 1, WILD));
		OreDictionary.registerOre("itemProPickSteel", new ItemStack(TFCItems.proPickSteel, 1, WILD));

		for (Item shovel : Recipes.shovels)
			OreDictionary.registerOre("itemShovel", new ItemStack(shovel, 1, WILD));

		OreDictionary.registerOre("itemShovelStone", new ItemStack(TFCItems.sedShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelStone", new ItemStack(TFCItems.igInShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelStone", new ItemStack(TFCItems.igExShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelStone", new ItemStack(TFCItems.mMShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelBismuthBronze", new ItemStack(TFCItems.bismuthBronzeShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelBlackBronze", new ItemStack(TFCItems.blackBronzeShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelBlackSteel", new ItemStack(TFCItems.blackSteelShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelBlueSteel", new ItemStack(TFCItems.blueSteelShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelBronze", new ItemStack(TFCItems.bronzeShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelCopper", new ItemStack(TFCItems.copperShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelWroughtIron", new ItemStack(TFCItems.wroughtIronShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelRedSteel", new ItemStack(TFCItems.redSteelShovel, 1, WILD));
		OreDictionary.registerOre("itemShovelSteel", new ItemStack(TFCItems.steelShovel, 1, WILD));

		for (Item hoe : Recipes.hoes)
			OreDictionary.registerOre("itemHoe", new ItemStack(hoe, 1, WILD));

		OreDictionary.registerOre("itemHoeStone", new ItemStack(TFCItems.sedHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeStone", new ItemStack(TFCItems.igInHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeStone", new ItemStack(TFCItems.igExHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeStone", new ItemStack(TFCItems.mMHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeBismuthBronze", new ItemStack(TFCItems.bismuthBronzeHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeBlackBronze", new ItemStack(TFCItems.blackBronzeHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeBlackSteel", new ItemStack(TFCItems.blackSteelHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeBlueSteel", new ItemStack(TFCItems.blueSteelHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeBronze", new ItemStack(TFCItems.bronzeHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeCopper", new ItemStack(TFCItems.copperHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeWroughtIron", new ItemStack(TFCItems.wroughtIronHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeRedSteel", new ItemStack(TFCItems.redSteelHoe, 1, WILD));
		OreDictionary.registerOre("itemHoeSteel", new ItemStack(TFCItems.steelHoe, 1, WILD));

		//Weapons are also registered with their material to help prevent issues with unification.
		for (Item sword : Recipes.swords)
			OreDictionary.registerOre("itemSword", new ItemStack(sword, 1, WILD));

		OreDictionary.registerOre("itemSwordBismuthBronze", new ItemStack(TFCItems.bismuthBronzeSword, 1, WILD));
		OreDictionary.registerOre("itemSwordBlackBronze", new ItemStack(TFCItems.blackBronzeSword, 1, WILD));
		OreDictionary.registerOre("itemSwordBlackSteel", new ItemStack(TFCItems.blackSteelSword, 1, WILD));
		OreDictionary.registerOre("itemSwordBlueSteel", new ItemStack(TFCItems.blueSteelSword, 1, WILD));
		OreDictionary.registerOre("itemSwordBronze", new ItemStack(TFCItems.bronzeSword, 1, WILD));
		OreDictionary.registerOre("itemSwordCopper", new ItemStack(TFCItems.copperSword, 1, WILD));
		OreDictionary.registerOre("itemSwordWroughtIron", new ItemStack(TFCItems.wroughtIronSword, 1, WILD));
		OreDictionary.registerOre("itemSwordRedSteel", new ItemStack(TFCItems.redSteelSword, 1, WILD));
		OreDictionary.registerOre("itemSwordSteel", new ItemStack(TFCItems.steelSword, 1, WILD));

		for (Item mace : Recipes.maces)
			OreDictionary.registerOre("itemMace", new ItemStack(mace, 1, WILD));

		OreDictionary.registerOre("itemMaceBismuthBronze", new ItemStack(TFCItems.bismuthBronzeMace, 1, WILD));
		OreDictionary.registerOre("itemMaceBlackBronze", new ItemStack(TFCItems.blackBronzeMace, 1, WILD));
		OreDictionary.registerOre("itemMaceBlackSteel", new ItemStack(TFCItems.blackSteelMace, 1, WILD));
		OreDictionary.registerOre("itemMaceBlueSteel", new ItemStack(TFCItems.blueSteelMace, 1, WILD));
		OreDictionary.registerOre("itemMaceBronze", new ItemStack(TFCItems.bronzeMace, 1, WILD));
		OreDictionary.registerOre("itemMaceCopper", new ItemStack(TFCItems.copperMace, 1, WILD));
		OreDictionary.registerOre("itemMaceWroughtIron", new ItemStack(TFCItems.wroughtIronMace, 1, WILD));
		OreDictionary.registerOre("itemMaceRedSteel", new ItemStack(TFCItems.redSteelMace, 1, WILD));
		OreDictionary.registerOre("itemMaceSteel", new ItemStack(TFCItems.steelMace, 1, WILD));

		for (Item javelin : Recipes.javelins)
			OreDictionary.registerOre("itemJavelin", new ItemStack(javelin, 1, WILD));

		OreDictionary.registerOre("itemJavelinStone", new ItemStack(TFCItems.sedStoneJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinStone", new ItemStack(TFCItems.igInStoneJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinStone", new ItemStack(TFCItems.igExStoneJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinStone", new ItemStack(TFCItems.mMStoneJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinBismuthBronze", new ItemStack(TFCItems.bismuthBronzeJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinBlackBronze", new ItemStack(TFCItems.blackBronzeJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinBlackSteel", new ItemStack(TFCItems.blackSteelJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinBlueSteel", new ItemStack(TFCItems.blueSteelJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinBronze", new ItemStack(TFCItems.bronzeJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinCopper", new ItemStack(TFCItems.copperJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinWroughtIron", new ItemStack(TFCItems.wroughtIronJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinRedSteel", new ItemStack(TFCItems.redSteelJavelin, 1, WILD));
		OreDictionary.registerOre("itemJavelinSteel", new ItemStack(TFCItems.steelJavelin, 1, WILD));

		for (Item tuyere : Recipes.tuyeres)
			OreDictionary.registerOre("itemTuyere", new ItemStack(tuyere, 1, WILD));

		OreDictionary.registerOre("itemTuyereBismuthBronze", new ItemStack(TFCItems.tuyereBismuthBronze, 1, WILD));
		OreDictionary.registerOre("itemTuyereBlackBronze", new ItemStack(TFCItems.tuyereBlackBronze, 1, WILD));
		OreDictionary.registerOre("itemTuyereBlackSteel", new ItemStack(TFCItems.tuyereBlackSteel, 1, WILD));
		OreDictionary.registerOre("itemTuyereBlueSteel", new ItemStack(TFCItems.tuyereBlueSteel, 1, WILD));
		OreDictionary.registerOre("itemTuyereBronze", new ItemStack(TFCItems.tuyereBronze, 1, WILD));
		OreDictionary.registerOre("itemTuyereCopper", new ItemStack(TFCItems.tuyereCopper, 1, WILD));
		OreDictionary.registerOre("itemTuyereWroughtIron", new ItemStack(TFCItems.tuyereWroughtIron, 1, WILD));
		OreDictionary.registerOre("itemTuyereRedSteel", new ItemStack(TFCItems.tuyereRedSteel, 1, WILD));
		OreDictionary.registerOre("itemTuyereSteel", new ItemStack(TFCItems.tuyereSteel, 1, WILD));

		OreDictionary.registerOre("itemShearsBlackSteel", new ItemStack(TFCItems.shearsBlackSteel, 1, WILD));

		//Miscellaneous Items
		OreDictionary.registerOre("lumpClay", new ItemStack(Items.clay_ball));
		OreDictionary.registerOre("lumpClay", new ItemStack(TFCItems.clayBall, 1, 0));

		OreDictionary.registerOre("itemArrow", new ItemStack(Items.arrow));
		OreDictionary.registerOre("itemArrow", new ItemStack(TFCItems.arrow));

		OreDictionary.registerOre("itemReed", new ItemStack(Items.reeds));
		OreDictionary.registerOre("itemReed", new ItemStack(TFCItems.reeds));

		OreDictionary.registerOre("itemRock", new ItemStack(TFCItems.looseRock, 1, WILD));

		OreDictionary.registerOre("bucketEmpty", new ItemStack(Items.bucket));
		OreDictionary.registerOre("bucketEmpty", new ItemStack(TFCItems.woodenBucketEmpty));
		OreDictionary.registerOre("bucketEmpty", new ItemStack(TFCItems.redSteelBucketEmpty));
		OreDictionary.registerOre("bucketEmpty", new ItemStack(TFCItems.blueSteelBucketEmpty));

		OreDictionary.registerOre("bucketWater", new ItemStack(Items.water_bucket));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.woodenBucketWater, 1, WILD));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.redSteelBucketWater, 1, WILD));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.woodenBucketSaltWater, 1, WILD));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFCItems.redSteelBucketSaltWater, 1, WILD));

		OreDictionary.registerOre("bucketFreshWater", new ItemStack(TFCItems.woodenBucketWater, 1, WILD));
		OreDictionary.registerOre("bucketFreshWater", new ItemStack(TFCItems.redSteelBucketWater, 1, WILD));

		OreDictionary.registerOre("bucketSaltWater", new ItemStack(TFCItems.woodenBucketSaltWater, 1, WILD));
		OreDictionary.registerOre("bucketSaltWater", new ItemStack(TFCItems.redSteelBucketSaltWater, 1, WILD));

		OreDictionary.registerOre("bucketLava", new ItemStack(Items.lava_bucket));
		OreDictionary.registerOre("bucketLava", new ItemStack(TFCItems.blueSteelBucketLava));

		OreDictionary.registerOre("bucketMilk", new ItemStack(Items.milk_bucket));
		OreDictionary.registerOre("bucketMilk", new ItemStack(TFCItems.woodenBucketMilk));

		OreDictionary.registerOre("toolFlintSteel", new ItemStack(Items.flint_and_steel, 1, WILD));
		OreDictionary.registerOre("toolFlintSteel", new ItemStack(TFCItems.flintSteel, 1, WILD));

		//Miscellaneous Blocks
		OreDictionary.registerOre("blockSand", new ItemStack(Blocks.sand));
		OreDictionary.registerOre("blockSand", new ItemStack(TFCBlocks.sand, 1, WILD));
		OreDictionary.registerOre("blockSand", new ItemStack(TFCBlocks.sand2, 1, WILD));

		//OreDictionary.registerOre("blockGravel", new ItemStack(Blocks.gravel));
		OreDictionary.registerOre("blockGravel", new ItemStack(TFCBlocks.gravel, 1, WILD));
		OreDictionary.registerOre("blockGravel", new ItemStack(TFCBlocks.gravel2, 1, WILD));

		OreDictionary.registerOre("blockDirt", new ItemStack(Blocks.dirt));
		OreDictionary.registerOre("blockDirt", new ItemStack(TFCBlocks.dirt, 1, WILD));
		OreDictionary.registerOre("blockDirt", new ItemStack(TFCBlocks.dirt2, 1, WILD));

		OreDictionary.registerOre("blockTorch", new ItemStack(Blocks.torch));
		OreDictionary.registerOre("blockTorch", new ItemStack(TFCBlocks.torch));

		OreDictionary.registerOre("blockPumpkin", new ItemStack(Blocks.pumpkin));
		OreDictionary.registerOre("blockPumpkin", new ItemStack(TFCBlocks.pumpkin));
		OreDictionary.registerOre("blockLitPumpkin", new ItemStack(Blocks.lit_pumpkin));
		OreDictionary.registerOre("blockLitPumpkin", new ItemStack(TFCBlocks.litPumpkin));

		OreDictionary.registerOre("blockIce", new ItemStack(TFCBlocks.ice, 1, WILD));

		//Seed Bags
		for (Item seed : Recipes.seeds)
			OreDictionary.registerOre("seedBag", new ItemStack(seed, 1, WILD));
		OreDictionary.registerOre("seedWheat", new ItemStack(TFCItems.seedsWheat, 1, WILD));
		OreDictionary.registerOre("seedMaize", new ItemStack(TFCItems.seedsMaize, 1, WILD));
		OreDictionary.registerOre("seedTomato", new ItemStack(TFCItems.seedsTomato, 1, WILD));
		OreDictionary.registerOre("seedBarley", new ItemStack(TFCItems.seedsBarley, 1, WILD));
		OreDictionary.registerOre("seedRye", new ItemStack(TFCItems.seedsRye, 1, WILD));
		OreDictionary.registerOre("seedOat", new ItemStack(TFCItems.seedsOat, 1, WILD));
		OreDictionary.registerOre("seedRice", new ItemStack(TFCItems.seedsRice, 1, WILD));
		OreDictionary.registerOre("seedPotato", new ItemStack(TFCItems.seedsPotato, 1, WILD));
		OreDictionary.registerOre("seedOnion", new ItemStack(TFCItems.seedsOnion, 1, WILD));
		OreDictionary.registerOre("seedCabbage", new ItemStack(TFCItems.seedsCabbage, 1, WILD));
		OreDictionary.registerOre("seedGarlic", new ItemStack(TFCItems.seedsGarlic, 1, WILD));
		OreDictionary.registerOre("seedCarrot", new ItemStack(TFCItems.seedsCarrot, 1, WILD));
		OreDictionary.registerOre("seedSugarcane", new ItemStack(TFCItems.seedsSugarcane, 1, WILD));
		OreDictionary.registerOre("seedYelloBellPepper", new ItemStack(TFCItems.seedsYellowBellPepper, 1, WILD));
		OreDictionary.registerOre("seedRedBellPepper", new ItemStack(TFCItems.seedsRedBellPepper, 1, WILD));
		OreDictionary.registerOre("seedSoybean", new ItemStack(TFCItems.seedsSoybean, 1, WILD));
		OreDictionary.registerOre("seedGreenbean", new ItemStack(TFCItems.seedsGreenbean, 1, WILD));
		OreDictionary.registerOre("seedSquash", new ItemStack(TFCItems.seedsSquash, 1, WILD));
		OreDictionary.registerOre("seedJute", new ItemStack(TFCItems.seedsJute, 1, WILD));

		//Fruit Tree Saplings
		OreDictionary.registerOre("fruitTreeSapling", new ItemStack(TFCBlocks.fruitTreeSapling, 1, WILD));
		OreDictionary.registerOre("fruitTreeSaplingRedApple", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 0));
		OreDictionary.registerOre("fruitTreeSaplingBanana", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 1));
		OreDictionary.registerOre("fruitTreeSaplingOrange", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 2));
		OreDictionary.registerOre("fruitTreeSaplingGreenApple", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 3));
		OreDictionary.registerOre("fruitTreeSaplingLemon", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 4));
		OreDictionary.registerOre("fruitTreeSaplingOlive", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 5));
		OreDictionary.registerOre("fruitTreeSaplingCherry", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 6));
		OreDictionary.registerOre("fruitTreeSaplingPeach", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 7));
		OreDictionary.registerOre("fruitTreeSaplingPlum", new ItemStack(TFCBlocks.fruitTreeSapling, 1, 8));

		//Berry Bushes
		OreDictionary.registerOre("bushBerry", new ItemStack(TFCBlocks.berryBush, 1, WILD));
		OreDictionary.registerOre("bushBerryBlueberry", new ItemStack(TFCBlocks.berryBush, 1, 0));
		OreDictionary.registerOre("bushBerryRaspberry", new ItemStack(TFCBlocks.berryBush, 1, 1));
		OreDictionary.registerOre("bushBerryStrawberry", new ItemStack(TFCBlocks.berryBush, 1, 2));
		OreDictionary.registerOre("bushBerryBlackberry", new ItemStack(TFCBlocks.berryBush, 1, 3));
		OreDictionary.registerOre("bushBerryBunchberry", new ItemStack(TFCBlocks.berryBush, 1, 4));
		OreDictionary.registerOre("bushBerryCranberry", new ItemStack(TFCBlocks.berryBush, 1, 5));
		OreDictionary.registerOre("bushBerrySnowberry", new ItemStack(TFCBlocks.berryBush, 1, 6));
		OreDictionary.registerOre("bushBerryElderberry", new ItemStack(TFCBlocks.berryBush, 1, 7));
		OreDictionary.registerOre("bushBerryGooseberry", new ItemStack(TFCBlocks.berryBush, 1, 8));
		OreDictionary.registerOre("bushBerryCloudberry", new ItemStack(TFCBlocks.berryBush, 1, 9));
	}
}
