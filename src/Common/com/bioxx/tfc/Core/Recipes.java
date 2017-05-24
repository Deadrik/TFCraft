package com.bioxx.tfc.Core;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import com.bioxx.tfc.Core.Config.TFC_ConfigFiles;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.*;
import com.bioxx.tfc.api.Enums.RuleEnum;

public class Recipes 
{
	public static Item[] axes;
	public static Item[] chisels;
	public static Item[] saws;
	public static Item[] scythes;
	public static Item[] knives;
	public static Item[] meltedMetal;
	public static Item[] hammers;
	public static Item[] picks;
	public static Item[] proPicks;
	public static Item[] shovels;
	public static Item[] hoes;
	public static Item[] swords;
	public static Item[] maces;
	public static Item[] javelins;
	public static Item[] spindle;
	public static Item[] gems;
	public static Item[] seeds;
	public static Item[] doors;
	public static Item[] tuyeres;

	public static final int WILD = OreDictionary.WILDCARD_VALUE;

	public static void registerRecipes()
	{
		TEBarrel.registerRecipes();
		TELoom.registerRecipes();
// Remove Vanilla recipes before adding TFC recipes for oredict compatibility
		vanillaRecipes();
//Wood Specific Stuff
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			GameRegistry.addRecipe(new ItemStack(doors[i]), "WW", "WW", "WW", 'W', new ItemStack(TFCItems.singlePlank, 1, i));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.chest, 1, i), "###", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 1, i)));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.toolRack, 1, i), "###", "   ", "###", '#', new ItemStack(TFCItems.singlePlank, 1, i));
			//GameRegistry.addRecipe(new ItemStack(TFCBlocks.bookshelf, 1, i), "###", "BBB", "###", '#', new ItemStack(TFCItems.singlePlank, 1, i), 'B', new ItemStack(Items.book));
			int l = i%16;
			if(i==l)
			{
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 8, i), new ItemStack(TFCItems.logs, 1, i), "itemSaw"));
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 4, i), new ItemStack(TFCBlocks.planks, 1, i), "itemSaw"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.woodSupportV, 8, i), "A2", " 2", '2', new ItemStack(TFCItems.logs, 1, i), 'A', "itemSaw"));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.planks, 1, i), "11","11", '1', new ItemStack(TFCItems.singlePlank, 1, i));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.barrel, 1, i), "# #", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 1, i));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.fence, 6, i), "LPL", "LPL", 'L', new ItemStack(TFCItems.logs, 1, i), 'P', new ItemStack(TFCItems.singlePlank, 1, i));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.fenceGate, 2, i), "LPL", "LPL", 'L', new ItemStack(TFCItems.singlePlank, 1, i), 'P', new ItemStack(TFCBlocks.planks, 1, i));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.armorStand, 1, i), "###", " # ", "%%%", '#', new ItemStack(TFCItems.singlePlank, 1, i), '%', new ItemStack(TFCBlocks.planks, 1, i));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.loom, 1, i), "LLL", "LSL", "L L", 'L', new ItemStack(TFCItems.singlePlank, 1, i), 'S', "stickWood"));
			}
			else if(i/16 == 1)
			{
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 8, i), new ItemStack(TFCItems.logs, 1, i), "itemSaw"));
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.singlePlank, 4, i), new ItemStack(TFCBlocks.planks2, 1, l), "itemSaw"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.woodSupportV2, 8, l), "A2", " 2", '2', new ItemStack(TFCItems.logs, 1, i), 'A', "itemSaw"));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.planks2, 1, l), "11","11", '1', new ItemStack(TFCItems.singlePlank, 1, i));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.fence2, 6, l), "LPL", "LPL", 'L', new ItemStack(TFCItems.logs, 1, i), 'P', new ItemStack(TFCItems.singlePlank, 1, i));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.fenceGate2, 2, l), "LPL", "LPL", 'L', new ItemStack(TFCItems.singlePlank, 1, i), 'P', new ItemStack(TFCBlocks.planks2, 1, l));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.armorStand2, 1, l), "###", " # ", "%%%", '#', new ItemStack(TFCItems.singlePlank, 1, i), '%', new ItemStack(TFCBlocks.planks2, 1, l));
				GameRegistry.addRecipe(new ItemStack(TFCBlocks.barrel, 1, i), "# #", "# #", "###", '#', new ItemStack(TFCItems.singlePlank, 1, i));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.loom, 1, i), "LLL", "LSL", "L L", 'L', new ItemStack(TFCItems.singlePlank, 1, i), 'S', "stickWood"));
			}
		}
//Wood Mix & Match
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.sluiceItem, 1), "  1", " 12", "122", '1', "stickWood", '2', "woodLumber"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.nestBox,1), "S S","PSP","PPP", 'S', new ItemStack(TFCItems.straw,1), 'P', "woodLumber"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.woodenBucketEmpty, 1), "w w", "w w", " w ", 'w', "woodLumber"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.trapdoor, 1, 0), "###", "###", '#', "woodLumber"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.sign, 1, 0), "###", "###", " 1 ", '#', "woodLumber", '1', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.buttonWood, 1), "#", '#', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.bed, 1), "PPP", "QQQ", 'P', "materialCloth", 'Q', "woodLumber"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.workbench, 1), "PP", "PP", 'P', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.bellows, 1, 0), "###", "???", "###", '#', "woodLumber", '?', "materialLeather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.boat), "LL", "LA", 'L', "logWood", 'A', "itemAxeStoneHead"));
//Hide & Sheepskin
		for (int k = 0; k < 3; k++)
		{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.wool, 1 + k, 0), new ItemStack(TFCItems.sheepSkin, 1, k), "itemKnife"));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.wool, 1 + k, 0), new ItemStack(TFCItems.pbearSkin, 1, k), "itemKnife"));
		}
//Dyes
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye,1,4), new ItemStack(TFCItems.powder,1,6)); // Lapis - Blue
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye,1,2), new ItemStack(TFCItems.powder,1,8)); // Malachite - Green
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye,1,1), new ItemStack(TFCItems.powder,1,5)); // Hematite - Red
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.dye,1,11), new ItemStack(TFCItems.powder,1,7)); // Limonite - Yellow
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.dye,1,12), new ItemStack(TFCItems.powder,1,8),new ItemStack(TFCItems.powder,1,0), "blockSand")); // Malachite, Flux & Sand - Light Blue
//Flux Powder
		for (int i = 0; i < Global.STONE_FLUXINDEX.length; i++)
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.powder, 2, 0), new ItemStack(TFCItems.looseRock, 1, Global.STONE_FLUXINDEX[i]), "itemHammer"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.powder, 6, 0), new ItemStack(TFCItems.oreMineralChunk, 1, 15), "itemHammer"));
//Anvils
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil, 1, 1), "###"," # ","###", '#', "ingotDoubleCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil, 1, 2), "###"," # ","###", '#', "ingotDoubleBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil, 1, 3), "###"," # ","###", '#', "ingotDoubleWroughtIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil, 1, 4), "###"," # ","###", '#', "ingotDoubleSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil, 1, 5), "###"," # ","###", '#', "ingotDoubleBlackSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil, 1, 6), "###"," # ","###", '#', "ingotDoubleBlueSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil, 1, 7), "###"," # ","###", '#', "ingotDoubleRedSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil2, 1, 0), "###"," # ","###", '#', "ingotDoubleRoseGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil2, 1, 1), "###"," # ","###", '#', "ingotDoubleBismuthBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.anvil2, 1, 2), "###"," # ","###", '#', "ingotDoubleBlackBronze"));
//Devices
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.bloomery, 1), "PPP", "P P", "PPP", 'P', "plateDoubleAnyBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.blastFurnace, 1), "PPP", "PCP", "PPP", 'P', "plateDoubleWroughtIron", 'C', new ItemStack(TFCBlocks.crucible, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.spawnMeter, 1), "PPP", "GKG", "PPP", 'P', "stoneSmooth", 'K', "gemChipped", 'G', new ItemStack(Blocks.glass, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCBlocks.quern, 1), "PPP", 'P', "stoneSmooth"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.quern, 1), "  W", "PPP", 'P', "stone", 'W', "stickWood"));
//Fire Clay
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.clayBall, 1, 1), "PXP", "XCX", "PXP",
				'P', new ItemStack(TFCItems.powder, 1, 1), 'X', new ItemStack(TFCItems.powder, 1, 2), 'C', "lumpClay"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.clayBall, 1, 1), "PXP", "XCX", "PXP",
				'P', new ItemStack(TFCItems.powder, 1, 2), 'X', new ItemStack(TFCItems.powder, 1, 1), 'C', "lumpClay"));
		GameRegistry.addRecipe(new ItemStack(TFCItems.fireBrick, 2, 0), "PP", "PP", 'P', new ItemStack(TFCItems.clayBall, 1, 1));
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.fireBrick, 2, 0), "PXP", "XPX", "PXP", 'P', new ItemStack(TFCItems.fireBrick, 1, 1), 'X', new ItemStack(TFCItems.mortar, 1));
//Straw & Thatch
		GameRegistry.addRecipe(new ItemStack(TFCBlocks.thatch, 1), "PP", "PP", 'P', new ItemStack(TFCItems.straw, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.straw, 4), new ItemStack(TFCBlocks.thatch, 1));
//Coal
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.coal, 9), new ItemStack(Blocks.coal_block));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.coal_block, 1), "###", "###", "###", '#', "gemCoal"));
//Minecarts
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.rail, 64), "PsP","PsP", 'P', "ingotIron", 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.golden_rail, 64), " r ","PsP","PsP", 'P', "ingotGold", 's', "stickWood", 'r', Items.redstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.minecart, 2), "P P", "PPP", 'P', "plateWroughtIron"));
//Redstone
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.lever, 1), "P","H", 'P', "stickWood", 'H', "itemRock"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.comparator, 1), " T ", "TJT", "SSS", 'T', new ItemStack(Blocks.redstone_torch, 1), 'J', "oreJet", 'S', "stone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.dispenser, 1), "CCC", "CBC", "CRC", 'C', "stoneCobble", 'B', new ItemStack(TFCItems.bow, 1), 'R', Items.redstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.daylight_detector, 1), "GGG", "JRJ", "LLL", 'G', "blockGlassColorless", 'J', "oreJet", 'R', Items.redstone, 'L', "slabWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.hopper, 1), "H", "C", "h", 'H', "itemHammer", 'C', "chestWood", 'h', new ItemStack(TFCBlocks.hopper)));
//Misc Items
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.arrow, 8), "itemRock", "stickWood", new ItemStack(Items.feather)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 2, 0), "gemCharcoal", "dustSulfur", "dustSaltpeter"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame, 1), "###", "#$#", "###", '#', "stickWood", '$', "materialLeather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.painting, 1), "###", "#$#", "###", '#', "stickWood", '$', "materialCloth"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.carpet, 2, 0), "$$", '$', "materialCloth"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCBlocks.litPumpkin, 1), "blockTorch", "blockPumpkin"));
		//GameRegistry.addRecipe(new ItemStack(TFCItems.glassBottle, 3), "# #", " # ", '#', new ItemStack(Blocks.glass));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3), "###", '#', "itemReed"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.book, 1), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), "materialLeather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.compass, 1), "I", "R", "G", 'I', "oreSmallIron", 'R', Items.redstone, 'G', new ItemStack(TFCItems.goldPan)));
