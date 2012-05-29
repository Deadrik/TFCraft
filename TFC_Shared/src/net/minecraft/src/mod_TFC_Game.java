package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.TFC_Core.*;
import net.minecraft.src.TFC_Core.General.AnvilReq;
import net.minecraft.src.TFC_Core.General.PacketHandler;
import net.minecraft.src.TFC_Core.General.TFCHeat;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.TFC_Game.*;
import net.minecraft.src.TFC_Mining.*;
import net.minecraft.src.forge.*;

public class mod_TFC_Game extends NetworkMod
{
    static Configuration config;
    public static mod_TFC_Game instance;
    public static IProxy proxy;

    public static int terraFirepitGuiId = 20;
    public static int terraAnvilGuiId = 21;
    public static int terraScribeGuiId = 22;
    public static int terraForgeGuiId = 23;
    public static int terraMetallurgyGuiId = 24;
    public static int terraSluiceGuiId = 25;
    public static int terraBloomeryGuiId = 26;

    public static Block terraFirepit;
    public static Block terraFirepitOn;
    public static Item terraFireStarter;
    public static Block terraBellows;
    public static Block terraAnvil;
    public static Block terraAnvil2;
    public static Block terraScribe;
    public static Block terraForge;
    public static Block terraForgeOn;
    public static Block terraBloomery;
    public static Block terraBloomeryOn;
    public static Block terraMetalTable;
    public static Block terraMolten;

    public static Item terraBellowsItem;
    public static Item terraStoneAnvilItem;
    public static Item terraBismuthBronzeAnvilItem;
    public static Item terraBlackBronzeAnvilItem;
    public static Item terraBlackSteelAnvilItem;
    public static Item terraBlueSteelAnvilItem;
    public static Item terraBronzeAnvilItem;
    public static Item terraCopperAnvilItem;
    public static Item terraWroughtIronAnvilItem;
    public static Item terraRedSteelAnvilItem;
    public static Item terraRoseGoldAnvilItem;
    public static Item terraSteelAnvilItem;

    public static Item StoneHammer;
    public static Item BismuthHammer;
    public static Item BismuthBronzeHammer;
    public static Item BlackBronzeHammer;
    public static Item BlackSteelHammer;
    public static Item BlueSteelHammer;
    public static Item BronzeHammer;
    public static Item CopperHammer;
    public static Item WroughtIronHammer;
    public static Item RedSteelHammer;
    public static Item RoseGoldHammer;
    public static Item SteelHammer;
    public static Item TinHammer;
    public static Item ZincHammer;

    public static Item BismuthUnshaped;
    public static Item BismuthBronzeUnshaped;
    public static Item BlackBronzeUnshaped;
    public static Item BlackSteelUnshaped;
    public static Item HCBlackSteelUnshaped;
    public static Item BlueSteelUnshaped;
    public static Item WeakBlueSteelUnshaped;
    public static Item HCBlueSteelUnshaped;
    public static Item BrassUnshaped;
    public static Item BronzeUnshaped;
    public static Item CopperUnshaped;
    public static Item GoldUnshaped;
    public static Item WroughtIronUnshaped;
    public static Item LeadUnshaped;
    public static Item NickelUnshaped;
    public static Item PigIronUnshaped;
    public static Item PlatinumUnshaped;
    public static Item RedSteelUnshaped;
    public static Item WeakRedSteelUnshaped;
    public static Item HCRedSteelUnshaped;
    public static Item RoseGoldUnshaped;
    public static Item SilverUnshaped;
    public static Item SteelUnshaped;
    public static Item WeakSteelUnshaped;
    public static Item HCSteelUnshaped;
    public static Item SterlingSilverUnshaped;
    public static Item TinUnshaped;
    public static Item ZincUnshaped;
    public static Item terraClayMold;
    public static Item terraCeramicMold;
    public static Item terraMeltedUnknown;

    public static Item terraSlag;
    public static Item terraInk;

    //Plans
    public static Item PickaxeHeadPlan;
    public static Item ShovelHeadPlan;
    public static Item HoeHeadPlan;
    public static Item AxeHeadPlan;
    public static Item HammerHeadPlan;
    public static Item ChiselHeadPlan;
    public static Item SwordBladePlan;
    public static Item MaceHeadPlan;
    public static Item SawBladePlan;
    public static Item ProPickHeadPlan;
    
    public static Item HelmetPlan;
    public static Item ChestplatePlan;
    public static Item GreavesPlan;
    public static Item BootsPlan;

    //Tool Heads
    public static Item BismuthPickaxeHead;
    public static Item BismuthBronzePickaxeHead;
    public static Item BlackBronzePickaxeHead;
    public static Item BlackSteelPickaxeHead;
    public static Item BlueSteelPickaxeHead;
    public static Item BronzePickaxeHead;
    public static Item CopperPickaxeHead;
    public static Item WroughtIronPickaxeHead;
    public static Item RedSteelPickaxeHead;
    public static Item RoseGoldPickaxeHead;
    public static Item SteelPickaxeHead;
    public static Item TinPickaxeHead;
    public static Item ZincPickaxeHead;

    public static Item BismuthShovelHead;
    public static Item BismuthBronzeShovelHead;
    public static Item BlackBronzeShovelHead;
    public static Item BlackSteelShovelHead;
    public static Item BlueSteelShovelHead;
    public static Item BronzeShovelHead;
    public static Item CopperShovelHead;
    public static Item WroughtIronShovelHead;
    public static Item RedSteelShovelHead;
    public static Item RoseGoldShovelHead;
    public static Item SilverShovelHead;
    public static Item SteelShovelHead;
    public static Item TinShovelHead;
    public static Item ZincShovelHead;

    public static Item BismuthHoeHead;
    public static Item BismuthBronzeHoeHead;
    public static Item BlackBronzeHoeHead;
    public static Item BlackSteelHoeHead;
    public static Item BlueSteelHoeHead;
    public static Item BronzeHoeHead;
    public static Item CopperHoeHead;
    public static Item WroughtIronHoeHead;
    public static Item RedSteelHoeHead;
    public static Item RoseGoldHoeHead;
    public static Item SteelHoeHead;
    public static Item TinHoeHead;
    public static Item ZincHoeHead;

    public static Item BismuthAxeHead;
    public static Item BismuthBronzeAxeHead;
    public static Item BlackBronzeAxeHead;
    public static Item BlackSteelAxeHead;
    public static Item BlueSteelAxeHead;
    public static Item BronzeAxeHead;
    public static Item CopperAxeHead;
    public static Item WroughtIronAxeHead;
    public static Item RedSteelAxeHead;
    public static Item RoseGoldAxeHead;
    public static Item SteelAxeHead;
    public static Item TinAxeHead;
    public static Item ZincAxeHead;

    public static Item BismuthHammerHead;
    public static Item BismuthBronzeHammerHead;
    public static Item BlackBronzeHammerHead;
    public static Item BlackSteelHammerHead;
    public static Item BlueSteelHammerHead;
    public static Item BronzeHammerHead;
    public static Item CopperHammerHead;
    public static Item WroughtIronHammerHead;
    public static Item RedSteelHammerHead;
    public static Item RoseGoldHammerHead;
    public static Item SteelHammerHead;
    public static Item TinHammerHead;
    public static Item ZincHammerHead;

    public static Item BismuthChiselHead;
    public static Item BismuthBronzeChiselHead;
    public static Item BlackBronzeChiselHead;
    public static Item BlackSteelChiselHead;
    public static Item BlueSteelChiselHead;
    public static Item BronzeChiselHead;
    public static Item CopperChiselHead;
    public static Item WroughtIronChiselHead;
    public static Item RedSteelChiselHead;
    public static Item RoseGoldChiselHead;
    public static Item SteelChiselHead;
    public static Item TinChiselHead;
    public static Item ZincChiselHead;

    public static Item BismuthSwordHead;
    public static Item BismuthBronzeSwordHead;
    public static Item BlackBronzeSwordHead;
    public static Item BlackSteelSwordHead;
    public static Item BlueSteelSwordHead;
    public static Item BronzeSwordHead;
    public static Item CopperSwordHead;
    public static Item WroughtIronSwordHead;
    public static Item RedSteelSwordHead;
    public static Item RoseGoldSwordHead;
    public static Item SteelSwordHead;
    public static Item TinSwordHead;
    public static Item ZincSwordHead;

