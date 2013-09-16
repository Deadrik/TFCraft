package TFC.Core;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.API.Crafting.AnvilCraftingManagerTFC;
import TFC.API.Crafting.AnvilRecipe;
import TFC.API.Crafting.AnvilReq;
import TFC.API.Crafting.CraftingManagerTFC;
import TFC.API.Crafting.KilnCraftingManager;
import TFC.API.Crafting.KilnRecipe;
import TFC.API.Enums.CraftingRuleEnum;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes 
{
	public static Item[] Axes;

	public static Item[] Chisels;

	public static Item[] Saws;

	public static Item[] Scythes;

	public static Item[] Knives;

	public static Item[] MeltedMetal;

	public static Item[] Hammers;

	public static Item[] Spindle;

	public static Item[] Gems;

	public static Item[] Doors;

	public static void registerRecipes()
	{
		Item[] Ingots = {TFCItems.BismuthIngot, TFCItems.BismuthBronzeIngot,TFCItems.BlackBronzeIngot,
				TFCItems.BlackSteelIngot,TFCItems.BlueSteelIngot,TFCItems.BrassIngot,TFCItems.BronzeIngot,
				TFCItems.BronzeIngot,TFCItems.CopperIngot,TFCItems.GoldIngot,TFCItems.WroughtIronIngot,TFCItems.LeadIngot
				,TFCItems.NickelIngot,TFCItems.PigIronIngot,TFCItems.PlatinumIngot,TFCItems.RedSteelIngot,
				TFCItems.RoseGoldIngot,TFCItems.SilverIngot,TFCItems.SteelIngot,TFCItems.SterlingSilverIngot
				,TFCItems.TinIngot,TFCItems.ZincIngot};

		GameRegistry.addShapelessRecipe(new ItemStack(Item.arrow, 8), new Object[] { 
			new ItemStack(TFCItems.LooseRock, 1, 32767), new ItemStack(Item.stick,1,32767),
			new ItemStack(Item.feather,1,32767)});

		//stone javelins
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneJavelinHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneJavelinHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneJavelinHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneJavelinHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});

		//stone shovels
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		//stone axes
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		//stone hoes
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});

		//bone shovels
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		//bone axes
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		//bone hoes
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,32767)});

		GameRegistry.addRecipe(new ItemStack(TFCItems.StoneHammer, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneHammerHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});

		GameRegistry.addRecipe(new ItemStack(TFCItems.StoneKnife, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneKnifeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		


		GameRegistry.addRecipe(new ItemStack(TFCItems.WoodenBucketEmpty, 1), new Object[] { "w w","w w"," w ", Character.valueOf('w'), new ItemStack(TFCItems.SinglePlank, 1, 32767) });

		GameRegistry.addShapelessRecipe(new ItemStack(Item.gunpowder,2,0),new Object[] {new ItemStack(Item.coal,1,1),new ItemStack(TFCItems.Powder, 1, 4),new ItemStack(TFCItems.Powder, 1, 3)});

		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			for(int j = 0; j < Saws.length; j++)
			{
				GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 8, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(Saws[j], 1, 32767)});
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.WoodSupportV, 8, i), new Object[] { "A2"," 2", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Saws[j], 1, 32767)});
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.WoodSupportH, 8, i), new Object[] { "A ","22", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Saws[j], 1, 32767)});
			}

			GameRegistry.addRecipe(new ItemStack(Block.planks.blockID, 1, i), new Object[] {"11","11", Character.valueOf('1'), new ItemStack(TFCItems.SinglePlank, 1, i)});
		}

		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			GameRegistry.addRecipe(new ItemStack(Doors[i], 1, i), new Object[] { "WW","WW","WW", Character.valueOf('W'), new ItemStack(TFCItems.SinglePlank, 1, i)});
		}

		for(int j = 0; j < Knives.length; j++)
		{
			GameRegistry.addRecipe(new ItemStack(Item.bowlEmpty, 4, 0), new Object[] { 
				"2","1", Character.valueOf('1'),new ItemStack(TFCItems.Logs,1,32767), Character.valueOf('2'),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ScrapedHide,1,0), new Object[] {				
				"2","1", Character.valueOf('1'), new ItemStack(TFCItems.SoakedHide,1),Character.valueOf('2'),new ItemStack(Knives[j],1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.Wool,1,0), new Object[] {				
				"2","1", Character.valueOf('1'), new ItemStack(TFCItems.SheepSkin,1),Character.valueOf('2'),new ItemStack(Knives[j],1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.TerraLeather,0,0), new Object[] {				
				"2","1", Character.valueOf('1'), new ItemStack(TFCItems.TerraLeather,1),Character.valueOf('2'),new ItemStack(Knives[j],1,32767)});
		}

		GameRegistry.addRecipe(new ItemStack(Item.itemFrame,1),new Object[]{"###","#$#","###",Character.valueOf('#'),new ItemStack(Item.stick,1),Character.valueOf('$'),new ItemStack(TFCItems.TerraLeather,1)});
		GameRegistry.addRecipe(new ItemStack(Item.painting,1),new Object[]{"###","#$#","###",Character.valueOf('#'),new ItemStack(Item.stick,1),Character.valueOf('$'),new ItemStack(TFCItems.WoolCloth,1)});
		
		GameRegistry.addRecipe(new ItemStack(Block.field_111031_cC,2,0),new Object[]{"$$",Character.valueOf('$'),new ItemStack(TFCItems.WoolCloth,1)});
		for(int i = 0; i < 16;i++){
			GameRegistry.addShapelessRecipe(new ItemStack(Block.field_111031_cC,1,i),new Object[] {new ItemStack(Item.dyePowder,1,15-i),new ItemStack(Block.field_111031_cC,1,32767)});
		}
		
		//Chest
		GameRegistry.addRecipe(new ItemStack(Block.chest, 1), new Object[] { "###","# #","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, 32767)});

		// Stone Bricks
		for(int i = 0; i < Chisels.length; i++)
		{			
			for(int j = 0; j < Global.STONE_IGIN.length; j++)
			{
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneIgInBrick,4,j), 
						new Object[] {"PXP","XPX","PXP",Character.valueOf('P'),new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_IGIN_START),Character.valueOf('X'),new ItemStack(TFCItems.Mortar,1)});
				GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_IGIN_START), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j+Global.STONE_IGIN_START),new ItemStack(Chisels[i],1,32767)});
			}

			for(int j = 0; j < Global.STONE_SED.length; j++)
			{
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneSedBrick,4,j), 
						new Object[] {"PXP","XPX","PXP",Character.valueOf('P'),new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_SED_START),Character.valueOf('X'),new ItemStack(TFCItems.Mortar,1)});
				GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_SED_START), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j+Global.STONE_SED_START),new ItemStack(Chisels[i],1,32767)});
			}

			for(int j = 0; j < Global.STONE_IGEX.length; j++)
			{
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneIgExBrick,4,j), 
						new Object[] {"PXP","XPX","PXP",Character.valueOf('P'),new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_IGEX_START),Character.valueOf('X'),new ItemStack(TFCItems.Mortar,1)});
				GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_IGEX_START), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j+Global.STONE_IGEX_START),new ItemStack(Chisels[i],1,32767)});
			}

			for(int j = 0; j < Global.STONE_MM.length; j++)
			{
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneMMBrick,4,j), 
						new Object[] {"PXP","XPX","PXP",Character.valueOf('P'),new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_MM_START),Character.valueOf('X'),new ItemStack(TFCItems.Mortar,1)});
				GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_MM_START), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j+Global.STONE_MM_START),new ItemStack(Chisels[i],1,32767)});
			}
		}

		GameRegistry.addRecipe(new ItemStack(TFCItems.Mortar,16), new Object[]{"P","X",Character.valueOf('P'),new ItemStack(TFCBlocks.Sand,1,32767),Character.valueOf('X'),new ItemStack(TFCItems.Limewater,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Mortar,16), new Object[]{"P","X",Character.valueOf('P'),new ItemStack(TFCBlocks.Sand2,1,32767),Character.valueOf('X'),new ItemStack(TFCItems.Limewater,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Limewater,1), new Object[]{"P","X",Character.valueOf('P'),new ItemStack(TFCItems.Powder, 1, 0),Character.valueOf('X'),new ItemStack(TFCItems.WoodenBucketWater.setContainerItem(null))});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Mortar,16), new Object[]{"P","X","Y",Character.valueOf('P'),new ItemStack(TFCBlocks.Sand,1,32767),Character.valueOf('X'),new ItemStack(TFCItems.Powder, 1, 0),Character.valueOf('Y'),new ItemStack(TFCItems.WoodenBucketWater,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Mortar,16), new Object[]{"P","X","Y",Character.valueOf('P'),new ItemStack(TFCBlocks.Sand2,1,32767),Character.valueOf('X'),new ItemStack(TFCItems.Powder, 1, 0),Character.valueOf('Y'),new ItemStack(TFCItems.WoodenBucketWater,1,32767)});


		//Gold Pan
		GameRegistry.addRecipe(new ItemStack(TFCItems.GoldPan, 1, 0), new Object[] { 
			"1", Character.valueOf('1'),Item.bowlEmpty});
		//Sluice
		GameRegistry.addRecipe(new ItemStack(TFCItems.SluiceItem, 1), new Object[] { 
			"  1"," 12","122", Character.valueOf('1'),new ItemStack(Item.stick,1,32767), Character.valueOf('2'),new ItemStack(TFCItems.SinglePlank,1,32767)});

		for(int j = 0; j < Recipes.Hammers.length; j++)
		{
			for (int i=0; i < Global.STONE_FLUXINDEX.length; i++) {
				GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Powder, 2, 0), new Object[] {new ItemStack(TFCItems.LooseRock, 1, Global.STONE_FLUXINDEX[i]), new ItemStack(Recipes.Hammers[j], 1, 32767)});
			}
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Powder, 6, 0), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(Recipes.Hammers[j], 1, 32767)});
		}

		GameRegistry.addRecipe(new ItemStack(Item.bow,1), new Object[] {" #$","# $"," #$",Character.valueOf('#'),new ItemStack(Item.stick,1),Character.valueOf('$'),new ItemStack(TFCItems.WoolYarn,1)});
		GameRegistry.addRecipe(new ItemStack(Item.bow,1), new Object[] {"$# ","$ #","$# ",Character.valueOf('#'),new ItemStack(Item.stick,1),Character.valueOf('$'),new ItemStack(TFCItems.WoolYarn,1)});

		GameRegistry.addRecipe(new ItemStack(Item.fishingRod,1), new Object[] {"  #"," #$","# $",Character.valueOf('#'),new ItemStack(Item.stick,1),Character.valueOf('$'),new ItemStack(TFCItems.WoolYarn,1)});
		GameRegistry.addRecipe(new ItemStack(Item.fishingRod,1), new Object[] {"#  ","$# ","$ #",Character.valueOf('#'),new ItemStack(Item.stick,1),Character.valueOf('$'),new ItemStack(TFCItems.WoolYarn,1)});

		//Tool Rack & Barrel
		for(int j = 0; j < 16; j++)
		{
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.ToolRack, 1, j), new Object[] { "###","   ","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,j)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.Barrel,1,j), new Object[] { "# #","# #","###",Character.valueOf('#'),new ItemStack(TFCItems.SinglePlank,1,j)});
		}

		for (int i=0; i < Global.STONE_FLUXINDEX.length; i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Powder, 1, 0), new Object[] {new ItemStack(TFCItems.LooseRock, 1, Global.STONE_FLUXINDEX[i]), new ItemStack(TFCItems.StoneHammer, 1, 32767)});
		}
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Powder, 4, 0), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(TFCItems.StoneHammer, 1, 32767)});

		GameRegistry.addRecipe(new ItemStack(TFCItems.Ink, 16, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(Item.dyePowder,1,0)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.FireStarter, 1, 0), new Object[] { "2 "," 2", Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Bellows, 1, 0), new Object[] { "###","???","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,32767), Character.valueOf('?'), TFCItems.TerraLeather});
		//GameRegistry.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgIn});
		//GameRegistry.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgEx});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil, 1, 1), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.CopperIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil, 1, 2), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BronzeIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil, 1, 3), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.WroughtIronIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil, 1, 4), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.SteelIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil, 1, 5), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackSteelIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil, 1, 6), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlueSteelIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil, 1, 7), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RedSteelIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil2, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RoseGoldIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil2, 1, 1), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BismuthBronzeIngot2x});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Anvil2, 1, 2), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackBronzeIngot2x});

		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Scribe, 1), new Object[] { " L ","#P#","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,32767),
			Character.valueOf('P'), Item.paper,Character.valueOf('L'), Item.feather});
		//GameRegistry.addRecipe(new ItemStack(TFCItems.CeramicMold, 4, 0), new Object[] { "# #","###", Character.valueOf('#'), new ItemStack(Item.clay,1,32767)});

		GameRegistry.addRecipe(new ItemStack(TFCBlocks.EarlyBloomery, 1), new Object[] { "PPP","P P","PPP", Character.valueOf('P'), new ItemStack(TFCItems.BronzeSheet2x, 1)});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.EarlyBloomery, 1), new Object[] { "PPP","P P","PPP", Character.valueOf('P'), new ItemStack(TFCItems.BismuthBronzeSheet2x, 1)});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.EarlyBloomery, 1), new Object[] { "PPP","P P","PPP", Character.valueOf('P'), new ItemStack(TFCItems.BlackBronzeSheet2x, 1)});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.BlastFurnace, 1), new Object[] { "PPP","PCP","PPP", Character.valueOf('P'), new ItemStack(TFCItems.WroughtIronSheet2x, 1),Character.valueOf('C'), new ItemStack(TFCBlocks.Crucible, 1)});

		GameRegistry.addRecipe(new ItemStack(Block.rail, 64), new Object[] { "PsP","PsP", Character.valueOf('P'), TFCItems.WroughtIronIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,32767)});
		GameRegistry.addRecipe(new ItemStack(Block.railPowered, 64), new Object[] { " r ","PsP","PsP", Character.valueOf('P'), TFCItems.GoldIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,32767), Character.valueOf('r'), Item.redstone});
		GameRegistry.addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[] { "P P","PPP", Character.valueOf('P'), TFCItems.WroughtIronSheet});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.minecartCrate, 1), new Object[] { new ItemStack(Block.chest), new ItemStack(Item.minecartEmpty)});
		GameRegistry.addRecipe(new ItemStack(Item.shears, 1), new Object[] { "P "," P", Character.valueOf('P'), TFCItems.WroughtIronIngot});
		GameRegistry.addRecipe(new ItemStack(Block.lever, 1), new Object[] { "P","H", Character.valueOf('P'), new ItemStack(Item.stick,1,32767), Character.valueOf('H'), new ItemStack(TFCItems.LooseRock,1,32767)});
		GameRegistry.addRecipe(new ItemStack(Block.trapdoor, 1, 0), new Object[] { "###","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,32767)});
		GameRegistry.addRecipe(new ItemStack(Item.sign, 1, 0), new Object[] { "###","###"," 1 ", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,32767),Character.valueOf('1'), new ItemStack(Item.stick,1,32767)});

		for(int j = 0; j < Gems.length; j++)
		{
			for(int k = 0; k < 3; k++)
			{
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneIgExSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneIgInSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneSedSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP","GKG","PPP", 
					Character.valueOf('P'), TFCBlocks.StoneMMSmooth, Character.valueOf('K'), new ItemStack(Gems[j],1,0), Character.valueOf('G'), new ItemStack(Block.glass,1)});
			}
		}

		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Quern, 1), new Object[] { "PPP", 
			Character.valueOf('P'), TFCBlocks.StoneIgExSmooth});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Quern, 1), new Object[] { "PPP", 
			Character.valueOf('P'), TFCBlocks.StoneIgInSmooth});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Quern, 1), new Object[] { "PPP", 
			Character.valueOf('P'), TFCBlocks.StoneSedSmooth});
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Quern, 1), new Object[] { "PPP", 
			Character.valueOf('P'), TFCBlocks.StoneMMSmooth});
		GameRegistry.addRecipe(new ItemStack(Item.book,1),new Object[]{" P "," P "," PQ",Character.valueOf('P'),new ItemStack(Item.paper,1),		
			Character.valueOf('Q'),new ItemStack(TFCItems.TerraLeather,1)});
		GameRegistry.addRecipe(new ItemStack(Item.book,1),new Object[]{" P "," P ","QP ",Character.valueOf('P'),new ItemStack(Item.paper,1),		
			Character.valueOf('Q'),new ItemStack(TFCItems.TerraLeather,1)});

		//remove wool and book and quill recipes
		//RemoveRecipe(new ItemStack(Item.writableBook));
		RemoveRecipe(new ItemStack(Block.cloth));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.writabeBookTFC,1), new Object[]{new ItemStack(Item.book,1)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Spindle,1), new Object[] { "P","#",Character.valueOf('P'),
			new ItemStack(TFCItems.SpindleHead,1, 1),Character.valueOf('#'),new ItemStack(Item.stick,1)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SpindleHead,1,0), new Object[] { "P","#",Character.valueOf('P'),
			new ItemStack(Item.clay,32767),Character.valueOf('#'),new ItemStack(Item.stick,1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WoolYarn,8),new Object[]{new ItemStack(TFCItems.Wool,1,32767),new ItemStack(TFCItems.Spindle,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.WoolCloth,1), new Object[]{"PP","PP",Character.valueOf('P'),new ItemStack(TFCItems.WoolYarn,1)});
		GameRegistry.addRecipe(new ItemStack(Item.bed,1),new Object[]{"PPP","QQQ",Character.valueOf('P'),new ItemStack(TFCItems.WoolCloth,1),Character.valueOf('Q'),
			new ItemStack(TFCItems.SinglePlank,1,32767)});

		GameRegistry.addRecipe(new ItemStack(TFCItems.Quern, 1), new Object[] { "  W", "PPP",
			Character.valueOf('P'), TFCBlocks.StoneIgEx, Character.valueOf('W'), Item.stick});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Quern, 1), new Object[] { "  W", "PPP", 
			Character.valueOf('P'), TFCBlocks.StoneIgIn, Character.valueOf('W'), Item.stick});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Quern, 1), new Object[] { "  W", "PPP", 
			Character.valueOf('P'), TFCBlocks.StoneSed, Character.valueOf('W'), Item.stick});
		GameRegistry.addRecipe(new ItemStack(TFCItems.Quern, 1), new Object[] { "  W", "PPP", 
			Character.valueOf('P'), TFCBlocks.StoneMM, Character.valueOf('W'), Item.stick});

		GameRegistry.addRecipe(new ItemStack(TFCItems.FlintSteel, 1), new Object[] { "W ", " P", 
			Character.valueOf('P'), Item.flint, Character.valueOf('W'), TFCItems.WroughtIronIngot});
		GameRegistry.addRecipe(new ItemStack(TFCItems.FlintSteel, 1), new Object[] { "W ", " P", 
			Character.valueOf('P'), Item.flint, Character.valueOf('W'), TFCItems.SteelIngot});

		// Walls & cobble blocks
		for(int j = 0; j < Global.STONE_IGIN.length; j++)
		{
			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneIgInCobble,1,j), 
					new Object[] {"PP","PP",Character.valueOf('P'),new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_IGIN_START)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LooseRock,4,j + Global.STONE_IGIN_START),new ItemStack(TFCBlocks.StoneIgInCobble,1,j));

			// walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallCobbleIgIn, 4, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_IGIN_START)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallRawIgIn, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneIgIn,1,j)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallBrickIgIn, 3, j), 
					new Object[] {"BMB", "MBM", Character.valueOf('B'), new ItemStack(TFCItems.StoneBrick,1,j + Global.STONE_IGIN_START),Character.valueOf('M'),new ItemStack(TFCItems.Mortar,1)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallSmoothIgIn, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneIgInSmooth,1,j)});
		}
		for(int j = 0; j < Global.STONE_SED.length; j++)
		{
			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneSedCobble,1,j), 
					new Object[] {"PP","PP",Character.valueOf('P'),new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_SED_START)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LooseRock,4,j + Global.STONE_SED_START),new ItemStack(TFCBlocks.StoneSedCobble,1,j));

			// walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallCobbleSed, 4, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_SED_START)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallRawSed, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneSed,1,j)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallBrickSed, 3, j), 
					new Object[] {"BMB", "MBM", Character.valueOf('B'), new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_SED_START),Character.valueOf('M'),new ItemStack(TFCItems.Mortar,1)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallSmoothSed, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneSedSmooth,1,j)});
		}

		for(int j = 0; j < Global.STONE_IGEX.length; j++)
		{
			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneIgExCobble,1,j), 
					new Object[] {"PP","PP",Character.valueOf('P'),new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_IGEX_START)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LooseRock,4,j + Global.STONE_IGEX_START),new ItemStack(TFCBlocks.StoneIgExCobble,1,j));

			//walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallCobbleIgEx, 4, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_IGEX_START)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallRawIgEx, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneIgEx,1,j)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallBrickIgEx, 3, j), 
					new Object[] {"BMB", "MBM", Character.valueOf('B'),new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_IGEX_START),Character.valueOf('M'),new ItemStack(TFCItems.Mortar,1)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallSmoothIgEx, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneIgExSmooth,1,j)});
		}

		for(int j = 0; j < Global.STONE_MM.length; j++)
		{
			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneMMCobble,1,j), 
					new Object[] {"PP","PP",Character.valueOf('P'),new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_MM_START)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LooseRock,4,j + Global.STONE_MM_START),new ItemStack(TFCBlocks.StoneMMCobble,1,j));

			//walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallCobbleMM, 4, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCItems.LooseRock,1,j + Global.STONE_MM_START)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallRawMM, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneMM,1,j)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallBrickMM, 3, j), 
					new Object[] {"BMB", "MBM", Character.valueOf('B'),new ItemStack(TFCItems.StoneBrick,1,j+Global.STONE_MM_START),Character.valueOf('M'),new ItemStack(TFCItems.Mortar,1)});
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.WallSmoothMM, 3, j), 
					new Object[] {"RRR", "RRR", Character.valueOf('R'), new ItemStack(TFCBlocks.StoneMMSmooth,1,j)});
		}

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Blueprint, 1), new Object[] { new ItemStack(TFCItems.Ink,3,32767), new ItemStack(Item.paper, 1)});

		//Ingot -> Unshaped
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BismuthIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BismuthBronzeIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BlackBronzeIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackSteelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BlackSteelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlueSteelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BlueSteelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BrassUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BrassIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BronzeIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.CopperIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GoldUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.GoldIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WroughtIronUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WroughtIronIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LeadUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.LeadIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.NickelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.NickelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.PigIronUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.PigIronIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.PlatinumUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.PlatinumIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RedSteelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.RedSteelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RoseGoldUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.RoseGoldIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SteelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.SteelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SterlingSilverUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.SterlingSilverIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.TinUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.TinIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.ZincUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ZincIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WeakSteelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WeakSteelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WeakBlueSteelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WeakBlueSteelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WeakRedSteelUnshaped, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WeakRedSteelIngot, 1)), new ItemStack(TFCItems.CeramicMold, 1, 1)});

		//Unshaped -> Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BismuthUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BlackSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlueSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BlueSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BrassIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BrassUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.BronzeUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.CopperUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GoldIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.GoldUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.HCSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.HCSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.HCBlackSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.HCBlackSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.HCBlueSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.HCBlueSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.HCRedSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.HCRedSteelUnshaped, 1))});		
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WroughtIronIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WroughtIronUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LeadIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.LeadUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.NickelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.NickelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.PigIronIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.PigIronUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.PlatinumIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.PlatinumUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RedSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.RedSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RoseGoldIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.RoseGoldUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SilverIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.SilverUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.SteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SterlingSilverIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.SterlingSilverUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.TinIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.TinUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.ZincIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ZincUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WeakSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WeakSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WeakBlueSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WeakBlueSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WeakRedSteelIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.WeakRedSteelUnshaped, 1))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.UnknownIngot, 1, 0), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.UnknownUnshaped, 1))});

		GameRegistry.addRecipe(new ItemStack(Item.clay,1,1), 
				new Object[] {"PXP","XCX","PXP",Character.valueOf('P'),new ItemStack(TFCItems.Powder,1,1),Character.valueOf('X'),new ItemStack(TFCItems.Powder,1,2),Character.valueOf('C'),new ItemStack(Item.clay,1,0)});

		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Thatch,1), 
				new Object[] {"PP","PP",Character.valueOf('P'),new ItemStack(TFCItems.Straw, 1)});

		RegisterToolRecipes();
		registerFoodRecipes();
		VanillaRecipes();
		registerKilnRecipes();
		registerToolMolds();
	}
	
	private static ItemStack checkMelted(ItemStack is){
		if(TFC_ItemHeat.GetTemperature(is)>TFC_ItemHeat.getMeltingPoint(is)){
			return null;
		}
		return is;
	}

	private static void VanillaRecipes()
	{
		if(TFCOptions.enableVanillaDiamondRecipe == true)
		{
			GameRegistry.addRecipe(new ItemStack(Item.diamond, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GemDiamond,1,2)});
			GameRegistry.addRecipe(new ItemStack(Item.diamond, 2), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GemDiamond,1,3)});
			GameRegistry.addRecipe(new ItemStack(Item.diamond, 3), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GemDiamond,1,4)});
		}
		if(TFCOptions.enableVanillaIronRecipe== true)
		{
			GameRegistry.addRecipe(new ItemStack(Item.ingotIron, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.WroughtIronIngot,1)});

		}
		if(TFCOptions.enableVanillaGoldRecipe == true)
		{
			GameRegistry.addRecipe(new ItemStack(Item.ingotGold, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GoldIngot,1)});
		}
		if(TFCOptions.enableVanillaRecipes == true)
		{
			GameRegistry.addRecipe(new ItemStack(Item.appleRed,1),new Object[]{"1",Character.valueOf('1'),new ItemStack(TFCItems.RedApple,1)});

			//Terrastone to Cobblestone
			GameRegistry.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneSedCobble});
			GameRegistry.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneIgInCobble});
			GameRegistry.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneIgExCobble});
			GameRegistry.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.StoneMMCobble});
			GameRegistry.addShapelessRecipe(new ItemStack(Item.leather,1),new Object[]{new ItemStack(TFCItems.TerraLeather,1)});

			//Conversion to vanilla tools for recipes in other mods
			GameRegistry.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgInShovel});
			GameRegistry.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgExShovel});
			GameRegistry.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.SedShovel});
			GameRegistry.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.MMShovel});
			GameRegistry.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgInHoe});
			GameRegistry.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgExHoe});
			GameRegistry.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.SedHoe});
			GameRegistry.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.MMHoe});
			GameRegistry.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgInAxe});
			GameRegistry.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.IgExAxe});
			GameRegistry.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.SedAxe});
			GameRegistry.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.MMAxe});
		}
		else
		{
			RemoveRecipe(new ItemStack(Item.pickaxeWood,1));
			RemoveRecipe(new ItemStack(Item.axeWood,1));
			RemoveRecipe(new ItemStack(Item.shovelWood,1));
			RemoveRecipe(new ItemStack(Item.hoeWood,1));
			RemoveRecipe(new ItemStack(Item.swordWood,1));
			RemoveRecipe(new ItemStack(Block.furnaceIdle,1));
			RemoveRecipe(new ItemStack(Block.torchWood,4));
			RemoveRecipe(new ItemStack(Item.stick,4));
			RemoveRecipe(new ItemStack(Block.planks,4));
			RemoveRecipe(new ItemStack(Item.minecartCrate, 1));
			RemoveRecipe(new ItemStack(Item.dyePowder,3, 15));
			RemoveRecipe(new ItemStack(Item.dyePowder,2, 1));
			RemoveRecipe(new ItemStack(Item.dyePowder,2, 11));
		}
	}

	private static void RegisterToolRecipes()
	{
		for(int i = 0; i <= 25; i+=5)
		{
			float j = ((float)i / 100);
			//pickaxes
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzePick, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzePick, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelPick, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelPick, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzePick, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperPick, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronPick, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelPick, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelPick, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Shovels
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeShovel, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeShovel, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelShovel, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelShovel, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeShovel, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperShovel, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronShovel, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelShovel, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelShovel, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Hoes
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeHoe, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeHoe, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelHoe, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelHoe, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeHoe, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperHoe, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronHoe, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelHoe, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelHoe, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Axes
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeAxe, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeAxe, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelAxe, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelAxe, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeAxe, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperAxe, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronAxe, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelAxe, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelAxe, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Swords
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeSword, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeSword, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelSword, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelSword, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeSword, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperSword, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronSword, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelSword, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelSword, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Maces
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeMace, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeMace, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelMace, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelMace, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeMace, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperMace, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronMace, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelMace, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelMace, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Hammers
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeHammer, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeHammer, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelHammer, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelHammer, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeHammer, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperHammer, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronHammer, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelHammer, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelHammer, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Saws
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeSaw, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeSaw, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelSaw, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelSaw, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeSaw, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperSaw, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronSaw, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelSaw, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelSaw, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//Chisels
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeChisel, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeChisel, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelChisel, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelChisel, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeChisel, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperChisel, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronChisel, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelChisel, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelChisel, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//propicks
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickBismuthBronze, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickBlackBronze, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickBlackSteel, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickBlueSteel, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickBronze, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickCopper, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickIron, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickRedSteel, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.ProPickSteel, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			//Scythes
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeScythe, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeScythe, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelScythe, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelScythe, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeScythe, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperScythe, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronScythe, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelScythe, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelScythe, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelScytheHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			//Knifes
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeKnife, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeKnife, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelKnife, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelKnife, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeKnife, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperKnife, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronKnife, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelKnife, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelKnife, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelKnifeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

			//javelins
			GameRegistry.addRecipe(new ItemStack(TFCItems.BismuthBronzeJavelin, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BismuthBronzeJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackBronzeJavelin, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackBronzeJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlackSteelJavelin, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlackSteelJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BlueSteelJavelin, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BlueSteelJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.BronzeJavelin, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.BronzeJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.CopperJavelin, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.CopperJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.WroughtIronJavelin, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.WroughtIronJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.RedSteelJavelin, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.RedSteelJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});
			GameRegistry.addRecipe(new ItemStack(TFCItems.SteelJavelin, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
				new ItemStack(TFCItems.SteelJavelinHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick,1,32767)});

		}

		//stone prospecting pick
		//        GameRegistry.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
		//            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		//        GameRegistry.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
		//            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		//        GameRegistry.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
		//            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		//        GameRegistry.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
		//            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), new ItemStack(Item.stick,1,32767)});
		//scribing table
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.PickaxeHeadPlan, 1), new Object[] { " ### ","#   #", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ShovelHeadPlan, 1), new Object[] { " ### "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.HoeHeadPlan, 1), new Object[] { "#####","   ##", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.AxeHeadPlan, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.HammerHeadPlan, 1), new Object[] { "#####","#####","  #  ", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ChiselHeadPlan, 1), new Object[] { "#","#","#","#","#", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SwordBladePlan, 1), new Object[] { "   ##","  ###"," ### "," ##  ","#    ", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.MaceHeadPlan, 1), new Object[] { "  #  "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SawBladePlan, 1), new Object[] { "##   ","###  "," ### "," ####","   ##", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ProPickHeadPlan, 1), new Object[] { " ####","#   #","    #", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.HelmetPlan, 1), new Object[] { "#####","#   #","#   #", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ChestplatePlan, 1), new Object[] { "#   #","#####","#####","#####","#####", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.GreavesPlan, 1), new Object[] { "#####","#####","## ##","## ##","## ##", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.BootsPlan, 1), new Object[] { "##   ","##   ","##   ","#### ","#####", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ScythePlan, 1), new Object[] { "#### "," ####","   ##", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.KnifePlan, 1), new Object[] { " #","##","##","##","##", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.BucketPlan, 1), new Object[] { "#   #","#   #","#   #","#   #"," ### ", Character.valueOf('#'), TFCItems.Ink});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.JavelinHeadPlan, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), TFCItems.Ink});

		//clay molds
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldPick, 1), new Object[] { "     "," ### ","#   #", "     ",Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldShovel, 1), new Object[] { " ### "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHoe, 1), new Object[] { "     ","#####","   ##","     ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldAxe, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHammer, 1), new Object[] { "     ","#####","#####","  #  ","     ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldChisel, 1), new Object[] { "  #  ","  #  ","  #  ","  #  ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSword, 1), new Object[] { "   ##","  ###"," ### "," ##  ","#    ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldMace, 1), new Object[] { "  #  "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSaw, 1), new Object[] { "##   ","###  "," ### "," ####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldProPick, 1), new Object[] { "     "," ####","#   #","    #","     ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldScythe, 1), new Object[] { "     ","#### "," ####","   ##","     ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldKnife, 1), new Object[] { "  # "," ## "," ## "," ## "," ## ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldJavelin, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});

		registerAlloys();

		registerKnapping();

		//Leather Working
		RemoveRecipe(new ItemStack(Item.helmetLeather));
		RemoveRecipe(new ItemStack(Item.plateLeather));
		RemoveRecipe(new ItemStack(Item.legsLeather));
		RemoveRecipe(new ItemStack(Item.bootsLeather));
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(Item.helmetLeather, 1), new Object[] { "#####","#   #","#   #", Character.valueOf('#'), TFCItems.FlatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(Item.plateLeather, 1), new Object[] { "#   #","#####","#####","#####","#####", Character.valueOf('#'), TFCItems.FlatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(Item.legsLeather, 1), new Object[] { "#####","#####","## ##","## ##","## ##", Character.valueOf('#'), TFCItems.FlatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(Item.bootsLeather, 1), new Object[] { "##   ","##   ","##   ","#### ","#####", Character.valueOf('#'), TFCItems.FlatLeather});



	}

	private static void registerKnapping() {
		//Knapping
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { " #  #","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { "#  # ","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { "#   #","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { " # # ","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 1), new Object[] { " #","##","##","##","##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneHammerHead, 1), new Object[] { "#####","#####","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});

		for(int i = 0; i < Global.STONE_IGIN.length; i++) {
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneHoeHead, 1), new Object[] { "#####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
		}
		for(int i = 0; i < Global.STONE_SED.length; i++) {
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneHoeHead, 1), new Object[] { "#####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
		}
		for(int i = 0; i < Global.STONE_IGEX.length; i++) {
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneHoeHead, 1), new Object[] { "#####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
		}
		for(int i = 0; i < Global.STONE_MM.length; i++) {
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.MMStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_MM_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.MMStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_MM_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.MMStoneHoeHead, 1), new Object[] { "#####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_MM_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.MMStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_MM_START)});
		}        


		//Inverse Clay Knapping
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.CeramicMold, 2, 0), new Object[] { 
			"    ",
			" ## ",
			" ## ",
			" ## ",
			"    ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.PotteryJug, 1, 0), new Object[] { 
			"X XXX",
			"    X",
			"   X ",
			"    X",
			"   XX", Character.valueOf('X'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.PotterySmallVessel, 1, 0), new Object[] { 
			"#   #",
			"     ",
			"     ",
			"     ",
			"#   #", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCBlocks.Crucible, 1), new Object[] { 
			" ### ",
			" ### ",
			" ### ",
			" ### ",
			"     ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 3)});
	}

	private static void registerAlloys() {
		/**metallurgy*/
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.CopperUnshaped),
			new ItemStack(TFCItems.TinUnshaped), new ItemStack(TFCItems.BismuthUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.CopperUnshaped),
			new ItemStack(TFCItems.ZincUnshaped), new ItemStack(TFCItems.BismuthUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.CopperUnshaped),
			new ItemStack(TFCItems.SilverUnshaped), new ItemStack(TFCItems.GoldUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.WeakSteelUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.SteelUnshaped),new ItemStack(TFCItems.SteelUnshaped),
			new ItemStack(TFCItems.NickelUnshaped), new ItemStack(TFCItems.BlackBronzeUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.WeakBlueSteelUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.BlackSteelUnshaped), new ItemStack(TFCItems.BismuthBronzeUnshaped), 
			new ItemStack(TFCItems.SterlingSilverUnshaped),new ItemStack(TFCItems.SteelUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.BrassUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.CopperUnshaped),
			new ItemStack(TFCItems.CopperUnshaped), new ItemStack(TFCItems.ZincUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.BronzeUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.CopperUnshaped),
			new ItemStack(TFCItems.CopperUnshaped), new ItemStack(TFCItems.TinUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.WeakRedSteelUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.BlackSteelUnshaped), new ItemStack(TFCItems.RoseGoldUnshaped),  
			new ItemStack(TFCItems.BrassUnshaped), new ItemStack(TFCItems.SteelUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.RoseGoldUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.GoldUnshaped),
			new ItemStack(TFCItems.GoldUnshaped), new ItemStack(TFCItems.GoldUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.HCSteelUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.PigIronUnshaped),new ItemStack(TFCItems.WroughtIronUnshaped),
			new ItemStack(TFCItems.WroughtIronUnshaped), new ItemStack(TFCItems.WroughtIronUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.SterlingSilverUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.SilverUnshaped),
			new ItemStack(TFCItems.SilverUnshaped), new ItemStack(TFCItems.SilverUnshaped)});
	}

	private static void registerToolMolds() 
	{
		////////////////////////////////////////////////////////////////
		//Mold pouring
		////////////////////////////////////////////////////////////////        
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldAxe, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldAxe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldAxe, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldAxe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldAxe, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldAxe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldAxe, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldAxe, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldChisel, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldChisel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldChisel, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldChisel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldChisel, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldChisel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldChisel, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldChisel, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHammer, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHammer, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHammer, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHammer, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHammer, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHammer, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHammer, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHammer, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHoe, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHoe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHoe, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHoe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHoe, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHoe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldHoe, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldHoe, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldKnife, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldKnife, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldKnife, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldKnife, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldKnife, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldKnife, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldKnife, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldKnife, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldJavelin, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldJavelin, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldJavelin, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldJavelin, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldJavelin, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldJavelin, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldJavelin, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldJavelin, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldMace, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldMace, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldMace, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldMace, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldMace, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldMace, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldMace, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldMace, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldPick, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldPick, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldPick, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldPick, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldPick, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldProPick, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldProPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldProPick, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldProPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldProPick, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldProPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldProPick, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldProPick, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSaw, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSaw, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSaw, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSaw, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSaw, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSaw, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSaw, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSaw, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldScythe, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldScythe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldScythe, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldScythe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldScythe, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldScythe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldScythe, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldScythe, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldShovel, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldShovel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldShovel, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldShovel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldShovel, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldShovel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldShovel, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldShovel, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSword, 1, 2), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.CopperUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSword, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSword, 1, 3), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSword, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSword, 1, 4), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BismuthBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSword, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ClayMoldSword, 1, 5), 
				new Object[] {"12", Character.valueOf('1'), getStackTemp(new ItemStack(TFCItems.BlackBronzeUnshaped, 1, 1)), Character.valueOf('2'), new ItemStack(TFCItems.ClayMoldSword, 1, 1)});
		////////////////////////////////////////////////////////////////
		//Actual Tools
		////////////////////////////////////////////////////////////////
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperAxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldAxe, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeAxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldAxe, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeAxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldAxe, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeAxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldAxe, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperChiselHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldChisel, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeChiselHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldChisel, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeChiselHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldChisel, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeChiselHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldChisel, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperHammerHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHammer, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeHammerHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHammer, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeHammerHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHammer, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeHammerHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHammer, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperHoeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHoe, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeHoeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHoe, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeHoeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHoe, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeHoeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldHoe, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperKnifeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldKnife, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeKnifeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldKnife, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeKnifeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldKnife, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeKnifeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldKnife, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperJavelinHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldJavelin, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeJavelinHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldJavelin, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeJavelinHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldJavelin, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeJavelinHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldJavelin, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperMaceHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldMace, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeMaceHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldMace, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeMaceHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldMace, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeMaceHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldMace, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperPickaxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldPick, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzePickaxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldPick, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzePickaxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldPick, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzePickaxeHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldPick, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperProPickHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldProPick, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeProPickHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldProPick, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeProPickHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldProPick, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeProPickHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldProPick, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperSawHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSaw, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeSawHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSaw, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeSawHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSaw, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeSawHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSaw, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperScytheHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldScythe, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeScytheHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldScythe, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeScytheHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldScythe, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeScytheHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldScythe, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperShovelHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldShovel, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeShovelHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldShovel, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeShovelHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldShovel, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeShovelHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldShovel, 1, 5))});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CopperSwordHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSword, 1, 2))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BronzeSwordHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSword, 1, 3))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeSwordHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSword, 1, 4))});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BlackBronzeSwordHead), 
				new Object[] {getStackNoTemp(new ItemStack(TFCItems.ClayMoldSword, 1, 5))});
	}

	public static ItemStack	getStackTemp(ItemStack is)
	{
		NBTTagCompound Temp = new NBTTagCompound();
		Temp.setBoolean("temperature", true);
		is.setTagCompound(Temp);
		return is;
	}

	public static ItemStack	getStackNoTemp(ItemStack is)
	{
		NBTTagCompound noTemp = new NBTTagCompound();
		noTemp.setBoolean("noTemp", true);
		is.setTagCompound(noTemp);
		return is;
	}

	public static void registerAnvilRecipes(Random R, World world)
	{
		AnvilCraftingManagerTFC manager = AnvilCraftingManagerTFC.getInstance();
		int v = -5 + R.nextInt(5);

		/**
		 * Weld Recipes
		 */
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot),new ItemStack(TFCItems.BismuthIngot),AnvilReq.STONE, new ItemStack(TFCItems.BismuthIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot),new ItemStack(TFCItems.BismuthBronzeIngot),AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot),new ItemStack(TFCItems.BlackBronzeIngot),AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot),new ItemStack(TFCItems.BlackSteelIngot),AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot),new ItemStack(TFCItems.BlueSteelIngot),AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BrassIngot),new ItemStack(TFCItems.BrassIngot),AnvilReq.COPPER, new ItemStack(TFCItems.BrassIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot),new ItemStack(TFCItems.BronzeIngot),AnvilReq.COPPER, new ItemStack(TFCItems.BronzeIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot),new ItemStack(TFCItems.CopperIngot),AnvilReq.STONE, new ItemStack(TFCItems.CopperIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.GoldIngot),new ItemStack(TFCItems.GoldIngot),AnvilReq.COPPER, new ItemStack(TFCItems.GoldIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot),new ItemStack(TFCItems.WroughtIronIngot),AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.LeadIngot),new ItemStack(TFCItems.LeadIngot),AnvilReq.COPPER, new ItemStack(TFCItems.LeadIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.NickelIngot),new ItemStack(TFCItems.NickelIngot),AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.NickelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronIngot),new ItemStack(TFCItems.PigIronIngot),AnvilReq.BRONZE, new ItemStack(TFCItems.PigIronIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.PlatinumIngot),new ItemStack(TFCItems.PlatinumIngot),AnvilReq.BRONZE, new ItemStack(TFCItems.PlatinumIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot),new ItemStack(TFCItems.RedSteelIngot),AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot),new ItemStack(TFCItems.RoseGoldIngot),AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SilverIngot),new ItemStack(TFCItems.SilverIngot),AnvilReq.COPPER, new ItemStack(TFCItems.SilverIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot),new ItemStack(TFCItems.SteelIngot),AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SterlingSilverIngot),new ItemStack(TFCItems.SterlingSilverIngot),AnvilReq.BRONZE, new ItemStack(TFCItems.SterlingSilverIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot),new ItemStack(TFCItems.TinIngot),AnvilReq.STONE, new ItemStack(TFCItems.TinIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot),new ItemStack(TFCItems.ZincIngot),AnvilReq.STONE, new ItemStack(TFCItems.ZincIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakSteelIngot),new ItemStack(TFCItems.PigIronIngot),AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.HCBlackSteelIngot, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakBlueSteelIngot),new ItemStack(TFCItems.BlackSteelIngot),AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCBlueSteelIngot, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakRedSteelIngot),new ItemStack(TFCItems.BlackSteelIngot),AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCRedSteelIngot, 1)));

		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthSheet),new ItemStack(TFCItems.BismuthSheet),AnvilReq.STONE, new ItemStack(TFCItems.BismuthSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet),new ItemStack(TFCItems.BismuthBronzeSheet),AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet),new ItemStack(TFCItems.BlackBronzeSheet),AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet),new ItemStack(TFCItems.BlackSteelSheet),AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet),new ItemStack(TFCItems.BlueSteelSheet),AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BrassSheet),new ItemStack(TFCItems.BrassSheet),AnvilReq.COPPER, new ItemStack(TFCItems.BrassSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet),new ItemStack(TFCItems.BronzeSheet),AnvilReq.COPPER, new ItemStack(TFCItems.BronzeSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet),new ItemStack(TFCItems.CopperSheet),AnvilReq.STONE, new ItemStack(TFCItems.CopperSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.GoldSheet),new ItemStack(TFCItems.GoldSheet),AnvilReq.COPPER, new ItemStack(TFCItems.GoldSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet),new ItemStack(TFCItems.WroughtIronSheet),AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.LeadSheet),new ItemStack(TFCItems.LeadSheet),AnvilReq.COPPER, new ItemStack(TFCItems.LeadSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.NickelSheet),new ItemStack(TFCItems.NickelSheet),AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.NickelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronSheet),new ItemStack(TFCItems.PigIronSheet),AnvilReq.BRONZE, new ItemStack(TFCItems.PigIronSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.PlatinumSheet),new ItemStack(TFCItems.PlatinumSheet),AnvilReq.BRONZE, new ItemStack(TFCItems.PlatinumSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet),new ItemStack(TFCItems.RedSteelSheet),AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldSheet),new ItemStack(TFCItems.RoseGoldSheet),AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SilverSheet),new ItemStack(TFCItems.SilverSheet),AnvilReq.COPPER, new ItemStack(TFCItems.SilverSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet),new ItemStack(TFCItems.SteelSheet),AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SterlingSilverSheet),new ItemStack(TFCItems.SterlingSilverSheet),AnvilReq.BRONZE, new ItemStack(TFCItems.SterlingSilverSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinSheet),new ItemStack(TFCItems.TinSheet),AnvilReq.STONE, new ItemStack(TFCItems.TinSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincSheet),new ItemStack(TFCItems.ZincSheet),AnvilReq.STONE, new ItemStack(TFCItems.ZincSheet2x, 1)));

		//chest
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.BismuthBronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.BlackBronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.BlackSteelSheet2x),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.BlueSteelSheet2x),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.BronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,0),new ItemStack(TFCItems.CopperSheet2x),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,0),new ItemStack(TFCItems.WroughtIronSheet2x),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.RedSteelSheet2x),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.SteelSheet2x),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedChestplate,1, 1)));

		//greaves
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,0),new ItemStack(TFCItems.CopperSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,0),new ItemStack(TFCItems.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedGreaves,1, 1)));

		//Helmet
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,0),new ItemStack(TFCItems.CopperSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,0),new ItemStack(TFCItems.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedHelmet,1, 1)));

		//Boots
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedBoots,1,0),new ItemStack(TFCItems.CopperSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,0),new ItemStack(TFCItems.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedBoots,1,0),new ItemStack(TFCItems.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedBoots,1, 1)));

		/**
		 * Normal Recipes Start Here
		 */
		//Ingots
		/*manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BrassUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BrassIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.CopperIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.GoldUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.GoldIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.LeadUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.LeadIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.NickelUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.NickelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.PigIronIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PlatinumUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.PlatinumIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SilverUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.SilverIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SterlingSilverUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.SterlingSilverIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnshaped), null,19,CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincIngot)));*/
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronIngot), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.HCSteelIngot))); 

		/*manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakBlueSteelUnshaped), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.WeakBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakRedSteelUnshaped), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.WeakRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakSteelUnshaped), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WeakSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlackSteelUnshaped), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.HCBlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlueSteelUnshaped), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCRedSteelUnshaped), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCSteelUnshaped), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.HCSteelIngot)));*/
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlackSteelIngot), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelIngot)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlueSteelIngot), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelIngot)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCRedSteelIngot), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelIngot)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCSteelIngot), null,70 + R.nextInt(15),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelIngot)));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot2x), null, 20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BrassIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BrassSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.GoldIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.GoldSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.LeadIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.LeadSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.NickelIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.NickelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.PigIronSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PlatinumIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.PlatinumSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldSheet)));       
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SilverIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.SilverSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SterlingSilverIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.SterlingSilverSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot2x), null,20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincSheet)));

		//Picks
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Picks") + world.getSeed());
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.PickaxeHeadPlan), 20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthPickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzePickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzePickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelPickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelPickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzePickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperPickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronPickaxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelPickaxeHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldPickaxeHead, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelPickaxeHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinPickaxeHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.BENDNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincPickaxeHead, 1, dam)));
		}
		//shovels
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Shovels") + world.getSeed());
			int dam = i * 5;
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeShovelHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeShovelHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelShovelHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelShovelHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeShovelHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperShovelHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronShovelHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelShovelHead, 1, dam)));   
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.ANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelShovelHead, 1, dam)));
		}
		//axes 
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Axes") + world.getSeed());
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronAxeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelAxeHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldAxeHead, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelAxeHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinAxeHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincAxeHead, 1, dam)));
		}
		//hoes
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Hoes") + world.getSeed());
			int dam = i * 5;
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHoeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHoeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHoeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHoeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHoeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperHoeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHoeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHoeHead, 1, dam)));    
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelHoeHead, 1, dam)));
		}
		//Hammers
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Hammers") + world.getSeed());
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHammerHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHammerHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldHammerHead, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelHammerHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinHammerHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincHammerHead, 1, dam)));
		}
		//Chisels     
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Chisels") + world.getSeed());
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronChiselHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelChiselHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldChiselHead, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelChiselHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinChiselHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITNOTLAST, CraftingRuleEnum.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincChiselHead, 1, dam)));
		}
		//ProPicks
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Pro Picks") + world.getSeed());
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronProPickHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelProPickHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldProPickHead, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelProPickHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinProPickHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.DRAWNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincProPickHead, 1, dam)));
		}
		//Saws
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Saws") + world.getSeed());
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSawHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSawHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldSawHead, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelSawHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.STONE, new ItemStack(TFCItems.TinSawHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincSawHead, 1, dam)));
		}
		//Swords
		R = new Random (valueOfString("Swords") + world.getSeed());
		for(int i = 0; i < 5; i++)
		{
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSwordHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSwordHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldSwordHead, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelSwordHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinSwordHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincSwordHead, 1, dam)));
		}
		//Maces
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Maces") + world.getSeed());
			int dam = i * 5;
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronMaceHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelMaceHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldMaceHead, 1, dam)));      
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelMaceHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinMaceHead, 1, dam)));
			//manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.SHRINKNOTLAST, CraftingRuleEnum.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincMaceHead, 1, dam)));
		}
		//Scythes
		R = new Random (valueOfString("Scythes") + world.getSeed());
		for(int i = 0; i < 5; i++)
		{
			int dam = i * 5;
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeScytheHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeScytheHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelScytheHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelScytheHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeScytheHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperScytheHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronScytheHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelScytheHead, 1, dam)));    
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.ScythePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelScytheHead, 1, dam)));
		}
		//Knifes
		R = new Random (valueOfString("Knifes") + world.getSeed());
		for(int i = 0; i < 5; i++)
		{
			int dam = i * 5;
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelKnifeHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.KnifePlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.DRAWSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelKnifeHead, 1, dam)));
		}

		//javelin heads
		R = new Random (valueOfString("Javelins") + world.getSeed());
		for(int i = 0; i < 5; i++)
		{
			int dam = i * 5;
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronJavelinHead, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.JavelinHeadPlan),20 + R.nextInt(55),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.DRAWTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperJavelinHead, 1, dam)));   
		}

		R = new Random (valueOfString("Helmet") + world.getSeed());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedHelmet, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedHelmet, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedHelmet, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedHelmet, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedHelmet, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedHelmet, 1, 0)));     
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedHelmet, 1, 0)));
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Helmet") + world.getSeed());
			int dam = i * 5;
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHelmet, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHelmet, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHelmet, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHelmet, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHelmet, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperHelmet, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHelmet, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHelmet, 1, dam)));    
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelHelmet, 1, dam)));
		}

		R = new Random (valueOfString("Chestplate") + world.getSeed());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedChestplate, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedChestplate, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedChestplate, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedChestplate, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedChestplate, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedChestplate, 1, 0)));   
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedChestplate, 1, 0)));
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Chestplate") + world.getSeed());
			int dam = i * 5;
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeChestplate, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeChestplate, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelChestplate, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelChestplate, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeChestplate, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperChestplate, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronChestplate, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelChestplate, 1, dam)));       
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelChestplate, 1, dam)));
		}

		R = new Random (valueOfString("Greaves") + world.getSeed());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedGreaves, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedGreaves, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedGreaves, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedGreaves, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedGreaves, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedGreaves, 1, 0)));     
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedGreaves, 1, 0)));
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Greaves") + world.getSeed());
			int dam = i * 5;

			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelGreaves, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDANY, CraftingRuleEnum.DRAWANY, CraftingRuleEnum.HITANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelGreaves, 1, dam)));

		}

		R = new Random (valueOfString("Boots") + world.getSeed());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedBoots, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedBoots, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedBoots, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedBoots, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedBoots, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedBoots, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedBoots, 1, 0)));      
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedBoots, 1, 0)));
		for(int i = 0; i < 5; i++)
		{
			R = new Random (valueOfString("Boots") + world.getSeed());
			int dam = i * 5;    
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelBoots, 1, dam)));
			manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelBoots, 1, dam)));
		}

		//Buckets
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), new ItemStack(TFCItems.BucketPlan),20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelBucketEmpty, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet), new ItemStack(TFCItems.BucketPlan),20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelBucketEmpty, 1)));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RawBloom, 1, 32767), null, 60, CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.Bloom, 1)).setInheritsDamage());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.Bloom, 1, 100), null, 20 + R.nextInt(55) , CraftingRuleEnum.HITLAST, CraftingRuleEnum.HITSECONDFROMLAST, CraftingRuleEnum.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronIngot, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.Bloom, 1, 32767), null, 20 + R.nextInt(55) , CraftingRuleEnum.PUNCHLAST, CraftingRuleEnum.ANY, CraftingRuleEnum.ANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.Bloom, 1)));

		//Tuyeres
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.TuyereCopper, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.TuyereBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.TuyereBismuthBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.TuyereBlackBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.TuyereWroughtIron, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.TuyereSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.TuyereBlackSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.TuyereBlueSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), null,20 + R.nextInt(55),CraftingRuleEnum.BENDLAST, CraftingRuleEnum.BENDSECONDFROMLAST, CraftingRuleEnum.ANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.TuyereRedSteel, 1)));
	}

	public static void registerFoodRecipes()
	{
		for(int j = 0; j < Knives.length; j++)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WheatGrain, 4), new Object[] {new ItemStack(TFCItems.WheatWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RyeGrain, 4), new Object[] {new ItemStack(TFCItems.RyeWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BarleyGrain, 4), new Object[] {new ItemStack(TFCItems.BarleyWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.OatGrain, 4), new Object[] {new ItemStack(TFCItems.OatWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RiceGrain, 4), new Object[] {new ItemStack(TFCItems.RiceWhole, 1),new ItemStack(Knives[j], 1, 32767)});
		}



		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsWheat, 1), new Object[] {new ItemStack(TFCItems.WheatGrain, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsRye, 1), new Object[] {new ItemStack(TFCItems.RyeGrain, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsBarley, 1), new Object[] {new ItemStack(TFCItems.BarleyGrain, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsOat, 1), new Object[] {new ItemStack(TFCItems.OatGrain, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsRice, 1), new Object[] {new ItemStack(TFCItems.RiceGrain, 1)});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsTomato, 2), new Object[] {new ItemStack(TFCItems.Tomato, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsPotato, 2), new Object[] {new ItemStack(TFCItems.Potato, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsMaize, 2), new Object[] {new ItemStack(TFCItems.MaizeEar, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsOnion, 2), new Object[] {new ItemStack(TFCItems.Onion, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsCabbage, 2), new Object[] {new ItemStack(TFCItems.Cabbage, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsGarlic, 2), new Object[] {new ItemStack(TFCItems.Garlic, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsCarrot, 2), new Object[] {new ItemStack(TFCItems.Carrot, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsYellowBellPepper, 2), new Object[] {new ItemStack(TFCItems.YellowBellPepper, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsRedBellPepper, 2), new Object[] {new ItemStack(TFCItems.RedBellPepper, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsSoybean, 2), new Object[] {new ItemStack(TFCItems.Soybean, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsGreenbean, 2), new Object[] {new ItemStack(TFCItems.Greenbeans, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.SeedsSquash, 2), new Object[] {new ItemStack(TFCItems.Squash, 1)});

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WheatDough, 2), new Object[] {TFCItems.WheatGround,TFCItems.WheatGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BarleyDough, 2), new Object[] {TFCItems.BarleyGround,TFCItems.BarleyGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RyeDough, 2), new Object[] {TFCItems.RyeGround,TFCItems.RyeGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.OatDough, 2), new Object[] {TFCItems.OatGround,TFCItems.OatGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RiceDough, 2), new Object[] {TFCItems.RiceGround,TFCItems.RiceGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CornmealDough, 2), new Object[] {TFCItems.CornmealGround,TFCItems.CornmealGround, TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.WheatDough, 2), new Object[] {TFCItems.WheatGround,TFCItems.WheatGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.BarleyDough, 2), new Object[] {TFCItems.BarleyGround,TFCItems.BarleyGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RyeDough, 2), new Object[] {TFCItems.RyeGround,TFCItems.RyeGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.OatDough, 2), new Object[] {TFCItems.OatGround,TFCItems.OatGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.RiceDough, 2), new Object[] {TFCItems.RiceGround,TFCItems.RiceGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.CornmealDough, 2), new Object[] {TFCItems.CornmealGround,TFCItems.CornmealGround, TFCItems.RedSteelBucketWater});
	}

	public static void registerKilnRecipes()
	{
		KilnCraftingManager manager = KilnCraftingManager.getInstance();

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.CeramicMold,1,0),
						0, 
						new ItemStack(TFCItems.CeramicMold,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.SpindleHead,1,0),
						0, 
						new ItemStack(TFCItems.SpindleHead,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.PotteryJug,1,0),
						0, 
						new ItemStack(TFCItems.PotteryJug,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.PotterySmallVessel,1,0),
						0, 
						new ItemStack(TFCItems.PotterySmallVessel,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.PotteryPot,1,0),
						0, 
						new ItemStack(TFCItems.PotteryPot,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.PotteryLargeVessel,1,0),
						0, 
						new ItemStack(TFCItems.PotteryLargeVessel,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldAxe,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldAxe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldAxe,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldAxe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldChisel,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldChisel,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldHammer,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldHammer,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldHoe,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldHoe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldKnife,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldKnife,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldMace,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldMace,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldPick,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldPick,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldProPick,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldProPick,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldSaw,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldSaw,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldScythe,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldScythe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldShovel,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldShovel,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldSword,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldSword,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ClayMoldJavelin,1,0),
						0, 
						new ItemStack(TFCItems.ClayMoldJavelin,1,1)));
	}

	public static int valueOfString(String s)
	{
		if(s.length() > 0)
		{
			byte[] b = s.getBytes();
			int out = 0;
			for(int i = 0; i < b.length; i++)
			{
				out+=b[i];
			}
			return out;
		} else {
			return 0;
		}
	}

	public static void RemoveRecipe(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof IRecipe) {
				IRecipe recipe = tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();

				if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
					recipes.remove(i--);
				}
			}
		}
	}
}
