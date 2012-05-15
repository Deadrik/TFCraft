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
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot),new ItemStack(mod_TFC_Core.terraBismuthIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraBismuthIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot),new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot),new ItemStack(mod_TFC_Core.terraBlackBronzeIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBlackBronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot),new ItemStack(mod_TFC_Core.terraBlackSteelIngot),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Core.terraBlackSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot),new ItemStack(mod_TFC_Core.terraBlueSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraBlueSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBrassIngot),new ItemStack(mod_TFC_Core.terraBrassIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBrassIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot),new ItemStack(mod_TFC_Core.terraBronzeIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraBronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot),new ItemStack(mod_TFC_Core.terraCopperIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraCopperIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraGoldIngot),new ItemStack(mod_TFC_Core.terraGoldIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraGoldIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot),new ItemStack(mod_TFC_Core.terraWroughtIronIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraWroughtIronIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraLeadIngot),new ItemStack(mod_TFC_Core.terraLeadIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraLeadIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraNickelIngot),new ItemStack(mod_TFC_Core.terraNickelIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraNickelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraPigIronIngot),new ItemStack(mod_TFC_Core.terraPigIronIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraPigIronIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraPlatinumIngot),new ItemStack(mod_TFC_Core.terraPlatinumIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraPlatinumIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot),new ItemStack(mod_TFC_Core.terraRedSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraRedSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot),new ItemStack(mod_TFC_Core.terraRoseGoldIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraRoseGoldIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSilverIngot),new ItemStack(mod_TFC_Core.terraSilverIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.terraSilverIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot),new ItemStack(mod_TFC_Core.terraSteelIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSterlingSilverIngot),new ItemStack(mod_TFC_Core.terraSterlingSilverIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraSterlingSilverIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot),new ItemStack(mod_TFC_Core.terraTinIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraTinIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot),new ItemStack(mod_TFC_Core.terraZincIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.terraZincIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWeakSteelIngot),new ItemStack(mod_TFC_Core.terraPigIronIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraHCBlackSteelIngot, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWeakBlueSteelIngot),new ItemStack(mod_TFC_Core.terraBlackSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraHCBlueSteelIngot, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWeakRedSteelIngot),new ItemStack(mod_TFC_Core.terraBlackSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraHCRedSteelIngot, 1)));
        
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet),new ItemStack(mod_TFC_Game.BismuthSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet),new ItemStack(mod_TFC_Game.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet),new ItemStack(mod_TFC_Game.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BlackBronzeSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet),new ItemStack(mod_TFC_Game.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Game.BlackSteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet),new ItemStack(mod_TFC_Game.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlueSteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet),new ItemStack(mod_TFC_Game.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BronzeSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet),new ItemStack(mod_TFC_Game.CopperSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.CopperSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet),new ItemStack(mod_TFC_Game.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.WroughtIronSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet),new ItemStack(mod_TFC_Game.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.RedSteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet),new ItemStack(mod_TFC_Game.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.RoseGoldSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet),new ItemStack(mod_TFC_Game.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.SteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet),new ItemStack(mod_TFC_Game.TinSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet),new ItemStack(mod_TFC_Game.ZincSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSheet2x, 1)));
        //chest
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.BismuthSheet2x),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.BlackBronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.BlackSteelSheet2x),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.BlueSteelSheet2x),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.BronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.CopperSheet2x),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.WroughtIronSheet2x),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.RedSteelSheet2x),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.RoseGoldSheet2x),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.SteelSheet2x),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.TinSheet2x),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate,1,0),new ItemStack(mod_TFC_Game.ZincSheet2x),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate,1,1)));
        //greaves
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.BismuthSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.CopperSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.TinSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves,1,0),new ItemStack(mod_TFC_Game.ZincSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves,1,1)));
        //Helmet
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BismuthSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.CopperSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.TinSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.ZincSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,1)));
        //Boots
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BismuthSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.CopperSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.TinSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,0),new ItemStack(mod_TFC_Game.ZincSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,1)));
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
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraPigIronIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.terraWroughtIronIngot))); 
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakBlueSteel), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraWeakBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakRedSteel), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraWeakRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedWeakSteel), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraWeakSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCBlackSteel), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.terraHCBlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCBlueSteel), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraHCBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCRedSteel), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraHCRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.UnshapedHCSteel), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraHCSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCBlackSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.terraBlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCBlueSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCRedSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.terraRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraHCSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.terraSteelIngot)));
        
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot2x), null, -35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldSheet)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot2x), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSheet)));
        
        //Picks
        R = new Random (valueOfString("Picks"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan), -35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldPickaxeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincPickaxeHead, 1, dam)));
        }
        //shovels
        R = new Random (valueOfString("Shovels"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldShovelHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincShovelHead, 1, dam)));
        }
        //axes
        R = new Random (valueOfString("Axes"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldAxeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincAxeHead, 1, dam)));
        }
        //hoes
        R = new Random (valueOfString("Hoes"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHoeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHoeHead, 1, dam)));
        }
        //Hammers
        R = new Random (valueOfString("Hammers"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHammerHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHammerHead, 1, dam)));
        }
        //Chisels
        R = new Random (valueOfString("Chisels"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldChiselHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincChiselHead, 1, dam)));
        }
        //ProPicks
        R = new Random (valueOfString("Pro Picks"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldProPickHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),-35 + R.nextInt(105),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincProPickHead, 1, dam)));
        }
        //Saws
        R = new Random (valueOfString("Saws"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldSawHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot), new ItemStack(mod_TFC_Game.SawBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSawHead, 1, dam)));
        }
        //Swords
        R = new Random (valueOfString("Swords"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldSwordHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSwordHead, 1, dam)));
        }
        //Maces
        R = new Random (valueOfString("Maces"));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraCopperIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRedSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldMaceHead, 1, dam)));      
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraTinIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.terraZincIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincMaceHead, 1, dam)));
        }
        
        R = new Random (valueOfString("Helmet"));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedHelmet, 1)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet2x), new ItemStack(mod_TFC_Game.HelmetPlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHelmet, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHelmet, 1, dam)));
        }
        
        R = new Random (valueOfString("Chestplate"));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldChestplate, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate,1,1), null,-35 + R.nextInt(105),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincChestplate, 1, dam)));
        }
        
        R = new Random (valueOfString("Greaves"));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldGreaves, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincGreaves, 1, dam)));
        }
        
        R = new Random (valueOfString("Boots"));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet2x), new ItemStack(mod_TFC_Game.BootsPlan),-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedBoots, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;    
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldBoots, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedBoots,1,1), null,-35 + R.nextInt(105),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincBoots, 1, dam)));
        }

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
        }
        else return 0;
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
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraRoseGoldAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraRoseGoldIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBismuthBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraBismuthBronzeIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBlackBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraBlackBronzeIngot2x});

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

        for(int i = 0; i <= 25; i+=5)
        {
            float j = (float)i / 100;
            //pickaxes
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthPick, 1, (int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzePick, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzePick, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelPick, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelPick, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzePick, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperPick, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronPick, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelPick, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldPick, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelPick, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinPick, 1, (int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincPick, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Shovels
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthShovel, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeShovel, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeShovel, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelShovel, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelShovel, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeShovel, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperShovel, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronShovel, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelShovel, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldShovel, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelShovel, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinShovel, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincShovel, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Hoes
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthHoe, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeHoe, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeHoe, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelHoe, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelHoe, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeHoe, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperHoe, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronHoe, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelHoe, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldHoe, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelHoe, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinHoe, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincHoe, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Axes
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthAxe, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeAxe, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeAxe, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelAxe, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelAxe, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeAxe, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperAxe, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronAxe, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelAxe, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldAxe, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelAxe, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinAxe, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincAxe, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Swords
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthSword, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeSword, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeSword, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelSword, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelSword, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeSword, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperSword, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronSword, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelSword, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldSword, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelSword, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinSword, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincSword, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Maces
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthMace, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeMace, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeMace, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelMace, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelMace, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeMace, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperMace, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronMace, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelMace, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldMace, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelMace, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinMace, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincMace, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Hammers
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BismuthHammer, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeHammer, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BlackBronzeHammer, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BlackSteelHammer, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BlueSteelHammer, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.BronzeHammer, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.CopperHammer, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.WroughtIronHammer, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.RedSteelHammer, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.RoseGoldHammer, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.SteelHammer, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.TinHammer, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.ZincHammer, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Saws
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthSaw, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeSaw, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeSaw, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelSaw, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelSaw, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeSaw, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperSaw, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronSaw, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelSaw, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldSaw, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelSaw, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinSaw, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincSaw, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Chisels
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthChisel, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeChisel, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackBronzeChisel, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlackSteelChisel, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BlueSteelChisel, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.BronzeChisel, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.CopperChisel, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.WroughtIronChisel, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RedSteelChisel, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.RoseGoldChisel, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.SteelChisel, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.TinChisel, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Core.ZincChisel, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //propicks
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBismuth, 1,(int)(mod_TFC_Core.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBismuthBronze, 1,(int)(mod_TFC_Core.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BismuthBronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBlackBronze, 1,(int)(mod_TFC_Core.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackBronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBlackSteel, 1,(int)(mod_TFC_Core.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlackSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBlueSteel, 1,(int)(mod_TFC_Core.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BlueSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickBronze, 1,(int)(mod_TFC_Core.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.BronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickCopper, 1,(int)(mod_TFC_Core.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.CopperProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickIron, 1,(int)(mod_TFC_Core.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.WroughtIronProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickRedSteel, 1,(int)(mod_TFC_Core.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RedSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickRoseGold, 1,(int)(mod_TFC_Core.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.RoseGoldProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickSteel, 1,(int)(mod_TFC_Core.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.SteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickTin, 1,(int)(mod_TFC_Core.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.TinProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickZinc, 1,(int)(mod_TFC_Core.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(mod_TFC_Game.ZincProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
        }

        //stone prospecting pick
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
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
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.HelmetPlan, 1), new Object[] { "#####","#   #","#   #", Character.valueOf('#'), mod_TFC_Game.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.ChestplatePlan, 1), new Object[] { "#   #","#####","#####","#####","#####", Character.valueOf('#'), mod_TFC_Game.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.GreavesPlan, 1), new Object[] { "#####","#####","## ##","## ##","## ##", Character.valueOf('#'), mod_TFC_Game.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(mod_TFC_Game.BootsPlan, 1), new Object[] { "##   ","##   ","##   ","#### ","#####", Character.valueOf('#'), mod_TFC_Game.terraInk});
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
            new ItemStack(mod_TFC_Game.UnshapedBrass), new ItemStack(mod_TFC_Game.UnshapedSteel)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedRoseGold, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedGold),
            new ItemStack(mod_TFC_Game.UnshapedGold), new ItemStack(mod_TFC_Game.UnshapedGold)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedHCSteel, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedPigIron),new ItemStack(mod_TFC_Game.UnshapedWroughtIron),
            new ItemStack(mod_TFC_Game.UnshapedWroughtIron), new ItemStack(mod_TFC_Game.UnshapedWroughtIron)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.UnshapedSterlingSilver, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.UnshapedCopper),new ItemStack(mod_TFC_Game.UnshapedSilver),
            new ItemStack(mod_TFC_Game.UnshapedSilver), new ItemStack(mod_TFC_Game.UnshapedSilver)});

        //Gold Pan
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraGoldPan, 1, 0), new Object[] { 
            "1", Character.valueOf('1'),Item.bowlEmpty});
        //Sluice
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraSluiceItem, 1), new Object[] { 
            "  1"," 12","122", Character.valueOf('1'),Item.stick, Character.valueOf('2'),Block.planks});


    }
}
