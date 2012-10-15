package TFC.Core;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.TFCBlocks;

public class Recipes 
{
	public static Item[] Axes;

	public static Item[] Chisels;

	public static Item[] Saws;

	public static Item[] Knives;

	public static Item[] MeltedMetal;

	public static Item[] Hammers;

	public static Item[] Gems;

	public static void registerRecipes()
	{
		Item[] Ingots = {TFCItems.BismuthIngot, TFCItems.BismuthBronzeIngot,TFCItems.BlackBronzeIngot,
				TFCItems.BlackSteelIngot,TFCItems.BlueSteelIngot,TFCItems.BrassIngot,TFCItems.BronzeIngot,
				TFCItems.BronzeIngot,TFCItems.CopperIngot,TFCItems.GoldIngot,TFCItems.WroughtIronIngot,TFCItems.LeadIngot
				,TFCItems.NickelIngot,TFCItems.PigIronIngot,TFCItems.PlatinumIngot,TFCItems.RedSteelIngot,
				TFCItems.RoseGoldIngot,TFCItems.SilverIngot,TFCItems.SteelIngot,TFCItems.SterlingSilverIngot
				,TFCItems.TinIngot,TFCItems.ZincIngot};


		//jimmynator's javelin
		ModLoader.addRecipe(new ItemStack(TFCItems.Javelin, 1), new Object[] { 
			"1","2", Character.valueOf('1'), new ItemStack(TFCItems.LooseRock, 1, -1),Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.arrow, 8), new Object[] { 
			new ItemStack(TFCItems.LooseRock, 1, -1), new ItemStack(Item.stick,1,-1),
			new ItemStack(Item.feather,1,-1)});

		//stone picks
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//stone shovels
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//stone axes
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//stone hoes
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//bone pick
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		//bone shovels
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		//bone axes
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		//bone hoes
		ModLoader.addRecipe(new ItemStack(TFCItems.IgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.SedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.IgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.MMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});

		ModLoader.addRecipe(new ItemStack(TFCItems.StoneHammer, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneHammerHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		ModLoader.addRecipe(new ItemStack(TFCItems.StoneKnife, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneKnifeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		//        ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
		//            "1","2", Character.valueOf('1'), TFCItems.StoneProPickHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		//        ModLoader.addRecipe(new ItemStack(TFCItems.FlintPaxel, 1, 0), new Object[] { 
		//            "1","2", Character.valueOf('1'), Item.flint,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		ModLoader.addRecipe(new ItemStack(TFCItems.WoodenBucketEmpty, 1), new Object[] { "w w","w w"," w ", Character.valueOf('w'), new ItemStack(TFCItems.SinglePlank, 1, -1) });

		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < Axes.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 3, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(Axes[j], 1, -1)});
			}
			for(int j = 0; j < Saws.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 8, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(Saws[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(TFCItems.WoodSupportItemV, 8, i), new Object[] { "A2"," 2", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Saws[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(TFCItems.WoodSupportItemH, 8, i), new Object[] { "A ","22", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Saws[j], 1, -1)});
			}

			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 4, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(TFCItems.FlintPaxel, 1, -1)});
			ModLoader.addRecipe(new ItemStack(Block.planks.blockID, 1, i), new Object[] {"11","11", Character.valueOf('1'), new ItemStack(TFCItems.SinglePlank, 1, i)});
		}

		for(int j = 0; j < Knives.length; j++)
		{
			ModLoader.addRecipe(new ItemStack(Item.bowlEmpty, 4, 0), new Object[] { 
				"2","1", Character.valueOf('1'),new ItemStack(TFCItems.Logs,1,-1), Character.valueOf('2'),new ItemStack(Knives[j], 1, -1)});

			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WheatGrain, 4), new Object[] {new ItemStack(TFCItems.WheatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.RyeGrain, 4), new Object[] {new ItemStack(TFCItems.RyeWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.BarleyGrain, 4), new Object[] {new ItemStack(TFCItems.BarleyWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.OatGrain, 4), new Object[] {new ItemStack(TFCItems.OatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.RiceGrain, 4), new Object[] {new ItemStack(TFCItems.RiceWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildWheatGrain, 4), new Object[] {new ItemStack(TFCItems.WildWheatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildRyeGrain, 4), new Object[] {new ItemStack(TFCItems.WildRyeWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildBarleyGrain, 4), new Object[] {new ItemStack(TFCItems.WildBarleyWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildOatGrain, 4), new Object[] {new ItemStack(TFCItems.WildOatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildRiceGrain, 4), new Object[] {new ItemStack(TFCItems.WildRiceWhole, 1),new ItemStack(Knives[j], 1, -1)});
		}

		//Chest
		ModLoader.addRecipe(new ItemStack(Block.chest, 1), new Object[] { "###","# #","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, -1)});

		//Red Stone		
		ModLoader.addRecipe(new ItemStack(Item.redstone, 8), new Object[] { 
			"1", Character.valueOf('1'),new ItemStack(TFCItems.OreChunk, 1, 28)});
		//Lapis Lazuli	
		ModLoader.addShapelessRecipe(new ItemStack(Item.dyePowder, 4,4), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 34)});

		//knapping
		for(int i = 0; i < 23; i++)
		{
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.LooseRock, 1, i), new Object[] {new ItemStack(TFCItems.LooseRock, 1, i), new ItemStack(TFCItems.LooseRock, 1, i)});
		}

		for(int i = 0; i < 13; i++)
		{			
			for(int j = 0; j < 3; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.StoneIgInBrick,1,j), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 3; j < 13; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.StoneSedBrick,1,j-3), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 13; j < 17; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.StoneIgExBrick,1,j-13), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 17; j < 23; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.StoneMMBrick,1,j-17), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}
		}

		//Gold Pan
		ModLoader.addRecipe(new ItemStack(TFCItems.terraGoldPan, 1, 0), new Object[] { 
			"1", Character.valueOf('1'),Item.bowlEmpty});
		//Sluice
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSluiceItem, 1), new Object[] { 
			"  1"," 12","122", Character.valueOf('1'),new ItemStack(Item.stick,1,-1), Character.valueOf('2'),new ItemStack(TFCItems.SinglePlank,1,-1)});