    public static Item BismuthMaceHead;
    public static Item BismuthBronzeMaceHead;
    public static Item BlackBronzeMaceHead;
    public static Item BlackSteelMaceHead;
    public static Item BlueSteelMaceHead;
    public static Item BronzeMaceHead;
    public static Item CopperMaceHead;
    public static Item WroughtIronMaceHead;
    public static Item RedSteelMaceHead;
    public static Item RoseGoldMaceHead;
    public static Item SteelMaceHead;
    public static Item TinMaceHead;
    public static Item ZincMaceHead;

    public static Item BismuthSawHead;
    public static Item BismuthBronzeSawHead;
    public static Item BlackBronzeSawHead;
    public static Item BlackSteelSawHead;
    public static Item BlueSteelSawHead;
    public static Item BronzeSawHead;
    public static Item CopperSawHead;
    public static Item WroughtIronSawHead;
    public static Item RedSteelSawHead;
    public static Item RoseGoldSawHead;
    public static Item SteelSawHead;
    public static Item TinSawHead;
    public static Item ZincSawHead;

    public static Item BismuthProPickHead;
    public static Item BismuthBronzeProPickHead;
    public static Item BlackBronzeProPickHead;
    public static Item BlackSteelProPickHead;
    public static Item BlueSteelProPickHead;
    public static Item BronzeProPickHead;
    public static Item CopperProPickHead;
    public static Item WroughtIronProPickHead;
    public static Item RedSteelProPickHead;
    public static Item RoseGoldProPickHead;
    public static Item SteelProPickHead;
    public static Item TinProPickHead;
    public static Item ZincProPickHead;

    public static Item Coke;
    public static Item Flux;

    //Formerly TFC_Mining
    public static Block terraSluice;
    public static Item terraGoldPan;
    public static Item terraSluiceItem;

    public static Item terraProPickStone;
    public static Item terraProPickBismuth;	
    public static Item terraProPickBismuthBronze;	
    public static Item terraProPickBlackBronze;
    public static Item terraProPickBlackSteel;
    public static Item terraProPickBlueSteel;
    public static Item terraProPickBronze;
    public static Item terraProPickCopper;
    public static Item terraProPickIron;
    public static Item terraProPickRedSteel;
    public static Item terraProPickRoseGold;
    public static Item terraProPickSteel;
    public static Item terraProPickTin;
    public static Item terraProPickZinc;

    /**Armor Crafting related items*/
    public static Item BismuthSheet;
    public static Item BismuthBronzeSheet;
    public static Item BlackBronzeSheet;
    public static Item BlackSteelSheet;
    public static Item BlueSteelSheet;
    public static Item BronzeSheet;
    public static Item CopperSheet;
    public static Item WroughtIronSheet;
    public static Item RedSteelSheet;
    public static Item RoseGoldSheet;
    public static Item SteelSheet;
    public static Item TinSheet;
    public static Item ZincSheet;

    public static Item BismuthSheet2x;
    public static Item BismuthBronzeSheet2x;
    public static Item BlackBronzeSheet2x;
    public static Item BlackSteelSheet2x;
    public static Item BlueSteelSheet2x;
    public static Item BronzeSheet2x;
    public static Item CopperSheet2x;
    public static Item WroughtIronSheet2x;
    public static Item RedSteelSheet2x;
    public static Item RoseGoldSheet2x;
    public static Item SteelSheet2x;
    public static Item TinSheet2x;
    public static Item ZincSheet2x;

    public static Item BismuthUnfinishedChestplate;
    public static Item BismuthBronzeUnfinishedChestplate;
    public static Item BlackBronzeUnfinishedChestplate;
    public static Item BlackSteelUnfinishedChestplate;
    public static Item BlueSteelUnfinishedChestplate;
    public static Item BronzeUnfinishedChestplate;
    public static Item CopperUnfinishedChestplate;
    public static Item WroughtIronUnfinishedChestplate;
    public static Item RedSteelUnfinishedChestplate;
    public static Item RoseGoldUnfinishedChestplate;
    public static Item SteelUnfinishedChestplate;
    public static Item TinUnfinishedChestplate;
    public static Item ZincUnfinishedChestplate;

    public static Item BismuthUnfinishedGreaves;
    public static Item BismuthBronzeUnfinishedGreaves;
    public static Item BlackBronzeUnfinishedGreaves;
    public static Item BlackSteelUnfinishedGreaves;
    public static Item BlueSteelUnfinishedGreaves;
    public static Item BronzeUnfinishedGreaves;
    public static Item CopperUnfinishedGreaves;
    public static Item WroughtIronUnfinishedGreaves;
    public static Item RedSteelUnfinishedGreaves;
    public static Item RoseGoldUnfinishedGreaves;
    public static Item SteelUnfinishedGreaves;
    public static Item TinUnfinishedGreaves;
    public static Item ZincUnfinishedGreaves;

    public static Item BismuthUnfinishedBoots;
    public static Item BismuthBronzeUnfinishedBoots;
    public static Item BlackBronzeUnfinishedBoots;
    public static Item BlackSteelUnfinishedBoots;
    public static Item BlueSteelUnfinishedBoots;
    public static Item BronzeUnfinishedBoots;
    public static Item CopperUnfinishedBoots;
    public static Item WroughtIronUnfinishedBoots;
    public static Item RedSteelUnfinishedBoots;
    public static Item RoseGoldUnfinishedBoots;
    public static Item SteelUnfinishedBoots;
    public static Item TinUnfinishedBoots;
    public static Item ZincUnfinishedBoots;

    public static Item BismuthUnfinishedHelmet;
    public static Item BismuthBronzeUnfinishedHelmet;
    public static Item BlackBronzeUnfinishedHelmet;
    public static Item BlackSteelUnfinishedHelmet;
    public static Item BlueSteelUnfinishedHelmet;
    public static Item BronzeUnfinishedHelmet;
    public static Item CopperUnfinishedHelmet;
    public static Item WroughtIronUnfinishedHelmet;
    public static Item RedSteelUnfinishedHelmet;
    public static Item RoseGoldUnfinishedHelmet;
    public static Item SteelUnfinishedHelmet;
    public static Item TinUnfinishedHelmet;
    public static Item ZincUnfinishedHelmet;

    public static Item BismuthChestplate;
    public static Item BismuthBronzeChestplate;
    public static Item BlackBronzeChestplate;
    public static Item BlackSteelChestplate;
    public static Item BlueSteelChestplate;
    public static Item BronzeChestplate;
    public static Item CopperChestplate;
    public static Item WroughtIronChestplate;
    public static Item RedSteelChestplate;
    public static Item RoseGoldChestplate;
    public static Item SteelChestplate;
    public static Item TinChestplate;
    public static Item ZincChestplate;

    public static Item BismuthGreaves;
    public static Item BismuthBronzeGreaves;
    public static Item BlackBronzeGreaves;
    public static Item BlackSteelGreaves;
    public static Item BlueSteelGreaves;
    public static Item BronzeGreaves;
    public static Item CopperGreaves;
    public static Item WroughtIronGreaves;
    public static Item RedSteelGreaves;
    public static Item RoseGoldGreaves;
    public static Item SteelGreaves;
    public static Item TinGreaves;
    public static Item ZincGreaves;

    public static Item BismuthBoots;
    public static Item BismuthBronzeBoots;
    public static Item BlackBronzeBoots;
    public static Item BlackSteelBoots;
    public static Item BlueSteelBoots;
    public static Item BronzeBoots;
    public static Item CopperBoots;
    public static Item WroughtIronBoots;
    public static Item RedSteelBoots;
    public static Item RoseGoldBoots;
    public static Item SteelBoots;
    public static Item TinBoots;
    public static Item ZincBoots;

    public static Item BismuthHelmet;
    public static Item BismuthBronzeHelmet;
    public static Item BlackBronzeHelmet;
    public static Item BlackSteelHelmet;
    public static Item BlueSteelHelmet;
    public static Item BronzeHelmet;
    public static Item CopperHelmet;
    public static Item WroughtIronHelmet;
    public static Item RedSteelHelmet;
    public static Item RoseGoldHelmet;
    public static Item SteelHelmet;
    public static Item TinHelmet;
    public static Item ZincHelmet;




    public static void DamageItem(EntityPlayer entityplayer, IInventory iinventory, int i, int shiftedindex)
    {
        if(iinventory.getStackInSlot(i).itemID == shiftedindex) 
        {
            int index = i;
            ItemStack item = iinventory.getStackInSlot(index).copy();
            if(item != null)
            {
                item.damageItem(1 , entityplayer);
                if (item.getItemDamage() != 0)
                {
                    //ContainerTerraWorkbench cw = (ContainerTerraWorkbench)((GuiContainerTFC)guiscreen).inventorySlots;
                    iinventory.setInventorySlotContents(index, item);
                    iinventory.getStackInSlot(index).stackSize = iinventory.getStackInSlot(index).stackSize + 1;
                    if(iinventory.getStackInSlot(index).stackSize > 2) {
                        iinventory.getStackInSlot(index).stackSize = 2;
                    }
                }
            }
        }
    }