//Dying Recipes
		for (int i = 0; i < Global.DYE_NAMES.length; i++)
		{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.carpet, 1, i), Global.DYE_NAMES[i], new ItemStack(Blocks.carpet, 1, WILD)));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.potterySmallVessel, 1, 0), new ItemStack(TFCItems.potterySmallVessel, 1, 0), Global.DYE_NAMES[i]));
		}




		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.woolYarn, 8), "materialWool", new ItemStack(TFCItems.spindle, 1, WILD)));
//Markings & Blueprint
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.ink, 16, 0), "2", '2', "dyeBlack"));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blueprint, 1), new ItemStack(TFCItems.ink), new ItemStack(Items.paper, 1));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.nametag, 1), new ItemStack(TFCItems.ink), new ItemStack(Items.paper, 1), "materialString"));

		//Stone Stuff
		for (int j = 0; j < Global.STONE_IGIN.length; j++)
		{
			//Bricks
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneIgInBrick, 4, j), "PXP", "XPX", "PXP",
					'P', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGIN_START), 'X', new ItemStack(TFCItems.mortar, 1));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGIN_START), new ItemStack(TFCItems.looseRock, 1, j + Global.STONE_IGIN_START), "itemChisel"));

			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneIgInCobble,1,j),
					"PP","PP", 'P',new ItemStack(TFCItems.looseRock,1,j + Global.STONE_IGIN_START));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.looseRock,4,j + Global.STONE_IGIN_START),new ItemStack(TFCBlocks.stoneIgInCobble,1,j));

			// walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallCobbleIgIn, 4, j),
					"RRR", "RRR", 'R', new ItemStack(TFCItems.looseRock,1,j + Global.STONE_IGIN_START));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallRawIgIn, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneIgIn,1,j));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallBrickIgIn, 3, j),
					"BMB", "MBM", 'B', new ItemStack(TFCItems.stoneBrick,1,j + Global.STONE_IGIN_START), 'M',new ItemStack(TFCItems.mortar,1));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallSmoothIgIn, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneIgInSmooth,1,j));
		}

		for (int j = 0; j < Global.STONE_SED.length; j++)
		{
			//Bricks
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneSedBrick, 4, j), "PXP", "XPX", "PXP", 'P', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_SED_START), 'X', new ItemStack(TFCItems.mortar, 1));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_SED_START), new ItemStack(TFCItems.looseRock, 1, j + Global.STONE_SED_START), "itemChisel"));

			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneSedCobble,1,j),
					"PP","PP", 'P',new ItemStack(TFCItems.looseRock,1,j + Global.STONE_SED_START));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.looseRock,4,j + Global.STONE_SED_START),new ItemStack(TFCBlocks.stoneSedCobble,1,j));

			// walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallCobbleSed, 4, j),
					"RRR", "RRR", 'R', new ItemStack(TFCItems.looseRock,1,j + Global.STONE_SED_START));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallRawSed, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneSed,1,j));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallBrickSed, 3, j),
					"BMB", "MBM", 'B', new ItemStack(TFCItems.stoneBrick,1,j+Global.STONE_SED_START), 'M',new ItemStack(TFCItems.mortar,1));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallSmoothSed, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneSedSmooth,1,j));
		}

		for (int j = 0; j < Global.STONE_IGEX.length; j++)
		{
			//Bricks
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneIgExBrick, 4, j), "PXP", "XPX", "PXP", 'P', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGEX_START), 'X', new ItemStack(TFCItems.mortar, 1));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_IGEX_START), new ItemStack(TFCItems.looseRock, 1, j + Global.STONE_IGEX_START), "itemChisel"));

			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneIgExCobble,1,j),
					"PP","PP", 'P',new ItemStack(TFCItems.looseRock,1,j + Global.STONE_IGEX_START));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.looseRock,4,j + Global.STONE_IGEX_START),new ItemStack(TFCBlocks.stoneIgExCobble,1,j));

			//walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallCobbleIgEx, 4, j),
					"RRR", "RRR", 'R', new ItemStack(TFCItems.looseRock,1,j + Global.STONE_IGEX_START));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallRawIgEx, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneIgEx,1,j));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallBrickIgEx, 3, j),
					"BMB", "MBM", 'B',new ItemStack(TFCItems.stoneBrick,1,j+Global.STONE_IGEX_START), 'M',new ItemStack(TFCItems.mortar,1));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallSmoothIgEx, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneIgExSmooth,1,j));
		}

		for (int j = 0; j < Global.STONE_MM.length; j++)
		{
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneMMBrick, 4, j), "PXP", "XPX", "PXP", 'P', new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_MM_START), 'X', new ItemStack(TFCItems.mortar, 1));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.stoneBrick, 1, j + Global.STONE_MM_START), new ItemStack(TFCItems.looseRock, 1, j + Global.STONE_MM_START), "itemChisel"));

			// cobble <-> cobble block
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.stoneMMCobble,1,j),
					"PP","PP", 'P',new ItemStack(TFCItems.looseRock,1,j + Global.STONE_MM_START));
			GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.looseRock,4,j + Global.STONE_MM_START),new ItemStack(TFCBlocks.stoneMMCobble,1,j));

			//walls
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallCobbleMM, 4, j),
					"RRR", "RRR", 'R', new ItemStack(TFCItems.looseRock,1,j + Global.STONE_MM_START));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallRawMM, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneMM,1,j));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallBrickMM, 3, j),
					"BMB", "MBM", 'B',new ItemStack(TFCItems.stoneBrick,1,j+Global.STONE_MM_START), 'M',new ItemStack(TFCItems.mortar,1));
			GameRegistry.addRecipe(new ItemStack(TFCBlocks.wallSmoothMM, 3, j),
					"RRR", "RRR", 'R', new ItemStack(TFCBlocks.stoneMMSmooth,1,j));
		}

//Ore Piles Stuff

//Pile = i. Normal ore = i. Rich ore = (i+18). Poor ore = (i+36)
		for (int i = 0; i < 18; i++) {
//Rich to Poor
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.oreChunk, 3, i + 36), new ItemStack(TFCItems.oreChunk, 1, i + 18), "itemHammerTier3"));
//Poor to Pile
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.orePile, 1, i), new ItemStack(TFCItems.oreChunk, 1, i + 36), new ItemStack(TFCItems.oreChunk, 1, i + 36), new ItemStack(TFCItems.oreChunk, 1, i + 36), new ItemStack(TFCItems.oreChunk, 1, i + 36), "itemHammerTier3"));
//Normal to Pile
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.orePile, 1, i), new ItemStack(TFCItems.oreChunk, 1, i), new ItemStack(TFCItems.oreChunk, 1, i), "itemHammerTier3"));
//Rich to Pile
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.orePile, 3, i), new ItemStack(TFCItems.oreChunk, 1, i + 18), new ItemStack(TFCItems.oreChunk, 1, i + 18), new ItemStack(TFCItems.oreChunk, 1, i + 18), new ItemStack(TFCItems.oreChunk, 1, i + 18), "itemHammerTier3"));
//Poor + Normal to Pile
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.orePile, 1, i), new ItemStack(TFCItems.oreChunk, 1, i), new ItemStack(TFCItems.oreChunk, 1, i + 36), new ItemStack(TFCItems.oreChunk, 1, i + 36), "itemHammerTier3"));
//Poor + Rich to Pile
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.orePile, 1, i), new ItemStack(TFCItems.oreChunk, 1, i + 18), new ItemStack(TFCItems.oreChunk, 1, i + 36), "itemHammerTier3"));
		}
//Ingot -> Nugget
//Metals
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 0), new ItemStack(TFCItems.bismuthIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 1), new ItemStack(TFCItems.copperIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 2), new ItemStack(TFCItems.goldIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 3), new ItemStack(TFCItems.wroughtIronIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 4), new ItemStack(TFCItems.leadIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 5), new ItemStack(TFCItems.nickelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 6), new ItemStack(TFCItems.pigIronIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 7), new ItemStack(TFCItems.platinumIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 8), new ItemStack(TFCItems.silverIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 9), new ItemStack(TFCItems.steelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 10), new ItemStack(TFCItems.tinIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 11), new ItemStack(TFCItems.zincIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 12), new ItemStack(TFCItems.osmiumIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 13), new ItemStack(TFCItems.aluminumIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 14), new ItemStack(TFCItems.tungstenIngot), "itemChisel"));
//Alloys
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 15), new ItemStack(TFCItems.bismuthBronzeIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 16), new ItemStack(TFCItems.blackBronzeIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 17), new ItemStack(TFCItems.blackSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 18), new ItemStack(TFCItems.blueSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 19), new ItemStack(TFCItems.brassIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 20), new ItemStack(TFCItems.bronzeIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 21), new ItemStack(TFCItems.highCarbonBlackSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 22), new ItemStack(TFCItems.highCarbonBlueSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 23), new ItemStack(TFCItems.highCarbonRedSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 24), new ItemStack(TFCItems.redSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 25), new ItemStack(TFCItems.roseGoldIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 26), new ItemStack(TFCItems.sterlingSilverIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 27), new ItemStack(TFCItems.weakSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 28), new ItemStack(TFCItems.weakBlueSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 29), new ItemStack(TFCItems.weakRedSteelIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 30), new ItemStack(TFCItems.electrumIngot), "itemChisel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.smallMetalChunk, 10, 31), new ItemStack(TFCItems.cupronickelIngot), "itemChisel"));

