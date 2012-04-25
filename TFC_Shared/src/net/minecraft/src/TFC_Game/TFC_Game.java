package net.minecraft.src.TFC_Game;

import java.util.Hashtable;
import com.google.common.collect.*;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;

public class TFC_Game 
{
	public static Hashtable AnvilWeldRecipes;
	public static com.google.common.collect.Multimap<String,Object[]> RecipeMap;

	public static Item[] MeltedMetal = {mod_TFC_Game.terraMeltedBismuth,mod_TFC_Game.terraMeltedBismuthBronze,mod_TFC_Game.terraMeltedBlackBronze,
		mod_TFC_Game.terraMeltedBlackSteel,mod_TFC_Game.terraMeltedBlueSteel,mod_TFC_Game.terraMeltedBrass,mod_TFC_Game.terraMeltedBronze,
		mod_TFC_Game.terraMeltedCopper,mod_TFC_Game.terraMeltedGold,
		mod_TFC_Game.terraMeltedWroughtIron,mod_TFC_Game.terraMeltedLead,mod_TFC_Game.terraMeltedNickel,mod_TFC_Game.terraMeltedPigIron,
		mod_TFC_Game.terraMeltedPlatinum,mod_TFC_Game.terraMeltedRedSteel,mod_TFC_Game.terraMeltedRoseGold,mod_TFC_Game.terraMeltedSilver,
		mod_TFC_Game.terraMeltedSteel,mod_TFC_Game.terraMeltedSterlingSilver,
		mod_TFC_Game.terraMeltedTin,mod_TFC_Game.terraMeltedZinc, mod_TFC_Game.terraMeltedHCSteel, mod_TFC_Game.terraMeltedWeakSteel,
		mod_TFC_Game.terraMeltedHCBlackSteel, mod_TFC_Game.terraMeltedHCBlueSteel, mod_TFC_Game.terraMeltedHCRedSteel, 
		mod_TFC_Game.terraMeltedWeakBlueSteel, mod_TFC_Game.terraMeltedWeakRedSteel};


	public static Item[] Hammers = {mod_TFC_Game.BismuthHammer,mod_TFC_Game.BismuthBronzeHammer,mod_TFC_Game.BlackBronzeHammer,
		mod_TFC_Game.BlackSteelHammer,mod_TFC_Game.BlueSteelHammer,mod_TFC_Game.BronzeHammer,mod_TFC_Game.CopperHammer,
		mod_TFC_Game.WroughtIronHammer,mod_TFC_Game.RedSteelHammer,mod_TFC_Game.RoseGoldHammer,mod_TFC_Game.SteelHammer,
		mod_TFC_Game.TinHammer,mod_TFC_Game.ZincHammer};

