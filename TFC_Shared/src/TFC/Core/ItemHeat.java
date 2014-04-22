package TFC.Core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.HeatIndex;
import TFC.API.HeatRaw;
import TFC.API.HeatRegistry;

public class ItemHeat
{

	public static void SetupItemHeat()
	{
		HeatRegistry manager = HeatRegistry.getInstance();

		/*HeatRaw now uses ticks to determine cook length since it is soo much more manageable than that confusing melt temp/specific 
		 * heat that was a vestige of very very olden times when real temps were more important
		 * 
		 * Reference Times:
		 * 20 ticks = 1 Second
		 * 600 ticks = 30 seconds
		 * 1200 ticks = 60 seconds
		 * 1800 ticks = 90 seconds
		 */

		HeatRaw BismuthRaw = new HeatRaw(600);
		HeatRaw BismuthBronzeRaw = new HeatRaw(900);
		HeatRaw BlackBronzeRaw = new HeatRaw(900);
		HeatRaw BlackSteelRaw = new HeatRaw(1500);
		HeatRaw BlueSteelRaw = new HeatRaw(1800);
		HeatRaw BrassRaw = new HeatRaw(900);
		HeatRaw BronzeRaw = new HeatRaw(900);
		HeatRaw CopperRaw = new HeatRaw(900);
		HeatRaw GoldRaw = new HeatRaw(600);
		HeatRaw IronRaw = new HeatRaw(1500);
		HeatRaw LeadRaw = new HeatRaw(400);
		HeatRaw NickelRaw = new HeatRaw(1500);
		HeatRaw PigIronRaw = new HeatRaw(1500);
		HeatRaw PlatinumRaw = new HeatRaw(1500);
		HeatRaw RedSteelRaw = new HeatRaw(1800);
		HeatRaw RoseGoldRaw = new HeatRaw(900);
		HeatRaw SilverRaw = new HeatRaw(600);
		HeatRaw SteelRaw = new HeatRaw(1500);//sh = 0.63F; boil = 3500; melt = 1540;
		HeatRaw SterlingSilverRaw = new HeatRaw(900);//sh = 0.72F; boil = 2212; melt = 893;
		HeatRaw TinRaw = new HeatRaw(600);
		HeatRaw ZincRaw = new HeatRaw(600);//sh = 0.66F; boil = 907; melt = 420;

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,0), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,1), GoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,2), PlatinumRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,3), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,4), SilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,5), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,6), LeadRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,7), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,8), NickelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,9), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,10), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,11), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,12), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,13), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,35), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,36), GoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,37), PlatinumRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,38), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,39), SilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,40), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,41), LeadRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,42), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,43), NickelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,44), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,45), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,46), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,47), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,48), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,49), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,50), GoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,51), PlatinumRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,52), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,53), SilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,54), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,55), LeadRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,56), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,57), NickelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,58), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,59), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,60), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,61), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,62), CopperRaw));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,0), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,1), GoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,2), PlatinumRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,3), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,4), SilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,5), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,6), LeadRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,7), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,8), NickelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,9), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,10), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,11), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,12), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,13), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,35), SilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,36), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,37), PlatinumRaw));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakSteelUnshaped,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakRedSteelUnshaped,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakBlueSteelUnshaped,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlackSteelUnshaped,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlueSteelUnshaped,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCRedSteelUnshaped,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCSteelUnshaped,1), SteelRaw));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakSteelIngot,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakRedSteelIngot,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WeakBlueSteelIngot,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlackSteelIngot,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCBlueSteelIngot,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCRedSteelIngot,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.HCSteelIngot,1), SteelRaw));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.UnknownIngot,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.UnknownUnshaped,1), CopperRaw));

		//Bismuth
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthIngot,1), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthIngot2x,1), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthUnshaped,1), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthSheet,1), BismuthRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthSheet2x,1), BismuthRaw));
		//Bismuth Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeIngot,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeIngot2x,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnshaped,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeSheet,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeSheet2x,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,0), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,0), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,0), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,0), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,1), BismuthBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil2, 1, 1), BismuthBronzeRaw, null));
		//Black Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeIngot,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeIngot2x,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnshaped,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeSheet,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeSheet2x,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,0), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,0), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,0), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,0), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,1), BlackBronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil2, 1, 2), BlackBronzeRaw, null));
		//Black Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelIngot,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelIngot2x,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnshaped,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelSheet,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelSheet2x,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,0), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,0), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,0), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,0), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,1), BlackSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 5), BlackSteelRaw, null));
		//Blue Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelIngot,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelIngot2x,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnshaped,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelSheet,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelSheet2x,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,0), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,0), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,0), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,0), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,1), BlueSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 6), BlueSteelRaw, null));
		//Brass
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassIngot,1), BrassRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassIngot2x,1), BrassRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassUnshaped,1), BrassRaw));
		//Bronze
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeIngot,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeIngot2x,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnshaped,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeSheet,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeSheet2x,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,0), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,0), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,0), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,0), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,1), BronzeRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 2), BronzeRaw, null));
		//Copper
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperIngot,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperIngot2x,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnshaped,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperSheet,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperSheet2x,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,0), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,0), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,0), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedBoots,1,0), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CopperUnfinishedBoots,1,1), CopperRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 1), CopperRaw, null));
		//Gold
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldIngot,1), GoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldIngot2x,1), GoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.GoldUnshaped,1), GoldRaw));
		//Wrought Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronIngot,1), IronRaw));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Bloom, 1, 32767), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RawBloom, 1, 32767), IronRaw));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronIngot2x,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnshaped,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronSheet,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronSheet2x,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,0), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,0), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,0), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,0), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,1), IronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 3), IronRaw, null));
		//Lead
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadIngot,1), LeadRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadIngot2x,1), LeadRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadUnshaped,1), LeadRaw));
		//Nickel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelIngot,1), NickelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelIngot2x,1), NickelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelUnshaped,1), NickelRaw));
		//Pig Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronIngot,1), PigIronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronIngot2x,1), PigIronRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronUnshaped,1), PigIronRaw));
		//Platinum
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumIngot,1), PlatinumRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumIngot2x,1), PlatinumRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumUnshaped,1), PlatinumRaw));
		//Red Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelIngot,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelIngot2x,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnshaped,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelSheet,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelSheet2x,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,0), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,0), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,0), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,0), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,1), RedSteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 7), RedSteelRaw, null));
		//Rose Gold
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldIngot,1), RoseGoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldIngot2x,1), RoseGoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldUnshaped,1), RoseGoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldSheet,1), RoseGoldRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RoseGoldSheet2x,1), RoseGoldRaw));
		//Silver
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverIngot,1), SilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverIngot2x,1), SilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverUnshaped,1), SilverRaw));
		//Steel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelIngot,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelIngot2x,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnshaped,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelSheet,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelSheet2x,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,0), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,0), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,0), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedBoots,1,0), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SteelUnfinishedBoots,1,1), SteelRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 4), SteelRaw, null));
		//Sterling Silver
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverIngot,1), SterlingSilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverIngot2x,1), SterlingSilverRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SterlingSilverUnshaped,1), SterlingSilverRaw));
		//Tin
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinIngot,1), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinIngot2x,1), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinUnshaped,1), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinSheet,1), TinRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.TinSheet2x,1), TinRaw));
		//Zinc
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincIngot,1), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincIngot2x,1), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincUnshaped,1), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincSheet,1), ZincRaw));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ZincSheet2x,1), ZincRaw));
		//Ceramics
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Sand, 1, 32767), 600, new ItemStack(Block.glass, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Sand2, 1, 32767), 600, new ItemStack(Block.glass, 1)));
		//Food
		manager.addIndex(new HeatIndex(new ItemStack(Item.porkRaw, 1), 200, new ItemStack(Item.porkCooked, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(Item.beefRaw, 1), 200, new ItemStack(Item.beefCooked, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.muttonRaw,1),200, new ItemStack(TFCItems.muttonCooked,1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.venisonRaw,1),200, new ItemStack(TFCItems.venisonCooked,1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CalamariRaw,1),200, new ItemStack(TFCItems.CalamariCooked,1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(Item.chickenRaw, 1), 200, new ItemStack(Item.chickenCooked, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(Item.fishRaw, 1), 200, new ItemStack(Item.fishCooked, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(Item.egg, 1), 140, new ItemStack(TFCItems.EggCooked, 1)).setKeepNBT(true));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.horseMeatRaw, 1), 200, new ItemStack(TFCItems.horseMeatCooked, 1)).setKeepNBT(true));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WheatDough, 1), 200, new ItemStack(TFCItems.WheatBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BarleyDough, 1), 200, new ItemStack(TFCItems.BarleyBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RyeDough, 1), 200, new ItemStack(TFCItems.RyeBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OatDough, 1), 200, new ItemStack(TFCItems.OatBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RiceDough, 1), 200, new ItemStack(TFCItems.RiceBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CornmealDough, 1), 200, new ItemStack(TFCItems.CornBread, 1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealGeneric, 1, 32767), 200, new ItemStack(Item.bowlEmpty, 1)));

		//Other
		manager.addIndex(new HeatIndex(new ItemStack(Item.stick, 1, 32767), 80, new ItemStack(Block.torchWood, 2)));

	}
}