//Alloy Dust Crafting
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 15), "CCC", "ZBZ", "CCC", 'C', "dustCopper", 'Z', "dustZinc", 'B', "dustBismuth"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 16), "CGC", "SCS", "CGC", 'C', "dustCopper", 'G', "dustGold", 'S', "dustSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 19), "CCC", "CZC", "CCC", 'C', "dustCopper", 'Z', "dustZinc"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 20), "CCC", "CTC", "CCC", 'C', "dustCopper", 'T', "dustTin"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.metalDust, 1, 21), "dustWeakSteel", "dustPigIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.metalDust, 1, 22), "dustWeakBlueSteel","dustBlackSteel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.metalDust, 1, 23), "dustWeakRedSteel", "dustBlackSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 25), "GGG", "CGC", "GGG", 'C', "dustCopper", 'G', "dustGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 26), "SSS", "CCC", "SSS", 'C', "dustCopper", 'S', "dustSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 27), "SNS", "BSB", "SNS", 'S', "dustSteel", 'N', "dustNickel", 'B', "dustBlackBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 28), "BbB", "SBS", "BsB", 'B', "dustBlackSteel", 'b', "dustBismuthBronze", 'S', "dustSteel", 's', "dustSterlingSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.metalDust, 9, 29), "BbB", "SBS", "BRB", 'B', "dustBlackSteel", 'b', "dustBrass", 'S', "dustSteel", 'R', "dustRoseGold"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.metalDust, 2, 30), "dustGold", "dustSilver"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.metalDust, 2, 31), "dustCopper", "dustNickel"));

