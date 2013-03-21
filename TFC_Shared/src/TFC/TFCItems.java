package TFC;

import java.io.File;

import TFC.Blocks.BlockCustomDoor;
import TFC.Blocks.BlockSluice;
import TFC.Core.*;
import TFC.Enums.EnumMetalType;
import TFC.Enums.EnumSize;
import TFC.Food.ItemMeal;
import TFC.Food.ItemTerraFood;
import TFC.Items.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.*;

public class TFCItems
{
    public static Item WoodSupportItemH;
    public static Item WoodSupportItemV;

    public static Item GemRuby;
    public static Item GemSapphire;
    public static Item GemEmerald;
    public static Item GemTopaz;
    public static Item GemGarnet;
    public static Item GemOpal;
    public static Item GemAmethyst;
    public static Item GemJasper;
    public static Item GemBeryl;
    public static Item GemTourmaline;
    public static Item GemJade;
    public static Item GemAgate;
    public static Item GemDiamond;
    public static Item SulfurPowder;
    public static Item SaltpeterPowder;

    public static Item BismuthIngot;
    public static Item BismuthBronzeIngot;
    public static Item BlackBronzeIngot;
    public static Item BlackSteelIngot;
    public static Item HCBlackSteelIngot;
    public static Item BlueSteelIngot;
    public static Item WeakBlueSteelIngot;
    public static Item HCBlueSteelIngot;
    public static Item BrassIngot;
    public static Item BronzeIngot;
    public static Item CopperIngot;
    public static Item GoldIngot;
    public static Item WroughtIronIngot;
    public static Item LeadIngot;
    public static Item NickelIngot;
    public static Item PigIronIngot;
    public static Item PlatinumIngot;
    public static Item RedSteelIngot;
    public static Item WeakRedSteelIngot;
    public static Item HCRedSteelIngot;
    public static Item RoseGoldIngot;
    public static Item SilverIngot;
    public static Item SteelIngot;
    public static Item WeakSteelIngot;
    public static Item HCSteelIngot;
    public static Item SterlingSilverIngot;
    public static Item TinIngot;
    public static Item ZincIngot;

    public static Item BismuthIngot2x;
    public static Item BismuthBronzeIngot2x;
    public static Item BlackBronzeIngot2x;
    public static Item BlackSteelIngot2x;
    public static Item BlueSteelIngot2x;
    public static Item BrassIngot2x;
    public static Item BronzeIngot2x;
    public static Item CopperIngot2x;
    public static Item GoldIngot2x;
    public static Item WroughtIronIngot2x;
    public static Item LeadIngot2x;
    public static Item NickelIngot2x;
    public static Item PigIronIngot2x;
    public static Item PlatinumIngot2x;
    public static Item RedSteelIngot2x;
    public static Item RoseGoldIngot2x;
    public static Item SilverIngot2x;
    public static Item SteelIngot2x;
    public static Item SterlingSilverIngot2x;
    public static Item TinIngot2x;
    public static Item ZincIngot2x;

    public static Item IgInPick;
    public static Item IgInShovel;
    public static Item IgInAxe;
    public static Item IgInHoe;
    public static Item SedPick;
    public static Item SedShovel;
    public static Item SedAxe;
    public static Item SedHoe;
    public static Item IgExPick;
    public static Item IgExShovel;
    public static Item IgExAxe;
    public static Item IgExHoe;
    public static Item MMPick;
    public static Item MMShovel;
    public static Item MMAxe;
    public static Item MMHoe;

    public static Item BismuthPick;
    public static Item BismuthShovel;
    public static Item BismuthAxe;
    public static Item BismuthHoe;
    public static Item BismuthBronzePick;
    public static Item BismuthBronzeShovel;
    public static Item BismuthBronzeAxe;
    public static Item BismuthBronzeHoe;
    public static Item BlackBronzePick;
    public static Item BlackBronzeShovel;
    public static Item BlackBronzeAxe;
    public static Item BlackBronzeHoe;
    public static Item BlackSteelPick;
    public static Item BlackSteelShovel;
    public static Item BlackSteelAxe;
    public static Item BlackSteelHoe;
    public static Item BlueSteelPick;
    public static Item BlueSteelShovel;
    public static Item BlueSteelAxe;
    public static Item BlueSteelHoe;
    public static Item BronzePick;
    public static Item BronzeShovel;
    public static Item BronzeAxe;
    public static Item BronzeHoe;
    public static Item CopperPick;
    public static Item CopperShovel;
    public static Item CopperAxe;
    public static Item CopperHoe;
    public static Item WroughtIronPick;
    public static Item WroughtIronShovel;
    public static Item WroughtIronAxe;
    public static Item WroughtIronHoe;
    public static Item RedSteelPick;
    public static Item RedSteelShovel;
    public static Item RedSteelAxe;
    public static Item RedSteelHoe;
    public static Item RoseGoldPick;
    public static Item RoseGoldShovel;
    public static Item RoseGoldAxe;
    public static Item RoseGoldHoe;
    public static Item SteelPick;
    public static Item SteelShovel;
    public static Item SteelAxe;
    public static Item SteelHoe;
    public static Item TinPick;
    public static Item TinShovel;
    public static Item TinAxe;
    public static Item TinHoe;
    public static Item ZincPick;
    public static Item ZincShovel;
    public static Item ZincAxe;
    public static Item ZincHoe;

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
    public static Item Barrel;
    public static Item Javelin;

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
    
    public static Item BismuthScythe;
    public static Item BismuthBronzeScythe;
    public static Item BlackBronzeScythe;
    public static Item BlackSteelScythe;
    public static Item BlueSteelScythe;
    public static Item BronzeScythe;
    public static Item CopperScythe;
    public static Item WroughtIronScythe;
    public static Item RedSteelScythe;
    public static Item RoseGoldScythe;
    public static Item SteelScythe;
    public static Item TinScythe;
    public static Item ZincScythe;
    
    public static Item BismuthKnife;
    public static Item BismuthBronzeKnife;
    public static Item BlackBronzeKnife;
    public static Item BlackSteelKnife;
    public static Item BlueSteelKnife;
    public static Item BronzeKnife;
    public static Item CopperKnife;
    public static Item WroughtIronKnife;
    public static Item RedSteelKnife;
    public static Item RoseGoldKnife;
    public static Item SteelKnife;
    public static Item TinKnife;
    public static Item ZincKnife;
    
    public static Item FireStarter;
    public static Item BellowsItem;
    public static Item StoneAnvilItem;
    public static Item BismuthBronzeAnvilItem;
    public static Item BlackBronzeAnvilItem;
    public static Item BlackSteelAnvilItem;
    public static Item BlueSteelAnvilItem;
    public static Item BronzeAnvilItem;
    public static Item CopperAnvilItem;
    public static Item WroughtIronAnvilItem;
    public static Item RedSteelAnvilItem;
    public static Item RoseGoldAnvilItem;
    public static Item SteelAnvilItem;

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
    public static Item ClayMold;
    public static Item CeramicMold;
    public static Item terraMeltedUnknown;

    public static Item terraSlag;
    public static Item Ink;

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
    public static Item ScythePlan;
    public static Item KnifePlan;
    public static Item BucketPlan;

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
    
    public static Item BismuthScytheHead;
    public static Item BismuthBronzeScytheHead;
    public static Item BlackBronzeScytheHead;
    public static Item BlackSteelScytheHead;
    public static Item BlueSteelScytheHead;
    public static Item BronzeScytheHead;
    public static Item CopperScytheHead;
    public static Item WroughtIronScytheHead;
    public static Item RedSteelScytheHead;
    public static Item RoseGoldScytheHead;
    public static Item SteelScytheHead;
    public static Item TinScytheHead;
    public static Item ZincScytheHead;
    
    public static Item BismuthKnifeHead;
    public static Item BismuthBronzeKnifeHead;
    public static Item BlackBronzeKnifeHead;
    public static Item BlackSteelKnifeHead;
    public static Item BlueSteelKnifeHead;
    public static Item BronzeKnifeHead;
    public static Item CopperKnifeHead;
    public static Item WroughtIronKnifeHead;
    public static Item RedSteelKnifeHead;
    public static Item RoseGoldKnifeHead;
    public static Item SteelKnifeHead;
    public static Item TinKnifeHead;
    public static Item ZincKnifeHead;

    public static Item Coke;
    public static Item Flux;

    //Formerly TFC_Mining
    
    public static Item terraGoldPan;
    public static Item terraSluiceItem;

    public static Item ProPickStone;
    public static Item ProPickBismuth; 
    public static Item ProPickBismuthBronze;   
    public static Item ProPickBlackBronze;
    public static Item ProPickBlackSteel;
    public static Item ProPickBlueSteel;
    public static Item ProPickBronze;
    public static Item ProPickCopper;
    public static Item ProPickIron;
    public static Item ProPickRedSteel;
    public static Item ProPickRoseGold;
    public static Item ProPickSteel;
    public static Item ProPickTin;
    public static Item ProPickZinc;

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
    
    public static Item BrassSheet;
    public static Item GoldSheet;
    public static Item LeadSheet;
    public static Item NickelSheet;
    public static Item PigIronSheet;
    public static Item PlatinumSheet;
    public static Item SilverSheet;
    public static Item SterlingSilverSheet;

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
    
    public static Item BrassSheet2x;
    public static Item GoldSheet2x;
    public static Item LeadSheet2x;
    public static Item NickelSheet2x;
    public static Item PigIronSheet2x;
    public static Item PlatinumSheet2x;
    public static Item SilverSheet2x;
    public static Item SterlingSilverSheet2x;

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
    
    public static Item WoodenBucketEmpty;
    public static Item WoodenBucketWater;
    public static Item WoodenBucketMilk;
    
    /**Food Related Items and Blocks*/
    public static Item SeedsWheat;
    public static Item SeedsMelon;
    public static Item SeedsPumpkin;
    public static Item SeedsMaize;
    public static Item SeedsTomato;
    public static Item SeedsBarley;
    public static Item SeedsRye;
    public static Item SeedsOat;
    public static Item SeedsRice;
    public static Item SeedsPotato;
    public static Item SeedsOnion;
    public static Item SeedsCabbage;
    public static Item SeedsGarlic;
    public static Item SeedsCarrot;
    public static Item SeedsSugarcane;
    public static Item SeedsHemp;
    public static Item SeedsYellowBellPepper;
    public static Item SeedsRedBellPepper;
    public static Item SeedsSoybean;
    public static Item SeedsGreenbean;
    public static Item SeedsSquash;
    
    public static Item FruitTreeSapling1;
    public static Item FruitTreeSapling2;
    
    public static Item RedApple;
    public static Item GreenApple;
    public static Item Banana;
    public static Item Orange;
    public static Item Lemon;
    public static Item Olive;
    public static Item Cherry;
    public static Item Peach;
    public static Item Plum;
    public static Item Egg;
    public static Item EggCooked;
    
    public static Item WheatGrain;
    public static Item BarleyGrain;
    public static Item OatGrain;
    public static Item RyeGrain;
    public static Item RiceGrain;
    public static Item MaizeEar;
    public static Item Tomato;
    public static Item Potato;
    public static Item Onion;
    public static Item Cabbage;
    public static Item Garlic;
    public static Item Carrot;
    public static Item Sugarcane;
    public static Item Hemp;
    public static Item Soybean;
    public static Item Greenbeans;
    public static Item GreenBellPepper;
    public static Item YellowBellPepper;
    public static Item RedBellPepper;
    public static Item Squash;
    
    public static Item WheatGround;
    public static Item BarleyGround;
    public static Item OatGround;
    public static Item RyeGround;
    public static Item RiceGround;
    public static Item CornmealGround;
    
    public static Item WheatDough;
    public static Item BarleyDough;
    public static Item OatDough;
    public static Item RyeDough;
    public static Item RiceDough;
    public static Item CornmealDough;
    
    public static Item BarleyBread;
    public static Item OatBread;
    public static Item RyeBread;
    public static Item RiceBread;
    public static Item CornBread;
    
    public static Item WheatWhole;
    public static Item BarleyWhole;
    public static Item OatWhole;
    public static Item RyeWhole;
    public static Item RiceWhole;
    
    public static Item LooseRock;
    public static Item FlatRock;
    
    public static Item IgInStonePickaxeHead;
    public static Item SedStonePickaxeHead;
    public static Item IgExStonePickaxeHead;
    public static Item MMStonePickaxeHead;
    public static Item IgInStoneShovelHead;
    public static Item SedStoneShovelHead;
    public static Item IgExStoneShovelHead;
    public static Item MMStoneShovelHead;
    public static Item IgInStoneAxeHead;
    public static Item SedStoneAxeHead;
    public static Item IgExStoneAxeHead;
    public static Item MMStoneAxeHead;
    public static Item IgInStoneHoeHead;
    public static Item SedStoneHoeHead;
    public static Item IgExStoneHoeHead;
    public static Item MMStoneHoeHead;
    
    public static Item StoneKnife;
    public static Item StoneKnifeHead;
    public static Item StoneHammerHead;
    public static Item StoneProPickHead;
    public static Item SmallOreChunk;
    public static Item SinglePlank;
    
    public static Item minecartEmpty;
    public static Item minecartCrate;
    
    public static Item RedSteelBucketEmpty;
    public static Item RedSteelBucketWater;
    
    public static Item BlueSteelBucketEmpty;
    public static Item BlueSteelBucketLava;
    
    public static Item MealGeneric;
    public static Item MealMoveSpeed;
    public static Item MealDigSpeed;
    public static Item MealDamageBoost;
    public static Item MealJump;
    public static Item MealDamageResist;
    public static Item MealFireResist;
    public static Item MealWaterBreathing;
    public static Item MealNightVision;
    
    public static Item Quern;
    public static Item FlintSteel;
    
    public static Item DoorOak;
	public static Item DoorAspen;
	public static Item DoorBirch;
	public static Item DoorChestnut;
	public static Item DoorDouglasFir;
	public static Item DoorHickory;
	public static Item DoorMaple;
	public static Item DoorAsh;
	public static Item DoorPine;
	public static Item DoorSequoia;
	public static Item DoorSpruce;
	public static Item DoorSycamore;
	public static Item DoorWhiteCedar;
	public static Item DoorWhiteElm;
	public static Item DoorWillow;
	public static Item DoorKapok;
	
	public static Item Blueprint;
	public static Item writabeBookTFC;
    public static Item WoolYarn;
    public static Item Wool;
    public static Item WoolCloth;
    public static Item Spindle;
    public static Item ClaySpindle;
    public static Item SpindleHead;
    public static Item StoneBrick;
    public static Item Mortar;
    public static Item Limewater;
    public static Item Hide;
    public static Item SoakedHide;
    public static Item ScrapedHide;
    public static Item PrepHide;
    public static Item SheepSkin;
    public static Item TerraLeather;
    public static Item muttonRaw;
    public static Item muttonCooked;
    public static Item FlatLeather;
    public static Item Beer;
    
    /**
     * Item Uses Setup
     * */
    public static int IgInStoneUses = 60;
    public static int IgExStoneUses = 70;
    public static int SedStoneUses = 50;
    public static int MMStoneUses = 55; 
    