		for(int j = 0; j < Recipes.Hammers.length; j++)
		{
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 8), new ItemStack(Recipes.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 12), new ItemStack(Recipes.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 22), new ItemStack(Recipes.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 10), new ItemStack(Recipes.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 6), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(Recipes.Hammers[j], 1, -1)});
		}

		//Tool Rack
		for(int j = 0; j < 16; j++)
		{
			ModLoader.addRecipe(new ItemStack(TFCBlocks.ToolRack, 1, j), new Object[] { "###","   ","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,j)});
		}

		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 4), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(TFCItems.StoneHammer, 1, -1)});

		ModLoader.addRecipe(new ItemStack(Item.redstone, 8, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(TFCItems.OreChunk,1,27)});
		ModLoader.addRecipe(new ItemStack(TFCItems.Ink, 16, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(Item.dyePowder,1,0)});
		ModLoader.addRecipe(new ItemStack(TFCItems.FireStarter, 1, 0), new Object[] { "2 "," 2", Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.BellowsItem, 1, 0), new Object[] { "###","???","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1), Character.valueOf('?'), Item.leather});
		//ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgIn});
		//ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgEx});
		ModLoader.addRecipe(new ItemStack(TFCItems.CopperAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.CopperIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.BronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BronzeIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.WroughtIronAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.WroughtIronIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.SteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.SteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.BlackSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.BlueSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlueSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.RedSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RedSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.RoseGoldAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RoseGoldIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.BismuthBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BismuthBronzeIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.BlackBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackBronzeIngot2x});

		ModLoader.addRecipe(new ItemStack(TFCBlocks.Scribe, 1), new Object[] { " L ","#P#","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1),
			Character.valueOf('P'), Item.paper,Character.valueOf('L'), Item.feather});
		ModLoader.addRecipe(new ItemStack(TFCItems.ClayMold, 4), new Object[] { "# #","###", Character.valueOf('#'), new ItemStack(Item.clay,1,-1)});

		ModLoader.addRecipe(new ItemStack(TFCBlocks.MetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.StoneIgEx});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.MetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.StoneIgIn});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.MetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.StoneSed});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.MetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.StoneMM});

		ModLoader.addRecipe(new ItemStack(TFCBlocks.Bloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.StoneIgExBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.Bloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.StoneIgInBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.Bloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.StoneSedBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.Bloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.StoneMMBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});

		ModLoader.addRecipe(new ItemStack(Block.rail, 64), new Object[] { "PsP","PsP", Character.valueOf('P'), TFCItems.WroughtIronIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(Block.railPowered, 64), new Object[] { " r ","PsP","PsP", Character.valueOf('P'), TFCItems.GoldIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,-1), Character.valueOf('r'), Item.redstone});
		ModLoader.addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[] { "P P","PPP", Character.valueOf('P'), TFCItems.WroughtIronSheet});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.minecartCrate, 1), new Object[] { new ItemStack(Block.chest), new ItemStack(Item.minecartEmpty)});
		ModLoader.addRecipe(new ItemStack(Item.shears, 1), new Object[] { "P "," P", Character.valueOf('P'), TFCItems.WroughtIronIngot});
		ModLoader.addRecipe(new ItemStack(Block.lever, 1), new Object[] { "P","H", Character.valueOf('P'), new ItemStack(Item.stick,1,-1), Character.valueOf('H'), new ItemStack(TFCItems.LooseRock,1,-1)});
		ModLoader.addRecipe(new ItemStack(Item.doorWood, 1, 0), new Object[] { "##","##","##", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1)});
		ModLoader.addRecipe(new ItemStack(Block.trapdoor, 1, 0), new Object[] { "###","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1)});
		ModLoader.addRecipe(new ItemStack(Item.sign, 1, 0), new Object[] { "###","###"," 1 ", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1),Character.valueOf('1'), new ItemStack(Item.stick,1,-1)});

		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildWheat, 1), new Object[] {new ItemStack(TFCItems.WildWheatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildRye, 1), new Object[] {new ItemStack(TFCItems.WildRyeGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildBarley, 1), new Object[] {new ItemStack(TFCItems.WildBarleyGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildOat, 1), new Object[] {new ItemStack(TFCItems.WildOatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildRice, 1), new Object[] {new ItemStack(TFCItems.WildRiceGrain, 1)});

		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWheat, 1), new Object[] {new ItemStack(TFCItems.WheatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsRye, 1), new Object[] {new ItemStack(TFCItems.RyeGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsBarley, 1), new Object[] {new ItemStack(TFCItems.BarleyGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsOat, 1), new Object[] {new ItemStack(TFCItems.OatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsRice, 1), new Object[] {new ItemStack(TFCItems.RiceGrain, 1)});

		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.WheatGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.RyeGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.BarleyGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.OatGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.RiceGrain});

		for(int j = 0; j < Gems.length; j++)
		{
			for(int k = 0; k < 3; k++)
			{
				ModLoader.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneIgExSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
				ModLoader.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneIgInSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
				ModLoader.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneSedSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
				ModLoader.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneMMSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
			}
		}
		VanillaRecipes();
	}

	private static void VanillaRecipes()
	{
		if(TFC_Settings.enableVanillaDiamondRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.diamond, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GemDiamond,1,2)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 2), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GemDiamond,1,3)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 3), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GemDiamond,1,4)});
		}
		if(TFC_Settings.enableVanillaIronRecipe== true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotIron, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.WroughtIronIngot,1)});

		}
		if(TFC_Settings.enableVanillaGoldRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotGold, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GoldIngot,1)});
		}
		if(TFC_Settings.enableVanillaRecipes == true)
		{
			//Terrastone to Cobblestone
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneSedCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneIgInCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneIgExCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneMMCobble});

			//Conversion to vanilla tools for recipes in other mods
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgInPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgExPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.SedPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.MMPick});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgInShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgExShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.SedShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.MMShovel});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgInHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgExHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.SedHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.MMHoe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgInAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgExAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.SedAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.MMAxe});
		}
		else
		{
			RemoveRecipe(new ItemStack(Item.pickaxeWood,1));
			RemoveRecipe(new ItemStack(Item.axeWood,1));
			RemoveRecipe(new ItemStack(Item.shovelWood,1));
			RemoveRecipe(new ItemStack(Item.hoeWood,1));
			RemoveRecipe(new ItemStack(Item.swordWood,1));
			RemoveRecipe(new ItemStack(Block.stoneOvenIdle,1));
			RemoveRecipe(new ItemStack(Block.torchWood,4));
			RemoveRecipe(new ItemStack(Item.stick,4));
			RemoveRecipe(new ItemStack(Block.planks,4));
		}
	}

	public static void RemoveRecipe(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes)tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();

				if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
					recipes.remove(i--);
				}
			}
		}
	}
}
