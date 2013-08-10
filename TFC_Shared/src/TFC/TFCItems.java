package TFC;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import TFC.API.Armor;
import TFC.API.Metal;
import TFC.API.TFCTabs;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Enums.EnumSize;
import TFC.Core.Recipes;
import TFC.Core.TFC_Settings;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.MetalRegistry;
import TFC.Food.ItemMeal;
import TFC.Food.ItemTerraFood;
import TFC.Items.ItemAlcohol;
import TFC.Items.ItemBellows;
import TFC.Items.ItemBloom;
import TFC.Items.ItemBlueprint;
import TFC.Items.ItemClay;
import TFC.Items.ItemCustomMinecart;
import TFC.Items.ItemCustomPotion;
import TFC.Items.ItemCustomSeeds;
import TFC.Items.ItemDyeCustom;
import TFC.Items.ItemFlatLeather;
import TFC.Items.ItemFlatRock;
import TFC.Items.ItemFruitTreeSapling;
import TFC.Items.ItemGem;
import TFC.Items.ItemIngot;
import TFC.Items.ItemLeather;
import TFC.Items.ItemLogs;
import TFC.Items.ItemLooseRock;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.ItemMetalSheet;
import TFC.Items.ItemMetalSheet2x;
import TFC.Items.ItemOre;
import TFC.Items.ItemOreSmall;
import TFC.Items.ItemPlank;
import TFC.Items.ItemSluice;
import TFC.Items.ItemStick;
import TFC.Items.ItemStoneBrick;
import TFC.Items.ItemTFCArmor;
import TFC.Items.ItemTerra;
import TFC.Items.ItemTuyere;
import TFC.Items.ItemUnfinishedArmor;
import TFC.Items.ItemBlocks.ItemWoodDoor;
import TFC.Items.Pottery.ItemPotteryBase;
import TFC.Items.Pottery.ItemPotteryJug;
import TFC.Items.Pottery.ItemPotteryLargeVessel;
import TFC.Items.Pottery.ItemPotteryMold;
import TFC.Items.Pottery.ItemPotteryPot;
import TFC.Items.Pottery.ItemPotterySmallVessel;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemCustomAxe;
import TFC.Items.Tools.ItemCustomBlueSteelBucket;
import TFC.Items.Tools.ItemCustomBow;
import TFC.Items.Tools.ItemCustomBucket;
import TFC.Items.Tools.ItemCustomBucketMilk;
import TFC.Items.Tools.ItemCustomHoe;
import TFC.Items.Tools.ItemCustomKnife;
import TFC.Items.Tools.ItemCustomPickaxe;
import TFC.Items.Tools.ItemCustomRedSteelBucket;
import TFC.Items.Tools.ItemCustomSaw;
import TFC.Items.Tools.ItemCustomScythe;
import TFC.Items.Tools.ItemCustomShovel;
import TFC.Items.Tools.ItemCustomSword;
import TFC.Items.Tools.ItemFirestarter;
import TFC.Items.Tools.ItemFlintSteel;
import TFC.Items.Tools.ItemGoldPan;
import TFC.Items.Tools.ItemHammer;
import TFC.Items.Tools.ItemJavelin;
import TFC.Items.Tools.ItemMiscToolHead;
import TFC.Items.Tools.ItemPlan;
import TFC.Items.Tools.ItemProPick;
import TFC.Items.Tools.ItemSpindle;
import TFC.Items.Tools.ItemWritableBookTFC;

public class TFCItems
{
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


	public static Item IgInShovel;
	public static Item IgInAxe;
	public static Item IgInHoe;

	public static Item SedShovel;
	public static Item SedAxe;
	public static Item SedHoe;

	public static Item IgExShovel;
	public static Item IgExAxe;
	public static Item IgExHoe;

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

	// javelins
	public static Item IgInStoneJavelin;
	public static Item SedStoneJavelin;
	public static Item IgExStoneJavelin;
	public static Item MMStoneJavelin;
	public static Item TinJavelin;
	public static Item BismuthJavelin;
	public static Item ZincJavelin;
	public static Item CopperJavelin;
	public static Item BismuthBronzeJavelin;
	public static Item BronzeJavelin;
	public static Item BlackBronzeJavelin;
	public static Item WroughtIronJavelin;
	public static Item RoseGoldJavelin;
	public static Item SteelJavelin;
	public static Item BlackSteelJavelin;
	public static Item BlueSteelJavelin;
	public static Item RedSteelJavelin;

	// javelin heads
	public static Item IgInStoneJavelinHead;
	public static Item SedStoneJavelinHead;
	public static Item IgExStoneJavelinHead;
	public static Item MMStoneJavelinHead;
	public static Item TinJavelinHead;
	public static Item BismuthJavelinHead;
	public static Item ZincJavelinHead;
	public static Item CopperJavelinHead;
	public static Item BismuthBronzeJavelinHead;
	public static Item BronzeJavelinHead;
	public static Item BlackBronzeJavelinHead;
	public static Item WroughtIronJavelinHead;
	public static Item RoseGoldJavelinHead;
	public static Item SteelJavelinHead;
	public static Item BlackSteelJavelinHead;
	public static Item BlueSteelJavelinHead;
	public static Item RedSteelJavelinHead;

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
	public static Item CeramicMold;
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
	public static Item JavelinHeadPlan;

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
	public static Item Powder;

	//Formerly TFC_Mining

	public static Item GoldPan;
	public static Item SluiceItem;

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

	public static Item BismuthBronzeUnfinishedChestplate;
	public static Item BlackBronzeUnfinishedChestplate;
	public static Item BlackSteelUnfinishedChestplate;
	public static Item BlueSteelUnfinishedChestplate;
	public static Item BronzeUnfinishedChestplate;
	public static Item CopperUnfinishedChestplate;
	public static Item WroughtIronUnfinishedChestplate;
	public static Item RedSteelUnfinishedChestplate;
	public static Item SteelUnfinishedChestplate;

	public static Item BismuthBronzeUnfinishedGreaves;
	public static Item BlackBronzeUnfinishedGreaves;
	public static Item BlackSteelUnfinishedGreaves;
	public static Item BlueSteelUnfinishedGreaves;
	public static Item BronzeUnfinishedGreaves;
	public static Item CopperUnfinishedGreaves;
	public static Item WroughtIronUnfinishedGreaves;
	public static Item RedSteelUnfinishedGreaves;
	public static Item SteelUnfinishedGreaves;

	public static Item BismuthBronzeUnfinishedBoots;
	public static Item BlackBronzeUnfinishedBoots;
	public static Item BlackSteelUnfinishedBoots;
	public static Item BlueSteelUnfinishedBoots;
	public static Item BronzeUnfinishedBoots;
	public static Item CopperUnfinishedBoots;
	public static Item WroughtIronUnfinishedBoots;
	public static Item RedSteelUnfinishedBoots;
	public static Item SteelUnfinishedBoots;

	public static Item BismuthBronzeUnfinishedHelmet;
	public static Item BlackBronzeUnfinishedHelmet;
	public static Item BlackSteelUnfinishedHelmet;
	public static Item BlueSteelUnfinishedHelmet;
	public static Item BronzeUnfinishedHelmet;
	public static Item CopperUnfinishedHelmet;
	public static Item WroughtIronUnfinishedHelmet;
	public static Item RedSteelUnfinishedHelmet;
	public static Item SteelUnfinishedHelmet;

	public static Item BismuthBronzeChestplate;
	public static Item BlackBronzeChestplate;
	public static Item BlackSteelChestplate;
	public static Item BlueSteelChestplate;
	public static Item BronzeChestplate;
	public static Item CopperChestplate;
	public static Item WroughtIronChestplate;
	public static Item RedSteelChestplate;
	public static Item SteelChestplate;

	public static Item BismuthBronzeGreaves;
	public static Item BlackBronzeGreaves;
	public static Item BlackSteelGreaves;
	public static Item BlueSteelGreaves;
	public static Item BronzeGreaves;
	public static Item CopperGreaves;
	public static Item WroughtIronGreaves;
	public static Item RedSteelGreaves;
	public static Item SteelGreaves;

	public static Item BismuthBronzeBoots;
	public static Item BlackBronzeBoots;
	public static Item BlackSteelBoots;
	public static Item BlueSteelBoots;
	public static Item BronzeBoots;
	public static Item CopperBoots;
	public static Item WroughtIronBoots;
	public static Item RedSteelBoots;
	public static Item SteelBoots;

	public static Item BismuthBronzeHelmet;
	public static Item BlackBronzeHelmet;
	public static Item BlackSteelHelmet;
	public static Item BlueSteelHelmet;
	public static Item BronzeHelmet;
	public static Item CopperHelmet;
	public static Item WroughtIronHelmet;
	public static Item RedSteelHelmet;
	public static Item SteelHelmet;

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
	public static Item CalamariRaw;
	public static Item CalamariCooked;
	public static Item FlatLeather;
	public static Item Beer;
	public static Item Cider;
	public static Item Rum;
	public static Item RyeWhiskey;
	public static Item Sake;
	public static Item Vodka;
	public static Item Whiskey;

	public static Item PotteryJug;
	public static Item PotteryPot;
	public static Item PotterySmallVessel;
	public static Item PotteryLargeVessel;
	public static Item KilnRack;
	public static Item Straw;
	public static Item FlatClay;

	public static Item ClayMoldAxe;
	public static Item ClayMoldChisel;
	public static Item ClayMoldHammer;
	public static Item ClayMoldHoe;
	public static Item ClayMoldKnife;
	public static Item ClayMoldMace;
	public static Item ClayMoldPick;
	public static Item ClayMoldProPick;
	public static Item ClayMoldSaw;
	public static Item ClayMoldScythe;
	public static Item ClayMoldShovel;
	public static Item ClayMoldSword;
	public static Item ClayMoldJavelin;

	public static Item TuyereCopper;
	public static Item TuyereBronze;
	public static Item TuyereBlackBronze;
	public static Item TuyereBismuthBronze;
	public static Item TuyereWroughtIron;
	public static Item TuyereSteel;
	public static Item TuyereBlackSteel;
	public static Item TuyereBlueSteel;
	public static Item TuyereRedSteel;

	public static Item Bloom;
	public static Item RawBloom;
	public static Item UnknownIngot;
	public static Item UnknownUnshaped;

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
	public static float BismuthEff = 4;
	public static float TinEff = 3.5f;
	public static float ZincEff = 3;
	//Tier 1
	public static float CopperEff = 5;
	//Tier 2
	public static float BronzeEff = 8;
	public static float RoseGoldEff = 6;
	public static float BismuthBronzeEff = 8;
	public static float BlackBronzeEff = 8;
	//Tier 3
	public static float WroughtIronEff = 10;
	//Tier 4
	public static float SteelEff = 14;
	//Tier 5
	public static float BlackSteelEff = 16;
	//Tier 6
	public static float BlueSteelEff = 18;
	public static float RedSteelEff = 18;




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
	public static EnumToolMaterial IronToolMaterial;
	public static EnumToolMaterial RedSteelToolMaterial;
	public static EnumToolMaterial RoseGoldToolMaterial;
	public static EnumToolMaterial SteelToolMaterial;
	public static EnumToolMaterial TinToolMaterial;
	public static EnumToolMaterial ZincToolMaterial;



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

