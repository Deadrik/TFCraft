package net.minecraft.src.TFC_Game;

import java.util.Hashtable;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TFCItems;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.mod_TFC_Game;
import net.minecraft.src.TFC_Core.General.AnvilRecipe;
import net.minecraft.src.TFC_Core.General.AnvilReq;
import net.minecraft.src.TFC_Core.General.CraftingRule;

public class TFC_Game 
{
    public static Item[] MeltedMetal = {TFCItems.BismuthUnshaped,TFCItems.BismuthBronzeUnshaped,TFCItems.BlackBronzeUnshaped,
        TFCItems.BlackSteelUnshaped,TFCItems.BlueSteelUnshaped,TFCItems.BrassUnshaped,TFCItems.BronzeUnshaped,
        TFCItems.CopperUnshaped,TFCItems.GoldUnshaped,
        TFCItems.WroughtIronUnshaped,TFCItems.LeadUnshaped,TFCItems.NickelUnshaped,TFCItems.PigIronUnshaped,
        TFCItems.PlatinumUnshaped,TFCItems.RedSteelUnshaped,TFCItems.RoseGoldUnshaped,TFCItems.SilverUnshaped,
        TFCItems.SteelUnshaped,TFCItems.SterlingSilverUnshaped,
        TFCItems.TinUnshaped,TFCItems.ZincUnshaped, TFCItems.HCSteelUnshaped, TFCItems.WeakSteelUnshaped,
        TFCItems.HCBlackSteelUnshaped, TFCItems.HCBlueSteelUnshaped, TFCItems.HCRedSteelUnshaped, 
        TFCItems.WeakBlueSteelUnshaped, TFCItems.WeakRedSteelUnshaped};