//Ingot -> Unshaped
//Metals
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.bismuthIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.copperIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.goldUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.goldIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.wroughtIronUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.wroughtIronIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.leadUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.leadIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.nickelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.nickelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.pigIronUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.pigIronIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.platinumUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.platinumIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.silverUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.silverIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.steelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.steelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.tinUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.tinIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.zincUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.zincIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.osmiumUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.osmiumIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.aluminumUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.aluminumIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.tungstenUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.tungstenIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
//Alloys
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.bismuthBronzeIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.blackBronzeIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.blackSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blueSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.blueSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.brassUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.brassIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.bronzeIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.redSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.redSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.roseGoldUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.roseGoldIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.sterlingSilverUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.sterlingSilverIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.electrumUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.electrumIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.cupronickelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.cupronickelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
//Unusable Alloys
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonBlackSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonBlackSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonBlueSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonBlueSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonRedSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonRedSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.weakSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.weakSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.weakBlueSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.weakBlueSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.weakRedSteelUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.weakRedSteelIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.unknownUnshaped, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.unknownIngot, 1)), new ItemStack(TFCItems.ceramicMold, 1, 1));
//Unshaped -> Ingot
//Metals
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.bismuthUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.copperUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.goldIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.goldUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.wroughtIronIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.wroughtIronUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.leadIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.leadUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.nickelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.nickelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.pigIronIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.pigIronUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.platinumIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.platinumUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.silverIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.silverUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.steelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.steelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.tinIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.tinUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.zincIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.zincUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.osmiumIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.osmiumUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.aluminumIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.aluminumUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.tungstenIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.tungstenUnshaped, 1)));
//Alloys
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.blackSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blueSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.blueSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.brassIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.brassUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.bronzeUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.redSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.redSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.roseGoldIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.roseGoldUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.sterlingSilverIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.sterlingSilverUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.electrumIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.electrumUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.cupronickelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.cupronickelUnshaped, 1)));
//Unusable
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonBlackSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonBlackSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonBlueSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonBlueSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.highCarbonRedSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.highCarbonRedSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.weakSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.weakSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.weakBlueSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.weakBlueSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.weakRedSteelIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.weakRedSteelUnshaped, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.unknownIngot, 1, 0),
				getStackNoTemp(new ItemStack(TFCItems.unknownUnshaped, 1)));

		registerToolRecipes();
		registerFoodRecipes();
		registerKilnRecipes();
		registerToolMolds();
		registerQuernRecipes();
	}


	/*private static ItemStack checkMelted(ItemStack is)
	{
		if(TFC_ItemHeat.GetTemp(is) > TFC_ItemHeat.IsCookable(is))
			return null;
		return is;
	}*/

	private static void vanillaRecipes()
	{
		// With oredict in place, we MUST remove the vanilla crafting table.
		// Otherwise TFC can not use the 4 planks recipe to create its own crafting upgrade.
		removeRecipe(new ItemStack(Blocks.crafting_table));
		// Other Conflicting Recipes
		removeRecipe(new ItemStack(Items.fishing_rod));
		removeRecipe(new ItemStack(Blocks.wooden_button));
		removeRecipe(new ItemStack(Items.flint_and_steel));
		removeRecipe(new ItemStack(Items.coal, 9));
		removeRecipe(new ItemStack(Items.sugar));
		//removeRecipe(new ItemStack(Items.glass_bottle, 3));
		removeRecipe(new ItemStack(Items.paper, 3));
		removeRecipe(new ItemStack(Items.comparator));
		removeRecipe(new ItemStack(Blocks.dispenser));
		removeRecipe(new ItemStack(Blocks.daylight_detector));
		removeRecipe(new ItemStack(Items.compass));

		//Have to do this by class for some items that are overriden like the bow
		removeRecipe(ItemBow.class);

		// Needs items to be available, but the TFC recipes can't yet be registerd
		TFC_ConfigFiles.firstLoadCrafting();
	}

	private static void registerToolRecipes()
	{
		//Misc Tools
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.flintSteel, 1), Items.flint, "ingotIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TFCItems.flintSteel, 1), Items.flint, "ingotSteel"));

		GameRegistry.addRecipe(new ItemStack(TFCItems.rope, 1), "RR ", "RR ", "  R", 'R', new ItemStack(TFCItems.juteFiber, 1));

		GameRegistry.addRecipe(new ItemStack(TFCItems.goldPan, 1, 0), "1", '1', new ItemStack(TFCItems.potteryBowl, 1, 1));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.fireStarter, 1, 0), "2 ", " 2", '2', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bow, 1), " #$", "# $", " #$", '#', "stickWood", '$', "materialString"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.fishingRod, 1), " #", "#$", '#', "stickWood", '$', "materialString"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.spindle, 1), "P", "#", 'P', new ItemStack(TFCItems.spindleHead, 1, 1), '#', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.spindleHead, 1, 0), "P", "#", 'P', "lumpClay", '#', "stickWood"));

		//stone javelins
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igInStoneJavelin, 1, 0), "1","2", '1', TFCItems.igInStoneJavelinHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.sedStoneJavelin, 1, 0), "1","2", '1', TFCItems.sedStoneJavelinHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igExStoneJavelin, 1, 0), "1","2", '1', TFCItems.igExStoneJavelinHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.mMStoneJavelin, 1, 0), "1","2", '1', TFCItems.mMStoneJavelinHead, '2', "stickWood"));

		//stone shovels
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igInShovel, 1, 0), "1","2", '1', TFCItems.igInStoneShovelHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.sedShovel, 1, 0), "1","2", '1', TFCItems.sedStoneShovelHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igExShovel, 1, 0), "1","2", '1', TFCItems.igExStoneShovelHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.mMShovel, 1, 0), "1","2", '1', TFCItems.mMStoneShovelHead, '2', "stickWood"));
		//stone axes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igInAxe, 1, 0), "1","2", '1', TFCItems.igInStoneAxeHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.sedAxe, 1, 0), "1","2", '1', TFCItems.sedStoneAxeHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igExAxe, 1, 0), "1","2", '1', TFCItems.igExStoneAxeHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.mMAxe, 1, 0), "1","2", '1', TFCItems.mMStoneAxeHead, '2', "stickWood"));
		//stone hoes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igInHoe, 1, 0), "1","2", '1', TFCItems.igInStoneHoeHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.sedHoe, 1, 0), "1","2", '1', TFCItems.sedStoneHoeHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.igExHoe, 1, 0), "1","2", '1', TFCItems.igExStoneHoeHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.mMHoe, 1, 0), "1","2", '1', TFCItems.mMStoneHoeHead, '2', "stickWood"));

		//bone shovels
		GameRegistry.addRecipe(new ItemStack(TFCItems.igInShovel, 1, 0), "1", "2", '1', TFCItems.igInStoneShovelHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.sedShovel, 1, 0), "1", "2", '1', TFCItems.sedStoneShovelHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.igExShovel, 1, 0), "1", "2", '1', TFCItems.igExStoneShovelHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.mMShovel, 1, 0), "1", "2", '1', TFCItems.mMStoneShovelHead, '2', new ItemStack(Items.bone));
		//bone axes
		GameRegistry.addRecipe(new ItemStack(TFCItems.igInAxe, 1, 0), "1", "2", '1', TFCItems.igInStoneAxeHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.sedAxe, 1, 0), "1", "2", '1', TFCItems.sedStoneAxeHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.igExAxe, 1, 0), "1", "2", '1', TFCItems.igExStoneAxeHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.mMAxe, 1, 0), "1", "2", '1', TFCItems.mMStoneAxeHead, '2', new ItemStack(Items.bone));
		//bone hoes
		GameRegistry.addRecipe(new ItemStack(TFCItems.igInHoe, 1, 0), "1", "2", '1', TFCItems.igInStoneHoeHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.sedHoe, 1, 0), "1", "2", '1', TFCItems.sedStoneHoeHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.igExHoe, 1, 0), "1", "2", '1', TFCItems.igExStoneHoeHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.mMHoe, 1, 0), "1", "2", '1', TFCItems.mMStoneHoeHead, '2', new ItemStack(Items.bone));
		//bone javelins
		GameRegistry.addRecipe(new ItemStack(TFCItems.igInStoneJavelin, 1, 0), "1", "2", '1', TFCItems.igInStoneJavelinHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.sedStoneJavelin, 1, 0), "1", "2", '1', TFCItems.sedStoneJavelinHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.igExStoneJavelin, 1, 0), "1", "2", '1', TFCItems.igExStoneJavelinHead, '2', new ItemStack(Items.bone));
		GameRegistry.addRecipe(new ItemStack(TFCItems.mMStoneJavelin, 1, 0), "1", "2", '1', TFCItems.mMStoneJavelinHead, '2', new ItemStack(Items.bone));


		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.stoneHammer, 1, 0), "1","2", '1', TFCItems.stoneHammerHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.stoneHammer, 1, 0), "1", "2", '1', TFCItems.stoneHammerHead, '2', new ItemStack(Items.bone)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.stoneKnife, 1, 0), "1","2", '1', TFCItems.stoneKnifeHead, '2', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.stoneKnife, 1, 0), "1", "2", '1', TFCItems.stoneKnifeHead, '2', new ItemStack(Items.bone)));

		//pickaxes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzePick, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzePickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzePick, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzePickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelPick, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelPickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelPick, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelPickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzePick, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzePickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperPick, 1), "#","I", '#',
				new ItemStack(TFCItems.copperPickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronPick, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronPickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelPick, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelPickaxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelPick, 1), "#","I", '#',
				new ItemStack(TFCItems.steelPickaxeHead, 1, 0), 'I', "stickWood"));

		//Shovels
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.copperShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelShovelHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelShovel, 1), "#","I", '#',
				new ItemStack(TFCItems.steelShovelHead, 1, 0), 'I', "stickWood"));

		//Hoes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.copperHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelHoeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelHoe, 1), "#","I", '#',
				new ItemStack(TFCItems.steelHoeHead, 1, 0), 'I', "stickWood"));

		//Axes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.copperAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelAxeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelAxe, 1), "#","I", '#',
				new ItemStack(TFCItems.steelAxeHead, 1, 0), 'I', "stickWood"));

		//Swords
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeSword, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeSword, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelSword, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelSword, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeSword, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperSword, 1), "#","I", '#',
				new ItemStack(TFCItems.copperSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronSword, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelSword, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelSwordHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelSword, 1), "#","I", '#',
				new ItemStack(TFCItems.steelSwordHead, 1, 0), 'I', "stickWood"));

		//Maces
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeMace, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeMace, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelMace, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelMace, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeMace, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperMace, 1), "#","I", '#',
				new ItemStack(TFCItems.copperMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronMace, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelMace, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelMaceHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelMace, 1), "#","I", '#',
				new ItemStack(TFCItems.steelMaceHead, 1, 0), 'I', "stickWood"));

		//Hammers
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.copperHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelHammerHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelHammer, 1), "#","I", '#',
				new ItemStack(TFCItems.steelHammerHead, 1, 0), 'I', "stickWood"));

		//Saws
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.copperSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelSawHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelSaw, 1), "#","I", '#',
				new ItemStack(TFCItems.steelSawHead, 1, 0), 'I', "stickWood"));

		//Chisels
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.copperChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelChiselHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelChisel, 1), "#","I", '#',
				new ItemStack(TFCItems.steelChiselHead, 1, 0), 'I', "stickWood"));

		//propicks
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickBismuthBronze, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickBlackBronze, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickBlackSteel, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickBlueSteel, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickBronze, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickCopper, 1), "#","I", '#',
				new ItemStack(TFCItems.copperProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickIron, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickRedSteel, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelProPickHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.proPickSteel, 1), "#","I", '#',
				new ItemStack(TFCItems.steelProPickHead, 1, 0), 'I', "stickWood"));
		//Scythes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.copperScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelScytheHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelScythe, 1), "#","I", '#',
				new ItemStack(TFCItems.steelScytheHead, 1, 0), 'I', "stickWood"));
		//Knifes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.copperKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelKnifeHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelKnife, 1), "#","I", '#',
				new ItemStack(TFCItems.steelKnifeHead, 1, 0), 'I', "stickWood"));

		//javelins
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bismuthBronzeJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.bismuthBronzeJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackBronzeJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.blackBronzeJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blackSteelJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.blackSteelJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.blueSteelJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.blueSteelJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.bronzeJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.bronzeJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.copperJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.copperJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.wroughtIronJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.wroughtIronJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.redSteelJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.redSteelJavelinHead, 1, 0), 'I', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TFCItems.steelJavelin, 1), "#","I", '#',
				new ItemStack(TFCItems.steelJavelinHead, 1, 0), 'I', "stickWood"));

		//clay molds
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldPick, 1), new Object[] { "     "," ### ","#   #", "     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldShovel, 1), new Object[] { " ### "," ### "," ### "," ### ","  #  ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHoe, 1), new Object[] { "     ","#####","   ##","     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldAxe, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHammer, 1), new Object[] { "     ","#####","#####","  #  ","     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldChisel, 1), new Object[] { "  #  ","  #  ","  #  ","  #  ","  #  ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSword, 1), new Object[] { "   ##","  ###"," ### "," ##  ","#    ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldMace, 1), new Object[] { "  #  "," ### "," ### "," ### ","  #  ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSaw, 1), new Object[] { "##   ","###  "," ### "," ####","   ##", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldProPick, 1), new Object[] { "     "," ####","#   #","    #","     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldScythe, 1), new Object[] { "     ","#### "," ####","   ##","     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldKnife, 1), new Object[] { "  # "," ## "," ## "," ## "," ## ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldJavelin, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});

		registerAlloys();

		registerKnapping();

		//Leather Working
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.leatherHelmet, 1), new Object[] { "#####","#   #","#   #", '#', TFCItems.flatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.leatherChestplate, 1), new Object[] { "#   #","#####","#####","#####","#####", '#', TFCItems.flatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.leatherLeggings, 1), new Object[] { "#####","#####","## ##","## ##","## ##", '#', TFCItems.flatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.leatherBoots, 1), new Object[] { "##   ","##   ","##   ","#### ","#####", '#', TFCItems.flatLeather});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.quiver, 1), new Object[] { " ####","# ###","# ###","# ###"," ####", '#', TFCItems.flatLeather});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(Items.saddle, 1), new Object[] { "  #  ","#####","#####","#####","  #  ", '#', TFCItems.flatLeather});
	}

	private static void registerKnapping()
	{
		//Knapping
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.stoneKnifeHead, 2), new Object[]
				{ " #  #", "## ##", "## ##", "## ##", "## ##", '#', new ItemStack(TFCItems.flatRock, 1, WILD) });
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.stoneKnifeHead, 2), new Object[]
				{ "#  # ", "## ##", "## ##", "## ##", "## ##", '#', new ItemStack(TFCItems.flatRock, 1, WILD) });
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.stoneKnifeHead, 2), new Object[]
				{ "#   #", "## ##", "## ##", "## ##", "## ##", '#', new ItemStack(TFCItems.flatRock, 1, WILD) });
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.stoneKnifeHead, 2), new Object[]
				{ " # # ", "## ##", "## ##", "## ##", "## ##", '#', new ItemStack(TFCItems.flatRock, 1, WILD) });
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.stoneKnifeHead, 1), new Object[]
				{ " #", "##", "##", "##", "##", '#', new ItemStack(TFCItems.flatRock, 1, WILD) });
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.stoneHammerHead, 1), new Object[]
				{ "#####", "#####", "  #  ", '#', new ItemStack(TFCItems.flatRock, 1, WILD) });

		for(int i = 0; i < Global.STONE_IGIN.length; i++)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igInStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igInStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igInStoneHoeHead, 1), new Object[] { "#####","   ##", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGIN_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igInStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGIN_START)});
		}
		for(int i = 0; i < Global.STONE_SED.length; i++)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.sedStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.sedStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.sedStoneHoeHead, 1), new Object[] { "#####","   ##", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_SED_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.sedStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_SED_START)});
		}
		for(int i = 0; i < Global.STONE_IGEX.length; i++)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igExStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igExStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igExStoneHoeHead, 1), new Object[] { "#####","   ##", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGEX_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.igExStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_IGEX_START)});
		}
		for(int i = 0; i < Global.STONE_MM.length; i++)
		{
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.mMStoneShovelHead, 1), new Object[] { "###","###","###","###"," # ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_MM_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.mMStoneAxeHead, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_MM_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.mMStoneHoeHead, 1), new Object[] { "#####","   ##", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_MM_START)});
			CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.mMStoneJavelinHead, 1), new Object[] { "###  ","#### ","#####"," ### ","  #  ", '#', new ItemStack(TFCItems.flatRock, 1, i + Global.STONE_MM_START)});
		}


		//Inverse Clay Knapping
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ceramicMold, 2, 0), new Object[] { 
			"    ",
			" ## ",
			" ## ",
			" ## ",
			"    ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.potteryJug, 1, 0), new Object[] { 
			"X XXX",
			"    X",
			"   X ",
			"    X",
			"   XX", 'X', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.potterySmallVessel, 1, 0), new Object[] { 
			"#   #",
			"     ",
			"     ",
			"     ",
			"#   #", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCBlocks.flowerPot), new Object[] { 
			"#   #",
			" ### ",
			" ### ",
			" ### ",
			"#   #", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCBlocks.crucible, 1), new Object[] { 
			" ### ",
			" ### ",
			" ### ",
			" ### ",
			"     ", '#', new ItemStack(TFCItems.flatClay, 1, 3)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCBlocks.vessel, 1), new Object[] { 
			" ### ",
			" ### ",
			" ### ",
			" ### ",
			"     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		/* Moved to TFC_ConfigFiles.firstLoadCrafting(), as it is dependant on a configureation option.
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.potteryBowl, 2), new Object[]
		{
			"#####",
			"#####",
			"#####",
			" ### ",
			"#   #", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
		*/
	}

	private static void registerAlloys()
	{
		/**metallurgy*/
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.copperUnshaped),new ItemStack(TFCItems.copperUnshaped),
			new ItemStack(TFCItems.tinUnshaped), new ItemStack(TFCItems.bismuthUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.copperUnshaped),new ItemStack(TFCItems.copperUnshaped),
			new ItemStack(TFCItems.zincUnshaped), new ItemStack(TFCItems.bismuthUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.blackBronzeUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.copperUnshaped),new ItemStack(TFCItems.copperUnshaped),
			new ItemStack(TFCItems.silverUnshaped), new ItemStack(TFCItems.goldUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.weakSteelUnshaped, 4), 
				new Object[] {  new ItemStack(TFCItems.steelUnshaped),new ItemStack(TFCItems.steelUnshaped),
			new ItemStack(TFCItems.nickelUnshaped), new ItemStack(TFCItems.blackBronzeUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.weakBlueSteelUnshaped, 4),
				new Object[]{new ItemStack(TFCItems.blackSteelUnshaped), new ItemStack(TFCItems.bismuthBronzeUnshaped),
						new ItemStack(TFCItems.sterlingSilverUnshaped), new ItemStack(TFCItems.steelUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.brassUnshaped, 4),
				new Object[]{new ItemStack(TFCItems.copperUnshaped), new ItemStack(TFCItems.copperUnshaped),
						new ItemStack(TFCItems.copperUnshaped), new ItemStack(TFCItems.zincUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.bronzeUnshaped, 4),
				new Object[]{new ItemStack(TFCItems.copperUnshaped), new ItemStack(TFCItems.copperUnshaped),
						new ItemStack(TFCItems.copperUnshaped), new ItemStack(TFCItems.tinUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.weakRedSteelUnshaped, 4),
				new Object[]{new ItemStack(TFCItems.blackSteelUnshaped), new ItemStack(TFCItems.roseGoldUnshaped),
						new ItemStack(TFCItems.brassUnshaped), new ItemStack(TFCItems.steelUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.roseGoldUnshaped, 4),
				new Object[]{new ItemStack(TFCItems.copperUnshaped), new ItemStack(TFCItems.goldUnshaped),
						new ItemStack(TFCItems.goldUnshaped), new ItemStack(TFCItems.goldUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.highCarbonSteelUnshaped, 4),
				new Object[]{new ItemStack(TFCItems.pigIronUnshaped), new ItemStack(TFCItems.wroughtIronUnshaped),
						new ItemStack(TFCItems.wroughtIronUnshaped), new ItemStack(TFCItems.wroughtIronUnshaped)});

		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.sterlingSilverUnshaped, 4),
				new Object[]{new ItemStack(TFCItems.copperUnshaped), new ItemStack(TFCItems.silverUnshaped),
						new ItemStack(TFCItems.silverUnshaped), new ItemStack(TFCItems.silverUnshaped)});
	}

	private static void registerToolMolds()
	{
		////////////////////////////////////////////////////////////////
		//Mold pouring
		////////////////////////////////////////////////////////////////        
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldAxe, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldAxe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldAxe, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldAxe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldAxe, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldAxe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldAxe, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldAxe, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldChisel, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldChisel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldChisel, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldChisel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldChisel, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldChisel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldChisel, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldChisel, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHammer, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHammer, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHammer, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHammer, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHammer, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHammer, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHammer, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHammer, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHoe, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHoe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHoe, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHoe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHoe, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHoe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldHoe, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldHoe, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldKnife, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldKnife, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldKnife, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldKnife, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldKnife, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldKnife, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldKnife, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldKnife, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldJavelin, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldJavelin, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldJavelin, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldJavelin, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldJavelin, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldJavelin, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldJavelin, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldJavelin, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldMace, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldMace, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldMace, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldMace, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldMace, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldMace, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldMace, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldMace, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldPick, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldPick, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldPick, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldPick, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldPick, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldProPick, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldProPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldProPick, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldProPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldProPick, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldProPick, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldProPick, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldProPick, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSaw, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSaw, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSaw, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSaw, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSaw, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSaw, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSaw, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSaw, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldScythe, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldScythe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldScythe, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldScythe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldScythe, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldScythe, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldScythe, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldScythe, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldShovel, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldShovel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldShovel, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldShovel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldShovel, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldShovel, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldShovel, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldShovel, 1, 1)});

		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSword, 1, 2), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.copperUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSword, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSword, 1, 3), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSword, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSword, 1, 4), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.bismuthBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSword, 1, 1)});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.clayMoldSword, 1, 5), 
				new Object[] {"12", '1', getStackTemp(new ItemStack(TFCItems.blackBronzeUnshaped, 1, 1)), '2', new ItemStack(TFCItems.clayMoldSword, 1, 1)});
		////////////////////////////////////////////////////////////////
		//Actual Tools
		////////////////////////////////////////////////////////////////
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperAxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldAxe, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeAxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldAxe, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeAxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldAxe, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeAxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldAxe, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperChiselHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldChisel, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeChiselHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldChisel, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeChiselHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldChisel, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeChiselHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldChisel, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperHammerHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHammer, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeHammerHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHammer, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeHammerHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHammer, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeHammerHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHammer, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperHoeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHoe, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeHoeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHoe, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeHoeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHoe, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeHoeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldHoe, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperKnifeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldKnife, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeKnifeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldKnife, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeKnifeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldKnife, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeKnifeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldKnife, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperJavelinHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldJavelin, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeJavelinHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldJavelin, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeJavelinHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldJavelin, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeJavelinHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldJavelin, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperMaceHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldMace, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeMaceHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldMace, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeMaceHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldMace, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeMaceHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldMace, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperPickaxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldPick, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzePickaxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldPick, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzePickaxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldPick, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzePickaxeHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldPick, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperProPickHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldProPick, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeProPickHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldProPick, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeProPickHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldProPick, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeProPickHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldProPick, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperSawHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSaw, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeSawHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSaw, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeSawHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSaw, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeSawHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSaw, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperScytheHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldScythe, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeScytheHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldScythe, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeScytheHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldScythe, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeScytheHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldScythe, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperShovelHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldShovel, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeShovelHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldShovel, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeShovelHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldShovel, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeShovelHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldShovel, 1, 5)));

		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.copperSwordHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSword, 1, 2)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bronzeSwordHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSword, 1, 3)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.bismuthBronzeSwordHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSword, 1, 4)));
		GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.blackBronzeSwordHead),
				getStackNoTemp(new ItemStack(TFCItems.clayMoldSword, 1, 5)));
	}

	public static ItemStack getStackTemp(ItemStack is)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("temp", true);
		is.setTagCompound(nbt);
		return is;
	}

	public static ItemStack	getStackNoTemp(ItemStack is)
	{
		NBTTagCompound noTemp = new NBTTagCompound();
		noTemp.setBoolean("noTemp", true);
		is.setTagCompound(noTemp);
		return is;
	}

	public static void registerAnvilRecipes(Random r, World world)
	{
		AnvilManager manager = AnvilManager.getInstance();
		//We need to set the world ref so that all anvil recipes can generate correctly
		AnvilManager.world = world;

		manager.addPlan("ingot", new PlanRecipe(
				new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		manager.addPlan("sheet", new PlanRecipe(
				new RuleEnum[] {RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.HITTHIRDFROMLAST}));
		manager.addPlan("block", new PlanRecipe(
				new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
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
		manager.addPlan("oillamp", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.DRAWTHIRDFROMLAST}));
		manager.addPlan("hopper", new PlanRecipe(new RuleEnum[]{RuleEnum.UPSETLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));

		addWeldRecipes(manager);

		/**
		 * Normal Recipes Start Here
		 */
//Processing Ingots and Blooms
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.rawBloom, 1, WILD), null, "refinebloom", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bloom, 1)).setInheritsDamage().clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bloom, 1, 100), null , "refinebloom", AnvilReq.BRONZE,
				new ItemStack(TFCItems.wroughtIronIngot, 1)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bloom, 1, WILD), null, "splitbloom", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bloom, 1)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.pigIronIngot), null, "ingot", false, AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.highCarbonSteelIngot)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.highCarbonBlackSteelIngot), null, "ingot", false, AnvilReq.STEEL,
				new ItemStack(TFCItems.blackSteelIngot)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.highCarbonBlueSteelIngot), null, "ingot", false, AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blueSteelIngot)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.highCarbonRedSteelIngot), null, "ingot",  false, AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.redSteelIngot)).clearRecipeSkills());
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.highCarbonSteelIngot), null, "ingot", false, AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.steelIngot)).clearRecipeSkills());
//Sheets
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthIngot2x), null, "sheet", false, AnvilReq.STONE,
				new ItemStack(TFCItems.bismuthSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot2x), null, "sheet", false, AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot2x), null, "sheet", false, AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot2x), null, "sheet", false, AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot2x), null, "sheet", false, AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.brassIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.brassSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot2x), null, "sheet", false, AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.copperSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.goldIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.goldSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot2x), null, "sheet", false, AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.leadIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.leadSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.nickelIngot2x), null, "sheet", false, AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.nickelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.pigIronIngot2x), null, "sheet", false, AnvilReq.BRONZE,
				new ItemStack(TFCItems.pigIronSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.platinumIngot2x), null, "sheet", false, AnvilReq.BRONZE,
				new ItemStack(TFCItems.platinumSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot2x), null, "sheet", false, AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.roseGoldIngot2x), null, "sheet", false, AnvilReq.BRONZE,
				new ItemStack(TFCItems.roseGoldSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.silverIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.silverSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot2x), null, "sheet", false, AnvilReq.STEEL,
				new ItemStack(TFCItems.steelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.sterlingSilverIngot2x), null, "sheet", false, AnvilReq.BRONZE,
				new ItemStack(TFCItems.sterlingSilverSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.tinIngot2x), null, "sheet", false, AnvilReq.STONE,
				new ItemStack(TFCItems.tinSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.zincIngot2x), null, "sheet", false, AnvilReq.STONE,
				new ItemStack(TFCItems.zincSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.electrumIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.electrumSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.cupronickelIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.cupronickelSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.osmiumIngot2x), null, "sheet", false, AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.osmiumSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.aluminumIngot2x), null, "sheet", false, AnvilReq.COPPER,
				new ItemStack(TFCItems.aluminumSheet)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.tungstenIngot2x), null, "sheet", false, AnvilReq.STEEL,
				new ItemStack(TFCItems.tungstenSheet)));