	static
	{
		AnvilWeldRecipes = new Hashtable();
		RecipeMap = HashMultimap.create();

		AnvilWeldRecipes.put("item.terraBismuthIngot|item.terraBismuthIngot", new Object[]{new ItemStack(mod_TFC_Core.terraBismuthIngot2x)});
		AnvilWeldRecipes.put("item.terraBismuthBronzeIngot|item.terraBismuthBronzeIngot", new Object[]{new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot2x)});
		AnvilWeldRecipes.put("item.terraBlackBronzeIngot|item.terraBlackBronzeIngot", new Object[]{new ItemStack(mod_TFC_Core.terraBlackBronzeIngot2x)});
		AnvilWeldRecipes.put("item.terraBlackSteelIngot|item.terraBlackSteelIngot", new Object[]{new ItemStack(mod_TFC_Core.terraBlackSteelIngot2x)});
		AnvilWeldRecipes.put("item.terraBrassIngot|item.terraBrassIngot", new Object[]{new ItemStack(mod_TFC_Core.terraBrassIngot2x)});
		AnvilWeldRecipes.put("item.terraBronzeIngot|item.terraBronzeIngot", new Object[]{new ItemStack(mod_TFC_Core.terraBronzeIngot2x)});
		AnvilWeldRecipes.put("item.terraCopperIngot|item.terraCopperIngot", new Object[]{new ItemStack(mod_TFC_Core.terraCopperIngot2x)});
		AnvilWeldRecipes.put("item.terraGoldIngot|item.terraGoldIngot", new Object[]{new ItemStack(mod_TFC_Core.terraGoldIngot2x)});
		AnvilWeldRecipes.put("item.terraWroughtIronIngot|item.terraWroughtIronIngot", new Object[]{new ItemStack(mod_TFC_Core.terraWroughtIronIngot2x)});
		AnvilWeldRecipes.put("item.terraLeadIngot|item.terraLeadIngot", new Object[]{new ItemStack(mod_TFC_Core.terraLeadIngot2x)});
		AnvilWeldRecipes.put("item.terraNickelIngot|item.terraNickelIngot", new Object[]{new ItemStack(mod_TFC_Core.terraNickelIngot2x)});
		AnvilWeldRecipes.put("item.terraPigIronIngot|item.terraPigIronIngot", new Object[]{new ItemStack(mod_TFC_Core.terraPigIronIngot2x)});
		AnvilWeldRecipes.put("item.terraPlatinumIngot|item.terraPlatinumIngot", new Object[]{new ItemStack(mod_TFC_Core.terraPlatinumIngot2x)});
		AnvilWeldRecipes.put("item.terraRedSteelIngot|item.terraRedSteelIngot", new Object[]{new ItemStack(mod_TFC_Core.terraRedSteelIngot2x)});
		AnvilWeldRecipes.put("item.terraRoseGoldIngot|item.terraRoseGoldIngot", new Object[]{new ItemStack(mod_TFC_Core.terraRoseGoldIngot2x)});
		AnvilWeldRecipes.put("item.terraSilverIngot|item.terraSilverIngot", new Object[]{new ItemStack(mod_TFC_Core.terraSilverIngot2x)});
		AnvilWeldRecipes.put("item.terraSteelIngot|item.terraSteelIngot", new Object[]{new ItemStack(mod_TFC_Core.terraSteelIngot2x)});
		AnvilWeldRecipes.put("item.terraSterlingSilverIngot|item.terraSterlingSilverIngot", new Object[]{new ItemStack(mod_TFC_Core.terraSterlingSilverIngot2x)});
		AnvilWeldRecipes.put("item.terraTinIngot|item.terraTinIngot", new Object[]{new ItemStack(mod_TFC_Core.terraTinIngot2x)});
		AnvilWeldRecipes.put("item.terraZincIngot|item.terraZincIngot", new Object[]{new ItemStack(mod_TFC_Core.terraZincIngot2x)});
		AnvilWeldRecipes.put("item.terraWeakSteelIngot|item.terraPigIronIngot", new Object[]{new ItemStack(mod_TFC_Core.terraHCBlackSteelIngot)});
		AnvilWeldRecipes.put("item.terraWeakBlueSteelIngot|item.terraBlackSteelIngot", new Object[]{new ItemStack(mod_TFC_Core.terraHCBlueSteelIngot)});
		AnvilWeldRecipes.put("item.terraWeakRedSteelIngot|item.terraBlackSteelIngot", new Object[]{new ItemStack(mod_TFC_Core.terraHCRedSteelIngot)});