		IgInToolMaterial = EnumHelper.addToolMaterial("IgIn", 1, IgInStoneUses, 7F, 10, 5);
		SedToolMaterial = EnumHelper.addToolMaterial("Sed", 1, SedStoneUses, 6F, 10, 5);
		IgExToolMaterial = EnumHelper.addToolMaterial("IgEx", 1, IgExStoneUses, 7F, 10, 5);
		MMToolMaterial = EnumHelper.addToolMaterial("MM", 1, MMStoneUses, 6.5F, 10, 5);

		BismuthToolMaterial = EnumHelper.addToolMaterial("Bismuth", 2, BismuthUses, BismuthEff, 65, 10);
		BismuthBronzeToolMaterial = EnumHelper.addToolMaterial("BismuthBronze", 2, BismuthBronzeUses, BismuthBronzeEff, 85, 10);
		BlackBronzeToolMaterial = EnumHelper.addToolMaterial("BlackBronze", 2, BlackBronzeUses, BlackBronzeEff, 100, 10);
		BlackSteelToolMaterial = EnumHelper.addToolMaterial("BlackSteel", 2, BlackSteelUses, BlackSteelEff, 165, 12);
		BlueSteelToolMaterial = EnumHelper.addToolMaterial("BlueSteel", 3, BlueSteelUses, BlueSteelEff, 185, 22);
		BronzeToolMaterial = EnumHelper.addToolMaterial("Bronze", 2, BronzeUses, BronzeEff, 100, 13);
		CopperToolMaterial = EnumHelper.addToolMaterial("Copper", 2, CopperUses, CopperEff, 85, 8);
		IronToolMaterial = EnumHelper.addToolMaterial("Iron", 2, WroughtIronUses, WroughtIronEff, 135, 10);
		RedSteelToolMaterial = EnumHelper.addToolMaterial("RedSteel", 3, RedSteelUses, RedSteelEff, 185, 22);
		RoseGoldToolMaterial = EnumHelper.addToolMaterial("RoseGold", 2, RoseGoldUses, RoseGoldEff, 100, 20);
		SteelToolMaterial = EnumHelper.addToolMaterial("Steel", 2, SteelUses, SteelEff, 150, 10);
		TinToolMaterial = EnumHelper.addToolMaterial("Tin", 2, TinUses, TinEff, 65, 8);
		ZincToolMaterial = EnumHelper.addToolMaterial("Zinc", 2, ZincUses, ZincEff, 65, 8);

		System.out.println(new StringBuilder().append("[TFC] Loading Items").toString());

		//Replace any vanilla Items here
		Item.itemsList[Item.coal.itemID] = null; Item.itemsList[Item.coal.itemID] = (new TFC.Items.ItemCoal(7)).setUnlocalizedName("coal");
		Item.itemsList[Item.stick.itemID] = null; Item.itemsList[Item.stick.itemID] = new ItemStick(24).setFull3D().setUnlocalizedName("stick");
		Item.itemsList[Item.leather.itemID] = null; Item.itemsList[Item.leather.itemID] = new ItemTerra(Item.leather.itemID).setFull3D().setUnlocalizedName("leather");
		Item.itemsList[Block.vine.blockID] = new ItemColored(Block.vine.blockID - 256, false);

		minecartCrate = (new ItemCustomMinecart(TFC_Settings.getIntFor(config,"item","minecartCrate",16000), 1)).setUnlocalizedName("minecartChest").func_111206_d("minecart_chest");

		Item.itemsList[5+256] = null; Item.itemsList[5+256] = (new ItemCustomBow(5)).setUnlocalizedName("bow").func_111206_d("bow");
		Item.itemsList[63+256] = null; Item.itemsList[63+256] = new ItemTerra(63).setUnlocalizedName("porkchopRaw");
		Item.itemsList[64+256] = null; Item.itemsList[64+256] = new ItemTerraFood(64, 35, 0.8F, true, 38).setFolder("").setUnlocalizedName("porkchopCooked");
		Item.itemsList[93+256] = null; Item.itemsList[93+256] = new ItemTerra(93).setUnlocalizedName("fishRaw");
		Item.itemsList[94+256] = null; Item.itemsList[94+256] = new ItemTerraFood(94, 30, 0.6F, true, 39).setFolder("").setUnlocalizedName("fishCooked");
		Item.itemsList[107+256] = null; Item.itemsList[107+256] = new ItemTerra(107).setUnlocalizedName("beefRaw");
		Item.itemsList[108+256] = null; Item.itemsList[108+256] = new ItemTerraFood(108, 40, 0.8F, true, 40).setFolder("").setUnlocalizedName("beefCooked");
		Item.itemsList[109+256] = null; Item.itemsList[109+256] = new ItemTerra(109).setUnlocalizedName("chickenRaw");
		Item.itemsList[110+256] = null; Item.itemsList[110+256] = new ItemTerraFood(110, 35, 0.6F, true, 41).setFolder("").setUnlocalizedName("chickenCooked");
		Item.itemsList[41+256] = null; Item.itemsList[41+256] = (new ItemTerraFood(41, 25, 0.6F, false, 42)).setFolder("").setUnlocalizedName("bread");
		Item.itemsList[88+256] = null; Item.itemsList[88+256] = (new ItemTerra(88)).setUnlocalizedName("egg");
		Item.itemsList[Item.dyePowder.itemID] = null; Item.itemsList[Item.dyePowder.itemID] = new ItemDyeCustom(95).setUnlocalizedName("dyePowder").func_111206_d("dye_powder");
		Item.itemsList[Item.potion.itemID] = null; Item.itemsList[Item.potion.itemID] = (new ItemCustomPotion(117)).setUnlocalizedName("potion").func_111206_d("potion");

		Item.itemsList[Block.tallGrass.blockID] = null; Item.itemsList[Block.tallGrass.blockID] = (new ItemColored(Block.tallGrass.blockID - 256, true)).setBlockNames(new String[] {"shrub", "grass", "fern"});


		GoldPan = new ItemGoldPan(TFC_Settings.getIntFor(config,"item","GoldPan",16001)).setUnlocalizedName("GoldPan");
		SluiceItem = new ItemSluice(TFC_Settings.getIntFor(config,"item","SluiceItem",16002)).setFolder("devices/").setUnlocalizedName("SluiceItem");

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

		BismuthIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthIngot",16028)).setUnlocalizedName("Bismuth Ingot");
		BismuthBronzeIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthBronzeIngot",16029)).setUnlocalizedName("Bismuth Bronze Ingot");
		BlackBronzeIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackBronzeIngot",16030)).setUnlocalizedName("Black Bronze Ingot");
		BlackSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackSteelIngot",16031)).setUnlocalizedName("Black Steel Ingot");
		BlueSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BlueSteelIngot",16032)).setUnlocalizedName("Blue Steel Ingot");
		BrassIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BrassIngot",16033)).setUnlocalizedName("Brass Ingot");
		BronzeIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","BronzeIngot",16034)).setUnlocalizedName("Bronze Ingot");
		CopperIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","CopperIngot",16035)).setUnlocalizedName("Copper Ingot");
		GoldIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","GoldIngot",16036)).setUnlocalizedName("Gold Ingot");
		WroughtIronIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WroughtIronIngot",16037)).setUnlocalizedName("Wrought Iron Ingot");
		LeadIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","LeadIngot",16038)).setUnlocalizedName("Lead Ingot");
		NickelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","NickelIngot",16039)).setUnlocalizedName("Nickel Ingot");
		PigIronIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","PigIronIngot",16040)).setUnlocalizedName("Pig Iron Ingot");
		PlatinumIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","PlatinumIngot",16041)).setUnlocalizedName("Platinum Ingot");
		RedSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","RedSteelIngot",16042)).setUnlocalizedName("Red Steel Ingot");
		RoseGoldIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","RoseGoldIngot",16043)).setUnlocalizedName("Rose Gold Ingot");
		SilverIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","SilverIngot",16044)).setUnlocalizedName("Silver Ingot");
		SteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","SteelIngot",16045)).setUnlocalizedName("Steel Ingot");
		SterlingSilverIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","SterlingSilverIngot",16046)).setUnlocalizedName("Sterling Silver Ingot");
		TinIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","TinIngot",16047)).setUnlocalizedName("Tin Ingot");
		ZincIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","ZincIngot",16048)).setUnlocalizedName("Zinc Ingot");

		BismuthIngot2x = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthIngot2x",16049)).setUnlocalizedName("Bismuth Double Ingot")).setSize(EnumSize.LARGE);
		BismuthBronzeIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BismuthBronzeIngot2x",16050)).setUnlocalizedName("Bismuth Bronze Double Ingot")).setSize(EnumSize.LARGE);
		BlackBronzeIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackBronzeIngot2x",16051)).setUnlocalizedName("Black Bronze Double Ingot")).setSize(EnumSize.LARGE);
		BlackSteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BlackSteelIngot2x",16052)).setUnlocalizedName("Black Steel Double Ingot")).setSize(EnumSize.LARGE);
		BlueSteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BlueSteelIngot2x",16053)).setUnlocalizedName("Blue Steel Double Ingot")).setSize(EnumSize.LARGE);
		BrassIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BrassIngot2x",16054)).setUnlocalizedName("Brass Double Ingot")).setSize(EnumSize.LARGE);
		BronzeIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","BronzeIngot2x",16055)).setUnlocalizedName("Bronze Double Ingot")).setSize(EnumSize.LARGE);
		CopperIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","CopperIngot2x",16056)).setUnlocalizedName("Copper Double Ingot")).setSize(EnumSize.LARGE);
		GoldIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","GoldIngot2x",16057)).setUnlocalizedName("Gold Double Ingot")).setSize(EnumSize.LARGE);
		WroughtIronIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","WroughtIronIngot2x",16058)).setUnlocalizedName("Wrought Iron Double Ingot")).setSize(EnumSize.LARGE);
		LeadIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","LeadIngot2x",16059)).setUnlocalizedName("Lead Double Ingot")).setSize(EnumSize.LARGE);
		NickelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","NickelIngot2x",16060)).setUnlocalizedName("Nickel Double Ingot")).setSize(EnumSize.LARGE);
		PigIronIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","PigIronIngot2x",16061)).setUnlocalizedName("Pig Iron Double Ingot")).setSize(EnumSize.LARGE);
		PlatinumIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","PlatinumIngot2x",16062)).setUnlocalizedName("Platinum Double Ingot")).setSize(EnumSize.LARGE);
		RedSteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","RedSteelIngot2x",16063)).setUnlocalizedName("Red Steel Double Ingot")).setSize(EnumSize.LARGE);
		RoseGoldIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","RoseGoldIngot2x",16064)).setUnlocalizedName("Rose Gold Double Ingot")).setSize(EnumSize.LARGE);
		SilverIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","SilverIngot2x",16065)).setUnlocalizedName("Silver Double Ingot")).setSize(EnumSize.LARGE);
		SteelIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","SteelIngot2x",16066)).setUnlocalizedName("Steel Double Ingot")).setSize(EnumSize.LARGE);
		SterlingSilverIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","SterlingSilverIngot2x",16067)).setUnlocalizedName("Sterling Silver Double Ingot")).setSize(EnumSize.LARGE);
		TinIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","TinIngot2x",16068)).setUnlocalizedName("Tin Double Ingot")).setSize(EnumSize.LARGE);
		ZincIngot2x  = ((ItemIngot)new ItemIngot(TFC_Settings.getIntFor(config,"item","ZincIngot2x",16069)).setUnlocalizedName("Zinc Double Ingot")).setSize(EnumSize.LARGE);

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

		IgInShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","IgInShovel",16101),IgInToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(IgInStoneUses);
		IgInAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","IgInAxe",16102),IgInToolMaterial).setUnlocalizedName("Stone Axe").setMaxDamage(IgInStoneUses);
		IgInHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","IgInHoe",16103),IgInToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(IgInStoneUses);

		SedShovel= new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","SedShovel",16105),SedToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(SedStoneUses);
		SedAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","SedAxe",16106),SedToolMaterial).setUnlocalizedName("Stone Axe").setMaxDamage(SedStoneUses);
		SedHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","SedHoe",16107),SedToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(SedStoneUses);

		IgExShovel= new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","IgExShovel",16109),IgExToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(IgExStoneUses);
		IgExAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","IgExAxe",16110),IgExToolMaterial).setUnlocalizedName("Stone Axe").setMaxDamage(IgExStoneUses);
		IgExHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","IgExHoe",16111),IgExToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(IgExStoneUses);

		MMShovel = new ItemCustomShovel(TFC_Settings.getIntFor(config,"item","MMShovel",16113),MMToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(MMStoneUses);
		MMAxe = new ItemCustomAxe(TFC_Settings.getIntFor(config,"item","MMAxe",16114),MMToolMaterial).setUnlocalizedName("Stone Axe").setMaxDamage(MMStoneUses);
		MMHoe = new ItemCustomHoe(TFC_Settings.getIntFor(config,"item","MMHoe",16115),MMToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(MMStoneUses);

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

		BismuthMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BismuthMace",16262),BismuthToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Bismuth Mace").setMaxDamage(BismuthUses);
		BismuthBronzeMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BismuthBronzeMace",16263),BismuthBronzeToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Bismuth Bronze Mace").setMaxDamage(BismuthBronzeUses);
		BlackBronzeMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlackBronzeMace",16264),BlackBronzeToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Black Bronze Mace").setMaxDamage(BlackBronzeUses);
		BlackSteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlackSteelMace",16265),BlackSteelToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Black Steel Mace").setMaxDamage(BlackSteelUses);
		BlueSteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BlueSteelMace",16266),BlueSteelToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Blue Steel Mace").setMaxDamage(BlueSteelUses);
		BronzeMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","BronzeMace",16267),BronzeToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Bronze Mace").setMaxDamage(BronzeUses);
		CopperMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","CopperMace",16268),CopperToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Copper Mace").setMaxDamage(CopperUses);
		WroughtIronMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","WroughtIronMace",16269),IronToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Wrought Iron Mace").setMaxDamage(WroughtIronUses);
		RedSteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","RedSteelMace",16270),RedSteelToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Red Steel Mace").setMaxDamage(RedSteelUses);
		RoseGoldMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","RoseGoldMace",16271),RoseGoldToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Rose Gold Mace").setMaxDamage(RoseGoldUses);
		SteelMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","SteelMace",16272),SteelToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Steel Mace").setMaxDamage(SteelUses);
		TinMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","TinMace",16273),TinToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Tin Mace").setMaxDamage(TinUses);
		ZincMace = new ItemCustomSword(TFC_Settings.getIntFor(config,"item","ZincMace",16274),ZincToolMaterial, EnumDamageType.CRUSHING).setUnlocalizedName("Zinc Mace").setMaxDamage(ZincUses);

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

		HCBlackSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCBlackSteelIngot",16290)).setUnlocalizedName("HC Black Steel Ingot");
		WeakBlueSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WeakBlueSteelIngot",16291)).setUnlocalizedName("Weak Blue Steel Ingot");
		WeakRedSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WeakRedSteelIngot",16292)).setUnlocalizedName("Weak Red Steel Ingot");
		WeakSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","WeakSteelIngot",16293)).setUnlocalizedName("Weak Steel Ingot");
		HCBlueSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCBlueSteelIngot",16294)).setUnlocalizedName("HC Blue Steel Ingot");
		HCRedSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCRedSteelIngot",16295)).setUnlocalizedName("HC Red Steel Ingot");
		HCSteelIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","HCSteelIngot",16296)).setUnlocalizedName("HC Steel Ingot");

		OreChunk = new ItemOre(TFC_Settings.getIntFor(config,"item","OreChunk",16297)).setFolder("ore/").setUnlocalizedName("Ore");
		Logs = new ItemLogs(TFC_Settings.getIntFor(config,"item","Logs",16298)).setUnlocalizedName("Log");

		//javelins
		IgInStoneJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","IgInJavelin",16316),IgInToolMaterial).setUnlocalizedName("Stone Javelin");
		SedStoneJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","SedJavelin",16317),SedToolMaterial).setUnlocalizedName("Stone Javelin");
		IgExStoneJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","IgExJavelin",16318),IgExToolMaterial).setUnlocalizedName("Stone Javelin");
		MMStoneJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","MMJavelin",16319),MMToolMaterial).setUnlocalizedName("Stone Javelin");        
		TinJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","TinJavelin",16320),TinToolMaterial).setUnlocalizedName("Tin Javelin");
		BismuthJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","BismuthJavelin",16321),BismuthToolMaterial).setUnlocalizedName("Bismuth Javelin");
		ZincJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","ZincJavelin",16322),ZincToolMaterial).setUnlocalizedName("Zinc Javelin");
		CopperJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","CopperJavelin",16323),CopperToolMaterial).setUnlocalizedName("Copper Javelin");
		BismuthBronzeJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","BismuthBronzeJavelin",16324),BronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Javelin");
		BronzeJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","BronzeJavelin",16325),BronzeToolMaterial).setUnlocalizedName("Bronze Javelin");
		RoseGoldJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","RoseGoldJavelin",16326),RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Javelin");
		BlackBronzeJavelin = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeJavelin",16347)).setUnlocalizedName("Black Bronze Javelin");
		WroughtIronJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","WroughtIronJavelin",16328),IronToolMaterial).setUnlocalizedName("Wrought Iron Javelin");
		SteelJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","SteelJavelin",16329),SteelToolMaterial).setUnlocalizedName("Steel Javelin");
		BlackSteelJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","BlackSteelJavelin",16330),BlackSteelToolMaterial).setUnlocalizedName("Black Steel Javelin");
		BlueSteelJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","BlueSteelJavelin",16331),BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Javelin");
		RedSteelJavelin = new ItemJavelin(TFC_Settings.getIntFor(config,"item","RedSteelJavelin",16332),RedSteelToolMaterial).setUnlocalizedName("Red Steel Javelin");

		//javelin heads
		IgInStoneJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgInJavelinHead",16333)).setUnlocalizedName("Stone Javelin Head");
		SedStoneJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SedJavelinHead",16334)).setUnlocalizedName("Stone Javelin Head");
		IgExStoneJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","IgExJavelinHead",16335)).setUnlocalizedName("Stone Javelin Head");
		MMStoneJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","MMJavelinHead",16336)).setUnlocalizedName("Stone Javelin Head");
		TinJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config, "item", "TinJavelinHead", 16337)).setUnlocalizedName("Tin Javelin Head");
		BismuthJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthJavelinHead",16338)).setUnlocalizedName("Bismuth Javelin Head");
		ZincJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","ZincJavelinHead",16339)).setUnlocalizedName("Zinc Javelin Head");
		CopperJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperJavelinHead",16340)).setUnlocalizedName("Copper Javelin Head");
		BismuthBronzeJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzeJavelinHead",16341)).setUnlocalizedName("Bismuth Bronze Javelin Head");
		BronzeJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzeJavelinHead",16342)).setUnlocalizedName("Bronze Javelin Head");
		RoseGoldJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RoseGoldJavelinHead",16343)).setUnlocalizedName("Rose Gold Javelin Head");
		BlackBronzeJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzeJavelinHead",16344)).setUnlocalizedName("Black Bronze Javelin Head");
		WroughtIronJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","WroughtIronJavelinHead",16345)).setUnlocalizedName("Wrought Iron Javelin Head");
		SteelJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","SteelJavelinHead",16346)).setUnlocalizedName("Steel Javelin Head");
		BlackSteelJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelJavelinHead",16347)).setUnlocalizedName("Black Steel Javelin Head");
		BlueSteelJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelJavelinHead",16348)).setUnlocalizedName("Blue Steel Javelin Head");
		RedSteelJavelinHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","RedSteelJavelinHead",16349)).setUnlocalizedName("Red Steel Javelin Head");

		BismuthUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBismuth",16350)).setUnlocalizedName("Bismuth Unshaped");
		BismuthBronzeUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBismuthBronze",16351)).setUnlocalizedName("Bismuth Bronze Unshaped");
		BlackBronzeUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBlackBronze",16352)).setUnlocalizedName("Black Bronze Unshaped");
		BlackSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBlackSteel",16353)).setUnlocalizedName("Black Steel Unshaped");
		BlueSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBlueSteel",16354)).setUnlocalizedName("Blue Steel Unshaped");
		BrassUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBrass",16355)).setUnlocalizedName("Brass Unshaped");
		BronzeUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedBronze",16356)).setUnlocalizedName("Bronze Unshaped");
		CopperUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedCopper",16357)).setUnlocalizedName("Copper Unshaped");
		GoldUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedGold",16358)).setUnlocalizedName("Gold Unshaped");
		WroughtIronUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedIron",16359)).setUnlocalizedName("Wrought Iron Unshaped");
		LeadUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedLead",16360)).setUnlocalizedName("Lead Unshaped");
		NickelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedNickel",16361)).setUnlocalizedName("Nickel Unshaped");
		PigIronUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedPigIron",16362)).setUnlocalizedName("Pig Iron Unshaped");
		PlatinumUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedPlatinum",16363)).setUnlocalizedName("Platinum Unshaped");
		RedSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedRedSteel",16364)).setUnlocalizedName("Red Steel Unshaped");
		RoseGoldUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedRoseGold",16365)).setUnlocalizedName("Rose Gold Unshaped");
		SilverUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedSilver",16366)).setUnlocalizedName("Silver Unshaped");
		SteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedSteel",16367)).setUnlocalizedName("Steel Unshaped");
		SterlingSilverUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedSterlingSilver",16368)).setUnlocalizedName("Sterling Silver Unshaped");
		TinUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedTin",16369)).setUnlocalizedName("Tin Unshaped");
		ZincUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedZinc",16370)).setUnlocalizedName("Zinc Unshaped");

		//Hammers
		StoneHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","StoneHammer",16371),TFCItems.IgInToolMaterial).setUnlocalizedName("Stone Hammer").setMaxDamage(TFCItems.IgInStoneUses);
		BismuthHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","BismuthHammer",16372),TFCItems.BismuthToolMaterial).setUnlocalizedName("Bismuth Hammer").setMaxDamage(TFCItems.BismuthUses);
		BismuthBronzeHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","BismuthBronzeHammer",16373),TFCItems.BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hammer").setMaxDamage(TFCItems.BismuthBronzeUses);
		BlackBronzeHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","BlackBronzeHammer",16374),TFCItems.BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hammer").setMaxDamage(TFCItems.BlackBronzeUses);
		BlackSteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","BlackSteelHammer",16375),TFCItems.BlackSteelToolMaterial).setUnlocalizedName("Black Steel Hammer").setMaxDamage(TFCItems.BlackSteelUses);
		BlueSteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","BlueSteelHammer",16376),TFCItems.BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Hammer").setMaxDamage(TFCItems.BlueSteelUses);
		BronzeHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","BronzeHammer",16377),TFCItems.BronzeToolMaterial).setUnlocalizedName("Bronze Hammer").setMaxDamage(TFCItems.BronzeUses);
		CopperHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","CopperHammer",16378),TFCItems.CopperToolMaterial).setUnlocalizedName("Copper Hammer").setMaxDamage(TFCItems.CopperUses);
		WroughtIronHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","WroughtIronHammer",16379),TFCItems.IronToolMaterial).setUnlocalizedName("Wrought Iron Hammer").setMaxDamage(TFCItems.WroughtIronUses);
		RedSteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","RedSteelHammer",16380),TFCItems.RedSteelToolMaterial).setUnlocalizedName("Red Steel Hammer").setMaxDamage(TFCItems.RedSteelUses);
		RoseGoldHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","RoseGoldHammer",16381),TFCItems.RoseGoldToolMaterial).setUnlocalizedName("Rose Gold Hammer").setMaxDamage(TFCItems.RoseGoldUses);
		SteelHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","SteelHammer",16382),TFCItems.SteelToolMaterial).setUnlocalizedName("Steel Hammer").setMaxDamage(TFCItems.SteelUses);
		TinHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","TinHammer",16383),TFCItems.TinToolMaterial).setUnlocalizedName("Tin Hammer").setMaxDamage(TFCItems.TinUses);
		ZincHammer = new ItemHammer(TFC_Settings.getIntFor(config,"item","ZincHammer",16384),TFCItems.ZincToolMaterial).setUnlocalizedName("Zinc Hammer").setMaxDamage(TFCItems.ZincUses);

		Ink = new ItemTerra(TFC_Settings.getIntFor(config,"item","Ink",16391)).setUnlocalizedName("Ink");

		BellowsItem = new ItemBellows(TFC_Settings.getIntFor(config,"item","BellowsItem",16406)).setUnlocalizedName("Bellows");

		FireStarter = new ItemFirestarter(TFC_Settings.getIntFor(config,"item","FireStarter",16407)).setFolder("tools/").setUnlocalizedName("Firestarter");

		//Tool heads
		BismuthPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthPickaxeHead",16500)).setUnlocalizedName("Bismuth Pick Head");
		BismuthBronzePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BismuthBronzePickaxeHead",16501)).setUnlocalizedName("Bismuth Bronze Pick Head");
		BlackBronzePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackBronzePickaxeHead",16502)).setUnlocalizedName("Black Bronze Pick Head");
		BlackSteelPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlackSteelPickaxeHead",16503)).setUnlocalizedName("Black Steel Pick Head");
		BlueSteelPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BlueSteelPickaxeHead",16504)).setUnlocalizedName("Blue Steel Pick Head");
		BronzePickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","BronzePickaxeHead",16505)).setUnlocalizedName("Bronze Pick Head");
		CopperPickaxeHead = new ItemMiscToolHead(TFC_Settings.getIntFor(config,"item","CopperPickaxeHead",16506)).setUnlocalizedName("Copper Pick Head");
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

		HCBlackSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCBlackSteel",16617)).setUnlocalizedName("HC Black Steel Unshaped");
		WeakBlueSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedWeakBlueSteel",16618)).setUnlocalizedName("Weak Blue Steel Unshaped");
		HCBlueSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCBlueSteel",16619)).setUnlocalizedName("HC Blue Steel Unshaped");
		WeakRedSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedWeakRedSteel",16621)).setUnlocalizedName("Weak Red Steel Unshaped");
		HCRedSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCRedSteel",16622)).setUnlocalizedName("HC Red Steel Unshaped");
		WeakSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedWeakSteel",16623)).setUnlocalizedName("Weak Steel Unshaped");
		HCSteelUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnshapedHCSteel",16624)).setUnlocalizedName("HC Steel Unshaped");
		Coke = (new ItemTerra(TFC_Settings.getIntFor(config,"item","Coke",16625)).setUnlocalizedName("Coke"));

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

		Powder = new ItemTerra(TFC_Settings.getIntFor(config,"item","Flux",16639)).setMetaNames(new String[]
				{"Flux", "Kaolinite Powder", "Graphite Powder", "Sulfur Powder", "Saltpeter Powder"}).setUnlocalizedName("Powder");

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

		WoodenBucketEmpty = (new ItemCustomBucket(TFC_Settings.getIntFor(config,"item","WoodenBucketEmpty",num), 0)).setUnlocalizedName("Wooden Bucket Empty");num++;
		WoodenBucketWater = (new ItemCustomBucket(TFC_Settings.getIntFor(config,"item","WoodenBucketWater",num), TFCBlocks.finiteWater.blockID)).setUnlocalizedName("Wooden Bucket Water").setContainerItem(WoodenBucketEmpty);num++;
		WoodenBucketMilk = (new ItemCustomBucketMilk(TFC_Settings.getIntFor(config,"item","WoodenBucketMilk",num))).setUnlocalizedName("Wooden Bucket Milk").setContainerItem(WoodenBucketEmpty);num++;

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

		FlatRock = (new ItemFlatRock(TFC_Settings.getIntFor(config,"item","FlatRock",num)).setFolder("rocks/flatrocks/").setUnlocalizedName("FlatRock"));num++;
		LooseRock = (new ItemLooseRock(TFC_Settings.getIntFor(config,"item","LooseRock",num)).setSpecialCraftingType(FlatRock).setFolder("rocks/").setMetaNames(Global.STONE_ALL).setUnlocalizedName("LooseRock"));num++;

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

		StoneKnife = new ItemCustomKnife(TFC_Settings.getIntFor(config,"item","StoneKnife",num),IgExToolMaterial).setUnlocalizedName("Stone Knife").setMaxDamage(IgExStoneUses);num++;
		SmallOreChunk = new ItemOreSmall(TFC_Settings.getIntFor(config,"item","SmallOreChunk",num++)).setUnlocalizedName("Small Ore");
		SinglePlank = new ItemPlank(TFC_Settings.getIntFor(config,"item","SinglePlank",num++)).setUnlocalizedName("SinglePlank");

		RedSteelBucketEmpty = (new ItemCustomRedSteelBucket(TFC_Settings.getIntFor(config,"item","RedSteelBucketEmpty",num++), 0)).setUnlocalizedName("Red Steel Bucket Empty");
		RedSteelBucketWater = (new ItemCustomRedSteelBucket(TFC_Settings.getIntFor(config,"item","RedSteelBucketWater",num++), Block.waterMoving.blockID)).setUnlocalizedName("Red Steel Bucket Water").setContainerItem(RedSteelBucketEmpty);

		BlueSteelBucketEmpty = (new ItemCustomBlueSteelBucket(TFC_Settings.getIntFor(config,"item","BlueSteelBucketEmpty",num++), 0)).setUnlocalizedName("Blue Steel Bucket Empty");
		BlueSteelBucketLava = (new ItemCustomBlueSteelBucket(TFC_Settings.getIntFor(config,"item","BlueSteelBucketLava",num++), Block.lavaMoving.blockID)).setUnlocalizedName("Blue Steel Bucket Lava").setContainerItem(BlueSteelBucketEmpty);

		Quern = new ItemTerra(TFC_Settings.getIntFor(config,"item","Quern",num++)).setUnlocalizedName("Quern").setMaxDamage(250);
		FlintSteel = new ItemFlintSteel(TFC_Settings.getIntFor(config,"item","FlintSteel",num++)).setUnlocalizedName("flintAndSteel").setMaxDamage(200).func_111206_d("flint_and_steel");

		DoorOak = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorOak", num++), 0).setUnlocalizedName("Oak Door");
		DoorAspen = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorAspen", num++), 1).setUnlocalizedName("Aspen Door");
		DoorBirch = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorBirch", num++), 2).setUnlocalizedName("Birch Door");
		DoorChestnut = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorChestnut", num++), 3).setUnlocalizedName("Chestnut Door");
		DoorDouglasFir = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorDouglasFir", num++), 4).setUnlocalizedName("Douglas Fir Door");
		DoorHickory = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorHickory", num++), 5).setUnlocalizedName("Hickory Door");
		DoorMaple = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorMaple", num++), 6).setUnlocalizedName("Maple Door");
		DoorAsh = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorAsh", num++), 7).setUnlocalizedName("Ash Door");
		DoorPine = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorPine", num++), 8).setUnlocalizedName("Pine Door");
		DoorSequoia = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorSequoia", num++), 9).setUnlocalizedName("Sequoia Door");
		DoorSpruce = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorSpruce", num++), 10).setUnlocalizedName("Spruce Door");
		DoorSycamore = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorSycamore", num++), 11).setUnlocalizedName("Sycamore Door");
		DoorWhiteCedar = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorWhiteCedar", num++), 12).setUnlocalizedName("White Cedar Door");
		DoorWhiteElm = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorWhiteElm", num++), 13).setUnlocalizedName("White Elm Door");
		DoorWillow = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorWillow", num++), 14).setUnlocalizedName("Willow Door");
		DoorKapok = new ItemWoodDoor(TFC_Settings.getIntFor(config,"item","DoorKapok", num++), 15).setUnlocalizedName("Kapok Door");

		Beer = new ItemAlcohol(TFC_Settings.getIntFor(config,"item","Beer",num++)).setUnlocalizedName("Beer").setCreativeTab(CreativeTabs.tabFood);
		Cider = new ItemAlcohol(TFC_Settings.getIntFor(config,"item","Cider",num++)).setUnlocalizedName("Cider").setCreativeTab(CreativeTabs.tabFood);
		Rum = new ItemAlcohol(TFC_Settings.getIntFor(config,"item","Rum",num++)).setUnlocalizedName("Rum").setCreativeTab(CreativeTabs.tabFood);
		RyeWhiskey = new ItemAlcohol(TFC_Settings.getIntFor(config,"item","RyeWhiskey",num++)).setUnlocalizedName("RyeWhiskey").setCreativeTab(CreativeTabs.tabFood);
		Sake = new ItemAlcohol(TFC_Settings.getIntFor(config,"item","Sake",num++)).setUnlocalizedName("Sake").setCreativeTab(CreativeTabs.tabFood);
		Vodka = new ItemAlcohol(TFC_Settings.getIntFor(config,"item","Vodka",num++)).setUnlocalizedName("Vodka").setCreativeTab(CreativeTabs.tabFood);
		Whiskey = new ItemAlcohol(TFC_Settings.getIntFor(config,"item","Whiskey",num++)).setUnlocalizedName("Whiskey").setCreativeTab(CreativeTabs.tabFood);

		Blueprint = new ItemBlueprint(TFC_Settings.getIntFor(config,"item","Blueprint", num++));
		writabeBookTFC = new ItemWritableBookTFC(TFC_Settings.getIntFor(config,"item","WritableBookTFC", num++),"Fix Me I'm Broken").setUnlocalizedName("book");
		WoolYarn = new ItemTerra(TFC_Settings.getIntFor(config, "item", "WoolYarn", num++)).setUnlocalizedName("WoolYarn").setCreativeTab(TFCTabs.TFCMaterials);
		Wool = new ItemTerra(TFC_Settings.getIntFor(config,"item","Wool",num++)).setUnlocalizedName("Wool").setCreativeTab(TFCTabs.TFCMaterials);
		WoolCloth = new ItemTerra(TFC_Settings.getIntFor(config, "item", "WoolCloth", num++)).setUnlocalizedName("WoolCloth").setCreativeTab(TFCTabs.TFCMaterials);
		Spindle = new ItemSpindle(TFC_Settings.getIntFor(config,"item","Spindle",num++),SedToolMaterial).setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCMaterials);

		SpindleHead = new ItemPotteryBase(TFC_Settings.getIntFor(config, "item", "SpindleHead", num++)).setMetaNames(new String[]{"Clay Spindle", "Spindle Head"}).setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCMaterials);
		StoneBrick = (new ItemStoneBrick(TFC_Settings.getIntFor(config,"item","ItemStoneBrick2",num++)).setFolder("tools/").setUnlocalizedName("ItemStoneBrick"));
		Mortar = new ItemTerra(TFC_Settings.getIntFor(config,"item","Mortar",num++)).setFolder("tools/").setUnlocalizedName("Mortar").setCreativeTab(TFCTabs.TFCMaterials);
		Limewater = new ItemCustomBucket(TFC_Settings.getIntFor(config,"item","Limewater",num++),2).setFolder("tools/").setUnlocalizedName("Lime Water").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCMaterials);
		Hide = new ItemTerra(TFC_Settings.getIntFor(config,"item","Hide",num++)).setFolder("tools/").setUnlocalizedName("Hide").setCreativeTab(TFCTabs.TFCMaterials);
		SoakedHide = new ItemTerra(TFC_Settings.getIntFor(config,"item","SoakedHide",num++)).setFolder("tools/").setUnlocalizedName("Soaked Hide").setCreativeTab(TFCTabs.TFCMaterials);
		ScrapedHide = new ItemTerra(TFC_Settings.getIntFor(config,"item","ScrapedHide",num++)).setFolder("tools/").setUnlocalizedName("Scraped Hide").setCreativeTab(TFCTabs.TFCMaterials);
		PrepHide = new ItemTerra(TFC_Settings.getIntFor(config,"item","PrepHide",num++)).setFolder("tools/").setFolder("tools/").setUnlocalizedName("Prep Hide").setCreativeTab(TFCTabs.TFCMaterials);

		SheepSkin = new ItemTerra(TFC_Settings.getIntFor(config,"item","SheepSkin",num++)).setFolder("tools/").setUnlocalizedName("Sheep Skin").setCreativeTab(TFCTabs.TFCMaterials);
		muttonRaw = new ItemTerra(TFC_Settings.getIntFor(config,"item","muttonRaw",num++)).setFolder("food/").setUnlocalizedName("Mutton Raw");
		muttonCooked =  new ItemTerraFood(TFC_Settings.getIntFor(config,"item","muttonCooked",num++), 40, 0.8F, true, 48).setUnlocalizedName("Mutton Cooked");
		FlatLeather = (new ItemFlatLeather(TFC_Settings.getIntFor(config,"item","FlatLeather2",num++)).setFolder("tools/").setUnlocalizedName("Flat Leather"));
		TerraLeather = new ItemLeather(TFC_Settings.getIntFor(config,"item","TFCLeather",num++)).setSpecialCraftingType(FlatLeather).setFolder("tools/").setUnlocalizedName("TFC Leather").setCreativeTab(TFCTabs.TFCMaterials);

		Straw = new ItemTerra(TFC_Settings.getIntFor(config,"item","Straw",num++)).setFolder("plants/").setUnlocalizedName("Straw");
		FlatClay = (new ItemFlatLeather(TFC_Settings.getIntFor(config,"item","FlatClay",num++)).setFolder("pottery/").setMetaNames(new String[]{"clay flat light", "clay flat dark", "clay flat fire", "clay flat dark fire"}).setUnlocalizedName(""));

		PotteryJug = new ItemPotteryJug(TFC_Settings.getIntFor(config,"item","PotteryJug",num++)).setUnlocalizedName("Jug");
		PotterySmallVessel = new ItemPotterySmallVessel(TFC_Settings.getIntFor(config,"items","PotterySmallVessel",num++)).setUnlocalizedName("Small Vessel");
		PotteryLargeVessel = new ItemPotteryLargeVessel(TFC_Settings.getIntFor(config,"items","PotteryLargeVessel",num++)).setUnlocalizedName("Large Vessel");
		PotteryPot = new ItemPotteryPot(TFC_Settings.getIntFor(config,"item","PotteryPot",num++)).setUnlocalizedName("Pot");
		CeramicMold = new ItemPotteryBase(TFC_Settings.getIntFor(config,"item","CeramicMold",16409)).setMetaNames(new String[]{"Clay Mold","Ceramic Mold"}).setUnlocalizedName("Mold");
		Item.itemsList[Item.clay.itemID] = null; Item.itemsList[Item.clay.itemID] = (new ItemClay(Item.clay.itemID).setSpecialCraftingType(FlatClay, new ItemStack(FlatClay, 1, 1))).setMetaNames(new String[]{"Clay", "Fire Clay"}).setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials);
		ClayMoldAxe = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldAxe",num++)).setMetaNames(new String[]{"Clay Mold Axe","Ceramic Mold Axe",
				"Ceramic Mold Axe Copper","Ceramic Mold Axe Bronze","Ceramic Mold Axe Bismuth Bronze","Ceramic Mold Axe Black Bronze"}).setUnlocalizedName("Axe Mold");
		ClayMoldChisel = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldChisel",num++)).setMetaNames(new String[]{"Clay Mold Chisel","Ceramic Mold Chisel",
				"Ceramic Mold Chisel Copper","Ceramic Mold Chisel Bronze","Ceramic Mold Chisel Bismuth Bronze","Ceramic Mold Chisel Black Bronze"}).setUnlocalizedName("Chisel Mold");
		ClayMoldHammer = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldHammer",num++)).setMetaNames(new String[]{"Clay Mold Hammer","Ceramic Mold Hammer",
				"Ceramic Mold Hammer Copper","Ceramic Mold Hammer Bronze","Ceramic Mold Hammer Bismuth Bronze","Ceramic Mold Hammer Black Bronze"}).setUnlocalizedName("Hammer Mold");
		ClayMoldHoe = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldHoe",num++)).setMetaNames(new String[]{"Clay Mold Hoe","Ceramic Mold Hoe",
				"Ceramic Mold Hoe Copper","Ceramic Mold Hoe Bronze","Ceramic Mold Hoe Bismuth Bronze","Ceramic Mold Hoe Black Bronze"}).setUnlocalizedName("Hoe Mold");
		ClayMoldKnife = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldKnife",num++)).setMetaNames(new String[]{"Clay Mold Knife","Ceramic Mold Knife",
				"Ceramic Mold Knife Copper","Ceramic Mold Knife Bronze","Ceramic Mold Knife Bismuth Bronze","Ceramic Mold Knife Black Bronze"}).setUnlocalizedName("Knife Mold");
		ClayMoldMace = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldMace",num++)).setMetaNames(new String[]{"Clay Mold Mace","Ceramic Mold Mace",
				"Ceramic Mold Mace Copper","Ceramic Mold Mace Bronze","Ceramic Mold Mace Bismuth Bronze","Ceramic Mold Mace Black Bronze"}).setUnlocalizedName("Mace Mold");
		ClayMoldPick = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldPick",num++)).setMetaNames(new String[]{"Clay Mold Pick","Ceramic Mold Pick",
				"Ceramic Mold Pick Copper","Ceramic Mold Pick Bronze","Ceramic Mold Pick Bismuth Bronze","Ceramic Mold Pick Black Bronze"}).setUnlocalizedName("Pick Mold");
		ClayMoldProPick = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldProPick",num++)).setMetaNames(new String[]{"Clay Mold ProPick","Ceramic Mold ProPick",
				"Ceramic Mold ProPick Copper","Ceramic Mold ProPick Bronze","Ceramic Mold ProPick Bismuth Bronze","Ceramic Mold ProPick Black Bronze"}).setUnlocalizedName("ProPick Mold");
		ClayMoldSaw = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldSaw",num++)).setMetaNames(new String[]{"Clay Mold Saw","Ceramic Mold Saw",
				"Ceramic Mold Saw Copper","Ceramic Mold Saw Bronze","Ceramic Mold Saw Bismuth Bronze","Ceramic Mold Saw Black Bronze"}).setUnlocalizedName("Saw Mold");
		ClayMoldScythe = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldScythe",num++)).setMetaNames(new String[]{"Clay Mold Scythe","Ceramic Mold Scythe",
				"Ceramic Mold Scythe Copper","Ceramic Mold Scythe Bronze","Ceramic Mold Scythe Bismuth Bronze","Ceramic Mold Scythe Black Bronze"}).setUnlocalizedName("Scythe Mold");
		ClayMoldShovel = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldShovel",num++)).setMetaNames(new String[]{"Clay Mold Shovel","Ceramic Mold Shovel",
				"Ceramic Mold Shovel Copper","Ceramic Mold Shovel Bronze","Ceramic Mold Shovel Bismuth Bronze","Ceramic Mold Shovel Black Bronze"}).setUnlocalizedName("Shovel Mold");
		ClayMoldSword = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldSword",num++)).setMetaNames(new String[]{"Clay Mold Sword","Ceramic Mold Sword",
				"Ceramic Mold Sword Copper","Ceramic Mold Sword Bronze","Ceramic Mold Sword Bismuth Bronze","Ceramic Mold Sword Black Bronze"}).setUnlocalizedName("Sword Mold");
		ClayMoldJavelin = new ItemPotteryMold(TFC_Settings.getIntFor(config,"item","ClayMoldJavelin",num++)).setMetaNames(new String[]{"Clay Mold Javelin","Ceramic Mold Javelin",
				"Ceramic Mold Javelin Copper","Ceramic Mold Javelin Bronze","Ceramic Mold Javelin Bismuth Bronze","Ceramic Mold Javelin Black Bronze"}).setUnlocalizedName("Javelin Mold");

		TuyereCopper = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereCopper",num++), 40, 0).setUnlocalizedName("Copper Tuyere");
		TuyereBronze = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereBronze",num++), 80, 1).setUnlocalizedName("Bronze Tuyere");
		TuyereBlackBronze = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereBlackBronze",num++), 80, 1).setUnlocalizedName("Black Bronze Tuyere");
		TuyereBismuthBronze = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereBismuthBronze",num++), 80, 1).setUnlocalizedName("Bismuth Bronze Tuyere");
		TuyereWroughtIron = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereWroughtIron",num++), 120, 2).setUnlocalizedName("Wrought Iron Tuyere");
		TuyereSteel = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereSteel",num++), 180, 3).setUnlocalizedName("Steel Tuyere");
		TuyereBlackSteel = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereBlackSteel",num++), 260, 4).setUnlocalizedName("Black Steel Tuyere");
		TuyereRedSteel = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereRedSteel",num++), 400, 5).setUnlocalizedName("Red Steel Tuyere");
		TuyereBlueSteel = new ItemTuyere(TFC_Settings.getIntFor(config,"item","TuyereBlueSteel",num++), 500, 6).setUnlocalizedName("Blue Steel Tuyere");

		Bloom = new ItemBloom(TFC_Settings.getIntFor(config,"item","Bloom",num++)).setFolder("ingots/").setUnlocalizedName("Iron Bloom");
		RawBloom = new ItemBloom(TFC_Settings.getIntFor(config,"item","RawBloom",num++)).setFolder("ingots/").setUnlocalizedName("Raw Iron Bloom");

		UnknownIngot = new ItemIngot(TFC_Settings.getIntFor(config,"item","UnknownIngot",num++)).setUnlocalizedName("Unknown Ingot");
		UnknownUnshaped = new ItemMeltedMetal(TFC_Settings.getIntFor(config,"item","UnknownUnshaped",num++)).setUnlocalizedName("Unknown Unshaped");

		CalamariRaw = new ItemTerra(TFC_Settings.getIntFor(config,"item","CalamariRaw",num++)).setFolder("").setUnlocalizedName("Calamari Raw");
		CalamariCooked = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","CalamariCooked",num++), 29, 0.6F, true, 49).setFolder("").setUnlocalizedName("Calamari Cooked");

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
				TinAxe,ZincAxe};

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

		Recipes.Scythes = new Item[]{TFCItems.BismuthScythe,TFCItems.BismuthBronzeScythe,TFCItems.BlackBronzeScythe,
				TFCItems.BlackSteelScythe,TFCItems.BlueSteelScythe,TFCItems.BronzeScythe,TFCItems.CopperScythe,
				TFCItems.WroughtIronScythe,TFCItems.RedSteelScythe,TFCItems.RoseGoldScythe,TFCItems.SteelScythe,
				TFCItems.TinScythe,TFCItems.ZincScythe};

		Recipes.Spindle = new Item[]{TFCItems.Spindle};

		Recipes.Gems  = new Item[]{TFCItems.GemAgate, TFCItems.GemAmethyst, TFCItems.GemBeryl, TFCItems.GemDiamond, TFCItems.GemEmerald, TFCItems.GemGarnet, 
				TFCItems.GemJade, TFCItems.GemJasper, TFCItems.GemOpal,TFCItems.GemRuby,TFCItems.GemSapphire,TFCItems.GemTopaz,TFCItems.GemTourmaline};

		Meals = new Item[]{MealMoveSpeed, MealDigSpeed, MealDamageBoost, MealJump, MealDamageResist, 
				MealFireResist, MealWaterBreathing, MealNightVision};

		((TFCTabs)TFCTabs.TFCTools).setTabIconItemIndex(TFCItems.SteelHammer.itemID);
		((TFCTabs)TFCTabs.TFCMaterials).setTabIconItemIndex(TFCItems.Spindle.itemID);
		((TFCTabs)TFCTabs.TFCUnfinished).setTabIconItemIndex(TFCItems.SteelHammerHead.itemID);
		((TFCTabs)TFCTabs.TFCArmor).setTabIconItemIndex(TFCItems.SteelHelmet.itemID);        

		registerMetals();

		System.out.println(new StringBuilder().append("[TFC] Done Loading Items").toString());
		if (config != null) {
			config.save();
		}
	}

	private static void registerMetals() 
	{
		Global.BISMUTH = new Metal("Bismuth", BismuthUnshaped.itemID, BismuthIngot.itemID);
		Global.BISMUTHBRONZE = new Metal("Bismuth Bronze", BismuthBronzeUnshaped.itemID, BismuthBronzeIngot.itemID);
		Global.BLACKBRONZE = new Metal("Black Bronze", BlackBronzeUnshaped.itemID, BlackBronzeIngot.itemID); 
		Global.BLACKSTEEL = new Metal("Black Steel", BlackSteelUnshaped.itemID, BlackSteelIngot.itemID); 
		Global.BLUESTEEL = new Metal("Blue Steel", BlueSteelUnshaped.itemID, BlueSteelIngot.itemID); 
		Global.BRASS = new Metal("Brass", BrassUnshaped.itemID, BrassIngot.itemID); 
		Global.BRONZE = new Metal("Bronze", BronzeUnshaped.itemID, BronzeIngot.itemID);
		Global.COPPER = new Metal("Copper", CopperUnshaped.itemID, CopperIngot.itemID);
		Global.GOLD = new Metal("Gold", GoldUnshaped.itemID, GoldIngot.itemID); 
		Global.WROUGHTIRON = new Metal("Iron", WroughtIronUnshaped.itemID, WroughtIronIngot.itemID); 
		Global.LEAD = new Metal("Lead", LeadUnshaped.itemID, LeadIngot.itemID); 
		Global.NICKEL = new Metal("Nickel", NickelUnshaped.itemID, NickelIngot.itemID); 
		Global.PIGIRON = new Metal("Pig Iron", PigIronUnshaped.itemID, PigIronIngot.itemID); 
		Global.PLATINUM = new Metal("Platinum", PlatinumUnshaped.itemID, PlatinumIngot.itemID); 
		Global.REDSTEEL = new Metal("Red Steel", RedSteelUnshaped.itemID, RedSteelIngot.itemID); 
		Global.ROSEGOLD = new Metal("Rose Gold", RoseGoldUnshaped.itemID, RoseGoldIngot.itemID); 
		Global.SILVER = new Metal("Silver", SilverUnshaped.itemID, SilverIngot.itemID); 
		Global.STEEL = new Metal("Steel", SteelUnshaped.itemID, SteelIngot.itemID); 
		Global.STERLINGSILVER = new Metal("Sterling Silver", SterlingSilverUnshaped.itemID, SterlingSilverIngot.itemID); 
		Global.TIN = new Metal("Tin", TinUnshaped.itemID, TinIngot.itemID);
		Global.ZINC = new Metal("Zinc", ZincUnshaped.itemID, ZincIngot.itemID);
		Global.WEAKSTEEL = new Metal("Weak Steel", WeakSteelUnshaped.itemID, WeakSteelIngot.itemID); 
		Global.HCBLACKSTEEL = new Metal("HC Black Steel", HCBlackSteelUnshaped.itemID, HCBlackSteelIngot.itemID); 
		Global.WEAKREDSTEEL = new Metal("Weak Red Steel", WeakRedSteelUnshaped.itemID, WeakRedSteelIngot.itemID); 
		Global.HCREDSTEEL = new Metal("HC Red Steel", HCRedSteelUnshaped.itemID, HCRedSteelIngot.itemID); 
		Global.WEAKBLUESTEEL = new Metal("Weak Blue Steel", WeakBlueSteelUnshaped.itemID, WeakBlueSteelIngot.itemID);
		Global.HCBLUESTEEL = new Metal("HC Blue Steel", HCBlueSteelUnshaped.itemID, HCBlueSteelIngot.itemID); 
		Global.UNKNOWN = new Metal("Unknown", UnknownUnshaped.itemID, UnknownIngot.itemID); 

		MetalRegistry.instance.addMetal(Global.BISMUTH, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BISMUTHBRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BLACKBRONZE, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.BLACKSTEEL, Alloy.EnumTier.TierV); 
		MetalRegistry.instance.addMetal(Global.BLUESTEEL, Alloy.EnumTier.TierV); 
		MetalRegistry.instance.addMetal(Global.BRASS, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.BRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.COPPER, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.GOLD, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.WROUGHTIRON, Alloy.EnumTier.TierIII); 
		MetalRegistry.instance.addMetal(Global.LEAD, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.NICKEL, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.PIGIRON, Alloy.EnumTier.TierIV); 
		MetalRegistry.instance.addMetal(Global.PLATINUM, Alloy.EnumTier.TierV); 
		MetalRegistry.instance.addMetal(Global.REDSTEEL, Alloy.EnumTier.TierV); 
		MetalRegistry.instance.addMetal(Global.ROSEGOLD, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.SILVER, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.STEEL, Alloy.EnumTier.TierIV); 
		MetalRegistry.instance.addMetal(Global.STERLINGSILVER, Alloy.EnumTier.TierI); 
		MetalRegistry.instance.addMetal(Global.TIN, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.ZINC, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.WEAKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCBLACKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.WEAKREDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCREDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.WEAKBLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCBLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.UNKNOWN, Alloy.EnumTier.TierI);

		Alloy Bronze = new Alloy(Global.BRONZE, Alloy.EnumTier.TierI);
		Bronze.addIngred(Global.COPPER, 88, 92);
		Bronze.addIngred(Global.TIN, 8, 12);
		AlloyManager.instance.addAlloy(Bronze);

		Alloy Brass = new Alloy(Global.BRASS, Alloy.EnumTier.TierI);
		Brass.addIngred(Global.COPPER, 88, 92);
		Brass.addIngred(Global.ZINC, 8, 12);
		AlloyManager.instance.addAlloy(Brass);

		Alloy RoseGold = new Alloy(Global.ROSEGOLD, Alloy.EnumTier.TierI);
		RoseGold.addIngred(Global.GOLD, 70, 85);
		RoseGold.addIngred(Global.COPPER, 15, 30);
		AlloyManager.instance.addAlloy(RoseGold);

		Alloy BlackBronze = new Alloy(Global.BLACKBRONZE, Alloy.EnumTier.TierI);
		BlackBronze.addIngred(Global.GOLD, 10, 20);
		BlackBronze.addIngred(Global.COPPER, 50, 70);
		BlackBronze.addIngred(Global.SILVER, 10, 20);
		AlloyManager.instance.addAlloy(BlackBronze);

		Alloy BismuthBronze = new Alloy(Global.BISMUTHBRONZE, Alloy.EnumTier.TierI);
		BismuthBronze.addIngred(Global.ZINC, 20, 30);
		BismuthBronze.addIngred(Global.COPPER, 50, 70);
		BismuthBronze.addIngred(Global.BISMUTH, 10, 20);
		AlloyManager.instance.addAlloy(BismuthBronze);

		Alloy SterlingSilver = new Alloy(Global.STERLINGSILVER, Alloy.EnumTier.TierI);
		SterlingSilver.addIngred(Global.SILVER, 60, 80);
		SterlingSilver.addIngred(Global.COPPER, 20, 40);
		AlloyManager.instance.addAlloy(SterlingSilver);

		Alloy WeakSteel = new Alloy(Global.WEAKSTEEL, Alloy.EnumTier.TierIII);
		WeakSteel.addIngred(Global.STEEL, 50, 70);
		WeakSteel.addIngred(Global.NICKEL, 15, 25);
		WeakSteel.addIngred(Global.BLACKBRONZE, 15, 25);
		AlloyManager.instance.addAlloy(WeakSteel);

		Alloy WeakRedSteel = new Alloy(Global.WEAKREDSTEEL, Alloy.EnumTier.TierIII);
		WeakRedSteel.addIngred(Global.BLACKSTEEL, 50, 60);
		WeakRedSteel.addIngred(Global.ROSEGOLD, 10, 15);
		WeakRedSteel.addIngred(Global.BRASS, 10, 15);
		WeakRedSteel.addIngred(Global.STEEL, 20, 25);
		AlloyManager.instance.addAlloy(WeakRedSteel);

		Alloy WeakBlueSteel = new Alloy(Global.WEAKBLUESTEEL, Alloy.EnumTier.TierIII);
		WeakBlueSteel.addIngred(Global.BLACKSTEEL, 50, 60);
		WeakBlueSteel.addIngred(Global.BISMUTHBRONZE, 10, 15);
		WeakBlueSteel.addIngred(Global.STERLINGSILVER, 10, 15);
		WeakBlueSteel.addIngred(Global.STEEL, 20, 25);
		AlloyManager.instance.addAlloy(WeakBlueSteel);
	}

	public static void SetupPlans(int num)
	{
		PickaxeHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","PickaxeHeadPlan",num)).setUnlocalizedName("PickaxeHeadPlan");num++;
		ShovelHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","ShovelHeadPlan",num)).setUnlocalizedName("ShovelHeadPlan");num++;
		HoeHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","HoeHeadPlan",num)).setUnlocalizedName("HoeHeadPlan");num++;
		AxeHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","AxeHeadPlan",num)).setUnlocalizedName("AxeHeadPlan");num++;
		HammerHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","HammerHeadPlan",num)).setUnlocalizedName("HammerHeadPlan");num++;
		ChiselHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","ChiselHeadPlan",num)).setUnlocalizedName("ChiselHeadPlan");num++;
		SwordBladePlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","SwordBladePlan",num)).setUnlocalizedName("SwordBladePlan");num++;
		MaceHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","MaceHeadPlan",num)).setUnlocalizedName("MaceHeadPlan");num++;
		SawBladePlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","SawBladePlan",num)).setUnlocalizedName("SawBladePlan");num++;
		ProPickHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","ProPickHeadPlan",num)).setUnlocalizedName("ProPickHeadPlan");num++;
		HelmetPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","HelmetPlan",num)).setUnlocalizedName("HelmetPlan");num++;
		ChestplatePlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","ChestplatePlan",num)).setUnlocalizedName("ChestplatePlan");num++;
		GreavesPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","GreavesPlan",num)).setUnlocalizedName("GreavesPlan");num++;
		BootsPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","BootsPlan",num)).setUnlocalizedName("BootsPlan");num++;
		ScythePlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","ScythePlan",num)).setUnlocalizedName("ScythePlan");num++;
		KnifePlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","KnifePlan",num)).setUnlocalizedName("KnifePlan");num++;
		BucketPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","BucketPlan",num)).setUnlocalizedName("BucketPlan");num++;
		JavelinHeadPlan = new ItemPlan(TFC_Settings.getIntFor(config,"item","JavelinHeadPlan",num)).setUnlocalizedName("JavelinHeadPlan");num++;
	}

	public static void SetupFood(int num)
	{
		FruitTreeSapling1 = new ItemFruitTreeSapling(TFC_Settings.getIntFor(config,"item","FruitSapling1", num), 0).setUnlocalizedName("FruitSapling1");num++;
		FruitTreeSapling2 = new ItemFruitTreeSapling(TFC_Settings.getIntFor(config,"item","FruitSapling2", num), 8).setUnlocalizedName("FruitSapling2");num++;
		RedApple = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Red Apple",num), 15, 0.1F, false, 2).setUnlocalizedName("Red Apple");num++;
		Banana = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Banana",num), 10, 0.1F, false, 3).setUnlocalizedName("Banana");num++;
		Orange = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Orange",num), 10, 0.1F, false, 4).setUnlocalizedName("Orange");num++;
		GreenApple = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Green Apple",num), 15, 0.1F, false, 5).setUnlocalizedName("Green Apple");num++;
		Lemon = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Lemon",num), 10, 0.03F, false, 6).setUnlocalizedName("Lemon");num++;
		Olive = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Olive",num), 10, 0.05F, false, 7).setUnlocalizedName("Olive");num++;
		Cherry = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Cherry",num), 10, 0.03F, false, 8).setUnlocalizedName("Cherry");num++;
		Peach = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Peach",num), 12, 0.1F, false, 9).setUnlocalizedName("Peach");num++;
		Plum = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Fruit.Plum",num), 10, 0.1F, false, 10).setUnlocalizedName("Plum");num++;
		EggCooked = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Meat.EggCooked",num), 25, 0.4F, false, 11).setUnlocalizedName("Egg Cooked");num++;

		WheatGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","WheatGrain",num++), 1, 0.4F, false, 12).setUnlocalizedName("Wheat Grain");
		BarleyGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","BarleyGrain",num++), 1, 0.4F, false, 14).setUnlocalizedName("Barley Grain");
		OatGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","OatGrain",num++), 1, 0.4F, false, 16).setUnlocalizedName("Oat Grain");
		RyeGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RyeGrain",num++), 1, 0.4F, false, 18).setUnlocalizedName("Rye Grain");
		RiceGrain = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RiceGrain",num++), 1, 0.4F, false, 20).setUnlocalizedName("Rice Grain");
		MaizeEar = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","MaizeEar",num++), 10, 0.4F, false, 22).setUnlocalizedName("Maize Ear");
		Tomato = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Tomato",num++), 15, 0.4F, false, 24).setUnlocalizedName("Tomato");
		Potato = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Potato",num++), 22, 0.4F, false, 25).setUnlocalizedName("Potato");
		Onion = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Onion",num++), 10, 0.4F, false, 27).setUnlocalizedName("Onion");
		Cabbage = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Cabbage",num++), 20, 0.4F, false, 28).setUnlocalizedName("Cabbage");
		Garlic = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Garlic",num++), 10, 0.4F, false, 29).setUnlocalizedName("Garlic");
		Carrot = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Carrot",num++), 5, 0.4F, false, 30).setUnlocalizedName("Carrot");
		Sugarcane = new ItemTerra(TFC_Settings.getIntFor(config,"item","Sugarcane",num++)).setFolder("plants/").setUnlocalizedName("Sugarcane");
		Hemp = new ItemTerra(TFC_Settings.getIntFor(config,"item","Hemp",num++)).setFolder("plants/").setUnlocalizedName("Hemp");
		Soybean = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Soybeans",num++), 10, 0.4F, false, 31).setUnlocalizedName("Soybeans");
		Greenbeans = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Greenbeans",num++), 10, 0.4F, false, 32).setUnlocalizedName("Greenbeans");
		GreenBellPepper = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","GreenBellPepper",num++), 10, 0.4F, false, 34).setUnlocalizedName("Green Bell Pepper");
		YellowBellPepper = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","YellowBellPepper",num++), 10, 0.4F, false, 35).setUnlocalizedName("Yellow Bell Pepper");
		RedBellPepper = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RedBellPepper",num++), 10, 0.4F, false, 36).setUnlocalizedName("Red Bell Pepper");
		Squash = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","Squash",num++), 12, 0.4F, false, 37).setUnlocalizedName("Squash");

		WheatWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","WheatWhole",num++)).setFolder("food/").setUnlocalizedName("Wheat Whole");
		BarleyWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","BarleyWhole",num++)).setFolder("food/").setUnlocalizedName("Barley Whole");
		OatWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","OatWhole",num++)).setFolder("food/").setUnlocalizedName("Oat Whole");
		RyeWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","RyeWhole",num++)).setFolder("food/").setUnlocalizedName("Rye Whole");
		RiceWhole = new ItemTerra(TFC_Settings.getIntFor(config,"item","RiceWhole",num++)).setFolder("food/").setUnlocalizedName("Rice Whole");

		MealGeneric = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealGeneric",num++)).setUnlocalizedName("MealGeneric");
		MealMoveSpeed = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealMoveSpeed",num++)).setPotionEffect(new PotionEffect(Potion.moveSpeed.id,8000,4)).setUnlocalizedName("MealGeneric");
		MealDigSpeed = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealDigSpeed",num++)).setPotionEffect(new PotionEffect(Potion.digSpeed.id,8000,4)).setUnlocalizedName("MealGeneric");
		MealDamageBoost = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealDamageBoost",num++)).setPotionEffect(new PotionEffect(Potion.damageBoost.id,4000,4)).setUnlocalizedName("MealGeneric");
		MealJump = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealJump",num++)).setPotionEffect(new PotionEffect(Potion.jump.id,8000,4)).setUnlocalizedName("MealGeneric");
		MealDamageResist = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealDamageResist",num++)).setPotionEffect(new PotionEffect(Potion.resistance.id,8000,4)).setUnlocalizedName("MealGeneric");
		MealFireResist = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealFireResist",num++)).setPotionEffect(new PotionEffect(Potion.fireResistance.id,8000,4)).setUnlocalizedName("MealGeneric");
		MealWaterBreathing = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealWaterBreathing",num++)).setPotionEffect(new PotionEffect(Potion.waterBreathing.id,8000,4)).setUnlocalizedName("MealGeneric");
		MealNightVision = new ItemMeal(TFC_Settings.getIntFor(config,"item","MealNightVision",num++)).setPotionEffect(new PotionEffect(Potion.nightVision.id,4000,4)).setUnlocalizedName("MealGeneric");

		WheatGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","WheatGround",num++)).setFolder("food/").setUnlocalizedName("Wheat Ground");
		BarleyGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","BarleyGround",num++)).setFolder("food/").setUnlocalizedName("Barley Ground");
		OatGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","OatGround",num++)).setFolder("food/").setUnlocalizedName("Oat Ground");
		RyeGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","RyeGround",num++)).setFolder("food/").setUnlocalizedName("Rye Ground");
		RiceGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","RiceGround",num++)).setFolder("food/").setUnlocalizedName("Rice Ground");
		CornmealGround = new ItemTerra(TFC_Settings.getIntFor(config,"item","CornmealGround",num++)).setFolder("food/").setUnlocalizedName("Cornmeal Ground");

		WheatDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","WheatDough",num++), 1, 0.0F, false, 0).setUnlocalizedName("Wheat Dough");
		BarleyDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","BarleyDough",num++), 1, 0.0F, false, 0).setUnlocalizedName("Barley Dough");
		OatDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","OatDough",num++), 1, 0.0F, false, 0).setUnlocalizedName("Oat Dough");
		RyeDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RyeDough",num++), 1, 0.0F, false, 0).setUnlocalizedName("Rye Dough");
		RiceDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RiceDough",num++), 1, 0.0F, false, 0).setUnlocalizedName("Rice Dough");
		CornmealDough = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","CornmealDough",num++), 1, 0.0F, false, 0).setUnlocalizedName("Cornmeal Dough");

		BarleyBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","BarleyBread",num++), 25, 0.6F, false, 43).setUnlocalizedName("Barley Bread");
		OatBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","OatBread",num++), 25, 0.6F, false, 44).setUnlocalizedName("Oat Bread");
		RyeBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RyeBread",num++), 25, 0.6F, false, 45).setUnlocalizedName("Rye Bread");
		RiceBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","RiceBread",num++), 25, 0.6F, false, 46).setUnlocalizedName("Rice Bread");
		CornBread = new ItemTerraFood(TFC_Settings.getIntFor(config,"item","CornBread",num++), 25, 0.6F, false, 47).setUnlocalizedName("Corn Bread");

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
		String[] Names = {"Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Bronze", "Copper", "Wrought Iron", "Red Steel", "Steel"};
		String[] NamesNS = {"Bismuth", "BismuthBronze", "BlackBronze", "BlackSteel", "BlueSteel", "Bronze", "Copper", "WroughtIron", "RedSteel", "RoseGold", "Steel", "Tin", "Zinc"};
		String[] NamesNSO = {"Brass", "Gold", "Lead", "Nickel", "Pig Iron", "Platinum", "Silver", "Sterling Silver"};
		CommonProxy proxy = TerraFirmaCraft.proxy;      

		int i = 0;
		TFCItems.BismuthSheet = 		(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Bismuth Sheet"));num++;
		TFCItems.BismuthBronzeSheet = 	(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Bismuth Bronze Sheet"));num++;
		TFCItems.BlackBronzeSheet = 	(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Black Bronze Sheet"));num++;
		TFCItems.BlackSteelSheet = 		(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Black Steel Sheet"));num++;
		TFCItems.BlueSteelSheet = 		(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Blue Steel Sheet"));num++;
		TFCItems.BronzeSheet = 			(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Bronze Sheet"));num++;
		TFCItems.CopperSheet = 			(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Copper Sheet"));num++;
		TFCItems.WroughtIronSheet = 	(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Wrought Iron Sheet"));num++;
		TFCItems.RedSteelSheet = 		(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Red Steel Sheet"));num++;
		TFCItems.RoseGoldSheet = 		(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Rose Gold Sheet"));num++;
		TFCItems.SteelSheet = 			(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Steel Sheet"));num++;
		TFCItems.TinSheet = 			(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Tin Sheet"));num++;
		TFCItems.ZincSheet = 			(new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+" Sheet",num)).setUnlocalizedName("Zinc Sheet"));num++;

		i = 0;
		TFCItems.BismuthSheet2x = 		(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Bismuth Double Sheet"));num++;
		TFCItems.BismuthBronzeSheet2x = (new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Bismuth Bronze Double Sheet"));num++;
		TFCItems.BlackBronzeSheet2x = 	(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Black Bronze Double Sheet"));num++;
		TFCItems.BlackSteelSheet2x = 	(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Black Steel Double Sheet"));num++;
		TFCItems.BlueSteelSheet2x = 	(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Blue Steel Double Sheet"));num++;
		TFCItems.BronzeSheet2x = 		(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Bronze Double Sheet"));num++;
		TFCItems.CopperSheet2x = 		(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Copper Double Sheet"));num++;
		TFCItems.WroughtIronSheet2x = 	(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Wrought Iron Double Sheet"));num++;
		TFCItems.RedSteelSheet2x = 		(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Red Steel Double Sheet"));num++;
		TFCItems.RoseGoldSheet2x = 		(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Rose Gold Double Sheet"));num++;
		TFCItems.SteelSheet2x = 		(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Steel Double Sheet"));num++;
		TFCItems.TinSheet2x = 			(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Tin Double Sheet"));num++;
		TFCItems.ZincSheet2x = 			(new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNS[i++]+"Sheet2x",num)).setUnlocalizedName("Zinc Double Sheet"));num++;

		i = 0;
		TFCItems.BismuthBronzeUnfinishedBoots = 	(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.BlackBronzeUnfinishedBoots = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.BlackSteelUnfinishedBoots = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.BlueSteelUnfinishedBoots = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.BronzeUnfinishedBoots = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.CopperUnfinishedBoots = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.WroughtIronUnfinishedBoots = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.RedSteelUnfinishedBoots = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		TFCItems.SteelUnfinishedBoots = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Boots",num)).setUnlocalizedName(Names[i]+" Unfinished Boots")); num++;i++;
		i = 0;

		TFCItems.BismuthBronzeBoots = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.BlackBronzeBoots = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.BlackSteelBoots = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.BlueSteelBoots = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.BronzeBoots = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.CopperBoots = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.CopperPlate, proxy.getArmorRenderID("copper"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.WroughtIronBoots = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.RedSteelBoots = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;
		TFCItems.SteelBoots = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Boots",num), Armor.SteelPlate, proxy.getArmorRenderID("steel"), 3).setUnlocalizedName(Names[i]+" Boots")); num++;i++;

		i = 0;
		TFCItems.BismuthBronzeUnfinishedGreaves = 	(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.BlackBronzeUnfinishedGreaves = 	(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.BlackSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.BlueSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.BronzeUnfinishedGreaves = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.CopperUnfinishedGreaves = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.WroughtIronUnfinishedGreaves = 	(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.RedSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		TFCItems.SteelUnfinishedGreaves = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Greaves",num)).setUnlocalizedName(Names[i]+" Unfinished Greaves")); num++;i++;
		i = 0;
		TFCItems.BismuthBronzeGreaves = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.BlackBronzeGreaves = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.BlackSteelGreaves = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.BlueSteelGreaves = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.BronzeGreaves = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.CopperGreaves = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.CopperPlate, proxy.getArmorRenderID("copper"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.WroughtIronGreaves =		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.RedSteelGreaves = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		TFCItems.SteelGreaves = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Greaves",num), Armor.SteelPlate, proxy.getArmorRenderID("steel"), 2).setUnlocalizedName(Names[i]+" Greaves")); num++;i++;
		i = 0;
		TFCItems.BismuthBronzeUnfinishedChestplate = 	(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.BlackBronzeUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.BlackSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.BlueSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.BronzeUnfinishedChestplate = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.CopperUnfinishedChestplate = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.WroughtIronUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.RedSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		TFCItems.SteelUnfinishedChestplate = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Chestplate",num)).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); num++;i++;
		i = 0;
		TFCItems.BismuthBronzeChestplate =	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.BlackBronzeChestplate = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.BlackSteelChestplate = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.BlueSteelChestplate = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.BronzeChestplate = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.CopperChestplate = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.CopperPlate, proxy.getArmorRenderID("copper"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.WroughtIronChestplate = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.RedSteelChestplate = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		TFCItems.SteelChestplate = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Chestplate",num), Armor.SteelPlate, proxy.getArmorRenderID("steel"), 1).setUnlocalizedName(Names[i]+" Chestplate")); num++;i++;
		i = 0;
		TFCItems.BismuthBronzeUnfinishedHelmet = 	(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.BlackBronzeUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.BlackSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.BlueSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.BronzeUnfinishedHelmet = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.CopperUnfinishedHelmet = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.WroughtIronUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.RedSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		TFCItems.SteelUnfinishedHelmet = 			(new ItemUnfinishedArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Unfinished Helmet",num)).setUnlocalizedName(Names[i]+" Unfinished Helmet")); num++;i++;
		i = 0;
		TFCItems.BismuthBronzeHelmet = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.BlackBronzeHelmet = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.BlackSteelHelmet = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.BlueSteelHelmet = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.BronzeHelmet = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.CopperHelmet = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.CopperPlate, proxy.getArmorRenderID("copper"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.WroughtIronHelmet = 	(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.RedSteelHelmet = 		(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;
		TFCItems.SteelHelmet = 			(new ItemTFCArmor(TFC_Settings.getIntFor(config,"item",NamesNS[i]+" Helmet",num), Armor.SteelPlate, proxy.getArmorRenderID("steel"), 0).setUnlocalizedName(Names[i]+" Helmet")); num++;i++;

		i = 0;
		TFCItems.BrassSheet = 			new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.GoldSheet = 			new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.LeadSheet = 			new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.NickelSheet = 			new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.PigIronSheet = 		new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.PlatinumSheet = 		new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.SilverSheet = 			new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.SterlingSilverSheet = 	new ItemMetalSheet(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+" Sheet",num++)).setUnlocalizedName(NamesNSO[i++]+" Sheet");

		i = 0;
		TFCItems.BrassSheet2x = 			new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.GoldSheet2x = 				new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.LeadSheet2x = 				new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.NickelSheet2x = 			new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.PigIronSheet2x = 			new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.PlatinumSheet2x = 			new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.SilverSheet2x = 			new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.SterlingSilverSheet2x = 	new ItemMetalSheet2x(TFC_Settings.getIntFor(config,"item",NamesNSO[i]+"Sheet2x",num++)).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
	}

	public static Item[] Meals;
}
