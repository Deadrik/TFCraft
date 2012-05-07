package net.minecraft.src.TFC_Game;

import java.util.Hashtable;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.TFC_Core.General.AnvilRecipe;
import net.minecraft.src.TFC_Core.General.AnvilReq;
import net.minecraft.src.TFC_Core.General.CraftingRule;

public class TFC_Game 
{
	public static Hashtable AnvilWeldRecipes;

	public static Item[] MeltedMetal = {mod_TFC_Game.UnshapedBismuth,mod_TFC_Game.UnshapedBismuthBronze,mod_TFC_Game.UnshapedBlackBronze,
		mod_TFC_Game.UnshapedBlackSteel,mod_TFC_Game.UnshapedBlueSteel,mod_TFC_Game.UnshapedBrass,mod_TFC_Game.UnshapedBronze,
		mod_TFC_Game.UnshapedCopper,mod_TFC_Game.UnshapedGold,
		mod_TFC_Game.UnshapedWroughtIron,mod_TFC_Game.UnshapedLead,mod_TFC_Game.UnshapedNickel,mod_TFC_Game.UnshapedPigIron,
		mod_TFC_Game.UnshapedPlatinum,mod_TFC_Game.UnshapedRedSteel,mod_TFC_Game.UnshapedRoseGold,mod_TFC_Game.UnshapedSilver,
		mod_TFC_Game.UnshapedSteel,mod_TFC_Game.UnshapedSterlingSilver,
		mod_TFC_Game.UnshapedTin,mod_TFC_Game.UnshapedZinc, mod_TFC_Game.UnshapedHCSteel, mod_TFC_Game.UnshapedWeakSteel,
		mod_TFC_Game.UnshapedHCBlackSteel, mod_TFC_Game.UnshapedHCBlueSteel, mod_TFC_Game.UnshapedHCRedSteel, 
		mod_TFC_Game.UnshapedWeakBlueSteel, mod_TFC_Game.UnshapedWeakRedSteel};


	public static Item[] Hammers = {mod_TFC_Game.BismuthHammer,mod_TFC_Game.BismuthBronzeHammer,mod_TFC_Game.BlackBronzeHammer,
		mod_TFC_Game.BlackSteelHammer,mod_TFC_Game.BlueSteelHammer,mod_TFC_Game.BronzeHammer,mod_TFC_Game.CopperHammer,
		mod_TFC_Game.WroughtIronHammer,mod_TFC_Game.RedSteelHammer,mod_TFC_Game.RoseGoldHammer,mod_TFC_Game.SteelHammer,
		mod_TFC_Game.TinHammer,mod_TFC_Game.ZincHammer};

