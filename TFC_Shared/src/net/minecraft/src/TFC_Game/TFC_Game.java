package net.minecraft.src.TFC_Game;

import java.util.Hashtable;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.TFC_Core.General.AnvilRecipe;
import net.minecraft.src.TFC_Core.General.AnvilReq;
import net.minecraft.src.TFC_Core.General.CraftingRule;

public class TFC_Game 
{
    public static Item[] MeltedMetal = {mod_TFC_Game.BismuthUnshaped,mod_TFC_Game.BismuthBronzeUnshaped,mod_TFC_Game.BlackBronzeUnshaped,
        mod_TFC_Game.BlackSteelUnshaped,mod_TFC_Game.BlueSteelUnshaped,mod_TFC_Game.BrassUnshaped,mod_TFC_Game.BronzeUnshaped,
        mod_TFC_Game.CopperUnshaped,mod_TFC_Game.GoldUnshaped,
        mod_TFC_Game.WroughtIronUnshaped,mod_TFC_Game.LeadUnshaped,mod_TFC_Game.NickelUnshaped,mod_TFC_Game.PigIronUnshaped,
        mod_TFC_Game.PlatinumUnshaped,mod_TFC_Game.RedSteelUnshaped,mod_TFC_Game.RoseGoldUnshaped,mod_TFC_Game.SilverUnshaped,
        mod_TFC_Game.SteelUnshaped,mod_TFC_Game.SterlingSilverUnshaped,
        mod_TFC_Game.TinUnshaped,mod_TFC_Game.ZincUnshaped, mod_TFC_Game.HCSteelUnshaped, mod_TFC_Game.WeakSteelUnshaped,
        mod_TFC_Game.HCBlackSteelUnshaped, mod_TFC_Game.HCBlueSteelUnshaped, mod_TFC_Game.HCRedSteelUnshaped, 
        mod_TFC_Game.WeakBlueSteelUnshaped, mod_TFC_Game.WeakRedSteelUnshaped};


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