    public static int getBlockIdFor(String item, int standardID)
    {
        if (config == null)
        {
            return standardID;
        }
        try
        {
            return new Integer(config.getOrCreateIntProperty(item, "Block", standardID).value).intValue();
        }
        catch (Exception e)
        {
            System.out.println(new StringBuilder().append("[TFC] Error while trying to access ID-List, config wasn't loaded properly!").toString());
        }return standardID;
    }

    public static int getGeneralIdFor(String item, int standardID)
    {
        if (config == null)
        {
            return standardID;
        }
        try
        {
            return new Integer(config.getOrCreateIntProperty(item, "General", standardID).value).intValue();
        }
        catch (Exception e)
        {
            System.out.println(new StringBuilder().append("[TFC] Error while trying to access ID-List, config wasn't loaded properly!").toString());
        }return standardID;
    }
    public static int getItemIdFor(String item, int standardID)
    {
        if (config == null)
        {
            return standardID;
        }
        try
        {
            return new Integer(config.getOrCreateIntProperty(item, "Item", standardID).value).intValue();
        }
        catch (Exception e)
        {
            System.out.println(new StringBuilder().append("[TFC] Error while trying to access ID-List, config wasn't loaded properly!").toString());
        }return standardID;
    }
    //this will loop through a list and handle damaging the item in the crafting area if it matches
    public static void HandleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] Items)
    {
        ItemStack item = null;
        for(int i = 0; i < iinventory.getSizeInventory(); i++) 
        {             
            if(iinventory.getStackInSlot(i) == null) 
            {
                continue;
            }
            for(int j = 0; j < Items.length; j++) 
            {  
                DamageItem(entityplayer,iinventory,i,Items[j].shiftedIndex);
            }
        }
    }

    private static void setupCraftHook() 
    {
        ICraftingHandler handler = new ICraftingHandler()
        {
            public void onTakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory) 
            {
                int index = 0;

                if(itemstack.itemID == mod_TFC_Game.Flux.shiftedIndex)
                {
                    HandleItem(entityplayer, iinventory, TFC_Game.Hammers);
                }
            }
        };

        MinecraftForge.registerCraftingHandler(handler);
    }
    public mod_TFC_Game()
    {
        ModLoader.setInGameHook(this, true, true);
    }
    @Override
    public boolean clientSideRequired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getVersion()
    {
        return "0.2a";
    }

    public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {

        return false;
    }

    @Override
    public void load()
    {
        proxy = ServerClientProxy.getProxy();
        instance = this;


        //Register Blocks	
        ModLoader.registerBlock(terraFirepit);
        ModLoader.registerBlock(terraBellows);
        ModLoader.registerBlock(terraAnvil);
        ModLoader.registerBlock(terraScribe);
        ModLoader.registerBlock(terraForge);
        ModLoader.registerBlock(terraMetalTable);
        ModLoader.registerBlock(terraMolten);
        ModLoader.registerBlock(terraBloomery);
        ModLoader.registerBlock(terraSluice);


        MinecraftForge.setGuiHandler(this, proxy);
        MinecraftForge.registerConnectionHandler(new PacketHandler());
        proxy.registerTileEntities();
        proxy.registerRenderInformation();

        setupCraftHook();
        
        String[] Names = {"Bismuth", "Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Bronze", "Copper", "Wrought Iron", "Red Steel", "Rose Gold", "Steel", "Tin", "Zinc"};
        
        Item[] unfinishedChest = new Item[] {BismuthUnfinishedChestplate,BismuthBronzeUnfinishedChestplate,BlackBronzeUnfinishedChestplate,BlackSteelUnfinishedChestplate,BlueSteelUnfinishedChestplate,BronzeUnfinishedChestplate,CopperUnfinishedChestplate,
                WroughtIronUnfinishedChestplate,RedSteelUnfinishedChestplate, RoseGoldUnfinishedChestplate, SteelUnfinishedChestplate, TinUnfinishedChestplate, ZincUnfinishedChestplate};
        
        Item[] finishedChest = new Item[] {BismuthChestplate,BismuthBronzeChestplate,BlackBronzeChestplate,BlackSteelChestplate,BlueSteelChestplate,BronzeChestplate,CopperChestplate,
                WroughtIronChestplate,RedSteelChestplate, RoseGoldChestplate, SteelChestplate, TinChestplate, ZincChestplate};
        
        Item[] unfinishedGreaves = new Item[] {BismuthUnfinishedGreaves,BismuthBronzeUnfinishedGreaves,BlackBronzeUnfinishedGreaves,BlackSteelUnfinishedGreaves,BlueSteelUnfinishedGreaves,BronzeUnfinishedGreaves,CopperUnfinishedGreaves,
                WroughtIronUnfinishedGreaves,RedSteelUnfinishedGreaves, RoseGoldUnfinishedGreaves, SteelUnfinishedGreaves, TinUnfinishedGreaves, ZincUnfinishedGreaves};
        
        Item[] finishedGreaves = new Item[] {BismuthGreaves,BismuthBronzeGreaves,BlackBronzeGreaves,BlackSteelGreaves,BlueSteelGreaves,BronzeGreaves,CopperGreaves,
                WroughtIronGreaves,RedSteelGreaves, RoseGoldGreaves, SteelGreaves, TinGreaves, ZincGreaves};
        
        Item[] unfinishedBoots = new Item[] {BismuthUnfinishedBoots,BismuthBronzeUnfinishedBoots,BlackBronzeUnfinishedBoots,BlackSteelUnfinishedBoots,BlueSteelUnfinishedBoots,BronzeUnfinishedBoots,CopperUnfinishedBoots,
                WroughtIronUnfinishedBoots,RedSteelUnfinishedBoots, RoseGoldUnfinishedBoots, SteelUnfinishedBoots, TinUnfinishedBoots, ZincUnfinishedBoots};
        
        List finishedBoots = new ArrayList();
        finishedBoots.add(BismuthBoots);finishedBoots.add(BismuthBronzeBoots);finishedBoots.add(BlackBronzeBoots);finishedBoots.add(BlackSteelBoots);finishedBoots.add(BlueSteelBoots);finishedBoots.add(BronzeBoots);finishedBoots.add(CopperBoots);
        finishedBoots.add(WroughtIronBoots);finishedBoots.add(RedSteelBoots);finishedBoots.add(RoseGoldBoots);finishedBoots.add(SteelBoots);finishedBoots.add(TinBoots);finishedBoots.add(ZincBoots);
                
                //BismuthBoots,BismuthBronzeBoots,BlackBronzeBoots,BlackSteelBoots,BlueSteelBoots,BronzeBoots,CopperBoots,
                //WroughtIronBoots,RedSteelBoots, RoseGoldBoots, SteelBoots, TinBoots, ZincBoots});
        
        Item[] unfinishedHelmet = new Item[] {BismuthUnfinishedHelmet,BismuthBronzeUnfinishedHelmet,BlackBronzeUnfinishedHelmet,BlackSteelUnfinishedHelmet,BlueSteelUnfinishedHelmet,BronzeUnfinishedHelmet,CopperUnfinishedHelmet,
                WroughtIronUnfinishedHelmet,RedSteelUnfinishedHelmet, RoseGoldUnfinishedHelmet, SteelUnfinishedHelmet, TinUnfinishedHelmet, ZincUnfinishedHelmet};
        
        Item[] finishedHelmet = new Item[] {BismuthHelmet,BismuthBronzeHelmet,BlackBronzeHelmet,BlackSteelHelmet,BlueSteelHelmet,BronzeHelmet,CopperHelmet,
                WroughtIronHelmet,RedSteelHelmet, RoseGoldHelmet, SteelHelmet, TinHelmet, ZincHelmet};
        
        EnumArmorMaterial[] mats = new EnumArmorMaterial[]{mod_TFC_Core.BismuthArmorMaterial,mod_TFC_Core.BismuthBronzeArmorMaterial,mod_TFC_Core.BlackBronzeArmorMaterial,mod_TFC_Core.BlackSteelArmorMaterial,mod_TFC_Core.BlueSteelArmorMaterial,
                mod_TFC_Core.BronzeArmorMaterial,mod_TFC_Core.CopperArmorMaterial,mod_TFC_Core.IronArmorMaterial,mod_TFC_Core.RedSteelArmorMaterial,mod_TFC_Core.RoseGoldArmorMaterial,
                mod_TFC_Core.SteelArmorMaterial,mod_TFC_Core.TinArmorMaterial,mod_TFC_Core.ZincArmorMaterial};
        
        int num = 19026;
        int i = 0;
        BismuthUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BismuthBronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackBronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlueSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        CopperUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        WroughtIronUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RedSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RoseGoldUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        SteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TinUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        ZincUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        BismuthBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        BismuthBronzeBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        BlackBronzeBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        BlackSteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        BlueSteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        BronzeBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        CopperBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        WroughtIronBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        RedSteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        RoseGoldBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        SteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TinBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        ZincBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        i = 0;
        BismuthUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BismuthBronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackBronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlueSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        CopperUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        WroughtIronUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RedSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RoseGoldUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        SteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TinUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        ZincUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        BismuthGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        BismuthBronzeGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        BlackBronzeGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        BlackSteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        BlueSteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        BronzeGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        CopperGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        WroughtIronGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        RedSteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        RoseGoldGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        SteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TinGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        ZincGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        i = 0;
        BismuthUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BismuthBronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackBronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlueSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        CopperUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        WroughtIronUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RedSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RoseGoldUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        SteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TinUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        ZincUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        BismuthChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        BismuthBronzeChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        BlackBronzeChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        BlackSteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        BlueSteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        BronzeChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        CopperChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        WroughtIronChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        RedSteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        RoseGoldChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        SteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TinChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        ZincChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        i = 0;
        BismuthUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BismuthBronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackBronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlackSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BlueSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        BronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        CopperUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        WroughtIronUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RedSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        RoseGoldUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        SteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TinUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        ZincUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        BismuthHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        BismuthBronzeHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        BlackBronzeHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        BlackSteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        BlueSteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        BronzeHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        CopperHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        WroughtIronHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        RedSteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        RoseGoldHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        SteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TinHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        ZincHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        
        
        ModLoader.addRecipe(new ItemStack(mod_TFC_Game.RedSteelBoots), new Object[] { "#", Character.valueOf('#'), 
            new ItemStack(mod_TFC_Core.RedSteelIngot)});
    }

    @Override
    public boolean serverSideRequired() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean onTickInGame(MinecraftServer minecraftServer)
    {
        for (Iterator iterator = minecraftServer.getWorldManager(0).playerManager.players.iterator(); iterator.hasNext();)
        {
            EntityPlayer thePlayer = (EntityPlayer)iterator.next();
            ItemStack[] inv = thePlayer.inventory.mainInventory;
            double xCoord = thePlayer.posX;
            double yCoord = thePlayer.posY;
            double zCoord = thePlayer.posZ;

            TFCHeat.HandleContainerHeat((World)minecraftServer.getWorldManager(0), inv, (int)xCoord,(int)yCoord,(int)zCoord);
        }
        return true;
    }

    public boolean onTickInGame(float var1, Minecraft mc)
    {
        ItemStack[] inv = ((Minecraft)mc).thePlayer.inventory.mainInventory;
        double xCoord = ((Minecraft)mc).thePlayer.posX;
        double yCoord = ((Minecraft)mc).thePlayer.posY;
        double zCoord = ((Minecraft)mc).thePlayer.posZ;

        TFCHeat.HandleContainerHeat(((Minecraft)mc).theWorld, inv, (int)xCoord,(int)yCoord,(int)zCoord);
        return true;
    }



    static
    {

        try
        {
            config = new Configuration(new File(ServerClientProxy.getProxy().getMinecraftDir(), "/config/TFC.cfg"));
            config.load();
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("[TFC] Error while trying to access configuration!").toString());
            config = null;
        }

        /////////////////////////
        //Blocks
        /////////////////////////
        terraFirepit = new BlockTerraFirepit(TFCSettings.getIntFor(config,"block","terraFirepit", 207), TileEntityTerraFirepit.class, 80).setBlockName("terraFirepit").setHardness(1).setLightValue(0F);
        terraBellows = new BlockTerraBellows(TFCSettings.getIntFor(config,"block","terraBellows", 206),Material.wood).setBlockName("terraBellows").setHardness(2);
        terraForge= new BlockTerraForge(TFCSettings.getIntFor(config,"block","terraForge", 216), TileEntityTerraForge.class, 90).setBlockName("terraForge").setHardness(20).setLightValue(0F);
        terraScribe = new BlockTerraScribe(TFCSettings.getIntFor(config,"block","terraScribe", 204), TileEntityTerraScribe.class).setBlockName("terraScribe").setHardness(2);
        terraAnvil = new BlockTerraAnvil(TFCSettings.getIntFor(config,"block","terraAnvil", 205),192, TileEntityTerraAnvil.class).setBlockName("terraAnvil").setHardness(3);
        terraAnvil2 = new BlockTerraAnvil(TFCSettings.getIntFor(config,"block","terraAnvil2", 225),208, TileEntityTerraAnvil.class).setBlockName("terraAnvil2").setHardness(3);
        terraMetalTable = new BlockTerraMetallurgy(TFCSettings.getIntFor(config,"block","terraMetallurgy", 218), TileEntityTerraMetallurgy.class).setBlockName("terraMetallurgy").setHardness(3);
        terraMolten = new BlockTerraMolten(TFCSettings.getIntFor(config,"block","terraMolten", 219)).setBlockName("terraMolten").setHardness(20);
        terraBloomery = new BlockTerraBloomery(TFCSettings.getIntFor(config,"block","terraBloomery", 220), TileEntityTerraBloomery.class, 65).setBlockName("terraBloomery").setHardness(20).setLightValue(0F);
        terraBloomeryOn = new BlockTerraBloomery(TFCSettings.getIntFor(config,"block","terraBloomeryOn", 221), TileEntityTerraBloomery.class, 66).setBlockName("terraBloomeryOn").setHardness(20).setLightValue(1.0F);
        terraFirepitOn = new BlockTerraFirepit(TFCSettings.getIntFor(config,"block","terraFirepitOn", 222), TileEntityTerraFirepit.class, 81).setBlockName("terraFirepitOn").setHardness(1).setLightValue(1.0F);
        terraForgeOn = new BlockTerraForge(TFCSettings.getIntFor(config,"block","terraForgeOn", 223), TileEntityTerraForge.class, 91).setBlockName("terraForgeOn").setHardness(20).setLightValue(1.0F);
        /////////////////////////
        //Items
        /////////////////////////


        terraSlag = new ItemTerra(TFCSettings.getIntFor(config,"item","terraSlag",16349),"/bioxx/terrasprites.png").setItemName("terraSlag").setIconCoord(2, 0);

        BismuthUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedBismuth",16350)).setItemName("UnshapedBismuth").setIconCoord(0, 9);
        BismuthBronzeUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedBismuthBronze",16351)).setItemName("UnshapedBismuthBronze").setIconCoord(1, 9);
        BlackBronzeUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedBlackBronze",16352)).setItemName("UnshapedBlackBronze").setIconCoord(2, 9);
        BlackSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedBlackSteel",16353)).setItemName("UnshapedBlackSteel").setIconCoord(3, 9);
        BlueSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedBlueSteel",16354)).setItemName("UnshapedBlueSteel").setIconCoord(4, 9);
        BrassUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedBrass",16355)).setItemName("UnshapedBrass").setIconCoord(5, 9);
        BronzeUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedBronze",16356)).setItemName("UnshapedBronze").setIconCoord(6, 9);
        CopperUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedCopper",16357)).setItemName("UnshapedCopper").setIconCoord(7, 9);
        GoldUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedGold",16358)).setItemName("UnshapedGold").setIconCoord(8, 9);
        WroughtIronUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedIron",16359)).setItemName("UnshapedWroughtIron").setIconCoord(9, 9);
        LeadUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedLead",16360)).setItemName("UnshapedLead").setIconCoord(10, 9);
        NickelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedNickel",16361)).setItemName("UnshapedNickel").setIconCoord(0, 10);
        PigIronUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedPigIron",16362)).setItemName("UnshapedPigIron").setIconCoord(1, 10);
        PlatinumUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedPlatinum",16363)).setItemName("UnshapedPlatinum").setIconCoord(2, 10);
        RedSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedRedSteel",16364)).setItemName("UnshapedRedSteel").setIconCoord(3, 10);
        RoseGoldUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedRoseGold",16365)).setItemName("UnshapedRoseGold").setIconCoord(4, 10);
        SilverUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedSilver",16366)).setItemName("UnshapedSilver").setIconCoord(5, 10);
        SteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedSteel",16367)).setItemName("UnshapedSteel").setIconCoord(6, 10);
        SterlingSilverUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedSterlingSilver",16368)).setItemName("UnshapedSterlingSilver").setIconCoord(7, 10);
        TinUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedTin",16369)).setItemName("UnshapedTin").setIconCoord(8, 10);
        ZincUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedZinc",16370)).setItemName("UnshapedZinc").setIconCoord(9, 10);

        //Hammers
        StoneHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraStoneHammer",16371),mod_TFC_Core.IgInToolMaterial).setItemName("Stone Hammer").setIconCoord(0, 11).setMaxDamage(mod_TFC_Core.IgInStoneUses);
        BismuthHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraBismuthHammer",16372),mod_TFC_Core.BismuthToolMaterial).setItemName("Bismuth Hammer").setIconCoord(1, 11).setMaxDamage(mod_TFC_Core.BismuthUses);
        BismuthBronzeHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraBismuthBronzeHammer",16373),mod_TFC_Core.BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Hammer").setIconCoord(2, 11).setMaxDamage(mod_TFC_Core.BismuthBronzeUses);
        BlackBronzeHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraBlackBronzeHammer",16374),mod_TFC_Core.BlackBronzeToolMaterial).setItemName("Black Bronze Hammer").setIconCoord(3, 11).setMaxDamage(mod_TFC_Core.BlackBronzeUses);
        BlackSteelHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraBlackSteelHammer",16375),mod_TFC_Core.BlackSteelToolMaterial).setItemName("Black Steel Hammer").setIconCoord(4, 11).setMaxDamage(mod_TFC_Core.BlackSteelUses);
        BlueSteelHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraBlueSteelHammer",16376),mod_TFC_Core.BlueSteelToolMaterial).setItemName("Blue Steel Hammer").setIconCoord(5, 11).setMaxDamage(mod_TFC_Core.BlueSteelUses);
        BronzeHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraBronzeHammer",16377),mod_TFC_Core.BronzeToolMaterial).setItemName("Bronze Hammer").setIconCoord(6, 11).setMaxDamage(mod_TFC_Core.BronzeUses);
        CopperHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraCopperHammer",16378),mod_TFC_Core.CopperToolMaterial).setItemName("Copper Hammer").setIconCoord(7, 11).setMaxDamage(mod_TFC_Core.CopperUses);
        WroughtIronHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraWroughtIronHammer",16379),mod_TFC_Core.IronToolMaterial).setItemName("Wrought Iron Hammer").setIconCoord(8, 11).setMaxDamage(mod_TFC_Core.WroughtIronUses);
        RedSteelHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraRedSteelHammer",16380),mod_TFC_Core.RedSteelToolMaterial).setItemName("Red Steel Hammer").setIconCoord(9, 11).setMaxDamage(mod_TFC_Core.RedSteelUses);
        RoseGoldHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraRoseGoldHammer",16381),mod_TFC_Core.RoseGoldToolMaterial).setItemName("Rose Gold Hammer").setIconCoord(10, 11).setMaxDamage(mod_TFC_Core.RoseGoldUses);
        SteelHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraSteelHammer",16382),mod_TFC_Core.SteelToolMaterial).setItemName("Steel Hammer").setIconCoord(11, 11).setMaxDamage(mod_TFC_Core.SteelUses);
        TinHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraTinHammer",16383),mod_TFC_Core.TinToolMaterial).setItemName("Tin Hammer").setIconCoord(12, 11).setMaxDamage(mod_TFC_Core.TinUses);
        ZincHammer = new ItemHammer(TFCSettings.getIntFor(config,"item","terraZincHammer",16384),mod_TFC_Core.ZincToolMaterial).setItemName("Zinc Hammer").setIconCoord(13, 11).setMaxDamage(mod_TFC_Core.ZincUses);

        terraInk = new ItemTerra(TFCSettings.getIntFor(config,"item","terraInk",16391),"/bioxx/terrasprites.png").setItemName("terraInk").setIconCoord(3, 0);

        //Plans
        PickaxeHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","PickaxeHeadPlan",17000)).setItemName("PickaxeHeadPlan").setIconCoord(0, 0);
        ShovelHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","ShovelHeadPlan",17001)).setItemName("ShovelHeadPlan").setIconCoord(0, 0);
        HoeHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","HoeHeadPlan",17002)).setItemName("HoeHeadPlan").setIconCoord(0, 0);
        AxeHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","AxeHeadPlan",17003)).setItemName("AxeHeadPlan").setIconCoord(0, 0);
        HammerHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","HammerHeadPlan",17004)).setItemName("HammerHeadPlan").setIconCoord(0, 0);
        ChiselHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","ChiselHeadPlan",17005)).setItemName("ChiselHeadPlan").setIconCoord(0, 0);
        SwordBladePlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","SwordBladePlan",17006)).setItemName("SwordBladePlan").setIconCoord(0, 0);
        MaceHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","MaceHeadPlan",17007)).setItemName("MaceHeadPlan").setIconCoord(0, 0);
        SawBladePlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","SawBladePlan",17008)).setItemName("SawBladePlan").setIconCoord(0, 0);
        ProPickHeadPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","ProPickHeadPlan",17009)).setItemName("ProPickHeadPlan").setIconCoord(0, 0);
        
        HelmetPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","HelmetPlan",17010)).setItemName("HelmetPlan").setIconCoord(0, 0);
        ChestplatePlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","ChestplatePlan",17011)).setItemName("ChestplatePlan").setIconCoord(0, 0);
        GreavesPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","GreavesPlan",17012)).setItemName("GreavesPlan").setIconCoord(0, 0);
        BootsPlan = new ItemTerraMiscTool(TFCSettings.getIntFor(config,"item","BootsPlan",17013)).setItemName("BootsPlan").setIconCoord(0, 0);

        terraStoneAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraStoneAnvilItem",16398), 0, AnvilReq.STONE).setItemName("terraStoneAnvilItem").setIconCoord(0, 2);
        terraBlackSteelAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraBlackSteelAnvilItem",16399), 5, AnvilReq.BLACKSTEEL).setItemName("terraBlackSteelAnvilItem").setIconCoord(4, 2);
        terraBlueSteelAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraBlueSteelAnvilItem",16400), 7, AnvilReq.BLUESTEEL).setItemName("terraBlueSteelAnvilItem").setIconCoord(5, 2);
        terraBronzeAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraBronzeAnvilItem",16401), 2, AnvilReq.BRONZE).setItemName("terraBronzeAnvilItem").setIconCoord(6, 2);
        terraCopperAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraCopperAnvilItem",16402), 1, AnvilReq.COPPER).setItemName("terraCopperAnvilItem").setIconCoord(7, 2);
        terraWroughtIronAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraWroughtIronAnvilItem",16403), 3, AnvilReq.WROUGHTIRON).setItemName("terraWroughtIronAnvilItem").setIconCoord(8, 2);
        terraRedSteelAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraRedSteelAnvilItem",16404), 6, AnvilReq.REDSTEEL).setItemName("terraRedSteelAnvilItem").setIconCoord(9, 2);
        terraSteelAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraSteelAnvilItem",16405), 4, AnvilReq.STEEL).setItemName("terraSteelAnvilItem").setIconCoord(11, 2);

        terraBellowsItem = new ItemTerraBellows(TFCSettings.getIntFor(config,"item","terraBellowsItem",16406)).setItemName("terraBellowsItem").setIconCoord(8, 0);

        terraFireStarter = new ItemTerraFirestarter(TFCSettings.getIntFor(config,"item","terraFireStarter",16407)).setItemName("terraFireStarter").setIconCoord(7, 0);
        terraClayMold = new ItemTerra(TFCSettings.getIntFor(config,"item","terraClayMold",16408),"/bioxx/terrasprites.png").setItemName("terraClayMold").setIconCoord(10, 10);
        terraCeramicMold = new ItemTerra(TFCSettings.getIntFor(config,"item","terraFiredClayMold",16409),"/bioxx/terrasprites.png").setItemName("terraFiredClayMold").setIconCoord(6, 10);
        //Tool heads
        BismuthPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthPickaxeHead",16500)).setItemName("Bismuth Pickaxe Head").setIconCoord(1, 3);
        BismuthBronzePickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzePickaxeHead",16501)).setItemName("Bismuth Bronze Pickaxe Head").setIconCoord(2, 3);
        BlackBronzePickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzePickaxeHead",16502)).setItemName("Black Bronze Pickaxe Head").setIconCoord(3, 3);
        BlackSteelPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelPickaxeHead",16503)).setItemName("Black Steel Pickaxe Head").setIconCoord(4, 3);
        BlueSteelPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelPickaxeHead",16504)).setItemName("Blue Steel Pickaxe Head").setIconCoord(5, 3);
        BronzePickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzePickaxeHead",16505)).setItemName("Bronze Pickaxe Head").setIconCoord(6, 3);
        CopperPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperPickaxeHead",16506)).setItemName("Copper Pickaxe Head").setIconCoord(7, 3);
        WroughtIronPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronPickaxeHead",16507)).setItemName("Wrought Iron Pickaxe Head").setIconCoord(8, 3);
        RedSteelPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelPickaxeHead",16508)).setItemName("Red Steel Pickaxe Head").setIconCoord(9, 3);
        RoseGoldPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldPickaxeHead",16509)).setItemName("Rose Gold Pickaxe Head").setIconCoord(10, 3);
        SteelPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelPickaxeHead",16510)).setItemName("Steel Pickaxe Head").setIconCoord(11, 3);
        TinPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinPickaxeHead",16511)).setItemName("Tin Pickaxe Head").setIconCoord(12, 3);
        ZincPickaxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincPickaxeHead",16512)).setItemName("Zinc Pickaxe Head").setIconCoord(13, 3);

        BismuthShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthShovelHead",16513)).setItemName("Bismuth Shovel Head").setIconCoord(1, 4);
        BismuthBronzeShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeShovelHead",16514)).setItemName("Bismuth Bronze Shovel Head").setIconCoord(2, 4);
        BlackBronzeShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeShovelHead",16515)).setItemName("Black Bronze Shovel Head").setIconCoord(3, 4);
        BlackSteelShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelShovelHead",16516)).setItemName("Black Steel Shovel Head").setIconCoord(4, 4);
        BlueSteelShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelShovelHead",16517)).setItemName("Blue Steel Shovel Head").setIconCoord(5, 4);
        BronzeShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeShovelHead",16518)).setItemName("Bronze Shovel Head").setIconCoord(6, 4);
        CopperShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperShovelHead",16519)).setItemName("Copper Shovel Head").setIconCoord(7, 4);
        WroughtIronShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronShovelHead",16520)).setItemName("Wrought Iron Shovel Head").setIconCoord(8, 4);
        RedSteelShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelShovelHead",16521)).setItemName("Red Steel Shovel Head").setIconCoord(9, 4);
        RoseGoldShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldShovelHead",16522)).setItemName("Rose Gold Shovel Head").setIconCoord(10, 4);
        SteelShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelShovelHead",16523)).setItemName("Steel Shovel Head").setIconCoord(11, 4);
        TinShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinShovelHead",16524)).setItemName("Tin Shovel Head").setIconCoord(12, 4);
        ZincShovelHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincShovelHead",16525)).setItemName("Zinc Shovel Head").setIconCoord(13, 4);

        BismuthHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthHoeHead",16526)).setItemName("Bismuth Hoe Head").setIconCoord(1, 6);
        BismuthBronzeHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeHoeHead",16527)).setItemName("Bismuth Bronze Hoe Head").setIconCoord(2, 6);
        BlackBronzeHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeHoeHead",16528)).setItemName("Black Bronze Hoe Head").setIconCoord(3, 6);
        BlackSteelHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelHoeHead",16529)).setItemName("Black Steel Hoe Head").setIconCoord(4, 6);
        BlueSteelHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelHoeHead",16530)).setItemName("Blue Steel Hoe Head").setIconCoord(5, 6);
        BronzeHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeHoeHead",16531)).setItemName("Bronze Hoe Head").setIconCoord(6, 6);
        CopperHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperHoeHead",16532)).setItemName("Copper Hoe Head").setIconCoord(7, 6);
        WroughtIronHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronHoeHead",16533)).setItemName("Wrought Iron Hoe Head").setIconCoord(8, 6);
        RedSteelHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelHoeHead",16534)).setItemName("Red Steel Hoe Head").setIconCoord(9, 6);
        RoseGoldHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldHoeHead",16535)).setItemName("Rose Gold Hoe Head").setIconCoord(10, 6);
        SteelHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelHoeHead",16536)).setItemName("Steel Hoe Head").setIconCoord(11, 6);
        TinHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinHoeHead",16537)).setItemName("Tin Hoe Head").setIconCoord(12, 6);
        ZincHoeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincHoeHead",16538)).setItemName("Zinc Hoe Head").setIconCoord(13, 6);

        BismuthAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthAxeHead",16539)).setItemName("Bismuth Axe Head").setIconCoord(1, 5);
        BismuthBronzeAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeAxeHead",16540)).setItemName("Bismuth Bronze Axe Head").setIconCoord(2, 5);
        BlackBronzeAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeAxeHead",16541)).setItemName("Black Bronze Axe Head").setIconCoord(3, 5);
        BlackSteelAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelAxeHead",16542)).setItemName("Black Steel Axe Head").setIconCoord(4, 5);
        BlueSteelAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelAxeHead",16543)).setItemName("Blue Steel Axe Head").setIconCoord(5, 5);
        BronzeAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeAxeHead",16544)).setItemName("Bronze Axe Head").setIconCoord(6, 5);
        CopperAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperAxeHead",16545)).setItemName("Copper Axe Head").setIconCoord(7, 5);
        WroughtIronAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronAxeHead",16546)).setItemName("Wrought Iron Axe Head").setIconCoord(8, 5);
        RedSteelAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelAxeHead",16547)).setItemName("Red Steel Axe Head").setIconCoord(9, 5);
        RoseGoldAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldAxeHead",16548)).setItemName("Rose Gold Axe Head").setIconCoord(10, 5);
        SteelAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelAxeHead",16549)).setItemName("Steel Axe Head").setIconCoord(11, 5);
        TinAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinAxeHead",16550)).setItemName("Tin Axe Head").setIconCoord(12, 5);
        ZincAxeHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincAxeHead",16551)).setItemName("Zinc Axe Head").setIconCoord(13, 5);

        BismuthHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthHammerHead",16552)).setItemName("Bismuth Hammer Head").setIconCoord(1, 11);
        BismuthBronzeHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeHammerHead",16553)).setItemName("Bismuth Bronze Hammer Head").setIconCoord(2, 11);
        BlackBronzeHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeHammerHead",16554)).setItemName("Black Bronze Hammer Head").setIconCoord(3, 11);
        BlackSteelHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelHammerHead",16555)).setItemName("Black Steel Hammer Head").setIconCoord(4, 11);
        BlueSteelHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelHammerHead",16556)).setItemName("Blue Steel Hammer Head").setIconCoord(5, 11);
        BronzeHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeHammerHead",16557)).setItemName("Bronze Hammer Head").setIconCoord(6, 11);
        CopperHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperHammerHead",16558)).setItemName("Copper Hammer Head").setIconCoord(7, 11);
        WroughtIronHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronHammerHead",16559)).setItemName("Wrought Iron Hammer Head").setIconCoord(8, 11);
        RedSteelHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelHammerHead",16560)).setItemName("Red Steel Hammer Head").setIconCoord(9, 11);
        RoseGoldHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldHammerHead",16561)).setItemName("Rose Gold Hammer Head").setIconCoord(10, 11);
        SteelHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelHammerHead",16562)).setItemName("Steel Hammer Head").setIconCoord(11, 11);
        TinHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinHammerHead",16563)).setItemName("Tin Hammer Head").setIconCoord(12, 11);
        ZincHammerHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincHammerHead",16564)).setItemName("Zinc Hammer Head").setIconCoord(13, 11);

        //chisel heads
        BismuthChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthChiselHead",16565)).setItemName("Bismuth Chisel Head").setIconCoord(1, 7);
        BismuthBronzeChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeChiselHead",16566)).setItemName("Bismuth Bronze Chisel Head").setIconCoord(2, 7);
        BlackBronzeChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeChiselHead",16567)).setItemName("Black Bronze Chisel Head").setIconCoord(3, 7);
        BlackSteelChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelChiselHead",16568)).setItemName("Black Steel Chisel Head").setIconCoord(4, 7);
        BlueSteelChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelChiselHead",16569)).setItemName("Blue Steel Chisel Head").setIconCoord(5, 7);
        BronzeChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeChiselHead",16570)).setItemName("Bronze Chisel Head").setIconCoord(6, 7);
        CopperChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperChiselHead",16571)).setItemName("Copper Chisel Head").setIconCoord(7, 7);
        WroughtIronChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronChiselHead",16572)).setItemName("Wrought Iron Chisel Head").setIconCoord(8, 7);
        RedSteelChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelChiselHead",16573)).setItemName("Red Steel Chisel Head").setIconCoord(9, 7);
        RoseGoldChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldChiselHead",16574)).setItemName("Rose Gold Chisel Head").setIconCoord(10, 7);
        SteelChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelChiselHead",16575)).setItemName("Steel Chisel Head").setIconCoord(11, 7);
        TinChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinChiselHead",16576)).setItemName("Tin Chisel Head").setIconCoord(12, 7);
        ZincChiselHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincChiselHead",16577)).setItemName("Zinc Chisel Head").setIconCoord(13, 7);

        BismuthSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthSwordHead",16578)).setItemName("Bismuth Sword Blade").setIconCoord(1, 13);
        BismuthBronzeSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeSwordHead",16579)).setItemName("Bismuth Bronze Sword Blade").setIconCoord(2, 13);
        BlackBronzeSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeSwordHead",16580)).setItemName("Black Bronze Sword Blade").setIconCoord(3, 13);
        BlackSteelSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelSwordHead",16581)).setItemName("Black Steel Sword Blade").setIconCoord(4, 13);
        BlueSteelSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelSwordHead",16582)).setItemName("Blue Steel Sword Blade").setIconCoord(5, 13);
        BronzeSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeSwordHead",16583)).setItemName("Bronze Sword Blade").setIconCoord(6, 13);
        CopperSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperSwordHead",16584)).setItemName("Copper Sword Blade").setIconCoord(7, 13);
        WroughtIronSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronSwordHead",16585)).setItemName("Wrought Iron Sword Blade").setIconCoord(8, 13);
        RedSteelSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelSwordHead",16586)).setItemName("Red Steel Sword Blade").setIconCoord(9, 13);
        RoseGoldSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldSwordHead",16587)).setItemName("Rose Gold Sword Blade").setIconCoord(10, 13);
        SteelSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelSwordHead",16588)).setItemName("Steel Sword Blade").setIconCoord(11, 13);
        TinSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinSwordHead",16589)).setItemName("Tin Sword Blade").setIconCoord(12, 13);
        ZincSwordHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincSwordHead",16590)).setItemName("Zinc Sword Blade").setIconCoord(13, 13);

        BismuthMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthMaceHead",16591)).setItemName("Bismuth Mace Head").setIconCoord(1, 12);
        BismuthBronzeMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeMaceHead",16592)).setItemName("Bismuth Bronze Mace Head").setIconCoord(2, 12);
        BlackBronzeMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeMaceHead",16593)).setItemName("Black Bronze Mace Head").setIconCoord(3, 12);
        BlackSteelMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelMaceHead",16594)).setItemName("Black Steel Mace Head").setIconCoord(4, 12);
        BlueSteelMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelMaceHead",16595)).setItemName("Blue Steel Mace Head").setIconCoord(5, 12);
        BronzeMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeMaceHead",16596)).setItemName("Bronze Mace Head").setIconCoord(6, 12);
        CopperMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperMaceHead",16597)).setItemName("Copper Mace Head").setIconCoord(7, 12);
        WroughtIronMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronMaceHead",16598)).setItemName("Wrought Iron Mace Head").setIconCoord(8, 12);
        RedSteelMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelMaceHead",16599)).setItemName("Red Steel Mace Head").setIconCoord(9, 12);
        RoseGoldMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldMaceHead",16600)).setItemName("Rose Gold Mace Head").setIconCoord(10, 12);
        SteelMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelMaceHead",16601)).setItemName("Steel Mace Head").setIconCoord(11, 12);
        TinMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinMaceHead",16602)).setItemName("Tin Mace Head").setIconCoord(12, 12);
        ZincMaceHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincMaceHead",16603)).setItemName("Zinc Mace Head").setIconCoord(13, 12);

        BismuthSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthSawHead",16604)).setItemName("Bismuth Saw Blade").setIconCoord(1, 8);
        BismuthBronzeSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeSawHead",16605)).setItemName("Bismuth Bronze Saw Blade").setIconCoord(2, 8);
        BlackBronzeSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeSawHead",16606)).setItemName("Black Bronze Saw Blade").setIconCoord(3, 8);
        BlackSteelSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelSawHead",16607)).setItemName("Black Steel Saw Blade").setIconCoord(4, 8);
        BlueSteelSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelSawHead",16608)).setItemName("Blue Steel Saw Blade").setIconCoord(5, 8);
        BronzeSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeSawHead",16609)).setItemName("Bronze Saw Blade").setIconCoord(6, 8);
        CopperSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperSawHead",16610)).setItemName("Copper Saw Blade").setIconCoord(7, 8);
        WroughtIronSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronSawHead",16611)).setItemName("Wrought Iron Saw Blade").setIconCoord(8, 8);
        RedSteelSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelSawHead",16612)).setItemName("Red Steel Saw Blade").setIconCoord(9, 8);
        RoseGoldSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldSawHead",16613)).setItemName("Rose Gold Saw Blade").setIconCoord(10, 8);
        SteelSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelSawHead",16614)).setItemName("Steel Saw Blade").setIconCoord(11, 8);
        TinSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinSawHead",16615)).setItemName("Tin Saw Blade").setIconCoord(12, 8);
        ZincSawHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincSawHead",16616)).setItemName("Zinc Saw Blade").setIconCoord(13, 8);

        HCBlackSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedHCBlackSteel",16617)).setItemName("UnshapedHCBlackSteel").setIconCoord(3, 9);
        WeakBlueSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedWeakBlueSteel",16618)).setItemName("UnshapedWeakBlueSteel").setIconCoord(4, 9);
        HCBlueSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedHCBlueSteel",16619)).setItemName("UnshapedHCBlueSteel").setIconCoord(4, 9);
        WeakRedSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedWeakRedSteel",16621)).setItemName("UnshapedWeakRedSteel").setIconCoord(3, 10);
        HCRedSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedHCRedSteel",16622)).setItemName("UnshapedHCRedSteel").setIconCoord(3, 10);
        WeakSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedWeakSteel",16623)).setItemName("UnshapedWeakSteel").setIconCoord(6, 10);
        HCSteelUnshaped = new ItemTerraMeltedMetal(TFCSettings.getIntFor(config,"item","UnshapedHCSteel",16624)).setItemName("UnshapedHCSteel").setIconCoord(6, 10);
        Coke = ((ItemTerra) new ItemTerra(TFCSettings.getIntFor(config,"item","Coke",16625)).setItemName("coke").setIconCoord(2, 0)).setTexturePath("/bioxx/terrasprites.png");

        BismuthProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthProPickHead",16626)).setItemName("Bismuth ProPick Head").setIconCoord(1, 1);
        BismuthBronzeProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BismuthBronzeProPickHead",16627)).setItemName("Bismuth Bronze ProPick Head").setIconCoord(2, 1);
        BlackBronzeProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackBronzeProPickHead",16628)).setItemName("Black Bronze ProPick Head").setIconCoord(3, 1);
        BlackSteelProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlackSteelProPickHead",16629)).setItemName("Black Steel ProPick Head").setIconCoord(4, 1);
        BlueSteelProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BlueSteelProPickHead",16630)).setItemName("Blue Steel ProPick Head").setIconCoord(5, 1);
        BronzeProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","BronzeProPickHead",16631)).setItemName("Bronze ProPick Head").setIconCoord(6, 1);
        CopperProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","CopperProPickHead",16632)).setItemName("Copper ProPick Head").setIconCoord(7, 1);
        WroughtIronProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","WroughtIronProPickHead",16633)).setItemName("Wrought Iron ProPick Head").setIconCoord(8, 1);
        RedSteelProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RedSteelProPickHead",16634)).setItemName("Red Steel ProPick Head").setIconCoord(9, 1);
        RoseGoldProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","RoseGoldProPickHead",16635)).setItemName("Rose Gold ProPick Head").setIconCoord(10, 1);
        SteelProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","SteelProPickHead",16636)).setItemName("Steel ProPick Head").setIconCoord(11, 1);
        TinProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","TinProPickHead",16637)).setItemName("Tin ProPick Head").setIconCoord(12, 1);
        ZincProPickHead = new ItemTerraMiscToolHead(TFCSettings.getIntFor(config,"item","ZincProPickHead",16638)).setItemName("Zinc ProPick Head").setIconCoord(13, 1);

        Flux = ((ItemTerra) new ItemTerra(TFCSettings.getIntFor(config,"item","Flux",16639)).setItemName("flux").setIconCoord(0, 0)).setTexturePath("/bioxx/terrasprites.png");

        terraBismuthBronzeAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraBismuthBronzeAnvilItem",16640), 0, AnvilReq.BISMUTHBRONZE).setItemName("terraBismuthBronzeAnvilItem").setIconCoord(2, 2);
        terraBlackBronzeAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraBlackBronzeAnvilItem",16641), 1, AnvilReq.BLACKBRONZE).setItemName("terraBlackBronzeAnvilItem").setIconCoord(3, 2);
        terraRoseGoldAnvilItem = new ItemTerraAnvil(TFCSettings.getIntFor(config,"item","terraRoseGoldAnvilItem",16642), 2, AnvilReq.ROSEGOLD).setItemName("terraRoseGoldAnvilItem").setIconCoord(10, 2);
        
        /**Armor Crafting related items*/
        BismuthSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","BismuthSheet",19000)).setItemName("BismuthSheet").setIconCoord(5,14)).setTexturePath("/bioxx/terraarmor1.png");
        BismuthBronzeSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Bismuth BronzeSheet",19001)).setItemName("Bismuth BronzeSheet").setIconCoord(6, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BlackBronzeSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Black BronzeSheet",19002)).setItemName("Black BronzeSheet").setIconCoord(7, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BlackSteelSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Black SteelSheet",19003)).setItemName("Black SteelSheet").setIconCoord(8, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BlueSteelSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Blue SteelSheet",19004)).setItemName("Blue SteelSheet").setIconCoord(9, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BronzeSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","BronzeSheet",19005)).setItemName("BronzeSheet").setIconCoord(11, 14)).setTexturePath("/bioxx/terraarmor1.png");
        CopperSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","CopperSheet",19006)).setItemName("CopperSheet").setIconCoord(12, 14)).setTexturePath("/bioxx/terraarmor1.png");
        WroughtIronSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Wrought IronSheet",19007)).setItemName("Wrought IronSheet").setIconCoord(14, 14)).setTexturePath("/bioxx/terraarmor1.png");
        RedSteelSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Red SteelSheet",19008)).setItemName("Red SteelSheet").setIconCoord(8, 15)).setTexturePath("/bioxx/terraarmor1.png");
        RoseGoldSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Rose GoldSheet",19009)).setItemName("Rose GoldSheet").setIconCoord(9, 15)).setTexturePath("/bioxx/terraarmor1.png");
        SteelSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","SteelSheet",19010)).setItemName("SteelSheet").setIconCoord(11, 15)).setTexturePath("/bioxx/terraarmor1.png");
        TinSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","TinSheet",19011)).setItemName("TinSheet").setIconCoord(13, 15)).setTexturePath("/bioxx/terraarmor1.png");
        ZincSheet = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","ZincSheet",19012)).setItemName("ZincSheet").setIconCoord(14, 15)).setTexturePath("/bioxx/terraarmor1.png");
        
        BismuthSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","BismuthSheet2x",19013)).setItemName("BismuthSheet2x").setIconCoord(5,14)).setTexturePath("/bioxx/terraarmor1.png");
        BismuthBronzeSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Bismuth BronzeSheet2x",19014)).setItemName("Bismuth BronzeSheet2x").setIconCoord(6, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BlackBronzeSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Black BronzeSheet2x",19015)).setItemName("Black BronzeSheet2x").setIconCoord(7, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BlackSteelSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Black SteelSheet2x",19016)).setItemName("Black SteelSheet2x").setIconCoord(8, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BlueSteelSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Blue SteelSheet2x",19017)).setItemName("Blue SteelSheet2x").setIconCoord(9, 14)).setTexturePath("/bioxx/terraarmor1.png");
        BronzeSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","BronzeSheet2x",19018)).setItemName("BronzeSheet2x").setIconCoord(11, 14)).setTexturePath("/bioxx/terraarmor1.png");
        CopperSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","CopperSheet2x",19019)).setItemName("CopperSheet2x").setIconCoord(12, 14)).setTexturePath("/bioxx/terraarmor1.png");
        WroughtIronSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Wrought IronSheet2x",19020)).setItemName("Wrought IronSheet2x").setIconCoord(14, 14)).setTexturePath("/bioxx/terraarmor1.png");
        RedSteelSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Red SteelSheet2x",19021)).setItemName("Red SteelSheet2x").setIconCoord(8, 15)).setTexturePath("/bioxx/terraarmor1.png");
        RoseGoldSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","Rose GoldSheet2x",19022)).setItemName("Rose GoldSheet2x").setIconCoord(9, 15)).setTexturePath("/bioxx/terraarmor1.png");
        SteelSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","SteelSheet2x",19023)).setItemName("SteelSheet2x").setIconCoord(11, 15)).setTexturePath("/bioxx/terraarmor1.png");
        TinSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","TinSheet2x",19024)).setItemName("TinSheet2x").setIconCoord(13, 15)).setTexturePath("/bioxx/terraarmor1.png");
        ZincSheet2x = ((ItemTerra)new ItemTerra(TFCSettings.getIntFor(config,"item","ZincSheet2x",19025)).setItemName("ZincSheet2x").setIconCoord(14, 15)).setTexturePath("/bioxx/terraarmor1.png");
        

        


        
        /**Formerly TFC_Mining*/
        terraSluice = new BlockTerraSluice(TFCSettings.getIntFor(config,"block","TerraSluice", 217), TileEntityTerraSluice.class).setBlockName("Sluice").setHardness(2F).setResistance(20F);

        terraGoldPan = new ItemTerraGoldPan(TFCSettings.getIntFor(config,"item","terraGoldPan",16001)).setItemName("GoldPan").setIconCoord(1, 0);
        terraSluiceItem = new ItemTerraSluice(TFCSettings.getIntFor(config,"item","terraSluiceItem",16002)).setItemName("SluiceItem").setIconCoord(6, 0);
        terraProPickStone = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickStone",16003)).setItemName("StoneProPick").setIconCoord(0, 1).setMaxDamage(64);
        terraProPickBismuth = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickBismuth",16004)).setItemName("BismuthProPick").setIconCoord(1, 1).setMaxDamage(128);
        terraProPickBismuthBronze = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickBismuthBronze",16005)).setItemName("BismuthBronzeProPick").setIconCoord(2, 1).setMaxDamage(180);
        terraProPickBlackBronze = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickBlackBronze",16006)).setItemName("BlackBronzeProPick").setIconCoord(3, 1).setMaxDamage(220);
        terraProPickBlackSteel = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickBlackSteel",16007)).setItemName("BlackSteelProPick").setIconCoord(4, 1).setMaxDamage(1024);
        terraProPickBlueSteel = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickBlueSteel",16008)).setItemName("BlueSteelProPick").setIconCoord(5, 1).setMaxDamage(1800);
        terraProPickBronze = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickBronze",16009)).setItemName("BronzeProPick").setIconCoord(6, 1).setMaxDamage(180);
        terraProPickCopper = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickCopper",16010)).setItemName("CopperProPick").setIconCoord(7, 1).setMaxDamage(180);
        terraProPickIron = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickWroughtIron",16012)).setItemName("WroughtIronProPick").setIconCoord(8, 1).setMaxDamage(256);
        terraProPickRedSteel = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickRedSteel",16016)).setItemName("RedSteelProPick").setIconCoord(9, 1).setMaxDamage(1800);
        terraProPickRoseGold = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickRoseGold",16017)).setItemName("RoseGoldProPick").setIconCoord(10, 1).setMaxDamage(190);
        terraProPickSteel = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickSteel",16019)).setItemName("SteelProPick").setIconCoord(11, 1).setMaxDamage(768);
        terraProPickTin = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickTin",16021)).setItemName("TinProPick").setIconCoord(12, 1).setMaxDamage(96);
        terraProPickZinc = new ItemTerraProPick(TFCSettings.getIntFor(config,"item","terraProPickZinc",16022)).setItemName("ZincProPick").setIconCoord(13, 1).setMaxDamage(160);
        TFC_Game.registerRecipes();

        MinecraftForge.registerOreHandler(new IOreHandler() 
        {
            public void registerOre(String oreClass, ItemStack ore) 
            {

            }
        });

        if (config != null) {
            config.save();
        }

    }
}