	static
	{
		AnvilWeldRecipes = new Hashtable();

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

	public static void registerAnvilRecipes(Random R)
	{
	    AnvilCraftingManagerTFC manager = AnvilCraftingManagerTFC.getInstance();
	    int v = -5 + R.nextInt(5);
	    
	    /**
	     * Weld Recipes
	     */
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot),new ItemStack(mod_TFC_Core.terraBismuthIngot),true,AnvilReq.STONE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot),new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot),new ItemStack(mod_TFC_Core.terraBlackBronzeIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot),new ItemStack(mod_TFC_Core.terraBlackSteelIngot),true,AnvilReq.STEEL));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot),new ItemStack(mod_TFC_Core.terraBlueSteelIngot),true,AnvilReq.BLACKSTEEL));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBrassIngot),new ItemStack(mod_TFC_Core.terraBrassIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot),new ItemStack(mod_TFC_Core.terraBronzeIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot),new ItemStack(mod_TFC_Core.terraCopperIngot),true,AnvilReq.STONE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraGoldIngot),new ItemStack(mod_TFC_Core.terraGoldIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot),new ItemStack(mod_TFC_Core.terraWroughtIronIngot),true,AnvilReq.BRONZE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraLeadIngot),new ItemStack(mod_TFC_Core.terraLeadIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraNickelIngot),new ItemStack(mod_TFC_Core.terraNickelIngot),true,AnvilReq.WROUGHTIRON));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraPigIronIngot),new ItemStack(mod_TFC_Core.terraPigIronIngot),true,AnvilReq.BRONZE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraPlatinumIngot),new ItemStack(mod_TFC_Core.terraPlatinumIngot),true,AnvilReq.BRONZE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot),new ItemStack(mod_TFC_Core.terraRedSteelIngot),true,AnvilReq.BLACKSTEEL));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot),new ItemStack(mod_TFC_Core.terraRoseGoldIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSilverIngot),new ItemStack(mod_TFC_Core.terraSilverIngot),true,AnvilReq.COPPER));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot),new ItemStack(mod_TFC_Core.terraSteelIngot),true,AnvilReq.WROUGHTIRON));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSterlingSilverIngot),new ItemStack(mod_TFC_Core.terraSterlingSilverIngot),true,AnvilReq.BRONZE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot),new ItemStack(mod_TFC_Core.terraTinIngot),true,AnvilReq.STONE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot),new ItemStack(mod_TFC_Core.terraZincIngot),true,AnvilReq.STONE));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWeakSteelIngot),new ItemStack(mod_TFC_Core.terraPigIronIngot),true,AnvilReq.WROUGHTIRON));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWeakBlueSteelIngot),new ItemStack(mod_TFC_Core.terraBlackSteelIngot),true,AnvilReq.BLACKSTEEL));
	    manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWeakRedSteelIngot),new ItemStack(mod_TFC_Core.terraBlackSteelIngot),true,AnvilReq.BLACKSTEEL));
	    
	    /**
	     * Normal Recipes Start Here
	     */
	    //Ingots
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedBismuth), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraBismuthIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedBismuthBronze), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedBlackBronze), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBlackBronzeIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedBlackSteel), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.terraBlackSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedBlueSteel), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraBlueSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedBrass), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBrassIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedBronze), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBronzeIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedCopper), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraCopperIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedGold), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraGoldIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedWroughtIron), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraWroughtIronIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedLead), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraLeadIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedNickel), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraNickelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedPigIron), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraPigIronIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedPlatinum), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraPlatinumIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedRedSteel), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraRedSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedRoseGold), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraRoseGoldIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedSilver), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraSilverIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedSteel), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedSterlingSilver), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraSterlingSilverIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedTin), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraTinIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedZinc), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraZincIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraPigIronIngot), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraWroughtIronIngot))); 
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakBlueSteel), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraWeakBlueSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakRedSteel), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraWeakRedSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakSteel), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraWeakSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCBlackSteel), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.terraHCBlackSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCBlueSteel), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraHCBlueSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCRedSteel), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraHCRedSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCSteel), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraHCSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCBlackSteelIngot), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.terraBlackSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCBlueSteelIngot), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraBlueSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCRedSteelIngot), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraRedSteelIngot)));
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCSteelIngot), null,89,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraSteelIngot)));
	    
	    //Picks
	    manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzePickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzePickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzePickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldPickaxeHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinPickaxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),27+v,CraftingRule.PUNCHLAST, CraftingRule.BENDLASTTWO, CraftingRule.DRAWLASTTWO, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincPickaxeHead)));
        //shovels
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldShovelHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinShovelHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),28+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincShovelHead)));
        //axes
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldAxeHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinAxeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),29+v,CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincAxeHead)));
        //hoes
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHoeHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHoeHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),30+v,CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHoeHead)));
        //Hammers
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHammerHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHammerHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),31+v,CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHammerHead)));
        //Chisels
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldChiselHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinChiselHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),32+v,CraftingRule.HITLAST, CraftingRule.HITNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincChiselHead)));
        //ProPicks
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldProPickHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinProPickHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),33+v,CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincProPickHead)));
        //Saws
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldSawHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSawHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.SawBladePlan),34+v,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSawHead)));
        //Swords
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldSwordHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSwordHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.SwordBladePlan),45+v,CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSwordHead)));
        //Maces
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldMaceHead)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinMaceHead)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),45+v,CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincMaceHead)));
        
	}

	public static void registerRecipes()
	{

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
		CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.ProPickHeadPlan, 1), new Object[] { " ####","#   #","    #", Character.valueOf('#'), mod_TFC_Game.terraInk});
		//metallurgy
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedBismuthBronze, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedCopper),
			new ItemStack(mod_TFC_Game.UnshapedTin), new ItemStack(mod_TFC_Game.UnshapedBismuth)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedBlackBronze, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedCopper),
			new ItemStack(mod_TFC_Game.UnshapedSilver), new ItemStack(mod_TFC_Game.UnshapedGold)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedSteel),new ItemStack(mod_TFC_Game.UnshapedSteel),
			new ItemStack(mod_TFC_Game.UnshapedNickel), new ItemStack(mod_TFC_Game.UnshapedBlackBronze)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakBlueSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedBlackSteel), new ItemStack(mod_TFC_Game.UnshapedBismuthBronze), 
			new ItemStack(mod_TFC_Game.UnshapedSterlingSilver),new ItemStack(mod_TFC_Game.UnshapedSteel)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedBrass, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedCopper),
			new ItemStack(mod_TFC_Game.UnshapedCopper), new ItemStack(mod_TFC_Game.UnshapedZinc)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedBronze, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedCopper),
			new ItemStack(mod_TFC_Game.UnshapedCopper), new ItemStack(mod_TFC_Game.UnshapedTin)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakRedSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedBlackSteel), new ItemStack(mod_TFC_Game.UnshapedRoseGold),  
			new ItemStack(mod_TFC_Game.UnshapedPlatinum), new ItemStack(mod_TFC_Game.UnshapedSteel)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedRoseGold, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedGold),
			new ItemStack(mod_TFC_Game.UnshapedGold), new ItemStack(mod_TFC_Game.UnshapedGold)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedHCSteel, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedPigIron),new ItemStack(mod_TFC_Game.UnshapedWroughtIron),
			new ItemStack(mod_TFC_Game.UnshapedWroughtIron), new ItemStack(mod_TFC_Game.UnshapedWroughtIron)});
		CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedSterlingSilver, 4), 
				new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedSilver),
			new ItemStack(mod_TFC_Game.UnshapedSilver), new ItemStack(mod_TFC_Game.UnshapedSilver)});


	}
}
