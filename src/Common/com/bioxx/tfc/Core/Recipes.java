package com.bioxx.tfc.Core;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.api.TFCCrafting;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;
import com.bioxx.tfc.api.Crafting.KilnCraftingManager;
import com.bioxx.tfc.api.Crafting.KilnRecipe;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Crafting.QuernManager;
import com.bioxx.tfc.api.Crafting.QuernRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

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

	/**
	 * Minecraft changed from -1 to Short.MAX_VALUE in 1.5 release for the "block wildcard". Use this in case it
	 * changes again.
	 */
	public static final int WILDCARD = Short.MAX_VALUE;

	public static void registerRecipes()
	{
		TEBarrel.registerRecipes();
		TELoom.registerRecipes();
		Item[] Ingots = {TFCItems.BismuthIngot, TFCItems.BismuthBronzeIngot,TFCItems.BlackBronzeIngot,
				TFCItems.BlackSteelIngot,TFCItems.BlueSteelIngot,TFCItems.BrassIngot,TFCItems.BronzeIngot,
				TFCItems.BronzeIngot,TFCItems.CopperIngot,TFCItems.GoldIngot,TFCItems.WroughtIronIngot,TFCItems.LeadIngot,
				TFCItems.NickelIngot,TFCItems.PigIronIngot,TFCItems.PlatinumIngot,TFCItems.RedSteelIngot,
				TFCItems.RoseGoldIngot,TFCItems.SilverIngot,TFCItems.SteelIngot,TFCItems.SterlingSilverIngot,
				TFCItems.TinIngot,TFCItems.ZincIngot};

		// Remove Vanilla recipes before adding TFC recipes for oredict compatibility
		VanillaRecipes();

		//Wood Specific Stuff
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			GameRegistry.addRecipe(new ItemStack(Doors[i]), new Object[] { "WW", "WW", "WW", Character.valueOf('W'), new ItemStack(TFCItems.SinglePlank, 1, i) });
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Chest, 1, i), new Object[] { "###", "# #", "###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, i) }));

			int l = i%16;
			if(i==l)
			{
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.SinglePlank, 8, i), new Object[] { new ItemStack(TFCItems.Logs, 1, i), "itemSaw" }));
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.SinglePlank, 4, i), new Object[] { new ItemStack(TFCBlocks.Planks, 1, i), "itemSaw" }));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.WoodSupportV, 8, i), new Object[] { "A2", " 2", Character.valueOf('2'), new ItemStack(TFCItems.Logs, 1, i), Character.valueOf('A'), "itemSaw" }));

				GameRegistry.addRecipe(new ItemStack(TFCBlocks.Planks, 1, i), new Object[] {"11","11", Character.valueOf('1'), new ItemStack(TFCItems.SinglePlank, 1, i)});
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.ToolRack, 1, i), new Object[] { "###", "   ", "###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, i) });
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.Barrel, 1, i), new Object[] { "# #", "# #", "###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, i) });

				GameRegistry.addRecipe(new ItemStack(TFCBlocks.Fence, 6, i), new Object[] { "LPL", "LPL", Character.valueOf('L'), new ItemStack(TFCItems.Logs, 1, i), Character.valueOf('P'), new ItemStack(TFCItems.SinglePlank, 1, i) });
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.FenceGate, 2, i), new Object[] { "LPL", "LPL", Character.valueOf('L'), new ItemStack(TFCItems.SinglePlank, 1, i), Character.valueOf('P'), new ItemStack(TFCBlocks.Planks, 1, i) });
				/*GameRegistry.addRecipe(new ItemStack(TFCBlocks.ArmourStand,1,i), new Object[] { "###"," # ","%%%",Character.valueOf('#'),new ItemStack(TFCItems.SinglePlank,1,j),Character.valueOf('%'),
				new ItemStack(TFCBlocks.Planks,1,i)});*/

				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Loom, 1, i), new Object[] { "LLL", "LSL", "L L", Character.valueOf('L'), new ItemStack(TFCItems.SinglePlank, 1, i), Character.valueOf('S'), "stickWood" }));
			}
			else if(i/16 == 1)
			{
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.SinglePlank, 8, i), new Object[] { new ItemStack(TFCItems.Logs, 1, i), "itemSaw" }));
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.SinglePlank, 4, i), new Object[] { new ItemStack(TFCBlocks.Planks2, 1, l), "itemSaw" }));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.WoodSupportV2, 8, l), new Object[] { "A2", " 2", Character.valueOf('2'), new ItemStack(TFCItems.Logs, 1, i), Character.valueOf('A'), "itemSaw" }));;

				GameRegistry.addRecipe(new ItemStack(TFCBlocks.Planks2, 1, l), new Object[] {"11","11", Character.valueOf('1'), new ItemStack(TFCItems.SinglePlank, 1, i)});
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.ToolRack2, 1, l), new Object[] { "###", "   ", "###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, i) });

				GameRegistry.addRecipe(new ItemStack(TFCBlocks.Fence2, 6, l), new Object[] { "LPL", "LPL", Character.valueOf('L'), new ItemStack(TFCItems.Logs, 1, i), Character.valueOf('P'), new ItemStack(TFCItems.SinglePlank, 1, i) });
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.FenceGate2, 2, l), new Object[] { "LPL", "LPL", Character.valueOf('L'), new ItemStack(TFCItems.SinglePlank, 1, i), Character.valueOf('P'), new ItemStack(TFCBlocks.Planks2, 1, l) });

				/*GameRegistry.addRecipe(new ItemStack(TFCBlocks.ArmourStand2,1,l), new Object[] { "###"," # ","%%%",Character.valueOf('#'),new ItemStack(TFCItems.SinglePlank,1,i),Character.valueOf('%'),
				new ItemStack(TFCBlocks.Planks2,1,l)});*/

				GameRegistry.addRecipe(new ItemStack(TFCBlocks.Barrel, 1, i), new Object[] { "# #", "# #", "###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, i) });
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Loom, 1, i), new Object[] { "LLL", "LSL", "L L", Character.valueOf('L'), new ItemStack(TFCItems.SinglePlank, 1, i), Character.valueOf('S'), "stickWood" }));
			}
		}

		//Wood Mix & Match
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.bowl, 4, 0), new Object[] { "logWood", "itemKnife" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SluiceItem, 1), new Object[] { "  1", " 12", "122", Character.valueOf('1'), "stickWood", Character.valueOf('2'), "woodLumber" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.NestBox,1), new Object[] {"S S","PSP","PPP",Character.valueOf('S'), new ItemStack(TFCItems.Straw,1),Character.valueOf('P'), "woodLumber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WoodenBucketEmpty, 1), new Object[] { "w w", "w w", " w ", Character.valueOf('w'), "woodLumber" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.trapdoor, 1, 0), new Object[] { "###", "###", Character.valueOf('#'), "woodLumber" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.sign, 1, 0), new Object[] { "###", "###", " 1 ", Character.valueOf('#'), "woodLumber", Character.valueOf('1'), "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.ButtonWood, 1), new Object[] { "#", "#", Character.valueOf('#'), "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.bed, 1), new Object[] { "PPP", "QQQ", Character.valueOf('P'), "materialCloth", Character.valueOf('Q'), "woodLumber" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Workbench, 1), new Object[] { "PP", "PP", Character.valueOf('P'), "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Bellows, 1, 0), new Object[] { "###", "???", "###", Character.valueOf('#'), "woodLumber", Character.valueOf('?'), "materialLeather" }));

		//Hide & Sheepskin
		for (int k = 0; k < 3; k++)
		{
			//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.ScrapedHide, 1, k), new Object[] { new ItemStack(TFCItems.SoakedHide, 1, k), "itemKnife" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.Wool, 1 + k, 0), new Object[] { new ItemStack(TFCItems.SheepSkin, 1, k), "itemKnife" }));
		}

		//Dyes
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Dye,1,4),new Object[]{new ItemStack(TFCItems.Powder,1,6)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Dye,1,2),new Object[]{new ItemStack(TFCItems.Powder,1,8)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Dye,1,1),new Object[]{new ItemStack(TFCItems.Powder,1,5)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Dye,1,11),new Object[]{new ItemStack(TFCItems.Powder,1,7)});
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.Dye,1,12),new Object[]{new ItemStack(TFCItems.Powder,1,8),new ItemStack(TFCItems.Powder,1,0), "blockSand"}));

		//Mortar
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.Mortar,16), new Object[]{"blockSand", new ItemStack(TFCItems.Powder, 1, 0), "itemBucketWater"}));

		//Flux Powder
		for (int i = 0; i < Global.STONE_FLUXINDEX.length; i++)
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.Powder, 2, 0), new Object[] { new ItemStack(TFCItems.LooseRock, 1, Global.STONE_FLUXINDEX[i]), "itemHammer" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.Powder, 6, 0), new Object[] { new ItemStack(TFCItems.OreChunk, 1, 32), "itemHammer" }));

		//Devices
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil, 1, 1), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil, 1, 2), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleBronze"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil, 1, 3), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleWroughtIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil, 1, 4), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleSteel"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil, 1, 5), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleBlackSteel"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil, 1, 6), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleBlueSteel"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil, 1, 7), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleRedSteel"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil2, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleRoseGold"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil2, 1, 1), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleBismuthBronze"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Anvil2, 1, 2), new Object[] { "###"," # ","###", Character.valueOf('#'), "ingotDoubleBlackBronze"}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.EarlyBloomery, 1), new Object[] { "PPP","P P","PPP", Character.valueOf('P'), "sheetDoubleAnyBronze"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.BlastFurnace, 1), new Object[]
				{ "PPP", "PCP", "PPP", Character.valueOf('P'), "sheetDoubleWroughtIron", Character.valueOf('C'), new ItemStack(TFCBlocks.Crucible, 1) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.SpawnMeter, 1), new Object[] { "PPP", "GKG", "PPP", Character.valueOf('P'), "stoneSmooth", Character.valueOf('K'), "gemChipped", Character.valueOf('G'), new ItemStack(Blocks.glass, 1) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.Quern, 1), new Object[] { "PPP", Character.valueOf('P'), "stoneSmooth" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.Quern, 1), new Object[] { "  W", "PPP", Character.valueOf('P'), "stone", Character.valueOf('W'), "stickWood" }));

		//Fire Clay
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ClayBall, 1, 1), new Object[] { "PXP", "XCX", "PXP", Character.valueOf('P'), new ItemStack(TFCItems.Powder, 1, 1), Character.valueOf('X'), new ItemStack(TFCItems.Powder, 1, 2), Character.valueOf('C'), "lumpClay" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ClayBall, 1, 1), new Object[] { "PXP", "XCX", "PXP", Character.valueOf('P'), new ItemStack(TFCItems.Powder, 1, 2), Character.valueOf('X'), new ItemStack(TFCItems.Powder, 1, 1), Character.valueOf('C'), "lumpClay" }));

		GameRegistry.addRecipe(new ItemStack(TFCItems.FireBrick, 2, 0), new Object[] { "PP", "PP", Character.valueOf('P'), new ItemStack(TFCItems.ClayBall, 1, 1) });

		GameRegistry.addRecipe(new ItemStack(TFCBlocks.FireBrick, 2, 0), new Object[] { "PXP", "XPX", "PXP", Character.valueOf('P'), new ItemStack(TFCItems.FireBrick, 1, 1), Character.valueOf('X'), new ItemStack(TFCItems.Mortar, 1) });

		//Straw & Thatch
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.Thatch, 1), new Object[] { "PP", "PP", Character.valueOf('P'), new ItemStack(TFCItems.Straw, 1) });
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Straw, 4), new Object[] { new ItemStack(TFCBlocks.Thatch, 1) });

		//Coal
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Coal, 9), new Object[] { new ItemStack(Blocks.coal_block) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.coal_block, 1), new Object[] { "###", "###", "###", Character.valueOf('#'), "fuelCoal" }));

		//Misc Items
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.Arrow, 8), "itemRock", "stickWood", new ItemStack(Items.feather, 1, 32767)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 2, 0), new Object[] { "fuelCoal", new ItemStack(TFCItems.Powder, 1, 4), new ItemStack(TFCItems.Powder, 1, 3) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame, 1), new Object[] { "###", "#$#", "###", Character.valueOf('#'), "stickWood", Character.valueOf('$'), "materialLeather" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.painting, 1), new Object[] { "###", "#$#", "###", Character.valueOf('#'), "stickWood", Character.valueOf('$'), "materialCloth" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.carpet, 2, 0), new Object[] { "$$", Character.valueOf('$'), "materialCloth" }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.lit_pumpkin, 1), "blockTorch", "blockPumpkin"));

		for (int i = 0; i < 16; i++)
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.carpet, 1, i), new Object[] { new ItemStack(TFCItems.Dye, 1, 15 - i), new ItemStack(Blocks.carpet, 1, 32767) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.rail, 64), new Object[] { "PsP","PsP", Character.valueOf('P'), "ingotIron", Character.valueOf('s'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.golden_rail, 64), new Object[] { " r ","PsP","PsP", Character.valueOf('P'), "ingotGold", Character.valueOf('s'), "stickWood", Character.valueOf('r'), Items.redstone}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.minecart, 1), new Object[] { "P P","PPP", Character.valueOf('P'), "sheetWroughtIron"}));
		//GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.minecartCrate, 1), new Object[] { new ItemStack(TFCBlocks.Chest), new ItemStack(Items.minecart)});

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.lever, 1), new Object[] { "P","H", Character.valueOf('P'), "stickWood", Character.valueOf('H'), "itemRock"}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.book, 1), new Object[]
				{ new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), "materialLeather" }));
		//GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.writabeBookTFC, 1), new Object[]{new ItemStack(Items.book, 1)});

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.WoolYarn, 8), new Object[]
				{ "materialWool", new ItemStack(TFCItems.Spindle, 1, 32767) }));

		//Markings & Blueprint
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.Ink, 16, 0), new Object[] { "2", Character.valueOf('2'), "dyeBlack" }));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Blueprint, 1), new Object[] { new ItemStack(TFCItems.Ink, 3, 32767), new ItemStack(Items.paper, 1) });

		//Stone Stuff
		for (int j = 0; j < Global.STONE_IGIN.length; j++)
		{
			//Bricks
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneIgInBrick, 4, j), new Object[] { "PXP", "XPX", "PXP", Character.valueOf('P'), new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_IGIN_START), Character.valueOf('X'), new ItemStack(TFCItems.Mortar, 1) });
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_IGIN_START), new Object[] { new ItemStack(TFCItems.LooseRock, 1, j + Global.STONE_IGIN_START), "itemChisel" }));

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

		for (int j = 0; j < Global.STONE_SED.length; j++)
		{
			//Bricks
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneSedBrick, 4, j), new Object[] { "PXP", "XPX", "PXP", Character.valueOf('P'), new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_SED_START), Character.valueOf('X'), new ItemStack(TFCItems.Mortar, 1) });
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_SED_START), new Object[] { new ItemStack(TFCItems.LooseRock, 1, j + Global.STONE_SED_START), "itemChisel" }));

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

		for (int j = 0; j < Global.STONE_IGEX.length; j++)
		{
			//Bricks
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneIgExBrick, 4, j), new Object[] { "PXP", "XPX", "PXP", Character.valueOf('P'), new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_IGEX_START), Character.valueOf('X'), new ItemStack(TFCItems.Mortar, 1) });
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_IGEX_START), new Object[] { new ItemStack(TFCItems.LooseRock, 1, j + Global.STONE_IGEX_START), "itemChisel" }));

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

		for (int j = 0; j < Global.STONE_MM.length; j++)
		{
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.StoneMMBrick, 4, j), new Object[] { "PXP", "XPX", "PXP", Character.valueOf('P'), new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_MM_START), Character.valueOf('X'), new ItemStack(TFCItems.Mortar, 1) });
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.StoneBrick, 1, j + Global.STONE_MM_START), new Object[] { new ItemStack(TFCItems.LooseRock, 1, j + Global.STONE_MM_START), "itemChisel" }));

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

		RegisterToolRecipes();
		registerFoodRecipes();
		registerKilnRecipes();
		registerToolMolds();
		registerOreDict();
		registerQuernRecipes();
	}

	private static void registerOreDict()
	{
		//Wood & Trees
		OreDictionary.registerOre("logWood", new ItemStack(TFCItems.Logs, 1, WILDCARD));

		OreDictionary.registerOre("plankWood", new ItemStack(TFCBlocks.Planks, 1, WILDCARD));
		OreDictionary.registerOre("plankWood", new ItemStack(TFCBlocks.Planks2, 1, WILDCARD));

		OreDictionary.registerOre("woodLumber", new ItemStack(TFCItems.SinglePlank, 1, WILDCARD));

		OreDictionary.registerOre("stickWood", new ItemStack(TFCItems.Stick));

		OreDictionary.registerOre("treeSapling", new ItemStack(TFCBlocks.Sapling, 1, WILDCARD));
		OreDictionary.registerOre("treeSapling", new ItemStack(TFCBlocks.Sapling2, 1, WILDCARD));

		OreDictionary.registerOre("treeLeaves", new ItemStack(TFCBlocks.Leaves, 1, WILDCARD));
		OreDictionary.registerOre("treeLeaves", new ItemStack(TFCBlocks.Leaves2, 1, WILDCARD));

		//Ores
		OreDictionary.registerOre("oreCopper", new ItemStack(TFCItems.OreChunk, 1, 0)); //Native Copper
		OreDictionary.registerOre("oreCopper", new ItemStack(TFCItems.OreChunk, 1, 9)); //Malachite
		OreDictionary.registerOre("oreCopper", new ItemStack(TFCItems.OreChunk, 1, 13)); //Tetrahedrite		
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.SmallOreChunk, 1, 0)); //Native Copper
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.SmallOreChunk, 1, 9)); //Malachite
		OreDictionary.registerOre("oreSmallCopper", new ItemStack(TFCItems.SmallOreChunk, 1, 13)); //Tetrahedrite		
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.OreChunk, 1, 35)); //Native Copper
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.OreChunk, 1, 44)); //Malachite
		OreDictionary.registerOre("oreRichCopper", new ItemStack(TFCItems.OreChunk, 1, 48)); //Tetrahedrite		
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.OreChunk, 1, 49)); //Native Copper
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.OreChunk, 1, 58)); //Malachite
		OreDictionary.registerOre("orePoorCopper", new ItemStack(TFCItems.OreChunk, 1, 62)); //Tetrahedrite

		OreDictionary.registerOre("oreGold", new ItemStack(TFCItems.OreChunk, 1, 1)); //Native Gold
		OreDictionary.registerOre("oreSmallGold", new ItemStack(TFCItems.SmallOreChunk, 1, 1)); //Native Gold
		OreDictionary.registerOre("oreRichGold", new ItemStack(TFCItems.OreChunk, 1, 36)); //Native Gold
		OreDictionary.registerOre("orePoorGold", new ItemStack(TFCItems.OreChunk, 1, 50)); //Native Gold

		OreDictionary.registerOre("orePlatinum", new ItemStack(TFCItems.OreChunk, 1, 2)); //Native Platinum
		OreDictionary.registerOre("oreSmallPlatinum", new ItemStack(TFCItems.SmallOreChunk, 1, 2)); //Native Platinum
		OreDictionary.registerOre("oreRichPlatinum", new ItemStack(TFCItems.OreChunk, 1, 37)); //Native Platinum
		OreDictionary.registerOre("orePoorPlatinum", new ItemStack(TFCItems.OreChunk, 1, 51)); //Native Platinum

		OreDictionary.registerOre("oreIron", new ItemStack(TFCItems.OreChunk, 1, 3)); //Hematite
		OreDictionary.registerOre("oreIron", new ItemStack(TFCItems.OreChunk, 1, 10)); //Magnetite
		OreDictionary.registerOre("oreIron", new ItemStack(TFCItems.OreChunk, 1, 11)); //Limonite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.SmallOreChunk, 1, 3)); //Hematite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.SmallOreChunk, 1, 10)); //Magnetite
		OreDictionary.registerOre("oreSmallIron", new ItemStack(TFCItems.SmallOreChunk, 1, 11)); //Limonite		
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.OreChunk, 1, 38)); //Hematite
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.OreChunk, 1, 45)); //Magnetite
		OreDictionary.registerOre("oreRichIron", new ItemStack(TFCItems.OreChunk, 1, 46)); //Limonite		
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.OreChunk, 1, 52)); //Hematite
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.OreChunk, 1, 59)); //Magnetite
		OreDictionary.registerOre("orePoorIron", new ItemStack(TFCItems.OreChunk, 1, 60)); //Limonite

		OreDictionary.registerOre("oreSilver", new ItemStack(TFCItems.OreChunk, 1, 4)); //Native Silver
		OreDictionary.registerOre("oreSmallSilver", new ItemStack(TFCItems.SmallOreChunk, 1, 4)); //Native Silver
		OreDictionary.registerOre("oreRichSilver", new ItemStack(TFCItems.OreChunk, 1, 39)); //Native Silver
		OreDictionary.registerOre("orePoorSilver", new ItemStack(TFCItems.OreChunk, 1, 53)); //Native Silver

		OreDictionary.registerOre("oreTin", new ItemStack(TFCItems.OreChunk, 1, 5)); //Cassiterite
		OreDictionary.registerOre("oreSmallTin", new ItemStack(TFCItems.SmallOreChunk, 1, 5)); //Cassiterite
		OreDictionary.registerOre("oreRichTin", new ItemStack(TFCItems.OreChunk, 1, 40)); //Cassiterite
		OreDictionary.registerOre("orePoorTin", new ItemStack(TFCItems.OreChunk, 1, 54)); //Cassiterite

		OreDictionary.registerOre("oreLead", new ItemStack(TFCItems.OreChunk, 1, 6)); //Galena
		OreDictionary.registerOre("oreSmallLead", new ItemStack(TFCItems.SmallOreChunk, 1, 6)); //Galena
		OreDictionary.registerOre("oreRichLead", new ItemStack(TFCItems.OreChunk, 1, 41)); //Galena
		OreDictionary.registerOre("orePoorLead", new ItemStack(TFCItems.OreChunk, 1, 55)); //Galena

		OreDictionary.registerOre("oreBismuth", new ItemStack(TFCItems.OreChunk, 1, 7)); //Bismuthinite
		OreDictionary.registerOre("oreSmallBismuth", new ItemStack(TFCItems.SmallOreChunk, 1, 7)); //Bismuthinite
		OreDictionary.registerOre("oreRichBismuth", new ItemStack(TFCItems.OreChunk, 1, 42)); //Bismuthinite
		OreDictionary.registerOre("orePoorBismuth", new ItemStack(TFCItems.OreChunk, 1, 56)); //Bismuthinite

		OreDictionary.registerOre("oreNickel", new ItemStack(TFCItems.OreChunk, 1, 8)); //Garnierite
		OreDictionary.registerOre("oreSmallNickel", new ItemStack(TFCItems.SmallOreChunk, 1, 8)); //Garnierite
		OreDictionary.registerOre("oreRichNickel", new ItemStack(TFCItems.OreChunk, 1, 43)); //Garnierite
		OreDictionary.registerOre("orePoorNickel", new ItemStack(TFCItems.OreChunk, 1, 57)); //Garnierite

		OreDictionary.registerOre("oreZinc", new ItemStack(TFCItems.OreChunk, 1, 12)); //Sphalerite
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
		OreDictionary.registerOre("sheetBismuth", new ItemStack(TFCItems.BismuthSheet));
		OreDictionary.registerOre("sheetTin", new ItemStack(TFCItems.TinSheet));
		OreDictionary.registerOre("sheetZinc", new ItemStack(TFCItems.ZincSheet));
		OreDictionary.registerOre("sheetCopper", new ItemStack(TFCItems.CopperSheet));
		OreDictionary.registerOre("sheetBronze", new ItemStack(TFCItems.BronzeSheet));
		OreDictionary.registerOre("sheetBismuthBronze", new ItemStack(TFCItems.BismuthBronzeSheet));
		OreDictionary.registerOre("sheetBlackBronze", new ItemStack(TFCItems.BlackBronzeSheet));
		OreDictionary.registerOre("sheetBrass", new ItemStack(TFCItems.BrassSheet));
		OreDictionary.registerOre("sheetLead", new ItemStack(TFCItems.LeadSheet));
		OreDictionary.registerOre("sheetGold", new ItemStack(TFCItems.GoldSheet));
		OreDictionary.registerOre("sheetRoseGold", new ItemStack(TFCItems.RoseGoldSheet));
		OreDictionary.registerOre("sheetSilver", new ItemStack(TFCItems.SilverSheet));
		OreDictionary.registerOre("sheetSterlingSilver", new ItemStack(TFCItems.SterlingSilverSheet));
		OreDictionary.registerOre("sheetPlatinum", new ItemStack(TFCItems.PlatinumSheet));
		OreDictionary.registerOre("sheetWroughtIron", new ItemStack(TFCItems.WroughtIronSheet));
		OreDictionary.registerOre("sheetNickel", new ItemStack(TFCItems.NickelSheet));
		OreDictionary.registerOre("sheetPigIron", new ItemStack(TFCItems.PigIronSheet));
		OreDictionary.registerOre("sheetSteel", new ItemStack(TFCItems.SteelSheet));
		OreDictionary.registerOre("sheetBlackSteel", new ItemStack(TFCItems.BlackSteelSheet));
		OreDictionary.registerOre("sheetRedSteel", new ItemStack(TFCItems.RedSteelSheet));
		OreDictionary.registerOre("sheetBlueSteel", new ItemStack(TFCItems.BlueSteelSheet));

		//Double Sheets
		OreDictionary.registerOre("sheetDoubleBismuth", new ItemStack(TFCItems.BismuthSheet2x));
		OreDictionary.registerOre("sheetDoubleTin", new ItemStack(TFCItems.TinSheet2x));
		OreDictionary.registerOre("sheetDoubleZinc", new ItemStack(TFCItems.ZincSheet2x));
		OreDictionary.registerOre("sheetDoubleCopper", new ItemStack(TFCItems.CopperSheet2x));
		OreDictionary.registerOre("sheetDoubleBronze", new ItemStack(TFCItems.BronzeSheet2x));
		OreDictionary.registerOre("sheetDoubleBismuthBronze", new ItemStack(TFCItems.BismuthBronzeSheet2x));
		OreDictionary.registerOre("sheetDoubleBlackBronze", new ItemStack(TFCItems.BlackBronzeSheet2x));
		OreDictionary.registerOre("sheetDoubleBrass", new ItemStack(TFCItems.BrassSheet2x));
		OreDictionary.registerOre("sheetDoubleLead", new ItemStack(TFCItems.LeadSheet2x));
		OreDictionary.registerOre("sheetDoubleGold", new ItemStack(TFCItems.GoldSheet2x));
		OreDictionary.registerOre("sheetDoubleRoseGold", new ItemStack(TFCItems.RoseGoldSheet2x));
		OreDictionary.registerOre("sheetDoubleSilver", new ItemStack(TFCItems.SilverSheet2x));
		OreDictionary.registerOre("sheetDoubleSterlingSilver", new ItemStack(TFCItems.SterlingSilverSheet2x));
		OreDictionary.registerOre("sheetDoublePlatinum", new ItemStack(TFCItems.PlatinumSheet2x));
		OreDictionary.registerOre("sheetDoubleWroughtIron", new ItemStack(TFCItems.WroughtIronSheet2x));
		OreDictionary.registerOre("sheetDoubleNickel", new ItemStack(TFCItems.NickelSheet2x));
		OreDictionary.registerOre("sheetDoublePigIron", new ItemStack(TFCItems.PigIronSheet2x));
		OreDictionary.registerOre("sheetDoubleSteel", new ItemStack(TFCItems.SteelSheet2x));
		OreDictionary.registerOre("sheetDoubleBlackSteel", new ItemStack(TFCItems.BlackSteelSheet2x));
		OreDictionary.registerOre("sheetDoubleRedSteel", new ItemStack(TFCItems.RedSteelSheet2x));
		OreDictionary.registerOre("sheetDoubleBlueSteel", new ItemStack(TFCItems.BlueSteelSheet2x));

		OreDictionary.registerOre("sheetDoubleAnyBronze", new ItemStack(TFCItems.BronzeSheet2x));
		OreDictionary.registerOre("sheetDoubleAnyBronze", new ItemStack(TFCItems.BismuthBronzeSheet2x));
		OreDictionary.registerOre("sheetDoubleAnyBronze", new ItemStack(TFCItems.BlackBronzeSheet2x));

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
		OreDictionary.registerOre("fuelCoal", new ItemStack(Items.coal, 1, 0));
		OreDictionary.registerOre("fuelCoal", new ItemStack(TFCItems.Coal, 1, 0));
		OreDictionary.registerOre("fuelCharcoal", new ItemStack(Items.coal, 1, 1));
		OreDictionary.registerOre("fuelCharcoal", new ItemStack(TFCItems.Coal, 1, 1));

		//Stone
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneIgEx, 1, WILDCARD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneIgIn, 1, WILDCARD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneMM, 1, WILDCARD));
		OreDictionary.registerOre("stone", new ItemStack(TFCBlocks.StoneSed, 1, WILDCARD));

		//Cobblestone
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneIgExCobble, 1, WILDCARD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneIgInCobble, 1, WILDCARD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneMMCobble, 1, WILDCARD));
		OreDictionary.registerOre("cobblestone", new ItemStack(TFCBlocks.StoneSedCobble, 1, WILDCARD));

		//Stone Bricks
		OreDictionary.registerOre("stoneBrick", new ItemStack(Blocks.stonebrick));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.StoneIgExBrick, 1, WILDCARD));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.StoneIgInBrick, 1, WILDCARD));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.StoneMMBrick, 1, WILDCARD));
		OreDictionary.registerOre("stoneBrick", new ItemStack(TFCBlocks.StoneSedBrick, 1, WILDCARD));

		//Smooth Stone
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneIgExSmooth, 1, WILDCARD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneIgInSmooth, 1, WILDCARD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneMMSmooth, 1, WILDCARD));
		OreDictionary.registerOre("stoneSmooth", new ItemStack(TFCBlocks.StoneSedSmooth, 1, WILDCARD));

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
		OreDictionary.registerOre("materialWool", new ItemStack(TFCItems.Wool));

		//Tools
		for (Item chisel : Chisels)
			OreDictionary.registerOre("itemChisel", new ItemStack(chisel, 1, WILDCARD));

		for (Item hammer : Hammers)
			OreDictionary.registerOre("itemHammer", new ItemStack(hammer, 1, WILDCARD));

		for (Item knife : Knives)
			OreDictionary.registerOre("itemKnife", new ItemStack(knife, 1, WILDCARD));

		for (Item saw : Saws)
			OreDictionary.registerOre("itemSaw", new ItemStack(saw, 1, WILDCARD));

		//Miscellaneous Items
		OreDictionary.registerOre("lumpClay", new ItemStack(Items.clay_ball));
		OreDictionary.registerOre("lumpClay", new ItemStack(TFCItems.ClayBall, 1, 0));

		OreDictionary.registerOre("itemArrow", new ItemStack(Items.arrow));
		OreDictionary.registerOre("itemArrow", new ItemStack(TFCItems.Arrow));

		OreDictionary.registerOre("itemReed", new ItemStack(Items.reeds));
		OreDictionary.registerOre("itemReed", new ItemStack(TFCItems.Reeds));

		OreDictionary.registerOre("itemRock", new ItemStack(TFCItems.LooseRock, 1, WILDCARD));

		OreDictionary.registerOre("itemBucketWater", new ItemStack(Items.water_bucket));
		OreDictionary.registerOre("itemBucketWater", new ItemStack(TFCItems.WoodenBucketWater, 1, WILDCARD));
		OreDictionary.registerOre("itemBucketWater", new ItemStack(TFCItems.RedSteelBucketWater, 1, WILDCARD));
		OreDictionary.registerOre("itemBucketWater", new ItemStack(TFCItems.WoodenBucketSaltWater, 1, WILDCARD));
		OreDictionary.registerOre("itemBucketWater", new ItemStack(TFCItems.RedSteelBucketSaltWater, 1, WILDCARD));

		OreDictionary.registerOre("itemBucketFreshWater", new ItemStack(TFCItems.WoodenBucketWater, 1, WILDCARD));
		OreDictionary.registerOre("itemBucketFreshWater", new ItemStack(TFCItems.RedSteelBucketWater, 1, WILDCARD));

		OreDictionary.registerOre("itemBucketSaltWater", new ItemStack(TFCItems.WoodenBucketSaltWater, 1, WILDCARD));
		OreDictionary.registerOre("itemBucketSaltWater", new ItemStack(TFCItems.RedSteelBucketSaltWater, 1, WILDCARD));

		//Miscellaneous Blocks
		OreDictionary.registerOre("blockSand", new ItemStack(Blocks.sand));
		OreDictionary.registerOre("blockSand", new ItemStack(TFCBlocks.Sand, 1, WILDCARD));
		OreDictionary.registerOre("blockSand", new ItemStack(TFCBlocks.Sand2, 1, WILDCARD));

		OreDictionary.registerOre("blockDirt", new ItemStack(Blocks.dirt));
		OreDictionary.registerOre("blockDirt", new ItemStack(TFCBlocks.Dirt, 1, WILDCARD));
		OreDictionary.registerOre("blockDirt", new ItemStack(TFCBlocks.Dirt2, 1, WILDCARD));

		OreDictionary.registerOre("blockTorch", new ItemStack(Blocks.torch));
		OreDictionary.registerOre("blockTorch", new ItemStack(TFCBlocks.Torch));

		OreDictionary.registerOre("blockPumpkin", new ItemStack(Blocks.pumpkin));
		OreDictionary.registerOre("blockPumpkin", new ItemStack(TFCBlocks.Pumpkin));
	}

	private static ItemStack checkMelted(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemp(is) > TFC_ItemHeat.IsCookable(is))
			return null;
		return is;
	}

	private static void VanillaRecipes()
	{
		// With oredict in place, we MUST remove the vanilla crafting table.
		// Otherwise TFC can not use the 4 planks recipe to create its own crafting upgrade.
		RemoveRecipe(new ItemStack(Blocks.crafting_table));
		// Other Conflicting Recipes
		RemoveRecipe(new ItemStack(Items.bow));
		RemoveRecipe(new ItemStack(Items.fishing_rod));
		RemoveRecipe(new ItemStack(Blocks.wooden_button));
		RemoveRecipe(new ItemStack(Items.flint_and_steel));
		RemoveRecipe(new ItemStack(Items.coal, 9));

		//Recipe Configuration
		if (TFCCrafting.anvilRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.anvil));

		if (TFCCrafting.arrowsRecipe == false)
			RemoveRecipe(new ItemStack(Items.arrow, 4));

		if (TFCCrafting.bedRecipe == false)
			RemoveRecipe(new ItemStack(Items.bed));

		if (TFCCrafting.bonemealRecipe == false)
			RemoveRecipe(new ItemStack(Items.dye, 3, 15));

		if (TFCCrafting.bowlRecipe == false)
			RemoveRecipe(new ItemStack(Items.bowl, 4));

		if (TFCCrafting.brewingRecipe == false)
			RemoveRecipe(new ItemStack(Items.brewing_stand));

		if (TFCCrafting.bucketRecipe == false)
			RemoveRecipe(new ItemStack(Items.bucket));

		if (TFCCrafting.cauldronRecipe == false)
			RemoveRecipe(new ItemStack(Items.cauldron));

		if (TFCCrafting.chestRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.chest));

		if (TFCCrafting.clockRecipe == false)
			RemoveRecipe(new ItemStack(Items.clock));

		if (TFCCrafting.compassRecipe == false)
			RemoveRecipe(new ItemStack(Items.compass));

		if (TFCCrafting.dandelionYellowRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.dye, 1, 11));
			RemoveRecipe(new ItemStack(Items.dye, 2, 11));
		}

		if (TFCCrafting.diamondArmorRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.diamond_helmet));
			RemoveRecipe(new ItemStack(Items.diamond_chestplate));
			RemoveRecipe(new ItemStack(Items.diamond_leggings));
			RemoveRecipe(new ItemStack(Items.diamond_boots));
		}

		if (TFCCrafting.diamondBlockRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.diamond_block));

		if (TFCCrafting.diamondToolsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.diamond_pickaxe));
			RemoveRecipe(new ItemStack(Items.diamond_axe));
			RemoveRecipe(new ItemStack(Items.diamond_shovel));
			RemoveRecipe(new ItemStack(Items.diamond_hoe));
			RemoveRecipe(new ItemStack(Items.diamond_sword));
		}

		if (TFCCrafting.dispenserRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.dispenser));

		if (TFCCrafting.dropperRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.dropper));

		if (TFCCrafting.enchantTableRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.enchanting_table));

		if (TFCCrafting.fenceGateRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.fence_gate));

		if (TFCCrafting.fenceRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.fence, 2));

		if (TFCCrafting.furnaceRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.furnace));

		if (TFCCrafting.goldAppleRecipe == false)
			RemoveRecipe(new ItemStack(Items.golden_apple));

		if (TFCCrafting.goldArmorRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.golden_helmet));
			RemoveRecipe(new ItemStack(Items.golden_chestplate));
			RemoveRecipe(new ItemStack(Items.golden_leggings));
			RemoveRecipe(new ItemStack(Items.golden_boots));
		}

		if (TFCCrafting.goldBlockRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.gold_block));

		if (TFCCrafting.goldNuggetRecipe == false)
			RemoveRecipe(new ItemStack(Items.gold_nugget, 9));

		if (TFCCrafting.goldPlateRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.light_weighted_pressure_plate));

		if (TFCCrafting.goldToolsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.golden_pickaxe));
			RemoveRecipe(new ItemStack(Items.golden_axe));
			RemoveRecipe(new ItemStack(Items.golden_shovel));
			RemoveRecipe(new ItemStack(Items.golden_hoe));
			RemoveRecipe(new ItemStack(Items.golden_sword));
		}

		if (TFCCrafting.hopperRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.hopper));

		if (TFCCrafting.ironArmorRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.iron_helmet));
			RemoveRecipe(new ItemStack(Items.iron_chestplate));
			RemoveRecipe(new ItemStack(Items.iron_leggings));
			RemoveRecipe(new ItemStack(Items.iron_boots));
		}

		if (TFCCrafting.ironBarsRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.iron_bars, 16));

		if (TFCCrafting.ironBlockRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.iron_block));

		if (TFCCrafting.ironDoorRecipe == false)
			RemoveRecipe(new ItemStack(Items.iron_door));

		if (TFCCrafting.ironPlateRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate));

		if (TFCCrafting.ironToolsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.iron_pickaxe));
			RemoveRecipe(new ItemStack(Items.iron_axe));
			RemoveRecipe(new ItemStack(Items.iron_shovel));
			RemoveRecipe(new ItemStack(Items.iron_hoe));
			RemoveRecipe(new ItemStack(Items.iron_sword));
		}

		if (TFCCrafting.jukeboxRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.jukebox));

		if (TFCCrafting.leatherArmorRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.leather_helmet));
			RemoveRecipe(new ItemStack(Items.leather_chestplate));
			RemoveRecipe(new ItemStack(Items.leather_leggings));
			RemoveRecipe(new ItemStack(Items.leather_boots));
		}

		if (TFCCrafting.leverRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.lever));

		if (TFCCrafting.minecartChestRecipe == false)
			RemoveRecipe(new ItemStack(Items.chest_minecart));

		if (TFCCrafting.minecartRecipe == false)
			RemoveRecipe(new ItemStack(Items.minecart));

		if (TFCCrafting.pistonRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.piston));

		if (TFCCrafting.plankBlockRecipe == false)
		{
			RemoveRecipe(new ItemStack(Blocks.planks, 4, 0));
			RemoveRecipe(new ItemStack(Blocks.planks, 4, 1));
			RemoveRecipe(new ItemStack(Blocks.planks, 4, 2));
			RemoveRecipe(new ItemStack(Blocks.planks, 4, 3));
			RemoveRecipe(new ItemStack(Blocks.planks, 4, 4));
			RemoveRecipe(new ItemStack(Blocks.planks, 4, 5));
		}

		if (TFCCrafting.poweredRailsRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.golden_rail, 6));

		if (TFCCrafting.railsRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.rail, 16));

		if (TFCCrafting.repeaterRecipe == false)
			RemoveRecipe(new ItemStack(Items.repeater));

		if (TFCCrafting.roseRedRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.dye, 1, 1));
			RemoveRecipe(new ItemStack(Items.dye, 2, 1));
		}

		if (TFCCrafting.signRecipe == false)
			RemoveRecipe(new ItemStack(Items.sign, 3));

		if (TFCCrafting.stickRecipe == false)
			RemoveRecipe(new ItemStack(Items.stick, 4));

		if (TFCCrafting.stoneSlabsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Blocks.stone_slab, 6));
			RemoveRecipe(new ItemStack(Blocks.stone_slab, 6, 3));
		}

		if (TFCCrafting.stoneStairsRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.stone_stairs, 4));

		if (TFCCrafting.stoneToolsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.stone_pickaxe));
			RemoveRecipe(new ItemStack(Items.stone_axe));
			RemoveRecipe(new ItemStack(Items.stone_shovel));
			RemoveRecipe(new ItemStack(Items.stone_hoe));
			RemoveRecipe(new ItemStack(Items.stone_sword));
		}

		if (TFCCrafting.torchRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.torch, 4));

		if (TFCCrafting.trapDoorRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.trapdoor, 2));

		if (TFCCrafting.tripwireRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.tripwire_hook, 2));

		if (TFCCrafting.woodDoorRecipe == false)
			RemoveRecipe(new ItemStack(Items.wooden_door));

		if (TFCCrafting.woodSlabsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Blocks.wooden_slab, 6, 0));
			RemoveRecipe(new ItemStack(Blocks.wooden_slab, 6, 1));
			RemoveRecipe(new ItemStack(Blocks.wooden_slab, 6, 2));
			RemoveRecipe(new ItemStack(Blocks.wooden_slab, 6, 3));
			RemoveRecipe(new ItemStack(Blocks.wooden_slab, 6, 4));
			RemoveRecipe(new ItemStack(Blocks.wooden_slab, 6, 5));
		}

		if (TFCCrafting.woodStairsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Blocks.birch_stairs, 4));
			RemoveRecipe(new ItemStack(Blocks.jungle_stairs, 4));
			RemoveRecipe(new ItemStack(Blocks.oak_stairs, 4));
			RemoveRecipe(new ItemStack(Blocks.spruce_stairs, 4));
			RemoveRecipe(new ItemStack(Blocks.acacia_stairs, 4));
			RemoveRecipe(new ItemStack(Blocks.dark_oak_stairs, 4));
		}

		if (TFCCrafting.woodToolsRecipe == false)
		{
			RemoveRecipe(new ItemStack(Items.wooden_pickaxe));
			RemoveRecipe(new ItemStack(Items.wooden_axe));
			RemoveRecipe(new ItemStack(Items.wooden_shovel));
			RemoveRecipe(new ItemStack(Items.wooden_hoe));
			RemoveRecipe(new ItemStack(Items.wooden_sword));
		}

		if (TFCCrafting.woolRecipe == false)
			RemoveRecipe(new ItemStack(Blocks.wool));

		//Conversion Configuration
		if (TFCCrafting.appleConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.apple, 1), new Object[] { new ItemStack(TFCItems.RedApple, 1) });
		}

		if (TFCCrafting.arrowConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.arrow, 1), new Object[] { new ItemStack(TFCItems.Arrow, 1) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Arrow, 1), new Object[] { new ItemStack(Items.arrow, 1) });
		}

		if (TFCCrafting.bowConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.bow, 1), new Object[] { new ItemStack(TFCItems.Bow, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Bow, 1, 0), new Object[] { new ItemStack(Items.bow, 1) });
		}

		if (TFCCrafting.coalConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.coal, 1), new Object[] { new ItemStack(TFCItems.Coal, 1) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Coal, 1), new Object[] { new ItemStack(Items.coal, 1) });
		}

		if (TFCCrafting.diamondConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond, 1), new Object[] {new ItemStack(TFCItems.GemDiamond,1,2)});
			GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond, 2), new Object[] {new ItemStack(TFCItems.GemDiamond,1,3)});
			GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond, 3), new Object[] {new ItemStack(TFCItems.GemDiamond,1,4)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GemDiamond,1,2), new Object[] {new ItemStack(Items.diamond)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GemDiamond,1,3), new Object[] {new ItemStack(Items.diamond), new ItemStack(Items.diamond)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GemDiamond,1,4), new Object[] {new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Items.diamond)});
		}

		if (TFCCrafting.emeraldConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.emerald, 1), new Object[] {new ItemStack(TFCItems.GemEmerald,1,2)});
			GameRegistry.addShapelessRecipe(new ItemStack(Items.emerald, 2), new Object[] {new ItemStack(TFCItems.GemEmerald,1,3)});
			GameRegistry.addShapelessRecipe(new ItemStack(Items.emerald, 3), new Object[] {new ItemStack(TFCItems.GemEmerald,1,4)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GemEmerald,1,2), new Object[] {new ItemStack(Items.emerald)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GemEmerald,1,3), new Object[] {new ItemStack(Items.emerald), new ItemStack(Items.emerald)});
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.GemEmerald,1,4), new Object[] {new ItemStack(Items.emerald), new ItemStack(Items.emerald), new ItemStack(Items.emerald)});
		}

		if (TFCCrafting.fishConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.fish, 1), new Object[] { new ItemStack(TFCItems.fishRaw, 1) });
		}

		if (TFCCrafting.fishingRodConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.fishing_rod, 1), new Object[] { new ItemStack(TFCItems.FishingRod, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.FishingRod, 1, 0), new Object[] { new ItemStack(Items.fishing_rod, 1) });
		}

		if (TFCCrafting.flintSteelConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.flint_and_steel, 1, 0), new Object[] { new ItemStack(TFCItems.FlintSteel, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.FlintSteel, 1, 0), new Object[] { new ItemStack(Items.flint_and_steel, 1, 0) });
		}

		if (TFCCrafting.leatherArmorConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.leather_helmet, 1, 0), new Object[] { new ItemStack(TFCItems.LeatherHelmet, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LeatherHelmet, 1, 0), new Object[] { new ItemStack(Items.leather_helmet, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.leather_chestplate, 1, 0), new Object[] { new ItemStack(TFCItems.LeatherChestplate, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LeatherChestplate, 1, 0), new Object[] { new ItemStack(Items.leather_chestplate, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.leather_leggings, 1, 0), new Object[] { new ItemStack(TFCItems.LeatherLeggings, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LeatherLeggings, 1, 0), new Object[] { new ItemStack(Items.leather_leggings, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.leather_boots, 1, 0), new Object[] { new ItemStack(TFCItems.LeatherBoots, 1, 0) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.LeatherBoots, 1, 0), new Object[] { new ItemStack(Items.leather_boots, 1, 0) });
		}

		if (TFCCrafting.leatherConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.leather, 1), new Object[] { new ItemStack(TFCItems.Leather, 1) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.Leather, 1), new Object[] { new ItemStack(Items.leather, 1) });
		}

		if (TFCCrafting.stoneAxeConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_axe, 1, 0), new Object[] { TFCItems.IgInAxe });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_axe, 1, 0), new Object[] { TFCItems.IgExAxe });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_axe, 1, 0), new Object[] { TFCItems.SedAxe });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_axe, 1, 0), new Object[] { TFCItems.MMAxe });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.IgExAxe, 1, 0), new Object[] { Items.stone_axe });
		}

		if (TFCCrafting.stoneHoeConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_hoe, 1, 0), new Object[] { TFCItems.IgInHoe });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_hoe, 1, 0), new Object[] { TFCItems.IgExHoe });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_hoe, 1, 0), new Object[] { TFCItems.SedHoe });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_hoe, 1, 0), new Object[] { TFCItems.MMHoe });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.IgExHoe, 1, 0), new Object[] { Items.stone_hoe });
		}

		if (TFCCrafting.stoneShovelConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_shovel, 1, 0), new Object[] { TFCItems.IgInShovel });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_shovel, 1, 0), new Object[] { TFCItems.IgExShovel });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_shovel, 1, 0), new Object[] { TFCItems.SedShovel });
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stone_shovel, 1, 0), new Object[] { TFCItems.MMShovel });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.IgExShovel, 1, 0), new Object[] { Items.stone_shovel });
		}

		if (TFCCrafting.woodButtonConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wooden_button, 1), new Object[] { new ItemStack(TFCBlocks.ButtonWood, 1) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCBlocks.ButtonWood, 1), new Object[] { new ItemStack(Blocks.wooden_button, 1) });
		}

		if (TFCCrafting.workbenchConversion == true)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.crafting_table, 1), new Object[] { new ItemStack(TFCBlocks.Workbench, 1) });
			GameRegistry.addShapelessRecipe(new ItemStack(TFCBlocks.Workbench, 1), new Object[] { new ItemStack(Blocks.crafting_table, 1) });
		}
	}

	private static void RegisterToolRecipes()
	{
		//Misc Tools
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.shears, 1), new Object[] { "P ", " P", Character.valueOf('P'), "ingotIron" }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.FlintSteel, 1), new Object[] { Items.flint, "ingotIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.FlintSteel, 1), new Object[] { Items.flint, "ingotSteel" }));

		GameRegistry.addRecipe(new ItemStack(TFCItems.Leash, 1), new Object[] { "RR ", "RR ", "  R", Character.valueOf('R'), new ItemStack(TFCItems.JuteFibre, 1) });

		GameRegistry.addRecipe(new ItemStack(TFCItems.GoldPan, 1, 0), new Object[] { "1", Character.valueOf('1'), new ItemStack(TFCItems.PotteryBowl, 1, 1) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.FireStarter, 1, 0), new Object[] { "2 ", " 2", Character.valueOf('2'), "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.Bow, 1), new Object[] { " #$", "# $", " #$", Character.valueOf('#'), "stickWood", Character.valueOf('$'), "materialString" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.Bow, 1), new Object[] { "$# ", "$ #", "$# ", Character.valueOf('#'), "stickWood", Character.valueOf('$'), "materialString" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.FishingRod, 1), new Object[] { "  #", " #$", "# $", Character.valueOf('#'), "stickWood", Character.valueOf('$'), "materialString" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.Spindle, 1), new Object[] { "P", "#", Character.valueOf('P'), new ItemStack(TFCItems.SpindleHead, 1, 1), Character.valueOf('#'), "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SpindleHead, 1, 0), new Object[] { "P", "#", Character.valueOf('P'), "lumpClay", Character.valueOf('#'), "stickWood" }));

		//stone javelins
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgInStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneJavelinHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SedStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneJavelinHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgExStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneJavelinHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.MMStoneJavelin, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneJavelinHead,Character.valueOf('2'), "stickWood"}));

		//stone shovels
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.MMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), "stickWood"}));
		//stone axes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.MMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), "stickWood"}));
		//stone hoes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.IgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.MMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), "stickWood"}));

		//bone shovels
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		//bone axes
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		//bone hoes
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.SedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.IgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});
		GameRegistry.addRecipe(new ItemStack(TFCItems.MMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Items.bone,1,32767)});

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.StoneHammer, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneHammerHead,Character.valueOf('2'), "stickWood"}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.StoneKnife, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneKnifeHead,Character.valueOf('2'), "stickWood"}));

		//pickaxes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzePick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzePickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzePick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzePickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelPick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelPickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelPick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelPickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzePick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzePickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperPick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperPickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronPick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronPickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelPick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelPickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelPick, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelPickaxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Shovels
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelShovel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelShovelHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Hoes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelHoe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelHoeHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Axes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelAxe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelAxeHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Swords
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelSword, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelSwordHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Maces
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelMace, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelMaceHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Hammers
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelHammer, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelHammerHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Saws
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelSaw, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelSawHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//Chisels
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelChisel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelChiselHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//propicks
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickBismuthBronze, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickBlackBronze, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickBlackSteel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickBlueSteel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickBronze, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickCopper, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickIron, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickRedSteel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ProPickSteel, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelProPickHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		//Scythes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelScythe, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelScytheHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		//Knifes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelKnife, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelKnifeHead, 1, 0), Character.valueOf('I'), "stickWood"}));

		//javelins
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BismuthBronzeJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BismuthBronzeJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackBronzeJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackBronzeJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlackSteelJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlackSteelJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BlueSteelJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BlueSteelJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.BronzeJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.BronzeJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.CopperJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.CopperJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.WroughtIronJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.WroughtIronJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.RedSteelJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.RedSteelJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.SteelJavelin, 1), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(TFCItems.SteelJavelinHead, 1, 0), Character.valueOf('I'), "stickWood"}));

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
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.LeatherHelmet, 1), new Object[] { "#####","#   #","#   #", Character.valueOf('#'), TFCItems.FlatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.LeatherChestplate, 1), new Object[] { "#   #","#####","#####","#####","#####", Character.valueOf('#'), TFCItems.FlatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.LeatherLeggings, 1), new Object[] { "#####","#####","## ##","## ##","## ##", Character.valueOf('#'), TFCItems.FlatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.LeatherBoots, 1), new Object[] { "##   ","##   ","##   ","#### ","#####", Character.valueOf('#'), TFCItems.FlatLeather});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.Quiver, 1), new Object[] { " ####","# ###","# ###","# ###"," ####",Character.valueOf('#'), TFCItems.FlatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(Items.saddle, 1), new Object[] { "  #  ","#####","#####","#####","  #  ",Character.valueOf('#'), TFCItems.FlatLeather});
	}

	private static void registerKnapping()
	{
		//Knapping
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { " #  #","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { "#  # ","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { "#   #","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 2), new Object[] { " # # ","## ##","## ##","## ##","## ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneKnifeHead, 1), new Object[] { " #","##","##","##","##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.StoneHammerHead, 1), new Object[] { "#####","#####","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, 32767)});

		for(int i = 0; i < Global.STONE_IGIN.length; i++)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneHoeHead, 1), new Object[] { "#####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgInStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGIN_START)});
		}
		for(int i = 0; i < Global.STONE_SED.length; i++)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneHoeHead, 1), new Object[] { "#####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SedStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_SED_START)});
		}
		for(int i = 0; i < Global.STONE_IGEX.length; i++)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneHoeHead, 1), new Object[] { "#####","   ##", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.IgExStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", Character.valueOf('#'), new ItemStack(TFCItems.FlatRock, 1, i + Global.STONE_IGEX_START)});
		}
		for(int i = 0; i < Global.STONE_MM.length; i++)
		{
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
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCBlocks.Vessel, 1), new Object[] { 
			" ### ",
			" ### ",
			" ### ",
			" ### ",
			"     ", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.PotteryBowl, 4), new Object[] { 
			"#####",
			"#####",
			"#####",
			" ### ",
			"#   #", Character.valueOf('#'), new ItemStack(TFCItems.FlatClay, 1, 1)});
	}

	private static void registerAlloys()
	{
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

	public static ItemStack getStackTemp(ItemStack is)
	{
		NBTTagCompound Temp = new NBTTagCompound();
		Temp.setBoolean("temp", true);
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
		AnvilManager manager = AnvilManager.getInstance();

		manager.addPlan("ingot", new PlanRecipe(
				new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		manager.addPlan("sheet", new PlanRecipe(
				new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		manager.addPlan("pickaxe", new PlanRecipe(
				new RuleEnum[] {RuleEnum.PUNCHLAST, RuleEnum.BENDNOTLAST, RuleEnum.DRAWNOTLAST}));
		manager.addPlan("shovel", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.HITNOTLAST, RuleEnum.ANY}));

		manager.addPlan("axe", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.UPSETTHIRDFROMLAST}));
		manager.addPlan("hoe", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.HITNOTLAST, RuleEnum.BENDNOTLAST}));
		manager.addPlan("hammer", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.ANY, RuleEnum.SHRINKNOTLAST}));
		manager.addPlan("chisel", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITNOTLAST, RuleEnum.DRAWNOTLAST}));
		manager.addPlan("propick", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.DRAWNOTLAST, RuleEnum.BENDNOTLAST}));
		manager.addPlan("saw", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.ANY}));
		manager.addPlan("sword", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		manager.addPlan("mace", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.SHRINKNOTLAST, RuleEnum.BENDNOTLAST}));
		manager.addPlan("scythe", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.DRAWSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		manager.addPlan("knife", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.DRAWSECONDFROMLAST, RuleEnum.DRAWTHIRDFROMLAST}));
		manager.addPlan("javelin", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.DRAWTHIRDFROMLAST}));
		manager.addPlan("helmplate", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		manager.addPlan("chestplate", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.UPSETTHIRDFROMLAST}));
		manager.addPlan("legsplate", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDANY, RuleEnum.DRAWANY, RuleEnum.HITANY}));
		manager.addPlan("bootsplate", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.SHRINKTHIRDFROMLAST}));
		manager.addPlan("bucket", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		manager.addPlan("refinebloom", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		manager.addPlan("splitbloom", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.ANY, RuleEnum.ANY}));
		manager.addPlan("tuyere", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.ANY}));
		manager.addPlan("trapdoor", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.SHRINKNOTLAST, RuleEnum.UPSETNOTLAST}));
		manager.addPlan("grill", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.DRAWSECONDFROMLAST, RuleEnum.DRAWTHIRDFROMLAST}));
		manager.addPlan("shears", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));

		addWeldRecipes(manager);

		/**
		 * Normal Recipes Start Here
		 */
		//Ingots
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronIngot), null, "ingot", false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.HCSteelIngot)).clearRecipeSkills()); 

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlackSteelIngot), null, "ingot", false, AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelIngot)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlueSteelIngot), null, "ingot", false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelIngot)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCRedSteelIngot), null, "ingot",  false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelIngot)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCSteelIngot), null, "ingot", false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelIngot)).clearRecipeSkills());

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot2x), null, "sheet", false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), null, "sheet", false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), null, "sheet", false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), null, "sheet", false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), null, "sheet", false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BrassIngot2x), null, "sheet", false, AnvilReq.COPPER, new ItemStack(TFCItems.BrassSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), null, "sheet", false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), null, "sheet", false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.GoldIngot2x), null, "sheet", false, AnvilReq.COPPER, new ItemStack(TFCItems.GoldSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), null, "sheet", false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.LeadIngot2x), null, "sheet", false, AnvilReq.COPPER, new ItemStack(TFCItems.LeadSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.NickelIngot2x), null, "sheet", false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.NickelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronIngot2x), null, "sheet", false, AnvilReq.BRONZE, new ItemStack(TFCItems.PigIronSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PlatinumIngot2x), null, "sheet", false, AnvilReq.BRONZE, new ItemStack(TFCItems.PlatinumSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), null, "sheet", false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot2x), null, "sheet", false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldSheet)));       
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SilverIngot2x), null, "sheet", false, AnvilReq.COPPER, new ItemStack(TFCItems.SilverSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), null, "sheet", false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SterlingSilverIngot2x), null, "sheet", false, AnvilReq.BRONZE, new ItemStack(TFCItems.SterlingSilverSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot2x), null, "sheet", false, AnvilReq.STONE, new ItemStack(TFCItems.TinSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot2x), null, "sheet", false, AnvilReq.STONE, new ItemStack(TFCItems.ZincSheet)));

		//Picks
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "pickaxe", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzePickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null, "pickaxe", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzePickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null, "pickaxe", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null, "pickaxe", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null, "pickaxe", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzePickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null, "pickaxe", AnvilReq.COPPER, new ItemStack(TFCItems.CopperPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null, "pickaxe", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null, "pickaxe", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));   
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null, "pickaxe", AnvilReq.STEEL, new ItemStack(TFCItems.SteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));


		//shovels
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "shovel",  AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null, "shovel", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null, "shovel", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null, "shovel",  AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null, "shovel",  AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null, "shovel", AnvilReq.COPPER, new ItemStack(TFCItems.CopperShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null, "shovel", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null, "shovel", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));   
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null, "shovel", AnvilReq.STEEL, new ItemStack(TFCItems.SteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//axes 
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "axe", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null, "axe",  AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null, "axe",  AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null, "axe",  AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null, "axe",  AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null, "axe",  AnvilReq.COPPER, new ItemStack(TFCItems.CopperAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null, "axe",  AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null, "axe",  AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));    
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null, "axe", AnvilReq.STEEL, new ItemStack(TFCItems.SteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//hoes
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "hoe", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null, "hoe", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null, "hoe", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null, "hoe", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null, "hoe", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null, "hoe", AnvilReq.COPPER, new ItemStack(TFCItems.CopperHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null, "hoe", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null, "hoe", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));    
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null, "hoe", AnvilReq.STEEL, new ItemStack(TFCItems.SteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//Hammers
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "hammer", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null, "hammer", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null, "hammer", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null, "hammer", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null, "hammer", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null, "hammer", AnvilReq.COPPER, new ItemStack(TFCItems.CopperHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null, "hammer", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null, "hammer", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null, "hammer",AnvilReq.STEEL, new ItemStack(TFCItems.SteelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//Chisels     
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "chisel", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null, "chisel", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null, "chisel", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null, "chisel", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null, "chisel", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null, "chisel", AnvilReq.COPPER, new ItemStack(TFCItems.CopperChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null, "chisel", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null, "chisel", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));   
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null, "chisel", AnvilReq.STEEL, new ItemStack(TFCItems.SteelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//ProPicks
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "propick", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null, "propick", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null, "propick", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null, "propick", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null, "propick", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null, "propick", AnvilReq.COPPER, new ItemStack(TFCItems.CopperProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null, "propick", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null, "propick", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null, "propick", AnvilReq.STEEL, new ItemStack(TFCItems.SteelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//Saws
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "saw", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null,"saw", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null,"saw", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null,"saw", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null,"saw", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null,"saw", AnvilReq.COPPER, new ItemStack(TFCItems.CopperSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null,"saw", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null,"saw", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));     
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null,"saw", AnvilReq.STEEL, new ItemStack(TFCItems.SteelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//Swords
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), null, "sword", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), null, "sword", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), null, "sword", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), null, "sword", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), null, "sword", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), null, "sword", AnvilReq.COPPER, new ItemStack(TFCItems.CopperSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), null, "sword", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), null, "sword", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));    
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), null, "sword", AnvilReq.STEEL, new ItemStack(TFCItems.SteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));


		//Maces
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), null, "mace", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), null, "mace", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), null, "mace", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), null, "mace", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), null, "mace", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), null, "mace", AnvilReq.COPPER, new ItemStack(TFCItems.CopperMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), null, "mace", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), null, "mace", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));     
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), null, "mace", AnvilReq.STEEL, new ItemStack(TFCItems.SteelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));

		//Scythes
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null, "scythe", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null,"scythe", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot),null,"scythe", false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null,"scythe", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null,"scythe", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null,"scythe", AnvilReq.COPPER, new ItemStack(TFCItems.CopperScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null,"scythe", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null,"scythe", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));    
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null,"scythe", AnvilReq.STEEL, new ItemStack(TFCItems.SteelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));

		//Knifes		
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot),null,"knife", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null,"knife", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null,"knife", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null,"knife", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null,"knife", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null,"knife", AnvilReq.COPPER, new ItemStack(TFCItems.CopperKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null,"knife", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null,"knife", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null,"knife", AnvilReq.STEEL, new ItemStack(TFCItems.SteelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).SetCraftingBound(40));


		//javelin heads	
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), null,"javelin", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), null,"javelin", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), null,"javelin", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), null,"javelin", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), null,"javelin", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), null,"javelin", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), null,"javelin", AnvilReq.STEEL, new ItemStack(TFCItems.SteelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), null,"javelin", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), null,"javelin", AnvilReq.COPPER, new ItemStack(TFCItems.CopperJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));   

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet), null,"helmPlate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet), null,"helmPlate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet), null,"helmPlate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet), null,"helmPlate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet), null,"helmPlate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet), null,"helmPlate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet), null,"helmPlate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), null,"helmPlate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));     
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet), null,"helmPlate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));    
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), null,"chestPlate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), null,"chestPlate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), null,"chestPlate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), null,"chestPlate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), null,"chestPlate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), null,"chestPlate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), null,"chestPlate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), null,"chestPlate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));   
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), null,"chestPlate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));       
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), null,"legsPlate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), null,"legsPlate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), null,"legsPlate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), null,"legsPlate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), null,"legsPlate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), null,"legsPlate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), null,"legsPlate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), null, "legsPlate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));     
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), null, "legsPlate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet), null,"bootsplate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet), null,"bootsplate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet), null,"bootsplate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet), null,"bootsplate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet), null,"bootsplate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet), null,"bootsplate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet), null,"bootsplate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), null,"bootsplate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));      
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet), null,"bootsplate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.COPPER, new ItemStack(TFCItems.CopperBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.STEEL, new ItemStack(TFCItems.SteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));


		//Buckets
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), null, "bucket", AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelBucketEmpty, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet),null, "bucket", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelBucketEmpty, 1)));

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RawBloom, 1, 32767), null, "refinebloom", AnvilReq.BRONZE, new ItemStack(TFCItems.Bloom, 1)).setInheritsDamage().clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.Bloom, 1, 100), null , "refinebloom", AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronIngot, 1)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.Bloom, 1, 32767), null , "splitbloom", AnvilReq.BRONZE, new ItemStack(TFCItems.Bloom, 1)).clearRecipeSkills());

		//Tuyeres
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), null,"tuyere", AnvilReq.COPPER, new ItemStack(TFCItems.TuyereCopper, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), null,"tuyere", AnvilReq.BRONZE, new ItemStack(TFCItems.TuyereBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), null,"tuyere", AnvilReq.BRONZE, new ItemStack(TFCItems.TuyereBismuthBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), null,"tuyere", AnvilReq.BRONZE, new ItemStack(TFCItems.TuyereBlackBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), null,"tuyere", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.TuyereWroughtIron, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), null,"tuyere", AnvilReq.STEEL, new ItemStack(TFCItems.TuyereSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), null,"tuyere", AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.TuyereBlackSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), null,"tuyere", AnvilReq.BLUESTEEL, new ItemStack(TFCItems.TuyereBlueSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), null,"tuyere", AnvilReq.REDSTEEL, new ItemStack(TFCItems.TuyereRedSteel, 1)));

		addTrapDoor(TFCItems.BismuthSheet, 0);addTrapDoor(TFCItems.BismuthBronzeSheet, 1);addTrapDoor(TFCItems.BlackBronzeSheet, 2);addTrapDoor(TFCItems.BlackSteelSheet, 3);
		addTrapDoor(TFCItems.BlueSteelSheet, 4);addTrapDoor(TFCItems.BrassSheet, 5);addTrapDoor(TFCItems.BronzeSheet, 6);addTrapDoor(TFCItems.CopperSheet, 7);
		addTrapDoor(TFCItems.GoldSheet, 8);addTrapDoor(TFCItems.WroughtIronSheet, 9);addTrapDoor(TFCItems.LeadSheet, 10);addTrapDoor(TFCItems.NickelSheet, 11);
		addTrapDoor(TFCItems.NickelSheet, 12);addTrapDoor(TFCItems.PlatinumSheet, 13);addTrapDoor(TFCItems.RedSteelSheet, 14);addTrapDoor(TFCItems.RoseGoldSheet, 15);
		addTrapDoor(TFCItems.SilverSheet, 16);addTrapDoor(TFCItems.SteelSheet, 17);addTrapDoor(TFCItems.SterlingSilverSheet, 18);addTrapDoor(TFCItems.TinSheet, 19);
		addTrapDoor(TFCItems.ZincSheet, 20);

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), new ItemStack(TFCItems.WroughtIronIngot2x),"grill", AnvilReq.WROUGHTIRON, new ItemStack(TFCBlocks.Grill, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronKnifeHead), new ItemStack(TFCItems.WroughtIronKnifeHead),"shears", AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.Shears, 1, 0)));

	}

	private static void addTrapDoor(Item sheet, int index)
	{
		AnvilManager manager = AnvilManager.getInstance();
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.BismuthIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.BismuthBronzeIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (1 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.BlackBronzeIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (2 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.BlackSteelIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (3 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.BlueSteelIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (4 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.BrassIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (5 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.BronzeIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (6 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.CopperIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (7 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.GoldIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (8 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.WroughtIronIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (9 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.LeadIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (10 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.NickelIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (11 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.PlatinumIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (13 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.RedSteelIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (14 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.RoseGoldIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (15 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.SilverIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (16 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.SteelIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (17 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.SterlingSilverIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (18 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.TinIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (19 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.ZincIngot), "trapdoor", AnvilReq.COPPER, new ItemStack(TFCBlocks.MetalTrapDoor, 1, index + (20 << 5))));
	}

	/**
	 * @param manager
	 */
	private static void addWeldRecipes(AnvilManager manager)
	{
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
	}

	public static void registerFoodRecipes()
	{
		for(int j = 0; j < Knives.length; j++)
		{
			GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.WheatGrain, 1)), new Object[] {new ItemStack(TFCItems.WheatWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RyeGrain, 1)), new Object[] {new ItemStack(TFCItems.RyeWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.BarleyGrain, 1)), new Object[] {new ItemStack(TFCItems.BarleyWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.OatGrain, 1)), new Object[] {new ItemStack(TFCItems.OatWhole, 1),new ItemStack(Knives[j], 1, 32767)});
			GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RiceGrain, 1)), new Object[] {new ItemStack(TFCItems.RiceWhole, 1),new ItemStack(Knives[j], 1, 32767)});
		}

		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.WheatDough, 1)), new Object[] {TFCItems.WheatGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.BarleyDough, 1)), new Object[] {TFCItems.BarleyGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RyeDough, 1)), new Object[] {TFCItems.RyeGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.OatDough, 1)), new Object[] {TFCItems.OatGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RiceDough, 1)), new Object[] {TFCItems.RiceGround,TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.CornmealDough, 1)), new Object[] {TFCItems.CornmealGround, TFCItems.WoodenBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.WheatDough, 1)), new Object[] {TFCItems.WheatGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.BarleyDough, 1)), new Object[] {TFCItems.BarleyGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RyeDough, 1)), new Object[] {TFCItems.RyeGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.OatDough, 1)), new Object[] {TFCItems.OatGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.RiceDough, 1)), new Object[] {TFCItems.RiceGround,TFCItems.RedSteelBucketWater});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.CornmealDough, 1)), new Object[] {TFCItems.CornmealGround, TFCItems.RedSteelBucketWater});

		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.venisonRaw, 1), 0), new Object[] {new ItemStack(TFCItems.venisonRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.beefRaw, 1), 0), new Object[] {new ItemStack(TFCItems.beefRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.chickenRaw, 1), 0), new Object[] {new ItemStack(TFCItems.chickenRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.porkchopRaw, 1), 0), new Object[] {new ItemStack(TFCItems.porkchopRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.fishRaw, 1), 0), new Object[] {new ItemStack(TFCItems.fishRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.calamariRaw, 1), 0), new Object[] {new ItemStack(TFCItems.calamariRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.muttonRaw, 1), 0), new Object[] {new ItemStack(TFCItems.muttonRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.horseMeatRaw, 1), 0), new Object[] {new ItemStack(TFCItems.horseMeatRaw, 1), new ItemStack(TFCItems.Powder, 1, 9)});
		for(Item i : TFCItems.FoodList)
		{
			addFoodMergeRecipe(i);
			for(int j = 0; j < Knives.length; j++)
				GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(i, 1), 0), new Object[] {new ItemStack(i, 1), new ItemStack(Knives[j], 1, 32767)});
		}
	}

	public static void addFoodMergeRecipe(Item food)
	{
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), 
			new ItemStack(food, 1)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), 
			new ItemStack(food, 1), new ItemStack(food, 1)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), 
			new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), 
			new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), 
			new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1)});
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1), 0), new Object[] {new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), 
			new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1), new ItemStack(food, 1)});
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
						new ItemStack(TFCBlocks.Vessel,1,0),
						0, 
						new ItemStack(TFCBlocks.Vessel,1,1)));

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

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.FireBrick,1,0),
						0, 
						new ItemStack(TFCItems.FireBrick,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.PotteryBowl,1,0),
						0, 
						new ItemStack(TFCItems.PotteryBowl,1,1)));
	}

	private static void registerQuernRecipes()
	{
		QuernManager manager = QuernManager.getInstance();

		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.WheatGrain, 1), new ItemStack(TFCItems.WheatGround, 1)));//Wheat Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.RyeGrain, 1), new ItemStack(TFCItems.RyeGround, 1)));//Rye Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OatGrain, 1), new ItemStack(TFCItems.OatGround, 1)));//Oat Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.BarleyGrain, 1), new ItemStack(TFCItems.BarleyGround, 1)));//Barley Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.RiceGrain, 1), new ItemStack(TFCItems.RiceGround, 1)));//Rice Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.MaizeEar, 1), new ItemStack(TFCItems.CornmealGround, 1)));//Cornmeal
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 16),new ItemStack( TFCItems.Powder, 4, 1)));//Kaolinite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 20), new ItemStack(TFCItems.Powder, 4, 2)));//Graphite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 27), new ItemStack(Items.redstone, 8)));//Cinnabar to Redstone
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 28), new ItemStack(Items.redstone, 8)));//Cryolite to Redstone
		manager.addRecipe(new QuernRecipe(new ItemStack(Items.bone, 1), new ItemStack(TFCItems.Dye, 2, 15)));//Bone Meal
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 34), new ItemStack(TFCItems.Powder, 4, 6)));//Lapis Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.SmallOreChunk, 1, 9), new ItemStack(TFCItems.Powder, 1, 8)));//Small Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 58), new ItemStack(TFCItems.Powder, 2, 8)));//Poor Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 9), new ItemStack(TFCItems.Powder, 4, 8)));//Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 44), new ItemStack(TFCItems.Powder, 6, 8)));//Rich Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.SmallOreChunk, 1, 3), new ItemStack(TFCItems.Powder, 1, 5)));//Small Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 52), new ItemStack(TFCItems.Powder, 2, 5)));//Poor Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 3),new ItemStack( TFCItems.Powder, 4, 5)));//Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 38), new ItemStack(TFCItems.Powder, 6, 5)));//Rich Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.SmallOreChunk, 1, 11), new ItemStack(TFCItems.Powder, 1, 7)));//Small Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 60), new ItemStack(TFCItems.Powder, 2, 7)));//Poor Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 11), new ItemStack(TFCItems.Powder, 4, 7)));//Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 46), new ItemStack(TFCItems.Powder, 6, 7)));//Rich Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.OreChunk, 1, 31), new ItemStack(TFCItems.Fertilizer, 4)));//Sylvite to Fertilizer
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.LooseRock, 1, 5), new ItemStack(TFCItems.Powder, 4, 9)));//Rock Salt to Salt
	}

	public static int valueOfString(String s)
	{
		if(s.length() > 0)
		{
			byte[] b = s.getBytes();
			int out = 0;
			for(int i = 0; i < b.length; i++)
				out+=b[i];
			return out;
		} else
			return 0;
	}

	public static void RemoveRecipe(ItemStack resultItem)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof IRecipe)
			{
				IRecipe recipe = tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();

				if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
					recipes.remove(i--);
			}
		}
	}
}
