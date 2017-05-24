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
		HeatRaw osmiumRaw = new HeatRaw(0.35, 2200); //actual is 3027
		HeatRaw aluminumRaw = new HeatRaw(0.35, 980);
		HeatRaw tungstenRaw = new HeatRaw(0.35, 2200); //actual is 3422
		HeatRaw electrumRaw = new HeatRaw(0.55, 1060);
		HeatRaw cupronickelRaw = new HeatRaw(0.48, 1453);
//Ores
//Normal
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
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,14), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		if (TFCOptions.enableAluminumSmelting) {
			manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk, 1, 15), aluminumRaw, new ItemStack(TFCItems.aluminumUnshaped, 1))); }
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,16), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,17), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Rich
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,18), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,19), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,20), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,21), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,22), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,23), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,24), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,25), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,26), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,27), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,28), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,29), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,30), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,31), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,32), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		if (TFCOptions.enableAluminumSmelting) {
			manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,33), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,1))); }
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,34), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,35), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Poor
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,36), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,37), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,38), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,39), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,40), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,41), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,42), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,43), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,44), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,45), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,46), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,47), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,48), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,49), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,50), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		if (TFCOptions.enableAluminumSmelting) {
				manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,51), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,1))); }
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,52), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.oreChunk,1,53), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Small Ore
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
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,14), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		if (TFCOptions.enableAluminumSmelting) {
			manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk, 1, 15), aluminumRaw, new ItemStack(TFCItems.aluminumUnshaped, 1)));
		}
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,16), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallOreChunk,1,17), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Ore Pile
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,2), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,3), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,4), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,5), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,6), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,7), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,8), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,9), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,10), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,11), ironRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,12), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,13), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,14), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		if (TFCOptions.enableAluminumSmelting) {
			manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile, 1, 15), aluminumRaw, new ItemStack(TFCItems.aluminumUnshaped, 1)));
		}
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,16), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.orePile,1,17), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Nuggets melting
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,0), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,2), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,3), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,4), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,5), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,6), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,7), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,8), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,9), steelRaw,new ItemStack(TFCItems.steelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,10), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,11), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,12), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,13), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,14), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,15), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,16), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,17), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,18), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,19), brassRaw,new ItemStack(TFCItems.brassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,20), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,21), blackSteelRaw,new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,22), blueSteelRaw,new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,23), redSteelRaw,new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,24), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,25), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,26), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,18), steelRaw,new ItemStack(TFCItems.weakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,19), blueSteelRaw,new ItemStack(TFCItems.weakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,20), redSteelRaw,new ItemStack(TFCItems.weakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,30), electrumRaw,new ItemStack(TFCItems.electrumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.smallMetalChunk,1,31), cupronickelRaw,new ItemStack(TFCItems.cupronickelUnshaped,1)));
//Dusts melting
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,0), bismuthRaw, new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,1), copperRaw, new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,2), goldRaw, new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,3), ironRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,4), leadRaw, new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,5), nickelRaw, new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,6), pigIronRaw, new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,7), platinumRaw, new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,8), silverRaw, new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,9), steelRaw, new ItemStack(TFCItems.steelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,10), tinRaw, new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,11), zincRaw, new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,12), osmiumRaw, new ItemStack(TFCItems.osmiumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,13), aluminumRaw, new ItemStack(TFCItems.aluminumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,14), tungstenRaw, new ItemStack(TFCItems.tungstenUnshaped,1)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,15), bismuthBronzeRaw, new ItemStack(TFCItems.bismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,16), blackBronzeRaw, new ItemStack(TFCItems.blackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,17), blackSteelRaw, new ItemStack(TFCItems.blackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,18), blueSteelRaw, new ItemStack(TFCItems.blueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,19), brassRaw, new ItemStack(TFCItems.brassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,20), bronzeRaw, new ItemStack(TFCItems.bronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,21), blackSteelRaw, new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,22), blueSteelRaw, new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,23), redSteelRaw, new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,24), redSteelRaw, new ItemStack(TFCItems.redSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,25), roseGoldRaw, new ItemStack(TFCItems.roseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,26), sterlingSilverRaw, new ItemStack(TFCItems.sterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,18), steelRaw, new ItemStack(TFCItems.weakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,19), blueSteelRaw, new ItemStack(TFCItems.weakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,20), redSteelRaw, new ItemStack(TFCItems.weakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,30), electrumRaw, new ItemStack(TFCItems.electrumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.metalDust,1,31), cupronickelRaw, new ItemStack(TFCItems.cupronickelUnshaped,1)));