    public static Item[] Hammers = {TFCItems.BismuthHammer,TFCItems.BismuthBronzeHammer,TFCItems.BlackBronzeHammer,
        TFCItems.BlackSteelHammer,TFCItems.BlueSteelHammer,TFCItems.BronzeHammer,TFCItems.CopperHammer,
        TFCItems.WroughtIronHammer,TFCItems.RedSteelHammer,TFCItems.RoseGoldHammer,TFCItems.SteelHammer,
        TFCItems.TinHammer,TFCItems.ZincHammer};

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
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot),new ItemStack(TFCItems.BismuthIngot),true,AnvilReq.STONE, new ItemStack(TFCItems.BismuthIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot),new ItemStack(TFCItems.BismuthBronzeIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot),new ItemStack(TFCItems.BlackBronzeIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot),new ItemStack(TFCItems.BlackSteelIngot),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot),new ItemStack(TFCItems.BlueSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BrassIngot),new ItemStack(TFCItems.BrassIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.BrassIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot),new ItemStack(TFCItems.BronzeIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot),new ItemStack(TFCItems.CopperIngot),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.GoldIngot),new ItemStack(TFCItems.GoldIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.GoldIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot),new ItemStack(TFCItems.WroughtIronIngot),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.LeadIngot),new ItemStack(TFCItems.LeadIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.LeadIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.NickelIngot),new ItemStack(TFCItems.NickelIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.NickelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronIngot),new ItemStack(TFCItems.PigIronIngot),true,AnvilReq.BRONZE, new ItemStack(TFCItems.PigIronIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.PlatinumIngot),new ItemStack(TFCItems.PlatinumIngot),true,AnvilReq.BRONZE, new ItemStack(TFCItems.PlatinumIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot),new ItemStack(TFCItems.RedSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot),new ItemStack(TFCItems.RoseGoldIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SilverIngot),new ItemStack(TFCItems.SilverIngot),true,AnvilReq.COPPER, new ItemStack(TFCItems.SilverIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot),new ItemStack(TFCItems.SteelIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SterlingSilverIngot),new ItemStack(TFCItems.SterlingSilverIngot),true,AnvilReq.BRONZE, new ItemStack(TFCItems.SterlingSilverIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot),new ItemStack(TFCItems.TinIngot),true,AnvilReq.STONE, new ItemStack(TFCItems.TinIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot),new ItemStack(TFCItems.ZincIngot),true,AnvilReq.STONE, new ItemStack(TFCItems.ZincIngot2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakSteelIngot),new ItemStack(TFCItems.PigIronIngot),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.HCBlackSteelIngot, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakBlueSteelIngot),new ItemStack(TFCItems.BlackSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCBlueSteelIngot, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakRedSteelIngot),new ItemStack(TFCItems.BlackSteelIngot),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCRedSteelIngot, 1)));
        
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthSheet),new ItemStack(TFCItems.BismuthSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.BismuthSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet),new ItemStack(TFCItems.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet),new ItemStack(TFCItems.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet),new ItemStack(TFCItems.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet),new ItemStack(TFCItems.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet),new ItemStack(TFCItems.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet),new ItemStack(TFCItems.CopperSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet),new ItemStack(TFCItems.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet),new ItemStack(TFCItems.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldSheet),new ItemStack(TFCItems.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet),new ItemStack(TFCItems.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinSheet),new ItemStack(TFCItems.TinSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.TinSheet2x, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincSheet),new ItemStack(TFCItems.ZincSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.ZincSheet2x, 1)));
        //chest
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedChestplate,1,0),new ItemStack(TFCItems.BismuthSheet2x),true,AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.BismuthBronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.BlackBronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.BlackSteelSheet2x),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.BlueSteelSheet2x),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,0),new ItemStack(TFCItems.BronzeSheet2x),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,0),new ItemStack(TFCItems.CopperSheet2x),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,0),new ItemStack(TFCItems.WroughtIronSheet2x),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.RedSteelSheet2x),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedChestplate,1,0),new ItemStack(TFCItems.RoseGoldSheet2x),true,AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,0),new ItemStack(TFCItems.SteelSheet2x),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedChestplate,1,0),new ItemStack(TFCItems.TinSheet2x),true,AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedChestplate,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedChestplate,1,0),new ItemStack(TFCItems.ZincSheet2x),true,AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedChestplate,1,1)));
        //greaves
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedGreaves,1,0),new ItemStack(TFCItems.BismuthSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,0),new ItemStack(TFCItems.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,0),new ItemStack(TFCItems.CopperSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,0),new ItemStack(TFCItems.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedGreaves,1,0),new ItemStack(TFCItems.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,0),new ItemStack(TFCItems.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedGreaves,1,0),new ItemStack(TFCItems.TinSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedGreaves,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedGreaves,1,0),new ItemStack(TFCItems.ZincSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedGreaves,1,1)));
        //Helmet
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedHelmet,1,0),new ItemStack(TFCItems.BismuthSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,0),new ItemStack(TFCItems.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,0),new ItemStack(TFCItems.CopperSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,0),new ItemStack(TFCItems.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedHelmet,1,0),new ItemStack(TFCItems.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,0),new ItemStack(TFCItems.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedHelmet,1,0),new ItemStack(TFCItems.TinSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedHelmet,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedHelmet,1,0),new ItemStack(TFCItems.ZincSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedHelmet,1,1)));
        //Boots
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedBoots,1,0),new ItemStack(TFCItems.BismuthSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.BismuthBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.BlackBronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.BlackSteelSheet),true,AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.BlueSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,0),new ItemStack(TFCItems.BronzeSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.BronzeUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedBoots,1,0),new ItemStack(TFCItems.CopperSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.CopperUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,0),new ItemStack(TFCItems.WroughtIronSheet),true,AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,0),new ItemStack(TFCItems.RedSteelSheet),true,AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedBoots,1,0),new ItemStack(TFCItems.RoseGoldSheet),true,AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedBoots,1,0),new ItemStack(TFCItems.SteelSheet),true,AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedBoots,1,0),new ItemStack(TFCItems.TinSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedBoots,1, 1)));
        manager.addWeldRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedBoots,1,0),new ItemStack(TFCItems.ZincSheet),true,AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedBoots,1,1)));
        /**
         * Normal Recipes Start Here
         */
        //Ingots
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BismuthBronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BlackBronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BrassUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BrassIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.BronzeIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.CopperIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.GoldUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.GoldIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.LeadUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.LeadIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.NickelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.NickelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.PigIronIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PlatinumUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.PlatinumIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.RoseGoldIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SilverUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.SilverIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SterlingSilverUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.SterlingSilverIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnshaped), null,19,CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.PigIronIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.WroughtIronIngot))); 
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakBlueSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.WeakBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakRedSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.WeakRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WeakSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WeakSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlackSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.HCBlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlueSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCBlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCRedSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.HCRedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCSteelUnshaped), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.HCSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlackSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.BlackSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCBlueSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlueSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCRedSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.RedSteelIngot)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.HCSteelIngot), null,70 + R.nextInt(15),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.SteelIngot)));
        
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot2x), null, 20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldSheet)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinSheet)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot2x), null,20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.HITTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincSheet)));
        
        //Picks
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Picks") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.PickaxeHeadPlan), 20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzePickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldPickaxeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinPickaxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.PickaxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.BENDNOTLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincPickaxeHead, 1, dam)));
        }
        //shovels
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Shovels") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldShovelHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(TFCItems.TinShovelHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.ShovelHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.HITLASTTHREE, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincShovelHead, 1, dam)));
        }
        //axes 
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Axes") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.RoseGoldAxeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinAxeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.AxeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincAxeHead, 1, dam)));
        }
        //hoes
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Hoes") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldHoeHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(TFCItems.TinHoeHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.HoeHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.HITLASTTHREE, CraftingRule.BENDLASTTHREE, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincHoeHead, 1, dam)));
        }
        //Hammers
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Hammers") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldHammerHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinHammerHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.HammerHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.ANY, CraftingRule.SHRINKNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincHammerHead, 1, dam)));
        }
        //Chisels     
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Chisels") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldChiselHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinChiselHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.ChiselHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.DRAWNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincChiselHead, 1, dam)));
        }
        //ProPicks
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Pro Picks") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldProPickHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinProPickHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.ProPickHeadPlan),20 + R.nextInt(55),CraftingRule.PUNCHLAST, CraftingRule.DRAWNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincProPickHead, 1, dam)));
        }
        //Saws
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Saws") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldSawHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(TFCItems.TinSawHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot), new ItemStack(TFCItems.SawBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.ANY, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincSawHead, 1, dam)));
        }
        //Swords
        R = new Random (valueOfString("Swords") + world.getSeed());
        for(int i = 0; i < 5; i++)
        {
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldSwordHead, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinSwordHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot2x), new ItemStack(TFCItems.SwordBladePlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincSwordHead, 1, dam)));
        }
        //Maces
        
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Maces") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldMaceHead, 1, dam)));      
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinMaceHead, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincIngot2x), new ItemStack(TFCItems.MaceHeadPlan),20 + R.nextInt(55),CraftingRule.HITLAST, CraftingRule.SHRINKNOTLAST, CraftingRule.BENDNOTLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincMaceHead, 1, dam)));
        }
        
        R = new Random (valueOfString("Helmet") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldUnfinishedHelmet, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedHelmet, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedHelmet, 1)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincSheet), new ItemStack(TFCItems.HelmetPlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedHelmet, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Helmet") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldHelmet, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinHelmet, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedHelmet,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.BENDTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincHelmet, 1, dam)));
        }
        
        R = new Random (valueOfString("Chestplate") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldUnfinishedChestplate, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedChestplate, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincSheet2x), new ItemStack(TFCItems.ChestplatePlan),40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedChestplate, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Chestplate") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldChestplate, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinChestplate, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedChestplate,1,1), null,40 + R.nextInt(35),CraftingRule.HITLAST, CraftingRule.HITSECONDFROMLAST, CraftingRule.UPSETTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincChestplate, 1, dam)));
        }
        
        R = new Random (valueOfString("Greaves") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldUnfinishedGreaves, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedGreaves, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincSheet2x), new ItemStack(TFCItems.GreavesPlan),40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedGreaves, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Greaves") + world.getSeed());
            int dam = i * 5;
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldGreaves, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(TFCItems.TinGreaves, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedGreaves,1,1), null,40 + R.nextInt(35),CraftingRule.BENDANY, CraftingRule.DRAWANY, CraftingRule.HITANY, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincGreaves, 1, dam)));
        }
        
        R = new Random (valueOfString("Boots") + world.getSeed());
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldUnfinishedBoots, 1, 0)));       
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinUnfinishedBoots, 1, 0)));
        manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincSheet), new ItemStack(TFCItems.BootsPlan),40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincUnfinishedBoots, 1, 0)));
        for(int i = 0; i < 5; i++)
        {
            R = new Random (valueOfString("Boots") + world.getSeed());
            int dam = i * 5;    
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.BismuthBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BismuthBronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BISMUTHBRONZE, new ItemStack(TFCItems.BismuthBronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackBronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKBRONZE, new ItemStack(TFCItems.BlackBronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlackSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLACKSTEEL, new ItemStack(TFCItems.BlackSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BlueSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BLUESTEEL, new ItemStack(TFCItems.BlueSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.BronzeUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.BRONZE, new ItemStack(TFCItems.BronzeBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.CopperUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.COPPER, new ItemStack(TFCItems.CopperBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.WroughtIronUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.WROUGHTIRON, new ItemStack(TFCItems.WroughtIronBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RedSteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.REDSTEEL, new ItemStack(TFCItems.RedSteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.RoseGoldUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.ROSEGOLD, new ItemStack(TFCItems.RoseGoldBoots, 1, dam)));       
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.SteelUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STEEL, new ItemStack(TFCItems.SteelBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.TinUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.TinBoots, 1, dam)));
            manager.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.ZincUnfinishedBoots,1,1), null,40 + R.nextInt(35),CraftingRule.BENDLAST, CraftingRule.BENDSECONDFROMLAST, CraftingRule.SHRINKTHIRDFROMLAST, false, AnvilReq.STONE, new ItemStack(TFCItems.ZincBoots, 1, dam)));
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
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 5), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 9), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneMMCobble, 1, 5), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble, 1, 7), new ItemStack(Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 6), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(Hammers[j], 1, -1)});
        }

        ModLoader.addRecipe(new ItemStack(Item.redstone, 8, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(TFCItems.OreChunk,1,27)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraInk, 16, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(Item.dyePowder,1,0)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraFireStarter, 1, 0), new Object[] { "2 "," 2", Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBellowsItem, 1, 0), new Object[] { "###","???","###", Character.valueOf('#'), Block.planks, Character.valueOf('?'), Item.leather});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgIn});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgEx});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraCopperAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.CopperIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BronzeIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraWroughtIronAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.WroughtIronIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.SteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBlueSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlueSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraRedSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RedSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraRoseGoldAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RoseGoldIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BismuthBronzeIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackBronzeIngot2x});

        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraScribe, 1), new Object[] { " L ","#P#","###", Character.valueOf('#'), Block.planks,
            Character.valueOf('P'), Item.paper,Character.valueOf('L'), Item.feather});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraClayMold, 4), new Object[] { "# #","###", Character.valueOf('#'), Item.clay});

        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgEx});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgIn});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneSed});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneMM});

        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgExBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgInBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneSedBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneMMBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});

        //stone hammers
        ModLoader.addRecipe(new ItemStack(TFCItems.StoneHammer, 1), new Object[] { 
            "111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(TFCItems.StoneHammer, 1), new Object[] { 
            "111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(TFCItems.StoneHammer, 1), new Object[] { 
            "111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(TFCItems.StoneHammer, 1), new Object[] { 
            "111","121"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});

        for(int i = 0; i <= 25; i+=5)
        {
            float j = (float)i / 100;
            //pickaxes
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthPick, 1, (int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthBronzePick, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackBronzePick, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackSteelPick, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlueSteelPick, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBronzePick, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzePickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraCopperPick, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraWroughtIronPick, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRedSteelPick, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRoseGoldPick, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraSteelPick, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraTinPick, 1, (int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraZincPick, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincPickaxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Shovels
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthShovel, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthBronzeShovel, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackBronzeShovel, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackSteelShovel, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlueSteelShovel, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBronzeShovel, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraCopperShovel, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraWroughtIronShovel, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRedSteelShovel, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRoseGoldShovel, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraSteelShovel, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraTinShovel, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraZincShovel, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincShovelHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Hoes
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthHoe, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthBronzeHoe, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackBronzeHoe, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackSteelHoe, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlueSteelHoe, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBronzeHoe, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraCopperHoe, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraWroughtIronHoe, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRedSteelHoe, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRoseGoldHoe, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraSteelHoe, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraTinHoe, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraZincHoe, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincHoeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Axes
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthAxe, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthBronzeAxe, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackBronzeAxe, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackSteelAxe, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBlueSteelAxe, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraBronzeAxe, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraCopperAxe, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraWroughtIronAxe, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRedSteelAxe, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraRoseGoldAxe, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraSteelAxe, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraTinAxe, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraZincAxe, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincAxeHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Swords
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthSword, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthBronzeSword, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackBronzeSword, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackSteelSword, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlueSteelSword, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BronzeSword, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.CopperSword, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.WroughtIronSword, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RedSteelSword, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RoseGoldSword, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.SteelSword, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.TinSword, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.ZincSword, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincSwordHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Maces
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthMace, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthBronzeMace, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackBronzeMace, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackSteelMace, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlueSteelMace, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BronzeMace, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.CopperMace, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.WroughtIronMace, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RedSteelMace, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RoseGoldMace, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.SteelMace, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.TinMace, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.ZincMace, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincMaceHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Hammers
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthHammer, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthBronzeHammer, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackBronzeHammer, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackSteelHammer, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlueSteelHammer, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BronzeHammer, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.CopperHammer, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.WroughtIronHammer, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RedSteelHammer, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RoseGoldHammer, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.SteelHammer, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.TinHammer, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.ZincHammer, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincHammerHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Saws
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthSaw, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthBronzeSaw, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackBronzeSaw, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackSteelSaw, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlueSteelSaw, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BronzeSaw, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.CopperSaw, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.WroughtIronSaw, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RedSteelSaw, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RoseGoldSaw, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.SteelSaw, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.TinSaw, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.ZincSaw, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincSawHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //Chisels
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthChisel, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BismuthBronzeChisel, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackBronzeChisel, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlackSteelChisel, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BlueSteelChisel, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.BronzeChisel, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.CopperChisel, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.WroughtIronChisel, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RedSteelChisel, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.RoseGoldChisel, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.SteelChisel, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.TinChisel, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.ZincChisel, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincChiselHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});

            //propicks
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickBismuth, 1,(int)(TFCItems.BismuthUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickBismuthBronze, 1,(int)(TFCItems.BismuthBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BismuthBronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickBlackBronze, 1,(int)(TFCItems.BlackBronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackBronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickBlackSteel, 1,(int)(TFCItems.BlackSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlackSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickBlueSteel, 1,(int)(TFCItems.BlueSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BlueSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickBronze, 1,(int)(TFCItems.BronzeUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.BronzeProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickCopper, 1,(int)(TFCItems.CopperUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.CopperProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickIron, 1,(int)(TFCItems.WroughtIronUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.WroughtIronProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickRedSteel, 1,(int)(TFCItems.RedSteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RedSteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickRoseGold, 1,(int)(TFCItems.RoseGoldUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.RoseGoldProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickSteel, 1,(int)(TFCItems.SteelUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.SteelProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickTin, 1,(int)(TFCItems.TinUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.TinProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
            ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickZinc, 1,(int)(TFCItems.ZincUses*j)), new Object[] { "#","I", Character.valueOf('#'), 
                new ItemStack(TFCItems.ZincProPickHead, 1, i), Character.valueOf('I'), new ItemStack(Item.stick)});
        }

        //stone prospecting pick
        ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
            "111"," 21"," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
        //scribing table
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.PickaxeHeadPlan, 1), new Object[] { " ### ","#   #", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ShovelHeadPlan, 1), new Object[] { " ### "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.HoeHeadPlan, 1), new Object[] { "#####","   ##", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.AxeHeadPlan, 1), new Object[] { " #   ","#### ","#####","#### "," #   ", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.HammerHeadPlan, 1), new Object[] { "#####","#####","  #  ", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ChiselHeadPlan, 1), new Object[] { "  #  ","  #  ","  #  ","  #  ","  #  ", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SwordBladePlan, 1), new Object[] { "   ##","  ###"," ### "," ##  ","#    ", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.MaceHeadPlan, 1), new Object[] { "  #  "," ### "," ### "," ### ","  #  ", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.SawBladePlan, 1), new Object[] { "##   ","###  "," ### "," ####","   ##", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ProPickHeadPlan, 1), new Object[] { " ####","#   #","    #", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.HelmetPlan, 1), new Object[] { "#####","#   #","#   #", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.ChestplatePlan, 1), new Object[] { "#   #","#####","#####","#####","#####", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.GreavesPlan, 1), new Object[] { "#####","#####","## ##","## ##","## ##", Character.valueOf('#'), TFCItems.terraInk});
        CraftingManagerTFC.getInstance().addRecipe(new ItemStack(TFCItems.BootsPlan, 1), new Object[] { "##   ","##   ","##   ","#### ","#####", Character.valueOf('#'), TFCItems.terraInk});
        //metallurgy
        CraftingManagerTFC.getInstance().addShapelessRecipe(new ItemStack(TFCItems.BismuthBronzeUnshaped, 4), 
                new Object[] {  new ItemStack(TFCItems.CopperUnshaped),new ItemStack(TFCItems.CopperUnshaped),
            new ItemStack(TFCItems.TinUnshaped), new ItemStack(TFCItems.BismuthUnshaped)});
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

        //Gold Pan
        ModLoader.addRecipe(new ItemStack(TFCItems.terraGoldPan, 1, 0), new Object[] { 
            "1", Character.valueOf('1'),Item.bowlEmpty});
        //Sluice
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSluiceItem, 1), new Object[] { 
            "  1"," 12","122", Character.valueOf('1'),Item.stick, Character.valueOf('2'),Block.planks});


    }
}
