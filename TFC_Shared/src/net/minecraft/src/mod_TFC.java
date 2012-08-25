//=======================================================
//Mod Client File
//=======================================================
package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import TFC.*;
import TFC.Blocks.*;
import TFC.Core.*;
import TFC.Entities.*;
import TFC.Items.*;
import TFC.TileEntities.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.modloader.BaseModTicker;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.EnumHelper;
import net.minecraft.src.forge.ICraftingHandler;
import net.minecraft.src.forge.IOreHandler;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.TFCItems;

public class mod_TFC extends NetworkMod implements ITickHandler
{
    static Configuration config;
    public static mod_TFC instance;
    public static IProxy proxy;
    public static float fogValue = -1;

    //////////////////Features////////////////////
    public static int logPileGuiId = 0;
    public static int workbenchGuiId = 1;
    public static int toolRackGuiId = 2;
    public static int terraFirepitGuiId = 20;
    public static int terraAnvilGuiId = 21;
    public static int terraScribeGuiId = 22;
    public static int terraForgeGuiId = 23;
    public static int terraMetallurgyGuiId = 24;
    public static int terraSluiceGuiId = 25;
    public static int terraBloomeryGuiId = 26;
    

    //////////////////////////////////////////////
    public static int sulfurRenderId;
    public static int woodFruitRenderId;
    public static int leavesFruitRenderId;
    public static int woodThickRenderId;
    public static int woodSupportRenderIdH;
    public static int woodSupportRenderIdV;
    public static int grassRenderId;
    public static int oreRenderId;
    public static int moltenRenderId;
    public static int looseRockRenderId;
    public static int snowRenderId;
    public static int terraFirepitRenderId;
    public static int terraAnvilRenderId;
    public static int terraBellowsRenderId;
    public static int terraScribeRenderId;
    public static int terraForgeRenderId;
    public static int sluiceRenderId;
    public static int toolRackRenderId;
    public static int finiteWaterRenderId;
    public static int partialRenderId;
    public static int stairRenderId;
    public static int slabRenderId;
    public static int cropRenderId;
    public static int cookingPitRenderId;
    public static int leavesRenderId;

    public static Block terraStoneIgIn;
    public static Block terraStoneIgEx;
    public static Block terraStoneSed;
    public static Block terraStoneMM;

    public static Block terraStoneIgInCobble;
    public static Block terraStoneIgExCobble;
    public static Block terraStoneSedCobble;
    public static Block terraStoneMMCobble;

    public static Block terraStoneIgInBrick;
    public static Block terraStoneIgExBrick;
    public static Block terraStoneSedBrick;
    public static Block terraStoneMMBrick;

    public static Block terraStoneIgInSmooth;
    public static Block terraStoneIgExSmooth;
    public static Block terraStoneSedSmooth;
    public static Block terraStoneMMSmooth;

    public static Block terraOre;
    public static Block terraOre2;
    public static Block terraOre3;
    public static Block terraSulfur;
    public static Block terraWood;
    public static Block terraLeaves;
    public static Block terraSapling;
    public static Block terraWoodSupportV;
    public static Block terraWoodSupportH;
    public static BlockTerraGrass terraGrass;
    public static BlockTerraGrass2 terraGrass2;
    public static Block terraDirt;
    public static Block terraDirt2;
    public static Block terraClay;
    public static Block terraClay2;
    public static BlockTerraClayGrass terraClayGrass;
    public static BlockTerraClayGrass2 terraClayGrass2;
    public static Block terraPeat;
    public static BlockTerraPeatGrass terraPeatGrass;
    public static Block LooseRock;
    public static Block LogPile;

    public static Block tilledSoil;
    public static Block tilledSoil2;

    public static Block finiteWater;
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

    public static Block fruitTreeWood;
    public static Block fruitTreeLeaves;
    public static Block fruitTreeLeaves2;

    public static Block stoneStairs;
    public static Block stoneSlabs;
    public static Block stoneStalac;

    public static EnumArmorMaterial BismuthArmorMaterial;
    public static EnumArmorMaterial BismuthBronzeArmorMaterial;
    public static EnumArmorMaterial BlackBronzeArmorMaterial;
    public static EnumArmorMaterial BlackSteelArmorMaterial;
    public static EnumArmorMaterial BlueSteelArmorMaterial;
    public static EnumArmorMaterial BronzeArmorMaterial;
    public static EnumArmorMaterial CopperArmorMaterial;
    public static EnumArmorMaterial IronArmorMaterial;
    public static EnumArmorMaterial RedSteelArmorMaterial;
    public static EnumArmorMaterial RoseGoldArmorMaterial;
    public static EnumArmorMaterial SteelArmorMaterial;
    public static EnumArmorMaterial TinArmorMaterial;
    public static EnumArmorMaterial ZincArmorMaterial;

    public mod_TFC()
    {
        proxy = ServerClientProxy.getProxy();
        ModLoader.setInGameHook(this, true, true);
        proxy.registerKeys(this);
    }

