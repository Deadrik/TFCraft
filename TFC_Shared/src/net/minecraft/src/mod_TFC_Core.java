//=======================================================
//Mod Client File
//=======================================================
package net.minecraft.src;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.TFC_Core.*;
import net.minecraft.src.TFC_Core.General.PacketHandler;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.EnumHelper;
import net.minecraft.src.forge.ICraftingHandler;
import net.minecraft.src.forge.IOreHandler;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;

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
    public static int finiteWaterRenderId;

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
    public static Block finiteSaltWater;

    public static Item terraWoodSupportItemH;
    public static Item terraWoodSupportItemV;

    public static Item terraGemRuby;
    public static Item terraGemSapphire;
    public static Item terraGemEmerald;
    public static Item terraGemTopaz;
    public static Item terraGemGarnet;
    public static Item terraGemOpal;
    public static Item terraGemAmethyst;
    public static Item terraGemJasper;
    public static Item terraGemBeryl;
    public static Item terraGemTourmaline;
    public static Item terraGemJade;
    public static Item terraGemAgate;
    public static Item terraGemDiamond;
    public static Item terraSulfurPowder;
    public static Item terraSaltpeterPowder;

    public static Item terraBismuthIngot;
    public static Item terraBismuthBronzeIngot;
    public static Item terraBlackBronzeIngot;
    public static Item terraBlackSteelIngot;
    public static Item terraHCBlackSteelIngot;
    public static Item terraBlueSteelIngot;
    public static Item terraWeakBlueSteelIngot;
    public static Item terraHCBlueSteelIngot;
    public static Item terraBrassIngot;
    public static Item terraBronzeIngot;
    public static Item terraCopperIngot;
    public static Item terraGoldIngot;
    public static Item terraWroughtIronIngot;
    public static Item terraLeadIngot;
    public static Item terraNickelIngot;
    public static Item terraPigIronIngot;
    public static Item terraPlatinumIngot;
    public static Item terraRedSteelIngot;
    public static Item terraWeakRedSteelIngot;
    public static Item terraHCRedSteelIngot;
    public static Item terraRoseGoldIngot;
    public static Item terraSilverIngot;
    public static Item terraSteelIngot;
    public static Item terraWeakSteelIngot;
    public static Item terraHCSteelIngot;
    public static Item terraSterlingSilverIngot;
    public static Item terraTinIngot;
    public static Item terraZincIngot;

    public static Item terraBismuthIngot2x;
    public static Item terraBismuthBronzeIngot2x;
    public static Item terraBlackBronzeIngot2x;
    public static Item terraBlackSteelIngot2x;
    public static Item terraBlueSteelIngot2x;
    public static Item terraBrassIngot2x;
    public static Item terraBronzeIngot2x;
    public static Item terraCopperIngot2x;
    public static Item terraGoldIngot2x;
    public static Item terraWroughtIronIngot2x;
    public static Item terraLeadIngot2x;
    public static Item terraNickelIngot2x;
    public static Item terraPigIronIngot2x;
    public static Item terraPlatinumIngot2x;
    public static Item terraRedSteelIngot2x;
    public static Item terraRoseGoldIngot2x;
    public static Item terraSilverIngot2x;
    public static Item terraSteelIngot2x;
    public static Item terraSterlingSilverIngot2x;
    public static Item terraTinIngot2x;
    public static Item terraZincIngot2x;

    public static Item terraIgInPick;
    public static Item terraIgInShovel;
    public static Item terraIgInAxe;
    public static Item terraIgInHoe;
    public static Item terraSedPick;
    public static Item terraSedShovel;
    public static Item terraSedAxe;
    public static Item terraSedHoe;
    public static Item terraIgExPick;
    public static Item terraIgExShovel;
    public static Item terraIgExAxe;
    public static Item terraIgExHoe;
    public static Item terraMMPick;
    public static Item terraMMShovel;
    public static Item terraMMAxe;
    public static Item terraMMHoe;

    public static Item terraBismuthPick;
    public static Item terraBismuthShovel;
    public static Item terraBismuthAxe;
    public static Item terraBismuthHoe;
    public static Item terraBismuthBronzePick;
    public static Item terraBismuthBronzeShovel;
    public static Item terraBismuthBronzeAxe;
    public static Item terraBismuthBronzeHoe;
    public static Item terraBlackBronzePick;
    public static Item terraBlackBronzeShovel;
    public static Item terraBlackBronzeAxe;
    public static Item terraBlackBronzeHoe;
    public static Item terraBlackSteelPick;
    public static Item terraBlackSteelShovel;
    public static Item terraBlackSteelAxe;
    public static Item terraBlackSteelHoe;
    public static Item terraBlueSteelPick;
    public static Item terraBlueSteelShovel;
    public static Item terraBlueSteelAxe;
    public static Item terraBlueSteelHoe;
    public static Item terraBronzePick;
    public static Item terraBronzeShovel;
    public static Item terraBronzeAxe;
    public static Item terraBronzeHoe;
    public static Item terraCopperPick;
    public static Item terraCopperShovel;
    public static Item terraCopperAxe;
    public static Item terraCopperHoe;
    public static Item terraWroughtIronPick;
    public static Item terraWroughtIronShovel;
    public static Item terraWroughtIronAxe;
    public static Item terraWroughtIronHoe;
    public static Item terraRedSteelPick;
    public static Item terraRedSteelShovel;
    public static Item terraRedSteelAxe;
    public static Item terraRedSteelHoe;
    public static Item terraRoseGoldPick;
    public static Item terraRoseGoldShovel;
    public static Item terraRoseGoldAxe;
    public static Item terraRoseGoldHoe;
    public static Item terraSteelPick;
    public static Item terraSteelShovel;
    public static Item terraSteelAxe;
    public static Item terraSteelHoe;
    public static Item terraTinPick;
    public static Item terraTinShovel;
    public static Item terraTinAxe;
    public static Item terraTinHoe;
    public static Item terraZincPick;
    public static Item terraZincShovel;
    public static Item terraZincAxe;
    public static Item terraZincHoe;

    public static Item StoneChisel;
    public static Item BismuthChisel;
    public static Item BismuthBronzeChisel;
    public static Item BlackBronzeChisel;
    public static Item BlackSteelChisel;
    public static Item BlueSteelChisel;
    public static Item BronzeChisel;
    public static Item CopperChisel;
    public static Item WroughtIronChisel;
    public static Item RedSteelChisel;
    public static Item RoseGoldChisel;
    public static Item SteelChisel;
    public static Item TinChisel;
    public static Item ZincChisel;

    public static Item IgInStoneSword;
    public static Item IgExStoneSword;
    public static Item SedStoneSword;
    public static Item MMStoneSword;
    public static Item BismuthSword;
    public static Item BismuthBronzeSword;
    public static Item BlackBronzeSword;
    public static Item BlackSteelSword;
    public static Item BlueSteelSword;
    public static Item BronzeSword;
    public static Item CopperSword;
    public static Item WroughtIronSword;
    public static Item RedSteelSword;
    public static Item RoseGoldSword;
    public static Item SteelSword;
    public static Item TinSword;
    public static Item ZincSword;

    public static Item IgInStoneMace;
    public static Item IgExStoneMace;
    public static Item SedStoneMace;
    public static Item MMStoneMace;	
    public static Item BismuthMace;
    public static Item BismuthBronzeMace;
    public static Item BlackBronzeMace;
    public static Item BlackSteelMace;
    public static Item BlueSteelMace;
    public static Item BronzeMace;
    public static Item CopperMace;
    public static Item WroughtIronMace;
    public static Item RedSteelMace;
    public static Item RoseGoldMace;
    public static Item SteelMace;
    public static Item TinMace;
    public static Item ZincMace;

    public static Item BismuthSaw;
    public static Item BismuthBronzeSaw;
    public static Item BlackBronzeSaw;
    public static Item BlackSteelSaw;
    public static Item BlueSteelSaw;
    public static Item BronzeSaw;
    public static Item CopperSaw;
    public static Item WroughtIronSaw;
    public static Item RedSteelSaw;
    public static Item RoseGoldSaw;
    public static Item SteelSaw;
    public static Item TinSaw;
    public static Item ZincSaw;

    public static Item OreChunk;
    public static Item Logs;
    public static Item FlintPaxel;
    public static Item Javelin;
    public static Item bucket;

    public static Item boneIgInPick;
    public static Item boneIgInShovel;
    public static Item boneIgInAxe;
    public static Item boneIgInHoe;
    public static Item boneIgExPick;
    public static Item boneIgExShovel;
    public static Item boneIgExAxe;
    public static Item boneIgExHoe;
    public static Item boneSedPick;
    public static Item boneSedShovel;
    public static Item boneSedAxe;
    public static Item boneSedHoe;
    public static Item boneMMPick;
    public static Item boneMMShovel;
    public static Item boneMMAxe;
    public static Item boneMMHoe;

    /**Food Related Items and Blocks*/
    public static Item SeedsWheat;
    public static Item SeedsMelon;
    public static Item SeedsPumpkin;


    public static EnumToolMaterial IgInToolMaterial;
    public static EnumToolMaterial SedToolMaterial;
    public static EnumToolMaterial IgExToolMaterial;
    public static EnumToolMaterial MMToolMaterial;

    public static EnumToolMaterial BismuthToolMaterial;
    public static EnumToolMaterial BismuthBronzeToolMaterial;
    public static EnumToolMaterial BlackBronzeToolMaterial;
    public static EnumToolMaterial BlackSteelToolMaterial;
    public static EnumToolMaterial BlueSteelToolMaterial;
    public static EnumToolMaterial BronzeToolMaterial;
    public static EnumToolMaterial CopperToolMaterial;
    public static EnumToolMaterial GoldToolMaterial;
    public static EnumToolMaterial IronToolMaterial;
    public static EnumToolMaterial PigIronToolMaterial;
    public static EnumToolMaterial PlatinumToolMaterial;
    public static EnumToolMaterial RedSteelToolMaterial;
    public static EnumToolMaterial RoseGoldToolMaterial;
    public static EnumToolMaterial SilverToolMaterial;
    public static EnumToolMaterial SteelToolMaterial;
    public static EnumToolMaterial SterlingSilverToolMaterial;
    public static EnumToolMaterial TinToolMaterial;
    public static EnumToolMaterial ZincToolMaterial;

    public static int IgInStoneUses = 60;
    public static int IgExStoneUses = 70;
    public static int SedStoneUses = 50;
    public static int MMStoneUses = 55;	
    public static int BismuthUses = 210;
    public static int BismuthBronzeUses = 580;
    public static int BlackBronzeUses = 530;
    public static int BlackSteelUses = 1500;
    public static int BlueSteelUses = 2000;
    public static int BronzeUses = 600;
    public static int CopperUses = 410;
    public static int WroughtIronUses = 800;
    public static int RedSteelUses = 2000;
    public static int RoseGoldUses = 520;
    public static int SteelUses = 1100;
    public static int TinUses = 295;
    public static int ZincUses = 280;

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
        finiteWaterRenderId = proxy.getUniqueBlockModelID(this, false);

        //Register Blocks
        ModLoader.registerBlock(terraOre, net.minecraft.src.TFC_Core.ItemTerraRock.class);
        ModLoader.registerBlock(terraOre2, net.minecraft.src.TFC_Core.ItemTerraRock.class);
        ModLoader.registerBlock(terraOre3, net.minecraft.src.TFC_Core.ItemTerraRock.class);
        ModLoader.registerBlock(terraStoneIgIn, net.minecraft.src.TFC_Core.ItemTerraRock.class);
        ModLoader.registerBlock(terraStoneIgEx, net.minecraft.src.TFC_Core.ItemTerraRock.class);
        ModLoader.registerBlock(terraStoneSed, net.minecraft.src.TFC_Core.ItemTerraRock.class);
        ModLoader.registerBlock(terraStoneMM, net.minecraft.src.TFC_Core.ItemTerraRock.class);

        ModLoader.registerBlock(terraStoneIgInCobble, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneIgExCobble, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneSedCobble, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneMMCobble, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneIgInSmooth, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneIgExSmooth, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneSedSmooth, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneMMSmooth, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneIgInBrick, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneIgExBrick, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneSedBrick, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);
        ModLoader.registerBlock(terraStoneMMBrick, net.minecraft.src.TFC_Core.ItemTerraRockCobble.class);

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
        ModLoader.registerBlock(finiteSaltWater);

        ModLoader.registerBlock(terraWoodSupportV);
        ModLoader.registerBlock(terraWoodSupportH);
        ModLoader.registerBlock(terraSulfur);
        ModLoader.registerBlock(Block.wood, net.minecraft.src.TFC_Core.ItemCustomWood.class);
        ModLoader.registerBlock(Block.leaves, net.minecraft.src.TFC_Core.ItemCustomLeaves.class);
        ModLoader.registerBlock(Block.sapling, net.minecraft.src.TFC_Core.ItemSapling.class);
        ModLoader.registerBlock(Block.planks, net.minecraft.src.TFC_Core.ItemTerraPlanks.class);

        //Items
        Item.itemsList[terraStoneIgEx.blockID] = new ItemTerraRock(terraStoneIgEx.blockID - 256);
        Item.itemsList[terraStoneSed.blockID] = new ItemTerraRock(terraStoneSed.blockID - 256);
        Item.itemsList[terraStoneIgIn.blockID] = new ItemTerraRock(terraStoneIgIn.blockID - 256);
        Item.itemsList[terraStoneMM.blockID] = new ItemTerraRock(terraStoneMM.blockID - 256);
        Item.itemsList[terraOre.blockID] = new ItemTerraRock(terraOre.blockID - 256);
        Item.itemsList[terraOre2.blockID] = new ItemTerraRock(terraOre2.blockID - 256);
        Item.itemsList[terraOre3.blockID] = new ItemTerraRock(terraOre3.blockID - 256);

        Item.itemsList[terraStoneIgExCobble.blockID] = new ItemTerraRockCobble(terraStoneIgExCobble.blockID - 256);
        Item.itemsList[terraStoneSedCobble.blockID] = new ItemTerraRockCobble(terraStoneSedCobble.blockID - 256);
        Item.itemsList[terraStoneIgInCobble.blockID] = new ItemTerraRockCobble(terraStoneIgInCobble.blockID - 256);
        Item.itemsList[terraStoneMMCobble.blockID] = new ItemTerraRockCobble(terraStoneMMCobble.blockID - 256);

        Item.itemsList[terraStoneIgExBrick.blockID] = new ItemTerraRockCobble(terraStoneIgExBrick.blockID - 256);
        Item.itemsList[terraStoneSedBrick.blockID] = new ItemTerraRockCobble(terraStoneSedBrick.blockID - 256);
        Item.itemsList[terraStoneIgInBrick.blockID] = new ItemTerraRockCobble(terraStoneIgInBrick.blockID - 256);
        Item.itemsList[terraStoneMMBrick.blockID] = new ItemTerraRockCobble(terraStoneMMBrick.blockID - 256);

        Item.itemsList[terraStoneIgExSmooth.blockID] = new ItemTerraRockCobble(terraStoneIgExSmooth.blockID - 256);
        Item.itemsList[terraStoneSedSmooth.blockID] = new ItemTerraRockCobble(terraStoneSedSmooth.blockID - 256);
        Item.itemsList[terraStoneIgInSmooth.blockID] = new ItemTerraRockCobble(terraStoneIgInSmooth.blockID - 256);
        Item.itemsList[terraStoneMMSmooth.blockID] = new ItemTerraRockCobble(terraStoneMMSmooth.blockID - 256);

        Item.itemsList[terraGrass.blockID] = new ItemTerraDirt(terraGrass.blockID - 256);
        Item.itemsList[terraGrass2.blockID] = new ItemTerraDirt(terraGrass2.blockID - 256);
        Item.itemsList[terraDirt.blockID] = new ItemTerraDirt(terraDirt.blockID - 256);
        Item.itemsList[terraDirt2.blockID] = new ItemTerraDirt(terraDirt2.blockID - 256);
        Item.itemsList[terraClay.blockID] = new ItemTerraDirt(terraClay.blockID - 256);
        Item.itemsList[terraClay2.blockID] = new ItemTerraDirt(terraClay2.blockID - 256);
        Item.itemsList[terraClayGrass.blockID] = new ItemTerraDirt(terraClayGrass.blockID - 256);
        Item.itemsList[terraClayGrass2.blockID] = new ItemTerraDirt(terraClayGrass2.blockID - 256);
        Item.itemsList[terraClayGrass2.blockID] = new ItemTerraRockCobble(LooseRock.blockID - 256);

        

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
        RemoveRecipe(new ItemStack(Item.stick,4));
        RemoveRecipe(new ItemStack(Block.planks,4));
        if(TFCSettings.enableVanillaRecipes == false)
        {
            RemoveRecipe(new ItemStack(Item.pickaxeWood,1));
            RemoveRecipe(new ItemStack(Item.axeWood,1));
            RemoveRecipe(new ItemStack(Item.shovelWood,1));
            RemoveRecipe(new ItemStack(Item.hoeWood,1));
            RemoveRecipe(new ItemStack(Item.swordWood,1));
            RemoveRecipe(new ItemStack(Block.stoneOvenIdle,1));
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
        TFC_Core.Generate(world,rand, chunkX, chunkZ);
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
                else if(itemstack.itemID == terraWoodSupportItemV.shiftedIndex || itemstack.itemID == terraWoodSupportItemH.shiftedIndex)
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
        IgInToolMaterial = EnumHelper.addToolMaterial("IgIn", 1, IgInStoneUses, 4F, 1, 5);
        SedToolMaterial = EnumHelper.addToolMaterial("Sed", 1, SedStoneUses, 3F, 1, 5);
        IgExToolMaterial = EnumHelper.addToolMaterial("IgEx", 1, IgExStoneUses, 4F, 1, 5);
        MMToolMaterial = EnumHelper.addToolMaterial("MM", 1, MMStoneUses, 3.5F, 1, 5);

        BismuthToolMaterial = EnumHelper.addToolMaterial("Bismuth", 2, BismuthUses, 4F, 2, 10);
        BismuthBronzeToolMaterial = EnumHelper.addToolMaterial("BismuthBronze", 2, BismuthBronzeUses, 6F, 3, 10);
        BlackBronzeToolMaterial = EnumHelper.addToolMaterial("BlackBronze", 2, BlackBronzeUses, 6F, 4, 10);
        BlackSteelToolMaterial = EnumHelper.addToolMaterial("BlackSteel", 2, BlackSteelUses, 11F, 6, 12);
        BlueSteelToolMaterial = EnumHelper.addToolMaterial("BlueSteel", 3, BlueSteelUses, 14F, 7, 22);
        BronzeToolMaterial = EnumHelper.addToolMaterial("Bronze", 2, BronzeUses, 7F, 4, 13);
        CopperToolMaterial = EnumHelper.addToolMaterial("Copper", 2, CopperUses, 6F, 3, 8);
        IronToolMaterial = EnumHelper.addToolMaterial("Iron", 2, WroughtIronUses, 7F, 5, 10);
        RedSteelToolMaterial = EnumHelper.addToolMaterial("RedSteel", 3, RedSteelUses, 14F, 7, 22);
        RoseGoldToolMaterial = EnumHelper.addToolMaterial("RoseGold", 2, RoseGoldUses, 6F, 4, 20);
        SteelToolMaterial = EnumHelper.addToolMaterial("Steel", 2, SteelUses, 9F, 6, 10);
        TinToolMaterial = EnumHelper.addToolMaterial("Tin", 2, TinUses, 4F, 2, 8);
        ZincToolMaterial = EnumHelper.addToolMaterial("Zinc", 2, ZincUses, 4F, 2, 8);

        terraStoneIgInCobble = new BlockTerraIgInCobble(TFCSettings.getIntFor(config,"block","terraStoneIgInCobble", 198), Material.rock).setHardness(6F).setResistance(10F).setBlockName("IgInRockCobble");
        terraStoneIgIn = new BlockTerraIgIn(TFCSettings.getIntFor(config,"block","TerraIgIn", 209), Material.rock, terraStoneIgInCobble.blockID).setHardness(8F).setResistance(10F).setBlockName("IgInRock");	
        terraStoneIgInSmooth = new BlockTerraIgInSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgInSmooth", 182), 64).setHardness(12F).setResistance(20F).setBlockName("IgInRockSmooth");
        terraStoneIgInBrick = new BlockTerraIgInSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgInBrick", 186), 32).setHardness(12F).setResistance(25F).setBlockName("IgInRockBrick");
        
        terraStoneSedCobble = new BlockTerraSedCobble(TFCSettings.getIntFor(config,"block","terraStoneSedCobble", 199), Material.rock).setHardness(4F).setResistance(10F).setBlockName("SedRockCobble");
        terraStoneSed = new BlockTerraSed(TFCSettings.getIntFor(config,"block","TerraSed", 210), Material.rock, terraStoneSedCobble.blockID).setHardness(6F).setResistance(7F).setBlockName("SedRock");
        terraStoneSedSmooth = new BlockTerraSedSmooth(TFCSettings.getIntFor(config,"block","terraStoneSedSmooth", 183), 64).setHardness(10F).setResistance(20F).setBlockName("SedRockSmooth");
        terraStoneSedBrick = new BlockTerraSedSmooth(TFCSettings.getIntFor(config,"block","terraStoneSedBrick", 187), 32).setHardness(10F).setResistance(25F).setBlockName("SedRockBrick");
        
        terraStoneIgExCobble = new BlockTerraIgExCobble(TFCSettings.getIntFor(config,"block","terraStoneIgExCobble", 200), Material.rock).setHardness(7F).setResistance(10F).setBlockName("IgExRockCobble");
        terraStoneIgEx = new BlockTerraIgEx(TFCSettings.getIntFor(config,"block","TerraIgEx", 211), Material.rock, terraStoneIgExCobble.blockID).setHardness(9F).setResistance(10F).setBlockName("IgExRock");
        terraStoneIgExSmooth = new BlockTerraIgExSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgExSmooth", 184), 64).setHardness(13F).setResistance(20F).setBlockName("IgExRockSmooth");
        terraStoneIgExBrick = new BlockTerraIgExSmooth(TFCSettings.getIntFor(config,"block","terraStoneIgExBrick", 188), 32).setHardness(13F).setResistance(25F).setBlockName("IgExRockBrick");
        
        terraStoneMMCobble = new BlockTerraMMCobble(TFCSettings.getIntFor(config,"block","terraStoneMMCobble", 201), Material.rock).setHardness(4F).setResistance(10F).setBlockName("MMRockCobble");
        terraStoneMM = new BlockTerraMM(TFCSettings.getIntFor(config,"block","TerraMM", 212), Material.rock, terraStoneMMCobble.blockID).setHardness(6F).setResistance(8F).setBlockName("MMRock");
        terraStoneMMSmooth = new BlockTerraMMSmooth(TFCSettings.getIntFor(config,"block","terraStoneMMSmooth", 185), 64).setHardness(10F).setResistance(20F).setBlockName("MMRockSmooth");
        terraStoneMMBrick = new BlockTerraMMSmooth(TFCSettings.getIntFor(config,"block","terraStoneMMBrick", 189), 32).setHardness(10F).setResistance(25F).setBlockName("MMRockBrick");

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

        finiteWater = new BlockFiniteWater(TFCSettings.getIntFor(config,"block","finiteWater", 224)).setHardness(100.0F).setLightOpacity(3).disableStats().setRequiresSelfNotify().setBlockName("finiteWater");
        finiteSaltWater = new BlockFiniteWater(TFCSettings.getIntFor(config,"block","finiteSaltWater", 225)).setHardness(100.0F).setLightOpacity(3).disableStats().setRequiresSelfNotify().setBlockName("finiteSaltWater");

        terraWood = Block.wood;
        terraLeaves = Block.leaves;
        terraSapling = Block.sapling;

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

        terraBismuthIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBismuthIngot",16028)).setItemName("terraBismuthIngot").setIconCoord(0, 3);
        terraBismuthBronzeIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBismuthBronzeIngot",16029)).setItemName("terraBismuthBronzeIngot").setIconCoord(1, 3);
        terraBlackBronzeIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBlackBronzeIngot",16030)).setItemName("terraBlackBronzeIngot").setIconCoord(2, 3);
        terraBlackSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBlackSteelIngot",16031)).setItemName("terraBlackSteelIngot").setIconCoord(3, 3);
        terraBlueSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBlueSteelIngot",16032)).setItemName("terraBlueSteelIngot").setIconCoord(4, 3);
        terraBrassIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBrassIngot",16033)).setItemName("terraBrassIngot").setIconCoord(5, 3);
        terraBronzeIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBronzeIngot",16034)).setItemName("terraBronzeIngot").setIconCoord(6, 3);
        terraCopperIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraCopperIngot",16035)).setItemName("terraCopperIngot").setIconCoord(7, 3);
        terraGoldIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraGoldIngot",16036)).setItemName("terraGoldIngot").setIconCoord(8, 3);
        terraWroughtIronIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraWroughtIronIngot",16037)).setItemName("terraWroughtIronIngot").setIconCoord(9, 3);
        terraLeadIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraLeadIngot",16038)).setItemName("terraLeadIngot").setIconCoord(10, 3);
        terraNickelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraNickelIngot",16039)).setItemName("terraNickelIngot").setIconCoord(0, 4);
        terraPigIronIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraPigIronIngot",16040)).setItemName("terraPigIronIngot").setIconCoord(1, 4);
        terraPlatinumIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraPlatinumIngot",16041)).setItemName("terraPlatinumIngot").setIconCoord(2, 4);
        terraRedSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraRedSteelIngot",16042)).setItemName("terraRedSteelIngot").setIconCoord(3, 4);
        terraRoseGoldIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraRoseGoldIngot",16043)).setItemName("terraRoseGoldIngot").setIconCoord(4, 4);
        terraSilverIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraSilverIngot",16044)).setItemName("terraSilverIngot").setIconCoord(5, 4);
        terraSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraSteelIngot",16045)).setItemName("terraSteelIngot").setIconCoord(6, 4);
        terraSterlingSilverIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraSterlingSilverIngot",16046)).setItemName("terraSterlingSilverIngot").setIconCoord(7, 4);
        terraTinIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraTinIngot",16047)).setItemName("terraTinIngot").setIconCoord(8, 4);
        terraZincIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraZincIngot",16048)).setItemName("terraZincIngot").setIconCoord(9, 4);

        terraBismuthIngot2x = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBismuthIngot2x",16049)).setItemName("terraBismuthIngot2x").setIconCoord(0, 7);
        terraBismuthBronzeIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBismuthBronzeIngot2x",16050)).setItemName("terraBismuthBronzeIngot2x").setIconCoord(1, 7);
        terraBlackBronzeIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBlackBronzeIngot2x",16051)).setItemName("terraBlackBronzeIngot2x").setIconCoord(2, 7);
        terraBlackSteelIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBlackSteelIngot2x",16052)).setItemName("terraBlackSteelIngot2x").setIconCoord(3, 7);
        terraBlueSteelIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBlueSteelIngot2x",16053)).setItemName("terraBlueSteelIngot2x").setIconCoord(4, 7);
        terraBrassIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBrassIngot2x",16054)).setItemName("terraBrassIngot2x").setIconCoord(5, 7);
        terraBronzeIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraBronzeIngot2x",16055)).setItemName("terraBronzeIngot2x").setIconCoord(6, 7);
        terraCopperIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraCopperIngot2x",16056)).setItemName("terraCopperIngot2x").setIconCoord(7, 7);
        terraGoldIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraGoldIngot2x",16057)).setItemName("terraGoldIngot2x").setIconCoord(8, 7);
        terraWroughtIronIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraWroughtIronIngot2x",16058)).setItemName("terraWroughtIronIngot2x").setIconCoord(9, 7);
        terraLeadIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraLeadIngot2x",16059)).setItemName("terraLeadIngot2x").setIconCoord(10, 7);
        terraNickelIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraNickelIngot2x",16060)).setItemName("terraNickelIngot2x").setIconCoord(0, 8);
        terraPigIronIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraPigIronIngot2x",16061)).setItemName("terraPigIronIngot2x").setIconCoord(1, 8);
        terraPlatinumIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraPlatinumIngot2x",16062)).setItemName("terraPlatinumIngot2x").setIconCoord(2, 8);
        terraRedSteelIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraRedSteelIngot2x",16063)).setItemName("terraRedSteelIngot2x").setIconCoord(3, 8);
        terraRoseGoldIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraRoseGoldIngot2x",16064)).setItemName("terraRoseGoldIngot2x").setIconCoord(4, 8);
        terraSilverIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraSilverIngot2x",16065)).setItemName("terraSilverIngot2x").setIconCoord(5, 8);
        terraSteelIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraSteelIngot2x",16066)).setItemName("terraSteelIngot2x").setIconCoord(6, 8);
        terraSterlingSilverIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraSterlingSilverIngot2x",16067)).setItemName("terraSterlingSilverIngot2x").setIconCoord(7, 8);
        terraTinIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraTinIngot2x",16068)).setItemName("terraTinIngot2x").setIconCoord(8, 8);
        terraZincIngot2x  = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraZincIngot2x",16069)).setItemName("terraZincIngot2x").setIconCoord(9, 8);

        terraSulfurPowder = new ItemTerra(TFCSettings.getIntFor(config,"item","terraSulfurPowder",16070)).setTexturePath("/bioxx/terrasprites.png").setItemName("SulfurPowder").setIconCoord(1, 0);
        terraSaltpeterPowder = new ItemTerra(TFCSettings.getIntFor(config,"item","terraSaltpeterPowder",16071)).setTexturePath("/bioxx/terrasprites.png").setItemName("SaltpeterPowder").setIconCoord(0, 0);

        terraGemRuby = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemRuby",16080)).setItemName("Ruby").setIconCoord(11, 3);
        terraGemSapphire = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemSapphire",16081)).setItemName("Sapphire").setIconCoord(11, 4);
        terraGemEmerald = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemEmerald",16082)).setItemName("Emerald").setIconCoord(11, 5);
        terraGemTopaz = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemTopaz",16083)).setItemName("Topaz").setIconCoord(11, 6);
        terraGemTourmaline = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemTourmaline",16084)).setItemName("Tourmaline").setIconCoord(11, 7);
        terraGemJade = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemJade",16085)).setItemName("Jade").setIconCoord(11, 8);
        terraGemBeryl = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemBeryl",16086)).setItemName("Beryl").setIconCoord(11, 9);
        terraGemAgate = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemAgate",16087)).setItemName("Agate").setIconCoord(11, 10);
        terraGemOpal = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemOpal",16088)).setItemName("Opal").setIconCoord(11, 11);
        terraGemGarnet = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemGarnet",16089)).setItemName("Garnet").setIconCoord(11, 12);
        terraGemJasper = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemJasper",16090)).setItemName("Jasper").setIconCoord(11, 13);
        terraGemAmethyst = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemAmethyst",16091)).setItemName("Amethyst").setIconCoord(11, 14);
        terraGemDiamond = new ItemTerraGem(TFCSettings.getIntFor(config,"item","terraGemDiamond",16092)).setItemName("Diamond").setIconCoord(11, 15);

        //Tools
        terraIgInPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraIgInPick",16100),IgInToolMaterial).setItemName("IgIn Stone Pick").setMaxDamage(IgInStoneUses).setIconCoord(0, 3);
        terraIgInShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraIgInShovel",16101),IgInToolMaterial).setItemName("IgIn Stone Shovel").setMaxDamage(IgInStoneUses).setIconCoord(0, 4);
        terraIgInAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terraIgInAxe",16102),IgInToolMaterial).setItemName("IgIn Stone Axe").setMaxDamage(IgInStoneUses).setIconCoord(0, 5);
        terraIgInHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraIgInHoe",16103),IgInToolMaterial).setItemName("IgIn Stone Hoe").setMaxDamage(IgInStoneUses).setIconCoord(0, 6);
        terraSedPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraSedPick",16104),SedToolMaterial).setItemName("Sed Stone Pick").setMaxDamage(SedStoneUses).setIconCoord(0, 3);
        terraSedShovel= new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraSedShovel",16105),SedToolMaterial).setItemName("Sed Stone Shovel").setMaxDamage(SedStoneUses).setIconCoord(0, 4);
        terraSedAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terraSedAxe",16106),SedToolMaterial).setItemName("Sed Stone Axe").setMaxDamage(SedStoneUses).setIconCoord(0, 5);
        terraSedHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraSedHoe",16107),SedToolMaterial).setItemName("Sed Stone Hoe").setMaxDamage(SedStoneUses).setIconCoord(0, 6);
        terraIgExPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraIgExPick",16108),IgExToolMaterial).setItemName("IgEx Stone Pick").setMaxDamage(IgExStoneUses).setIconCoord(0, 3);
        terraIgExShovel= new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraIgExShovel",16109),IgExToolMaterial).setItemName("IgEx Stone Shovel").setMaxDamage(IgExStoneUses).setIconCoord(0, 4);
        terraIgExAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terraIgExAxe",16110),IgExToolMaterial).setItemName("IgEx Stone Axe").setMaxDamage(IgExStoneUses).setIconCoord(0, 5);
        terraIgExHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraIgExHoe",16111),IgExToolMaterial).setItemName("IgEx Stone Hoe").setMaxDamage(IgExStoneUses).setIconCoord(0, 6);
        terraMMPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraMMPick",16112),MMToolMaterial).setItemName("MM Stone Pick").setMaxDamage(MMStoneUses).setIconCoord(0, 3);
        terraMMShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraMMShovel",16113),MMToolMaterial).setItemName("MM Stone Shovel").setMaxDamage(MMStoneUses).setIconCoord(0, 4);
        terraMMAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terraMMAxe",16114),MMToolMaterial).setItemName("MM Stone Axe").setMaxDamage(MMStoneUses).setIconCoord(0, 5);
        terraMMHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraMMHoe",16115),MMToolMaterial).setItemName("MM Stone Hoe").setMaxDamage(MMStoneUses).setIconCoord(0, 6);

        terraBismuthPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraBismuthPick",16116),BismuthToolMaterial).setItemName("Bismuth Pick").setMaxDamage(BismuthUses).setIconCoord(1, 3);
        terraBismuthShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraBismuthShovel",16117),BismuthToolMaterial).setItemName("Bismuth Shovel").setMaxDamage(BismuthUses).setIconCoord(1, 4);
        terraBismuthAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranBismuthAxe",16118),BismuthToolMaterial).setItemName("Bismuth Axe").setMaxDamage(BismuthUses).setIconCoord(1, 5);
        terraBismuthHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraBismuthHoe",16119),BismuthToolMaterial).setItemName("Bismuth Hoe").setMaxDamage(BismuthUses).setIconCoord(1, 6);

        terraBismuthBronzePick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraBismuthBronzePick",16120),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Pick").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 3);
        terraBismuthBronzeShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraBismuthBronzeShovel",16121),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Shovel").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 4);
        terraBismuthBronzeAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranBismuthBronzeAxe",16122),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Axe").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 5);
        terraBismuthBronzeHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraBismuthBronzeHoe",16123),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Hoe").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 6);

        terraBlackBronzePick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraBlackBronzePick",16124),BlackBronzeToolMaterial).setItemName("Black Bronze Pick").setMaxDamage(BlackBronzeUses).setIconCoord(3, 3);
        terraBlackBronzeShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraBlackBronzeShovel",16125),BlackBronzeToolMaterial).setItemName("Black Bronze Shovel").setMaxDamage(BlackBronzeUses).setIconCoord(3, 4);
        terraBlackBronzeAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranBlackBronzeAxe",16126),BlackBronzeToolMaterial).setItemName("Black Bronze Axe").setMaxDamage(BlackBronzeUses).setIconCoord(3, 5);
        terraBlackBronzeHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraBlackBronzeHoe",16127),BlackBronzeToolMaterial).setItemName("Black Bronze Hoe").setMaxDamage(BlackBronzeUses).setIconCoord(3, 6);

        terraBlackSteelPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraBlackSteelPick",16128),BlackSteelToolMaterial).setItemName("Black Steel Pick").setMaxDamage(BlackSteelUses).setIconCoord(4, 3);
        terraBlackSteelShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraBlackSteelShovel",16129),BlackSteelToolMaterial).setItemName("Black Steel Shovel").setMaxDamage(BlackSteelUses).setIconCoord(4, 4);
        terraBlackSteelAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranBlackSteelAxe",16130),BlackSteelToolMaterial).setItemName("Black Steel Axe").setMaxDamage(BlackSteelUses).setIconCoord(4, 5);
        terraBlackSteelHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraBlackSteelHoe",16131),BlackSteelToolMaterial).setItemName("Black Steel Hoe").setMaxDamage(BlackSteelUses).setIconCoord(4, 6);

        terraBlueSteelPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraBlueSteelPick",16132),BlueSteelToolMaterial).setItemName("Blue Steel Pick").setMaxDamage(BlueSteelUses).setIconCoord(5, 3);
        terraBlueSteelShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraBlueSteelShovel",16133),BlueSteelToolMaterial).setItemName("Blue Steel Shovel").setMaxDamage(BlueSteelUses).setIconCoord(5, 4);
        terraBlueSteelAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranBlueSteelAxe",16134),BlueSteelToolMaterial).setItemName("Blue Steel Axe").setMaxDamage(BlueSteelUses).setIconCoord(5, 5);
        terraBlueSteelHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraBlueSteelHoe",16135),BlueSteelToolMaterial).setItemName("Blue Steel Hoe").setMaxDamage(BlueSteelUses).setIconCoord(5, 6);

        terraBronzePick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraBronzePick",16136),BronzeToolMaterial).setItemName("Bronze Pick").setMaxDamage(BronzeUses).setIconCoord(6,3);
        terraBronzeShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraBronzeShovel",16137),BronzeToolMaterial).setItemName("Bronze Shovel").setMaxDamage(BronzeUses).setIconCoord(6, 4);
        terraBronzeAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranBronzeAxe",16138),BronzeToolMaterial).setItemName("Bronze Axe").setMaxDamage(BronzeUses).setIconCoord(6, 5);
        terraBronzeHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraBronzeHoe",16139),BronzeToolMaterial).setItemName("Bronze Hoe").setMaxDamage(BronzeUses).setIconCoord(6, 6);

        terraCopperPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraCopperPick",16140),CopperToolMaterial).setItemName("Copper Pick").setMaxDamage(CopperUses).setIconCoord(7, 3);
        terraCopperShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraCopperShovel",16141),CopperToolMaterial).setItemName("Copper Shovel").setMaxDamage(CopperUses).setIconCoord(7, 4);
        terraCopperAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranCopperAxe",16142),CopperToolMaterial).setItemName("Copper Axe").setMaxDamage(CopperUses).setIconCoord(7, 5);
        terraCopperHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraCopperHoe",16143),CopperToolMaterial).setItemName("Copper Hoe").setMaxDamage(CopperUses).setIconCoord(7, 6);

        terraWroughtIronPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraWroughtIronPick",16148),IronToolMaterial).setItemName("Wrought Iron Pick").setMaxDamage(WroughtIronUses).setIconCoord(8, 3);
        terraWroughtIronShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraWroughtIronShovel",16149),IronToolMaterial).setItemName("Wrought Iron Shovel").setMaxDamage(WroughtIronUses).setIconCoord(8, 4);
        terraWroughtIronAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranWroughtIronAxe",16150),IronToolMaterial).setItemName("Wrought Iron Axe").setMaxDamage(WroughtIronUses).setIconCoord(8, 5);
        terraWroughtIronHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraWroughtIronHoe",16151),IronToolMaterial).setItemName("Wrought Iron Hoe").setMaxDamage(WroughtIronUses).setIconCoord(8, 6);

        terraRedSteelPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraRedSteelPick",16168),RedSteelToolMaterial).setItemName("Red Steel Pick").setMaxDamage(RedSteelUses).setIconCoord(9, 3);
        terraRedSteelShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraRedSteelShovel",16169),RedSteelToolMaterial).setItemName("Red Steel Shovel").setMaxDamage(RedSteelUses).setIconCoord(9, 4);
        terraRedSteelAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranRedSteelAxe",16170),RedSteelToolMaterial).setItemName("Red Steel Axe").setMaxDamage(RedSteelUses).setIconCoord(9, 5);
        terraRedSteelHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraRedSteelHoe",16171),RedSteelToolMaterial).setItemName("Red Steel Hoe").setMaxDamage(RedSteelUses).setIconCoord(9, 6);

        terraRoseGoldPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraRoseGoldPick",16172),RoseGoldToolMaterial).setItemName("Rose Gold Pick").setMaxDamage(RoseGoldUses).setIconCoord(10, 3);
        terraRoseGoldShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraRoseGoldShovel",16173),RoseGoldToolMaterial).setItemName("Rose Gold Shovel").setMaxDamage(RoseGoldUses).setIconCoord(10, 4);
        terraRoseGoldAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranRoseGoldAxe",16174),RoseGoldToolMaterial).setItemName("Rose Gold Axe").setMaxDamage(RoseGoldUses).setIconCoord(10, 5);
        terraRoseGoldHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraRoseGoldHoe",16175),RoseGoldToolMaterial).setItemName("Rose Gold Hoe").setMaxDamage(RoseGoldUses).setIconCoord(10, 6);

        terraSteelPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraSteelPick",16180),SteelToolMaterial).setItemName("Steel Pick").setMaxDamage(SteelUses).setIconCoord(11, 3);
        terraSteelShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraSteelShovel",16181),SteelToolMaterial).setItemName("Steel Shovel").setMaxDamage(SteelUses).setIconCoord(11, 4);
        terraSteelAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranSteelAxe",16182),SteelToolMaterial).setItemName("Steel Axe").setMaxDamage(SteelUses).setIconCoord(11, 5);
        terraSteelHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraSteelHoe",16183),SteelToolMaterial).setItemName("Steel Hoe").setMaxDamage(SteelUses).setIconCoord(11, 6);

        terraTinPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraTinPick",16188),TinToolMaterial).setItemName("Tin Pick").setMaxDamage(TinUses).setIconCoord(12, 3);
        terraTinShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraTinShovel",16189),TinToolMaterial).setItemName("Tin Shovel").setMaxDamage(TinUses).setIconCoord(12, 4);
        terraTinAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranTinAxe",16190),TinToolMaterial).setItemName("Tin Axe").setMaxDamage(TinUses).setIconCoord(12, 5);
        terraTinHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraTinHoe",16191),TinToolMaterial).setItemName("Tin Hoe").setMaxDamage(TinUses).setIconCoord(12, 6);

        terraZincPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","terraZincPick",16192),ZincToolMaterial).setItemName("Zinc Pick").setMaxDamage(ZincUses).setIconCoord(13, 3);
        terraZincShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","terraZincShovel",16193),ZincToolMaterial).setItemName("Zinc Shovel").setMaxDamage(ZincUses).setIconCoord(13, 4);
        terraZincAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","terranZincAxe",16194),ZincToolMaterial).setItemName("Zinc Axe").setMaxDamage(ZincUses).setIconCoord(13, 5);
        terraZincHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","terraZincHoe",16195),ZincToolMaterial).setItemName("Zinc Hoe").setMaxDamage(ZincUses).setIconCoord(13, 6);

        //chisels
        BismuthChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","BismuthChisel",16226),BismuthToolMaterial).setItemName("Bismuth Chisel").setMaxDamage(BismuthUses).setIconCoord(1, 7);
        BismuthBronzeChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","BismuthBronzeChisel",16227),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Chisel").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 7);
        BlackBronzeChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","BlackBronzeChisel",16228),BlackBronzeToolMaterial).setItemName("Black Bronze Chisel").setMaxDamage(BlackBronzeUses).setIconCoord(3, 7);
        BlackSteelChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","BlackSteelChisel",16230),BlackSteelToolMaterial).setItemName("Black Steel Chisel").setMaxDamage(BlackSteelUses).setIconCoord(4, 7);
        BlueSteelChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","BlueSteelChisel",16231),BlueSteelToolMaterial).setItemName("Blue Steel Chisel").setMaxDamage(BlueSteelUses).setIconCoord(5, 7);
        BronzeChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","BronzeChisel",16232),BronzeToolMaterial).setItemName("Bronze Chisel").setMaxDamage(BronzeUses).setIconCoord(6, 7);
        CopperChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","CopperChisel",16233),CopperToolMaterial).setItemName("Copper Chisel").setMaxDamage(CopperUses).setIconCoord(7, 7);
        WroughtIronChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","WroughtIronChisel",16234),IronToolMaterial).setItemName("Wrought Iron Chisel").setMaxDamage(WroughtIronUses).setIconCoord(8, 7);
        RedSteelChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","RedSteelChisel",16235),RedSteelToolMaterial).setItemName("Red Steel Chisel").setMaxDamage(RedSteelUses).setIconCoord(9, 7);
        RoseGoldChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","RoseGoldChisel",16236),RoseGoldToolMaterial).setItemName("Rose Gold Chisel").setMaxDamage(RoseGoldUses).setIconCoord(10, 7);
        SteelChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","SteelChisel",16237),SteelToolMaterial).setItemName("Steel Chisel").setMaxDamage(SteelUses).setIconCoord(11, 7);
        TinChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","TinChisel",16238),TinToolMaterial).setItemName("Tin Chisel").setMaxDamage(TinUses).setIconCoord(12, 7);
        ZincChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","ZincChisel",16239),ZincToolMaterial).setItemName("Zinc Chisel").setMaxDamage(ZincUses).setIconCoord(13, 7);
        StoneChisel = new ItemChisel(TFCSettings.getIntFor(config,"item","StoneChisel",16240),IgInToolMaterial).setItemName("Stone Chisel").setMaxDamage(IgInStoneUses).setIconCoord(0, 7);

        IgInStoneSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","IgInStoneSword",16241),IgInToolMaterial).setItemName("Stone Sword").setMaxDamage(IgInStoneUses).setIconCoord(0, 13);
        IgExStoneSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","IgExStoneSword",16242),IgExToolMaterial).setItemName("Stone Sword").setMaxDamage(IgExStoneUses).setIconCoord(0, 13);
        SedStoneSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","SedStoneSword",16243),SedToolMaterial).setItemName("Stone Sword").setMaxDamage(SedStoneUses).setIconCoord(0, 13);
        MMStoneSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","MMStoneSword",16244),MMToolMaterial).setItemName("Stone Sword").setMaxDamage(MMStoneUses).setIconCoord(0, 13);
        BismuthSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BismuthSword",16245),BismuthToolMaterial).setItemName("Bismuth Sword").setMaxDamage(BismuthUses).setIconCoord(1, 13);
        BismuthBronzeSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BismuthBronzeSword",16246),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Sword").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 13);
        BlackBronzeSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BlackBronzeSword",16247),BlackBronzeToolMaterial).setItemName("Black Bronze Sword").setMaxDamage(BlackBronzeUses).setIconCoord(3, 13);
        BlackSteelSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BlackSteelSword",16248),BlackSteelToolMaterial).setItemName("Black Steel Sword").setMaxDamage(BlackSteelUses).setIconCoord(4, 13);
        BlueSteelSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BlueSteelSword",16249),BlueSteelToolMaterial).setItemName("Blue Steel Sword").setMaxDamage(BlueSteelUses).setIconCoord(5, 13);
        BronzeSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BronzeSword",16250),BronzeToolMaterial).setItemName("Bronze Sword").setMaxDamage(BronzeUses).setIconCoord(6, 13);
        CopperSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","CopperSword",16251),CopperToolMaterial).setItemName("Copper Sword").setMaxDamage(CopperUses).setIconCoord(7, 13);
        WroughtIronSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","WroughtIronSword",16252),IronToolMaterial).setItemName("Wrought Iron Sword").setMaxDamage(WroughtIronUses).setIconCoord(8, 13);
        RedSteelSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","RedSteelSword",16253),RedSteelToolMaterial).setItemName("Red Steel Sword").setMaxDamage(RedSteelUses).setIconCoord(9, 13);
        RoseGoldSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","RoseGoldSword",16254),RoseGoldToolMaterial).setItemName("Rose Gold Sword").setMaxDamage(RoseGoldUses).setIconCoord(10, 13);
        SteelSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","SteelSword",16255),SteelToolMaterial).setItemName("Steel Sword").setMaxDamage(SteelUses).setIconCoord(11, 13);
        TinSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","TinSword",16256),TinToolMaterial).setItemName("Tin Sword").setMaxDamage(TinUses).setIconCoord(12, 13);
        ZincSword = new ItemTerraSword(TFCSettings.getIntFor(config,"item","ZincSword",16257),ZincToolMaterial).setItemName("Zinc Sword").setMaxDamage(ZincUses).setIconCoord(13, 13);

        IgInStoneMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","IgInStoneMace",16258),IgInToolMaterial).setItemName("Stone Mace").setMaxDamage(IgInStoneUses).setIconCoord(0, 12);
        IgExStoneMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","IgExStoneMace",16259),IgExToolMaterial).setItemName("Stone Mace").setMaxDamage(IgExStoneUses).setIconCoord(0, 12);
        SedStoneMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","SedStoneMace",16260),SedToolMaterial).setItemName("Stone Mace").setMaxDamage(SedStoneUses).setIconCoord(0, 12);
        MMStoneMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","MMStoneMace",16261),MMToolMaterial).setItemName("Stone Mace").setMaxDamage(MMStoneUses).setIconCoord(0, 12);
        BismuthMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BismuthMace",16262),BismuthToolMaterial).setItemName("Bismuth Mace").setMaxDamage(BismuthUses).setIconCoord(1, 12);
        BismuthBronzeMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BismuthBronzeMace",16263),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Mace").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 12);
        BlackBronzeMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BlackBronzeMace",16264),BlackBronzeToolMaterial).setItemName("Black Bronze Mace").setMaxDamage(BlackBronzeUses).setIconCoord(3, 12);
        BlackSteelMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BlackSteelMace",16265),BlackSteelToolMaterial).setItemName("Black Steel Mace").setMaxDamage(BlackSteelUses).setIconCoord(4, 12);
        BlueSteelMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BlueSteelMace",16266),BlueSteelToolMaterial).setItemName("Blue Steel Mace").setMaxDamage(BlueSteelUses).setIconCoord(5, 12);
        BronzeMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","BronzeMace",16267),BronzeToolMaterial).setItemName("Bronze Mace").setMaxDamage(BronzeUses).setIconCoord(6, 12);
        CopperMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","CopperMace",16268),CopperToolMaterial).setItemName("Copper Mace").setMaxDamage(CopperUses).setIconCoord(7, 12);
        WroughtIronMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","WroughtIronMace",16269),IronToolMaterial).setItemName("Wrought Iron Mace").setMaxDamage(WroughtIronUses).setIconCoord(8, 12);
        RedSteelMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","RedSteelMace",16270),RedSteelToolMaterial).setItemName("Red Steel Mace").setMaxDamage(RedSteelUses).setIconCoord(9, 12);
        RoseGoldMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","RoseGoldMace",16271),RoseGoldToolMaterial).setItemName("Rose Gold Mace").setMaxDamage(RoseGoldUses).setIconCoord(10, 12);
        SteelMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","SteelMace",16272),SteelToolMaterial).setItemName("Steel Mace").setMaxDamage(SteelUses).setIconCoord(11, 12);
        TinMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","TinMace",16273),TinToolMaterial).setItemName("Tin Mace").setMaxDamage(TinUses).setIconCoord(12, 12);
        ZincMace = new ItemTerraSword(TFCSettings.getIntFor(config,"item","ZincMace",16274),ZincToolMaterial).setItemName("Zinc Mace").setMaxDamage(ZincUses).setIconCoord(13, 12);

        BismuthSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","BismuthSaw",16275),BismuthToolMaterial).setItemName("Bismuth Saw").setMaxDamage(BismuthUses).setIconCoord(1, 8);
        BismuthBronzeSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","BismuthBronzeSaw",16276),BismuthBronzeToolMaterial).setItemName("Bismuth Bronze Saw").setMaxDamage(BismuthBronzeUses).setIconCoord(2, 8);
        BlackBronzeSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","BlackBronzeSaw",16277),BlackBronzeToolMaterial).setItemName("Black Bronze Saw").setMaxDamage(BlackBronzeUses).setIconCoord(3, 8);
        BlackSteelSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","BlackSteelSaw",16278),BlackSteelToolMaterial).setItemName("Black Steel Saw").setMaxDamage(BlackSteelUses).setIconCoord(4, 8);
        BlueSteelSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","BlueSteelSaw",16279),BlueSteelToolMaterial).setItemName("Blue Steel Saw").setMaxDamage(BlueSteelUses).setIconCoord(5, 8);
        BronzeSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","BronzeSaw",16280),BronzeToolMaterial).setItemName("Bronze Saw").setMaxDamage(BronzeUses).setIconCoord(6, 8);
        CopperSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","CopperSaw",16281),CopperToolMaterial).setItemName("Copper Saw").setMaxDamage(CopperUses).setIconCoord(7, 8);
        WroughtIronSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","WroughtIronSaw",16282),IronToolMaterial).setItemName("Wrought Iron Saw").setMaxDamage(WroughtIronUses).setIconCoord(8, 8);
        RedSteelSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","RedSteelSaw",16283),RedSteelToolMaterial).setItemName("Red Steel Saw").setMaxDamage(RedSteelUses).setIconCoord(9, 8);
        RoseGoldSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","RoseGoldSaw",16284),RoseGoldToolMaterial).setItemName("Rose Gold Saw").setMaxDamage(RoseGoldUses).setIconCoord(10, 8);
        SteelSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","SteelSaw",16285),SteelToolMaterial).setItemName("Steel Saw").setMaxDamage(SteelUses).setIconCoord(11, 8);
        TinSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","TinSaw",16286),TinToolMaterial).setItemName("Tin Saw").setMaxDamage(TinUses).setIconCoord(12, 8);
        ZincSaw = new ItemCustomSaw(TFCSettings.getIntFor(config,"item","ZincSaw",16287),ZincToolMaterial).setItemName("Zinc Saw").setMaxDamage(ZincUses).setIconCoord(13, 8);

        terraHCBlackSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraHCBlackSteelIngot",16290)).setItemName("terraHCBlackSteelIngot").setIconCoord(3, 3);
        terraWeakBlueSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraWeakBlueSteelIngot",16291)).setItemName("terraWeakBlueSteelIngot").setIconCoord(4, 3);
        terraWeakRedSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraWeakRedSteelIngot",16292)).setItemName("terraWeakRedSteelIngot").setIconCoord(3, 4);
        terraWeakSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraWeakSteelIngot",16293)).setItemName("terraWeakSteelIngot").setIconCoord(6, 4);
        terraHCBlueSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraHCBlueSteelIngot",16294)).setItemName("terraHCBlueSteelIngot").setIconCoord(4, 3);
        terraHCRedSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraHCRedSteelIngot",16295)).setItemName("terraHCRedSteelIngot").setIconCoord(3, 4);
        terraHCSteelIngot = new ItemTerraIngot(TFCSettings.getIntFor(config,"item","terraHCSteelIngot",16296)).setItemName("terraHCSteelIngot").setIconCoord(6, 4);

        OreChunk = new ItemTerraSmallOre(TFCSettings.getIntFor(config,"item","OreChunk",16297)).setItemName("Ore").setIconCoord(0, 3);
        Logs = new ItemTerraLogs(TFCSettings.getIntFor(config,"item","Logs",16298)).setItemName("Log").setIconCoord(0, 2);
        FlintPaxel = new ItemCustomPaxel(TFCSettings.getIntFor(config,"item","flintPaxel",16299)).setItemName("flintPaxel").setIconCoord(10, 0);

        terraWoodSupportItemV = new ItemTerraWoodSupport(TFCSettings.getIntFor(config,"item","terraWoodSupportItemV", 16300), true).setItemName("terraWoodSupportItemV").setIconCoord(0, 0);
        terraWoodSupportItemH = new ItemTerraWoodSupport(TFCSettings.getIntFor(config,"item","terraWoodSupportItemH", 16301), false).setItemName("terraWoodSupportItemH").setIconCoord(0, 1);
        boneIgInPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","boneIgInPick",16302),IgInToolMaterial).setItemName("Bone IgIn Stone Pick").setMaxDamage(IgInStoneUses).setIconCoord(14, 3);
        boneIgInShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","boneIgInShovel",16303),IgInToolMaterial).setItemName("Bone IgIn Stone Shovel").setMaxDamage(IgInStoneUses).setIconCoord(14, 4);
        boneIgInAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","boneIgInAxe",16304),IgInToolMaterial).setItemName("Bone IgIn Stone Axe").setMaxDamage(IgInStoneUses).setIconCoord(14, 5);
        boneIgInHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","boneIgInHoe",16305),IgInToolMaterial).setItemName("Bone IgIn Stone Hoe").setMaxDamage(IgInStoneUses).setIconCoord(14, 6);
        boneSedPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","boneSedPick",16306),SedToolMaterial).setItemName("Bone Sed Stone Pick").setMaxDamage(SedStoneUses).setIconCoord(14, 3);
        boneSedShovel= new ItemCustomShovel(TFCSettings.getIntFor(config,"item","boneSedShovel",16307),SedToolMaterial).setItemName("Bone Sed Stone Shovel").setMaxDamage(SedStoneUses).setIconCoord(14, 4);
        boneSedAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","boneSedAxe",16308),SedToolMaterial).setItemName("Bone Sed Stone Axe").setMaxDamage(SedStoneUses).setIconCoord(14, 5);
        boneSedHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","boneSedHoe",16309),SedToolMaterial).setItemName("Bone Sed Stone Hoe").setMaxDamage(SedStoneUses).setIconCoord(14, 6);
        boneIgExPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","boneIgExPick",16310),IgExToolMaterial).setItemName("Bone IgEx Stone Pick").setMaxDamage(IgExStoneUses).setIconCoord(14, 3);
        boneIgExShovel= new ItemCustomShovel(TFCSettings.getIntFor(config,"item","boneIgExShovel",16311),IgExToolMaterial).setItemName("Bone IgEx Stone Shovel").setMaxDamage(IgExStoneUses).setIconCoord(14, 4);
        boneIgExAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","boneIgExAxe",16312),IgExToolMaterial).setItemName("Bone IgEx Stone Axe").setMaxDamage(IgExStoneUses).setIconCoord(14, 5);
        boneIgExHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","boneIgExHoe",16313),IgExToolMaterial).setItemName("Bone IgEx Stone Hoe").setMaxDamage(IgExStoneUses).setIconCoord(14, 6);
        boneMMPick = new ItemCustomPickaxe(TFCSettings.getIntFor(config,"item","boneMMPick",16314),MMToolMaterial).setItemName("Bone MM Stone Pick").setMaxDamage(MMStoneUses).setIconCoord(14, 3);
        boneMMShovel = new ItemCustomShovel(TFCSettings.getIntFor(config,"item","boneMMShovel",16315),MMToolMaterial).setItemName("Bone MM Stone Shovel").setMaxDamage(MMStoneUses).setIconCoord(14, 4);
        boneMMAxe = new ItemCustomAxe(TFCSettings.getIntFor(config,"item","boneMMAxe",16316),MMToolMaterial).setItemName("Bone MM Stone Axe").setMaxDamage(MMStoneUses).setIconCoord(14, 5);
        boneMMHoe = new ItemCustomHoe(TFCSettings.getIntFor(config,"item","boneMMHoe",16317),MMToolMaterial).setItemName("Bone MM Stone Hoe").setMaxDamage(MMStoneUses).setIconCoord(14, 6);
        
        //Javelin = new ItemTerraJavelin(TFCSettings.getIntFor(config,"item","javelin",16318)).setItemName("javelin").setIconCoord(12, 0);
        bucket = new ItemTerraBucket(TFCSettings.getIntFor(config,"item","bucket",16319)).setItemName("bucket").setIconCoord(0, 14);

        SeedsWheat = new ItemCustomSeeds(TFCSettings.getIntFor(config,"item","SeedsWheat",18000),Block.crops.blockID,tilledSoil.blockID,tilledSoil2.blockID).setItemName("SeedsWheat").setIconCoord(9, 0);



        TFC_Core.Axes = new Item[]{mod_TFC_Core.terraSedAxe,mod_TFC_Core.terraIgInAxe,mod_TFC_Core.terraIgExAxe,mod_TFC_Core.terraMMAxe,
                mod_TFC_Core.terraBismuthAxe,mod_TFC_Core.terraBismuthBronzeAxe,mod_TFC_Core.terraBlackBronzeAxe,
                mod_TFC_Core.terraBlackSteelAxe,mod_TFC_Core.terraBlueSteelAxe,mod_TFC_Core.terraBronzeAxe,mod_TFC_Core.terraCopperAxe,
                mod_TFC_Core.terraWroughtIronAxe,mod_TFC_Core.terraRedSteelAxe,mod_TFC_Core.terraRoseGoldAxe,mod_TFC_Core.terraSteelAxe,
                mod_TFC_Core.terraTinAxe,mod_TFC_Core.terraZincAxe,
                mod_TFC_Core.boneSedAxe,mod_TFC_Core.boneIgInAxe,mod_TFC_Core.boneIgExAxe,mod_TFC_Core.boneMMAxe};
        TFC_Core.Chisels = new Item[]{mod_TFC_Core.BismuthChisel,mod_TFC_Core.BismuthBronzeChisel,mod_TFC_Core.BlackBronzeChisel,
            mod_TFC_Core.BlackSteelChisel,mod_TFC_Core.BlueSteelChisel,mod_TFC_Core.BronzeChisel,mod_TFC_Core.CopperChisel,
            mod_TFC_Core.WroughtIronChisel,mod_TFC_Core.RedSteelChisel,mod_TFC_Core.RoseGoldChisel,mod_TFC_Core.SteelChisel,
            mod_TFC_Core.TinChisel,mod_TFC_Core.ZincChisel};
        TFC_Core.Saws = new Item[]{mod_TFC_Core.BismuthSaw,mod_TFC_Core.BismuthBronzeSaw,mod_TFC_Core.BlackBronzeSaw,
                mod_TFC_Core.BlackSteelSaw,mod_TFC_Core.BlueSteelSaw,mod_TFC_Core.BronzeSaw,mod_TFC_Core.CopperSaw,
                mod_TFC_Core.WroughtIronSaw,mod_TFC_Core.RedSteelSaw,mod_TFC_Core.RoseGoldSaw,mod_TFC_Core.SteelSaw,
                mod_TFC_Core.TinSaw,mod_TFC_Core.ZincSaw};
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
        MinecraftForge.setToolClass(terraIgInPick, "pickaxe", 1);
        MinecraftForge.setToolClass(terraIgExPick, "pickaxe", 1);
        MinecraftForge.setToolClass(terraSedPick, "pickaxe", 1);
        MinecraftForge.setToolClass(terraMMPick, "pickaxe", 1);
        MinecraftForge.setToolClass(boneIgInPick, "pickaxe", 1);
        MinecraftForge.setToolClass(boneIgExPick, "pickaxe", 1);
        MinecraftForge.setToolClass(boneSedPick, "pickaxe", 1);
        MinecraftForge.setToolClass(boneMMPick, "pickaxe", 1);
        MinecraftForge.setToolClass(terraBismuthPick, "pickaxe", 1);
        MinecraftForge.setToolClass(terraBismuthBronzePick, "pickaxe", 2);
        MinecraftForge.setToolClass(terraBlackBronzePick, "pickaxe", 2);
        MinecraftForge.setToolClass(terraBlackSteelPick, "pickaxe", 5);
        MinecraftForge.setToolClass(terraBlueSteelPick, "pickaxe", 6);
        MinecraftForge.setToolClass(terraBronzePick, "pickaxe", 2);
        MinecraftForge.setToolClass(terraCopperPick, "pickaxe", 1);
        MinecraftForge.setToolClass(terraWroughtIronPick, "pickaxe", 3);
        MinecraftForge.setToolClass(terraRedSteelPick, "pickaxe", 6);
        MinecraftForge.setToolClass(terraRoseGoldPick, "pickaxe", 2);
        MinecraftForge.setToolClass(terraSteelPick, "pickaxe", 4);
        MinecraftForge.setToolClass(terraTinPick, "pickaxe", 1);
        MinecraftForge.setToolClass(terraZincPick, "pickaxe", 1);
        //shovels
        MinecraftForge.setToolClass(terraIgInShovel, "shovel", 1);
        MinecraftForge.setToolClass(terraIgExShovel, "shovel", 1);
        MinecraftForge.setToolClass(terraSedShovel, "shovel", 1);
        MinecraftForge.setToolClass(terraMMShovel, "shovel", 1);
        MinecraftForge.setToolClass(boneIgInShovel, "shovel", 1);
        MinecraftForge.setToolClass(boneIgExShovel, "shovel", 1);
        MinecraftForge.setToolClass(boneSedShovel, "shovel", 1);
        MinecraftForge.setToolClass(boneMMShovel, "shovel", 1);
        MinecraftForge.setToolClass(terraBismuthShovel, "shovel", 1);
        MinecraftForge.setToolClass(terraBismuthBronzeShovel, "shovel", 2);
        MinecraftForge.setToolClass(terraBlackBronzeShovel, "shovel", 2);
        MinecraftForge.setToolClass(terraBlackSteelShovel, "shovel", 5);
        MinecraftForge.setToolClass(terraBlueSteelShovel, "shovel", 6);
        MinecraftForge.setToolClass(terraBronzeShovel, "shovel", 2);
        MinecraftForge.setToolClass(terraCopperShovel, "shovel", 1);
        MinecraftForge.setToolClass(terraWroughtIronShovel, "shovel", 3);
        MinecraftForge.setToolClass(terraRedSteelShovel, "shovel", 6);
        MinecraftForge.setToolClass(terraRoseGoldShovel, "shovel", 2);
        MinecraftForge.setToolClass(terraSteelShovel, "shovel", 4);
        MinecraftForge.setToolClass(terraTinShovel, "shovel", 1);
        MinecraftForge.setToolClass(terraZincShovel, "shovel", 1);
        //Axes
        MinecraftForge.setToolClass(terraIgInAxe, "axe", 1);
        MinecraftForge.setToolClass(terraIgExAxe, "axe", 1);
        MinecraftForge.setToolClass(terraSedAxe, "axe", 1);
        MinecraftForge.setToolClass(terraMMAxe, "axe", 1);
        MinecraftForge.setToolClass(boneIgInAxe, "axe", 1);
        MinecraftForge.setToolClass(boneIgExAxe, "axe", 1);
        MinecraftForge.setToolClass(boneSedAxe, "axe", 1);
        MinecraftForge.setToolClass(boneMMAxe, "axe", 1);
        MinecraftForge.setToolClass(terraBismuthAxe, "axe", 1);
        MinecraftForge.setToolClass(terraBismuthBronzeAxe, "axe", 2);
        MinecraftForge.setToolClass(terraBlackBronzeAxe, "axe", 2);
        MinecraftForge.setToolClass(terraBlackSteelAxe, "axe", 5);
        MinecraftForge.setToolClass(terraBlueSteelAxe, "axe", 6);
        MinecraftForge.setToolClass(terraBronzeAxe, "axe", 2);
        MinecraftForge.setToolClass(terraCopperAxe, "axe", 1);
        MinecraftForge.setToolClass(terraWroughtIronAxe, "axe", 3);
        MinecraftForge.setToolClass(terraRedSteelAxe, "axe", 6);
        MinecraftForge.setToolClass(terraRoseGoldAxe, "axe", 2);
        MinecraftForge.setToolClass(terraSteelAxe, "axe", 4);
        MinecraftForge.setToolClass(terraTinAxe, "axe", 1);
        MinecraftForge.setToolClass(terraZincAxe, "axe", 1);

        MinecraftForge.setToolClass(BismuthSaw, "axe", 1);
        MinecraftForge.setToolClass(BismuthBronzeSaw, "axe", 2);
        MinecraftForge.setToolClass(BlackBronzeSaw, "axe", 2);
        MinecraftForge.setToolClass(BlackSteelSaw, "axe", 5);
        MinecraftForge.setToolClass(BlueSteelSaw, "axe", 6);
        MinecraftForge.setToolClass(BronzeSaw, "axe", 2);
        MinecraftForge.setToolClass(CopperSaw, "axe", 1);
        MinecraftForge.setToolClass(WroughtIronSaw, "axe", 3);
        MinecraftForge.setToolClass(RedSteelSaw, "axe", 6);
        MinecraftForge.setToolClass(RoseGoldSaw, "axe", 2);
        MinecraftForge.setToolClass(SteelSaw, "axe", 4);
        MinecraftForge.setToolClass(TinSaw, "axe", 1);
        MinecraftForge.setToolClass(ZincSaw, "axe", 1);
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

        MinecraftForge.registerOre("oreTin", new ItemStack(terraOre,1,28));
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

        MinecraftForge.registerOre("ingotBismuth", new ItemStack(terraBismuthIngot,1));
        MinecraftForge.registerOre("ingotBismuthBronze", new ItemStack(terraBismuthBronzeIngot,1));
        MinecraftForge.registerOre("ingotBrass", new ItemStack(terraBrassIngot,1));
        MinecraftForge.registerOre("ingotBronze", new ItemStack(terraBronzeIngot,1));
        MinecraftForge.registerOre("ingotBlackBronze", new ItemStack(terraBlackBronzeIngot,1));
        MinecraftForge.registerOre("ingotBlackSteel", new ItemStack(terraBlackSteelIngot,1));
        MinecraftForge.registerOre("ingotBlueSteel", new ItemStack(terraBlueSteelIngot,1));
        MinecraftForge.registerOre("ingotCopper", new ItemStack(terraCopperIngot,1));
        MinecraftForge.registerOre("ingotGold", new ItemStack(terraGoldIngot,1));
        MinecraftForge.registerOre("ingotWroughtIron", new ItemStack(terraWroughtIronIngot,1));
        MinecraftForge.registerOre("ingotLead", new ItemStack(terraLeadIngot,1));
        MinecraftForge.registerOre("ingotNickel", new ItemStack(terraNickelIngot,1));
        MinecraftForge.registerOre("ingotPigIron", new ItemStack(terraPigIronIngot,1));
        MinecraftForge.registerOre("ingotPlatinum", new ItemStack(terraPlatinumIngot,1));
        MinecraftForge.registerOre("ingotRedSteel", new ItemStack(terraRedSteelIngot,1));
        MinecraftForge.registerOre("ingotRoseGold", new ItemStack(terraRoseGoldIngot,1));
        MinecraftForge.registerOre("ingotSilver", new ItemStack(terraSilverIngot,1));
        MinecraftForge.registerOre("ingotSteel", new ItemStack(terraSteelIngot,1));
        MinecraftForge.registerOre("ingotSterlingSilver", new ItemStack(terraSterlingSilverIngot,1));
        MinecraftForge.registerOre("ingotTin", new ItemStack(terraTinIngot,1));
        MinecraftForge.registerOre("ingotZinc", new ItemStack(terraZincIngot,1));

        MinecraftForge.registerOre("gemAgateChipped", new ItemStack(terraGemAgate, 1, 0));
        MinecraftForge.registerOre("gemAgateFlawed", new ItemStack(terraGemAgate, 1, 1));
        MinecraftForge.registerOre("gemAgate", new ItemStack(terraGemAgate, 1, 2));
        MinecraftForge.registerOre("gemAgateFlawless", new ItemStack(terraGemAgate, 1, 3));
        MinecraftForge.registerOre("gemAgateExquisite", new ItemStack(terraGemAgate, 1, 4));
        MinecraftForge.registerOre("gemAmethystChipped", new ItemStack(terraGemAmethyst, 1, 0));
        MinecraftForge.registerOre("gemAmethystFlawed", new ItemStack(terraGemAmethyst, 1, 1));
        MinecraftForge.registerOre("gemAmethyst", new ItemStack(terraGemAmethyst, 1, 2));
        MinecraftForge.registerOre("gemAmethystFlawless", new ItemStack(terraGemAmethyst, 1, 3));
        MinecraftForge.registerOre("gemAmethystExquisite", new ItemStack(terraGemAmethyst, 1, 4));
        MinecraftForge.registerOre("gemBerylChipped", new ItemStack(terraGemBeryl, 1, 0));
        MinecraftForge.registerOre("gemBerylFlawed", new ItemStack(terraGemBeryl, 1, 1));
        MinecraftForge.registerOre("gemBeryl", new ItemStack(terraGemBeryl, 1, 2));
        MinecraftForge.registerOre("gemBerylFlawless", new ItemStack(terraGemBeryl, 1, 3));
        MinecraftForge.registerOre("gemBerylExquisite", new ItemStack(terraGemBeryl, 1, 4));

        MinecraftForge.registerOre("gemEmeraldChipped", new ItemStack(terraGemEmerald, 1, 0));
        MinecraftForge.registerOre("gemEmeraldFlawed", new ItemStack(terraGemEmerald, 1, 1));
        MinecraftForge.registerOre("gemEmerald", new ItemStack(terraGemEmerald, 1, 2));
        MinecraftForge.registerOre("gemEmeraldFlawless", new ItemStack(terraGemEmerald, 1, 3));
        MinecraftForge.registerOre("gemEmeraldExquisite", new ItemStack(terraGemEmerald, 1, 4));

        MinecraftForge.registerOre("gemGarnetChipped", new ItemStack(terraGemGarnet, 1, 0));
        MinecraftForge.registerOre("gemGarnetFlawed", new ItemStack(terraGemGarnet, 1, 1));
        MinecraftForge.registerOre("gemGarnet", new ItemStack(terraGemGarnet, 1, 2));
        MinecraftForge.registerOre("gemGarnetFlawless", new ItemStack(terraGemGarnet, 1, 3));
        MinecraftForge.registerOre("gemGarnetExquisite", new ItemStack(terraGemGarnet, 1, 4));

        MinecraftForge.registerOre("gemJadeChipped", new ItemStack(terraGemJade, 1, 0));
        MinecraftForge.registerOre("gemJadeFlawed", new ItemStack(terraGemJade, 1, 1));
        MinecraftForge.registerOre("gemJade", new ItemStack(terraGemJade, 1, 2));
        MinecraftForge.registerOre("gemJadeFlawless", new ItemStack(terraGemJade, 1, 3));
        MinecraftForge.registerOre("gemJadeExquisite", new ItemStack(terraGemJade, 1, 4));

        MinecraftForge.registerOre("gemJasperChipped", new ItemStack(terraGemJasper, 1, 0));
        MinecraftForge.registerOre("gemJasperFlawed", new ItemStack(terraGemJasper, 1, 1));
        MinecraftForge.registerOre("gemJasper", new ItemStack(terraGemJasper, 1, 2));
        MinecraftForge.registerOre("gemJasperFlawless", new ItemStack(terraGemJasper, 1, 3));
        MinecraftForge.registerOre("gemJasperExquisite", new ItemStack(terraGemJasper, 1, 4));

        MinecraftForge.registerOre("gemOpalChipped", new ItemStack(terraGemOpal, 1, 0));
        MinecraftForge.registerOre("gemOpalFlawed", new ItemStack(terraGemOpal, 1, 1));
        MinecraftForge.registerOre("gemOpal", new ItemStack(terraGemOpal, 1, 2));
        MinecraftForge.registerOre("gemOpalFlawless", new ItemStack(terraGemOpal, 1, 3));
        MinecraftForge.registerOre("gemOpalExquisite", new ItemStack(terraGemOpal, 1, 4));

        MinecraftForge.registerOre("gemRubyChipped", new ItemStack(terraGemRuby, 1, 0));
        MinecraftForge.registerOre("gemRubyFlawed", new ItemStack(terraGemRuby, 1, 1));
        MinecraftForge.registerOre("gemRuby", new ItemStack(terraGemRuby, 1, 2));
        MinecraftForge.registerOre("gemRubyFlawless", new ItemStack(terraGemRuby, 1, 3));
        MinecraftForge.registerOre("gemRubyExquisite", new ItemStack(terraGemRuby, 1, 4));

        MinecraftForge.registerOre("gemSapphireChipped", new ItemStack(terraGemSapphire, 1, 0));
        MinecraftForge.registerOre("gemSapphireFlawed", new ItemStack(terraGemSapphire, 1, 1));
        MinecraftForge.registerOre("gemSapphire", new ItemStack(terraGemSapphire, 1, 2));
        MinecraftForge.registerOre("gemSapphireFlawless", new ItemStack(terraGemSapphire, 1, 3));
        MinecraftForge.registerOre("gemSapphireExquisite", new ItemStack(terraGemSapphire, 1, 4));

        MinecraftForge.registerOre("gemTourmalineChipped", new ItemStack(terraGemTourmaline, 1, 0));
        MinecraftForge.registerOre("gemTourmalineFlawed", new ItemStack(terraGemTourmaline, 1, 1));
        MinecraftForge.registerOre("gemTourmaline", new ItemStack(terraGemTourmaline, 1, 2));
        MinecraftForge.registerOre("gemTourmalineFlawless", new ItemStack(terraGemTourmaline, 1, 3));
        MinecraftForge.registerOre("gemTourmalineExquisite", new ItemStack(terraGemTourmaline, 1, 4));

        MinecraftForge.registerOre("gemTopazChipped", new ItemStack(terraGemTopaz, 1, 0));
        MinecraftForge.registerOre("gemTopazFlawed", new ItemStack(terraGemTopaz, 1, 1));
        MinecraftForge.registerOre("gemTopaz", new ItemStack(terraGemTopaz, 1, 2));
        MinecraftForge.registerOre("gemTopazFlawless", new ItemStack(terraGemTopaz, 1, 3));
        MinecraftForge.registerOre("gemTopazExquisite", new ItemStack(terraGemTopaz, 1, 4));

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