//Sheets2x
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeSheet2x), new ItemStack(TFCItems.bismuthBronzeSheet2x),"block", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 0)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeSheet2x), new ItemStack(TFCItems.blackBronzeSheet2x),"block", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelSheet2x), new ItemStack(TFCItems.blackSteelSheet2x),"block", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 2)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet2x), new ItemStack(TFCItems.blueSteelSheet2x),"block", AnvilReq.BLUESTEEL,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 3)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.brassSheet2x), new ItemStack(TFCItems.brassSheet2x),"block", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 4)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeSheet2x), new ItemStack(TFCItems.bronzeSheet2x),"block", AnvilReq.BRONZE,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 5)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet2x), new ItemStack(TFCItems.redSteelSheet2x),"block", AnvilReq.REDSTEEL,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 6)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.roseGoldSheet2x), new ItemStack(TFCItems.roseGoldSheet2x),"block", AnvilReq.BRONZE,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 7)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.sterlingSilverSheet2x), new ItemStack(TFCItems.sterlingSilverSheet2x),"block", AnvilReq.BRONZE,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 8)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.electrumSheet2x), new ItemStack(TFCItems.electrumSheet2x),"block", AnvilReq.STONE,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 9)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.cupronickelSheet2x), new ItemStack(TFCItems.cupronickelSheet2x),"block", AnvilReq.STONE,
				new ItemStack(TFCBlocks.metalAlloyBlock, 1, 10)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