		////////////////////////////////////////CraftingValue, Tolerance, Schematic,  Result,                  Forging Rules,        			Crafting Variance, Anvil Level
		//RecipeMap.put("tile.stonebrick", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terra), new int[]{0,-1,-1},                    	0, 				0});
		RecipeMap.put("item.terraMeltedBismuth", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraBismuthIngot), new int[]{0,-1,-1},            	0, 				0});
		RecipeMap.put("item.terraMeltedBismuthBronze", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new int[]{0,-1,-1},	0, 				1});
		RecipeMap.put("item.terraMeltedBlackBronze", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new int[]{0,-1,-1},   	0, 				2});
		RecipeMap.put("item.terraMeltedBlackSteel", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new int[]{0,-1,-1},			0, 				4});
		RecipeMap.put("item.terraMeltedBlueSteel", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new int[]{0,-1,-1},			0, 				5});
		RecipeMap.put("item.terraMeltedBrass", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraBrassIngot), new int[]{0,-1,-1},					0, 				1});
		RecipeMap.put("item.terraMeltedBronze", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraBronzeIngot), new int[]{0,-1,-1},					0, 				1});
		RecipeMap.put("item.terraMeltedCopper", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraCopperIngot), new int[]{0,-1,-1},					0, 				0});
		RecipeMap.put("item.terraMeltedGold", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraGoldIngot), new int[]{0,-1,-1},						0, 				1});
		RecipeMap.put("item.terraMeltedWroughtIron", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new int[]{0,-1,-1},		0, 				2});
		RecipeMap.put("item.terraMeltedLead", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraLeadIngot), new int[]{0,-1,-1},						0, 				0});
		RecipeMap.put("item.terraMeltedNickel", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraNickelIngot), new int[]{0,-1,-1},					0, 				3});
		RecipeMap.put("item.terraMeltedPigIron", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraPigIronIngot), new int[]{0,-1,-1},				0, 				2});
		RecipeMap.put("item.terraMeltedPlatinum", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraPlatinumIngot), new int[]{0,-1,-1},				0, 				3});
		RecipeMap.put("item.terraMeltedRedSteel", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraRedSteelIngot), new int[]{0,-1,-1},				0, 				5});
		RecipeMap.put("item.terraMeltedRoseGold", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new int[]{0,-1,-1},				0, 				1});
		RecipeMap.put("item.terraMeltedSilver", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraSilverIngot), new int[]{0,-1,-1},					0, 				1});
		RecipeMap.put("item.terraMeltedSteel", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraSteelIngot), new int[]{0,-1,-1},					0, 				3});
		RecipeMap.put("item.terraMeltedSterlingSilver", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraSterlingSilverIngot), new int[]{0,-1,-1},	0, 				1});
		RecipeMap.put("item.terraMeltedTin", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraTinIngot), new int[]{0,-1,-1},						0, 				0});
		RecipeMap.put("item.terraMeltedZinc", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraZincIngot), new int[]{0,-1,-1},						0, 				0});
		//this is for making pig iron into wrought iron
		RecipeMap.put("item.terraMeltedPigIron", new Object[]{9,0,null,new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new int[]{0,0,0},				0, 				2});

		RecipeMap.put("item.terraMeltedHCBlackSteel", new Object[]{47,0,null,new ItemStack(mod_TFC_Core.terraHCBlackSteelIngot), new int[]{0,0,4},		0, 				4});
		RecipeMap.put("item.terraMeltedHCBlueSteel", new Object[]{57,0,null,new ItemStack(mod_TFC_Core.terraHCBlueSteelIngot), new int[]{0,0,4},		0, 				5});
		RecipeMap.put("item.terraMeltedHCRedSteel", new Object[]{57,0,null,new ItemStack(mod_TFC_Core.terraHCRedSteelIngot), new int[]{0,0,4},			0, 				5});
		RecipeMap.put("item.terraMeltedHCSteel", new Object[]{37,0,null,new ItemStack(mod_TFC_Core.terraHCSteelIngot), new int[]{0,0,4},				0, 				3});
		RecipeMap.put("item.terraHCBlackSteelIngot", new Object[]{47,0,null,new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new int[]{0,0,4},			0, 				4});
		RecipeMap.put("item.terraHCBlueSteelIngot", new Object[]{57,0,null,new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new int[]{0,0,4},			0, 				5});
		RecipeMap.put("item.terraHCRedSteelIngot", new Object[]{57,0,null,new ItemStack(mod_TFC_Core.terraRedSteelIngot), new int[]{0,0,4},				0, 				5});
		RecipeMap.put("item.terraHCSteelIngot", new Object[]{37,0,null,new ItemStack(mod_TFC_Core.terraSteelIngot), new int[]{0,0,4},					0, 				3});

		////////////////////////////////////////CraftingValue, Tolerance, Schematic,  Result,                  													Forging Rules, 		Crafting Variance, Anvil Level
		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.BismuthPickaxeHead), new int[]{-1,-1,3},				2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.BismuthBronzePickaxeHead), new int[]{-1,-1,3},	2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.BlackBronzePickaxeHead), new int[]{-1,-1,3},		2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.BlackSteelPickaxeHead), new int[]{-1,-1,3},		2, 				4});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.BlueSteelPickaxeHead), new int[]{-1,-1,3},			2, 				5});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.BronzePickaxeHead), new int[]{-1,-1,3},				2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.CopperPickaxeHead), new int[]{-1,-1,3},				2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.WroughtIronPickaxeHead), new int[]{-1,-1,3},				2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.RedSteelPickaxeHead), new int[]{-1,-1,3},			2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.RoseGoldPickaxeHead), new int[]{-1,-1,3},			2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.SteelPickaxeHead), new int[]{-1,-1,3},					2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.TinPickaxeHead), new int[]{-1,-1,3},						2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{27,0,mod_TFC_Game.PickaxeHeadPlan,new ItemStack(mod_TFC_Game.ZincPickaxeHead), new int[]{-1,-1,3},					2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.BismuthShovelHead), new int[]{-1,-1,3},				2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.BismuthBronzeShovelHead), new int[]{-1,-1,3},	2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.BlackBronzeShovelHead), new int[]{-1,-1,3},		2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.BlackSteelShovelHead), new int[]{-1,-1,3},		2, 				4});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.BlueSteelShovelHead), new int[]{-1,-1,3},			2, 				5});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.BronzeShovelHead), new int[]{-1,-1,3},				2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.CopperShovelHead), new int[]{-1,-1,3},				2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.WroughtIronShovelHead), new int[]{-1,-1,3},				2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.RedSteelShovelHead), new int[]{-1,-1,3},			2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.RoseGoldShovelHead), new int[]{-1,-1,3},			2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.SteelShovelHead), new int[]{-1,-1,3},					2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.TinShovelHead), new int[]{-1,-1,3},						2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{29,0,mod_TFC_Game.ShovelHeadPlan,new ItemStack(mod_TFC_Game.ZincShovelHead), new int[]{-1,-1,3},					2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.BismuthHoeHead), new int[]{-1,-1,3},						2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.BismuthBronzeHoeHead), new int[]{-1,-1,3},			2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.BlackBronzeHoeHead), new int[]{-1,-1,3},				2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.BlackSteelHoeHead), new int[]{-1,-1,3},				2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.BlueSteelHoeHead), new int[]{-1,-1,3},					2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.BronzeHoeHead), new int[]{-1,-1,3},						2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.CopperHoeHead), new int[]{-1,-1,3},						2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.WroughtIronHoeHead), new int[]{-1,-1,3},						2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.RedSteelHoeHead), new int[]{-1,-1,3},					2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.RoseGoldHoeHead), new int[]{-1,-1,3},					2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.SteelHoeHead), new int[]{-1,-1,3},							2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.TinHoeHead), new int[]{-1,-1,3},								2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{26,0,mod_TFC_Game.HoeHeadPlan,new ItemStack(mod_TFC_Game.ZincHoeHead), new int[]{-1,-1,3},							2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.BismuthAxeHead), new int[]{-1,-1,3},						2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.BismuthBronzeAxeHead), new int[]{-1,-1,3},			2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.BlackBronzeAxeHead), new int[]{-1,-1,3},				2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.BlackSteelAxeHead), new int[]{-1,-1,3},				2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.BlueSteelAxeHead), new int[]{-1,-1,3},					2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.BronzeAxeHead), new int[]{-1,-1,3},						2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.CopperAxeHead), new int[]{-1,-1,3},						2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.WroughtIronAxeHead), new int[]{-1,-1,3},						2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.RedSteelAxeHead), new int[]{-1,-1,3},					2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.RoseGoldAxeHead), new int[]{-1,-1,3},					2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.SteelAxeHead), new int[]{-1,-1,3},							2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.TinAxeHead), new int[]{-1,-1,3},								2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{31,0,mod_TFC_Game.AxeHeadPlan,new ItemStack(mod_TFC_Game.ZincAxeHead), new int[]{-1,-1,3},							2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.BismuthHammerHead), new int[]{-1,-1,3},				2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.BismuthBronzeHammerHead), new int[]{-1,-1,3},	2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.BlackBronzeHammerHead), new int[]{-1,-1,3},		2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.BlackSteelHammerHead), new int[]{-1,-1,3},			2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.BlueSteelHammerHead), new int[]{-1,-1,3},			2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.BronzeHammerHead), new int[]{-1,-1,3},					2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.CopperHammerHead), new int[]{-1,-1,3},					2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.WroughtIronHammerHead), new int[]{-1,-1,3},				2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.RedSteelHammerHead), new int[]{-1,-1,3},				2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.RoseGoldHammerHead), new int[]{-1,-1,3},				2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.SteelHammerHead), new int[]{-1,-1,3},					2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.TinHammerHead), new int[]{-1,-1,3},						2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{36,0,mod_TFC_Game.HammerHeadPlan,new ItemStack(mod_TFC_Game.ZincHammerHead), new int[]{-1,-1,3},						2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.BismuthSwordHead), new int[]{-1,0,4},				2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.BismuthBronzeSwordHead), new int[]{-1,0,4},	2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.BlackBronzeSwordHead), new int[]{-1,0,4},		2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.BlackSteelSwordHead), new int[]{-1,0,4},			2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.BlueSteelSwordHead), new int[]{-1,0,4},			2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.BronzeSwordHead), new int[]{-1,0,4},					2, 				1});
		RecipeMap.put("item."+"terraCopperIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.CopperSwordHead), new int[]{-1,0,4},					2, 				1});
		RecipeMap.put("item."+"terraIronIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.WroughtIronSwordHead), new int[]{-1,0,4},				2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.RedSteelSwordHead), new int[]{-1,0,4},				2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.RoseGoldSwordHead), new int[]{-1,0,4},				2, 				1});
		RecipeMap.put("item."+"terraSteelIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.SteelSwordHead), new int[]{-1,0,4},					2, 				3});
		RecipeMap.put("item."+"terraTinIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.TinSwordHead), new int[]{-1,0,4},						2, 				0});
		RecipeMap.put("item."+"terraZincIngot2x",  new Object[]{43,0,mod_TFC_Game.SwordBladePlan,new ItemStack(mod_TFC_Game.ZincSwordHead), new int[]{-1,0,4},						2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.BismuthMaceHead), new int[]{-1,-1,4},					2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.BismuthBronzeMaceHead), new int[]{-1,-1,4},		2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.BlackBronzeMaceHead), new int[]{-1,-1,4},			2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.BlackSteelMaceHead), new int[]{-1,-1,4},				2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.BlueSteelMaceHead), new int[]{-1,-1,4},				2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.BronzeMaceHead), new int[]{-1,-1,4},						2, 				1});
		RecipeMap.put("item."+"terraCopperIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.CopperMaceHead), new int[]{-1,-1,4},						2, 				1});
		RecipeMap.put("item."+"terraIronIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.WroughtIronMaceHead), new int[]{-1,-1,4},					2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.RedSteelMaceHead), new int[]{-1,-1,4},					2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.RoseGoldMaceHead), new int[]{-1,-1,4},					2, 				1});
		RecipeMap.put("item."+"terraSteelIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.SteelMaceHead), new int[]{-1,-1,4},						2, 				3});
		RecipeMap.put("item."+"terraTinIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.TinMaceHead), new int[]{-1,-1,4},							2, 				0});
		RecipeMap.put("item."+"terraZincIngot2x",  new Object[]{39,0,mod_TFC_Game.MaceHeadPlan,new ItemStack(mod_TFC_Game.ZincMaceHead), new int[]{-1,-1,4},							2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.BismuthChiselHead), new int[]{-1,-1,3},				2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.BismuthBronzeChiselHead), new int[]{-1,-1,3},	2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.BlackBronzeChiselHead), new int[]{-1,-1,3},		2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.BlackSteelChiselHead), new int[]{-1,-1,3},			2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.BlueSteelChiselHead), new int[]{-1,-1,3},			2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.BronzeChiselHead), new int[]{-1,-1,3},					2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.CopperChiselHead), new int[]{-1,-1,3},					2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.WroughtIronChiselHead), new int[]{-1,-1,3},				2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.RedSteelChiselHead), new int[]{-1,-1,3},				2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.RoseGoldChiselHead), new int[]{-1,-1,3},				2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.SteelChiselHead), new int[]{-1,-1,3},					2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.TinChiselHead), new int[]{-1,-1,3},						2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{33,0,mod_TFC_Game.ChiselHeadPlan,new ItemStack(mod_TFC_Game.ZincChiselHead), new int[]{-1,-1,3},						2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.BismuthProPickHead), new int[]{-1,0,3},						2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.BismuthBronzeProPickHead), new int[]{-1,0,3},			2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.BlackBronzeProPickHead), new int[]{-1,0,3},				2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.BlackSteelProPickHead), new int[]{-1,0,3},				2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.BlueSteelProPickHead), new int[]{-1,0,3},					2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.BronzeProPickHead), new int[]{-1,0,3},						2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.CopperProPickHead), new int[]{-1,0,3},						2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.WroughtIronProPickHead), new int[]{-1,0,3},					2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.RedSteelProPickHead), new int[]{-1,0,3},					2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.RoseGoldProPickHead), new int[]{-1,0,3},					2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.SteelProPickHead), new int[]{-1,0,3},							2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.TinProPickHead), new int[]{-1,0,3},								2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{41,0,mod_TFC_Game.ProPickBladePlan,new ItemStack(mod_TFC_Game.ZincProPickHead), new int[]{-1,0,3},							2, 				0});

		RecipeMap.put("item."+"terraBismuthIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.BismuthSawHead), new int[]{0,0,-1},						2, 				0});
		RecipeMap.put("item."+"terraBismuthBronzeIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.BismuthBronzeSawHead), new int[]{0,0,-1},			2, 				1});
		RecipeMap.put("item."+"terraBlackBronzeIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.BlackBronzeSawHead), new int[]{0,0,-1},				2, 				2});
		RecipeMap.put("item."+"terraBlackSteelIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.BlackSteelSawHead), new int[]{0,0,-1},				2, 				3});
		RecipeMap.put("item."+"terraBlueSteelIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.BlueSteelSawHead), new int[]{0,0,-1},					2, 				4});
		RecipeMap.put("item."+"terraBronzeIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.BronzeSawHead), new int[]{0,0,-1},						2, 				1});
		RecipeMap.put("item."+"terraCopperIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.CopperSawHead), new int[]{0,0,-1},						2, 				1});
		RecipeMap.put("item."+"terraIronIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.WroughtIronSawHead), new int[]{0,0,-1},					2, 				2});
		RecipeMap.put("item."+"terraRedSteelIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.RedSteelSawHead), new int[]{0,0,-1},					2, 				5});
		RecipeMap.put("item."+"terraRoseGoldIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.RoseGoldSawHead), new int[]{0,0,-1},					2, 				1});
		RecipeMap.put("item."+"terraSteelIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.SteelSawHead), new int[]{0,0,-1},							2, 				3});
		RecipeMap.put("item."+"terraTinIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.TinSawHead), new int[]{0,0,-1},								2, 				0});
		RecipeMap.put("item."+"terraZincIngot",  new Object[]{39,0,mod_TFC_Game.SawBladePlan,new ItemStack(mod_TFC_Game.ZincSawHead), new int[]{0,0,-1},							2, 				0});

	}



	public static EnumWoodMaterial getWoodMaterial(ItemStack is)
	{
		if(is.itemID == mod_TFC_Core.terraPeat.blockID)
		{
			return EnumWoodMaterial.PEAT;
		}
		if(is.getItemDamage() == 0)
		{
			return EnumWoodMaterial.ASH;
		}
		else if(is.getItemDamage() == 1)
		{
			return EnumWoodMaterial.ASPEN;
		}
		else if(is.getItemDamage() == 2)
		{
			return EnumWoodMaterial.BIRCH;
		}
		else if(is.getItemDamage() == 3)
		{
			return EnumWoodMaterial.CHESTNUT;
		}
		else if(is.getItemDamage() == 4)
		{
			return EnumWoodMaterial.DOUGLASFIR;
		}
		else if(is.getItemDamage() == 5)
		{
			return EnumWoodMaterial.HICKORY;
		}
		else if(is.getItemDamage() == 6)
		{
			return EnumWoodMaterial.MAPLE;
		}
		else if(is.getItemDamage() == 7)
		{
			return EnumWoodMaterial.OAK;
		}
		else if(is.getItemDamage() == 8)
		{
			return EnumWoodMaterial.PINE;
		}
		else if(is.getItemDamage() == 9)
		{
			return EnumWoodMaterial.REDWOOD;
		}
		else if(is.getItemDamage() == 10)
		{
			return EnumWoodMaterial.SPRUCE;
		}
		else if(is.getItemDamage() == 11)
		{
			return EnumWoodMaterial.SYCAMORE;
		}
		else if(is.getItemDamage() == 12)
		{
			return EnumWoodMaterial.WHITECEDAR;
		}
		else if(is.getItemDamage() == 13)
		{
			return EnumWoodMaterial.WHITEELM;
		}
		else if(is.getItemDamage() == 14)
		{
			return EnumWoodMaterial.WILLOW;
		}
		else if(is.getItemDamage() == 15)
		{
			return EnumWoodMaterial.KAPOK;
		}
		else
		{
			return EnumWoodMaterial.ASPEN;
		}
	}




	public static void registerRecipes()
	{
		///////////////////////Debug Recipes
		//ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBlackSteelAnvilItem, 1), new Object[] { "2", Character.valueOf('2'), new ItemStack(mod_TFC_Core.terraDirt,1, -1)});
		//ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelPick, 1), new Object[] { "2", Character.valueOf('2'), new ItemStack(mod_TFC_Core.terraDirt2,1,-1)});
		//ModLoader.addRecipe(new ItemStack(Block.torchWood, 64), new Object[] { "22", Character.valueOf('2'), new ItemStack(mod_TFC_Core.terraDirt,1, -1)});
		//ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelAxe, 64,7), new Object[] { "22","22", Character.valueOf('2'), new ItemStack(mod_TFC_Core.terraDirt,1, -1)});
		///////////////////////

		for(int j = 0; j < Hammers.length; j++)
		{
			ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 8), new ItemStack(Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 12), new ItemStack(Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneMMCobble, 1, 22), new ItemStack(Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 10), new ItemStack(Hammers[j], 1, -1)});
		}

		ModLoader.addRecipe(new ItemStack(Item.redstone, 8, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(mod_TFC_Core.OreChunk,1,27)});		
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraInk, 16, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(Item.dyePowder,1,0)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraFireStarter, 1, 0), new Object[] { "2 "," 2", Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBellowsItem, 1, 0), new Object[] { "###","???","###", Character.valueOf('#'), Block.planks, Character.valueOf('?'), Item.leather});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgIn});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgEx});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraCopperAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraCopperIngot2x});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraBronzeIngot2x});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraWroughtIronAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraWroughtIronIngot2x});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBlackSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraBlackSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBlueSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraBlueSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraRedSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraRedSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraScribe, 1), new Object[] { " L ","#P#","###", Character.valueOf('#'), Block.planks,
			Character.valueOf('P'), Item.paper,Character.valueOf('L'), Item.feather});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraClayMold, 4), new Object[] { "# #","###", Character.valueOf('#'), Item.clay});

		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgEx});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgIn});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneSed});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneMM});

		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgExBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgInBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneSedBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneMMBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});

		//stone hammers
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.StoneHammer, 1), new Object[] { 
			"111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.StoneHammer, 1), new Object[] { 
			"111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.StoneHammer, 1), new Object[] { 
			"111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.StoneHammer, 1), new Object[] { 
			"111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});

		//pickaxes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzePick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzePickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzePick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzePickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzePick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzePickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincPick, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincPickaxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Shovels
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincShovel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincShovelHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Hoes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincHoe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincHoeHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Axes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincAxe, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincAxeHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Swords
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.IgExStoneSword, 1), new Object[] { "#","#","I", Character.valueOf('#'), 
			mod_TFC_Core.terraStoneIgExCobble, Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.IgInStoneSword, 1), new Object[] { "#","#","I", Character.valueOf('#'), 
			mod_TFC_Core.terraStoneIgInCobble, Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SedStoneSword, 1), new Object[] { "#","#","I", Character.valueOf('#'), 
			mod_TFC_Core.terraStoneSedCobble, Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.MMStoneSword, 1), new Object[] { "#","#","I", Character.valueOf('#'), 
			mod_TFC_Core.terraStoneMMCobble, Character.valueOf('I'), new ItemStack(Item.stick)});

		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincSword, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincSwordHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Maces
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincMace, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincMaceHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Hammers
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BismuthHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BlackBronzeHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BlackSteelHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BlueSteelHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BronzeHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.CopperHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.WroughtIronHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.RedSteelHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.RoseGoldHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.SteelHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.TinHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.ZincHammer, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincHammerHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Saws
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincSaw, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincSawHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Chisels
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincChisel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincChiselHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//Gold Pan
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraGoldPan, 1, 0), new Object[] { 
			"1", Character.valueOf('1'),Item.bowlEmpty});
		//Sluice
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraSluiceItem, 1), new Object[] { 
			"  1"," 12","122", Character.valueOf('1'),Item.stick, Character.valueOf('2'),Block.planks});

		//stone prospecting pick
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
			"111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
			"111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
			"111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
			"111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});

		//propicks
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBismuth, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBismuthBronze, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BismuthBronzeProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBlackBronze, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackBronzeProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBlackSteel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlackSteelProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBlueSteel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BlueSteelProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBronze, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.BronzeProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickCopper, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.CopperProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickIron, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.WroughtIronProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickRedSteel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RedSteelProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickRoseGold, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.RoseGoldProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickSteel, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.SteelProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickTin, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.TinProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickZinc, 1,0), new Object[] { "#","I", Character.valueOf('#'), 
			new ItemStack(mod_TFC_Game.ZincProPickHead), Character.valueOf('I'), new ItemStack(Item.stick)});

		//scribing table
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.PickaxeHeadPlan, 1), new Object[] { " ### ","#   #", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.ShovelHeadPlan, 1), new Object[] { " ### "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.HoeHeadPlan, 1), new Object[] { "#####","   ##", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.AxeHeadPlan, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.HammerHeadPlan, 1), new Object[] { "#####","#####","  #  ", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.ChiselHeadPlan, 1), new Object[] { "  #  ","  #  ","  #  ","  #  ","  #  ", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.SwordBladePlan, 1), new Object[] { "   ##","  ###"," ### "," ##  ","#    ", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.MaceHeadPlan, 1), new Object[] { "  #  "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.SawBladePlan, 1), new Object[] { "##   ","###  "," ### "," ####","   ##", Character.valueOf('#'), mod_TFC_Game.terraInk});
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.ProPickBladePlan, 1), new Object[] { " ####","#   #","    #", Character.valueOf('#'), mod_TFC_Game.terraInk});
		//metallurgy
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedBismuthBronze, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedCopper),new ItemStack(mod_TFC_Game.terraMeltedCopper),
			new ItemStack(mod_TFC_Game.terraMeltedTin), new ItemStack(mod_TFC_Game.terraMeltedBismuth)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedBlackBronze, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedCopper),new ItemStack(mod_TFC_Game.terraMeltedCopper),
			new ItemStack(mod_TFC_Game.terraMeltedSilver), new ItemStack(mod_TFC_Game.terraMeltedGold)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedWeakSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedSteel),new ItemStack(mod_TFC_Game.terraMeltedSteel),
			new ItemStack(mod_TFC_Game.terraMeltedNickel), new ItemStack(mod_TFC_Game.terraMeltedBlackBronze)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedWeakBlueSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedBlackSteel), new ItemStack(mod_TFC_Game.terraMeltedBismuthBronze), 
			new ItemStack(mod_TFC_Game.terraMeltedSterlingSilver),new ItemStack(mod_TFC_Game.terraMeltedSteel)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedBrass, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedCopper),new ItemStack(mod_TFC_Game.terraMeltedCopper),
			new ItemStack(mod_TFC_Game.terraMeltedCopper), new ItemStack(mod_TFC_Game.terraMeltedZinc)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedBronze, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedCopper),new ItemStack(mod_TFC_Game.terraMeltedCopper),
			new ItemStack(mod_TFC_Game.terraMeltedCopper), new ItemStack(mod_TFC_Game.terraMeltedTin)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedWeakRedSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedBlackSteel), new ItemStack(mod_TFC_Game.terraMeltedRoseGold),  
			new ItemStack(mod_TFC_Game.terraMeltedPlatinum), new ItemStack(mod_TFC_Game.terraMeltedSteel)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedRoseGold, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedCopper),new ItemStack(mod_TFC_Game.terraMeltedGold),
			new ItemStack(mod_TFC_Game.terraMeltedGold), new ItemStack(mod_TFC_Game.terraMeltedGold)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedHCSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedPigIron),new ItemStack(mod_TFC_Game.terraMeltedWroughtIron),
			new ItemStack(mod_TFC_Game.terraMeltedWroughtIron), new ItemStack(mod_TFC_Game.terraMeltedWroughtIron)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.terraMeltedSterlingSilver, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.terraMeltedCopper),new ItemStack(mod_TFC_Game.terraMeltedSilver),
			new ItemStack(mod_TFC_Game.terraMeltedSilver), new ItemStack(mod_TFC_Game.terraMeltedSilver)});


	}
}
