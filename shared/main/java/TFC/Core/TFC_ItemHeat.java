package TFC.Core;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.HeatIndex;
import TFC.API.HeatRaw;
import TFC.API.HeatRegistry;
import TFC.API.Metal;
import TFC.API.TFCOptions;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class TFC_ItemHeat 
{

	public static void SetupItemHeat()
	{
		HeatRegistry manager = HeatRegistry.getInstance();

		HeatRaw BismuthRaw = new HeatRaw(0.70F, 271);
		HeatRaw BismuthBronzeRaw = new HeatRaw(0.65F, 985);
		HeatRaw BlackBronzeRaw = new HeatRaw(0.7F, 1070);
		HeatRaw BlackSteelRaw = new HeatRaw(0.66F, 1485);
		HeatRaw BlueSteelRaw = new HeatRaw(0.63F, 1540);
		HeatRaw BrassRaw = new HeatRaw(0.68F, 930);
		HeatRaw BronzeRaw = new HeatRaw(0.68F, 950);
		HeatRaw CopperRaw = new HeatRaw(0.70F, 1084);
		HeatRaw GoldRaw = new HeatRaw(0.75F, 1063);
		HeatRaw IronRaw = new HeatRaw(0.67F, 1536);
		HeatRaw LeadRaw = new HeatRaw(0.75F, 328);
		HeatRaw NickelRaw = new HeatRaw(0.68F, 1453);
		HeatRaw PigIronRaw = new HeatRaw(0.64F, 1500);
		HeatRaw PlatinumRaw = new HeatRaw(0.82F, 1730);
		HeatRaw RedSteelRaw = new HeatRaw(0.63F, 1540);
		HeatRaw RoseGoldRaw = new HeatRaw(0.69F, 960);
		HeatRaw SilverRaw = new HeatRaw(0.72F, 961);
		HeatRaw SteelRaw = new HeatRaw(0.66F, 1540);//sh = 0.63F; boil = 3500; melt = 1540;
		HeatRaw SterlingSilverRaw = new HeatRaw(0.72F, 893);//sh = 0.72F; boil = 2212; melt = 893;
		HeatRaw TinRaw = new HeatRaw(0.69F, 232);
		HeatRaw ZincRaw = new HeatRaw(0.66F, 420);//sh = 0.66F; boil = 907; melt = 420;

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)).setMinMax(25));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,2), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,3), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,4), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,5), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,6), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,7), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,8), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,9), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,10), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)).setMinMax(25, 60));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,11), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,12), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,13), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)).setMinMax(10, 20));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,35), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)).setMinMax(15, 30));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,36), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)).setMinMax(20, 40));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OreChunk,1,37), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)).setMinMax(2, 6));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,0), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,1), GoldRaw,new ItemStack(TFCItems.GoldUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,2), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)).setMinMax(1, 4));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,3), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,4), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,5), TinRaw,new ItemStack(TFCItems.TinUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,6), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,7), BismuthRaw,new ItemStack(TFCItems.BismuthUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,8), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,9), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,10), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,11), IronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,12), ZincRaw,new ItemStack(TFCItems.ZincUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,13), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)).setMinMax(3, 6));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,35), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)).setMinMax(4, 8));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,36), CopperRaw,new ItemStack(TFCItems.CopperUnshaped,1)).setMinMax(5, 10));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SmallOreChunk,1,37), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)).setMinMax(1, 3));

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
		//Brass
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassIngot,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassIngot2x,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BrassUnshaped,1), BrassRaw,new ItemStack(TFCItems.BrassUnshaped,1)));
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
		//Wrought Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WroughtIronIngot,1), IronRaw,new ItemStack(TFCItems.WroughtIronUnshaped,1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.Bloom, 1, 32767), IronRaw, new ItemStack(TFCItems.WroughtIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RawBloom, 1, 32767), IronRaw, null/*new ItemStack(TFCItems.WroughtIronUnshaped,1)*/));

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
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Anvil, 1, 3), IronRaw, null));
		//Lead
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadIngot,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadIngot2x,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.LeadUnshaped,1), LeadRaw,new ItemStack(TFCItems.LeadUnshaped,1)));
		//Nickel
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelIngot,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelIngot2x,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.NickelUnshaped,1), NickelRaw,new ItemStack(TFCItems.NickelUnshaped,1)));
		//Pig Iron
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronIngot,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronIngot2x,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PigIronUnshaped,1), PigIronRaw,new ItemStack(TFCItems.PigIronUnshaped,1)));
		//Platinum
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumIngot,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumIngot2x,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.PlatinumUnshaped,1), PlatinumRaw,new ItemStack(TFCItems.PlatinumUnshaped,1)));
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
		//Silver
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverIngot,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverIngot2x,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.SilverUnshaped,1), SilverRaw,new ItemStack(TFCItems.SilverUnshaped,1)));
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
		HeatRaw ClayRaw = new HeatRaw(1.40F, 515.5F);
		//manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CeramicMold,1,0), ClayRaw,new ItemStack(TFCItems.CeramicMold, 1)));
		//manager.addIndex(new HeatIndex(new ItemStack(TFCItems.ClaySpindle,1,1), ClayRaw,new ItemStack(TFCItems.SpindleHead, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Sand, 1, 32767), 0.95F, 800F, new ItemStack(Block.glass, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCBlocks.Sand2, 1, 32767), 0.95F, 800F, new ItemStack(Block.glass, 1)));
		//Food
		manager.addIndex(new HeatIndex(new ItemStack(Item.porkRaw, 1), 0.85F, 130.5F, new ItemStack(Item.porkCooked, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(Item.beefRaw, 1), 0.85F, 135.5F, new ItemStack(Item.beefCooked, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.muttonRaw,1),0.85F,135.5F, new ItemStack(TFCItems.muttonCooked,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CalamariRaw,1),0.85F,135.5F, new ItemStack(TFCItems.CalamariCooked,1)));
		manager.addIndex(new HeatIndex(new ItemStack(Item.chickenRaw, 1), 0.85F, 120.5F, new ItemStack(Item.chickenCooked, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(Item.fishRaw, 1), 0.85F, 120.5F, new ItemStack(Item.fishCooked, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(Item.egg, 1), 0.90F, 110.5F, new ItemStack(TFCItems.EggCooked, 1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.WheatDough, 1), 0.90F, 130.5F, new ItemStack(TFCItems.WheatBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.BarleyDough, 1), 0.90F, 130.5F, new ItemStack(TFCItems.BarleyBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RyeDough, 1), 0.90F, 130.5F, new ItemStack(TFCItems.RyeBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.OatDough, 1), 0.90F, 130.5F, new ItemStack(TFCItems.OatBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.RiceDough, 1), 0.90F, 130.5F, new ItemStack(TFCItems.RiceBread, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.CornmealDough, 1), 0.90F, 130.5F, new ItemStack(TFCItems.CornBread, 1)));

		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealGeneric, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealDamageBoost, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealDamageResist, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealDigSpeed, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealFireResist, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealJump, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealMoveSpeed, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealNightVision, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		manager.addIndex(new HeatIndex(new ItemStack(TFCItems.MealWaterBreathing, 1), 0.85F, 135.5F, new ItemStack(Item.bowlEmpty, 1)));
		//Other
		manager.addIndex(new HeatIndex(new ItemStack(Item.stick, 1, 32767), 13.0F, 210F, new ItemStack(Block.torchWood, 2)));

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
		if(temp < 80)
		{
			phrase = StringUtil.localize("gui.ItemHeat.Warming");
			if(temp>(80 * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>(80 * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>(80 * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>(80 * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 80 && temp < 210)
		{
			phrase = StringUtil.localize("gui.ItemHeat.Hot");
			if(temp>80+((210-80) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>80+((210-80) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>80+((210-80) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>80+((210-80) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 210 &&  temp < 480)
		{
			phrase = StringUtil.localize("gui.ItemHeat.VeryHot");
			if(temp>210+((480-210) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>210+((480-210) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>210+((480-210) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>210+((480-210) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 480 &&  temp < 580)
		{
			phrase = "\2474" + StringUtil.localize("gui.ItemHeat.FaintRed");
			if(temp>480+((580-480) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>480+((580-480) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>480+((580-480) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>480+((580-480) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 580 &&  temp < 730)
		{
			phrase = "\2474" + StringUtil.localize("gui.ItemHeat.DarkRed");
			if(temp>580+((730-580) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>580+((730-580) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>580+((730-580) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>580+((730-580) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 730 &&  temp < 930)
		{
			phrase = "\247c" + StringUtil.localize("gui.ItemHeat.BrightRed");
			if(temp>730+((930-730) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>730+((930-730) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>730+((930-730) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>730+((930-730) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 930 &&  temp < 1100)
		{
			phrase = "\2476" + StringUtil.localize("gui.ItemHeat.Orange");
			if(temp>930+((1100-930) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>930+((1100-930) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>930+((1100-930) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>930+((1100-930) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 1100 &&  temp < 1300)
		{
			phrase = "\247e" + StringUtil.localize("gui.ItemHeat.Yellow");
			if(temp>1100+((1300-1100) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1100+((1300-1100) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1100+((1300-1100) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1100+((1300-1100) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 1300 &&  temp < 1400)
		{
			phrase = "\247e" + StringUtil.localize("gui.ItemHeat.YellowWhite");
			if(temp>1300+((1400-1300) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1300+((1400-1300) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1300+((1400-1300) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1300+((1400-1300) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 1400 &&  temp < 1500)
		{
			phrase = "\247f" + StringUtil.localize("gui.ItemHeat.White");
			if(temp>1400+((1500-1400) * 0.2)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1400+((1500-1400) * 0.4)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1400+((1500-1400) * 0.6)) {
				phrase = phrase + "\u2605";
			}
			if(temp>1400+((1500-1400) * 0.8)) {
				phrase = phrase + "\u2605";
			}
		}
		else if(temp >= 1500)
		{
			phrase = "\247f" + StringUtil.localize("gui.ItemHeat.BrilliantWhite");
		}

		if(temp > meltTemp)
		{
			phrase = phrase + "\247f - " + StringUtil.localize("gui.ItemHeat.Liquid");
		}

		return phrase;
	}

	public static String getHeatColorFood(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp < meltTemp*0.1F)
			{
				return StringUtil.localize("gui.FoodHeat.Cold");
			}
			else if(temp >= meltTemp*0.1F && temp < meltTemp*0.4F)
			{
				return "\2474" + StringUtil.localize("gui.FoodHeat.Warm");
			}
			else if(temp >= meltTemp*0.4F && temp < meltTemp*0.8F)
			{
				return "\2474" + StringUtil.localize("gui.ItemHeat.Hot");
			}
			else
			{
				return "\2474" + StringUtil.localize("gui.ItemHeat.VeryHot");
			}
		}

		return StringUtil.localize("gui.ClearSlot");
	}

	public static String getHeatColorTorch(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp > 0 && temp < meltTemp*0.8F)
			{
				return StringUtil.localize("gui.Torch.CatchingFire");
			}
			else if(temp >= meltTemp*0.8F)
			{
				return "\2474" + StringUtil.localize("gui.Torch.Lit");
			}
		}

		return StringUtil.localize("gui.ClearSlot");
	}

	public static Boolean getIsLiquid(ItemStack is)
	{       
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager != null && is != null)
		{
			HeatIndex hi = manager.findMatchingIndex(is);
			if(hi != null && is.hasTagCompound())
			{
				float temp = 0;
				if(is.getTagCompound().hasKey("temperature")) {
					temp = is.getTagCompound().getFloat("temperature");
				}
				return temp >= hi.meltTemp;
			} else {
				return false;
			}
		} 
		else 
		{
			return false;
		}
	}

	public static float getMeltingPoint(ItemStack is)
	{       
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(is);
			if(hi != null)
			{
				return hi.meltTemp;
			} else {
				return -1;
			}
		} 
		else 
		{
			return -1;
		}
	}

	public static float getMeltingPoint(Metal m)
	{       
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(new ItemStack(Item.itemsList[m.MeltedItemID]));
			if(hi != null)
			{
				return hi.meltTemp;
			} else {
				return -1;
			}
		} 
		else 
		{
			return -1;
		}
	}

	public static float getSpecificHeat(ItemStack is)
	{       
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager!=null)
		{
			HeatIndex hi = manager.findMatchingIndex(is);
			if(hi != null)
			{
				return hi.specificHeat;
			} else {
				return 0.7F;
			}
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

	public static float getTempIncrease(ItemStack is, float fireTemp, float fireMaxTemp)
	{
		byte debugBump = 0;
		if(TFCOptions.enableDebugMode) {
			debugBump = 5;
		}
		return ((fireTemp / fireMaxTemp)) * getSpecificHeat(is) + debugBump;
	}

	public static void HandleItemHeat(ItemStack is, int xCoord, int yCoord, int zCoord)
	{
		if(is.hasTagCompound())
		{
			NBTTagCompound comp = is.getTagCompound();
			if(comp.hasKey("temperature"))
			{
				float temp = comp.getFloat("temperature");
				if(temp > 0)
				{
					temp -= TFC_ItemHeat.getTempDecrease(is);
					comp.setFloat("temperature",temp);
				}
				if(temp <= 0)
				{
					comp.removeTag("temperature");
				}
				if(comp.getTags().size() == 0)
				{
					is.stackTagCompound = null;
				}
			}
		}
	}

	public static void HandleContainerHeat(World world, ItemStack[] inventory, int xCoord, int yCoord, int zCoord)
	{
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && inventory[i].stackSize <= 0) 
			{
				inventory[i].stackSize = 1;
			}

			if(inventory[i] != null && inventory[i].hasTagCompound() && !(inventory[i].getItem() instanceof ItemTerra))
			{
				NBTTagCompound comp = inventory[i].getTagCompound();
				if(comp.hasKey("temperature"))
				{
					float temp = comp.getFloat("temperature");
					if(temp > 0)
					{
						temp -= TFC_ItemHeat.getTempDecrease(inventory[i]);
						comp.setFloat("temperature",temp);
					}
					//inventory[i].setTagCompound(comp);
					if(temp <= 0)
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

	public static void HandleContainerHeatChest(World world, ItemStack[] inventory, int xCoord, int yCoord, int zCoord)
	{
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && inventory[i].stackSize <= 0) 
			{
				inventory[i].stackSize = 1;
			}

			if(inventory[i] != null && inventory[i].hasTagCompound())
			{
				NBTTagCompound comp = inventory[i].getTagCompound();
				if(comp.hasKey("temperature"))
				{
					float temp = comp.getFloat("temperature");
					if(temp > 0)
					{
						temp -= TFC_ItemHeat.getTempDecrease(inventory[i])*3;
						comp.setFloat("temperature",temp);
					}
					if(temp <= 0)
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
