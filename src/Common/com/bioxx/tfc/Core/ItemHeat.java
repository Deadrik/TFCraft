package com.bioxx.tfc.Core;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import com.bioxx.tfc.api.*;

public class ItemHeat
{

	public static void setupItemHeat()
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		/**
		 * Heat now increases at a base rate of 1C per tick. Specific heat is now just a multiplier on this rate. 
		 * This means that a metlTemp of 100C will be reached in 5 seconds with a Specific Heat of 1.0 and 10 seconds at 2.0
		 */

		final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

		HeatRaw bismuthRaw = new HeatRaw(0.14, 270);
		HeatRaw bismuthBronzeRaw = new HeatRaw(0.35, 985);
		HeatRaw blackBronzeRaw = new HeatRaw(0.35, 1070);
		HeatRaw blackSteelRaw = new HeatRaw(0.35, 1485);
		HeatRaw blueSteelRaw = new HeatRaw(0.35, 1540);
		HeatRaw brassRaw = new HeatRaw(0.35, 930);
		HeatRaw bronzeRaw = new HeatRaw(0.35, 950);
		HeatRaw copperRaw = new HeatRaw(0.35, 1080);
		HeatRaw goldRaw = new HeatRaw(0.6, 1060);
		HeatRaw ironRaw = new HeatRaw(0.35, 1535);
		HeatRaw leadRaw = new HeatRaw(0.22, 328);
		HeatRaw nickelRaw = new HeatRaw(0.48, 1453);
		HeatRaw pigIronRaw = new HeatRaw(0.35, 1500);
		HeatRaw platinumRaw = new HeatRaw(0.35, 1730);
		HeatRaw redSteelRaw = new HeatRaw(0.35, 1540);
		HeatRaw roseGoldRaw = new HeatRaw(0.35, 960);
		HeatRaw silverRaw = new HeatRaw(0.48, 961);
		HeatRaw steelRaw = new HeatRaw(0.35, 1540);//sh = 0.63F; boil = 3500; melt = 1540;
		HeatRaw sterlingSilverRaw = new HeatRaw(0.35, 900);//sh = 0.72F; boil = 2212; melt = 893;
		HeatRaw tinRaw = new HeatRaw(0.14, 230);
		HeatRaw zincRaw = new HeatRaw(0.21, 420);//sh = 0.66F; boil = 907; melt = 420;

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,2), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,3), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,4), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,5), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,6), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,7), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,8), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,9), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,10), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,11), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,12), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,13), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,35), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,36), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,37), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,38), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,39), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,40), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,41), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,42), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,43), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,44), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,45), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,46), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,47), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,48), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,49), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,50), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,51), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,52), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,53), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,54), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,55), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,56), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,57), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,58), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,59), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,60), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,61), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,62), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,2), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,3), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,4), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,5), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,6), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,7), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,8), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,9), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,10), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,11), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,12), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,13), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		// Invallid
		// manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,35), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		// manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,36), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		// manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,37), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakSteelUnshaped,1), steelRaw,new ItemStack(TFCItems.weakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakRedSteelUnshaped,1), redSteelRaw,new ItemStack(TFCItems.weakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakBlueSteelUnshaped,1), blueSteelRaw,new ItemStack(TFCItems.weakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1), blackSteelRaw,new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1), blueSteelRaw,new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1), redSteelRaw,new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonSteelUnshaped,1), steelRaw,new ItemStack(TFCItems.highCarbonSteelUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakSteelIngot,1), steelRaw,new ItemStack(TFCItems.weakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakRedSteelIngot,1), redSteelRaw,new ItemStack(TFCItems.weakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakBlueSteelIngot,1), blueSteelRaw,new ItemStack(TFCItems.weakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlackSteelIngot,1), blackSteelRaw,new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlueSteelIngot,1), blueSteelRaw,new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonRedSteelIngot,1), redSteelRaw,new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonSteelIngot,1), steelRaw,new ItemStack(TFCItems.highCarbonSteelUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.unknownIngot,1), copperRaw,new ItemStack(TFCItems.unknownUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.unknownUnshaped,1), copperRaw,new ItemStack(TFCItems.unknownUnshaped,1)));

		//Bismuth
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthIngot,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthIngot2x,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthUnshaped,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthSheet,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthSheet2x,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,4,0)));
		//Bismuth Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeIngot,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeIngot2x,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnshaped,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeSheet,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeSheet2x,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil2, 1, 1), bismuthBronzeRaw, null));
		//Black Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeIngot,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeIngot2x, 1), blackBronzeRaw, new ItemStack(TFCItems.blackBronzeUnshaped, 2, 0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnshaped,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeSheet,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeSheet2x,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil2, 1, 2), blackBronzeRaw, null));
		//Black Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelIngot,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelIngot2x,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnshaped,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelSheet,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelSheet2x,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 5), blackSteelRaw, null));
		//Blue Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelIngot,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelIngot2x,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnshaped,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelSheet,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelSheet2x,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 6), blueSteelRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 5), blueSteelRaw, null));
		//Brass
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassIngot,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassIngot2x,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassUnshaped,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassSheet,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassSheet2x,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,4,0)));
		//Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeIngot,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeIngot2x,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnshaped,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeSheet,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeSheet2x,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 2), bronzeRaw, null));
		//Copper
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperIngot,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperIngot2x,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnshaped,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperSheet,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperSheet2x,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedHelmet,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedHelmet,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedChestplate,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedChestplate,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedGreaves,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedGreaves,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedBoots,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedBoots,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 1), copperRaw, null));
		//Gold
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldIngot,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldIngot2x,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldUnshaped,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldSheet,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldSheet2x,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 0), goldRaw, null));
		//Wrought Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronIngot,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bloom, 1, WILDCARD_VALUE), ironRaw, new ItemStack(TFCItems.wroughtIronUnshaped, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.rawBloom, 1, WILDCARD_VALUE), ironRaw, new ItemStack(TFCItems.unknownUnshaped, 1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronIngot2x,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnshaped,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronSheet,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronSheet2x,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronKnifeHead, 1), ironRaw, new ItemStack(TFCItems.wroughtIronUnshaped, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 3), ironRaw, null));
		//Lead
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadIngot,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadIngot2x,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadUnshaped,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadSheet,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadSheet2x,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,4,0)));
		//Nickel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelIngot,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelIngot2x,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelUnshaped,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelSheet,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelSheet2x,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,4,0)));
		//Pig Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronIngot,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronIngot2x,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronUnshaped,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronSheet,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronSheet2x,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,4,0)));
		//Platinum
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumIngot,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumIngot2x,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumUnshaped,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumSheet,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumSheet2x,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 1), platinumRaw, null));
		//Red Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelIngot,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelIngot2x,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnshaped,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelSheet,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelSheet2x,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 7), redSteelRaw, null));
		//Rose Gold
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldIngot,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldIngot2x,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldUnshaped,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldSheet,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldSheet2x,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 2), roseGoldRaw, null));
		//Silver
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverIngot,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverIngot2x,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverUnshaped,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverSheet,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverSheet2x,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 3), silverRaw, null));
		//Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelIngot,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelIngot2x,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnshaped,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelSheet,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelSheet2x,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedHelmet,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedHelmet,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedChestplate,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedChestplate,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedGreaves,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedGreaves,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedBoots,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedBoots,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 4), steelRaw, null));
		//Sterling Silver
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverIngot,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverIngot2x,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverUnshaped,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverSheet,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverSheet2x,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 4), sterlingSilverRaw, null));
		//Tin
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinIngot,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinIngot2x,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinUnshaped,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinSheet,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinSheet2x,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,4,0)));
		//Zinc
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincIngot,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincIngot2x,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincUnshaped,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincSheet,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincSheet2x,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,4,0)));
		//Ceramics
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.sand, 1, WILDCARD_VALUE), 1, 600, new ItemStack(Blocks.glass, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.sand2, 1, WILDCARD_VALUE), 1, 600, new ItemStack(Blocks.glass, 1)));

		//Food
		//Proteins
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.egg, 1), 1, 600, new ItemStack(TFCItems.eggCooked, 1)).setKeepNBT(true));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.porkchopRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.fishRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.beefRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.chickenRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.soybean, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.eggCooked, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.calamariRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.muttonRaw,1),1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.venisonRaw,1),1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.horseMeatRaw, 1), 1, 1200, null));

		//Dairy
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cheese, 1), 1, 1200, null));

		//Grains
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.maizeEar, 1), 1, 1200, null));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wheatDough, 1), 1, 600, new ItemStack(TFCItems.wheatBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.barleyDough, 1), 1, 600, new ItemStack(TFCItems.barleyBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oatDough, 1), 1, 600, new ItemStack(TFCItems.oatBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ryeDough, 1), 1, 600, new ItemStack(TFCItems.ryeBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.riceDough, 1), 1, 600, new ItemStack(TFCItems.riceBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cornmealDough, 1), 1, 600, new ItemStack(TFCItems.cornBread, 1)).setKeepNBT(true));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wheatBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.barleyBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oatBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ryeBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.riceBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cornBread, 1), 1, 1200, null));

		//Vegetables
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tomato, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.potato, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.onion, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cabbage, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.garlic, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.carrot, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.greenbeans, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.greenBellPepper, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.yellowBellPepper, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redBellPepper, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.squash, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.seaWeed, 1), 1, 1200, null));

		//Fruit
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redApple, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.banana, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orange, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.greenApple, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.lemon, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.olive, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cherry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.peach, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.plum, 1), 1, 1200, null));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wintergreenBerry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.raspberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.strawberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bunchberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cranberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.snowberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.elderberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.gooseberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cloudberry, 1), 1, 1200, null));

		//manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealGeneric, 1, WILDCARD_VALUE), 0.2, 200, new ItemStack(Items.bowl, 1)));

		//Other
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.stick, 1, WILDCARD_VALUE), 1, 40, new ItemStack(TFCBlocks.torch, 2)));
	}
}