    public static void registerAnvilRecipes(Random R, World world)
    {
        AnvilCraftingManagerTFC manager = AnvilCraftingManagerTFC.getInstance();
        int v = -5 + R.nextInt(5);

        /**
         * Weld Recipes
         */
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot),new ItemStack(mod_TFC_Core.BismuthIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.BismuthIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot),new ItemStack(mod_TFC_Core.BismuthBronzeIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BismuthBronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot),new ItemStack(mod_TFC_Core.BlackBronzeIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BlackBronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot),new ItemStack(mod_TFC_Core.BlackSteelIngot),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Core.BlackSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot),new ItemStack(mod_TFC_Core.BlueSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.BlueSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BrassIngot),new ItemStack(mod_TFC_Core.BrassIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BrassIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot),new ItemStack(mod_TFC_Core.BronzeIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot),new ItemStack(mod_TFC_Core.CopperIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.CopperIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.GoldIngot),new ItemStack(mod_TFC_Core.GoldIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.GoldIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot),new ItemStack(mod_TFC_Core.WroughtIronIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.WroughtIronIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.LeadIngot),new ItemStack(mod_TFC_Core.LeadIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.LeadIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.NickelIngot),new ItemStack(mod_TFC_Core.NickelIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.NickelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.PigIronIngot),new ItemStack(mod_TFC_Core.PigIronIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.PigIronIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.PlatinumIngot),new ItemStack(mod_TFC_Core.PlatinumIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.PlatinumIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot),new ItemStack(mod_TFC_Core.RedSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.RedSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot),new ItemStack(mod_TFC_Core.RoseGoldIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.RoseGoldIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SilverIngot),new ItemStack(mod_TFC_Core.SilverIngot),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Core.SilverIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot),new ItemStack(mod_TFC_Core.SteelIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.SteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SterlingSilverIngot),new ItemStack(mod_TFC_Core.SterlingSilverIngot),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.SterlingSilverIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot),new ItemStack(mod_TFC_Core.TinIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.TinIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot),new ItemStack(mod_TFC_Core.ZincIngot),true,AnvilReq.STONE, new ItemStack(mod_TFC_Core.ZincIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WeakSteelIngot),new ItemStack(mod_TFC_Core.PigIronIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.HCBlackSteelIngot, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WeakBlueSteelIngot),new ItemStack(mod_TFC_Core.BlackSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.HCBlueSteelIngot, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WeakRedSteelIngot),new ItemStack(mod_TFC_Core.BlackSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.HCRedSteelIngot, 1)));
        
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
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.BismuthSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.CopperSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.CopperUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.SteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.TinSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedBoots,1,0),new ItemStack(mod_TFC_Game.ZincSheet),true,AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedBoots,1,1)));
        /**
         * Normal Recipes Start Here
         */
        //Ingots
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.BismuthIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BismuthBronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BlackBronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.BlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.BlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BrassUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BrassIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.BronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.CopperIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.GoldUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.GoldIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.WroughtIronIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.LeadUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.LeadIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.NickelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.NickelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.PigIronUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.PigIronIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.PlatinumUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.PlatinumIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.RedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.RoseGoldIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SilverUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Core.SilverIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.SteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SterlingSilverUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.SterlingSilverIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.TinIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Core.ZincIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.PigIronIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Core.WroughtIronIngot))); 
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WeakBlueSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.WeakBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WeakRedSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.WeakRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WeakSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.WeakSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.HCBlackSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.HCBlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.HCBlueSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.HCBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.HCRedSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.HCRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.HCSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.HCSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.HCBlackSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Core.BlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.HCBlueSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.BlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.HCRedSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Core.RedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.HCSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Core.SteelIngot)));
        
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot2x), null, 20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldSheet)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSheet)));
        
        //Picks
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Picks") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan), 20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldPickaxeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincPickaxeHead, 1, dam)));
        }
        //shovels
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Shovels") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldShovelHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincShovelHead, 1, dam)));
        }
        //axes 
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Axes") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.RoseGoldAxeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincAxeHead, 1, dam)));
        }
        //hoes
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Hoes") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHoeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHoeHead, 1, dam)));
        }
        //Hammers
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Hammers") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHammerHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHammerHead, 1, dam)));
        }
        //Chisels     
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Chisels") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldChiselHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincChiselHead, 1, dam)));
        }
        //ProPicks
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Pro Picks") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldProPickHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincProPickHead, 1, dam)));
        }
        //Saws
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Saws") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldSawHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot), new ItemStack(mod_TFC_Game.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSawHead, 1, dam)));
        }
        //Swords
        R = new Random (valueOfString("Swords") + world.getSeed());
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldSwordHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot2x), new ItemStack(mod_TFC_Game.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincSwordHead, 1, dam)));
        }
        //Maces
        
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Maces") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BismuthBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackBronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlackSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BlueSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.BronzeIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.CopperIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.WroughtIronIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RedSteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.RoseGoldIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldMaceHead, 1, dam)));      
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.SteelIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.TinIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Core.ZincIngot2x), new ItemStack(mod_TFC_Game.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincMaceHead, 1, dam)));
        }
        
        R = new Random (valueOfString("Helmet") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedHelmet, 1)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet), new ItemStack(mod_TFC_Game.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Helmet") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldHelmet, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincHelmet, 1, dam)));
        }
        
        R = new Random (valueOfString("Chestplate") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet2x), new ItemStack(mod_TFC_Game.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Chestplate") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldChestplate, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincChestplate, 1, dam)));
        }
        
        R = new Random (valueOfString("Greaves") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet2x), new ItemStack(mod_TFC_Game.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Greaves") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldGreaves, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincGreaves, 1, dam)));
        }
        
        R = new Random (valueOfString("Boots") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincSheet), new ItemStack(mod_TFC_Game.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincUnfinishedBoots, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Boots") + world.getSeed());
            int dam = i * 5;    
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.BismuthBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(mod_TFC_Game.BismuthBronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(mod_TFC_Game.BlackBronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlackSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(mod_TFC_Game.BlackSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BlueSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(mod_TFC_Game.BlueSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.BronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(mod_TFC_Game.BronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.CopperUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(mod_TFC_Game.CopperBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.WroughtIronUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(mod_TFC_Game.WroughtIronBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RedSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(mod_TFC_Game.RedSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(mod_TFC_Game.RoseGoldBoots, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.SteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(mod_TFC_Game.SteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.TinUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.TinBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(mod_TFC_Game.ZincUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(mod_TFC_Game.ZincBoots, 1, dam)));
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
            ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 5), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 9), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneMMCobble, 1, 5), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 7), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Game.Flux, 6), new Object[] {new ItemStack(mod_TFC_Core.OreChunk, 1, 32), new ItemStack(Hammers[j], 1, -1)});
        }

        ModLoader.addRecipe(new ItemStack(Item.redstone, 8, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(mod_TFC_Core.OreChunk,1,27)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraInk, 16, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(Item.dyePowder,1,0)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraFireStarter, 1, 0), new Object[] { "2 "," 2", Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBellowsItem, 1, 0), new Object[] { "###","???","###", Character.valueOf('#'), Block.planks, Character.valueOf('?'), Item.leather});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgIn});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgEx});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraCopperAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.CopperIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.BronzeIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraWroughtIronAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.WroughtIronIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.SteelIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBlackSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.BlackSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBlueSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.BlueSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraRedSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.RedSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraRoseGoldAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.RoseGoldIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBismuthBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.BismuthBronzeIngot2x});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBlackBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.BlackBronzeIngot2x});

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
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.CopperUnshaped),new ItemStack(mod_TFC_Game.CopperUnshaped),
            new ItemStack(mod_TFC_Game.TinUnshaped), new ItemStack(mod_TFC_Game.BismuthUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.BlackBronzeUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.CopperUnshaped),new ItemStack(mod_TFC_Game.CopperUnshaped),
            new ItemStack(mod_TFC_Game.SilverUnshaped), new ItemStack(mod_TFC_Game.GoldUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.WeakSteelUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.SteelUnshaped),new ItemStack(mod_TFC_Game.SteelUnshaped),
            new ItemStack(mod_TFC_Game.NickelUnshaped), new ItemStack(mod_TFC_Game.BlackBronzeUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.WeakBlueSteelUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.BlackSteelUnshaped), new ItemStack(mod_TFC_Game.BismuthBronzeUnshaped), 
            new ItemStack(mod_TFC_Game.SterlingSilverUnshaped),new ItemStack(mod_TFC_Game.SteelUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.BrassUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.CopperUnshaped),new ItemStack(mod_TFC_Game.CopperUnshaped),
            new ItemStack(mod_TFC_Game.CopperUnshaped), new ItemStack(mod_TFC_Game.ZincUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.BronzeUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.CopperUnshaped),new ItemStack(mod_TFC_Game.CopperUnshaped),
            new ItemStack(mod_TFC_Game.CopperUnshaped), new ItemStack(mod_TFC_Game.TinUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.WeakRedSteelUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.BlackSteelUnshaped), new ItemStack(mod_TFC_Game.RoseGoldUnshaped),  
            new ItemStack(mod_TFC_Game.BrassUnshaped), new ItemStack(mod_TFC_Game.SteelUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.RoseGoldUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.CopperUnshaped),new ItemStack(mod_TFC_Game.GoldUnshaped),
            new ItemStack(mod_TFC_Game.GoldUnshaped), new ItemStack(mod_TFC_Game.GoldUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.HCSteelUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.PigIronUnshaped),new ItemStack(mod_TFC_Game.WroughtIronUnshaped),
            new ItemStack(mod_TFC_Game.WroughtIronUnshaped), new ItemStack(mod_TFC_Game.WroughtIronUnshaped)});
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(mod_TFC_Game.SterlingSilverUnshaped, 4), 
                new Object[] { 	new ItemStack(mod_TFC_Game.CopperUnshaped),new ItemStack(mod_TFC_Game.SilverUnshaped),
            new ItemStack(mod_TFC_Game.SilverUnshaped), new ItemStack(mod_TFC_Game.SilverUnshaped)});

        //Gold Pan
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraGoldPan, 1, 0), new Object[] { 
            "1", Character.valueOf('1'),Item.bowlEmpty});
        //Sluice
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraSluiceItem, 1), new Object[] { 
            "  1"," 12","122", Character.valueOf('1'),Item.stick, Character.valueOf('2'),Block.planks});


    }
}