//Blocks
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthSheet2x), new ItemStack(TFCItems.bismuthSheet2x),"block", AnvilReq.STONE,
				new ItemStack(TFCBlocks.metalBlock, 1, 0)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperSheet2x), new ItemStack(TFCItems.copperSheet2x),"block", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalBlock, 1, 1)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.goldSheet2x), new ItemStack(TFCItems.goldSheet2x),"block", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalBlock, 1, 2)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet2x), new ItemStack(TFCItems.wroughtIronSheet2x),"block", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCBlocks.metalBlock, 1, 3)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.leadSheet2x), new ItemStack(TFCItems.leadSheet2x),"block", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalBlock, 1, 4)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.nickelSheet2x), new ItemStack(TFCItems.nickelSheet2x),"block", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCBlocks.metalBlock, 1, 5)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.pigIronSheet2x), new ItemStack(TFCItems.pigIronSheet2x),"block", AnvilReq.BRONZE,
				new ItemStack(TFCBlocks.metalBlock, 1, 6)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.platinumSheet2x), new ItemStack(TFCItems.platinumSheet2x),"block", AnvilReq.BRONZE,
				new ItemStack(TFCBlocks.metalBlock, 1, 7)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.silverSheet2x), new ItemStack(TFCItems.silverSheet2x),"block", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalBlock, 1, 8)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelSheet2x), new ItemStack(TFCItems.steelSheet2x),"block", AnvilReq.STEEL,
				new ItemStack(TFCBlocks.metalBlock, 1, 9)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.tinSheet2x), new ItemStack(TFCItems.tinSheet2x),"block", AnvilReq.STONE,
				new ItemStack(TFCBlocks.metalBlock, 1, 10)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.zincSheet2x), new ItemStack(TFCItems.zincSheet2x),"block", AnvilReq.STONE,
				new ItemStack(TFCBlocks.metalBlock, 1, 11)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.osmiumSheet2x), new ItemStack(TFCItems.osmiumSheet2x),"block", AnvilReq.BLUESTEEL,
				new ItemStack(TFCBlocks.metalBlock, 1, 12)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.aluminumSheet2x), new ItemStack(TFCItems.aluminumSheet2x),"block", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalBlock, 1, 13)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.tungstenSheet2x), new ItemStack(TFCItems.tungstenSheet2x),"block", AnvilReq.STEEL,
				new ItemStack(TFCBlocks.metalBlock, 1, 14)).addRecipeSkill(Global.SKILL_GENERAL_SMITHING).setCraftingXP(1));
//Tools
		//Picks
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "pickaxe", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzePickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, "pickaxe", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzePickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, "pickaxe", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, "pickaxe", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, "pickaxe", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzePickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "pickaxe", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "pickaxe", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, "pickaxe", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, "pickaxe", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//shovels
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "shovel",  AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, "shovel", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, "shovel", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, "shovel",  AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, "shovel",  AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "shovel", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "shovel", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, "shovel", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, "shovel", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//axes
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "axe", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, "axe",  AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, "axe",  AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, "axe",  AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, "axe",  AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "axe",  AnvilReq.COPPER,
				new ItemStack(TFCItems.copperAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "axe",  AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, "axe",  AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, "axe", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//hoes
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "hoe", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, "hoe", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, "hoe", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, "hoe", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, "hoe", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "hoe", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "hoe", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, "hoe", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, "hoe", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//Hammers
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "hammer", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, "hammer", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, "hammer", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, "hammer", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, "hammer", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "hammer", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "hammer", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, "hammer", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, "hammer",AnvilReq.STEEL,
				new ItemStack(TFCItems.steelHammerHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//Chisels
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "chisel", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, "chisel", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, "chisel", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, "chisel", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, "chisel", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "chisel", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "chisel", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, "chisel", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, "chisel", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelChiselHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//ProPicks
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "propick", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null, "propick", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null, "propick", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null, "propick", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null, "propick", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "propick", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "propick", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null, "propick", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null, "propick", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelProPickHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//Saws
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "saw", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null,"saw", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null,"saw", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null,"saw", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null,"saw", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null,"saw", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null,"saw", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null,"saw", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null,"saw", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelSawHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//Scythes
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null, "scythe", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null,"scythe", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot),null,"scythe", false, AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null,"scythe", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null,"scythe", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null,"scythe", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null,"scythe", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null,"scythe", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null,"scythe", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelScytheHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
//Shears
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronKnifeHead), new ItemStack(TFCItems.wroughtIronKnifeHead),"shears", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.shears, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelKnifeHead), new ItemStack(TFCItems.blackSteelKnifeHead),"shears", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.shearsBlackSteel, 1, 0)));
//Weapons
//Knifes
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot),null,"knife", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null,"knife", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null,"knife", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null,"knife", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null,"knife", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null,"knife", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null,"knife", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronKnifeHead, 1)).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null,"knife", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null,"knife", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelKnifeHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH).setCraftingBound(40));
//Swords
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot2x), null, "sword", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot2x), null, "sword", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot2x), null, "sword", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot2x), null, "sword", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot2x), null, "sword", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot2x), null, "sword", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot2x), null, "sword", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot2x), null, "sword", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot2x), null, "sword", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
//Maces
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot2x), null, "mace", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot2x), null, "mace", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot2x), null, "mace", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot2x), null, "mace", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot2x), null, "mace", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot2x), null, "mace", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot2x), null, "mace", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot2x), null, "mace", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot2x), null, "mace", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelMaceHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
//javelin
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot), null,"javelin", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot), null,"javelin", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot), null,"javelin", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot), null,"javelin", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null,"javelin", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot), null,"javelin", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot), null,"javelin", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null,"javelin", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot), null,"javelin", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperJavelinHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
//Armour
//Helmet Unfinished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeSheet), null,"helmPlate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeSheet), null,"helmPlate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelSheet), null,"helmPlate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet), null,"helmPlate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeSheet), null,"helmPlate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperSheet), null,"helmPlate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet), null,"helmPlate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet), null,"helmPlate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelSheet), null,"helmPlate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelUnfinishedHelmet, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
//Helmet Finished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedHelmet,1,1), null,"helmPlate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelHelmet, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
//Chestpiece Unfinished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeSheet2x), null,"chestPlate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeSheet2x), null,"chestPlate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelSheet2x), null,"chestPlate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet2x), null,"chestPlate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeSheet2x), null,"chestPlate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperSheet2x), null,"chestPlate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet2x), null,"chestPlate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet2x), null,"chestPlate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelSheet2x), null,"chestPlate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelUnfinishedChestplate, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
//Chestpiece Finished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedChestplate,1,1), null,"chestPlate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelChestplate, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
//Greaves Unfinished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeSheet2x), null,"legsPlate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeSheet2x), null,"legsPlate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelSheet2x), null,"legsPlate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet2x), null,"legsPlate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeSheet2x), null,"legsPlate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperSheet2x), null,"legsPlate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet2x), null,"legsPlate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet2x), null, "legsPlate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelSheet2x), null, "legsPlate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelUnfinishedGreaves, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
//Greaves Finished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedGreaves,1,1), null,"legsPlate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelGreaves, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
//Boots Unfinished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeSheet), null,"bootsplate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeSheet), null,"bootsplate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelSheet), null,"bootsplate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet), null,"bootsplate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeSheet), null,"bootsplate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperSheet), null,"bootsplate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet), null,"bootsplate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet), null,"bootsplate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelSheet), null,"bootsplate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelUnfinishedBoots, 1, 0)).addRecipeSkill(Global.SKILL_ARMORSMITH));
//Boots Finished
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BISMUTHBRONZE,
				new ItemStack(TFCItems.bismuthBronzeBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BLACKBRONZE,
				new ItemStack(TFCItems.blackBronzeBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blackSteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.BRONZE,
				new ItemStack(TFCItems.bronzeBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.COPPER,
				new ItemStack(TFCItems.copperBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.wroughtIronBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedBoots,1,1), null,"bootsplate", AnvilReq.STEEL,
				new ItemStack(TFCItems.steelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(2));
//Items
//Grill
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot2x), new ItemStack(TFCItems.wroughtIronIngot2x),"grill", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCBlocks.grill, 1, 0)));
//Buckets
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet), null, "bucket", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.redSteelBucketEmpty, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet),null, "bucket", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.blueSteelBucketEmpty, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet), new ItemStack(TFCItems.blueSteelSheet), "bucket", AnvilReq.BLUESTEEL,
				new ItemStack(Items.bucket, 1)));
//Tuyeres
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperSheet2x), null,"tuyere", AnvilReq.COPPER,
				new ItemStack(TFCItems.tuyereCopper, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeSheet2x), null,"tuyere", AnvilReq.BRONZE,
				new ItemStack(TFCItems.tuyereBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeSheet2x), null,"tuyere", AnvilReq.BRONZE,
				new ItemStack(TFCItems.tuyereBismuthBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeSheet2x), null,"tuyere", AnvilReq.BRONZE,
				new ItemStack(TFCItems.tuyereBlackBronze, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet2x), null,"tuyere", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.tuyereWroughtIron, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelSheet2x), null,"tuyere", AnvilReq.STEEL, new ItemStack(TFCItems.tuyereSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelSheet2x), null,"tuyere", AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.tuyereBlackSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet2x), null,"tuyere", AnvilReq.BLUESTEEL,
				new ItemStack(TFCItems.tuyereBlueSteel, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet2x), null,"tuyere", AnvilReq.REDSTEEL,
				new ItemStack(TFCItems.tuyereRedSteel, 1)));
