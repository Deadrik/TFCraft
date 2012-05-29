//=======================================================
//Mod Client File
//=======================================================
package net.minecraft.src;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.TFC_Core.*;
import net.minecraft.src.TFC_Core.Custom.ItemDyeCustom;
import net.minecraft.src.TFC_Core.General.PacketHandler;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.EnumHelper;
import net.minecraft.src.forge.ICraftingHandler;
import net.minecraft.src.forge.IOreHandler;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.TFCItems;

public class mod_TFC_Core extends NetworkMod
{
    static Configuration config;
    public static mod_TFC_Core instance;
    public static IProxy proxy;
    public static float fogValue = -1;

    //////////////////Features////////////////////
    public static int logPileGuiId = 0;
    public static int workbenchGuiId = 1;

    //////////////////////////////////////////////
    public static int sulfurRenderId;
    public static int woodRenderId;
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
    
    public static Block bucketWater;
    
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

    public mod_TFC_Core()
    {
        ModLoader.setInGameHook(this, true, true);
    }

    @Override
    public String getVersion()
    {
        return "0.2a";
    }

    @Override
    public void load()
    {
        instance = this;
        proxy = ServerClientProxy.getProxy();
        MinecraftForge.registerConnectionHandler(new PacketHandler());
        

        sulfurRenderId = proxy.getUniqueBlockModelID(this, false);
        woodSupportRenderIdH = proxy.getUniqueBlockModelID(this, false);
        woodSupportRenderIdV = proxy.getUniqueBlockModelID(this, false);
        grassRenderId = proxy.getUniqueBlockModelID(this, false);
        oreRenderId = proxy.getUniqueBlockModelID(this, false);
        moltenRenderId = proxy.getUniqueBlockModelID(this, false);
        looseRockRenderId = proxy.getUniqueBlockModelID(this, false);
        terraFirepitRenderId = proxy.getUniqueBlockModelID(this, false);
        terraAnvilRenderId = proxy.getUniqueBlockModelID(this, true);
        terraBellowsRenderId = proxy.getUniqueBlockModelID(this, true);
        terraScribeRenderId = proxy.getUniqueBlockModelID(this, false);
        terraForgeRenderId = proxy.getUniqueBlockModelID(this, false);
        sluiceRenderId = proxy.getUniqueBlockModelID(this, false);

        //Register Blocks
        ModLoader.registerBlock(terraOre, net.minecraft.src.TFC_Core.ItemOre1.class);
        ModLoader.registerBlock(terraOre2, net.minecraft.src.TFC_Core.ItemOre2.class);
        ModLoader.registerBlock(terraOre3, net.minecraft.src.TFC_Core.ItemOre3.class);
        ModLoader.registerBlock(terraStoneIgIn, net.minecraft.src.TFC_Core.ItemIgIn.class);
        ModLoader.registerBlock(terraStoneIgEx, net.minecraft.src.TFC_Core.ItemIgEx.class);
        ModLoader.registerBlock(terraStoneSed, net.minecraft.src.TFC_Core.ItemSed.class);
        ModLoader.registerBlock(terraStoneMM, net.minecraft.src.TFC_Core.ItemMM.class);

        ModLoader.registerBlock(terraStoneIgInCobble, net.minecraft.src.TFC_Core.ItemIgInCobble.class);
        ModLoader.registerBlock(terraStoneIgExCobble, net.minecraft.src.TFC_Core.ItemIgExCobble.class);
        ModLoader.registerBlock(terraStoneSedCobble, net.minecraft.src.TFC_Core.ItemSedCobble.class);
        ModLoader.registerBlock(terraStoneMMCobble, net.minecraft.src.TFC_Core.ItemMMCobble.class);
        ModLoader.registerBlock(terraStoneIgInSmooth, net.minecraft.src.TFC_Core.ItemIgInCobble.class);
        ModLoader.registerBlock(terraStoneIgExSmooth, net.minecraft.src.TFC_Core.ItemIgExCobble.class);
        ModLoader.registerBlock(terraStoneSedSmooth, net.minecraft.src.TFC_Core.ItemSedCobble.class);
        ModLoader.registerBlock(terraStoneMMSmooth, net.minecraft.src.TFC_Core.ItemMMCobble.class);
        ModLoader.registerBlock(terraStoneIgInBrick, net.minecraft.src.TFC_Core.ItemIgInCobble.class);
        ModLoader.registerBlock(terraStoneIgExBrick, net.minecraft.src.TFC_Core.ItemIgExCobble.class);
        ModLoader.registerBlock(terraStoneSedBrick, net.minecraft.src.TFC_Core.ItemSedCobble.class);
        ModLoader.registerBlock(terraStoneMMBrick, net.minecraft.src.TFC_Core.ItemMMCobble.class);

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
        
        ModLoader.registerBlock(bucketWater);

        ModLoader.registerBlock(terraWoodSupportV);
        ModLoader.registerBlock(terraWoodSupportH);
        ModLoader.registerBlock(terraSulfur);
        ModLoader.registerBlock(Block.wood, net.minecraft.src.TFC_Core.ItemCustomWood.class);
        ModLoader.registerBlock(Block.leaves, net.minecraft.src.TFC_Core.ItemCustomLeaves.class);
        ModLoader.registerBlock(Block.sapling, net.minecraft.src.TFC_Core.ItemSapling.class);
        ModLoader.registerBlock(Block.planks, net.minecraft.src.TFC_Core.ItemTerraPlanks.class);

        //Items
        Item.itemsList[terraStoneIgEx.blockID] = new ItemIgEx(terraStoneIgEx.blockID - 256);
        Item.itemsList[terraStoneSed.blockID] = new ItemSed(terraStoneSed.blockID - 256);
        Item.itemsList[terraStoneIgIn.blockID] = new ItemIgIn(terraStoneIgIn.blockID - 256);
        Item.itemsList[terraStoneMM.blockID] = new ItemMM(terraStoneMM.blockID - 256);
        Item.itemsList[terraOre.blockID] = new ItemOre1(terraOre.blockID - 256);
        Item.itemsList[terraOre2.blockID] = new ItemOre2(terraOre2.blockID - 256);
        Item.itemsList[terraOre3.blockID] = new ItemOre3(terraOre3.blockID - 256);

        Item.itemsList[terraStoneIgExCobble.blockID] = new ItemIgExCobble(terraStoneIgExCobble.blockID - 256);
        Item.itemsList[terraStoneSedCobble.blockID] = new ItemSedCobble(terraStoneSedCobble.blockID - 256);
        Item.itemsList[terraStoneIgInCobble.blockID] = new ItemIgInCobble(terraStoneIgInCobble.blockID - 256);
        Item.itemsList[terraStoneMMCobble.blockID] = new ItemMMCobble(terraStoneMMCobble.blockID - 256);

        Item.itemsList[terraStoneIgExBrick.blockID] = new ItemIgExCobble(terraStoneIgExBrick.blockID - 256);
        Item.itemsList[terraStoneSedBrick.blockID] = new ItemSedCobble(terraStoneSedBrick.blockID - 256);
        Item.itemsList[terraStoneIgInBrick.blockID] = new ItemIgInCobble(terraStoneIgInBrick.blockID - 256);
        Item.itemsList[terraStoneMMBrick.blockID] = new ItemMMCobble(terraStoneMMBrick.blockID - 256);

        Item.itemsList[terraStoneIgExSmooth.blockID] = new ItemIgExCobble(terraStoneIgExSmooth.blockID - 256);
        Item.itemsList[terraStoneSedSmooth.blockID] = new ItemSedCobble(terraStoneSedSmooth.blockID - 256);
        Item.itemsList[terraStoneIgInSmooth.blockID] = new ItemIgInCobble(terraStoneIgInSmooth.blockID - 256);
        Item.itemsList[terraStoneMMSmooth.blockID] = new ItemMMCobble(terraStoneMMSmooth.blockID - 256);

        Item.itemsList[terraGrass.blockID] = new ItemTerraDirt(terraGrass.blockID - 256);
        Item.itemsList[terraGrass2.blockID] = new ItemTerraDirt(terraGrass2.blockID - 256);
        Item.itemsList[terraDirt.blockID] = new ItemTerraDirt(terraDirt.blockID - 256);
        Item.itemsList[terraDirt2.blockID] = new ItemTerraDirt(terraDirt2.blockID - 256);
        Item.itemsList[terraClay.blockID] = new ItemTerraDirt(terraClay.blockID - 256);
        Item.itemsList[terraClay2.blockID] = new ItemTerraDirt(terraClay2.blockID - 256);
        Item.itemsList[terraClayGrass.blockID] = new ItemTerraDirt(terraClayGrass.blockID - 256);
        Item.itemsList[terraClayGrass2.blockID] = new ItemTerraDirt(terraClayGrass2.blockID - 256);
        
        

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
        TFC_Core.RegisterRecipes();		
        proxy.registerTileEntities();
        setupCraftHook();
        MinecraftForge.setGuiHandler(this, proxy);
        MinecraftForge.registerConnectionHandler(new PacketHandler());
        proxy.registerRenderInformation();
        
        MinecraftForge.registerEntity(EntityTerraJavelin.class, this, 1, 160, 5, true);
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
        TFC_Core.Generate(world,rand, chunkX, chunkZ,64,160);
        TFC_Core.Generate(world,rand, chunkX, chunkZ,128,224);
        
        TFC_Core.GeneratePits(world,rand, chunkX, chunkZ);
        TFC_Core.GeneratePlants(world,rand, chunkX, chunkZ);
        TFC_Core.GenerateLooseRocks(world,rand, chunkX, chunkZ);
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

                if(itemstack.itemID == mod_TFC_Core.terraStoneSedBrick.blockID || itemstack.itemID == mod_TFC_Core.terraStoneIgInBrick.blockID || 
                        itemstack.itemID == mod_TFC_Core.terraStoneIgExBrick.blockID || itemstack.itemID == mod_TFC_Core.terraStoneMMBrick.blockID)
                {
                    HandleItem(entityplayer, iinventory, TFC_Core.Chisels);
                }
                else if(itemstack.itemID == Block.planks.blockID)
                {
                    HandleItem(entityplayer, iinventory, TFC_Core.Axes);
                    HandleItem(entityplayer, iinventory, TFC_Core.Saws);
                }
                else if(itemstack.itemID == TFCItems.terraWoodSupportItemV.shiftedIndex || itemstack.itemID == TFCItems.terraWoodSupportItemH.shiftedIndex)
                {
                    HandleItem(entityplayer, iinventory, TFC_Core.Axes);
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
                    //ContainerTerraWorkbench cw = (ContainerTerraWorkbench)((GuiContainerTFC)guiscreen).inventorySlots;
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

        terraStoneIgInCobble = new BlockTerraIgInCobble(TFCSettings.getIntFor(config,"block","terraStoneIgInCobble", 198), Material.rock).setHardness(6F).setResistance(10F).setBlockName("IgInRockCobble");
        terraStoneIgIn = new BlockTerraIgIn(TFCSettings.getIntFor(config,"block","TerraIgIn", 209), Material.rock, terraStoneIgInCobble.blockID).setHardness(8F).setResistance(10F).setBlockName("IgInRock");	
        terraStoneIgInSmooth = new BlockTerraIgInSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgInSmooth", 182), 48).setHardness(12F).setResistance(20F).setBlockName("IgInRockSmooth");
        terraStoneIgInBrick = new BlockTerraIgInSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgInBrick", 186), 32).setHardness(12F).setResistance(25F).setBlockName("IgInRockBrick");
        
        terraStoneSedCobble = new BlockTerraSedCobble(TFCSettings.getIntFor(config,"block","terraStoneSedCobble", 199), Material.rock).setHardness(4F).setResistance(10F).setBlockName("SedRockCobble");
        terraStoneSed = new BlockTerraSed(TFCSettings.getIntFor(config,"block","TerraSed", 210), Material.rock, terraStoneSedCobble.blockID).setHardness(6F).setResistance(7F).setBlockName("SedRock");
        terraStoneSedSmooth = new BlockTerraSedSmooth(TFCSettings.getIntFor(config,"block","terraStoneSedSmooth", 183), 112).setHardness(10F).setResistance(20F).setBlockName("SedRockSmooth");
        terraStoneSedBrick = new BlockTerraSedSmooth(TFCSettings.getIntFor(config,"block","terraStoneSedBrick", 187), 96).setHardness(10F).setResistance(25F).setBlockName("SedRockBrick");
        
        terraStoneIgExCobble = new BlockTerraIgExCobble(TFCSettings.getIntFor(config,"block","terraStoneIgExCobble", 200), Material.rock).setHardness(7F).setResistance(10F).setBlockName("IgExRockCobble");
        terraStoneIgEx = new BlockTerraIgEx(TFCSettings.getIntFor(config,"block","TerraIgEx", 211), Material.rock, terraStoneIgExCobble.blockID).setHardness(9F).setResistance(10F).setBlockName("IgExRock");
        terraStoneIgExSmooth = new BlockTerraIgExSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgExSmooth", 184), 51).setHardness(13F).setResistance(20F).setBlockName("IgExRockSmooth");
        terraStoneIgExBrick = new BlockTerraIgExSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgExBrick", 188), 35).setHardness(13F).setResistance(25F).setBlockName("IgExRockBrick");
        
        terraStoneMMCobble = new BlockTerraMMCobble(TFCSettings.getIntFor(config,"block","terraStoneMMCobble", 201), Material.rock).setHardness(4F).setResistance(10F).setBlockName("MMRockCobble");
        terraStoneMM = new BlockTerraMM(TFCSettings.getIntFor(config,"block","TerraMM", 212), Material.rock, terraStoneMMCobble.blockID).setHardness(6F).setResistance(8F).setBlockName("MMRock");
        terraStoneMMSmooth = new BlockTerraMMSmooth(TFCSettings.getIntFor(config,"block","terraStoneMMSmooth", 185), 122).setHardness(10F).setResistance(20F).setBlockName("MMRockSmooth");
        terraStoneMMBrick = new BlockTerraMMSmooth(TFCSettings.getIntFor(config,"block","terraStoneMMBrick", 189), 106).setHardness(10F).setResistance(25F).setBlockName("MMRockBrick");

        terraDirt = (new net.minecraft.src.TFC_Core.BlockTerraDirt(TFCSettings.getIntFor(config,"block","terraDirt", 190), 112,tilledSoil)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("dirt");
        terraDirt2 = (new net.minecraft.src.TFC_Core.BlockTerraDirt2(TFCSettings.getIntFor(config,"block","terraDirt2", 191), 128,tilledSoil2)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("dirt");
        terraClay = (new net.minecraft.src.TFC_Core.BlockTerraClay(TFCSettings.getIntFor(config,"block","terraClay", 192), 144)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("clay");
        terraClay2 = (new net.minecraft.src.TFC_Core.BlockTerraClay2(TFCSettings.getIntFor(config,"block","terraClay2", 193), 160)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("clay");
        terraClayGrass = (BlockTerraClayGrass) (new net.minecraft.src.TFC_Core.BlockTerraClayGrass(TFCSettings.getIntFor(config,"block","terraClayGrass", 194), 144, terraClay)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("ClayGrass"); 
        terraClayGrass2 = (BlockTerraClayGrass2) (new net.minecraft.src.TFC_Core.BlockTerraClayGrass2(TFCSettings.getIntFor(config,"block","terraClayGrass2", 195), 160, terraClay2)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("ClayGrass"); 
        terraGrass = (BlockTerraGrass) (new net.minecraft.src.TFC_Core.BlockTerraGrass(TFCSettings.getIntFor(config,"block","terraGrass", 196), 112, terraDirt)).setHardness(2F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");
        terraGrass2 = (BlockTerraGrass2) (new net.minecraft.src.TFC_Core.BlockTerraGrass2(TFCSettings.getIntFor(config,"block","terraGrass2", 197), 128, terraDirt2)).setHardness(2F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");  
        terraPeat = (new net.minecraft.src.TFC_Core.BlockTerraPeat(TFCSettings.getIntFor(config,"block","terraPeat", 180), 135)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("peat");
        terraPeatGrass = (BlockTerraPeatGrass)(new net.minecraft.src.TFC_Core.BlockTerraPeatGrass(TFCSettings.getIntFor(config,"block","terraPeatGrass", 181), 135, terraPeat)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("PeatGrass");

        //Block.grass = (BlockGrass)terraGrass;
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

        terraWood = Block.wood;
        terraLeaves = Block.leaves;
        terraSapling = Block.sapling;
        
        bucketWater = new BlockBucketWater(TFCSettings.getIntFor(config,"block","bucketWater", 224)).setHardness(100.0F).setLightOpacity(3).disableStats().setRequiresSelfNotify().setBlockName("bucketWater");

        MinecraftForge.setBlockHarvestLevel(terraStoneIgIn, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(terraStoneIgEx, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(terraStoneSed, "pickaxe", 0);
        MinecraftForge.setBlockHarvestLevel(terraStoneMM, "pickaxe", 0);
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

        
        

        
        RegisterToolClasses();
        //last thing
        initOreDictionary();

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

    public static void initOreDictionary() 
    {
        MinecraftForge.registerOre("terraRock", new ItemStack(terraStoneIgIn,1));
        MinecraftForge.registerOre("terraRock", new ItemStack(terraStoneIgEx,1));
        MinecraftForge.registerOre("terraRock", new ItemStack(terraStoneSed,1));
        MinecraftForge.registerOre("terraRock", new ItemStack(terraStoneMM,1));
        MinecraftForge.registerOre("terraRockIgIn", new ItemStack(terraStoneIgIn,1));
        MinecraftForge.registerOre("terraRockIgEx", new ItemStack(terraStoneIgEx,1));
        MinecraftForge.registerOre("terraRockSed", new ItemStack(terraStoneSed,1));
        MinecraftForge.registerOre("terraRockMM", new ItemStack(terraStoneMM,1));

        MinecraftForge.registerOre("terraRockGranite", new ItemStack(terraStoneIgIn,1,0));
        MinecraftForge.registerOre("terraRockDiorite", new ItemStack(terraStoneIgIn,1,1));
        MinecraftForge.registerOre("terraRockGabbro", new ItemStack(terraStoneIgIn,1,2));
        MinecraftForge.registerOre("terraRockSiltstone", new ItemStack(terraStoneSed,1,3));
        MinecraftForge.registerOre("terraRockMudstone", new ItemStack(terraStoneSed,1,4));
        MinecraftForge.registerOre("terraRockShale", new ItemStack(terraStoneSed,1,5));
        MinecraftForge.registerOre("terraRockClaystone", new ItemStack(terraStoneSed,1,6));
        MinecraftForge.registerOre("terraRockRockSalt", new ItemStack(terraStoneSed,1,7));
        MinecraftForge.registerOre("terraRockLimestone", new ItemStack(terraStoneSed,1,8));
        MinecraftForge.registerOre("terraRockConglomerate", new ItemStack(terraStoneSed,1,9));
        MinecraftForge.registerOre("terraRockDolomite", new ItemStack(terraStoneSed,1,10));
        MinecraftForge.registerOre("terraRockChert", new ItemStack(terraStoneSed,1,11));
        MinecraftForge.registerOre("terraRockChalk", new ItemStack(terraStoneSed,1,12));
        MinecraftForge.registerOre("terraRockRhyolite", new ItemStack(terraStoneIgEx,1,13));
        MinecraftForge.registerOre("terraRockBasalt", new ItemStack(terraStoneIgEx,1,14));
        MinecraftForge.registerOre("terraRockAndesite", new ItemStack(terraStoneIgEx,1,15));
        MinecraftForge.registerOre("terraRockDacite", new ItemStack(terraStoneIgEx,1,16));
        MinecraftForge.registerOre("terraRockQuartzite", new ItemStack(terraStoneMM,1,17));
        MinecraftForge.registerOre("terraRockSlate", new ItemStack(terraStoneMM,1,18));
        MinecraftForge.registerOre("terraRockPhyllite", new ItemStack(terraStoneMM,1,19));
        MinecraftForge.registerOre("terraRockSchist", new ItemStack(terraStoneMM,1,20));
        MinecraftForge.registerOre("terraRockGneiss", new ItemStack(terraStoneMM,1,21));
        MinecraftForge.registerOre("terraRockMarble", new ItemStack(terraStoneMM,1,22));

        MinecraftForge.registerOre("terraOreNativeCopper", new ItemStack(terraOre,1,23));
        MinecraftForge.registerOre("terraOreNativeGold", new ItemStack(terraOre,1,24));
        MinecraftForge.registerOre("terraOreNativePlatinum", new ItemStack(terraOre,1,25));
        MinecraftForge.registerOre("terraOreHematite", new ItemStack(terraOre,1,26));
        MinecraftForge.registerOre("terraOreNativeSilver", new ItemStack(terraOre,1,27));
        MinecraftForge.registerOre("terraOreCassiterite", new ItemStack(terraOre,1,28));
        MinecraftForge.registerOre("terraOreGalena", new ItemStack(terraOre,1,29));
        MinecraftForge.registerOre("terraOreBismuthinite", new ItemStack(terraOre,1,30));
        MinecraftForge.registerOre("terraOreGarnierite", new ItemStack(terraOre,1,31));
        MinecraftForge.registerOre("terraOreMalachite", new ItemStack(terraOre,1,32));
        MinecraftForge.registerOre("terraOreMagnetite", new ItemStack(terraOre,1,33));
        MinecraftForge.registerOre("terraOreLimonite", new ItemStack(terraOre,1,34));
        MinecraftForge.registerOre("terraOreSphalerite", new ItemStack(terraOre,1,35));
        MinecraftForge.registerOre("terraOreTetrahedrite", new ItemStack(terraOre,1,36));
        MinecraftForge.registerOre("terraOreBituminousCoal", new ItemStack(terraOre,1,37));
        MinecraftForge.registerOre("terraOreLignite", new ItemStack(terraOre,1,38));
        MinecraftForge.registerOre("terraOreKaolinite", new ItemStack(terraOre2,1,39));
        MinecraftForge.registerOre("terraOreGypsum", new ItemStack(terraOre2,1,40));
        MinecraftForge.registerOre("terraOreSatinspar", new ItemStack(terraOre2,1,41));
        MinecraftForge.registerOre("terraOreSelenite", new ItemStack(terraOre2,1,42));
        MinecraftForge.registerOre("terraOreGraphite", new ItemStack(terraOre2,1,43));
        MinecraftForge.registerOre("terraOreKimberlite", new ItemStack(terraOre2,1,44));
        MinecraftForge.registerOre("terraOrePetrifiedWood", new ItemStack(terraOre2,1,45));
        MinecraftForge.registerOre("terraOreSulfur", new ItemStack(terraOre2,1,46));
        MinecraftForge.registerOre("terraOreJet", new ItemStack(terraOre2,1,47));
        MinecraftForge.registerOre("terraOreMicrocline", new ItemStack(terraOre2,1,48));
        MinecraftForge.registerOre("terraOrePitchblende", new ItemStack(terraOre2,1,49));
        MinecraftForge.registerOre("terraOreCinnabar", new ItemStack(terraOre2,1,50));
        MinecraftForge.registerOre("terraOreCryolite", new ItemStack(terraOre2,1,51));
        MinecraftForge.registerOre("terraOreSaltpeter", new ItemStack(terraOre2,1,52));
        MinecraftForge.registerOre("terraOreSerpentine", new ItemStack(terraOre2,1,53));
        MinecraftForge.registerOre("terraOreSylvite", new ItemStack(terraOre2,1,54));
        MinecraftForge.registerOre("terraOreBorax", new ItemStack(terraOre3,1,55));
        MinecraftForge.registerOre("terraOreOlivine", new ItemStack(terraOre3,1,56));
        MinecraftForge.registerOre("terraOreLapisLazuli", new ItemStack(terraOre3,1,57));

        MinecraftForge.registerOre("oreTin", new ItemStack(TFCItems.OreChunk,1,28));
        MinecraftForge.registerOre("oreCopper", new ItemStack(terraOre,1,23));
        MinecraftForge.registerOre("oreCopper", new ItemStack(terraOre,1,36));
        MinecraftForge.registerOre("oreIron", new ItemStack(terraOre,1,26));
        MinecraftForge.registerOre("oreIron", new ItemStack(terraOre,1,33));
        MinecraftForge.registerOre("oreIron", new ItemStack(terraOre,1,34));
        MinecraftForge.registerOre("oreGold", new ItemStack(terraOre,1,24));
        MinecraftForge.registerOre("oreSilver", new ItemStack(terraOre,1,27));
        MinecraftForge.registerOre("oreSilver", new ItemStack(terraOre,1,29));
        MinecraftForge.registerOre("oreZinc", new ItemStack(terraOre,1,35));
        MinecraftForge.registerOre("oreBismuth", new ItemStack(terraOre,1,30));
        MinecraftForge.registerOre("oreNickel", new ItemStack(terraOre,1,31));
        MinecraftForge.registerOre("orePlatinum", new ItemStack(terraOre,1,25));
        MinecraftForge.registerOre("itemOreUran", new ItemStack(terraOre2,1,49));

        MinecraftForge.registerOre("ingotBismuth", new ItemStack(TFCItems.BismuthIngot,1));
        MinecraftForge.registerOre("ingotBismuthBronze", new ItemStack(TFCItems.BismuthBronzeIngot,1));
        MinecraftForge.registerOre("ingotBrass", new ItemStack(TFCItems.BrassIngot,1));
        MinecraftForge.registerOre("ingotBronze", new ItemStack(TFCItems.BronzeIngot,1));
        MinecraftForge.registerOre("ingotBlackBronze", new ItemStack(TFCItems.BlackBronzeIngot,1));
        MinecraftForge.registerOre("ingotBlackSteel", new ItemStack(TFCItems.BlackSteelIngot,1));
        MinecraftForge.registerOre("ingotBlueSteel", new ItemStack(TFCItems.BlueSteelIngot,1));
        MinecraftForge.registerOre("ingotCopper", new ItemStack(TFCItems.CopperIngot,1));
        MinecraftForge.registerOre("ingotGold", new ItemStack(TFCItems.GoldIngot,1));
        MinecraftForge.registerOre("ingotWroughtIron", new ItemStack(TFCItems.WroughtIronIngot,1));
        MinecraftForge.registerOre("ingotLead", new ItemStack(TFCItems.LeadIngot,1));
        MinecraftForge.registerOre("ingotNickel", new ItemStack(TFCItems.NickelIngot,1));
        MinecraftForge.registerOre("ingotPigIron", new ItemStack(TFCItems.PigIronIngot,1));
        MinecraftForge.registerOre("ingotPlatinum", new ItemStack(TFCItems.PlatinumIngot,1));
        MinecraftForge.registerOre("ingotRedSteel", new ItemStack(TFCItems.RedSteelIngot,1));
        MinecraftForge.registerOre("ingotRoseGold", new ItemStack(TFCItems.RoseGoldIngot,1));
        MinecraftForge.registerOre("ingotSilver", new ItemStack(TFCItems.SilverIngot,1));
        MinecraftForge.registerOre("ingotSteel", new ItemStack(TFCItems.SteelIngot,1));
        MinecraftForge.registerOre("ingotRefinedIron", new ItemStack(TFCItems.SteelIngot,1));
        MinecraftForge.registerOre("ingotSterlingSilver", new ItemStack(TFCItems.SterlingSilverIngot,1));
        MinecraftForge.registerOre("ingotTin", new ItemStack(TFCItems.TinIngot,1));
        MinecraftForge.registerOre("ingotZinc", new ItemStack(TFCItems.ZincIngot,1));

        MinecraftForge.registerOre("gemAgateChipped", new ItemStack(TFCItems.terraGemAgate, 1, 0));
        MinecraftForge.registerOre("gemAgateFlawed", new ItemStack(TFCItems.terraGemAgate, 1, 1));
        MinecraftForge.registerOre("gemAgate", new ItemStack(TFCItems.terraGemAgate, 1, 2));
        MinecraftForge.registerOre("gemAgateFlawless", new ItemStack(TFCItems.terraGemAgate, 1, 3));
        MinecraftForge.registerOre("gemAgateExquisite", new ItemStack(TFCItems.terraGemAgate, 1, 4));
        MinecraftForge.registerOre("gemAmethystChipped", new ItemStack(TFCItems.terraGemAmethyst, 1, 0));
        MinecraftForge.registerOre("gemAmethystFlawed", new ItemStack(TFCItems.terraGemAmethyst, 1, 1));
        MinecraftForge.registerOre("gemAmethyst", new ItemStack(TFCItems.terraGemAmethyst, 1, 2));
        MinecraftForge.registerOre("gemAmethystFlawless", new ItemStack(TFCItems.terraGemAmethyst, 1, 3));
        MinecraftForge.registerOre("gemAmethystExquisite", new ItemStack(TFCItems.terraGemAmethyst, 1, 4));
        MinecraftForge.registerOre("gemBerylChipped", new ItemStack(TFCItems.terraGemBeryl, 1, 0));
        MinecraftForge.registerOre("gemBerylFlawed", new ItemStack(TFCItems.terraGemBeryl, 1, 1));
        MinecraftForge.registerOre("gemBeryl", new ItemStack(TFCItems.terraGemBeryl, 1, 2));
        MinecraftForge.registerOre("gemBerylFlawless", new ItemStack(TFCItems.terraGemBeryl, 1, 3));
        MinecraftForge.registerOre("gemBerylExquisite", new ItemStack(TFCItems.terraGemBeryl, 1, 4));

        MinecraftForge.registerOre("gemEmeraldChipped", new ItemStack(TFCItems.terraGemEmerald, 1, 0));
        MinecraftForge.registerOre("gemEmeraldFlawed", new ItemStack(TFCItems.terraGemEmerald, 1, 1));
        MinecraftForge.registerOre("gemEmerald", new ItemStack(TFCItems.terraGemEmerald, 1, 2));
        MinecraftForge.registerOre("gemEmeraldFlawless", new ItemStack(TFCItems.terraGemEmerald, 1, 3));
        MinecraftForge.registerOre("gemEmeraldExquisite", new ItemStack(TFCItems.terraGemEmerald, 1, 4));

        MinecraftForge.registerOre("gemGarnetChipped", new ItemStack(TFCItems.terraGemGarnet, 1, 0));
        MinecraftForge.registerOre("gemGarnetFlawed", new ItemStack(TFCItems.terraGemGarnet, 1, 1));
        MinecraftForge.registerOre("gemGarnet", new ItemStack(TFCItems.terraGemGarnet, 1, 2));
        MinecraftForge.registerOre("gemGarnetFlawless", new ItemStack(TFCItems.terraGemGarnet, 1, 3));
        MinecraftForge.registerOre("gemGarnetExquisite", new ItemStack(TFCItems.terraGemGarnet, 1, 4));

        MinecraftForge.registerOre("gemJadeChipped", new ItemStack(TFCItems.terraGemJade, 1, 0));
        MinecraftForge.registerOre("gemJadeFlawed", new ItemStack(TFCItems.terraGemJade, 1, 1));
        MinecraftForge.registerOre("gemJade", new ItemStack(TFCItems.terraGemJade, 1, 2));
        MinecraftForge.registerOre("gemJadeFlawless", new ItemStack(TFCItems.terraGemJade, 1, 3));
        MinecraftForge.registerOre("gemJadeExquisite", new ItemStack(TFCItems.terraGemJade, 1, 4));

        MinecraftForge.registerOre("gemJasperChipped", new ItemStack(TFCItems.terraGemJasper, 1, 0));
        MinecraftForge.registerOre("gemJasperFlawed", new ItemStack(TFCItems.terraGemJasper, 1, 1));
        MinecraftForge.registerOre("gemJasper", new ItemStack(TFCItems.terraGemJasper, 1, 2));
        MinecraftForge.registerOre("gemJasperFlawless", new ItemStack(TFCItems.terraGemJasper, 1, 3));
        MinecraftForge.registerOre("gemJasperExquisite", new ItemStack(TFCItems.terraGemJasper, 1, 4));

        MinecraftForge.registerOre("gemOpalChipped", new ItemStack(TFCItems.terraGemOpal, 1, 0));
        MinecraftForge.registerOre("gemOpalFlawed", new ItemStack(TFCItems.terraGemOpal, 1, 1));
        MinecraftForge.registerOre("gemOpal", new ItemStack(TFCItems.terraGemOpal, 1, 2));
        MinecraftForge.registerOre("gemOpalFlawless", new ItemStack(TFCItems.terraGemOpal, 1, 3));
        MinecraftForge.registerOre("gemOpalExquisite", new ItemStack(TFCItems.terraGemOpal, 1, 4));

        MinecraftForge.registerOre("gemRubyChipped", new ItemStack(TFCItems.terraGemRuby, 1, 0));
        MinecraftForge.registerOre("gemRubyFlawed", new ItemStack(TFCItems.terraGemRuby, 1, 1));
        MinecraftForge.registerOre("gemRuby", new ItemStack(TFCItems.terraGemRuby, 1, 2));
        MinecraftForge.registerOre("gemRubyFlawless", new ItemStack(TFCItems.terraGemRuby, 1, 3));
        MinecraftForge.registerOre("gemRubyExquisite", new ItemStack(TFCItems.terraGemRuby, 1, 4));

        MinecraftForge.registerOre("gemSapphireChipped", new ItemStack(TFCItems.terraGemSapphire, 1, 0));
        MinecraftForge.registerOre("gemSapphireFlawed", new ItemStack(TFCItems.terraGemSapphire, 1, 1));
        MinecraftForge.registerOre("gemSapphire", new ItemStack(TFCItems.terraGemSapphire, 1, 2));
        MinecraftForge.registerOre("gemSapphireFlawless", new ItemStack(TFCItems.terraGemSapphire, 1, 3));
        MinecraftForge.registerOre("gemSapphireExquisite", new ItemStack(TFCItems.terraGemSapphire, 1, 4));

        MinecraftForge.registerOre("gemTourmalineChipped", new ItemStack(TFCItems.terraGemTourmaline, 1, 0));
        MinecraftForge.registerOre("gemTourmalineFlawed", new ItemStack(TFCItems.terraGemTourmaline, 1, 1));
        MinecraftForge.registerOre("gemTourmaline", new ItemStack(TFCItems.terraGemTourmaline, 1, 2));
        MinecraftForge.registerOre("gemTourmalineFlawless", new ItemStack(TFCItems.terraGemTourmaline, 1, 3));
        MinecraftForge.registerOre("gemTourmalineExquisite", new ItemStack(TFCItems.terraGemTourmaline, 1, 4));

        MinecraftForge.registerOre("gemTopazChipped", new ItemStack(TFCItems.terraGemTopaz, 1, 0));
        MinecraftForge.registerOre("gemTopazFlawed", new ItemStack(TFCItems.terraGemTopaz, 1, 1));
        MinecraftForge.registerOre("gemTopaz", new ItemStack(TFCItems.terraGemTopaz, 1, 2));
        MinecraftForge.registerOre("gemTopazFlawless", new ItemStack(TFCItems.terraGemTopaz, 1, 3));
        MinecraftForge.registerOre("gemTopazExquisite", new ItemStack(TFCItems.terraGemTopaz, 1, 4));

        MinecraftForge.registerOreHandler(new IOreHandler() 
        {
            public void registerOre(String oreClass, ItemStack ore) 
            {
                Object[] pickaxeRecipe = new Object[] { "111"," 2 "," 2 ", Character.valueOf('1'), ore,Character.valueOf('2'), Item.stick};
                Object[] shovelRecipe = new Object[] { "1 ","2 ","2 ", Character.valueOf('1'), ore,Character.valueOf('2'), Item.stick};
                Object[] hoeRecipe = new Object[] { "11"," 2"," 2", Character.valueOf('1'), ore,Character.valueOf('2'), Item.stick};
                Object[] axeRecipe = new Object[] { "11 ","12 "," 2 ", Character.valueOf('1'), ore,Character.valueOf('2'), Item.stick};

                if (oreClass.equals("ingotBismuth"))
                {

                }
                if (oreClass.equals("ingotBismuthBronze"))
                {

                }
                if (oreClass.equals("ingotBlackBronze"))
                {

                }
                if (oreClass.equals("ingotBlackSteel"))
                {

                }
                if (oreClass.equals("ingotBlueSteel"))
                {

                }
                if (oreClass.equals("ingotBronze"))
                {

                }
                if (oreClass.equals("ingotCopper"))
                {

                }
                if (oreClass.equals("ingotGold"))
                {

                }
                if (oreClass.equals("ingotWroughtIron"))
                {

                }
                if (oreClass.equals("ingotNickel"))
                {

                }
                if (oreClass.equals("ingotPigIron"))
                {

                }
                if (oreClass.equals("ingotPlatinum"))
                {

                }
                if (oreClass.equals("ingotRedSteel"))
                {

                }
                if (oreClass.equals("ingotRoseGold"))
                {

                }
                if (oreClass.equals("ingotSilver"))
                {

                }
                if (oreClass.equals("ingotSteel"))
                {

                }
                if (oreClass.equals("ingotSterlingSilver"))
                {

                }
                if (oreClass.equals("ingotTin"))
                {

                }
                if (oreClass.equals("ingotZinc"))
                {

                }
            }
        });

    }

    @Override
    public boolean clientSideRequired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean serverSideRequired() {
        // TODO Auto-generated method stub
        return false;
    }

}