    @Override
    public void load()
    {
        instance = this;

        MinecraftForge.registerConnectionHandler(new PacketHandler());

        sulfurRenderId = proxy.getUniqueBlockModelID(this, false);
        woodSupportRenderIdH = proxy.getUniqueBlockModelID(this, false);
        woodSupportRenderIdV = proxy.getUniqueBlockModelID(this, false);
        grassRenderId = proxy.getUniqueBlockModelID(this, false);
        oreRenderId = proxy.getUniqueBlockModelID(this, false);
        moltenRenderId = proxy.getUniqueBlockModelID(this, false);
        looseRockRenderId = proxy.getUniqueBlockModelID(this, false);
        terraFirepitRenderId = proxy.getUniqueBlockModelID(this, false);
        terraAnvilRenderId = proxy.getUniqueBlockModelID(this, false);
        terraBellowsRenderId = proxy.getUniqueBlockModelID(this, true);
        terraScribeRenderId = proxy.getUniqueBlockModelID(this, false);
        terraForgeRenderId = proxy.getUniqueBlockModelID(this, false);
        sluiceRenderId = proxy.getUniqueBlockModelID(this, false);
        woodFruitRenderId = proxy.getUniqueBlockModelID(this, false);
        leavesFruitRenderId = proxy.getUniqueBlockModelID(this, false);
        finiteWaterRenderId = proxy.getUniqueBlockModelID(this, false);
        stairRenderId = proxy.getUniqueBlockModelID(this, false);
        slabRenderId = proxy.getUniqueBlockModelID(this, false);
        cropRenderId = proxy.getUniqueBlockModelID(this, false);
        cookingPitRenderId = proxy.getUniqueBlockModelID(this, false);
        leavesRenderId = proxy.getUniqueBlockModelID(this, false);

        //Register Blocks
        ModLoader.registerBlock(terraOre, TFC.Items.ItemOre1.class);
        ModLoader.registerBlock(terraOre2, TFC.Items.ItemOre2.class);
        ModLoader.registerBlock(terraOre3, TFC.Items.ItemOre3.class);
        ModLoader.registerBlock(terraStoneIgIn, TFC.Items.ItemIgIn.class);
        ModLoader.registerBlock(terraStoneIgEx, TFC.Items.ItemIgEx.class);
        ModLoader.registerBlock(terraStoneSed, TFC.Items.ItemSed.class);
        ModLoader.registerBlock(terraStoneMM, TFC.Items.ItemMM.class);

        ModLoader.registerBlock(terraStoneIgInCobble, TFC.Items.ItemIgInCobble.class);
        ModLoader.registerBlock(terraStoneIgExCobble, TFC.Items.ItemIgExCobble.class);
        ModLoader.registerBlock(terraStoneSedCobble, TFC.Items.ItemSedCobble.class);
        ModLoader.registerBlock(terraStoneMMCobble, TFC.Items.ItemMMCobble.class);
        ModLoader.registerBlock(terraStoneIgInSmooth, TFC.Items.ItemIgInCobble.class);
        ModLoader.registerBlock(terraStoneIgExSmooth, TFC.Items.ItemIgExCobble.class);
        ModLoader.registerBlock(terraStoneSedSmooth, TFC.Items.ItemSedCobble.class);
        ModLoader.registerBlock(terraStoneMMSmooth, TFC.Items.ItemMMCobble.class);
        ModLoader.registerBlock(terraStoneIgInBrick, TFC.Items.ItemIgInCobble.class);
        ModLoader.registerBlock(terraStoneIgExBrick, TFC.Items.ItemIgExCobble.class);
        ModLoader.registerBlock(terraStoneSedBrick, TFC.Items.ItemSedCobble.class);
        ModLoader.registerBlock(terraStoneMMBrick, TFC.Items.ItemMMCobble.class);

        ModLoader.registerBlock(terraDirt);
        ModLoader.registerBlock(terraDirt2);
        ModLoader.registerBlock(terraClay);
        ModLoader.registerBlock(terraClay2);
        ModLoader.registerBlock(terraGrass);
        ModLoader.registerBlock(terraGrass2);
        ModLoader.registerBlock(terraClayGrass);
        ModLoader.registerBlock(terraClayGrass2);
        ModLoader.registerBlock(terraPeatGrass);
        ModLoader.registerBlock(terraPeat);
        ModLoader.registerBlock(LooseRock);
        ModLoader.registerBlock(LogPile);

        ModLoader.registerBlock(tilledSoil);
        ModLoader.registerBlock(tilledSoil2);

        ModLoader.registerBlock(finiteWater);

        ModLoader.registerBlock(terraWoodSupportV);
        ModLoader.registerBlock(terraWoodSupportH);
        ModLoader.registerBlock(terraSulfur);
        ModLoader.registerBlock(Block.wood, TFC.Items.ItemCustomWood.class);
        ModLoader.registerBlock(Block.leaves, TFC.Items.ItemCustomLeaves.class);
        ModLoader.registerBlock(Block.sapling, TFC.Items.ItemSapling.class);
        ModLoader.registerBlock(Block.planks, TFC.Items.ItemTerraPlanks.class);

        ModLoader.registerBlock(terraFirepit);
        ModLoader.registerBlock(terraBellows);
        ModLoader.registerBlock(terraAnvil);
        ModLoader.registerBlock(terraScribe);
        ModLoader.registerBlock(terraForge);
        ModLoader.registerBlock(terraMetalTable);
        ModLoader.registerBlock(terraMolten);
        ModLoader.registerBlock(terraBloomery);
        ModLoader.registerBlock(terraSluice);

        ModLoader.registerBlock(fruitTreeWood);
        ModLoader.registerBlock(fruitTreeLeaves);
        ModLoader.registerBlock(fruitTreeLeaves2);

        ModLoader.registerBlock(stoneStairs);
        ModLoader.registerBlock(stoneSlabs);
        ModLoader.registerBlock(stoneStalac);

        //Items
//        Item.itemsList[terraStoneIgEx.blockID] = new ItemIgEx(terraStoneIgEx.blockID - 256);
//        Item.itemsList[terraStoneSed.blockID] = new ItemSed(terraStoneSed.blockID - 256);
//        Item.itemsList[terraStoneIgIn.blockID] = new ItemIgIn(terraStoneIgIn.blockID - 256);
//        Item.itemsList[terraStoneMM.blockID] = new ItemMM(terraStoneMM.blockID - 256);
//        Item.itemsList[terraOre.blockID] = new ItemOre1(terraOre.blockID - 256);
//        Item.itemsList[terraOre2.blockID] = new ItemOre2(terraOre2.blockID - 256);
//        Item.itemsList[terraOre3.blockID] = new ItemOre3(terraOre3.blockID - 256);
//
//        Item.itemsList[terraStoneIgExCobble.blockID] = new ItemIgExCobble(terraStoneIgExCobble.blockID - 256);
//        Item.itemsList[terraStoneSedCobble.blockID] = new ItemSedCobble(terraStoneSedCobble.blockID - 256);
//        Item.itemsList[terraStoneIgInCobble.blockID] = new ItemIgInCobble(terraStoneIgInCobble.blockID - 256);
//        Item.itemsList[terraStoneMMCobble.blockID] = new ItemMMCobble(terraStoneMMCobble.blockID - 256);
//
//        Item.itemsList[terraStoneIgExBrick.blockID] = new ItemIgExCobble(terraStoneIgExBrick.blockID - 256);
//        Item.itemsList[terraStoneSedBrick.blockID] = new ItemSedCobble(terraStoneSedBrick.blockID - 256);
//        Item.itemsList[terraStoneIgInBrick.blockID] = new ItemIgInCobble(terraStoneIgInBrick.blockID - 256);
//        Item.itemsList[terraStoneMMBrick.blockID] = new ItemMMCobble(terraStoneMMBrick.blockID - 256);
//
//        Item.itemsList[terraStoneIgExSmooth.blockID] = new ItemIgExCobble(terraStoneIgExSmooth.blockID - 256);
//        Item.itemsList[terraStoneSedSmooth.blockID] = new ItemSedCobble(terraStoneSedSmooth.blockID - 256);
//        Item.itemsList[terraStoneIgInSmooth.blockID] = new ItemIgInCobble(terraStoneIgInSmooth.blockID - 256);
//        Item.itemsList[terraStoneMMSmooth.blockID] = new ItemMMCobble(terraStoneMMSmooth.blockID - 256);
//
//        Item.itemsList[terraGrass.blockID] = new ItemTerraDirt(terraGrass.blockID - 256);
//        Item.itemsList[terraGrass2.blockID] = new ItemTerraDirt(terraGrass2.blockID - 256);
//        Item.itemsList[terraDirt.blockID] = new ItemTerraDirt(terraDirt.blockID - 256);
//        Item.itemsList[terraDirt2.blockID] = new ItemTerraDirt(terraDirt2.blockID - 256);
//        Item.itemsList[terraClay.blockID] = new ItemTerraDirt(terraClay.blockID - 256);
//        Item.itemsList[terraClay2.blockID] = new ItemTerraDirt(terraClay2.blockID - 256);
//        Item.itemsList[terraClayGrass.blockID] = new ItemTerraDirt(terraClayGrass.blockID - 256);
//        Item.itemsList[terraClayGrass2.blockID] = new ItemTerraDirt(terraClayGrass2.blockID - 256);



        terraGrass.setIDs(terraGrass.blockID, terraGrass2.blockID, terraDirt.blockID, terraDirt2.blockID, 
                terraClay.blockID, terraClay2.blockID, terraClayGrass.blockID, terraClayGrass2.blockID, terraPeat.blockID, terraPeatGrass.blockID);
        terraGrass2.setIDs(terraGrass.blockID, terraGrass2.blockID, terraDirt.blockID, terraDirt2.blockID, 
                terraClay.blockID, terraClay2.blockID, terraClayGrass.blockID, terraClayGrass2.blockID, terraPeat.blockID, terraPeatGrass.blockID);
        terraClayGrass.setIDs(terraGrass.blockID, terraGrass2.blockID, terraDirt.blockID, terraDirt2.blockID, 
                terraClay.blockID, terraClay2.blockID, terraClayGrass.blockID, terraClayGrass2.blockID, terraPeat.blockID, terraPeatGrass.blockID);
        terraClayGrass2.setIDs(terraGrass.blockID, terraGrass2.blockID, terraDirt.blockID, terraDirt2.blockID, 
                terraClay.blockID, terraClay2.blockID, terraClayGrass.blockID, terraClayGrass2.blockID, terraPeat.blockID, terraPeatGrass.blockID);
        terraPeatGrass.setIDs(terraGrass.blockID, terraGrass2.blockID, terraDirt.blockID, terraDirt2.blockID, 
                terraClay.blockID, terraClay2.blockID, terraClayGrass.blockID, terraClayGrass2.blockID, terraPeat.blockID, terraPeatGrass.blockID);

        //Add Item Name Localizations
        proxy.registerTranslations();

        if(TFCSettings.enableVanillaRecipes == false)
        {
            RemoveRecipe(new ItemStack(Item.pickaxeWood,1));
            RemoveRecipe(new ItemStack(Item.axeWood,1));
            RemoveRecipe(new ItemStack(Item.shovelWood,1));
            RemoveRecipe(new ItemStack(Item.hoeWood,1));
            RemoveRecipe(new ItemStack(Item.swordWood,1));
            RemoveRecipe(new ItemStack(Block.stoneOvenIdle,1));
            RemoveRecipe(new ItemStack(Block.torchWood,4));
            RemoveRecipe(new ItemStack(Item.stick,4));
            RemoveRecipe(new ItemStack(Block.planks,4));
        }
        TFCItems.Setup();
        TFC_Core.RegisterRecipes();	
        TFC_Game.registerRecipes();
        RegisterToolClasses();
        proxy.registerTileEntities();
        setupCraftHook();

        MinecraftForge.setGuiHandler(this, proxy);
        MinecraftForge.registerConnectionHandler(new PacketHandler());
        proxy.registerRenderInformation();

        MinecraftForge.registerEntity(EntityTerraJavelin.class, this, 1, 160, 5, true);
        MinecraftForge.registerEntity(EntitySquidTFC.class, this, 2, 160, 5, true);
        MinecraftForge.registerEntity(EntityFallingStone.class, this, 3, 160, 5, true);
        MinecraftForge.registerEntity(EntityFallingDirt.class, this, 4, 160, 5, true);
        MinecraftForge.registerEntity(EntityFallingStone2.class, this, 5, 160, 5, true);
        MinecraftForge.registerEntity(EntityCowTFC.class, this, 6, 160, 5, true);
        MinecraftForge.registerEntity(EntityWolfTFC.class, this, 7, 160, 5, true);
        MinecraftForge.registerEntity(EntityBear.class, this, 8, 160, 5, true);
        MinecraftForge.registerEntity(EntityChickenTFC.class, this, 9, 160, 5, true);
        MinecraftForge.registerEntity(EntityPigTFC.class, this, 10, 160, 5, true);
        MinecraftForge.registerEntity(EntityDeer.class, this, 11, 160, 5, true);

        FMLCommonHandler.instance().registerTickHandler(this);
        MinecraftForge.registerEntityLivingHandler(new EntityLivingHandler());

    }