//Trap Doors
		addTrapDoor(TFCItems.bismuthSheet, 0);addTrapDoor(TFCItems.bismuthBronzeSheet, 1);addTrapDoor(TFCItems.blackBronzeSheet, 2);addTrapDoor(TFCItems.blackSteelSheet, 3);
		addTrapDoor(TFCItems.blueSteelSheet, 4);addTrapDoor(TFCItems.brassSheet, 5);addTrapDoor(TFCItems.bronzeSheet, 6);addTrapDoor(TFCItems.copperSheet, 7);
		addTrapDoor(TFCItems.goldSheet, 8);addTrapDoor(TFCItems.wroughtIronSheet, 9);addTrapDoor(TFCItems.leadSheet, 10);addTrapDoor(TFCItems.nickelSheet, 11);
		addTrapDoor(TFCItems.nickelSheet, 12);addTrapDoor(TFCItems.platinumSheet, 13);addTrapDoor(TFCItems.redSteelSheet, 14);addTrapDoor(TFCItems.roseGoldSheet, 15);
		addTrapDoor(TFCItems.silverSheet, 16);addTrapDoor(TFCItems.steelSheet, 17);addTrapDoor(TFCItems.sterlingSilverSheet, 18);addTrapDoor(TFCItems.tinSheet, 19);
		addTrapDoor(TFCItems.zincSheet, 20);addTrapDoor(TFCItems.electrumSheet, 21);addTrapDoor(TFCItems.cupronickelSheet, 22);addTrapDoor(TFCItems.osmiumSheet, 23);
		addTrapDoor(TFCItems.aluminumSheet, 24);addTrapDoor(TFCItems.tungstenSheet, 25);

		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.goldIngot), null,"oillamp", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.oilLamp, 1, 0)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.platinumIngot), null,"oillamp", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.oilLamp, 1, 1)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.roseGoldIngot), null,"oillamp", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.oilLamp, 1, 2)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.silverIngot), null,"oillamp", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.oilLamp, 1, 3)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.sterlingSilverIngot), null,"oillamp", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.oilLamp, 1, 4)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot), null,"oillamp", AnvilReq.BLUESTEEL,
				new ItemStack(TFCBlocks.oilLamp, 1, 5)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet2x), new ItemStack(TFCItems.wroughtIronSheet2x),"hopper", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCBlocks.hopper, 1, 0)));
	}

	private static void addTrapDoor(Item sheet, int index)
	{
		AnvilManager manager = AnvilManager.getInstance();
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.bismuthIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index)));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.bismuthBronzeIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (1 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.blackBronzeIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (2 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.blackSteelIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (3 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.blueSteelIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (4 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.brassIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (5 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.bronzeIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (6 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.copperIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (7 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.goldIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (8 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.wroughtIronIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (9 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.leadIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (10 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.nickelIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (11 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.pigIronIngot), "trapdoor", AnvilReq.WROUGHTIRON,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (12 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.platinumIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (13 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.redSteelIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (14 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.roseGoldIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (15 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.silverIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (16 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.steelIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (17 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.sterlingSilverIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (18 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.tinIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (19 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.zincIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (20 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.electrumIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (21 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.cupronickelIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (22 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.osmiumIngot), "trapdoor", AnvilReq.BLUESTEEL,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (23 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.aluminumIngot), "trapdoor", AnvilReq.COPPER,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (24 << 5))));
		manager.addRecipe(new AnvilRecipe(new ItemStack(sheet), new ItemStack(TFCItems.tungstenIngot), "trapdoor", AnvilReq.STEEL,
				new ItemStack(TFCBlocks.metalTrapDoor, 1, index + (25 << 5))));
	}

	/**
	 * @param manager
	 */
	private static void addWeldRecipes(AnvilManager manager)
	{
/**
* Weld Recipes
*/
//Ingot2x
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthIngot),new ItemStack(TFCItems.bismuthIngot),AnvilReq.STONE,
				new ItemStack(TFCItems.bismuthIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeIngot),new ItemStack(TFCItems.bismuthBronzeIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.bismuthBronzeIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeIngot),new ItemStack(TFCItems.blackBronzeIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.blackBronzeIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelIngot),new ItemStack(TFCItems.blackSteelIngot),AnvilReq.STEEL,
				new ItemStack(TFCItems.blackSteelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelIngot),new ItemStack(TFCItems.blueSteelIngot),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blueSteelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.brassIngot),new ItemStack(TFCItems.brassIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.brassIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeIngot),new ItemStack(TFCItems.bronzeIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.bronzeIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperIngot),new ItemStack(TFCItems.copperIngot),AnvilReq.STONE,
				new ItemStack(TFCItems.copperIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.goldIngot),new ItemStack(TFCItems.goldIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.goldIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot),new ItemStack(TFCItems.wroughtIronIngot),AnvilReq.BRONZE,
				new ItemStack(TFCItems.wroughtIronIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.leadIngot),new ItemStack(TFCItems.leadIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.leadIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.nickelIngot),new ItemStack(TFCItems.nickelIngot),AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.nickelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.pigIronIngot),new ItemStack(TFCItems.pigIronIngot),AnvilReq.BRONZE,
				new ItemStack(TFCItems.pigIronIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.platinumIngot),new ItemStack(TFCItems.platinumIngot),AnvilReq.BRONZE,
				new ItemStack(TFCItems.platinumIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelIngot),new ItemStack(TFCItems.redSteelIngot),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.redSteelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.roseGoldIngot),new ItemStack(TFCItems.roseGoldIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.roseGoldIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.silverIngot),new ItemStack(TFCItems.silverIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.silverIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot),new ItemStack(TFCItems.steelIngot),AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.steelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.sterlingSilverIngot),new ItemStack(TFCItems.sterlingSilverIngot),AnvilReq.BRONZE,
				new ItemStack(TFCItems.sterlingSilverIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.tinIngot),new ItemStack(TFCItems.tinIngot),AnvilReq.STONE,
				new ItemStack(TFCItems.tinIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.zincIngot),new ItemStack(TFCItems.zincIngot),AnvilReq.STONE,
				new ItemStack(TFCItems.zincIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.electrumIngot),new ItemStack(TFCItems.electrumIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.electrumIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.cupronickelIngot),new ItemStack(TFCItems.cupronickelIngot),AnvilReq.COPPER,
				new ItemStack(TFCItems.cupronickelIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.weakSteelIngot),new ItemStack(TFCItems.pigIronIngot),AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.highCarbonBlackSteelIngot, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.weakBlueSteelIngot),new ItemStack(TFCItems.blackSteelIngot),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.highCarbonBlueSteelIngot, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.weakRedSteelIngot),new ItemStack(TFCItems.blackSteelIngot),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.highCarbonRedSteelIngot, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.osmiumIngot),new ItemStack(TFCItems.osmiumIngot),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.osmiumIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.aluminumIngot),new ItemStack(TFCItems.aluminumIngot),AnvilReq.STONE,
				new ItemStack(TFCItems.aluminumIngot2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.tungstenIngot),new ItemStack(TFCItems.tungstenIngot),AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.tungstenIngot2x, 1)));
//Sheet2x
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthSheet),new ItemStack(TFCItems.bismuthSheet),AnvilReq.STONE,
				new ItemStack(TFCItems.bismuthSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeSheet),new ItemStack(TFCItems.bismuthBronzeSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.bismuthBronzeSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeSheet),new ItemStack(TFCItems.blackBronzeSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.blackBronzeSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelSheet),new ItemStack(TFCItems.blackSteelSheet),AnvilReq.STEEL,
				new ItemStack(TFCItems.blackSteelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelSheet),new ItemStack(TFCItems.blueSteelSheet),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blueSteelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.brassSheet),new ItemStack(TFCItems.brassSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.brassSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeSheet),new ItemStack(TFCItems.bronzeSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.bronzeSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperSheet),new ItemStack(TFCItems.copperSheet),AnvilReq.STONE,
				new ItemStack(TFCItems.copperSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.goldSheet),new ItemStack(TFCItems.goldSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.goldSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronSheet),new ItemStack(TFCItems.wroughtIronSheet),AnvilReq.BRONZE,
				new ItemStack(TFCItems.wroughtIronSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.leadSheet),new ItemStack(TFCItems.leadSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.leadSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.nickelSheet),new ItemStack(TFCItems.nickelSheet),AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.nickelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.pigIronSheet),new ItemStack(TFCItems.pigIronSheet),AnvilReq.BRONZE,
				new ItemStack(TFCItems.pigIronSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.platinumSheet),new ItemStack(TFCItems.platinumSheet),AnvilReq.BRONZE,
				new ItemStack(TFCItems.platinumSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelSheet),new ItemStack(TFCItems.redSteelSheet),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.redSteelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.roseGoldSheet),new ItemStack(TFCItems.roseGoldSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.roseGoldSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.silverSheet),new ItemStack(TFCItems.silverSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.silverSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelSheet),new ItemStack(TFCItems.steelSheet),AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.steelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.sterlingSilverSheet),new ItemStack(TFCItems.sterlingSilverSheet),AnvilReq.BRONZE,
				new ItemStack(TFCItems.sterlingSilverSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.tinSheet),new ItemStack(TFCItems.tinSheet),AnvilReq.STONE,
				new ItemStack(TFCItems.tinSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.zincSheet),new ItemStack(TFCItems.zincSheet),AnvilReq.STONE,
				new ItemStack(TFCItems.zincSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.electrumSheet),new ItemStack(TFCItems.electrumSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.electrumSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.cupronickelSheet),new ItemStack(TFCItems.cupronickelSheet),AnvilReq.COPPER,
				new ItemStack(TFCItems.cupronickelSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.osmiumSheet),new ItemStack(TFCItems.osmiumSheet),AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.osmiumSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.aluminumSheet),new ItemStack(TFCItems.aluminumSheet),AnvilReq.STONE,
				new ItemStack(TFCItems.aluminumSheet2x, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.tungstenSheet),new ItemStack(TFCItems.tungstenSheet),AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.tungstenSheet2x, 1)));
//Armour
//Helmet
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.bismuthBronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.blackBronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.blackBronzeUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.blackSteelSheet),true,AnvilReq.STEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.blueSteelSheet),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.bronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bronzeUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedHelmet,1,0),new ItemStack(TFCItems.copperSheet),true,AnvilReq.STONE,
				new ItemStack(TFCItems.copperUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1,0),new ItemStack(TFCItems.wroughtIronSheet),true,AnvilReq.BRONZE,
				new ItemStack(TFCItems.wroughtIronUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.redSteelSheet),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedHelmet,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedHelmet,1,0),new ItemStack(TFCItems.steelSheet),true,AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.steelUnfinishedHelmet,1, 1)));
//Chestpiece
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.bismuthBronzeSheet2x),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.blackBronzeSheet2x),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.blackBronzeUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.blackSteelSheet2x),true,AnvilReq.STEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.blueSteelSheet2x),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.bronzeSheet2x),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bronzeUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedChestplate,1,0),new ItemStack(TFCItems.copperSheet2x),true,AnvilReq.STONE,
				new ItemStack(TFCItems.copperUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1,0),new ItemStack(TFCItems.wroughtIronSheet2x),true,AnvilReq.BRONZE,
				new ItemStack(TFCItems.wroughtIronUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.redSteelSheet2x),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedChestplate,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedChestplate,1,0),new ItemStack(TFCItems.steelSheet2x),true,AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.steelUnfinishedChestplate,1, 1)));
