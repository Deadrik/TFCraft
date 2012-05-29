package net.minecraft.src.TFC_Core.General;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagFloat;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;

public class TFCHeat 
{
	
	static
	{
	    HeatManager manager = HeatManager.getInstance();
		
		HeatRaw BismuthRaw = new HeatRaw(0.75F, 271, 1564);
		HeatRaw BismuthBronzeRaw = new HeatRaw(0.65F, 985, 1941);
		HeatRaw BlackBronzeRaw = new HeatRaw(0.7F, 1070, 2219);
		HeatRaw BlackSteelRaw = new HeatRaw(0.63F, 1485, 2726);
		HeatRaw BlueSteelRaw = new HeatRaw(0.6F, 1680, 3460);
		HeatRaw BrassRaw = new HeatRaw(0.65F, 930, 1760);
		HeatRaw BronzeRaw = new HeatRaw(0.65F, 950, 2380);
		HeatRaw CopperRaw = new HeatRaw(0.67F, 1084);
		HeatRaw GoldRaw = new HeatRaw(0.77F, 1063);
		HeatRaw IronRaw = new HeatRaw(0.64F, 1536);
		HeatRaw LeadRaw = new HeatRaw(0.77F, 328);
		HeatRaw NickelRaw = new HeatRaw(0.65F, 1453);
		HeatRaw PigIronRaw = new HeatRaw(0.59F, 1500, 3150);
		HeatRaw PlatinumRaw = new HeatRaw(0.82F, 1770);
		HeatRaw RedSteelRaw = new HeatRaw(0.6F, 1770, 3589);
		HeatRaw RoseGoldRaw = new HeatRaw(0.69F, 960, 2650);
		HeatRaw SilverRaw = new HeatRaw(0.72F, 961);
		HeatRaw SteelRaw = new HeatRaw(0.63F, 1540, 3500);//sh = 0.63F; boil = 3500; melt = 1540;
		HeatRaw SterlingSilverRaw = new HeatRaw(0.72F, 893, 2212);//sh = 0.72F; boil = 2212; melt = 893;
		HeatRaw TinRaw = new HeatRaw(0.69F, 232);
		HeatRaw ZincRaw = new HeatRaw(0.66F, 420, 907);//sh = 0.66F; boil = 907; melt = 420;
		
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,0), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,1)).setMinMax(30, 60));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,1), GoldRaw,new ItemStack(mod_TFC_Game.GoldUnshaped,1)).setMinMax(30, 60));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,2), PlatinumRaw,new ItemStack(mod_TFC_Game.PlatinumUnshaped,1)).setMinMax(30, 60));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,3), IronRaw,new ItemStack(mod_TFC_Game.PigIronUnshaped,1)).setMinMax(15, 40));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,4), SilverRaw,new ItemStack(mod_TFC_Game.SilverUnshaped,1)).setMinMax(30, 60));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,5), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,1)).setMinMax(15, 40));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,6), LeadRaw,new ItemStack(mod_TFC_Game.LeadUnshaped,1)).setMinMax(15, 40).setMorph(new ItemStack(mod_TFC_Core.OreChunk,1,35)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,7), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,1)).setMinMax(15, 40));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,8), NickelRaw,new ItemStack(mod_TFC_Game.NickelUnshaped,1)).setMinMax(15, 40));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,9), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,1)).setMinMax(15, 40));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,10), IronRaw,new ItemStack(mod_TFC_Game.PigIronUnshaped,1)).setMinMax(25, 60));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,11), IronRaw,new ItemStack(mod_TFC_Game.PigIronUnshaped,1)).setMinMax(15, 40));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,12), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,1)).setMinMax(15, 40));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,13), SilverRaw,new ItemStack(mod_TFC_Game.SilverUnshaped,1)).setMinMax(5, 20).setMorph(new ItemStack(mod_TFC_Core.OreChunk,1,36)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,35), SilverRaw,new ItemStack(mod_TFC_Game.SilverUnshaped,1)).setMinMax(10, 25));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.OreChunk,1,36), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,1)).setMinMax(10, 40));
		
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WeakSteelUnshaped,1), SteelRaw,new ItemStack(mod_TFC_Game.WeakSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WeakRedSteelUnshaped,1), RedSteelRaw,new ItemStack(mod_TFC_Game.WeakRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WeakBlueSteelUnshaped,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.WeakBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.HCBlackSteelUnshaped,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.HCBlackSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.HCBlueSteelUnshaped,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.HCBlueSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.HCRedSteelUnshaped,1), RedSteelRaw,new ItemStack(mod_TFC_Game.HCRedSteelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.HCSteelUnshaped,1), SteelRaw,new ItemStack(mod_TFC_Game.HCSteelUnshaped,1)));
		
		//Bismuth
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BismuthIngot,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BismuthIngot2x,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnshaped,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthSheet,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthSheet2x,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,4)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
		manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthUnshaped,2)));
        //Bismuth Bronze
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BismuthBronzeIngot,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BismuthBronzeIngot2x,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeSheet,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots,1,0), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots,1,1), BismuthRaw,new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped,2)));
        //Black Bronze
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BlackBronzeIngot,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BlackBronzeIngot2x,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeSheet,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeSheet2x,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1,0), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate,1,0), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate,1,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves,1,0), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves,1,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots,1,0), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots,1,1), BlackBronzeRaw,new ItemStack(mod_TFC_Game.BlackBronzeUnshaped,2)));
        //Black Steel
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BlackSteelIngot,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BlackSteelIngot2x,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnshaped,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelSheet,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelSheet2x,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1,0), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate,1,0), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate,1,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves,1,0), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves,1,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots,1,0), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots,1,1), BlackSteelRaw,new ItemStack(mod_TFC_Game.BlackSteelUnshaped,2)));
        //Blue Steel
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BlueSteelIngot,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BlueSteelIngot2x,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnshaped,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelSheet,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelSheet2x,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1,0), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate,1,0), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate,1,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves,1,0), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves,1,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots,1,0), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots,1,1), BlueSteelRaw,new ItemStack(mod_TFC_Game.BlueSteelUnshaped,2)));
        //Brass
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BrassIngot,1), BrassRaw,new ItemStack(mod_TFC_Game.BrassUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BrassIngot2x,1), BrassRaw,new ItemStack(mod_TFC_Game.BrassUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BrassUnshaped,1), BrassRaw,new ItemStack(mod_TFC_Game.BrassUnshaped,1)));
        //Bronze
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BronzeIngot,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.BronzeIngot2x,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnshaped,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeSheet,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeSheet2x,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1,0), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate,1,0), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate,1,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves,1,0), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves,1,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots,1,0), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots,1,1), BronzeRaw,new ItemStack(mod_TFC_Game.BronzeUnshaped,2)));
        //Copper
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.CopperIngot,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.CopperIngot2x,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnshaped,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperSheet,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperSheet2x,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1,0), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate,1,0), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate,1,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves,1,0), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves,1,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedBoots,1,0), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.CopperUnfinishedBoots,1,1), CopperRaw,new ItemStack(mod_TFC_Game.CopperUnshaped,2)));
        //Gold
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.GoldIngot,1), GoldRaw,new ItemStack(mod_TFC_Game.GoldUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.GoldIngot2x,1), GoldRaw,new ItemStack(mod_TFC_Game.GoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.GoldUnshaped,1), GoldRaw,new ItemStack(mod_TFC_Game.GoldUnshaped,1)));
        //Wrought Iron
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.WroughtIronIngot,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.WroughtIronIngot2x,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnshaped,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronSheet,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronSheet2x,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1,0), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate,1,0), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate,1,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves,1,0), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves,1,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots,1,0), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots,1,1), IronRaw,new ItemStack(mod_TFC_Game.WroughtIronUnshaped,2)));
        //Lead
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.LeadIngot,1), LeadRaw,new ItemStack(mod_TFC_Game.LeadUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.LeadIngot2x,1), LeadRaw,new ItemStack(mod_TFC_Game.LeadUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.LeadUnshaped,1), LeadRaw,new ItemStack(mod_TFC_Game.LeadUnshaped,1)));
        //Nickel
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.NickelIngot,1), NickelRaw,new ItemStack(mod_TFC_Game.NickelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.NickelIngot2x,1), NickelRaw,new ItemStack(mod_TFC_Game.NickelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.NickelUnshaped,1), NickelRaw,new ItemStack(mod_TFC_Game.NickelUnshaped,1)));
        //Pig Iron
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.PigIronIngot,1), PigIronRaw,new ItemStack(mod_TFC_Game.PigIronUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.PigIronIngot2x,1), PigIronRaw,new ItemStack(mod_TFC_Game.PigIronUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.PigIronUnshaped,1), PigIronRaw,new ItemStack(mod_TFC_Game.PigIronUnshaped,1)));
        //Platinum
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.PlatinumIngot,1), PlatinumRaw,new ItemStack(mod_TFC_Game.PlatinumUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.PlatinumIngot2x,1), PlatinumRaw,new ItemStack(mod_TFC_Game.PlatinumUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.PlatinumUnshaped,1), PlatinumRaw,new ItemStack(mod_TFC_Game.PlatinumUnshaped,1)));
        //Red Steel
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.RedSteelIngot,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.RedSteelIngot2x,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnshaped,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelSheet,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelSheet2x,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1,0), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate,1,0), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate,1,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves,1,0), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves,1,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots,1,0), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots,1,1), RedSteelRaw,new ItemStack(mod_TFC_Game.RedSteelUnshaped,2)));
        //Rose Gold
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.RoseGoldIngot,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.RoseGoldIngot2x,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnshaped,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldSheet,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldSheet2x,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1,0), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate,1,0), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate,1,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves,1,0), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves,1,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots,1,0), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots,1,1), RoseGoldRaw,new ItemStack(mod_TFC_Game.RoseGoldUnshaped,2)));
        //Silver
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.SilverIngot,1), SilverRaw,new ItemStack(mod_TFC_Game.SilverUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.SilverIngot2x,1), SilverRaw,new ItemStack(mod_TFC_Game.SilverUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SilverUnshaped,1), SilverRaw,new ItemStack(mod_TFC_Game.SilverUnshaped,1)));
        //Steel
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.SteelIngot,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.SteelIngot2x,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnshaped,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelSheet,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelSheet2x,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1,0), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate,1,0), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate,1,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves,1,0), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves,1,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedBoots,1,0), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SteelUnfinishedBoots,1,1), SteelRaw,new ItemStack(mod_TFC_Game.SteelUnshaped,2)));
        //Sterling Silver
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.SterlingSilverIngot,1), SterlingSilverRaw,new ItemStack(mod_TFC_Game.SterlingSilverUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.SterlingSilverIngot2x,1), SterlingSilverRaw,new ItemStack(mod_TFC_Game.SterlingSilverUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.SterlingSilverUnshaped,1), SterlingSilverRaw,new ItemStack(mod_TFC_Game.SterlingSilverUnshaped,1)));
        //Tin
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.TinIngot,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.TinIngot2x,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnshaped,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinSheet,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinSheet2x,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1,0), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedChestplate,1,0), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedChestplate,1,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedGreaves,1,0), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedGreaves,1,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedBoots,1,0), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.TinUnfinishedBoots,1,1), TinRaw,new ItemStack(mod_TFC_Game.TinUnshaped,2)));
        //Zinc
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.ZincIngot,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Core.ZincIngot2x,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnshaped,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,1)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincSheet,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincSheet2x,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,0), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate,1,0), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate,1,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,4)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves,1,0), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves,1,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedBoots,1,0), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.ZincUnfinishedBoots,1,1), ZincRaw,new ItemStack(mod_TFC_Game.ZincUnshaped,2)));
        //Ceramics
        HeatRaw ClayRaw = new HeatRaw(1.40F, 515.5F);
        manager.addIndex(new HeatIndex(new ItemStack(mod_TFC_Game.terraClayMold,1,1), ClayRaw,new ItemStack(mod_TFC_Game.terraCeramicMold, 1)));
        manager.addIndex(new HeatIndex(new ItemStack(Block.sand, 1), 0.75F, 800, 1200.5F,new ItemStack(Block.glass, 1)));
        //Food
        manager.addIndex(new HeatIndex(new ItemStack(Item.porkRaw, 1), 0.85F, 130.5F, 385, new ItemStack(Item.porkCooked, 1)));
        manager.addIndex(new HeatIndex(new ItemStack(Item.beefRaw, 1), 0.85F, 135.5F, 425, new ItemStack(Item.beefCooked, 1)));
        manager.addIndex(new HeatIndex(new ItemStack(Item.chickenRaw, 1), 0.85F, 120.5F, 425,new ItemStack(Item.chickenCooked, 1)));
        manager.addIndex(new HeatIndex(new ItemStack(Item.fishRaw, 1), 0.85F, 120.5F, 425,new ItemStack(Item.fishCooked, 1)));
        //Other
        manager.addIndex(new HeatIndex(new ItemStack(Item.stick, 1), 13.0F,210,600F,new ItemStack(Block.torchWood, 2)));
        
	}
	public static Boolean canRemoveTag(Object tag, String key, Class c)
	{
		if(tag.getClass() == c)
		{
			if (((NBTBase)c.cast(tag)).getName() == key)
			{
				return true;
			}
		}
		return false;
	}

	public static String getHeatColor(float temp, float meltTemp)
	{
		String phrase = "";
		if(TFCSettings.BlacksmithModeHeatScale)
		{
			if(temp < 80)
			{
				phrase = "Warm";
				if(temp>(80 * 0.2)) phrase = phrase + "*";
				if(temp>(80 * 0.4)) phrase = phrase + "*";
				if(temp>(80 * 0.6)) phrase = phrase + "*";
				if(temp>(80 * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 80 && temp < 210)
			{
				phrase = "Hot";
				if(temp>80+((210-80) * 0.2)) phrase = phrase + "*";
                if(temp>80+((210-80) * 0.4)) phrase = phrase + "*";
                if(temp>80+((210-80) * 0.6)) phrase = phrase + "*";
                if(temp>80+((210-80) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 210 &&  temp < 480)
			{
				phrase = "Very Hot";
				if(temp>210+((480-210) * 0.2)) phrase = phrase + "*";
                if(temp>210+((480-210) * 0.4)) phrase = phrase + "*";
                if(temp>210+((480-210) * 0.6)) phrase = phrase + "*";
                if(temp>210+((480-210) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 480 &&  temp < 580)
			{
				phrase = "\2474Faint Red";
				if(temp>480+((580-480) * 0.2)) phrase = phrase + "*";
                if(temp>480+((580-480) * 0.4)) phrase = phrase + "*";
                if(temp>480+((580-480) * 0.6)) phrase = phrase + "*";
                if(temp>480+((580-480) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 580 &&  temp < 730)
			{
				phrase = "\2474Dark Red";
				if(temp>580+((730-580) * 0.2)) phrase = phrase + "*";
                if(temp>580+((730-580) * 0.4)) phrase = phrase + "*";
                if(temp>580+((730-580) * 0.6)) phrase = phrase + "*";
                if(temp>580+((730-580) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 730 &&  temp < 930)
			{
				phrase = "\247cBright Red";
				if(temp>730+((930-730) * 0.2)) phrase = phrase + "*";
                if(temp>730+((930-730) * 0.4)) phrase = phrase + "*";
                if(temp>730+((930-730) * 0.6)) phrase = phrase + "*";
                if(temp>730+((930-730) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 930 &&  temp < 1100)
			{
				phrase = "\2476Orange";
				if(temp>930+((1100-930) * 0.2)) phrase = phrase + "*";
                if(temp>930+((1100-930) * 0.4)) phrase = phrase + "*";
                if(temp>930+((1100-930) * 0.6)) phrase = phrase + "*";
                if(temp>930+((1100-930) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1100 &&  temp < 1300)
			{
				phrase = "\247eYellow";
				if(temp>1100+((1300-1100) * 0.2)) phrase = phrase + "*";
                if(temp>1100+((1300-1100) * 0.4)) phrase = phrase + "*";
                if(temp>1100+((1300-1100) * 0.6)) phrase = phrase + "*";
                if(temp>1100+((1300-1100) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1300 &&  temp < 1400)
			{
				phrase = "\247eYellow White";
				if(temp>1300+((1400-1300) * 0.2)) phrase = phrase + "*";
                if(temp>1300+((1400-1300) * 0.4)) phrase = phrase + "*";
                if(temp>1300+((1400-1300) * 0.6)) phrase = phrase + "*";
                if(temp>1300+((1400-1300) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1400 &&  temp < 1500)
			{
				phrase = "\247fWhite";
				if(temp>1400+((1500-1400) * 0.2)) phrase = phrase + "*";
                if(temp>1400+((1500-1400) * 0.4)) phrase = phrase + "*";
                if(temp>1400+((1500-1400) * 0.6)) phrase = phrase + "*";
                if(temp>1400+((1500-1400) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1500)
			{
				phrase = "\247fBrilliant White";
			}

			if(temp > meltTemp) {
				phrase = phrase + "\247f - Liquid";
			}
		}
		else
		{
			if(temp < meltTemp)
			{
				if(temp < meltTemp*0.1F)
				{
					return "Warm";
				}
				else if(temp >= meltTemp*0.1F && temp < meltTemp*0.3F)
				{
					return "\2474Hot";
				}
				else if(temp >= meltTemp*0.3F && temp < meltTemp*0.5F)
				{
					return "\247cRed";
				}
				else if(temp >= meltTemp*0.5F && temp < meltTemp*0.6F)
				{
					return "\2476Orange";
				}
				else if(temp >= meltTemp*0.6F && temp < meltTemp*0.7F)
				{
					return "\247eYellow";
				}
				else if(temp >= meltTemp*0.7F && temp < meltTemp*0.8F)
				{
					return "\247eYellow White";
				}
				else if(temp >= meltTemp*0.8F && temp < meltTemp*0.9F)
				{
					return "\247fWhite";
				}
				else if(temp >= meltTemp*0.9F && temp < meltTemp)
				{
					return "\247fBrilliant White";
				}
			}

			return "\247fBrilliant White: Liquid";
		}

		return phrase;
	}

	public static String getHeatColorFood(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp < meltTemp*0.1F)
			{
				return "Cold";
			}
			else if(temp >= meltTemp*0.1F && temp < meltTemp*0.4F)
			{
				return "\2474Warming";
			}
			else if(temp >= meltTemp*0.4F && temp < meltTemp*0.8F)
			{
				return "\2474Hot";
			}
			else
			{
				return "\2474Very Hot";
			}
		}

		return "Clear an output slot.";
	}

	public static String getHeatColorTorch(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp > 0 && temp < meltTemp*0.8F)
			{
				return "Catching Fire";
			}
			else if(temp >= meltTemp*0.8F)
			{
				return "\2474Lit";
			}
		}

		return "Clear an output slot.";
	}
	
	public static Boolean getIsBoiling(ItemStack is)
    {       
        HeatManager manager = HeatManager.getInstance();
        if(manager != null && is != null)
        {
            HeatIndex hi = manager.findMatchingIndex(is);
            if(hi != null && is.hasTagCompound())
            {
                float temp = 0;
                if(is.getTagCompound().hasKey("temperature"))
                    temp = is.getTagCompound().getFloat("temperature");
                return temp >= hi.boilTemp;
            }
            else return false;
        } 
        else 
        {
            return false;
        }
    }

	public static float getMeltingPoint(ItemStack is)
    {       
	    HeatManager manager = HeatManager.getInstance();
        if(manager!=null)
        {
            HeatIndex hi = manager.findMatchingIndex(is);
            if(hi != null)
            {
                return hi.meltTemp;
            }
            else return -1;
        } 
        else 
        {
            return -1;
        }
    }
	
	public static float getSpecificHeat(ItemStack is)
    {       
        HeatManager manager = HeatManager.getInstance();
        if(manager!=null)
        {
            HeatIndex hi = manager.findMatchingIndex(is);
            if(hi != null)
            {
                return hi.specificHeat;
            }
            else return 0.7F;
        } 
        else 
        {
            return 0.7F;
        }
    }

	public static float getTempDecrease(ItemStack is)
	{
		return 0.2F * getSpecificHeat(is);
	}

	public static float GetTemperature(ItemStack is)
	{
		if(is != null)
		{
			if(is.hasTagCompound() && is.getTagCompound().hasKey("temperature"))
			{
				return is.getTagCompound().getFloat("temperature");
			}
			else 
			{
				return 0F;
			}
		} else {
			return 0F;
		}

	}

	public static float getTempIncrease(ItemStack is, float itemTemp, float fireTemp)
	{
		return 0.35F * getSpecificHeat(is);
	}

	public static void HandleContainerHeat(World world, ItemStack[] inventory, int xCoord, int yCoord, int zCoord)
	{
		float ambient = world.getWorldChunkManager().getBiomeGenAt(xCoord, zCoord).getFloatTemperature();
		ambient = ambient / 2.0F;//Normalize the value to between 0 and 1
		ambient = 62 * ambient-20;

		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && inventory[i].hasTagCompound())
			{
				if(inventory[i].stackSize <= 0) {
					inventory[i].stackSize = 0;
				}

				NBTTagCompound comp = inventory[i].getTagCompound();
				if(comp.hasKey("temperature"))
				{
					float temp = comp.getFloat("temperature");
					if(temp > ambient)
					{
						temp -= TFCHeat.getTempDecrease(inventory[i]);
						comp.setFloat("temperature",temp);
					}
					inventory[i].setTagCompound(comp);
					if(temp <= ambient)
					{
						Collection C = comp.getTags();
						Iterator itr = C.iterator();
						while(itr.hasNext())
						{
							Object tag = itr.next();
							if(canRemoveTag(tag, "temperature", NBTTagFloat.class))
							{
								itr.remove();
								break;
							}
						}
					}
					if(comp.getTags().size() == 0)
					{
						inventory[i].stackTagCompound = null;
					}
				}
			}
		}
	}

	public static Boolean SetTemperature(ItemStack is, float Temp)
	{
		if(is != null)
		{
			if(is.hasTagCompound())
			{
				is.getTagCompound().setFloat("temperature", Temp);
			}
			else 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setFloat("temperature", Temp);
				is.setTagCompound(nbt);
			}
		} else {
			return false;
		}

		return true;
	}
}
