package com.bioxx.tfc.Core;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.bioxx.tfc.api.*;

public class ItemHeat
{

	public static void SetupItemHeat()
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		/**
		 * Heat now increases at a base rate of 1C per tick. Specific heat is now just a multiplier on this rate. 
		 * This means that a metlTemp of 100C will be reached in 5 seconds with a Specific Heat of 1.0 and 10 seconds at 2.0
		 */

		final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

		HeatRaw BismuthRaw = new HeatRaw(0.14, 270);
		HeatRaw BismuthBronzeRaw = new HeatRaw(0.35, 985);
		HeatRaw BlackBronzeRaw = new HeatRaw(0.35, 1070);
		HeatRaw BlackSteelRaw = new HeatRaw(0.35, 1485);
		HeatRaw BlueSteelRaw = new HeatRaw(0.35, 1540);
		HeatRaw BrassRaw = new HeatRaw(0.35, 930);
		HeatRaw BronzeRaw = new HeatRaw(0.35, 950);
		HeatRaw CopperRaw = new HeatRaw(0.35, 1080);
		HeatRaw GoldRaw = new HeatRaw(0.6, 1060);
		HeatRaw IronRaw = new HeatRaw(0.35, 1535);
		HeatRaw LeadRaw = new HeatRaw(0.22, 328);
		HeatRaw NickelRaw = new HeatRaw(0.48, 1453);
		HeatRaw PigIronRaw = new HeatRaw(0.35, 1500);
		HeatRaw PlatinumRaw = new HeatRaw(0.35, 1730);
		HeatRaw RedSteelRaw = new HeatRaw(0.35, 1540);
		HeatRaw RoseGoldRaw = new HeatRaw(0.35, 960);
		HeatRaw SilverRaw = new HeatRaw(0.48, 961);
		HeatRaw SteelRaw = new HeatRaw(0.35, 1540);//sh = 0.63F; boil = 3500; melt = 1540;
		HeatRaw SterlingSilverRaw = new HeatRaw(0.35, 900);//sh = 0.72F; boil = 2212; melt = 893;
		HeatRaw TinRaw = new HeatRaw(0.14, 230);
		HeatRaw ZincRaw = new HeatRaw(0.21, 420);//sh = 0.66F; boil = 907; melt = 420;

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,2), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,3), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,4), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,5), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,6), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,7), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,8), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,9), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,10), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,11), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,12), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,13), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,35), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,36), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,37), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,38), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,39), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,40), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,41), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,42), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,43), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,44), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,45), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,46), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,47), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,48), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,49), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,50), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,51), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,52), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,53), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,54), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,55), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,56), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,57), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,58), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,59), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,60), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,61), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,62), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,2), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,3), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,4), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,5), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,6), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,7), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,8), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,9), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,10), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,11), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,12), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,13), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		// Invallid
		// manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,35), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		// manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,36), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		// manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,37), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakSteelUnshaped,1), SteelRaw,new ItemStack(TFCItems.WeakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakRedSteelUnshaped,1), RedSteelRaw,new ItemStack(TFCItems.WeakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakBlueSteelUnshaped,1), BlueSteelRaw,new ItemStack(TFCItems.WeakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlackSteelUnshaped,1), BlackSteelRaw,new ItemStack(TFCItems.HCBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlueSteelUnshaped,1), BlueSteelRaw,new ItemStack(TFCItems.HCBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCRedSteelUnshaped,1), RedSteelRaw,new ItemStack(TFCItems.HCRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCSteelUnshaped,1), SteelRaw,new ItemStack(TFCItems.HCSteelUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakSteelIngot,1), SteelRaw,new ItemStack(TFCItems.WeakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakRedSteelIngot,1), RedSteelRaw,new ItemStack(TFCItems.WeakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakBlueSteelIngot,1), BlueSteelRaw,new ItemStack(TFCItems.WeakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlackSteelIngot,1), BlackSteelRaw,new ItemStack(TFCItems.HCBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlueSteelIngot,1), BlueSteelRaw,new ItemStack(TFCItems.HCBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCRedSteelIngot,1), RedSteelRaw,new ItemStack(TFCItems.HCRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCSteelIngot,1), SteelRaw,new ItemStack(TFCItems.HCSteelUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.UnknownIngot,1), CopperRaw,new ItemStack(TFCItems.UnknownUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.UnknownUnshaped,1), CopperRaw,new ItemStack(TFCItems.UnknownUnshaped,1)));

		//Bismuth
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthIngot,1), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthIngot2x,1), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthUnshaped,1), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthSheet,1), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthSheet2x,1), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,4,0)));
		//Bismuth Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeIngot,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeIngot2x,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnshaped,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeSheet,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeSheet2x,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,0), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,0), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,0), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,0), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,1), BismuthBronzeRaw,new ItemStack(TFCItems.BismuthBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil2, 1, 1), BismuthBronzeRaw, null));
		//Black Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeIngot,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeIngot2x,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnshaped,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeSheet,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeSheet2x,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,0), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,0), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,0), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,0), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,1), BlackBronzeRaw,new ItemStack(TFCItems.BlackBronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil2, 1, 2), BlackBronzeRaw, null));
		//Black Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelIngot,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelIngot2x,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnshaped,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelSheet,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelSheet2x,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,0), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,0), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,0), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,0), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,1), BlackSteelRaw,new ItemStack(TFCItems.BlackSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 5), BlackSteelRaw, null));
		//Blue Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelIngot,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelIngot2x,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnshaped,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelSheet,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelSheet2x,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,0), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,0), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,0), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,0), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,1), BlueSteelRaw,new ItemStack(TFCItems.BlueSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 6), BlueSteelRaw, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.OilLamp, 1, 5), BlueSteelRaw, null));
		//Brass
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassIngot,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassIngot2x,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassUnshaped,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassSheet,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassSheet2x,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,4,0)));
		//Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeIngot,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeIngot2x,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnshaped,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeSheet,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeSheet2x,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,0), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,0), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,0), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,0), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,1), BronzeRaw,new ItemStack(TFCItems.BronzeUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 2), BronzeRaw, null));
		//Copper
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperIngot,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperIngot2x,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnshaped,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperSheet,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperSheet2x,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedBoots,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedBoots,1,1), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 1), CopperRaw, null));
		//Gold
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldIngot,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldIngot2x,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldUnshaped,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldSheet,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldSheet2x,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.OilLamp, 1, 0), GoldRaw, null));
		//Wrought Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronIngot,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Bloom, 1, WILDCARD_VALUE), IronRaw, new ItemStack(TFCItems.WroughtIronUnshaped, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RawBloom, 1, WILDCARD_VALUE), IronRaw, new ItemStack(TFCItems.UnknownUnshaped, 1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronIngot2x,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnshaped,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronSheet,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronSheet2x,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,0), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,0), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,0), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,0), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronKnifeHead, 1), IronRaw, new ItemStack(TFCItems.WroughtIronUnshaped, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 3), IronRaw, null));
		//Lead
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadIngot,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadIngot2x,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadUnshaped,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadSheet,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadSheet2x,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,4,0)));
		//Nickel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelIngot,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelIngot2x,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelUnshaped,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelSheet,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelSheet2x,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,4,0)));
		//Pig Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronIngot,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronIngot2x,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronUnshaped,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronSheet,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronSheet2x,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,4,0)));
		//Platinum
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumIngot,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumIngot2x,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumUnshaped,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumSheet,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumSheet2x,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.OilLamp, 1, 1), PlatinumRaw, null));
		//Red Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelIngot,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelIngot2x,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnshaped,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelSheet,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelSheet2x,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,0), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,0), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,0), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,0), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,1), RedSteelRaw,new ItemStack(TFCItems.RedSteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 7), RedSteelRaw, null));
		//Rose Gold
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldIngot,1), RoseGoldRaw,new ItemStack(TFCItems.RoseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldIngot2x,1), RoseGoldRaw,new ItemStack(TFCItems.RoseGoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldUnshaped,1), RoseGoldRaw,new ItemStack(TFCItems.RoseGoldUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldSheet,1), RoseGoldRaw,new ItemStack(TFCItems.RoseGoldUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldSheet2x,1), RoseGoldRaw,new ItemStack(TFCItems.RoseGoldUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.OilLamp, 1, 2), RoseGoldRaw, null));
		//Silver
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverIngot,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverIngot2x,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverUnshaped,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverSheet,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverSheet2x,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.OilLamp, 1, 3), SilverRaw, null));
		//Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelIngot,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelIngot2x,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnshaped,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelSheet,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelSheet2x,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,0), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,0), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,0), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedBoots,1,0), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedBoots,1,1), SteelRaw,new ItemStack(TFCItems.SteelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 4), SteelRaw, null));
		//Sterling Silver
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverIngot,1), SterlingSilverRaw,new ItemStack(TFCItems.SterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverIngot2x,1), SterlingSilverRaw,new ItemStack(TFCItems.SterlingSilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverUnshaped,1), SterlingSilverRaw,new ItemStack(TFCItems.SterlingSilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverSheet,1), SterlingSilverRaw,new ItemStack(TFCItems.SterlingSilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverSheet2x,1), SterlingSilverRaw,new ItemStack(TFCItems.SterlingSilverUnshaped,4,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.OilLamp, 1, 4), SterlingSilverRaw, null));
		//Tin
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinIngot,1), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinIngot2x,1), TinRaw,new ItemStack(TFCItems.TinUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinUnshaped,1), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinSheet,1), TinRaw,new ItemStack(TFCItems.TinUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinSheet2x,1), TinRaw,new ItemStack(TFCItems.TinUnshaped,4,0)));
		//Zinc
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincIngot,1), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincIngot2x,1), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincUnshaped,1), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincSheet,1), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincSheet2x,1), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,4,0)));
		//Ceramics
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Sand, 1, WILDCARD_VALUE), 1, 600, new ItemStack(Blocks.glass, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Sand2, 1, WILDCARD_VALUE), 1, 600, new ItemStack(Blocks.glass, 1)));

		//Food
		//Proteins
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Egg, 1), 1, 600, new ItemStack(TFCItems.EggCooked, 1)).setKeepNBT(true));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.porkchopRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.fishRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.beefRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.chickenRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Soybean, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.EggCooked, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.calamariRaw, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.muttonRaw,1),1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.venisonRaw,1),1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.horseMeatRaw, 1), 1, 1200, null));

		//Dairy
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Cheese, 1), 1, 1200, null));

		//Grains
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MaizeEar, 1), 1, 1200, null));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WheatDough, 1), 1, 600, new ItemStack(TFCItems.WheatBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BarleyDough, 1), 1, 600, new ItemStack(TFCItems.BarleyBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OatDough, 1), 1, 600, new ItemStack(TFCItems.OatBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RyeDough, 1), 1, 600, new ItemStack(TFCItems.RyeBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RiceDough, 1), 1, 600, new ItemStack(TFCItems.RiceBread, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CornmealDough, 1), 1, 600, new ItemStack(TFCItems.CornBread, 1)).setKeepNBT(true));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WheatBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BarleyBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OatBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RyeBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RiceBread, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CornBread, 1), 1, 1200, null));

		//Vegetables
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Tomato, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Potato, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Onion, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Cabbage, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Garlic, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Carrot, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Greenbeans, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GreenBellPepper, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.YellowBellPepper, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedBellPepper, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Squash, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SeaWeed, 1), 1, 1200, null));

		//Fruit
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedApple, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Banana, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Orange, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GreenApple, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Lemon, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Olive, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Cherry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Peach, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Plum, 1), 1, 1200, null));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WintergreenBerry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Blueberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Raspberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Strawberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Blackberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Bunchberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Cranberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Snowberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Elderberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Gooseberry, 1), 1, 1200, null));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Cloudberry, 1), 1, 1200, null));

		//manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealGeneric, 1, WILDCARD_VALUE), 0.2, 200, new ItemStack(Items.bowl, 1)));

		//Other
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Stick, 1, WILDCARD_VALUE), 1, 40, new ItemStack(TFCBlocks.Torch, 2)));
	}
}