//Greaves
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.bismuthBronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.blackBronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.blackBronzeUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.blackSteelSheet),true,AnvilReq.STEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.blueSteelSheet),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.bronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bronzeUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedGreaves,1,0),new ItemStack(TFCItems.copperSheet),true,AnvilReq.STONE,
				new ItemStack(TFCItems.copperUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1,0),new ItemStack(TFCItems.wroughtIronSheet),true,AnvilReq.BRONZE,
				new ItemStack(TFCItems.wroughtIronUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.redSteelSheet),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedGreaves,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedGreaves,1,0),new ItemStack(TFCItems.steelSheet),true,AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.steelUnfinishedGreaves,1, 1)));
//Boots
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.bismuthBronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bismuthBronzeUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.blackBronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.blackBronzeUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blackSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.blackSteelSheet),true,AnvilReq.STEEL,
				new ItemStack(TFCItems.blackSteelUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.blueSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.blueSteelSheet),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.blueSteelUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.bronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.bronzeSheet),true,AnvilReq.COPPER,
				new ItemStack(TFCItems.bronzeUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.copperUnfinishedBoots,1,0),new ItemStack(TFCItems.copperSheet),true,AnvilReq.STONE,
				new ItemStack(TFCItems.copperUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1,0),new ItemStack(TFCItems.wroughtIronSheet),true,AnvilReq.BRONZE,
				new ItemStack(TFCItems.wroughtIronUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.redSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.redSteelSheet),true,AnvilReq.BLACKSTEEL,
				new ItemStack(TFCItems.redSteelUnfinishedBoots,1, 1)));
		manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelUnfinishedBoots,1,0),new ItemStack(TFCItems.steelSheet),true,AnvilReq.WROUGHTIRON,
				new ItemStack(TFCItems.steelUnfinishedBoots,1, 1)));
	}

	public static void registerFoodRecipes()
	{
		addFoodRefineRecipe(TFCItems.wheatWhole, TFCItems.wheatGrain);
		addFoodRefineRecipe(TFCItems.ryeWhole, TFCItems.ryeGrain);
		addFoodRefineRecipe(TFCItems.barleyWhole, TFCItems.barleyGrain);
		addFoodRefineRecipe(TFCItems.oatWhole, TFCItems.oatGrain);
		addFoodRefineRecipe(TFCItems.riceWhole, TFCItems.riceGrain);

		addFoodDoughRecipe(TFCItems.wheatGround, TFCItems.wheatDough, TFCItems.woodenBucketWater);
		addFoodDoughRecipe(TFCItems.barleyGround, TFCItems.barleyDough, TFCItems.woodenBucketWater);
		addFoodDoughRecipe(TFCItems.ryeGround, TFCItems.ryeDough, TFCItems.woodenBucketWater);
		addFoodDoughRecipe(TFCItems.oatGround, TFCItems.oatDough, TFCItems.woodenBucketWater);
		addFoodDoughRecipe(TFCItems.riceGround, TFCItems.riceDough, TFCItems.woodenBucketWater);
		addFoodDoughRecipe(TFCItems.cornmealGround, TFCItems.cornmealDough, TFCItems.woodenBucketWater);
		addFoodDoughRecipe(TFCItems.wheatGround, TFCItems.wheatDough, TFCItems.redSteelBucketWater);
		addFoodDoughRecipe(TFCItems.barleyGround, TFCItems.barleyDough, TFCItems.redSteelBucketWater);
		addFoodDoughRecipe(TFCItems.ryeGround, TFCItems.ryeDough, TFCItems.redSteelBucketWater);
		addFoodDoughRecipe(TFCItems.oatGround, TFCItems.oatDough, TFCItems.redSteelBucketWater);
		addFoodDoughRecipe(TFCItems.riceGround, TFCItems.riceDough, TFCItems.redSteelBucketWater);
		addFoodDoughRecipe(TFCItems.cornmealGround, TFCItems.cornmealDough, TFCItems.redSteelBucketWater);

		addFoodSaltRecipe(TFCItems.venisonRaw);
		addFoodSaltRecipe(TFCItems.beefRaw);
		addFoodSaltRecipe(TFCItems.bearRaw);
		addFoodSaltRecipe(TFCItems.chickenRaw);
		addFoodSaltRecipe(TFCItems.porkchopRaw);
		addFoodSaltRecipe(TFCItems.fishRaw);
		addFoodSaltRecipe(TFCItems.calamariRaw);
		addFoodSaltRecipe(TFCItems.muttonRaw);
		addFoodSaltRecipe(TFCItems.horseMeatRaw);
		for(Item i : TFCItems.foodList)
		{
			addFoodMergeRecipe(i);
			GameRegistry.addRecipe(new ShapelessOreRecipe(ItemFoodTFC.createTag(new ItemStack(i, 1)), ItemFoodTFC.createTag(new ItemStack(i, 1)), "itemKnife"));
			GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(i, 1)), ItemFoodTFC.createTag(new ItemStack(i, 1)));
		}
	}

	public static void addFoodSaltRecipe(Item food)
	{
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), new ItemStack(TFCItems.powder, 1, 9));
	}

	public static void addFoodRefineRecipe(Item foodInput, Item foodOutput)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemFoodTFC.createTag(new ItemStack(foodOutput, 1)), ItemFoodTFC.createTag(new ItemStack(foodInput, 1)), "itemKnife"));
	}

	public static void addFoodDoughRecipe(Item foodInput, Item foodOutput, Item bucket)
	{
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(foodOutput, 1)), ItemFoodTFC.createTag(new ItemStack(foodInput, 1)), bucket);
	}

	public static void addFoodMergeRecipe(Item food)
	{
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
		GameRegistry.addShapelessRecipe(ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)), ItemFoodTFC.createTag(new ItemStack(food, 1)));
	}

	public static void registerKilnRecipes()
	{
		KilnCraftingManager manager = KilnCraftingManager.getInstance();

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.ceramicMold,1,0),
						0, 
						new ItemStack(TFCItems.ceramicMold,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.spindleHead,1,0),
						0, 
						new ItemStack(TFCItems.spindleHead,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.potteryJug,1,0),
						0, 
						new ItemStack(TFCItems.potteryJug,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.potterySmallVessel,1,0),
						0, 
						new ItemStack(TFCItems.potterySmallVessel,1,1)));

		/*manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.PotteryPot,1,0),
						0, 
						new ItemStack(TFCItems.PotteryPot,1,1)));*/

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCBlocks.vessel,1,0),
						0, 
						new ItemStack(TFCBlocks.vessel,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldAxe,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldAxe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldAxe,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldAxe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldChisel,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldChisel,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldHammer,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldHammer,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldHoe,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldHoe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldKnife,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldKnife,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldMace,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldMace,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldPick,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldPick,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldProPick,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldProPick,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldSaw,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldSaw,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldScythe,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldScythe,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldShovel,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldShovel,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldSword,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldSword,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.clayMoldJavelin,1,0),
						0, 
						new ItemStack(TFCItems.clayMoldJavelin,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.fireBrick,1,0),
						0, 
						new ItemStack(TFCItems.fireBrick,1,1)));

		manager.addRecipe(
				new KilnRecipe(
						new ItemStack(TFCItems.potteryBowl,1,0),
						0, 
						new ItemStack(TFCItems.potteryBowl,1,1)));
	}

	private static void registerQuernRecipes()
	{
		QuernManager manager = QuernManager.getInstance();

		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.wheatGrain, 1), new ItemStack(TFCItems.wheatGround, 1)));//Wheat Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.ryeGrain, 1), new ItemStack(TFCItems.ryeGround, 1)));//Rye Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oatGrain, 1), new ItemStack(TFCItems.oatGround, 1)));//Oat Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.barleyGrain, 1), new ItemStack(TFCItems.barleyGround, 1)));//Barley Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.riceGrain, 1), new ItemStack(TFCItems.riceGround, 1)));//Rice Flour
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.maizeEar, 1), new ItemStack(TFCItems.cornmealGround, 1)));//Cornmeal
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreMineralChunk, 1, 0),new ItemStack( TFCItems.powder, 4, 1)));//Kaolinite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreMineralChunk, 1, 4), new ItemStack(TFCItems.powder, 4, 2)));//Graphite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreMineralChunk, 1, 11), new ItemStack(Items.redstone, 8)));//Cinnabar to Redstone
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreMineralChunk, 1, 12), new ItemStack(Items.redstone, 8)));//Cryolite to Redstone
		manager.addRecipe(new QuernRecipe(new ItemStack(Items.bone, 1), new ItemStack(TFCItems.dye, 2, 15)));//Bone Meal
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreMineralChunk, 1, 19), new ItemStack(TFCItems.powder, 4, 6)));//Lapis Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.smallOreChunk, 1, 9), new ItemStack(TFCItems.powder, 1, 8)));//Small Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 45), new ItemStack(TFCItems.powder, 2, 8)));//Poor Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 9), new ItemStack(TFCItems.powder, 4, 8)));//Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 27), new ItemStack(TFCItems.powder, 6, 8)));//Rich Malachite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.smallOreChunk, 1, 3), new ItemStack(TFCItems.powder, 1, 5)));//Small Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 39), new ItemStack(TFCItems.powder, 2, 5)));//Poor Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 3),new ItemStack( TFCItems.powder, 4, 5)));//Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 21), new ItemStack(TFCItems.powder, 6, 5)));//Rich Hematite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.smallOreChunk, 1, 11), new ItemStack(TFCItems.powder, 1, 7)));//Small Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 47), new ItemStack(TFCItems.powder, 2, 7)));//Poor Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 11), new ItemStack(TFCItems.powder, 4, 7)));//Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreChunk, 1, 29), new ItemStack(TFCItems.powder, 6, 7)));//Rich Limonite Powder
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.oreMineralChunk, 1, 20), new ItemStack(TFCItems.fertilizer, 4)));//Sylvite to Fertilizer
		manager.addRecipe(new QuernRecipe(new ItemStack(TFCItems.looseRock, 1, 5), new ItemStack(TFCItems.powder, 4, 9)));//Rock Salt to Salt

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

	public static void removeRecipe(ItemStack resultItem)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			if (recipes.get(i) != null)
			{
				ItemStack recipeResult = recipes.get(i).getRecipeOutput();

				if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
					recipes.remove(i--);
			}
		}
	}

	public static void removeRecipe(Class clazz)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe != null)
			{
				ItemStack recipeResult = tmpRecipe.getRecipeOutput();

				if (recipeResult!= null && clazz.isInstance(recipeResult.getItem()))
					recipes.remove(i--);
			}
		}
	}
}
