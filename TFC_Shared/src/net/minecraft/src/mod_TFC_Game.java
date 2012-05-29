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
    
    public static Block terraSluice;

    




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

                if(itemstack.itemID == TFCItems.Flux.shiftedIndex)
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
       
        
        EnumArmorMaterial[] mats = new EnumArmorMaterial[]{mod_TFC_Core.BismuthArmorMaterial,mod_TFC_Core.BismuthBronzeArmorMaterial,mod_TFC_Core.BlackBronzeArmorMaterial,mod_TFC_Core.BlackSteelArmorMaterial,mod_TFC_Core.BlueSteelArmorMaterial,
                mod_TFC_Core.BronzeArmorMaterial,mod_TFC_Core.CopperArmorMaterial,mod_TFC_Core.IronArmorMaterial,mod_TFC_Core.RedSteelArmorMaterial,mod_TFC_Core.RoseGoldArmorMaterial,
                mod_TFC_Core.SteelArmorMaterial,mod_TFC_Core.TinArmorMaterial,mod_TFC_Core.ZincArmorMaterial};
        
        int num = 19026;
        int i = 0;
        TFCItems.BismuthUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BismuthBronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackBronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlueSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.CopperUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.WroughtIronUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RedSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RoseGoldUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.SteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.TinUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.ZincUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedBoots",num)).setItemName(Names[i]+"UnfinishedBoots").setIconCoord(1+i,3)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        TFCItems.BismuthBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.BismuthBronzeBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.BlackBronzeBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.BlackSteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.BlueSteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.BronzeBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.CopperBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.WroughtIronBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.RedSteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.RoseGoldBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.SteelBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.TinBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        TFCItems.ZincBoots = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setItemName(Names[i]+"Boots").setIconCoord(1+i,3)); num++;i++;
        i = 0;
        TFCItems.BismuthUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BismuthBronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackBronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlueSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.CopperUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.WroughtIronUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RedSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RoseGoldUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.SteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.TinUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.ZincUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedGreaves",num)).setItemName(Names[i]+"UnfinishedGreaves").setIconCoord(1+i,2)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        TFCItems.BismuthGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.BismuthBronzeGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.BlackBronzeGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.BlackSteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.BlueSteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.BronzeGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.CopperGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.WroughtIronGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.RedSteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.RoseGoldGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.SteelGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.TinGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        TFCItems.ZincGreaves = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setItemName(Names[i]+"Greaves").setIconCoord(1+i,2)); num++;i++;
        i = 0;
        TFCItems.BismuthUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BismuthBronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackBronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlueSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.CopperUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.WroughtIronUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RedSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RoseGoldUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.SteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.TinUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.ZincUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedChestplate",num)).setItemName(Names[i]+"UnfinishedChestplate").setIconCoord(1+i,1)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        TFCItems.BismuthChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.BismuthBronzeChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.BlackBronzeChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.BlackSteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.BlueSteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.BronzeChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.CopperChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.WroughtIronChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.RedSteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.RoseGoldChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.SteelChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.TinChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        TFCItems.ZincChestplate = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setItemName(Names[i]+"Chestplate").setIconCoord(1+i,1)); num++;i++;
        i = 0;
        TFCItems.BismuthUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BismuthBronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackBronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlackSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BlueSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.BronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.CopperUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.WroughtIronUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RedSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.RoseGoldUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.SteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.TinUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        TFCItems.ZincUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFCSettings.getIntFor(config,"item",Names[i]+"UnfinishedHelmet",num)).setItemName(Names[i]+"UnfinishedHelmet").setIconCoord(1+i,0)).setTexturePath("/bioxx/terraarmor1.png"); num++;i++;
        i = 0;
        TFCItems.BismuthHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.BismuthBronzeHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.BlackBronzeHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.BlackSteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.BlueSteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.BronzeHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.CopperHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.WroughtIronHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.RedSteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.RoseGoldHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.SteelHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.TinHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        TFCItems.ZincHelmet = ((ItemTerraArmor)new ItemTerraArmor(TFCSettings.getIntFor(config,"item",Names[i]+"Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setItemName(Names[i]+"Helmet").setIconCoord(1+i,0)); num++;i++;
        
        
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
        terraSluice = new BlockTerraSluice(TFCSettings.getIntFor(config,"block","TerraSluice", 217), TileEntityTerraSluice.class).setBlockName("Sluice").setHardness(2F).setResistance(20F);
        /////////////////////////
        //Items
        /////////////////////////


        
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