    public void addRenderer(Map map)
    {
        proxy.addRenderer(map);
    }

    public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        proxy.renderWorldBlock(renderblocks, iblockaccess, i, j, k, block, l);
        return true;
    }
    @Override
    public void generateSurface(World world, Random rand, int chunkX, int chunkZ)
    {
        TFC_Core.Generate(world,rand, chunkX, chunkZ,5,96);
        TFC_Core.Generate(world,rand, chunkX, chunkZ,64,130);
        TFC_Core.GenerateHigh(world,rand, chunkX, chunkZ,100,150);
        TFC_Core.GenerateHigh(world,rand, chunkX, chunkZ,130,200);

        TFC_Core.GeneratePits(world,rand, chunkX, chunkZ);
        TFC_Core.GeneratePlants(world,rand, chunkX, chunkZ);
        TFC_Core.GenerateLooseRocks(world,rand, chunkX, chunkZ);
        TFC_Core.generateCaveDecor(world,rand, chunkX, chunkZ);
    }

    public int farPlane = -1;
    public int transitioncount = 0;

    private static void setupCraftHook() 
    {
        ICraftingHandler handler = new ICraftingHandler()
        {
            public void onTakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory) 
            {
                int index = 0;

                if(iinventory != null)
                {
                    if(itemstack.itemID == mod_TFC.terraStoneSedBrick.blockID || itemstack.itemID == mod_TFC.terraStoneIgInBrick.blockID || 
                            itemstack.itemID == mod_TFC.terraStoneIgExBrick.blockID || itemstack.itemID == mod_TFC.terraStoneMMBrick.blockID)
                    {
                        HandleItem(entityplayer, iinventory, TFC_Core.Chisels);
                    }
                    else if(itemstack.itemID == TFCItems.SinglePlank.shiftedIndex)
                    {
                        HandleItem(entityplayer, iinventory, TFC_Core.Axes);
                        HandleItem(entityplayer, iinventory, TFC_Core.Saws);
                    }
                    else if(itemstack.itemID == Item.bowlEmpty.shiftedIndex || 
                            itemstack.getItem() instanceof ItemTerraFood)
                    {
                        HandleItem(entityplayer, iinventory, TFC_Core.Knives);
                    }
                    else if(itemstack.itemID == TFCItems.terraWoodSupportItemV.shiftedIndex || itemstack.itemID == TFCItems.terraWoodSupportItemH.shiftedIndex)
                    {
                        HandleItem(entityplayer, iinventory, TFC_Core.Axes);
                    }
                    else if(itemstack.itemID == TFCItems.Flux.shiftedIndex)
                    {
                        HandleItem(entityplayer, iinventory, TFCItems.Hammers);
                    }
                    else if(itemstack.itemID == TFCItems.LooseRock.shiftedIndex)
                    {
                        boolean openGui = false;
                        for(int i = 0; i < iinventory.getSizeInventory(); i++) 
                        {             
                            if(iinventory.getStackInSlot(i) == null) 
                            {
                                continue;
                            }
                            if(iinventory.getStackInSlot(i).itemID == TFCItems.LooseRock.shiftedIndex)
                            {
                                if(iinventory.getStackInSlot(i).stackSize == 1)
                                    iinventory.setInventorySlotContents(i, null);
                                else
                                {
                                    ItemStack is = iinventory.getStackInSlot(i); is.stackSize-=1;
                                    iinventory.setInventorySlotContents(i, is);
                                }
                                itemstack.stackSize = 1;
                                PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
                                pi.knappingRockType = new ItemStack(TFCItems.FlatRock, 1, itemstack.getItemDamage());
                                openGui = true;
                            }
                        }
                        if(openGui)
                            entityplayer.openGui(mod_TFC.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);

                        //itemstack = new ItemStack(TFCItems.FlatRock, 1, itemstack.getItemDamage());
                    }
                }
            }
        };

        MinecraftForge.registerCraftingHandler(handler);
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
                    iinventory.setInventorySlotContents(index, item);
                    iinventory.getStackInSlot(index).stackSize = iinventory.getStackInSlot(index).stackSize + 1;
                    if(iinventory.getStackInSlot(index).stackSize > 2)
                        iinventory.getStackInSlot(index).stackSize = 2;
                }
            }
        }
    }
    private static void RemoveRecipe(ItemStack resultItem) {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++)
        {
            IRecipe tmpRecipe = recipes.get(i);
            if (tmpRecipe instanceof ShapedRecipes) {
                ShapedRecipes recipe = (ShapedRecipes)tmpRecipe;
                ItemStack recipeResult = recipe.getRecipeOutput();

                if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                    recipes.remove(i--);
                }
            }
        }
    }

    public void keyboardEvent(KeyBinding var1) 
    {
        this.proxy.keyboardEvent(var1);
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


        //EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability)

        BismuthArmorMaterial = EnumHelper.addArmorMaterial("Bismuth", 10, new int[] {2,4,3,2}, 1);
        BismuthBronzeArmorMaterial = EnumHelper.addArmorMaterial("BismuthBronze", 20, new int[] {4,6,5,4}, 1);
        BlackBronzeArmorMaterial = EnumHelper.addArmorMaterial("BlackBronze", 20, new int[] {4,6,5,4}, 1);
        BlackSteelArmorMaterial = EnumHelper.addArmorMaterial("BlackSteel", 35, new int[] {6,8,7,6}, 1);
        BlueSteelArmorMaterial = EnumHelper.addArmorMaterial("BlueSteel", 40, new int[] {7,8,8,7}, 1);
        BronzeArmorMaterial = EnumHelper.addArmorMaterial("Bronze", 21, new int[] {4,6,5,4}, 1);
        CopperArmorMaterial = EnumHelper.addArmorMaterial("Copper", 15, new int[] {3,5,4,3}, 1);
        IronArmorMaterial = EnumHelper.addArmorMaterial("Iron", 25, new int[] {5,7,6,5}, 1);
        RedSteelArmorMaterial = EnumHelper.addArmorMaterial("RedSteel", 40, new int[] {7,8,8,7}, 1);
        RoseGoldArmorMaterial = EnumHelper.addArmorMaterial("RoseGold", 20, new int[] {4,6,5,4}, 1);
        SteelArmorMaterial = EnumHelper.addArmorMaterial("Steel", 30, new int[] {6,8,7,6}, 1);
        TinArmorMaterial = EnumHelper.addArmorMaterial("Tin", 10, new int[] {2,4,3,2}, 1);
        ZincArmorMaterial = EnumHelper.addArmorMaterial("Zinc", 10, new int[] {2,4,3,2}, 1);

        terraStoneIgInCobble = new BlockTerraIgInCobble(TFCSettings.getIntFor(config,"block","terraStoneIgInCobble", 198), Material.rock).setHardness(10F).setResistance(10F).setBlockName("IgInRockCobble");
        terraStoneIgIn = new BlockTerraIgIn(TFCSettings.getIntFor(config,"block","TerraIgIn", 209), Material.rock, terraStoneIgInCobble.blockID).setHardness(10F).setResistance(10F).setBlockName("IgInRock");	
        terraStoneIgInSmooth = new BlockTerraIgInSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgInSmooth", 182), 48).setHardness(10F).setResistance(20F).setBlockName("IgInRockSmooth");
        terraStoneIgInBrick = new BlockTerraIgInSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgInBrick", 186), 32).setHardness(10F).setResistance(15F).setBlockName("IgInRockBrick");

        terraStoneSedCobble = new BlockTerraSedCobble(TFCSettings.getIntFor(config,"block","terraStoneSedCobble", 199), Material.rock).setHardness(10F).setResistance(10F).setBlockName("SedRockCobble");
        terraStoneSed = new BlockTerraSed(TFCSettings.getIntFor(config,"block","TerraSed", 210), Material.rock, terraStoneSedCobble.blockID).setHardness(10F).setResistance(7F).setBlockName("SedRock");
        terraStoneSedSmooth = new BlockTerraSedSmooth(TFCSettings.getIntFor(config,"block","terraStoneSedSmooth", 183), 112).setHardness(10F).setResistance(20F).setBlockName("SedRockSmooth");
        terraStoneSedBrick = new BlockTerraSedSmooth(TFCSettings.getIntFor(config,"block","terraStoneSedBrick", 187), 96).setHardness(10F).setResistance(15F).setBlockName("SedRockBrick");

        terraStoneIgExCobble = new BlockTerraIgExCobble(TFCSettings.getIntFor(config,"block","terraStoneIgExCobble", 200), Material.rock).setHardness(10F).setResistance(10F).setBlockName("IgExRockCobble");
        terraStoneIgEx = new BlockTerraIgEx(TFCSettings.getIntFor(config,"block","TerraIgEx", 211), Material.rock, terraStoneIgExCobble.blockID).setHardness(10F).setResistance(10F).setBlockName("IgExRock");
        terraStoneIgExSmooth = new BlockTerraIgExSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgExSmooth", 184), 51).setHardness(10F).setResistance(20F).setBlockName("IgExRockSmooth");
        terraStoneIgExBrick = new BlockTerraIgExSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgExBrick", 188), 35).setHardness(1F).setResistance(15F).setBlockName("IgExRockBrick");

        terraStoneMMCobble = new BlockTerraMMCobble(TFCSettings.getIntFor(config,"block","terraStoneMMCobble", 201), Material.rock).setHardness(10F).setResistance(10F).setBlockName("MMRockCobble");
        terraStoneMM = new BlockTerraMM(TFCSettings.getIntFor(config,"block","TerraMM", 212), Material.rock, terraStoneMMCobble.blockID).setHardness(10F).setResistance(8F).setBlockName("MMRock");
        terraStoneMMSmooth = new BlockTerraMMSmooth(TFCSettings.getIntFor(config,"block","terraStoneMMSmooth", 185), 122).setHardness(10F).setResistance(20F).setBlockName("MMRockSmooth");
        terraStoneMMBrick = new BlockTerraMMSmooth(TFCSettings.getIntFor(config,"block","terraStoneMMBrick", 189), 106).setHardness(10F).setResistance(15F).setBlockName("MMRockBrick");

        terraDirt = (new TFC.Blocks.BlockTerraDirt(TFCSettings.getIntFor(config,"block","terraDirt", 190), 112,tilledSoil)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("dirt");
        terraDirt2 = (new TFC.Blocks.BlockTerraDirt2(TFCSettings.getIntFor(config,"block","terraDirt2", 191), 128,tilledSoil2)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("dirt");
        terraClay = (new TFC.Blocks.BlockTerraClay(TFCSettings.getIntFor(config,"block","terraClay", 192), 144)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("clay");
        terraClay2 = (new TFC.Blocks.BlockTerraClay2(TFCSettings.getIntFor(config,"block","terraClay2", 193), 160)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("clay");
        terraClayGrass = (BlockTerraClayGrass) (new TFC.Blocks.BlockTerraClayGrass(TFCSettings.getIntFor(config,"block","terraClayGrass", 194), 144, terraClay)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("ClayGrass"); 
        terraClayGrass2 = (BlockTerraClayGrass2) (new TFC.Blocks.BlockTerraClayGrass2(TFCSettings.getIntFor(config,"block","terraClayGrass2", 195), 160, terraClay2)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("ClayGrass"); 
        terraGrass = (BlockTerraGrass) (new TFC.Blocks.BlockTerraGrass(TFCSettings.getIntFor(config,"block","terraGrass", 196), 112, terraDirt)).setHardness(2F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");
        terraGrass2 = (BlockTerraGrass2) (new TFC.Blocks.BlockTerraGrass2(TFCSettings.getIntFor(config,"block","terraGrass2", 197), 128, terraDirt2)).setHardness(2F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");  
        terraPeat = (new TFC.Blocks.BlockTerraPeat(TFCSettings.getIntFor(config,"block","terraPeat", 180), 135)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("peat");
        terraPeatGrass = (BlockTerraPeatGrass)(new TFC.Blocks.BlockTerraPeatGrass(TFCSettings.getIntFor(config,"block","terraPeatGrass", 181), 135, terraPeat)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("PeatGrass");

        terraOre = new BlockTerraOre(TFCSettings.getIntFor(config,"block","TerraOre", 213), Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
        terraOre2 = new BlockTerraOre2(TFCSettings.getIntFor(config,"block","TerraOre2", 214), Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
        terraOre3 = new BlockTerraOre3(TFCSettings.getIntFor(config,"block","TerraOre3", 215), Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
        LooseRock = new BlockLooseRock(TFCSettings.getIntFor(config,"block","LooseRock", 179)).setHardness(0.05F).setResistance(1F).setBlockName("LooseRock");
        LogPile = new BlockTerraLogPile(TFCSettings.getIntFor(config,"block","LogPile", 178), TileEntityTerraLogPile.class).setHardness(10F).setResistance(1F).setBlockName("LogPile");

        terraSulfur = new BlockTerraSulfur(TFCSettings.getIntFor(config,"block","TerraSulfur", 208), Material.rock).setBlockName("Sulfur").setHardness(0.5F).setResistance(1F);
        terraWoodSupportV = new BlockTerraWoodSupport(TFCSettings.getIntFor(config,"block","terraWoodSupportV", 203), Material.wood).setBlockName("WoodSupportV").setHardness(0.5F).setResistance(1F);
        terraWoodSupportH = new BlockTerraWoodSupport(TFCSettings.getIntFor(config,"block","terraWoodSupportH", 202), Material.wood).setBlockName("WoodSupportH").setHardness(0.5F).setResistance(1F);

        tilledSoil = new BlockTerraFarmland(TFCSettings.getIntFor(config,"block","tilledSoil", 177), terraDirt.blockID).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("tilledSoil");
        tilledSoil2 = new BlockTerraFarmland(TFCSettings.getIntFor(config,"block","tilledSoil2", 176), terraDirt2.blockID).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("tilledSoil2");

        fruitTreeWood = new BlockFruitWood(TFCSettings.getIntFor(config,"block","fruitTreeWood", 175), 0, TileEntityFruitTreeWood.class).setBlockName("fruitTreeWood").setHardness(5.5F).setResistance(2F);
        fruitTreeLeaves = new BlockFruitLeaves(TFCSettings.getIntFor(config,"block","fruitTreeLeaves", 174), 48).setBlockName("fruitTreeLeaves").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundGrassFootstep);
        fruitTreeLeaves2 = new BlockFruitLeaves(TFCSettings.getIntFor(config,"block","fruitTreeLeaves2", 173), 56).setBlockName("fruitTreeLeaves2").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundGrassFootstep);

        Block.blocksList[5] = null;
        Block.blocksList[6] = null;
        //Block.blocksList[8] = null;
        //Block.blocksList[9] = null;
        Block.blocksList[17] = null;
        Block.blocksList[18] = null;
        Block.blocksList[31] = null;
        Block.blocksList[37] = null;
        Block.blocksList[38] = null;
        Block.blocksList[39] = null;
        Block.blocksList[40] = null;
        Block.blocksList[53] = null;
        Block.blocksList[54] = null;
        Block.blocksList[58] = null;
        Block.blocksList[59] = null;
        Block.blocksList[78] = null;
        Block.blocksList[79] = null;
        Block.blocksList[83] = null;
        Block.blocksList[106] = null;

        Block.blocksList[5] = (new BlockTerraPlanks(5, Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setBlockName("wood").setRequiresSelfNotify();
        Block.blocksList[6] = (new BlockCustomSapling(6, 160)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("sapling").setRequiresSelfNotify();
        //Block.blocksList[8]= (new BlockCustomFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().setRequiresSelfNotify();
        //Block.blocksList[9] = (new BlockCustomStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().setRequiresSelfNotify();
        Block.blocksList[17] = (new BlockTerraWood(17)).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setBlockName("log").setRequiresSelfNotify();
        Block.blocksList[18] = (new BlockCustomLeaves(18, 96)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setBlockName("leaves").setRequiresSelfNotify();
        Block.blocksList[31] = (BlockTallGrass)(new BlockCustomTallGrass(31, 39)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("tallgrass");
        Block.blocksList[37] = (BlockFlower)(new BlockCustomFlower(37, 13)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("flower");
        Block.blocksList[38] = (BlockFlower)(new BlockCustomFlower(38, 12)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("rose");
        Block.blocksList[39] = (BlockFlower)(new BlockCustomMushroom(39, 29)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setLightValue(0.125F).setBlockName("mushroom");
        Block.blocksList[40] = (BlockFlower)(new BlockCustomMushroom(40, 28)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("mushroom");
        Block.blocksList[53] = (new BlockCustomStairs(53, Block.planks)).setBlockName("stairsWood").setRequiresSelfNotify();
        Block.blocksList[54] = (new BlockChestTFC(54)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("chest").setRequiresSelfNotify();
        Block.blocksList[58] = (new BlockTerraWorkbench(58, TileEntityTerraWorkbench.class)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("workbench");
        Block.blocksList[59] = (new BlockCrop(59, 88)).setHardness(0.3F).setStepSound(Block.soundGrassFootstep).setBlockName("crops").disableStats().setRequiresSelfNotify();
        Block.blocksList[78] = (new BlockCustomSnow(78, 66)).setHardness(0.1F).setStepSound(Block.soundClothFootstep).setBlockName("snow").setLightOpacity(1);
        Block.blocksList[79] = (new BlockCustomIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundGlassFootstep).setBlockName("ice");
        Block.blocksList[83] = (new BlockCustomReed(83, 73)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("reeds").disableStats();
        Block.blocksList[106] = (new BlockCustomVine(106)).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setBlockName("vine").setRequiresSelfNotify();


        terraWood = Block.blocksList[17];
        terraLeaves = Block.blocksList[18];
        terraSapling = Block.blocksList[6];

        finiteWater = new BlockFiniteWater(TFCSettings.getIntFor(config,"block","bucketWater", 224)).setHardness(100.0F).setLightOpacity(3).disableStats().setRequiresSelfNotify().setBlockName("bucketWater");

        terraFirepit = new BlockFirepit(TFCSettings.getIntFor(config,"block","terraFirepit", 207), TileEntityTerraFirepit.class, 80).setBlockName("terraFirepit").setHardness(1).setLightValue(0F);
        terraBellows = new BlockTerraBellows(TFCSettings.getIntFor(config,"block","terraBellows", 206),Material.wood).setBlockName("terraBellows").setHardness(2);
        terraForge= new BlockTerraForge(TFCSettings.getIntFor(config,"block","terraForge", 216), TileEntityTerraForge.class, 90).setBlockName("terraForge").setHardness(20).setLightValue(0F);
        terraScribe = new BlockTerraScribe(TFCSettings.getIntFor(config,"block","terraScribe", 204), TileEntityTerraScribe.class).setBlockName("terraScribe").setHardness(2);
        terraAnvil = new BlockTerraAnvil(TFCSettings.getIntFor(config,"block","terraAnvil", 205),192, TileEntityTerraAnvil.class).setBlockName("terraAnvil").setHardness(3);
        terraAnvil2 = new BlockTerraAnvil(TFCSettings.getIntFor(config,"block","terraAnvil2", 225),208, TileEntityTerraAnvil.class).setBlockName("terraAnvil2").setHardness(3);

        terraMetalTable = new BlockTerraMetallurgy(TFCSettings.getIntFor(config,"block","terraMetallurgy", 218), TileEntityTerraMetallurgy.class).setBlockName("terraMetallurgy").setHardness(3);
        terraMolten = new BlockTerraMolten(TFCSettings.getIntFor(config,"block","terraMolten", 219)).setBlockName("terraMolten").setHardness(20);
        terraBloomery = new BlockTerraBloomery(TFCSettings.getIntFor(config,"block","terraBloomery", 220), TileEntityTerraBloomery.class, 65).setBlockName("terraBloomery").setHardness(20).setLightValue(0F);
        terraBloomeryOn = new BlockTerraBloomery(TFCSettings.getIntFor(config,"block","terraBloomeryOn", 221), TileEntityTerraBloomery.class, 66).setBlockName("terraBloomeryOn").setHardness(20).setLightValue(1.0F);
        terraFirepitOn = new BlockFirepit(TFCSettings.getIntFor(config,"block","terraFirepitOn", 222), TileEntityTerraFirepit.class, 81).setBlockName("terraFirepitOn").setHardness(1).setLightValue(1.0F);
        terraForgeOn = new BlockTerraForge(TFCSettings.getIntFor(config,"block","terraForgeOn", 223), TileEntityTerraForge.class, 91).setBlockName("terraForgeOn").setHardness(20).setLightValue(1.0F);
        terraSluice = new BlockTerraSluice(TFCSettings.getIntFor(config,"block","TerraSluice", 217), TileEntityTerraSluice.class).setBlockName("Sluice").setHardness(2F).setResistance(20F);

        stoneStairs = new BlockStair(TFCSettings.getIntFor(config,"block","stoneStairs", 2000)).setBlockName("stoneStairs").setRequiresSelfNotify().setHardness(5).setResistance(15F);
        stoneSlabs = new BlockSlab(TFCSettings.getIntFor(config,"block","stoneSlabs", 2001)).setBlockName("stoneSlabs").setRequiresSelfNotify().setHardness(5).setResistance(15F);
        stoneStalac = new BlockStalactite(TFCSettings.getIntFor(config,"block","stoneStalac", 2002)).setBlockName("stoneStalac").setRequiresSelfNotify().setHardness(5);





        MinecraftForge.setBlockHarvestLevel(terraStoneIgIn, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(terraStoneIgEx, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(terraStoneSed, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(terraStoneMM, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(stoneStalac, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(terraOre, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(terraOre2, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(terraOre3, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(terraDirt, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraDirt2, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraGrass, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraGrass2, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraClay, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraClay2, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraClayGrass, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraClayGrass2, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraWood, "axe", 1);
        MinecraftForge.setBlockHarvestLevel(terraPeat, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(terraPeatGrass, "shovel", 0);




        if (config != null) {
            config.save();
        }
    }

    private static void RegisterToolClasses() 
    {
        //pickaxes
        MinecraftForge.setToolClass(TFCItems.terraIgInPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.terraIgExPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.terraSedPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.terraMMPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.boneIgInPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.boneIgExPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.boneSedPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.boneMMPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.terraBismuthPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.terraBismuthBronzePick, "pickaxe", 2);
        MinecraftForge.setToolClass(TFCItems.terraBlackBronzePick, "pickaxe", 2);
        MinecraftForge.setToolClass(TFCItems.terraBlackSteelPick, "pickaxe", 5);
        MinecraftForge.setToolClass(TFCItems.terraBlueSteelPick, "pickaxe", 6);
        MinecraftForge.setToolClass(TFCItems.terraBronzePick, "pickaxe", 2);
        MinecraftForge.setToolClass(TFCItems.terraCopperPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.terraWroughtIronPick, "pickaxe", 3);
        MinecraftForge.setToolClass(TFCItems.terraRedSteelPick, "pickaxe", 6);
        MinecraftForge.setToolClass(TFCItems.terraRoseGoldPick, "pickaxe", 2);
        MinecraftForge.setToolClass(TFCItems.terraSteelPick, "pickaxe", 4);
        MinecraftForge.setToolClass(TFCItems.terraTinPick, "pickaxe", 1);
        MinecraftForge.setToolClass(TFCItems.terraZincPick, "pickaxe", 1);
        //shovels
        MinecraftForge.setToolClass(TFCItems.terraIgInShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.terraIgExShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.terraSedShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.terraMMShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.boneIgInShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.boneIgExShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.boneSedShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.boneMMShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.terraBismuthShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.terraBismuthBronzeShovel, "shovel", 2);
        MinecraftForge.setToolClass(TFCItems.terraBlackBronzeShovel, "shovel", 2);
        MinecraftForge.setToolClass(TFCItems.terraBlackSteelShovel, "shovel", 5);
        MinecraftForge.setToolClass(TFCItems.terraBlueSteelShovel, "shovel", 6);
        MinecraftForge.setToolClass(TFCItems.terraBronzeShovel, "shovel", 2);
        MinecraftForge.setToolClass(TFCItems.terraCopperShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.terraWroughtIronShovel, "shovel", 3);
        MinecraftForge.setToolClass(TFCItems.terraRedSteelShovel, "shovel", 6);
        MinecraftForge.setToolClass(TFCItems.terraRoseGoldShovel, "shovel", 2);
        MinecraftForge.setToolClass(TFCItems.terraSteelShovel, "shovel", 4);
        MinecraftForge.setToolClass(TFCItems.terraTinShovel, "shovel", 1);
        MinecraftForge.setToolClass(TFCItems.terraZincShovel, "shovel", 1);
        //Axes
        MinecraftForge.setToolClass(TFCItems.terraIgInAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.terraIgExAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.terraSedAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.terraMMAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.boneIgInAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.boneIgExAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.boneSedAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.boneMMAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.terraBismuthAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.terraBismuthBronzeAxe, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.terraBlackBronzeAxe, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.terraBlackSteelAxe, "axe", 5);
        MinecraftForge.setToolClass(TFCItems.terraBlueSteelAxe, "axe", 6);
        MinecraftForge.setToolClass(TFCItems.terraBronzeAxe, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.terraCopperAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.terraWroughtIronAxe, "axe", 3);
        MinecraftForge.setToolClass(TFCItems.terraRedSteelAxe, "axe", 6);
        MinecraftForge.setToolClass(TFCItems.terraRoseGoldAxe, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.terraSteelAxe, "axe", 4);
        MinecraftForge.setToolClass(TFCItems.terraTinAxe, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.terraZincAxe, "axe", 1);

        MinecraftForge.setToolClass(TFCItems.BismuthSaw, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.BismuthBronzeSaw, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.BlackBronzeSaw, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.BlackSteelSaw, "axe", 5);
        MinecraftForge.setToolClass(TFCItems.BlueSteelSaw, "axe", 6);
        MinecraftForge.setToolClass(TFCItems.BronzeSaw, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.CopperSaw, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.WroughtIronSaw, "axe", 3);
        MinecraftForge.setToolClass(TFCItems.RedSteelSaw, "axe", 6);
        MinecraftForge.setToolClass(TFCItems.RoseGoldSaw, "axe", 2);
        MinecraftForge.setToolClass(TFCItems.SteelSaw, "axe", 4);
        MinecraftForge.setToolClass(TFCItems.TinSaw, "axe", 1);
        MinecraftForge.setToolClass(TFCItems.ZincSaw, "axe", 1);
    }

    @Override
    public boolean clientSideRequired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean serverSideRequired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getVersion()
    {
        return "Beta 2 Build 48";
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        if (type.contains(TickType.WORLD))
        {
            World world;

            if(TFC_Core.isClient())
            {
                assert ((tickData[2] instanceof World));
                world = (World)tickData[2];
            }
            else
            {
                assert ((tickData[0] instanceof World));
                world = (World)tickData[0];
            }

            TFCSeasons.UpdateSeasons(world);
            for(Object p : world.playerEntities)
            {
                TFCHeat.HandleContainerHeat(world, ((EntityPlayer)p).inventory.mainInventory, (int)((EntityPlayer)p).posX, (int)((EntityPlayer)p).posY, (int)((EntityPlayer)p).posZ);
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {


    }

    @Override
    public EnumSet ticks()
    {
        return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.GAME);
    }
    @Override
    public String getLabel()
    {
        return "TFC";
    }

    private boolean doOnce = false;

    public boolean onTickInGame(float time, Minecraft minecraftInstance)
    {
        if(!doOnce && TFC_Core.isClient())
        {
            if(PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(minecraftInstance.thePlayer) == null)
                PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(mod_TFC.proxy.getPlayerName()));
            doOnce = true;
        }
        return false;
    }
}