    //Tier 0
    public static int BismuthUses = 300;
    public static int TinUses = 290;
    public static int ZincUses = 250;
    //Tier 1
    public static int CopperUses = 600;
    //Tier 2
    public static int BronzeUses = 1300;
    public static int RoseGoldUses = 1140;
    public static int BismuthBronzeUses = 1200;
    public static int BlackBronzeUses = 1460;
    //Tier 3
    public static int WroughtIronUses = 2200;
    //Tier 4
    public static int SteelUses = 3300;
    //Tier 5
    public static int BlackSteelUses = 4200;
    //Tier 6
    public static int BlueSteelUses = 6500;
    public static int RedSteelUses = 6500;
    
    
  //Tier 0
    public static float BismuthEff = 11;
    public static float TinEff = 10.5f;
    public static float ZincEff = 10;
    //Tier 1
    public static float CopperEff = 12;
    //Tier 2
    public static float BronzeEff = 15;
    public static float RoseGoldEff = 13;
    public static float BismuthBronzeEff = 14;
    public static float BlackBronzeEff = 16;
    //Tier 3
    public static float WroughtIronEff = 16;
    //Tier 4
    public static float SteelEff = 17;
    //Tier 5
    public static float BlackSteelEff = 18;
    //Tier 6
    public static float BlueSteelEff = 21;
    public static float RedSteelEff = 21;
    
    
    
    
    public static EnumToolMaterial IgInToolMaterial = EnumHelper.addToolMaterial("IgIn", 1, IgInStoneUses, 7F, 10, 5);
    public static EnumToolMaterial SedToolMaterial = EnumHelper.addToolMaterial("Sed", 1, SedStoneUses, 6F, 10, 5);
    public static EnumToolMaterial IgExToolMaterial = EnumHelper.addToolMaterial("IgEx", 1, IgExStoneUses, 7F, 10, 5);
    public static EnumToolMaterial MMToolMaterial = EnumHelper.addToolMaterial("MM", 1, MMStoneUses, 6.5F, 10, 5);

    public static EnumToolMaterial BismuthToolMaterial = EnumHelper.addToolMaterial("Bismuth", 2, BismuthUses, BismuthEff, 65, 10);
    public static EnumToolMaterial BismuthBronzeToolMaterial = EnumHelper.addToolMaterial("BismuthBronze", 2, BismuthBronzeUses, BismuthBronzeEff, 85, 10);
    public static EnumToolMaterial BlackBronzeToolMaterial = EnumHelper.addToolMaterial("BlackBronze", 2, BlackBronzeUses, BlackBronzeEff, 100, 10);
    public static EnumToolMaterial BlackSteelToolMaterial = EnumHelper.addToolMaterial("BlackSteel", 2, BlackSteelUses, BlackSteelEff, 165, 12);
    public static EnumToolMaterial BlueSteelToolMaterial = EnumHelper.addToolMaterial("BlueSteel", 3, BlueSteelUses, BlueSteelEff, 185, 22);
    public static EnumToolMaterial BronzeToolMaterial = EnumHelper.addToolMaterial("Bronze", 2, BronzeUses, BronzeEff, 100, 13);
    public static EnumToolMaterial CopperToolMaterial = EnumHelper.addToolMaterial("Copper", 2, CopperUses, CopperEff, 85, 8);
    public static EnumToolMaterial IronToolMaterial = EnumHelper.addToolMaterial("Iron", 2, WroughtIronUses, WroughtIronEff, 135, 10);
    public static EnumToolMaterial RedSteelToolMaterial = EnumHelper.addToolMaterial("RedSteel", 3, RedSteelUses, RedSteelEff, 185, 22);
    public static EnumToolMaterial RoseGoldToolMaterial = EnumHelper.addToolMaterial("RoseGold", 2, RoseGoldUses, RoseGoldEff, 100, 20);
    public static EnumToolMaterial SteelToolMaterial = EnumHelper.addToolMaterial("Steel", 2, SteelUses, SteelEff, 150, 10);
    public static EnumToolMaterial TinToolMaterial = EnumHelper.addToolMaterial("Tin", 2, TinUses, TinEff, 65, 8);
    public static EnumToolMaterial ZincToolMaterial = EnumHelper.addToolMaterial("Zinc", 2, ZincUses, ZincEff, 65, 8);
    
    
    