//Blocks
//Metals
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,0), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,2), goldRaw,new ItemStack(TFCItems.goldUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,3), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,4), leadRaw,new ItemStack(TFCItems.leadUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,5), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,6), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,7), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,8), silverRaw,new ItemStack(TFCItems.silverUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,9), steelRaw,new ItemStack(TFCItems.steelUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,10), tinRaw,new ItemStack(TFCItems.tinUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,11), zincRaw,new ItemStack(TFCItems.zincUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,12), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,13), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalBlock,1,14), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,8,0)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,2), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,3), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,4), brassRaw,new ItemStack(TFCItems.brassUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,5), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,6), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,7), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,8), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,9), electrumRaw,new ItemStack(TFCItems.electrumUnshaped,8,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.metalAlloyBlock,1,10), cupronickelRaw,new ItemStack(TFCItems.cupronickelUnshaped,8,0)));
//Blooms
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bloom, 1, WILDCARD_VALUE), ironRaw, new ItemStack(TFCItems.wroughtIronUnshaped, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.rawBloom, 1, WILDCARD_VALUE), ironRaw, new ItemStack(TFCItems.unknownUnshaped, 1)));
//Unshaped->Unshaped
//Metals
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthUnshaped,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnshaped,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldUnshaped,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnshaped,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadUnshaped,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelUnshaped,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronUnshaped,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumUnshaped,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverUnshaped,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnshaped,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinUnshaped,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincUnshaped,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.osmiumUnshaped,1), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.aluminumUnshaped,1), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tungstenUnshaped,1), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnshaped,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnshaped,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnshaped,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnshaped,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassUnshaped,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnshaped,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnshaped,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldUnshaped,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverUnshaped,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.electrumUnshaped,1), electrumRaw,new ItemStack(TFCItems.electrumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cupronickelUnshaped,1), cupronickelRaw,new ItemStack(TFCItems.cupronickelUnshaped,1)));
//Unusable
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakSteelUnshaped,1), steelRaw,new ItemStack(TFCItems.weakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakRedSteelUnshaped,1), redSteelRaw,new ItemStack(TFCItems.weakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakBlueSteelUnshaped,1), blueSteelRaw,new ItemStack(TFCItems.weakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1), blackSteelRaw,new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1), blueSteelRaw,new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1), redSteelRaw,new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonSteelUnshaped,1), steelRaw,new ItemStack(TFCItems.highCarbonSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.unknownUnshaped,1), copperRaw,new ItemStack(TFCItems.unknownUnshaped,1)));
//Ingots->Unshaped
//Metals
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthIngot,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperIngot,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldIngot,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronIngot,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadIngot,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelIngot,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronIngot,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumIngot,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverIngot,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelIngot,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinIngot,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincIngot,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.osmiumIngot,1), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.aluminumIngot,1), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tungstenIngot,1), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,1)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeIngot,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeIngot,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelIngot,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelIngot,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassIngot,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeIngot,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelIngot,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldIngot,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverIngot,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.electrumIngot,1), electrumRaw,new ItemStack(TFCItems.electrumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cupronickelIngot,1), cupronickelRaw,new ItemStack(TFCItems.cupronickelUnshaped,1)));
//Unusable
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakSteelIngot,1), steelRaw,new ItemStack(TFCItems.weakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakRedSteelIngot,1), redSteelRaw,new ItemStack(TFCItems.weakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.weakBlueSteelIngot,1), blueSteelRaw,new ItemStack(TFCItems.weakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlackSteelIngot,1), blackSteelRaw,new ItemStack(TFCItems.highCarbonBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonBlueSteelIngot,1), blueSteelRaw,new ItemStack(TFCItems.highCarbonBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonRedSteelIngot,1), redSteelRaw,new ItemStack(TFCItems.highCarbonRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.highCarbonSteelIngot,1), steelRaw,new ItemStack(TFCItems.highCarbonSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.unknownIngot,1), copperRaw,new ItemStack(TFCItems.unknownUnshaped,1)));
//Ingots2x->Unshaped
//Metals
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthIngot2x,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperIngot2x,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldIngot2x,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronIngot2x,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadIngot2x,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelIngot2x,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronIngot2x,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumIngot2x,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverIngot2x,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelIngot2x,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinIngot2x,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincIngot2x,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.osmiumIngot2x,1), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.aluminumIngot2x,1), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tungstenIngot2x,1), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,2,0)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeIngot2x,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeIngot2x, 1), blackBronzeRaw, new ItemStack(TFCItems.blackBronzeUnshaped, 2, 0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelIngot2x,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelIngot2x,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassIngot2x,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeIngot2x,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelIngot2x,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldIngot2x,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverIngot2x,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.electrumIngot2x,1), electrumRaw,new ItemStack(TFCItems.electrumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cupronickelIngot2x,1), cupronickelRaw,new ItemStack(TFCItems.cupronickelUnshaped,2,0)));
//Sheets->Unshaped
//Metals
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthSheet,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperSheet,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldSheet,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronSheet,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadSheet,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelSheet,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronSheet,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumSheet,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverSheet,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelSheet,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinSheet,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincSheet,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.osmiumSheet,1), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.aluminumSheet,1), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tungstenSheet,1), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,2,0)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeSheet,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeSheet,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelSheet,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelSheet,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassSheet,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeSheet,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelSheet,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldSheet,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverSheet,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.electrumSheet,1), electrumRaw,new ItemStack(TFCItems.electrumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cupronickelSheet,1), cupronickelRaw,new ItemStack(TFCItems.cupronickelUnshaped,2,0)));
//Sheets2x->Unshaped
//Metals
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthSheet2x,1), bismuthRaw,new ItemStack(TFCItems.bismuthUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperSheet2x,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.goldSheet2x,1), goldRaw,new ItemStack(TFCItems.goldUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronSheet2x,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.leadSheet2x,1), leadRaw,new ItemStack(TFCItems.leadUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.nickelSheet2x,1), nickelRaw,new ItemStack(TFCItems.nickelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.pigIronSheet2x,1), pigIronRaw,new ItemStack(TFCItems.pigIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.platinumSheet2x,1), platinumRaw,new ItemStack(TFCItems.platinumUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.silverSheet2x,1), silverRaw,new ItemStack(TFCItems.silverUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelSheet2x,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tinSheet2x,1), tinRaw,new ItemStack(TFCItems.tinUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.zincSheet2x,1), zincRaw,new ItemStack(TFCItems.zincUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.osmiumSheet2x,1), osmiumRaw,new ItemStack(TFCItems.osmiumUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.aluminumSheet2x,1), aluminumRaw,new ItemStack(TFCItems.aluminumUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.tungstenSheet2x,1), tungstenRaw,new ItemStack(TFCItems.tungstenUnshaped,4,0)));
//Alloys
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeSheet2x,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeSheet2x,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelSheet2x,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelSheet2x,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.brassSheet2x,1), brassRaw,new ItemStack(TFCItems.brassUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeSheet2x,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelSheet2x,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.roseGoldSheet2x,1), roseGoldRaw,new ItemStack(TFCItems.roseGoldUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.sterlingSilverSheet2x,1), sterlingSilverRaw,new ItemStack(TFCItems.sterlingSilverUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.electrumSheet2x,1), electrumRaw,new ItemStack(TFCItems.electrumUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.cupronickelSheet2x,1), cupronickelRaw,new ItemStack(TFCItems.cupronickelUnshaped,4,0)));
//Armour->Unshaped
//Helmet
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedHelmet,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedHelmet,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedHelmet,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedHelmet,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
//Chestpiece
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedChestplate,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedChestplate,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedChestplate,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedChestplate,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,4,0)));
//Greaves
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedGreaves,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedGreaves,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedGreaves,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedGreaves,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
//Boots
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,0), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,1), bismuthBronzeRaw,new ItemStack(TFCItems.bismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,0), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,1), blackBronzeRaw,new ItemStack(TFCItems.blackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,0), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,1), blackSteelRaw,new ItemStack(TFCItems.blackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,0), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,1), blueSteelRaw,new ItemStack(TFCItems.blueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,0), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,1), bronzeRaw,new ItemStack(TFCItems.bronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedBoots,1,0), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.copperUnfinishedBoots,1,1), copperRaw,new ItemStack(TFCItems.copperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,0), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,1), ironRaw,new ItemStack(TFCItems.wroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,0), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,1), redSteelRaw,new ItemStack(TFCItems.redSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedBoots,1,0), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.steelUnfinishedBoots,1,1), steelRaw,new ItemStack(TFCItems.steelUnshaped,2,0)));
//Anvil->Unshaped
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 1), copperRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 2), bronzeRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 3), ironRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 4), steelRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 5), blackSteelRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 6), blueSteelRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil, 1, 7), redSteelRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil2, 1, 0), roseGoldRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil2, 1, 1), bismuthBronzeRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.anvil2, 1, 2), blackBronzeRaw, null));
//Lamp->Unshaped
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 5), blueSteelRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 0), goldRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 1), platinumRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 2), roseGoldRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 3), silverRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.oilLamp, 1, 4), sterlingSilverRaw, null));
//Misc
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.wroughtIronKnifeHead, 1), ironRaw, new ItemStack(TFCItems.wroughtIronUnshaped, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.blackSteelKnifeHead, 1), blackSteelRaw, new ItemStack(TFCItems.blackSteelUnshaped, 1)));
//Ceramics
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.sand, 1, WILDCARD_VALUE), 1, 600, new ItemStack(Blocks.glass, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.sand2, 1, WILDCARD_VALUE), 1, 600, new ItemStack(Blocks.glass, 1)));
//Food
//Proteins
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.egg, 1), 1, 600, new ItemStack(TFCItems.eggCooked, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.porkchopRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.fishRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.beefRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.bearRaw, 1), 1, 1200, null));
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
//Other
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.stick, 1, WILDCARD_VALUE), 1, 40, new ItemStack(TFCBlocks.torch, 2)));
	}
}