    static Configuration config;
    
    
    public static void Setup()
    {
        try
        {
            config = new net.minecraftforge.common.Configuration(
            		new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFC.cfg"));
            config.load();
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("[TFC] Error while trying to access item configuration!").toString());
            config = null;
        } 
        System.out.println(new StringBuilder().append("[TFC] Loading Items").toString());
        
        //Replace any vanilla Items here
        Item.itemsList[Item.coal.itemID] = null; Item.itemsList[Item.coal.itemID] = (new TFC.Items.ItemCoal(7)).setUnlocalizedName("coal");
        Item.itemsList[Item.stick.itemID] = null; Item.itemsList[Item.stick.itemID] = new ItemStick(24).setFull3D().setUnlocalizedName("Stick");
        Item.itemsList[Item.leather.itemID] = null; Item.itemsList[Item.leather.itemID] = new ItemTerra(Item.leather.itemID).setFull3D().setUnlocalizedName("Leather");
        
        minecartCrate = (new ItemCustomMinecart(TFC_Settings.getIntFor(config,"item","minecartCrate",16000), 1)).setUnlocalizedName("minecartChest");
        
        Item.itemsList[5+256] = null; Item.itemsList[5+256] = (new ItemCustomBow(5)).setUnlocalizedName("bow");
        Item.itemsList[63+256] = null; Item.itemsList[63+256] = new ItemTerra(63,"/gui/items.png").setUnlocalizedName("porkchopRaw");
        Item.itemsList[64+256] = null; Item.itemsList[64+256] = new ItemTerraFood(64, 35, 0.8F, true,"/gui/items.png", 38).setUnlocalizedName("porkchopCooked");
        Item.itemsList[93+256] = null; Item.itemsList[93+256] = new ItemTerra(93, "/gui/items.png").setUnlocalizedName("fishRaw");
        Item.itemsList[94+256] = null; Item.itemsList[94+256] = new ItemTerraFood(94, 30, 0.6F, true,"/gui/items.png", 39).setUnlocalizedName("fishCooked");
        Item.itemsList[107+256] = null; Item.itemsList[107+256] = new ItemTerra(107, "/gui/items.png").setUnlocalizedName("beefRaw");
        Item.itemsList[108+256] = null; Item.itemsList[108+256] = new ItemTerraFood(108, 40, 0.8F, true,"/gui/items.png", 40).setUnlocalizedName("beefCooked");
        Item.itemsList[109+256] = null; Item.itemsList[109+256] = new ItemTerra(109,"/gui/items.png").setUnlocalizedName("chickenRaw");
        Item.itemsList[110+256] = null; Item.itemsList[110+256] = new ItemTerraFood(110, 35, 0.6F, true,"/gui/items.png", 41).setUnlocalizedName("chickenCooked");
        Item.itemsList[41+256] = null; Item.itemsList[41+256] = (new ItemTerraFood(41, 25, 0.6F, false,"/gui/items.png", 42)).setUnlocalizedName("bread");
        Item.itemsList[88+256] = null; Item.itemsList[88+256] = (new ItemTerra(88,"/gui/items.png")).setUnlocalizedName("egg");
        Item.itemsList[Item.dyePowder.itemID] = null; Item.itemsList[Item.dyePowder.itemID] = new ItemDyeCustom(95).setUnlocalizedName("dyePowder");
        Item.itemsList[Item.potion.itemID] = null; Item.itemsList[Item.potion.itemID] = (ItemCustomPotion)(new ItemCustomPotion(117)).setUnlocalizedName("potion");
        
        
        
		
        terraGoldPan = new ItemGoldPan(TFC_Settings.getIntFor(config,"item","terraGoldPan",16001)).setUnlocalizedName("GoldPan");
        terraSluiceItem = new ItemSluice(TFC_Settings.getIntFor(config,"item","terraSluiceItem",16002)).setUnlocalizedName("SluiceItem");
        
        ProPickStone = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickStone",16003)).setUnlocalizedName("Stone ProPick").setMaxDamage(64);
        ProPickBismuth = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickBismuth",16004)).setUnlocalizedName("Bismuth ProPick").setMaxDamage(BismuthUses);
        ProPickBismuthBronze = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickBismuthBronze",16005)).setUnlocalizedName("Bismuth Bronze ProPick").setMaxDamage(BismuthBronzeUses);
        ProPickBlackBronze = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickBlackBronze",16006)).setUnlocalizedName("Black Bronze ProPick").setMaxDamage(BlackBronzeUses);
        ProPickBlackSteel = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickBlackSteel",16007)).setUnlocalizedName("Black Steel ProPick").setMaxDamage(BlackSteelUses);
        ProPickBlueSteel = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickBlueSteel",16008)).setUnlocalizedName("Blue Steel ProPick").setMaxDamage(BlueSteelUses);
        ProPickBronze = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickBronze",16009)).setUnlocalizedName("Bronze ProPick").setMaxDamage(BronzeUses);
        ProPickCopper = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickCopper",16010)).setUnlocalizedName("Copper ProPick").setMaxDamage(CopperUses);
        ProPickIron = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickWroughtIron",16012)).setUnlocalizedName("Wrought Iron ProPick").setMaxDamage(WroughtIronUses);
        ProPickRedSteel = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickRedSteel",16016)).setUnlocalizedName("Red Steel ProPick").setMaxDamage(RedSteelUses);
        ProPickRoseGold = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickRoseGold",16017)).setUnlocalizedName("Rose Gold ProPick").setMaxDamage(RoseGoldUses);
        ProPickSteel = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickSteel",16019)).setUnlocalizedName("Steel ProPick").setMaxDamage(SteelUses);
        ProPickTin = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickTin",16021)).setUnlocalizedName("Tin ProPick").setMaxDamage(TinUses);
        ProPickZinc = new ItemProPick(TFC_Settings.getIntFor(config,"item","ProPickZinc",16022)).setUnlocalizedName("Zinc ProPick").setMaxDamage(ZincUses);
        
        BismuthIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthIngot",16028), EnumMetalType.BISMUTH).setUnlocalizedName("BismuthIngot");
        BismuthBronzeIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthBronzeIngot",16029), EnumMetalType.BISMUTHBRONZE).setUnlocalizedName("BismuthBronzeIngot");
        BlackBronzeIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackBronzeIngot",16030), EnumMetalType.BLACKBRONZE).setUnlocalizedName("BlackBronzeIngot");
        BlackSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackSteelIngot",16031), EnumMetalType.BLACKSTEEL).setUnlocalizedName("BlackSteelIngot");
        BlueSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BlueSteelIngot",16032), EnumMetalType.BLUESTEEL).setUnlocalizedName("BlueSteelIngot");
        BrassIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BrassIngot",16033), EnumMetalType.BRASS).setUnlocalizedName("BrassIngot");
        BronzeIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BronzeIngot",16034), EnumMetalType.BRONZE).setUnlocalizedName("BronzeIngot");
        CopperIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","CopperIngot",16035), EnumMetalType.COPPER).setUnlocalizedName("CopperIngot");
        GoldIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","GoldIngot",16036), EnumMetalType.GOLD).setUnlocalizedName("GoldIngot");
        WroughtIronIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WroughtIronIngot",16037), EnumMetalType.WROUGHTIRON).setUnlocalizedName("WroughtIronIngot");
        LeadIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","LeadIngot",16038), EnumMetalType.LEAD).setUnlocalizedName("LeadIngot");
        NickelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","NickelIngot",16039), EnumMetalType.NICKEL).setUnlocalizedName("NickelIngot");
        PigIronIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","PigIronIngot",16040), EnumMetalType.PIGIRON).setUnlocalizedName("PigIronIngot");
        PlatinumIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","PlatinumIngot",16041), EnumMetalType.PLATINUM).setUnlocalizedName("PlatinumIngot");
        RedSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","RedSteelIngot",16042), EnumMetalType.REDSTEEL).setUnlocalizedName("RedSteelIngot");
        RoseGoldIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","RoseGoldIngot",16043), EnumMetalType.ROSEGOLD).setUnlocalizedName("RoseGoldIngot");
        SilverIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","SilverIngot",16044), EnumMetalType.SILVER).setUnlocalizedName("SilverIngot");
        SteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","SteelIngot",16045), EnumMetalType.STEEL).setUnlocalizedName("SteelIngot");
        SterlingSilverIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","SterlingSilverIngot",16046), EnumMetalType.STERLINGSILVER).setUnlocalizedName("SterlingSilverIngot");
        TinIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","TinIngot",16047), EnumMetalType.TIN).setUnlocalizedName("TinIngot");
        ZincIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","ZincIngot",16048), EnumMetalType.ZINC).setUnlocalizedName("ZincIngot");

        BismuthIngot2x = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthIngot2x",16049), EnumMetalType.BISMUTH).setUnlocalizedName("BismuthIngot2x")).setSize(EnumSize.LARGE);
        BismuthBronzeIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthBronzeIngot2x",16050), EnumMetalType.BISMUTHBRONZE).setUnlocalizedName("BismuthBronzeIngot2x")).setSize(EnumSize.LARGE);
        BlackBronzeIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackBronzeIngot2x",16051), EnumMetalType.BLACKBRONZE).setUnlocalizedName("BlackBronzeIngot2x")).setSize(EnumSize.LARGE);
        BlackSteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackSteelIngot2x",16052), EnumMetalType.BLACKSTEEL).setUnlocalizedName("BlackSteelIngot2x")).setSize(EnumSize.LARGE);
        BlueSteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BlueSteelIngot2x",16053), EnumMetalType.BLUESTEEL).setUnlocalizedName("BlueSteelIngot2x")).setSize(EnumSize.LARGE);
        BrassIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BrassIngot2x",16054), EnumMetalType.BRASS).setUnlocalizedName("BrassIngot2x")).setSize(EnumSize.LARGE);
        BronzeIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BronzeIngot2x",16055), EnumMetalType.BRONZE).setUnlocalizedName("BronzeIngot2x")).setSize(EnumSize.LARGE);
        CopperIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","CopperIngot2x",16056), EnumMetalType.COPPER).setUnlocalizedName("CopperIngot2x")).setSize(EnumSize.LARGE);
        GoldIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","GoldIngot2x",16057), EnumMetalType.GOLD).setUnlocalizedName("GoldIngot2x")).setSize(EnumSize.LARGE);
        WroughtIronIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","WroughtIronIngot2x",16058), EnumMetalType.WROUGHTIRON).setUnlocalizedName("WroughtIronIngot2x")).setSize(EnumSize.LARGE);
        LeadIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","LeadIngot2x",16059), EnumMetalType.LEAD).setUnlocalizedName("LeadIngot2x")).setSize(EnumSize.LARGE);
        NickelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","NickelIngot2x",16060), EnumMetalType.NICKEL).setUnlocalizedName("NickelIngot2x")).setSize(EnumSize.LARGE);
        PigIronIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","PigIronIngot2x",16061), EnumMetalType.PIGIRON).setUnlocalizedName("PigIronIngot2x")).setSize(EnumSize.LARGE);
        PlatinumIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","PlatinumIngot2x",16062), EnumMetalType.PLATINUM).setUnlocalizedName("PlatinumIngot2x")).setSize(EnumSize.LARGE);
        RedSteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","RedSteelIngot2x",16063), EnumMetalType.REDSTEEL).setUnlocalizedName("RedSteelIngot2x")).setSize(EnumSize.LARGE);
        RoseGoldIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","RoseGoldIngot2x",16064), EnumMetalType.ROSEGOLD).setUnlocalizedName("RoseGoldIngot2x")).setSize(EnumSize.LARGE);
        SilverIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","SilverIngot2x",16065), EnumMetalType.SILVER).setUnlocalizedName("SilverIngot2x")).setSize(EnumSize.LARGE);
        SteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","SteelIngot2x",16066), EnumMetalType.STEEL).setUnlocalizedName("SteelIngot2x")).setSize(EnumSize.LARGE);
        SterlingSilverIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","SterlingSilverIngot2x",16067), EnumMetalType.STERLINGSILVER).setUnlocalizedName("SterlingSilverIngot2x")).setSize(EnumSize.LARGE);
        TinIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","TinIngot2x",16068), EnumMetalType.TIN).setUnlocalizedName("TinIngot2x")).setSize(EnumSize.LARGE);
        ZincIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","ZincIngot2x",16069), EnumMetalType.ZINC).setUnlocalizedName("ZincIngot2x")).setSize(EnumSize.LARGE);

        SulfurPowder = new ItemTerra(TFC_Settings.getIntFor(config,"item","SulfurPowder",16070)).setUnlocalizedName("SulfurPowder");
        SaltpeterPowder = new ItemTerra(TFC_Settings.getIntFor(config,"item","SaltpeterPowder",16071)).setUnlocalizedName("SaltpeterPowder");

        GemRuby = new ItemGem(TFC_Settings.getIntFor(config,"item","GemRuby",16080)).setUnlocalizedName("Ruby");
        GemSapphire = new ItemGem(TFC_Settings.getIntFor(config,"item","GemSapphire",16081)).setUnlocalizedName("Sapphire");
        GemEmerald = new ItemGem(TFC_Settings.getIntFor(config,"item","GemEmerald",16082)).setUnlocalizedName("Emerald");
        GemTopaz = new ItemGem(TFC_Settings.getIntFor(config,"item","GemTopaz",16083)).setUnlocalizedName("Topaz");
        GemTourmaline = new ItemGem(TFC_Settings.getIntFor(config,"item","GemTourmaline",16084)).setUnlocalizedName("Tourmaline");
        GemJade = new ItemGem(TFC_Settings.getIntFor(config,"item","GemJade",16085)).setUnlocalizedName("Jade");
        GemBeryl = new ItemGem(TFC_Settings.getIntFor(config,"item","GemBeryl",16086)).setUnlocalizedName("Beryl");
        GemAgate = new ItemGem(TFC_Settings.getIntFor(config,"item","GemAgate",16087)).setUnlocalizedName("Agate");
        GemOpal = new ItemGem(TFC_Settings.getIntFor(config,"item","GemOpal",16088)).setUnlocalizedName("Opal");
        GemGarnet = new ItemGem(TFC_Settings.getIntFor(config,"item","GemGarnet",16089)).setUnlocalizedName("Garnet");
        GemJasper = new ItemGem(TFC_Settings.getIntFor(config,"item","GemJasper",16090)).setUnlocalizedName("Jasper");
        GemAmethyst = new ItemGem(TFC_Settings.getIntFor(config,"item","GemAmethyst",16091)).setUnlocalizedName("Amethyst");
        GemDiamond = new ItemGem(TFC_Settings.getIntFor(config,"item","GemDiamond",16092)).setUnlocalizedName("Diamond");

        //Tools
        IgInPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","IgInPick",16100),IgInToolMaterial).setUnlocalizedName("IgIn Stone Pick").setMaxDamage(IgInStoneUses);
        IgInShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","IgInShovel",16101),IgInToolMaterial).setUnlocalizedName("IgIn Stone Shovel").setMaxDamage(IgInStoneUses);
        IgInAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","IgInAxe",16102),IgInToolMaterial).setUnlocalizedName("IgIn Stone Axe").setMaxDamage(IgInStoneUses);
        IgInHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","IgInHoe",16103),IgInToolMaterial).setUnlocalizedName("IgIn Stone Hoe").setMaxDamage(IgInStoneUses);
        SedPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","SedPick",16104),SedToolMaterial).setUnlocalizedName("Sed Stone Pick").setMaxDamage(SedStoneUses);
        SedShovel= new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","SedShovel",16105),SedToolMaterial).setUnlocalizedName("Sed Stone Shovel").setMaxDamage(SedStoneUses);
        SedAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","SedAxe",16106),SedToolMaterial).setUnlocalizedName("Sed Stone Axe").setMaxDamage(SedStoneUses);
        SedHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","SedHoe",16107),SedToolMaterial).setUnlocalizedName("Sed Stone Hoe").setMaxDamage(SedStoneUses);
        IgExPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","IgExPick",16108),IgExToolMaterial).setUnlocalizedName("IgEx Stone Pick").setMaxDamage(IgExStoneUses);
        IgExShovel= new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","IgExShovel",16109),IgExToolMaterial).setUnlocalizedName("IgEx Stone Shovel").setMaxDamage(IgExStoneUses);
        IgExAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","IgExAxe",16110),IgExToolMaterial).setUnlocalizedName("IgEx Stone Axe").setMaxDamage(IgExStoneUses);
        IgExHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","IgExHoe",16111),IgExToolMaterial).setUnlocalizedName("IgEx Stone Hoe").setMaxDamage(IgExStoneUses);
        MMPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","MMPick",16112),MMToolMaterial).setUnlocalizedName("MM Stone Pick").setMaxDamage(MMStoneUses);
        MMShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","MMShovel",16113),MMToolMaterial).setUnlocalizedName("MM Stone Shovel").setMaxDamage(MMStoneUses);
        MMAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","MMAxe",16114),MMToolMaterial).setUnlocalizedName("MM Stone Axe").setMaxDamage(MMStoneUses);
        MMHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","MMHoe",16115),MMToolMaterial).setUnlocalizedName("MM Stone Hoe").setMaxDamage(MMStoneUses);

        BismuthPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","BismuthPick",16116),BismuthToolMaterial).setUnlocalizedName("Bismuth Pick").setMaxDamage(BismuthUses);
        BismuthShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","BismuthShovel",16117),BismuthToolMaterial).setUnlocalizedName("Bismuth Shovel").setMaxDamage(BismuthUses);
        BismuthAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","BismuthAxe",16118),BismuthToolMaterial).setUnlocalizedName("Bismuth Axe").setMaxDamage(BismuthUses);
        BismuthHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","BismuthHoe",16119),BismuthToolMaterial).setUnlocalizedName("Bismuth Hoe").setMaxDamage(BismuthUses);

        BismuthBronzePick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","BismuthBronzePick",16120),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Pick").setMaxDamage(BismuthBronzeUses);
        BismuthBronzeShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","BismuthBronzeShovel",16121),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Shovel").setMaxDamage(BismuthBronzeUses);
        BismuthBronzeAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","BismuthBronzeAxe",16122),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Axe").setMaxDamage(BismuthBronzeUses);
        BismuthBronzeHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","BismuthBronzeHoe",16123),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hoe").setMaxDamage(BismuthBronzeUses);

        BlackBronzePick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","BlackBronzePick",16124),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Pick").setMaxDamage(BlackBronzeUses);
        BlackBronzeShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","BlackBronzeShovel",16125),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Shovel").setMaxDamage(BlackBronzeUses);
        BlackBronzeAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","BlackBronzeAxe",16126),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Axe").setMaxDamage(BlackBronzeUses);
        BlackBronzeHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","BlackBronzeHoe",16127),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hoe").setMaxDamage(BlackBronzeUses);

        BlackSteelPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","BlackSteelPick",16128),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Pick").setMaxDamage(BlackSteelUses);
        BlackSteelShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","BlackSteelShovel",16129),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Shovel").setMaxDamage(BlackSteelUses);
        BlackSteelAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","BlackSteelAxe",16130),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Axe").setMaxDamage(BlackSteelUses);
        BlackSteelHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","BlackSteelHoe",16131),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Hoe").setMaxDamage(BlackSteelUses);

        BlueSteelPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","BlueSteelPick",16132),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Pick").setMaxDamage(BlueSteelUses);
        BlueSteelShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","BlueSteelShovel",16133),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Shovel").setMaxDamage(BlueSteelUses);
        BlueSteelAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","BlueSteelAxe",16134),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Axe").setMaxDamage(BlueSteelUses);
        BlueSteelHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","BlueSteelHoe",16135),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Hoe").setMaxDamage(BlueSteelUses);

        BronzePick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","BronzePick",16136),BronzeToolMaterial).setUnlocalizedName("Bronze Pick").setMaxDamage(BronzeUses);
        BronzeShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","BronzeShovel",16137),BronzeToolMaterial).setUnlocalizedName("Bronze Shovel").setMaxDamage(BronzeUses);
        BronzeAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","BronzeAxe",16138),BronzeToolMaterial).setUnlocalizedName("Bronze Axe").setMaxDamage(BronzeUses);
        BronzeHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","BronzeHoe",16139),BronzeToolMaterial).setUnlocalizedName("Bronze Hoe").setMaxDamage(BronzeUses);

        CopperPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","CopperPick",16140),CopperToolMaterial).setUnlocalizedName("Copper Pick").setMaxDamage(CopperUses);
        CopperShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","CopperShovel",16141),CopperToolMaterial).setUnlocalizedName("Copper Shovel").setMaxDamage(CopperUses);
        CopperAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","CopperAxe",16142),CopperToolMaterial).setUnlocalizedName("Copper Axe").setMaxDamage(CopperUses);
        CopperHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","CopperHoe",16143),CopperToolMaterial).setUnlocalizedName("Copper Hoe").setMaxDamage(CopperUses);

        WroughtIronPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","WroughtIronPick",16148),IronToolMaterial).setUnlocalizedName("Wrought Iron Pick").setMaxDamage(WroughtIronUses);
        WroughtIronShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","WroughtIronShovel",16149),IronToolMaterial).setUnlocalizedName("Wrought Iron Shovel").setMaxDamage(WroughtIronUses);
        WroughtIronAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","WroughtIronAxe",16150),IronToolMaterial).setUnlocalizedName("Wrought Iron Axe").setMaxDamage(WroughtIronUses);
        WroughtIronHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","WroughtIronHoe",16151),IronToolMaterial).setUnlocalizedName("Wrought Iron Hoe").setMaxDamage(WroughtIronUses);;

        RedSteelPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","RedSteelPick",16168),RedSteelToolMaterial).setUnlocalizedName("Red Steel Pick").setMaxDamage(RedSteelUses);
        RedSteelShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","RedSteelShovel",16169),RedSteelToolMaterial).setUnlocalizedName("Red Steel Shovel").setMaxDamage(RedSteelUses);
        RedSteelAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","RedSteelAxe",16170),RedSteelToolMaterial).setUnlocalizedName("Red Steel Axe").setMaxDamage(RedSteelUses);
        RedSteelHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","RedSteelHoe",16171),RedSteelToolMaterial).setUnlocalizedName("Red Steel Hoe").setMaxDamage(RedSteelUses);

        RoseGoldPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","RoseGoldPick",16172),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Pick").setMaxDamage(RoseGoldUses);
        RoseGoldShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","RoseGoldShovel",16173),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Shovel").setMaxDamage(RoseGoldUses);
        RoseGoldAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","RoseGoldAxe",16174),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Axe").setMaxDamage(RoseGoldUses);
        RoseGoldHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","RoseGoldHoe",16175),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Hoe").setMaxDamage(RoseGoldUses);

        SteelPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","SteelPick",16180),SteelToolMaterial).setUnlocalizedName("Steel Pick").setMaxDamage(SteelUses);
        SteelShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","SteelShovel",16181),SteelToolMaterial).setUnlocalizedName("Steel Shovel").setMaxDamage(SteelUses);
        SteelAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","SteelAxe",16182),SteelToolMaterial).setUnlocalizedName("Steel Axe").setMaxDamage(SteelUses);
        SteelHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","SteelHoe",16183),SteelToolMaterial).setUnlocalizedName("Steel Hoe").setMaxDamage(SteelUses);

        TinPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","TinPick",16188),TinToolMaterial).setUnlocalizedName("Tin Pick").setMaxDamage(TinUses);
        TinShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","TinShovel",16189),TinToolMaterial).setUnlocalizedName("Tin Shovel").setMaxDamage(TinUses);
        TinAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","TinAxe",16190),TinToolMaterial).setUnlocalizedName("Tin Axe").setMaxDamage(TinUses);
        TinHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","TinHoe",16191),TinToolMaterial).setUnlocalizedName("Tin Hoe").setMaxDamage(TinUses);

        ZincPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","ZincPick",16192),ZincToolMaterial).setUnlocalizedName("Zinc Pick").setMaxDamage(ZincUses);
        ZincShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","ZincShovel",16193),ZincToolMaterial).setUnlocalizedName("Zinc Shovel").setMaxDamage(ZincUses);
        ZincAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","ZincAxe",16194),ZincToolMaterial).setUnlocalizedName("Zinc Axe").setMaxDamage(ZincUses);
        ZincHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","ZincHoe",16195),ZincToolMaterial).setUnlocalizedName("Zinc Hoe").setMaxDamage(ZincUses);

        //chisels
        BismuthChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","BismuthChisel",16226),BismuthToolMaterial).setUnlocalizedName("Bismuth Chisel").setMaxDamage(BismuthUses);
        BismuthBronzeChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","BismuthBronzeChisel",16227),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Chisel").setMaxDamage(BismuthBronzeUses);
        BlackBronzeChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","BlackBronzeChisel",16228),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Chisel").setMaxDamage(BlackBronzeUses);
        BlackSteelChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","BlackSteelChisel",16230),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Chisel").setMaxDamage(BlackSteelUses);
        BlueSteelChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","BlueSteelChisel",16231),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Chisel").setMaxDamage(BlueSteelUses);
        BronzeChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","BronzeChisel",16232),BronzeToolMaterial).setUnlocalizedName("Bronze Chisel").setMaxDamage(BronzeUses);
        CopperChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","CopperChisel",16233),CopperToolMaterial).setUnlocalizedName("Copper Chisel").setMaxDamage(CopperUses);
        WroughtIronChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","WroughtIronChisel",16234),IronToolMaterial).setUnlocalizedName("Wrought Iron Chisel").setMaxDamage(WroughtIronUses);
        RedSteelChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","RedSteelChisel",16235),RedSteelToolMaterial).setUnlocalizedName("Red Steel Chisel").setMaxDamage(RedSteelUses);
        RoseGoldChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","RoseGoldChisel",16236),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Chisel").setMaxDamage(RoseGoldUses);
        SteelChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","SteelChisel",16237),SteelToolMaterial).setUnlocalizedName("Steel Chisel").setMaxDamage(SteelUses);
        TinChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","TinChisel",16238),TinToolMaterial).setUnlocalizedName("Tin Chisel").setMaxDamage(TinUses);
        ZincChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","ZincChisel",16239),ZincToolMaterial).setUnlocalizedName("Zinc Chisel").setMaxDamage(ZincUses);
        StoneChisel = new ItemChisel(TFC_Settings.getIntFor(config,"item","StoneChisel",16240),IgInToolMaterial).setUnlocalizedName("Stone Chisel").setMaxDamage(IgInStoneUses);

        BismuthSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BismuthSword",16245),BismuthToolMaterial).setUnlocalizedName("Bismuth Sword").setMaxDamage(BismuthUses);
        BismuthBronzeSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BismuthBronzeSword",16246),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Sword").setMaxDamage(BismuthBronzeUses);
        BlackBronzeSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlackBronzeSword",16247),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Sword").setMaxDamage(BlackBronzeUses);
        BlackSteelSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlackSteelSword",16248),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Sword").setMaxDamage(BlackSteelUses);
        BlueSteelSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlueSteelSword",16249),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Sword").setMaxDamage(BlueSteelUses);
        BronzeSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BronzeSword",16250),BronzeToolMaterial).setUnlocalizedName("Bronze Sword").setMaxDamage(BronzeUses);
        CopperSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","CopperSword",16251),CopperToolMaterial).setUnlocalizedName("Copper Sword").setMaxDamage(CopperUses);
        WroughtIronSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","WroughtIronSword",16252),IronToolMaterial).setUnlocalizedName("Wrought Iron Sword").setMaxDamage(WroughtIronUses);
        RedSteelSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","RedSteelSword",16253),RedSteelToolMaterial).setUnlocalizedName("Red Steel Sword").setMaxDamage(RedSteelUses);
        RoseGoldSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","RoseGoldSword",16254),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Sword").setMaxDamage(RoseGoldUses);
        SteelSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","SteelSword",16255),SteelToolMaterial).setUnlocalizedName("Steel Sword").setMaxDamage(SteelUses);
        TinSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","TinSword",16256),TinToolMaterial).setUnlocalizedName("Tin Sword").setMaxDamage(TinUses);
        ZincSword = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","ZincSword",16257),ZincToolMaterial).setUnlocalizedName("Zinc Sword").setMaxDamage(ZincUses);

        BismuthMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BismuthMace",16262),BismuthToolMaterial).setUnlocalizedName("Bismuth Mace").setMaxDamage(BismuthUses);
        BismuthBronzeMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BismuthBronzeMace",16263),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Mace").setMaxDamage(BismuthBronzeUses);
        BlackBronzeMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlackBronzeMace",16264),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Mace").setMaxDamage(BlackBronzeUses);
        BlackSteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlackSteelMace",16265),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Mace").setMaxDamage(BlackSteelUses);
        BlueSteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlueSteelMace",16266),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Mace").setMaxDamage(BlueSteelUses);
        BronzeMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BronzeMace",16267),BronzeToolMaterial).setUnlocalizedName("Bronze Mace").setMaxDamage(BronzeUses);
        CopperMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","CopperMace",16268),CopperToolMaterial).setUnlocalizedName("Copper Mace").setMaxDamage(CopperUses);
        WroughtIronMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","WroughtIronMace",16269),IronToolMaterial).setUnlocalizedName("Wrought Iron Mace").setMaxDamage(WroughtIronUses);
        RedSteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","RedSteelMace",16270),RedSteelToolMaterial).setUnlocalizedName("Red Steel Mace").setMaxDamage(RedSteelUses);
        RoseGoldMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","RoseGoldMace",16271),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Mace").setMaxDamage(RoseGoldUses);
        SteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","SteelMace",16272),SteelToolMaterial).setUnlocalizedName("Steel Mace").setMaxDamage(SteelUses);
        TinMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","TinMace",16273),TinToolMaterial).setUnlocalizedName("Tin Mace").setMaxDamage(TinUses);
        ZincMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","ZincMace",16274),ZincToolMaterial).setUnlocalizedName("Zinc Mace").setMaxDamage(ZincUses);

        BismuthSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","BismuthSaw",16275),BismuthToolMaterial).setUnlocalizedName("Bismuth Saw").setMaxDamage(BismuthUses);
        BismuthBronzeSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","BismuthBronzeSaw",16276),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Saw").setMaxDamage(BismuthBronzeUses);
        BlackBronzeSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","BlackBronzeSaw",16277),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Saw").setMaxDamage(BlackBronzeUses);
        BlackSteelSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","BlackSteelSaw",16278),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Saw").setMaxDamage(BlackSteelUses);
        BlueSteelSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","BlueSteelSaw",16279),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Saw").setMaxDamage(BlueSteelUses);
        BronzeSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","BronzeSaw",16280),BronzeToolMaterial).setUnlocalizedName("Bronze Saw").setMaxDamage(BronzeUses);
        CopperSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","CopperSaw",16281),CopperToolMaterial).setUnlocalizedName("Copper Saw").setMaxDamage(CopperUses);
        WroughtIronSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","WroughtIronSaw",16282),IronToolMaterial).setUnlocalizedName("Wrought Iron Saw").setMaxDamage(WroughtIronUses);
        RedSteelSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","RedSteelSaw",16283),RedSteelToolMaterial).setUnlocalizedName("Red Steel Saw").setMaxDamage(RedSteelUses);
        RoseGoldSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","RoseGoldSaw",16284),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Saw").setMaxDamage(RoseGoldUses);
        SteelSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","SteelSaw",16285),SteelToolMaterial).setUnlocalizedName("Steel Saw").setMaxDamage(SteelUses);
        TinSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","TinSaw",16286),TinToolMaterial).setUnlocalizedName("Tin Saw").setMaxDamage(TinUses);
        ZincSaw = new ItemCustomSaw(TFC_Settings.getIntFor(config,"item","ZincSaw",16287),ZincToolMaterial).setUnlocalizedName("Zinc Saw").setMaxDamage(ZincUses);

        HCBlackSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCBlackSteelIngot",16290), EnumMetalType.BLACKSTEEL).setUnlocalizedName("HCBlackSteelIngot");
        WeakBlueSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WeakBlueSteelIngot",16291),EnumMetalType.BLUESTEEL).setUnlocalizedName("WeakBlueSteelIngot");
        WeakRedSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WeakRedSteelIngot",16292),EnumMetalType.REDSTEEL).setUnlocalizedName("WeakRedSteelIngot");
        WeakSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WeakSteelIngot",16293),EnumMetalType.STEEL).setUnlocalizedName("WeakSteelIngot");
        HCBlueSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCBlueSteelIngot",16294), EnumMetalType.BLUESTEEL).setUnlocalizedName("HCBlueSteelIngot");
        HCRedSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCRedSteelIngot",16295), EnumMetalType.REDSTEEL).setUnlocalizedName("HCRedSteelIngot");
        HCSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCSteelIngot",16296), EnumMetalType.STEEL).setUnlocalizedName("HCSteelIngot");

        OreChunk = new ItemOre(TFC_Settings.getIntFor(config,"item","OreChunk",16297)).setUnlocalizedName("Ore");
        Logs = new ItemLogs(TFC_Settings.getIntFor(config,"item","Logs",16298)).setUnlocalizedName("Log");
        Barrel = new ItemBarrels(TFC_Settings.getIntFor(config, "item", "barrels", 16299)).setUnlocalizedName("Barrel");
        
        WoodSupportItemV = new ItemWoodSupport(TFC_Settings.getIntFor(config,"item","WoodSupportItemV", 16300), true).setUnlocalizedName("WoodSupportItemV");
        WoodSupportItemH = new ItemWoodSupport(TFC_Settings.getIntFor(config,"item","WoodSupportItemH", 16301), false).setUnlocalizedName("WoodSupportItemH");
        boneIgInPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","boneIgInPick",16302),IgInToolMaterial).setUnlocalizedName("Bone IgIn Stone Pick").setMaxDamage(IgInStoneUses);
        boneIgInShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","boneIgInShovel",16303),IgInToolMaterial).setUnlocalizedName("Bone IgIn Stone Shovel").setMaxDamage(IgInStoneUses);
        boneIgInAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","boneIgInAxe",16304),IgInToolMaterial).setUnlocalizedName("Bone IgIn Stone Axe").setMaxDamage(IgInStoneUses);
        boneIgInHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","boneIgInHoe",16305),IgInToolMaterial).setUnlocalizedName("Bone IgIn Stone Hoe").setMaxDamage(IgInStoneUses);
        boneSedPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","boneSedPick",16306),SedToolMaterial).setUnlocalizedName("Bone Sed Stone Pick").setMaxDamage(SedStoneUses);
        boneSedShovel= new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","boneSedShovel",16307),SedToolMaterial).setUnlocalizedName("Bone Sed Stone Shovel").setMaxDamage(SedStoneUses);
        boneSedAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","boneSedAxe",16308),SedToolMaterial).setUnlocalizedName("Bone Sed Stone Axe").setMaxDamage(SedStoneUses);
        boneSedHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","boneSedHoe",16309),SedToolMaterial).setUnlocalizedName("Bone Sed Stone Hoe").setMaxDamage(SedStoneUses);
        boneIgExPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","boneIgExPick",16310),IgExToolMaterial).setUnlocalizedName("Bone IgEx Stone Pick").setMaxDamage(IgExStoneUses);
        boneIgExShovel= new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","boneIgExShovel",16311),IgExToolMaterial).setUnlocalizedName("Bone IgEx Stone Shovel").setMaxDamage(IgExStoneUses);
        boneIgExAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","boneIgExAxe",16312),IgExToolMaterial).setUnlocalizedName("Bone IgEx Stone Axe").setMaxDamage(IgExStoneUses);
        boneIgExHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","boneIgExHoe",16313),IgExToolMaterial).setUnlocalizedName("Bone IgEx Stone Hoe").setMaxDamage(IgExStoneUses);
        boneMMPick = new ItemCustomPickaxe(TFC_Settings.getIntFor(config,"item","boneMMPick",16314),MMToolMaterial).setUnlocalizedName("Bone MM Stone Pick").setMaxDamage(MMStoneUses);
        boneMMShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","boneMMShovel",16315),MMToolMaterial).setUnlocalizedName("Bone MM Stone Shovel").setMaxDamage(MMStoneUses);
        boneMMAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","boneMMAxe",16316),MMToolMaterial).setUnlocalizedName("Bone MM Stone Axe").setMaxDamage(MMStoneUses);
        boneMMHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","boneMMHoe",16317),MMToolMaterial).setUnlocalizedName("Bone MM Stone Hoe").setMaxDamage(MMStoneUses);
        Javelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","javelin",16318)).setUnlocalizedName("javelin");

        terraSlag = new ItemTerra(TFC_Settings.getIntFor(config,"item","terraSlag",16349),"/bioxx/terrasprites.png").setUnlocalizedName("terraSlag");

        BismuthUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBismuth",16350)).setUnlocalizedName("UnshapedBismuth");
        BismuthBronzeUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBismuthBronze",16351)).setUnlocalizedName("UnshapedBismuthBronze");
        BlackBronzeUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBlackBronze",16352)).setUnlocalizedName("UnshapedBlackBronze");
        BlackSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBlackSteel",16353)).setUnlocalizedName("UnshapedBlackSteel");
        BlueSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBlueSteel",16354)).setUnlocalizedName("UnshapedBlueSteel");
        BrassUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBrass",16355)).setUnlocalizedName("UnshapedBrass");
        BronzeUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBronze",16356)).setUnlocalizedName("UnshapedBronze");
        CopperUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedCopper",16357)).setUnlocalizedName("UnshapedCopper");
        GoldUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedGold",16358)).setUnlocalizedName("UnshapedGold");
        WroughtIronUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedIron",16359)).setUnlocalizedName("UnshapedWroughtIron");
        LeadUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedLead",16360)).setUnlocalizedName("UnshapedLead");
        NickelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedNickel",16361)).setUnlocalizedName("UnshapedNickel");
        PigIronUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedPigIron",16362)).setUnlocalizedName("UnshapedPigIron");
        PlatinumUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedPlatinum",16363)).setUnlocalizedName("UnshapedPlatinum");
        RedSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedRedSteel",16364)).setUnlocalizedName("UnshapedRedSteel");
        RoseGoldUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedRoseGold",16365)).setUnlocalizedName("UnshapedRoseGold");
        SilverUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedSilver",16366)).setUnlocalizedName("UnshapedSilver");
        SteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedSteel",16367)).setUnlocalizedName("UnshapedSteel");
        SterlingSilverUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedSterlingSilver",16368)).setUnlocalizedName("UnshapedSterlingSilver");
        TinUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedTin",16369)).setUnlocalizedName("UnshapedTin");
        ZincUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedZinc",16370)).setUnlocalizedName("UnshapedZinc");

        //Hammers
        StoneHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraStoneHammer",16371),TFCItems.IgInToolMaterial).setUnlocalizedName("Stone Hammer").setMaxDamage(TFCItems.IgInStoneUses);
        BismuthHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraBismuthHammer",16372),TFCItems.BismuthToolMaterial).setUnlocalizedName("Bismuth Hammer").setMaxDamage(TFCItems.BismuthUses);
        BismuthBronzeHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraBismuthBronzeHammer",16373),TFCItems.BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hammer").setMaxDamage(TFCItems.BismuthBronzeUses);
        BlackBronzeHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraBlackBronzeHammer",16374),TFCItems.BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hammer").setMaxDamage(TFCItems.BlackBronzeUses);
        BlackSteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraBlackSteelHammer",16375),TFCItems.BlackSteelToolMaterial).setUnlocalizedName("Black Steel Hammer").setMaxDamage(TFCItems.BlackSteelUses);
        BlueSteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraBlueSteelHammer",16376),TFCItems.BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Hammer").setMaxDamage(TFCItems.BlueSteelUses);
        BronzeHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraBronzeHammer",16377),TFCItems.BronzeToolMaterial).setUnlocalizedName("Bronze Hammer").setMaxDamage(TFCItems.BronzeUses);
        CopperHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraCopperHammer",16378),TFCItems.CopperToolMaterial).setUnlocalizedName("Copper Hammer").setMaxDamage(TFCItems.CopperUses);
        WroughtIronHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraWroughtIronHammer",16379),TFCItems.IronToolMaterial).setUnlocalizedName("Wrought Iron Hammer").setMaxDamage(TFCItems.WroughtIronUses);
        RedSteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraRedSteelHammer",16380),TFCItems.RedSteelToolMaterial).setUnlocalizedName("Red Steel Hammer").setMaxDamage(TFCItems.RedSteelUses);
        RoseGoldHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraRoseGoldHammer",16381),TFCItems.RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Hammer").setMaxDamage(TFCItems.RoseGoldUses);
        SteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraSteelHammer",16382),TFCItems.SteelToolMaterial).setUnlocalizedName("Steel Hammer").setMaxDamage(TFCItems.SteelUses);
        TinHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraTinHammer",16383),TFCItems.TinToolMaterial).setUnlocalizedName("Tin Hammer").setMaxDamage(TFCItems.TinUses);
        ZincHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","terraZincHammer",16384),TFCItems.ZincToolMaterial).setUnlocalizedName("Zinc Hammer").setMaxDamage(TFCItems.ZincUses);

        Ink = new ItemTerra(TFC_Settings.getIntFor(config,"item","Ink",16391),"/bioxx/terrasprites.png").setUnlocalizedName("Ink");

        StoneAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraStoneAnvilItem",16398), 0, AnvilReq.STONE).setUnlocalizedName("StoneAnvilItem");
        BlackSteelAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraBlackSteelAnvilItem",16399), 5, AnvilReq.BLACKSTEEL).setUnlocalizedName("BlackSteelAnvilItem");
        BlueSteelAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraBlueSteelAnvilItem",16400), 7, AnvilReq.BLUESTEEL).setUnlocalizedName("BlueSteelAnvilItem");
        BronzeAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraBronzeAnvilItem",16401), 2, AnvilReq.BRONZE).setUnlocalizedName("BronzeAnvilItem");
        CopperAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraCopperAnvilItem",16402), 1, AnvilReq.COPPER).setUnlocalizedName("CopperAnvilItem");
        WroughtIronAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraWroughtIronAnvilItem",16403), 3, AnvilReq.WROUGHTIRON).setUnlocalizedName("WroughtIronAnvilItem");
        RedSteelAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraRedSteelAnvilItem",16404), 6, AnvilReq.REDSTEEL).setUnlocalizedName("RedSteelAnvilItem");
        SteelAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","terraSteelAnvilItem",16405), 4, AnvilReq.STEEL).setUnlocalizedName("SteelAnvilItem");

        BellowsItem = new ItemBellows(TFC_Settings.getIntFor(config,"item","terraBellowsItem",16406)).setUnlocalizedName("BellowsItem");

        FireStarter = new ItemFirestarter(TFC_Settings.getIntFor(config,"item","terraFireStarter",16407)).setUnlocalizedName("FireStarter");
        ClayMold = new ItemTerra(TFC_Settings.getIntFor(config,"item","terraClayMold",16408),"/bioxx/terrasprites.png").setUnlocalizedName("ClayMold");
        CeramicMold = new ItemTerra(TFC_Settings.getIntFor(config,"item","terraFiredClayMold",16409),"/bioxx/terrasprites.png").setUnlocalizedName("FiredClayMold");
        //Tool heads
        BismuthPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthPickaxeHead",16500)).setUnlocalizedName("Bismuth Pick Head");
        BismuthBronzePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzePickaxeHead",16501)).setUnlocalizedName("Bismuth Bronze Pick Head");
        BlackBronzePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzePickaxeHead",16502)).setUnlocalizedName("Black Bronze Pick Head");
        BlackSteelPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelPickaxeHead",16503)).setUnlocalizedName("Black Steel Pick Head");
        BlueSteelPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelPickaxeHead",16504)).setUnlocalizedName("Blue Steel Pick Head");
        BronzePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzePickaxeHead",16505)).setUnlocalizedName("Bronze Pickaxe Head");
        CopperPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperPickaxeHead",16506)).setUnlocalizedName("Copper Pickaxe Head");
        WroughtIronPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronPickaxeHead",16507)).setUnlocalizedName("Wrought Iron Pick Head");
        RedSteelPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelPickaxeHead",16508)).setUnlocalizedName("Red Steel Pick Head");
        RoseGoldPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldPickaxeHead",16509)).setUnlocalizedName("Rose Gold Pick Head");
        SteelPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelPickaxeHead",16510)).setUnlocalizedName("Steel Pick Head");
        TinPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinPickaxeHead",16511)).setUnlocalizedName("Tin Pick Head");
        ZincPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincPickaxeHead",16512)).setUnlocalizedName("Zinc Pick Head");

        BismuthShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthShovelHead",16513)).setUnlocalizedName("Bismuth Shovel Head");
        BismuthBronzeShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeShovelHead",16514)).setUnlocalizedName("Bismuth Bronze Shovel Head");
        BlackBronzeShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeShovelHead",16515)).setUnlocalizedName("Black Bronze Shovel Head");
        BlackSteelShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelShovelHead",16516)).setUnlocalizedName("Black Steel Shovel Head");
        BlueSteelShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelShovelHead",16517)).setUnlocalizedName("Blue Steel Shovel Head");
        BronzeShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeShovelHead",16518)).setUnlocalizedName("Bronze Shovel Head");
        CopperShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperShovelHead",16519)).setUnlocalizedName("Copper Shovel Head");
        WroughtIronShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronShovelHead",16520)).setUnlocalizedName("Wrought Iron Shovel Head");
        RedSteelShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelShovelHead",16521)).setUnlocalizedName("Red Steel Shovel Head");
        RoseGoldShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldShovelHead",16522)).setUnlocalizedName("Rose Gold Shovel Head");
        SteelShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelShovelHead",16523)).setUnlocalizedName("Steel Shovel Head");
        TinShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinShovelHead",16524)).setUnlocalizedName("Tin Shovel Head");
        ZincShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincShovelHead",16525)).setUnlocalizedName("Zinc Shovel Head");

        BismuthHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthHoeHead",16526)).setUnlocalizedName("Bismuth Hoe Head");
        BismuthBronzeHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeHoeHead",16527)).setUnlocalizedName("Bismuth Bronze Hoe Head");
        BlackBronzeHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeHoeHead",16528)).setUnlocalizedName("Black Bronze Hoe Head");
        BlackSteelHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelHoeHead",16529)).setUnlocalizedName("Black Steel Hoe Head");
        BlueSteelHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelHoeHead",16530)).setUnlocalizedName("Blue Steel Hoe Head");
        BronzeHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeHoeHead",16531)).setUnlocalizedName("Bronze Hoe Head");
        CopperHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperHoeHead",16532)).setUnlocalizedName("Copper Hoe Head");
        WroughtIronHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronHoeHead",16533)).setUnlocalizedName("Wrought Iron Hoe Head");
        RedSteelHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelHoeHead",16534)).setUnlocalizedName("Red Steel Hoe Head");
        RoseGoldHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldHoeHead",16535)).setUnlocalizedName("Rose Gold Hoe Head");
        SteelHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelHoeHead",16536)).setUnlocalizedName("Steel Hoe Head");
        TinHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinHoeHead",16537)).setUnlocalizedName("Tin Hoe Head");
        ZincHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincHoeHead",16538)).setUnlocalizedName("Zinc Hoe Head");

        BismuthAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthAxeHead",16539)).setUnlocalizedName("Bismuth Axe Head");
        BismuthBronzeAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeAxeHead",16540)).setUnlocalizedName("Bismuth Bronze Axe Head");
        BlackBronzeAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeAxeHead",16541)).setUnlocalizedName("Black Bronze Axe Head");
        BlackSteelAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelAxeHead",16542)).setUnlocalizedName("Black Steel Axe Head");
        BlueSteelAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelAxeHead",16543)).setUnlocalizedName("Blue Steel Axe Head");
        BronzeAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeAxeHead",16544)).setUnlocalizedName("Bronze Axe Head");
        CopperAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperAxeHead",16545)).setUnlocalizedName("Copper Axe Head");
        WroughtIronAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronAxeHead",16546)).setUnlocalizedName("Wrought Iron Axe Head");
        RedSteelAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelAxeHead",16547)).setUnlocalizedName("Red Steel Axe Head");
        RoseGoldAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldAxeHead",16548)).setUnlocalizedName("Rose Gold Axe Head");
        SteelAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelAxeHead",16549)).setUnlocalizedName("Steel Axe Head");
        TinAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinAxeHead",16550)).setUnlocalizedName("Tin Axe Head");
        ZincAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincAxeHead",16551)).setUnlocalizedName("Zinc Axe Head");

        BismuthHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthHammerHead",16552)).setUnlocalizedName("Bismuth Hammer Head");
        BismuthBronzeHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeHammerHead",16553)).setUnlocalizedName("Bismuth Bronze Hammer Head");
        BlackBronzeHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeHammerHead",16554)).setUnlocalizedName("Black Bronze Hammer Head");
        BlackSteelHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelHammerHead",16555)).setUnlocalizedName("Black Steel Hammer Head");
        BlueSteelHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelHammerHead",16556)).setUnlocalizedName("Blue Steel Hammer Head");
        BronzeHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeHammerHead",16557)).setUnlocalizedName("Bronze Hammer Head");
        CopperHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperHammerHead",16558)).setUnlocalizedName("Copper Hammer Head");
        WroughtIronHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronHammerHead",16559)).setUnlocalizedName("Wrought Iron Hammer Head");
        RedSteelHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelHammerHead",16560)).setUnlocalizedName("Red Steel Hammer Head");
        RoseGoldHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldHammerHead",16561)).setUnlocalizedName("Rose Gold Hammer Head");
        SteelHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelHammerHead",16562)).setUnlocalizedName("Steel Hammer Head");
        TinHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinHammerHead",16563)).setUnlocalizedName("Tin Hammer Head");
        ZincHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincHammerHead",16564)).setUnlocalizedName("Zinc Hammer Head");

        //chisel heads
        BismuthChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthChiselHead",16565)).setUnlocalizedName("Bismuth Chisel Head");
        BismuthBronzeChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeChiselHead",16566)).setUnlocalizedName("Bismuth Bronze Chisel Head");
        BlackBronzeChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeChiselHead",16567)).setUnlocalizedName("Black Bronze Chisel Head");
        BlackSteelChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelChiselHead",16568)).setUnlocalizedName("Black Steel Chisel Head");
        BlueSteelChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelChiselHead",16569)).setUnlocalizedName("Blue Steel Chisel Head");
        BronzeChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeChiselHead",16570)).setUnlocalizedName("Bronze Chisel Head");
        CopperChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperChiselHead",16571)).setUnlocalizedName("Copper Chisel Head");
        WroughtIronChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronChiselHead",16572)).setUnlocalizedName("Wrought Iron Chisel Head");
        RedSteelChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelChiselHead",16573)).setUnlocalizedName("Red Steel Chisel Head");
        RoseGoldChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldChiselHead",16574)).setUnlocalizedName("Rose Gold Chisel Head");
        SteelChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelChiselHead",16575)).setUnlocalizedName("Steel Chisel Head");
        TinChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinChiselHead",16576)).setUnlocalizedName("Tin Chisel Head");
        ZincChiselHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincChiselHead",16577)).setUnlocalizedName("Zinc Chisel Head");

        BismuthSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthSwordHead",16578)).setUnlocalizedName("Bismuth Sword Blade");
        BismuthBronzeSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeSwordHead",16579)).setUnlocalizedName("Bismuth Bronze Sword Blade");
        BlackBronzeSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeSwordHead",16580)).setUnlocalizedName("Black Bronze Sword Blade");
        BlackSteelSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelSwordHead",16581)).setUnlocalizedName("Black Steel Sword Blade");
        BlueSteelSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelSwordHead",16582)).setUnlocalizedName("Blue Steel Sword Blade");
        BronzeSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeSwordHead",16583)).setUnlocalizedName("Bronze Sword Blade");
        CopperSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperSwordHead",16584)).setUnlocalizedName("Copper Sword Blade");
        WroughtIronSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronSwordHead",16585)).setUnlocalizedName("Wrought Iron Sword Blade");
        RedSteelSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelSwordHead",16586)).setUnlocalizedName("Red Steel Sword Blade");
        RoseGoldSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldSwordHead",16587)).setUnlocalizedName("Rose Gold Sword Blade");
        SteelSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelSwordHead",16588)).setUnlocalizedName("Steel Sword Blade");
        TinSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinSwordHead",16589)).setUnlocalizedName("Tin Sword Blade");
        ZincSwordHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincSwordHead",16590)).setUnlocalizedName("Zinc Sword Blade");

        BismuthMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthMaceHead",16591)).setUnlocalizedName("Bismuth Mace Head");
        BismuthBronzeMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeMaceHead",16592)).setUnlocalizedName("Bismuth Bronze Mace Head");
        BlackBronzeMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeMaceHead",16593)).setUnlocalizedName("Black Bronze Mace Head");
        BlackSteelMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelMaceHead",16594)).setUnlocalizedName("Black Steel Mace Head");
        BlueSteelMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelMaceHead",16595)).setUnlocalizedName("Blue Steel Mace Head");
        BronzeMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeMaceHead",16596)).setUnlocalizedName("Bronze Mace Head");
        CopperMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperMaceHead",16597)).setUnlocalizedName("Copper Mace Head");
        WroughtIronMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronMaceHead",16598)).setUnlocalizedName("Wrought Iron Mace Head");
        RedSteelMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelMaceHead",16599)).setUnlocalizedName("Red Steel Mace Head");
        RoseGoldMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldMaceHead",16600)).setUnlocalizedName("Rose Gold Mace Head");
        SteelMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelMaceHead",16601)).setUnlocalizedName("Steel Mace Head");
        TinMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinMaceHead",16602)).setUnlocalizedName("Tin Mace Head");
        ZincMaceHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincMaceHead",16603)).setUnlocalizedName("Zinc Mace Head");

        BismuthSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthSawHead",16604)).setUnlocalizedName("Bismuth Saw Blade");
        BismuthBronzeSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeSawHead",16605)).setUnlocalizedName("Bismuth Bronze Saw Blade");
        BlackBronzeSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeSawHead",16606)).setUnlocalizedName("Black Bronze Saw Blade");
        BlackSteelSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelSawHead",16607)).setUnlocalizedName("Black Steel Saw Blade");
        BlueSteelSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelSawHead",16608)).setUnlocalizedName("Blue Steel Saw Blade");
        BronzeSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeSawHead",16609)).setUnlocalizedName("Bronze Saw Blade");
        CopperSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperSawHead",16610)).setUnlocalizedName("Copper Saw Blade");
        WroughtIronSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronSawHead",16611)).setUnlocalizedName("Wrought Iron Saw Blade");
        RedSteelSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelSawHead",16612)).setUnlocalizedName("Red Steel Saw Blade");
        RoseGoldSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldSawHead",16613)).setUnlocalizedName("Rose Gold Saw Blade");
        SteelSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelSawHead",16614)).setUnlocalizedName("Steel Saw Blade");
        TinSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinSawHead",16615)).setUnlocalizedName("Tin Saw Blade");
        ZincSawHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincSawHead",16616)).setUnlocalizedName("Zinc Saw Blade");
        
        HCBlackSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCBlackSteel",16617)).setUnlocalizedName("UnshapedHCBlackSteel");
        WeakBlueSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedWeakBlueSteel",16618)).setUnlocalizedName("UnshapedWeakBlueSteel");
        HCBlueSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCBlueSteel",16619)).setUnlocalizedName("UnshapedHCBlueSteel");
        WeakRedSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedWeakRedSteel",16621)).setUnlocalizedName("UnshapedWeakRedSteel");
        HCRedSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCRedSteel",16622)).setUnlocalizedName("UnshapedHCRedSteel");
        WeakSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedWeakSteel",16623)).setUnlocalizedName("UnshapedWeakSteel");
        HCSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCSteel",16624)).setUnlocalizedName("UnshapedHCSteel");
        Coke = ((ItemTerra) new ItemTerra(TFC_Settings.getIntFor(config,"item","Coke",16625)).setUnlocalizedName("coke"));

        BismuthProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthProPickHead",16626)).setUnlocalizedName("Bismuth ProPick Head");
        BismuthBronzeProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeProPickHead",16627)).setUnlocalizedName("Bismuth Bronze ProPick Head");
        BlackBronzeProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeProPickHead",16628)).setUnlocalizedName("Black Bronze ProPick Head");
        BlackSteelProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelProPickHead",16629)).setUnlocalizedName("Black Steel ProPick Head");
        BlueSteelProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelProPickHead",16630)).setUnlocalizedName("Blue Steel ProPick Head");
        BronzeProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeProPickHead",16631)).setUnlocalizedName("Bronze ProPick Head");
        CopperProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperProPickHead",16632)).setUnlocalizedName("Copper ProPick Head");
        WroughtIronProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronProPickHead",16633)).setUnlocalizedName("Wrought Iron ProPick Head");
        RedSteelProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelProPickHead",16634)).setUnlocalizedName("Red Steel ProPick Head");
        RoseGoldProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldProPickHead",16635)).setUnlocalizedName("Rose Gold ProPick Head");
        SteelProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelProPickHead",16636)).setUnlocalizedName("Steel ProPick Head");
        TinProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinProPickHead",16637)).setUnlocalizedName("Tin ProPick Head");
        ZincProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincProPickHead",16638)).setUnlocalizedName("Zinc ProPick Head");
        
        Flux = ((ItemTerra) new ItemTerra(TFC_Settings.getIntFor(config,"item","Flux",16639)).setUnlocalizedName("flux"));
        BismuthBronzeAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","BismuthBronzeAnvilItem",16640), 0, AnvilReq.BISMUTHBRONZE).setUnlocalizedName("BismuthBronzeAnvilItem");
        BlackBronzeAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","BlackBronzeAnvilItem",16641), 1, AnvilReq.BLACKBRONZE).setUnlocalizedName("BlackBronzeAnvilItem");
        RoseGoldAnvilItem = new ItemAnvil(TFC_Settings.getIntFor(config,"item","RoseGoldAnvilItem",16642), 2, AnvilReq.ROSEGOLD).setUnlocalizedName("RoseGoldAnvilItem");

        /**
         * Scythe
         * */
        int num = 16643;
        BismuthScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","BismuthScythe",num),BismuthToolMaterial).setUnlocalizedName("Bismuth Scythe").setMaxDamage(BismuthUses);num++;
        BismuthBronzeScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","BismuthBronzeScythe",num),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Scythe").setMaxDamage(BismuthBronzeUses);num++;
        BlackBronzeScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","BlackBronzeScythe",num),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Scythe").setMaxDamage(BlackBronzeUses);num++;
        BlackSteelScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","BlackSteelScythe",num),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Scythe").setMaxDamage(BlackSteelUses);num++;
        BlueSteelScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","BlueSteelScythe",num),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Scythe").setMaxDamage(BlueSteelUses);num++;
        BronzeScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","BronzeScythe",num),BronzeToolMaterial).setUnlocalizedName("Bronze Scythe").setMaxDamage(BronzeUses);num++;
        CopperScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","CopperScythe",num),CopperToolMaterial).setUnlocalizedName("Copper Scythe").setMaxDamage(CopperUses);num++;
        WroughtIronScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","WroughtIronScythe",num),IronToolMaterial).setUnlocalizedName("Wrought Iron Scythe").setMaxDamage(WroughtIronUses);num++;
        RedSteelScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","RedSteelScythe",num),RedSteelToolMaterial).setUnlocalizedName("Red Steel Scythe").setMaxDamage(RedSteelUses);num++;
        RoseGoldScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","RoseGoldScythe",num),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Scythe").setMaxDamage(RoseGoldUses);num++;
        SteelScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","SteelScythe",num),SteelToolMaterial).setUnlocalizedName("Steel Scythe").setMaxDamage(SteelUses);num++;
        TinScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","TinScythe",num),TinToolMaterial).setUnlocalizedName("Tin Scythe").setMaxDamage(TinUses);num++;
        ZincScythe = new ItemCustomScythe(TFC_Settings.getIntFor(config,"item","ZincScythe",num),ZincToolMaterial).setUnlocalizedName("Zinc Scythe").setMaxDamage(ZincUses);num++;
        
        BismuthScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthScytheHead",num)).setUnlocalizedName("Bismuth Scythe Blade");num++;
        BismuthBronzeScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeScytheHead",num)).setUnlocalizedName("Bismuth Bronze Scythe Blade");num++;
        BlackBronzeScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeScytheHead",num)).setUnlocalizedName("Black Bronze Scythe Blade");num++;
        BlackSteelScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelScytheHead",num)).setUnlocalizedName("Black Steel Scythe Blade");num++;
        BlueSteelScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelScytheHead",num)).setUnlocalizedName("Blue Steel Scythe Blade");num++;
        BronzeScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeScytheHead",num)).setUnlocalizedName("Bronze Scythe Blade");num++;
        CopperScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperScytheHead",num)).setUnlocalizedName("Copper Scythe Blade");num++;
        WroughtIronScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronScytheHead",num)).setUnlocalizedName("Wrought Iron Scythe Blade");num++;
        RedSteelScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelScytheHead",num)).setUnlocalizedName("Red Steel Scythe Blade");num++;
        RoseGoldScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldScytheHead",num)).setUnlocalizedName("Rose Gold Scythe Blade");num++;
        SteelScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelScytheHead",num)).setUnlocalizedName("Steel Scythe Blade");num++;
        TinScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinScytheHead",num)).setUnlocalizedName("Tin Scythe Blade");num++;
        ZincScytheHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincScytheHead",num)).setUnlocalizedName("Zinc Scythe Blade");num++;
        
        WoodenBucketEmpty = (new ItemCustomBucket(TFC_Settings.getIntFor(config,"item","WoodenBucketEmpty",num), 0)).setUnlocalizedName("WoodenBucketEmpty");num++;
        WoodenBucketWater = (new ItemCustomBucket(TFC_Settings.getIntFor(config,"item","WoodenBucketWater",num), TFCBlocks.finiteWater.blockID)).setUnlocalizedName("WoodenBucketWater").setContainerItem(WoodenBucketEmpty);num++;
        WoodenBucketMilk = (new ItemCustomBucketMilk(TFC_Settings.getIntFor(config,"item","WoodenBucketMilk",num))).setUnlocalizedName("WoodenBucketMilk").setContainerItem(WoodenBucketEmpty);num++;
        
        BismuthKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthKnifeHead",num)).setUnlocalizedName("Bismuth Knife Blade");num++;
        BismuthBronzeKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeKnifeHead",num)).setUnlocalizedName("Bismuth Bronze Knife Blade");num++;
        BlackBronzeKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeKnifeHead",num)).setUnlocalizedName("Black Bronze Knife Blade");num++;
        BlackSteelKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelKnifeHead",num)).setUnlocalizedName("Black Steel Knife Blade");num++;
        BlueSteelKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelKnifeHead",num)).setUnlocalizedName("Blue Steel Knife Blade");num++;
        BronzeKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeKnifeHead",num)).setUnlocalizedName("Bronze Knife Blade");num++;
        CopperKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperKnifeHead",num)).setUnlocalizedName("Copper Knife Blade");num++;
        WroughtIronKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronKnifeHead",num)).setUnlocalizedName("Wrought Iron Knife Blade");num++;
        RedSteelKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelKnifeHead",num)).setUnlocalizedName("Red Steel Knife Blade");num++;
        RoseGoldKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldKnifeHead",num)).setUnlocalizedName("Rose Gold Knife Blade");num++;
        SteelKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelKnifeHead",num)).setUnlocalizedName("Steel Knife Blade");num++;
        TinKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","TinKnifeHead",num)).setUnlocalizedName("Tin Knife Blade");num++;
        ZincKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincKnifeHead",num)).setUnlocalizedName("Zinc Knife Blade");num++;
        
        BismuthKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","BismuthKnife",num),BismuthToolMaterial).setUnlocalizedName("Bismuth Knife").setMaxDamage(BismuthUses);num++;
        BismuthBronzeKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","BismuthBronzeKnife",num),BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Knife").setMaxDamage(BismuthBronzeUses);num++;
        BlackBronzeKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","BlackBronzeKnife",num),BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Knife").setMaxDamage(BlackBronzeUses);num++;
        BlackSteelKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","BlackSteelKnife",num),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Knife").setMaxDamage(BlackSteelUses);num++;
        BlueSteelKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","BlueSteelKnife",num),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Knife").setMaxDamage(BlueSteelUses);num++;
        BronzeKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","BronzeKnife",num),BronzeToolMaterial).setUnlocalizedName("Bronze Knife").setMaxDamage(BronzeUses);num++;
        CopperKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","CopperKnife",num),CopperToolMaterial).setUnlocalizedName("Copper Knife").setMaxDamage(CopperUses);num++;
        WroughtIronKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","WroughtIronKnife",num),IronToolMaterial).setUnlocalizedName("Wrought Iron Knife").setMaxDamage(WroughtIronUses);num++;
        RedSteelKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","RedSteelKnife",num),RedSteelToolMaterial).setUnlocalizedName("Red Steel Knife").setMaxDamage(RedSteelUses);num++;
        RoseGoldKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","RoseGoldKnife",num),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Knife").setMaxDamage(RoseGoldUses);num++;
        SteelKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","SteelKnife",num),SteelToolMaterial).setUnlocalizedName("Steel Knife").setMaxDamage(SteelUses);num++;
        TinKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","TinKnife",num),TinToolMaterial).setUnlocalizedName("Tin Knife").setMaxDamage(TinUses);num++;
        ZincKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","ZincKnife",num),ZincToolMaterial).setUnlocalizedName("Zinc Knife").setMaxDamage(ZincUses);num++;
        
        LooseRock = ((ItemTerra) new ItemLooseRock(TFC_Settings.getIntFor(config,"item","LooseRock",num)).setUnlocalizedName("LooseRock"));num++;
        
        FlatRock = ((ItemTerra) new ItemFlatRock(TFC_Settings.getIntFor(config,"item","FlatRock",num)).setUnlocalizedName("FlatRock"));num++;

        
        IgInStonePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgInStonePickaxeHead",num)).setUnlocalizedName("Stone Pickaxe Head");num++;
        SedStonePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SedStonePickaxeHead",num)).setUnlocalizedName("Stone Pickaxe Head");num++;
        IgExStonePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgExStonePickaxeHead",num)).setUnlocalizedName("Stone Pickaxe Head");num++;
        MMStonePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","MMStonePickaxeHead",num)).setUnlocalizedName("Stone Pickaxe Head");num++;
        
        IgInStoneShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgInStoneShovelHead",num)).setUnlocalizedName("Stone Shovel Head");num++;
        SedStoneShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SedStoneShovelHead",num)).setUnlocalizedName("Stone Shovel Head");num++;
        IgExStoneShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgExStoneShovelHead",num)).setUnlocalizedName("Stone Shovel Head");num++;
        MMStoneShovelHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","MMStoneShovelHead",num)).setUnlocalizedName("Stone Shovel Head");num++;
        
        IgInStoneAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgInStoneAxeHead",num)).setUnlocalizedName("Stone Axe Head");num++;
        SedStoneAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SedStoneAxeHead",num)).setUnlocalizedName("Stone Axe Head");num++;
        IgExStoneAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgExStoneAxeHead",num)).setUnlocalizedName("Stone Axe Head");num++;
        MMStoneAxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","MMStoneAxeHead",num)).setUnlocalizedName("Stone Axe Head");num++;
        
        IgInStoneHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgInStoneHoeHead",num)).setUnlocalizedName("Stone Hoe Head");num++;
        SedStoneHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SedStoneHoeHead",num)).setUnlocalizedName("Stone Hoe Head");num++;
        IgExStoneHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgExStoneHoeHead",num)).setUnlocalizedName("Stone Hoe Head");num++;
        MMStoneHoeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","MMStoneHoeHead",num)).setUnlocalizedName("Stone Hoe Head");num++;
        
        StoneKnifeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","StoneKnifeHead",num)).setUnlocalizedName("Stone Knife Blade");num++;
        StoneHammerHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","StoneHammerHead",num)).setUnlocalizedName("Stone Hammer Head");num++;
        StoneProPickHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","StoneProPickHead",num)).setUnlocalizedName("Stone ProPick Head");num++;
        
        StoneKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","StoneKnife",num),IgExToolMaterial).setUnlocalizedName("Stone Knife").setMaxDamage(IgExStoneUses);num++;
        SmallOreChunk = new ItemOreSmall(TFC_Settings.getIntFor(config,"item","SmallOreChunk",num++)).setUnlocalizedName("Small Ore");
        SinglePlank = new ItemPlank(TFC_Settings.getIntFor(config,"item","SinglePlank",num++)).setUnlocalizedName("SinglePlank");
        
        RedSteelBucketEmpty = (new ItemCustomRedSteelBucket(TFC_Settings.getIntFor(config,"item","RedSteelBucketEmpty",num++), 0)).setUnlocalizedName("RedSteelBucketEmpty");
        RedSteelBucketWater = (new ItemCustomRedSteelBucket(TFC_Settings.getIntFor(config,"item","RedSteelBucketWater",num++), Block.waterMoving.blockID)).setUnlocalizedName("RedSteelBucketWater");
        
        BlueSteelBucketEmpty = (new ItemCustomBlueSteelBucket(TFC_Settings.getIntFor(config,"item","BlueSteelBucketEmpty",num++), 0)).setUnlocalizedName("BlueSteelBucketEmpty");
        BlueSteelBucketLava = (new ItemCustomBlueSteelBucket(TFC_Settings.getIntFor(config,"item","BlueSteelBucketLava",num++), Block.lavaMoving.blockID)).setUnlocalizedName("BlueSteelBucketLava");
        
        Quern = new ItemTerra(TFC_Settings.getIntFor(config,"item","Quern",num++),"/bioxx/terrasprites.png").setUnlocalizedName("Quern").setMaxDamage(250);
        FlintSteel = new ItemFlintSteel(TFC_Settings.getIntFor(config,"item","FlintSteel",num++)).setUnlocalizedName("flintAndSteel").setMaxDamage(200);
        
        DoorOak = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorOak", num++), 0).setUnlocalizedName("Door Oak");
		DoorAspen = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorAspen", num++), 1).setUnlocalizedName("Door Aspen");
		DoorBirch = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorBirch", num++), 2).setUnlocalizedName("Door Birch");
		DoorChestnut = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorChestnut", num++), 3).setUnlocalizedName("Door Chestnut");
		DoorDouglasFir = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorDouglasFir", num++), 4).setUnlocalizedName("Door Douglas Fir");
		DoorHickory = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorHickory", num++), 5).setUnlocalizedName("Door Hickory");
		DoorMaple = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorMaple", num++), 6).setUnlocalizedName("Door Maple");
		DoorAsh = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorAsh", num++), 7).setUnlocalizedName("Door Ash");
		DoorPine = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorPine", num++), 8).setUnlocalizedName("Door Pine");
		DoorSequoia = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorSequoia", num++), 9).setUnlocalizedName("Door Sequoia");
		DoorSpruce = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorSpruce", num++), 10).setUnlocalizedName("Door Spruce");
		DoorSycamore = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorSycamore", num++), 11).setUnlocalizedName("Door Sycamore");
		DoorWhiteCedar = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorWhiteCedar", num++), 12).setUnlocalizedName("Door White Cedar");
		DoorWhiteElm = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorWhiteElm", num++), 13).setUnlocalizedName("Door White Elm");
		DoorWillow = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorWillow", num++), 14).setUnlocalizedName("Door Willow");
		DoorKapok = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorKapok", num++), 15).setUnlocalizedName("Door Kapok");
		
		Blueprint = new ItemBlueprint(TFC_Settings.getIntFor(config,"item","Blueprint", num++));
		writabeBookTFC = new ItemWritableBookTFC(TFC_Settings.getIntFor(config,"item","WritableBookTFC", num++),"Fix Me I'm Broken").setUnlocalizedName("book");
		WoolYarn = new ItemTerra(TFC_Settings.getIntFor(config, "item", "WoolYarn", num++),"/bioxx/terrasprites2.png").setUnlocalizedName("WoolYarn").setCreativeTab(TFCTabs.TFCMaterials);
		Wool = new ItemTerra(TFC_Settings.getIntFor(config,"item","Wool",num++),"/bioxx/terrasprites2.png").setUnlocalizedName("Wool").setCreativeTab(TFCTabs.TFCMaterials);
		WoolCloth = new ItemTerra(TFC_Settings.getIntFor(config, "item", "WoolCloth", num++),"/bioxx/terrasprites2.png").setUnlocalizedName("WoolCloth").setCreativeTab(TFCTabs.TFCMaterials);
		Spindle = new ItemSpindle(TFC_Settings.getIntFor(config,"item","Spindle",num++),SedToolMaterial).setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCMaterials);
		ClaySpindle = new ItemTerra(TFC_Settings.getIntFor(config, "item", "ClaySpindle", num++),"/bioxx/terratools.png").setUnlocalizedName("ClaySpindle").setCreativeTab(TFCTabs.TFCMaterials);
		SpindleHead = new ItemTerra(TFC_Settings.getIntFor(config, "item", "SpindleHead", num++),"/bioxx/terratoolheads.png").setUnlocalizedName("SpindleHead").setCreativeTab(TFCTabs.TFCMaterials);
		StoneBrick = ((ItemTerra) new ItemStoneBrick(TFC_Settings.getIntFor(config,"item","ItemStoneBrick2",num++)).setUnlocalizedName("ItemStoneBrick"));
		Mortar = new ItemTerra(TFC_Settings.getIntFor(config,"item","Mortar",num++),"/bioxx/terratools.png").setUnlocalizedName("Mortar").setCreativeTab(TFCTabs.TFCMaterials);
		Limewater = new ItemCustomBucket(TFC_Settings.getIntFor(config,"item","Limewater",num++),0).setUnlocalizedName("LimeWater").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCMaterials);
		Hide = new ItemTerra(TFC_Settings.getIntFor(config,"item","Hide",num++),"/bioxx/terratools.png").setUnlocalizedName("Hide").setCreativeTab(TFCTabs.TFCMaterials);
		SoakedHide = new ItemTerra(TFC_Settings.getIntFor(config,"item","SoakedHide",num++),"/bioxx/terratools.png").setUnlocalizedName("SoakedHide").setCreativeTab(TFCTabs.TFCMaterials);
		ScrapedHide = new ItemTerra(TFC_Settings.getIntFor(config,"item","ScrapedHide",num++),"/bioxx/terratools.png").setUnlocalizedName("ScrapedHide").setCreativeTab(TFCTabs.TFCMaterials);
		PrepHide = new ItemTerra(TFC_Settings.getIntFor(config,"item","PrepHide",num++),"/bioxx/terratools.png").setUnlocalizedName("PrepHide").setCreativeTab(TFCTabs.TFCMaterials);
		TerraLeather = new ItemTerra(TFC_Settings.getIntFor(config,"item","TFCLeather",num++),"/bioxx/terratools.png").setUnlocalizedName("TFCLeather").setCreativeTab(TFCTabs.TFCMaterials);
		SheepSkin = new ItemTerra(TFC_Settings.getIntFor(config,"item","SheepSkin",num++),"/bioxx/terratools.png").setUnlocalizedName("SheepSkin").setCreativeTab(TFCTabs.TFCMaterials);
		muttonRaw = new ItemTerra(TFC_Settings.getIntFor(config,"item","muttonRaw",num++),"/bioxx/FoodSprites.png").setUnlocalizedName("muttonRaw");
		muttonCooked =  new ItemTerraFood(TFC_Settings.getIntFor(config,"item","muttonCooked",num++), 40, 0.8F, true,"/bioxx/FoodSprites.png", 48).setUnlocalizedName("muttonCooked");
        FlatLeather = ((ItemTerra) new ItemFlatLeather(TFC_Settings.getIntFor(config,"items","FlatLeather2",num++)).setUnlocalizedName("FlatLeather"));
        
        
		/**Plans*/
        num = 20000;
        SetupPlans(num);
        
        /**Food related items*/
        num = 18000;
        SetupFood(num);

        /**Armor Crafting related items*/
        num = 19000;
        SetupArmor(num);
        
        Recipes.Doors = new Item[]{DoorOak, DoorAspen, DoorBirch, DoorChestnut, DoorDouglasFir, 
        		DoorHickory, DoorMaple, DoorAsh, DoorPine, DoorSequoia, DoorSpruce, DoorSycamore, 
        		DoorWhiteCedar, DoorWhiteElm, DoorWillow, DoorKapok};
        
        Recipes.Axes = new Item[]{SedAxe,IgInAxe,IgExAxe,MMAxe,
                BismuthAxe,BismuthBronzeAxe,BlackBronzeAxe,
                BlackSteelAxe,BlueSteelAxe,BronzeAxe,CopperAxe,
                WroughtIronAxe,RedSteelAxe,RoseGoldAxe,SteelAxe,
                TinAxe,ZincAxe,
                boneSedAxe,boneIgInAxe,boneIgExAxe,boneMMAxe};
        
        Recipes.Chisels = new Item[]{BismuthChisel,BismuthBronzeChisel,BlackBronzeChisel,
            BlackSteelChisel,BlueSteelChisel,BronzeChisel,CopperChisel,
            WroughtIronChisel,RedSteelChisel,RoseGoldChisel,SteelChisel,
            TinChisel,ZincChisel};
        
        Recipes.Saws = new Item[]{BismuthSaw,BismuthBronzeSaw,BlackBronzeSaw,
                BlackSteelSaw,BlueSteelSaw,BronzeSaw,CopperSaw,
                WroughtIronSaw,RedSteelSaw,RoseGoldSaw,SteelSaw,
                TinSaw,ZincSaw};
        
        Recipes.Knives = new Item[]{StoneKnife,BismuthKnife,BismuthBronzeKnife,BlackBronzeKnife,
                BlackSteelKnife,BlueSteelKnife,BronzeKnife,CopperKnife,
                WroughtIronKnife,RedSteelKnife,RoseGoldKnife,SteelKnife,
                TinKnife,ZincKnife};
        
        Recipes.MeltedMetal = new Item[]{BismuthUnshaped, BismuthBronzeUnshaped,BlackBronzeUnshaped,
                TFCItems.BlackSteelUnshaped,TFCItems.BlueSteelUnshaped,TFCItems.BrassUnshaped,TFCItems.BronzeUnshaped,
                TFCItems.CopperUnshaped,TFCItems.GoldUnshaped,
                TFCItems.WroughtIronUnshaped,TFCItems.LeadUnshaped,TFCItems.NickelUnshaped,TFCItems.PigIronUnshaped,
                TFCItems.PlatinumUnshaped,TFCItems.RedSteelUnshaped,TFCItems.RoseGoldUnshaped,TFCItems.SilverUnshaped,
                TFCItems.SteelUnshaped,TFCItems.SterlingSilverUnshaped,
                TFCItems.TinUnshaped,TFCItems.ZincUnshaped, TFCItems.HCSteelUnshaped, TFCItems.WeakSteelUnshaped,
                TFCItems.HCBlackSteelUnshaped, TFCItems.HCBlueSteelUnshaped, TFCItems.HCRedSteelUnshaped, 
                TFCItems.WeakBlueSteelUnshaped, TFCItems.WeakRedSteelUnshaped};
            
        Recipes.Hammers  = new Item[]{TFCItems.StoneHammer,TFCItems.BismuthHammer,TFCItems.BismuthBronzeHammer,TFCItems.BlackBronzeHammer,
                TFCItems.BlackSteelHammer,TFCItems.BlueSteelHammer,TFCItems.BronzeHammer,TFCItems.CopperHammer,
                TFCItems.WroughtIronHammer,TFCItems.RedSteelHammer,TFCItems.RoseGoldHammer,TFCItems.SteelHammer,
                TFCItems.TinHammer,TFCItems.ZincHammer};
        
       Recipes.Spindle = new Item[]{TFCItems.Spindle};
        
        Recipes.Gems  = new Item[]{TFCItems.GemAgate, TFCItems.GemAmethyst, TFCItems.GemBeryl, TFCItems.GemDiamond, TFCItems.GemEmerald, TFCItems.GemGarnet, 
        		TFCItems.GemJade, TFCItems.GemJasper, TFCItems.GemOpal,TFCItems.GemRuby,TFCItems.GemSapphire,TFCItems.GemTopaz,TFCItems.GemTourmaline};
        
        Meals = new Item[]{MealMoveSpeed, MealDigSpeed, MealDamageBoost, MealJump, MealDamageResist, 
            	MealFireResist, MealWaterBreathing, MealNightVision};
        
        ((TFCTabs)TFCTabs.TFCTools).setTabIconItemIndex(TFCItems.RoseGoldHammer.itemID);
        ((TFCTabs)TFCTabs.TFCMaterials).setTabIconItemIndex(TFCItems.Spindle.itemID);
        ((TFCTabs)TFCTabs.TFCUnfinished).setTabIconItemIndex(TFCItems.RoseGoldHammerHead.itemID);
        ((TFCTabs)TFCTabs.TFCArmor).setTabIconItemIndex(TFCItems.SteelHelmet.itemID);
        
        System.out.println(new StringBuilder().append("[TFC] Done Loading Items").toString());
        if (config != null) {
            config.save();
        }
    }
    
    public static void SetupPlans(int num)
    {
    	PickaxeHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","PickaxeHeadPlan",num)).setUnlocalizedName("PickaxeHeadPlan");num++;
        ShovelHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","ShovelHeadPlan",num)).setUnlocalizedName("ShovelHeadPlan");num++;
        HoeHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","HoeHeadPlan",num)).setUnlocalizedName("HoeHeadPlan");num++;
        AxeHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","AxeHeadPlan",num)).setUnlocalizedName("AxeHeadPlan");num++;
        HammerHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","HammerHeadPlan",num)).setUnlocalizedName("HammerHeadPlan");num++;
        ChiselHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","ChiselHeadPlan",num)).setUnlocalizedName("ChiselHeadPlan");num++;
        SwordBladePlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","SwordBladePlan",num)).setUnlocalizedName("SwordBladePlan");num++;
        MaceHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","MaceHeadPlan",num)).setUnlocalizedName("MaceHeadPlan");num++;
        SawBladePlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","SawBladePlan",num)).setUnlocalizedName("SawBladePlan");num++;
        ProPickHeadPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","ProPickHeadPlan",num)).setUnlocalizedName("ProPickHeadPlan");num++;
        HelmetPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","HelmetPlan",num)).setUnlocalizedName("HelmetPlan");num++;
        ChestplatePlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","ChestplatePlan",num)).setUnlocalizedName("ChestplatePlan");num++;
        GreavesPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","GreavesPlan",num)).setUnlocalizedName("GreavesPlan");num++;
        BootsPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","BootsPlan",num)).setUnlocalizedName("BootsPlan");num++;
        ScythePlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","ScythePlan",num)).setUnlocalizedName("ScythePlan");num++;
        KnifePlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","KnifePlan",num)).setUnlocalizedName("KnifePlan");num++;
        BucketPlan = new ItemMiscTool(TFC_Settings.getIntFor(config,"item","BucketPlan",num)).setUnlocalizedName("BucketPlan");num++;
    }
     
    public static void SetupFood(int num)
    {
    	FruitTreeSapling1 = new ItemFruitTreeSapling(TFC_Settings.getIntFor(config,"item","FruitSapling1", num), TFC_Textures.VegetationSheet, 0).setUnlocalizedName("FruitSapling1");num++;
        FruitTreeSapling2 = new ItemFruitTreeSapling(TFC_Settings.getIntFor(config,"item","FruitSapling2", num), TFC_Textures.VegetationSheet, 8).setUnlocalizedName("FruitSapling2");num++;
        RedApple = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Red Apple",num), 15, 0.1F, false,TFC_Textures.FoodSheet, 2).setUnlocalizedName("Fruit.Red Apple");num++;
        Banana = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Banana",num), 10, 0.1F, false,TFC_Textures.FoodSheet, 3).setUnlocalizedName("Fruit.Banana");num++;
        Orange = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Orange",num), 10, 0.1F, false,TFC_Textures.FoodSheet, 4).setUnlocalizedName("Fruit.Orange");num++;
        GreenApple = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Green Apple",num), 15, 0.1F, false,TFC_Textures.FoodSheet, 5).setUnlocalizedName("Fruit.Green Apple");num++;
        Lemon = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Lemon",num), 10, 0.03F, false,TFC_Textures.FoodSheet, 6).setUnlocalizedName("Fruit.Lemon");num++;
        Olive = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Olive",num), 10, 0.05F, false,TFC_Textures.FoodSheet, 7).setUnlocalizedName("Fruit.Olive");num++;
        Cherry = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Cherry",num), 10, 0.03F, false,TFC_Textures.FoodSheet, 8).setUnlocalizedName("Fruit.Cherry");num++;
        Peach = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Peach",num), 12, 0.1F, false,TFC_Textures.FoodSheet, 9).setUnlocalizedName("Fruit.Peach");num++;
        Plum = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Plum",num), 10, 0.1F, false,TFC_Textures.FoodSheet, 10).setUnlocalizedName("Fruit.Plum");num++;
        EggCooked = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Meat.EggCooked",num), 25, 0.4F, false,TFC_Textures.FoodSheet, 11).setUnlocalizedName("Meat.EggCooked");num++;
        
        WheatGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","WheatGrain",num++), 1, 0.4F, false,TFC_Textures.FoodSheet, 12).setUnlocalizedName("Wheat Grain");
        BarleyGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","BarleyGrain",num++), 1, 0.4F, false,TFC_Textures.FoodSheet, 14).setUnlocalizedName("Barley Grain");
        OatGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","OatGrain",num++), 1, 0.4F, false,TFC_Textures.FoodSheet, 16).setUnlocalizedName("Oat Grain");
        RyeGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RyeGrain",num++), 1, 0.4F, false,TFC_Textures.FoodSheet, 18).setUnlocalizedName("Rye Grain");
        RiceGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RiceGrain",num++), 1, 0.4F, false,TFC_Textures.FoodSheet, 20).setUnlocalizedName("Rice Grain");
        MaizeEar = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","MaizeEar",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 22).setUnlocalizedName("Maize Ear");
        Tomato = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Tomato",num++), 15, 0.4F, false,TFC_Textures.FoodSheet, 24).setUnlocalizedName("Tomato");
        Potato = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Potato",num++), 22, 0.4F, false,TFC_Textures.FoodSheet, 25).setUnlocalizedName("Potato");
        Onion = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Onion",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 27).setUnlocalizedName("Onion");
        Cabbage = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Cabbage",num++), 20, 0.4F, false,TFC_Textures.FoodSheet, 28).setUnlocalizedName("Cabbage");
        Garlic = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Garlic",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 29).setUnlocalizedName("Garlic");
        Carrot = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Carrot",num++), 5, 0.4F, false,TFC_Textures.FoodSheet, 30).setUnlocalizedName("Carrot");
        Sugarcane = new ItemTerra(TFC_Settings.getIntFor(config,"item","Sugarcane",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Sugarcane");
        Hemp = new ItemTerra(TFC_Settings.getIntFor(config,"item","Hemp",num++), TFC_Textures.FoodSheet).setUnlocalizedName("Hemp");
        Soybean = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Soybeans",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 31).setUnlocalizedName("Soybeans");
        Greenbeans = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Greenbeans",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 32).setUnlocalizedName("Greenbeans");
        GreenBellPepper = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","GreenBellPepper",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 34).setUnlocalizedName("Green Bell Pepper");
        YellowBellPepper = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","YellowBellPepper",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 35).setUnlocalizedName("Yellow Bell Pepper");
        RedBellPepper = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RedBellPepper",num++), 10, 0.4F, false,TFC_Textures.FoodSheet, 36).setUnlocalizedName("Red Bell Pepper");
        Squash = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Squash",num++), 12, 0.4F, false,TFC_Textures.FoodSheet, 37).setUnlocalizedName("Squash");
        
        WheatWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","WheatWhole",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Wheat Whole");
        BarleyWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","BarleyWhole",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Barley Whole");
        OatWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","OatWhole",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Oat Whole");
        RyeWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","RyeWhole",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Rye Whole");
        RiceWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","RiceWhole",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Rice Whole");
        
        MealGeneric = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealGeneric",num++),TFC_Textures.FoodSheet).setUnlocalizedName("MealGeneric");
        MealMoveSpeed = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealMoveSpeed",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.moveSpeed.id,8000,4)).setUnlocalizedName("MealGeneric");
        MealDigSpeed = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealDigSpeed",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.digSpeed.id,8000,4)).setUnlocalizedName("MealGeneric");
        MealDamageBoost = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealDamageBoost",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.damageBoost.id,4000,4)).setUnlocalizedName("MealGeneric");
        MealJump = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealJump",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.jump.id,8000,4)).setUnlocalizedName("MealGeneric");
        MealDamageResist = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealDamageResist",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.resistance.id,8000,4)).setUnlocalizedName("MealGeneric");
        MealFireResist = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealFireResist",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.fireResistance.id,8000,4)).setUnlocalizedName("MealGeneric");
        MealWaterBreathing = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealWaterBreathing",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.waterBreathing.id,8000,4)).setUnlocalizedName("MealGeneric");
        MealNightVision = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealNightVision",num++),TFC_Textures.FoodSheet).setPotionEffect(new PotionEffect(Potion.nightVision.id,4000,4)).setUnlocalizedName("MealGeneric");
        
        WheatGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","WheatGround",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Wheat Ground");
        BarleyGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","BarleyGround",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Barley Ground");
        OatGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","OatGround",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Oat Ground");
        RyeGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","RyeGround",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Rye Ground");
        RiceGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","RiceGround",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Rice Ground");
        CornmealGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","CornmealGround",num++),TFC_Textures.FoodSheet).setUnlocalizedName("Cornmeal Ground");
        
        WheatDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","WheatDough",num++), 1, 0.0F, false,TFC_Textures.FoodSheet, 0).setUnlocalizedName("Wheat Dough");
        BarleyDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","BarleyDough",num++), 1, 0.0F, false,TFC_Textures.FoodSheet, 0).setUnlocalizedName("Barley Dough");
        OatDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","OatDough",num++), 1, 0.0F, false,TFC_Textures.FoodSheet, 0).setUnlocalizedName("Oat Dough");
        RyeDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RyeDough",num++), 1, 0.0F, false,TFC_Textures.FoodSheet, 0).setUnlocalizedName("Rye Dough");
        RiceDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RiceDough",num++), 1, 0.0F, false,TFC_Textures.FoodSheet, 0).setUnlocalizedName("Rice Dough");
        CornmealDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","CornmealDough",num++), 1, 0.0F, false,TFC_Textures.FoodSheet, 0).setUnlocalizedName("Cornmeal Dough");
        
        BarleyBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","BarleyBread",num++), 25, 0.6F, false,TFC_Textures.FoodSheet, 43).setUnlocalizedName("Barley Bread");
        OatBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","OatBread",num++), 25, 0.6F, false,TFC_Textures.FoodSheet, 44).setUnlocalizedName("Oat Bread");
        RyeBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RyeBread",num++), 25, 0.6F, false,TFC_Textures.FoodSheet, 45).setUnlocalizedName("Rye Bread");
        RiceBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RiceBread",num++), 25, 0.6F, false,TFC_Textures.FoodSheet, 46).setUnlocalizedName("Rice Bread");
        CornBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","CornBread",num++), 25, 0.6F, false,TFC_Textures.FoodSheet, 47).setUnlocalizedName("Corn Bread");
        
        num = 18900;
        SeedsWheat = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsWheat",num++),0).setUnlocalizedName("Seeds Wheat");
        SeedsBarley = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsBarley",num++),5).setUnlocalizedName("Seeds Barley");
        SeedsRye = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsRye",num++),7).setUnlocalizedName("Seeds Rye");
        SeedsOat = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsOat",num++),9).setUnlocalizedName("Seeds Oat");
        SeedsRice = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsRice",num++),11).setUnlocalizedName("Seeds Rice");
        SeedsMaize = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsMaize",num++),2).setUnlocalizedName("Seeds Maize");
        SeedsPotato = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsPotato",num++),13).setUnlocalizedName("Seeds Potato");
        SeedsOnion = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsOnion",num++),15).setUnlocalizedName("Seeds Onion");
        SeedsCabbage = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsCabbage",num++),16).setUnlocalizedName("Seeds Cabbage");
        SeedsGarlic = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsGarlic",num++),17).setUnlocalizedName("Seeds Garlic");
        SeedsCarrot = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsCarrot",num++),18).setUnlocalizedName("Seeds Carrot");
        SeedsSugarcane = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsSugarcane",num++),21).setUnlocalizedName("Seeds Sugarcane");
        SeedsHemp = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsHemp",num++),22).setUnlocalizedName("Seeds Hemp");
        SeedsTomato = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsTomato",num++),4).setUnlocalizedName("Seeds Tomato");
        SeedsYellowBellPepper = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsYellowBellPepper",num++),19).setUnlocalizedName("Seeds Yellow Bell Pepper");
        SeedsRedBellPepper = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsRedBellPepper",num++),20).setUnlocalizedName("Seeds Red Bell Pepper");
        SeedsSoybean = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsSoybean",num++),21).setUnlocalizedName("Seeds Soybean");
        SeedsGreenbean = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsGreenbean",num++),22).setUnlocalizedName("Seeds Greenbean");
        SeedsSquash = new ItemCustomSeeds(TFC_Settings.getIntFor(config,"item","SeedsSquash",num++),23).setUnlocalizedName("Seeds Squash");
    }
    
    public static void SetupArmor(int num)
    {
    	BismuthArmorMaterial = EnumHelper.addArmorMaterial("Bismuth", 20, new int[] {2,4,3,2}, 1);
		BismuthBronzeArmorMaterial = EnumHelper.addArmorMaterial("BismuthBronze", 40, new int[] {4,6,5,4}, 1);
		BlackBronzeArmorMaterial = EnumHelper.addArmorMaterial("BlackBronze", 40, new int[] {4,6,5,4}, 1);
		BlackSteelArmorMaterial = EnumHelper.addArmorMaterial("BlackSteel", 70, new int[] {6,8,7,6}, 1);
		BlueSteelArmorMaterial = EnumHelper.addArmorMaterial("BlueSteel", 80, new int[] {7,8,8,7}, 1);
		BronzeArmorMaterial = EnumHelper.addArmorMaterial("Bronze", 42, new int[] {4,6,5,4}, 1);
		CopperArmorMaterial = EnumHelper.addArmorMaterial("Copper", 30, new int[] {3,5,4,3}, 1);
		IronArmorMaterial = EnumHelper.addArmorMaterial("Iron", 50, new int[] {5,7,6,5}, 1);
		RedSteelArmorMaterial = EnumHelper.addArmorMaterial("RedSteel", 80, new int[] {7,8,8,7}, 1);
		RoseGoldArmorMaterial = EnumHelper.addArmorMaterial("RoseGold", 40, new int[] {4,6,5,4}, 1);
		SteelArmorMaterial = EnumHelper.addArmorMaterial("Steel", 60, new int[] {6,8,7,6}, 1);
		TinArmorMaterial = EnumHelper.addArmorMaterial("Tin", 20, new int[] {2,4,3,2}, 1);
		ZincArmorMaterial = EnumHelper.addArmorMaterial("Zinc", 20, new int[] {2,4,3,2}, 1);
		
    	String[] Names = {"Bismuth", "Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Bronze", "Copper", "Wrought Iron", "Red Steel", "Rose Gold", "Steel", "Tin", "Zinc"};
    	String[] NamesNS = {"Bismuth", "BismuthBronze", "BlackBronze", "BlackSteel", "BlueSteel", "Bronze", "Copper", "WroughtIron", "RedSteel", "RoseGold", "Steel", "Tin", "Zinc"};
    	String[] NamesNSO = {"Brass", "Gold", "Lead", "Nickel", "Pig Iron", "Platinum", "Silver", "Sterling Silver"};
    	CommonProxy proxy = TerraFirmaCraft.proxy;
        
        EnumArmorMaterial[] mats = new EnumArmorMaterial[]{TFCItems.BismuthArmorMaterial,TFCItems.BismuthBronzeArmorMaterial,TFCItems.BlackBronzeArmorMaterial,TFCItems.BlackSteelArmorMaterial,TFCItems.BlueSteelArmorMaterial,
                TFCItems.BronzeArmorMaterial,TFCItems.CopperArmorMaterial,TFCItems.IronArmorMaterial,TFCItems.RedSteelArmorMaterial,TFCItems.RoseGoldArmorMaterial,
                TFCItems.SteelArmorMaterial,TFCItems.TinArmorMaterial,TFCItems.ZincArmorMaterial};
        
        
        
        int i = 0;
        BismuthSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Bismuth Sheet"));num++;
        BismuthBronzeSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Bismuth Bronze Sheet"));num++;
        BlackBronzeSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Black Bronze Sheet"));num++;
        BlackSteelSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Black Steel Sheet"));num++;
        BlueSteelSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Blue Steel Sheet"));num++;
        BronzeSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Bronze Sheet"));num++;
        CopperSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Copper Sheet"));num++;
        WroughtIronSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Wrought Iron Sheet"));num++;
        RedSteelSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Red Steel Sheet"));num++;
        RoseGoldSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Rose Gold Sheet"));num++;
        SteelSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Steel Sheet"));num++;
        TinSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Tin Sheet"));num++;
        ZincSheet = ((ItemTerra)new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Zinc Sheet"));num++;
        
        i = 0;
        BismuthSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("BismuthSheet2x"));num++;
        BismuthBronzeSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Bismuth BronzeSheet2x"));num++;
        BlackBronzeSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Black BronzeSheet2x"));num++;
        BlackSteelSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Black SteelSheet2x"));num++;
        BlueSteelSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Blue SteelSheet2x"));num++;
        BronzeSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("BronzeSheet2x"));num++;
        CopperSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("CopperSheet2x"));num++;
        WroughtIronSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Wrought IronSheet2x"));num++;
        RedSteelSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Red SteelSheet2x"));num++;
        RoseGoldSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Rose GoldSheet2x"));num++;
        SteelSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("SteelSheet2x"));num++;
        TinSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("TinSheet2x"));num++;
        ZincSheet2x = ((ItemTerra)new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("ZincSheet2x"));num++;
        
        i = 0;
        TFCItems.BismuthUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.BismuthBronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.BlackBronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.BlackSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.BlueSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.BronzeUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.CopperUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.WroughtIronUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.RedSteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.RoseGoldUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.SteelUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.TinUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        TFCItems.ZincUnfinishedBoots = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
        i = 0;
        TFCItems.BismuthBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.BismuthBronzeBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.BlackBronzeBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.BlackSteelBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.BlueSteelBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.BronzeBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.CopperBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.WroughtIronBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.RedSteelBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.RoseGoldBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.SteelBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.TinBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        TFCItems.ZincBoots = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num),mats[i], proxy.getArmorRenderID(i), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
        i = 0;
        TFCItems.BismuthUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.BismuthBronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.BlackBronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.BlackSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.BlueSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.BronzeUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.CopperUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.WroughtIronUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.RedSteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.RoseGoldUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.SteelUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.TinUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        TFCItems.ZincUnfinishedGreaves = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
        i = 0;
        TFCItems.BismuthGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.BismuthBronzeGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.BlackBronzeGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.BlackSteelGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.BlueSteelGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.BronzeGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.CopperGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.WroughtIronGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.RedSteelGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.RoseGoldGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.SteelGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.TinGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        TFCItems.ZincGreaves = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num),mats[i], proxy.getArmorRenderID(i), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
        i = 0;
        TFCItems.BismuthUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.BismuthBronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.BlackBronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.BlackSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.BlueSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.BronzeUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.CopperUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.WroughtIronUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.RedSteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.RoseGoldUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.SteelUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.TinUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        TFCItems.ZincUnfinishedChestplate = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
        i = 0;
        TFCItems.BismuthChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.BismuthBronzeChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.BlackBronzeChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.BlackSteelChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.BlueSteelChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.BronzeChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.CopperChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.WroughtIronChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.RedSteelChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.RoseGoldChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.SteelChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.TinChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        TFCItems.ZincChestplate = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num),mats[i], proxy.getArmorRenderID(i), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
        i = 0;
        TFCItems.BismuthUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.BismuthBronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.BlackBronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.BlackSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.BlueSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.BronzeUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.CopperUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.WroughtIronUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.RedSteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.RoseGoldUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.SteelUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.TinUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        TFCItems.ZincUnfinishedHelmet = ((ItemUnfinishedArmor)new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
        i = 0;
        TFCItems.BismuthHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.BismuthBronzeHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.BlackBronzeHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.BlackSteelHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.BlueSteelHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.BronzeHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.CopperHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.WroughtIronHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.RedSteelHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.RoseGoldHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.SteelHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.TinHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
        TFCItems.ZincHelmet = ((ItemCustomArmor)new ItemCustomArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num),mats[i], proxy.getArmorRenderID(i), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
    
        i = 0;
        BrassSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        GoldSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        LeadSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        NickelSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        PigIronSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        PlatinumSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        SilverSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        SterlingSilverSheet = new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
        
        i = 0;
        BrassSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
        GoldSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
        LeadSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
        NickelSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
        PigIronSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
        PlatinumSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
        SilverSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
        SterlingSilverSheet2x = new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+"Sheet2x");
    }
    
    public static Item[] Meals;


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
}
